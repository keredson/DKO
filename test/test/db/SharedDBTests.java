package test.db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.nosco.Context;
import org.nosco.Context.Undoer;
import org.nosco.Query;
import org.nosco.datasource.ConnectionCountingDataSource;
import org.nosco.unittest.public_.Category;
import org.nosco.unittest.public_.Item;
import org.nosco.unittest.public_.Product;
import org.nosco.unittest.public_.Supplier;


public class SharedDBTests extends TestCase {

	DataSource ds = null;

	public void test01() throws SQLException {
		Connection conn = ds.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select count(1) from inventory");
		rs.next();
		int count = rs.getInt(1);
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
		for (Item i : Item.ALL.use(ds).top(10)) ++count;
		assertTrue(count == 10);
	}

	public void testWithAndCross() throws SQLException {
		int count = 0;
		int countp = Product.ALL.use(ds).count();
		int countc = Category.ALL.use(ds).count();
		Query<Product> q = Product.ALL.use(ds).cross(Category.class);
		for (Product p : q) {
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
		int itemCount = Item.ALL.use(ds).count();
		int itemCount2 = Item.ALL.use(ds).with(Item.FK_SUPPLIER).count();
		// counts should be the same w/ and w/o the FK reference
		assertTrue(itemCount == itemCount2);
		int count = 0;
		ConnectionCountingDataSource ccds = new ConnectionCountingDataSource(ds);
		boolean sawEST29 = false;
		for (Item item : Item.ALL.use(ccds).with(Item.FK_SUPPLIER)) {
			count++;
			if (!"EST-29".equals(item.getItemid())) {
				assertNotNull(item.getSupplierFK());
			} else {
				sawEST29 = true;
			}
		}
		assertTrue(sawEST29);
		assertTrue(itemCount == count);
		// make sure the calls to the FK object didn't gen new queries
		assertTrue(ccds.getCount() == 1);
	}

	public void testFKNoWith() throws SQLException {
		ConnectionCountingDataSource ccds = new ConnectionCountingDataSource(ds);
		Undoer x = Context.getVMContext().setDataSource(ccds);
		for (Item item : Item.ALL) { //.with(Item.FK_SUPPLIER)
			// this should create O(n) queries because we didn't specify with() above
			item.getSupplierFK();
		}
		assertTrue(ccds.getCount() > 1);
	}

	@SuppressWarnings("unused")
	public void testFKReverse() throws SQLException {
		System.err.println("testFKReverse start");
		Undoer x = Context.getVMContext().setDataSource(ds);
		int count1 = Supplier.ALL.count();
		int count2 = Supplier.ALL.with(Item.FK_SUPPLIER).count();
		assertTrue(count1 == count2);
		ConnectionCountingDataSource ccds = new ConnectionCountingDataSource(ds);
		Undoer y = Context.getVMContext().setDataSource(ccds);
		for (Supplier s : Supplier.ALL.with(Item.FK_SUPPLIER)) {
			System.err.println(s);
			for (Item i : s.getItemSet()) {
				System.err.println("\t"+i);
			}
			if (s.getSuppid() == 1) assertTrue(s.getItemSet().count() > 0);
			else assertTrue(s.getItemSet().count() == 0);
		}
		assertTrue(ccds.getCount() == 2);
		System.err.println("testFKReverse done");
	}

	public void testFKTwoLevels() throws SQLException {
		ConnectionCountingDataSource ccds = new ConnectionCountingDataSource(ds);
		Undoer u = Context.getVMContext().setDataSource(ccds);
		for (Item i : Item.ALL.with(Item.FK_PRODUCTID_PRODUCT, Product.FK_CATEGORY)) {
			assertNotNull(i);
			assertNotNull(i.getProductidFK());
			assertNotNull(i.getProductidFK().getCategoryFK());
			System.err.println(i);
		}
		assertTrue(ccds.getCount() == 1);
	}

}
