package org.nosco;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nosco.ConnectionManager.ConnectionInfo.Login;
import org.nosco.json.JSONException;
import org.nosco.json.JSONObject;
import org.nosco.util.Misc;
import org.nosco.util.Tree;

public class ConnectionManager {

	public enum DB_TYPE {MYSQL, SQLSERVER}

	private static ConnectionManager instance;
	private List<ConnectionInfo> connections = new ArrayList<ConnectionInfo>();
	private Map<String,ConnectionInfo> connectionsByName = new HashMap<String,ConnectionInfo>();
	private Map<String,DB_TYPE> dbTypesByDBName = new HashMap<String,DB_TYPE>();
	private Map<String,Login> loginsByDBMode = new HashMap<String,Login>();
	private Map<String,Connection> connectionsByDBMode = new HashMap<String,Connection>();
	private Tree<ConnectionInfo> conTree = new Tree<ConnectionInfo>();

	private void loadConnections(String fn) throws IOException, JSONException {
		JSONObject o = Misc.loadJSONObject(fn);
		for (String name : o.keySet()) {
			ConnectionInfo c = new ConnectionInfo(name, o.getJSONObject(name));
			connections.add(c);
			connectionsByName.put(name, c);
			dbTypesByDBName.put(c.database, c.dbtype);
		}
	}

	public DB_TYPE getDBType(String database) {
		return dbTypesByDBName.get(database);
	}

	private Login getConnectionInfo(String database, String mode) {
		mode = mode.toLowerCase();
		String key = database +":"+ mode;
		Login al = loginsByDBMode.get(key);
		if (al != null) return al;
		boolean read = mode.contains("r");
		boolean write = mode.contains("w");
		boolean create = mode.contains("c");
		for (ConnectionInfo c : connectionsByName.values()) {
			if (!c.database.equals(database)) continue;
			for (Login l : c.logins) {
				if (l.connectionInfo.mirrorOf == null) {
					if ((l.read || !read) && (l.write || !write) && (l.create || !create)) {
						loginsByDBMode.put(key, l);
						return l;
					}
				} else {
					if (read && !write && !create) {
						loginsByDBMode.put(key, l);
						return l;
					}
				}
			}
		}
		return null;
	}

	public static ConnectionManager instance() {
		if (instance == null) {
			instance = new ConnectionManager();
			try {
				instance.loadConnections("connections.json");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public Connection getConnection(String database, String mode) throws SQLException {
		mode = mode.toLowerCase();
		String key = database +":"+ mode;
		Connection c = connectionsByDBMode.get(key);
		if (c==null || c.isClosed()) {
			Login login = getConnectionInfo(database, mode);
	        String url = null;
	        if (login.connectionInfo.dbtype == DB_TYPE.MYSQL) {
	        	url = "jdbc:mysql://"+ login.connectionInfo.server +"/?zeroDateTimeBehavior=convertToNull";
	        }
	        if (login.connectionInfo.dbtype == DB_TYPE.SQLSERVER) {
	        	try {
					Driver d = (Driver) Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
	        	url = "jdbc:sqlserver://"+ login.connectionInfo.server +":1433";
	        }
	        c = DriverManager.getConnection (url, login.username, login.password);
	        connectionsByDBMode.put(key, c);
		}
		return c;
	}

	public static void main(String[] args) throws IOException, JSONException {
		ConnectionManager c = new ConnectionManager();
		c.loadConnections(args[0]);
	}

	static class ConnectionInfo {

		final String name;
		final DB_TYPE dbtype;
		final String server;
		final String database;
		final String mirrorOfName;
		ConnectionInfo mirrorOf = null;
		Set<ConnectionInfo> mirrors = new HashSet<ConnectionInfo>();
		final Set<Login> logins;

		ConnectionInfo(String name, JSONObject o) throws JSONException {
			this.name = name;
			if ("mysql".equals(o.getString("dbtype"))) this.dbtype = DB_TYPE.MYSQL;
			else if ("sqlserver".equals(o.getString("dbtype"))) this.dbtype = DB_TYPE.SQLSERVER;
			else {
				dbtype = null;
				System.err.println("error: unknown dbtype "+ o.getString("dbtype"));
			}
			//System.err.println("dbtype "+ dbtype);
			this.server = o.getString("server");
			this.database = o.getString("database");
			this.mirrorOfName = o.has("mirror_of") ? o.getString("mirror_of") : null;
			Set<Login> logins = new HashSet<Login>();
			for (JSONObject login : o.getJSONArray("logins").asListJSONObject()) {
				Login l = new Login(login.getString("username"),
							login.getString("password"),
							login.getString("mode"));
				if (l.read) logins.add(l);
				else System.err.println("warning: ignoring login w/o read access: "+ l);
			}
			this.logins = Collections.unmodifiableSet(logins);
		}

		class Login {

			final ConnectionInfo connectionInfo = ConnectionInfo.this;
			final String username;
			final String password;
			final boolean read;
			final boolean write;
			final boolean create;

			Login(String username, String password, String mode) {
				this.username = username;
				this.password = password;
				mode = mode.toLowerCase();
				this.read = mode.contains("r");
				this.write = mode.contains("w");
				this.create = mode.contains("c");
			}

		}

	}


}
