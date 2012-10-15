package test.db;

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
import org.kered.dko.unittest.nosco_test_jpetstore.Item;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import junit.framework.TestCase;

public class TestUsageMonitor extends TestCase {

	private final ClassLoader cl = this.getClass().getClassLoader();
	private Class<?> umc = null;
	private Field tumf = null;
	private Field umqhf = null;
	private Field umqcf = null;
	private Field tfvf = null;
	private Field umsff = null;
	private Method umqucm = null;
	private Method umwbfum = null;
	private Method umlsfm;

	public TestUsageMonitor() {
		try {
			umc = cl.loadClass("org.kered.dko.UsageMonitor");
			tumf = Table.class.getDeclaredField("__NOSCO_USAGE_MONITOR");
			tumf.setAccessible(true);
			tfvf  = Table.class.getDeclaredField("__NOSCO_FETCHED_VALUES");
			tfvf.setAccessible(true);
			umqhf  = umc.getDeclaredField("queryHash");
			umqhf.setAccessible(true);
			umqcf   = umc.getDeclaredField("qc");
			umqcf.setAccessible(true);
			umsff = umc.getDeclaredField("surpriseFields");
			umsff.setAccessible(true);
			umwbfum   = umc.getDeclaredMethod("warnBadFKUsage");
			umwbfum.setAccessible(true);
			umqucm  = umc.getDeclaredMethod("questionUnusedColumns");
			umqucm.setAccessible(true);
			umlsfm  = umc.getDeclaredMethod("loadStatsFor", Class.class);
			umlsfm.setAccessible(true);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	protected void setUp() throws Exception {
		super.setUp();
		final MysqlDataSource mysqlDS = new MysqlDataSource();
		mysqlDS.setUser("root");
		mysqlDS.setDatabaseName("nosco_test_jpetstore");
		final Context vmContext = Context.getVMContext();
		vmContext.setDataSource(mysqlDS).setAutoUndo(false);
		umlsfm.invoke(null, Item.class);
		final Map<String,Map<org.kered.dko.Field<?>,Long>> qc =
				(Map<String,Map<org.kered.dko.Field<?>,Long>>)umqcf.get(null);
		qc.clear();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@SuppressWarnings("unchecked")
	public void test01() throws Exception {
		String sth = null;
		for (int i=0; i<3; ++i) {
			//WeakReference<?> wfum = null;
			boolean first = true;
			Object um = null;
			for (final Item item : Item.ALL) {
				um = tumf.get(item);
				sth = (String) umqhf.get(um);
//				if (wfum==null) {
//					wfum = new WeakReference(um);
//				}
				final BitSet fv = (BitSet) tfvf.get(item);
				if (i==0) {
					assertEquals(item.FIELDS().size(), fv.cardinality());
					assertTrue(fv.get(Item.ATTR1.INDEX));
				}
				if (i==1) {
					assertEquals(item.PK.GET_FIELDS().size(), fv.cardinality());
					assertFalse(fv.get(Item.ATTR1.INDEX));
				}
				if (i==2) {
					assertEquals(item.PK.GET_FIELDS().size()+1, fv.cardinality());
					assertTrue(fv.get(Item.ATTR1.INDEX));
				}
				Collection<org.kered.dko.Field<?>> surpriseFields = null;
				surpriseFields = (Collection<org.kered.dko.Field<?>>)umsff.get(um);
				final int sizeBefore = surpriseFields == null ? 0 : surpriseFields.size();
				item.getAttr1();
				if (first) {
					surpriseFields = (Collection<org.kered.dko.Field<?>>)umsff.get(um);
					if (i==1) {
						assertEquals(sizeBefore+1, surpriseFields.size());
					}
					if (i==2) {
						assertNull(surpriseFields);
					}
				}
				first = false;
			}
			umwbfum.invoke(um);
			umqucm.invoke(um);
			//waitUntilGone(wfum);
			if (i==0) {
				final Map<String,Map<org.kered.dko.Field<?>,Long>> qc =
						(Map<String,Map<org.kered.dko.Field<?>,Long>>)umqcf.get(null);
				final Map<org.kered.dko.Field<?>,Long> used = qc.get(sth);
				used.clear();
			}
		}
	}

//	private void waitUntilGone(WeakReference<?> wfum)
//			throws InterruptedException {
//		Object um = wfum.get();
//		while (um != null) {
//			System.err.println(um);
//			um = null;
//			System.gc();
//			Thread.currentThread().sleep(500);
//			um = wfum.get();
//		}
//	}

}
