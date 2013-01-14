package test.db;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

class PassThruDS implements DataSource {
	
	private DataSource ds;

	PassThruDS(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return ds.getLogWriter();
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return ds.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return ds.getParentLogger();
	}

	@Override
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		ds.setLogWriter(arg0);
	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		ds.setLoginTimeout(arg0);
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return ds.isWrapperFor(arg0);
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return ds.unwrap(arg0);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	@Override
	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		return ds.getConnection(arg0, arg1);
	}

}
