package org.nosco;

import org.nosco.DBQuery.JoinInfo;
import org.nosco.Field.FK;

class TableInfo implements Cloneable {

	Table table = null;
	Class<? extends Table> tableClass = null;
	String tableName = null;
	int start = -1;
	int end = -1;
	FK[] path = null;
	TableInfo fkInfo = null;
	boolean nameAutogenned = false;
	JoinInfo join = null;
	int position = -1;

	public TableInfo(final Table table, final String tableName, final FK[] path) {
		this.table = table;
		this.tableClass = table.getClass();
		this.tableName = tableName;
		this.path  = path;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		final TableInfo x = new TableInfo(table, tableName, path);
		x.tableClass = tableClass;
		x.start = start;
		x.end = end;
		x.nameAutogenned = nameAutogenned;
		return x;
	}

	@Override
	public String toString() {
		return "[TableInfo "+ table +", "+ tableName +", nameAutogenned="+ nameAutogenned +"]";
	}

}