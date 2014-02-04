package test.db;

import static org.kered.dko.SQLFunction.COUNT;

import java.sql.SQLException;
import java.util.List;

import org.kered.dko.Context;
import org.kered.dko.datasource.ConnectionCountingDataSource;
import org.kered.dko.datasource.JDBCDriverDataSource;
import org.kered.dko.unittest.nosco_test_jpetstore.Item;
import org.kered.dko.unittest.nosco_test_jpetstore.Supplier;

public class TestDerbyDB extends SharedDBTests {

	protected PassThruDS createPassThruDS() {
		PassThruDS passThruDS = new PassThruDS(ds);
		Context context = Context.getVMContext();
//		context.overrideDatabaseName(passThruDS, "nosco_test_jpetstore", "").setAutoUndo(false);
		return passThruDS;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ds = new JDBCDriverDataSource("jdbc:derby:bin/testderby");
		Context context = Context.getVMContext();
		context.setDataSource(ds).setAutoUndo(false);
//		context.overrideDatabaseName(ds, "nosco_test_jpetstore", "").setAutoUndo(false);
		ccds = new ConnectionCountingDataSource(ds);
//		context.overrideDatabaseName(ccds, "nosco_test_jpetstore", "").setAutoUndo(false);
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

	public void testExplain() throws SQLException {
		printTestName();
		System.err.println("Derby doesn't support EXPLAIN.");
	}

}
