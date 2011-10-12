package org.nosco;

import java.util.Collection;

import org.nosco.Condition.Binary;
import org.nosco.Condition.Ternary;


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

	private String boundTable = null;

	public Field(int index, Class<? extends Table> table, String name, Class<T> type) {
		INDEX = index;
		TABLE = table;
		NAME = name;
		TYPE = type;
	}

	public Condition eq(T v) {
		return new Binary(this, "=", v);
	}

	public Condition neq(T v) {
		return new Binary(this, "!=", v);
	}

	public Condition eq(Field<T> v) {
		return new Binary(this, "=", v);
	}

	public Condition neq(Field<T> v) {
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

	public Condition like(T v) {
		return new Binary(this, " like ", v);
	}

	public Condition lt(T v) {
		return new Binary(this, "<", v);
	}

	public Condition lte(T v) {
		return new Binary(this, "<=", v);
	}

	public Condition gt(T v) {
		return new Binary(this, ">", v);
	}

	public Condition gte(T v) {
		return new Binary(this, ">=", v);
	}

	public Condition isNull() {
		return new Condition.Unary(this, " is null");
	}

	public Condition isNotNull() {
		return new Condition.Unary(this, " is not null");
	}

	public Condition between(T v1, T v2) {
		return new Ternary(this, " between ", v1, " and ",  v2);
	}

	public Condition in(T... set) {
		return new Condition.In(this, " in ", set);
	}

	public Condition in(Collection<T> set) {
		return new Condition.In(this, " in ", set);
	}

	public Condition in(Query q) {
		return new Binary(this, " in ", q);
	}

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
