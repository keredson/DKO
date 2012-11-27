package org.kered.dko.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * Fakes a {@code DataSource} to only ever use one (continuously open) connection. &nbsp;
 * Intercepts all calls to close the connection.
 * <p>
 * User is responsible for eventually closing the original connection.
 *
 * @author dander
 */
public class SingleConnectionDataSource implements DataSource {

	private final Connection conn;
	private final Lock lock = new ReentrantLock();
	private final int timeout = 10;
	private static final Logger log = Logger.getLogger("org.kered.dko.datasource.SingleConnectionDataSource");

	public SingleConnectionDataSource(final Connection conn) {
		this.conn = new UnClosableConnection(conn, new UnClosableConnection.CloseListener() {
			@Override
			public void wasClosed(final UnClosableConnection c) {
				lock.unlock();
			}
		});
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
		try {
			if (lock.tryLock(timeout, TimeUnit.SECONDS)) return conn;
			log.warning("unable to aquire connection lock after "+ timeout +" seconds");
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Connection getConnection(final String username, final String password) throws SQLException {
		try {
			if (lock.tryLock(timeout, TimeUnit.SECONDS)) return conn;
			log.warning("unable to aquire connection lock after "+ timeout +" seconds");
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	//@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}

}
