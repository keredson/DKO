package org.nosco.ant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

import org.nosco.Field;
import org.nosco.Table;
import org.nosco.Field.FK;

public class JoinGenerator {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		genJoinsFile(new File("Join.java"), 8);
	}

	private static void genJoinsFile(final File file, final int n) throws IOException {
		final BufferedWriter w = new BufferedWriter(new FileWriter(file));
		genJoins(w, n);
		w.close();
	}

	private static String[] ops = {"insert", "update", "delete", "save", "exists"};

	private static void genJoins(final Writer w, final int n) throws IOException {
		w.write("package org.nosco;\n\n");
		w.write("import java.sql.SQLException;\n");
		w.write("import java.util.ArrayList;\n");
		w.write("import java.util.Collections;\n");
		w.write("import java.util.List;\n");
		w.write("import javax.sql.DataSource;\n");
		w.write("import org.nosco.Field.FK;\n\n");
		w.write("public class Join {\n");

		for (int i=2; i<=n; ++i) {
			w.write("\n");
			w.write("\tpublic static class J"+ i +" <");
			for (int j=1; j<=i; ++j) {
				w.write("T"+ j +" extends Table");
				if (j < i) w.write(", ");
			}
			w.write("> extends Table {\n");

			w.write("\t\tprivate List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;\n");
			for (int j=1; j<=i; ++j) {
				w.write("\t\tpublic final T"+ j +" t"+ j +";\n");
			}

			w.write("\t\tpublic J"+ i +"(");
			for (int j=1; j<=i; ++j) {
				w.write("final T"+ j +" t"+ j +"");
				if (j < i) w.write(", ");
			}
			w.write(") {\n");
			for (int j=1; j<=i; ++j) {
				w.write("\t\t\tthis.t"+ j +" = t"+ j +";\n");
			}
			w.write("\t\t}\n");

			w.write("\t\t@SuppressWarnings(\"unchecked\")\n");
			w.write("\t\tJ"+ i +"(final Object[] oa, final int offset) {\n");
			for (int j=1; j<=i; ++j) {
				w.write("\t\t\tt"+ j +" = (T"+ j +") oa[offset+"+ (j-1) +"];\n");
			}
			w.write("\t\t}\n");

			w.write("\t\t@Override\n");
			w.write("\t\tprotected String SCHEMA_NAME() {\n");
			w.write("\t\t\treturn ");
			for (int j=1; j<=i; ++j) {
				w.write("t"+ j +".SCHEMA_NAME()");
				if (j < i) w.write("+\" + \"+");
			}
			w.write(";\n");
			w.write("\t\t}\n");

			w.write("\t\t@Override\n");
			w.write("\t\tprotected String TABLE_NAME() {\n");
			w.write("\t\t\treturn ");
			for (int j=1; j<=i; ++j) {
				w.write("t"+ j +".TABLE_NAME()");
				if (j < i) w.write("+\" + \"+");
			}
			w.write(";\n");
			w.write("\t\t}\n");

			w.write("\t\t@Override\n");
			w.write("\t\tpublic List<Field<?>> FIELDS() {\n");
			w.write("\t\t\tif (__NOSCO_PRIVATE_FIELDS == null) {\n");
			w.write("\t\t\t\t__NOSCO_PRIVATE_FIELDS = new ArrayList<Field<?>>();\n");
			for (int j=1; j<=i; ++j) {
				w.write("\t\t\t\t__NOSCO_PRIVATE_FIELDS.addAll(t"+ j +".FIELDS());\n");
			}
			w.write("\t\t\t\t__NOSCO_PRIVATE_FIELDS = Collections.unmodifiableList(__NOSCO_PRIVATE_FIELDS);\n");
			w.write("\t\t\t}\n");
			w.write("\t\t\treturn __NOSCO_PRIVATE_FIELDS;\n");
			w.write("\t\t}\n");

			w.write("\t\t@SuppressWarnings(\"rawtypes\")\n");
			w.write("\t\t@Override\n");
			w.write("\t\tprotected FK[] FKS() {\n");
			w.write("\t\t\tfinal FK[] ret = {};\n");
			w.write("\t\t\treturn ret;\n");
			w.write("\t\t}\n");

			w.write("\t\t@Override\n");
			w.write("\t\tpublic <S> S get(final Field<S> field) {\n");
			for (int j=1; j<=i; ++j) {
				w.write("\t\t\ttry { return t"+ j +".get(field); }\n");
				w.write("\t\t\tcatch (final IllegalArgumentException e) { /* ignore */ }\n");
			}
			w.write("\t\t\tthrow new IllegalArgumentException(\"unknown field \"+ field);\n");
			w.write("\t\t}\n");

			w.write("\t\t@Override\n");
			w.write("\t\tpublic <S> void set(final Field<S> field, final S value) {\n");
			for (int j=1; j<=i; ++j) {
				w.write("\t\t\ttry { t"+ j +".set(field, value); return; }\n");
				w.write("\t\t\tcatch (final IllegalArgumentException e) { /* ignore */ }\n");
			}
			w.write("\t\t\tthrow new IllegalArgumentException(\"unknown field \"+ field);\n");
			w.write("\t\t}\n");

			for (final String op : ops) {
				final String cmp = "exists".equals(op) ? " || " : " && ";
				w.write("\t\t@Override\n");
				w.write("\t\tpublic boolean "+ op +"() throws SQLException {\n");
				w.write("\t\t\treturn ");
				for (int j=1; j<=i; ++j) {
					w.write("t"+ j +"."+ op +"()");
					if (j < i) w.write(cmp);
				}
				w.write(";\n");
				w.write("\t\t}\n");
				w.write("\t\t@Override\n");
				w.write("\t\tpublic boolean "+ op +"(DataSource ds) throws SQLException {\n");
				w.write("\t\t\treturn ");
				for (int j=1; j<=i; ++j) {
					w.write("t"+ j +"."+ op +"(ds)");
					if (j < i) w.write(cmp);
				}
				w.write(";\n");
				w.write("\t\t}\n");
			}

			w.write("\t\t@Override\n");
			w.write("\t\tprotected Object __NOSCO_PRIVATE_mapType(final Object o) {\n");
			w.write("\t\t\treturn t1.__NOSCO_PRIVATE_mapType(o);\n");
			w.write("\t\t}\n");

			w.write("\t\t@Override\n");
			w.write("\t\tpublic String toString() {\n");
			w.write("\t\t\treturn ");
			for (int j=1; j<=i; ++j) {
				w.write("t"+ j);
				if (j < i) w.write(" +\"+\"+ ");
			}
			w.write(";\n");
			w.write("\t\t}\n");

			w.write("\t\t@Override\n");
			w.write("\t\tpublic String toStringDetailed() {\n");
			w.write("\t\t\treturn ");
			for (int j=1; j<=i; ++j) {
				w.write("(t"+ j +"==null ? t"+ j +" : t"+ j +".toStringDetailed())");
				if (j < i) w.write(" +\"+\"+ ");
			}
			w.write(";\n");
			w.write("\t\t}\n");

			w.write("\t}\n");

		}

		w.write("\n}\n");
	}

}
