package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.kered.dko.persistence.QuerySize;

import junit.framework.TestCase;

public class TestSerializable extends TestCase {

	public void test01() throws IOException, IOException, ClassNotFoundException {
		File f = new File(".TestSerializable.tmp");
		f.deleteOnExit();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
		QuerySize qs = new QuerySize().setHashCode(12345).setRowCount(4l).setTableName("junk");
		oos.writeObject(qs);
		oos.close();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
		QuerySize qs2 = (QuerySize) ois.readObject();
		assertEquals(12345, qs2.getHashCode().intValue());
		assertEquals(4l, qs2.getRowCount().longValue());
		assertEquals("junk", qs2.getTableName());
	}

}
