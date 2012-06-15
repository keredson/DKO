package org.nosco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.nosco.Field.PK;
import org.nosco.json.JSONException;
import org.nosco.json.JSONObject;

class Util {

	static String derefField(Field<?> field, SqlContext context) {
		if (field.isBound()) return field.toString();
		List<String> selectedTables = new ArrayList<String>();
		List<TableInfo> unboundTables = new ArrayList<TableInfo>();
		SqlContext tmp = context;
		while (tmp != null) {
			for (TableInfo info : tmp.tableInfos) {
				selectedTables.add(info.table.SCHEMA_NAME() +"."+ info.table.TABLE_NAME());
				if (info.nameAutogenned && field.TABLE.isInstance(info.table)) {
					unboundTables.add(info);
				}
			}
			tmp = tmp.parentContext;
		}
		if (unboundTables.size() < 1) {
			throw new RuntimeException("field "+ field +
					" is not from one of the selected tables {"+
					join(",", selectedTables) +"}");
		} else if (unboundTables.size() > 1) {
			List<String> x = new ArrayList<String>();
			for (TableInfo info : unboundTables) {
				x.add(info.table.SCHEMA_NAME() +"."+ info.table.TABLE_NAME());
			}
			throw new RuntimeException("field "+ field +
					" is ambigious over the tables {"+ join(",", x) +"}");
		} else {
			TableInfo theOne = unboundTables.iterator().next();
			return (theOne.tableName == null ? theOne.table.TABLE_NAME() : theOne.tableName)
					+ "."+ field;
		}
	}

	/**
	 * @param rs
	 * @param type
	 * @param i
	 * @return
	 * @throws SQLException
	 */
	static Object fixObjectType(ResultSet rs, Class<?> type, int i) throws SQLException {
		if (type == Long.class) return rs.getLong(i);
		if (type == Double.class) {
			double v = rs.getDouble(i);
			return rs.wasNull() ? null : v;
		}
		if (type == Character.class) {
			String s = rs.getString(i);
			if (s != null && s.length() > 0) return s.charAt(0);
			else return null;
		}
		Object o = rs.getObject(i);
		if (o instanceof Short) o = ((Short)o).intValue();
		return o;
	}

	/**
	 * Please do not use.
	 * @return
	 */
	static boolean sameTable(Table t1, Table t2) {
		if (t1 == null && t2 == null) return true;
		if (t1 == null || t2 == null) return false;
		return t1.SCHEMA_NAME() == t2.SCHEMA_NAME() && t1.TABLE_NAME() == t2.TABLE_NAME();
	}

	@SuppressWarnings("unchecked")
	static <T extends Table> Field.PK<T> getPK(T t) {
		try {
			return (PK<T>) t.getClass().getField("PK").get(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static boolean deepEqual(Object[] path, Object[] path2) {
		if (path == null && path2 == null) return true;
		if (path == path2) return true;
		if ((path != null && path2 == null)) return false;
		if ((path == null && path2 != null)) return false;
		if (path.length != path2.length) return false;
		for (int i=0; i<path2.length; ++i) {
			if (path[i]==null ? path2[i]!=null : path[i] != path2[i]
					&& !path[i].equals(path2[i])) return false;
		}
		return true;
	}

	static String join(String s, Collection<?> c) {
	    StringBuilder sb = new StringBuilder();
	    for (Object o : c) {
	    	sb.append(o);
	    	sb.append(s);
	    }
	    if (c != null && c.size() > 0) {
	    	sb.delete(sb.length()-s.length(), sb.length());
	    }
	    return sb.toString();
	}

	static <T extends Object> String join(String s, T... c) {
		if(c==null || c.length==0) return "";
	    StringBuilder sb = new StringBuilder();
	    for (Object o : c) {
	    	sb.append(o==null ? "" : o.toString());
	    	sb.append(s);
	    }
	    return sb.delete(sb.length()-s.length(), sb.length()).toString();
	}

	static JSONObject loadJSONObject(String fn) throws IOException, JSONException {
		BufferedReader br = new BufferedReader(new FileReader(fn));
		StringBuffer sb = new StringBuffer();
		String s = null;
		while ((s=br.readLine())!=null) sb.append(s).append('\n');
		JSONObject o = new JSONObject(sb.toString());
		return o;
	}

	static void log(String sql, List<Object> bindings) {
		PrintStream log = null; //System.out;
		String property = System.getProperty(Constants.PROP_LOG_SQL);
		String property2 = System.getProperty(Constants.PROP_LOG);
		if ("System.err".equalsIgnoreCase(property))
			log = System.err;
		if ("System.out".equalsIgnoreCase(property))
			log = System.out;
		if (log == null && truthy(property))
			log = System.err;
		if ("System.err".equalsIgnoreCase(property2))
			log = System.err;
		if ("System.out".equalsIgnoreCase(property2))
			log = System.out;
		if (log == null && truthy(property2))
			log = System.err;
		if (log == null) return;
		log.println("==> "+ sql +"");
		if (bindings != null && bindings.size() > 0)
			log.println("^^^ ["+ join("|", bindings) +"]");
	}

	static boolean truthy(String s) {
		if (s == null) return false;
		s = s.trim();
		if ("true".equalsIgnoreCase(s)) return true;
		if ("t".equalsIgnoreCase(s)) return true;
		if ("1".equals(s)) return true;
		return false;
	}

	static String readFileToString(File file) {
		try {
			FileReader reader = new FileReader(file);
			StringBuffer sb = new StringBuffer();
			int chars;
			char[] buf = new char[1024];
			while ((chars = reader.read(buf)) > 0) {
				sb.append(buf, 0, chars);
			}
			reader.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


}
