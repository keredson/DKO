package org.nosco.ant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.nosco.json.JSONArray;
import org.nosco.json.JSONException;
import org.nosco.json.JSONObject;
import org.nosco.util.Misc;

class ClassGenerator {

	private final String pkg;
	private final String dir;

	private String[] stripPrefixes;

	private String[] stripSuffixes;

	private Map<String, String> tableToClassName;


	public ClassGenerator(String dir, String pkg, String[] stripPrefixes, String[] stripSuffixes) {
		this.dir = dir;
		this.pkg = pkg;
		this.stripPrefixes = stripPrefixes.clone();
		this.stripSuffixes = stripSuffixes.clone();
	}

	private static class FK {
	    String name = null;
	    String[] reffing = null;
	    String[] reffed = null;
	    Map<String,String> columns = new LinkedHashMap<String,String>();
	}

	public static void go(String dir, String pkg, String[] stripPrefixes,
		String[] stripSuffixes, String metadataFile, String fakefksFile, String dataSource,
		String callbackPackage) throws IOException, JSONException {

		BufferedReader br = new BufferedReader(new FileReader(metadataFile));
		StringBuffer sb = new StringBuffer();
		String s = null;
		while ((s=br.readLine())!=null) sb.append(s).append('\n');
		JSONObject metadata = new JSONObject(sb.toString());

		JSONObject fakeFKs = new JSONObject();
		File fakeFKsFile = new File(fakefksFile);
		if (fakeFKsFile.exists()) {
			br = new BufferedReader(new FileReader(fakeFKsFile));
			sb = new StringBuffer();
			s = null;
			while ((s=br.readLine())!=null) sb.append(s).append('\n');
			fakeFKs = new JSONObject(sb.toString());
		}

		ClassGenerator generator = new ClassGenerator(dir, pkg, stripPrefixes, stripSuffixes);

		JSONObject schemas = metadata.getJSONObject("schemas");
		JSONObject foreignKeys = metadata.getJSONObject("foreign_keys");

		String dataSourceName = DataSourceGenerator.getDataSourceName(dataSource);

		for (String schema : schemas.keySet()) {
			JSONObject tables = schemas.getJSONObject(schema);

			generator.tableToClassName = new HashMap<String,String>();

			for (String table : new TreeSet<String>(tables.keySet())) {
				// we process these in order to avoid naming conflicts when you have
				// both plural and singular tables of the same root word
				generator.genTableClassName(table);
			}

			for (String table : tables.keySet()) {
			    // skip these junk mssql tables
			    if(table.startsWith("syncobj_")) continue;

				JSONObject columns = tables.getJSONObject(table);
				JSONArray pks = getJSONArray(metadata, "primary_keys", schema, table);
				List<FK> fks = new ArrayList<FK>();
				List<FK> fksIn = new ArrayList<FK>();

				for (String constraint_name : foreignKeys.keySet()) {
				    JSONObject fkmd = foreignKeys.getJSONObject(constraint_name);
				    splitFK(schema, table, fks, fksIn, constraint_name, fkmd);
				}
				for (String constraint_name : fakeFKs.keySet()) {
				    JSONObject fkmd = fakeFKs.getJSONObject(constraint_name);
				    splitFK(schema, table, fks, fksIn, constraint_name, fkmd);
				}

				generator.generate(schema, table, columns, pks, fks, fksIn, dataSourceName,
						callbackPackage);
			}
		}

	}

	private static void splitFK(String schema, String table, List<FK> fks, List<FK> fksIn,
		String constraint_name, JSONObject fkmd) throws JSONException {
	    FK fk = new FK();
	    fk.name = constraint_name;
	    String[] reffing = {fkmd.getJSONArray("reffing").getString(0),
	        fkmd.getJSONArray("reffing").getString(1)};
	    fk.reffing = reffing;
	    String[] reffed = {fkmd.getJSONArray("reffed").getString(0),
	        fkmd.getJSONArray("reffed").getString(1)};
	    fk.reffed = reffed;
	    JSONObject cols = fkmd.getJSONObject("columns");
	    for (String key : cols.keySet()) {
	    fk.columns.put(key, cols.getString(key));
	    }
	    if (schema.equals(fk.reffing[0]) && table.equals(fk.reffing[1])) {
	    fks.add(fk);
	    }
	    if (schema.equals(fk.reffed[0]) && table.equals(fk.reffed[1])) {
	    fksIn.add(fk);
	    }
	}

	private static JSONObject getJSONObject(JSONObject o, String... path) throws JSONException {
		for (String s : path) {
			if (o==null) return null;
			if (o.has(s)) o = o.optJSONObject(s);
			else return null;
		}
		return o;
	}

