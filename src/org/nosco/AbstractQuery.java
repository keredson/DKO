package org.nosco;

import static org.nosco.Constants.DIRECTION.DESCENDING;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * shared methods between query implementations
 */
abstract class AbstractQuery<T extends Table> implements Query<T> {

	@Override
	public T get(final Condition... conditions) {
		return where(conditions).getTheOnly();
	}

	@Override
	public Iterable<T> none() {
		return Collections.emptyList();
	}

	@Override
	public Query<T> top(final int i) {
		return limit(i);
	}

	@Override
	public T first() {
		for(final T t : this.top(1)) {
			return t;
		}
		return null;
	}

	@Override
	public T latest(final Field<?> field) {
		for(final T t : orderBy(DESCENDING, field).top(1)) {
			return t;
		}
		return null;
	}

	@Override
	public boolean isEmpty() throws SQLException {
		return this.count()==0;
	}

	@Override
	public T getTheOnly() {
		T x = null;
		for (final T t : this.top(2)) {
			if (x==null) x = t;
			else throw new RuntimeException("more than one result found in Query.getTheOnly()");
		}
		return x;
	}

	@Override
	public long size() throws SQLException {
		return count();
	}

	@Override
	public <S> Map<S, T> mapBy(final Field<S> byField) throws SQLException {
		final Map<S, T> ret = new LinkedHashMap<S, T>();
		for (final T t : this) {
			ret.put(t.get(byField), t);
		}
		return ret;
	}

	@Override
	public <S, U> Map<S, Map<U, T>> mapBy(Field<S> byField1, Field<U> byField2)
			throws SQLException {
		final Map<S, Map<U, T>> ret = new LinkedHashMap<S, Map<U,T>>();
		for (final T t : this) {
			S f1 = t.get(byField1);
			U f2 = t.get(byField2);
			Map<U, T> inner = ret.get(f1);
			if (inner == null) {
				inner = new LinkedHashMap<U,T>();
				ret.put(f1, inner);
			}
			inner.put(f2, t);
		}
		return ret;
	}

	@Override
	public <S> Map<S, Collection<T>> multiMapBy(Field<S> byField)
			throws SQLException {
		final Map<S, Collection<T>> ret = new LinkedHashMap<S, Collection<T>>();
		for (final T t : this) {
			S key = t.get(byField);
			Collection<T> col = ret.get(key);
			if (col == null) {
				col = new ArrayList<T>();
				ret.put(key, col);
			}
			col.add(t);
		}
		return ret;
	}

	@Override
	public List<T> asList() {
		final List<T> list = new ArrayList<T>();
		for (final T t : this) list.add(t);
		return list;
	}

	@Override
	public <S> List<S> asList(final Field<S> field) {
		final List<S> ret = new ArrayList<S>();
		for (final S s : select(field)) {
			ret.add(s);
		}
		return ret;
	}

	@Override
	public Set<T> asSet() {
		final Set<T> set = new HashSet<T>();
		for (final T t : this) set.add(t);
		return set;
	}

	@Override
	public <S> Set<S> asSet(final Field<S> field) {
		final Set<S> ret = new HashSet<S>();
		for (final S s : this.distinct().select(field)) {
			ret.add(s);
		}
		return ret;
	}

	@Override
	public Iterable<Object[]> asIterableOfObjectArrays() {
		final Query<T> q = this;
		return new Iterable<Object[]>() {
			@Override
			public Iterator<Object[]> iterator() {
				final Iterator<T> it = q.iterator();
				return new Iterator<Object[]>() {
					Field<?>[] fields = null;
					@Override
					public boolean hasNext() {
						return it.hasNext();
					}
					@Override
					public Object[] next() {
						T t = it.next();
						if (fields == null) fields = t.FIELDS();
						Object[] oa = new Object[fields.length];
						for (int i=0; i<oa.length; ++i) {
							oa[i] = t.get(fields[i]);
						}
						return oa;
					}
					@Override
					public void remove() {
						it.remove();
					}
				};
			}
		};
	}

	@Override
	public int deleteAll() throws SQLException {
		return this.delete();
	}

}
