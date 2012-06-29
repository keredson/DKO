package org.nosco;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.nosco.Constants.DB_TYPE;
import org.nosco.Constants.DIRECTION;
import org.nosco.Field.FK;
import org.nosco.Table.__Alias;
import org.nosco.Table.__PrimaryKey;

class InMemoryQuery<T extends Table> implements Query<T> {

	List<T> cache = null;
	private Field<?>[] selectFields;
	private Query<T> query;
	private boolean loaded = false;

	InMemoryQuery(Query<T> query) {
		this(query, false);
	}

	InMemoryQuery(Query<T> query, boolean lazy) {
		this.query = query;
		if (!lazy) load();
	}

	private synchronized void load() {
		cache = new ArrayList<T>();
		this.selectFields = query.getSelectFields();
		for (T t : query) {
			cache.add(t);
		}
		loaded = true;
	}

	private InMemoryQuery(InMemoryQuery<T> q) {
		cache = new ArrayList<T>();
		loaded = true;
	}

	InMemoryQuery() {
		cache = new ArrayList<T>();
		loaded = true;
	}

	@Override
	public Iterator<T> iterator() {
		if (!loaded) load();
		return Collections.unmodifiableList(cache).iterator();
	}

	@Override
	public Query<T> where(Condition... conditions) {
		if (!loaded) load();
		InMemoryQuery<T> q = new InMemoryQuery<T>(this);
		for (T t : cache) {
			boolean include = true;
			for (Condition c : conditions) {
				include &= c.matches(t);
			}
			if (include) q.cache.add(t);
		}
		return q;
	}

	@Override
	public T get(Condition... conditions) {
		return where(conditions).getTheOnly();
	}

	@Override
	public int count() throws SQLException {
		if (!loaded) load();
		return cache.size();
	}

	@Override
	public int size() throws SQLException {
		if (!loaded) load();
		return cache.size();
	}

	@Override
	public Query<T> exclude(Condition... conditions) {
		return null;
	}

