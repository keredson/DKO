package org.nosco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A collection of SQL functions. &nbsp; Use these to call functions on fields
 * or values in the generated SQL. &nbsp; For example:
 *
 * <pre>   import static org.nosco.Function.*;
 *   SomeClass.ALL.where(SomeClass.BIRTH_DATE.gt(NOW())).count()</pre>
 *
 * <p>would generate:
 *
 * <pre>   select * from some_class where birth_date > NOW()</pre>
 *
 * @author Derek Anderson
 */
public abstract class Function {

	/**
	 * Create a SQL function for IFNULL() (ISNULL() on SQL Server).
	 * @param f
	 * @param v
	 * @return
	 */
	public static <T> Function IFNULL(Field<? extends T> f, T v) {
		return new SimpleFunction("ifnull", "isnull", f, v);
	}

	/**
	 * @return the sql NOW() function (or GETDATE() on sql server)
	 */
	public static Function NOW() {
		return new SimpleFunction("now", "getdate");
	}

	/**
	 * @return same as NOW()
	 */
	public static Function GETDATE() {
		return NOW();
	}

	/**
	 * COALESCE is a sql function that will return the first non-null parameter value.
	 * @param fields
	 * @return
	 */
	public static <T> Function COALESCE(Field<? extends T>... fields) {
		return new SimpleFunction("coalesce", fields);
	}

	/**
	 * COALESCE is a sql function that will return the first non-null parameter value.
	 * @param field
	 * @param v
	 * @return
	 */
	public static <T> Function COALESCE(Field<? extends T> field, T v) {
		return new SimpleFunction("coalesce", field, v);
	}

	/**
	 * COALESCE is a sql function that will return the first non-null parameter value.
	 * @param f1
	 * @param f2
	 * @param v
	 * @return
	 */
	public static <T> Function COALESCE(Field<? extends T> f1, Field<? extends T> f2, T v) {
		return new SimpleFunction("coalesce", f1, f2, v);
	}

	/**
	 * COALESCE is a sql function that will return the first non-null parameter value.
	 * @param f1
	 * @param f2
	 * @param f3
	 * @param v
	 * @return
	 */
	public static <T> Function COALESCE(Field<? extends T> f1, Field<? extends T> f2,
			Field<? extends T> f3, T v) {
		return new SimpleFunction("coalesce", f1, f2, f3, v);
	}

	/**
	 * COALESCE is a sql function that will return the first non-null parameter value.
	 * &nbsp;
	 * If you need more than 4, roll your own Function instance. &nbsp;
	 * Sadly Java won't let you use varargs without it being the final
	 * parameter in the method call.
	 * @param f1
	 * @param f2
	 * @param f3
	 * @param f4
	 * @param v
	 * @return
	 */
	public static <T> Function COALESCE(Field<? extends T> f1, Field<? extends T> f2,
			Field<? extends T> f3, Field<? extends T> f4, T v) {
		return new SimpleFunction("coalesce", f1, f2, f3, f4, v);
	}

	public abstract String getSQL(SqlContext context);

	public abstract Collection<? extends Object> getSQLBindings();


	private static class SimpleFunction extends Function {

		private String mysql;
		private String sqlserver;
		private Object[] objects = null;
		String sql = null;
		private List<Object> bindings = null;

		SimpleFunction(String func) {
			this.mysql = func;
			this.sqlserver = func;
		}

		SimpleFunction(String func, Object... objects) {
			this.mysql = func;
			this.sqlserver = func;
		}

		SimpleFunction(String mysql, String sqlserver) {
			this.mysql = mysql;
			this.sqlserver = sqlserver;
		}

		SimpleFunction(String mysql, String sqlserver, Object... objects) {
			this.mysql = mysql;
			this.sqlserver = sqlserver;
			this.objects  = objects;
		}

		@Override
		public String getSQL(SqlContext context) {
			if (sql != null) return sql;
			StringBuilder sb = new StringBuilder();
			switch (context.dbType) {
			case MYSQL:		sb.append(mysql); break;
			case SQLSERVER:	sb.append(sqlserver); break;
			default: throw new RuntimeException("unknown DB_TYPE "+ context.dbType);
			}
			sb.append("(");
			if (objects != null) {
				for (int i=0; i<objects.length; ++i) {
					Object o = objects[i];
					if (o instanceof Field) {
						sb.append(Condition.derefField((Field<?>) o, context));
					} else {
						sb.append("?");
						if (bindings == null) bindings = new ArrayList<Object>();
						bindings.add(o);
					}
					if (i < objects.length-1) sb.append(", ");
				}
			}
			sb.append(")");
			sql = sb.toString();
			return sql;
		}

		@Override
		public Collection<? extends Object> getSQLBindings() {
			return bindings == null ? Collections.emptyList() : bindings;
		}

	}
}
