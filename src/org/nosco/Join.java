package org.nosco;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.nosco.DBQuery.JoinInfo;
import org.nosco.Field.FK;

public class Join<L extends Table, R extends Table> extends Table {

	public final L l;
	public final R r;
	private List<Field<?>> __NOSCO_PRIVATE_FIELDS;

	Join(final L l, final R r) {
		this.l = l;
		this.r = r;
	}

	@Override
	protected String SCHEMA_NAME() {
		return l.SCHEMA_NAME() +" + "+ r.SCHEMA_NAME();
	}

	@Override
	protected String TABLE_NAME() {
		return l.TABLE_NAME() +" + "+ r.TABLE_NAME();
	}

	@Override
	public List<Field<?>> FIELDS() {
		if (__NOSCO_PRIVATE_FIELDS == null) {
			__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
			__NOSCO_PRIVATE_FIELDS.addAll(l.FIELDS());
			__NOSCO_PRIVATE_FIELDS.addAll(r.FIELDS());
		}
		return __NOSCO_PRIVATE_FIELDS;
	}

	@Override
	protected FK[] FKS() {
		final FK[] ret = {};
		return ret;
	}

	@Override
	public <S> S get(final Field<S> field) {
		try {
			return l.get(field);
		} catch (final IllegalArgumentException e) {
			return r.get(field);
		}
	}

	@Override
	public <S> void set(final Field<S> field, final S value) {
		try {
			l.set(field, value);
		} catch (final IllegalArgumentException e) {
			r.set(field, value);
		}
	}

	@Override
	public boolean insert() throws SQLException {
		return l.insert() && r.insert();
	}

	@Override
	public boolean update() throws SQLException {
		return l.update() && r.update();
	}

	@Override
	public boolean delete() throws SQLException {
		return l.delete() && r.delete();
	}

	@Override
	public boolean save() throws SQLException {
		return l.save() && r.save();
	}

	@Override
	public boolean exists() throws SQLException {
		return l.exists() || r.exists();
	}

	@Override
	public boolean insert(final DataSource ds) throws SQLException {
		return l.insert(ds) && r.insert(ds);
	}

	@Override
	public boolean update(final DataSource ds) throws SQLException {
		return l.update(ds) && r.update(ds);
	}

	@Override
	public boolean delete(final DataSource ds) throws SQLException {
		return l.delete(ds) && r.delete(ds);
	}

	@Override
	public boolean save(final DataSource ds) throws SQLException {
		return l.save(ds) && r.save(ds);
	}

	@Override
	public boolean exists(final DataSource ds) throws SQLException {
		return l.exists(ds) && r.exists(ds);
	}

	@Override
	protected Object __NOSCO_PRIVATE_mapType(final Object o) {
		Object ret = l.__NOSCO_PRIVATE_mapType(o);
		if (ret == null) ret = r.__NOSCO_PRIVATE_mapType(o);
		return ret;
	}

	@Override
	public String toString() {
		return l +"+"+ r;
	}

	@Override
	public String toStringDetailed() {
		return (l==null ? l : l.toStringDetailed()) +"+"+ (r==null ? r : r.toStringDetailed());
	}

}
