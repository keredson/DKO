package org.nosco;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.nosco.Constants.DB_TYPE;
import org.nosco.Constants.DIRECTION;
import org.nosco.Field.FK;
import org.nosco.Table.__Alias;
import org.nosco.Table.__PrimaryKey;

class InMemoryQuery<T extends Table> extends AbstractQuery<T> {

	List<T> cache = null;
	private List<Field<?>> selectFields;
	private Query<T> query;
	private boolean loaded = false;

	InMemoryQuery(final Query<T> query) {
		this(query, false);
	}

	InMemoryQuery(final Query<T> query, final boolean lazy) {
		this.query = query;
		if (!lazy) load();
	}

	private synchronized void load() {
		if (loaded) return;
		cache = new ArrayList<T>();
		this.selectFields = query.getSelectFields();
		for (final T t : query) {
			cache.add(t);
		}
		loaded = true;
	}

	private InMemoryQuery(final InMemoryQuery<T> q) {
		cache = new ArrayList<T>();
		loaded = true;
	}

	InMemoryQuery() {
		cache = new ArrayList<T>();
		loaded = true;
	}

	InMemoryQuery(final Iterable<T> items) {
		cache = new ArrayList<T>();
		for (final T t : items) {
			cache.add(t);
		}
		loaded = true;
	}

	@Override
	public Iterator<T> iterator() {
		if (!loaded) load();
		return Collections.unmodifiableList(cache).iterator();
	}

	@Override
	public Query<T> where(final Condition... conditions) {
		if (!loaded) load();
		final InMemoryQuery<T> q = new InMemoryQuery<T>(this);
		for (final T t : cache) {
			boolean include = true;
			for (final Condition c : conditions) {
				include &= c.matches(t);
			}
			if (include) q.cache.add(t);
		}
		return q;
	}

	@Override
	public long count() throws SQLException {
		if (!loaded) load();
		return cache.size();
	}

	@Override
	public Query<T> exclude(final Condition... conditions) {
		return null;
	}

