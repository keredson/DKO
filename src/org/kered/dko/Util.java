package org.kered.dko;

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
import java.util.logging.Logger;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Field.PK;
import org.kered.dko.json.JSONException;
import org.kered.dko.json.JSONObject;

class Util {

	static String derefField(final Field<?> field, final SqlContext context) {
		if (context == null) return field.getSQL(context);
		if (field.isBound()) return field.getSQL(context);
		final List<String> selectedTables = new ArrayList<String>();
		final List<TableInfo> unboundTables = new ArrayList<TableInfo>();
		SqlContext tmp = context;
		while (tmp != null) {
			for (final TableInfo info : tmp.tableInfos) {
				selectedTables.add(getSCHEMA_NAME(info.tableClass) +"."+ getTABLE_NAME(info.tableClass));
				if (info.nameAutogenned && sameTable(field.TABLE, info.tableClass)) {
					unboundTables.add(info);
				}
			}
			tmp = tmp.parentContext;
		}
		if (unboundTables.size() < 1) {
			throw new RuntimeException("field "+ field.TABLE.getSimpleName() +"."+ field +
					" is not from one of the selected tables {"+
					join(",", selectedTables) +"}");
		} else if (unboundTables.size() > 1) {
			final List<String> x = new ArrayList<String>();
			for (final TableInfo info : unboundTables) {
				x.add(Util.getSCHEMA_NAME(info.tableClass) +"."+ getTABLE_NAME(info.tableClass));
			}
			throw new RuntimeException("field "+ field +
					" is ambigious over the tables {"+ join(",", x) +"}");
		} else {
			final TableInfo theOne = unboundTables.iterator().next();
			return (theOne.tableName == null ? getTABLE_NAME(theOne.tableClass) : theOne.tableName)
					+ "."+ field.getSQL(context);
		}
	}

	@SuppressWarnings("unchecked")
	static <S> S getTypedValueFromRS(final ResultSet rs, final int i, final Field<S> field) throws SQLException {
		final Class<S> type = field.TYPE;
		if (type == Byte.class) {
			final Byte v = Byte.valueOf(rs.getByte(i));
			return (S) (rs.wasNull() ? null : v);
		}
		if (type == Double.class) {
			final Double v = Double.valueOf(rs.getDouble(i));
			return (S) (rs.wasNull() ? null : v);
		}
		if (type == Float.class) {
			final Float v = Float.valueOf(rs.getFloat(i));
			return (S) (rs.wasNull() ? null : v);
		}
		if (type == Integer.class) {
			final Integer v = Integer.valueOf(rs.getInt(i));
			return (S) (rs.wasNull() ? null : v);
		}
		if (type == Long.class) {
			final Long v = Long.valueOf(rs.getLong(i));
			return (S) (rs.wasNull() ? null : v);
		}
		if (type == Short.class) {
			final Short v = Short.valueOf(rs.getShort(i));
			return (S) (rs.wasNull() ? null : v);
		}
		if (type == Character.class) {
			final String s = rs.getString(i);
			if (s != null && s.length() > 0) return (S) Character.valueOf(s.charAt(0));
			else return null;
		}
		Object o = rs.getObject(i);
		if (o instanceof Short) o = ((Short)o).intValue();
		return (S) o;
	}

	/**
	 * Please do not use.
	 * @return
	 */
	static boolean sameTable(final Class<? extends Table> t1, final Class<? extends Table> t2) {
		if (t1 == null && t2 == null) return true;
		if (t1 == null || t2 == null) return false;
		if (t1 == t2) return true;
		return getSCHEMA_NAME(t1).equals(getSCHEMA_NAME(t2)) && getTABLE_NAME(t1) == getTABLE_NAME(t2);
	}

