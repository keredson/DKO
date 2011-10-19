package org.nosco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Condition {

	public static final Condition TRUE = new Condition() {
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap) {
			sb.append(" 1=1");
		}
	};

	public static final Condition FALSE = new Condition() {
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap) {
			sb.append(" 1=0");
		}
	};

	List bindings = null;

	String getSQL(Map<String,Set<String>> tableNameMap) {
		StringBuffer sb = new StringBuffer();
		bindings = new ArrayList();
		getSQL(sb, bindings, tableNameMap);
		return sb.toString();
	}

	List getSQLBindings() {
		return bindings;
	}

	protected abstract void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap);

	public Condition not() {
		return new Not(this);
	}

	public Condition and(Condition... conditions) {
		AndCondition c = new AndCondition(conditions);
		c.conditions.add(this);
		return c;
	}

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
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap) {
			sb.append("(");
			for (int i=0; i<conditions.size(); ++i) {
				Condition condition = conditions.get(i);
				condition.getSQL(sb, bindings, tableNameMap);
				if (i<conditions.size()-1) {
					sb.append(" and ");
				}
			}
			sb.append(")");
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
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap) {
			sb.append("(");
			for (int i=0; i<conditions.size(); ++i) {
				Condition condition = conditions.get(i);
				condition.getSQL(sb, bindings, tableNameMap);
				if (i<conditions.size()-1) {
					sb.append(" or ");
				}
			}
			sb.append(")");
		}

	}

	public static class Not extends Condition {

		private Condition condition;

		public Not(Condition condition) {
			this.condition = condition;
		}

		@Override
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap) {
			sb.append(" not (");
			condition.getSQL(sb, bindings, tableNameMap);
			sb.append(")");
		}

	}

	public static class Ternary extends Condition {

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
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap) {
			sb.append(' ');
			sb.append(derefField(field, tableNameMap));
			sb.append(cmp1);
			sb.append("?");
			bindings.add(v1);
			sb.append(cmp2);
			sb.append("?");
			bindings.add(v2);
		}

	}

	public static class Unary extends Condition {

		private Field<?> field;
		private String cmp;

		public <T> Unary(Field<T> field, String cmp) {
			this.field = field;
			this.cmp = cmp;
		}

		@Override
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap) {
			sb.append(' ');
			sb.append(derefField(field, tableNameMap));
			sb.append(cmp);
		}

	}



	public static class Binary extends Condition {

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
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap) {
			sb.append(' ');
			if (v!=null) {
				sb.append(derefField(field, tableNameMap));
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
							sb.append(derefField(field, tableNameMap));
							sb.append(cmp);
							sb.append(derefField(field2, tableNameMap));
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
					sb.append(derefField(field, tableNameMap));
					sb.append(cmp);
					sb.append(derefField(field2, tableNameMap));
				}
			} else if (s!=null) {
				sb.append(derefField(field, tableNameMap));
				sb.append(cmp);
				sb.append('(');
				sb.append(s.getSQL(true));
				bindings.addAll(s.getSQLBindings());
				sb.append(')');
			} else {
				sb.append(derefField(field, tableNameMap));
				sb.append(" is null");
			}
		}

	}

	public static class In extends Condition {

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
		protected void getSQL(StringBuffer sb, List bindings, Map<String,Set<String>> tableNameMap) {
			sb.append(' ');
			sb.append(derefField(field, tableNameMap));
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
	}

	public static String derefField(Field<?> field, Map<String,Set<String>> tableNameMap) {
		if (field.isBound()) return field.toString();
		try {
			Table table = field.TABLE.newInstance();
			String id = table.SCHEMA_NAME() +"."+ table.TABLE_NAME();
			Set<String> tableNames = tableNameMap.get(id);
			if (tableNames.size() > 1) {
				throw new RuntimeException("field ambigious");
			} else if (tableNames.size() < 1) {
				throw new RuntimeException("field's table not in the named table map");
			} else {
				return tableNames.iterator().next() + "."+ field;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