	@Override
	public Query<T> orderBy(final Field<?>... fields) {
		if (!loaded) load();
		final InMemoryQuery<T> q = new InMemoryQuery<T>(this);
		q.cache.addAll(cache);
		Collections.sort(q.cache, new Comparator<T>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public int compare(final T o1, final T o2) {
				for (final Field<?> f : fields) {
					final Comparable c1 = (Comparable) o1.get(f);
					final Object c2 = o2.get(f);
					if (c1 == null && c2 != null) return 1;
					final int c = c1.compareTo(c2);
					if (c != 0) return c;
				}
				return 0;
			}});
		return q;
	}

	@Override
	public Query<T> limit(final long n) {
		if (!loaded) load();
		final InMemoryQuery<T> q = new InMemoryQuery<T>(this);
		q.cache = new ArrayList<T>();
		q.cache.addAll(cache.subList(0, Math.min((int)n, cache.size())));
		return q;
	}

	@Override
	public Query<T> distinct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> with(final FK... fields) {
		throw new UnsupportedOperationException("can't pre-pull FKs from in-memory queries");
	}

	@Override
	public Query<T> deferFields(final Field<?>... fields) {
		// do nothing
		return this;
	}

	@Override
	public Query<T> onlyFields(final Field<?>... fields) {
		// do nothing
		return this;
	}

	@Override
	public Query<T> deferFields(final Collection<Field<?>> fields) {
		// do nothing
		return this;
	}

	@Override
	public Query<T> onlyFields(final Collection<Field<?>> fields) {
		// do nothing
		return this;
	}

	@Override
	public int update() throws SQLException {
		throw new UnsupportedOperationException("can't update on an in-memory query");
	}

	@Override
	public int delete() throws SQLException {
		throw new UnsupportedOperationException("can't delete on an in-memory query");
	}

	@Override
	public Iterable<T> all() {
		if (!loaded) load();
		return Collections.unmodifiableList(cache);
	}

	@Override
	public Query<T> orderBy(final DIRECTION direction, final Field<?>... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> set(final Field<?> key, final Object value) {
		throw new UnsupportedOperationException("can't do this on an in-memory query");
	}

	@Override
	public Query<T> set(final Map<Field<?>, Object> values) {
		throw new UnsupportedOperationException("can't do this on an in-memory query");
	}

	@Override
	public Object insert() throws SQLException {
		throw new UnsupportedOperationException("can't insert on an in-memory query");
	}

	@Override
	public <S> Map<S, Double> sumBy(final Field<? extends Number> sumField,
			final Field<S> byField) throws SQLException {
		final Map<S, Double> ret = new HashMap<S, Double>();
		for (final T t : this) {
			final S key = t.get(byField);
			Double value = ret.get(key);
			if (value == null) value = 0.0;
			value += t.get(sumField).doubleValue();
			ret.put(key, value);
		}
		return ret;
	}

	@Override
	public Double sum(final Field<? extends Number> f) throws SQLException {
		double sum = 0;
		for (final T t : this) {
			sum += t.get(f).doubleValue();
		}
		return sum;
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
	public Query<T> use(final DataSource ds) {
		throw new UnsupportedOperationException("can't specify a DataSource " +
				"for an in-memory query");
	}

	@Override
	public Query<T> cross(final __Alias<? extends Table> t) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public Query<T> cross(final Class<? extends Table> t) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public Query<T> toMemory() {
		return this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Query<T> max() {
		throw new UnsupportedOperationException("max not supported yet on in-memory queries");
	}

	@Override
	public Query<T> min() {
		throw new UnsupportedOperationException("min not supported yet on in-memory queries");
	}

	@Override
	public Condition exists() {
		if (!loaded) load();
		if (cache.isEmpty()) return Condition.Literal.FALSE;
		else return Condition.Literal.TRUE;
	}

	@Override
	public DataSource getDataSource() {
		return null;
	}

	@Override
	public <S> Iterable<S> select(final Field<S> field) {
		final List<S> ret = new ArrayList<S>();
		for (final T t : this) ret.add(t.get(field));
		return ret;
	}

	@Override
	public <S> List<S> asList(final Field<S> field) {
		final List<S> ret = new ArrayList<S>();
		for (final S s : select(field)) {
			ret.add(s);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(final __PrimaryKey<T> pk) {
		if (!loaded) load();
		if (cache==null || cache.size() == 0) return null;
		return get(Util.getPK(cache.get(0)).eq(pk));
	}

	@Override
	public Query<T> use(final Connection conn) {
		// do nothing
		return this;
	}

	@Override
	public List<Field<?>> getSelectFields() {
		if (!loaded) load();
		return selectFields;
	}

	@Override
	public Query<T> use(final DB_TYPE type) {
		// ignore
		return this;
	}

	@Override
	public <S extends Table> Query<Join<T, S>> crossJoin(final Class<S> other) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public <S extends Table> Query<Join<T, S>> crossJoin(final __Alias<S> other) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public <S extends Table> Query<Join<T, S>> leftJoin(final Class<S> other,
			final Condition condition) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public <S extends Table> Query<Join<T, S>> leftJoin(final __Alias<S> other,
			final Condition condition) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public <S extends Table> Query<Join<T, S>> rightJoin(final Class<S> other,
			final Condition condition) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public <S extends Table> Query<Join<T, S>> rightJoin(final __Alias<S> other,
			final Condition condition) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public <S extends Table> Query<Join<T, S>> outerJoin(final Class<S> other,
			final Condition condition) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public <S extends Table> Query<Join<T, S>> outerJoin(final __Alias<S> other,
			final Condition condition) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public <S extends Table> Query<Join<T, S>> innerJoin(final Class<S> other,
			final Condition condition) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public <S extends Table> Query<Join<T, S>> innerJoin(final __Alias<S> other,
			final Condition condition) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

	@Override
	public Query<T> in(final Collection<T> set) {
		throw new UnsupportedOperationException("can't join on an in-memory query");
	}

}
