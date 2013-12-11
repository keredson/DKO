package org.kered.dko.ant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.kered.dko.json.JSONException;
import org.kered.dko.json.JSONObject;

/**
 * Using the {@code schemas.json} file produced by the {@code SchemaExtractor}, this Ant task
 * produces a JAR file for use in your application. &nbsp; A simple example would be:
 *
 * <pre>{@code <target name="gen-dko-src">
 *   <taskdef name="dkogen" classname="org.kered.dko.ant.CodeGenerator"
 *      classpath="${dko_jar_location}"/>
 *
 *   <dkogen package="com.mycompany.dkos"
 *      debug="true"
 *      javaoutputdir="gensrc/dkos"
 *      schemas="schemas.json"
 *      datasource="dbgd1 = com.mycompany.dkos.callbacks.MyDataSourceFactory.get();"
 *   />
 *</target>}</pre>
 *
 * @author Derek Anderson
 */
public class CodeGenerator extends Task {

	private File jarfile = null;
	private File schemas = null;
	private File fake_fks = null;
	private String pkg = null;
	private String classpath = null;
	private String[] stripPrefixes = {};
	private final String[] stripSuffixes = {};
	private String dataSource = null;
	private String javacTarget = null;
	private String javacSource = null;
	private String javac = "javac";
	private File srcjarfile = null;
	private boolean debug = true;
	private String callbackPackage = null;
	private File javaOutputDir = null;
	private File typeMappings = null;
	private File enumsFile = null;
	private final Map<String,String> schemaAliases = new HashMap<String,String>();
	private boolean useDetailedToString = false;
	private final boolean genGson = false;
	private String allConstType = null;
	private String allConstFactory = null;

	/**
	 * Path to the jar file that should be generated.
	 * @param s
	 */
	public void setJarfile(final String s) {
		this.jarfile = new File(s);
	}

	/**
	 * Optional path to the jar file that should be generated containing only source code.
	 * @param s
	 */
	public void setSrcJarfile(final String s) {
		this.srcjarfile = new File(s);
	}
	
	/**
	 * Optional set of space-separated prefixes to strip from table names.
	 * @param s
	 */
	public void setStripPrefixes(final String s) {
		if (s!=null && s.length()>0) {
			stripPrefixes = s.split(" ");
		}
	}
	
	/**
	 * The output directory for the generated java classes.
	 * @param s
	 */
	public void setJavaOutputDir(final String s) {
		this.javaOutputDir = new File(s);
		if (!javaOutputDir.exists()) {
			javaOutputDir.mkdir();
		}
		if (!javaOutputDir.isDirectory()) {
			throw new RuntimeException("not a directory: "+ javaOutputDir.getAbsolutePath());
		}
	}

	/**
	 * Whether to enable debugging in the generated code.
	 * @param s
	 */
	public void setDebug(final String s) {
		this.debug = Util.truthy(s);
	}

	/**
	 * The base path of the generated classes.  For instance, with a base path of "org.kered.myapp",
	 * a schema called "mydb" and a table "my_table", the generated class would be {@code org.kered.myapp.mydb.MyTable}.
	 * @param s
	 */
	public void setPackage(final String s) {
		this.pkg = s;
	}
	
	public void setAllConstType(final String s) {
		this.allConstType = s;
	}

	public void setAllConstFactory(final String s) {
		this.allConstFactory = s;
	}

	/**
	 * The classpath to use when compiling the genned code
	 * @param s
	 */
	public void setClasspath(final String s) {
		this.classpath = s;
	}

	/**
	 * Defines the default datasource for for all generated classes.  This should look like a Java assignment
	 * operation that calls some code of yours that returns a datasource.  For instance: {@code myDb = MyDataSourceFactory.getDataSource();} <br>
	 * @param s
	 */
	public void setDataSource(final String s) {
		this.dataSource = s;
	}

	/**
	 * The path to the JSON file (generated with {@link org.kered.dko.ant.SchemaExtractorBase}) containing your database schema.
	 * @param s
	 */
	public void setSchemas(final String s) {
		this.schemas = new File(s);
	}

