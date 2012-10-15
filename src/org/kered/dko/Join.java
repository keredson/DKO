package org.kered.dko;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.kered.dko.Field.FK;
import org.kered.dko.Table.__Alias;

public class Join {

	static abstract class J extends Table {

		List<Class<?>> types = null;
	}

	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> left(final Class<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "left join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> left(final Class<T1> t1, __Alias<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "left join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> left(final Query<T1> q, Class<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> left(final __Alias<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "left join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> left(final __Alias<T1> t1, __Alias<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "left join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> left(final Query<T1> q, __Alias<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> right(final Class<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "right join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> right(final Class<T1> t1, __Alias<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "right join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> right(final Query<T1> q, Class<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> right(final __Alias<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "right join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> right(final __Alias<T1> t1, __Alias<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "right join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> right(final Query<T1> q, __Alias<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> inner(final Class<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "inner join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> inner(final Class<T1> t1, __Alias<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "inner join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> inner(final Query<T1> q, Class<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> inner(final __Alias<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "inner join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> inner(final __Alias<T1> t1, __Alias<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "inner join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> inner(final Query<T1> q, __Alias<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> outer(final Class<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "outer join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> outer(final Class<T1> t1, __Alias<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "outer join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> outer(final Query<T1> q, Class<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> outer(final __Alias<T1> t1, Class<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "outer join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> outer(final __Alias<T1> t1, __Alias<T2> t2, Condition on) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "outer join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> outer(final Query<T1> q, __Alias<T2> t, Condition on) {
		return new Query2<T1, T2>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> cross(final Class<T1> t1, Class<T2> t2) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "cross join", null);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> cross(final Class<T1> t1, __Alias<T2> t2) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "cross join", null);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> cross(final Query<T1> q, Class<T2> t) {
		return new Query2<T1, T2>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> cross(final __Alias<T1> t1, Class<T2> t2) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "cross join", null);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> cross(final __Alias<T1> t1, __Alias<T2> t2) {
		return new Query2<T1, T2>(new DBQuery<T1>(t1), t2, "cross join", null);
	}
	/** 
	 * Joins types T1, T2 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J2<T1, T2>>}
	 */
	public static <T1 extends Table, T2 extends Table> Query2<T1, T2> cross(final Query<T1> q, __Alias<T2> t) {
		return new Query2<T1, T2>(q, t, "cross join", null);
	}

	private static class Query2<T1 extends Table, T2 extends Table> extends DBQuery<J2<T1, T2>> {
		final int SIZE = 2;
		public Query2(final Query<T1> q, final Class<T2> t, String joinType, Condition on) {
			super(J2.class, q, t, joinType, on);
		}
		public Query2(final Query<T1> q, final __Alias<T2> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> left(final Query<J2<T1, T2>> q, Class<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> left(final Query<J2<T1, T2>> q, __Alias<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> right(final Query<J2<T1, T2>> q, Class<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> right(final Query<J2<T1, T2>> q, __Alias<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> inner(final Query<J2<T1, T2>> q, Class<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> inner(final Query<J2<T1, T2>> q, __Alias<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> outer(final Query<J2<T1, T2>> q, Class<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> outer(final Query<J2<T1, T2>> q, __Alias<T3> t, Condition on) {
		return new Query3<T1, T2, T3>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> cross(final Query<J2<T1, T2>> q, Class<T3> t) {
		return new Query3<T1, T2, T3>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J3<T1, T2, T3>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table> Query3<T1, T2, T3> cross(final Query<J2<T1, T2>> q, __Alias<T3> t) {
		return new Query3<T1, T2, T3>(q, t, "cross join", null);
	}

	private static class Query3<T1 extends Table, T2 extends Table, T3 extends Table> extends DBQuery<J3<T1, T2, T3>> {
		final int SIZE = 3;
		Query3(final Query<J2<T1, T2>> q, final Class<T3> t, String joinType, Condition on) {
			super(J3.class, q, t, joinType, on);
		}
		Query3(final Query<J2<T1, T2>> q, final __Alias<T3> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> left(final Query<J3<T1, T2, T3>> q, Class<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> left(final Query<J3<T1, T2, T3>> q, __Alias<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> right(final Query<J3<T1, T2, T3>> q, Class<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> right(final Query<J3<T1, T2, T3>> q, __Alias<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> inner(final Query<J3<T1, T2, T3>> q, Class<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> inner(final Query<J3<T1, T2, T3>> q, __Alias<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> outer(final Query<J3<T1, T2, T3>> q, Class<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> outer(final Query<J3<T1, T2, T3>> q, __Alias<T4> t, Condition on) {
		return new Query4<T1, T2, T3, T4>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> cross(final Query<J3<T1, T2, T3>> q, Class<T4> t) {
		return new Query4<T1, T2, T3, T4>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J4<T1, T2, T3, T4>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> Query4<T1, T2, T3, T4> cross(final Query<J3<T1, T2, T3>> q, __Alias<T4> t) {
		return new Query4<T1, T2, T3, T4>(q, t, "cross join", null);
	}

	private static class Query4<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table> extends DBQuery<J4<T1, T2, T3, T4>> {
		final int SIZE = 4;
		Query4(final Query<J3<T1, T2, T3>> q, final Class<T4> t, String joinType, Condition on) {
			super(J4.class, q, t, joinType, on);
		}
		Query4(final Query<J3<T1, T2, T3>> q, final __Alias<T4> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> left(final Query<J4<T1, T2, T3, T4>> q, Class<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> left(final Query<J4<T1, T2, T3, T4>> q, __Alias<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> right(final Query<J4<T1, T2, T3, T4>> q, Class<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> right(final Query<J4<T1, T2, T3, T4>> q, __Alias<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> inner(final Query<J4<T1, T2, T3, T4>> q, Class<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> inner(final Query<J4<T1, T2, T3, T4>> q, __Alias<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> outer(final Query<J4<T1, T2, T3, T4>> q, Class<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> outer(final Query<J4<T1, T2, T3, T4>> q, __Alias<T5> t, Condition on) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> cross(final Query<J4<T1, T2, T3, T4>> q, Class<T5> t) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J5<T1, T2, T3, T4, T5>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> Query5<T1, T2, T3, T4, T5> cross(final Query<J4<T1, T2, T3, T4>> q, __Alias<T5> t) {
		return new Query5<T1, T2, T3, T4, T5>(q, t, "cross join", null);
	}

	private static class Query5<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table> extends DBQuery<J5<T1, T2, T3, T4, T5>> {
		final int SIZE = 5;
		Query5(final Query<J4<T1, T2, T3, T4>> q, final Class<T5> t, String joinType, Condition on) {
			super(J5.class, q, t, joinType, on);
		}
		Query5(final Query<J4<T1, T2, T3, T4>> q, final __Alias<T5> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> left(final Query<J5<T1, T2, T3, T4, T5>> q, Class<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> left(final Query<J5<T1, T2, T3, T4, T5>> q, __Alias<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> right(final Query<J5<T1, T2, T3, T4, T5>> q, Class<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> right(final Query<J5<T1, T2, T3, T4, T5>> q, __Alias<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> inner(final Query<J5<T1, T2, T3, T4, T5>> q, Class<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> inner(final Query<J5<T1, T2, T3, T4, T5>> q, __Alias<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> outer(final Query<J5<T1, T2, T3, T4, T5>> q, Class<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> outer(final Query<J5<T1, T2, T3, T4, T5>> q, __Alias<T6> t, Condition on) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> cross(final Query<J5<T1, T2, T3, T4, T5>> q, Class<T6> t) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J6<T1, T2, T3, T4, T5, T6>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> Query6<T1, T2, T3, T4, T5, T6> cross(final Query<J5<T1, T2, T3, T4, T5>> q, __Alias<T6> t) {
		return new Query6<T1, T2, T3, T4, T5, T6>(q, t, "cross join", null);
	}

	private static class Query6<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table> extends DBQuery<J6<T1, T2, T3, T4, T5, T6>> {
		final int SIZE = 6;
		Query6(final Query<J5<T1, T2, T3, T4, T5>> q, final Class<T6> t, String joinType, Condition on) {
			super(J6.class, q, t, joinType, on);
		}
		Query6(final Query<J5<T1, T2, T3, T4, T5>> q, final __Alias<T6> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> left(final Query<J6<T1, T2, T3, T4, T5, T6>> q, Class<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> left(final Query<J6<T1, T2, T3, T4, T5, T6>> q, __Alias<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> right(final Query<J6<T1, T2, T3, T4, T5, T6>> q, Class<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> right(final Query<J6<T1, T2, T3, T4, T5, T6>> q, __Alias<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> inner(final Query<J6<T1, T2, T3, T4, T5, T6>> q, Class<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> inner(final Query<J6<T1, T2, T3, T4, T5, T6>> q, __Alias<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> outer(final Query<J6<T1, T2, T3, T4, T5, T6>> q, Class<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> outer(final Query<J6<T1, T2, T3, T4, T5, T6>> q, __Alias<T7> t, Condition on) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> cross(final Query<J6<T1, T2, T3, T4, T5, T6>> q, Class<T7> t) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J7<T1, T2, T3, T4, T5, T6, T7>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> Query7<T1, T2, T3, T4, T5, T6, T7> cross(final Query<J6<T1, T2, T3, T4, T5, T6>> q, __Alias<T7> t) {
		return new Query7<T1, T2, T3, T4, T5, T6, T7>(q, t, "cross join", null);
	}

	private static class Query7<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table> extends DBQuery<J7<T1, T2, T3, T4, T5, T6, T7>> {
		final int SIZE = 7;
		Query7(final Query<J6<T1, T2, T3, T4, T5, T6>> q, final Class<T7> t, String joinType, Condition on) {
			super(J7.class, q, t, joinType, on);
		}
		Query7(final Query<J6<T1, T2, T3, T4, T5, T6>> q, final __Alias<T7> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> left(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, Class<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> left(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, __Alias<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> right(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, Class<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> right(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, __Alias<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> inner(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, Class<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> inner(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, __Alias<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> outer(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, Class<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> outer(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, __Alias<T8> t, Condition on) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> cross(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, Class<T8> t) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J8<T1, T2, T3, T4, T5, T6, T7, T8>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> Query8<T1, T2, T3, T4, T5, T6, T7, T8> cross(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, __Alias<T8> t) {
		return new Query8<T1, T2, T3, T4, T5, T6, T7, T8>(q, t, "cross join", null);
	}

	private static class Query8<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table> extends DBQuery<J8<T1, T2, T3, T4, T5, T6, T7, T8>> {
		final int SIZE = 8;
		Query8(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, final Class<T8> t, String joinType, Condition on) {
			super(J8.class, q, t, joinType, on);
		}
		Query8(final Query<J7<T1, T2, T3, T4, T5, T6, T7>> q, final __Alias<T8> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> left(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, Class<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> left(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, __Alias<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> right(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, Class<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> right(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, __Alias<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> inner(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, Class<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> inner(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, __Alias<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> outer(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, Class<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> outer(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, __Alias<T9> t, Condition on) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> cross(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, Class<T9> t) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9> cross(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, __Alias<T9> t) {
		return new Query9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(q, t, "cross join", null);
	}

	private static class Query9<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table> extends DBQuery<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> {
		final int SIZE = 9;
		Query9(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, final Class<T9> t, String joinType, Condition on) {
			super(J9.class, q, t, joinType, on);
		}
		Query9(final Query<J8<T1, T2, T3, T4, T5, T6, T7, T8>> q, final __Alias<T9> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> left(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, Class<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> left(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, __Alias<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> right(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, Class<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> right(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, __Alias<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> inner(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, Class<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> inner(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, __Alias<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> outer(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, Class<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> outer(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, __Alias<T10> t, Condition on) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> cross(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, Class<T10> t) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> cross(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, __Alias<T10> t) {
		return new Query10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(q, t, "cross join", null);
	}

	private static class Query10<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table> extends DBQuery<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> {
		final int SIZE = 10;
		Query10(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, final Class<T10> t, String joinType, Condition on) {
			super(J10.class, q, t, joinType, on);
		}
		Query10(final Query<J9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> q, final __Alias<T10> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> left(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, Class<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> left(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, __Alias<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> right(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, Class<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> right(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, __Alias<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> inner(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, Class<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> inner(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, __Alias<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> outer(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, Class<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> outer(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, __Alias<T11> t, Condition on) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> cross(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, Class<T11> t) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> cross(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, __Alias<T11> t) {
		return new Query11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(q, t, "cross join", null);
	}

	private static class Query11<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table> extends DBQuery<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> {
		final int SIZE = 11;
		Query11(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, final Class<T11> t, String joinType, Condition on) {
			super(J11.class, q, t, joinType, on);
		}
		Query11(final Query<J10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> q, final __Alias<T11> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> left(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, Class<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> left(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, __Alias<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> right(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, Class<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> right(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, __Alias<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> inner(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, Class<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> inner(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, __Alias<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> outer(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, Class<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> outer(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, __Alias<T12> t, Condition on) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> cross(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, Class<T12> t) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> cross(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, __Alias<T12> t) {
		return new Query12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>(q, t, "cross join", null);
	}

	private static class Query12<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table> extends DBQuery<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> {
		final int SIZE = 12;
		Query12(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, final Class<T12> t, String joinType, Condition on) {
			super(J12.class, q, t, joinType, on);
		}
		Query12(final Query<J11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> q, final __Alias<T12> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> left(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, Class<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> left(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, __Alias<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> right(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, Class<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> right(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, __Alias<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> inner(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, Class<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> inner(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, __Alias<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> outer(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, Class<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> outer(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, __Alias<T13> t, Condition on) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> cross(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, Class<T13> t) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> cross(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, __Alias<T13> t) {
		return new Query13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(q, t, "cross join", null);
	}

	private static class Query13<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table> extends DBQuery<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> {
		final int SIZE = 13;
		Query13(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, final Class<T13> t, String joinType, Condition on) {
			super(J13.class, q, t, joinType, on);
		}
		Query13(final Query<J12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> q, final __Alias<T13> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> left(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, Class<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> left(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, __Alias<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> right(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, Class<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> right(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, __Alias<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> inner(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, Class<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> inner(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, __Alias<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> outer(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, Class<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> outer(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, __Alias<T14> t, Condition on) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> cross(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, Class<T14> t) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> cross(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, __Alias<T14> t) {
		return new Query14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(q, t, "cross join", null);
	}

	private static class Query14<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table> extends DBQuery<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> {
		final int SIZE = 14;
		Query14(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, final Class<T14> t, String joinType, Condition on) {
			super(J14.class, q, t, joinType, on);
		}
		Query14(final Query<J13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> q, final __Alias<T14> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> left(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, Class<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> left(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, __Alias<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> right(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, Class<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> right(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, __Alias<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> inner(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, Class<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> inner(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, __Alias<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> outer(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, Class<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> outer(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, __Alias<T15> t, Condition on) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> cross(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, Class<T15> t) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> cross(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, __Alias<T15> t) {
		return new Query15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>(q, t, "cross join", null);
	}

	private static class Query15<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table> extends DBQuery<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> {
		final int SIZE = 15;
		Query15(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, final Class<T15> t, String joinType, Condition on) {
			super(J15.class, q, t, joinType, on);
		}
		Query15(final Query<J14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> q, final __Alias<T15> t, String joinType, Condition on) {
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
	 * it as a {@code org.kered.dko.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> left(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, Class<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> left(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, __Alias<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> right(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, Class<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> right(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, __Alias<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> inner(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, Class<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> inner(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, __Alias<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> outer(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, Class<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> outer(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, __Alias<T16> t, Condition on) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> cross(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, Class<T16> t) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> cross(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, __Alias<T16> t) {
		return new Query16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(q, t, "cross join", null);
	}

	private static class Query16<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table> extends DBQuery<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> {
		final int SIZE = 16;
		Query16(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, final Class<T16> t, String joinType, Condition on) {
			super(J16.class, q, t, joinType, on);
		}
		Query16(final Query<J15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> q, final __Alias<T16> t, String joinType, Condition on) {
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

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> left(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, Class<T17> t, Condition on) {
		return new Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> left(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, __Alias<T17> t, Condition on) {
		return new Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> right(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, Class<T17> t, Condition on) {
		return new Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> right(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, __Alias<T17> t, Condition on) {
		return new Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> inner(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, Class<T17> t, Condition on) {
		return new Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> inner(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, __Alias<T17> t, Condition on) {
		return new Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> outer(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, Class<T17> t, Condition on) {
		return new Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> outer(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, __Alias<T17> t, Condition on) {
		return new Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> cross(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, Class<T17> t) {
		return new Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> cross(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, __Alias<T17> t) {
		return new Query17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(q, t, "cross join", null);
	}

	private static class Query17<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> extends DBQuery<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> {
		final int SIZE = 17;
		Query17(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, final Class<T17> t, String joinType, Condition on) {
			super(J17.class, q, t, joinType, on);
		}
		Query17(final Query<J16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> q, final __Alias<T17> t, String joinType, Condition on) {
			super(J17.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 17 tables.
	 * It contains 17 typed references (t1 to t17) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J17 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table> extends J {
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
		public final T17 t17;
		public J17(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17) {
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
			this.t17 = t17;
		}
		@SuppressWarnings("unchecked")
		J17(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
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
			try { return t17.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> left(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, Class<T18> t, Condition on) {
		return new Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> left(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, __Alias<T18> t, Condition on) {
		return new Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> right(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, Class<T18> t, Condition on) {
		return new Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> right(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, __Alias<T18> t, Condition on) {
		return new Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> inner(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, Class<T18> t, Condition on) {
		return new Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> inner(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, __Alias<T18> t, Condition on) {
		return new Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> outer(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, Class<T18> t, Condition on) {
		return new Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> outer(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, __Alias<T18> t, Condition on) {
		return new Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> cross(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, Class<T18> t) {
		return new Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> cross(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, __Alias<T18> t) {
		return new Query18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>(q, t, "cross join", null);
	}

	private static class Query18<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> extends DBQuery<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> {
		final int SIZE = 18;
		Query18(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, final Class<T18> t, String joinType, Condition on) {
			super(J18.class, q, t, joinType, on);
		}
		Query18(final Query<J17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> q, final __Alias<T18> t, String joinType, Condition on) {
			super(J18.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 18 tables.
	 * It contains 18 typed references (t1 to t18) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J18 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public J18(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18) {
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
			this.t17 = t17;
			this.t18 = t18;
		}
		@SuppressWarnings("unchecked")
		J18(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> left(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, Class<T19> t, Condition on) {
		return new Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> left(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, __Alias<T19> t, Condition on) {
		return new Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> right(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, Class<T19> t, Condition on) {
		return new Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> right(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, __Alias<T19> t, Condition on) {
		return new Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> inner(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, Class<T19> t, Condition on) {
		return new Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> inner(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, __Alias<T19> t, Condition on) {
		return new Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> outer(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, Class<T19> t, Condition on) {
		return new Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> outer(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, __Alias<T19> t, Condition on) {
		return new Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> cross(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, Class<T19> t) {
		return new Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> cross(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, __Alias<T19> t) {
		return new Query19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(q, t, "cross join", null);
	}

	private static class Query19<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> extends DBQuery<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> {
		final int SIZE = 19;
		Query19(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, final Class<T19> t, String joinType, Condition on) {
			super(J19.class, q, t, joinType, on);
		}
		Query19(final Query<J18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> q, final __Alias<T19> t, String joinType, Condition on) {
			super(J19.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 19 tables.
	 * It contains 19 typed references (t1 to t19) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J19 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public J19(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
		}
		@SuppressWarnings("unchecked")
		J19(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> left(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, Class<T20> t, Condition on) {
		return new Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> left(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, __Alias<T20> t, Condition on) {
		return new Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> right(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, Class<T20> t, Condition on) {
		return new Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> right(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, __Alias<T20> t, Condition on) {
		return new Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> inner(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, Class<T20> t, Condition on) {
		return new Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> inner(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, __Alias<T20> t, Condition on) {
		return new Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> outer(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, Class<T20> t, Condition on) {
		return new Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> outer(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, __Alias<T20> t, Condition on) {
		return new Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> cross(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, Class<T20> t) {
		return new Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> cross(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, __Alias<T20> t) {
		return new Query20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>(q, t, "cross join", null);
	}

	private static class Query20<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> extends DBQuery<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> {
		final int SIZE = 20;
		Query20(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, final Class<T20> t, String joinType, Condition on) {
			super(J20.class, q, t, joinType, on);
		}
		Query20(final Query<J19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> q, final __Alias<T20> t, String joinType, Condition on) {
			super(J20.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 20 tables.
	 * It contains 20 typed references (t1 to t20) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J20 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public J20(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
		}
		@SuppressWarnings("unchecked")
		J20(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> left(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, Class<T21> t, Condition on) {
		return new Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> left(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, __Alias<T21> t, Condition on) {
		return new Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> right(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, Class<T21> t, Condition on) {
		return new Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> right(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, __Alias<T21> t, Condition on) {
		return new Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> inner(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, Class<T21> t, Condition on) {
		return new Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> inner(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, __Alias<T21> t, Condition on) {
		return new Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> outer(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, Class<T21> t, Condition on) {
		return new Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> outer(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, __Alias<T21> t, Condition on) {
		return new Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> cross(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, Class<T21> t) {
		return new Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> cross(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, __Alias<T21> t) {
		return new Query21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>(q, t, "cross join", null);
	}

	private static class Query21<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> extends DBQuery<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> {
		final int SIZE = 21;
		Query21(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, final Class<T21> t, String joinType, Condition on) {
			super(J21.class, q, t, joinType, on);
		}
		Query21(final Query<J20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> q, final __Alias<T21> t, String joinType, Condition on) {
			super(J21.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 21 tables.
	 * It contains 21 typed references (t1 to t21) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J21 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public J21(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
		}
		@SuppressWarnings("unchecked")
		J21(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> left(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, Class<T22> t, Condition on) {
		return new Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> left(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, __Alias<T22> t, Condition on) {
		return new Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> right(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, Class<T22> t, Condition on) {
		return new Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> right(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, __Alias<T22> t, Condition on) {
		return new Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> inner(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, Class<T22> t, Condition on) {
		return new Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> inner(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, __Alias<T22> t, Condition on) {
		return new Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> outer(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, Class<T22> t, Condition on) {
		return new Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> outer(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, __Alias<T22> t, Condition on) {
		return new Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> cross(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, Class<T22> t) {
		return new Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> cross(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, __Alias<T22> t) {
		return new Query22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>(q, t, "cross join", null);
	}

	private static class Query22<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> extends DBQuery<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> {
		final int SIZE = 22;
		Query22(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, final Class<T22> t, String joinType, Condition on) {
			super(J22.class, q, t, joinType, on);
		}
		Query22(final Query<J21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> q, final __Alias<T22> t, String joinType, Condition on) {
			super(J22.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 22 tables.
	 * It contains 22 typed references (t1 to t22) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J22 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public final T22 t22;
		public J22(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21, final T22 t22) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
			this.t22 = t22;
		}
		@SuppressWarnings("unchecked")
		J22(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
			t22 = (T22) oa[offset+21];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME())+" + "+(t22==null ? null : t22.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME())+" + "+(t22==null ? null : t22.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t22.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t22.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t22.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert()) && (t22!=null && t22.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds)) && (t22!=null && t22.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update()) && (t22!=null && t22.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds)) && (t22!=null && t22.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete()) && (t22!=null && t22.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds)) && (t22!=null && t22.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save()) && (t22!=null && t22.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds)) && (t22!=null && t22.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists()) || (t22!=null && t22.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds)) || (t22!=null && t22.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21 +"+"+ t22;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed()) +"+"+ (t22==null ? t22 : t22.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23> left(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, Class<T23> t, Condition on) {
		return new Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23> left(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, __Alias<T23> t, Condition on) {
		return new Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23> right(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, Class<T23> t, Condition on) {
		return new Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23> right(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, __Alias<T23> t, Condition on) {
		return new Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23> inner(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, Class<T23> t, Condition on) {
		return new Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23> inner(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, __Alias<T23> t, Condition on) {
		return new Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23> outer(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, Class<T23> t, Condition on) {
		return new Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23> outer(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, __Alias<T23> t, Condition on) {
		return new Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23> cross(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, Class<T23> t) {
		return new Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23> cross(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, __Alias<T23> t) {
		return new Query23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>(q, t, "cross join", null);
	}

	private static class Query23<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> extends DBQuery<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> {
		final int SIZE = 23;
		Query23(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, final Class<T23> t, String joinType, Condition on) {
			super(J23.class, q, t, joinType, on);
		}
		Query23(final Query<J22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> q, final __Alias<T23> t, String joinType, Condition on) {
			super(J23.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 23 tables.
	 * It contains 23 typed references (t1 to t23) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J23 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public final T22 t22;
		public final T23 t23;
		public J23(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21, final T22 t22, final T23 t23) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
			this.t22 = t22;
			this.t23 = t23;
		}
		@SuppressWarnings("unchecked")
		J23(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
			t22 = (T22) oa[offset+21];
			t23 = (T23) oa[offset+22];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME())+" + "+(t22==null ? null : t22.SCHEMA_NAME())+" + "+(t23==null ? null : t23.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME())+" + "+(t22==null ? null : t22.TABLE_NAME())+" + "+(t23==null ? null : t23.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t22.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t23.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t22.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t23.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t22.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t23.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert()) && (t22!=null && t22.insert()) && (t23!=null && t23.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds)) && (t22!=null && t22.insert(ds)) && (t23!=null && t23.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update()) && (t22!=null && t22.update()) && (t23!=null && t23.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds)) && (t22!=null && t22.update(ds)) && (t23!=null && t23.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete()) && (t22!=null && t22.delete()) && (t23!=null && t23.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds)) && (t22!=null && t22.delete(ds)) && (t23!=null && t23.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save()) && (t22!=null && t22.save()) && (t23!=null && t23.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds)) && (t22!=null && t22.save(ds)) && (t23!=null && t23.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists()) || (t22!=null && t22.exists()) || (t23!=null && t23.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds)) || (t22!=null && t22.exists(ds)) || (t23!=null && t23.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21 +"+"+ t22 +"+"+ t23;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed()) +"+"+ (t22==null ? t22 : t22.toStringDetailed()) +"+"+ (t23==null ? t23 : t23.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24> left(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, Class<T24> t, Condition on) {
		return new Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24> left(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, __Alias<T24> t, Condition on) {
		return new Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24> right(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, Class<T24> t, Condition on) {
		return new Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24> right(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, __Alias<T24> t, Condition on) {
		return new Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24> inner(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, Class<T24> t, Condition on) {
		return new Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24> inner(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, __Alias<T24> t, Condition on) {
		return new Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24> outer(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, Class<T24> t, Condition on) {
		return new Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24> outer(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, __Alias<T24> t, Condition on) {
		return new Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24> cross(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, Class<T24> t) {
		return new Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24> cross(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, __Alias<T24> t) {
		return new Query24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>(q, t, "cross join", null);
	}

	private static class Query24<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> extends DBQuery<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> {
		final int SIZE = 24;
		Query24(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, final Class<T24> t, String joinType, Condition on) {
			super(J24.class, q, t, joinType, on);
		}
		Query24(final Query<J23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>> q, final __Alias<T24> t, String joinType, Condition on) {
			super(J24.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 24 tables.
	 * It contains 24 typed references (t1 to t24) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J24 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public final T22 t22;
		public final T23 t23;
		public final T24 t24;
		public J24(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21, final T22 t22, final T23 t23, final T24 t24) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
			this.t22 = t22;
			this.t23 = t23;
			this.t24 = t24;
		}
		@SuppressWarnings("unchecked")
		J24(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
			t22 = (T22) oa[offset+21];
			t23 = (T23) oa[offset+22];
			t24 = (T24) oa[offset+23];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME())+" + "+(t22==null ? null : t22.SCHEMA_NAME())+" + "+(t23==null ? null : t23.SCHEMA_NAME())+" + "+(t24==null ? null : t24.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME())+" + "+(t22==null ? null : t22.TABLE_NAME())+" + "+(t23==null ? null : t23.TABLE_NAME())+" + "+(t24==null ? null : t24.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t22.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t23.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t24.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t22.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t23.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t24.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t22.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t23.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t24.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert()) && (t22!=null && t22.insert()) && (t23!=null && t23.insert()) && (t24!=null && t24.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds)) && (t22!=null && t22.insert(ds)) && (t23!=null && t23.insert(ds)) && (t24!=null && t24.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update()) && (t22!=null && t22.update()) && (t23!=null && t23.update()) && (t24!=null && t24.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds)) && (t22!=null && t22.update(ds)) && (t23!=null && t23.update(ds)) && (t24!=null && t24.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete()) && (t22!=null && t22.delete()) && (t23!=null && t23.delete()) && (t24!=null && t24.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds)) && (t22!=null && t22.delete(ds)) && (t23!=null && t23.delete(ds)) && (t24!=null && t24.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save()) && (t22!=null && t22.save()) && (t23!=null && t23.save()) && (t24!=null && t24.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds)) && (t22!=null && t22.save(ds)) && (t23!=null && t23.save(ds)) && (t24!=null && t24.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists()) || (t22!=null && t22.exists()) || (t23!=null && t23.exists()) || (t24!=null && t24.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds)) || (t22!=null && t22.exists(ds)) || (t23!=null && t23.exists(ds)) || (t24!=null && t24.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21 +"+"+ t22 +"+"+ t23 +"+"+ t24;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed()) +"+"+ (t22==null ? t22 : t22.toStringDetailed()) +"+"+ (t23==null ? t23 : t23.toStringDetailed()) +"+"+ (t24==null ? t24 : t24.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25> left(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, Class<T25> t, Condition on) {
		return new Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25> left(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, __Alias<T25> t, Condition on) {
		return new Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25> right(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, Class<T25> t, Condition on) {
		return new Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25> right(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, __Alias<T25> t, Condition on) {
		return new Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25> inner(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, Class<T25> t, Condition on) {
		return new Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25> inner(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, __Alias<T25> t, Condition on) {
		return new Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25> outer(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, Class<T25> t, Condition on) {
		return new Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25> outer(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, __Alias<T25> t, Condition on) {
		return new Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25> cross(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, Class<T25> t) {
		return new Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25> cross(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, __Alias<T25> t) {
		return new Query25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>(q, t, "cross join", null);
	}

	private static class Query25<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> extends DBQuery<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> {
		final int SIZE = 25;
		Query25(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, final Class<T25> t, String joinType, Condition on) {
			super(J25.class, q, t, joinType, on);
		}
		Query25(final Query<J24<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24>> q, final __Alias<T25> t, String joinType, Condition on) {
			super(J25.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 25 tables.
	 * It contains 25 typed references (t1 to t25) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J25 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public final T22 t22;
		public final T23 t23;
		public final T24 t24;
		public final T25 t25;
		public J25(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21, final T22 t22, final T23 t23, final T24 t24, final T25 t25) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
			this.t22 = t22;
			this.t23 = t23;
			this.t24 = t24;
			this.t25 = t25;
		}
		@SuppressWarnings("unchecked")
		J25(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
			t22 = (T22) oa[offset+21];
			t23 = (T23) oa[offset+22];
			t24 = (T24) oa[offset+23];
			t25 = (T25) oa[offset+24];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME())+" + "+(t22==null ? null : t22.SCHEMA_NAME())+" + "+(t23==null ? null : t23.SCHEMA_NAME())+" + "+(t24==null ? null : t24.SCHEMA_NAME())+" + "+(t25==null ? null : t25.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME())+" + "+(t22==null ? null : t22.TABLE_NAME())+" + "+(t23==null ? null : t23.TABLE_NAME())+" + "+(t24==null ? null : t24.TABLE_NAME())+" + "+(t25==null ? null : t25.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t22.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t23.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t24.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t25.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t22.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t23.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t24.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t25.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t22.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t23.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t24.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t25.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert()) && (t22!=null && t22.insert()) && (t23!=null && t23.insert()) && (t24!=null && t24.insert()) && (t25!=null && t25.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds)) && (t22!=null && t22.insert(ds)) && (t23!=null && t23.insert(ds)) && (t24!=null && t24.insert(ds)) && (t25!=null && t25.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update()) && (t22!=null && t22.update()) && (t23!=null && t23.update()) && (t24!=null && t24.update()) && (t25!=null && t25.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds)) && (t22!=null && t22.update(ds)) && (t23!=null && t23.update(ds)) && (t24!=null && t24.update(ds)) && (t25!=null && t25.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete()) && (t22!=null && t22.delete()) && (t23!=null && t23.delete()) && (t24!=null && t24.delete()) && (t25!=null && t25.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds)) && (t22!=null && t22.delete(ds)) && (t23!=null && t23.delete(ds)) && (t24!=null && t24.delete(ds)) && (t25!=null && t25.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save()) && (t22!=null && t22.save()) && (t23!=null && t23.save()) && (t24!=null && t24.save()) && (t25!=null && t25.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds)) && (t22!=null && t22.save(ds)) && (t23!=null && t23.save(ds)) && (t24!=null && t24.save(ds)) && (t25!=null && t25.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists()) || (t22!=null && t22.exists()) || (t23!=null && t23.exists()) || (t24!=null && t24.exists()) || (t25!=null && t25.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds)) || (t22!=null && t22.exists(ds)) || (t23!=null && t23.exists(ds)) || (t24!=null && t24.exists(ds)) || (t25!=null && t25.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21 +"+"+ t22 +"+"+ t23 +"+"+ t24 +"+"+ t25;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed()) +"+"+ (t22==null ? t22 : t22.toStringDetailed()) +"+"+ (t23==null ? t23 : t23.toStringDetailed()) +"+"+ (t24==null ? t24 : t24.toStringDetailed()) +"+"+ (t25==null ? t25 : t25.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26> left(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, Class<T26> t, Condition on) {
		return new Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26> left(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, __Alias<T26> t, Condition on) {
		return new Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26> right(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, Class<T26> t, Condition on) {
		return new Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26> right(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, __Alias<T26> t, Condition on) {
		return new Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26> inner(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, Class<T26> t, Condition on) {
		return new Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26> inner(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, __Alias<T26> t, Condition on) {
		return new Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26> outer(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, Class<T26> t, Condition on) {
		return new Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26> outer(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, __Alias<T26> t, Condition on) {
		return new Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26> cross(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, Class<T26> t) {
		return new Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26> cross(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, __Alias<T26> t) {
		return new Query26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>(q, t, "cross join", null);
	}

	private static class Query26<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> extends DBQuery<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> {
		final int SIZE = 26;
		Query26(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, final Class<T26> t, String joinType, Condition on) {
			super(J26.class, q, t, joinType, on);
		}
		Query26(final Query<J25<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25>> q, final __Alias<T26> t, String joinType, Condition on) {
			super(J26.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 26 tables.
	 * It contains 26 typed references (t1 to t26) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J26 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public final T22 t22;
		public final T23 t23;
		public final T24 t24;
		public final T25 t25;
		public final T26 t26;
		public J26(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21, final T22 t22, final T23 t23, final T24 t24, final T25 t25, final T26 t26) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
			this.t22 = t22;
			this.t23 = t23;
			this.t24 = t24;
			this.t25 = t25;
			this.t26 = t26;
		}
		@SuppressWarnings("unchecked")
		J26(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
			t22 = (T22) oa[offset+21];
			t23 = (T23) oa[offset+22];
			t24 = (T24) oa[offset+23];
			t25 = (T25) oa[offset+24];
			t26 = (T26) oa[offset+25];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME())+" + "+(t22==null ? null : t22.SCHEMA_NAME())+" + "+(t23==null ? null : t23.SCHEMA_NAME())+" + "+(t24==null ? null : t24.SCHEMA_NAME())+" + "+(t25==null ? null : t25.SCHEMA_NAME())+" + "+(t26==null ? null : t26.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME())+" + "+(t22==null ? null : t22.TABLE_NAME())+" + "+(t23==null ? null : t23.TABLE_NAME())+" + "+(t24==null ? null : t24.TABLE_NAME())+" + "+(t25==null ? null : t25.TABLE_NAME())+" + "+(t26==null ? null : t26.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t22.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t23.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t24.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t25.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t26.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t22.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t23.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t24.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t25.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t26.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t22.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t23.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t24.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t25.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t26.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert()) && (t22!=null && t22.insert()) && (t23!=null && t23.insert()) && (t24!=null && t24.insert()) && (t25!=null && t25.insert()) && (t26!=null && t26.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds)) && (t22!=null && t22.insert(ds)) && (t23!=null && t23.insert(ds)) && (t24!=null && t24.insert(ds)) && (t25!=null && t25.insert(ds)) && (t26!=null && t26.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update()) && (t22!=null && t22.update()) && (t23!=null && t23.update()) && (t24!=null && t24.update()) && (t25!=null && t25.update()) && (t26!=null && t26.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds)) && (t22!=null && t22.update(ds)) && (t23!=null && t23.update(ds)) && (t24!=null && t24.update(ds)) && (t25!=null && t25.update(ds)) && (t26!=null && t26.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete()) && (t22!=null && t22.delete()) && (t23!=null && t23.delete()) && (t24!=null && t24.delete()) && (t25!=null && t25.delete()) && (t26!=null && t26.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds)) && (t22!=null && t22.delete(ds)) && (t23!=null && t23.delete(ds)) && (t24!=null && t24.delete(ds)) && (t25!=null && t25.delete(ds)) && (t26!=null && t26.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save()) && (t22!=null && t22.save()) && (t23!=null && t23.save()) && (t24!=null && t24.save()) && (t25!=null && t25.save()) && (t26!=null && t26.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds)) && (t22!=null && t22.save(ds)) && (t23!=null && t23.save(ds)) && (t24!=null && t24.save(ds)) && (t25!=null && t25.save(ds)) && (t26!=null && t26.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists()) || (t22!=null && t22.exists()) || (t23!=null && t23.exists()) || (t24!=null && t24.exists()) || (t25!=null && t25.exists()) || (t26!=null && t26.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds)) || (t22!=null && t22.exists(ds)) || (t23!=null && t23.exists(ds)) || (t24!=null && t24.exists(ds)) || (t25!=null && t25.exists(ds)) || (t26!=null && t26.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21 +"+"+ t22 +"+"+ t23 +"+"+ t24 +"+"+ t25 +"+"+ t26;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed()) +"+"+ (t22==null ? t22 : t22.toStringDetailed()) +"+"+ (t23==null ? t23 : t23.toStringDetailed()) +"+"+ (t24==null ? t24 : t24.toStringDetailed()) +"+"+ (t25==null ? t25 : t25.toStringDetailed()) +"+"+ (t26==null ? t26 : t26.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27> left(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, Class<T27> t, Condition on) {
		return new Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27> left(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, __Alias<T27> t, Condition on) {
		return new Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27> right(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, Class<T27> t, Condition on) {
		return new Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27> right(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, __Alias<T27> t, Condition on) {
		return new Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27> inner(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, Class<T27> t, Condition on) {
		return new Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27> inner(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, __Alias<T27> t, Condition on) {
		return new Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27> outer(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, Class<T27> t, Condition on) {
		return new Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27> outer(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, __Alias<T27> t, Condition on) {
		return new Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27> cross(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, Class<T27> t) {
		return new Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27> cross(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, __Alias<T27> t) {
		return new Query27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>(q, t, "cross join", null);
	}

	private static class Query27<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> extends DBQuery<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> {
		final int SIZE = 27;
		Query27(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, final Class<T27> t, String joinType, Condition on) {
			super(J27.class, q, t, joinType, on);
		}
		Query27(final Query<J26<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26>> q, final __Alias<T27> t, String joinType, Condition on) {
			super(J27.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 27 tables.
	 * It contains 27 typed references (t1 to t27) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J27 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public final T22 t22;
		public final T23 t23;
		public final T24 t24;
		public final T25 t25;
		public final T26 t26;
		public final T27 t27;
		public J27(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21, final T22 t22, final T23 t23, final T24 t24, final T25 t25, final T26 t26, final T27 t27) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
			this.t22 = t22;
			this.t23 = t23;
			this.t24 = t24;
			this.t25 = t25;
			this.t26 = t26;
			this.t27 = t27;
		}
		@SuppressWarnings("unchecked")
		J27(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
			t22 = (T22) oa[offset+21];
			t23 = (T23) oa[offset+22];
			t24 = (T24) oa[offset+23];
			t25 = (T25) oa[offset+24];
			t26 = (T26) oa[offset+25];
			t27 = (T27) oa[offset+26];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME())+" + "+(t22==null ? null : t22.SCHEMA_NAME())+" + "+(t23==null ? null : t23.SCHEMA_NAME())+" + "+(t24==null ? null : t24.SCHEMA_NAME())+" + "+(t25==null ? null : t25.SCHEMA_NAME())+" + "+(t26==null ? null : t26.SCHEMA_NAME())+" + "+(t27==null ? null : t27.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME())+" + "+(t22==null ? null : t22.TABLE_NAME())+" + "+(t23==null ? null : t23.TABLE_NAME())+" + "+(t24==null ? null : t24.TABLE_NAME())+" + "+(t25==null ? null : t25.TABLE_NAME())+" + "+(t26==null ? null : t26.TABLE_NAME())+" + "+(t27==null ? null : t27.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t22.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t23.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t24.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t25.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t26.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t27.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t22.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t23.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t24.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t25.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t26.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t27.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t22.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t23.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t24.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t25.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t26.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t27.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert()) && (t22!=null && t22.insert()) && (t23!=null && t23.insert()) && (t24!=null && t24.insert()) && (t25!=null && t25.insert()) && (t26!=null && t26.insert()) && (t27!=null && t27.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds)) && (t22!=null && t22.insert(ds)) && (t23!=null && t23.insert(ds)) && (t24!=null && t24.insert(ds)) && (t25!=null && t25.insert(ds)) && (t26!=null && t26.insert(ds)) && (t27!=null && t27.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update()) && (t22!=null && t22.update()) && (t23!=null && t23.update()) && (t24!=null && t24.update()) && (t25!=null && t25.update()) && (t26!=null && t26.update()) && (t27!=null && t27.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds)) && (t22!=null && t22.update(ds)) && (t23!=null && t23.update(ds)) && (t24!=null && t24.update(ds)) && (t25!=null && t25.update(ds)) && (t26!=null && t26.update(ds)) && (t27!=null && t27.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete()) && (t22!=null && t22.delete()) && (t23!=null && t23.delete()) && (t24!=null && t24.delete()) && (t25!=null && t25.delete()) && (t26!=null && t26.delete()) && (t27!=null && t27.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds)) && (t22!=null && t22.delete(ds)) && (t23!=null && t23.delete(ds)) && (t24!=null && t24.delete(ds)) && (t25!=null && t25.delete(ds)) && (t26!=null && t26.delete(ds)) && (t27!=null && t27.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save()) && (t22!=null && t22.save()) && (t23!=null && t23.save()) && (t24!=null && t24.save()) && (t25!=null && t25.save()) && (t26!=null && t26.save()) && (t27!=null && t27.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds)) && (t22!=null && t22.save(ds)) && (t23!=null && t23.save(ds)) && (t24!=null && t24.save(ds)) && (t25!=null && t25.save(ds)) && (t26!=null && t26.save(ds)) && (t27!=null && t27.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists()) || (t22!=null && t22.exists()) || (t23!=null && t23.exists()) || (t24!=null && t24.exists()) || (t25!=null && t25.exists()) || (t26!=null && t26.exists()) || (t27!=null && t27.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds)) || (t22!=null && t22.exists(ds)) || (t23!=null && t23.exists(ds)) || (t24!=null && t24.exists(ds)) || (t25!=null && t25.exists(ds)) || (t26!=null && t26.exists(ds)) || (t27!=null && t27.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21 +"+"+ t22 +"+"+ t23 +"+"+ t24 +"+"+ t25 +"+"+ t26 +"+"+ t27;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed()) +"+"+ (t22==null ? t22 : t22.toStringDetailed()) +"+"+ (t23==null ? t23 : t23.toStringDetailed()) +"+"+ (t24==null ? t24 : t24.toStringDetailed()) +"+"+ (t25==null ? t25 : t25.toStringDetailed()) +"+"+ (t26==null ? t26 : t26.toStringDetailed()) +"+"+ (t27==null ? t27 : t27.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28> left(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, Class<T28> t, Condition on) {
		return new Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28> left(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, __Alias<T28> t, Condition on) {
		return new Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28> right(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, Class<T28> t, Condition on) {
		return new Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28> right(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, __Alias<T28> t, Condition on) {
		return new Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28> inner(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, Class<T28> t, Condition on) {
		return new Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28> inner(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, __Alias<T28> t, Condition on) {
		return new Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28> outer(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, Class<T28> t, Condition on) {
		return new Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28> outer(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, __Alias<T28> t, Condition on) {
		return new Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28> cross(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, Class<T28> t) {
		return new Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28> cross(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, __Alias<T28> t) {
		return new Query28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>(q, t, "cross join", null);
	}

	private static class Query28<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> extends DBQuery<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> {
		final int SIZE = 28;
		Query28(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, final Class<T28> t, String joinType, Condition on) {
			super(J28.class, q, t, joinType, on);
		}
		Query28(final Query<J27<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27>> q, final __Alias<T28> t, String joinType, Condition on) {
			super(J28.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 28 tables.
	 * It contains 28 typed references (t1 to t28) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J28 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public final T22 t22;
		public final T23 t23;
		public final T24 t24;
		public final T25 t25;
		public final T26 t26;
		public final T27 t27;
		public final T28 t28;
		public J28(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21, final T22 t22, final T23 t23, final T24 t24, final T25 t25, final T26 t26, final T27 t27, final T28 t28) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
			this.t22 = t22;
			this.t23 = t23;
			this.t24 = t24;
			this.t25 = t25;
			this.t26 = t26;
			this.t27 = t27;
			this.t28 = t28;
		}
		@SuppressWarnings("unchecked")
		J28(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
			t22 = (T22) oa[offset+21];
			t23 = (T23) oa[offset+22];
			t24 = (T24) oa[offset+23];
			t25 = (T25) oa[offset+24];
			t26 = (T26) oa[offset+25];
			t27 = (T27) oa[offset+26];
			t28 = (T28) oa[offset+27];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME())+" + "+(t22==null ? null : t22.SCHEMA_NAME())+" + "+(t23==null ? null : t23.SCHEMA_NAME())+" + "+(t24==null ? null : t24.SCHEMA_NAME())+" + "+(t25==null ? null : t25.SCHEMA_NAME())+" + "+(t26==null ? null : t26.SCHEMA_NAME())+" + "+(t27==null ? null : t27.SCHEMA_NAME())+" + "+(t28==null ? null : t28.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME())+" + "+(t22==null ? null : t22.TABLE_NAME())+" + "+(t23==null ? null : t23.TABLE_NAME())+" + "+(t24==null ? null : t24.TABLE_NAME())+" + "+(t25==null ? null : t25.TABLE_NAME())+" + "+(t26==null ? null : t26.TABLE_NAME())+" + "+(t27==null ? null : t27.TABLE_NAME())+" + "+(t28==null ? null : t28.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t22.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t23.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t24.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t25.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t26.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t27.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t28.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t22.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t23.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t24.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t25.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t26.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t27.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t28.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t22.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t23.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t24.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t25.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t26.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t27.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t28.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert()) && (t22!=null && t22.insert()) && (t23!=null && t23.insert()) && (t24!=null && t24.insert()) && (t25!=null && t25.insert()) && (t26!=null && t26.insert()) && (t27!=null && t27.insert()) && (t28!=null && t28.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds)) && (t22!=null && t22.insert(ds)) && (t23!=null && t23.insert(ds)) && (t24!=null && t24.insert(ds)) && (t25!=null && t25.insert(ds)) && (t26!=null && t26.insert(ds)) && (t27!=null && t27.insert(ds)) && (t28!=null && t28.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update()) && (t22!=null && t22.update()) && (t23!=null && t23.update()) && (t24!=null && t24.update()) && (t25!=null && t25.update()) && (t26!=null && t26.update()) && (t27!=null && t27.update()) && (t28!=null && t28.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds)) && (t22!=null && t22.update(ds)) && (t23!=null && t23.update(ds)) && (t24!=null && t24.update(ds)) && (t25!=null && t25.update(ds)) && (t26!=null && t26.update(ds)) && (t27!=null && t27.update(ds)) && (t28!=null && t28.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete()) && (t22!=null && t22.delete()) && (t23!=null && t23.delete()) && (t24!=null && t24.delete()) && (t25!=null && t25.delete()) && (t26!=null && t26.delete()) && (t27!=null && t27.delete()) && (t28!=null && t28.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds)) && (t22!=null && t22.delete(ds)) && (t23!=null && t23.delete(ds)) && (t24!=null && t24.delete(ds)) && (t25!=null && t25.delete(ds)) && (t26!=null && t26.delete(ds)) && (t27!=null && t27.delete(ds)) && (t28!=null && t28.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save()) && (t22!=null && t22.save()) && (t23!=null && t23.save()) && (t24!=null && t24.save()) && (t25!=null && t25.save()) && (t26!=null && t26.save()) && (t27!=null && t27.save()) && (t28!=null && t28.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds)) && (t22!=null && t22.save(ds)) && (t23!=null && t23.save(ds)) && (t24!=null && t24.save(ds)) && (t25!=null && t25.save(ds)) && (t26!=null && t26.save(ds)) && (t27!=null && t27.save(ds)) && (t28!=null && t28.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists()) || (t22!=null && t22.exists()) || (t23!=null && t23.exists()) || (t24!=null && t24.exists()) || (t25!=null && t25.exists()) || (t26!=null && t26.exists()) || (t27!=null && t27.exists()) || (t28!=null && t28.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds)) || (t22!=null && t22.exists(ds)) || (t23!=null && t23.exists(ds)) || (t24!=null && t24.exists(ds)) || (t25!=null && t25.exists(ds)) || (t26!=null && t26.exists(ds)) || (t27!=null && t27.exists(ds)) || (t28!=null && t28.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21 +"+"+ t22 +"+"+ t23 +"+"+ t24 +"+"+ t25 +"+"+ t26 +"+"+ t27 +"+"+ t28;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed()) +"+"+ (t22==null ? t22 : t22.toStringDetailed()) +"+"+ (t23==null ? t23 : t23.toStringDetailed()) +"+"+ (t24==null ? t24 : t24.toStringDetailed()) +"+"+ (t25==null ? t25 : t25.toStringDetailed()) +"+"+ (t26==null ? t26 : t26.toStringDetailed()) +"+"+ (t27==null ? t27 : t27.toStringDetailed()) +"+"+ (t28==null ? t28 : t28.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29> left(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, Class<T29> t, Condition on) {
		return new Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29> left(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, __Alias<T29> t, Condition on) {
		return new Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29> right(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, Class<T29> t, Condition on) {
		return new Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29> right(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, __Alias<T29> t, Condition on) {
		return new Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29> inner(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, Class<T29> t, Condition on) {
		return new Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29> inner(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, __Alias<T29> t, Condition on) {
		return new Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29> outer(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, Class<T29> t, Condition on) {
		return new Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29> outer(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, __Alias<T29> t, Condition on) {
		return new Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29> cross(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, Class<T29> t) {
		return new Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29> cross(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, __Alias<T29> t) {
		return new Query29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>(q, t, "cross join", null);
	}

	private static class Query29<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> extends DBQuery<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> {
		final int SIZE = 29;
		Query29(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, final Class<T29> t, String joinType, Condition on) {
			super(J29.class, q, t, joinType, on);
		}
		Query29(final Query<J28<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28>> q, final __Alias<T29> t, String joinType, Condition on) {
			super(J29.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 29 tables.
	 * It contains 29 typed references (t1 to t29) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J29 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public final T22 t22;
		public final T23 t23;
		public final T24 t24;
		public final T25 t25;
		public final T26 t26;
		public final T27 t27;
		public final T28 t28;
		public final T29 t29;
		public J29(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21, final T22 t22, final T23 t23, final T24 t24, final T25 t25, final T26 t26, final T27 t27, final T28 t28, final T29 t29) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
			this.t22 = t22;
			this.t23 = t23;
			this.t24 = t24;
			this.t25 = t25;
			this.t26 = t26;
			this.t27 = t27;
			this.t28 = t28;
			this.t29 = t29;
		}
		@SuppressWarnings("unchecked")
		J29(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
			t22 = (T22) oa[offset+21];
			t23 = (T23) oa[offset+22];
			t24 = (T24) oa[offset+23];
			t25 = (T25) oa[offset+24];
			t26 = (T26) oa[offset+25];
			t27 = (T27) oa[offset+26];
			t28 = (T28) oa[offset+27];
			t29 = (T29) oa[offset+28];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME())+" + "+(t22==null ? null : t22.SCHEMA_NAME())+" + "+(t23==null ? null : t23.SCHEMA_NAME())+" + "+(t24==null ? null : t24.SCHEMA_NAME())+" + "+(t25==null ? null : t25.SCHEMA_NAME())+" + "+(t26==null ? null : t26.SCHEMA_NAME())+" + "+(t27==null ? null : t27.SCHEMA_NAME())+" + "+(t28==null ? null : t28.SCHEMA_NAME())+" + "+(t29==null ? null : t29.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME())+" + "+(t22==null ? null : t22.TABLE_NAME())+" + "+(t23==null ? null : t23.TABLE_NAME())+" + "+(t24==null ? null : t24.TABLE_NAME())+" + "+(t25==null ? null : t25.TABLE_NAME())+" + "+(t26==null ? null : t26.TABLE_NAME())+" + "+(t27==null ? null : t27.TABLE_NAME())+" + "+(t28==null ? null : t28.TABLE_NAME())+" + "+(t29==null ? null : t29.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t22.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t23.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t24.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t25.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t26.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t27.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t28.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t29.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t22.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t23.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t24.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t25.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t26.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t27.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t28.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t29.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t22.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t23.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t24.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t25.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t26.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t27.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t28.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t29.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert()) && (t22!=null && t22.insert()) && (t23!=null && t23.insert()) && (t24!=null && t24.insert()) && (t25!=null && t25.insert()) && (t26!=null && t26.insert()) && (t27!=null && t27.insert()) && (t28!=null && t28.insert()) && (t29!=null && t29.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds)) && (t22!=null && t22.insert(ds)) && (t23!=null && t23.insert(ds)) && (t24!=null && t24.insert(ds)) && (t25!=null && t25.insert(ds)) && (t26!=null && t26.insert(ds)) && (t27!=null && t27.insert(ds)) && (t28!=null && t28.insert(ds)) && (t29!=null && t29.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update()) && (t22!=null && t22.update()) && (t23!=null && t23.update()) && (t24!=null && t24.update()) && (t25!=null && t25.update()) && (t26!=null && t26.update()) && (t27!=null && t27.update()) && (t28!=null && t28.update()) && (t29!=null && t29.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds)) && (t22!=null && t22.update(ds)) && (t23!=null && t23.update(ds)) && (t24!=null && t24.update(ds)) && (t25!=null && t25.update(ds)) && (t26!=null && t26.update(ds)) && (t27!=null && t27.update(ds)) && (t28!=null && t28.update(ds)) && (t29!=null && t29.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete()) && (t22!=null && t22.delete()) && (t23!=null && t23.delete()) && (t24!=null && t24.delete()) && (t25!=null && t25.delete()) && (t26!=null && t26.delete()) && (t27!=null && t27.delete()) && (t28!=null && t28.delete()) && (t29!=null && t29.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds)) && (t22!=null && t22.delete(ds)) && (t23!=null && t23.delete(ds)) && (t24!=null && t24.delete(ds)) && (t25!=null && t25.delete(ds)) && (t26!=null && t26.delete(ds)) && (t27!=null && t27.delete(ds)) && (t28!=null && t28.delete(ds)) && (t29!=null && t29.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save()) && (t22!=null && t22.save()) && (t23!=null && t23.save()) && (t24!=null && t24.save()) && (t25!=null && t25.save()) && (t26!=null && t26.save()) && (t27!=null && t27.save()) && (t28!=null && t28.save()) && (t29!=null && t29.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds)) && (t22!=null && t22.save(ds)) && (t23!=null && t23.save(ds)) && (t24!=null && t24.save(ds)) && (t25!=null && t25.save(ds)) && (t26!=null && t26.save(ds)) && (t27!=null && t27.save(ds)) && (t28!=null && t28.save(ds)) && (t29!=null && t29.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists()) || (t22!=null && t22.exists()) || (t23!=null && t23.exists()) || (t24!=null && t24.exists()) || (t25!=null && t25.exists()) || (t26!=null && t26.exists()) || (t27!=null && t27.exists()) || (t28!=null && t28.exists()) || (t29!=null && t29.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds)) || (t22!=null && t22.exists(ds)) || (t23!=null && t23.exists(ds)) || (t24!=null && t24.exists(ds)) || (t25!=null && t25.exists(ds)) || (t26!=null && t26.exists(ds)) || (t27!=null && t27.exists(ds)) || (t28!=null && t28.exists(ds)) || (t29!=null && t29.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21 +"+"+ t22 +"+"+ t23 +"+"+ t24 +"+"+ t25 +"+"+ t26 +"+"+ t27 +"+"+ t28 +"+"+ t29;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed()) +"+"+ (t22==null ? t22 : t22.toStringDetailed()) +"+"+ (t23==null ? t23 : t23.toStringDetailed()) +"+"+ (t24==null ? t24 : t24.toStringDetailed()) +"+"+ (t25==null ? t25 : t25.toStringDetailed()) +"+"+ (t26==null ? t26 : t26.toStringDetailed()) +"+"+ (t27==null ? t27 : t27.toStringDetailed()) +"+"+ (t28==null ? t28 : t28.toStringDetailed()) +"+"+ (t29==null ? t29 : t29.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30> left(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, Class<T30> t, Condition on) {
		return new Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30> left(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, __Alias<T30> t, Condition on) {
		return new Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30> right(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, Class<T30> t, Condition on) {
		return new Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30> right(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, __Alias<T30> t, Condition on) {
		return new Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30> inner(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, Class<T30> t, Condition on) {
		return new Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30> inner(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, __Alias<T30> t, Condition on) {
		return new Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30> outer(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, Class<T30> t, Condition on) {
		return new Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30> outer(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, __Alias<T30> t, Condition on) {
		return new Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30> cross(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, Class<T30> t) {
		return new Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30> cross(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, __Alias<T30> t) {
		return new Query30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>(q, t, "cross join", null);
	}

	private static class Query30<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> extends DBQuery<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> {
		final int SIZE = 30;
		Query30(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, final Class<T30> t, String joinType, Condition on) {
			super(J30.class, q, t, joinType, on);
		}
		Query30(final Query<J29<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29>> q, final __Alias<T30> t, String joinType, Condition on) {
			super(J30.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 30 tables.
	 * It contains 30 typed references (t1 to t30) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J30 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public final T22 t22;
		public final T23 t23;
		public final T24 t24;
		public final T25 t25;
		public final T26 t26;
		public final T27 t27;
		public final T28 t28;
		public final T29 t29;
		public final T30 t30;
		public J30(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21, final T22 t22, final T23 t23, final T24 t24, final T25 t25, final T26 t26, final T27 t27, final T28 t28, final T29 t29, final T30 t30) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
			this.t22 = t22;
			this.t23 = t23;
			this.t24 = t24;
			this.t25 = t25;
			this.t26 = t26;
			this.t27 = t27;
			this.t28 = t28;
			this.t29 = t29;
			this.t30 = t30;
		}
		@SuppressWarnings("unchecked")
		J30(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
			t22 = (T22) oa[offset+21];
			t23 = (T23) oa[offset+22];
			t24 = (T24) oa[offset+23];
			t25 = (T25) oa[offset+24];
			t26 = (T26) oa[offset+25];
			t27 = (T27) oa[offset+26];
			t28 = (T28) oa[offset+27];
			t29 = (T29) oa[offset+28];
			t30 = (T30) oa[offset+29];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME())+" + "+(t22==null ? null : t22.SCHEMA_NAME())+" + "+(t23==null ? null : t23.SCHEMA_NAME())+" + "+(t24==null ? null : t24.SCHEMA_NAME())+" + "+(t25==null ? null : t25.SCHEMA_NAME())+" + "+(t26==null ? null : t26.SCHEMA_NAME())+" + "+(t27==null ? null : t27.SCHEMA_NAME())+" + "+(t28==null ? null : t28.SCHEMA_NAME())+" + "+(t29==null ? null : t29.SCHEMA_NAME())+" + "+(t30==null ? null : t30.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME())+" + "+(t22==null ? null : t22.TABLE_NAME())+" + "+(t23==null ? null : t23.TABLE_NAME())+" + "+(t24==null ? null : t24.TABLE_NAME())+" + "+(t25==null ? null : t25.TABLE_NAME())+" + "+(t26==null ? null : t26.TABLE_NAME())+" + "+(t27==null ? null : t27.TABLE_NAME())+" + "+(t28==null ? null : t28.TABLE_NAME())+" + "+(t29==null ? null : t29.TABLE_NAME())+" + "+(t30==null ? null : t30.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t22.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t23.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t24.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t25.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t26.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t27.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t28.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t29.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t30.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t22.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t23.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t24.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t25.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t26.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t27.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t28.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t29.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t30.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t22.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t23.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t24.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t25.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t26.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t27.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t28.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t29.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t30.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert()) && (t22!=null && t22.insert()) && (t23!=null && t23.insert()) && (t24!=null && t24.insert()) && (t25!=null && t25.insert()) && (t26!=null && t26.insert()) && (t27!=null && t27.insert()) && (t28!=null && t28.insert()) && (t29!=null && t29.insert()) && (t30!=null && t30.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds)) && (t22!=null && t22.insert(ds)) && (t23!=null && t23.insert(ds)) && (t24!=null && t24.insert(ds)) && (t25!=null && t25.insert(ds)) && (t26!=null && t26.insert(ds)) && (t27!=null && t27.insert(ds)) && (t28!=null && t28.insert(ds)) && (t29!=null && t29.insert(ds)) && (t30!=null && t30.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update()) && (t22!=null && t22.update()) && (t23!=null && t23.update()) && (t24!=null && t24.update()) && (t25!=null && t25.update()) && (t26!=null && t26.update()) && (t27!=null && t27.update()) && (t28!=null && t28.update()) && (t29!=null && t29.update()) && (t30!=null && t30.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds)) && (t22!=null && t22.update(ds)) && (t23!=null && t23.update(ds)) && (t24!=null && t24.update(ds)) && (t25!=null && t25.update(ds)) && (t26!=null && t26.update(ds)) && (t27!=null && t27.update(ds)) && (t28!=null && t28.update(ds)) && (t29!=null && t29.update(ds)) && (t30!=null && t30.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete()) && (t22!=null && t22.delete()) && (t23!=null && t23.delete()) && (t24!=null && t24.delete()) && (t25!=null && t25.delete()) && (t26!=null && t26.delete()) && (t27!=null && t27.delete()) && (t28!=null && t28.delete()) && (t29!=null && t29.delete()) && (t30!=null && t30.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds)) && (t22!=null && t22.delete(ds)) && (t23!=null && t23.delete(ds)) && (t24!=null && t24.delete(ds)) && (t25!=null && t25.delete(ds)) && (t26!=null && t26.delete(ds)) && (t27!=null && t27.delete(ds)) && (t28!=null && t28.delete(ds)) && (t29!=null && t29.delete(ds)) && (t30!=null && t30.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save()) && (t22!=null && t22.save()) && (t23!=null && t23.save()) && (t24!=null && t24.save()) && (t25!=null && t25.save()) && (t26!=null && t26.save()) && (t27!=null && t27.save()) && (t28!=null && t28.save()) && (t29!=null && t29.save()) && (t30!=null && t30.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds)) && (t22!=null && t22.save(ds)) && (t23!=null && t23.save(ds)) && (t24!=null && t24.save(ds)) && (t25!=null && t25.save(ds)) && (t26!=null && t26.save(ds)) && (t27!=null && t27.save(ds)) && (t28!=null && t28.save(ds)) && (t29!=null && t29.save(ds)) && (t30!=null && t30.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists()) || (t22!=null && t22.exists()) || (t23!=null && t23.exists()) || (t24!=null && t24.exists()) || (t25!=null && t25.exists()) || (t26!=null && t26.exists()) || (t27!=null && t27.exists()) || (t28!=null && t28.exists()) || (t29!=null && t29.exists()) || (t30!=null && t30.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds)) || (t22!=null && t22.exists(ds)) || (t23!=null && t23.exists(ds)) || (t24!=null && t24.exists(ds)) || (t25!=null && t25.exists(ds)) || (t26!=null && t26.exists(ds)) || (t27!=null && t27.exists(ds)) || (t28!=null && t28.exists(ds)) || (t29!=null && t29.exists(ds)) || (t30!=null && t30.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21 +"+"+ t22 +"+"+ t23 +"+"+ t24 +"+"+ t25 +"+"+ t26 +"+"+ t27 +"+"+ t28 +"+"+ t29 +"+"+ t30;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed()) +"+"+ (t22==null ? t22 : t22.toStringDetailed()) +"+"+ (t23==null ? t23 : t23.toStringDetailed()) +"+"+ (t24==null ? t24 : t24.toStringDetailed()) +"+"+ (t25==null ? t25 : t25.toStringDetailed()) +"+"+ (t26==null ? t26 : t26.toStringDetailed()) +"+"+ (t27==null ? t27 : t27.toStringDetailed()) +"+"+ (t28==null ? t28 : t28.toStringDetailed()) +"+"+ (t29==null ? t29 : t29.toStringDetailed()) +"+"+ (t30==null ? t30 : t30.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31> left(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, Class<T31> t, Condition on) {
		return new Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31> left(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, __Alias<T31> t, Condition on) {
		return new Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31> right(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, Class<T31> t, Condition on) {
		return new Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31> right(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, __Alias<T31> t, Condition on) {
		return new Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31> inner(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, Class<T31> t, Condition on) {
		return new Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31> inner(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, __Alias<T31> t, Condition on) {
		return new Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31> outer(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, Class<T31> t, Condition on) {
		return new Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31> outer(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, __Alias<T31> t, Condition on) {
		return new Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31> cross(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, Class<T31> t) {
		return new Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31> cross(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, __Alias<T31> t) {
		return new Query31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>(q, t, "cross join", null);
	}

	private static class Query31<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> extends DBQuery<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> {
		final int SIZE = 31;
		Query31(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, final Class<T31> t, String joinType, Condition on) {
			super(J31.class, q, t, joinType, on);
		}
		Query31(final Query<J30<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30>> q, final __Alias<T31> t, String joinType, Condition on) {
			super(J31.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 31 tables.
	 * It contains 31 typed references (t1 to t31) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J31 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public final T22 t22;
		public final T23 t23;
		public final T24 t24;
		public final T25 t25;
		public final T26 t26;
		public final T27 t27;
		public final T28 t28;
		public final T29 t29;
		public final T30 t30;
		public final T31 t31;
		public J31(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21, final T22 t22, final T23 t23, final T24 t24, final T25 t25, final T26 t26, final T27 t27, final T28 t28, final T29 t29, final T30 t30, final T31 t31) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
			this.t22 = t22;
			this.t23 = t23;
			this.t24 = t24;
			this.t25 = t25;
			this.t26 = t26;
			this.t27 = t27;
			this.t28 = t28;
			this.t29 = t29;
			this.t30 = t30;
			this.t31 = t31;
		}
		@SuppressWarnings("unchecked")
		J31(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
			t22 = (T22) oa[offset+21];
			t23 = (T23) oa[offset+22];
			t24 = (T24) oa[offset+23];
			t25 = (T25) oa[offset+24];
			t26 = (T26) oa[offset+25];
			t27 = (T27) oa[offset+26];
			t28 = (T28) oa[offset+27];
			t29 = (T29) oa[offset+28];
			t30 = (T30) oa[offset+29];
			t31 = (T31) oa[offset+30];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME())+" + "+(t22==null ? null : t22.SCHEMA_NAME())+" + "+(t23==null ? null : t23.SCHEMA_NAME())+" + "+(t24==null ? null : t24.SCHEMA_NAME())+" + "+(t25==null ? null : t25.SCHEMA_NAME())+" + "+(t26==null ? null : t26.SCHEMA_NAME())+" + "+(t27==null ? null : t27.SCHEMA_NAME())+" + "+(t28==null ? null : t28.SCHEMA_NAME())+" + "+(t29==null ? null : t29.SCHEMA_NAME())+" + "+(t30==null ? null : t30.SCHEMA_NAME())+" + "+(t31==null ? null : t31.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME())+" + "+(t22==null ? null : t22.TABLE_NAME())+" + "+(t23==null ? null : t23.TABLE_NAME())+" + "+(t24==null ? null : t24.TABLE_NAME())+" + "+(t25==null ? null : t25.TABLE_NAME())+" + "+(t26==null ? null : t26.TABLE_NAME())+" + "+(t27==null ? null : t27.TABLE_NAME())+" + "+(t28==null ? null : t28.TABLE_NAME())+" + "+(t29==null ? null : t29.TABLE_NAME())+" + "+(t30==null ? null : t30.TABLE_NAME())+" + "+(t31==null ? null : t31.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t22.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t23.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t24.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t25.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t26.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t27.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t28.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t29.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t30.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t31.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t22.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t23.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t24.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t25.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t26.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t27.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t28.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t29.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t30.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t31.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t22.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t23.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t24.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t25.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t26.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t27.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t28.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t29.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t30.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t31.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert()) && (t22!=null && t22.insert()) && (t23!=null && t23.insert()) && (t24!=null && t24.insert()) && (t25!=null && t25.insert()) && (t26!=null && t26.insert()) && (t27!=null && t27.insert()) && (t28!=null && t28.insert()) && (t29!=null && t29.insert()) && (t30!=null && t30.insert()) && (t31!=null && t31.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds)) && (t22!=null && t22.insert(ds)) && (t23!=null && t23.insert(ds)) && (t24!=null && t24.insert(ds)) && (t25!=null && t25.insert(ds)) && (t26!=null && t26.insert(ds)) && (t27!=null && t27.insert(ds)) && (t28!=null && t28.insert(ds)) && (t29!=null && t29.insert(ds)) && (t30!=null && t30.insert(ds)) && (t31!=null && t31.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update()) && (t22!=null && t22.update()) && (t23!=null && t23.update()) && (t24!=null && t24.update()) && (t25!=null && t25.update()) && (t26!=null && t26.update()) && (t27!=null && t27.update()) && (t28!=null && t28.update()) && (t29!=null && t29.update()) && (t30!=null && t30.update()) && (t31!=null && t31.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds)) && (t22!=null && t22.update(ds)) && (t23!=null && t23.update(ds)) && (t24!=null && t24.update(ds)) && (t25!=null && t25.update(ds)) && (t26!=null && t26.update(ds)) && (t27!=null && t27.update(ds)) && (t28!=null && t28.update(ds)) && (t29!=null && t29.update(ds)) && (t30!=null && t30.update(ds)) && (t31!=null && t31.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete()) && (t22!=null && t22.delete()) && (t23!=null && t23.delete()) && (t24!=null && t24.delete()) && (t25!=null && t25.delete()) && (t26!=null && t26.delete()) && (t27!=null && t27.delete()) && (t28!=null && t28.delete()) && (t29!=null && t29.delete()) && (t30!=null && t30.delete()) && (t31!=null && t31.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds)) && (t22!=null && t22.delete(ds)) && (t23!=null && t23.delete(ds)) && (t24!=null && t24.delete(ds)) && (t25!=null && t25.delete(ds)) && (t26!=null && t26.delete(ds)) && (t27!=null && t27.delete(ds)) && (t28!=null && t28.delete(ds)) && (t29!=null && t29.delete(ds)) && (t30!=null && t30.delete(ds)) && (t31!=null && t31.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save()) && (t22!=null && t22.save()) && (t23!=null && t23.save()) && (t24!=null && t24.save()) && (t25!=null && t25.save()) && (t26!=null && t26.save()) && (t27!=null && t27.save()) && (t28!=null && t28.save()) && (t29!=null && t29.save()) && (t30!=null && t30.save()) && (t31!=null && t31.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds)) && (t22!=null && t22.save(ds)) && (t23!=null && t23.save(ds)) && (t24!=null && t24.save(ds)) && (t25!=null && t25.save(ds)) && (t26!=null && t26.save(ds)) && (t27!=null && t27.save(ds)) && (t28!=null && t28.save(ds)) && (t29!=null && t29.save(ds)) && (t30!=null && t30.save(ds)) && (t31!=null && t31.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists()) || (t22!=null && t22.exists()) || (t23!=null && t23.exists()) || (t24!=null && t24.exists()) || (t25!=null && t25.exists()) || (t26!=null && t26.exists()) || (t27!=null && t27.exists()) || (t28!=null && t28.exists()) || (t29!=null && t29.exists()) || (t30!=null && t30.exists()) || (t31!=null && t31.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds)) || (t22!=null && t22.exists(ds)) || (t23!=null && t23.exists(ds)) || (t24!=null && t24.exists(ds)) || (t25!=null && t25.exists(ds)) || (t26!=null && t26.exists(ds)) || (t27!=null && t27.exists(ds)) || (t28!=null && t28.exists(ds)) || (t29!=null && t29.exists(ds)) || (t30!=null && t30.exists(ds)) || (t31!=null && t31.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21 +"+"+ t22 +"+"+ t23 +"+"+ t24 +"+"+ t25 +"+"+ t26 +"+"+ t27 +"+"+ t28 +"+"+ t29 +"+"+ t30 +"+"+ t31;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed()) +"+"+ (t22==null ? t22 : t22.toStringDetailed()) +"+"+ (t23==null ? t23 : t23.toStringDetailed()) +"+"+ (t24==null ? t24 : t24.toStringDetailed()) +"+"+ (t25==null ? t25 : t25.toStringDetailed()) +"+"+ (t26==null ? t26 : t26.toStringDetailed()) +"+"+ (t27==null ? t27 : t27.toStringDetailed()) +"+"+ (t28==null ? t28 : t28.toStringDetailed()) +"+"+ (t29==null ? t29 : t29.toStringDetailed()) +"+"+ (t30==null ? t30 : t30.toStringDetailed()) +"+"+ (t31==null ? t31 : t31.toStringDetailed());
		}
	}

	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32> left(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, Class<T32> t, Condition on) {
		return new Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32> left(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, __Alias<T32> t, Condition on) {
		return new Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>(q, t, "left join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32> right(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, Class<T32> t, Condition on) {
		return new Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32> right(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, __Alias<T32> t, Condition on) {
		return new Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>(q, t, "right join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32> inner(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, Class<T32> t, Condition on) {
		return new Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32> inner(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, __Alias<T32> t, Condition on) {
		return new Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>(q, t, "inner join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32> outer(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, Class<T32> t, Condition on) {
		return new Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32> outer(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, __Alias<T32> t, Condition on) {
		return new Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>(q, t, "outer join", on);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32> cross(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, Class<T32> t) {
		return new Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>(q, t, "cross join", null);
	}
	/** 
	 * Joins types T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32 into one query.
	 * The return is a private type (to avoid type erasure conflicts), but you should use
	 * it as a {@code org.kered.dko.Query<Join.J32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>>}
	 */
	public static <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32> cross(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, __Alias<T32> t) {
		return new Query32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>(q, t, "cross join", null);
	}

	private static class Query32<T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> extends DBQuery<J32<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31, T32>> {
		final int SIZE = 32;
		Query32(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, final Class<T32> t, String joinType, Condition on) {
			super(J32.class, q, t, joinType, on);
		}
		Query32(final Query<J31<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23, T24, T25, T26, T27, T28, T29, T30, T31>> q, final __Alias<T32> t, String joinType, Condition on) {
			super(J32.class, q, t, joinType, on);
		}
	}

	/**
	 * This class represents a join across 32 tables.
	 * It contains 32 typed references (t1 to t32) to the join row components.
	 * (each of them containing all the columns they contributed to the join)
	 */
	public static class J32 <T1 extends Table, T2 extends Table, T3 extends Table, T4 extends Table, T5 extends Table, T6 extends Table, T7 extends Table, T8 extends Table, T9 extends Table, T10 extends Table, T11 extends Table, T12 extends Table, T13 extends Table, T14 extends Table, T15 extends Table, T16 extends Table, T17 extends Table, T18 extends Table, T19 extends Table, T20 extends Table, T21 extends Table, T22 extends Table, T23 extends Table, T24 extends Table, T25 extends Table, T26 extends Table, T27 extends Table, T28 extends Table, T29 extends Table, T30 extends Table, T31 extends Table, T32 extends Table> extends J {
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
		public final T17 t17;
		public final T18 t18;
		public final T19 t19;
		public final T20 t20;
		public final T21 t21;
		public final T22 t22;
		public final T23 t23;
		public final T24 t24;
		public final T25 t25;
		public final T26 t26;
		public final T27 t27;
		public final T28 t28;
		public final T29 t29;
		public final T30 t30;
		public final T31 t31;
		public final T32 t32;
		public J32(final T1 t1, final T2 t2, final T3 t3, final T4 t4, final T5 t5, final T6 t6, final T7 t7, final T8 t8, final T9 t9, final T10 t10, final T11 t11, final T12 t12, final T13 t13, final T14 t14, final T15 t15, final T16 t16, final T17 t17, final T18 t18, final T19 t19, final T20 t20, final T21 t21, final T22 t22, final T23 t23, final T24 t24, final T25 t25, final T26 t26, final T27 t27, final T28 t28, final T29 t29, final T30 t30, final T31 t31, final T32 t32) {
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
			this.t17 = t17;
			this.t18 = t18;
			this.t19 = t19;
			this.t20 = t20;
			this.t21 = t21;
			this.t22 = t22;
			this.t23 = t23;
			this.t24 = t24;
			this.t25 = t25;
			this.t26 = t26;
			this.t27 = t27;
			this.t28 = t28;
			this.t29 = t29;
			this.t30 = t30;
			this.t31 = t31;
			this.t32 = t32;
		}
		@SuppressWarnings("unchecked")
		J32(final Object[] oa, final int offset) {
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
			t17 = (T17) oa[offset+16];
			t18 = (T18) oa[offset+17];
			t19 = (T19) oa[offset+18];
			t20 = (T20) oa[offset+19];
			t21 = (T21) oa[offset+20];
			t22 = (T22) oa[offset+21];
			t23 = (T23) oa[offset+22];
			t24 = (T24) oa[offset+23];
			t25 = (T25) oa[offset+24];
			t26 = (T26) oa[offset+25];
			t27 = (T27) oa[offset+26];
			t28 = (T28) oa[offset+27];
			t29 = (T29) oa[offset+28];
			t30 = (T30) oa[offset+29];
			t31 = (T31) oa[offset+30];
			t32 = (T32) oa[offset+31];
		}
		@Override
		protected String SCHEMA_NAME() {
			return (t1==null ? null : t1.SCHEMA_NAME())+" + "+(t2==null ? null : t2.SCHEMA_NAME())+" + "+(t3==null ? null : t3.SCHEMA_NAME())+" + "+(t4==null ? null : t4.SCHEMA_NAME())+" + "+(t5==null ? null : t5.SCHEMA_NAME())+" + "+(t6==null ? null : t6.SCHEMA_NAME())+" + "+(t7==null ? null : t7.SCHEMA_NAME())+" + "+(t8==null ? null : t8.SCHEMA_NAME())+" + "+(t9==null ? null : t9.SCHEMA_NAME())+" + "+(t10==null ? null : t10.SCHEMA_NAME())+" + "+(t11==null ? null : t11.SCHEMA_NAME())+" + "+(t12==null ? null : t12.SCHEMA_NAME())+" + "+(t13==null ? null : t13.SCHEMA_NAME())+" + "+(t14==null ? null : t14.SCHEMA_NAME())+" + "+(t15==null ? null : t15.SCHEMA_NAME())+" + "+(t16==null ? null : t16.SCHEMA_NAME())+" + "+(t17==null ? null : t17.SCHEMA_NAME())+" + "+(t18==null ? null : t18.SCHEMA_NAME())+" + "+(t19==null ? null : t19.SCHEMA_NAME())+" + "+(t20==null ? null : t20.SCHEMA_NAME())+" + "+(t21==null ? null : t21.SCHEMA_NAME())+" + "+(t22==null ? null : t22.SCHEMA_NAME())+" + "+(t23==null ? null : t23.SCHEMA_NAME())+" + "+(t24==null ? null : t24.SCHEMA_NAME())+" + "+(t25==null ? null : t25.SCHEMA_NAME())+" + "+(t26==null ? null : t26.SCHEMA_NAME())+" + "+(t27==null ? null : t27.SCHEMA_NAME())+" + "+(t28==null ? null : t28.SCHEMA_NAME())+" + "+(t29==null ? null : t29.SCHEMA_NAME())+" + "+(t30==null ? null : t30.SCHEMA_NAME())+" + "+(t31==null ? null : t31.SCHEMA_NAME())+" + "+(t32==null ? null : t32.SCHEMA_NAME());
		}
		@Override
		protected String TABLE_NAME() {
			return (t1==null ? null : t1.TABLE_NAME())+" + "+(t2==null ? null : t2.TABLE_NAME())+" + "+(t3==null ? null : t3.TABLE_NAME())+" + "+(t4==null ? null : t4.TABLE_NAME())+" + "+(t5==null ? null : t5.TABLE_NAME())+" + "+(t6==null ? null : t6.TABLE_NAME())+" + "+(t7==null ? null : t7.TABLE_NAME())+" + "+(t8==null ? null : t8.TABLE_NAME())+" + "+(t9==null ? null : t9.TABLE_NAME())+" + "+(t10==null ? null : t10.TABLE_NAME())+" + "+(t11==null ? null : t11.TABLE_NAME())+" + "+(t12==null ? null : t12.TABLE_NAME())+" + "+(t13==null ? null : t13.TABLE_NAME())+" + "+(t14==null ? null : t14.TABLE_NAME())+" + "+(t15==null ? null : t15.TABLE_NAME())+" + "+(t16==null ? null : t16.TABLE_NAME())+" + "+(t17==null ? null : t17.TABLE_NAME())+" + "+(t18==null ? null : t18.TABLE_NAME())+" + "+(t19==null ? null : t19.TABLE_NAME())+" + "+(t20==null ? null : t20.TABLE_NAME())+" + "+(t21==null ? null : t21.TABLE_NAME())+" + "+(t22==null ? null : t22.TABLE_NAME())+" + "+(t23==null ? null : t23.TABLE_NAME())+" + "+(t24==null ? null : t24.TABLE_NAME())+" + "+(t25==null ? null : t25.TABLE_NAME())+" + "+(t26==null ? null : t26.TABLE_NAME())+" + "+(t27==null ? null : t27.TABLE_NAME())+" + "+(t28==null ? null : t28.TABLE_NAME())+" + "+(t29==null ? null : t29.TABLE_NAME())+" + "+(t30==null ? null : t30.TABLE_NAME())+" + "+(t31==null ? null : t31.TABLE_NAME())+" + "+(t32==null ? null : t32.TABLE_NAME());
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
				__NOSCO_PRIVATE_FIELDS.addAll(t17.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t18.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t19.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t20.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t21.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t22.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t23.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t24.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t25.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t26.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t27.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t28.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t29.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t30.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t31.FIELDS());
				__NOSCO_PRIVATE_FIELDS.addAll(t32.FIELDS());
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
			try { return t17.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t18.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t19.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t20.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t21.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t22.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t23.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t24.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t25.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t26.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t27.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t28.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t29.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t30.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t31.get(field); }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { return t32.get(field); }
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
			try { t17.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t18.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t19.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t20.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t21.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t22.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t23.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t24.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t25.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t26.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t27.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t28.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t29.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t30.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t31.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			try { t32.set(field, value); return; }
			catch (final IllegalArgumentException e) { /* ignore */ }
			throw new IllegalArgumentException("unknown field "+ field);
		}
		@Override
		public boolean insert() throws SQLException {
			return (t1!=null && t1.insert()) && (t2!=null && t2.insert()) && (t3!=null && t3.insert()) && (t4!=null && t4.insert()) && (t5!=null && t5.insert()) && (t6!=null && t6.insert()) && (t7!=null && t7.insert()) && (t8!=null && t8.insert()) && (t9!=null && t9.insert()) && (t10!=null && t10.insert()) && (t11!=null && t11.insert()) && (t12!=null && t12.insert()) && (t13!=null && t13.insert()) && (t14!=null && t14.insert()) && (t15!=null && t15.insert()) && (t16!=null && t16.insert()) && (t17!=null && t17.insert()) && (t18!=null && t18.insert()) && (t19!=null && t19.insert()) && (t20!=null && t20.insert()) && (t21!=null && t21.insert()) && (t22!=null && t22.insert()) && (t23!=null && t23.insert()) && (t24!=null && t24.insert()) && (t25!=null && t25.insert()) && (t26!=null && t26.insert()) && (t27!=null && t27.insert()) && (t28!=null && t28.insert()) && (t29!=null && t29.insert()) && (t30!=null && t30.insert()) && (t31!=null && t31.insert()) && (t32!=null && t32.insert());
		}
		@Override
		public boolean insert(DataSource ds) throws SQLException {
			return (t1!=null && t1.insert(ds)) && (t2!=null && t2.insert(ds)) && (t3!=null && t3.insert(ds)) && (t4!=null && t4.insert(ds)) && (t5!=null && t5.insert(ds)) && (t6!=null && t6.insert(ds)) && (t7!=null && t7.insert(ds)) && (t8!=null && t8.insert(ds)) && (t9!=null && t9.insert(ds)) && (t10!=null && t10.insert(ds)) && (t11!=null && t11.insert(ds)) && (t12!=null && t12.insert(ds)) && (t13!=null && t13.insert(ds)) && (t14!=null && t14.insert(ds)) && (t15!=null && t15.insert(ds)) && (t16!=null && t16.insert(ds)) && (t17!=null && t17.insert(ds)) && (t18!=null && t18.insert(ds)) && (t19!=null && t19.insert(ds)) && (t20!=null && t20.insert(ds)) && (t21!=null && t21.insert(ds)) && (t22!=null && t22.insert(ds)) && (t23!=null && t23.insert(ds)) && (t24!=null && t24.insert(ds)) && (t25!=null && t25.insert(ds)) && (t26!=null && t26.insert(ds)) && (t27!=null && t27.insert(ds)) && (t28!=null && t28.insert(ds)) && (t29!=null && t29.insert(ds)) && (t30!=null && t30.insert(ds)) && (t31!=null && t31.insert(ds)) && (t32!=null && t32.insert(ds));
		}
		@Override
		public boolean update() throws SQLException {
			return (t1!=null && t1.update()) && (t2!=null && t2.update()) && (t3!=null && t3.update()) && (t4!=null && t4.update()) && (t5!=null && t5.update()) && (t6!=null && t6.update()) && (t7!=null && t7.update()) && (t8!=null && t8.update()) && (t9!=null && t9.update()) && (t10!=null && t10.update()) && (t11!=null && t11.update()) && (t12!=null && t12.update()) && (t13!=null && t13.update()) && (t14!=null && t14.update()) && (t15!=null && t15.update()) && (t16!=null && t16.update()) && (t17!=null && t17.update()) && (t18!=null && t18.update()) && (t19!=null && t19.update()) && (t20!=null && t20.update()) && (t21!=null && t21.update()) && (t22!=null && t22.update()) && (t23!=null && t23.update()) && (t24!=null && t24.update()) && (t25!=null && t25.update()) && (t26!=null && t26.update()) && (t27!=null && t27.update()) && (t28!=null && t28.update()) && (t29!=null && t29.update()) && (t30!=null && t30.update()) && (t31!=null && t31.update()) && (t32!=null && t32.update());
		}
		@Override
		public boolean update(DataSource ds) throws SQLException {
			return (t1!=null && t1.update(ds)) && (t2!=null && t2.update(ds)) && (t3!=null && t3.update(ds)) && (t4!=null && t4.update(ds)) && (t5!=null && t5.update(ds)) && (t6!=null && t6.update(ds)) && (t7!=null && t7.update(ds)) && (t8!=null && t8.update(ds)) && (t9!=null && t9.update(ds)) && (t10!=null && t10.update(ds)) && (t11!=null && t11.update(ds)) && (t12!=null && t12.update(ds)) && (t13!=null && t13.update(ds)) && (t14!=null && t14.update(ds)) && (t15!=null && t15.update(ds)) && (t16!=null && t16.update(ds)) && (t17!=null && t17.update(ds)) && (t18!=null && t18.update(ds)) && (t19!=null && t19.update(ds)) && (t20!=null && t20.update(ds)) && (t21!=null && t21.update(ds)) && (t22!=null && t22.update(ds)) && (t23!=null && t23.update(ds)) && (t24!=null && t24.update(ds)) && (t25!=null && t25.update(ds)) && (t26!=null && t26.update(ds)) && (t27!=null && t27.update(ds)) && (t28!=null && t28.update(ds)) && (t29!=null && t29.update(ds)) && (t30!=null && t30.update(ds)) && (t31!=null && t31.update(ds)) && (t32!=null && t32.update(ds));
		}
		@Override
		public boolean delete() throws SQLException {
			return (t1!=null && t1.delete()) && (t2!=null && t2.delete()) && (t3!=null && t3.delete()) && (t4!=null && t4.delete()) && (t5!=null && t5.delete()) && (t6!=null && t6.delete()) && (t7!=null && t7.delete()) && (t8!=null && t8.delete()) && (t9!=null && t9.delete()) && (t10!=null && t10.delete()) && (t11!=null && t11.delete()) && (t12!=null && t12.delete()) && (t13!=null && t13.delete()) && (t14!=null && t14.delete()) && (t15!=null && t15.delete()) && (t16!=null && t16.delete()) && (t17!=null && t17.delete()) && (t18!=null && t18.delete()) && (t19!=null && t19.delete()) && (t20!=null && t20.delete()) && (t21!=null && t21.delete()) && (t22!=null && t22.delete()) && (t23!=null && t23.delete()) && (t24!=null && t24.delete()) && (t25!=null && t25.delete()) && (t26!=null && t26.delete()) && (t27!=null && t27.delete()) && (t28!=null && t28.delete()) && (t29!=null && t29.delete()) && (t30!=null && t30.delete()) && (t31!=null && t31.delete()) && (t32!=null && t32.delete());
		}
		@Override
		public boolean delete(DataSource ds) throws SQLException {
			return (t1!=null && t1.delete(ds)) && (t2!=null && t2.delete(ds)) && (t3!=null && t3.delete(ds)) && (t4!=null && t4.delete(ds)) && (t5!=null && t5.delete(ds)) && (t6!=null && t6.delete(ds)) && (t7!=null && t7.delete(ds)) && (t8!=null && t8.delete(ds)) && (t9!=null && t9.delete(ds)) && (t10!=null && t10.delete(ds)) && (t11!=null && t11.delete(ds)) && (t12!=null && t12.delete(ds)) && (t13!=null && t13.delete(ds)) && (t14!=null && t14.delete(ds)) && (t15!=null && t15.delete(ds)) && (t16!=null && t16.delete(ds)) && (t17!=null && t17.delete(ds)) && (t18!=null && t18.delete(ds)) && (t19!=null && t19.delete(ds)) && (t20!=null && t20.delete(ds)) && (t21!=null && t21.delete(ds)) && (t22!=null && t22.delete(ds)) && (t23!=null && t23.delete(ds)) && (t24!=null && t24.delete(ds)) && (t25!=null && t25.delete(ds)) && (t26!=null && t26.delete(ds)) && (t27!=null && t27.delete(ds)) && (t28!=null && t28.delete(ds)) && (t29!=null && t29.delete(ds)) && (t30!=null && t30.delete(ds)) && (t31!=null && t31.delete(ds)) && (t32!=null && t32.delete(ds));
		}
		@Override
		public boolean save() throws SQLException {
			return (t1!=null && t1.save()) && (t2!=null && t2.save()) && (t3!=null && t3.save()) && (t4!=null && t4.save()) && (t5!=null && t5.save()) && (t6!=null && t6.save()) && (t7!=null && t7.save()) && (t8!=null && t8.save()) && (t9!=null && t9.save()) && (t10!=null && t10.save()) && (t11!=null && t11.save()) && (t12!=null && t12.save()) && (t13!=null && t13.save()) && (t14!=null && t14.save()) && (t15!=null && t15.save()) && (t16!=null && t16.save()) && (t17!=null && t17.save()) && (t18!=null && t18.save()) && (t19!=null && t19.save()) && (t20!=null && t20.save()) && (t21!=null && t21.save()) && (t22!=null && t22.save()) && (t23!=null && t23.save()) && (t24!=null && t24.save()) && (t25!=null && t25.save()) && (t26!=null && t26.save()) && (t27!=null && t27.save()) && (t28!=null && t28.save()) && (t29!=null && t29.save()) && (t30!=null && t30.save()) && (t31!=null && t31.save()) && (t32!=null && t32.save());
		}
		@Override
		public boolean save(DataSource ds) throws SQLException {
			return (t1!=null && t1.save(ds)) && (t2!=null && t2.save(ds)) && (t3!=null && t3.save(ds)) && (t4!=null && t4.save(ds)) && (t5!=null && t5.save(ds)) && (t6!=null && t6.save(ds)) && (t7!=null && t7.save(ds)) && (t8!=null && t8.save(ds)) && (t9!=null && t9.save(ds)) && (t10!=null && t10.save(ds)) && (t11!=null && t11.save(ds)) && (t12!=null && t12.save(ds)) && (t13!=null && t13.save(ds)) && (t14!=null && t14.save(ds)) && (t15!=null && t15.save(ds)) && (t16!=null && t16.save(ds)) && (t17!=null && t17.save(ds)) && (t18!=null && t18.save(ds)) && (t19!=null && t19.save(ds)) && (t20!=null && t20.save(ds)) && (t21!=null && t21.save(ds)) && (t22!=null && t22.save(ds)) && (t23!=null && t23.save(ds)) && (t24!=null && t24.save(ds)) && (t25!=null && t25.save(ds)) && (t26!=null && t26.save(ds)) && (t27!=null && t27.save(ds)) && (t28!=null && t28.save(ds)) && (t29!=null && t29.save(ds)) && (t30!=null && t30.save(ds)) && (t31!=null && t31.save(ds)) && (t32!=null && t32.save(ds));
		}
		@Override
		public boolean exists() throws SQLException {
			return (t1!=null && t1.exists()) || (t2!=null && t2.exists()) || (t3!=null && t3.exists()) || (t4!=null && t4.exists()) || (t5!=null && t5.exists()) || (t6!=null && t6.exists()) || (t7!=null && t7.exists()) || (t8!=null && t8.exists()) || (t9!=null && t9.exists()) || (t10!=null && t10.exists()) || (t11!=null && t11.exists()) || (t12!=null && t12.exists()) || (t13!=null && t13.exists()) || (t14!=null && t14.exists()) || (t15!=null && t15.exists()) || (t16!=null && t16.exists()) || (t17!=null && t17.exists()) || (t18!=null && t18.exists()) || (t19!=null && t19.exists()) || (t20!=null && t20.exists()) || (t21!=null && t21.exists()) || (t22!=null && t22.exists()) || (t23!=null && t23.exists()) || (t24!=null && t24.exists()) || (t25!=null && t25.exists()) || (t26!=null && t26.exists()) || (t27!=null && t27.exists()) || (t28!=null && t28.exists()) || (t29!=null && t29.exists()) || (t30!=null && t30.exists()) || (t31!=null && t31.exists()) || (t32!=null && t32.exists());
		}
		@Override
		public boolean exists(DataSource ds) throws SQLException {
			return (t1!=null && t1.exists(ds)) || (t2!=null && t2.exists(ds)) || (t3!=null && t3.exists(ds)) || (t4!=null && t4.exists(ds)) || (t5!=null && t5.exists(ds)) || (t6!=null && t6.exists(ds)) || (t7!=null && t7.exists(ds)) || (t8!=null && t8.exists(ds)) || (t9!=null && t9.exists(ds)) || (t10!=null && t10.exists(ds)) || (t11!=null && t11.exists(ds)) || (t12!=null && t12.exists(ds)) || (t13!=null && t13.exists(ds)) || (t14!=null && t14.exists(ds)) || (t15!=null && t15.exists(ds)) || (t16!=null && t16.exists(ds)) || (t17!=null && t17.exists(ds)) || (t18!=null && t18.exists(ds)) || (t19!=null && t19.exists(ds)) || (t20!=null && t20.exists(ds)) || (t21!=null && t21.exists(ds)) || (t22!=null && t22.exists(ds)) || (t23!=null && t23.exists(ds)) || (t24!=null && t24.exists(ds)) || (t25!=null && t25.exists(ds)) || (t26!=null && t26.exists(ds)) || (t27!=null && t27.exists(ds)) || (t28!=null && t28.exists(ds)) || (t29!=null && t29.exists(ds)) || (t30!=null && t30.exists(ds)) || (t31!=null && t31.exists(ds)) || (t32!=null && t32.exists(ds));
		}
		@Override
		protected Object __NOSCO_PRIVATE_mapType(final Object o) {
			return t1.__NOSCO_PRIVATE_mapType(o);
		}
		@Override
		public String toString() {
			return t1 +"+"+ t2 +"+"+ t3 +"+"+ t4 +"+"+ t5 +"+"+ t6 +"+"+ t7 +"+"+ t8 +"+"+ t9 +"+"+ t10 +"+"+ t11 +"+"+ t12 +"+"+ t13 +"+"+ t14 +"+"+ t15 +"+"+ t16 +"+"+ t17 +"+"+ t18 +"+"+ t19 +"+"+ t20 +"+"+ t21 +"+"+ t22 +"+"+ t23 +"+"+ t24 +"+"+ t25 +"+"+ t26 +"+"+ t27 +"+"+ t28 +"+"+ t29 +"+"+ t30 +"+"+ t31 +"+"+ t32;
		}
		@Override
		public String toStringDetailed() {
			return (t1==null ? t1 : t1.toStringDetailed()) +"+"+ (t2==null ? t2 : t2.toStringDetailed()) +"+"+ (t3==null ? t3 : t3.toStringDetailed()) +"+"+ (t4==null ? t4 : t4.toStringDetailed()) +"+"+ (t5==null ? t5 : t5.toStringDetailed()) +"+"+ (t6==null ? t6 : t6.toStringDetailed()) +"+"+ (t7==null ? t7 : t7.toStringDetailed()) +"+"+ (t8==null ? t8 : t8.toStringDetailed()) +"+"+ (t9==null ? t9 : t9.toStringDetailed()) +"+"+ (t10==null ? t10 : t10.toStringDetailed()) +"+"+ (t11==null ? t11 : t11.toStringDetailed()) +"+"+ (t12==null ? t12 : t12.toStringDetailed()) +"+"+ (t13==null ? t13 : t13.toStringDetailed()) +"+"+ (t14==null ? t14 : t14.toStringDetailed()) +"+"+ (t15==null ? t15 : t15.toStringDetailed()) +"+"+ (t16==null ? t16 : t16.toStringDetailed()) +"+"+ (t17==null ? t17 : t17.toStringDetailed()) +"+"+ (t18==null ? t18 : t18.toStringDetailed()) +"+"+ (t19==null ? t19 : t19.toStringDetailed()) +"+"+ (t20==null ? t20 : t20.toStringDetailed()) +"+"+ (t21==null ? t21 : t21.toStringDetailed()) +"+"+ (t22==null ? t22 : t22.toStringDetailed()) +"+"+ (t23==null ? t23 : t23.toStringDetailed()) +"+"+ (t24==null ? t24 : t24.toStringDetailed()) +"+"+ (t25==null ? t25 : t25.toStringDetailed()) +"+"+ (t26==null ? t26 : t26.toStringDetailed()) +"+"+ (t27==null ? t27 : t27.toStringDetailed()) +"+"+ (t28==null ? t28 : t28.toStringDetailed()) +"+"+ (t29==null ? t29 : t29.toStringDetailed()) +"+"+ (t30==null ? t30 : t30.toStringDetailed()) +"+"+ (t31==null ? t31 : t31.toStringDetailed()) +"+"+ (t32==null ? t32 : t32.toStringDetailed());
		}
	}

}
