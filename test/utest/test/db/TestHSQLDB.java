package test.db;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import org.hsqldb.jdbc.JDBCDataSource;
import org.nosco.Context;
import org.nosco.datasource.ConnectionCountingDataSource;

public class TestHSQLDB extends SharedDBTests {

	static String readFileToString(final File file) {
		try {
			final FileReader reader = new FileReader(file);
			final StringBuffer sb = new StringBuffer();
			int chars;
			final char[] buf = new char[1024];
			while ((chars = reader.read(buf)) > 0) {
				sb.append(buf, 0, chars);
			}
			reader.close();
			return sb.toString();
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	String schema = readFileToString(new File("deps/jpetstore/hsql/jpetstore-hsqldb-schema.sql"));
	String data = readFileToString(new File("deps/jpetstore/hsql/jpetstore-hsqldb-dataload.sql"));

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

}
