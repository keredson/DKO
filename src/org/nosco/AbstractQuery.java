package org.nosco;

import static org.nosco.Constants.DIRECTION.DESCENDING;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nosco.Table.__PrimaryKey;

/**
 * shared methods between query implementations
 */
public abstract class AbstractQuery<T extends Table> implements Query<T> {

	final Class<T> type;

	AbstractQuery(final Query<T> q) {
		type = q.getType();
	}

	AbstractQuery(final Class<? extends Table> type) {
		this.type = (Class<T>) type;
	}

	@Override
	public Class<T> getType() {
		return type;
	}

	@Override
	public T get(final Condition... conditions) {
		return where(conditions).getTheOnly();
	}

	@Override
	public T get(final __PrimaryKey<T> pk) {
		Condition c = null;
		for (@SuppressWarnings("rawtypes") final Field f : pk.FIELDS()) {
			@SuppressWarnings("unchecked") final Condition tmp = f.eq(pk.get(f));
			c = c==null ? tmp : c.and(tmp);
		}
		return this.where(c).getTheOnly();
	}

	@Override
	public Iterable<T> none() {
		return Collections.emptyList();
	}

    @Override
    public long count() throws SQLException {
    	long c = 0;
    	for (final T x : this) ++c;
    	return c;
    }

	@Override
	public Query<T> top(final long i) {
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
	public <S, U> Map<S, Map<U, T>> mapBy(final Field<S> byField1, final Field<U> byField2)
			throws SQLException {
		final Map<S, Map<U, T>> ret = new LinkedHashMap<S, Map<U,T>>();
		for (final T t : this) {
			final S f1 = t.get(byField1);
			final U f2 = t.get(byField2);
			Map<U, T> inner = ret.get(f1);
			if (inner == null) {
				inner = new LinkedHashMap<U,T>();
				ret.put(f1, inner);
			}
			inner.put(f2, t);
		}
		return ret;
	}

	/**
	 * @deprecated Use {@link #collectBy(Field<S>)} instead
	 */
	@Override
	public <S> Map<S, Collection<T>> multiMapBy(final Field<S> byField)
			throws SQLException {
				return collectBy(byField);
			}

	@Override
	public <S> Map<S, Collection<T>> collectBy(final Field<S> byField)
			throws SQLException {
		final Map<S, Collection<T>> ret = new LinkedHashMap<S, Collection<T>>();
		for (final T t : this) {
			final S key = t.get(byField);
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
	public <S, U> Map<S, Map<U, Collection<T>>> collectBy(final Field<S> byField1, final Field<U> byField2) throws SQLException {
		final Map<S, Map<U, Collection<T>>> ret = new LinkedHashMap<S, Map<U, Collection<T>>>();
		for (final T t : this) {
			final S key1 = t.get(byField1);
			final U key2 = t.get(byField2);
			Map<U, Collection<T>> col = ret.get(key1);
			if (col == null) {
				col = new LinkedHashMap<U, Collection<T>>();
				ret.put(key1, col);
			}
			Collection<T> col2 = col.get(key2);
			if (col2 == null) {
				col2 = new ArrayList<T>();
				col.put(key2, col2);
			}
			col2.add(t);
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
					List<Field<?>> fields = null;
					@Override
					public boolean hasNext() {
						return it.hasNext();
					}
					@Override
					public Object[] next() {
						final T t = it.next();
						if (fields == null) fields = t.FIELDS();
						final Object[] oa = new Object[fields.size()];
						for (int i=0; i<oa.length; ++i) {
							oa[i] = t.get(fields.get(i));
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
	public Iterable<Map<Field<?>, Object>> asIterableOfMaps() {
		final Query<T> q = this;
		return new Iterable<Map<Field<?>, Object>>() {
			@Override
			public Iterator<Map<Field<?>, Object>> iterator() {
				final Iterator<T> it = q.iterator();
				return new Iterator<Map<Field<?>, Object>>() {
					List<Field<?>> fields = null;
					@Override
					public boolean hasNext() {
						return it.hasNext();
					}
					@Override
					public Map<Field<?>, Object> next() {
						final T t = it.next();
						if (fields == null) fields = t.FIELDS();
						final Map<Field<?>, Object> ret = new HashMap<Field<?>, Object>();
						for (final Field<?> field : fields) {
							ret.put(field, t.get(field));
						}
						return ret;
					}
					@Override
					public void remove() {
						it.remove();
					}
				};
			}
		};
	}

//	public Query<T> in(final Collection<T> set) {
//		return intersection(set);
//	}

	public Query<T> in(final T... ts) {
		final List<T> set = new ArrayList<T>();
		for (final T t : ts) {
			set.add(t);
		}
		return in(set);
	}

	@Override
	public int deleteAll() throws SQLException {
		return this.delete();
	}

}
