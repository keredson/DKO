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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tools.ant.Task;
import org.nosco.Constants;
import org.nosco.Constants.DB_TYPE;
import org.nosco.json.JSONArray;
import org.nosco.json.JSONException;
import org.nosco.json.JSONObject;

/**
 * Extracts schema information from a provided database into a JSON file. &nbsp;
 * Designed to be run occasionally, with the output file checked into version control.
 * (to avoid regular builds hitting your DB server, and for code generation history) &nbsp;
 *
 * @author Derek Anderson
 */
public class SchemaExtractor extends Task {

	private static int[] version = {0,2,0};

	private DB_TYPE dbType = null;
	private String url = null;
	private String username = null;
	private String password = null;
	private HashSet<String> includeSchemas = null;
	private String[] enums = {};
	private File enumsOut = null;
	private File out = null;

	public void setOut(String s) {
		this.out  = new File(s);
	}

	public void setEnums(String s) {
		if (s != null && s.length() > 0) this.enums = s.split(",");
	}

	public void setEnumsOut(String s) {
		this.enumsOut = s==null ? null : new File(s);
	}

	public void setDBType(String s) {
		if ("sqlserver".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.SQLSERVER;
		if ("mysql".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.MYSQL;
		if ("hsql".equalsIgnoreCase(s) || "hsqldb".equalsIgnoreCase(s))
			this.dbType  = Constants.DB_TYPE.HSQL;
	}

	public void setURL(String s) {
		this.url = s;
		if (url.startsWith("jdbc:sqlserver")) {
			try {
				Driver d = (Driver) Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
				dbType = DB_TYPE.SQLSERVER;
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
		if (url.startsWith("jdbc:hsqldb")) {
			try {
				Driver d = (Driver) Class.forName("org.hsqldb.jdbc.JDBCDriver").newInstance();
				dbType = DB_TYPE.HSQL;
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
			this.includeSchemas.add(schema.trim());
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

			System.err.println("writing: "+ out.getAbsolutePath());
			FileWriter w = new FileWriter(out);
			w.write(json.toString(4));
			w.close();

			if (enums!=null && enums.length > 0 && enumsOut != null) {
				JSONObject allEnums = new JSONObject();
				for (String x : enums) {
					x = x.trim();
					String[] xa = x.split("[.]");
					if (xa.length != 3) {
						throw new RuntimeException("'"+ x +"' " +
								"must be of the format 'schema.table.name_column'");
					}
					String schema = xa[0];
					String table = xa[1];
					String column = xa[2];
					allEnums.put(x, getEnums(conn, schema, table, column,
							primaryKeys.get(schema).get(table)));
				}
				System.err.println("writing: "+ enumsOut.getAbsolutePath());
				FileWriter w2 = new FileWriter(enumsOut);
				w2.write(allEnums.toString(4));
				w2.close();
			}



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

	private JSONObject getEnums(Connection conn, String schema, String table, String column,
			Set<String> pks) throws SQLException {
		if (pks == null) throw new RuntimeException("primary keys not set for enum table: "
			+ schema +"."+ table +"."+ column);
		String sep =".";
		if (conn.getClass().getName().startsWith("com.microsoft")) {
			sep ="..";
	    }
		JSONObject ret = new JSONObject();
		String sql = "select "+ column +", "+ Util.join(", ", pks) +" "
				+ "from "+ schema + sep + table +" order by "+ column +";";
		System.err.println(sql);
		Statement s = conn.createStatement();
		s.execute(sql);
		ResultSet rs = s.getResultSet();
		while (rs.next()) {
			String name = rs.getString(1);
			JSONArray values = new JSONArray();
			for (String key : pks) {
				values.put(rs.getObject(key));
			}
			try {
				ret.put(name, values);
			} catch (JSONException e) {
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
			Connection conn) throws SQLException {
		if (dbType == DB_TYPE.SQLSERVER) return getSchemasMSSQL(conn);
		return getSchemasMySQL(conn);
	}

	private Map<String, Map<String, Map<String, String>>> getSchemasMySQL(
			Connection conn)
			throws SQLException {
		Map<String, Map<String, Map<String, String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();

		Statement s = conn.createStatement();
		s.execute("select table_schema, table_name, column_name, data_type "
				+ "from information_schema.columns order by table_schema, table_name, column_name;");
		ResultSet rs = s.getResultSet();
		while (rs.next()) {
			String schema = rs.getString("table_schema").toLowerCase();
			String table = rs.getString("table_name").toLowerCase();
			String column = rs.getString("column_name").toLowerCase();
			String type = rs.getString("data_type").toLowerCase();

			if (ignoredSchemas.contains(schema))
				continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;

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

	private Map<String, Map<String, Map<String, String>>> getSchemasMSSQL(Connection conn)
			throws SQLException {
		Map<String, Map<String, Map<String, String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();

		List<String> dbs = new ArrayList<String>();
		Statement s = conn.createStatement();
		s.execute("SELECT name FROM sys.databases order by name;");
		ResultSet rs = s.getResultSet();
		while (rs.next()) {
			String schema = rs.getString("name");
			if (ignoredSchemas.contains(schema))
				continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;
			dbs.add(schema);
		}
		rs.close();
		// s.close();

		for (String db : dbs) {
			if (ignoredSchemas.contains(db))
				continue;
			if (includeSchemas != null && !includeSchemas.contains(db))
				continue;

			System.err.println("extracting schema for: "+ db);

			try {
				s.execute("use \"" + db + "\";");
				s.execute("select table_schema, table_name, column_name, data_type, " +
						"character_maximum_length from information_schema.columns order by " +
						"table_schema, table_name, column_name;");
				ResultSet rs2 = s.getResultSet();
				while (rs2.next()) {
					String schema = db; // +"."+ rs2.getString("table_schema");
					Map<String, Map<String, String>> tables = schemas.get(schema);
					if (tables == null) {
						tables = new LinkedHashMap<String, Map<String, String>>();
						schemas.put(schema, tables);
					}

					String table = rs2.getString("table_name");
					if (table.startsWith("syncobj_")) continue;
					if (table.startsWith("MS") && table.length() > 2
							&& Character.isLowerCase(table.charAt(2))) continue;
					String column = rs2.getString("column_name");
					String type = rs2.getString("data_type");
					int maxLength = rs2.getInt("character_maximum_length");
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
		if (dbType == DB_TYPE.SQLSERVER) return getPrimaryKeysMSSQL(conn);
		if (dbType == DB_TYPE.HSQL) return getPrimaryKeysHSQL(conn);
		return getPrimaryKeysMySQL(conn);
	}

	private Map<String, Map<String, Set<String>>> getPrimaryKeysMSSQL(Connection conn) throws SQLException {
	    Map<String,Map<String,Map<String,String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();
		Map<String,Map<String,Set<String>>> primaryKeys =
			new LinkedHashMap<String, Map<String, Set<String>>>();

	    Statement s = conn.createStatement();
	    s.execute("SELECT name FROM sys.databases order by name;");
	    ResultSet rs2 = s.getResultSet();
	    while (rs2.next()) {
		String schema = rs2.getString("name");
		Map<String, Map<String, String>> tables = new LinkedHashMap<String, Map<String, String>>();
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
				"order by a.table_catalog, a.table_name, b.column_name, a.constraint_name;");
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

	private Map<String, Map<String, Set<String>>> getPrimaryKeysHSQL(Connection conn) throws SQLException {
	    Map<String,Map<String,Map<String,String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();
		Map<String,Map<String,Set<String>>> primaryKeys =
			new LinkedHashMap<String, Map<String, Set<String>>>();

	    Statement s = conn.createStatement();
		s.execute("select a.table_catalog, a.table_name, b.column_name, a.constraint_name " +
				"from information_schema.table_constraints a, " +
				"information_schema.constraint_column_usage b " +
				"where constraint_type = 'PRIMARY KEY' " +
				"and a.constraint_name = b.constraint_name " +
				"order by a.table_catalog, a.table_name, b.column_name, a.constraint_name;");
		ResultSet rs = s.getResultSet();
		while (rs.next()) {
			String schema = rs.getString("table_catalog").toLowerCase();
			String table = rs.getString("table_name").toLowerCase();
			String column = rs.getString("column_name").toLowerCase();

			if (ignoredSchemas.contains(schema)) continue;
			if (includeSchemas != null && !includeSchemas.contains(schema))
				continue;

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

	private Map<String,Map<String,Set<String>>> getPrimaryKeysMySQL(Connection conn) throws SQLException {
	    Map<String,Map<String,Map<String,String>>> schemas = new LinkedHashMap<String, Map<String, Map<String, String>>>();
		Map<String,Map<String,Set<String>>> primaryKeys =
			new LinkedHashMap<String, Map<String, Set<String>>>();

		Statement s = conn.createStatement();
		s.execute("select table_schema, table_name, column_name, column_key " +
				"from information_schema.columns " +
				"order by table_schema, table_name, column_name, column_key;");
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
		Connection conn) throws SQLException {
		if (dbType == DB_TYPE.SQLSERVER) return getForeignKeysMSSQL(conn);
		if (dbType == DB_TYPE.HSQL) return getForeignKeysHSQL(conn);
		return getForeignKeysMySQL(conn);
	}

	private Map<String, Map<String,Object>> getForeignKeysMSSQL(
		Connection conn) {
	    return null;
	}

	private Map<String, Map<String,Object>> getForeignKeysMySQL(
			Connection conn) throws SQLException {
	    Map<String, Map<String,Object>> foreignKeys =
			new LinkedHashMap<String, Map<String,Object>>();

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
				"order by constraint_name, table_schema, table_name, column_name;");
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
			    fk = new LinkedHashMap<String,Object>();
			    String[] reffing = {schema, table};
			    fk.put("reffing", reffing);
			    String[] reffed = {referenced_schema, referenced_table};
			    fk.put("reffed", reffed);
				foreignKeys.put(constraint_name,fk);
				fk.put("columns", new LinkedHashMap<String,String>());
			}

			@SuppressWarnings("unchecked")
			Map<String,String> columns = (Map<String,String>) fk.get("columns");
			columns.put(column, referenced_column);

		}

		return foreignKeys;
	}

	private Map<String, Map<String,Object>> getForeignKeysHSQL(
			Connection conn) throws SQLException {
	    Map<String, Map<String,Object>> foreignKeys =
			new LinkedHashMap<String, Map<String,Object>>();

		Statement s = conn.createStatement();
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
		ResultSet rs = s.getResultSet();

		while (rs.next()) {
			String constraint_name = rs.getString("fk_name").toLowerCase();
			String schema = rs.getString("pktable_schem").toLowerCase();
			String table = rs.getString("pktable_name").toLowerCase();
			String column = rs.getString("pkcolumn_name").toLowerCase();
			String referenced_schema = rs.getString("fktable_schem").toLowerCase();
			String referenced_table = rs.getString("fktable_name").toLowerCase();
			String referenced_column = rs.getString("fkcolumn_name").toLowerCase();

			Map<String, Object> fk = foreignKeys.get(constraint_name);
			if (fk==null) {
			    fk = new LinkedHashMap<String,Object>();
			    String[] reffing = {schema, table};
			    fk.put("reffing", reffing);
			    String[] reffed = {referenced_schema, referenced_table};
			    fk.put("reffed", reffed);
				foreignKeys.put(constraint_name,fk);
				fk.put("columns", new LinkedHashMap<String,String>());
			}

			@SuppressWarnings("unchecked")
			Map<String,String> columns = (Map<String,String>) fk.get("columns");
			columns.put(column, referenced_column);

		}

		return foreignKeys;
	}

}

