package org.nosco.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import org.nosco.Constants;
import org.nosco.json.JSONException;
import org.nosco.json.JSONObject;

public class Misc {

	public static String join(String s, Collection<?> c) {
	    StringBuilder sb = new StringBuilder();
	    for (Object o : c) {
	    	sb.append(o);
	    	sb.append(s);
	    }
	    return sb.delete(sb.length()-s.length(), sb.length()).toString();
	}

	public static String join(String s, Object... c) {
		if(c==null || c.length==0) return "";
	    StringBuilder sb = new StringBuilder();
	    for (Object o : c) {
	    	sb.append(o);
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

}
