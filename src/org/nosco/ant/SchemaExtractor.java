package org.nosco.ant;

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

import org.nosco.json.JSONArray;
import org.nosco.json.JSONException;
import org.nosco.json.JSONObject;
import org.nosco.util.RSArgsParser;

public class SchemaExtractor {

	private static Set<String> ignoredSchemas = new HashSet<String>() {{
		add("information_schema");
		add("mysql");
	}};

	private static RSArgsParser argsParser = new RSArgsParser(new HashMap<String,Boolean>() {{
		put("url", true);
		put("username", true);
		put("password", true);
		put("driver", true);
		put("filename", true);
	}}, new HashMap<String,String>() {{
		put("u", "username");
		put("p", "password");
		put("d", "driver");
		put("f", "filename");
	}}, new HashMap<String,String>() {{
		put("url", "jdbc:mysql://localhost/");
		put("username", "root");
		put("password", "");
	}});

	private static int[] version = {0,2,0};

	/**
	 * @param args
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws JSONException
	 * @throws IOException
	 */
	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, JSONException, IOException {
		Map<String, String> params = argsParser.parse(args);

		//Driver d = (Driver)Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
		Connection conn = DriverManager.getConnection (params.get("url"),
				params.get("username"), params.get("password"));

		Map<String,Map<String,Map<String,String>>> schemas = getSchemas(conn);

		Map<String,Map<String,Set<String>>> primaryKeys =getPrimaryKeys(conn);

		Map<String, Map<String,Object>> foreignKeys = getForeignKeys(conn);

		JSONObject json = new JSONObject();
		json.put("version", new JSONArray(version));
		json.put("schemas", new JSONObject(schemas));
		json.put("primary_keys", new JSONObject(primaryKeys));
		json.put("foreign_keys", new JSONObject(foreignKeys));

		if (params.containsKey("filename")) {
			FileWriter w = new FileWriter(params.get("filename"));
			w.write(json.toString(4));
			w.close();
		} else {
			System.out.println(json.toString(4));
		}

	}

	private static Map<String, Map<String, Map<String, String>>> getSchemas(Connection conn) throws SQLException {
	    if (conn.getClass().getName().startsWith("com.mysql")) {
		return getSchemasMySQL(conn);
	    } else if (conn.getClass().getName().startsWith("com.microsoft")) {
		return getSchemasMSSQL(conn);
	    }
	    return null;
	}

	private static Map<String, Map<String, Map<String, String>>> getSchemasMySQL(Connection conn) throws SQLException {
	    Map<String,Map<String,Map<String,String>>> schemas = new HashMap<String, Map<String, Map<String, String>>>();

	    Statement s = conn.createStatement();
	    s.execute("select table_schema, table_name, column_name, data_type " +
		    "from information_schema.columns;");
	    ResultSet rs = s.getResultSet();
	    while (rs.next()) {
		String schema = rs.getString("table_schema");
		String table = rs.getString("table_name");
		String column = rs.getString("column_name");
		String type = rs.getString("data_type");

		if (ignoredSchemas.contains(schema)) continue;

		Map<String, Map<String, String>> tables = schemas.get(schema);
		if (tables==null) {
		    tables = new HashMap<String, Map<String, String>>();
		    schemas.put(schema,tables);
		}

		Map<String, String> columns = tables.get(table);
		if (columns==null) {
		    columns = new HashMap<String, String>();
		    tables.put(table,columns);
		}

		columns.put(column, type);
	    }
	    rs.close();
	    s.close();

	    return schemas;
	}

	private static Map<String, Map<String, Map<String, String>>> getSchemasMSSQL(Connection conn) throws SQLException {
	    Map<String,Map<String,Map<String,String>>> schemas = new HashMap<String, Map<String, Map<String, String>>>();

	    Statement s = conn.createStatement();
	    s.execute("SELECT name FROM sys.databases;");
	    ResultSet rs = s.getResultSet();
	    while (rs.next()) {
		String schema = rs.getString("name");
		Map<String, Map<String, String>> tables = new HashMap<String, Map<String, String>>();
		schemas.put(schema,tables);
	    }
	    rs.close();
	    //s.close();

	    for(String schema : schemas.keySet()) {
		if (ignoredSchemas.contains(schema)) continue;
		System.err.println(schema);
		Map<String, Map<String, String>> tables = schemas.get(schema);

		try {
			s.execute("use \""+ schema +"\";");
			s.execute("select table_schema, table_name, column_name, data_type " +
					"from information_schema.columns;");
			ResultSet rs2 = s.getResultSet();
			while (rs2.next()) {
				String table = rs2.getString("table_name");
				String column = rs2.getString("column_name");
				String type = rs2.getString("data_type");

				Map<String, String> columns = tables.get(table);
				if (columns==null) {
					columns = new HashMap<String, String>();
					tables.put(table,columns);
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

	private static Map<String,Map<String,Set<String>>> getPrimaryKeys(Connection conn) throws SQLException {
	    if (conn.getClass().getName().startsWith("com.mysql")) {
		return getPrimaryKeysMySQL(conn);
	    } else if (conn.getClass().getName().startsWith("com.microsoft")) {
		return getPrimaryKeysMSSQL(conn);
	    }
	    return null;
	}

	private static Map<String, Map<String, Set<String>>> getPrimaryKeysMSSQL(Connection conn) throws SQLException {
	    Map<String,Map<String,Map<String,String>>> schemas = new HashMap<String, Map<String, Map<String, String>>>();
		Map<String,Map<String,Set<String>>> primaryKeys =
			new HashMap<String, Map<String, Set<String>>>();

		Statement s = conn.createStatement();
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
		s.close();

		return primaryKeys;
	}

	private static Map<String,Map<String,Set<String>>> getPrimaryKeysMySQL(Connection conn) throws SQLException {
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

