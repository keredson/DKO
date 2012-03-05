package org.nosco;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nosco.QueryImpl.TableInfo;
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

	/**
	 * always true
	 */
	public static final Condition TRUE = new Condition() {
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap, List<TableInfo> tableInfos) {
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
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap, List<TableInfo> tableInfos) {
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
		protected void getSQL(StringBuffer sb, List bindings,
				Map<String, Set<String>> tableNameMap,
				List<TableInfo> tableInfos) {
			sb.append(s);
		}

	}

	List bindings = null;

	/**
	 * Internal function.  Do not use.  Subject to change.
	 */
	String getSQL(Map<String,Set<String>> tableNameMap, List<TableInfo> tableInfos) {
		StringBuffer sb = new StringBuffer();
		bindings = new ArrayList();
		getSQL(sb, bindings, tableNameMap, tableInfos);
		return sb.toString();
	}

	/**
	 * Internal function.  Do not use.  Subject to change.
	 */
	List getSQLBindings() {
		return bindings;
	}

	/**
	 * Internal function.  Do not use.  Subject to change.
	 */
	abstract boolean matches(Table t);

	/**
	 * Internal function.  Do not use.  Subject to change.
	 */
	protected abstract void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap, List<TableInfo> tableInfos);

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
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap, List<TableInfo> tableInfos) {
			sb.append("(");
			for (int i=0; i<conditions.size(); ++i) {
				Condition condition = conditions.get(i);
				condition.getSQL(sb, bindings, tableNameMap, tableInfos);
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
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap, List<TableInfo> tableInfos) {
			sb.append("(");
			for (int i=0; i<conditions.size(); ++i) {
				Condition condition = conditions.get(i);
				condition.getSQL(sb, bindings, tableNameMap, tableInfos);
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
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap, List<TableInfo> tableInfos) {
			sb.append(" not ");
			if (parens) sb.append("(");
			condition.getSQL(sb, bindings, tableNameMap, tableInfos);
			if (parens) sb.append(")");
		}

		@Override
		boolean matches(Table t) {
			return !condition.matches(t);
		}

	}

	static class Ternary extends Condition {

		private Field field;
		private String cmp1;
		private String cmp2;
		private Object v1;
		private Object v2;

		public Ternary(Field field, String cmp1, Object v1, String cmp2, Object v2) {
			this.field = field;
			this.cmp1 = cmp1;
			this.cmp2 = cmp2;
			this.v1 = v1;
			this.v2 = v2;
		}

		@Override
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap, List<TableInfo> tableInfos) {
			sb.append(' ');
			sb.append(derefField(field, tableInfos));
			sb.append(cmp1);
			sb.append("?");
			bindings.add(v1);
			sb.append(cmp2);
			sb.append("?");
			bindings.add(v2);
		}

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
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap, List<TableInfo> tableInfos) {
			sb.append(' ');
			sb.append(derefField(field, tableInfos));
			sb.append(cmp);
		}

		@Override
		boolean matches(Table t) {
			Object v = t.get(field);
			if (" is null".equals(this.cmp)) {
				return t == null;
			}
			if (" is not null".equals(this.cmp)) {
				return t != null;
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

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap, List<TableInfo> tableInfos) {
			sb.append(' ');
			if (v!=null) {
				sb.append(derefField(field, tableInfos));
				sb.append(cmp);
				sb.append("?");
				bindings.add(v);
			} else if (field2!=null) {
				if (!field.isBound() && !field2.isBound() && field.sameField(field2)) {
					try {
						Table table = field.TABLE.newInstance();
						String id = table.SCHEMA_NAME() +"."+ table.TABLE_NAME();
						Set<String> tableNames = tableNameMap.get(id);
						if (tableNames.size() > 2) {
							throw new RuntimeException("field ambigious");
						} else if (tableNames.size() < 2) {
							sb.append(derefField(field, tableInfos));
							sb.append(cmp);
							sb.append(derefField(field2, tableInfos));
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
					sb.append(derefField(field, tableInfos));
					sb.append(cmp);
					sb.append(derefField(field2, tableInfos));
				}
			} else if (s!=null) {
				sb.append(derefField(field, tableInfos));
				sb.append(cmp);
				sb.append('(');
				sb.append(s.getSQL(true));
				bindings.addAll(s.getSQLBindings());
				sb.append(')');
			} else {
				sb.append(derefField(field, tableInfos));
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

		private Field field;
		private String cmp;
		private Object[] set;
		private Collection<?> set2;

		public In(Field field, String cmp, Object... set) {
			this.field = field;
			this.cmp = cmp;
			this.set = set;
			this.set2 = null;
		}

		public In(Field field, String cmp, Collection<?> set) {
			this.field = field;
			this.cmp = cmp;
			this.set = null;
			this.set2 = set;
		}

		@Override
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap, List<TableInfo> tableInfos) {
			sb.append(' ');
			sb.append(derefField(field, tableInfos));
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

	/**
	 * Internal function.  Do not use.  Subject to change.
	 */
	public static String derefField(Field<?> field, List<TableInfo> tableInfos) {
		if (field.isBound()) return field.toString();
		List<String> selectedTables = new ArrayList<String>();
		List<TableInfo> unboundTables = new ArrayList<TableInfo>();
		for (TableInfo info : tableInfos) {
			selectedTables.add(info.table.SCHEMA_NAME() +"."+ info.table.TABLE_NAME());
			if (info.nameAutogenned && field.TABLE.isInstance(info.table)) {
				unboundTables.add(info);
			}
		}
		if (unboundTables.size() < 1) {
			throw new RuntimeException("field "+ field +
					" is not from one of the selected tables {"+
					Misc.join(",", selectedTables) +"}");
		} else if (unboundTables.size() > 1) {
			List<String> x = new ArrayList<String>();
			for (TableInfo info : unboundTables) {
				x.add(info.table.SCHEMA_NAME() +"."+ info.table.TABLE_NAME());
			}
			throw new RuntimeException("field "+ field +
					" is ambigious over the tables {"+ Misc.join(",", x) +"}");
		} else {
			TableInfo theOne = unboundTables.iterator().next();
			return (theOne.tableName == null ? theOne.table.TABLE_NAME() : theOne.tableName)
					+ "."+ field;
		}
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
		protected void getSQL(StringBuffer sb, List bindings,
				Map<String, Set<String>> tableNameMap,
				List<TableInfo> tableInfos) {
			sb.append(" exists (");
			sb.append(s.getSQL());
			bindings.addAll(s.getSQLBindings());
			sb.append(")");
		}

	}

}
