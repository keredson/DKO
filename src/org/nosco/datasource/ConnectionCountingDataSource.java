package org.nosco.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * This class wraps a {@code DataSource} and counts the connections it returns.
 * Mostly used for testing.
 *
 * @author Derek Anderson
 */
public class ConnectionCountingDataSource implements DataSource {

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private int count = 0;
	private final DataSource ds;

	public ConnectionCountingDataSource(DataSource ds) {
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
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		ds.setLogWriter(arg0);
	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		ds.setLoginTimeout(arg0);
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		if (ds.getClass().equals(arg0)) return true;
		return ds.isWrapperFor(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		if (ds.getClass().equals(arg0)) return (T) ds;
		return ds.unwrap(arg0);
	}

	@Override
	public Connection getConnection() throws SQLException {
		++count;
		return ds.getConnection();
	}

	@Override
	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		++count;
		return ds.getConnection(arg0, arg1);
	}

	public DataSource getUnderlyingDataSource() {
		return ds;
	}

	@Override
	public String toString() {
		return "[ConnectionCountingDataSource count:"+ count +" for:"+ ds +"]";
	}

}
