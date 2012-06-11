package org.nosco.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import org.nosco.Constants;
import org.nosco.Field;
import org.nosco.Field.FK;
import org.nosco.Field.PK;
import org.nosco.Table;
import org.nosco.json.JSONException;
import org.nosco.json.JSONObject;

public class Misc {

	public static String join(String s, Collection<?> c) {
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

	public static <T extends Object> String join(String s, T... c) {
		if(c==null || c.length==0) return "";
	    StringBuilder sb = new StringBuilder();
	    for (Object o : c) {
	    	sb.append(o==null ? "" : o.toString());
	    	sb.append(s);
	    }
	    return sb.delete(sb.length()-s.length(), sb.length()).toString();
	}

	public static JSONObject loadJSONObject(String fn) throws IOException, JSONException {
		BufferedReader br = new BufferedReader(new FileReader(fn));
		StringBuffer sb = new StringBuffer();
		String s = null;
		while ((s=br.readLine())!=null) sb.append(s).append('\n');
		JSONObject o = new JSONObject(sb.toString());
		return o;
	}

	public static void log(String sql, List<Object> bindings) {
		PrintStream log = null;
		String property = System.getProperty(Constants.PROP_LOG_SQL);
		if ("System.err".equalsIgnoreCase(property))
			log = System.err;
		if ("System.out".equalsIgnoreCase(property))
			log = System.out;
		if (log == null && Misc.truthy(property))
			log = System.err;
		if (log == null) return;
		log.println("==> "+ sql +"");
		if (bindings != null && bindings.size() > 0)
			log.println("^^^ ["+ join("|", bindings) +"]");
	}

	public static boolean truthy(String s) {
		if (s == null) return false;
		s = s.trim();
		if ("true".equalsIgnoreCase(s)) return true;
		if ("t".equalsIgnoreCase(s)) return true;
		if ("1".equals(s)) return true;
		return false;
	}

	public static boolean startsWith(FK<?>[] path, FK<?>[] path2) {
		if (path2 == null) return true;
		for (int i=0; i<path2.length; ++i) {
			if (path[i] != path2[i]) return false;
		}
		return true;
	}

	public static boolean deepEqual(Object[] path, Object[] path2) {
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

	@SuppressWarnings("unchecked")
	public static <T extends Table> Field.PK<T> getPK(T t) {
		try {
			return (PK<T>) t.getClass().getField("PK").get(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String readFileToString(File file) {
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
