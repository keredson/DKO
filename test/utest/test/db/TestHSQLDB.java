package test.db;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import org.hsqldb.jdbc.JDBCDataSource;
import org.kered.dko.Context;
import org.kered.dko.datasource.ConnectionCountingDataSource;

public class TestHSQLDB extends SharedDBTests {

	String schema = Util.readFileToString(new File("deps/jpetstore/hsql/jpetstore-hsqldb-schema.sql"));
	String data = Util.readFileToString(new File("deps/jpetstore/hsql/jpetstore-hsqldb-dataload.sql"));

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		final JDBCDataSource ds = new JDBCDataSource();
		ds.setDatabase("jdbc:hsqldb:mem:tmp");
		ds.setUser("sa");
		final Connection conn = ds.getConnection();
		final Statement stmt = conn.createStatement();
		for (String sql : schema.split(";")) {
			sql = sql.trim();
			if (sql.length() == 0) continue;
			//System.err.println(sql);
			stmt.execute(sql);
		}
		conn.commit();
		for (String sql : data.split(";")) {
			sql = sql.trim();
			if (sql.length() == 0) continue;
			//System.err.println(sql);
			stmt.execute(sql);
		}
		stmt.close();
		conn.commit();
		conn.close();
		this.ds = ds;
		Context.getVMContext().setDataSource(ds).setAutoUndo(false);
		ccds = new ConnectionCountingDataSource(ds);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	// mysql doesn't support outer joins
//	public void testOuterJoin() throws SQLException {
//		final long c1 = Item.ALL.count();
//		final Query<Join<Supplier, Item>> q = Supplier.ALL.outerJoin(Item.class, Item.SUPPLIER.eq(Supplier.SUPPID));
//		//assertEquals(c1,  q.count());
//		for (final Join<Supplier, Item> x : q.top(64)) {
//			System.err.println(x);
//		}
//	}

}
