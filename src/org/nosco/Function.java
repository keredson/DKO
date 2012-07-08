package org.nosco;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.nosco.Constants.CALENDAR;

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
	public static <T> Function IFNULL(final Field<? extends T> f, final T v) {
		return new CustomFunction("ifnull", "isnull", "ifnull", f, v);
	}

	/**
	 * @return the sql NOW() function (or GETDATE() on sql server)
	 */
	public static Function NOW() {
		return new CustomFunction("now", "getdate", "now");
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
	public static <T> Function COALESCE(final Field<? extends T>... fields) {
		return new CustomFunction("coalesce", (Object[]) fields);
	}

	/**
	 * COALESCE is a sql function that will return the first non-null parameter value.
	 * @param field
	 * @param v
	 * @return
	 */
	public static <T> Function COALESCE(final Field<? extends T> field, final T v) {
		return new CustomFunction("coalesce", field, v);
	}

	/**
	 * COALESCE is a sql function that will return the first non-null parameter value.
	 * @param f1
	 * @param f2
	 * @param v
	 * @return
	 */
	public static <T> Function COALESCE(final Field<? extends T> f1, final Field<? extends T> f2, final T v) {
		return new CustomFunction("coalesce", f1, f2, v);
	}

	/**
	 * COALESCE is a sql function that will return the first non-null parameter value.
	 * @param f1
	 * @param f2
	 * @param f3
	 * @param v
	 * @return
	 */
	public static <T> Function COALESCE(final Field<? extends T> f1, final Field<? extends T> f2,
			final Field<? extends T> f3, final T v) {
		return new CustomFunction("coalesce", f1, f2, f3, v);
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
	public static <T> Function COALESCE(final Field<? extends T> f1, final Field<? extends T> f2,
			final Field<? extends T> f3, final Field<? extends T> f4, final T v) {
		return new CustomFunction("coalesce", f1, f2, f3, f4, v);
	}

	/**
	 * @param f1
	 * @param count
	 * @param component
	 * @return
	 */
	public static <T> Function DATEADD(final Field<? extends T> f1, final int count, final CALENDAR component) {
		return new CustomFunction("date_add", "dateadd", "dateadd", component, count, f1);

	}


	abstract String getSQL(SqlContext context);

	abstract Collection<? extends Object> getSQLBindings();


	/**
	 * The list of built-in functions is far from comprehensive.
	 * Use this to implement your own one-off functions.
	 * Please submit functions you think are useful back to the project!
	 */
	public static class CustomFunction extends Function {

		private final String mysql;
		private final String sqlserver;
		private final String hsql;
		private Object[] objects = null;
		private String sql = null;
		private List<Object> bindings = null;

		/**
		 * For a simple, no argument SQL function like NOW().
		 * @param func
		 */
		public CustomFunction(final String func) {
			this.mysql = func;
			this.sqlserver = func;
			this.hsql = func;
		}

		/**
		 * For functions that take arguments.  The first string is the function name.
		 * The remaining parameters are passed as arguments.
		 * If an argument is a field it is referenced to a table in the from clause.
		 * For all others, the object is passed verbatim to the PreparedStatement with setObject().
		 * @param func the name of the function
		 * @param objects the arguments of the function
		 */
		public CustomFunction(final String func, final Object... objects) {
			this.mysql = func;
			this.sqlserver = func;
			this.hsql = func;
			this.objects = objects;
		}

		CustomFunction(final String mysql, final String sqlserver, final String hsql) {
			this.mysql = mysql;
			this.sqlserver = sqlserver;
			this.hsql = hsql;
		}

		CustomFunction(final String mysql, final String sqlserver, final String hsql, final Object... objects) {
			this.mysql = mysql;
			this.sqlserver = sqlserver;
			this.hsql = hsql;
			this.objects  = objects;
		}

		@Override
		String getSQL(final SqlContext context) {
			if (sql != null) return sql;
			final StringBuilder sb = new StringBuilder();
			switch (context.dbType) {
			case MYSQL:		sb.append(mysql); break;
			case SQLSERVER:	sb.append(sqlserver); break;
			case HSQL:		sb.append(hsql); break;
			default: throw new RuntimeException("unknown DB_TYPE "+ context.dbType);
			}
			sb.append("(");
			if (objects != null) {
				for (int i=0; i<objects.length; ++i) {
					final Object o = objects[i];
					if (o instanceof Field) {
						sb.append(Util.derefField((Field<?>) o, context));
					} else if (o instanceof CALENDAR) {
						sb.append(o.toString());
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
		Collection<? extends Object> getSQLBindings() {
			return bindings == null ? Collections.emptyList() : bindings;
		}

	}
}
