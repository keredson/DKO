package test.db;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

import org.hsqldb.jdbc.JDBCDataSource;
import org.nosco.util.Misc;

public class TestHSQLDB extends SharedDBTests {

	String schema = Misc.readFileToString(new File("deps/jpetstore/hsql/jpetstore-hsqldb-schema.sql"));
	String data = Misc.readFileToString(new File("deps/jpetstore/hsql/jpetstore-hsqldb-dataload.sql"));

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		File tmp = new File("bin/jpetstore.jsqldb");
		JDBCDataSource ds = new JDBCDataSource();
		//ds.setUrl("jdbc:hsqldb:file:"+tmp.getAbsolutePath()+";shutdown=true");
		ds.setDatabase("jdbc:hsqldb:mem:tmp");
		ds.setUser("sa");
		Connection conn = ds.getConnection();
		Statement stmt = conn.createStatement();
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
		System.err.println("done!!!");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
