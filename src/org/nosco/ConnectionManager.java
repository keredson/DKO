package org.nosco;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileManager.Location;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

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

	private void loadConnections(String fn) throws IOException, JSONException {
		JSONObject o = Misc.loadJSONObject(fn);
		for (String name : o.keySet()) {
			name = parseSystemProperties(name);
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
	        	/*try {
					Driver d = (Driver) Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} //*/
	        	url = "jdbc:sqlserver://"+ login.connectionInfo.server +":1433";
	        }
	        if (login.connectionInfo.hibernate == null) {
		        c = DriverManager.getConnection (url, login.username, login.password);
	        } else {
	        	c = login.connectionInfo.hibernate.getConnection();
	        	//System.err.println("opened hibernate connection...");
	        }
	        connectionsByDBMode.put(key, c);
		}
		return c;
	}

	public static void main(String[] args) throws IOException, JSONException {
		ConnectionManager c = new ConnectionManager();
		c.loadConnections(args[0]);
	}

	private static Pattern sysProp = Pattern.compile("[$][{][a-zA-Z0-9_.-]+[}]");

	private static String parseSystemProperties(String s) {
		if (s == null) return s;
		StringBuilder sb = new StringBuilder();
		Matcher m = sysProp.matcher(s);
		int i = 0;
		while (m.find(i)) {
			sb.append(s.substring(i, m.start()));
			String propName = s.substring(m.start()+2, m.end()-1);
			String v = System.getProperty(propName);
			sb.append(v);
			i = m.end();
		}
		sb.append(s.substring(i));
		return sb.toString();
	}

	static class ConnectionInfo {

		final String name;
		final DB_TYPE dbtype;
		final String server;
		final String database;
		final String mirrorOfName;
		final HibernateSessionFactoryFactory hibernate;
		ConnectionInfo mirrorOf = null;
		Set<ConnectionInfo> mirrors = new HashSet<ConnectionInfo>();
		final Set<Login> logins;

		ConnectionInfo(String name, JSONObject o) throws JSONException {
			this.name = name;
			String dbType = parseSystemProperties(o.getString("dbtype"));
			if ("mysql".equals(dbType)) this.dbtype = DB_TYPE.MYSQL;
			else if ("sqlserver".equals(dbType)) this.dbtype = DB_TYPE.SQLSERVER;
			else {
				dbtype = null;
				System.err.println("error: unknown dbtype "+ dbType);
			}
			//System.err.println("dbtype "+ dbtype);
			this.server = parseSystemProperties(o.getString("server"));
			this.database = parseSystemProperties(o.getString("database"));
			this.mirrorOfName = parseSystemProperties(o.has("mirror_of")
					? o.getString("mirror_of") : null);
			Set<Login> logins = new HashSet<Login>();
			for (JSONObject login : o.getJSONArray("logins").asListJSONObject()) {
				Login l = new Login(parseSystemProperties(login.getString("username")),
						parseSystemProperties(login.getString("password")),
						parseSystemProperties(login.getString("mode")));
				if (l.read) logins.add(l);
				else System.err.println("warning: ignoring login w/o read access: "+ l);
			}
			this.logins = Collections.unmodifiableSet(logins);
			if (o.has("hibernate_session_factory")) {
				String s = parseSystemProperties(o.getString("hibernate_session_factory"));
				hibernate = new HibernateSessionFactoryFactory(s);
			} else {
				hibernate = null;
			}

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

	/**
	 * this is to add support for hibernate session factories without actually having
	 * to depend on them as a library in our code.
	 * @author dander
	 */
	private static class HibernateSessionFactoryFactory {

		private Object sessionFactory = null;
		private Method openSession = null;
		private Method connection = null;

		HibernateSessionFactoryFactory(String s) {

			String className = "HibernateSessionFactoryFactoryTmpInstance"+this.hashCode();
		    StringWriter writer = new StringWriter();
		    PrintWriter out = new PrintWriter(writer);
		    out.println("public class "+ className +" {");
		    out.println("  public static org.hibernate.SessionFactory get() {");
		    out.println("    return "+ s +";");
		    out.println("  }");
		    out.println("}");
		    out.close();

			DynamicCompiler compiler = new DynamicCompiler();
			try {
				compiler.init();
				Class<?> cls = compiler.compileToClass(className, writer.toString());
	        	Method m = cls.getDeclaredMethod("get");
	        	this.sessionFactory = m.invoke(null);
	        	Class<?> ohsfClass = Class.forName("org.hibernate.SessionFactory");
	        	this.openSession = ohsfClass.getDeclaredMethod("openSession");
	        	Class<?> ohsClass = Class.forName("org.hibernate.Session");
	        	this.connection = ohsClass.getDeclaredMethod("connection");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		Connection getConnection() {
			try {
				//System.err.println(this.openSession +" "+ this.openSession.getClass().getName());
				Object session = this.openSession.invoke(this.sessionFactory);
				Object conn = this.connection.invoke(session);
				return (Connection) conn;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	private static  class ByteArrayJavaFileObject extends SimpleJavaFileObject {
		private final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		public ByteArrayJavaFileObject(String name, Kind kind) {
			super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
		}
		public byte[] getClassBytes() {
			return bos.toByteArray();
		}
		@Override
		public OutputStream openOutputStream()
				throws IOException {
			return bos;
		}
	}


	private static class ByteArrayClassLoader extends ClassLoader {
		private Map<String, ByteArrayJavaFileObject> cache = new HashMap<String,
				ByteArrayJavaFileObject>();
		public ByteArrayClassLoader()
				throws Exception {
			super(ByteArrayClassLoader.class.getClassLoader());
		}
		public void put(String name, ByteArrayJavaFileObject obj) {
			ByteArrayJavaFileObject co = cache.get(name);
			if (co == null) {
				cache.put(name, obj);
			}
		}
		@Override
		protected Class<?> findClass(String name)
				throws ClassNotFoundException {
			Class<?> cls = null;
			try {
				ByteArrayJavaFileObject co = cache.get(name);
				if (co != null) {
					byte[] ba = co.getClassBytes();
					cls = defineClass(name, ba, 0, ba.length);
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
				throw new ClassNotFoundException("Class name: " + name, ex);
			}
			return cls;
		}
	}

	private static class DynamicClassFileManager <FileManager> extends
	ForwardingJavaFileManager<JavaFileManager> {
		private ByteArrayClassLoader loader = null;
		DynamicClassFileManager(StandardJavaFileManager mgr) {
			super(mgr);
			try {
				loader = new ByteArrayClassLoader();
			}
			catch (Exception ex) {
				ex.printStackTrace(System.err);
			}
		}
		@Override
		public JavaFileObject getJavaFileForOutput(Location location, String name, Kind kind,
				FileObject sibling)
						throws IOException {
			ByteArrayJavaFileObject co = new ByteArrayJavaFileObject(name, kind);
			loader.put(name, co);
			return co;
		}
		@Override
		public ClassLoader getClassLoader(Location location) {
			return loader;
		}
	}

	public static class DynamicCompiler {
		private JavaCompiler compiler;
		private DiagnosticCollector<JavaFileObject> collector;
		private JavaFileManager manager;
		public void init()
				throws Exception {
			compiler = ToolProvider.getSystemJavaCompiler();
			collector = new DiagnosticCollector<JavaFileObject>();
			manager = new
					DynamicClassFileManager<JavaFileManager>(compiler.getStandardFileManager(null, null, null));
		}
		public Class<?> compileToClass(String fullName, String javaCode)
				throws Exception {
			Class<?> clazz = null;
			StringJavaFileObject strFile = new StringJavaFileObject(fullName, javaCode);
			Iterable<? extends JavaFileObject> units = Arrays.asList(strFile);
			CompilationTask task = compiler.getTask(null, manager, collector, null, null, units);
			boolean status = task.call();
			if (status) {
				//System.out.printf("Compilation successful!!!\n");
				clazz = manager.getClassLoader(null).loadClass(fullName);
			}
			else {
				System.err.printf("Message:\n");
				for (Diagnostic<?> d : collector.getDiagnostics()) {
					System.err.printf("%s\n", d.getMessage(null));
				}
				System.err.printf("***** Compilation failed!!!\n");
			}
			return clazz;
		}
	}

	public static class StringJavaFileObject extends SimpleJavaFileObject {
		private String source;
		public StringJavaFileObject(String name, String source) {
			super(URI.create("string:///" + name.replace('.','/') + Kind.SOURCE.extension),
					Kind.SOURCE);
			this.source = source;
		}
		@Override
		public CharSequence getCharContent(boolean ignoreEncodingErrors) {
			return this.source;
		}
	}


}
