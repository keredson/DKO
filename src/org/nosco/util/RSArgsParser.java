package org.nosco.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RSArgsParser {
	
	private Map<String, String> aliases;
	private HashMap<String, String> defaults;
	private List<String> extras = new ArrayList<String>();
	private HashMap<String, Boolean> options;

	public RSArgsParser(HashMap<String, Boolean> options, Map<String,String> aliases, 
			Map<String,String> defaults) {
		this.options = new HashMap<String,Boolean>(options);
		this.aliases = new HashMap<String,String>(aliases);
		this.defaults = new HashMap<String,String>(defaults);
	}
	
	public RSArgsParser(String options, Map<String,String> aliases, 
			Map<String,String> defaults) {
		this.options = new HashMap<String,Boolean>();
		this.aliases = new HashMap<String,String>(aliases);
		this.defaults = new HashMap<String,String>(defaults);
		String prev = null;
		for (int i=0; i<options.length(); ++i) {
			String opt = options.substring(i, i+1);
			if (":".equals(opt)) {
				if (prev!=null) {
					this.options.put(prev, true);
				}
			} else {
				this.options.put(opt, false);
				prev = opt;
			}
		}
	}
	
	public RSArgsParser(HashMap<String, Boolean> options, HashMap<String, String> aliases) {
		this(options, aliases, null);
	}

	public RSArgsParser(String options, HashMap<String, String> aliases) {
		this(options, aliases, null);
	}

	public RSArgsParser(HashMap<String, Boolean> options) {
		this(options, null, null);
	}

	public RSArgsParser(String options) {
		this(options, null, null);
	}

	public Map<String,String> parse(String[] args) {
		HashMap<String, String> values = new HashMap<String,String>(defaults);
		extras = new ArrayList<String>();
		String key = null;
		for (String arg : args) {
			if (arg.startsWith("-")) {
				arg = arg.substring(1);
				if (arg.startsWith("-")) arg = arg.substring(1);
				if (aliases.containsKey(arg)) arg = aliases.get(arg);
				values.put(arg, null);
				if (this.options.get(arg)) key = arg;
				else key = null;
			} else {
				if (key!=null) {
					values.put(key, arg);
				} else {
					extras.add(arg);
				}
			}
		}
		return values;
	}

	public List<String> getExtras() {
		return extras;
	}

}
