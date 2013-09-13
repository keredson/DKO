package test.db;

import org.kered.dko.Context;
import org.kered.dko.datasource.ConnectionCountingDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TestPostgreSQLDB extends SharedDBTests {

	protected void setUp() throws Exception {
		super.setUp();
		PGSimpleDataSource postgresDS = new PGSimpleDataSource();
		postgresDS.setUser("postgres");
		postgresDS.setPassword("");
		postgresDS.setDatabaseName("nosco_test_jpetstore");
		ds = postgresDS;
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
