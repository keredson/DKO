package test.db;

import static org.kered.dko.SQLFunction.COUNT;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.kered.dko.Context;
import org.kered.dko.datasource.ConnectionCountingDataSource;
import org.kered.dko.datasource.JDBCDriverDataSource;
import org.kered.dko.unittest.nosco_test_jpetstore.Item;
import org.kered.dko.unittest.nosco_test_jpetstore.Supplier;

public class TestDerbyDB extends SharedDBTests {

	static String schema = Util.readFileToString(new File("deps/jpetstore/derby/jpetstore-derby-schema.sql"));
	static String data = Util.readFileToString(new File("deps/jpetstore/derby/jpetstore-derby-dataload.sql"));
	private static DataSource staticDS = null;
	
	static {
//		try {
			final DataSource ds = new JDBCDriverDataSource("jdbc:derby:db1;create=true");
//			Connection conn = ds.getConnection();
//			final Statement stmt = conn.createStatement();
//			for (String sql : schema.split(";")) {
//				sql = sql.trim();
//				if (sql.length() == 0) continue;
//				//System.err.println(sql);
//				stmt.execute(sql);
//			}
//			conn.commit();
//			for (String sql : data.split(";")) {
//				sql = sql.trim();
//				if (sql.length() == 0) continue;
//				System.err.println(sql);
//				stmt.execute(sql);
//			}
//			stmt.close();
//			conn.commit();
//			conn.close();
			staticDS = ds;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	protected PassThruDS createPassThruDS() {
		PassThruDS passThruDS = new PassThruDS(ds);
		Context context = Context.getVMContext();
		context.overrideDatabaseName(passThruDS, "nosco_test_jpetstore", "").setAutoUndo(false);
		return passThruDS;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ds = staticDS;
		Context context = Context.getVMContext();
		context.setDataSource(ds).setAutoUndo(false);
		context.overrideDatabaseName(ds, "nosco_test_jpetstore", "").setAutoUndo(false);
		ccds = new ConnectionCountingDataSource(ds);
		context.overrideDatabaseName(ccds, "nosco_test_jpetstore", "").setAutoUndo(false);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGroupBy() throws SQLException {
		printTestName();
		List<Item> items = Item.ALL.distinct().onlyFields(Item.SUPPLIER).groupBy(Item.SUPPLIER).asList();
		assertEquals(Supplier.ALL.count(), items.size());
	}

	public void testGroupByCount() throws SQLException {
		printTestName();
		for (Item item : Item.ALL.onlyFields(Item.SUPPLIER).alsoSelect(COUNT(1)).groupBy(Item.SUPPLIER)) {
			System.err.println(item + " ::: "+ item.get(COUNT(1)));
		}
	}
	
	public void testGroupByCountColumn() throws SQLException {
		printTestName();
		for (Item item : Item.ALL.onlyFields(Item.SUPPLIER).alsoSelect(COUNT(Item.ITEMID)).groupBy(Item.SUPPLIER)) {
			System.err.println(item + " ::: "+ item.get(COUNT(Item.ITEMID)));
		}
	}

	public void testGroupByCountStar() throws SQLException {
		printTestName();
		for (Item item : Item.ALL.onlyFields(Item.SUPPLIER).alsoSelect(COUNT("*")).groupBy(Item.SUPPLIER)) {
			System.err.println(item + " ::: "+ item.get(COUNT("*")));
		}
	}

}
