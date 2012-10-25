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

}