	/**
	 * By default generated DKO {@code toString()} methods call {@code toStringSimple()}
	 * (which display columns in the primary key plus any fields named like "*name*").
	 * If instead you'd like it to default to calling {@code toStringDetailed()} (which
	 * returns all fetched fields), set this to true.
	 * @param s
	 */
	public void setUseDetailedToString(final String s) {
		this.useDetailedToString  = Util.truthy(s);
	}

//	public void setGson(final String s) {
//		this.genGson  = Util.truthy(s);
//	}
//
	/**
	 * By default, DKOs use the schema name as the package name.  If you want to specify
	 * your own package name, do so here.  Format is comma separated with "as"... like so:
	 * "schema1 as pkg1, schema2 as pkg2, schema3 as pkg3"
	 * @param s
	 */
	public void setAliases(final String s) {
		for (String x : s.split(",")) {
			x = x.toLowerCase().trim();
			final int y = x.indexOf(" as ");
			if (y==-1) {
				schemaAliases.put(x, x);
			} else {
				schemaAliases.put(x.substring(0, y).trim(), x.substring(y+4).trim());
			}
		}
	}

	/**
	 * Path to the optional enums JSON file (which would be generated by {@link org.kered.dko.ant.SchemaExtractorBase}).
	 * @param s
	 */
	public void setEnums(final String s) {
		this.enumsFile = new File(s);
	}

	/**
	 * Path to an optional user-written JSON that controls what default types should map to what
	 * custom java types and how to convert those types.  For instance, SQL timestamp columns by default
	 * map to {@code java.sql.Timestamp} classes.  If instead you'd prefer {@code java.util.Date}s:
	 *
	 * <pre>{
	 *     "class_mappings": {
	 *         "java.sql.Timestamp": "java.util.Date",
	 *     },
	 *     "functions": {
	 *       "java.sql.Timestamp java.util.Date" :
	 *           "new java.util.Date((%s).getTime()",
	 *       "java.util.Date java.sql.Timestamp" :
	 *           "new java.sql.Timestamp((%s).getTime()",
	 *     }
	 * }</pre>
	 *
	 * Note the "functions" section where you can provide code that converts from one to another.
	 * <p>
	 * If you don't want all types to be mapped the same way for all classes, you can specify Java
	 * regex statements (matched against the schema/table/column names) to specify certain classes.
	 * For example, this will do the same as the previous example, but only for fields with names ending in "_date":
	 *
	 * <pre>{
	 *     "schema_mappings": {
	 *         ".*_date": "java.util.Date",
	 *     },
	 *     "functions": {
	 *       "java.sql.Timestamp java.util.Date" :
	 *           "new java.util.Date((%s).getTime()",
	 *       "java.util.Date java.sql.Timestamp" :
	 *           "new java.sql.Timestamp((%s).getTime()",
	 *     }
	 * }</pre>
	 *
	 * @param s
	 */
	public void setTypeMappings(final String s) {
		this.typeMappings  = new File(s);
	}

	/**
	 * Sets the path to your javac binary.
	 * @param s
	 */
	public void setJavac(final String s) {
		this.javac  = s;
	}

	/**
	 * Sets the Javac target Java version for the compiled code.
	 * @param s
	 */
	public void setJavacTarget(final String s) {
		this.javacTarget = s;
	}

	/**
	 * Sets the Javac source parameter.
	 * @param s
	 */
	public void setJavacSource(final String s) {
		this.javacSource  = s;
	}

	/**
	 * Usually DKOs use the automatically generated FK relationships extracted from the database.  But sometimes
	 * those foreign keys aren't actually there.  (usually for performance reasons)  Specifying this file lets
	 * you add FK relationships to the generated code regardless.  The format is JSON like the following:
	 *
	 * <pre>{
	 *     "fk_1":{
	 *         "reffing": ["my_schema","product"],
	 *         "reffed": ["my_schema","manufacturer"],
	 *         "columns": {"manufacturer_id": "id"}
	 *     },
	 * }</pre>
	 *
	 * This will create a FK relationship from {@code product.manufacturer_id} to {@code manufacturer.id}.
	 * Compound keys are simply additional entries in the "columns" map.
	 *
	 * @param s
	 */
	public void setFakeFKs(final String s) {
		this.fake_fks = new File(s);
	}

