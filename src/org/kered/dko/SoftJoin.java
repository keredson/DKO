package org.kered.dko;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.kered.dko.Condition.Binary;
import org.kered.dko.Condition.Binary2;
import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.DIRECTION;
import org.kered.dko.Constants.JOIN_TYPE;
import org.kered.dko.Field.FK;
import org.kered.dko.Field.PK;
import org.kered.dko.Table.__Alias;

class SoftJoin<T extends Table> extends AbstractQuery<T> {

	private static final Logger log = Logger
			.getLogger("org.kered.dko.SoftJoin");

	private Query<? extends Table> q1;
	private Query<? extends Table> q2;
	private Condition condition;
	private final JOIN_TYPE joinType;
	//private Class<? extends J> type;
	private long limit = -1;

	private transient List<Field<?>> selectFields;



	public SoftJoin(final JOIN_TYPE joinType,
			final Class<? extends Table> type,
			final Class<? extends Table> t1, final Class<? extends Table> t2,
			final Condition on) {
		super(type);
		this.joinType = joinType;
		q1 = QueryFactory.IT.getQuery(t1);
		q2 = QueryFactory.IT.getQuery(t2);
		if (on!=null) joinAwareWhere(on);
	}

	public SoftJoin(final SoftJoin<T> q) {
		super(q.ofType);
		joinType = q.joinType;
		q1 = q.q1;
		q2 = q.q2;
		condition = q.condition;
		limit = q.limit;
	}

	public SoftJoin(final JOIN_TYPE joinType,
			final Class<? extends Table> type,
			final Class<? extends Table> t1, final __Alias<? extends Table> t2,
			final Condition on) {
		super(type);
		this.joinType = joinType;
		q1 = QueryFactory.IT.getQuery(t1);
		q2 = QueryFactory.IT.getQuery(t2.table);
		if (on!=null) joinAwareWhere(on);
	}

	public SoftJoin(final JOIN_TYPE joinType,
			final Class<? extends Table> type,
			final __Alias<? extends Table> t1, final Class<? extends Table> t2,
			final Condition on) {
		super(type);
		this.joinType = joinType;
		q1 = QueryFactory.IT.getQuery(t1.table);
		q2 = QueryFactory.IT.getQuery(t2);
		if (on!=null) joinAwareWhere(on);
	}

	public SoftJoin(final JOIN_TYPE joinType,
			final Class<? extends Table> type,
			final __Alias<? extends Table> t1,
			final __Alias<? extends Table> t2, final Condition on) {
		super(type);
		this.joinType = joinType;
		q1 = QueryFactory.IT.getQuery(t1.table);
		q2 = QueryFactory.IT.getQuery(t2.table);
		if (on!=null) joinAwareWhere(on);
	}

	public SoftJoin(final JOIN_TYPE joinType, final Class<? extends Table> type, final Query<? extends Table> q, final __Alias<? extends Table> t, final Condition on) {
		super(type);
		this.joinType = joinType;
		q1 = q;
		q2 = QueryFactory.IT.getQuery(t.table);
		if (on!=null) joinAwareWhere(on);
	}

	public SoftJoin(final JOIN_TYPE joinType, final Class<? extends Table> type, final Query<? extends Table> q, final Class<? extends Table> t, final Condition on) {
		super(type);
		this.joinType = joinType;
		q1 = q;
		q2 = QueryFactory.IT.getQuery(t);
		if (on!=null) joinAwareWhere(on);
	}

	@Override
	public Query<T> where(final Condition... conditions) {
		final SoftJoin<T> q = new SoftJoin<T>(this);
		q.joinAwareWhere(conditions);
		return q;
	}

	private void joinAwareWhere(final Condition... conditions) {
		for (final Condition condition : conditions) {
			if (conditionIsAllReferencingQuery(condition, q1)) {
				q1 = q1.where(condition);
			} else if (conditionIsAllReferencingQuery(condition, q2)) {
				q2 = q2.where(condition);
			} else {
				if (this.condition==null) this.condition = condition;
				else this.condition = this.condition.and(condition);
			}
		}
	}

	private boolean conditionIsAllReferencingQuery(final Condition condition,
			final Query<? extends Table> q) {
		if (!(q instanceof DBQuery))
			return false;
		try {
			final DBQuery<? extends Table> q2 = (DBQuery<? extends Table>) q
					.where(condition);
			final SqlContext context = new SqlContext(q2);
			q2.getWhereClauseAndBindings(context);
			return true;
		} catch (final Util.FieldNotPartOfSelectableTableSet e) {
			return false;
		}
	}

	@Override
	public Query<T> orderBy(final Field<?>... fields) {
		throw new RuntimeException("not implemented yet"); // TODO
	}

	@Override
	public Query<T> limit(final long n) {
		final SoftJoin<T> q = new SoftJoin<T>(this);
		q.limit = n;
		return q;
	}

	@Override
	public Query<T> distinct() {
		throw new RuntimeException("not implemented yet"); // TODO
	}

	@Override
	public Query<T> max() {
		throw new RuntimeException("not implemented yet"); // TODO
	}

