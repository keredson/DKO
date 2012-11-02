package org.kered.dko;

import java.sql.Connection;
import java.sql.SQLException;

import org.kered.dko.DBQuery.JoinInfo;
import org.kered.dko.Field.FK;
import org.kered.dko.Table.__Alias;
import org.kered.dko.TemporaryTableFactory.DummyTableWithName;

class TableInfo implements Cloneable {

	Class<? extends Table> tableClass = null;
	String tableName = null;
	int start = -1;
	int end = -1;
	FK[] path = null;
	TableInfo fkInfo = null;
	boolean nameAutogenned = false;
	JoinInfo join = null;
	int position = -1;
	DummyTableWithName dummyTable = null;

	public TableInfo(final Class<? extends Table> tableClass, final String tableName, final FK[] path) {
		this.tableClass = tableClass;
		this.tableName = tableName;
		this.path  = path;
	}

	public TableInfo(final __Alias<? extends Table> alias, final Object object) {
		this.tableName = alias.alias;
		this.tableClass = alias.table;
		dummyTable  = alias.dummyTable;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		final TableInfo x = new TableInfo(tableClass, tableName, path);
		x.tableClass = tableClass;
		x.start = start;
		x.end = end;
		x.nameAutogenned = nameAutogenned;
		x.dummyTable = dummyTable;
		return x;
	}

	@Override
	public String toString() {
		return "[TableInfo "+ tableClass.getSimpleName() +", "+ tableName +", nameAutogenned="+ nameAutogenned +"]";
	}

	protected void __NOSCO_PRIVATE_postExecute(final SqlContext context, final Connection conn) throws SQLException {
		if (dummyTable != null) dummyTable.__NOSCO_PRIVATE_postExecute(context, conn);
	}
	protected void __NOSCO_PRIVATE_preExecute(final SqlContext context, final Connection conn) throws SQLException {
		if (dummyTable != null) dummyTable.__NOSCO_PRIVATE_preExecute(context, conn);
	}

}