	/**
	 * DKOs support a variety of callbacks pre and post database operations.  Specify this parameter for
	 * where the generated DKOs should look.  For instance, a schema/table {@code myapp.product} could
	 * generate a class {@code org.kered.myapp.Product}.  Which if given a callback package of
	 * "org.kered.myapp.callbacks" would look for callback methods in {@code org.kered.myapp.callbacks.ProductCB}.
	 *
	 * @param s
	 */
	public void setCallbackPackage(final String s) {
		this.callbackPackage  = s;
	}

	public void execute() {

		try {
			if (up2date()) {
				System.out
						.println(jarfile.getAbsolutePath() + " is up to date");
				return;
			}
		} catch (final ZipException e1) {
			e1.printStackTrace();
			throw new BuildException(e1);
		} catch (final IOException e1) {
			e1.printStackTrace();
			throw new BuildException(e1);
		}

		File tempDir = null;
		if (javaOutputDir == null) {
			final String tempdir = System.getProperty("java.io.tmpdir");
			tempDir = new File(tempdir + File.separator + "dko_"
					+ this.hashCode());
			tempDir.mkdir();
		} else {
			tempDir = this.javaOutputDir;
		}
		File classesDir = tempDir;  // new File(tempDir.getAbsolutePath() +
									// File.separator + "classes");
		if (!debug) {
			final String tempdir = System.getProperty("java.io.tmpdir");
			classesDir = new File(tempdir + File.separator + "dko_classes_"
					+ this.hashCode());
			classesDir.mkdir();
		}

		try {
			final String timestamp = String.valueOf(schemas.lastModified()) + ":"
					+ (fake_fks == null ? "null" : String.valueOf(fake_fks.lastModified()));
			final BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					tempDir.getAbsolutePath() + File.separator + ".timestamp")));
			bw.write(timestamp);
			bw.close();

			final JSONObject enums = enumsFile!=null && enumsFile.exists() ?
					readJSONObject(enumsFile) : new JSONObject();

			org.kered.dko.ant.ClassGenerator.go(tempDir.getAbsolutePath(), pkg,
					stripPrefixes, stripSuffixes, schemas.getAbsolutePath(), schemaAliases,
					fake_fks, typeMappings==null ? null : typeMappings.getAbsolutePath(),
					dataSource, callbackPackage, enums, useDetailedToString, genGson, allConstType, allConstFactory);

			if (dataSource != null) {
				org.kered.dko.ant.DataSourceGenerator.go(tempDir.getAbsolutePath(), pkg, dataSource,
						schemas.getAbsolutePath(), schemaAliases);
			}

			if (this.srcjarfile != null) {
				System.out.println("writing " + srcjarfile.getAbsolutePath());
				final String[] cmd2 = { "jar", "cf", srcjarfile.getAbsolutePath(), "-C",
						tempDir.getAbsolutePath(), "." };
				// System.out.println(Misc.join(" ", cmd2));
				final Process p2 = Runtime.getRuntime().exec(cmd2);
				p2.waitFor();
				BufferedReader br;
				br = new BufferedReader(new InputStreamReader(p2.getInputStream()));
				for (String s; (s = br.readLine()) != null; System.out.println(s));
				br = new BufferedReader(new InputStreamReader(p2.getErrorStream()));
				for (String s; (s = br.readLine()) != null; System.out.println(s));
				if (p2.exitValue() != 0) {
					throw new BuildException("jar exited " + p2.exitValue());
				}
			}

			if (javaOutputDir != null) {
				// we're done
				return;
			}

			System.out.println("compiling " + tempDir.getAbsolutePath());

			if (!javacExists()) {
				throw new RuntimeException("Command '"+ javac +"' does not exist.  Please " +
						"install the JDK, add javac to your PATH, or specify a path to it " +
						"via the 'javac' property in the noscogen ant task.");
			}

			final List<String> files = new ArrayList<String>();
			files.add(javac);
			if (debug) files.add("-g");
			if (this.javacTarget != null) {
				files.add("-target");
				files.add(javacTarget);
			}
			if (this.javacSource != null) {
				files.add("-source");
				files.add(javacSource);
			}
			final String[] options = {"-cp", classpath, "-d" + classesDir.getAbsolutePath() };
			for (final String s : options) files.add(s);
			findJava(tempDir, files);
			final String[] cmd = new String[files.size()];
			files.toArray(cmd);
			System.out.println(Util.join(" ", cmd));
			final Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			for (String s; (s = br.readLine()) != null; System.out.println(s));
			br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			for (String s; (s = br.readLine()) != null; System.out.println(s));
			if (p.exitValue() != 0) {
				throw new BuildException("javac exited " + p.exitValue());
			}

			System.out.println("writing " + jarfile.getAbsolutePath());
			final String[] cmd2 = { "jar", "cf", jarfile.getAbsolutePath(), "-C",
					classesDir.getAbsolutePath(), "." };
			// System.out.println(Misc.join(" ", cmd2));
			final Process p2 = Runtime.getRuntime().exec(cmd2);
			p2.waitFor();
			br = new BufferedReader(new InputStreamReader(p2.getInputStream()));
			for (String s; (s = br.readLine()) != null; System.out.println(s));
			br = new BufferedReader(new InputStreamReader(p2.getErrorStream()));
			for (String s; (s = br.readLine()) != null; System.out.println(s));
			if (p2.exitValue() != 0) {
				throw new BuildException("jar exited " + p2.exitValue());
			}

			delete(tempDir);
		} catch (final IOException e) {
			e.printStackTrace();
			throw new BuildException(e);
		} catch (final JSONException e) {
			e.printStackTrace();
			throw new BuildException(e);
		} catch (final InterruptedException e) {
			throw new BuildException(e);
		}

	}

	private boolean javacExists() {
		Process p;
		try {
			p = Runtime.getRuntime().exec(javac);
			final int ret = p.waitFor();
			if (ret == 2) return true;
		} catch (final IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (final InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	static JSONObject readJSONObject(final File enumsFile) throws IOException, JSONException {
		final BufferedReader br = new BufferedReader(new FileReader(enumsFile));
		final StringBuffer sb = new StringBuffer();
		String s = null;
		while ((s=br.readLine())!=null) sb.append(s).append('\n');
		return new JSONObject(sb.toString());
	}

	private boolean up2date() throws ZipException, IOException {
		if (jarfile == null && this.javaOutputDir != null) {
			System.err.println("you're outputing code to a directory, not a jar, so " +
					"up2date detection doesn't work.  always writing all classes.");
			return false;
		}
		if (!jarfile.exists()) return false;
		final String timestamp = String.valueOf(schemas.lastModified()) + ":"
				+ String.valueOf(fake_fks.lastModified());
		final ZipFile jar = new ZipFile(jarfile);
		final ZipEntry e = jar.getEntry(".timestamp");
		if (e == null) return false;
		final InputStream is = jar.getInputStream(e);
		final BufferedReader br = new BufferedReader(new InputStreamReader(is));
		final String s = br.readLine();
		br.close();
		jar.close();
		return timestamp.equals(s);
	}

	private static void delete(final File f) throws IOException {
		if (f.isDirectory()) {
			for (final File c : f.listFiles()) delete(c);
		}
		f.delete();
	}

	private static void findJava(final File f, final List<String> list) {
		if (f.isDirectory())
			for (final File c : f.listFiles())
				findJava(c, list);
		if (f.getAbsolutePath().endsWith(".java"))
			list.add(f.getAbsolutePath());
	}


}
