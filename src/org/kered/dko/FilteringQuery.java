package org.kered.dko;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.DIRECTION;
import org.kered.dko.Field.FK;

class FilteringQuery<T extends Table> extends AbstractQuery<T> implements MatryoshkaQuery<T> {

	private Query<T> q;
	private Condition condition = null;
	private long top = -1;

	FilteringQuery(final FilteringQuery<T> src) {
		super(src.getType());
		this.q = src.q;
		this.condition = src.condition;
		this.top = src.top;
	}

	FilteringQuery(final Query<T> q, final Condition... conditions) {
		super(q);
		this.q = q;
		if (conditions==null || conditions.length==0) return;
		condition = conditions[0];
		if (conditions.length>1) {
			Condition[] c2 = new Condition[conditions.length-1];
			System.arraycopy(conditions, 1, c2, 0, conditions.length-1);
			condition = condition.and(c2);
		}
	}

	@Override
	public Query<T> where(final Condition... conditions) {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		if (condition==null) {
			condition = conditions[0];
			if (conditions.length>1) {
				Condition[] c2 = new Condition[conditions.length-1];
				System.arraycopy(conditions, 1, c2, 0, conditions.length-1);
				condition = condition.and(c2);
			}
		} else {
			this.condition = this.condition.and(conditions);
		}
		return ret;
	}

	@Override
	public Query<T> limit(long n) {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.limit(-1);
		ret.top = n;
		return ret;
	}

	@Override
	public Query<T> distinct() {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.distinct();
		return ret;
	}

	@Override
	public Query<T> avg() {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.avg();
		return ret;
	}

	@Override
	public Query<T> max() {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.max();
		return ret;
	}

	@Override
	public Query<T> min() {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.min();
		return ret;
	}

	@Override
	public Query<T> with(FK... fields) {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.with(fields);
		return ret;
	}

	@Override
	public Query<T> deferFields(Field<?>... fields) {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.deferFields(fields);
		return ret;
	}

	@Override
	public Query<T> deferFields(Collection<Field<?>> fields) {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.deferFields(fields);
		return ret;
	}

	@Override
	public Query<T> onlyFields(Collection<Field<?>> fields) {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.onlyFields(fields);
		return ret;
	}

	@Override
	public Query<T> onlyFields(Field<?>... fields) {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.onlyFields(fields);
		return ret;
	}

	@Override
	public Query<T> set(Field<?> key, Object value) {
		throw new IllegalStateException("this is a read-only query");
	}

	@Override
	public Query<T> set(Map<Field<?>, Object> values) {
		throw new IllegalStateException("this is a read-only query");
	}

	@Override
	public Query<T> use(DataSource ds) {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.use(ds);
		return ret;
	}

	@Override
	public Query<T> use(Connection conn) {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.use(conn);
		return ret;
	}

	@Override
	public Query<T> use(DB_TYPE type) {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.use(type);
		return ret;
	}

	@Override
	public Query<T> in(Collection<T> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSource getDataSource() {
		return q.getDataSource();
	}

	@Override
	public List<Field<?>> getSelectFields() {
		return q.getSelectFields();
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {

			final Iterator<T> i = q.iterator();
			T next = null;
			int count = 0;

			@Override
			public boolean hasNext() {
				if (next!=null) return true;
				if (top > -1 && count >= top) return false;
				while (i.hasNext()) {
					T candidate = i.next();
					if (condition.matches(candidate)) {
						next = candidate;
						return true;
					}
				}
				return false;
			}

			@Override
			public T next() {
				T ret = next;
				next = null;
				++count;
				return ret;
			}

			@Override
			public void remove() {
				i.remove();
			}
		};
	}

	@Override
	public Collection<Query<? extends Table>> getUnderlying() {
		Collection<Query<? extends Table>> ret = new ArrayList();
		ret.add(q);
		return ret;
	}

	@Override
	public Query<T> orderBy(OrderByExpression<?>... obes) {
		FilteringQuery<T> ret = new FilteringQuery<T>(this);
		ret.q = ret.q.orderBy(obes);
		return ret;
	}

}
