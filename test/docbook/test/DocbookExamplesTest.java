package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.sql.DataSource;

import org.kered.docbook.Doctor;
import org.kered.docbook.Person;

import junit.framework.TestCase;

public class DocbookExamplesTest extends TestCase {

	int i = 0;
	private PrintStream out = null;
	PrintStream originalOut = System.out;
	PrintStream originalErr = System.err;
	DataSource ds = DefaultDataSource.get();

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		++i;
		final File f = new File("gen/docbook/example"+i+".out");
		out = new PrintStream(new FileOutputStream(f));
		System.setOut(out);
		System.setErr(out);
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		if (out!=null) out.close();
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	public void test1() {
		for (final Person person : Person.ALL) {
			System.out.println(person);
		}
	}

}
