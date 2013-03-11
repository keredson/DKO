package org.kered.dko;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.kered.dko.Tuple.Tuple2;


/**
 * This class represents a SQL conditional statement.  (ie: the contents of the {@code where} clause)
 * They are almost always created by {@code Field} instances. &nbsp; For example:
 * {@code SomeClass.ID.eq(123)}
 * would be a condition equivalent to {@code "someclass.id=123"} in SQL.
 * <p>
 * Conditions can be built into any arbitrary tree. &nbsp; For example:
 * {@code SomeClass.ID.eq(123).or(SomeClass.NAME.like("%me%"))} would generate
 * {@code "someclass.id=123 or someclass.name like '%me%'"}
 *
 * @author Derek Anderson
 */
public abstract class Condition {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		final String sql = getSQL(null);
		result = prime * result
				+ ((sql == null) ? 0 : sql.hashCode());
		for (final Object x : bindings) {
			result = prime * result
					+ ((x == null) ? 0 : x.hashCode());
		}
		//System.err.println("Condition hashCode()" +result);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Condition other = (Condition) obj;
		final String sql = getSQL(null);
		final String sqlOther = other.getSQL(null);
		if (sql == null) {
			if (sqlOther != null)
				return false;
		} else if (!sql.equals(sqlOther))
			return false;
		final Iterator<Object> it = bindings.iterator();
		final Iterator<Object> itOther = other.bindings.iterator();
		while (it.hasNext() && itOther.hasNext()) {
			final Object x = it.next();
			final Object y = itOther.next();;
			if (x == y) continue;
			if (x == null || y == null) return false;
			if (!x.equals(y)) return false;
		}
		if (it.hasNext() || itOther.hasNext()) return false;
		return true;
	}

	static class InTmpTable<T> extends Condition {

		private final Field<T> field;
		private final Collection<T> set;
		private final String tmpTableName = "#NOSCO_"+ Math.round(Math.random() * Integer.MAX_VALUE);
		private String type = null;

		public InTmpTable(final Field<T> field, final Collection<T> set) {
			this.field = field;
			// we make a set because we create a PK index on the tmp table
			this.set = new LinkedHashSet<T>(set);
			if (Integer.class.equals(field.TYPE)) type = "int";
			if (Long.class.equals(field.TYPE)) type = "long";
			if (String.class.equals(field.TYPE)) type = "varchar(4096)";
			if (Character.class.equals(field.TYPE)) type = "char(1)";
			if (type == null) throw new RuntimeException("unsupported type for temp table " +
					"generation: "+ field.TYPE);
		}

		@Override
		boolean matches(final Table t) {
			return set.contains(t.get(field));
		}

		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings,
				final SqlContext context) {
			sb.append(' ');
			sb.append(Util.derefField(field, context));
			sb.append(" in ");
			sb.append("(select id from "+ tmpTableName +")");
		}

		@Override
		public void _preExecute(final SqlContext context, final Connection conn) throws SQLException {
			Statement stmt = null;
			PreparedStatement ps = null;
			try {
				stmt = conn.createStatement();
				final String sql = "CREATE TABLE "+ tmpTableName + "(id "+ type +", PRIMARY KEY (id))";
				Util.log(sql, null);
				stmt.execute(sql);
				ps = conn.prepareStatement("insert into "+ tmpTableName +" values (?)");
				int i = 0;
				int added = 0;
				for (final T t : set) {
					++i;
					Util.setBindingWithTypeFixes(ps, 1, t);
					ps.addBatch();
					if (i%64 == 0) for (final int x : ps.executeBatch()) added += x;
				}
				if (i%64 != 0) {
					for (final int x : ps.executeBatch()) {
						added += x;
					}
				}
			} catch (final SQLException e) {
				throw e;
			} finally {
				try {
					if (stmt!=null && !stmt.isClosed()) stmt.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
				try {
					if (ps!=null && !ps.isClosed()) ps.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void _postExecute(final SqlContext context, final Connection conn) throws SQLException {
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				final String sql = "DROP TABLE "+ tmpTableName;
				Util.log(sql, null);
				stmt.execute(sql);
			} catch (final SQLException e) {
				throw e;
			} finally {
				try {
					if (stmt!=null && !stmt.isClosed()) stmt.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * always true
	 */
	public static final Condition TRUE = new Condition() {
		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			sb.append(" 1=1");
		}
		@Override
		boolean matches(final Table t) {
			return true;
		}
	};

	/**
	 * always false
	 */
	public static final Condition FALSE = new Condition() {
		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			sb.append(" 1=0");
		}
		@Override
		boolean matches(final Table t) {
			return false;
		}
	};

	/**
	 * Use this class to add custom SQL code to your where clause. &nbsp;
	 * For Example:
	 * <pre>   {@code SomeClass.ALL.where(new Condition.Literal("id = 1"));}</pre>
	 * Obviously this should be used sparingly, and can break compatibility between
	 * different databases. &nbsp;  But it's here if you really need it.
	 *
	 * @author Derek Anderson
	 */
	public static class Literal extends Condition {

		private final String s;

		public Literal(final String s) {
			this.s = s;
		}

		@Override
		boolean matches(final Table t) {
			throw new RuntimeException("literal conditions cannot be applied to in-memory queries");
		}

		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			sb.append(" ");
			sb.append(s);
		}

	}

	transient List<Object> bindings = null;

	/**
	 * Internal function.  Do not use.  Subject to change.
	 */
	String getSQL(final SqlContext context) {
		final StringBuffer sb = new StringBuffer();
		bindings = new ArrayList<Object>();
		getSQL(sb, bindings, context);
		return sb.toString();
	}

	/**
	 * Internal function.  Do not use.  Subject to change.
	 */
	List<?> getSQLBindings() {
		return bindings;
	}

	/**
	 * Internal function.  Do not use.  Subject to change.
	 */
	abstract boolean matches(Table t);

	/**
	 * Internal function.  Do not use.  Subject to change.
	 */
	protected abstract void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context);

	/**
	 * Creates a new condition negating the current condition.
	 * @return A new condition negating the current condition
	 */
	public Condition not() {
		return new Not(this);
	}

	/**
	 * Creates a condition that represents the logical AND between this condition and the given conditions.
	 * @param conditions
	 * @return A new condition ANDing this with the given conditions
	 */
	public Condition and(final Condition... conditions) {
		return new And(this, conditions);
	}

	/**
	 * Creates a condition that represents the logical OR between this condition and the given conditions.
	 * @param conditions
	 * @return A new condition ORing this with the given conditions
	 */
	public Condition or(final Condition... conditions) {
		final Or c = new Or(conditions);
		c.conditions.add(this);
		return c;
	}

	static class And extends Condition {

		List<Condition> conditions = new ArrayList<Condition>();

		public And(Condition cnd, final Condition[] conditions) {
			this.conditions.add(cnd);
			for (final Condition condition : conditions) {
				if (condition instanceof And) {
					for (final Condition c : ((And)condition).conditions) {
						this.conditions.add(c);
					}
				} else {
					this.conditions.add(condition);
				}
			}
		}

		public And(final Condition[] conditions) {
			for (final Condition condition : conditions) {
				if (condition instanceof And) {
					for (final Condition c : ((And)condition).conditions) {
						this.conditions.add(c);
					}
				} else {
					this.conditions.add(condition);
				}
			}
		}

		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			sb.append("(");
			for (int i=0; i<conditions.size(); ++i) {
				final Condition condition = conditions.get(i);
				condition.getSQL(sb, bindings, context);
				if (i<conditions.size()-1) {
					sb.append(" and ");
				}
			}
			sb.append(")");
		}

		@Override
		boolean matches(final Table t) {
			for (final Condition c : conditions) {
				if (!c.matches(t)) return false;
			}
			return true;
		}

		@Override
		void _preExecute(final SqlContext context, final Connection conn) throws SQLException {
			super._preExecute(context, conn);
			for (final Condition condition : conditions) {
				condition._preExecute(context, conn);
			}
		}

		@Override
		void _postExecute(final SqlContext context, final Connection conn) throws SQLException {
			super._postExecute(context, conn);
			for (final Condition condition : conditions) {
				condition._postExecute(context, conn);
			}
		}

		void visit(Visitor v) {
			v.visited(this);
			for (Condition condition : conditions) condition.visit(v);
		}

	}

	static class Or extends Condition {

		List<Condition> conditions = new ArrayList<Condition>();

		public Or(final Condition[] conditions) {
			for (final Condition condition : conditions) {
				if (condition instanceof Or) {
					for (final Condition c : ((Or)condition).conditions) {
						this.conditions.add(c);
					}
				} else {
					this.conditions.add(condition);
				}
			}
		}

		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			sb.append("(");
			for (int i=0; i<conditions.size(); ++i) {
				final Condition condition = conditions.get(i);
				condition.getSQL(sb, bindings, context);
				if (i<conditions.size()-1) {
					sb.append(" or ");
				}
			}
			sb.append(")");
		}

		@Override
		boolean matches(final Table t) {
			for (final Condition c : conditions) {
				if (c.matches(t)) return true;
			}
			return false;
		}

		@Override
		void _preExecute(final SqlContext context, final Connection conn) throws SQLException {
			super._preExecute(context, conn);
			for (final Condition condition : conditions) {
				condition._preExecute(context, conn);
			}
		}

		@Override
		void _postExecute(final SqlContext context, final Connection conn) throws SQLException {
			super._postExecute(context, conn);
			for (final Condition condition : conditions) {
				condition._postExecute(context, conn);
			}
		}

		void visit(Visitor v) {
			v.visited(this);
			for (Condition condition : conditions) condition.visit(v);
		}

	}

	static class Not extends Condition {

		private final Condition condition;
		private boolean parens = true;

		public Not(final Condition condition) {
			this.condition = condition;
		}

		public Not(final Condition condition, final boolean parens) {
			this.condition = condition;
			this.parens = parens;
		}

		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			sb.append(" not ");
			if (parens) sb.append("(");
			condition.getSQL(sb, bindings, context);
			if (parens) sb.append(")");
		}

		@Override
		boolean matches(final Table t) {
			return !condition.matches(t);
		}

		@Override
		void _preExecute(final SqlContext context, final Connection conn) throws SQLException {
			super._preExecute(context, conn);
			condition._preExecute(context, conn);
		}

		@Override
		void _postExecute(final SqlContext context, final Connection conn) throws SQLException {
			super._postExecute(context, conn);
			condition._postExecute(context, conn);
		}

		void visit(Visitor v) {
			v.visited(this);
			condition.visit(v);
		}

	}

	static class Ternary extends Condition {

		final Object v1;
		private final String cmp1;
		final Object v2;
		private final String cmp2;
		final Object v3;

		public Ternary(final Object v1, final String cmp1, final Object v2, final String cmp2, final Object v3) {
			this.v1 = v1;
			this.cmp1 = cmp1;
			this.v2 = v2;
			this.cmp2 = cmp2;
			this.v3 = v3;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			sb.append(' ');
			if (v1 instanceof SQLFunction) {
				((SQLFunction)v1).getSQL(sb, bindings, context);
			} else if (v1 instanceof Field) {
				sb.append(Util.derefField((Field)v1, context));
			} else {
				sb.append("?");
				bindings.add(v1);
			}
			sb.append(cmp1);
			if (v2 instanceof SQLFunction) {
				((SQLFunction)v2).getSQL(sb, bindings, context);
			} else if (v2 instanceof Field) {
				sb.append(Util.derefField((Field)v2, context));
			} else {
				sb.append("?");
				bindings.add(v2);
			}
			sb.append(cmp2);
			if (v3 instanceof SQLFunction) {
				((SQLFunction)v3).getSQL(sb, bindings, context);
			} else if (v2 instanceof Field) {
				sb.append(Util.derefField((Field)v3, context));
			} else {
				sb.append("?");
				bindings.add(v3);
			}
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		boolean matches(final Table t) {
			if (!cmp1.trim().equalsIgnoreCase("between")) {
				throw new IllegalStateException("unknown comparision function '"+ cmp1
						+"' for in-memory conditional check");
			}
			if (!cmp2.trim().equalsIgnoreCase("and")) {
				throw new IllegalStateException("unknown comparision function '"+ cmp2
						+"' for in-memory conditional check");
			}
			Comparable o1 = null;
			if (v1 instanceof Field) o1 = (Comparable) t.get((Field<?>) v1);
			else if (v1 instanceof Comparable) o1 = (Comparable) v1;
			Comparable o2 = null;
			if (v2 instanceof Field) o2 = (Comparable) t.get((Field<?>) v2);
			else if (v2 instanceof Comparable) o2 = (Comparable) v2;
			Comparable o3 = null;
			if (v3 instanceof Field) o3 = (Comparable) t.get((Field<?>) v3);
			else if (v3 instanceof Comparable) o3 = (Comparable) v3;
			if (o2 != null && o2.compareTo(o1) > 0) return false;
			if (o3 != null && o3.compareTo(o1) <= 0) return false;
			return true;
		}

	}

	static class Unary extends Condition {

		final Field<?> field;
		private final String cmp;

		public <T> Unary(final Field<T> field, final String cmp) {
			this.field = field;
			this.cmp = cmp;
		}

		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			sb.append(' ');
			sb.append(Util.derefField(field, context));
			sb.append(cmp);
		}

		@Override
		boolean matches(final Table t) {
			final Object v = t.get(field);
			if (" is null".equals(this.cmp)) {
				return v == null;
			}
			if (" is not null".equals(this.cmp)) {
				return v != null;
			}
			throw new IllegalStateException("unknown comparision function '"+ cmp
					+"' for in-memory conditional check");
		}

	}

	static class Binary extends Condition {

		final Field<?> field;
		private Object v;
		Field<?> field2;
		final String cmp;
		private DBRowIterator<?> s;
		private SQLFunction function;
		
		public <T> Binary(final Field<T> field, final String cmp, final Object v) {
			// note "v" should be of type T here - set to object to work around
			// this bug: http://stackoverflow.com/questions/5361513/reference-is-ambiguous-with-generics
			this.field = field;
			this.cmp = cmp;
			this.v = v;
		}

		public <T> Binary(final Field<T> field, final String cmp, final Field<T> field2) {
			if (field==null) throw new RuntimeException("field cannot be null");
			if (field2==null) throw new RuntimeException("field2 cannot be null");
			this.field = field;
			this.cmp = cmp;
			this.field2 = field2;
		}

		public <T,S extends Table> Binary(final Field<T> field, final String cmp, final DBQuery<S> q) {
			this.field = field;
			this.cmp = cmp;
			this.s = new DBRowIterator<S>(q);
		}

		public <T> Binary(final Field<T> field, final String cmp, final SQLFunction f) {
			this.field = field;
			this.cmp = cmp;
			this.function  = f;
		}

		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			sb.append(' ');
			if (v!=null) {
				sb.append(Util.derefField(field, context));
				sb.append(cmp);
				sb.append("?");
				bindings.add(v);
			} else if (field2!=null) {
				if (!field.isBound() && !field2.isBound() && field.sameField(field2) && context!=null && context.tableNameMap!=null) {
					final String id = context.getFullTableName(field.TABLE);
					final Set<String> tableNames = context.tableNameMap.get(id);
					if (tableNames.size() > 2) {
						throw new RuntimeException("field ambigious");
					} else if (tableNames.size() < 2) {
						sb.append(Util.derefField(field, context));
						sb.append(cmp);
						sb.append(Util.derefField(field2, context));
					} else {
						final Iterator<String> i = tableNames.iterator();
						sb.append(i.next() + "."+ field);
						sb.append(cmp);
						sb.append(i.next() + "."+ field2);
					}
				} else if (!field.isBound() && !field2.isBound() && field.sameField(field2) && context!=null) {
					final Set<String> tableNames = context.getPossibleTableMatches(field.TABLE);
					if (tableNames.size() > 2) {
						throw new RuntimeException("field ambigious");
					} else if (tableNames.size() < 2) {
						sb.append(Util.derefField(field, context));
						sb.append(cmp);
						sb.append(Util.derefField(field2, context));
					} else {
						final Iterator<String> i = tableNames.iterator();
						sb.append(i.next() + "."+ field.getSQL(context));
						sb.append(cmp);
						sb.append(i.next() + "."+ field2.getSQL(context));
					}

				} else {
					sb.append(Util.derefField(field, context));
					sb.append(cmp);
					sb.append(Util.derefField(field2, context));
				}
			} else if (s!=null) {
				sb.append(Util.derefField(field, context));
				sb.append(cmp);
				sb.append('(');
				final SqlContext innerContext = new SqlContext(s.getUnderlyingQuery(), context);
				if (" in ".equals(cmp)) {
					innerContext.maxFields = 1;
				}
				final Tuple2<String, List<Object>> ret = s.getSQL(innerContext);
				sb.append(ret.a);
				bindings.addAll(ret.b);
				sb.append(')');
			} else if (function!=null) {
				sb.append(Util.derefField(field, context));
				sb.append(cmp);
				function.getSQL(sb, bindings, context);
			} else {
				sb.append(Util.derefField(field, context));
				sb.append(" is null");
			}
		}

		@Override
		boolean matches(final Table t) {
			if (v!=null) {
				if ("=".equals(cmp)) return v.equals(t.get(field));
				if ("!=".equals(cmp)) return !v.equals(t.get(field));
				throw new RuntimeException("operator "+ cmp +" unsupported in in-memory comparisons");
			} else if (field2!=null) {
				final Object a = t.get(field);
				final Object b = t.get(field2);
				return a == b || (a != null && a.equals(b));
			} else {
				return t.get(field) == null;
			}
		}

	}

	static class Binary2 extends Condition {

		final String cmp;
		final Object o2;
		final Object o1;

		Binary2(final Object o1, final String cmp, final Object o2) {
			this.o1 = o1;
			this.cmp = cmp;
			this.o2 = o2;
		}

		@Override
		boolean matches(final Table t) {
			if ((o1 instanceof SQLFunction) || (o2 instanceof SQLFunction)) {
				throw new RuntimeException("Condition checking of functions cached queries not yet supported.");
			}
			Object v1 = o1 instanceof Field ? t.get((Field) o1) : o1;
			Object v2 = o2 instanceof Field ? t.get((Field) o2) : o2;
			return matches(v1, v2);
		}
		
		boolean matches(Object v1, Object v2) {
			throw new RuntimeException("In memory checking of this condition type is not supported yet.");
		}

		Object deO(Table t, Object o) {
			if (o instanceof Field) return t.get((Field) o);
			return o;
		}

		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			sb.append(' ');
			if (o1 instanceof SQLFunction) {
				final SQLFunction<?> f = (SQLFunction<?>) o1;
				f.getSQL(sb, bindings, context);
			} else if (o1 instanceof Field) {
				final Field<?> f = (Field<?>) o1;
				sb.append(Util.derefField(f, context));
			} else {
				sb.append("?");
				bindings.add(o1);
			}
			sb.append(cmp);
			if (o2 instanceof SQLFunction) {
				final SQLFunction<?> f = (SQLFunction<?>) o2;
				f.getSQL(sb, bindings, context);
			} else if (o2 instanceof Field) {
				final Field<?> f = (Field<?>) o2;
				sb.append(Util.derefField(f, context));
			} else {
				sb.append("?");
				bindings.add(o2);
			}
		}}

	static class In extends Condition {

		private final Field<?> field;
		private final String cmp;
		private final Object[] set;
		private final Collection<?> set2;

		public In(final Field<?> field, final String cmp, final Object... set) {
			this.field = field;
			this.cmp = cmp;
			this.set = set;
			this.set2 = null;
		}

		public In(final Field<?> field, final String cmp, final Collection<?> set) {
			this.field = field;
			this.cmp = cmp;
			this.set = null;
			this.set2 = set;
		}

		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			sb.append(' ');
			sb.append(Util.derefField(field, context));
			sb.append(cmp);
			sb.append('(');
			if (set != null && set.length > 0) {
				for (int i=0; i<set.length; ++i) {
					final Object v = set[i];
					if (v instanceof Field) {
						final Field<?> f = (Field<?>) v;
						sb.append(Util.derefField(f, context));
					} else {
						sb.append("?");
						bindings.add(v);
					}
					if (i<set.length-1) sb.append(",");
				}
			} else if (set2 != null && set2.size() > 0) {
				int i = 0;
				for (final Object v : set2) {
					sb.append("?");
					if (i<set2.size()-1) sb.append(",");
					bindings.add(v);
					++i;
				}
			} else {
				sb.append("null");
			}
			sb.append(')');
		}

		@Override
		boolean matches(final Table t) {
			boolean rev;
			if (cmp.trim().equalsIgnoreCase("in")) rev = false;
			else if (cmp.trim().equalsIgnoreCase("not in")) rev = true;
			else throw new IllegalStateException("unknown comparision function '"+ cmp
					+"' for in-memory conditional check");
			if (set != null && set.length > 0) {
				for (int i=0; i<set.length; ++i) {
					if (eq(t.get(field), set[i])) return rev ? false : true;
				}
				return rev ? true : false;
			} else if (set2 != null && set2.size() > 0) {
				final boolean v = set2.contains(t.get(field));
				return rev ? !v : v;
			} else {
				return false;
			}
		}
	}

	private static boolean eq(final Object a, final Object b) {
		return a == b || (a != null && a.equals(b));
	}

	static class Exists extends Condition {

		private final Query<? extends Table> q;
		private final DBRowIterator<?> s;

		<T extends Table> Exists(final Query<T> q) {
			this.q = q;
			if (q instanceof DBQuery) this.s = new DBRowIterator<T>((DBQuery<T>) q);
			else s = null;
		}

		@Override
		public Condition not() {
			return new Not(this, false);
		}

		@Override
		boolean matches(final Table t) {
			try {
				return q.size() > 0;
			} catch (final SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		protected void getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
		    if (s==null) throw new RuntimeException("Qsing a non-db query as a subquery of a db-query is not supported.");
		    sb.append(" exists (");
		    final SqlContext innerContext = new SqlContext(s.getUnderlyingQuery(), context);
		    innerContext.dbType = context==null ? null : context.dbType;
		    final Tuple2<String, List<Object>> ret = s.getSQL(innerContext);
		    sb.append(ret.a);
		    bindings.addAll(ret.b);
		    sb.append(")");
		}

	}

	void _preExecute(final SqlContext context, final Connection conn) throws SQLException {
		// default to do nothing
	}

	void _postExecute(final SqlContext context, final Connection conn) throws SQLException {
		// default to do nothing
	}
	
	void visit(Visitor v) {
		v.visited(this);
	}
	
	static interface Visitor {
		void visited(Condition c);
	}

}
