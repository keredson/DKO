package org.nosco;

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

import org.nosco.util.Misc;

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

	static class InTmpTable<T> extends Condition {

		private Field<T> field;
		private Collection<T> set;
		private String tmpTableName = "#NOSCO_"+ Math.round(Math.random() * Integer.MAX_VALUE);
		private String type = null;

		public InTmpTable(Field<T> field, Collection<T> set) {
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
		boolean matches(Table t) {
			return set.contains(t.get(field));
		}

		@Override
		protected void getSQL(StringBuffer sb, List<Object> bindings,
				SqlContext context) {
			sb.append(' ');
			sb.append(Util.derefField(field, context));
			sb.append(" in ");
			sb.append("(select id from "+ tmpTableName +")");
		}

		@Override
		public void _preExecute(Connection conn) throws SQLException {
			Statement stmt = null;
			PreparedStatement ps = null;
			try {
				stmt = conn.createStatement();
				String sql = "CREATE TABLE "+ tmpTableName + "(id "+ type +", PRIMARY KEY (id))";
				Misc.log(sql, null);
				stmt.execute(sql);
				ps = conn.prepareStatement("insert into "+ tmpTableName +" values (?)");
				int i = 0;
				int added = 0;
				for (T t : set) {
					++i;
					if (t instanceof Character) ps.setString(1, t.toString());
					else ps.setObject(1, t);
					ps.addBatch();
					if (i%64 == 0) for (int x : ps.executeBatch()) added += x;
				}
				if (i%64 != 0) {
					for (int x : ps.executeBatch()) {
						added += x;
					}
				}
			} catch (SQLException e) {
				throw e;
			} finally {
				try {
					if (stmt!=null && !stmt.isClosed()) stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (ps!=null && !ps.isClosed()) ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void _postExecute(Connection conn) throws SQLException {
			Statement stmt = null;
			try {
				stmt = conn.createStatement();
				String sql = "DROP TABLE "+ tmpTableName;
				Misc.log(sql, null);
				stmt.execute(sql);
			} catch (SQLException e) {
				throw e;
			} finally {
				try {
					if (stmt!=null && !stmt.isClosed()) stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * always true
	 */
	public static final Condition TRUE = new Condition() {
		protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
			sb.append(" 1=1");
		}
		@Override
		boolean matches(Table t) {
			return true;
		}
	};

	/**
	 * always false
	 */
	public static final Condition FALSE = new Condition() {
		protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
			sb.append(" 1=0");
		}
		@Override
		boolean matches(Table t) {
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

		private String s;

		public Literal(String s) {
			this.s = s;
		}

		@Override
		boolean matches(Table t) {
			throw new RuntimeException("literal conditions cannot be applied to in-memory queries");
		}

		@Override
		protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
			sb.append(" ");
			sb.append(s);
		}

	}

	List<Object> bindings = null;

	/**
	 * Internal function.  Do not use.  Subject to change.
	 */
	String getSQL(SqlContext context) {
		StringBuffer sb = new StringBuffer();
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
	public Condition and(Condition... conditions) {
		AndCondition c = new AndCondition(conditions);
		c.conditions.add(this);
		return c;
	}

	/**
	 * Creates a condition that represents the logical OR between this condition and the given conditions.
	 * @param conditions
	 * @return A new condition ORing this with the given conditions
	 */
	public Condition or(Condition... conditions) {
		OrCondition c = new OrCondition(conditions);
		c.conditions.add(this);
		return c;
	}

	private static class AndCondition extends Condition {

		List<Condition> conditions = new ArrayList<Condition>();

		public AndCondition(Condition[] conditions) {
			for (Condition condition : conditions) {
				if (condition instanceof AndCondition) {
					for (Condition c : ((AndCondition)condition).conditions) {
						this.conditions.add(c);
					}
				} else {
					this.conditions.add(condition);
				}
			}
		}

		@Override
		protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
			sb.append("(");
			for (int i=0; i<conditions.size(); ++i) {
				Condition condition = conditions.get(i);
				condition.getSQL(sb, bindings, context);
				if (i<conditions.size()-1) {
					sb.append(" and ");
				}
			}
			sb.append(")");
		}

		@Override
		boolean matches(Table t) {
			for (Condition c : conditions) {
				if (!c.matches(t)) return false;
			}
			return true;
		}

	}

	private static class OrCondition extends Condition {

		List<Condition> conditions = new ArrayList<Condition>();

		public OrCondition(Condition[] conditions) {
			for (Condition condition : conditions) {
				if (condition instanceof OrCondition) {
					for (Condition c : ((OrCondition)condition).conditions) {
						this.conditions.add(c);
					}
				} else {
					this.conditions.add(condition);
				}
			}
		}

		@Override
		protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
			sb.append("(");
			for (int i=0; i<conditions.size(); ++i) {
				Condition condition = conditions.get(i);
				condition.getSQL(sb, bindings, context);
				if (i<conditions.size()-1) {
					sb.append(" or ");
				}
			}
			sb.append(")");
		}

		@Override
		boolean matches(Table t) {
			for (Condition c : conditions) {
				if (c.matches(t)) return true;
			}
			return false;
		}

	}

	static class Not extends Condition {

		private Condition condition;
		private boolean parens = true;

		public Not(Condition condition) {
			this.condition = condition;
		}

		public Not(Condition condition, boolean parens) {
			this.condition = condition;
			this.parens = parens;
		}

		@Override
		protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
			sb.append(" not ");
			if (parens) sb.append("(");
			condition.getSQL(sb, bindings, context);
			if (parens) sb.append(")");
		}

		@Override
		boolean matches(Table t) {
			return !condition.matches(t);
		}

	}

	static class Ternary extends Condition {

		private Field<?> field;
		private String cmp1;
		private String cmp2;
		private Object v1;
		private Object v2;
		private Function function1;
		private Function function2;

		public Ternary(Field<?> field, String cmp1, Object v1, String cmp2, Object v2) {
			this.field = field;
			this.cmp1 = cmp1;
			this.cmp2 = cmp2;
			this.v1 = v1;
			this.v2 = v2;
			function1 = null;
			function2 = null;
		}

		public Ternary(Field<?> field, String cmp1, Function f1, String cmp2, Object v2) {
			this.field = field;
			this.cmp1 = cmp1;
			this.cmp2 = cmp2;
			this.v1 = null;
			this.v2 = v2;
			function1 = f1;
			function2 = null;
		}

		public Ternary(Field<?> field, String cmp1, Object v1, String cmp2, Function f2) {
			this.field = field;
			this.cmp1 = cmp1;
			this.cmp2 = cmp2;
			this.v1 = v1;
			this.v2 = null;
			function1 = null;
			function2 = f2;
		}

		public Ternary(Field<?> field, String cmp1, Function f1, String cmp2, Function f2) {
			this.field = field;
			this.cmp1 = cmp1;
			this.cmp2 = cmp2;
			this.v1 = null;
			this.v2 = null;
			function1 = f1;
			function2 = f2;
		}

		@Override
		protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
			sb.append(' ');
			sb.append(Util.derefField(field, context));
			sb.append(cmp1);
			if (function1 == null) {
				sb.append("?");
				bindings.add(v1);
			} else {
				sb.append(function1.getSQL(context));
				bindings.addAll(function1.getSQLBindings());
			}
			sb.append(cmp2);
			if (function2 == null) {
				sb.append("?");
				bindings.add(v2);
			} else {
				sb.append(function2.getSQL(context));
				bindings.addAll(function2.getSQLBindings());
			}
		}

		@SuppressWarnings("rawtypes")
		@Override
		boolean matches(Table t) {
			if (!cmp1.trim().equalsIgnoreCase("between")) {
				throw new IllegalStateException("unknown comparision function '"+ cmp1
						+"' for in-memory conditional check");
			}
			if (!cmp2.trim().equalsIgnoreCase("and")) {
				throw new IllegalStateException("unknown comparision function '"+ cmp2
						+"' for in-memory conditional check");
			}
			Comparable o1 = (Comparable) v1;
			Comparable o2 = (Comparable) v2;
			Comparable v = (Comparable) t.get(field);
			if (o1 != null && o1.compareTo(v) > 0) return false;
			if (o2 != null && o2.compareTo(v) <= 0) return false;
			return true;
		}

	}

	static class Unary extends Condition {

		private Field<?> field;
		private String cmp;

		public <T> Unary(Field<T> field, String cmp) {
			this.field = field;
			this.cmp = cmp;
		}

		@Override
		protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
			sb.append(' ');
			sb.append(Util.derefField(field, context));
			sb.append(cmp);
		}

		@Override
		boolean matches(Table t) {
			Object v = t.get(field);
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

		private Field<?> field;
		private Object v;
		private Field<?> field2;
		private String cmp;
		private Select<?> s;
		private Function function = null;

		public <T> Binary(Field<T> field, String cmp, Object v) {
			// note "v" should be of type T here - set to object to work around
			// this bug: http://stackoverflow.com/questions/5361513/reference-is-ambiguous-with-generics
			this.field = field;
			this.cmp = cmp;
			this.v = v;
		}

		public <T> Binary(Field<T> field, String cmp, Field<T> field2) {
			this.field = field;
			this.cmp = cmp;
			this.field2 = field2;
		}

		public <T> Binary(Field<T> field, String cmp, Query<?> q) {
			this.field = field;
			this.cmp = cmp;
			this.s = (Select<?>) q.all();
		}

		public <T> Binary(Field<T> field, String cmp, Function f) {
			this.field = field;
			this.cmp = cmp;
			this.function  = f;
		}

		@Override
		protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
			sb.append(' ');
			if (v!=null) {
				sb.append(Util.derefField(field, context));
				sb.append(cmp);
				sb.append("?");
				bindings.add(v);
			} else if (field2!=null) {
				if (!field.isBound() && !field2.isBound() && field.sameField(field2)) {
					try {
						Table table = field.TABLE.newInstance();
						String id = table.SCHEMA_NAME() +"."+ table.TABLE_NAME();
						Set<String> tableNames = context.tableNameMap.get(id);
						if (tableNames.size() > 2) {
							throw new RuntimeException("field ambigious");
						} else if (tableNames.size() < 2) {
							sb.append(Util.derefField(field, context));
							sb.append(cmp);
							sb.append(Util.derefField(field2, context));
						} else {
							Iterator<String> i = tableNames.iterator();
							sb.append(i.next() + "."+ field);
							sb.append(cmp);
							sb.append(i.next() + "."+ field2);
						}
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
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
				SqlContext innerContext = new SqlContext(s.getUnderlyingQuery());
				innerContext.parentContext = context;
				if (" in ".equals(cmp)) {
					innerContext.maxFields = 1;
				}
				Tuple2<String, List<Object>> ret = s.getSQL(innerContext);
				sb.append(ret.a);
				bindings.addAll(ret.b);
				sb.append(')');
			} else if (function!=null) {
				sb.append(Util.derefField(field, context));
				sb.append(cmp);
				sb.append(function.getSQL(context));
				bindings.addAll(function.getSQLBindings());
			} else {
				sb.append(Util.derefField(field, context));
				sb.append(" is null");
			}
		}

		@Override
		boolean matches(Table t) {
			if (v!=null) {
				return v.equals(t.get(field));
			} else if (field2!=null) {
				Object a = t.get(field);
				Object b = t.get(field2);
				return a == b || (a != null && a.equals(b));
			} else {
				return t.get(field) == null;
			}
		}

	}

	static class In extends Condition {

		private Field<?> field;
		private String cmp;
		private Object[] set;
		private Collection<?> set2;

		public In(Field<?> field, String cmp, Object... set) {
			this.field = field;
			this.cmp = cmp;
			this.set = set;
			this.set2 = null;
		}

		public In(Field<?> field, String cmp, Collection<?> set) {
			this.field = field;
			this.cmp = cmp;
			this.set = null;
			this.set2 = set;
		}

		@Override
		protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
			sb.append(' ');
			sb.append(Util.derefField(field, context));
			sb.append(cmp);
			sb.append('(');
			if (set != null && set.length > 0) {
				for (int i=0; i<set.length; ++i) {
					Object v = set[i];
					sb.append("?");
					if (i<set.length-1) sb.append(",");
					bindings.add(v);
				}
			} else if (set2 != null && set2.size() > 0) {
				int i = 0;
				for (Object v : set2) {
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
		boolean matches(Table t) {
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
				boolean v = set2.contains(t.get(field));
				return rev ? !v : v;
			} else {
				return false;
			}
		}
	}

	private static boolean eq(Object a, Object b) {
		return a == b || (a != null && a.equals(b));
	}

	static class Exists extends Condition {

		private Query<? extends Table> q;
		private Select<?> s;

		Exists(Query<? extends Table> q) {
			this.q = q;
			this.s = (Select<?>) q.all();
		}

		@Override
		public Condition not() {
			return new Not(this, false);
		}

		@Override
		boolean matches(Table t) {
			try {
				return q.size() > 0;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
			sb.append(" exists (");
			SqlContext innerContext = new SqlContext(s.getUnderlyingQuery());
			innerContext.parentContext = context;
			innerContext.dbType = context.dbType;
			Tuple2<String, List<Object>> ret = s.getSQL(innerContext);
			sb.append(ret.a);
			bindings.addAll(ret.b);
			sb.append(")");
		}

	}

	void _preExecute(Connection conn) throws SQLException {
		// default to do nothing
	}

	void _postExecute(Connection conn) throws SQLException {
		// default to do nothing
	}

}
