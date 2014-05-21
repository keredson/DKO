package org.kered.dko.ant;

import java.util.Collection;

class Util {

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

	static String underscoreToCamelCase(String s, final boolean capitalizeFirstChar) {
		if (s==null) return null;
		if (s.length()==0) return s;
		s = s.toLowerCase();
		s = s.replace(' ', '_').replace("%", "_PERCENT");
		final char[] c = s.toCharArray();
		if (capitalizeFirstChar) {
			c[0] = Character.toUpperCase(c[0]);
		}
		for (int i=1; i<c.length; ++i) {
			if (c[i-1]=='_') {
				c[i] = Character.toUpperCase(c[i]);
			}
		}
		String ret = new String(c).replaceAll("_", "");
		if (c[0]=='_') return "_"+ret;
		else return ret;
	}

	static String underscoreToCamelCase(final Collection<String> strings, boolean capitalizeFirstChar) {
	    final StringBuffer sb = new StringBuffer();
	    for (final String s : strings) {
		sb.append(underscoreToCamelCase(s, capitalizeFirstChar));
		capitalizeFirstChar = true;
	    }
	    return sb.toString();
	}

	static String splitCamelCase(final String s) {
		return s.replaceAll(
				String.format("%s|%s|%s",
						"(?<=[A-Z])(?=[A-Z][a-z])",
						"(?<=[^A-Z])(?=[A-Z])",
						"(?<=[A-Za-z])(?=[^A-Za-z])"
						),
						" "
				);
	}

}
