package org.nosco;

import java.util.ArrayList;
import java.util.List;

import org.nosco.QueryImpl.TableInfo;
import org.nosco.util.Misc;

class Util {

	public static String derefField(Field<?> field, SqlContext context) {
		if (field.isBound()) return field.toString();
		List<String> selectedTables = new ArrayList<String>();
		List<TableInfo> unboundTables = new ArrayList<TableInfo>();
		SqlContext tmp = context;
		while (tmp != null) {
			for (TableInfo info : tmp.tableInfos) {
				selectedTables.add(info.table.SCHEMA_NAME() +"."+ info.table.TABLE_NAME());
				if (info.nameAutogenned && field.TABLE.isInstance(info.table)) {
					unboundTables.add(info);
				}
			}
			tmp = tmp.parentContext;
		}
		if (unboundTables.size() < 1) {
			throw new RuntimeException("field "+ field +
					" is not from one of the selected tables {"+
					Misc.join(",", selectedTables) +"}");
		} else if (unboundTables.size() > 1) {
			List<String> x = new ArrayList<String>();
			for (TableInfo info : unboundTables) {
				x.add(info.table.SCHEMA_NAME() +"."+ info.table.TABLE_NAME());
			}
			throw new RuntimeException("field "+ field +
					" is ambigious over the tables {"+ Misc.join(",", x) +"}");
		} else {
			TableInfo theOne = unboundTables.iterator().next();
			return (theOne.tableName == null ? theOne.table.TABLE_NAME() : theOne.tableName)
					+ "."+ field;
		}
	}


}
