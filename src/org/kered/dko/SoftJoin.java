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

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.DIRECTION;
import org.kered.dko.Constants.JOIN_TYPE;
import org.kered.dko.Field.FK;

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
			if (SoftJoinUtil.conditionIsAllReferencingQuery(condition, q1)) {
				q1 = q1.where(condition);
			} else if (SoftJoinUtil.conditionIsAllReferencingQuery(condition, q2)) {
				q2 = q2.where(condition);
			} else {
				if (this.condition==null) this.condition = condition;
				else this.condition = this.condition.and(condition);
			}
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
		System.err.println("q1Rows "+ q1.getType().getName() +" "+ q1.hashCode() +" "+ q1Rows);
		System.err.println("q2Rows "+ q2.getType().getName() +" "+ q2.hashCode() +" "+ q2Rows);
	    final Iterable<? extends Table> qXa;
	    final Iterable<? extends Table> qYa;
	    final Iterable<? extends Table> q1nulled = (this.joinType==Constants.JOIN_TYPE.RIGHT || this.joinType==Constants.JOIN_TYPE.OUTER) ? new SoftJoinUtil.AddNullAtEnd(q1) : q1;
	    final Iterable<? extends Table> q2nulled = (this.joinType==Constants.JOIN_TYPE.LEFT || this.joinType==Constants.JOIN_TYPE.OUTER) ? new SoftJoinUtil.AddNullAtEnd(q2) : q2;
	    final boolean swapped;
	    final boolean q1pk, q2pk;
	    final boolean qXpk, qYpk;
	    final Map<Field<?>, Field<?>> fieldOpposingPK;
		q1pk = SoftJoinUtil.doesConditionCoverPK(q1.getType(), condition);
		q2pk = SoftJoinUtil.doesConditionCoverPK(q2.getType(), condition);
		if (q1Rows > q2Rows) { // && !(q2pk && !q1pk)
	    	qXa = q1nulled;
	    	qYa = new LazyCacheIterable(q2nulled, (int) (q2Rows*1.1));
	    	swapped = false;
			qXpk = q1pk;
			if (qXpk) fieldOpposingPK = SoftJoinUtil.getFieldsOpposingPK(q1.getType(), condition);
			else fieldOpposingPK = null;
			qYpk = q2pk;
	    } else {
	    	qXa = q2nulled;
	    	qYa = new LazyCacheIterable(q1nulled, (int) (q1Rows*1.1));
	    	swapped = true;
			qXpk = q2pk;
			if (qXpk) fieldOpposingPK = SoftJoinUtil.getFieldsOpposingPK(q2.getType(), condition);
			else fieldOpposingPK = null;
			qYpk = q1pk;
	    }
		
		System.err.println("swapped: "+ swapped);
		System.err.println("q1.getType(): "+ q1.getType());
		System.err.println("q2.getType(): "+ q2.getType());
		Constructor c = null;
		try {
			c = getType().getDeclaredConstructor(Table.class, Table.class);
			if (!c.isAccessible()) c.setAccessible(true);
		} catch (final SecurityException e) {
			e.printStackTrace();
		} catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}
		final Set<Field<?>> fields = Collections.unmodifiableSet(new HashSet<Field<?>>(getSelectFields()));
		final Constructor jc = c;
		final int st1 = SoftJoinUtil.getObjectSizeOfQuery(q1);
		final int st2 = SoftJoinUtil.getObjectSizeOfQuery(q2);

		return new ClosableIterator<T>() {

			Iterator<? extends Table> qXi = qXa.iterator();
			Iterator<? extends Table> qYi = qYa.iterator();
			boolean matchedqX = false;
			boolean matchedqY = false;
			long count = 0;
			Table tX = null;
			private T next;
			boolean first = true;
			boolean firstPassQY = true;

			@Override
			public boolean hasNext() {
				if (next!=null) return true;
				if ((limit>=0 && count>=limit)) {
					close();
					return false;
				}
				while (qXi.hasNext() || (qYi!=null && qYi.hasNext())) {
					final T t = peekNext();
					if (t==null) continue;
					if (tX==null && qXi.hasNext()) tX = qXi.next();
					boolean matches = true;
					if (condition!=null) {
						Object qXo = swapped ? ((Join)t).r : ((Join)t).l;
						Object qYo = swapped ? ((Join)t).l : ((Join)t).r;
						if (!matchedqX && qXo==null) {
							matchedqX = true;
						} else if (!matchedqY && qYo==null) {
							matchedqY = true;
						} else {
							matches &= condition.matches(t);
							if (matches) {
								matchedqX = true;
								matchedqY = true;
							}
						}
					}
					if (matches) {
						next = t;
						if (qYpk) {
							if (qXi.hasNext()) {
								qYi = qYa.iterator();
								firstPassQY = false;
								tX = qXi.next();
							} else {
								qYi = null;
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
			
			Set<List> seenTYKeys = new HashSet<List>();
			private List lastTXPKValue = null;

			public T peekNext() {
				if (first) {
					first = false;
					if (qXi.hasNext()) {
						tX = qXi.next();
						if (qXpk) registerTX();
					}
					else return null;
				}
				final Table tY;
				if (!qYi.hasNext() && qXi.hasNext()) {
					tX = qXi.next();
					if (qXpk) {
						List values = registerTX();
						while (!isT1inT2(values)) {
							if (qXi.hasNext()) {
								tX = qXi.next();
								values = registerTX();
							} else {
								return null;
							}
						}
					}
					if (qYi instanceof ClosableIterator) ((ClosableIterator)qYi).close();
					if (qXpk && weHitAll()) return null;
					qYi = qYa.iterator();
					firstPassQY = false;
					matchedqY = false;
					if (!qYi.hasNext()) return null;
					tY = qYi.next();
				} else {
					tY = qYi.next();
				}
				if (firstPassQY && qXpk) registerTY(tY);
				try {
					if (swapped) {
						return (T) jc.newInstance(tY, tX);
					} else {
						return (T) jc.newInstance(tX, tY);
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

			private boolean isT1inT2(List values) {
				return seenTYKeys.contains(values);
			}

			private List registerTX() {
				if (tX==null) return Collections.emptyList();
				List values = new ArrayList();
				for (Field<?> f : fieldOpposingPK.keySet()) {
					values.add(tX.get(f));
				}
				this.lastTXPKValue = values;
				return values;
			}

			private void registerTY(Table t2) {
				List values = new ArrayList();
				for (Field<?> f : fieldOpposingPK.values()) {
					values.add(t2.get(f));
				}
				seenTYKeys.add(values);
			}

			private boolean weHitAll() {
				Set<List> keysLeft = seenTYKeys; //new HashSet<List>(seenTYKeys);
				keysLeft.remove(lastTXPKValue);
				boolean weHitAll = keysLeft.isEmpty();
				if (!weHitAll) {
					int size = keysLeft.size();
					if (size%10==0 || size<10) System.err.println("weHitAll? "+ weHitAll +" "+ size);
				}
				return weHitAll;
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
				if (qXi instanceof ClosableIterator) ((ClosableIterator)qXi).close();
				if (qYi instanceof ClosableIterator) ((ClosableIterator)qYi).close();
			}
		};
	}


}
