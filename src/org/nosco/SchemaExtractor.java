package org.nosco;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

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
		put("driver", "com.mysql.jdbc.Driver");
	}});

	private static int[] version = {0,1,0};
	
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

		Class.forName(params.get("driver")).newInstance();
		Connection conn = DriverManager.getConnection (params.get("url"), 
				params.get("username"), params.get("password"));

		Statement s = conn.createStatement();
		s.execute("select table_schema, table_name, column_name, data_type, column_key " +
				"from information_schema.columns;");
		ResultSet rs = s.getResultSet();
		
		Map<String,Map<String,Map<String,String>>> schemas = 
			new HashMap<String, Map<String, Map<String, String>>>();
		
		Map<String,Map<String,Set<String>>> primaryKeys = 
			new HashMap<String, Map<String, Set<String>>>();
		
		while (rs.next()) {
			String schema = rs.getString("table_schema");
			String table = rs.getString("table_name");
			String column = rs.getString("column_name");
			String type = rs.getString("data_type");
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

			columns.put(column, type);
			if ("PRI".equals(columnKey)) {
				pkColumns.add(column);
			}

		}
		
		Map<String,Map<String,Map<String,String[]>>> foreignKeys = getForeignKeys(conn);
			

		
		
		
		JSONObject json = new JSONObject();
		json.put("version", new JSONArray(version ));
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

	private static Map<String, Map<String, Map<String, String[]>>> getForeignKeys(
			Connection conn) throws SQLException {
		Map<String, Map<String, Map<String, String[]>>> foreignKeys = 
			new HashMap<String, Map<String, Map<String, String[]>>>();

		Statement s = conn.createStatement();
		s.execute("SELECT TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, REFERENCED_TABLE_SCHEMA, " +
				"       REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME " +
				"FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE " +
				"where TABLE_SCHEMA is not null and TABLE_NAME is not null " +
				"  and COLUMN_NAME is not null and REFERENCED_TABLE_SCHEMA is not null " +
				"  and REFERENCED_TABLE_NAME is not null and REFERENCED_COLUMN_NAME is not null " +
				"order by TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME, REFERENCED_TABLE_SCHEMA, " +
				"         REFERENCED_TABLE_NAME, REFERENCED_COLUMN_NAME; ");
		ResultSet rs = s.getResultSet();

		while (rs.next()) {
			String schema = rs.getString("table_schema");
			String table = rs.getString("table_name");
			String column = rs.getString("column_name");
			String referenced_schema = rs.getString("referenced_table_schema");
			String referenced_table = rs.getString("referenced_table_name");
			String referenced_column = rs.getString("referenced_column_name");

			Map<String, Map<String, String[]>> tables = foreignKeys.get(schema);
			if (tables==null) {
				tables = new HashMap<String, Map<String, String[]>>();
				foreignKeys.put(schema,tables);
			}

			Map<String, String[]> columns = tables.get(table);
			if (columns==null) {
				columns = new HashMap<String, String[]>();
				tables.put(table,columns);
			}
			
			String[] referenced = {referenced_schema, referenced_table, referenced_column};
			columns.put(column, referenced);
		
		}

		return foreignKeys;
	}

}

