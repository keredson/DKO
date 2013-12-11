package org.kered.dko.ant;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Main {

	static Map<String,Class<?>> commands = new LinkedHashMap<String,Class<?>>();
	static {
		commands.put("extract-schema", SchemaExtractorBase.class);
		commands.put("generate-dkos", CodeGeneratorBase.class);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length==0) {
			printHelp();
			return;
		}
		String commandName = args[0];
		Class<?> command = commands.get(commandName);
		if (command==null) {
			printHelp();
			return;
		}
		String[] options = new String[args.length-1];
		System.arraycopy(args, 1, options, 0, options.length);
		runCommand(commandName, command, options);
	}

	private static void runCommand(String commandName, Class<?> command, String[] options) {
		try {
			Object o = command.newInstance();
			String optionName = null;
			for (String option : options) {
				if (option.equals("--help")) {
					printHelp(commandName, command);
					return;
				} else if (option.startsWith("--")) {
					optionName = option.substring(2);
				} else {
					call(command, o, optionName, option);
				}
			}
			command.getMethod("execute").invoke(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void call(Class<?> command, Object o, String optionName, String option) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Method m = findSetter(command, optionName);
		m.invoke(o, option);
	}

	private static Method findSetter(Class<?> command, String optionName) throws NoSuchMethodException, SecurityException {
		String setter = "set"+ Util.underscoreToCamelCase(optionName.replace('-', '_'), true);
		for (Method m : command.getDeclaredMethods()) {
			if (m.getName().equalsIgnoreCase(setter)) return m;
		}
		throw new RuntimeException("could not find method for: "+ optionName);
	}

	private static void printHelp(String commandName, Class<?> command) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		System.err.println("");
		String header = " Help for command: "+ commandName +" ";
		System.err.println(header);
		for (int i=0; i<header.length(); ++i) System.err.print("-");
		System.err.println("\n");
		for (Method method : command.getMethods()) {
			String name = method.getName();
			if (!name.startsWith("set")) continue;
			String optionName = "--"+ Util.splitCamelCase(name.substring(3)).trim().replace(' ', '-').toLowerCase();
			Object helpText = command.getDeclaredMethod("getHelp", String.class).invoke(null, name);
			if (helpText==null) continue;
			System.err.println(optionName +" "+ helpText);
			System.err.println("");
		}
	}

	private static void printHelp() {
		System.err.println("ERROR: Unrecognized command.");
		System.err.println("Syntax: java -jar lib/dko.jar <command> [command_option...]");
		System.err.println("");
		System.err.println("Where <command> is one of the following:");
		for (Entry<String, Class<?>> e : commands.entrySet()) {
			System.err.print("  ");
			System.err.print(e.getKey());
			System.err.print(":\t");
			try {
				Method help = e.getValue().getDeclaredMethod("getDescription");
				System.err.println(help.invoke(null));
			} catch (Exception e1) {
				System.err.println("(no description available)");
			}
		}
		System.err.println("");
		System.err.println("For help on an individual command, run the command with '--help'.");
		System.err.println("");
	}

}
