package org.nosco;

import java.util.Collection;

import org.nosco.Condition.Binary;
import org.nosco.Condition.Ternary;


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

	public final int INDEX;
	public final Class<? extends Table> TABLE;
	public final String NAME;
	public final Class<T> TYPE;

	String boundTable = null;
	Field<T> unBound = null;

	public Field(int index, Class<? extends Table> table, String name, Class<T> type) {
		INDEX = index;
		TABLE = table;
		NAME = name;
		TYPE = type;
	}

	/**
	 * Creates a condition representing this field equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition eq(T v) {
		return new Binary(this, "=", v);
	}

	/**
	 * Creates a condition representing this field equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition eq(Field<T> v) {
		return new Binary(this, "=", v);
	}

	/**
	 * Creates a condition representing this field equal to some function.
	 * @param v
	 * @return
	 */
	public Condition eq(Function v) {
		return new Binary(this, "=", v);
	}

	/**
	 * Creates a condition representing this field not equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition neq(T v) {
		return new Binary(this, "!=", v);
	}

	/**
	 * Creates a condition representing this field not equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition neq(Field<T> v) {
		return new Binary(this, "!=", v);
	}

	/**
	 * Creates a condition representing this field not equal to some function.
	 * @param v
	 * @return
	 */
	public Condition neq(Function v) {
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
	public Condition like(T v) {
		return new Binary(this, " like ", v);
	}

	/**
	 * Creates a condition representing this field less than the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition lt(T v) {
		return new Binary(this, "<", v);
	}

	/**
	 * Creates a condition representing this field less than some other field.
	 * @param v
	 * @return
	 */
	public Condition lt(Field<T> v) {
		return new Binary(this, "<", v);
	}

	/**
	 * Creates a condition representing this field less than some function.
	 * @param v
	 * @return
	 */
	public Condition lt(Function v) {
		return new Binary(this, "<", v);
	}

	/**
	 * Creates a condition representing this field less than or equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition lte(T v) {
		return new Binary(this, "<=", v);
	}

	/**
	 * Creates a condition representing this field less than or equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition lte(Field<T> v) {
		return new Binary(this, "<=", v);
	}

	/**
	 * Creates a condition representing this field less than or equal to some function.
	 * @param v
	 * @return
	 */
	public Condition lte(Function v) {
		return new Binary(this, "<=", v);
	}

	/**
	 * Creates a condition representing this field greater than the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition gt(T v) {
		return new Binary(this, ">", v);
	}

	/**
	 * Creates a condition representing this field greater than some other field.
	 * @param v
	 * @return
	 */
	public Condition gt(Field<T> v) {
		return new Binary(this, ">", v);
	}

	/**
	 * Creates a condition representing this field greater than some function.
	 * @param v
	 * @return
	 */
	public Condition gt(Function v) {
		return new Binary(this, ">", v);
	}

	/**
	 * Creates a condition representing this field greater than or equal to the literal value of the parameter.
	 * @param v
	 * @return
	 */
	public Condition gte(T v) {
		return new Binary(this, ">=", v);
	}

	/**
	 * Creates a condition representing this field greater than or equal to some other field.
	 * @param v
	 * @return
	 */
	public Condition gte(Field<T> v) {
		return new Binary(this, ">=", v);
	}

	/**
	 * Creates a condition representing this field greater than or equal to some function.
	 * @param v
	 * @return
	 */
	public Condition gte(Function v) {
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
	 * Creates a condition representing this field between the literal values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(T v1, T v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the literal values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(T v1, Function v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the literal values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(Function v1, T v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field between the literal values of the parameters.
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Condition between(Function v1, Function v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	/**
	 * Creates a condition representing this field being a member of the given set.
	 * @param set
	 * @return
	 */
	public Condition in(T... set) {
		return new Condition.In(this, " in ", set);
	}

	/**
	 * Creates a condition representing this field being a member of the given set.
	 * @param set
	 * @return
	 */
	public Condition in(Collection<T> set) {
		return new Condition.In(this, " in ", set);
	}

	/**
	 * Creates a condition representing this field being a member of the given sub-query. &nbsp;
	 * Note that the given query MUST return only one field (using the {@code Query.onlyFields()})
	 * method), otherwise this will throw a {@code SQLException} at runtime.
	 * @param set
	 * @return
	 */
	public Condition in(Query q) {
		return new Binary(this, " in ", q);
	}

	/**
	 * Represents a foreign key constraint.
	 * @author Derek Anderson
	 */
	public static class FK {

		public final int INDEX;
		@SuppressWarnings("rawtypes")
		private final Field[] REFERENCING_FIELDS;
		@SuppressWarnings("rawtypes")
		private final Field[] REFERENCED_FIELDS;
		final Class<? extends Table> referencing;
		final Class<? extends Table> referenced;

		public FK(int index, Class<? extends Table> referencing, Class<? extends Table> referenced, @SuppressWarnings("rawtypes") Field... fields) {
			INDEX = index;
			this.referencing = referencing;
			this.referenced = referenced;
			assert fields.length%2 == 0;
			int c = fields.length / 2;
			REFERENCING_FIELDS = new Field[c];
			REFERENCED_FIELDS = new Field[c];
			System.arraycopy(fields, 0, REFERENCING_FIELDS, 0, c);
			System.arraycopy(fields, c, REFERENCED_FIELDS, 0, c);
		}

		@SuppressWarnings("rawtypes")
		public Field[] REFERENCING_FIELDS() {
			Field[] fields = new Field[REFERENCING_FIELDS.length];
			System.arraycopy(REFERENCING_FIELDS, 0, fields, 0, REFERENCING_FIELDS.length);
			return fields;
		}

		@SuppressWarnings("rawtypes")
		public Field[] REFERENCED_FIELDS() {
			Field[] fields = new Field[REFERENCED_FIELDS.length];
			System.arraycopy(REFERENCED_FIELDS, 0, fields, 0, REFERENCING_FIELDS.length);
			return fields;
		}

	}

	/**
	 * Represents a primary key constraint.
	 * @author Derek Anderson
	 */
	public static class PK {

		@SuppressWarnings("rawtypes")
		private final Field[] FIELDS;

		public PK(@SuppressWarnings("rawtypes") Field... fields) {
			FIELDS = new Field[fields.length];
			System.arraycopy(fields, 0, FIELDS, 0, fields.length);
		}

		@SuppressWarnings("rawtypes")
		public Field[] GET_FIELDS() {
			Field[] fields = new Field[FIELDS.length];
			System.arraycopy(FIELDS, 0, fields, 0, FIELDS.length);
			return fields;
		}

	}

	public Field<T> from(String table) {
		try {
			@SuppressWarnings("unchecked")
			Field<T> f = (Field<T>) this.clone();
			f.boundTable = table;
			f.unBound  = isBound() ? this.unBound : this;
			return f;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	boolean isBound() {
		return this.boundTable != null;
	}

	public boolean sameField(Field<?> other) {
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
