package org.nosco.ant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.nosco.util.Misc;

import db.Local;

class DataSourceGenerator {

	public static String getDataSourceName(String dataSource) {
		String[] x = dataSource.split("=");
		String name = x[0].trim();
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}
	
	public static String getClassName(String dataSource) {
		String[] x = dataSource.split("=");
		String cls = x[1].substring(0, x[1].lastIndexOf(".")).trim();
		return cls;
	}
	
	public static String getMethodName(String dataSource) {
		String[] x = dataSource.split("=");
		String method = x[1].substring(x[1].lastIndexOf(".")+1).trim();
		while (method.endsWith("(") || method.endsWith(")") || method.endsWith(" ") || method.endsWith(";")) {
			method = method.substring(0, method.length() - 1);
		}
		return method;
	}
	
	public static void go(String dir, String pkg, String dataSource) throws IOException {
		String pkgDir = Misc.join("/", pkg.split("[.]"));
		new File(Misc.join("/", dir, pkgDir)).mkdirs();
		
		String name = getDataSourceName(dataSource);
		String cls = getClassName(dataSource);
		String method = getMethodName(dataSource);
		
		File file = new File(Misc.join("/", dir, pkgDir, name + ".java"));
		System.out.println("writing: "+ file.getAbsolutePath());
		
		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		
		br.write("package db;\n");
		br.write("\n");
		br.write("import org.nosco.ReflectedDataSource;\n");
		br.write("\n");
		br.write("public class Local extends ReflectedDataSource {\n");
		br.write("\n");
		br.write("	public Local() {\n");
		br.write("		super(\""+ cls +"\", \""+ method +"\");\n");
		br.write("	}\n");
		br.write("\n");
		br.write("	public static Local IT = new Local();\n");
		br.write("\n");
		br.write("}\n");

		br.close();

	}

}
