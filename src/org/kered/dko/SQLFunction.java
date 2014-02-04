package org.kered.dko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kered.dko.Condition.Binary;
import org.kered.dko.Condition.Binary2;
import org.kered.dko.Constants.CALENDAR;
import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.DIRECTION;
import org.kered.dko.Table.__SimplePrimaryKey;

/**
 * A collection of SQL functions. &nbsp; Use these to call functions on fields
 * or values in the generated SQL. &nbsp; For example:
 *
 * <pre>   import static org.kered.dko.Function.*;
 *   SomeClass.ALL.where(SomeClass.BIRTH_DATE.gt(NOW())).count()</pre>
 *
 * <p>would generate:
 *
 * <pre>   select * from some_class where birth_date > NOW()</pre>
 *
 * @author Derek Anderson
 */
public abstract class SQLFunction<T> implements Expression.OrderBy<T>, Expression.Select<T>, Expression.Function<T> {
	
	Class<T> type;
	
	@Override
	public Select<T> __getUnderlying() {
		return null;
	}

	@Override
	public Class<T> getType() {
		return type;
	}

	@Override
	public Expression.OrderBy<T> asc() {
		return new OrderBySQLFunction<T>(this, DIRECTION.ASCENDING);
	}

	@Override
	public Expression.OrderBy<T> desc() {
		return new OrderBySQLFunction<T>(this, DIRECTION.DESCENDING);
	}

	/**
	 * Create a SQL function for IFNULL() (ISNULL() on SQL Server).
	 * @param f
	 * @param v
	 * @return
	 */
	public static <T> SQLFunction<Boolean> IFNULL(final Field<? extends T> f, final T v) {
		final Object[] oa = new Object[] {f, v};
		return new Custom<Boolean>(", ", "ifnull", "isnull", "ifnull", "COALESCE", oa);
	}

	/**
	 * Create a SQL function for IFNULL() (ISNULL() on SQL Server).
	 * @param f
	 * @param v
	 * @return
	 */
	public static <T> SQLFunction<Boolean> IFNULL(final SQLFunction<? extends T> f, final T v) {
		final Object[] oa = new Object[] {f, v};
		return new Custom<Boolean>(", ", "ifnull", "isnull", "ifnull", "coalesce", oa);
	}

	/**
	 * @return the sql NOW() function (or GETDATE() on sql server)
	 */
	public static SQLFunction<java.sql.Date> NOW() {
		final Object[] oa = new Object[] {};
		return new Custom<java.sql.Date>(", ", "now", "getdate", "now", "now", oa);
	}

	/**
	 * GETDATE and GETUTCDATE functions both return the current date and time. However,
	 * GETUTCDATE returns the current Universal Time Coordinate (UTC) time, whereas
	 * GETDATE returns the date and time on the computer where SQL Server is running.
	 * @return same as NOW()
	 */
	public static SQLFunction<java.sql.Date> GETDATE() {
		return NOW();
	}

	/**
	 * GETDATE and GETUTCDATE functions both return the current date and time. However,
	 * GETUTCDATE returns the current Universal Time Coordinate (UTC) time, whereas
	 * GETDATE returns the date and time on the computer where SQL Server is running.
	 * @return the sql GETUTCDATE() function
	 */
	public static SQLFunction<java.sql.Date> GETUTCDATE() {
		return new Custom<java.sql.Date>(", ", "GETUTCDATE", "GETUTCDATE", "GETUTCDATE");
	}