	@Override
	public Query<T> min() {
		throw new RuntimeException("not implemented yet"); // TODO
	}

	@Override
	public Query<T> with(final FK... fields) {
		throw new RuntimeException("not implemented yet"); // TODO
	}

	@Override
	public Query<T> deferFields(final Field<?>... fields) {
		throw new RuntimeException("not implemented yet"); // TODO
	}

	@Override
	public Query<T> deferFields(final Collection<Field<?>> fields) {
		throw new RuntimeException("not implemented yet"); // TODO
	}

	@Override
	public Query<T> onlyFields(final Collection<Field<?>> fields) {
		throw new RuntimeException("not implemented yet"); // TODO
	}

	@Override
	public Query<T> onlyFields(final Field<?>... fields) {
		throw new RuntimeException("not implemented yet"); // TODO
	}

	@Override
	public Query<T> orderBy(final DIRECTION direction, final Field<?>... fields) {
		throw new RuntimeException("not implemented yet"); // TODO
	}

	@Override
	public Query<T> set(final Field<?> key, final Object value) {
		throw new UnsupportedOperationException(
				"Write operations are not supported in software joins.");
	}

	@Override
	public Query<T> set(final Map<Field<?>, Object> values) {
		throw new UnsupportedOperationException(
				"Write operations are not supported in software joins.");
	}

	@Override
	public Query<T> use(final DataSource ds) {
		log.warning("Setting a data source doesn't make sense in a software join.  You probably want to set it on one of the consituant queries.");
		return this;
	}

	@Override
	public Query<T> use(final Connection conn) {
		log.warning("Setting a connection doesn't make sense in a software join.  You probably want to set it on one of the consituant queries.");
		return this;
	}

	@Override
	public Query<T> use(final DB_TYPE type) {
		log.warning("Setting a database type doesn't make sense in a software join.  You probably want to set it on one of the consituant queries.");
		return this;
	}

	@Override
	public Query<T> in(final Collection<T> set) {
		throw new RuntimeException("not implemented yet"); // TODO
	}

	@Override
	public DataSource getDataSource() {
		return q1.getDataSource();
	}

	@Override
	public List<Field<?>> getSelectFields() {
		if (selectFields == null) {
			selectFields = new ArrayList<Field<?>>();
			selectFields.addAll(q1.getSelectFields());
			selectFields.addAll(q2.getSelectFields());
			selectFields = Collections.unmodifiableList(selectFields);
		}
		return selectFields;
	}

