package sakila;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


public class Util {
	
	private static DataSource ds;

	public synchronized static DataSource getDS() {
		if (ds==null) {
			MysqlDataSource tmpDS = new MysqlDataSource();
			tmpDS.setURL("jdbc:mysql://localhost/sakila");
			tmpDS.setUser("root");
			tmpDS.setPassword("");
			ds = tmpDS;
		}
		return ds;
	}


}
