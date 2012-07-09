package org.nosco;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.nosco.Constants.CALENDAR;
import org.nosco.Constants.DB_TYPE;

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
public abstract class Function<T> {
	
	/**
	 * Create a SQL function for IFNULL() (ISNULL() on SQL Server).
	 * @param f
	 * @param v
	 * @return
	 */
	public static <T> Function<Boolean> IFNULL(final Field<? extends T> f, final T v) {
		return new Custom<Boolean>("ifnull", "isnull", "ifnull", f, v);
	}

	/**
	 * Create a SQL function for IFNULL() (ISNULL() on SQL Server).
	 * @param f
	 * @param v
	 * @return
	 */
	public static <T> Function<Boolean> IFNULL(final Function<? extends T> f, final T v) {
		return new Custom<Boolean>("ifnull", "isnull", "ifnull", f, v);
	}

	/**
	 * @return the sql NOW() function (or GETDATE() on sql server)
	 */
	public static Function<java.sql.Date> NOW() {
		return new Custom<java.sql.Date>("now", "getdate", "now");
	}

	/**
	 * @return same as NOW()
	 */
	public static Function<java.sql.Date> GETDATE() {
		return NOW();
	}

	/**
	 * COALESCE is a sql function that will return the first non-null parameter value.
	 * All objects other than functions or fields are passed verbatim to the 
	 * PreparedStatement with setObject().
	 * @param fields
	 * @return
	 */
	public static <T> Function<T> COALESCE(final Object... fields) {
		return new Custom<T>("coalesce", fields);
	}

	/**
	 * @param f1
	 * @param count
	 * @param component
	 * @return
	 */
	public static <T> Function<java.sql.Date> DATEADD(final Function<? extends T> f1, final int count, final CALENDAR component) {
		return new Function<java.sql.Date>() {
			List<Object> it = null;
			@Override
			String getSQL(final SqlContext context) {
				final String sql = f1.getSQL(context);
				it = new ArrayList<Object>();
				if (context.dbType == DB_TYPE.MYSQL) {
					it.addAll(f1.getSQLBindings());
					it.add(count);
					return "date_add(" + sql +", interval ? "+ component +")";
				} else {
					it.add(count);
					it.addAll(f1.getSQLBindings());
					return "dateadd(" + component +", ?, "+ sql +")";
				}
			}
			@Override
			Collection<? extends Object> getSQLBindings() {
				return it;
			}
		};

	}

	/**
	 * @param f1
	 * @param count
	 * @param component
	 * @return
	 */
	public static <T> Function<java.sql.Date> DATEADD(final Field<? extends T> field, final int count, final CALENDAR component) {
		return new Function<java.sql.Date>() {
			List<Object> it = null;
			@Override
			String getSQL(final SqlContext context) {
				it = new ArrayList<Object>();
				final String sql = Util.derefField(field, context);
				if (context.dbType == DB_TYPE.MYSQL) {
					it.add(count);
					return "date_add(" + sql +", interval ? "+ component +")";
				} else {
					it.add(count);
					return "dateadd(" + component +", ?, "+ sql +")";
				}
			}
			@Override
			Collection<? extends Object> getSQLBindings() {
				return it;
			}
		};

	}


	abstract String getSQL(SqlContext context);

	abstract Collection<? extends Object> getSQLBindings();


	/**
	 * The list of built-in functions is far from comprehensive.
	 * Use this to implement your own one-off functions.
	 * Please submit functions you think are useful back to the project!
	 */
	public static class Custom<T> extends Function<T> {

		private final String mysql;
		private final String sqlserver;
		private final String hsql;
		private Object[] objects = null;
		private String sql = null;
		private final List<Object> bindings = new ArrayList<Object>();

		/**
		 * For a simple, no argument SQL function like NOW().
		 * @param func
		 */
		public Custom(final String func) {
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
		public Custom(final String func, final Object... objects) {
			this.mysql = func;
			this.sqlserver = func;
			this.hsql = func;
			this.objects = objects;
		}

		Custom(final String mysql, final String sqlserver, final String hsql) {
			this.mysql = mysql;
			this.sqlserver = sqlserver;
			this.hsql = hsql;
		}

		Custom(final String mysql, final String sqlserver, final String hsql, final Object... objects) {
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
					if (o instanceof Field<?>) {
						sb.append(Util.derefField((Field<?>) o, context));
					} else if (o instanceof Function<?>) {
						final Function<?> f = (Function<?>) o;
						sb.append(f.getSQL(context));
						bindings.addAll(f.getSQLBindings());
					} else if (o instanceof CALENDAR) {
						sb.append(o.toString());
					} else {
						sb.append("?");
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
