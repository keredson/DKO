package test.db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.nosco.Constants.CALENDAR;
import org.nosco.Context;
import org.nosco.Context.Undoer;
import org.nosco.Diff;
import org.nosco.Diff.RowChange;
import static org.nosco.Function.*;
import org.nosco.Query;
import org.nosco.datasource.ConnectionCountingDataSource;
import org.nosco.unittest.nosco_test_jpetstore.Category;
import org.nosco.unittest.nosco_test_jpetstore.Item;
import org.nosco.unittest.nosco_test_jpetstore.Orderstatus;
import org.nosco.unittest.nosco_test_jpetstore.Product;
import org.nosco.unittest.nosco_test_jpetstore.Supplier;


public class SharedDBTests extends TestCase {

	DataSource ds = null;
	ConnectionCountingDataSource ccds = null;

	public void test01() throws SQLException {
		final Connection conn = ds.getConnection();
		final Statement stmt = conn.createStatement();
		final ResultSet rs = stmt.executeQuery("select count(1) from inventory");
		rs.next();
		final int count = rs.getInt(1);
		//System.err.println("count: "+ count);
		rs.close();
		stmt.close();
		conn.close();
		assertTrue(count > 0);
	}

	public void test02() throws SQLException {
		assertTrue(Item.ALL.use(ds).count() > 0);
	}

	public void test04() throws SQLException {
		int count = 0;
		for (final Item i : Item.ALL.use(ds).top(10)) ++count;
		assertEquals(10, count);
	}

	public void testWithAndCross() throws SQLException {
		int count = 0;
		final int countp = Product.ALL.use(ds).count();
		final int countc = Category.ALL.use(ds).count();
		final Query<Product> q = Product.ALL.use(ds).cross(Category.class);
		for (final Product p : q) {
			//assertNotNull(p);
			//assertNotNull(p.getProductidFK());
			++count;
		}
		System.err.println("countp "+ countp);
		System.err.println("countc "+ countc);
		System.err.println("testWithAndCross "+ count);
		System.err.println("testWithAndCross "+ Category.ALL.use(ds).count());
		//assertTrue(count == countp * countc);
	}

	public void testFK1() throws SQLException {
		final int itemCount = Item.ALL.use(ds).count();
		final int itemCount2 = Item.ALL.use(ds).with(Item.FK_SUPPLIER).count();
		// counts should be the same w/ and w/o the FK reference
		assertTrue(itemCount == itemCount2);
		int count = 0;
		boolean sawEST29 = false;
		for (final Item item : Item.ALL.use(ccds).with(Item.FK_SUPPLIER)) {
			count++;
			if (!"EST-29".equals(item.getItemid())) {
				assertNotNull(item.getSupplierFK());
			} else {
				sawEST29 = true;
			}
		}
		assertTrue(sawEST29);
		assertEquals(itemCount, count);
		// make sure the calls to the FK object didn't gen new queries
		assertEquals(1, ccds.getCount());
	}

	public void testFKNoWith() throws SQLException {
		final Undoer x = Context.getVMContext().setDataSource(ccds);
		for (final Item item : Item.ALL) { //.with(Item.FK_SUPPLIER)
			// this should create O(n) queries because we didn't specify with() above
			item.getSupplierFK();
		}
		assertTrue(ccds.getCount() > 1);
	}

	@SuppressWarnings("unused")
	public void testFKReverseCounts() throws SQLException {
		System.err.println("testFKReverseCounts start");
		final Undoer x = Context.getVMContext().setDataSource(ds);
		final int count1 = Supplier.ALL.count();
		final int count2 = Supplier.ALL.with(Item.FK_SUPPLIER).count();
		int count3 = 0;
		int supplierCount = 0;
		for (final Supplier s : Supplier.ALL) {
			supplierCount += 1;
			final int itemCount = s.getItemSet().count();
			count3 += Math.max(1, itemCount);
		}
		assertEquals(count2, count3);
		assertEquals(count1, supplierCount);
	}

	@SuppressWarnings("unused")
	public void testFKReverse() throws SQLException {
		System.err.println("testFKReverse start");
		final int count1 = Supplier.ALL.count();
		int count2 = 0;
		final Undoer y = Context.getVMContext().setDataSource(ccds);
		for (final Supplier s : Supplier.ALL.with(Item.FK_SUPPLIER)) {
			count2++;
			System.err.println(s);
			for (final Item i : s.getItemSet()) {
				System.err.println("\t"+i);
			}
			if (s.getSuppid() == 1) assertTrue(s.getItemSet().count() > 0);
			else assertEquals(0, s.getItemSet().count());
		}
		assertEquals(count1, count2);
		assertEquals(1, ccds.getCount());
		System.err.println("testFKReverse done");
	}

	@SuppressWarnings("unused")
	public void testFKReverseQueryReplicationBug() throws SQLException {
		final Query<Supplier> q = Supplier.ALL.with(Item.FK_SUPPLIER);
		q.count();
		for (final Supplier s : q) {
		}
	}

	public void testFKTwoLevels() throws SQLException {
		final Undoer u = Context.getVMContext().setDataSource(ccds);
		for (final Item i : Item.ALL.with(Item.FK_PRODUCTID_PRODUCT, Product.FK_CATEGORY)) {
			assertNotNull(i);
			assertNotNull(i.getProductidFK());
			assertNotNull(i.getProductidFK().getCategoryFK());
			System.err.println(i);
		}
		assertEquals(1, ccds.getCount());
	}

	public void testSimpleDiff() throws SQLException {
		final List<Item> items = Item.ALL.asList();
		assertTrue(items.size() > 0);
		items.get(0).setAttr4("something");
		items.add(new Item());
		items.add(new Item());
		int updates = 0;
		int adds = 0;
		int count = 0;
		for (final RowChange<Item> diff : Diff.diff(items)) {
			count += 1;
			if (diff.isAdd()) adds += 1;
			if (diff.isUpdate()) updates += 1;
		}
		assertEquals(3, count);
		assertEquals(1, updates);
		assertEquals(2, adds);
	}

	public void testDateAdd() throws SQLException {
		Orderstatus.ALL.where(Orderstatus.TIMESTAMP.lt(
				DATEADD(Orderstatus.TIMESTAMP, 1, CALENDAR.DAY)))
				.asList();
	}

	public void testRecommendation() throws InterruptedException {
		for (final Item i : Item.ALL) {
			i.getProductidFK();
		}
		// note: this should raise a logging WARNING!
		System.gc();
		Thread.sleep(1000);
		// this doesn't work for now since it's the last test
	}

	public void testExists() throws InterruptedException {
		for (final Item i : Item.ALL.where(Item.ALL.exists())) {}
	}

	public void testConcat() throws InterruptedException, SQLException {
		for (final Item i : Item.ALL) {

		}
		assertEquals(1, Item.ALL.where(CONCAT(Item.ITEMID, "!").eq("EST-20!")).count());
	}

}
