package org.nosco;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

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

		private static Map<DataSource, DB_TYPE> cache = Collections
				.synchronizedMap(new WeakHashMap<DataSource, DB_TYPE>());

		static DB_TYPE detect(final DataSource ds) {

			// see if we've already typed this one
			DB_TYPE cached = cache.get(ds);
			if (cached != null) return cached;

			// unwrap known datasource layers
			DataSource underlying = ds;
			while (true) {
				if (underlying instanceof MirroredDataSource) {
					underlying = ((MirroredDataSource)underlying).getPrimaryDataSource();
					continue;
				}
				if (underlying instanceof ReflectedDataSource) {
					underlying = ((ReflectedDataSource)underlying).getUnderlyingDataSource();
					continue;
				}
				if (underlying == null) return null;
				break;
			}

			// is the class recognizable?
			String className = underlying.getClass().getName();
			if (className.contains("SQLServer")) {
				cache.put(ds, SQLSERVER);
				return SQLSERVER;
			}
			if (className.contains("org.hsqldb")) {
				cache.put(ds, HSQL);
				return HSQL;
			}

			// attempt to detect from a connection
			Connection conn = null;
			try {
				conn = ds.getConnection();
				DB_TYPE type = detect(conn);
				if (type != null) {
					cache.put(ds, type);
					return type;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			// unknown
			System.err.println("unknown db type for DataSource: "+ ds);
			return null;
		}

		static DB_TYPE detect(Connection conn) throws SQLException {

			// try from the class name
			String className = conn.getClass().getName();
			if (className.contains("SQLServer")) return SQLSERVER;
			System.err.println(className);

			// try from the jdbc metadata
			DatabaseMetaData metaData = conn.getMetaData();
			String driver = null;
			String url = null;
			if (metaData != null) {
				driver = metaData.getDriverName();
				if (driver.contains("sqlserver")) return SQLSERVER;
				if (driver.contains("hsqldb")) return HSQL;
				url = metaData.getURL();
				if (url.startsWith("jdbc:sqlserver")) return SQLSERVER;
				if (url.startsWith("jdbc:hsql")) return HSQL;
			}

			System.err.println("unknown db type for Connection: "+ conn
					+" (driver:"+ driver +", url:"+ url +")");
			return null;
		}

		String getDatabaseTableSeparator() {
			return this == SQLSERVER ? ".dbo." : ".";
		}

	}

	public static final String PROP_LOG_SQL = "org.nosco.log_sql";

}

