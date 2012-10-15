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
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> cross(final Query<T1> q, Class<T2> t) {
		return new Query2<T1, T2>(q, t);
	}
	private static class Query2<T1 extends Table, T2 extends Table> extends DBQuery<J2<T1, T2>> {
		final int SIZE = 2;
		public Query2(final Query<T1> q, final Class<T2> t) {
			super(J2.class, q, t, "cross join");
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
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME());
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
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds));
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
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> cross(final Query<J2<T1, T2>> q, Class<T3> t) {
		return new Query3<T1, T2, T3>(q, t);
	}
	private static class Query3<T1 extends Table, T2 extends Table, T3 extends Table> extends DBQuery<J3<T1, T2, T3>> {
		final int SIZE = 3;
		Query3(final Query<J2<T1, T2>> q, final Class<T3> t) {
			super(J3.class, q, t, "cross join");
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
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME());
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
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds));
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

	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> cross(final Query<J3<T1, T2, T3>> q, Class<T4> t) {
		return new Query4<T1, T2, T3, T4>(q, t);
	}
	private static class Query4<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> extends DBQuery<J4<T1, T2, T3, T4>> {
		final int SIZE = 4;
		Query4(final Query<J3<T1, T2, T3>> q, final Class<T4> t) {
			super(J4.class, q, t, "cross join");
		}
	}

	public static class J4 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public J4(final T1 t1, final T2 t2, final T3 t3, final T4 t4) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
		}
		@SuppressWarnings("unchecked")
		J4(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
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
			try { return t4.get(field); }
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
			try { t4.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed());
		}
	}

}
