package test.db;

import org.nosco.Context;
import org.nosco.datasource.ConnectionCountingDataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TestMySQLDB extends SharedDBTests {

	protected void setUp() throws Exception {
		super.setUp();
		MysqlDataSource mysqlDS = new MysqlDataSource();
		mysqlDS.setUser("root");
		mysqlDS.setDatabaseName("nosco_test_jpetstore");
		ds = mysqlDS;
		Context vmContext = Context.getVMContext();
		vmContext.setDataSource(ds).setAutoUndo(false);
		//vmContext.overrideSchema(ds, "public", "nosco_test_jpetstore").setAutoUndo(false);
		ccds = new ConnectionCountingDataSource(ds);
		//vmContext.overrideSchema(ccds, "public", "nosco_test_jpetstore").setAutoUndo(false);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
