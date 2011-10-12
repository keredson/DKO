package org.nosco.ant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.nosco.json.JSONException;
import org.nosco.util.Misc;

public class CodeGenerator extends Task {

    private File jarfile = null;
    private String pkg = null;
    private String classpath = null;
    private String[] stripPrefixes = {};
    private String[] stripSuffixes = {};

    public void setJarfile(String s) {
	this.jarfile  = new File(s);
    }

    public void setPackage(String s) {
	this.pkg  = s;
    }

    public void setClasspath(String s) {
	this.classpath  = s;
    }

    public void execute() {

	try {
	    if (up2date()) {
	        System.out.println(jarfile.getAbsolutePath() +" is up to date");
	        return;
	    }
	} catch (ZipException e1) {
	    e1.printStackTrace();
	    throw new BuildException(e1);
	} catch (IOException e1) {
	    e1.printStackTrace();
	    throw new BuildException(e1);
	}

	String tempdir = System.getProperty("java.io.tmpdir");
	File tempDir = new File(tempdir + File.separator + "nosco_" + this.hashCode());
	tempDir.mkdir();
	File classesDir = tempDir; //new File(tempDir.getAbsolutePath() + File.separator + "classes");
	//classesDir.mkdir();

	try {
	    String timestamp = String.valueOf(new File("schema.json").lastModified()) + ":"
			+ String.valueOf(new File("fake_fks.json").lastModified());
	    BufferedWriter bw = new BufferedWriter(new FileWriter(new File(tempDir.getAbsolutePath() + File.separator + ".timestamp")));
	    bw.write(timestamp);
	    bw.close();

	    org.nosco.CodeGenerator.go(tempDir.getAbsolutePath(), pkg, stripPrefixes, stripSuffixes,
	    	"schema.json", "fake_fks.json");

	    System.out.println("compiling "+ tempDir.getAbsolutePath());
	    String[] cmd = {"javac", "-g", "-cp", classpath, "-d", classesDir.getAbsolutePath()};
	    List<String> files = new ArrayList<String>();
	    for (String s : cmd) files.add(s);
	    findJava(tempDir, files);
		    cmd = new String[files.size()];
	    files.toArray(cmd);
	    //System.out.println(Misc.join(" ", cmd));
	    Process p = Runtime.getRuntime().exec(cmd);
	    p.waitFor();
	    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    for (String s; (s=br.readLine())!=null; System.out.println(s));
	    br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	    for (String s; (s=br.readLine())!=null; System.out.println(s));
	    if (p.exitValue() != 0) {
		throw new BuildException("javac exited "+ p.exitValue());
	    }

	    System.out.println("writing "+ jarfile.getAbsolutePath());
	    String[] cmd2 = {"jar", "cf", jarfile.getAbsolutePath(), "-C", tempDir.getAbsolutePath(), "."};
	    //System.out.println(Misc.join(" ", cmd2));
	    Process p2 = Runtime.getRuntime().exec(cmd2);
	    p2.waitFor();
	    br = new BufferedReader(new InputStreamReader(p2.getInputStream()));
	    for (String s; (s=br.readLine())!=null; System.out.println(s));
	    br = new BufferedReader(new InputStreamReader(p2.getErrorStream()));
	    for (String s; (s=br.readLine())!=null; System.out.println(s));
	    if (p2.exitValue() != 0) {
		throw new BuildException("jar exited "+ p2.exitValue());
	    }

	    delete(tempDir);
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new BuildException(e);
	} catch (JSONException e) {
	    e.printStackTrace();
	    throw new BuildException(e);
	} catch (InterruptedException e) {
	    throw new BuildException(e);
	}


    }

    private boolean up2date() throws ZipException, IOException {
	if (!jarfile.exists()) return false;
	File schema = new File("schema.json");
	File fake_fks = new File("fake_fks.json");
	String timestamp = String.valueOf(new File("schema.json").lastModified()) + ":"
		+ String.valueOf(new File("fake_fks.json").lastModified());
	ZipFile jar = new ZipFile(jarfile);
	ZipEntry e = jar.getEntry(".timestamp");
	if (e == null) return false;
	InputStream is = jar.getInputStream(e);
	BufferedReader br = new BufferedReader(new InputStreamReader(is));
	String s = br.readLine();
	br.close();
	jar.close();
	return timestamp.equals(s);
    }

    private static void delete(File f) throws IOException {
	if (f.isDirectory())
	    for (File c : f.listFiles())
		delete(c);
	f.delete();
    }

    private static void findJava(File f, List<String> list) {
	if (f.isDirectory())
	    for (File c : f.listFiles())
		findJava(c, list);
	if (f.getAbsolutePath().endsWith(".java")) list.add(f.getAbsolutePath());
    }

}
