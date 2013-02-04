package org.kered.dko;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.kered.dko.Condition.Binary;
import org.kered.dko.Condition.Ternary;
import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Table.__SimplePrimaryKey;


/**
 * This class represents a column in a table. &nbsp; It it typed to the type of the column in the database. &nbsp;
 * They are generally found as auto-generated static fields in the classes representing tables, and
 * primarily used to create {@code Condition}s for {@code Query} objects. &nbsp; For example, if your database
 * table looked like this:
 * <table border="1" cellpadding="4" style="margin-left: 2em;">
 * <tr><th colspan="2">some_class</th></tr>
 * <tr><th>id</th><th>name</th></tr>
 * <tr><td>123</td><td>my name</td></tr>
 * <tr><td>456</td><td>your name</td></tr>
 * </table>
 * <p>
 * The generated class would look like this (simplified):
 *
 * <pre>  {@code public class SomeClass extends Table {
 *     static Query<SomeClass> ALL = new Query<SomeClass>();
 *     static Field<Integer> ID = new Field<Integer>();
 *     static Field<String> NAME = new Field<String>();
 *  }}</pre>
 *
 *  You could then write code like this:
 *  <pre>  {@code Condition c = SomeClass.ID.eq(123);
 *  for (SomeClass x : SomeClass.ALL.where(c))
 *    System.out.println(x);}
 *  </pre>
 *
 *  Note: &nbsp; I wrote these methods to all be short in length.  "eq" instead of "equals". &nbsp;
 *  This is because I feel the common use case will be to chain multiple of these together. &nbsp;
 *  Java is already quite verbose and I thought this API would be less useful if it almost always
 *  created really long lines of Java code.
 *
 * @author Derek Anderson
 * @param <T> the field type
 */
public class Field<T> implements Cloneable {

	@Override
	public String toString() {
		//throw new RuntimeException("don't use this to gen sql - use Field.getSQL(...)");
		if (boundTable == null) return TABLE.getSimpleName() +"."+ JAVA_NAME;
		return boundTable +"."+ NAME;
	}

	protected String getSQL(final SqlContext context) {
		final StringBuffer sb = new StringBuffer();
		getSQL(sb, context);
		return sb.toString();
	}

	protected String getSQL(final SqlContext context, List<Object> bindings) {
		final StringBuffer sb = new StringBuffer();
		getSQL(sb, bindings, context);
		return sb.toString();
	}

	protected void getSQL(final StringBuffer sb, final SqlContext context) {
		if (context!=null && context.fieldNameOverrides!=null && context.fieldNameOverrides.containsKey(this)) {
			sb.append(context.fieldNameOverrides.get(this));
		}
		getSQL(sb, context==null ? Constants.DB_TYPE.SQL92 : context.dbType);
	}

	protected void getSQL(StringBuffer sb, List<Object> bindings, SqlContext context) {
		getSQL(sb, context);
	}

	protected void getSQL(final StringBuffer sb, final Constants.DB_TYPE dbType) {
		final Set<String> kws = null;
		String name = NAME;
		if (dbType == Constants.DB_TYPE.SQLSERVER) {
			name = Constants.KEYWORDS_SQLSERVER.contains(NAME.toLowerCase()) || NAME.matches(".*\\W+.*") ? "["+NAME+"]" : NAME;
		} else if (dbType == Constants.DB_TYPE.MYSQL) {
			name = Constants.KEYWORDS_MYSQL.contains(NAME.toLowerCase()) || NAME.matches(".*\\W+.*") ? "`"+NAME+"`" : NAME;
		} else if (dbType == Constants.DB_TYPE.SQL92) {
			name = Constants.KEYWORDS_SQL92.contains(NAME.toLowerCase()) || NAME.matches(".*\\W+.*") ? "\""+NAME+"\"" : NAME;
		}
		if (boundTable != null) {
			sb.append(boundTable).append(".");
		}
		if (boundTableInfo != null) {
			sb.append(boundTableInfo.tableName).append(".");
		}
		sb.append(name);
	}

	protected String getSQL(final DB_TYPE dbType) {
		final StringBuffer sb = new StringBuffer();
		getSQL(sb, dbType);
		return sb.toString();
	}


	public final int INDEX;
	public final Class<? extends Table> TABLE;
	public final String NAME;
	public final Class<T> TYPE;
	final String SQL_TYPE;
	final String JAVA_NAME;
	public final Method GETTER;

