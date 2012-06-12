package org.nosco;

import java.sql.Connection;

import javax.sql.DataSource;

import org.nosco.datasource.MirroredDataSource;
import org.nosco.datasource.ReflectedDataSource;

/**
 * Defines constants for use in this API.
 *
 * @author Derek Anderson
 */
public class Constants {

	public static enum DIRECTION {
		ASCENDING,
		DESCENDING
	}

	public enum DB_TYPE {
		MYSQL,
		SQLSERVER,
		POSTGRES,
		ORACLE,
		SQLITE3,
		HSQL,
		SQL92;

		static DB_TYPE detect(DataSource ds) {
			while (true) {
				if (ds instanceof MirroredDataSource) {
					ds = ((MirroredDataSource)ds).getPrimaryDataSource();
					continue;
				}
				if (ds instanceof ReflectedDataSource) {
					ds = ((ReflectedDataSource)ds).getUnderlyingDataSource();
					continue;
				}
				if (ds == null) return null;
				break;
			}

			String className = ds.getClass().getName();

			if (className.contains("SQLServer")) return SQLSERVER;
			if (className.contains("org.hsqldb")) return HSQL;

			System.err.println("unknown: "+ className);
			return SQL92;
		}

		static DB_TYPE detect(Connection conn) {
			String className = conn.getClass().getName();
			if (className.contains("SQLServer")) return SQLSERVER;
			System.err.println("unknown: "+ className);
			return SQL92;
		}

		String getDatabaseTableSeparator() {
			return this == SQLSERVER ? ".dbo." : ".";
		}

	}

	public static final String PROP_LOG_SQL = "org.nosco.log_sql";

}

