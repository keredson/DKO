package org.nosco;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.nosco.Field.FK;

public class Join {
	static abstract class J extends Table {

		List<Class<?>> types = null;
	}

	/**
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> crossJoin(final Query<T1> q, final Class<T2> t) {
		return new Query2<T1, T2>(2, q, t);
	}
	private static class Query2<T1 extends Table, T2 extends Table> extends DBQuery<J2<T1, T2>> {
		public Query2(final int size, final Query<T1> q, final Class<T2> t) {
			super(q, t, "cross join");
		}
	}

	public static class J2 <T1 extends Table, T2 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public J2(final T1 t1, final T2 t2) {
			this.t1 = t1;
			this.t2 = t2;
		}
		@SuppressWarnings("unchecked")
		J2(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
		}
		@Override
		protected String SCHEMA_NAME() {
			return t1.SCHEMA_NAME()+" + "+t2.SCHEMA_NAME();
		}
		@Override
		protected String TABLE_NAME() {
			return t1.TABLE_NAME()+" + "+t2.TABLE_NAME();
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS = Collections.unmodifiableList(__NOSCO_PRIVATE_FIELDS);
			}
			return __NOSCO_PRIVATE_FIELDS;
		}
		@SuppressWarnings("rawtypes")
		@Override
		protected FK[] FKS() {
			final FK[] ret = {};
			return ret;
		}
		@Override
		public <S> S get(final Field<S> field) {
			try { return t1.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t2.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public <S> void set(final Field<S> field, final S value) {
			try { t1.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t2.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return t1.insert() && t2.insert();
		}
		@Override
		public boolean insert(final DataSource ds) throws SQLException {
			return t1.insert(ds) && t2.insert(ds);
		}
		@Override
		public boolean update() throws SQLException {
			return t1.update() && t2.update();
		}
		@Override
		public boolean update(final DataSource ds) throws SQLException {
			return t1.update(ds) && t2.update(ds);
		}
		@Override
		public boolean delete() throws SQLException {
			return t1.delete() && t2.delete();
		}
		@Override
		public boolean delete(final DataSource ds) throws SQLException {
			return t1.delete(ds) && t2.delete(ds);
		}
		@Override
		public boolean save() throws SQLException {
			return t1.save() && t2.save();
		}
		@Override
		public boolean save(final DataSource ds) throws SQLException {
			return t1.save(ds) && t2.save(ds);
		}
		@Override
		public boolean exists() throws SQLException {
			return t1.exists() || t2.exists();
		}
		@Override
		public boolean exists(final DataSource ds) throws SQLException {
			return t1.exists(ds) || t2.exists(ds);
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed());
		}
	}

	/**
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> crossJoin(final Query<J2<T1, T2>> q, final Class<T3> t) {
		return new Query3<T1, T2, T3>(3, q, t);
	}
	private static class Query3<T1 extends Table, T2 extends Table, T3 extends Table> extends DBQuery<J3<T1, T2, T3>> {
		Query3(final int size, final Query<J2<T1, T2>> q, final Class<T3> t) {
			super(new J3<T1, T2, T3>(null,null,null));
		}
	}

	public static class J3 <T1 extends Table, T2 extends Table, T3 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public J3(final T1 t1, final T2 t2, final T3 t3) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
		}
		@SuppressWarnings("unchecked")
		J3(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
		}
		@Override
		protected String SCHEMA_NAME() {
			return t1.SCHEMA_NAME()+" + "+t2.SCHEMA_NAME()+" + "+t3.SCHEMA_NAME();
		}
		@Override
		protected String TABLE_NAME() {
			return t1.TABLE_NAME()+" + "+t2.TABLE_NAME()+" + "+t3.TABLE_NAME();
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS = Collections.unmodifiableList(__NOSCO_PRIVATE_FIELDS);
			}
			return __NOSCO_PRIVATE_FIELDS;
		}
		@SuppressWarnings("rawtypes")
		@Override
		protected FK[] FKS() {
			final FK[] ret = {};
			return ret;
		}
		@Override
		public <S> S get(final Field<S> field) {
			try { return t1.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t2.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t3.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public <S> void set(final Field<S> field, final S value) {
			try { t1.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t2.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t3.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return t1.insert() && t2.insert() && t3.insert();
		}
		@Override
		public boolean insert(final DataSource ds) throws SQLException {
			return t1.insert(ds) && t2.insert(ds) && t3.insert(ds);
		}
		@Override
		public boolean update() throws SQLException {
			return t1.update() && t2.update() && t3.update();
		}
		@Override
		public boolean update(final DataSource ds) throws SQLException {
			return t1.update(ds) && t2.update(ds) && t3.update(ds);
		}
		@Override
		public boolean delete() throws SQLException {
			return t1.delete() && t2.delete() && t3.delete();
		}
		@Override
		public boolean delete(final DataSource ds) throws SQLException {
			return t1.delete(ds) && t2.delete(ds) && t3.delete(ds);
		}
		@Override
		public boolean save() throws SQLException {
			return t1.save() && t2.save() && t3.save();
		}
		@Override
		public boolean save(final DataSource ds) throws SQLException {
			return t1.save(ds) && t2.save(ds) && t3.save(ds);
		}
		@Override
		public boolean exists() throws SQLException {
			return t1.exists() || t2.exists() || t3.exists();
		}
		@Override
		public boolean exists(final DataSource ds) throws SQLException {
			return t1.exists(ds) || t2.exists(ds) || t3.exists(ds);
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed());
		}
	}

}
