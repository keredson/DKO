package org.nosco;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

public class TransactionThread {
	
	static ConcurrentHashMap<DataSource,ThreadLocal<Connection>> tls = new ConcurrentHashMap<DataSource,ThreadLocal<Connection>>();
	
	public static boolean inTransaction(DataSource ds) {
		ThreadLocal<Connection> tl = tls.get(ds);
		if (tl == null) return false;
		return tl.get() != null;
	}
	
	public static boolean start(DataSource ds) throws SQLException {
		ThreadLocal<Connection> tl = tls.get(ds);
		if (tl == null) {
			ThreadLocal<Connection> tmp = tls.put(ds, tl);
			if (tmp != null) tl = tmp;
		}
		Connection c = tl.get();
		if (c != null) return false;
		c = ds.getConnection();
		c.setAutoCommit(false);
		tl.set(c);
		return true;
	}
	
	public static boolean commit(DataSource ds) throws SQLException {
		ThreadLocal<Connection> tl = tls.get(ds);
		if (tl == null) return false;
		Connection c = tl.get();
		if (c == null) return false;
		c.commit();
		return true;
	}
	
	public static boolean rollback(DataSource ds) throws SQLException {
		ThreadLocal<Connection> tl = tls.get(ds);
		if (tl == null) return false;
		tls.remove(ds);
		Connection c = tl.get();
		if (c == null) return false;
		c.rollback();
		return true;
	}
	
	public static Connection getConnection(DataSource ds) {
		ThreadLocal<Connection> tl = tls.get(ds);
		if (tl == null) return null;
		return tl.get();
	}
	
}