	@Override
	public Query<T> orderBy(final Field<?>... fields) {
		if (!loaded) load();
		InMemoryQuery<T> q = new InMemoryQuery<T>(this);
		q.cache.addAll(cache);
		Collections.sort(q.cache, new Comparator<T>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public int compare(T o1, T o2) {
				for (Field<?> f : fields) {
					Comparable c1 = (Comparable) o1.get(f);
					Object c2 = o2.get(f);
					if (c1 == null && c2 != null) return 1;
					int c = c1.compareTo(c2);
					if (c != 0) return c;
				}
				return 0;
			}});
		return q;
	}

	@Override
	public Query<T> top(int n) {
		return limit(n);
	}

	@Override
	public Query<T> limit(int n) {
		if (!loaded) load();
		InMemoryQuery<T> q = new InMemoryQuery<T>(this);
		q.cache = new ArrayList<T>();
		q.cache.addAll(cache.subList(0, n));
		return q;
	}

	@Override
	public Query<T> distinct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> with(FK... fields) {
		throw new UnsupportedOperationException("can't pre-pull FKs from in-memory queries");
	}

	@Override
	public Query<T> deferFields(Field<?>... fields) {
		// do nothing
		return this;
	}

	@Override
	public Query<T> onlyFields(Field<?>... fields) {
		// do nothing
		return this;
	}

	@Override
	public T latest(Field<?> field) {
		if (!loaded) load();
		if (cache == null || cache.size() == 0) return null;
		return cache.get(cache.size() - 1);
	}

	@Override
	public T first() {
		if (!loaded) load();
		if (cache == null || cache.size() == 0) return null;
		return cache.get(0);
	}

	@Override
	public boolean isEmpty() throws SQLException {
		if (!loaded) load();
		return cache.isEmpty();
	}

	@Override
	public int update() throws SQLException {
		throw new UnsupportedOperationException("can't update on an in-memory query");
	}

	@Override
	public int deleteAll() throws SQLException {
		throw new UnsupportedOperationException("can't delete on an in-memory query");
	}

	@Override
	public Statistics stats(Field<?>... field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<T> all() {
		if (!loaded) load();
		return Collections.unmodifiableList(cache);
	}

	@Override
	public Iterable<T> none() {
		return Collections.emptyList();
	}

	@Override
	public Query<T> orderBy(DIRECTION direction, Field<?>... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> set(Field<?> key, Object value) {
		throw new UnsupportedOperationException("can't do this on an in-memory query");
	}

	@Override
	public Query<T> set(Map<Field<?>, Object> values) {
		throw new UnsupportedOperationException("can't do this on an in-memory query");
	}

	@Override
	public Object insert() throws SQLException {
		throw new UnsupportedOperationException("can't insert on an in-memory query");
	}

	@Override
	public T getTheOnly() {
		if (!loaded) load();
		if (cache.size() == 0) return null;
		if (cache.size() > 1) throw new RuntimeException("more than one result found in Query.getTheOnly()");
		return cache.get(0);
	}

	@Override
	public List<T> asList() {
		if (!loaded) load();
		return Collections.unmodifiableList(cache);
	}

	@Override
	public Set<T> asSet() {
		if (!loaded) load();
		return new HashSet<T>(cache);
	}

	@Override
	public <S> Map<S, Double> sumBy(Field<? extends Number> sumField,
			Field<S> byField) throws SQLException {
		if (!loaded) load();
		Map<S, Double> ret = new HashMap<S, Double>();
		for (T t : cache) {
			S key = t.get(byField);
			Double value = ret.get(key);
			if (value == null) value = 0.0;
			value += t.get(sumField).doubleValue();
			ret.put(key, value);
		}
		return ret;
	}

	@Override
	public Double sum(Field<? extends Number> f) throws SQLException {
		if (!loaded) load();
		double sum = 0;
		for (T t : cache) {
			sum += t.get(f).doubleValue();
		}
		return sum;
	}

	@Override
	public <S> Map<S, T> mapBy(Field<S> byField) throws SQLException {
		if (!loaded) load();
		Map<S, T> ret = new HashMap<S, T>();
		for (T t : cache) {
			ret.put(t.get(byField), t);
		}
		return ret;
	}

	@Override
	public <S> Map<S, Integer> countBy(Field<S> byField) throws SQLException {
		if (!loaded) load();
		Map<S, Integer> ret = new HashMap<S, Integer>();
		for (T t : cache) {
			S key = t.get(byField);
			Integer value = ret.get(key);
			if (value == null) value = 0;
			value += 1;
			ret.put(key, value);
		}
		return ret;
	}

	@Override
	public Query<T> use(DataSource ds) {
		throw new UnsupportedOperationException("can't specify a DataSource " +
				"for an in-memory query");
	}

	@Override
	public Query<T> cross(Table t) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public Query<T> cross(__Alias<? extends Table> t) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public Query<T> cross(Class<? extends Table> t) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public Query<T> toMemory() {
		return this;
	}

	@Override
	public Query<T> max() {
		if (!loaded) load();
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> min() {
		if (!loaded) load();
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Condition exists() {
		if (!loaded) load();
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSource getDataSource() {
		return null;
	}

	@Override
	public <S> Iterable<S> select(Field<S> field) {
		if (!loaded) load();
		List<S> ret = new ArrayList<S>();
		for (T t : cache) ret.add(t.get(field));
		return ret;
	}

	@Override
	public <S> List<S> asList(Field<S> field) {
		List<S> ret = new ArrayList<S>();
		for (S s : select(field)) {
			ret.add(s);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(__PrimaryKey<T> pk) {
		if (!loaded) load();
		if (cache==null || cache.size() == 0) return null;
		return get(Util.getPK(cache.get(0)).eq(pk));
	}

	@Override
	public Query<T> use(Connection conn) {
		// do nothing
		return this;
	}

	@Override
	public Field<?>[] getSelectFields() {
		if (!loaded) load();
		Field<?>[] ret = new Field<?>[selectFields.length];
		System.arraycopy(selectFields, 0, ret, 0, selectFields.length);
		return ret;
	}

	@Override
	public Iterable<Object[]> asIterableOfObjectArrays() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Query<T> use(DB_TYPE type) {
		// ignore
		return this;
	}

}
