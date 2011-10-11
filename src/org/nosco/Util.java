package org.nosco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map.Entry;

public class Util {

	public static Connection conn = null;

	static {
        String userName = "root";
        String password = "";
        String url = "jdbc:mysql://localhost/?zeroDateTimeBehavior=convertToNull";
        try {
			conn = DriverManager.getConnection (url, userName, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(conn==null) {
			System.exit(1);
		}
        System.out.println("Database connection established");
        for (Entry<Object, Object> entry : System.getProperties().entrySet()) {
        //	System.out.println(entry.getKey() +": "+ entry.getValue());
        }

	}

}
