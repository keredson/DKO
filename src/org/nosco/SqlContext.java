package org.nosco;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nosco.Constants.DB_TYPE;
import org.nosco.QueryImpl.TableInfo;

class SqlContext {

	public SqlContext(QueryImpl<?> q) {
		tableNameMap = q.tableNameMap;
		tableInfos = q.tableInfos;
		dbType = q.getDBType();
	}
	public Map<String, Set<String>> tableNameMap = null;
	public List<TableInfo> tableInfos = null;
	public DB_TYPE dbType = null;

}
