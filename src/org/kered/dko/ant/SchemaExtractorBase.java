package org.kered.dko.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.tools.ant.Task;
import org.kered.dko.Constants;
import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.json.JSONArray;
import org.kered.dko.json.JSONException;
import org.kered.dko.json.JSONObject;

/**
 * Extracts schema information from a provided database into a JSON file. &nbsp;
 * Designed to be run occasionally, with the output file checked into version control.
 * (to avoid regular builds hitting your DB server, and for code generation history) &nbsp;
 *
 * @author Derek Anderson
 */
public class SchemaExtractorBase {

	private static int[] version = {0,2,0};

	private DB_TYPE dbType = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private HashSet<String> includeSchemas = null;
	private String[] enums = {};
	private File enumsOut = null;
	private File out = null;
	private ConnectionAdapter connAdapter = null;
	private String connectionName = null;
	private Task antTask = null;

	private final List<Pattern> onlyTables = new ArrayList<Pattern>();
	private final List<Pattern> excludeTables = new ArrayList<Pattern>();

	public SchemaExtractorBase() {}

	public SchemaExtractorBase(Task t) {
		this.antTask = t;
	}

	public void setOut(final String s) {
		this.out  = new File(s);
	}

	public void setEnums(final String s) {
		if (s != null && s.length() > 0) this.enums = s.split(",");
	}

	public void setEnumsOut(final String s) {
		this.enumsOut = s==null ? null : new File(s);
	}

