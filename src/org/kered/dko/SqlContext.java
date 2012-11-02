package org.kered.dko;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.DBQuery.JoinInfo;
import org.kered.dko.TemporaryTableFactory.DummyTableWithName;

class SqlContext {

	private final DBQuery<?> q;

	SqlContext(final DBQuery<?> q) {
		this(q, null);
	}

	public SqlContext(final DBQuery<?> q, final SqlContext parentContext) {
		tableNameMap = q.tableNameMap;
		tableInfos = new ArrayList<TableInfo>(q.tableInfos);
		for (final JoinInfo join : q.joins) tableInfos.add(join.reffedTableInfo);
		for (final JoinInfo join : q.joinsToOne) tableInfos.add(join.reffedTableInfo);
		for (final JoinInfo join : q.joinsToMany) tableInfos.add(join.reffingTableInfo);
		this.parentContext = parentContext;
		dbType = parentContext == null ? q.getDBType() : parentContext.dbType;
		this.q = q;
	}

	Map<String, Set<String>> tableNameMap = null;
	List<TableInfo> tableInfos = null;
	DB_TYPE dbType = null;
	SqlContext parentContext = null;
	public int maxFields = Integer.MAX_VALUE;

	boolean inInnerQuery() {
		return parentContext != null;
	}

	DBQuery<?> getRootQuery() {
		if (parentContext != null) return parentContext.getRootQuery();
		return q;
	}

	String getFullTableName(final TableInfo ti) {
		if (ti.dummyTable != null) {
			return dbType==Constants.DB_TYPE.SQLSERVER ? "#"+ti.dummyTable.name : ti.dummyTable.name;
		}
		return getFullTableName(ti.tableClass);
	}

	String getFullTableName(final Class<? extends Table> tableClass) {
		final StringBuilder sb = new StringBuilder();
		String schema = Util.getSCHEMA_NAME(tableClass);
		if (schema != null) {
			schema = Context.getSchemaToUse(getRootQuery().getDataSource(), schema);
			sb.append(schema);
			sb.append(dbType==Constants.DB_TYPE.SQLSERVER ? ".dbo." : ".");
		}
		sb.append(Util.getTABLE_NAME(tableClass));
		return sb.toString();
	}

}
