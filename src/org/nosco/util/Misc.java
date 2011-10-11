package org.nosco.util;

import java.util.Collection;

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

}
