package performance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import org.kered.dko.unittest.nosco_test_jpetstore.Item;

import junit.framework.TestCase;

public class MicroBenchmarks extends TestCase {

	Method oldM = null;
	{
		try {
			oldM = Class.forName("org.kered.dko.Util").getDeclaredMethod("getSCHEMA_NAME", Class.class);
			oldM.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	Method newM = null;
	{
		try {
			newM = Class.forName("org.kered.dko.Util").getDeclaredMethod("getSchemaName", Class.class);
			newM.setAccessible(true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	final static long COUNT = 1000000;
	
	public void testOldGetSchema() throws Exception {
		long start = System.currentTimeMillis();
		for (int i=0; i<COUNT; ++i) {
			oldM.invoke(null, Item.class);
		}
		long end = System.currentTimeMillis();
		double time = (end-start)/1000.0;
		System.err.println("testOldGetSchema took "+ time +" seconds, or "+ (time/COUNT) +" per invocation");
	}

	public void testNewGetSchema() throws Exception {
		long start = System.currentTimeMillis();
		for (int i=0; i<COUNT; ++i) {
			newM.invoke(null, Item.class);
		}
		long end = System.currentTimeMillis();
		double time = (end-start)/1000.0;
		System.err.println("testNewGetSchema took "+ time +" seconds, or "+ (time/COUNT) +" per invocation");
	}

	public static void main(final String[] args) throws Exception {
		MicroBenchmarks o = new MicroBenchmarks();
		if (Math.random() < .5) {
			o.testOldGetSchema();
			o.testNewGetSchema();
		} else {
			o.testNewGetSchema();
			o.testOldGetSchema();
		}
	}

}
