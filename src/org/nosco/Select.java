package org.nosco;

import static org.nosco.Constants.DIRECTION.DESCENDING;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.sql.DataSource;

import org.nosco.Constants.DB_TYPE;
import org.nosco.Constants.DIRECTION;
import org.nosco.DBQuery.Join;
import org.nosco.Field.FK;
import org.nosco.Tuple.Tuple2;


class Select<T extends Table> implements Iterator<T> {

	private static final int BATCH_SIZE = 2048;

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (rs != null && !rs.isClosed()) rs.close();
		if (ps != null && !ps.isClosed()) ps.close();
	}

	private String sql;
	private final DBQuery<T> query;
	private PreparedStatement ps;
	private ResultSet rs;
	private T next;
	private Field<?>[] selectedFields;
	private Field<?>[] selectedBoundFields;
	private Constructor<T> constructor;
	private final Map<Class<? extends Table>,Constructor<? extends Table>> constructors =
			new HashMap<Class<? extends Table>, Constructor<? extends Table>>();
	private final Map<Class<? extends Table>,Method> fkToOneSetMethods =
			new HashMap<Class<? extends Table>,Method>();
	private final Map<FK<?>,Method> fkToManySetMethods =
			new HashMap<FK<?>,Method>();
	private Connection conn;
	private final Queue<Object[]> nextRows = new LinkedList<Object[]>();
	private boolean done = false;
	Object[] lastFieldValues;
	private boolean shouldCloseConnection = true;
	private SqlContext context = null;
	private DataSource ds = null;
	@SuppressWarnings("rawtypes")
	private final UsageMonitor<T> usageMonitor;
	private boolean initted = false;

	@SuppressWarnings("unchecked")
	Select(final DBQuery<T> dbQuery) {
		if (Context.usageWarningsEnabled()) {
			usageMonitor = new UsageMonitor<T>(dbQuery);
			this.query = usageMonitor.getSelectOptimizedQuery();
		} else {
			usageMonitor = null;
			this.query = dbQuery;
		}
		allTableInfos = query.getAllTableInfos();
		try {
			constructor = (Constructor<T>) query.getType().getDeclaredConstructor(
					new Field[0].getClass(), new Object[0].getClass(), Integer.TYPE, Integer.TYPE);
			constructor.setAccessible(true);

			final List<TableInfo> tableInfos = query.getAllTableInfos();
			for (final TableInfo tableInfo : tableInfos) {
				if (tableInfo.tableClass.getName().startsWith("org.nosco.TmpTableBuilder")) continue;
				final Constructor<? extends Table> constructor = tableInfo.tableClass.getDeclaredConstructor(
						new Field[0].getClass(), new Object[0].getClass(), Integer.TYPE, Integer.TYPE);
				constructor.setAccessible(true);
				constructors.put(tableInfo.tableClass, constructor);
				try {
					final Method setFKMethod  = tableInfo.tableClass.getDeclaredMethod(
							"SET_FK", Field.FK.class, Object.class);
					setFKMethod.setAccessible(true);
					fkToOneSetMethods.put(tableInfo.tableClass, setFKMethod);
				} catch (final NoSuchMethodException e) {
					/* ignore */
				}
			}
			try {
				for (final Join join : query.joinsToMany) {
					final FK<?> fk = join.fk;
					final Method setFKSetMethod  = fk.referenced.getDeclaredMethod(
							"SET_FK_SET", Field.FK.class, Query.class);
					setFKSetMethod.setAccessible(true);
					fkToManySetMethods.put(fk, setFKSetMethod);
					//System.out.println("found "+ setFKSetMethod);
				}
			} catch (final NoSuchMethodException e) {
				/* ignore */
			}
		} catch (final SecurityException e) {
			e.printStackTrace();
			throw e;
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	void init() {
		// old iterator method before merging
		try {
			ds  = query.getDataSource();
			final Tuple2<Connection,Boolean> connInfo = query.getConnR(ds);
			conn = connInfo.a;
			shouldCloseConnection  = connInfo.b;
			context  = new SqlContext(query);
			final Tuple2<String, List<Object>> ret = getSQL(context);
			Util.log(sql, ret.b);
			query._preExecute(context, conn);
			ps = conn.prepareStatement(ret.a);
			query.setBindings(ps, ret.b);
			ps.execute();
			rs = ps.getResultSet();
			done = false;
			//m = query.getType().getMethod("INSTANTIATE", Map.class);
		} catch (final SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (final SecurityException e) {
			e.printStackTrace();
			throw e;
		}
		initted  = true;
	}

	protected String getSQL() {
		return getSQL(new SqlContext(query)).a;
	}

	DBQuery<T> getUnderlyingQuery() {
		return query;
	}

	protected Tuple2<String,List<Object>> getSQL(final SqlContext context) {
		selectedFields = query.getSelectFields(false);
		this.usageMonitor.setSelectedFields(selectedFields);
		selectedBoundFields = query.getSelectFields(true);
		final StringBuffer sb = new StringBuffer();
		sb.append("select ");
		if (query.distinct) sb.append("distinct ");
		if (context.dbType==DB_TYPE.SQLSERVER && query.top>0 && query.joinsToMany.size()==0) {
			sb.append(" top ").append(query.top).append(" ");
		}
		if (query.globallyAppliedSelectFunction == null) {
			sb.append(Util.join(", ", selectedBoundFields));
		} else {
			final String[] x = new String[selectedBoundFields.length];
			for (int i=0; i < x.length; ++i) {
				x[i] = query.globallyAppliedSelectFunction + "("+ selectedBoundFields[i] +")";
			}
			sb.append(Util.join(", ", x));
		}
		sb.append(query.getFromClause(context));
		final Tuple2<String, List<Object>> ret = query.getWhereClauseAndBindings(context);
		sb.append(ret.a);

		final List<DIRECTION> directions = query.getOrderByDirections();
		final List<Field<?>> fields = query.getOrderByFields();
		if (!context.inInnerQuery() && directions!=null & fields!=null) {
			sb.append(" order by ");
			final int x = Math.min(directions.size(), fields.size());
			final String[] tmp = new String[x];
			for (int i=0; i<x; ++i) {
				final DIRECTION direction = directions.get(i);
				tmp[i] = Util.derefField(fields.get(i), context) + (direction==DESCENDING ? " DESC" : "");
			}
			sb.append(Util.join(", ", tmp));
		}

		if (context.dbType!=DB_TYPE.SQLSERVER && query.top>0 && query.joinsToMany.size()==0) {
			sb.append(" limit ").append(query.top);
		}

		if (selectedFields.length > context.maxFields) {
			Util.log(sb.toString(), null);
			throw new RuntimeException("too many fields selected: "+ selectedFields.length
					+" > "+ context.maxFields +" (note: inner queries inside a 'where x in " +
							"()' can have at most one returned column.  use onlyFields()" +
							"in the inner query)");
		}

		sql = sb.toString();
		return new Tuple2<String,List<Object>>(sb.toString(), ret.b);
	}

//	protected List<Object> getSQLBindings() {
//		return query.getSQLBindings();
//	}

	Object[] getNextRow() throws SQLException {
		final Object[] tmp = peekNextRow();
		return nextRows.poll();
	}

	Object[] peekNextRow() throws SQLException {
		if (!done && nextRows.isEmpty()) readNextRows(BATCH_SIZE);
		if (nextRows.isEmpty()) return null;
		else return nextRows.peek();
	}

	private int readNextRows(final int max) throws SQLException {
		if (rs == null) return 0;
		int c = 0;
		while (c < max) {
			if (!rs.next()) {
				cleanUp();
				//preFetchOtherJoins();
				return c;
			}
			++c;
			final Object[] nextRow = new Object[selectedFields.length];
			for (int i=0; i<selectedFields.length; ++i) {
				nextRow[i] = Util.fixObjectType(rs, selectedFields[i].TYPE, i+1);
			}
			nextRows.add(nextRow);
		}
		//preFetchOtherJoins();
		return c;
	}

	@SuppressWarnings("rawtypes")
	private final Map<Join,InMemoryQuery> ttbMap = new HashMap<Join,InMemoryQuery>();

	@Override
	public boolean hasNext() {
		if (!this.initted) init();
		if (query.top>0 && usageMonitor.count >= query.top) {
			this.next = null;
			cleanUp();
			return false;
		}
		if (next!=null) return true;
		ttbMap.clear();
		Object[] prevFieldValues = null;
		Table[] prevObjects = null;
		try {
			do {
				final Object[] peekRow = peekNextRow();
				if (peekRow == null) break;
				if (prevFieldValues != null) {
					final TableInfo ti = allTableInfos.get(0);
					if (!Util.allTheSame(prevFieldValues, peekRow, ti.start, ti.end)) break;
				}

				final Object[] fieldValues = getNextRow();
				this.lastFieldValues = fieldValues;
				if (fieldValues == null) return false;
				final int objectSize = allTableInfos.size();
				final Table[] objects = new Table[objectSize];
				final boolean[] newObjectThisRow = new boolean[objectSize];
				@SuppressWarnings("unchecked")
				final
				LinkedHashSet<Table>[] inMemoryCacheSets = new LinkedHashSet[objectSize];
				final InMemoryQuery[] inMemoryCaches = new InMemoryQuery[objectSize];
				TableInfo baseTableInfo = null;
				for (int i=0; i<objectSize; ++i) {
					final TableInfo ti = allTableInfos.get(i);
					if (i == 0) baseTableInfo = ti;
					if (ti.path == null) {
						if (next == null) {
							next = constructor.newInstance(selectedFields, fieldValues, ti.start, ti.end);
							next.__NOSCO_USAGE_MONITOR = usageMonitor;
							next.__NOSCO_ORIGINAL_DATA_SOURCE = ds;
							newObjectThisRow[i] = true;
						}
						objects[i] = next;
					} else {
						if (Util.allTheSame(prevFieldValues, fieldValues, ti.start, ti.end)) {
							objects[i] = prevObjects[i];
							newObjectThisRow[i] = false;
						} else {
							if (Util.notAllNull(fieldValues, ti.start, ti.end)) {
								final Table fkv = constructors.get(ti.table.getClass())
										.newInstance(selectedFields, fieldValues, ti.start, ti.end);
								fkv.__NOSCO_USAGE_MONITOR = usageMonitor;
								fkv.__NOSCO_ORIGINAL_DATA_SOURCE = ds;
								objects[i] = fkv;
							}
							newObjectThisRow[i] = true;
						}
					}
				}
				for(final Join join : query.joinsToOne) {
					final Object reffedObject = objects[join.reffedTableInfo.position];
					final Object reffingObject = objects[join.reffingTableInfo.position];
					if (!newObjectThisRow[join.reffingTableInfo.position]) continue;
					final Method fkSetMethod = fkToOneSetMethods.get(reffingObject.getClass());
					fkSetMethod.invoke(reffingObject, join.fk, reffedObject);
				}
				for(final Join join : query.joinsToMany) {
					final Object reffedObject = objects[join.reffedTableInfo.position];
					final Object reffingObject = objects[join.reffingTableInfo.position];
					final Method fkSetSetMethod = fkToManySetMethods.get(join.fk);
					InMemoryQuery tmpQuery = ttbMap.get(join);
					if (tmpQuery == null || newObjectThisRow[join.reffedTableInfo.position]) {
						tmpQuery = new InMemoryQuery();
						fkSetSetMethod.invoke(reffedObject, join.fk, tmpQuery);
						ttbMap.put(join, tmpQuery);
					}
					if (reffingObject != null) {
						tmpQuery.cache.add(reffingObject);
					}
				}
				prevFieldValues = fieldValues;
				prevObjects = objects;
			} while (!query.joinsToMany.isEmpty());

		} catch (final SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (final InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		final boolean hasNext = next != null;
		return hasNext;
	}

	private String key4IMQ(final FK<?>[] path) {
		final StringBuffer sb = new StringBuffer();
		for (final FK<?> fk : path) {
			sb.append(fk);
		}
		return sb.toString();
	}

	private void cleanUp() {
		if (done) return;
		try {
			query._postExecute(context, conn);
		} catch (final SQLException e1) {
			e1.printStackTrace();
		}
		if (shouldCloseConnection) {
			try {
				conn.close();
			} catch (final SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.done = true;
	}

	@Override
	public T next() {
		final T t = next;
		next = null;
		++usageMonitor.count;
		return t;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	static boolean startsWith(final FK<?>[] path, final FK<?>[] path2) {
		if (path2 == null) return true;
		for (int i=0; i<path2.length; ++i) {
			if (path[i] != path2[i]) return false;
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	private final Map<Class<? extends Table>,Map<Condition,WeakReference<Query<? extends Table>>>> subQueryCache = new HashMap<Class<? extends Table>,Map<Condition,WeakReference<Query<? extends Table>>>>();
	private final List<TableInfo> allTableInfos;

	@SuppressWarnings("unchecked")
	<S extends Table> Query<S> getSelectCachedQuery(final Class<S> cls, final Condition c) {
		Map<Condition, WeakReference<Query<? extends Table>>> x = subQueryCache.get(cls);
		if (x == null) {
			x = new HashMap<Condition,WeakReference<Query<? extends Table>>>();
			subQueryCache.put(cls, x);
		}
		final WeakReference<Query<? extends Table>> wr = x.get(c);
		Query<? extends Table> q = wr == null ? null : wr.get();
		if (q == null) {
			q = new InMemoryQuery<S>(QueryFactory.IT.getQuery(cls).use(this.query.getDataSource()).where(c));
			x.put(c, new WeakReference<Query<? extends Table>>(q));
		}
		return (Query<S>) q;
	}

}
