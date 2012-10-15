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
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> left(final Class<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "left join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> left(final Query<T1> q, Class<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> right(final Class<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "right join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> right(final Query<T1> q, Class<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> inner(final Class<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "inner join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> inner(final Query<T1> q, Class<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> outer(final Class<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "outer join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> outer(final Query<T1> q, Class<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> cross(final Class<T1> t1, Class<T2> t2) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "cross join", null);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> cross(final Query<T1> q, Class<T2> t) {
		return new Query2<T1, T2>(q, t, "cross join", null);
	}

	private static class Query2<T1 extends Table, T2 extends Table> extends DBQuery<J2<T1, T2>> {
		final int SIZE = 2;
		public Query2(final Query<T1> q, final Class<T2> t, String joinType, Condition on) {
			super(J2.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 2 tables.
	 * It contains 2 typed references (t1 to t2) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
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
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> left(final Query<J2<T1, T2>> q, Class<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> right(final Query<J2<T1, T2>> q, Class<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> inner(final Query<J2<T1, T2>> q, Class<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> outer(final Query<J2<T1, T2>> q, Class<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> cross(final Query<J2<T1, T2>> q, Class<T3> t) {
		return new Query3<T1, T2, T3>(q, t, "cross join", null);
	}

	private static class Query3<T1 extends Table, T2 extends Table, T3 extends Table> extends DBQuery<J3<T1, T2, T3>> {
		final int SIZE = 3;
		Query3(final Query<J2<T1, T2>> q, final Class<T3> t, String joinType, Condition on) {
			super(J3.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 3 tables.
	 * It contains 3 typed references (t1 to t3) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
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
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> left(final Query<J3<T1, T2, T3>> q, Class<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> right(final Query<J3<T1, T2, T3>> q, Class<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> inner(final Query<J3<T1, T2, T3>> q, Class<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> outer(final Query<J3<T1, T2, T3>> q, Class<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> cross(final Query<J3<T1, T2, T3>> q, Class<T4> t) {
		return new Query4<T1, T2, T3, T4>(q, t, "cross join", null);
	}

	private static class Query4<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> extends DBQuery<J4<T1, T2, T3, T4>> {
		final int SIZE = 4;
		Query4(final Query<J3<T1, T2, T3>> q, final Class<T4> t, String joinType, Condition on) {
			super(J4.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 4 tables.
	 * It contains 4 typed references (t1 to t4) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
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

	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> left(final Query<J4<T1, T2, T3, T4>> q, Class<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> right(final Query<J4<T1, T2, T3, T4>> q, Class<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> inner(final Query<J4<T1, T2, T3, T4>> q, Class<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> outer(final Query<J4<T1, T2, T3, T4>> q, Class<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> cross(final Query<J4<T1, T2, T3, T4>> q, Class<T5> t) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "cross join", null);
	}

	private static class Query5<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> extends DBQuery<J5<T1, T2, T3, T4, T5>> {
		final int SIZE = 5;
		Query5(final Query<J4<T1, T2, T3, T4>> q, final Class<T5> t, String joinType, Condition on) {
			super(J5.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 5 tables.
	 * It contains 5 typed references (t1 to t5) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J5 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public J5(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
		}
		@SuppressWarnings("unchecked")
		J5(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
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
			try { return t5.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> left(final Query<J5<T1, T2, T3, T4, T5>> q, Class<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> right(final Query<J5<T1, T2, T3, T4, T5>> q, Class<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> inner(final Query<J5<T1, T2, T3, T4, T5>> q, Class<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> outer(final Query<J5<T1, T2, T3, T4, T5>> q, Class<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> cross(final Query<J5<T1, T2, T3, T4, T5>> q, Class<T6> t) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "cross join", null);
	}

	private static class Query6<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> extends DBQuery<J6<T1, T2, T3, T4, T5, T6>> {
		final int SIZE = 6;
		Query6(final Query<J5<T1, T2, T3, T4, T5>> q, final Class<T6> t, String joinType, Condition on) {
			super(J6.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 6 tables.
	 * It contains 6 typed references (t1 to t6) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J6 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public final T6 t6;
		public J6(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
		}
		@SuppressWarnings("unchecked")
		J6(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
			t6 = (T6) oa[offset+5];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t6.FIELDS());
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
			try { return t5.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t6.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t6.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> left(final Query<J6<T1, T2, T3, T4, T5, T6>> q, Class<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> right(final Query<J6<T1, T2, T3, T4, T5, T6>> q, Class<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> inner(final Query<J6<T1, T2, T3, T4, T5, T6>> q, Class<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> outer(final Query<J6<T1, T2, T3, T4, T5, T6>> q, Class<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> cross(final Query<J6<T1, T2, T3, T4, T5, T6>> q, Class<T7> t) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "cross join", null);
	}

	private static class Query7<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> extends DBQuery<J7<T1, T2, T3, T4, T5, T6, T7>> {
		final int SIZE = 7;
		Query7(final Query<J6<T1, T2, T3, T4, T5, T6>> q, final Class<T7> t, String joinType, Condition on) {
			super(J7.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 7 tables.
	 * It contains 7 typed references (t1 to t7) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J7 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public final T6 t6;
		public final T7 t7;
		public J7(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
			this.t7 = t7;
		}
		@SuppressWarnings("unchecked")
		J7(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
			t6 = (T6) oa[offset+5];
			t7 = (T7) oa[offset+6];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t6.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t7.FIELDS());
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
			try { return t5.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t6.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t7.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t6.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t7.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> left(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, Class<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> right(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, Class<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> inner(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, Class<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> outer(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, Class<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> cross(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, Class<T8> t) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "cross join", null);
	}

	private static class Query8<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> extends DBQuery<J8<T1, T2, T3, T4, T5, T6, T7, T8>> {
		final int SIZE = 8;
		Query8(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, final Class<T8> t, String joinType, Condition on) {
			super(J8.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 8 tables.
	 * It contains 8 typed references (t1 to t8) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J8 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public final T6 t6;
		public final T7 t7;
		public final T8 t8;
		public J8(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
			this.t7 = t7;
			this.t8 = t8;
		}
		@SuppressWarnings("unchecked")
		J8(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
			t6 = (T6) oa[offset+5];
			t7 = (T7) oa[offset+6];
			t8 = (T8) oa[offset+7];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t6.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t7.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t8.FIELDS());
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
			try { return t5.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t6.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t7.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t8.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t6.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t7.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t8.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> left(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, Class<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> right(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, Class<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> inner(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, Class<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> outer(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, Class<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> cross(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, Class<T9> t) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "cross join", null);
	}

	private static class Query9<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> extends DBQuery<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> {
		final int SIZE = 9;
		Query9(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, final Class<T9> t, String joinType, Condition on) {
			super(J9.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 9 tables.
	 * It contains 9 typed references (t1 to t9) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J9 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public final T6 t6;
		public final T7 t7;
		public final T8 t8;
		public final T9 t9;
		public J9(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
			this.t7 = t7;
			this.t8 = t8;
			this.t9 = t9;
		}
		@SuppressWarnings("unchecked")
		J9(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
			t6 = (T6) oa[offset+5];
			t7 = (T7) oa[offset+6];
			t8 = (T8) oa[offset+7];
			t9 = (T9) oa[offset+8];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t6.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t7.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t8.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t9.FIELDS());
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
			try { return t5.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t6.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t7.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t8.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t9.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t6.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t7.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t8.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t9.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> left(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, Class<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> right(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, Class<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> inner(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, Class<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> outer(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, Class<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> cross(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, Class<T10> t) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "cross join", null);
	}

	private static class Query10<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> extends DBQuery<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> {
		final int SIZE = 10;
		Query10(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, final Class<T10> t, String joinType, Condition on) {
			super(J10.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 10 tables.
	 * It contains 10 typed references (t1 to t10) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J10 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public final T6 t6;
		public final T7 t7;
		public final T8 t8;
		public final T9 t9;
		public final T10 t10;
		public J10(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
			this.t7 = t7;
			this.t8 = t8;
			this.t9 = t9;
			this.t10 = t10;
		}
		@SuppressWarnings("unchecked")
		J10(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
			t6 = (T6) oa[offset+5];
			t7 = (T7) oa[offset+6];
			t8 = (T8) oa[offset+7];
			t9 = (T9) oa[offset+8];
			t10 = (T10) oa[offset+9];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t6.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t7.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t8.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t9.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t10.FIELDS());
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
			try { return t5.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t6.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t7.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t8.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t9.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t10.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t6.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t7.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t8.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t9.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t10.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> left(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, Class<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> right(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, Class<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> inner(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, Class<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> outer(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, Class<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> cross(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, Class<T11> t) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "cross join", null);
	}

	private static class Query11<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> extends DBQuery<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> {
		final int SIZE = 11;
		Query11(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, final Class<T11> t, String joinType, Condition on) {
			super(J11.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 11 tables.
	 * It contains 11 typed references (t1 to t11) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J11 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public final T6 t6;
		public final T7 t7;
		public final T8 t8;
		public final T9 t9;
		public final T10 t10;
		public final T11 t11;
		public J11(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
			this.t7 = t7;
			this.t8 = t8;
			this.t9 = t9;
			this.t10 = t10;
			this.t11 = t11;
		}
		@SuppressWarnings("unchecked")
		J11(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
			t6 = (T6) oa[offset+5];
			t7 = (T7) oa[offset+6];
			t8 = (T8) oa[offset+7];
			t9 = (T9) oa[offset+8];
			t10 = (T10) oa[offset+9];
			t11 = (T11) oa[offset+10];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t6.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t7.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t8.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t9.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t10.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t11.FIELDS());
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
			try { return t5.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t6.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t7.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t8.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t9.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t10.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t11.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t6.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t7.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t8.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t9.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t10.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t11.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> left(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, Class<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> right(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, Class<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> inner(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, Class<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> outer(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, Class<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> cross(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, Class<T12> t) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "cross join", null);
	}

	private static class Query12<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> extends DBQuery<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> {
		final int SIZE = 12;
		Query12(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, final Class<T12> t, String joinType, Condition on) {
			super(J12.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 12 tables.
	 * It contains 12 typed references (t1 to t12) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J12 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public final T6 t6;
		public final T7 t7;
		public final T8 t8;
		public final T9 t9;
		public final T10 t10;
		public final T11 t11;
		public final T12 t12;
		public J12(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
			this.t7 = t7;
			this.t8 = t8;
			this.t9 = t9;
			this.t10 = t10;
			this.t11 = t11;
			this.t12 = t12;
		}
		@SuppressWarnings("unchecked")
		J12(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
			t6 = (T6) oa[offset+5];
			t7 = (T7) oa[offset+6];
			t8 = (T8) oa[offset+7];
			t9 = (T9) oa[offset+8];
			t10 = (T10) oa[offset+9];
			t11 = (T11) oa[offset+10];
			t12 = (T12) oa[offset+11];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t6.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t7.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t8.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t9.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t10.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t11.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t12.FIELDS());
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
			try { return t5.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t6.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t7.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t8.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t9.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t10.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t11.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t12.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t6.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t7.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t8.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t9.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t10.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t11.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t12.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> left(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, Class<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> right(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, Class<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> inner(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, Class<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> outer(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, Class<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> cross(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, Class<T13> t) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "cross join", null);
	}

	private static class Query13<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> extends DBQuery<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> {
		final int SIZE = 13;
		Query13(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, final Class<T13> t, String joinType, Condition on) {
			super(J13.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 13 tables.
	 * It contains 13 typed references (t1 to t13) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J13 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public final T6 t6;
		public final T7 t7;
		public final T8 t8;
		public final T9 t9;
		public final T10 t10;
		public final T11 t11;
		public final T12 t12;
		public final T13 t13;
		public J13(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
			this.t7 = t7;
			this.t8 = t8;
			this.t9 = t9;
			this.t10 = t10;
			this.t11 = t11;
			this.t12 = t12;
			this.t13 = t13;
		}
		@SuppressWarnings("unchecked")
		J13(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
			t6 = (T6) oa[offset+5];
			t7 = (T7) oa[offset+6];
			t8 = (T8) oa[offset+7];
			t9 = (T9) oa[offset+8];
			t10 = (T10) oa[offset+9];
			t11 = (T11) oa[offset+10];
			t12 = (T12) oa[offset+11];
			t13 = (T13) oa[offset+12];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t6.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t7.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t8.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t9.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t10.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t11.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t12.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t13.FIELDS());
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
			try { return t5.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t6.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t7.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t8.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t9.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t10.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t11.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t12.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t13.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t6.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t7.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t8.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t9.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t10.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t11.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t12.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t13.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> left(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, Class<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> right(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, Class<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> inner(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, Class<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> outer(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, Class<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> cross(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, Class<T14> t) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "cross join", null);
	}

	private static class Query14<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> extends DBQuery<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> {
		final int SIZE = 14;
		Query14(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, final Class<T14> t, String joinType, Condition on) {
			super(J14.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 14 tables.
	 * It contains 14 typed references (t1 to t14) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J14 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public final T6 t6;
		public final T7 t7;
		public final T8 t8;
		public final T9 t9;
		public final T10 t10;
		public final T11 t11;
		public final T12 t12;
		public final T13 t13;
		public final T14 t14;
		public J14(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
			this.t7 = t7;
			this.t8 = t8;
			this.t9 = t9;
			this.t10 = t10;
			this.t11 = t11;
			this.t12 = t12;
			this.t13 = t13;
			this.t14 = t14;
		}
		@SuppressWarnings("unchecked")
		J14(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
			t6 = (T6) oa[offset+5];
			t7 = (T7) oa[offset+6];
			t8 = (T8) oa[offset+7];
			t9 = (T9) oa[offset+8];
			t10 = (T10) oa[offset+9];
			t11 = (T11) oa[offset+10];
			t12 = (T12) oa[offset+11];
			t13 = (T13) oa[offset+12];
			t14 = (T14) oa[offset+13];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t6.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t7.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t8.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t9.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t10.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t11.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t12.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t13.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t14.FIELDS());
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
			try { return t5.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t6.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t7.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t8.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t9.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t10.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t11.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t12.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t13.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t14.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t6.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t7.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t8.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t9.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t10.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t11.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t12.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t13.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t14.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> left(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, Class<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> right(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, Class<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> inner(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, Class<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> outer(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, Class<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> cross(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, Class<T15> t) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "cross join", null);
	}

	private static class Query15<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> extends DBQuery<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> {
		final int SIZE = 15;
		Query15(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, final Class<T15> t, String joinType, Condition on) {
			super(J15.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 15 tables.
	 * It contains 15 typed references (t1 to t15) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J15 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public final T6 t6;
		public final T7 t7;
		public final T8 t8;
		public final T9 t9;
		public final T10 t10;
		public final T11 t11;
		public final T12 t12;
		public final T13 t13;
		public final T14 t14;
		public final T15 t15;
		public J15(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
			this.t7 = t7;
			this.t8 = t8;
			this.t9 = t9;
			this.t10 = t10;
			this.t11 = t11;
			this.t12 = t12;
			this.t13 = t13;
			this.t14 = t14;
			this.t15 = t15;
		}
		@SuppressWarnings("unchecked")
		J15(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
			t6 = (T6) oa[offset+5];
			t7 = (T7) oa[offset+6];
			t8 = (T8) oa[offset+7];
			t9 = (T9) oa[offset+8];
			t10 = (T10) oa[offset+9];
			t11 = (T11) oa[offset+10];
			t12 = (T12) oa[offset+11];
			t13 = (T13) oa[offset+12];
			t14 = (T14) oa[offset+13];
			t15 = (T15) oa[offset+14];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t6.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t7.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t8.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t9.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t10.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t11.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t12.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t13.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t14.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t15.FIELDS());
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
			try { return t5.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t6.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t7.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t8.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t9.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t10.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t11.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t12.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t13.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t14.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t15.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t6.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t7.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t8.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t9.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t10.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t11.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t12.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t13.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t14.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t15.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> left(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, Class<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> right(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, Class<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> inner(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, Class<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> outer(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, Class<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.nosco.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> cross(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, Class<T16> t) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "cross join", null);
	}

	private static class Query16<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> extends DBQuery<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> {
		final int SIZE = 16;
		Query16(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, final Class<T16> t, String joinType, Condition on) {
			super(J16.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 16 tables.
	 * It contains 16 typed references (t1 to t16) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J16 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> extends J {
		private List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;
		public final T1 t1;
		public final T2 t2;
		public final T3 t3;
		public final T4 t4;
		public final T5 t5;
		public final T6 t6;
		public final T7 t7;
		public final T8 t8;
		public final T9 t9;
		public final T10 t10;
		public final T11 t11;
		public final T12 t12;
		public final T13 t13;
		public final T14 t14;
		public final T15 t15;
		public final T16 t16;
		public J16(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16) {
			this.t1 = t1;
			this.t2 = t2;
			this.t3 = t3;
			this.t4 = t4;
			this.t5 = t5;
			this.t6 = t6;
			this.t7 = t7;
			this.t8 = t8;
			this.t9 = t9;
			this.t10 = t10;
			this.t11 = t11;
			this.t12 = t12;
			this.t13 = t13;
			this.t14 = t14;
			this.t15 = t15;
			this.t16 = t16;
		}
		@SuppressWarnings("unchecked")
		J16(final Object[] oa, final int offset) {
			t1 = (T1) oa[offset+0];
			t2 = (T2) oa[offset+1];
			t3 = (T3) oa[offset+2];
			t4 = (T4) oa[offset+3];
			t5 = (T5) oa[offset+4];
			t6 = (T6) oa[offset+5];
			t7 = (T7) oa[offset+6];
			t8 = (T8) oa[offset+7];
			t9 = (T9) oa[offset+8];
			t10 = (T10) oa[offset+9];
			t11 = (T11) oa[offset+10];
			t12 = (T12) oa[offset+11];
			t13 = (T13) oa[offset+12];
			t14 = (T14) oa[offset+13];
			t15 = (T15) oa[offset+14];
			t16 = (T16) oa[offset+15];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME());
		}
		@Override
		public List<Field<?>> FIELDS() {
			if (__NOSCO_PRIVATE_FIELDS == null) {
				__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();
				__NOSCO_PRIVATE_FIELDS.addAll(t1.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t2.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t3.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t4.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t5.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t6.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t7.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t8.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t9.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t10.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t11.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t12.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t13.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t14.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t15.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t16.FIELDS());
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
			try { return t5.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t6.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t7.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t8.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t9.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t10.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t11.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t12.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t13.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t14.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t15.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t16.get(field); }
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
			try { t5.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t6.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t7.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t8.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t9.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t10.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t11.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t12.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t13.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t14.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t15.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t16.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed());
		}
	}

}
