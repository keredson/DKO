package org.kered.dko;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.kered.dko.Field.FK;

/**
 * This class represents a join across 2 tables. It contains 2 typed references
 * ('l' and 'r') to the join row components. (each of them containing all the
 * columns they contributed to the join)
 */
public class Join<L extends Table, R extends Table> extends Table {

	List<Class<?>> types = null;
	Collection<Field<?>> fields = null;

	public final L l;
	public final R r;

	public Join(final L l, final R r) {
		this.l = l;
		this.r = r;
	}

	Join(final Object[] oa) {
		this(oa, oa.length);
	}

	@SuppressWarnings("unchecked")
	Join(final Object[] oa, final int end) {
		if (end==2) l = (L) oa[0];
		else l = (L) new Join(oa, end-1);
		r = (R) oa[end-1];
	}

	@SuppressWarnings("unchecked")
	Join(final Object[] oa, final int offset, Collection<Field<?>> fields) {
		l = (L) oa[offset + 0];
		r = (R) oa[offset + 1];
		this.fields = fields;
	}

	@Override
	protected String SCHEMA_NAME() {
		return (l == null ? null : l.SCHEMA_NAME()) + " + " + (r == null ? null : r.SCHEMA_NAME());
	}

	@Override
	protected String TABLE_NAME() {
		return (l == null ? null : l.TABLE_NAME()) + " + " + (r == null ? null : r.TABLE_NAME());
	}

	@Override
	public List<Field<?>> fields() {
		List<Field<?>> fields = new ArrayList<Field<?>>();
		if (l != null)
			fields.addAll(l.fields());
		if (r != null)
			fields.addAll(r.fields());
		fields = Collections.unmodifiableList(fields);
		return fields;
	}

	private static final FK[] fks = {};
	@SuppressWarnings("rawtypes")
	@Override
	protected FK[] FKS() {
		return fks;
	}

	@Override
	public <S> S get(final Field<S> field) {
		if (l != null) {
			S o = l.get(field);
			if (o!=null) return o;
		}
		if (r != null) {
			S o = r.get(field);
			if (o!=null) return o;
		}
		if (fields!=null && fields.contains(field)) return null;
		//throw new IllegalArgumentException("unknown field " + field);
		return null;
	}

	@Override
	public <S> Table set(final Field<S> field, final S value) {
		try {
			l.set(field, value);
			return this;
		} catch (final IllegalArgumentException e) { /* ignore */ }
		try {
			r.set(field, value);
			return this;
		} catch (final IllegalArgumentException e) { /* ignore */ }
		if (fields != null && fields.contains(field))
			throw new RuntimeException("you can't set this field because the joined object is null (some join types can return nulls from the database): " + field);
		;
		throw new IllegalArgumentException("unknown field " + field);
	}

	@Override
	public boolean insert() throws SQLException {
		return (l != null && l.insert()) && (r != null && r.insert());
	}

	@Override
	public boolean insert(DataSource ds) throws SQLException {
		return (l != null && l.insert(ds)) && (r != null && r.insert(ds));
	}

	@Override
	public boolean update() throws SQLException {
		return (l != null && l.update()) && (r != null && r.update());
	}

	@Override
	public boolean update(DataSource ds) throws SQLException {
		return (l != null && l.update(ds)) && (r != null && r.update(ds));
	}

	@Override
	public boolean delete() throws SQLException {
		return (l != null && l.delete()) && (r != null && r.delete());
	}

	@Override
	public boolean delete(DataSource ds) throws SQLException {
		return (l != null && l.delete(ds)) && (r != null && r.delete(ds));
	}

	@Override
	public boolean save() throws SQLException {
		return (l != null && l.save()) && (r != null && r.save());
	}

	@Override
	public boolean save(DataSource ds) throws SQLException {
		return (l != null && l.save(ds)) && (r != null && r.save(ds));
	}

	@Override
	public boolean exists() throws SQLException {
		return (l != null && l.exists()) || (r != null && r.exists());
	}

	@Override
	public boolean exists(DataSource ds) throws SQLException {
		return (l != null && l.exists(ds)) || (r != null && r.exists(ds));
	}

	@Override
	protected Object __NOSCO_PRIVATE_mapType(final Object o) {
		return l.__NOSCO_PRIVATE_mapType(o);
	}

	@Override
	public String toString() {
		return l + "+" + r;
	}

	@Override
	public String toStringDetailed() {
		return (l == null ? l : l.toStringDetailed()) + "+"
				+ (r == null ? r : r.toStringDetailed());
	}

}
