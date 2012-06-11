package test.db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.nosco.unittest.public_.Item;
import org.nosco.unittest.public_.Product;


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

	public void test03() throws SQLException {
		int count = 0;
		for (Product p : Product.ALL.use(ds).with(Product.FK_PRODUCTID_ITEM)) {
			assertNotNull(p);
			assertNotNull(p.getProductidFK());
			++count;
		}
		assertTrue(count > 0);
	}

	public void test04() throws SQLException {
		int count = 0;
		for (Item i : Item.ALL.use(ds).top(10)) ++count;
		assertTrue(count == 10);
	}


}
