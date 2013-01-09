package test.db;

import org.kered.dko.Context;
import org.kered.dko.datasource.ConnectionCountingDataSource;
import org.kered.dko.datasource.JDBCDriverDataSource;

public class TestMSSQLDB extends SharedDBTests {

	protected void setUp() throws Exception {
		super.setUp();
		ds = new JDBCDriverDataSource("jdbc:sqlserver://XXXXXXXX:1433", "username", "password");
		Context vmContext = Context.getVMContext();
		vmContext.setDataSource(ds).setAutoUndo(false);
		ccds = new ConnectionCountingDataSource(ds);
		SELECT_COUNT_1_FROM_INVENTORY = "select count(1) from nosco_test_jpetstore..inventory";
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
