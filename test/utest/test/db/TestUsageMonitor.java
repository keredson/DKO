package test.db;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.BitSet;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.kered.dko.Context;
import org.kered.dko.Table;
import org.kered.dko.datasource.ConnectionCountingDataSource;
import org.kered.dko.persistence.ColumnAccess;
import org.kered.dko.persistence.QueryExecution;
import org.kered.dko.persistence.Util;
import org.kered.dko.unittest.nosco_test_jpetstore.Item;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import junit.framework.TestCase;

public class TestUsageMonitor extends TestCase {

	private final ClassLoader cl = this.getClass().getClassLoader();
	private static File f = null;

	static {
		try {
			f = File.createTempFile("dko_persistence_unit_test_", ".db");
			System.err.println(f);
			Util.setPersistenceDatabasePath(f);
			final MysqlDataSource ds = new MysqlDataSource();
			ds.setUser("root");
			ds.setDatabaseName("nosco_test_jpetstore");
			final Context vmContext = Context.getVMContext();
			vmContext.setDataSource(ds).setAutoUndo(false);
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testQueryExecutionCreate() throws Exception {
		SharedDBTests.printTestName();
		final long baseCount = QueryExecution.ALL.count();
		Item.ALL.asList();
		assertEquals(baseCount+1, QueryExecution.ALL.count());
	}

	public void testQueryExecutionCreateLoop() throws Exception {
		SharedDBTests.printTestName();
		final long baseCount = QueryExecution.ALL.count();
		for (int i=0; i<5; ++i) Item.ALL.asList();
		assertEquals(baseCount+1, QueryExecution.ALL.count());
	}

	public void testColumnAccessCreate() throws Exception {
		SharedDBTests.printTestName();
		final long baseCount = ColumnAccess.ALL.count();
		Item example = null;
		for (final Item item : Item.ALL) {
			item.getAttr3();
			example = item;
		}
		shutdownUsageMonitor(example);
		assertEquals(baseCount+1, ColumnAccess.ALL.count());
	}

	public void testColumnAccessCreateLoop() throws Exception {
		SharedDBTests.printTestName();
		final long baseCount = ColumnAccess.ALL.count();
		for (int i=0; i<5; ++i) {
			Item example = null;
			for (final Item item : Item.ALL) {
				item.getAttr3();
//				item.getAttr2();
				example = item;
			}
			shutdownUsageMonitor(example);
		}
		assertEquals(baseCount+1, ColumnAccess.ALL.count());
	}

	private void shutdownUsageMonitor(final Item example) throws Exception {
		final Field umf = Table.class.getDeclaredField("__NOSCO_USAGE_MONITOR");
		umf.setAccessible(true);
		final Object um = umf.get(example);
		final Method shutdown = Class.forName("org.kered.dko.UsageMonitor").getDeclaredMethod("shutdown");
		shutdown.setAccessible(true);
		shutdown.invoke(um);
	}

}
