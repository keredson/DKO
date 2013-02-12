package org.kered.dko.persistence;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.kered.dko.Constants;
import org.kered.dko.datasource.JDBCDriverDataSource;
import org.kered.dko.datasource.SingleThreadedDataSource;

public class Util {

    static final String CREATE_QS = "CREATE TABLE query_size (last_seen BIGINT, schema_name TEXT, table_name TEXT, id INTEGER PRIMARY KEY, hash_code int, row_count bigint);";
    static final String CREATE_CA = "CREATE TABLE column_access (id INTEGER PRIMARY KEY, query_execution_id int, table_name TEXT, column_name TEXT, last_seen bigint);";
    static final String CREATE_CA_I = "CREATE INDEX caqe ON column_access(query_execution_id ASC);";
    static final String CREATE_QE = "CREATE TABLE query_execution (id INTEGER PRIMARY KEY, query_hash int, stack_hash int, last_seen bigint, description text);";
    static final String CREATE_QE_I1 = "CREATE INDEX qeqh ON query_execution(query_hash ASC);";
    static final String CREATE_QE_I2 = "CREATE INDEX qesh ON query_execution(stack_hash ASC);";

	static DataSource ds = null;
	private static File dbPath = null;
	private static final Logger log = Logger.getLogger("org.kered.dko.persistence.Util");
	
	public static void setPersistenceDatabasePath(File f) {
		dbPath = f;
		ds = null;
	}

	public static DataSource getDS() {
		if (ds == null) {
			final String[] drivers = {"org.sqlite.JDBC", "org.sqldroid.SQLDroidDriver"};
			Class driver = null;
			for (final String s : drivers) {
				try {
					driver = Class.forName(s);
				} catch (final ClassNotFoundException e) {
					log.fine("could not find "+ s);
				}
			}
			if (driver==null) {
				log.warning("could not find any sqlite3 jdbc drivers");
				return null;
			}
			final String path = System
					.getProperty(Constants.PROPERTY_PERSISTENCE_DB);
			File PERSISTENCE_DB = null;
			if (dbPath != null) {
				PERSISTENCE_DB = dbPath;
			} else if (path == null) {
				final File BASE_DIR = new File(
						System.getProperty("user.home"));
				PERSISTENCE_DB = new File(BASE_DIR, ".dko_persistence.db");
			} else {
				PERSISTENCE_DB = new File(path);
			}
			final String url = "jdbc:sqlite:" + PERSISTENCE_DB.getPath();
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(url);
				checkQuerySize(conn);
				checkQueryExecution(conn);
				checkColumnAccess(conn);
			} catch (final SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
			ds = new SingleThreadedDataSource(new JDBCDriverDataSource(
					Constants.DB_TYPE.SQLITE3, url), 10000, true);
			
//			try {
//				Package pkg = Class.forName("org.kered.dko.persistence.QuerySize").getPackage();
//				Context[] contexts = {Context.getVMContext(), Context.getThreadContext(), Context.getThreadGroupContext() };
//				for (Context context : contexts) {
//					context.setDataSource(pkg, ds).setAutoUndo(false);
//				}
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		return ds;
	}

	private static void checkQueryExecution(Connection conn) throws SQLException {
		final Statement stmt = conn.createStatement();
		try {
			final ResultSet rs = stmt.executeQuery("select count(1) from query_execution");
			rs.next();
			rs.getInt(1);
			rs.close();
		} catch (final SQLException e) {
			log.info(CREATE_QE);
			stmt.executeUpdate(CREATE_QE);
			stmt.executeUpdate(CREATE_QE_I1);
			stmt.executeUpdate(CREATE_QE_I2);
		}
		stmt.close();
	}

	private static void checkColumnAccess(Connection conn) throws SQLException {
		final Statement stmt = conn.createStatement();
		try {
			final ResultSet rs = stmt.executeQuery("select count(1) from column_access");
			rs.next();
			rs.getInt(1);
			rs.close();
		} catch (final SQLException e) {
			log.info(CREATE_CA);
			stmt.executeUpdate(CREATE_CA);
			stmt.executeUpdate(CREATE_CA_I);
		}
		stmt.close();
	}

	private static void checkQuerySize(Connection conn) throws SQLException {
		final Statement stmt = conn.createStatement();
		try {
			final ResultSet rs = stmt.executeQuery("select count(1) from query_size");
			rs.next();
			rs.getInt(1);
			rs.close();
		} catch (final SQLException e) {
			log.info(CREATE_QS);
			stmt.executeUpdate(CREATE_QS);
		}
		stmt.close();
	}
}
