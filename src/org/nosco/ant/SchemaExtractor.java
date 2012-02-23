package org.nosco.ant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.tools.ant.Task;
import org.nosco.Constants;
import org.nosco.Constants.DB_TYPE;
import org.nosco.json.JSONArray;
import org.nosco.json.JSONException;
import org.nosco.json.JSONObject;

public class SchemaExtractor extends Task {

	private static int[] version = {0,2,0};

	private String fn = null;
	private DB_TYPE dbType = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private HashSet<String> includeSchemas = null;

	public void setOut(String s) {
		this.fn = s;
	}

	public void setDBType(String s) {
		if ("sqlserver".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.SQLSERVER;
		if ("mysql".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.MYSQL;
	}

	public void setURL(String s) {
		this.url = s;
		if (url.startsWith("jdbc:sqlserver")) {
			try {
				Driver d = (Driver) Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setUsername(String s) {
		this.username = s;
	}

	public void setPassword(String s) {
		this.password = s;
	}

	public void setPasswordFile(String s) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(s));
			this.password = br.readLine().trim();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSchemas(String s) {
		this.includeSchemas = new HashSet<String>();
		for (String schema : s.split(",")) {
			this.includeSchemas.add(schema);
		}
	}

	public void execute() {

		Connection conn;
		try {

			conn = DriverManager.getConnection (url, username, password);
			Map<String,Map<String,Map<String,String>>> schemas = getSchemas(conn);
			Map<String,Map<String,Set<String>>> primaryKeys =getPrimaryKeys(conn);
			Map<String, Map<String,Object>> foreignKeys = getForeignKeys(conn);

			JSONObject json = new JSONObject();
			json.put("version", new JSONArray(version));
			json.put("schemas", new JSONObject(schemas));
			json.put("primary_keys", new JSONObject(primaryKeys));
			json.put("foreign_keys", new JSONObject(foreignKeys));

			File f = new File(fn);
			System.err.println("writing: "+ f.getAbsolutePath());
			FileWriter w = new FileWriter(f);
			w.write(json.toString(4));
			w.close();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	private static Set<String> ignoredSchemas = new HashSet<String>() {{
		add("information_schema");
		add("mysql");
	}};

	private Map<String, Map<String, Map<String, String>>> getSchemas(
			Connection conn) throws SQLException {
	    if (conn.getClass().getName().startsWith("com.mysql")) {
		return getSchemasMySQL(conn);
	    } else if (conn.getClass().getName().startsWith("com.microsoft")) {
		return getSchemasMSSQL(conn);
	    }
	    return null;
	}

	private Map<String, Map<String, Map<String, String>>> getSchemasMySQL(
			Connection conn)
			throws SQLException {
		Map<String, Map<String, Map<String, String>>> schemas = new HashMap<String, Map<String, Map<String, String>>>();

		Statement s = conn.createStatement();
		s.execute("select table_schema, table_name, column_name, data_type "
				+ "from information_schema.columns;");
		ResultSet rs = s.getResultSet();
		while (rs.next()) {
			String schema = rs.getString("table_schema");
			String table = rs.getString("table_name");
			String column = rs.getString("column_name");
			String type = rs.getString("data_type");

			if (ignoredSchemas.contains(schema))
				continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;

			Map<String, Map<String, String>> tables = schemas.get(schema);
			if (tables == null) {
				tables = new HashMap<String, Map<String, String>>();
				schemas.put(schema, tables);
			}

			Map<String, String> columns = tables.get(table);
			if (columns == null) {
				columns = new HashMap<String, String>();
				tables.put(table, columns);
			}

			columns.put(column, type);
		}
		rs.close();
		s.close();

		return schemas;
	}

	private Map<String, Map<String, Map<String, String>>> getSchemasMSSQL(Connection conn)
			throws SQLException {
		Map<String, Map<String, Map<String, String>>> schemas = new HashMap<String, Map<String, Map<String, String>>>();

		Statement s = conn.createStatement();
		s.execute("SELECT name FROM sys.databases;");
		ResultSet rs = s.getResultSet();
		while (rs.next()) {
			String schema = rs.getString("name");
			if (ignoredSchemas.contains(schema))
				continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;
			Map<String, Map<String, String>> tables = new HashMap<String, Map<String, String>>();
			schemas.put(schema, tables);
		}
		rs.close();
		// s.close();

		for (String schema : schemas.keySet()) {
			if (ignoredSchemas.contains(schema))
				continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;

			System.err.println("extracting schema for: "+ schema);
			Map<String, Map<String, String>> tables = schemas.get(schema);

			try {
				s.execute("use \"" + schema + "\";");
				s.execute("select table_schema, table_name, column_name, data_type "
						+ "from information_schema.columns;");
				ResultSet rs2 = s.getResultSet();
				while (rs2.next()) {
					String table = rs2.getString("table_name");
					if (table.startsWith("syncobj_")) continue;
					if (table.startsWith("MS") && table.length() > 2
							&& Character.isLowerCase(table.charAt(2))) continue;
					String column = rs2.getString("column_name");
					String type = rs2.getString("data_type");

					Map<String, String> columns = tables.get(table);
					if (columns == null) {
						columns = new HashMap<String, String>();
						tables.put(table, columns);
					}

					columns.put(column, type);
				}
				rs2.close();
			} catch (SQLException e) {
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

	private Map<String,Map<String,Set<String>>> getPrimaryKeys(Connection conn) throws SQLException {
	    if (conn.getClass().getName().startsWith("com.mysql")) {
		return getPrimaryKeysMySQL(conn);
	    } else if (conn.getClass().getName().startsWith("com.microsoft")) {
		return getPrimaryKeysMSSQL(conn);
	    }
	    return null;
	}

	private Map<String, Map<String, Set<String>>> getPrimaryKeysMSSQL(Connection conn) throws SQLException {
	    Map<String,Map<String,Map<String,String>>> schemas = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String,Map<String,Set<String>>> primaryKeys =
			new HashMap<String, Map<String, Set<String>>>();

	    Statement s = conn.createStatement();
	    s.execute("SELECT name FROM sys.databases;");
	    ResultSet rs2 = s.getResultSet();
	    while (rs2.next()) {
		String schema = rs2.getString("name");
		Map<String, Map<String, String>> tables = new HashMap<String, Map<String, String>>();
		schemas.put(schema,tables);
	    }
	    rs2.close();



	    for(String schema2 : schemas.keySet()) {
			if (ignoredSchemas.contains(schema2)) continue;
			if (includeSchemas != null && !includeSchemas.contains(schema2))
				continue;
	    s.execute("use \""+ schema2 +"\";");
		s.execute("select a.table_catalog, a.table_name, b.column_name, a.constraint_name " +
				"from information_schema.table_constraints a, " +
				"information_schema.constraint_column_usage b " +
				"where constraint_type = 'PRIMARY KEY' " +
				"and a.constraint_name = b.constraint_name " +
				"order by a.table_name");
		ResultSet rs = s.getResultSet();
		while (rs.next()) {
			String schema = rs.getString("table_catalog");
			String table = rs.getString("table_name");
			String column = rs.getString("column_name");

			if (ignoredSchemas.contains(schema)) continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;

			Map<String, Map<String, String>> tables = schemas.get(schema);
			if (tables==null) {
				tables = new HashMap<String, Map<String, String>>();
				schemas.put(schema,tables);
			}

			Map<String, Set<String>> pkTables = primaryKeys.get(schema);
			if (pkTables==null) {
				pkTables = new HashMap<String, Set<String>>();
				primaryKeys.put(schema,pkTables);
			}

			Map<String, String> columns = tables.get(table);
			if (columns==null) {
				columns = new HashMap<String, String>();
				tables.put(table,columns);
			}

			Set<String> pkColumns = pkTables.get(table);
			if (pkColumns==null) {
				pkColumns = new HashSet<String>();
				pkTables.put(table,pkColumns);
			}

			pkColumns.add(column);
		}
		rs.close();
	    }

		s.close();

		return primaryKeys;
	}

	private Map<String,Map<String,Set<String>>> getPrimaryKeysMySQL(Connection conn) throws SQLException {
	    Map<String,Map<String,Map<String,String>>> schemas = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String,Map<String,Set<String>>> primaryKeys =
			new HashMap<String, Map<String, Set<String>>>();

		Statement s = conn.createStatement();
		s.execute("select table_schema, table_name, column_name, column_key " +
				"from information_schema.columns;");
		ResultSet rs = s.getResultSet();
		while (rs.next()) {
			String schema = rs.getString("table_schema");
			String table = rs.getString("table_name");
			String column = rs.getString("column_name");
			String columnKey = rs.getString("column_key");

			if (ignoredSchemas.contains(schema)) continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;

			Map<String, Map<String, String>> tables = schemas.get(schema);
			if (tables==null) {
				tables = new HashMap<String, Map<String, String>>();
				schemas.put(schema,tables);
			}

			Map<String, Set<String>> pkTables = primaryKeys.get(schema);
			if (pkTables==null) {
				pkTables = new HashMap<String, Set<String>>();
				primaryKeys.put(schema,pkTables);
			}

			Map<String, String> columns = tables.get(table);
			if (columns==null) {
				columns = new HashMap<String, String>();
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

	private static Map<String, Map<String,Object>> getForeignKeys(
		Connection conn) throws SQLException {
	    if (conn.getClass().getName().startsWith("com.mysql")) {
		return getForeignKeysMySQL(conn);
	    } else if (conn.getClass().getName().startsWith("com.microsoft")) {
		return getForeignKeysMSSQL(conn);
	    }
	    return null;
	}

	private static Map<String, Map<String,Object>> getForeignKeysMSSQL(
		Connection conn) {
	    return null;
	}

	private static Map<String, Map<String,Object>> getForeignKeysMySQL(
			Connection conn) throws SQLException {
	    Map<String, Map<String,Object>> foreignKeys =
			new HashMap<String, Map<String,Object>>();

		Statement s = conn.createStatement();
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
				"order by constraint_name;");
		ResultSet rs = s.getResultSet();

		while (rs.next()) {
			String constraint_name = rs.getString("constraint_name");
			String schema = rs.getString("table_schema");
			String table = rs.getString("table_name");
			String column = rs.getString("column_name");
			String referenced_schema = rs.getString("referenced_table_schema");
			String referenced_table = rs.getString("referenced_table_name");
			String referenced_column = rs.getString("referenced_column_name");

			Map<String, Object> fk = foreignKeys.get(constraint_name);
			if (fk==null) {
			    fk = new HashMap<String,Object>();
			    String[] reffing = {schema, table};
			    fk.put("reffing", reffing);
			    String[] reffed = {referenced_schema, referenced_table};
			    fk.put("reffed", reffed);
				foreignKeys.put(constraint_name,fk);
				fk.put("columns", new HashMap<String,String>());
			}

			@SuppressWarnings("unchecked")
			Map<String,String> columns = (Map<String,String>) fk.get("columns");
			columns.put(column, referenced_column);

		}

		return foreignKeys;
	}

}

