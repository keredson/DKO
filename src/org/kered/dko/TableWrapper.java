package org.kered.dko;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.kered.dko.Field.FK;

public class TableWrapper extends Table {

	private final Field<?> field;
	private Object value = null;
	private final Table t;

	public TableWrapper(final Table t, final Field<?> field, final Object value) {
		this.t = t;
		this.field = field;
		this.value = value;
	}

	@Override
	protected FK[] FKS() {
		return t.FKS();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S> S get(final Field<S> field) {
		if (this.field.sameField(field)) return (S) value;
		return t.get(field);
	}

	@Override
	public List<Field<?>> fields() {
		final List<Field<?>> ret = new ArrayList<Field<?>>(t.fields());
		ret.add(field);
		return Collections.unmodifiableList(ret);
	}

	@Override
	public <S> void set(final Field<S> field, final S value) {
		if (this.field.sameField(field)) this.value = value;
		else t.set(field, value);
	}

	@Override
	public boolean insert() throws SQLException {
		return t.insert();
	}

	@Override
	public boolean update() throws SQLException {
		return t.update();
	}

	@Override
	public boolean delete() throws SQLException {
		return t.delete();
	}

	@Override
	public boolean save() throws SQLException {
		return t.save();
	}

	@Override
	public boolean exists() throws SQLException {
		return t.exists();
	}

	@Override
	public boolean insert(final DataSource ds) throws SQLException {
		return t.insert(ds);
	}

	@Override
	public boolean update(final DataSource ds) throws SQLException {
		return t.update(ds);
	}

	@Override
	public boolean delete(final DataSource ds) throws SQLException {
		return t.delete(ds);
	}

	@Override
	public boolean save(final DataSource ds) throws SQLException {
		return t.save(ds);
	}

	@Override
	public boolean exists(final DataSource ds) throws SQLException {
		return t.exists(ds);
	}

	@Override
	protected Object __NOSCO_PRIVATE_mapType(final Object o) {
		return t.__NOSCO_PRIVATE_mapType(o);
	}

	@Override
	public String toStringDetailed() {
		return t.toStringDetailed()+"+["+value+"]";
	}

	@Override
	public String toStringSimple() {
		return t.toStringSimple()+"+["+value+"]";
	}

}
