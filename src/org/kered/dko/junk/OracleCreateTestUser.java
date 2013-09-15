package org.kered.dko.junk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleCreateTestUser {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Class.forName("oracle.jdbc.OracleDriver");
		Connection conn = DriverManager.getConnection("jdbc:oracle:thin:system/oracle@localhost:1521:orcl");
		Statement stmt = conn.createStatement();
		try {
			stmt.execute("CREATE USER nosco_test_jpetstore IDENTIFIED BY password");
		} catch (SQLException e) {
			if (e.getMessage().contains("conflicts with another user or role name")) {
				// that's ok - user already exists
			} else throw e;
		}

		stmt.execute("GRANT CONNECT TO nosco_test_jpetstore");
		stmt.execute("GRANT CREATE TABLE TO nosco_test_jpetstore");
		stmt.execute("GRANT CREATE SEQUENCE TO nosco_test_jpetstore");
		stmt.execute("GRANT UNLIMITED TABLESPACE TO nosco_test_jpetstore");

		conn.close();
		System.out.println("success");
//		ResultSet rs = stmt.executeQuery("SELECT owner, table_name FROM dba_tables");
//		while (rs.next()) {
//			System.out.println(rs.getString(1));
//		}
	}

}
