package test;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Something {
	
	static MysqlDataSource ds = null;
	
	static {
		ds = new MysqlConnectionPoolDataSource();
		ds.setUser("root");
		ds.setPassword("");
		ds.setServerName("localhost");
		ds.setPort(3306);
		ds.setDatabaseName("mysql");
	}
	
	public static DataSource callme() {
		return ds;
	}
}
