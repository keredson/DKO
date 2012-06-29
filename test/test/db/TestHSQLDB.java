package test.db;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import org.hsqldb.jdbc.JDBCDataSource;

public class TestHSQLDB extends SharedDBTests {

	static String readFileToString(File file) {
		try {
			FileReader reader = new FileReader(file);
			StringBuffer sb = new StringBuffer();
			int chars;
			char[] buf = new char[1024];
			while ((chars = reader.read(buf)) > 0) {
				sb.append(buf, 0, chars);
			}
			reader.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	String schema = readFileToString(new File("deps/jpetstore/hsql/jpetstore-hsqldb-schema.sql"));
	String data = readFileToString(new File("deps/jpetstore/hsql/jpetstore-hsqldb-dataload.sql"));

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		JDBCDataSource ds = new JDBCDataSource();
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
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
