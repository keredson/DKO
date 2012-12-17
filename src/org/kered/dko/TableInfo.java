package org.kered.dko;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import org.kered.dko.DBQuery.JoinInfo;
import org.kered.dko.Field.FK;
import org.kered.dko.Table.__Alias;
import org.kered.dko.TemporaryTableFactory.DummyTableWithName;

class TableInfo implements Cloneable {

	Class<? extends Table> tableClass = null;
	String tableName = null;
	FK[] path = null;
	TableInfo fkInfo = null;
	boolean nameAutogenned = false;
	JoinInfo join = null;
	DummyTableWithName dummyTable = null;

	transient int start = -1;
	transient int end = -1;
	transient int position = -1;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dummyTable == null) ? 0 : dummyTable.hashCode());
		result = prime * result + ((fkInfo == null) ? 0 : fkInfo.hashCode());
		//result = prime * result + ((join == null) ? 0 : join.hashCode());
		result = prime * result + (nameAutogenned ? 1231 : 1237);
		result = prime * result + Arrays.hashCode(path);
		result = prime * result
				+ ((tableClass == null) ? 0 : tableClass.getName().hashCode());
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
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
		final TableInfo other = (TableInfo) obj;
		if (dummyTable == null) {
			if (other.dummyTable != null)
				return false;
		} else if (!dummyTable.equals(other.dummyTable))
			return false;
		if (fkInfo == null) {
			if (other.fkInfo != null)
				return false;
		} else if (!fkInfo.equals(other.fkInfo))
			return false;
		if (join == null) {
			if (other.join != null)
				return false;
		} else if (!join.equals(other.join))
			return false;
		if (nameAutogenned != other.nameAutogenned)
			return false;
		if (!Arrays.equals(path, other.path))
			return false;
		if (tableClass == null) {
			if (other.tableClass != null)
				return false;
		} else if (!tableClass.equals(other.tableClass))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}

}