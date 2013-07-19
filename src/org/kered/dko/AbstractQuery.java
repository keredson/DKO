package org.kered.dko;

import static org.kered.dko.Constants.DIRECTION.DESCENDING;

import java.io.File;
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

import org.kered.dko.Table.__Alias;
import org.kered.dko.Table.__PrimaryKey;

/**
 * This class contains default implementations of some of the methods required of Query.
 */
public abstract class AbstractQuery<T extends Table> implements Query<T> {

	final Class<T> ofType;
	private boolean applyGlobalMaxFunction = false;

	AbstractQuery(final Query<T> q) {
		ofType = q.getType();
	}

	AbstractQuery(final AbstractQuery<T> q) {
		ofType = q.ofType;
		applyGlobalMaxFunction = q.applyGlobalMaxFunction;
	}

	/**
	 * @deprecated Use {@link #asIterableOf(Field<S>)} instead
	 */
	@Deprecated
	@Override
	public <S> Iterable<S> select(final Field<S> field) {
		return this.asIterableOf(field);
	}

	@Override
	public Query<T> toMemory() {
		return new InMemoryQuery<T>(this);
	}

	public AbstractQuery(final Class<? extends Table> type) {
		this.ofType = (Class<T>) type;
	}

	@Override
	public Class<T> getType() {
		return ofType;
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
	@Deprecated
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
		for (final S s : asIterableOf(field)) {
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
		for (final S s : this.distinct().asIterableOf(field)) {
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
					List<Field<?>> fields = q.getSelectFields();
					@Override
					public boolean hasNext() {
						return it.hasNext();
					}
					@Override
					public Object[] next() {
						final T t = it.next();
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
		final List<Field<?>> fields = q.getSelectFields();
		return new Iterable<Map<Field<?>, Object>>() {
			@Override
			public Iterator<Map<Field<?>, Object>> iterator() {
				final Iterator<T> it = q.iterator();
				return new Iterator<Map<Field<?>, Object>>() {
					@Override
					public boolean hasNext() {
						return it.hasNext();
					}
					@Override
					public Map<Field<?>, Object> next() {
						final T t = it.next();
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

	@Override
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

	@SuppressWarnings("unchecked")
	@Override
	public <S extends Number> S sum(final Field<S> f) throws SQLException {
		if (Byte.class.equals(f.TYPE)) {
			byte sum = 0;
			for (final T t : this) {
				sum += t.get(f).byteValue();
			}
			return (S) Byte.valueOf(sum);
		}
		if (Double.class.equals(f.TYPE)) {
			double sum = 0;
			for (final T t : this) {
				sum += t.get(f).doubleValue();
			}
			return (S) Double.valueOf(sum);
		}
		if (Float.class.equals(f.TYPE)) {
			float sum = 0;
			for (final T t : this) {
				sum += t.get(f).floatValue();
			}
			return (S) Float.valueOf(sum);
		}
		if (Integer.class.equals(f.TYPE)) {
			int sum = 0;
			for (final T t : this) {
				sum += t.get(f).intValue();
			}
			return (S) Integer.valueOf(sum);
		}
		if (Long.class.equals(f.TYPE)) {
			long sum = 0;
			for (final T t : this) {
				sum += t.get(f).longValue();
			}
			return (S) Long.valueOf(sum);
		}
		if (Short.class.equals(f.TYPE)) {
			short sum = 0;
			for (final T t : this) {
				sum += t.get(f).shortValue();
			}
			return (S) Short.valueOf(sum);
		}
		throw new IllegalArgumentException("unsupported number type: "+ f.TYPE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R, S extends Number> Map<R, S> sumBy(final Field<S> sumField, final Field<R> byField)
			throws SQLException {
		final Map<R,S> ret = new HashMap<R,S>();
		for (final T t : this) {
			final R key = t.get(byField);
			S value = ret.get(key);
			if (Byte.class.equals(sumField.TYPE)) {
				if (value == null) value = (S) Byte.valueOf((byte) 0);
				value = (S) Byte.valueOf((byte) ((Byte)value + (Byte)t.get(sumField)));
			}
			if (Double.class.equals(sumField.TYPE)) {
				if (value == null) value = (S) Double.valueOf(0);
				value = (S) Double.valueOf(((Double)value + (Double)t.get(sumField)));
			}
			if (Float.class.equals(sumField.TYPE)) {
				if (value == null) value = (S) Float.valueOf(0);
				value = (S) Float.valueOf(((Float)value + (Float)t.get(sumField)));
			}
			if (Integer.class.equals(sumField.TYPE)) {
				if (value == null) value = (S) Integer.valueOf(0);
				value = (S) Integer.valueOf(((Integer)value + (Integer)t.get(sumField)));
			}
			if (Long.class.equals(sumField.TYPE)) {
				if (value == null) value = (S) Long.valueOf(0);
				value = (S) Long.valueOf(((Long)value + (Long)t.get(sumField)));
			}
			if (Short.class.equals(sumField.TYPE)) {
				if (value == null) value = (S) Short.valueOf((short) 0);
				value = (S) Short.valueOf((short) ((Short)value + (Short)t.get(sumField)));
			}
			ret.put(key, value);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S extends Number> S average(final Field<S> f) throws SQLException {
		if (Byte.class.equals(f.TYPE)) {
			byte sum = 0;
			long count = 0;
			for (final T t : this) {
				++count;
				sum += t.get(f).byteValue();
			}
			return (S) Byte.valueOf((byte) (sum / count));
		}
		if (Double.class.equals(f.TYPE)) {
			double sum = 0;
			long count = 0;
			for (final T t : this) {
				++count;
				sum += t.get(f).doubleValue();
			}
			return (S) Double.valueOf(sum / count);
		}
		if (Float.class.equals(f.TYPE)) {
			float sum = 0;
			long count = 0;
			for (final T t : this) {
				++count;
				sum += t.get(f).floatValue();
			}
			return (S) Float.valueOf(sum / count);
		}
		if (Integer.class.equals(f.TYPE)) {
			int sum = 0;
			long count = 0;
			for (final T t : this) {
				++count;
				sum += t.get(f).intValue();
			}
			return (S) Integer.valueOf((int) (sum / count));
		}
		if (Long.class.equals(f.TYPE)) {
			long sum = 0;
			long count = 0;
			for (final T t : this) {
				++count;
				sum += t.get(f).longValue();
			}
			return (S) Long.valueOf(sum / count);
		}
		if (Short.class.equals(f.TYPE)) {
			short sum = 0;
			long count = 0;
			for (final T t : this) {
				++count;
				sum += t.get(f).shortValue();
			}
			return (S) Short.valueOf((short) (sum / count));
		}
		throw new IllegalArgumentException("unsupported number type: "+ f.TYPE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R, S extends Number> Map<R, S> averageBy(final Field<S> sumField, final Field<R> byField)
			throws SQLException {
		throw new RuntimeException("please implement me!");
//		final Map<R,S> ret = new HashMap<R,S>();
//		for (final T t : this) {
//			final R key = t.get(byField);
//			S value = ret.get(key);
//			if (Byte.class.equals(sumField.TYPE)) {
//				if (value == null) value = (S) Byte.valueOf((byte) 0);
//				value = (S) Byte.valueOf((byte) ((Byte)value + (Byte)t.get(sumField)));
//			}
//			if (Double.class.equals(sumField.TYPE)) {
//				if (value == null) value = (S) Double.valueOf(0);
//				value = (S) Double.valueOf(((Double)value + (Double)t.get(sumField)));
//			}
//			if (Float.class.equals(sumField.TYPE)) {
//				if (value == null) value = (S) Float.valueOf(0);
//				value = (S) Float.valueOf(((Float)value + (Float)t.get(sumField)));
//			}
//			if (Integer.class.equals(sumField.TYPE)) {
//				if (value == null) value = (S) Integer.valueOf(0);
//				value = (S) Integer.valueOf(((Integer)value + (Integer)t.get(sumField)));
//			}
//			if (Long.class.equals(sumField.TYPE)) {
//				if (value == null) value = (S) Long.valueOf(0);
//				value = (S) Long.valueOf(((Long)value + (Long)t.get(sumField)));
//			}
//			if (Short.class.equals(sumField.TYPE)) {
//				if (value == null) value = (S) Short.valueOf((short) 0);
//				value = (S) Short.valueOf((short) ((Short)value + (Short)t.get(sumField)));
//			}
//			ret.put(key, value);
//		}
//		return ret;
	}

	@Override
	public <S> Map<S, Integer> countBy(final Field<S> byField) throws SQLException {
		final Map<S, Integer> ret = new HashMap<S, Integer>();
		for (final T t : this) {
			final S key = t.get(byField);
			Integer value = ret.get(key);
			if (value == null) value = 0;
			value += 1;
			ret.put(key, value);
		}
		return ret;
	}

	@Override
	public Iterable<T> all() {
		return this;
	}

	@Override
	public Object insert() throws SQLException {
		throw new UnsupportedOperationException("writes on "+ this.getClass().getSimpleName() +" are not supported");
	}

	@Override
	public int update() throws SQLException {
		throw new UnsupportedOperationException("writes on "+ this.getClass().getSimpleName() +" are not supported");
	}

	@Override
	public int delete() throws SQLException {
		throw new UnsupportedOperationException("writes on "+ this.getClass().getSimpleName() +" are not supported");
	}

	@Override
	public Query<T> cross(final __Alias<? extends Table> t) {
		throw new UnsupportedOperationException("joins on "+ this.getClass().getSimpleName() +" are not supported");
	}

	@Override
	public Query<T> cross(final Class<? extends Table> t) {
		throw new UnsupportedOperationException("joins on "+ this.getClass().getSimpleName() +" are not supported");
	}

	@Override
	public <S extends Table> Query<Join<T, S>> crossJoin(final Class<S> table) {
		return new LocalJoin(Constants.JOIN_TYPE.CROSS, Join.class, this, table, null);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> crossJoin(final __Alias<S> table) {
		return new LocalJoin(Constants.JOIN_TYPE.CROSS, Join.class, this, table.table, null);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> crossJoin(final Query<S> other) {
		return new LocalJoin(Constants.JOIN_TYPE.CROSS, Join.class, this, other, null);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> leftJoin(final Class<S> table, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.LEFT, Join.class, this, table, on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> leftJoin(final __Alias<S> table, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.LEFT, Join.class, this, table.table, on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> leftJoin(final Query<S> other, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.LEFT, Join.class, this, other, on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> rightJoin(final Class<S> table, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.RIGHT, Join.class, this, table, on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> rightJoin(final __Alias<S> table, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.RIGHT, Join.class, this, table.table, on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> rightJoin(final Query<S> other, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.RIGHT, Join.class, this, other, on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> outerJoin(final Class<S> table, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.OUTER, Join.class, this, table, on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> outerJoin(final __Alias<S> table, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.OUTER, Join.class, this, table.table, on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> outerJoin(final Query<S> other, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.OUTER, Join.class, this, other, on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> innerJoin(final Class<S> table, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.INNER, Join.class, this, table, on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> innerJoin(final __Alias<S> table, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.INNER, Join.class, this, table.table, on);
	}

	@Override
	public <S extends Table> Query<Join<T, S>> innerJoin(final Query<S> other, final Condition on) {
		return new LocalJoin(Constants.JOIN_TYPE.INNER, Join.class, this, other, on);
	}

	@Override
	public Query<T> exclude(final Condition... conditions) {
	    final Condition[] where = new Condition[conditions.length];
	    for (int i=0; i<conditions.length; ++i) where[i] = conditions[i].not();
	    return this.where(where);
	}

	@Override
	public <S> Iterable<S> asIterableOf(final Field<S> field) {
		return new SelectSingleColumn<S>(this, field);
	}

	@Override
	public Condition exists() {
		return new Condition.Exists(this);
	}

	@Override
	public <S extends Comparable> S max(final Field<S> f) throws SQLException {
		S max = null;
		for (final S s : this.asIterableOf(f)) {
			if (max==null || max.compareTo(s)<0) {
				max = s;
			}
		}
		return max;
	}

	@Override
	public <R, S extends Comparable> Map<R, S> maxBy(final Field<S> maxField, final Field<R> byField)
			throws SQLException {
		final Map<R,S> maxes = new HashMap<R,S>();
		for (final T t : this) {
			final R r = t.get(byField);
			final S max = maxes.get(r);
			final S s = t.get(maxField);
			if (max==null || max.compareTo(s)<0) {
				maxes.put(r, s);
			}
		}
		return maxes;
	}

	@Override
	public <S extends Comparable> S min(final Field<S> f) throws SQLException {
		S min = null;
		for (final S s : this.asIterableOf(f)) {
			if (min==null || min.compareTo(s)>0) {
				min = s;
			}
		}
		return min;
	}

	@Override
	public <R, S extends Comparable> Map<R, S> minBy(final Field<S> minField, final Field<R> byField)
			throws SQLException {
		final Map<R,S> mins = new HashMap<R,S>();
		for (final T t : this) {
			final R r = t.get(byField);
			final S min = mins.get(r);
			final S s = t.get(minField);
			if (min==null || min.compareTo(s)>0) {
				mins.put(r, s);
			}
		}
		return mins;
	}

	@Override
	public Iterable<T> snapshot() {
		return new QuerySnapshot<T>(this);
	}

	@Override
	public Iterable<T> snapshot(final File f) {
		return new QuerySnapshot<T>(this, f);
	}

	@Override
	public Query<T> select(final Field<?>... fields) {
		return onlyFields(fields);
	}

	@Override
	public Query<T> select(final Collection<Field<?>> fields) {
		return onlyFields(fields);
	}

	@Override
	public Query<T> alsoSelect(final Field<?>... fields) {
		throw new UnsupportedOperationException("not supported (yet) on "+ this.getClass().getName());
	}

	@Override
	public Query<T> alsoSelect(final Collection<Field<?>> fields) {
		throw new UnsupportedOperationException("not supported (yet) on "+ this.getClass().getName());
	}

//	@Override
//	public <S extends Table> Query<T> alsoSelect(Query<S> q) {
//		throw new UnsupportedOperationException("not supported (yet) on "+ this.getClass().getName());
//	}

	@Override
	public <S> Field<S> asInnerQueryOf(final Field<S> field) {
		throw new UnsupportedOperationException("not supported (yet) on "+ this.getClass().getName());
	}

	@Override
	public String explainAsText() throws SQLException {
		return this.getClass().getSimpleName() + " does not implement explainAsText().";
	}

	@Override
	public Query<T> union(Query<T> other) {
		throw new UnsupportedOperationException(" does not implement union().");
	}

	@Override
	public Query<T> unionAll(Query<T> other) {
		throw new UnsupportedOperationException(" does not implement union().");
	}

}