	/**
	 * COALESCE is a sql function that will return the first non-null parameter value.
	 * All objects other than functions or fields are passed verbatim to the
	 * PreparedStatement with setObject().
	 * @param fields
	 * @return
	 */
	public static <T> SQLFunction<T> COALESCE(final Object... fields) {
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
	public static <T> SQLFunction<java.sql.Date> DATEADD(final SQLFunction<? extends T> f1, final int count, final CALENDAR component) {
		return new SQLFunction<java.sql.Date>() {
			@Override
			public void __getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
				final DB_TYPE dbType = context==null ? null : context.dbType;
				if (dbType == DB_TYPE.MYSQL) {
					sb.append("date_add(");
					f1.__getSQL(sb, bindings, context);
					sb.append(", interval ? "+ component +")");
					bindings.add(count);
				} else if (dbType == DB_TYPE.HSQL || dbType == DB_TYPE.DERBY) {
					sb.append("TIMESTAMPADD(SQL_TSI_" + component +", ?, ");
					bindings.add(count);
					f1.__getSQL(sb, bindings, context);
					sb.append(")");
				} else if (dbType == DB_TYPE.POSTGRES) {
					f1.__getSQL(sb, bindings, context);
					sb.append(" + INTERVAL '"+ count +" " + component +"'");
				} else if (dbType == DB_TYPE.ORACLE) {
					f1.__getSQL(sb, bindings, context);
					sb.append(" + INTERVAL '"+ count +"' " + component);
				} else {
					sb.append("dateadd(" + component +", ?, ");
					bindings.add(count);
					f1.__getSQL(sb, bindings, context);
					sb.append(")");
				}
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
	public static <T> SQLFunction<java.sql.Date> DATEADD(final Field<? extends T> field, final int count, final CALENDAR component) {
		return new SQLFunction<java.sql.Date>() {
			@Override
			public void __getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
				final String sql = Util.derefField(field, context);
				final DB_TYPE dbType = context==null ? null : context.dbType;
				if (dbType == DB_TYPE.MYSQL) {
					sb.append("date_add(" + sql +", interval ? "+ component +")");
					bindings.add(count);
				} else if (dbType == DB_TYPE.HSQL) {
					sb.append("TIMESTAMPADD(SQL_TSI_" + component +", ?, "+ sql +")");
					bindings.add(count);
				} else if (dbType == DB_TYPE.DERBY) {
					sb.append("CAST({fn TIMESTAMPADD(SQL_TSI_" + component +", ?, "+ sql +")} as DATE)");
					bindings.add(count);
				} else if (dbType == DB_TYPE.POSTGRES) {
					sb.append(sql +" + INTERVAL '"+ count +" " + component +"'");
				} else if (dbType == DB_TYPE.ORACLE) {
					sb.append(sql +" + INTERVAL '"+ count +"' " + component);
				} else {
					sb.append("dateadd(" + component +", ?, "+ sql +")");
					bindings.add(count);
				}
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
	public static SQLFunction<Integer> DATEPART(final CALENDAR component, final SQLFunction<?> f) {
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
	public static SQLFunction<Integer> DATEPART(final CALENDAR component, final Field<?> f) {
		return new Custom<Integer>("DATEPART", component, f);
	}

	/**
	 * DAY, MONTH and YEAR functions are deterministic. Each of these accepts a single date
	 * value as a parameter and returns respective portions of the date as an integer.
	 * @param f
	 * @return
	 */
	public static SQLFunction<Integer> DAY(final Field<?> f) {
		return new Custom<Integer>("DAY", f);
	}

	/**
	 * DAY, MONTH and YEAR functions are deterministic. Each of these accepts a single date
	 * value as a parameter and returns respective portions of the date as an integer.
	 * @param f
	 * @return
	 */
	public static SQLFunction<Integer> MONTH(final Field<?> f) {
		return new Custom<Integer>("MONTH", f);
	}

	/**
	 * The lowercase value of a field.
	 * @param f
	 * @return
	 */
	public static <S> SQLFunction<S> LOWER(final Field<S> f) {
		return new Custom<S>("LOWER", f);
	}

	/**
	 * The uppercase value of a field.
	 * @param f
	 * @return
	 */
	public static <S> SQLFunction<S> UPPER(final Field<S> f) {
		return new Custom<S>("UPPER", f);
	}

	/**
	 * DAY, MONTH and YEAR functions are deterministic. Each of these accepts a single date
	 * value as a parameter and returns respective portions of the date as an integer.
	 * @param f
	 * @return
	 */
	public static SQLFunction<Integer> YEAR(final Field<?> f) {
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
	public static SQLFunction<Integer> DATEDIFF(final CALENDAR component, final Field<?> f1, final Field<?> f2) {
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
	public static SQLFunction<Integer> DATEDIFF(final CALENDAR component, final Field<?> f1, final SQLFunction<?> f2) {
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
	public static SQLFunction<Integer> DATEDIFF(final CALENDAR component, final SQLFunction<?> f1, final Field<?> f2) {
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
	public static SQLFunction<Integer> DATEDIFF(final CALENDAR component, final SQLFunction<?> f1, final SQLFunction<?> f2) {
		return new Custom<Integer>("DATEDIFF", component, f1, f2);
	}

	/**
	 * The CONCAT() function is used to concatenate the values of any number of
	 * string arguments passed to it.
	 * @param fields
	 * @return
	 */
	public static SQLFunction<String> CONCAT(final Object... fields) {
		return new SQLFunction<String>() {
			@Override
			public void __getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
				final DB_TYPE dbType = context==null ? null : context.dbType;
				if (dbType == DB_TYPE.SQLSERVER) {
					new Custom<String>(" + ", null, null, null, fields).__getSQL(sb, bindings, context);
				} else if (dbType == DB_TYPE.DERBY) {
					sb.append("CAST(");
					new Custom<String>(" || ", null, null, null, fields).__getSQL(sb, bindings, context);
					sb.append(" as VARCHAR(32672))");
				} else {
					new Custom<String>("CONCAT", fields).__getSQL(sb, bindings, context);
				}
			}
		};
	}

	/**
	 * The COUNT(column_name) function.
	 * @param f
	 * @return
	 */
	public static SQLFunction<Integer> COUNT(final Field<?> f) {
		return new Custom<Integer>(Integer.class, "COUNT", f);
	}

	/**
	 * The COUNT(1) function;
	 * @param f
	 * @return
	 */
	public static SQLFunction<Integer> COUNT(int i) {
		SQLFunction<Integer> ret = _COUNT_INDEX.get(i);
		if (ret == null) {
			ret = new Custom<Integer>(Integer.class, "COUNT", new SQLLiteral(i));
			_COUNT_INDEX.put(i, ret);
		}
		return ret;
	}
	private static final Map<Integer,SQLFunction<Integer>> _COUNT_INDEX = Collections.synchronizedMap(new HashMap<Integer,SQLFunction<Integer>>());

	/**
	 * The COUNT(*) function.  String argument must equal "*".
	 * @param f
	 * @return
	 */
	public static SQLFunction<Integer> COUNT(CharSequence s) {
		if (!"*".equals(s)) throw new IllegalArgumentException("argument must equal '*'");
		return _COUNT_STAR;
	}
	private static final SQLFunction<Integer> _COUNT_STAR = new Custom<Integer>(Integer.class, "COUNT", "*");



	/* ======================================================================== */



	/**
	 * The list of built-in functions is far from comprehensive.
	 * Use this to implement your own one-off functions.
	 * Please submit functions you think are useful back to the project!
	 */
	public static class Custom<T> extends SQLFunction<T> {

		private final String mysql;
		private final String sqlserver;
		private final String hsql;
		private final String postgres;
		private final String oracle;
		private final String derby;
		private Object[] objects = null;
		private final String sql = null;
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
			this.postgres = func;
			this.oracle = func;
			this.derby = func;
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
			this.postgres = func;
			this.oracle = func;
			this.derby = func;
			this.objects = objects;
		}

		/**
		 * For functions that take arguments.  The first string is the function name.
		 * The remaining parameters are passed as arguments.
		 * If an argument is a field it is referenced to a table in the from clause.
		 * For all others, the object is passed verbatim to the PreparedStatement with setObject().
		 * @param func the name of the function
		 * @param objects the arguments of the function
		 */
		public Custom(Class<T> type, final String func, final Object... objects) {
			this.type = type;
			this.mysql = func;
			this.sqlserver = func;
			this.hsql = func;
			this.postgres = func;
			this.oracle = func;
			this.derby = func;
			this.objects = objects;
		}

		public Custom(Class<T> type, final String func, final Object o) {
			this.type = type;
			this.mysql = func;
			this.sqlserver = func;
			this.hsql = func;
			this.postgres = func;
			this.oracle = func;
			this.derby = func;
			this.objects = new Object[] {o};
		}

		Custom(final String mysql, final String sqlserver, final String hsql) {
			this.mysql = mysql;
			this.sqlserver = sqlserver;
			this.hsql = hsql;
			this.postgres = null;
			this.oracle = null;
			this.derby = null;
		}

		Custom(final String sep, final String mysql, final String sqlserver, final String hsql, final Object[] objects) {
			this.sep = sep;
			this.mysql = mysql;
			this.sqlserver = sqlserver;
			this.hsql = hsql;
			this.postgres = null;
			this.oracle = null;
			this.derby = null;
			this.objects  = objects;
		}

		Custom(final String sep, final String mysql, final String sqlserver, final String hsql, final String postgres, final String oracle, final Object[] objects) {
			this.sep = sep;
			this.mysql = mysql;
			this.sqlserver = sqlserver;
			this.hsql = hsql;
			this.postgres = postgres;
			this.oracle = oracle;
			this.derby = null;
			this.objects  = objects;
		}

		Custom(final String sep, final String mysql, final String sqlserver, final String hsql, final String postgres, final String oracle, final String derby, final Object[] objects) {
			this.sep = sep;
			this.mysql = mysql;
			this.sqlserver = sqlserver;
			this.hsql = hsql;
			this.postgres = postgres;
			this.oracle = oracle;
			this.derby = derby;
			this.objects  = objects;
		}

		Custom(final Object o1, final String sep, final Object o2) {
			this.sqlserver = null;
			this.hsql = null;
			this.mysql = null;
			this.postgres = null;
			this.oracle = null;
			this.derby = null;
		this.sep = sep;
			this.objects = new Object[] {o1, o2};
		}

		@Override
		public void __getSQL(final StringBuffer sb, final List<Object> bindings, final SqlContext context) {
			final DB_TYPE dbType = context==null ? null : context.dbType;
			if (dbType != null) {
				switch (dbType) {
				case MYSQL:		sb.append(mysql==null ? "" : mysql); break;
				case SQLSERVER:	sb.append(sqlserver==null ? "" : sqlserver); break;
				case HSQL:		sb.append(hsql==null ? "" : hsql); break;
				case SQLITE3:		sb.append(hsql==null ? "" : hsql); break;
				case POSTGRES:		sb.append(postgres==null ? "" : postgres); break;
				case ORACLE:		sb.append(oracle==null ? "" : oracle); break;
				case DERBY:		sb.append(derby==null ? "" : derby); break;
				default: throw new RuntimeException("unknown DB_TYPE "+ dbType);
				}
			} else {
				sb.append(hsql!=null ? hsql : mysql!=null ? mysql : sqlserver!=null ? sqlserver : "<UNK DB_TYPE>");
			}
			sb.append("(");
			if (objects != null) {
				for (int i=0; i<objects.length; ++i) {
					final Object o = objects[i];
					if (o instanceof Field<?>) {
						sb.append(Util.derefField((Field<?>) o, context));
					} else if (o instanceof SQLFunction<?>) {
						final SQLFunction<?> f = (SQLFunction<?>) o;
						f.__getSQL(sb, bindings, context);
					} else if (o instanceof SQLLiteral) {
						final SQLLiteral f = (SQLLiteral) o;
						sb.append(f.sql);
					} else if (o instanceof CALENDAR) {
						if (context != null && context.dbType == DB_TYPE.HSQL) {
							sb.append("'"+ o.toString().toLowerCase() +"'");
						} else {
							sb.append(o.toString());
						}
					} else if ("*".equals(o)) {
						sb.append("*");
					} else {
						sb.append("?");
						bindings.add(o);
					}
					if (i < objects.length-1) sb.append(sep);
				}
			}
			sb.append(")");
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((bindings == null) ? 0 : bindings.hashCode());
			result = prime * result + ((hsql == null) ? 0 : hsql.hashCode());
			result = prime * result + ((mysql == null) ? 0 : mysql.hashCode());
			result = prime * result + Arrays.hashCode(objects);
			result = prime * result + ((oracle == null) ? 0 : oracle.hashCode());
			result = prime * result + ((postgres == null) ? 0 : postgres.hashCode());
			result = prime * result + ((sep == null) ? 0 : sep.hashCode());
			result = prime * result + ((sql == null) ? 0 : sql.hashCode());
			result = prime * result + ((sqlserver == null) ? 0 : sqlserver.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			Custom other = (Custom) obj;
			if (bindings == null) {
				if (other.bindings != null)
					return false;
			} else if (!bindings.equals(other.bindings))
				return false;
			if (hsql == null) {
				if (other.hsql != null)
					return false;
			} else if (!hsql.equals(other.hsql))
				return false;
			if (mysql == null) {
				if (other.mysql != null)
					return false;
			} else if (!mysql.equals(other.mysql))
				return false;
			if (!Arrays.equals(objects, other.objects))
				return false;
			if (oracle == null) {
				if (other.oracle != null)
					return false;
			} else if (!oracle.equals(other.oracle))
				return false;
			if (postgres == null) {
				if (other.postgres != null)
					return false;
			} else if (!postgres.equals(other.postgres))
				return false;
			if (sep == null) {
				if (other.sep != null)
					return false;
			} else if (!sep.equals(other.sep))
				return false;
			if (sql == null) {
				if (other.sql != null)
					return false;
			} else if (!sql.equals(other.sql))
				return false;
			if (sqlserver == null) {
				if (other.sqlserver != null)
					return false;
			} else if (!sqlserver.equals(other.sqlserver))
				return false;
			return true;
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
	public Condition eq(final SQLFunction<?> v) {
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
	public Condition neq(final SQLFunction<?> v) {
		return new Binary2(this, "!=", v);
	}

	/**
	 * Creates a condition representing this function "like" the literal value of the parameter.
	 * Interpretation varies by database.
	 * @param v
	 * @return
	 */
	public Condition like(final T v) {
		return new Binary2(this, " like ", v);
	}

	/**
	 * Creates a condition representing this function "like" the value of the given SQL function.
	 * Interpretation varies by database.
	 * @param v
	 * @return
	 */
	public Condition like(final Expression<T> v) {
		return new Binary2(this, " like ", v);
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
	public Condition lt(final SQLFunction<?> v) {
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
	public Condition lte(final SQLFunction<?> v) {
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
	public Condition gt(final SQLFunction<?> v) {
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
	public Condition gte(final SQLFunction<?> v) {
		return new Binary2(this, ">=", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> add(final T v) {
		return new SQLFunction.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> add(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> add(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> sub(final T v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> sub(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> sub(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mul(final T v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mul(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mul(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> div(final T v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> div(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> div(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mod(final T v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mod(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mod(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	public SQLFunction<T> subtract(final T v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	public SQLFunction<T> subtract(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	public SQLFunction<T> subtract(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	public SQLFunction<T> multiply(final T v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	public SQLFunction<T> multiply(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	public SQLFunction<T> multiply(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	public SQLFunction<T> divide(final T v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	public SQLFunction<T> divide(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	public SQLFunction<T> divide(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	public SQLFunction<T> modulus(final T v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	public SQLFunction<T> modulus(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this function.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	public SQLFunction<T> modulus(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}
	
	static class SQLLiteral {
		final String sql;
		SQLLiteral(String sql) {
			this.sql = sql;
		}
		public SQLLiteral(int i) {
			sql = Integer.toString(i);
		}
	}
	
	static class OrderBySQLFunction<T> implements Expression.OrderBy<T> {

		final SQLFunction<T> underlying;
		final DIRECTION direction;

		OrderBySQLFunction(SQLFunction<T> sqlFunction, DIRECTION direction) {
			underlying = sqlFunction;
			this.direction = direction;
		}

		@Override
		public Expression.OrderBy<T> asc() {
			return new OrderBySQLFunction<T>(underlying, DIRECTION.ASCENDING);
		}

		@Override
		public Expression.OrderBy<T> desc() {
			return new OrderBySQLFunction<T>(underlying, DIRECTION.DESCENDING);
		}
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SQLFunction other = (SQLFunction) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.getName().equals(other.type==null ? null : other.type.getName()))
			return false;
		return true;
	}
	
	

}
