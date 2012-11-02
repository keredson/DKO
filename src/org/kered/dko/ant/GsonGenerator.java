package org.kered.dko.ant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class GsonGenerator {

	public static void go(final String dir, final String pkg, final String pkgName, final Map<String, String> tableToClassNames) throws IOException {
		final String pkgDir = Util.join("/", pkg.split("[.]"));
		File f = new File(dir, pkgDir);
		f = new File(f, pkgName);
		f = new File(f, "_Gson.java");
		System.out.println("writing: "+ f.getAbsolutePath());

		final BufferedWriter br = new BufferedWriter(new FileWriter(f));

		br.write("package "+ pkg +"."+ pkgName +";\n");
		br.write("\n");
		br.write("import java.io.*;\n");
		br.write("import java.lang.reflect.*;\n");
		br.write("import org.kered.dko.*;\n");
		br.write("import com.google.gson.*;\n");
		br.write("import com.google.gson.reflect.*;\n");
		br.write("import com.google.gson.stream.*;\n");
		br.write("\n");
		br.write("public class _Gson {\n");
		br.write("\n");
		br.write("\tfinal static TypeAdapter<Class> _class = new TypeAdapter<Class>() {\n");
		br.write("\t\t@Override\n");
		br.write("\t\tpublic Class read(final JsonReader r) throws IOException {\n");
		br.write("\t\t\ttry {\n");
		br.write("\t\t\t\treturn this.getClass().getClassLoader().loadClass(r.nextString());\n");
		br.write("\t\t\t} catch (final ClassNotFoundException e) {\n");
		br.write("\t\t\t\tthrow new RuntimeException(e);\n");
		br.write("\t\t\t}\n");
		br.write("\t\t}\n");
		br.write("\t\t@Override\n");
		br.write("\t\tpublic void write(final JsonWriter w, final Class cls) throws IOException {\n");
		br.write("\t\t\tw.value(cls.getName());\n");
		br.write("\t\t}\n");
		br.write("\t};\n");
		br.write("\n");
		br.write("\t\tfinal static InstanceCreator<Query<?>> _query = new InstanceCreator<Query<?>>() {\n");
		br.write("\t\t\t@SuppressWarnings({ \"unchecked\", \"rawtypes\" })\n");
		br.write("\t\t\t@Override\n");
		br.write("\t\t\tpublic Query<?> createInstance(final Type type) {\n");
		br.write("\t\t\t\tfinal Type[] typeParameters = ((ParameterizedType)type).getActualTypeArguments();\n");
		br.write("\t\t\t\tfinal Type idType = typeParameters[0];\n");
		br.write("\t\t\t\treturn QueryFactory.IT.getQuery((Class)idType);\n");
		br.write("\t\t\t}\n");
		br.write("\t\t};\n");
		br.write("\n");

		br.write("\tpublic static GsonBuilder registerAllDKOs(GsonBuilder builder) {\n");
		br.write("\t\tbuilder = builder.registerTypeHierarchyAdapter(Class.class, _class);\n");
		for (final Entry<String, String> e : tableToClassNames.entrySet()) {
			br.write("\t\tbuilder = builder.registerTypeAdapter(new TypeToken<Query<"+ e.getValue() +">>(){}.getType(), _query);\n");
		}

		br.write("\t\treturn builder;\n");
		br.write("\t}\n");
		br.write("\n");
		br.write("}\n");

		br.close();
	}

}
