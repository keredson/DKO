package org.kered.dko.ant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

class JoinGenerator {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException {
		int n = 9;
		if (args.length == 1) {
			n = Integer.valueOf(args[0]);
		}

		final String r = ""; //genRandomString();

		final File file = new File("gen/joinsrc/org/kered/dko/Join.java");
		System.err.println("writing (to J"+n+"): "+ file.getPath());
		genJoinsFile(file, n, r);
		final File file2 = new File("gen/joinsrc/org/kered/dko/_Join.java");
		gen_JoinsFile(file2, n, r);
	}

	private static String genRandomString() {
		final StringBuffer sb = new StringBuffer();
		for (int i=0; i<3; ++i) {
			sb.append((char)('A' + ((char)(Math.random()*26))));
		}
		return sb.toString();
	}

	private static void genJoinsFile(final File file, final int n, final String r) throws IOException {
		final File dir = file.getParentFile();
		if(!dir.exists()) dir.mkdirs();
		final BufferedWriter w = new BufferedWriter(new FileWriter(file));
		genJoins(w, n, r);
		w.close();
	}

	private static void gen_JoinsFile(final File file, final int n, final String r) throws IOException {
		final File dir = file.getParentFile();
		if(!dir.exists()) dir.mkdirs();
		final BufferedWriter w = new BufferedWriter(new FileWriter(file));
		gen_Joins(w, n, r);
		w.close();
	}

	private static String[] ops = {"insert", "update", "delete", "save", "exists"};