	public void setDBType(final String s) {
		if ("sqlserver".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.SQLSERVER;
		if ("mysql".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.MYSQL;
		if ("hsql".equalsIgnoreCase(s) || "hsqldb".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.HSQL;
		if ("sqlite".equalsIgnoreCase(s) || "sqlite3".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.SQLITE3;
		if ("postgres".equalsIgnoreCase(s) || "postgresql".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.POSTGRES;
		if ("oracle".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.ORACLE;
		if ("derby".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.DERBY;
	}

	public void setConnectionAdapter(String s) throws Exception {
		this.connAdapter = (ConnectionAdapter)Class.forName(s).newInstance();
	}

	public void setConnectionName(String s) {
		this.connectionName = s;
	}

	public void setURL(final String s) throws Exception {
		this.url = s;
		if (url.startsWith("jdbc:sqlserver")) {
			try {
				final Driver d = (Driver) Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
			} catch (final ClassNotFoundException e) {
				System.err.println("not found: com.microsoft.jdbc.sqlserver.SQLServerDriver");
				System.err.print("trying: com.microsoft.sqlserver.jdbc.SQLServerDriver");
				final Driver d = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
				System.err.println(" ...success!");
			}
			if (dbType==null) dbType = DB_TYPE.SQLSERVER;
		}
		if (url.startsWith("jdbc:mysql")) {
			final Driver d = (Driver) Class.forName("com.mysql.jdbc.Driver").newInstance();
			if (dbType==null) dbType = DB_TYPE.MYSQL;
		}
		if (url.startsWith("jdbc:hsqldb")) {
			final Driver d = (Driver) Class.forName("org.hsqldb.jdbc.JDBCDriver").newInstance();
			if (dbType==null) dbType = DB_TYPE.HSQL;
		}
		if (url.startsWith("jdbc:sqlite")) {
			final Driver d = (Driver) Class.forName("org.sqlite.JDBC").newInstance();
			if (dbType==null) dbType = DB_TYPE.SQLITE3;
		}
		if (url.startsWith("jdbc:postgresql")) {
			final Driver d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
			if (dbType==null) dbType = DB_TYPE.POSTGRES;
		}
		if (url.startsWith("jdbc:oracle")) {
			final Driver d = (Driver) Class.forName("oracle.jdbc.OracleDriver").newInstance();
			if (dbType==null) dbType = DB_TYPE.ORACLE;
		}
		if (url.startsWith("jdbc:derby")) {
			final Driver d = (Driver) Class.forName("org.apache.derby.jdbc.AutoloadedDriver").newInstance();
			if (dbType==null) dbType = DB_TYPE.DERBY;
		}
	}

	public void setUsername(final String s) {
		this.username = s;
	}

	public void setPassword(final String s) {
		this.password = s;
	}

	public void setPasswordFile(final String s) {
		try {
			final BufferedReader br = new BufferedReader(new FileReader(s));
			this.password = br.readLine().trim();
		} catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSchemas(final String s) {
		this.includeSchemas = new HashSet<String>();
		for (final String schema : s.split(",")) {
			this.includeSchemas.add(schema.trim());
		}
	}

	/**
	 * A comma separated list of regex patterns.  If "schema_name.table_name" contains the
	 * java pattern, the table will be included.  Otherwise, it will be excluded.
	 * If this is not set, all tables will be included.
	 * @param s
	 */
	public void setOnlyTables(final String s) {
		for (String v : s.split(",")) {
			v = v.trim();
			final Pattern p = Pattern.compile(v);
			onlyTables.add(p);
		}
	}

	/**
	 * A comma separated list of regex patterns.  If "schema_name.table_name" contains the
	 * java pattern, the table will be excluded.  Otherwise, it will be included.
	 * If this is not set, all tables will be included.
	 * @param s
	 */
	public void setExcludeTables(final String s) {
		for (String v : s.split(",")) {
			v = v.trim();
			final Pattern p = Pattern.compile(v);
			excludeTables.add(p);
		}
	}

	public Map<String,Map<String,Map<String,String>>> getDatabaseTableColumnTypes() throws SQLException {
		System.err.println("connecting to "+ url);
		Connection conn = null;
		try {
			conn = getConnection();
			final Map<String,Map<String,Map<String,String>>> schemas = getSchemas(conn);
			return schemas;
		} catch (SQLException e) {
			throw e;
		} finally {
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getDescription() {
		return "extracts your database schema(s) to a JSON file (to be checked into your VCS)";
	}

	static String getHelp(String methodName) {
		if ("setDBType".equals(methodName)) {
			return "<sqlserver|mysql|hsql|sqlite3|postgres|oracle> (optional)\n" +
					"Usually this is unnecessary because the type will be auto-detected from the JDBC URL.";
		}
		if ("setUsername".equals(methodName)) {
			return "<database_username> (required for most db-types)\n" +
					"Not needed for SQLite3, HSQL.";
		}
		if ("setPassword".equals(methodName)) {
			return "<password> (required for most db-types)\n" +
					"Not needed for SQLite3, HSQL.";
		}
		if ("setPasswordFile".equals(methodName)) {
			return "<filename> (optional)\n" +
					"If your database account's password is saved in a plain-text file, provide the path to it here.";
		}
		if ("setURL".equals(methodName)) {
			return "<jdbc_url> (required)\n" +
					"Your database's JDBC URL.  Examples:\n" +
					"  jdbc:mysql://localhost:3306/sakila?profileSQL=true\n" +
					"  jdbc:sqlite://dirA/dirB/dbfile\n" +
					"  jdbc:oracle:thin:@myhost:1521:orcl";
		}
		if ("setOut".equals(methodName)) {
			return "<filename> (required)\n" +
					"The file to write your database schema to.  Usually 'schemas.json'.\n" +
					"PLEASE CHECK THIS FILE INTO VERSION CONTROL!";
		}
		if ("setExcludeTables".equals(methodName)) {
			return "<string> (optional)\n" +
					"A comma separated list of regex patterns.  If 'schema_name.table_name' matches \n" +
					"the java pattern, the table will be excluded.  Otherwise, it will be included.";
		}
		if ("setOnlyTables".equals(methodName)) {
			return "<string> (optional)\n" +
					"A comma separated list of regex patterns.  If 'schema_name.table_name' matches \n" +
					"the java pattern, the table will be includes.  Otherwise, it will be excluded.";
		}
		if ("setSchemas".equals(methodName)) {
			return "<string> (optional)\n" +
					"A comma separated list of schema names to extract.  If not set all schemas will be extracted.";
		}
		if ("setEnums".equals(methodName)) {
			return "<string> (optional)\n" +
					"A comma separated list of 'schema_name.table_name.column_name' values (without the '').\n" +
					"DKOs can ENUMify table values for infrequently changing data.  For example:\n" +
					"'myschema.state.abbr' will turn the entire 'state' table into an enum, with enum entries\n" +
					"matching the values in the 'abbr' column.";
		}
		if ("setEnumsOut".equals(methodName)) {
			return "<filename> (required if --set-enums is used)\n" +
					"The file to write your enum values to.  Usually 'enums.json'.\n" +
					"PLEASE CHECK THIS FILE INTO VERSION CONTROL!";
		}
		return "<string> (no help available)";
	}

	private Connection getConnection() throws SQLException
	{
        	if (connAdapter == null)
			return DriverManager.getConnection(url, username, password);
		else
			return connAdapter.getConnection(connectionName, antTask);
	}

	public void execute() {

		Connection conn = null;
		try {

			System.err.println("connecting to "+ url);
			conn = getConnection();
			final Map<String,Map<String,Map<String,String>>> schemas = getSchemas(conn);
			final Map<String,Map<String,Set<String>>> primaryKeys = getPrimaryKeys(conn, schemas);
			final Map<String, Map<String,Object>> foreignKeys = getForeignKeys(conn);

			final JSONObject json = new JSONObject();
			json.put("version", new JSONArray(version));
			json.put("schemas", new JSONObject(schemas));
			json.put("primary_keys", new JSONObject(primaryKeys));
			json.put("foreign_keys", new JSONObject(foreignKeys));

			System.err.println("writing: "+ out.getAbsolutePath());
			final FileWriter w = new FileWriter(out);
			w.write(json.toString(4));
			w.close();

			if (enums!=null && enums.length > 0 && enumsOut != null) {
				final JSONObject allEnums = new JSONObject();
				for (String x : enums) {
					x = x.trim();
					final String[] xa = x.split("[.]");
					if (xa.length != 3) {
						throw new RuntimeException("'"+ x +"' " +
								"must be of the format 'schema.table.name_column'");
					}
					final String schema = xa[0];
					final String table = xa[1];
					final String column = xa[2];
					allEnums.put(x, getEnums(conn, schema, table, column,
							primaryKeys.get(schema).get(table)));
				}
				System.err.println("writing: "+ enumsOut.getAbsolutePath());
				final FileWriter w2 = new FileWriter(enumsOut);
				w2.write(allEnums.toString(4));
				w2.close();
			}

		} catch (final SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (final JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn!=null && !conn.isClosed()) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (dbType==DB_TYPE.DERBY) {
				try {
					DriverManager.getConnection(url+";shutdown=true");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private JSONObject getEnums(final Connection conn, final String schema, final String table, final String column,
			final Set<String> pks) throws SQLException {
		if (pks == null) throw new RuntimeException("primary keys not set for enum table: "
			+ schema +"."+ table +"."+ column);
		String sep =".";
		if (this.dbType == DB_TYPE.SQLSERVER) {
			sep ="..";
		}
		final JSONObject ret = new JSONObject();
		final String sql = "select "+ column +", "+ Util.join(", ", pks) +" "
				+ "from "+ schema + sep + table +" order by "+ column +";";
		System.err.println(sql);
		final Statement s = conn.createStatement();
		s.execute(sql);
		final ResultSet rs = s.getResultSet();
		while (rs.next()) {
			final String name = rs.getString(1);
			final JSONArray values = new JSONArray();
			for (final String key : pks) {
				values.put(rs.getObject(key));
			}
			try {
				ret.put(name, values);
			} catch (final JSONException e) {
				e.printStackTrace();
			}
		}
	    return ret;
	}

	private static Set<String> ignoredSchemas = new HashSet<String>() {{
		add("information_schema");
		add("mysql");
	}};

	private Map<String, Map<String, Map<String, String>>> getSchemas(
			final Connection conn) throws SQLException {
		if (dbType == DB_TYPE.SQLSERVER) return getSchemasMSSQL(conn);
		if (dbType == DB_TYPE.SQLITE3) return getSchemasSQLITE3(conn);
		if (dbType == DB_TYPE.POSTGRES) return getSchemasPostgreSQL(conn);
		if (dbType == DB_TYPE.ORACLE) return getSchemasJDBC(conn);
		if (dbType == DB_TYPE.DERBY) return getSchemasJDBC(conn);
		else return getSchemasSQL92(conn);
	}

	private Map<String, Map<String, Map<String, String>>> getSchemasJDBC(
			final Connection conn) throws SQLException {
		final Map<String, Map<String, Map<String, String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();
		final DatabaseMetaData metadata = conn.getMetaData();
		final ResultSet columnRS = metadata.getColumns(null, null, null, null);
		while (columnRS.next()) {
			final String catalog = columnRS.getString("TABLE_CAT");
			String schema = columnRS.getString("TABLE_SCHEM");
			if (schema == null) schema = "";
			if ("SYS".equals(schema)) continue;
			if ("SYSIBM".equals(schema)) continue;
			if (includeSchemas!=null && !includeSchemas.contains(schema)) continue;
			final String tableName = columnRS.getString("TABLE_NAME");
			//final String tableType = columnRS.getString("TABLE_TYPE");
			final String columnName = columnRS.getString("COLUMN_NAME");
			String columnType = columnRS.getString("TYPE_NAME");
			final int decimalDigits = columnRS.getInt("DECIMAL_DIGITS");
			final int columnSize = columnRS.getInt("COLUMN_SIZE");
			if (dbType==DB_TYPE.ORACLE && "NUMBER".equals(columnType) && decimalDigits==0) {
				// oracle converts int to number with decimal precision 0
				columnType = "INTEGER";
			}
			if (dbType==DB_TYPE.ORACLE && "VARCHAR2".equals(columnType)) {
				// in oracle, no varchar(2) == any varchar(3), so it's important
				// we keep the size
				columnType += "("+ columnSize +")";
			}
			System.out.println("found column: "+ schema +" "+ tableName +" "+ columnSize +" "+ columnName +" "+ columnType);
			//if (!"TABLE".equals(tableType)) continue;
			Map<String, Map<String, String>> tables = schemas.get(schema);
			if (tables == null) {
				System.out.println("found schema: "+ schema);
				tables = new LinkedHashMap<String, Map<String, String>>();
				schemas.put(schema, tables);
			}
			Map<String, String> columns = tables.get(tableName);
			if (columns == null) {
				System.out.println("found table: "+ tableName);
				columns = new LinkedHashMap<String, String>();
				tables.put(tableName, columns);
			}
			columns.put(columnName, columnType);
		}
		columnRS.close();
		return schemas;
	}

	private Map<String, Map<String, Map<String, String>>> getSchemasSQLITE3(
			final Connection conn) throws SQLException {
		final Map<String, Map<String, Map<String, String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();
		final DatabaseMetaData metadata = conn.getMetaData();
		final ResultSet tableRS = metadata.getTables(null, null, null, null);
		while (tableRS.next()) {
			final String catalog = tableRS.getString("TABLE_CAT");
			String schema = tableRS.getString("TABLE_SCHEM");
			if (schema == null) schema = "";
			final String tableName = tableRS.getString("TABLE_NAME");
			final String tableType = tableRS.getString("TABLE_TYPE");
			if (!"TABLE".equals(tableType)) continue;
			Map<String, Map<String, String>> tables = schemas.get(schema);
			if (tables == null) {
				System.out.println("found schema: "+ schema);
				tables = new LinkedHashMap<String, Map<String, String>>();
				schemas.put(schema, tables);
			}
			Map<String, String> columns = tables.get(tableName);
			if (columns == null) {
				System.out.println("found table: "+ tableName);
				columns = new LinkedHashMap<String, String>();
				tables.put(tableName, columns);
			}
		}
		final Statement stmt = conn.createStatement();
		for (final Entry<String, Map<String, Map<String, String>>> e1 : schemas.entrySet()) {
			final Map<String, Map<String, String>> tables = e1.getValue();
			for (final Entry<String, Map<String, String>> e2 : tables.entrySet()) {
				final String tableName = e2.getKey();
				final Map<String, String> columns = e2.getValue();
				final ResultSet rs = stmt.executeQuery("pragma table_info("+ tableName +");");
				while (rs.next()) {
					final String columnName = rs.getString(2);
					final String columnType = rs.getString(3);
					columns.put(columnName, columnType);
				}
			}
		}
		return schemas;
	}

	private Map<String, Map<String, Map<String, String>>> getSchemasPostgreSQL(
			final Connection conn) throws SQLException {
		final Map<String, Map<String, Map<String, String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();
		final DatabaseMetaData metadata = conn.getMetaData();
		final ResultSet columnRS = metadata.getColumns(null, null, null, null);
		while (columnRS.next()) {
			final String catalog = columnRS.getString("TABLE_CAT");
			String schema = columnRS.getString("TABLE_SCHEM");
			if (schema == null) schema = "";
			if ("pg_catalog".equals(schema)) continue;
			if ("pg_toast".equals(schema)) continue;
			if ("information_schema".equals(schema)) continue;
			final String tableName = columnRS.getString("TABLE_NAME");
			//final String tableType = columnRS.getString("TABLE_TYPE");
			final String columnName = columnRS.getString("COLUMN_NAME");
			final String columnType = columnRS.getString("TYPE_NAME");
			//System.out.println("found column: "+ columnName +" "+ columnType);
			//if (!"TABLE".equals(tableType)) continue;
			Map<String, Map<String, String>> tables = schemas.get(schema);
			if (tables == null) {
				System.out.println("found schema: "+ schema);
				tables = new LinkedHashMap<String, Map<String, String>>();
				schemas.put(schema, tables);
			}
			Map<String, String> columns = tables.get(tableName);
			if (columns == null) {
				System.out.println("found table: "+ tableName);
				columns = new LinkedHashMap<String, String>();
				tables.put(tableName, columns);
			}
			columns.put(columnName, columnType);
		}
		return schemas;
	}

	private Map<String, Map<String, Map<String, String>>> getSchemasSQL92(
			final Connection conn)
			throws SQLException {
		final Map<String, Map<String, Map<String, String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();

		final Statement s = conn.createStatement();
		s.execute("select table_schema, table_name, column_name, data_type "
				+ "from information_schema.columns order by table_schema, table_name, column_name;");
		final ResultSet rs = s.getResultSet();
		while (rs.next()) {
			final String schema = rs.getString("table_schema");
			final String table = rs.getString("table_name");
			final String column = rs.getString("column_name");
			final String type = rs.getString("data_type");

			if (ignoredSchemas.contains(schema))
				continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;
			if (!includeTable(schema, table)) continue;

			Map<String, Map<String, String>> tables = schemas.get(schema);
			if (tables == null) {
				tables = new LinkedHashMap<String, Map<String, String>>();
				schemas.put(schema, tables);
			}

			Map<String, String> columns = tables.get(table);
			if (columns == null) {
				columns = new LinkedHashMap<String, String>();
				tables.put(table, columns);
			}

			columns.put(column, type);
		}
		rs.close();
		s.close();

		return schemas;
	}

	private final Set<String> excluded = new HashSet<String>();

	private boolean includeTable(final String schema, final String table) {
		final String s = schema +"."+ table;
		if (this.onlyTables.size() > 0) {
			boolean include = false;
			for (final Pattern p : onlyTables) {
				if (p.matcher(s).find()) {
					include = true;
					break;
				}
			}
			if (!include) return false;
		}
		for (final Pattern p : this.excludeTables) {
			if (p.matcher(s).find()) {
				if (!excluded.contains(s)) System.err.println("excluding: "+ s);
				excluded.add(s);
				return false;
			}
		}
		return true;
	}

	private Map<String, Map<String, Map<String, String>>> getSchemasMSSQL(final Connection conn)
			throws SQLException {
		final Map<String, Map<String, Map<String, String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();

		final List<String> dbs = new ArrayList<String>();
		final Statement s = conn.createStatement();
		s.execute("SELECT name FROM sys.databases ORDER BY name;");
		final ResultSet rs = s.getResultSet();
		while (rs.next()) {
			final String schema = rs.getString("NAME");
			if (ignoredSchemas.contains(schema))
				continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;
			dbs.add(schema);
		}
		rs.close();
		// s.close();

		for (final String db : dbs) {
			if (ignoredSchemas.contains(db))
				continue;
			if (includeSchemas != null && !includeSchemas.contains(db))
				continue;

			System.err.println("extracting schema for: "+ db);

			try {
				s.execute("use \"" + db + "\";");
				s.execute("SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, DATA_TYPE, " +
						"CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS ORDER BY " +
						"TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME;");
				final ResultSet rs2 = s.getResultSet();
				while (rs2.next()) {
					final String schema = db; // +"."+ rs2.getString("TABLE_SCHEMA");
					final String table = rs2.getString("TABLE_NAME");
					if (!includeTable(schema, table)) continue;

					Map<String, Map<String, String>> tables = schemas.get(schema);
					if (tables == null) {
						tables = new LinkedHashMap<String, Map<String, String>>();
						schemas.put(schema, tables);
					}
					if (table.toLowerCase().startsWith("syncobj_")) continue;
					if (table.startsWith("MS") && table.length() > 2
							&& Character.isLowerCase(table.charAt(2))) continue;
					final String column = rs2.getString("COLUMN_NAME");
					String type = rs2.getString("DATA_TYPE");
					final int maxLength = rs2.getInt("CHARACTER_MAXIMUM_LENGTH");
					if ("char".equalsIgnoreCase(type) &&  maxLength > 1)
						type = "varchar";

					Map<String, String> columns = tables.get(table);
					if (columns == null) {
						columns = new LinkedHashMap<String, String>();
						tables.put(table, columns);
					}

					columns.put(column, type);
				}
				rs2.close();
			} catch (final SQLException e) {
				if (e.getMessage().contains("security context")) {
					System.err.println(e.getMessage());
				} else {
					throw e;
				}
			}
		}

		s.close();

		return schemas;
	}

	private Map<String,Map<String,Set<String>>> getPrimaryKeys(final Connection conn, Map<String, Map<String, Map<String, String>>> schemas) throws SQLException {
		if (dbType == DB_TYPE.SQLSERVER) return getPrimaryKeysMSSQL(conn);
		if (dbType == DB_TYPE.HSQL) return getPrimaryKeysHSQL(conn);
		if (dbType == DB_TYPE.SQLITE3) return getPrimaryKeysSQLITE3(conn);
		if (dbType == DB_TYPE.POSTGRES) return getPrimaryKeysJDBC(conn);
		if (dbType == DB_TYPE.ORACLE) return getPrimaryKeysOracle(conn);
		if (dbType == DB_TYPE.DERBY) return getPrimaryKeysByTableJDBC(conn, schemas);
		return getPrimaryKeysMySQL(conn);
	}

	private Map<String, Map<String, Set<String>>> getPrimaryKeysOracle(
			Connection conn) throws SQLException {
		final Map<String, Map<String, Set<String>>> pks = new LinkedHashMap<String, Map<String, Set<String>>>();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT ucc.table_name,ucc.constraint_name,ucc.column_name," +
				"ucc.position,uc.owner " +
				"FROM user_cons_columns ucc, user_constraints uc " +
				"WHERE ucc.constraint_name = uc.constraint_name AND ucc.table_name = uc.table_name " +
				"AND uc.constraint_type  = 'P'" +
				"ORDER BY ucc.constraint_name, ucc.position");
		while (rs.next()) {
			String constraint_name = rs.getString("constraint_name");
			String schema_name = rs.getString("owner");
			String table_name = rs.getString("table_name");
			String column_name = rs.getString("column_name");
			if (constraint_name.startsWith("BIN$") && table_name.startsWith("BIN$")) continue;
			Map<String, Set<String>> schema = pks.get(schema_name);
			if (schema == null) {
				schema = new LinkedHashMap<String, Set<String>>();
				pks.put(schema_name, schema);
			}
			Set<String> table = schema.get(table_name);
			if (table == null) {
				table = new LinkedHashSet<String>();
				schema.put(table_name, table);
			}
			table.add(column_name);
			System.out.println("found PK column: "+ schema_name +"."+ table_name +"."+ column_name);
		}
		stmt.close();
		return pks;
	}

	private Map<String, Map<String, Set<String>>> getPrimaryKeysSQLITE3(
			final Connection conn) throws SQLException {
		final Map<String, Map<String, Set<String>>> pks = new LinkedHashMap<String, Map<String, Set<String>>>();
		final DatabaseMetaData metadata = conn.getMetaData();
		final ResultSet tableRS = metadata.getTables(null, null, null, null);
		while (tableRS.next()) {
			final String catalog = tableRS.getString("TABLE_CAT");
			String schema = tableRS.getString("TABLE_SCHEM");
			if (schema == null) schema = "";
			final String tableName = tableRS.getString("TABLE_NAME");
			final String tableType = tableRS.getString("TABLE_TYPE");
			if (!"TABLE".equals(tableType)) continue;
			Map<String, Set<String>> tables = pks.get(schema);
			if (tables == null) {
				System.out.println("found schema: "+ schema);
				tables = new LinkedHashMap<String, Set<String>>();
				pks.put(schema, tables);
			}
			Set<String> pk = tables.get(tableName);
			if (pk == null) {
				System.out.println("found table: "+ tableName);
				pk = new LinkedHashSet<String>();
				tables.put(tableName, pk);
			}
		}
		final Statement stmt = conn.createStatement();
		for (final Entry<String, Map<String, Set<String>>> e1 : pks.entrySet()) {
			final Map<String, Set<String>> tables = e1.getValue();
			for (final Entry<String, Set<String>> e2 : tables.entrySet()) {
				final String tableName = e2.getKey();
				final Set<String> pk = e2.getValue();
				final ResultSet rs = stmt.executeQuery("pragma table_info("+ tableName +");");
				while (rs.next()) {
					if (rs.getInt(6) == 1) {
						pk.add(rs.getString(2));
					}
				}
			}
		}
		return pks;
	}

	private Map<String, Map<String, Set<String>>> getPrimaryKeysJDBC(
			final Connection conn) throws SQLException {
		final Map<String, Map<String, Set<String>>> pks = new LinkedHashMap<String, Map<String, Set<String>>>();
		final DatabaseMetaData metadata = conn.getMetaData();
		final ResultSet rs = metadata.getPrimaryKeys(null, null, null);
		while (rs.next()) {
			final String catalog = rs.getString("TABLE_CAT");
			final String schema = rs.getString("TABLE_SCHEM");
			final String tableName = rs.getString("TABLE_NAME");
			final String columnName = rs.getString("COLUMN_NAME");
			final String pkName = rs.getString("PK_NAME");
			if ("pg_toast".equals(schema)) continue;
			if (includeSchemas!=null && !includeSchemas.contains(schema)) continue;
			Map<String, Set<String>> tables = pks.get(schema);
			if (tables == null) {
				tables = new LinkedHashMap<String, Set<String>>();
				pks.put(schema, tables);
			}
			Set<String> pk = tables.get(tableName);
			if (pk == null) {
				pk = new LinkedHashSet<String>();
				tables.put(tableName, pk);
			}
			pk.add(columnName);
		}
		return pks;
	}

	private Map<String, Map<String, Set<String>>> getPrimaryKeysByTableJDBC(
			final Connection conn, Map<String, Map<String, Map<String, String>>> schemas) throws SQLException {
		final Map<String, Map<String, Set<String>>> pks = new LinkedHashMap<String, Map<String, Set<String>>>();
		final DatabaseMetaData metadata = conn.getMetaData();
		for (Entry<String, Map<String, Map<String, String>>> e1 : schemas.entrySet()) {
			String schema = e1.getKey();
			for (String table : e1.getValue().keySet()) {
				final ResultSet rs = metadata.getPrimaryKeys(null, schema, table);
				while (rs.next()) {
					final String catalog = rs.getString("TABLE_CAT");
					final String schemaName = rs.getString("TABLE_SCHEM");
					final String tableName = rs.getString("TABLE_NAME");
					final String columnName = rs.getString("COLUMN_NAME");
					final String pkName = rs.getString("PK_NAME");
					if ("pg_toast".equals(schema)) continue;
					if (includeSchemas!=null && !includeSchemas.contains(schemaName)) continue;
					Map<String, Set<String>> tables = pks.get(schemaName);
					if (tables == null) {
						tables = new LinkedHashMap<String, Set<String>>();
						pks.put(schemaName, tables);
					}
					Set<String> pk = tables.get(tableName);
					if (pk == null) {
						pk = new LinkedHashSet<String>();
						tables.put(tableName, pk);
					}
					pk.add(columnName);
				}
				rs.close();
			}
		}
		return pks;
	}

	private Map<String, Map<String, Set<String>>> getPrimaryKeysMSSQL(final Connection conn) throws SQLException {
	    final Map<String,Map<String,Map<String,String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();
		final Map<String,Map<String,Set<String>>> primaryKeys =
			new LinkedHashMap<String, Map<String, Set<String>>>();

	    final Statement s = conn.createStatement();
	    s.execute("SELECT name FROM sys.databases order by name;");
	    final ResultSet rs2 = s.getResultSet();
	    while (rs2.next()) {
		final String schema = rs2.getString("name");
		final Map<String, Map<String, String>> tables = new LinkedHashMap<String, Map<String, String>>();
		schemas.put(schema,tables);
	    }
	    rs2.close();



	    for(final String schema2 : schemas.keySet()) {
			if (ignoredSchemas.contains(schema2)) continue;
			if (includeSchemas != null && !includeSchemas.contains(schema2))
				continue;
	    s.execute("use \""+ schema2 +"\";");
		s.execute("SELECT A.TABLE_CATALOG, A.TABLE_NAME, B.COLUMN_NAME, A.CONSTRAINT_NAME " +
				"FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS A, " +
				"INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE B " +
				"WHERE CONSTRAINT_TYPE = 'PRIMARY KEY' " +
				"AND A.CONSTRAINT_NAME = B.CONSTRAINT_NAME " +
				"ORDER BY A.TABLE_CATALOG, A.TABLE_NAME, B.COLUMN_NAME, A.CONSTRAINT_NAME;");
		final ResultSet rs = s.getResultSet();
		while (rs.next()) {
			final String schema = rs.getString("TABLE_CATALOG");
			final String table = rs.getString("TABLE_NAME");
			final String column = rs.getString("COLUMN_NAME");

			if (ignoredSchemas.contains(schema)) continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;
			if (!includeTable(schema, table)) continue;

			Map<String, Map<String, String>> tables = schemas.get(schema);
			if (tables==null) {
				tables = new LinkedHashMap<String, Map<String, String>>();
				schemas.put(schema,tables);
			}

			Map<String, Set<String>> pkTables = primaryKeys.get(schema);
			if (pkTables==null) {
				pkTables = new LinkedHashMap<String, Set<String>>();
				primaryKeys.put(schema,pkTables);
			}

			Map<String, String> columns = tables.get(table);
			if (columns==null) {
				columns = new LinkedHashMap<String, String>();
				tables.put(table,columns);
			}

			Set<String> pkColumns = pkTables.get(table);
			if (pkColumns==null) {
				pkColumns = new LinkedHashSet<String>();
				pkTables.put(table,pkColumns);
			}

			pkColumns.add(column);
		}
		rs.close();
	    }

		s.close();

		return primaryKeys;
	}

	private Map<String, Map<String, Set<String>>> getPrimaryKeysHSQL(final Connection conn) throws SQLException {
	    final Map<String,Map<String,Map<String,String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();
		final Map<String,Map<String,Set<String>>> primaryKeys =
			new LinkedHashMap<String, Map<String, Set<String>>>();

	    final Statement s = conn.createStatement();
		s.execute("select a.table_catalog, a.table_name, b.column_name, a.constraint_name " +
				"from information_schema.table_constraints a, " +
				"information_schema.constraint_column_usage b " +
				"where constraint_type = 'PRIMARY KEY' " +
				"and a.constraint_name = b.constraint_name " +
				"order by a.table_catalog, a.table_name, b.column_name, a.constraint_name;");
		final ResultSet rs = s.getResultSet();
		while (rs.next()) {
			final String schema = rs.getString("table_catalog");
			final String table = rs.getString("table_name");
			final String column = rs.getString("column_name");

			if (ignoredSchemas.contains(schema)) continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;
			if (!includeTable(schema, table)) continue;

			Map<String, Map<String, String>> tables = schemas.get(schema);
			if (tables==null) {
				tables = new LinkedHashMap<String, Map<String, String>>();
				schemas.put(schema,tables);
			}

			Map<String, Set<String>> pkTables = primaryKeys.get(schema);
			if (pkTables==null) {
				pkTables = new LinkedHashMap<String, Set<String>>();
				primaryKeys.put(schema,pkTables);
			}

			Map<String, String> columns = tables.get(table);
			if (columns==null) {
				columns = new LinkedHashMap<String, String>();
				tables.put(table,columns);
			}

			Set<String> pkColumns = pkTables.get(table);
			if (pkColumns==null) {
				pkColumns = new LinkedHashSet<String>();
				pkTables.put(table,pkColumns);
			}

			pkColumns.add(column);
		}
		rs.close();

		s.close();

		return primaryKeys;
	}

	private Map<String,Map<String,Set<String>>> getPrimaryKeysMySQL(final Connection conn) throws SQLException {
	    final Map<String,Map<String,Map<String,String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();
		final Map<String,Map<String,Set<String>>> primaryKeys =
			new LinkedHashMap<String, Map<String, Set<String>>>();

		final Statement s = conn.createStatement();
		s.execute("select table_schema, table_name, column_name, column_key " +
				"from information_schema.columns " +
				"order by table_schema, table_name, column_name, column_key;");
		final ResultSet rs = s.getResultSet();
		while (rs.next()) {
			final String schema = rs.getString("table_schema");
			final String table = rs.getString("table_name");
			final String column = rs.getString("column_name");
			final String columnKey = rs.getString("column_key");

			if (ignoredSchemas.contains(schema)) continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;
			if (!includeTable(schema, table)) continue;

			Map<String, Map<String, String>> tables = schemas.get(schema);
			if (tables==null) {
				tables = new LinkedHashMap<String, Map<String, String>>();
				schemas.put(schema,tables);
			}

			Map<String, Set<String>> pkTables = primaryKeys.get(schema);
			if (pkTables==null) {
				pkTables = new LinkedHashMap<String, Set<String>>();
				primaryKeys.put(schema,pkTables);
			}

			Map<String, String> columns = tables.get(table);
			if (columns==null) {
				columns = new LinkedHashMap<String, String>();
				tables.put(table,columns);
			}

			Set<String> pkColumns = pkTables.get(table);
			if (pkColumns==null) {
				pkColumns = new HashSet<String>();
				pkTables.put(table,pkColumns);
			}

			if ("PRI".equals(columnKey)) {
				pkColumns.add(column);
			}
		}
		rs.close();
		s.close();

	    return primaryKeys;
	}

	private Map<String, Map<String,Object>> getForeignKeys(
		final Connection conn) throws SQLException {
		if (dbType == DB_TYPE.SQLSERVER) return getForeignKeysMSSQL(conn);
		if (dbType == DB_TYPE.HSQL) return getForeignKeysHSQL(conn);
		if (dbType == DB_TYPE.SQLITE3) return getForeignKeysJDBC(conn);
		if (dbType == DB_TYPE.MYSQL) return getForeignKeysMySQL(conn);
		if (dbType == DB_TYPE.POSTGRES) return getForeignKeysJDBC(conn);
		if (dbType == DB_TYPE.ORACLE) return getForeignKeysJDBC(conn);
		if (dbType == DB_TYPE.DERBY) return getForeignKeysJDBC(conn);
		return getForeignKeysJDBC(conn);
	}

	private Map<String, Map<String, Object>> getNoForeignKeys(final Connection conn) {
		return new LinkedHashMap<String, Map<String, Object>>();
	}

	private Map<String, Map<String,Object>> getForeignKeysMSSQL(
		final Connection conn) throws SQLException {
	    final Map<String, Map<String,Object>> foreignKeys =
			new LinkedHashMap<String, Map<String,Object>>();

		final Statement s = conn.createStatement();
		s.execute("SELECT KCU1.TABLE_CATALOG AS 'TABLE_SCHEMA', KCU1.CONSTRAINT_NAME, KCU1.TABLE_NAME, KCU1.COLUMN_NAME, KCU2.TABLE_CATALOG AS 'REFERENCED_TABLE_SCHEMA', KCU2.TABLE_NAME AS 'REFERENCED_TABLE_NAME', KCU2.COLUMN_NAME AS 'REFERENCED_COLUMN_NAME' " +
				"FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS RC " +
				"JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU1 ON KCU1.CONSTRAINT_CATALOG = RC.CONSTRAINT_CATALOG AND KCU1.CONSTRAINT_SCHEMA = RC.CONSTRAINT_SCHEMA AND KCU1.CONSTRAINT_NAME = RC.CONSTRAINT_NAME " +
				"JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU2 ON KCU2.CONSTRAINT_CATALOG = RC.UNIQUE_CONSTRAINT_CATALOG AND KCU2.CONSTRAINT_SCHEMA = RC.UNIQUE_CONSTRAINT_SCHEMA AND KCU2.CONSTRAINT_NAME = RC.UNIQUE_CONSTRAINT_NAME AND KCU2.ORDINAL_POSITION = KCU1.ORDINAL_POSITION " +
				"ORDER BY CONSTRAINT_NAME, TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, KCU1.ORDINAL_POSITION;");
		final ResultSet rs = s.getResultSet();

		while (rs.next()) {
			final String constraint_name = rs.getString("CONSTRAINT_NAME");
			final String schema = rs.getString("TABLE_SCHEMA");
			final String table = rs.getString("TABLE_NAME");
			final String column = rs.getString("COLUMN_NAME");
			final String referenced_schema = rs.getString("REFERENCED_TABLE_SCHEMA");
			final String referenced_table = rs.getString("REFERENCED_TABLE_NAME");
			final String referenced_column = rs.getString("REFERENCED_COLUMN_NAME");

			if (!includeTable(schema, table) || !includeTable(referenced_schema, referenced_table)) continue;

			Map<String, Object> fk = foreignKeys.get(constraint_name);
			if (fk==null) {
			    fk = new LinkedHashMap<String,Object>();
			    final String[] reffing = {schema, table};
			    fk.put("reffing", reffing);
			    final String[] reffed = {referenced_schema, referenced_table};
			    fk.put("reffed", reffed);
				foreignKeys.put(constraint_name,fk);
				fk.put("columns", new LinkedHashMap<String,String>());
			}

			@SuppressWarnings("unchecked")
			final
			Map<String,String> columns = (Map<String,String>) fk.get("columns");
			columns.put(column, referenced_column);

		}

		return foreignKeys;
	}

	private Map<String, Map<String,Object>> getForeignKeysMySQL(
			final Connection conn) throws SQLException {
	    final Map<String, Map<String,Object>> foreignKeys =
			new LinkedHashMap<String, Map<String,Object>>();

		final Statement s = conn.createStatement();
		s.execute("select constraint_name, table_schema, table_name, column_name, " +
				"  referenced_table_schema, referenced_table_name, " +
				"  referenced_column_name " +
				"from information_schema.key_column_usage " +
				"where table_schema is not null " +
				"  and table_name is not null " +
				"  and column_name is not null " +
				"  and referenced_table_schema is not null " +
				"  and referenced_table_name is not null " +
				"  and referenced_column_name is not null " +
				"order by constraint_name, table_schema, table_name, column_name;");
		final ResultSet rs = s.getResultSet();

		while (rs.next()) {
			final String constraint_name = rs.getString("constraint_name");
			final String schema = rs.getString("table_schema");
			final String table = rs.getString("table_name");
			final String column = rs.getString("column_name");
			final String referenced_schema = rs.getString("referenced_table_schema");
			final String referenced_table = rs.getString("referenced_table_name");
			final String referenced_column = rs.getString("referenced_column_name");

			if (!includeTable(schema, table) || !includeTable(referenced_schema, referenced_table)) continue;

			Map<String, Object> fk = foreignKeys.get(constraint_name);
			if (fk==null) {
			    fk = new LinkedHashMap<String,Object>();
			    final String[] reffing = {schema, table};
			    fk.put("reffing", reffing);
			    final String[] reffed = {referenced_schema, referenced_table};
			    fk.put("reffed", reffed);
				foreignKeys.put(constraint_name,fk);
				fk.put("columns", new LinkedHashMap<String,String>());
			}

			@SuppressWarnings("unchecked")
			final
			Map<String,String> columns = (Map<String,String>) fk.get("columns");
			columns.put(column, referenced_column);

		}

		return foreignKeys;
	}

	private Map<String, Map<String,Object>> getForeignKeysHSQL(
			final Connection conn) throws SQLException {
	    final Map<String, Map<String,Object>> foreignKeys =
			new LinkedHashMap<String, Map<String,Object>>();

		final Statement s = conn.createStatement();
		s.execute("select fk_name, pktable_schem, pktable_name, pkcolumn_name, " +
				"  fktable_schem, fktable_name, " +
				"  fkcolumn_name " +
				"from information_schema.SYSTEM_CROSSREFERENCE " +
				"where pktable_schem is not null " +
				"  and pktable_name is not null " +
				"  and pkcolumn_name is not null " +
				"  and fktable_schem is not null " +
				"  and fktable_name is not null " +
				"  and fkcolumn_name is not null " +
				"order by fk_name, pktable_schem, pktable_name, pkcolumn_name;");
		final ResultSet rs = s.getResultSet();

		while (rs.next()) {
			final String constraint_name = rs.getString("fk_name");
			final String schema = rs.getString("fktable_schem");
			final String table = rs.getString("fktable_name");
			final String column = rs.getString("fkcolumn_name");
			final String referenced_schema = rs.getString("pktable_schem");
			final String referenced_table = rs.getString("pktable_name");
			final String referenced_column = rs.getString("pkcolumn_name");

			if (!includeTable(schema, table) || !includeTable(referenced_schema, referenced_table)) continue;

			Map<String, Object> fk = foreignKeys.get(constraint_name);
			if (fk==null) {
			    fk = new LinkedHashMap<String,Object>();
			    final String[] reffing = {schema, table};
			    fk.put("reffing", reffing);
			    final String[] reffed = {referenced_schema, referenced_table};
			    fk.put("reffed", reffed);
				foreignKeys.put(constraint_name,fk);
				fk.put("columns", new LinkedHashMap<String,String>());
			}

			@SuppressWarnings("unchecked")
			final
			Map<String,String> columns = (Map<String,String>) fk.get("columns");
			columns.put(column, referenced_column);

		}

		return foreignKeys;
	}

	private Map<String, Map<String,Object>> getForeignKeysJDBC(
			final Connection conn) throws SQLException {
		    final Map<String, Map<String, Object>> foreignKeys =
				new LinkedHashMap<String, Map<String,Object>>();

			final DatabaseMetaData metadata = conn.getMetaData();
			
			final ResultSet tableRS = metadata.getTables(null, null, null, null);
			Map<String,Map<String,List<String>>> cats = new LinkedHashMap<String,Map<String,List<String>>>();
			while (tableRS.next()) {
				final String catName = tableRS.getString("TABLE_CAT");
				final String schemaName = tableRS.getString("TABLE_SCHEM");
				if (includeSchemas!=null && !includeSchemas.contains(schemaName)) continue;
				final String tableName = tableRS.getString("TABLE_NAME");
				Map<String, List<String>> schemas = cats.get(catName);
				if (schemas==null) {
					schemas = new LinkedHashMap<String, List<String>>();
					cats.put(catName, schemas);
				}
				List<String> tables = schemas.get(schemaName);
				if (tables==null) {
					tables = new ArrayList<String>();
					schemas.put(schemaName, tables);
				}
				tables.add(tableName);
			}
			tableRS.close();

			int c = 0;
			
			for (Entry<String, Map<String, List<String>>> e1 : cats.entrySet()) {
				String catName = e1.getKey();
				for (Entry<String, List<String>> e2 : e1.getValue().entrySet()) {
					String schema = e2.getKey();
					if (schema==null) schema = "";
					for (String table : e2.getValue()) {
						ResultSet rs = metadata.getImportedKeys(catName, schema, table);
						while (rs.next()) {
							String constraint_name = rs.getString("FK_NAME");
							if (constraint_name==null || "".equals(constraint_name)) {
								constraint_name = "FK_"+ (++c);
							}
							final String column = rs.getString("FKCOLUMN_NAME");
							String referenced_schema = rs.getString("PKTABLE_SCHEM");
							if (referenced_schema==null) referenced_schema = "";
							final String referenced_table = rs.getString("PKTABLE_NAME");
							final String referenced_column = rs.getString("PKCOLUMN_NAME");
							System.out.println("found FK: "+ table +" => "+ referenced_table);

							if (!includeTable(schema, table) || !includeTable(referenced_schema, referenced_table)) continue;
							
							Map<String, Object> fk = foreignKeys.get(constraint_name);
							if (fk == null) {
								fk = new LinkedHashMap<String, Object>();
								final String[] reffing = { schema, table };
								fk.put("reffing", reffing);
								final String[] reffed = { referenced_schema, referenced_table };
								fk.put("reffed", reffed);
								foreignKeys.put(constraint_name, fk);
								fk.put("columns", new LinkedHashMap<String, String>());
								Map<String,String> columns = (Map<String,String>) fk.get("columns");
								columns.put(column, referenced_column);
								foreignKeys.put(constraint_name, fk);
							}
						}
						rs.close();
					}
				}
			}

			return foreignKeys;
		}

}

