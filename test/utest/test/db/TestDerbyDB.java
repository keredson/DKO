package test.db;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.kered.dko.Context;
import org.kered.dko.datasource.ConnectionCountingDataSource;
import org.kered.dko.datasource.JDBCDriverDataSource;

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

}
