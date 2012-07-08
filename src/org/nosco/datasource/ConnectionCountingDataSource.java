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

	public void setCount(final int count) {
		this.count = count;
	}

	private int count = 0;
	private final DataSource ds;

	public ConnectionCountingDataSource(final DataSource ds) {
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
	public void setLogWriter(final PrintWriter arg0) throws SQLException {
		ds.setLogWriter(arg0);
	}

	@Override
	public void setLoginTimeout(final int arg0) throws SQLException {
		ds.setLoginTimeout(arg0);
	}

	@Override
	public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
		if (ds.getClass().equals(arg0)) return true;
		return ds.isWrapperFor(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(final Class<T> arg0) throws SQLException {
		if (ds.getClass().equals(arg0)) return (T) ds;
		return ds.unwrap(arg0);
	}

	@Override
	public Connection getConnection() throws SQLException {
		++count;
		return ds.getConnection();
	}

	@Override
	public Connection getConnection(final String arg0, final String arg1)
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
