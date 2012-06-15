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
		tableNameMap = q.tableNameMap;
		tableInfos = new ArrayList<TableInfo>(q.tableInfos);
		for (Join join : q.joins) tableInfos.add(join.tableInfo);
		dbType = q.getDBType();
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

}
