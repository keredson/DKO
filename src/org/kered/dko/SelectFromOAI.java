package org.kered.dko;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.kered.dko.DBQuery.JoinInfo;
import org.kered.dko.Field.FK;


class SelectFromOAI<T extends Table> implements ClosableIterator<T> {

	private static final int BATCH_SIZE = 2048;

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		close();
	}

	private static final Logger log = Logger.getLogger("org.kered.dko.SelectFromOAI");

	private final DBQuery<T> query;
	private T next;
	private Field<?>[] selectedFields;
	private final Map<Class<? extends Table>,Constructor<? extends Table>> constructors =
			new HashMap<Class<? extends Table>, Constructor<? extends Table>>();
	private final Map<Class<? extends Table>,Method> fkToOneSetMethods =
			new HashMap<Class<? extends Table>,Method>();
	private final Map<FK<?>,Method> fkToManySetMethods =
			new HashMap<FK<?>,Method>();
	private boolean done = false;
	Object[] lastFieldValues;
	private DataSource ds = null;
	private final UsageMonitor<T> usageMonitor;
	long count = 0;

	private boolean returnJoin;

	private Constructor<T> joinConstructor = null;

	private PeekableClosableIterator<Object[]> src;

	SelectFromOAI(final DBQuery<T> dbQuery) {
		this(dbQuery, true);
	}
	
	SelectFromOAI(final DBQuery<T> dbQuery, PeekableClosableIterator<Object[]> src) {
		this.src = src;
		query = dbQuery;
		usageMonitor = null;
		allTableInfos = query.getAllTableInfos();
		final List<Field<?>> selectFieldsList = query.getSelectFields(false);
		selectedFields = DBRowIterator.toArray(selectFieldsList);
		init();
	}
	
	SelectFromOAI(final DBQuery<T> dbQuery, final boolean useWarnings) {
		DBRowIterator<T> dbRowIterator = new DBRowIterator<T>(dbQuery, useWarnings);
		src = dbRowIterator;
		ds = dbRowIterator.ds;
		query = dbRowIterator.query;
		selectedFields = dbRowIterator.selectedFields;
		usageMonitor = dbRowIterator.usageMonitor;
		allTableInfos = query.getAllTableInfos();
		init();
	}
	
	private void init() {
		try {
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
				for (final JoinInfo<?,?> join : query.joinsToMany) {
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

			returnJoin = Join.class.isAssignableFrom(query.ofType);
			if (returnJoin) {
				joinConstructor = query.ofType.getDeclaredConstructor(Object[].class);//, Integer.TYPE, Collection.class);
				joinConstructor.setAccessible(true);
			}


		} catch (final SecurityException e) {
			e.printStackTrace();
			throw e;
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	DBQuery<T> getUnderlyingQuery() {
		return query;
	}

	@SuppressWarnings("rawtypes")
	private final Map<JoinInfo,InMemoryQuery> ttbMap = new HashMap<JoinInfo,InMemoryQuery>();

	@SuppressWarnings("unchecked")
	@Override
	public boolean hasNext() {
		if (query.top>0 && count >= query.top) {
			this.next = null;
			close();
			return false;
		}
		if (next!=null) return true;
		if (!src.hasNext()) return false;
		ttbMap.clear();
		Object[] prevFieldValues = null;
		Table[] prevObjects = null;
		try {
			do {
				final Object[] peekRow = src.peek();
				if (peekRow == null) break;
				if (prevFieldValues != null) {
					final TableInfo ti = allTableInfos.get(0);
					if (!Util.allTheSame(prevFieldValues, peekRow, ti.start, ti.end)) break;
				}

				final Object[] fieldValues = src.next();
				this.lastFieldValues = fieldValues;
				if (fieldValues == null) {
					close();
					return false;
				}
				final int objectSize = allTableInfos.size();
				final Table[] objects = new Table[objectSize];
				final boolean[] newObjectThisRow = new boolean[objectSize];
				@SuppressWarnings("unchecked")
				final
				LinkedHashSet<Table>[] inMemoryCacheSets = new LinkedHashSet[objectSize];
				final InMemoryQuery[] inMemoryCaches = new InMemoryQuery[objectSize];
				for (int i=0; i<objectSize; ++i) {
					final TableInfo ti = allTableInfos.get(i);
					if (Util.allTheSame(prevFieldValues, fieldValues, ti.start, ti.end)) {
						objects[i] = prevObjects[i];
						newObjectThisRow[i] = false;
					} else {
						if (Util.notAllNull(fieldValues, ti.start, ti.end)) {
							final Table fkv = constructors.get(ti.tableClass)
									.newInstance(selectedFields, fieldValues, ti.start, ti.end);
							fkv.__NOSCO_USAGE_MONITOR = usageMonitor;
							fkv.__NOSCO_ORIGINAL_DATA_SOURCE = ds;
							objects[i] = fkv;
						}
						newObjectThisRow[i] = true;
					}
				}
				if (next == null) {
					if (this.returnJoin) {
						next = (T) new Join(objects);
					} else {
						next = (T) objects[0];
					}
				}
				for(final JoinInfo<?,?> join : query.joinsToOne) {
					if (!newObjectThisRow[join.reffingTableInfo.position]) continue;
					final Object reffedObject = objects[join.reffedTableInfo.position];
					final Object reffingObject = objects[join.reffingTableInfo.position];
					final Method fkSetMethod = fkToOneSetMethods.get(join.reffingTableInfo.tableClass);
					if (reffingObject != null) {
						fkSetMethod.invoke(reffingObject, join.fk, reffedObject);
					}
				}
				for(final JoinInfo<?,?> join : query.joinsToMany) {
					final Object reffedObject = objects[join.reffedTableInfo.position];
					final Object reffingObject = objects[join.reffingTableInfo.position];
					final Method fkSetSetMethod = fkToManySetMethods.get(join.fk);
					InMemoryQuery tmpQuery = ttbMap.get(join);
					if (tmpQuery == null || newObjectThisRow[join.reffedTableInfo.position]) {
						if (reffedObject != null) {
							tmpQuery = new InMemoryQuery(join.fk.referencing);
							fkSetSetMethod.invoke(reffedObject, join.fk, tmpQuery);
							ttbMap.put(join, tmpQuery);
						}
					}
					if (newObjectThisRow[join.reffingTableInfo.position] && reffingObject != null) {
						tmpQuery.cache.add(reffingObject);
						final Method fkSetMethod = fkToOneSetMethods.get(join.reffingTableInfo.tableClass);
						fkSetMethod.invoke(reffingObject, join.fk, reffedObject);
					}
				}
				prevFieldValues = fieldValues;
				prevObjects = objects;
			} while (!query.joinsToMany.isEmpty());

		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
			close();
			throw new RuntimeException(e);
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
			close();
			throw new RuntimeException(e);
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
			close();
			throw new RuntimeException(e);
		} catch (final InstantiationException e) {
			e.printStackTrace();
			close();
			throw new RuntimeException(e);
		}
		final boolean hasNext = next != null;
		if (!hasNext) close();
		return hasNext;
	}

//	private String key4IMQ(final FK<?>[] path) {
//		final StringBuffer sb = new StringBuffer();
//		for (final FK<?> fk : path) {
//			sb.append(fk);
//		}
//		return sb.toString();
//	}

	@Override
	public synchronized void close() {
		if (done) return;
		src.close();
		done = true;
	}

	@Override
	public T next() {
		final T t = next;
		next = null;
		++count;
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