	private static JSONArray getJSONArray(Object o, String... path) throws JSONException {
		for (String s : path) {
			if (o==null) return null;
			if (o instanceof JSONObject && ((JSONObject)o).has(s)) {
				o = ((JSONObject)o).opt(s);
			} else return null;
		}
		if (o instanceof JSONArray) return (JSONArray) o;
		else return null;
	}


	private static void recursiveDelete(File file) {
		if (file==null) return;
		for (File f : file.listFiles()) {
			if (f.isDirectory()) recursiveDelete(f);
			else f.delete();
		}
		file.delete();
	}


	private static String getFieldName(String column) {
		return column.replace(' ', '_')
			.replace("%", "_PERCENT")
			.replace("-", "_DASH_")
			.toUpperCase();
	}

	private static String getFieldName(Collection<String> columns) {
	    StringBuffer sb = new StringBuffer();
	    int i = 1;
	    for (String column : columns) {
		sb.append(column.replace(' ', '_').toUpperCase());
		if (++i < columns.size()) sb.append("__");
	    }
		return sb.toString();
	}

	private void generate(String schema, String table, JSONObject columns, JSONArray pks,
			List<FK> fks, List<FK> fksIn, String dataSourceName, String callbackPackage)
	throws IOException, JSONException {
		String className = genTableClassName(table);
		Set<String> pkSet = new HashSet<String>();
		if (pks == null) pks = new JSONArray();
		for (int i=0; i<pks.length(); ++i) {
			pkSet.add(pks.getString(i));
		}
		int fieldCount = columns.keySet().size();

		String pkgDir = Misc.join("/", pkg.split("[.]"));
		new File(Misc.join("/", dir, pkgDir, schema)).mkdirs();
		File file = new File(Misc.join("/", dir, pkgDir, schema, className+".java"));
		System.out.println("writing: "+ file.getAbsolutePath());
		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		br.write("package "+ pkg +"."+ schema +";\n\n");
		br.write("import java.lang.reflect.Method;\n");
		br.write("import java.lang.reflect.InvocationTargetException;\n");
		br.write("import java.sql.SQLException;\n");
		br.write("import javax.sql.DataSource;\n");
		br.write("import java.util.Map;\n\n");
		br.write("import java.util.HashMap;\n\n");
		br.write("import org.nosco.Field;\n");
		br.write("import org.nosco.Query;\n");
		br.write("import org.nosco.QueryFactory;\n");
		br.write("import org.nosco.Table;\n");
		br.write("\n");
		br.write("public class "+ className +" extends Table implements Comparable<"+ className +"> {\n\n");

		// write field constants
		int index = 0;
		for (String column : columns.keySet()) {
			br.write("\tpublic static final Field<");
			br.write(getFieldType(columns.getString(column)).getName());
			br.write("> "+ getFieldName(column));
			br.write(" = new Field<"+ getFieldType(columns.getString(column)).getName());
			br.write(">("+ index +", "+ className +".class, \""+ column);
			br.write("\", "+ getFieldType(columns.getString(column)).getName() +".class");
			br.write(");\n");
			++index;
		}
		br.write("\n");

		// write primary keys
		br.write("\tpublic static Field.PK PK = new Field.PK(");
		for (int i=0; i<pks.length(); ++i) {
			br.write(pks.getString(i).toUpperCase());
			if (i<pks.length()-1) br.write(", ");
		}
		br.write(");\n");
		br.write("\tpublic org.nosco.Field.PK PK() { return PK; }\n\n");

		// write foreign keys
		for (FK fk : fks) {
			String referencedTable = fk.reffed[1];
			String referencedTableClassName = genTableClassName(referencedTable);
			if (!schema.equals(fk.reffed[0])) {
				referencedTableClassName = pkg +"."+ fk.reffed[0] +"."+ referencedTableClassName;
			}
			String fkName = genFKName(fk.columns.keySet(), referencedTable);
			br.write("\tpublic static final Field.FK FK_"+ fkName);
			br.write(" = new Field.FK("+ index +", "+ className +".class, ");
			br.write(referencedTableClassName +".class");
			for (Entry<String, String> e : fk.columns.entrySet()) {
				br.write(", "+ e.getKey().toUpperCase());
			}
			for (Entry<String, String> e : fk.columns.entrySet()) {
				br.write(", "+ referencedTableClassName +".");
				br.write(e.getValue().toUpperCase());
			}
			br.write(");\n");
			++index;
		}
		br.write("\n");

		// write field value references
		for (String column : columns.keySet()) {
			br.write("\tprivate "+ getFieldType(columns.getString(column)).getName());
			br.write(" "+ getInstanceFieldName(column) + " = null;\n");
		}
		br.write("\n");

		// write constructors
		br.write("\tpublic "+ className +"() {}\n\n");
		br.write("\t@SuppressWarnings(\"rawtypes\")\n");
		br.write("\tprotected "+ className +"(Field[] fields, Object[] objects, int start, int end) {\n");
		br.write("\t\tif (fields.length != objects.length)\n\t\t\tthrow new IllegalArgumentException(");
		br.write("\"fields.length != objects.length => \"+ fields.length +\" != \"+ objects.length");
		br.write(" +\"\");\n");
		br.write("\t\tfor (int i=start; i<end; ++i) {\n");
		for (String column : columns.keySet()) {
			br.write("\t\t\tif (fields[i]=="+ getFieldName(column) +") {\n");
			br.write("\t\t\t\t"+ getInstanceFieldName(column) +" = (");
			br.write(getFieldType(columns.getString(column)).getName());
			br.write(") objects[i];\n");
			br.write("\t\t\t\t__NOSCO_FETCHED_VALUES.set("+ getFieldName(column) +".INDEX);\n");
			br.write("\t\t\t\tcontinue;\n");
			br.write("\t\t\t}\n");
		}
		br.write("\t\t}\n\t}\n\n");

		// write abstract method impls
		br.write("\tpublic String SCHEMA_NAME() {\n\t\treturn \""+ schema +"\";\n\t}\n\n");

		br.write("\tpublic String TABLE_NAME() {\n\t\treturn \""+ table +"\";\n\t}\n\n");

		br.write("\t@SuppressWarnings(\"rawtypes\")\n");
		br.write("\tpublic Field[] FIELDS() {\n\t\tField[] fields = {");
		for (String column : columns.keySet()) {
			br.write(getFieldName(column)+",");
		}
		br.write("};\n\t\treturn fields;\n\t}\n\n");

		br.write("\t@SuppressWarnings(\"rawtypes\")\n");
		br.write("\tpublic Field.FK[] FKS() {\n\t\tField.FK[] fields = {");
		for (FK fk : fks) {
			String referencedTable = fk.reffed[1];
//			if (!schema.equals(fk.reffed[0])) {
//				referencedTable = fk.reffed[0] +"_"+ referencedTable;
//			}
			br.write("FK_" + genFKName(fk.columns.keySet(), referencedTable) + ",");
		}
		br.write("};\n\t\treturn fields;\n\t}\n\n");

		// write the generic get(field) method
		br.write("\t@SuppressWarnings(\"unchecked\")\n");
		br.write("\tpublic <S> S get(Field<S> _field) {\n");
		for (String column : columns.keySet()) {
			br.write("\t\tif (_field=="+ getFieldName(column) +") ");
			br.write("return (S) "+ getInstanceFieldName(column) +";\n");
		}
		br.write("\t\tthrow new IllegalArgumentException(\"unknown field \"+ _field);\n");
		br.write("\t}\n\n");

		// write the generic set(field, value) method
		br.write("\tpublic <S> void set(Field<S> _field, S _value) {\n");
		for (String column : columns.keySet()) {
			br.write("\t\tif (_field=="+ getFieldName(column) +") ");
			br.write(getInstanceFieldName(column) +" = ("+ getFieldType(columns.getString(column)).getName() +") _value;\n");
		}
		br.write("\t\tthrow new IllegalArgumentException(\"unknown field \"+ _field);\n");
		br.write("\t}\n\n");

		//br.write("\tpublic Field[] GET_PRIMARY_KEY_FIELDS() {\n\t\tField[] fields = {");
		//for (int i=0; i<pks.length(); ++i) {
		//	br.write(pks.getString(i).toUpperCase()+",");
		//}
		//br.write("};\n\t\treturn fields;\n\t}\n\n");

		br.write("\tpublic static final Query<"+ className +"> ALL = QueryFactory.IT.getQuery("
		+ className +".class).use("+ pkg +"."+ dataSourceName +"."+ schema.toUpperCase() +");\n\n");

		// write toString
		br.write("\t public String toString() {\n");
		br.write("\t\treturn \"["+ className);
		for (int i=0; i<pks.length(); ++i) {
			String pk = pks.getString(i);
			br.write(" "+ pk+":");
			br.write("\"+"+ getInstanceFieldName(pk));
			br.write("+\"");
		}
		br.write("]\";\n");
		br.write("\t}\n\n");

		// write getters and setters
		for (String column : columns.keySet()) {
			//boolean skipColumn = false;
			//for (FK fk : fks) {
			//	if (fk.columns.containsKey(column)) skipColumn = true;
			//}
			//if (skipColumn) continue;
			String cls = getFieldType(columns.getString(column)).getName();
			br.write("\tpublic "+ cls +" get"+ getInstanceMethodName(column) +"() {\n");
			br.write("\t\tif (!__NOSCO_FETCHED_VALUES.get("+ getFieldName(column) +".INDEX)) {\n");
			br.write("\t\t\t"+ getInstanceFieldName(column) +" = ALL.onlyFields(");
			br.write(getFieldName(column)+")");
			for (String pk : pkSet) {
				br.write(".where("+ getFieldName(pk) +".eq("+ getInstanceFieldName(pk) +"))");
			}
			br.write(".getTheOnly().get"+ getInstanceMethodName(column) +"();\n");
			br.write("\t\t\t__NOSCO_FETCHED_VALUES.set("+ getFieldName(column) +".INDEX);\n");
			br.write("\t\t}\n");
			br.write("\t\treturn "+ getInstanceFieldName(column) +";\n\t}\n\n");

			//if (!pkSet.contains(column)) {
			br.write("\tpublic "+ className +" set"+ getInstanceMethodName(column));
			br.write("("+ cls +" v) {\n");
			br.write("\t\t"+ getInstanceFieldName(column) +" = v;\n");
			br.write("\t\tif (__NOSCO_UPDATED_VALUES == null) __NOSCO_UPDATED_VALUES = new java.util.BitSet();\n");
			br.write("\t\t__NOSCO_UPDATED_VALUES.set("+ getFieldName(column) +".INDEX);\n");
			br.write("\t\t__NOSCO_FETCHED_VALUES.set("+ getFieldName(column) +".INDEX);\n");
			br.write("\t\treturn this;\n");
			br.write("\t}\n\n");
			//}
		}

		// write getters and setters for FKs
		for (FK fk : fks) {
			String referencedSchema = fk.reffed[0];
			String referencedTable = fk.reffed[1];
			//String referencedColumn = referenced.getString(2);
			String referencedTableClassName = genTableClassName(referencedTable);
			if (!schema.equals(fk.reffed[0])) {
				referencedTableClassName = pkg +"."+ fk.reffed[0] +"."+ referencedTableClassName;
		}
			String methodName = genFKMethodName(fk.columns.keySet(), referencedTable);
			String cachedObjectName = "_NOSCO_FK_"+ underscoreToCamelCase(fk.columns.keySet(), false);

			br.write("\tprivate "+ referencedTableClassName +" "+ cachedObjectName +" = null;\n\n");

			br.write("\tpublic "+ referencedTableClassName +" get"+ methodName +"() {\n");
			br.write("\t\tif (!__NOSCO_FETCHED_VALUES.get(FK_"+ genFKName(fk.columns.keySet(), referencedTable) +".INDEX)) {\n");
			br.write("\t\t\t"+ cachedObjectName +" = "+ referencedTableClassName +".ALL");
			br.write(".where("+ referencedTableClassName +"."+ getFieldName(fk.columns.values()) +".eq("+ underscoreToCamelCase(fk.columns.keySet(), false) +"))");
			br.write(".getTheOnly();\n");
			br.write("\t\t\t__NOSCO_FETCHED_VALUES.set(FK_"+ genFKName(fk.columns.keySet(), referencedTable) +".INDEX);\n");
			br.write("\t\t}\n");
			br.write("\t\treturn "+ cachedObjectName +";\n\t}\n\n");

			br.write("\tpublic "+ className +" set"+ methodName +"("+ referencedTableClassName +" v) {\n");
			//br.write(" v) {\n\t\tPUT_VALUE(");

			br.write("\t\t"+ underscoreToCamelCase(fk.columns.keySet(), false) +" = v.get"+ underscoreToCamelCase(fk.columns.values(), true) +"();\n");
			br.write("\t\tif (__NOSCO_UPDATED_VALUES == null) __NOSCO_UPDATED_VALUES = new java.util.BitSet();\n");
			br.write("\t\t__NOSCO_UPDATED_VALUES.set("+ getFieldName(fk.columns.keySet()) +".INDEX);\n");
			br.write("\t\t"+ cachedObjectName +" = v;\n");
			br.write("\t\t__NOSCO_UPDATED_VALUES.set(FK_"+ genFKName(fk.columns.keySet(), referencedTable) +".INDEX);\n");
			br.write("\t\treturn this;\n");

			br.write("\n\t}\n\n");
		}

		// write SET_FK
		br.write("\tprotected void SET_FK(Field.FK field, Object v) {\n");
		br.write("\t\tif (false);\n");
		for (FK fk : fks) {
			String cachedObjectName = "_NOSCO_FK_"+ underscoreToCamelCase(fk.columns.keySet(), false);
			String referencedTable = fk.reffed[1];
			String referencedTableClassName = genTableClassName(referencedTable);
			if (!schema.equals(fk.reffed[0])) {
				referencedTableClassName = pkg +"."+ fk.reffed[0] +"."+ referencedTableClassName;
		}
			br.write("\t\telse if (field == FK_"+ genFKName(fk.columns.keySet(), referencedTable) +") {\n");
			br.write("\t\t\t"+ cachedObjectName +" = ("+ referencedTableClassName +") v;\n");
			br.write("\t\t\t__NOSCO_FETCHED_VALUES.set(FK_"+ genFKName(fk.columns.keySet(), referencedTable) +".INDEX);\n");
			br.write("\t\t}\n");

		}
		br.write("\t\telse {throw new RuntimeException(\"unknown FK\");}\n");
		br.write("\t}\n\n");

		// write the getTableFKSet() functions
		Map<String, Integer> reffingCounts = new HashMap<String,Integer>();
		for (FK fk : fksIn) {
		    String relatedTable = fk.reffing[1];
		    Integer c = reffingCounts.get(relatedTable);
		    if (c == null) c = 0;
		    reffingCounts.put(relatedTable, c+1);
		}
		for (FK fk : fksIn) {
		    String relatedSchema = fk.reffing[0];
		    String relatedTable = fk.reffing[1];
		    String relatedTableClassName = this.genTableClassName(relatedTable);
		    //String method = genFKMethodName(fk.columns.keySet(), relatedTableClassName);
			if (!schema.equals(fk.reffing[0])) {
				relatedTableClassName = pkg +"."+ fk.reffing[0] +"."+ relatedTableClassName;
			}
		    String method = getInstanceMethodName(relatedTable);
		    if (reffingCounts.get(relatedTable) > 1) {
			    String tmp = Misc.join("_", fk.columns.keySet());
				method = method + "_" + getInstanceMethodName(tmp);
		    }
		    String localVar = "__NOSCO_CACHED_FK_SET___"+ relatedTable + "___"
		    		+ Misc.join("__", fk.columns.keySet());
		    br.write("\tprivate Query<"+ relatedTableClassName +"> "+ localVar +" = null;\n");
		    br.write("\tpublic Query<"+ relatedTableClassName +"> get"+ method +"Set() {\n");
		    br.write("\t\tif ("+ localVar +" != null) return "+ localVar + ";\n");
		    br.write("\t\telse return "+ relatedTableClassName +".ALL");
		    for (Entry<String, String> e : fk.columns.entrySet()) {
			String relatedColumn = e.getKey();
			String column = e.getValue();
			br.write(".where("+ relatedTableClassName +"."+ getFieldName(relatedColumn) +".eq(get"+ getInstanceMethodName(column) +"()))");
		    }
		    br.write(";\n");
		    br.write("\t}\n\n");
		}

		// write save function
		br.write("\tpublic boolean save() throws SQLException {\n");
		br.write("\t\t return save(ALL.getDataSource());\n");
		br.write("\t}\n");
		br.write("\t@SuppressWarnings(\"rawtypes\")\n");
		br.write("\tpublic boolean save(DataSource ds) throws SQLException {\n");
		br.write("\t\tif (!dirty()) return false;\n");
		br.write("\t\tQuery<"+ className +"> query = ALL.use(ds)");
		for (String pk : pkSet) {
			br.write(".where("+ getFieldName(pk) +".eq("+ getInstanceFieldName(pk) +"))");
		}
		br.write(";\n");
		if (pkSet == null || pkSet.isEmpty()) {
			br.write("\t\tthrow new RuntimeException(\"save() is ambiguous on objects without PKs - use insert() or update()\");\n");
		} else {
			br.write("\t\tint size = query.size();\n");
			br.write("\t\tif (size == 0) return this.insert(ds);\n");
			br.write("\t\telse if (size == 1) return this.update(ds);\n");
			br.write("\t\telse throw new RuntimeException(\"more than one result was returned " +
					"for a query that specified all the PKs.  this is bad.\");\n");
			br.write("\t\t\n");
		}
		br.write("\t}\n");

		// write update function
		br.write("\tpublic boolean update() throws SQLException {\n");
		br.write("\t\t return update(ALL.getDataSource());\n");
		br.write("\t}\n");
		br.write("\t@SuppressWarnings(\"rawtypes\")\n");
		br.write("\tpublic boolean update(DataSource ds) throws SQLException {\n");
		br.write("\t\tif (!dirty()) return false;\n");
		br.write("\t\tQuery<"+ className +"> query = ALL.use(ds)");
		for (String pk : pkSet) {
			br.write(".where("+ getFieldName(pk) +".eq("+ getInstanceFieldName(pk) +"))");
		}
		if (pkSet == null || pkSet.size() == 0) {
			for (String column : columns.keySet()) {
				br.write(".where("+ getFieldName(column) +".eq("+ getInstanceFieldName(column) +"))");
			}
		}
		br.write(";\n");
		br.write("\t\tif (__NOSCO_CALLBACK_UPDATE_PRE!=null) "
				+ "try { __NOSCO_CALLBACK_UPDATE_PRE.invoke(null, this); }"
				+ "catch (IllegalAccessException e) { e.printStackTrace(); } "
				+ "catch (InvocationTargetException e) { e.printStackTrace(); }\n");
		br.write("\t\tMap<Field<?>,Object> updates = new HashMap<Field<?>,Object>();\n");
		for (String column : columns.keySet()) {
			br.write("\t\tif (__NOSCO_UPDATED_VALUES.get("+ getFieldName(column) +".INDEX)) {\n");
			br.write("\t\t\tupdates.put("+ getFieldName(column) +", "+ getInstanceFieldName(column) +");\n");
			br.write("\t\t}\n");
		}
		br.write("\t\tquery = query.set(updates);\n");
		br.write("\t\tint count = query.update();\n");
		br.write("\t\tif (__NOSCO_CALLBACK_UPDATE_POST!=null) "
				+ "try { __NOSCO_CALLBACK_UPDATE_POST.invoke(null, this, ds); }"
				+ "catch (IllegalAccessException e) { e.printStackTrace(); } "
				+ "catch (InvocationTargetException e) { e.printStackTrace(); }\n");
		br.write("\t\treturn count==1;\n");
		br.write("\t}\n");

		// write delete function
		br.write("\tpublic boolean delete() throws SQLException {\n");
		br.write("\t\t return delete(ALL.getDataSource());\n");
		br.write("\t}\n");
		br.write("\t@SuppressWarnings(\"rawtypes\")\n");
		br.write("\tpublic boolean delete(DataSource ds) throws SQLException {\n");
		br.write("\t\tQuery<"+ className +"> query = ALL.use(ds)");
		for (String pk : pkSet) {
			br.write(".where("+ getFieldName(pk) +".eq("+ getInstanceFieldName(pk) +"))");
		}
		if (pkSet == null || pkSet.size() == 0) {
			for (String column : columns.keySet()) {
				br.write(".where("+ getFieldName(column) +".eq("+ getInstanceFieldName(column) +"))");
			}
		}
		br.write(";\n");
		br.write("\t\tint count = query.deleteAll();\n");
		br.write("\t\treturn count==1;\n");
		br.write("\t}\n");

		// write insert function
		br.write("\tpublic boolean insert() throws SQLException {\n");
		br.write("\t\t return insert(ALL.getDataSource());\n");
		br.write("\t}\n");
		br.write("\t@SuppressWarnings(\"rawtypes\")\n");
		br.write("\tpublic boolean insert(DataSource ds) throws SQLException {\n");
		br.write("\t\tif (!dirty()) return false;\n");
		br.write("\t\tQuery<"+ className +"> query = ALL.use(ds)");
		for (String pk : pkSet) {
			br.write(".where("+ getFieldName(pk) +".eq("+ getInstanceFieldName(pk) +"))");
		}
		br.write(";\n");
		if (!pkSet.isEmpty()) {
			br.write("\t\tif (__NOSCO_CALLBACK_INSERT_PRE!=null) "
					+ "try { __NOSCO_CALLBACK_INSERT_PRE.invoke(null, this); }"
					+ "catch (IllegalAccessException e) { e.printStackTrace(); } "
					+ "catch (InvocationTargetException e) { e.printStackTrace(); }\n");
		}
		br.write("\t\tMap<Field<?>,Object> updates = new HashMap<Field<?>,Object>();\n");
		for (String column : columns.keySet()) {
			br.write("\t\tupdates.put("+ getFieldName(column) +", "+ getInstanceFieldName(column) +");\n");
		}
		br.write("\t\tquery = query.set(updates);\n");
		br.write("\t\t\tquery.insert();\n");
		br.write("\t\t\tif (__NOSCO_CALLBACK_INSERT_POST!=null) "
				+ "try { __NOSCO_CALLBACK_INSERT_POST.invoke(null, this, ds); }"
				+ "catch (IllegalAccessException e) { e.printStackTrace(); } "
				+ "catch (InvocationTargetException e) { e.printStackTrace(); }\n");
		br.write("\t\t\treturn true;\n");
		br.write("\t}\n");

		// write exists function
		br.write("\tpublic boolean exists() throws SQLException {\n");
		br.write("\t\t return exists(ALL.getDataSource());\n");
		br.write("\t}\n");
		br.write("\t@SuppressWarnings(\"rawtypes\")\n");
		br.write("\tpublic boolean exists(DataSource ds) throws SQLException {\n");
		br.write("\t\tif (!dirty()) return false;\n");
		br.write("\t\tQuery<"+ className +"> query = ALL.use(ds)");
		for (String column : pkSet == null || pkSet.size() == 0 ? columns.keySet() : pkSet) {
			br.write(".where("+ getFieldName(column) +".eq("+ getInstanceFieldName(column) +"))");
		}
		br.write(";\n");
		br.write("\t\tint size = query.size();\n");
		br.write("\t\treturn size > 0;\n");
		br.write("\t}\n");


		// write callbacks
		br.write("\tprivate static Method __NOSCO_CALLBACK_INSERT_PRE = null;\n");
		br.write("\tprivate static Method __NOSCO_CALLBACK_INSERT_POST = null;\n");
		br.write("\tprivate static Method __NOSCO_CALLBACK_UPDATE_PRE = null;\n");
		br.write("\tprivate static Method __NOSCO_CALLBACK_UPDATE_POST = null;\n");
		br.write("\tstatic {\n");
		br.write("\t\ttry {\n\t\t\t __NOSCO_CALLBACK_INSERT_PRE = Class.forName(\""+ callbackPackage
				+"."+ schema +"."+ className +"CB\").getMethod(\"preInsert\", "
				+ className +".class);\n\t\t} catch (Exception e) { /* ignore */ }\n");
		br.write("\t\ttry {\n\t\t\t __NOSCO_CALLBACK_INSERT_POST = Class.forName(\""+ callbackPackage
				+"."+ schema +"."+ className +"CB\").getMethod(\"postInsert\", "
				+ className +".class, DataSource.class);\n\t\t} catch (Exception e) { /* ignore */ }\n");
		br.write("\t\ttry {\n\t\t\t __NOSCO_CALLBACK_UPDATE_PRE = Class.forName(\""+ callbackPackage
				+"."+ schema +"."+ className +"CB\").getMethod(\"preUpdate\", "
				+ className +".class);\n\t\t} catch (Exception e) { /* ignore */ }\n");
		br.write("\t\ttry {\n\t\t\t __NOSCO_CALLBACK_UPDATE_POST = Class.forName(\""+ callbackPackage
				+"."+ schema +"."+ className +"CB\").getMethod(\"postUpdate\", "
				+ className +".class, DataSource.class);\n\t\t} catch (Exception e) { /* ignore */ }\n");
		br.write("\t}\n");

		// write the alias function
		br.write("\t/**\n");
		br.write("\t * Returns a table alias.  This is used when specifying manual joins\n");
		br.write("\t * to reference later using Field.from(alias) in where() conditions.\n");
		br.write("\t */\n");
		br.write("\tpublic static Table.__Alias<"+ className +"> as(String alias) {\n");
		br.write("\t\treturn new Table.__Alias<"+ className +">("+ className +".class, alias);\n");
		br.write("\t}\n\n");

		// write the hashcode function
		br.write("\t@Override\n");
		br.write("\tpublic int hashCode() {\n");
		br.write("\t\tfinal int prime = 31;\n");
		br.write("\t\tint result = 1;\n");
		for (String column : pkSet == null || pkSet.size() == 0 ? columns.keySet() : pkSet) {
			br.write("\t\tresult = prime * result + (("+ getInstanceFieldName(column)
					+" == null) ? 0 : "+ getInstanceFieldName(column) +".hashCode());\n");
		}
		br.write("\t\treturn result;\n");
		br.write("\t}\n\n");

		// write the equals function
		br.write("\t@Override\n");
		br.write("\tpublic boolean equals(Object other) {\n");
		br.write("\t\treturn (other == this) || ((other != null) \n");
		br.write("\t\t\t&& (other instanceof "+ className +")\n");
		br.write("\t\t\n");
		for (String column : pkSet == null || pkSet.size() == 0 ? columns.keySet() : pkSet) {
			br.write("\t\t\t&& (("+ getInstanceFieldName(column) +" == null) ? ((("
					+ className +")other)."+ getInstanceFieldName(column) +" == null) : ("
					+ getInstanceFieldName(column) +".equals((("+ className +")other)."
					+ getInstanceFieldName(column) +")))\n");
		}
		br.write("\t\t);\n");
		br.write("\t}\n\n");

		// write the compare function
		br.write("\t@Override\n");
		br.write("\tpublic int compareTo("+ className +" o) {\n");
		br.write("\t\tint v = 0;\n");
		for (String column : pkSet == null || pkSet.size() == 0 ? columns.keySet() : pkSet) {
			br.write("\t\tv = "+ getInstanceFieldName(column) +"==null ? (o."
					+ getInstanceFieldName(column) +"==null ? 0 : -1) : "
					+ getInstanceFieldName(column) +".compareTo(o."
					+ getInstanceFieldName(column) +");\n");
			br.write("\t\tif (v != 0) return v;\n");
		}
		br.write("\t\treturn 0;\n");
		br.write("\t}\n\n");

		// end class
		br.write("}\n");
		br.close();
	}