	@Override
	public Iterator<T> iterator() {

	    final long q1Rows = UsageStats.estimateRowCount(q1);
	    final long q2Rows = UsageStats.estimateRowCount(q2);
		System.out.println("q1Rows "+ q1.getType().getName() +" "+ q1.hashCode() +" "+ q1Rows);
		System.out.println("q2Rows "+ q2.getType().getName() +" "+ q2.hashCode() +" "+ q2Rows);
	    final Iterable<? extends Table> q1a;
	    final Iterable<? extends Table> q2a;
	    final Iterable<? extends Table> q1nulled = (this.joinType==Constants.JOIN_TYPE.RIGHT || this.joinType==Constants.JOIN_TYPE.OUTER) ? new AddNullAtEnd(q1) : q1;
	    final Iterable<? extends Table> q2nulled = (this.joinType==Constants.JOIN_TYPE.LEFT || this.joinType==Constants.JOIN_TYPE.OUTER) ? new AddNullAtEnd(q2) : q2;
	    final boolean swapped;
	    final boolean q2pk;
		if (q1Rows > q2Rows) {
	    	q1a = q1nulled;
	    	q2a = new LazyCacheIterable(q2nulled, (int) (q2Rows*1.1));
	    	swapped = false;
			q2pk = doesConditionCoverPK(q2.getType(), condition);
	    } else {
	    	q1a = q2nulled;
	    	q2a = new LazyCacheIterable(q1nulled, (int) (q1Rows*1.1));
	    	swapped = true;
			q2pk = doesConditionCoverPK(q1.getType(), condition);
	    }
		
		System.out.println("swapped: "+ swapped);
		Constructor c = null;
		try {
			c = getType().getDeclaredConstructor(Object[].class, Integer.TYPE, Collection.class);
			if (!c.isAccessible()) c.setAccessible(true);
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}
		final Set<Field<?>> fields = Collections.unmodifiableSet(new HashSet<Field<?>>(getSelectFields()));
		final Constructor jc = c;
		final int st1 = getObjectSizeOfQuery(q1);
		final int st2 = getObjectSizeOfQuery(q2);

		return new ClosableIterator<T>() {

			Iterator<? extends Table> q1i = q1a.iterator();
			Iterator<? extends Table> q2i = q2a.iterator();
			boolean matchedq1 = false;
			boolean matchedq2 = false;
			long count = 0;
			Table t1 = null;
			private T next;
			boolean first = true;

			@Override
			public boolean hasNext() {
				if (next!=null) return true;
				if ((limit>=0 && count>=limit)) {
					close();
					return false;
				}
				while (q1i.hasNext() || (q2i!=null && q2i.hasNext())) {
					final T t = peekNext();
					if (t1==null && q1i.hasNext()) t1 = q1i.next();
					boolean matches = true;
					if (condition!=null) {
						Object q1o = swapped ? ((Join)t).r : ((Join)t).l;
						Object q2o = swapped ? ((Join)t).l : ((Join)t).r;
						if (!matchedq1 && q1o==null) {
							matchedq1 = true;
						} else if (!matchedq2 && q2o==null) {
							matchedq2 = true;
						} else {
							matches &= condition.matches(t);
							if (matches) {
								matchedq1 = true;
								matchedq2 = true;
							}
						}
					}
					if (matches) {
						next = t;
						if (q2pk) {
							if (q1i.hasNext()) {
								q2i = q2a.iterator();
								t1 = q1i.next();
							} else {
								q2i = null;
							}
						}
						return true;
					}
				}
				close();
				return false;
			}

			@Override
			public T next() {
				++count;
				final T ret = next;
				next = null;
				return ret;
			}

			public T peekNext() {
				if (first) {
					first = false;
					if (q1i.hasNext()) t1 = q1i.next();
					else return null;
				}
				final Table t2;
				if (!q2i.hasNext() && q1i.hasNext()) {
					t1 = q1i.next();
					if (q2i instanceof ClosableIterator) ((ClosableIterator)q2i).close();
					q2i = q2a.iterator();
					matchedq2 = false;
					// add an extra null in for some join types
					t2 = q2i.next();
				} else {
					t2 = q2i.next();
				}
				final Object[] oa = new Object[st1+st2];
				try {
					if (swapped) {
						if (st2==1) oa[0] = t2;
						else ((Join)t2).populateObjectArray(oa, 0);
						if (st1==1) oa[st2] = t1;
						else ((Join)t1).populateObjectArray(oa, st2);
						return (T) jc.newInstance(oa, 0, fields);
					} else {
						if (st1==1) oa[0] = t1;
						else ((Join)t1).populateObjectArray(oa, 0);
						if (st2==1) oa[st1] = t2;
						else ((Join)t2).populateObjectArray(oa, st1);
						return (T) jc.newInstance(oa, 0, fields);
					}
				} catch (final IllegalArgumentException e) {
					e.printStackTrace();
				} catch (final InstantiationException e) {
					e.printStackTrace();
				} catch (final IllegalAccessException e) {
					e.printStackTrace();
				} catch (final InvocationTargetException e) {
					e.printStackTrace();
				}
				return null;
			}

//			private Table addNullForSomeJoins(final Iterator<? extends Table> i) {
//				return joinType==JOIN_TYPE.LEFT || joinType==JOIN_TYPE.OUTER ? null : (i.hasNext() ? i.next() : null);
//			}
//
//			private Table addNullForOtherJoins(final Iterator<? extends Table> i) {
//				return joinType==JOIN_TYPE.RIGHT || joinType==JOIN_TYPE.OUTER ? null : (i.hasNext() ? i.next() : null);
//			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public void close() {
				if (q1i instanceof ClosableIterator) ((ClosableIterator)q1i).close();
				if (q2i instanceof ClosableIterator) ((ClosableIterator)q2i).close();
			}
		};
	}

	private static boolean doesConditionCoverPK(Class<? extends Table> type, Condition condition) {
		PK<? extends Table> pk = Util.getPK(type);
		if (pk==null) return false;
		Set<Field<?>> fields = new HashSet<Field<?>>(pk.GET_FIELDS());
		List<Condition> conditions;
		if (condition instanceof Condition.And) {
			conditions = ((Condition.And)condition).conditions;
		} else {
			conditions = new ArrayList<Condition>();
			conditions.add(condition);
		}
		for (Condition c : conditions) {
			if (c instanceof Binary) {
				Binary bc = (Binary)c;
				if (bc.cmp!=null && "=".equals(bc.cmp.trim())) {
					fields.remove(bc.field);
					fields.remove(bc.field2);
				}
			}
			if (c instanceof Binary2) {
				Binary2 bc = (Binary2)c;
				if (bc.cmp!=null && "=".equals(bc.cmp.trim())) {
					fields.remove(bc.o1);
					fields.remove(bc.o2);
				}
			}
		}
		return fields.isEmpty();
	}

	private int getObjectSizeOfQuery(final Query<? extends Table> q) {
		try {
			final java.lang.reflect.Field f = q.getClass().getDeclaredField("SIZE");
			return f.getInt(q);
		} catch (final NoSuchFieldException e) {
			// not a Join._Query
			return 1;
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	static class AddNullAtEnd<E extends Table> implements Iterable<E> {
		private Query<E> q;
		public AddNullAtEnd(Query<E> q) {
			this.q = q;
		}
		@Override
		public Iterator<E> iterator() {
			final Iterator<E> i = q.iterator();
			return new Iterator<E>() {
				boolean sentLastNull = false;
				@Override
				public boolean hasNext() {
					return i.hasNext() || !sentLastNull;
				}
				@Override
				public E next() {
					if (i.hasNext()) return i.next();
					sentLastNull = true;
					return null;
				}
				@Override
				public void remove() {
					i.remove();
				}
			};
		}

	}

}
