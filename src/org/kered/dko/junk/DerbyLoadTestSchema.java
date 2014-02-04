package org.kered.dko.junk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyLoadTestSchema {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		File schemaFile = new File("deps/jpetstore/derby/jpetstore-derby-schema.sql");
		String schema = load(schemaFile);
		File dataloadFile = new File("deps/jpetstore/derby/jpetstore-derby-dataload.sql");
		String dataload = load(dataloadFile);
		
		Class.forName("org.apache.derby.jdbc.AutoloadedDriver").newInstance();
		Connection conn = DriverManager.getConnection("jdbc:derby:bin/testderby;create=true");
		
		execute(schema, conn);
		execute(dataload, conn);
		
		conn.close();
		System.out.println("success - loaded jpetstore schema");
	}

	private static void execute(String sql, Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		String[] commands = sql.split(";\n");
		for (String command : commands) {
			command = command.trim();
			if ("".equals(command)) continue;
			try {
				stmt.execute(command);
			} catch(SQLException e) {
				if (command.toLowerCase().startsWith("drop ")) {
					// ignore failed drop commands
				} else {
					System.out.println(command);
					throw e;
				}
			}
		}
		stmt.close();
	}

	private static String load(File file) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new FileReader(file));
		for (String line; (line=br.readLine())!=null; ) {
			sb.append(line).append('\n');
		}
		br.close();
		return sb.toString();
	}

}
