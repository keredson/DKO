package org.nosco;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.nosco.DBQuery.JoinInfo;
import org.nosco.Field.FK;

/**
 * Represents multiple tables joined together.  This object can be used as one table (with all the fields of both)
 * children.  Or the children can be accessed directly (with the typed left/right member variables).
 *
 * @author dander
 *
 * @param <L>
 * @param <R>
 */
public class Join<L extends Table, R extends Table> extends Table {

	/**
	 * The left-hand side of this join.
	 */
	public final L l;

	/**
	 * The right-hand side of this join.
	 */
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

	/**
	 * Let's assume you have types A,B,C (all extend Table).  The code
	 * {@code Query<Join<A,B> q1 = A.ALL.leftJoin(B.class);} is easy to use with the left/right syntax.  For example:
	 * {@code for (Join<A,B> j : q1) {
	 *     A a = j.l;
	 *     B b = j.r;
	 * }}
	 * But if you then join with C:
	 * {@code Query<Join<Join<A,B>,C> q2 = q1.leftJoin(C.class);
	 * for (Join<Join<A,B>,C> j : q2) {
	 *     A a = j.l.l;
	 *     B b = j.l.r;
	 *     C c = j.r;
	 * }}
	 * That's less nice.  And it gets worse the more tables you join.
	 * <p>
	 * So for ease of use, there is this method.
	 * {@code for (Join<Join<A,B>,C> j : q2) {
	 *     A a = j.get(A.class);
	 *     B b = j.get(B.class);
	 *     C c = j.get(C.class);
	 * }}
	 * Which returns you the first object in a left-depth-first search of the join tree.
	 * (or more succinctly: the first object in the join of that type)
	 *
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <S extends Table> S get(final Class<S> type) {
		if (type.isInstance(l)) return (S) l;
		if (l instanceof Join) {
			@SuppressWarnings("rawtypes")
			final
			S ret = (S) ((Join)l).get(type);
			if (ret != null) return ret;
		}
		if (type.isInstance(r)) return (S) r;
		if (r instanceof Join) {
			@SuppressWarnings("rawtypes")
			final
			S ret = (S) ((Join)r).get(type);
			if (ret != null) return ret;
		}
		return null;
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
