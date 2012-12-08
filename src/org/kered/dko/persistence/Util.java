package org.kered.dko.persistence;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.kered.dko.Constants;
import org.kered.dko.datasource.JDBCDriverDataSource;
import org.kered.dko.datasource.SingleThreadedDataSource;

public class Util {

    static final String CREATE = "CREATE TABLE query_size (schema_name TEXT, table_name TEXT, id INTEGER PRIMARY KEY, hash_code int, row_count bigint)";

    static DataSource ds = null;
    public static DataSource getDS() {
	System.err.println("org.sqlite.JDBC");
	if (ds==null) {
	    try {
		Class.forName("org.sqlite.JDBC");
		final String path = System.getProperty(Constants.PROPERTY_PERSISTENCE_DB);
		File PERSISTENCE_DB = null;
		if (path == null) {
		    final File BASE_DIR = new File(System.getProperty("user.home"));
		    PERSISTENCE_DB = new File(BASE_DIR, ".dko_persistence.db");
		} else {
		    PERSISTENCE_DB = new File(path);
		}
		final String url = "jdbc:sqlite:"+PERSISTENCE_DB.getPath();
		Connection conn = null;
		try {
		    conn = DriverManager.getConnection(url);
		    final Statement stmt = conn.createStatement();
		    try {
			final ResultSet rs = stmt.executeQuery("select count(1) from query_size");
			rs.next();
			rs.getInt(1);
			rs.close();
		    } catch (final SQLException e) {
			System.err.println("init "+ PERSISTENCE_DB);
			stmt.executeUpdate(CREATE);
		    }
		    stmt.close();
		} catch (final SQLException e) {
		    e.printStackTrace();
		} finally {
		    try {
			conn.close();
		    } catch (final SQLException e) {
			e.printStackTrace();
		    }
		}
		ds = new SingleThreadedDataSource(new JDBCDriverDataSource(Constants.DB_TYPE.SQLITE3, url), 10000, true);
	    } catch (final ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	return ds;
    }
}
