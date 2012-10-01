package org.nosco;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.nosco.Condition.Binary;
import org.nosco.Condition.Ternary;
import org.nosco.Constants.DB_TYPE;
import org.nosco.Table.__SimplePrimaryKey;


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
		if (boundTable == null) return NAME;
		return boundTable +"."+ NAME;
	}

	protected String getSQL(final SqlContext context) {
		final StringBuffer sb = new StringBuffer();
		getSQL(sb, context);
		return sb.toString();
	}

	protected void getSQL(final StringBuffer sb, final SqlContext context) {
		getSQL(sb, context.dbType);
	}

	protected void getSQL(final StringBuffer sb, final Constants.DB_TYPE dbType) {
		final Set<String> kws = null;
		String name = NAME;
		if (dbType == Constants.DB_TYPE.SQLSERVER) {
			name = Constants.KEYWORDS_SQLSERVER.contains(NAME.toLowerCase()) ? "["+NAME+"]" : NAME;
		} else if (dbType == Constants.DB_TYPE.MYSQL) {
			name = Constants.KEYWORDS_MYSQL.contains(NAME.toLowerCase()) ? "`"+NAME+"`" : NAME;
		} else if (dbType == Constants.DB_TYPE.SQL92) {
			name = Constants.KEYWORDS_SQL92.contains(NAME.toLowerCase()) ? "\""+NAME+"\"" : NAME;
		}
		if (boundTable != null) {
			sb.append(boundTable).append(".");
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

	String boundTable = null;
	Field<T> unBound = null;

	public Field(final int index, final Class<? extends Table> table, final String name, final String javaName, final Class<T> type, final String sqlType) {
		INDEX = index;
		TABLE = table;
		NAME = name;
		TYPE = type;
		SQL_TYPE = sqlType;
		JAVA_NAME = javaName;
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
	public Condition eq(final Function v) {
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
	public Condition neq(final Function v) {
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
	public Condition lt(final Function v) {
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
	public Condition lte(final Function v) {
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
	public Condition gt(final Function v) {
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
	public Condition gte(final Function v) {
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
	public Condition between(final T v1, final Function v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final Function v1, final T v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final Function v1, final Function v2) {
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
	public Condition between(final Field v1, final Function v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(final Function v1, final Field v2) {
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
	 * Creates a condition representing this field being a member of the given set.
	 * @param set
	 * @return
	 */
	public Condition in(final T... set) {
		return new Condition.In(this, " in ", set);
	}

	/**
	 * Creates a condition representing this field being a member of the given set.
	 * @param set
	 * @return
	 */
	public Condition in(final Field<?>... fields) {
		return new Condition.In(this, " in ", (Object[]) fields);
	}

	/**
	 * Creates a condition representing this field being a member of the given set.
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
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> add(final T v) {
		return new Function.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> add(final Field<T> v) {
		return new Function.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> add(final Function v) {
		return new Function.Custom<T>(this, "+", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> sub(final T v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> sub(final Field<T> v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> sub(final Function v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> mul(final T v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> mul(final Field<T> v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> mul(final Function v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> div(final T v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> div(final Field<T> v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> div(final Function v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> mod(final T v) {
		return new Function.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> mod(final Field<T> v) {
		return new Function.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 */
	public Function<T> mod(final Function v) {
		return new Function.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	public Function<T> subtract(final T v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	public Function<T> subtract(final Field<T> v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use sub()
	 */
	public Function<T> subtract(final Function v) {
		return new Function.Custom<T>(this, "-", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	public Function<T> multiply(final T v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	public Function<T> multiply(final Field<T> v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mul()
	 */
	public Function<T> multiply(final Function v) {
		return new Function.Custom<T>(this, "*", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	public Function<T> divide(final T v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	public Function<T> divide(final Field<T> v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use div()
	 */
	public Function<T> divide(final Function v) {
		return new Function.Custom<T>(this, "/", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	public Function<T> modulus(final T v) {
		return new Function.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	public Function<T> modulus(final Field<T> v) {
		return new Function.Custom<T>(this, "%", v);
	}

	/**
	 * Performs a mathematical function on this field.
	 * @param v
	 * @return
	 * @deprecated use mod()
	 */
	public Function<T> modulus(final Function v) {
		return new Function.Custom<T>(this, "%", v);
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
			final Field[] fields = q.getSelectFields();
			if (fields.length != 1) throw new RuntimeException("cannot select more than one field in an inner query.");
			final Collection values = new ArrayList();
			for (final Table t : q) {
				values.add(t.get(fields[0]));
			}
			return in(values);
		}
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

		@SuppressWarnings("rawtypes")
		private final Field[] FIELDS;

		public PK(@SuppressWarnings("rawtypes") final Field... fields) {
			FIELDS = new Field[fields.length];
			System.arraycopy(fields, 0, FIELDS, 0, fields.length);
		}

		@SuppressWarnings("rawtypes")
		public Field[] GET_FIELDS() {
			final Field[] fields = new Field[FIELDS.length];
			System.arraycopy(FIELDS, 0, fields, 0, FIELDS.length);
			return fields;
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

	boolean isBound() {
		return this.boundTable != null;
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

}
