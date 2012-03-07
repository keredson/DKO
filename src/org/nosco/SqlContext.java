package org.nosco;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nosco.Constants.DB_TYPE;
import org.nosco.QueryImpl.TableInfo;

class SqlContext {

	SqlContext(QueryImpl<?> q) {
		tableNameMap = q.tableNameMap;
		tableInfos = q.tableInfos;
		dbType = q.getDBType();
	}

	Map<String, Set<String>> tableNameMap = null;
	List<TableInfo> tableInfos = null;
	DB_TYPE dbType = null;
	SqlContext parentContext = null;
	public int maxFields = Integer.MAX_VALUE;

	boolean inInnerQuery() {
		return parentContext != null;
	}

}
