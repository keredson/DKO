package org.nosco;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CodeGenerator {
	
	private static RSArgsParser argsParser = new RSArgsParser(new HashMap<String,Boolean>() {{
		put("package", true);
		put("metadata", true);
		put("dir", true);
		put("strip-prefixes", true);
		put("strip-suffixes", true);
	}}, new HashMap<String,String>() {{
		put("p", "package");
		put("m", "metadata");
		put("d", "dir");
	}}, new HashMap<String,String>() {{
		put("package", "noscodb");
		put("dir", "gensrc");
		put("metadata", "schema.json");
	}});

	private final String pkg;
	private final String dir;

	private String[] stripPrefixes;

	private String[] stripSuffixes;
	

	public CodeGenerator(String dir, String pkg, String[] stripPrefixes, String[] stripSuffixes) {
		this.dir = dir;
		this.pkg = pkg;
		this.stripPrefixes = stripPrefixes.clone();
		this.stripSuffixes = stripSuffixes.clone();
	}


	public static void main(String[] args) throws IOException, JSONException {
		Map<String, String> params = argsParser.parse(args);
		String dir = params.get("dir");
		//recursiveDelete(new File(dir));
		String pkg = params.get("package");
		String[] stripPrefixes = {};
		if (params.get("strip-prefixes") != null) stripPrefixes = params.get("strip-prefixes").split(",");
		String[] stripSuffixes = {};
		if (params.get("strip-suffixes") != null) stripSuffixes = params.get("strip-suffixes").split(",");
		
		BufferedReader br = new BufferedReader(new FileReader(params.get("metadata")));
		StringBuffer sb = new StringBuffer();
		String s = null;
		while ((s=br.readLine())!=null) sb.append(s).append('\n');
		JSONObject metadata = new JSONObject(sb.toString());

		CodeGenerator generator = new CodeGenerator(dir, pkg, stripPrefixes, stripSuffixes);
		
		JSONObject schemas = metadata.getJSONObject("schemas");
		JSONObject foreignKeys = metadata.getJSONObject("foreign_keys");

		for (String schema : schemas.keySet()) {
			JSONObject tables = schemas.getJSONObject(schema);
			for (String table : tables.keySet()) {
				JSONObject columns = tables.getJSONObject(table);
				JSONArray pks = getJSONArray(metadata, "primary_keys", schema, table);
				JSONObject fks = getJSONObject(metadata, "foreign_keys", schema, table);

				HashMap<String, Set<String[]>> incomingFKs = new HashMap<String, Set<String[]>>();
				for (String schema2 : foreignKeys.keySet()) {
					JSONObject tables2 = foreignKeys.getJSONObject(schema2);
					for (String table2 : tables2.keySet()) {
						JSONObject columns2 = tables2.getJSONObject(table2);
						for (String column2 : columns2.keySet()) {
							JSONArray relation = columns2.getJSONArray(column2);
							//System.out.println(relation);
							String relatedSchema = relation.getString(0);
							String relatedTable = relation.getString(1);
							String relatedColumn = relation.getString(2);
							String[] fk = {schema2, table2, column2};
							//System.out.println(fk[0] +"."+ fk[1] +"."+ fk[2]);
							if (!schema.equals(relatedSchema)) continue;
							if (!table.equals(relatedTable)) continue;
							Set<String[]> set = incomingFKs.get(relatedColumn);
							if (set==null) {
								set = new HashSet<String[]>();
								incomingFKs.put(relatedColumn, set);
							}
							set.add(fk);
						}
					}
				}

				
				generator.generate(schema, table, columns, pks, fks, incomingFKs);
			}
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
	
	
	private String getFieldName(String column) {
		return column.replace(' ', '_').toUpperCase();
	}

	private void generate(String schema, String table, JSONObject columns, JSONArray pks, 
			JSONObject fks, HashMap<String, Set<String[]>> incomingFKs) 
	throws IOException, JSONException {
		if (fks==null) fks = new JSONObject();
		String className = genTableClassName(table);
		Set<String> pkSet = new HashSet<String>();
		for (int i=0; i<pks.length(); ++i) {
			pkSet.add(pks.getString(i));
		}
		int fieldCount = columns.keySet().size();
		
		new File(Util.join("/", dir, pkg, schema)).mkdirs();
		File file = new File(Util.join("/", dir, pkg, schema, className+".java"));
		System.out.println("writing: "+ file.getAbsolutePath());
		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		br.write("package "+ pkg +"."+ schema +";\n\n");
		br.write("import java.sql.SQLException;\n");
		br.write("import java.util.Map;\n\n");
		br.write("import java.util.HashMap;\n\n");
		br.write("import org.nosco.Field;\n");
		br.write("import org.nosco.Query;\n");
		br.write("import org.nosco.QueryImpl;\n");
		br.write("import org.nosco.Table;\n");
		br.write("\n");
		br.write("public class "+ className +" extends Table {\n\n");
		
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
		for (String column : fks.keySet()) {
			JSONArray referenced = fks.getJSONArray(column);
			String referencedTable = referenced.getString(1);
			String referencedColumn = referenced.getString(2);
			String referencedTableClassName = genTableClassName(referencedTable);
			String fkName = genFKName(column, referencedTable);
			br.write("\tpublic static final Field.FK FK_"+ fkName);
			br.write(" = new Field.FK("+ index +", "+ className +".class, ");
			br.write(referencedTableClassName +".class, "+ column.toUpperCase() + ", ");
			br.write(referencedTableClassName + "." + referencedColumn.toUpperCase());
			br.write(");\n");
			++index;
		}
		br.write("\n");
		
		// write field value references
		for (String column : columns.keySet()) {
			br.write("\tprivate "+ getFieldType(columns.getString(column)).getName()); 
			br.write(" "+ underscoreToCamelCase(column, false) + " = null;\n"); 
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
			br.write("\t\t\t\t"+ underscoreToCamelCase(column, false) +" = (");
			br.write(getFieldType(columns.getString(column)).getName());
			br.write(") objects[i];\n");
			br.write("\t\t\t\tFETCHED_VALUES.set("+ getFieldName(column) +".INDEX);\n");
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
		for (String column : fks.keySet()) {
			JSONArray referenced = fks.getJSONArray(column);
			String referencedTable = referenced.getString(1);
			br.write("FK_" + genFKName(column, referencedTable) + ",");
		}
		br.write("};\n\t\treturn fields;\n\t}\n\n");
		
		//br.write("\tpublic Field[] GET_PRIMARY_KEY_FIELDS() {\n\t\tField[] fields = {");
		//for (int i=0; i<pks.length(); ++i) {
		//	br.write(pks.getString(i).toUpperCase()+",");
		//}
		//br.write("};\n\t\treturn fields;\n\t}\n\n");
		
		br.write("\tpublic static final Query<"+ className +"> ALL = new QueryImpl<");
		br.write(className +">("+ className +".class);\n\n");

		// write getters and setters
		for (String column : columns.keySet()) {
			if (fks.has(column)) continue;
			String cls = getFieldType(columns.getString(column)).getName();
			br.write("\tpublic "+ cls +" get"+ underscoreToCamelCase(column, true) +"() {\n");
			br.write("\t\tif (!FETCHED_VALUES.get("+ getFieldName(column) +".INDEX)) {\n");
			br.write("\t\t\t"+ underscoreToCamelCase(column, false) +" = ALL.onlyFields(");
			br.write(getFieldName(column)+")");
			for (String pk : pkSet) {
				br.write(".where("+ getFieldName(pk) +".eq("+ underscoreToCamelCase(pk, false) +"))");
			}
			br.write(".getTheOnly().get"+ underscoreToCamelCase(column, true) +"();\n");
			br.write("\t\t\tFETCHED_VALUES.set("+ getFieldName(column) +".INDEX);\n");
			br.write("\t\t}\n");
			br.write("\t\treturn "+ underscoreToCamelCase(column, false) +";\n\t}\n\n");
			
			if (!pkSet.contains(column)) {
				br.write("\tpublic void set"+ underscoreToCamelCase(column, true)); 
				br.write("("+ cls +" v) {\n");
				br.write("\t\t"+ underscoreToCamelCase(column, false) +" = v;\n");
				br.write("\t\tif (UPDATED_VALUES == null) UPDATED_VALUES = new java.util.BitSet();\n");
				br.write("\t\tUPDATED_VALUES.set("+ getFieldName(column) +".INDEX);\n");
				br.write("\t}\n\n");
			}
		}
		
		// write getters and setters for FKs
		for (String column : fks.keySet()) {
			JSONArray referenced = fks.getJSONArray(column);
			String referencedSchema = referenced.getString(0);
			String referencedTable = referenced.getString(1);
			String referencedColumn = referenced.getString(2);
			String referencedTableClassName = genTableClassName(referencedTable);
			String methodName = genFKMethodName(column, referencedTable);
			String cachedObjectName = "_NOSCO_FK_"+ underscoreToCamelCase(column, false);
			
			br.write("\tprivate "+ referencedTableClassName +" "+ cachedObjectName +" = null;\n\n");
			
			br.write("\tpublic "+ referencedTableClassName +" get"+ methodName +"() {\n");
			br.write("\t\tif (!FETCHED_VALUES.get(FK_"+ genFKName(column, referencedTable) +".INDEX)) {\n");
			br.write("\t\t\t"+ cachedObjectName +" = "+ referencedTableClassName +".ALL");
			br.write(".where("+ referencedTableClassName +"."+ getFieldName(referencedColumn) +".eq("+ underscoreToCamelCase(column, false) +"))");
			br.write(".getTheOnly();\n");
			br.write("\t\t\tFETCHED_VALUES.set(FK_"+ genFKName(column, referencedTable) +".INDEX);\n");
			br.write("\t\t}\n");
			br.write("\t\treturn "+ cachedObjectName +";\n\t}\n\n");
			
			br.write("\tpublic void set"+ methodName +"("+ referencedTableClassName +" v) {\n");
			//br.write(" v) {\n\t\tPUT_VALUE(");
			
			br.write("\t\t"+ underscoreToCamelCase(column, false) +" = v.get"+ underscoreToCamelCase(referencedColumn, true) +"();\n");
			br.write("\t\tif (UPDATED_VALUES == null) UPDATED_VALUES = new java.util.BitSet();\n");
			br.write("\t\tUPDATED_VALUES.set("+ getFieldName(column) +".INDEX);\n");
			br.write("\t\t"+ cachedObjectName +" = v;\n");
			br.write("\t\tUPDATED_VALUES.set(FK_"+ genFKName(column, referencedTable) +".INDEX);\n");
			
			br.write("\n\t}\n\n");
		}
		
		// write SET_FK
		br.write("\tprotected void SET_FK(Field.FK field, Object v) {\n");
		br.write("\t\tif (false);\n");
		for (String column : fks.keySet()) {
			String cachedObjectName = "_NOSCO_FK_"+ underscoreToCamelCase(column, false);
			JSONArray referenced = fks.getJSONArray(column);
			String referencedTable = referenced.getString(1);
			String referencedTableClassName = genTableClassName(referencedTable);
			br.write("\t\telse if (field == FK_"+ genFKName(column, referencedTable) +") {\n");
			br.write("\t\t\t"+ cachedObjectName +" = ("+ referencedTableClassName +") v;\n");
			br.write("\t\t\tFETCHED_VALUES.set(FK_"+ genFKName(column, referencedTable) +".INDEX);\n");
			br.write("\t\t}\n");
			
		}
		br.write("\t\telse {throw new RuntimeException(\"unknown FK\");}\n");
		br.write("\t}\n\n");

		// 
		for (Entry<String, Set<String[]>> entry : incomingFKs.entrySet()) {
			String column = entry.getKey();
			Set<String[]> relations = entry.getValue();
			for (String[] relation : relations) {
				String relatedSchema = relation[0];
				String relatedTable = relation[1];
				String relatedColumn = relation[2];
				String relatedTableClassName = this.genTableClassName(relatedTable);
				String s = relatedColumn.toLowerCase().endsWith("_id") ? 
						relatedColumn.substring(0, relatedColumn.length()-3) : relatedColumn;
				String method = relatedTableClassName;
				if (!table.toLowerCase().startsWith(s.toLowerCase())) {
					method = method +""+ underscoreToCamelCase(s,true);
				}
				br.write("\tpublic Query<"+ relatedTableClassName +"> get"+ method +"Set() {\n\t\treturn "+ relatedTableClassName +".ALL.where("+ relatedTableClassName +"."+ getFieldName(relatedColumn) +".eq(get"+ this.underscoreToCamelCase(column, true) +"()));\n\t}\n\n");
			}
		}
		
		// write save function
		br.write("\t@SuppressWarnings(\"rawtypes\")\n");
		br.write("\tpublic boolean save() throws SQLException {\n");
		br.write("\t\tif (!dirty()) return false;\n");
		br.write("\t\tQuery<"+ className +"> query = ALL");
		for (String pk : pkSet) {
			br.write(".where("+ getFieldName(pk) +".eq("+ underscoreToCamelCase(pk, false) +"))");
		}
		br.write(";\n");
		br.write("\t\tMap<Field<?>,Object> updates = new HashMap<Field<?>,Object>();\n");
		for (String column : columns.keySet()) {
			br.write("\t\tif (UPDATED_VALUES.get("+ getFieldName(column) +".INDEX)) {\n");
			br.write("\t\t\tupdates.put("+ getFieldName(column) +", "+ underscoreToCamelCase(column, false) +");\n");
			br.write("\t\t}\n");
		}
		br.write("\t\tquery = query.set(updates);\n");
		if (pkSet.isEmpty()) {
			br.write("\t\t\tquery.insert();\n\t\treturn true;\n");
		} else {
			br.write("\t\tif (");
			List<String> pkList = new ArrayList<String>(pkSet);
			for (int i=0; i<pkList.size(); ++i) {
				br.write(underscoreToCamelCase(pkList.get(i), false) +" == null");
				if (i<pkList.size()-1) br.write(" || ");
			}
			br.write(") {\n");
			br.write("\t\t\t"+ underscoreToCamelCase(pkList.get(0), false) +" = (");
			br.write(getFieldType(columns.getString(pkList.get(0))).getName() +") query.insert();\n");
			br.write("\t\t\treturn true;\n\t\t} else {\n");
			br.write("\t\t\tint count = query.update();\n");
			br.write("\t\t\treturn count==1;\n");
			br.write("\t\t}\n");
		}
		br.write("\t}\n");

		br.write("}\n");
		br.close();
	}


	private String genFKName(String column, String referencedTable) {
		column = column.toUpperCase();
		referencedTable = referencedTable.toUpperCase();
		if (column.endsWith("_ID")) column = column.substring(0,column.length()-3);
		if (referencedTable.startsWith(column)) {
			return dePlural(referencedTable).toUpperCase();
		} else {
			return dePlural(column +"_"+ referencedTable).toUpperCase();
		}
	}


	private String genFKMethodName(String column, String referencedTable) {
		return genTableClassName(genFKName(column, referencedTable));
	}


	private String genTableClassName(String table) {
		for (String prefix : stripPrefixes) {
			if (table.startsWith(prefix)) {
				table = table.substring(prefix.length());
				break;
			}
		}
		/*for (String suffix : stripSuffixes) {
			if (table.endsWith(suffix)) {
				table = table.substring(0,table.length()-suffix.length());
				break;
			}
		} //*/
		return underscoreToCamelCase(dePlural(table), true);
	}
	
	private String dePlural(String s) {
		s = s.toLowerCase();
		if (s.endsWith("series"));
		else if (s.endsWith("us"));
		else if (s.endsWith("is"));
		else if (s.endsWith("ies")) s = s.substring(0,s.length()-3)+"y";
		else if (s.endsWith("s")) s = s.substring(0,s.length()-1);
		return s;
	}


	private Class<? extends Object> getFieldType(String type) {
		if ("varchar".equals(type)) return String.class;
		if ("char".equals(type)) return Character.class;
		if ("longtext".equals(type)) return String.class;
		if ("text".equals(type)) return String.class;
		if ("tinytext".equals(type)) return String.class;
		if ("mediumtext".equals(type)) return String.class;
		if ("int".equals(type)) return Integer.class;
		if ("mediumint".equals(type)) return Integer.class;
		if ("smallint".equals(type)) return Integer.class;
		if ("tinyint".equals(type)) return Integer.class;
		if ("bigint".equals(type)) return Long.class;
		if ("decimal".equals(type)) return Double.class;
		if ("blob".equals(type)) return Blob.class;
		if ("longblob".equals(type)) return Blob.class;
		if ("datetime".equals(type)) return Timestamp.class;
		if ("date".equals(type)) return Date.class;
		if ("timestamp".equals(type)) return Timestamp.class;
		if ("year".equals(type)) return Integer.class;
		if ("enum".equals(type)) return Integer.class;
		if ("set".equals(type)) return String.class;
		return null;
	}


	private String underscoreToCamelCase(String s, boolean capitalizeFirstChar) {
		if (s==null) return null;
		if (s.length()==0) return s;
		s = s.toLowerCase();
		s = s.replace(' ', '_');
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

}