	private static String getInstanceFieldName(String column) {
	    if ("class".equals(column)) column = "JAVA_KEYWORD_class";
	    if ("for".equals(column)) column = "JAVA_KEYWORD_for";
	    column = column.replace("-", "_DASH_");
	    return underscoreToCamelCase(column, false);
	}

	private static String getInstanceMethodName(String column) {
	    if ("class".equals(column)) column = "JAVA_KEYWORD_class";
	    column = column.replace("-", "_DASH_");
	    return underscoreToCamelCase(column, true);
	}

	private String genFKName(Set<String> columns, String referencedTable) {
	    for(String column : columns) {
		column = column.toUpperCase();
		referencedTable = referencedTable.toUpperCase();
		if (column.endsWith("_ID")) column = column.substring(0,column.length()-3);
		if (referencedTable.startsWith(column)) {
			return dePlural(referencedTable).toUpperCase();
		} else {
			return dePlural(column +"_"+ referencedTable).toUpperCase();
		}
	    }
	    return null;
	}


	private String genFKMethodName(Set<String> columns, String referencedTable) {
		return this.underscoreToCamelCase(columns, true)+"FK";
		//return genTableClassName(genFKName(columns, referencedTable));
	}


	private String genTableClassName(String table) {
		if (tableToClassName.containsKey(table)) return tableToClassName.get(table);
		String proposed = table;
		for (String prefix : stripPrefixes) {
			if (proposed.startsWith(prefix)) {
				proposed = proposed.substring(prefix.length());
				break;
			}
		}
		/*for (String suffix : stripSuffixes) {
			if (table.endsWith(suffix)) {
				table = table.substring(0,table.length()-suffix.length());
				break;
			}
		} //*/
		String proposed2 = underscoreToCamelCase(dePlural(proposed), true);
		if (tableToClassName.containsValue(proposed2)) {
			proposed2 = underscoreToCamelCase(proposed, true);
		}
		tableToClassName.put(table, proposed2);
		return proposed2;
	}