	@SuppressWarnings("unchecked")
	static <T extends Table> Field.PK<T> getPK(final T t) {
		try {
			return (PK<T>) t.getClass().getField("PK").get(null);
		} catch (final Exception e) {
			//e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	static <T extends Table> Field.PK<T> getPK(final Class<T> t) {
		try {
			return (PK<T>) t.getField("PK").get(null);
		} catch (final Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	static <T extends Table> List<Field<?>> getFIELDS(final Class<T> t) {
		try {
			return (List<Field<?>>) t.getField("_FIELDS").get(null);
		} catch (final Exception e) {
			log.warning(e.toString() +" --- DKO class "+ t.getSimpleName()
					+" was generated prior to DKO v2.2.0.  falling back to FIELDS()...");
			try {
				return (List<Field<?>>) t.getMethod("FIELDS").invoke(t.newInstance());
			} catch (final Exception e1) {
				throw new RuntimeException(e1);
				//e1.printStackTrace();
			}
		}
	}

	static boolean deepEqual(final Object[] path, final Object[] path2) {
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

	static String join(final String s, final Collection<?> c) {
	    final StringBuilder sb = new StringBuilder();
	    for (final Object o : c) {
	    	sb.append(o);
	    	sb.append(s);
	    }
	    if (c != null && c.size() > 0) {
	    	sb.delete(sb.length()-s.length(), sb.length());
	    }
	    return sb.toString();
	}

	static <T extends Object> String join(final String s, final T... c) {
		if(c==null || c.length==0) return "";
	    final StringBuilder sb = new StringBuilder();
	    for (final Object o : c) {
	    	sb.append(o==null ? "" : o.toString());
	    	sb.append(s);
	    }
	    return sb.delete(sb.length()-s.length(), sb.length()).toString();
	}

	static JSONObject loadJSONObject(final String fn) throws IOException, JSONException {
		final BufferedReader br = new BufferedReader(new FileReader(fn));
		final StringBuffer sb = new StringBuffer();
		String s = null;
		while ((s=br.readLine())!=null) sb.append(s).append('\n');
		final JSONObject o = new JSONObject(sb.toString());
		return o;
	}

	private static final Logger logSql = Logger.getLogger("org.kered.dko.sql");
	private static final Logger log = Logger.getLogger("org.kered.dko.Util");

	static void log(final String sql, final List<Object> bindings) {
		final String msg = sql + (bindings != null && bindings.size() > 0 ? " -- ["+ join("|", bindings) +"]" : "");
		logSql.fine(msg);

		// legacy prop values
		PrintStream log = null; // System.err || null;
		final String property = System.getProperty(Constants.PROP_LOG_SQL);
		final String property2 = System.getProperty(Constants.PROP_LOG);
		if (log == null && property != null) {
			if ("System.err".equalsIgnoreCase(property)) log = System.err;
			if ("System.out".equalsIgnoreCase(property)) log = System.out;
			if (log == null && truthy(property)) log = System.err;
		}
		if (log == null && property2 != null) {
			if ("System.err".equalsIgnoreCase(property2)) log = System.err;
			if ("System.out".equalsIgnoreCase(property2)) log = System.out;
			if (truthy(property2)) log = System.err;
		}
		if (log != null) log.println("==> "+ msg);
	}

	static boolean truthy(String s) {
		if (s == null) return false;
		s = s.trim().toLowerCase();
		if ("true".equals(s)) return true;
		if ("false".equals(s)) return false;
		if ("t".equals(s)) return true;
		if ("f".equals(s)) return false;
		if ("yes".equals(s)) return true;
		if ("no".equals(s)) return false;
		if ("y".equals(s)) return true;
		if ("n".equals(s)) return false;
		try { return Integer.valueOf(s) != 0; }
		catch (final NumberFormatException e) { /* ignore */ }
		throw new RuntimeException("I don't know the truthiness of '"+ s
				+"'.  Accepted values are: true/false/t/f/yes/no/y/n/[0-9]+");
	}

	static String readFileToString(final File file) {
		try {
			final FileReader reader = new FileReader(file);
			final StringBuffer sb = new StringBuffer();
			int chars;
			final char[] buf = new char[1024];
			while ((chars = reader.read(buf)) > 0) {
				sb.append(buf, 0, chars);
			}
			reader.close();
			return sb.toString();
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	static boolean notAllNull(final Object[] fieldValues, final int start, final int end) {
		if (start == end) return true;
		for (int i=start; i<end; ++i) {
			if (fieldValues[i] != null) return true;
		}
		return false;
	}

	static boolean allNull(final Object[] fieldValues, final int start, final int end) {
		return !notAllNull(fieldValues, start, end);
	}

	static boolean allTheSame(final Object[] values1, final Object[] values2, final int start, final int end) {
		if (values1==null && values2!=null || values1!=null && values2==null) {
			return false;
		}
		for (int i=start; i<end; ++i) {
			final boolean sameObject = values1[i]==null ? values2[i]==null : values1[i].equals(values2[i]);
			if (!sameObject) return false;
		}
		return true;
	}

	static String joinFields(final SqlContext context, final String s, final Field[] c) {
		return joinFields(context.dbType, s, c);
	}

	static String joinFields(final DB_TYPE dbType, final String s, final Field[] c) {
		if(c==null || c.length==0) return "";
	    final StringBuilder sb = new StringBuilder();
	    for (final Field<?> o : c) {
	    	sb.append(o==null ? "" : o.getSQL(dbType));
	    	sb.append(s);
	    }
	    return sb.delete(sb.length()-s.length(), sb.length()).toString();
	}

	public static String joinFields(final DB_TYPE dbType, final String s, final List<Field<?>> c) {
		if(c==null || c.size()==0) return "";
	    final StringBuilder sb = new StringBuilder();
	    for (final Field<?> o : c) {
	    	sb.append(o==null ? "" : o.getSQL(dbType));
	    	sb.append(s);
	    }
	    return sb.delete(sb.length()-s.length(), sb.length()).toString();
	}

	public static String joinFields(final SqlContext context, final String s, final List<Field<?>> c) {
		return joinFields(context.dbType, s, c);
	}

	static String getSCHEMA_NAME(final Class<? extends Table> t) {
		try {
			return (String) t.getField("_SCHEMA_NAME").get(null);
		} catch (final Exception e) {
			log.warning(e.toString() +" --- DKO class "+ t.getSimpleName()
					+" was generated prior to DKO v2.2.0.  falling back to SCHEMA_NAME()...");
			try {
				return (String) t.getMethod("SCHEMA_NAME").invoke(t.newInstance());
			} catch (final Exception e1) {
				throw new RuntimeException(e1);
				//e1.printStackTrace();
			}
		}
	}

	static String getTABLE_NAME(final Class<? extends Table> t) {
		try {
			return (String) t.getField("_TABLE_NAME").get(null);
		} catch (final Exception e) {
			log.warning(e.toString() +" --- DKO class "+ t.getSimpleName()
					+" was generated prior to DKO v2.2.0.  falling back to TABLE_NAME()...");
			try {
				return (String) t.getMethod("TABLE_NAME").invoke(t.newInstance());
			} catch (final Exception e1) {
				throw new RuntimeException(e1);
			}
		}
	}


}
