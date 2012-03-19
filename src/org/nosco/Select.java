package org.nosco;

import static org.nosco.Constants.DIRECTION.DESCENDING;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.nosco.Constants.DB_TYPE;
import org.nosco.Constants.DIRECTION;
import org.nosco.Field.FK;
import org.nosco.QueryImpl.TableInfo;
import org.nosco.util.Misc;
import org.nosco.util.Tree.Callback;
import org.nosco.util.Tuple;



class Select<T extends Table> implements Iterable<T>, Iterator<T> {

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		if (rs != null && !rs.isClosed()) rs.close();
		if (ps != null && !ps.isClosed()) ps.close();
	}

	private String sql;
	private QueryImpl<T> query;
	private PreparedStatement ps;
	private ResultSet rs;
	private T next;
	private Field<?>[] selectedFields;
	private Field<?>[] selectedBoundFields;
	private Constructor<T> constructor;
	private Map<Class<? extends Table>,Constructor<? extends Table>> constructors =
			new HashMap<Class<? extends Table>, Constructor<? extends Table>>();
	private Map<Class<? extends Table>,Method> fkSetMethods =
			new HashMap<Class<? extends Table>,Method>();
	private Map<FK<?>,Method> fkSetSetMethods =
			new HashMap<FK<?>,Method>();
	private Connection conn;
	private Object[] nextRow;
	private boolean done = false;

	@SuppressWarnings("unchecked")
	Select(QueryImpl<T> query) {
		this.query = query;
		try {
			constructor = (Constructor<T>) query.getType().getDeclaredConstructor(
					new Field[0].getClass(), new Object[0].getClass(), Integer.TYPE, Integer.TYPE);
			constructor.setAccessible(true);

			for (TableInfo tableInfo : query.tableInfos) {
				Constructor<? extends Table> constructor = tableInfo.tableClass.getDeclaredConstructor(
						new Field[0].getClass(), new Object[0].getClass(), Integer.TYPE, Integer.TYPE);
				constructor.setAccessible(true);
				constructors.put(tableInfo.tableClass, constructor);
				try {
					Method setFKMethod  = (Method) tableInfo.tableClass.getDeclaredMethod(
							"SET_FK", Field.FK.class, Object.class);
					setFKMethod.setAccessible(true);
					fkSetMethods.put(tableInfo.tableClass, setFKMethod);
				} catch (NoSuchMethodException e) {
					/* ignore */
				}
				try {
					if (tableInfo.path != null && tableInfo.path.length > 0) {
						FK<?> fk = tableInfo.path[tableInfo.path.length - 1];
						if (fk.referencing.equals(tableInfo.tableClass)) {
							Method setFKSetMethod  = (Method) fk.referenced.getDeclaredMethod(
									"SET_FK_SET", Field.FK.class, Query.class);
							setFKSetMethod.setAccessible(true);
							fkSetSetMethods.put(fk, setFKSetMethod);
							//System.out.println("found "+ setFKSetMethod);
						}
					}
				} catch (NoSuchMethodException e) {
					/* ignore */
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	protected String getSQL() {
		return getSQL(new SqlContext(query)).a;
	}

	QueryImpl<T> getUnderlyingQuery() {
		return query;
	}

	protected Tuple<String,List<Object>> getSQL(SqlContext context) {
		selectedFields = query.getSelectFields(false);
		selectedBoundFields = query.getSelectFields(true);
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		if (query.distinct) sb.append("distinct ");
		if (query.getDBType()==DB_TYPE.SQLSERVER && query.top>0) {
			sb.append(" top ").append(query.top).append(" ");
		}
		if (query.globallyAppliedSelectFunction == null) {
			sb.append(Misc.join(", ", selectedBoundFields));
		} else {
			String[] x = new String[selectedBoundFields.length];
			for (int i=0; i < x.length; ++i) {
				x[i] = query.globallyAppliedSelectFunction + "("+ selectedBoundFields[i] +")";
			}
			sb.append(Misc.join(", ", x));
		}
		sb.append(" from ");
		sb.append(Misc.join(", ", query.getTableNameList()));
		Tuple<String, List<Object>> ret = query.getWhereClauseAndBindings(context);
		sb.append(ret.a);

		List<DIRECTION> directions = query.getOrderByDirections();
		List<Field<?>> fields = query.getOrderByFields();
		if (!context.inInnerQuery() && directions!=null & fields!=null) {
			sb.append(" order by ");
			int x = Math.min(directions.size(), fields.size());
			String[] tmp = new String[x];
			for (int i=0; i<x; ++i) {
				DIRECTION direction = directions.get(i);
				tmp[i] = fields.get(i) + (direction==DESCENDING ? " DESC" : "");
			}
			sb.append(Misc.join(", ", tmp));
		}

		if (query.getDBType()!=DB_TYPE.SQLSERVER && query.top>0) {
			sb.append(" limit ").append(query.top);
		}

		if (selectedFields.length > context.maxFields) {
			Misc.log(sb.toString(), null);
			throw new RuntimeException("too many fields selected: "+ selectedFields.length
					+" > "+ context.maxFields +" (note: inner queries inside a 'where x in " +
							"()' can have at most one returned column.  use onlyFields()" +
							"in the inner query)");
		}

		sql = sb.toString();
		return new Tuple<String,List<Object>>(sb.toString(), ret.b);
	}

//	protected List<Object> getSQLBindings() {
//		return query.getSQLBindings();
//	}

	@Override
	public Iterator<T> iterator() {
		try {
			conn = query.getConnR();
			SqlContext context = new SqlContext(query);
			Tuple<String, List<Object>> ret = getSQL(context);
			ps = conn.prepareStatement(ret.a);
			Misc.log(sql, ret.b);
			query.setBindings(ps, ret.b);
			query._preExecute(conn);
			ps.execute();
			rs = ps.getResultSet();
			done = false;
			//m = query.getType().getMethod("INSTANTIATE", Map.class);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return this;
	}

	private Object[] getNextRow() throws SQLException {
		Object[] tmp = peekNextRow();
		nextRow = null;
		return tmp;
	}

	private Object[] peekNextRow() throws SQLException {
		if (done) return null;
		if (nextRow != null) return nextRow;
		if (!rs.next()) {
			cleanUp();
			return null;
		}
		nextRow = new Object[selectedFields.length];
		for (int i=0; i<selectedFields.length; ++i) {
			if (selectedFields[i].TYPE == Long.class) nextRow[i] = rs.getLong(i+1);
			else if (selectedFields[i].TYPE == Double.class) {
				nextRow[i] = rs.getDouble(i+1);
				if (rs.wasNull()) nextRow[i] = null;
			}
			else if (selectedFields[i].TYPE == Character.class) {
				String s = rs.getString(i+1);
				if (s != null && s.length() > 0) nextRow[i] = s.charAt(0);
			}
			else nextRow[i] = rs.getObject(i+1);
		}
		return nextRow;
	}

	@Override
	public boolean hasNext() {
		if (next!=null) return true;
		try {
			Object[] fieldValues = getNextRow();
			if (fieldValues == null) return false;
			int objectSize = query.tableInfos.size();
			Table[] objects = new Table[objectSize];
			InMemoryQuery[] inMemoryCaches  = new InMemoryQuery[objectSize];
			QueryImpl.TableInfo baseTableInfo = null;
			for (int i=0; i<objectSize; ++i) {
				QueryImpl.TableInfo ti = query.tableInfos.get(i);
				if (i == 0) baseTableInfo = ti;
				if (ti.path == null) {
					if (next != null) continue;
					//System.out.println(ti.start +" "+ ti.end);
					next = (T) constructor.newInstance(selectedFields, fieldValues, ti.start, ti.end);
					objects[i] = next;
				} else {
					Table fkv = constructors.get(ti.table.getClass())
							.newInstance(selectedFields, fieldValues, ti.start, ti.end);
					objects[i] = fkv;
				}
			}
			for (int i=0; i<objectSize; ++i) {
				QueryImpl.TableInfo ti = query.tableInfos.get(i);
				for (int j=i+1; j<objectSize; ++j) {
					QueryImpl.TableInfo tj = query.tableInfos.get(j);
					if (tj.path == null) continue;
					if(startsWith(tj.path, ti.path)) {
						FK fk = tj.path[tj.path.length-1];
						if (fk.referencing.equals(objects[i].getClass()) &&
								fk.referenced.equals(objects[j].getClass())) {
							Method method = fkSetMethods.get(objects[i].getClass());
							method.invoke(objects[i], fk, objects[j]);
						}
						if (fk.referenced.equals(objects[i].getClass()) &&
								fk.referencing.equals(objects[j].getClass())) {
							//String key = key4IMQ(tj.path);
							InMemoryQuery<Table> cache = inMemoryCaches[j];
							if (cache == null) {
								cache = new InMemoryQuery<Table>();
								cache.cache.add(objects[j]);
								inMemoryCaches[j] = cache;
								Method method = fkSetSetMethods.get(fk);
								method.invoke(objects[i], fk, cache);
							}
						}
					}
				}
			}

			boolean sameObject = true;
			while (sameObject) {
				Object[] peekRow = peekNextRow();
				if (peekRow == null) break;
				for (int i=baseTableInfo.start; i < baseTableInfo.end; ++i) {
					sameObject &= fieldValues[i]==null ?
							peekRow[i]==null : fieldValues[i].equals(peekRow[i]);
				}
				if (sameObject) {

					Table[] peekObjects = new Table[objectSize];

					for (int i=0; i<objectSize; ++i) {
						QueryImpl.TableInfo ti = query.tableInfos.get(i);
						if (i == 0) baseTableInfo = ti;
						if (ti.path == null) {
							if (next != null) continue;
							//System.out.println(ti.start +" "+ ti.end);
							next = (T) constructor.newInstance(selectedFields, peekRow, ti.start, ti.end);
							peekObjects[i] = next;
						} else {
							Table fkv = constructors.get(ti.table.getClass())
									.newInstance(selectedFields, peekRow, ti.start, ti.end);
							peekObjects[i] = fkv;
						}
						if (objects[i]==null ? peekObjects[i]!=null : !objects[i].equals(peekObjects[i])) {
							InMemoryQuery<Table> cache = inMemoryCaches[i];
							if (cache!=null) cache.cache.add(peekObjects[i]);
						}
					}

					peekRow = getNextRow();
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		boolean hasNext = next != null;
		//if (!hasNext) cleanUp();
		return hasNext;
	}

	private String key4IMQ(FK<?>[] path) {
		StringBuffer sb = new StringBuffer();
		for (FK<?> fk : path) {
			sb.append(fk);
		}
		return sb.toString();
	}

	private void cleanUp() {
		if (done) return;
		try {
			query._postExecute(conn);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (!ThreadContext.inTransaction(query.ds)) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.done = true;
	}

	private boolean startsWith(FK[] path, FK[] path2) {
		if (path2 == null) return true;
		for (int i=0; i<path2.length; ++i) {
			if (path[i] != path2[i]) return false;
		}
		return true;
	}

	@Override
	public T next() {
		T t = next;
		next = null;
		return t;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
