package org.nosco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.nosco.Condition.Binary2;
import org.nosco.Constants.CALENDAR;
import org.nosco.Constants.DB_TYPE;
import org.nosco.Table.__SimplePrimaryKey;

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
		final Object[] oa = new Object[] {f, v};
		return new Custom<Boolean>(", ", "ifnull", "isnull", "ifnull", oa);
	}

	/**
	 * Create a SQL function for IFNULL() (ISNULL() on SQL Server).
	 * @param f
	 * @param v
	 * @return
	 */
	public static <T> Function<Boolean> IFNULL(final Function<? extends T> f, final T v) {
		final Object[] oa = new Object[] {f, v};
		return new Custom<Boolean>(", ", "ifnull", "isnull", "ifnull", oa);
	}

	/**
	 * @return the sql NOW() function (or GETDATE() on sql server)
	 */
	public static Function<java.sql.Date> NOW() {
		final Object[] oa = new Object[] {};
		return new Custom<java.sql.Date>(", ", "now", "getdate", "now", oa);
	}

	/**
	 * GETDATE and GETUTCDATE functions both return the current date and time. However,
	 * GETUTCDATE returns the current Universal Time Coordinate (UTC) time, whereas
	 * GETDATE returns the date and time on the computer where SQL Server is running.
	 * @return same as NOW()
	 */
	public static Function<java.sql.Date> GETDATE() {
		return NOW();
	}

	/**
	 * GETDATE and GETUTCDATE functions both return the current date and time. However,
	 * GETUTCDATE returns the current Universal Time Coordinate (UTC) time, whereas
	 * GETDATE returns the date and time on the computer where SQL Server is running.
	 * @return the sql GETUTCDATE() function
	 */
	public static Function<java.sql.Date> GETUTCDATE() {
		return new Custom<java.sql.Date>(", ", "GETUTCDATE", "GETUTCDATE", "GETUTCDATE");
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
	 * DATEADD function is deterministic; it adds a certain period of time to the
	 * existing date and time value.
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
				} else if ((context.dbType == DB_TYPE.HSQL)) {
					it.add(count);
					return "TIMESTAMPADD(SQL_TSI_" + component +", ?, "+ sql +")";
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
	 * DATEADD function is deterministic; it adds a certain period of time to the
	 * existing date and time value.
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
				} else if ((context.dbType == DB_TYPE.HSQL)) {
					it.add(count);
					return "TIMESTAMPADD(SQL_TSI_" + component +", ?, "+ sql +")";
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

	/**
	 * The DATEPART function allows retrieving any part of the date and time variable provided.
	 * This function is deterministic except when used with days of the week.
	 * <p>
	 * The DATEPART function takes two parameters: the part of the date that you want to
	 * retrieve and the date itself. The DATEPART function returns an integer representing any
	 * of the following parts of the supplied date: year, quarter, month, day of the year, day,
	 * week number, weekday number, hour, minute, second, or millisecond.
	 * @param component
	 * @param f
	 * @return
	 */
	public static Function<Integer> DATEPART(final CALENDAR component, final Function<?> f) {
		return new Custom<Integer>("DATEPART", component, f);
	}

	/**
	 * The DATEPART function allows retrieving any part of the date and time variable provided.
	 * This function is deterministic except when used with days of the week.
	 * <p>
	 * The DATEPART function takes two parameters: the part of the date that you want to
	 * retrieve and the date itself. The DATEPART function returns an integer representing any
	 * of the following parts of the supplied date: year, quarter, month, day of the year, day,
	 * week number, weekday number, hour, minute, second, or millisecond.
	 * @param component
	 * @param f
	 * @return
	 */
	public static Function<Integer> DATEPART(final CALENDAR component, final Field<?> f) {
		return new Custom<Integer>("DATEPART", component, f);
	}

	/**
	 * DAY, MONTH and YEAR functions are deterministic. Each of these accepts a single date
	 * value as a parameter and returns respective portions of the date as an integer.
	 * @param f
	 * @return
	 */
	public static Function<Integer> DAY(final Field<?> f) {
		return new Custom<Integer>("DAY", f);
	}

	/**
	 * DAY, MONTH and YEAR functions are deterministic. Each of these accepts a single date
	 * value as a parameter and returns respective portions of the date as an integer.
	 * @param f
	 * @return
	 */
	public static Function<Integer> MONTH(final Field<?> f) {
		return new Custom<Integer>("MONTH", f);
	}

	/**
	 * DAY, MONTH and YEAR functions are deterministic. Each of these accepts a single date
	 * value as a parameter and returns respective portions of the date as an integer.
	 * @param f
	 * @return
	 */
	public static Function<Integer> YEAR(final Field<?> f) {
		return new Custom<Integer>("YEAR", f);
	}

	/**
	 * DATEDIFF function is deterministic; it accepts two DATETIME values and a date portion
	 * (minute, hour, day, month, etc) as parameters. DATEDIFF() determines the difference
	 * between the two date values passed, expressed in the date portion specified.
	 * @param component
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static Function<Integer> DATEDIFF(final CALENDAR component, final Field<?> f1, final Field<?> f2) {
		return new Custom<Integer>("DATEDIFF", component, f1, f2);
	}

	/**
	 * DATEDIFF function is deterministic; it accepts two DATETIME values and a date portion
	 * (minute, hour, day, month, etc) as parameters. DATEDIFF() determines the difference
	 * between the two date values passed, expressed in the date portion specified.
	 * @param component
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static Function<Integer> DATEDIFF(final CALENDAR component, final Field<?> f1, final Function<?> f2) {
		return new Custom<Integer>("DATEDIFF", component, f1, f2);
	}

	/**
	 * DATEDIFF function is deterministic; it accepts two DATETIME values and a date portion
	 * (minute, hour, day, month, etc) as parameters. DATEDIFF() determines the difference
	 * between the two date values passed, expressed in the date portion specified.
	 * @param component
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static Function<Integer> DATEDIFF(final CALENDAR component, final Function<?> f1, final Field<?> f2) {
		return new Custom<Integer>("DATEDIFF", component, f1, f2);
	}

	/**
	 * DATEDIFF function is deterministic; it accepts two DATETIME values and a date portion
	 * (minute, hour, day, month, etc) as parameters. DATEDIFF() determines the difference
	 * between the two date values passed, expressed in the date portion specified.
	 * @param component
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static Function<Integer> DATEDIFF(final CALENDAR component, final Function<?> f1, final Function<?> f2) {
		return new Custom<Integer>("DATEDIFF", component, f1, f2);
	}

	/**
	 * The CONCAT() function is used to concatenate the values of any number of
	 * string arguments passed to it.
	 * @param fields
	 * @return
	 */
	public static Function<String> CONCAT(final Object... fields) {
		return new Function<String>() {
			Function<String> f = null;
			@Override
			String getSQL(final SqlContext context) {
				if (context.dbType == DB_TYPE.SQLSERVER) {
					f = new Custom<String>(" + ", null, null, null, fields);
				} else {
					f = new Custom<String>("CONCAT", fields);
				}
				return f.getSQL(context);
			}
			@Override
			Collection<? extends Object> getSQLBindings() {
				return f.getSQLBindings();
			}
		};
	}



	/* ======================================================================== */



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
		private String sep = ", ";

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

		Custom(final String sep, final String mysql, final String sqlserver, final String hsql, final Object[] objects) {
			this.sep = sep;
			this.mysql = mysql;
			this.sqlserver = sqlserver;
			this.hsql = hsql;
			this.objects  = objects;
		}

		Custom(final Object o1, final String sep, final Object o2) {
			this.sqlserver = null;
			this.hsql = null;
			this.mysql = null;
			this.sep = sep;
			this.objects = new Object[] {o1, o2};
		}

		@Override
		String getSQL(final SqlContext context) {
			if (sql != null) return sql;
			final StringBuilder sb = new StringBuilder();
			switch (context.dbType) {
			case MYSQL:		sb.append(mysql==null ? "" : mysql); break;
			case SQLSERVER:	sb.append(sqlserver==null ? "" : sqlserver); break;
			case HSQL:		sb.append(hsql==null ? "" : hsql); break;
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
					if (i < objects.length-1) sb.append(sep);
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



	/* ======================================================================== */



	/**
	 * Creates a condition representing this function equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition eq(final T v) {
		return new Binary2(this, "=", v);
	}

	/**
	 * Creates a condition representing this function equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition eq(final Field<T> v) {
		return new Binary2(this, "=", v);
	}

	public Condition eq(final __SimplePrimaryKey<?,T> v) {
		return eq(v.value());
	}

	/**
	 * Creates a condition representing this function equal to some function.
	 * @param v
	 * @return
	 */
	public Condition eq(final Function<?> v) {
		return new Binary2(this, "=", v);
	}

	/**
	 * Creates a condition representing this function not equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition neq(final T v) {
		return new Binary2(this, "!=", v);
	}

	/**
	 * Creates a condition representing this function not equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition neq(final Field<T> v) {
		return new Binary2(this, "!=", v);
	}

	/**
	 * Creates a condition representing this function not equal to some function.
	 * @param v
	 * @return
	 */
	public Condition neq(final Function<?> v) {
		return new Binary2(this, "!=", v);
	}

	/**
	 * Creates a condition representing this function less than the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition lt(final T v) {
		return new Binary2(this, "<", v);
	}

	/**
	 * Creates a condition representing this function less than some other field.
	 * @param v
	 * @return
	 */
	public Condition lt(final Field<T> v) {
		return new Binary2(this, "<", v);
	}

	/**
	 * Creates a condition representing this function less than some function.
	 * @param v
	 * @return
	 */
	public Condition lt(final Function<?> v) {
		return new Binary2(this, "<", v);
	}

	/**
	 * Creates a condition representing this function less than or equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition lte(final T v) {
		return new Binary2(this, "<=", v);
	}

	/**
	 * Creates a condition representing this function less than or equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition lte(final Field<T> v) {
		return new Binary2(this, "<=", v);
	}

	/**
	 * Creates a condition representing this function less than or equal to some function.
	 * @param v
	 * @return
	 */
	public Condition lte(final Function<?> v) {
		return new Binary2(this, "<=", v);
	}

	/**
	 * Creates a condition representing this function greater than the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition gt(final T v) {
		return new Binary2(this, ">", v);
	}

	/**
	 * Creates a condition representing this function greater than some other field.
	 * @param v
	 * @return
	 */
	public Condition gt(final Field<T> v) {
		return new Binary2(this, ">", v);
	}

	/**
	 * Creates a condition representing this function greater than some function.
	 * @param v
	 * @return
	 */
	public Condition gt(final Function<?> v) {
		return new Binary2(this, ">", v);
	}

	/**
	 * Creates a condition representing this function greater than or equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition gte(final T v) {
		return new Binary2(this, ">=", v);
	}

	/**
	 * Creates a condition representing this function greater than or equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition gte(final Field<T> v) {
		return new Binary2(this, ">=", v);
	}

	/**
	 * Creates a condition representing this function greater than or equal to some function.
	 * @param v
	 * @return
	 */
	public Condition gte(final Function<?> v) {
		return new Binary2(this, ">=", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> add(final T v) {
		return new Function.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> add(final Field<T> v) {
		return new Function.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> add(final Function v) {
		return new Function.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> sub(final T v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> sub(final Field<T> v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> sub(final Function v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> mul(final T v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> mul(final Field<T> v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> mul(final Function v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> div(final T v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> div(final Field<T> v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> div(final Function v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> mod(final T v) {
		return new Function.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> mod(final Field<T> v) {
		return new Function.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public Function<T> mod(final Function v) {
		return new Function.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	public Function<T> subtract(final T v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	public Function<T> subtract(final Field<T> v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	public Function<T> subtract(final Function v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	public Function<T> multiply(final T v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	public Function<T> multiply(final Field<T> v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	public Function<T> multiply(final Function v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	public Function<T> divide(final T v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	public Function<T> divide(final Field<T> v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	public Function<T> divide(final Function v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	public Function<T> modulus(final T v) {
		return new Function.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	public Function<T> modulus(final Field<T> v) {
		return new Function.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	public Function<T> modulus(final Function v) {
		return new Function.Custom<T>(this, "%", v);
	}

}
