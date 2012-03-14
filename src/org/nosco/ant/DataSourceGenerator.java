package org.nosco.ant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.nosco.json.JSONException;
import org.nosco.json.JSONObject;
import org.nosco.util.Misc;

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

	private static List<String> getSchemaList(String metadataFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(metadataFile));
		StringBuffer sb = new StringBuffer();
		String s = null;
		while ((s=br.readLine())!=null) sb.append(s).append('\n');
		try {
			JSONObject metadata = new JSONObject(sb.toString());
			JSONObject schemas = metadata.getJSONObject("schemas");
			return new ArrayList<String>(schemas.keySet());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void go(String dir, String pkg, String dataSource, String metadataFile) throws IOException {
		String pkgDir = Misc.join("/", pkg.split("[.]"));
		new File(Misc.join("/", dir, pkgDir)).mkdirs();

		String name = getDataSourceName(dataSource);
		String cls = getClassName(dataSource);
		String method = getMethodName(dataSource);
		List<String> schemaList = getSchemaList(metadataFile);

		File file = new File(Misc.join("/", dir, pkgDir, name + ".java"));
		System.out.println("writing: "+ file.getAbsolutePath());

		BufferedWriter br = new BufferedWriter(new FileWriter(file));

		br.write("package "+ pkg +";\n");
		br.write("\n");
		br.write("import org.nosco.datasource.ReflectedDataSource;\n");
		br.write("\n");
		br.write("public class "+ name +" extends ReflectedDataSource {\n");
		br.write("\n");
		br.write("	public "+ name +"(String schema) {\n");
		br.write("		super(\""+ cls +"\", \""+ method +"\", schema);\n");
		br.write("	}\n");
		br.write("\n");
		for (String schema : schemaList) {
			br.write("	public static "+ name +" "+ schema.toUpperCase()
					+" = new "+ name +"(\""+ schema +"\");\n");
		}
		br.write("\n");
		br.write("}\n");

		br.close();

	}

}
