package org.nosco;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.nosco.Constants.DB_TYPE;
import org.nosco.DBQuery.Join;

class SqlContext {

	private DBQuery<?> q;

	SqlContext(DBQuery<?> q) {
		this(q, null);
	}

	public SqlContext(DBQuery<?> q, SqlContext parentContext) {
		tableNameMap = q.tableNameMap;
		tableInfos = new ArrayList<TableInfo>(q.tableInfos);
		for (Join join : q.joins) tableInfos.add(join.reffedTableInfo);
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

	String getFullTableName(Table table) {
		StringBuilder sb = new StringBuilder();
		String schema = table.SCHEMA_NAME();
		if (schema != null) {
			schema = Context.getSchemaToUse(getRootQuery().getDataSource(), schema);
			sb.append(schema);
			sb.append(dbType==Constants.DB_TYPE.SQLSERVER ? ".dbo." : ".");
		}
		sb.append(table.TABLE_NAME());
		return sb.toString();
	}

}
