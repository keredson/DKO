package org.kered.dko.ant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kered.dko.json.JSONException;
import org.kered.dko.json.JSONObject;

class DataSourceGenerator {

	static final String _DEFAULT_PACKAGE_DATASOURCE_LOADER = "_DEFAULT_PACKAGE_DATASOURCE_LOADER";

	public static String getDataSourceName(final String dataSource) {
		if (dataSource == null) return null;
		if (!dataSource.contains("=")) return "_DefaultDS";
		final String[] x = dataSource.split("=");
		String name = x[0].trim();
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}

	public static String getClassName(final String dataSource) {
		String part2 = dataSource.contains("=") ? dataSource.split("=")[1] : dataSource;
		final String cls = part2.substring(0, part2.lastIndexOf(".")).trim();
		return cls;
	}

	public static String getMethodName(final String dataSource) {
		String part2 = dataSource.contains("=") ? dataSource.split("=")[1] : dataSource;
		String method = part2.substring(part2.lastIndexOf(".")+1).trim();
		while (method.endsWith("(") || method.endsWith(")") || method.endsWith(" ") || method.endsWith(";")) {
			method = method.substring(0, method.length() - 1);
		}
		return method;
	}

	private static List<String> getSchemaList(final String metadataFile) throws IOException {
		final BufferedReader br = new BufferedReader(new FileReader(metadataFile));
		final StringBuffer sb = new StringBuffer();
		String s = null;
		while ((s=br.readLine())!=null) sb.append(s).append('\n');
		try {
			final JSONObject metadata = new JSONObject(sb.toString());
			final JSONObject schemas = metadata.getJSONObject("schemas");
			return new ArrayList<String>(schemas.keySet());
		} catch (final JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void go(final String dir, final String pkg, final String dataSource,
			final String metadataFile, final Map<String, String> schemaAliases) throws IOException {
		final String pkgDir = Util.join("/", pkg.split("[.]"));
		new File(Util.join("/", dir, pkgDir)).mkdirs();

		final String name = getDataSourceName(dataSource);
		final String cls = getClassName(dataSource);
		final String method = getMethodName(dataSource);
		final List<String> schemaList = getSchemaList(metadataFile);

		for (String schema : schemaList) {

			if (schemaAliases.containsKey(schema)) {
				schema = schemaAliases.get(schema);
			}

			final String pkgName = ClassGenerator.sanitizeJavaKeywords(schema);

			final File file = new File(Util.join("/", dir, pkgDir, name + ".java"));
			System.out.println("writing: "+ file.getAbsolutePath());

			final BufferedWriter br = new BufferedWriter(new FileWriter(file));

			br.write("package "+ pkg +";\n");
			br.write("\n");
			br.write("import org.kered.dko.Context;\n");
			br.write("import org.kered.dko.datasource.ReflectedDataSource;\n");
			br.write("\n");
			br.write("public class "+ name +" {\n");
			br.write("\n");
			br.write("\n");
			br.write("\tpublic static "+ "ReflectedDataSource" +" "+ "INSTANCE"
					+" = new "+ "ReflectedDataSource" +"(\""+ cls +"\", \""+ method +"\");\n");
			br.write("\n");
			br.write("}\n");

			br.close();

		}

	}

}