	String boundTable = null;
	Field<T> unBound = null;
	Field<T> underlying = null;
	Set<Tag<? extends T>> tags = null;
	private TableInfo boundTableInfo = null;

	@Deprecated
	public Field(final int index, final Class<? extends Table> table, final String name, final String javaName, final Class<T> type, final String sqlType) {
		INDEX = index;
		TABLE = table;
		NAME = name;
		TYPE = type;
		SQL_TYPE = sqlType;
		JAVA_NAME = javaName;
		GETTER = null;
	}

	public Field(final int index, final Class<? extends Table> table, final String name, final String javaName, final String methodName, final Class<T> type, final String sqlType) {
		INDEX = index;
		TABLE = table;
		NAME = name;
		TYPE = type;
		SQL_TYPE = sqlType;
		JAVA_NAME = javaName;
		Method getter = null;
		try {
			getter = TABLE.getMethod(methodName, (Class<?>[]) null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		GETTER = getter;
	}

	/**
	 * Use this constructor when defining one-off fields unrelated to an actual database.
	 * @param name
	 * @param type
	 */
	public Field(final String name, final Class<T> type) {
		INDEX = (int) (Math.random() * Integer.MAX_VALUE);
		TABLE = Table.class;
		NAME = name;
		TYPE = type;
		SQL_TYPE = type.getSimpleName();
		JAVA_NAME = name;
		GETTER = null;
	}

	/**
	 * Creates a condition representing this field equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition eq(final T v) {
		return new Binary(this, "=", v);
	}

	/**
	 * Creates a condition representing this field equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition eq(final Field<T> v) {
		return new Binary(this, "=", v);
	}

	public Condition eq(final __SimplePrimaryKey<?,T> v) {
		return eq(v.value());
	}

	/**
	 * Creates a condition representing this field equal to some function.
	 * @param v
	 * @return
	 */
	public Condition eq(final SQLFunction v) {
		return new Binary(this, "=", v);
	}

	/**
	 * Creates a condition representing this field not equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition neq(final T v) {
		return new Binary(this, "!=", v);
	}

	/**
	 * Creates a condition representing this field not equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition neq(final Field<T> v) {
		return new Binary(this, "!=", v);
	}

	/**
	 * Creates a condition representing this field not equal to some function.
	 * @param v
	 * @return
	 */
	public Condition neq(final SQLFunction v) {
		return new Binary(this, "!=", v);
	}

/*	public Condition is(C v) {
		Condition c = Condition.TRUE;
		for (Field f : v.PK().GET_FIELDS()) {
			c.and(new Binary(f, "=", v.GET(f)));
		}
		return new Binary(this, "=", v);
	}

	public Condition nis(C v) {
		return new Binary(this, "!=", v);
	} //*/

	/**
	 * Creates a condition representing this field "like" the literal value of the parameter.
	 * Interpretation varies by database.
	 * @param v
	 * @return
	 */
	public Condition like(final T v) {
		return new Binary(this, " like ", v);
	}

	/**
	 * Creates a condition representing this field less than the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition lt(final T v) {
		return new Binary(this, "<", v);
	}

	/**
	 * Creates a condition representing this field less than some other field.
	 * @param v
	 * @return
	 */
	public Condition lt(final Field<T> v) {
		return new Binary(this, "<", v);
	}

	/**
	 * Creates a condition representing this field less than some function.
	 * @param v
	 * @return
	 */
	public Condition lt(final SQLFunction v) {
		return new Binary(this, "<", v);
	}

	/**
	 * Creates a condition representing this field less than or equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition lte(final T v) {
		return new Binary(this, "<=", v);
	}

	/**
	 * Creates a condition representing this field less than or equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition lte(final Field<T> v) {
		return new Binary(this, "<=", v);
	}

	/**
	 * Creates a condition representing this field less than or equal to some function.
	 * @param v
	 * @return
	 */
	public Condition lte(final SQLFunction v) {
		return new Binary(this, "<=", v);
	}

	/**
	 * Creates a condition representing this field greater than the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition gt(final T v) {
		return new Binary(this, ">", v);
	}

	/**
	 * Creates a condition representing this field greater than some other field.
	 * @param v
	 * @return
	 */
	public Condition gt(final Field<T> v) {
		return new Binary(this, ">", v);
	}

	/**
	 * Creates a condition representing this field greater than some function.
	 * @param v
	 * @return
	 */
	public Condition gt(final SQLFunction v) {
		return new Binary(this, ">", v);
	}

	/**
	 * Creates a condition representing this field greater than or equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition gte(final T v) {
		return new Binary(this, ">=", v);
	}

	/**
	 * Creates a condition representing this field greater than or equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition gte(final Field<T> v) {
		return new Binary(this, ">=", v);
	}

	/**
	 * Creates a condition representing this field greater than or equal to some function.
	 * @param v
	 * @return
	 */
	public Condition gte(final SQLFunction v) {
		return new Binary(this, ">=", v);
	}

	/**
	 * Creates a condition representing this field equal to null.
	 * @return
	 */
	public Condition isNull() {
		return new Condition.Unary(this, " is null");
	}

	/**
	 * Creates a condition representing this field not equal to null.
	 * @return
	 */
	public Condition isNotNull() {
		return new Condition.Unary(this, " is not null");
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final T v1, final T v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final T v1, final SQLFunction v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final SQLFunction v1, final T v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final SQLFunction v1, final SQLFunction v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final Field v1, final Field v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final Field v1, final SQLFunction v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final SQLFunction v1, final Field v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final Field v1, final T v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final T v1, final Field v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing a value between two fields.  Example:
	 * {@code select * from car where 25 between city_mpg and highway_mpg}
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static <T> Condition between(final T v1, final Field<T> f1, final Field<T> f2) {
		return new Ternary(v1, " between ", f1, " and ",  f2);
	}

	/**
	 * Creates a condition representing a value between two fields.  Example:
	 * {@code select * from car where 25 between city_mpg and highway_mpg}
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static <T> Condition between(final T v1, final SQLFunction f1, final Field<T> f2) {
		return new Ternary(v1, " between ", f1, " and ",  f2);
	}

	/**
	 * Creates a condition representing a value between two fields.  Example:
	 * {@code select * from car where 25 between city_mpg and highway_mpg}
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static <T> Condition between(final T v1, final Field<T> f1, final SQLFunction f2) {
		return new Ternary(v1, " between ", f1, " and ",  f2);
	}

	/**
	 * Creates a condition representing a value between two fields.  Example:
	 * {@code select * from car where 25 between city_mpg and highway_mpg}
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static <T> Condition between(final T v1, final SQLFunction f1, final SQLFunction f2) {
		return new Ternary(v1, " between ", f1, " and ",  f2);
	}

	/**
	 * Creates a condition representing this field being a member of the given set.
	 * @param set
	 * @return
	 */
	public Condition in(final T... set) {
		return new Condition.In(this, " in ", set);
	}

	/**
	 * Creates a condition representing this field being a member of the given set.
	 * If this collection is large a temporary table is created and joined against.
	 * @param set
	 * @return
	 */
	public Condition in(final Field<?>... fields) {
		return new Condition.In(this, " in ", (Object[]) fields);
	}

	/**
	 * Creates a condition representing this field being a member of the given set.
	 * If this collection is large a temporary table is created and joined against.
	 * @param set
	 * @return
	 */
	public Condition in(final Collection<T> set) {
		if (set.size() < 256) {
			return new Condition.In(this, " in ", set);
		} else {
			return new Condition.InTmpTable<T>(this, set);
		}
	}

	/**
	 * Creates a condition representing this field not being a member of the given set.
	 * @param set
	 * @return
	 */
	public Condition notIn(final T... set) {
		return in(set).not();
	}

	/**
	 * Creates a condition representing this field not being a member of the given set.
	 * If this collection is large a temporary table is created and joined against.
	 * @param set
	 * @return
	 */
	public Condition notIn(final Field<?>... fields) {
		return in(fields).not();
	}

	/**
	 * Creates a condition representing this field not being a member of the given set.
	 * If this collection is large a temporary table is created and joined against.
	 * @param set
	 * @return
	 */
	public Condition notIn(final Collection<T> set) {
		return in(set).not();
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> add(final T v) {
		return new SQLFunction.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> add(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> add(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> sub(final T v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> sub(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> sub(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mul(final T v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mul(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mul(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> div(final T v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> div(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> div(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mod(final T v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mod(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public SQLFunction<T> mod(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	@Deprecated
	public SQLFunction<T> subtract(final T v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	@Deprecated
	public SQLFunction<T> subtract(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	@Deprecated
	public SQLFunction<T> subtract(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	@Deprecated
	public SQLFunction<T> multiply(final T v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	@Deprecated
	public SQLFunction<T> multiply(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	@Deprecated
	public SQLFunction<T> multiply(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	@Deprecated
	public SQLFunction<T> divide(final T v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	@Deprecated
	public SQLFunction<T> divide(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	@Deprecated
	public SQLFunction<T> divide(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	@Deprecated
	public SQLFunction<T> modulus(final T v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	@Deprecated
	public SQLFunction<T> modulus(final Field<T> v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	@Deprecated
	public SQLFunction<T> modulus(final SQLFunction v) {
		return new SQLFunction.Custom<T>(this, "%", v);
	}

	/**
	 * Creates a condition representing this field being a member of the given sub-query. &nbsp;
	 * Note that the given query MUST return only one field (using the {@code Query.onlyFields()})
	 * method), otherwise this will throw a {@code SQLException} at runtime.
	 * @param set
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Condition in(final Query<?> q) {
		if (q instanceof DBQuery) {
			return new Binary(this, " in ", (DBQuery<?>) q);
		} else {
			final List<Field<?>> fields = q.getSelectFields();
			if (fields.size() != 1) throw new RuntimeException("cannot select more than one field in an inner query.");
			final Collection values = new ArrayList();
			for (final Table t : q) {
				values.add(t.get(fields.get(0)));
			}
			return in(values);
		}
	}

	/**
	 * Creates a condition representing this field not being a member of the given sub-query. &nbsp;
	 * Note that the given query MUST return only one field (using the {@code Query.onlyFields()})
	 * method), otherwise this will throw a {@code SQLException} at runtime.
	 * @param set
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Condition notIn(final Query<?> q) {
		return in(q).not();
	}

	/**
	 * Represents a foreign key constraint.
	 * @author Derek Anderson
	 */
	public static class FK<S extends Table> {

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append("FK[");
			sb.append(referencing.getName());
			sb.append("(");
			sb.append(Util.join(",", REFERENCING_FIELDS));
			sb.append(")");
			sb.append(" => ");
			sb.append(referenced.getName());
			sb.append("(");
			sb.append(Util.join(",", REFERENCED_FIELDS));
			sb.append(")");
			sb.append("]");
			return sb.toString();
		}

		public final int INDEX;
		@SuppressWarnings("rawtypes")
		private final Field[] REFERENCING_FIELDS;
		@SuppressWarnings("rawtypes")
		private final Field[] REFERENCED_FIELDS;
		final Class<? extends Table> referencing;
		final Class<? extends Table> referenced;
		final String name;

		public FK(final String name, final int index, final Class<? extends Table> referencing, final Class<? extends Table> referenced, @SuppressWarnings("rawtypes") final Field... fields) {
			this.name = name;
			INDEX = index;
			this.referencing = referencing;
			this.referenced = referenced;
			assert fields.length%2 == 0;
			final int c = fields.length / 2;
			REFERENCING_FIELDS = new Field[c];
			REFERENCED_FIELDS = new Field[c];
			System.arraycopy(fields, 0, REFERENCING_FIELDS, 0, c);
			System.arraycopy(fields, c, REFERENCED_FIELDS, 0, c);
		}

		@SuppressWarnings("rawtypes")
		public Field[] REFERENCING_FIELDS() {
			final Field[] fields = new Field[REFERENCING_FIELDS.length];
			System.arraycopy(REFERENCING_FIELDS, 0, fields, 0, REFERENCING_FIELDS.length);
			return fields;
		}

		@SuppressWarnings("rawtypes")
		public Field[] REFERENCED_FIELDS() {
			final Field[] fields = new Field[REFERENCED_FIELDS.length];
			System.arraycopy(REFERENCED_FIELDS, 0, fields, 0, REFERENCING_FIELDS.length);
			return fields;
		}

		public Condition eq(final Table.__PrimaryKey<S> pk) {
			Condition ret = null;
			for (int i=0; i<REFERENCING_FIELDS.length; ++i) {
				@SuppressWarnings("unchecked")
				final
				Condition c = REFERENCING_FIELDS[i].eq(pk.get(REFERENCED_FIELDS[i]));
				ret = ret == null ? c : ret.and(c);
			}
			return ret;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + INDEX;
			result = prime * result + Arrays.hashCode(REFERENCED_FIELDS);
			result = prime * result + Arrays.hashCode(REFERENCING_FIELDS);
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result
					+ ((referenced == null) ? 0 : referenced.getName().hashCode());
			result = prime * result
					+ ((referencing == null) ? 0 : referencing.getName().hashCode());
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
			final FK other = (FK) obj;
			if (INDEX != other.INDEX)
				return false;
			if (!Arrays.equals(REFERENCED_FIELDS, other.REFERENCED_FIELDS))
				return false;
			if (!Arrays.equals(REFERENCING_FIELDS, other.REFERENCING_FIELDS))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (referenced == null) {
				if (other.referenced != null)
					return false;
			} else if (!referenced.equals(other.referenced))
				return false;
			if (referencing == null) {
				if (other.referencing != null)
					return false;
			} else if (!referencing.equals(other.referencing))
				return false;
			return true;
		}



	}

	/**
	 * Represents a primary key constraint.
	 * @author Derek Anderson
	 */
	public static class PK<S extends Table> {

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append("PK[");
			sb.append(Util.join(",", FIELDS));
			sb.append("]");
			return super.toString();
		}

		private final List<Field<?>> FIELDS;

		public PK(@SuppressWarnings("rawtypes") final Field... fields) {
			final List<Field<?>> tmp = new ArrayList<Field<?>>();
			for (final Field<?> f : fields) {
				tmp.add(f);
			}
			FIELDS = Collections.unmodifiableList(tmp);
		}

		public PK(final Collection<Field<?>> fields) {
			FIELDS = Collections.unmodifiableList(new ArrayList<Field<?>>(fields));
		}

		@SuppressWarnings("rawtypes")
		public List<Field<?>> GET_FIELDS() {
			return FIELDS;
		}

		public Condition eq(final Table.__PrimaryKey<S> pk) {
			Condition ret = null;
			for (@SuppressWarnings("rawtypes") final Field f : GET_FIELDS()) {
				@SuppressWarnings("unchecked")
				final
				Condition c = f.eq(pk.get(f));
				ret = ret == null ? c : ret.and(c);
			}
			return ret;
		}

	}

	public Field<T> from(final String table) {
		try {
			@SuppressWarnings("unchecked")
			final
			Field<T> f = (Field<T>) this.clone();
			f.boundTable = table;
			f.unBound  = isBound() ? this.unBound : this;
			return f;
		} catch (final CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	Field<T>  from(TableInfo tableInfo) {
		try {
			@SuppressWarnings("unchecked")
			final
			Field<T> f = (Field<T>) this.clone();
			f.boundTableInfo = tableInfo;
			f.unBound  = isBound() ? this.unBound : this;
			return f;
		} catch (final CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns a clone of this field tagged with the given object.
	 * @param tag
	 * @return
	 */
	public Field<T> tag(final Tag<? extends T> tag) {
		try {
			@SuppressWarnings("unchecked")
			final Field<T> f = (Field<T>) this.clone();
			if (f.tags == null) f.tags = new HashSet<Tag<? extends T>>();
			else f.tags = new HashSet<Tag<? extends T>>(f.tags);
			f.tags.add(tag);
			f.underlying = this;
			return f;
		} catch (final CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns a clone of this field with the given tag object removed.
	 * @param tag
	 * @return
	 */
	public Field<T> untag(final Tag<? extends T> tag) {
		if (tags == null) return this;
		try {
			@SuppressWarnings("unchecked")
			final Field<T> f = (Field<T>) this.clone();
			f.tags = new HashSet<Tag<? extends T>>(f.tags);
			f.tags.remove(tag);
			if (f.tags.size() == 0) return f.underlying;
			else return f;
		} catch (final CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns true if this field has been tagged by this object;
	 * @param tag
	 * @return
	 */
	public boolean hasTag(final Tag<?> tag) {
		if (tags==null) return false;
		return tags.contains(tag);
	}

	boolean isBound() {
		return this.boundTable!=null || this.boundTableInfo!=null;
	}

	public boolean sameField(final Field<?> other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		if (INDEX != other.INDEX)
			return false;
		if (NAME == null) {
			if (other.NAME != null)
				return false;
		} else if (!NAME.equals(other.NAME))
			return false;
		if (TABLE == null) {
			if (other.TABLE != null)
				return false;
		} else if (!TABLE.equals(other.TABLE))
			return false;
		if (TYPE == null) {
			if (other.TYPE != null)
				return false;
		} else if (!TYPE.equals(other.TYPE))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + INDEX;
		result = prime * result
				+ ((JAVA_NAME == null) ? 0 : JAVA_NAME.hashCode());
		result = prime * result + ((NAME == null) ? 0 : NAME.hashCode());
		result = prime * result
				+ ((SQL_TYPE == null) ? 0 : SQL_TYPE.hashCode());
		result = prime * result + ((TABLE == null) ? 0 : TABLE.getName().hashCode());
		result = prime * result + ((TYPE == null) ? 0 : TYPE.getName().hashCode());
		result = prime * result
				+ ((boundTable == null) ? 0 : boundTable.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((unBound == null) ? 0 : unBound.hashCode());
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
		final Field other = (Field) obj;
		if (INDEX != other.INDEX)
			return false;
		if (JAVA_NAME == null) {
			if (other.JAVA_NAME != null)
				return false;
		} else if (!JAVA_NAME.equals(other.JAVA_NAME))
			return false;
		if (NAME == null) {
			if (other.NAME != null)
				return false;
		} else if (!NAME.equals(other.NAME))
			return false;
		if (SQL_TYPE == null) {
			if (other.SQL_TYPE != null)
				return false;
		} else if (!SQL_TYPE.equals(other.SQL_TYPE))
			return false;
		if (TABLE == null) {
			if (other.TABLE != null)
				return false;
		} else if (!TABLE.equals(other.TABLE))
			return false;
		if (TYPE == null) {
			if (other.TYPE != null)
				return false;
		} else if (!TYPE.equals(other.TYPE))
			return false;
		if (boundTable == null) {
			if (other.boundTable != null)
				return false;
		} else if (!boundTable.equals(other.boundTable))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (unBound == null) {
			if (other.unBound != null)
				return false;
		} else if (!unBound.equals(other.unBound))
			return false;
		return true;
	}
	
	/**
	 * Tags are useful if you have multiple data types that have enough similarity where they can be 
	 * interchanged in certain circumstances but not similar enough to be represented by the same
	 * classes.  For example:  Assume you have a vet office, and you track both customers and pets.
	 * You'll likely have different tables representing each, but you want to have only one 
	 * averageAge() function.  You could do the following:
	 * 
	 * <pre> {@code
	 * final static Tag<Double> AGE = new Tag<Double>();
	 * public double averageAge(Query<?> q) {
	 *     Field<Double> ageField = AGE.findField(q);
	 *     double sum = 0;  int count = 0;
	 *     for (Table t : q) {
	 *         sum += t.get(ageField);  ++count;
	 *     }
	 *     return sum / count;
	 * }}</pre>
	 *  
	 * You can then use it with multiple source queries like this:
	 *  
	 * <pre> {@code
	 * double avgPetAge = averageAge(Pet.ALL.alsoSelect(Pet.AGE.tag(AGE)));
	 * double avgCustAge = averageAge(Customer.ALL.alsoSelect(Customer.AGE.tag(AGE)));
	 * }</pre>
	 *  
	 * @param <S>
	 */
	public static class Tag<S> {
		
		private final Object key;

		/**
		 * Generates a globally unique tag.
		 */
		public Tag() {
			key = UUID.randomUUID();
		}
		
		/**
		 * Generates a tag only as unique as the key you pass it.
		 * (two tags made from the same key have equal hashCode() and equals() methods)
		 * @param key
		 */
		public Tag(Object key) {
			this.key = key;
		}
		
		/**
		 * Searches the given query for a field tagged with this tag.  Returns the field 
		 * if found.  Null if not found.  Throws a RuntimeException if more than one 
		 * field in this query is tagged with this tag. 
		 * @param q
		 * @return
		 */
		public Field<S> findField(Query<?> q) {
			Field<S> ret = null;
			List<Field<?>> fields = q.getSelectFields();
			for (Field<?> field : fields) {
				if (field.hasTag(this)) {
					if (ret!=null) throw new RuntimeException("More than one field in this query has been tagged with this tag: {"+ ret +" && "+ field +"}");
					ret = (Field<S>) field;
				}
			}
			return ret;
		}

		/**
		 * Searches the given query for a field tagged with this tag.
		 * Returns a collection containing all the fields found.
		 * @param q
		 * @return
		 */
		public Collection<Field<S>> findFields(Query<?> q) {
			Collection<Field<S>> ret = new LinkedHashSet<Field<S>>();
			List<Field<?>> fields = q.getSelectFields();
			for (Field<?> field : fields) {
				if (field.hasTag(this)) {
					ret.add((Field<S>) field);
				}
			}
			return ret;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
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
			Tag other = (Tag) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Tag[" + key + "]";
		}
		
	}

}
