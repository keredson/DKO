package org.nosco;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.nosco.Constants.DB_TYPE;
import org.nosco.Constants.DIRECTION;
import org.nosco.Field.FK;
import org.nosco.Table.__Alias;
import org.nosco.Table.__PrimaryKey;

class FilteringQuery<T extends Table> extends AbstractQuery<T> implements MatryoshkaQuery<T> {

	private final Query<T> q;

	FilteringQuery(final Query<T> q, final Condition... conditions) {
		super(q);
		this.q = q;
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

	@Override
	public <S> Map<S, Double> sumBy(final Field<? extends Number> sumField,
			final Field<S> byField) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double sum(final Field<? extends Number> f) throws SQLException {
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
	public <S> Iterable<S> select(final Field<S> field) {
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
	public <S extends Table> Query<Join<T, S>> crossJoin(final Class<S> table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<Join<T, S>> crossJoin(final __Alias<S> table) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<Join<T, S>> leftJoin(final Class<S> table,
			final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<Join<T, S>> leftJoin(final __Alias<S> table,
			final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<Join<T, S>> rightJoin(final Class<S> table,
			final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<Join<T, S>> rightJoin(final __Alias<S> table,
			final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<Join<T, S>> outerJoin(final Class<S> table,
			final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<Join<T, S>> outerJoin(final __Alias<S> table,
			final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<Join<T, S>> innerJoin(final Class<S> table,
			final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Table> Query<Join<T, S>> innerJoin(final __Alias<S> table,
			final Condition on) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<T> getUnderlying() {
		return q;
	}

}
