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
		public List<Field<?>> fields() {
			List<Field<?>> fields = new ArrayList<Field<?>>();
			fields.addAll(t1.fields());
			fields.addAll(t2.fields());
			fields = Collections.unmodifiableList(fields);
			return fields;
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
		public List<Field<?>> fields() {
			List<Field<?>> fields = new ArrayList<Field<?>>();
			fields.addAll(t1.fields());
			fields.addAll(t2.fields());
			fields.addAll(t3.fields());
			fields = Collections.unmodifiableList(fields);
			return fields;
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
		public List<Field<?>> fields() {
			List<Field<?>> fields = new ArrayList<Field<?>>();
			fields.addAll(t1.fields());
			fields.addAll(t2.fields());
			fields.addAll(t3.fields());
			fields.addAll(t4.fields());
			fields = Collections.unmodifiableList(fields);
			return fields;
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
		public List<Field<?>> fields() {
			List<Field<?>> fields = new ArrayList<Field<?>>();
			fields.addAll(t1.fields());
			fields.addAll(t2.fields());
			fields.addAll(t3.fields());
			fields.addAll(t4.fields());
			fields.addAll(t5.fields());
			fields = Collections.unmodifiableList(fields);
			return fields;
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
		public List<Field<?>> fields() {
			List<Field<?>> fields = new ArrayList<Field<?>>();
			fields.addAll(t1.fields());
			fields.addAll(t2.fields());
			fields.addAll(t3.fields());
			fields.addAll(t4.fields());
			fields.addAll(t5.fields());
			fields.addAll(t6.fields());
			fields = Collections.unmodifiableList(fields);
			return fields;
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
		public List<Field<?>> fields() {
			List<Field<?>> fields = new ArrayList<Field<?>>();
			fields.addAll(t1.fields());
			fields.addAll(t2.fields());
			fields.addAll(t3.fields());
			fields.addAll(t4.fields());
			fields.addAll(t5.fields());
			fields.addAll(t6.fields());
			fields.addAll(t7.fields());
			fields = Collections.unmodifiableList(fields);
			return fields;
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
		public List<Field<?>> fields() {
			List<Field<?>> fields = new ArrayList<Field<?>>();
			fields.addAll(t1.fields());
			fields.addAll(t2.fields());
			fields.addAll(t3.fields());
			fields.addAll(t4.fields());
			fields.addAll(t5.fields());
			fields.addAll(t6.fields());
			fields.addAll(t7.fields());
			fields.addAll(t8.fields());
			fields = Collections.unmodifiableList(fields);
			return fields;
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
		public List<Field<?>> fields() {
			List<Field<?>> fields = new ArrayList<Field<?>>();
			fields.addAll(t1.fields());
			fields.addAll(t2.fields());
			fields.addAll(t3.fields());
			fields.addAll(t4.fields());
			fields.addAll(t5.fields());
			fields.addAll(t6.fields());
			fields.addAll(t7.fields());
			fields.addAll(t8.fields());
			fields.addAll(t9.fields());
			fields = Collections.unmodifiableList(fields);
			return fields;
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

}
