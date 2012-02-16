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
	
	public boolean dirty() {
		return UPDATED_VALUES != null && !UPDATED_VALUES.isEmpty();
	}

	public abstract boolean save() throws SQLException;
	
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
	
	public boolean sameTable(Table t) {
		return t.SCHEMA_NAME() == SCHEMA_NAME() && t.TABLE_NAME() == TABLE_NAME();
	}
	
}
