package org.kered.dko.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * Fakes a {@code DataSource} to only ever use one connection. &nbsp;
 * Intercepts all calls to close the connection.
 * <p>
 * User is responsible for eventually closing the original connection.
 *
 * @author dander
 */
public class SingleConnectionDataSource implements DataSource {

	private final Connection conn;

	public SingleConnectionDataSource(final Connection conn) {
		this.conn = new UnClosableConnection(conn);
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogWriter(final PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(final int seconds) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Connection getConnection() throws SQLException {
		return conn;
	}

	@Override
	public Connection getConnection(final String username, final String password)
			throws SQLException {
		return conn;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}

}
