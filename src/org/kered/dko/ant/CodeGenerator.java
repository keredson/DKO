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
 * produces a JAR file for use in your application. &nbsp;
 *
 * @author Derek Anderson
 */
public class CodeGenerator extends Task {

	private File jarfile = null;
	private File schemas = null;
	private File fake_fks = null;
	private String pkg = null;
	private String classpath = null;
	private final String[] stripPrefixes = {};
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

	public void setJarfile(final String s) {
		this.jarfile = new File(s);
	}

	public void setSrcJarfile(final String s) {
		this.srcjarfile = new File(s);
	}

	public void setJavaOutputDir(final String s) {
		this.javaOutputDir = new File(s);
		if (!javaOutputDir.exists()) {
			javaOutputDir.mkdir();
		}
		if (!javaOutputDir.isDirectory()) {
			throw new RuntimeException("not a directory: "+ javaOutputDir.getAbsolutePath());
		}
	}

	public void setDebug(final String s) {
		this.debug  = "true".equalsIgnoreCase(s) || "t".equalsIgnoreCase(s) || "1".equals(s);
	}

	public void setPackage(final String s) {
		this.pkg = s;
	}

	public void setClasspath(final String s) {
		this.classpath = s;
	}

	public void setDataSource(final String s) {
		this.dataSource = s;
	}

	public void setSchemas(final String s) {
		this.schemas = new File(s);
	}

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

	public void setEnums(final String s) {
		this.enumsFile = new File(s);
	}

	public void setTypeMappings(final String s) {
		this.typeMappings  = new File(s);
	}

	public void setJavac(final String s) {
		this.javac  = s;
	}

	public void setJavacTarget(final String s) {
		this.javacTarget = s;
	}

	public void setJavacSource(final String s) {
		this.javacSource  = s;
	}

	public void setFakeFKs(final String s) {
		this.fake_fks = new File(s);
	}

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
					dataSource, callbackPackage, enums);

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