	private String dePlural(String s) {
		s = s.toLowerCase();
		if (s.endsWith("series"));
		else if (s.endsWith("us"));
		else if (s.endsWith("is"));
		else if (s.endsWith("as"));
		else if (s.endsWith("ies")) s = s.substring(0,s.length()-3)+"y";
		else if (s.endsWith("s")) s = s.substring(0,s.length()-1);
		return s;
	}


	private Class<? extends Object> getFieldType(String type) {
		if ("varchar".equals(type)) return String.class;
		if ("char".equals(type)) return Character.class;
		if ("nvarchar".equals(type)) return String.class;
		if ("nchar".equals(type)) return String.class;
		if ("longtext".equals(type)) return String.class;
		if ("text".equals(type)) return String.class;
		if ("tinytext".equals(type)) return String.class;
		if ("mediumtext".equals(type)) return String.class;
		if ("ntext".equals(type)) return String.class;
		if ("xml".equals(type)) return String.class;
		if ("int".equals(type)) return Integer.class;
		if ("mediumint".equals(type)) return Integer.class;
		if ("smallint".equals(type)) return Integer.class;
		if ("tinyint".equals(type)) return Integer.class;
		if ("bigint".equals(type)) return Long.class;
		if ("decimal".equals(type)) return Double.class;
		if ("money".equals(type)) return Double.class;
		if ("numeric".equals(type)) return Double.class;
		if ("float".equals(type)) return Double.class;
		if ("real".equals(type)) return Float.class;
		if ("blob".equals(type)) return Blob.class;
		if ("longblob".equals(type)) return Blob.class;
		if ("datetime".equals(type)) return Timestamp.class;
		if ("date".equals(type)) return Date.class;
		if ("timestamp".equals(type)) return Timestamp.class;
		if ("year".equals(type)) return Integer.class;
		if ("enum".equals(type)) return Integer.class;
		if ("set".equals(type)) return String.class;
		if ("bit".equals(type)) return Boolean.class;
		if ("binary".equals(type)) return java.sql.Blob.class;
		if ("varbinary".equals(type)) return java.sql.Blob.class;
		if ("sql_variant".equals(type)) return Object.class;
		if ("smalldatetime".equals(type)) return Timestamp.class;
		if ("uniqueidentifier".equals(type)) return String.class;
		if ("image".equals(type)) return java.sql.Blob.class;
		System.err.println("unknown field type: "+ type);
		return null;
	}


	private static String underscoreToCamelCase(String s, boolean capitalizeFirstChar) {
		if (s==null) return null;
		if (s.length()==0) return s;
		s = s.toLowerCase();
		s = s.replace(' ', '_').replace("%", "_PERCENT");
		char[] c = s.toCharArray();
		if (capitalizeFirstChar) {
			c[0] = Character.toUpperCase(c[0]);
		}
		for (int i=1; i<c.length; ++i) {
			if (c[i-1]=='_') {
				c[i] = Character.toUpperCase(c[i]);
			}
		}
		return new String(c).replaceAll("_", "");
	}

	private static String underscoreToCamelCase(Collection<String> strings, boolean capitalizeFirstChar) {
	    StringBuffer sb = new StringBuffer();
	    for (String s : strings) {
		sb.append(underscoreToCamelCase(s, capitalizeFirstChar));
		capitalizeFirstChar = true;
	    }
	    return sb.toString();
	}

}