	private static void genJoins(final Writer w, final int n, final String r) throws IOException {
		w.write("package org.kered.dko;\n\n");
		w.write("import java.sql.SQLException;\n");
		w.write("import java.util.ArrayList;\n");
		w.write("import java.util.Collections;\n");
		w.write("import java.util.List;\n");
		w.write("import javax.sql.DataSource;\n");
		w.write("import org.kered.dko.Field.FK;\n");
		w.write("import org.kered.dko.Table.__Alias;\n");
		w.write("import org.kered.dko._Join.*;\n");
		w.write("\n");

		w.write("public class Join {\n");
		w.write("\n");

		for (int i=2; i<=n; ++i) {
			w.write("\n");

			final String tExtendsTable =genTExtendsTable(i);
			final String tTypes =genTTypes(i);


			final String[] joinTypes = {"left", "right", "inner", "outer", "cross"};
			final String[] inputTypes = {"Class", "__Alias"};
			for (final String joinType : joinTypes) {
				for (final String inputType : inputTypes) {
					if (i == 2) {
						for (final String inputType2 : inputTypes) {
							writeJoinJavadoc(w, i, tTypes);
							w.write("\tpublic static <"+ tExtendsTable +"> Query<J"+ i +"<"+ tTypes);
							w.write(">> "+ joinType + i +"(final "+ inputType +"<T1> t1, "+ inputType2 +"<T2> t2");
							if (!"cross".equals(joinType)) w.write(", Condition on");
							w.write(") {\n");
							w.write("\t\treturn new _"+ r +"_Query2<T1, T2>(new DBQuery<T1>(t1), t2, \""+ joinType +" join\", "+ ("cross".equals(joinType) ? "null" : "on") +");\n");
							w.write("\t}\n");
						}
						writeJoinJavadoc(w, i, tTypes);
						w.write("\tpublic static <"+ tExtendsTable +"> Query<J"+ i +"<"+ tTypes);
						w.write(">> "+ joinType + i +"(final Query<T"+ (i-1) +"> q, "+ inputType +"<T"+ i +"> t");
						if (!"cross".equals(joinType)) w.write(", Condition on");
						w.write(") {\n");
						w.write("\t\treturn new _"+ r +"_Query2<T1, T2>(q, t, \""+ joinType +" join\", "+ ("cross".equals(joinType) ? "null" : "on") +");\n");
						w.write("\t}\n");
					} else {
						writeJoinJavadoc(w, i, tTypes);
						w.write("\tpublic static <"+ tExtendsTable +"> Query<J"+ i +"<"+ tTypes);
						w.write(">> "+ joinType + i +"(final Query<J"+ (i-1) +"<"+ genTTypes(i-1));
						w.write(">> q, "+ inputType +"<T"+ i +"> t");
						if (!"cross".equals(joinType)) w.write(", Condition on");
						w.write(") {\n");
						w.write("\t\treturn new _"+ r +"_Query"+ i +"<"+ tTypes +">(q, t, \""+ joinType +" join\", "+ ("cross".equals(joinType) ? "null" : "on") +");\n");
						w.write("\t}\n");
					}
				}
			}
			w.write("\n");

			w.write("\n");
			w.write("\t/**\n");
			w.write("\t * This class represents a join across "+ i +" tables.\n");
			w.write("\t * It contains "+ i +" typed references (t1 to t"+ i +") to the join row components.\n");
			w.write("\t * (each of them containing all the columns they contributed to the join)\n");
			w.write("\t */\n");
			w.write("\tpublic static class J"+ i +" <"+ tExtendsTable +"> extends J {\n");

			//w.write("\t\tprivate List<Field<?>> __NOSCO_PRIVATE_FIELDS = null;\n");
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
				w.write("(t"+ j +"==null ? null : t"+ j +".SCHEMA_NAME())");
				if (j < i) w.write("+\" + \"+");
			}
			w.write(";\n");
			w.write("\t\t}\n");

			w.write("\t\t@Override\n");
			w.write("\t\tprotected String TABLE_NAME() {\n");
			w.write("\t\t\treturn ");
			for (int j=1; j<=i; ++j) {
				w.write("(t"+ j +"==null ? null : t"+ j +".TABLE_NAME())");
				if (j < i) w.write("+\" + \"+");
			}
			w.write(";\n");
			w.write("\t\t}\n");

			w.write("\t\t@Override\n");
			w.write("\t\tpublic List<Field<?>> fields() {\n");
			w.write("\t\t\tList<Field<?>> fields = new ArrayList<Field<?>>();\n");
			for (int j=1; j<=i; ++j) {
				w.write("\t\t\tfields.addAll(t"+ j +".fields());\n");
			}
			w.write("\t\t\tfields = Collections.unmodifiableList(fields);\n");
			w.write("\t\t\treturn fields;\n");
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
					w.write("(t"+ j +"!=null && t"+ j +"."+ op +"())");
					if (j < i) w.write(cmp);
				}
				w.write(";\n");
				w.write("\t\t}\n");
				w.write("\t\t@Override\n");
				w.write("\t\tpublic boolean "+ op +"(DataSource ds) throws SQLException {\n");
				w.write("\t\t\treturn ");
				for (int j=1; j<=i; ++j) {
					w.write("(t"+ j +"!=null && t"+ j +"."+ op +"(ds))");
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

	private static void gen_Joins(final Writer w, final int n, final String r) throws IOException {
		w.write("package org.kered.dko;\n\n");
		w.write("import java.sql.SQLException;\n");
		w.write("import java.util.ArrayList;\n");
		w.write("import java.util.Collections;\n");
		w.write("import java.util.List;\n");
		w.write("import javax.sql.DataSource;\n");
		w.write("import org.kered.dko.Field.FK;\n");
		w.write("import org.kered.dko.Table.__Alias;\n");
		w.write("import org.kered.dko.Join.*;\n");
		w.write("\n");

		w.write("public class _Join {\n");
		w.write("\n");

		w.write("\tstatic abstract class J extends Table {\n");
		w.write("\t\tList<Class<?>> types = null;\n");
		w.write("\t}\n");

		for (int i=2; i<=n; ++i) {
			w.write("\n");

			final String tExtendsTable =genTExtendsTable(i);
			final String tTypes =genTTypes(i);


			final String[] joinTypes = {"left", "right", "inner", "outer", "cross"};
			final String[] inputTypes = {"Class", "__Alias"};

			w.write("\t/** \n");
			w.write("\t * Please don't reference this type directly.  Reference it as a {@code org.kered.dko.Query<Join.J"+ i +"<"+ tTypes +">>}\n");
			w.write("\t */\n");
			w.write("\tpublic static class _"+ r +"_Query"+ i +"<"+ tExtendsTable +"> extends DBQuery<J"+ i +"<"+ tTypes +">> {\n");
			w.write("\t\tfinal int SIZE = "+ i +";\n");
			for (final String inputType : inputTypes) {
				if (i == 2) {
					w.write("\t\t_"+ r +"_Query2(final Query<T1> q, final "+ inputType +"<T2> t, String joinType, Condition on) {\n");
					w.write("\t\t\tsuper(J"+ i +".class, q, t, joinType, on);\n");
					w.write("\t\t}\n");
				} else {
					w.write("\t\t_"+ r +"_Query"+ i +"(final Query<J"+ (i-1) +"<"+ genTTypes(i-1) +">> q, final "+ inputType +"<T"+ i +"> t, String joinType, Condition on) {\n");
					w.write("\t\t\tsuper(J"+ i +".class, q, t, joinType, on);\n");
					w.write("\t\t}\n");
				}
			}
			w.write("\t}\n");



		}

		w.write("\n}\n");
	}

	private static void writeJoinJavadoc(final Writer w, final int i,
			final String tTypes) throws IOException {
		w.write("\t/** \n");
		w.write("\t * Joins types "+ tTypes +" into a {@code org.kered.dko.Query<Join.J"+ i +"<"+ tTypes +">>}\n");
		w.write("\t */\n");
	}

	private static String genTTypes(final int i) {
		final StringBuffer sb = new StringBuffer();
		for (int j=1; j<=i; ++j) {
			sb.append("T"+ j);
			if (j < i) sb.append(", ");
		}
		return sb.toString();
	}

	public static String genTExtendsTable(final int i) {
		final StringBuffer sb = new StringBuffer();
		for (int j=1; j<=i; ++j) {
			sb.append("T"+ j +" extends Table");
			if (j < i) sb.append(", ");
		}
		return sb.toString();
	}

}
