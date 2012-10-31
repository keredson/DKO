package org.kered.dko;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.DIRECTION;
import org.kered.dko.Field.FK;
import org.kered.dko.Table.__Alias;

class FilteringQuery<T extends Table> extends AbstractQuery<T> implements MatryoshkaQuery<T> {

	private final List<Query<? extends Table>> q = new ArrayList<Query<? extends Table>>();

	FilteringQuery(final Query<T> q, final Condition... conditions) {
		super(q);
		this.q.add(q);
	}

	@Override
	public Query<T> where(final Condition... conditions) {
		return new FilteringQuery<T>(this, conditions);
	}

	@Override
	public Query<T> exclude(final Condition... conditions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> orderBy(final Field<?>... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> limit(final long n) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> distinct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> max() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> min() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> with(final FK... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> deferFields(final Field<?>... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> deferFields(final Collection<Field<?>> fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> onlyFields(final Collection<Field<?>> fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> onlyFields(final Field<?>... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<T> all() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> orderBy(final DIRECTION direction, final Field<?>... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> set(final Field<?> key, final Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> set(final Map<Field<?>, Object> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object insert() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public <R, S extends Number> Map<R, S> sumBy(final Field<S> sumField, final Field<R> byField)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public <S extends Number> S sum(final Field<S> f) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S> Map<S, Integer> countBy(final Field<S> byField) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> use(final DataSource ds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> use(final Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> use(final DB_TYPE type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> cross(final __Alias<? extends Table> t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> cross(final Class<? extends Table> t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> in(final Collection<T> set) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S> Iterable<S> asIterableOf(final Field<S> field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> toMemory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Condition exists() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataSource getDataSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Field<?>> getSelectFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Query<? extends Table>> getUnderlying() {
		return q;
	}

	@Override
	public <S extends Table> Query<T> crossJoin(final Class<S> table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<T> crossJoin(final __Alias<S> table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<T> leftJoin(final Class<S> table, final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<T> leftJoin(final __Alias<S> table, final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<T> rightJoin(final Class<S> table, final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<T> rightJoin(final __Alias<S> table, final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<T> outerJoin(final Class<S> table, final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<T> outerJoin(final __Alias<S> table, final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<T> innerJoin(final Class<S> table, final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<T> innerJoin(final __Alias<S> table, final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

}
