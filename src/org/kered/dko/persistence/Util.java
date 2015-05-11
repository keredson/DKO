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
    static final String CREATE_CA = "CREATE TABLE column_access (id INTEGER PRIMARY KEY, query_execution_id long, table_name TEXT, column_name TEXT, last_seen bigint);";
    static final String CREATE_CA_I = "CREATE INDEX caqe ON column_access(query_execution_id ASC);";
    static final String CREATE_QE = "CREATE TABLE query_execution (id INTEGER PRIMARY KEY, query_hash int, stack_hash int, last_seen bigint, description text);";
    static final String CREATE_QE_I1 = "CREATE INDEX qeqh ON query_execution(query_hash ASC);";
    static final String CREATE_QE_I2 = "CREATE INDEX qesh ON query_execution(stack_hash ASC);";

	static DataSource ds = null;
	private static File dbPath = null;
	private static final Logger log = Logger.getLogger("org.kered.dko.persistence.Util");

	public static void setPersistenceDatabasePath(final File f) {
		dbPath = f;
		ds = null;
	}

	static boolean warnedNoSqlite3 = false;

	public static DataSource getDS() {
		if ("false".equals(System.getProperty(Constants.PROPERTY_USE_PERSISTENCE_DB)))
			return null;
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
				if (!warnedNoSqlite3) {
					log.warning("Could not find any Sqlite3 JDBC drivers.  All query optimization and snapshot functionality is turned off.");
					warnedNoSqlite3 = true;
				}
				return null;
			}
			final String path = System
					.getProperty(Constants.PROPERTY_PERSISTENCE_DB);
			File PERSISTENCE_DB = null;
			if (dbPath != null) {
				PERSISTENCE_DB = dbPath;
			} else if (path == null) {
				String userHome = System.getProperty("user.home");
				if (thisIsAndroid()) userHome = getAndroidUserHome();
				if (userHome==null || "?".equals(userHome)) {
					throw new RuntimeException("System property 'user.home' not set.  "
						+ "This is a known bug in some versions of the JDK.  See JDK-6972329.");
				}
				final File BASE_DIR = new File(userHome);
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
				log.warning("I could not confirm the state of the persistence database ("+ PERSISTENCE_DB.getPath()
						+"), so the usage monitor will be diabled for this query.  This will not effect its output, "
						+ "only its speed.  This is the underlying error: "+ e);
				return null;
			} finally {
				try {
					if (conn!=null) conn.close();
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

	private static String getAndroidUserHome() {
		StackTraceElement[] st = Thread.currentThread().getStackTrace();
		int i=0;
		while (!st[i].getClassName().startsWith("org.kered.dko")) ++i;
		while (st[i].getClassName().startsWith("org.kered.dko")) ++i;
		String path = "/data/data/"+ st[i].getClassName();
		while (!new File(path).isDirectory()) {
			path = path.substring(0, path.lastIndexOf('.'));
		}
		return path;
	}

	private static boolean thisIsAndroid() {
		return "Dalvik".equals(System.getProperty("java.vm.name"));
	}

	private static void checkQueryExecution(final Connection conn) throws SQLException {
		final Statement stmt = conn.createStatement();
		try {
			final ResultSet rs = stmt.executeQuery("select count(1) from query_execution");
			rs.next();
			int count = rs.getInt(1);
			rs.close();
		} catch (final SQLException e) {
			log.fine(CREATE_QE);
			stmt.executeUpdate(CREATE_QE);
			stmt.executeUpdate(CREATE_QE_I1);
			stmt.executeUpdate(CREATE_QE_I2);
		}
		stmt.close();
	}

	private static void checkColumnAccess(final Connection conn) throws SQLException {
		final Statement stmt = conn.createStatement();
		try {
			final ResultSet rs = stmt.executeQuery("select count(1) from column_access");
			rs.next();
			int count = rs.getInt(1);
			rs.close();
		} catch (final SQLException e) {
			log.fine(CREATE_CA);
			stmt.executeUpdate(CREATE_CA);
			stmt.executeUpdate(CREATE_CA_I);
		}
		stmt.close();
	}

	private static void checkQuerySize(final Connection conn) throws SQLException {
		final Statement stmt = conn.createStatement();
		try {
			final ResultSet rs = stmt.executeQuery("select count(1) from query_size");
			rs.next();
			int count = rs.getInt(1);
			rs.close();
		} catch (final SQLException e) {
			log.fine(CREATE_QS);
			stmt.executeUpdate(CREATE_QS);
		}
		stmt.close();
	}
}
