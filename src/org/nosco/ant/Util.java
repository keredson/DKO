package org.nosco.ant;

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

}
