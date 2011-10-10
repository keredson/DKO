package org.nosco;

import java.sql.SQLException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import org.nosco.Field.PK;



public abstract class Table {
	
	public abstract String SCHEMA_NAME();

	public abstract String TABLE_NAME();

	@SuppressWarnings("rawtypes")
	public abstract Field[] FIELDS();

	public abstract Field.PK PK();

	public abstract Field.FK[] FKS();

	protected BitSet FETCHED_VALUES = new BitSet();
	protected BitSet UPDATED_VALUES = null;
	
/*	protected Iterable<? extends Table> GET_FKED_VALUES(Field field) {
		Field pk = GET_PRIMARY_KEY_FIELD();
		Object pkv = GET_VALUE(pk);
		return new QueryImpl<Table>(field.TABLE).filter(field, pkv);
	} //*/

/*	protected void PUT_VALUE(Field field, Object value) {
		if (field.PK) {
			throw new RuntimeException("can't update the PK field "+ field);
		}
		if (updatedValues==null) updatedValues = new HashMap<Field,Object>();
		updatedValues.put(field,value);
	}//*/
	
	public boolean dirty() {
		return UPDATED_VALUES != null && !UPDATED_VALUES.isEmpty();
	}

	public abstract boolean save() throws SQLException;
	/*{
		if (!dirty()) return;
		Query<Table> query = new QueryImpl<Table>(this);
		for (Field f : GET_FIELDS()) {
			if (UPDATED_VALUES.get(f.INDEX)) {
				query.set(f, value)
			}
		}
		if (weShouldUpdate) {
			for (Field pk : GET_FIELDS()) {
				if (!pk.PK) continue;
				query = query.filter(pk.eq(knownValues.get(pk)));
			}
			query.update();
		} else {
			Object pkv = query.insert();
			knownValues = updatedValues;
			updatedValues = null;
			for (Field pk : GET_FIELDS()) {
				if (!pk.PK) continue;
				knownValues.put(pk, pkv);
				break;
			}
		}
	}//*/
	
	static Map<Table,java.lang.reflect.Field> _pkCache = new HashMap<Table, java.lang.reflect.Field>();
	static Field.PK GET_TABLE_PK(Table table) {
		if (!_pkCache.containsKey(table)) {
			java.lang.reflect.Field field = null;
			try {
				field = table.getClass().getDeclaredField("PK");
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			_pkCache.put(table, field);
		}
		try {
			return (PK) _pkCache.get(table).get(table);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	boolean sameTable(Table t) {
		return t.SCHEMA_NAME() == SCHEMA_NAME() && t.TABLE_NAME() == TABLE_NAME();
	}
	
}
