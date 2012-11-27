package org.kered.dko.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * This DataSource wraps another while enforcing a single threaded access model.
 * A new connection cannot be opened until the previously granted connection has been closed.
 * New calls to {@code getConnection()} will block until a timeout has been reached (at
 * which point a null is returned).
 * This is highly recommended as a wrapper around SQLite3 backed DataSources, as it cannot
 * handle multiple simultaneous connections.
 *
 * @author Derek Anderson
 */
public class SingleThreadedDataSource implements MatryoshkaDataSource {

	private final Lock lock = new ReentrantLock();
	private final DataSource ds;
	private final long timeout;
	private static final Logger log = Logger.getLogger("org.kered.dko.datasource.SingleThreadedDataSource");

	/**
	 * @param ds
	 * @param timeout (in millis)
	 */
	public SingleThreadedDataSource(final DataSource ds, final long timeout) {
		this.ds = ds;
		this.timeout = timeout;
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		try {
			if (lock.tryLock(timeout, TimeUnit.MILLISECONDS)) {
				final Connection conn = ds.getConnection();
				return new UnClosableConnection(conn, new UnClosableConnection.CloseListener() {
					@Override
					public void wasClosed(final UnClosableConnection c) {
						try {
							conn.close();
						} catch (final SQLException e) {
							e.printStackTrace();
						}
						lock.unlock();
					}
				});
			}
			log.warning("unable to aquire connection lock after "+ timeout +" seconds");
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Connection getConnection(final String username, final String password)
			throws SQLException {
		try {
			if (lock.tryLock(timeout, TimeUnit.MILLISECONDS)) {
				final Connection conn = ds.getConnection(username, password);
				return new UnClosableConnection(conn, new UnClosableConnection.CloseListener() {
					@Override
					public void wasClosed(final UnClosableConnection c) {
						try {
							conn.close();
						} catch (final SQLException e) {
							e.printStackTrace();
						}
						lock.unlock();
					}
				});
			}
			log.warning("unable to aquire connection lock after "+ timeout +" seconds");
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return ds.getLogWriter();
	}

	@Override
	public void setLogWriter(final PrintWriter out) throws SQLException {
		ds.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(final int seconds) throws SQLException {
		ds.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return ds.getLoginTimeout();
	}

	@Override
	public <T> T unwrap(final Class<T> iface) throws SQLException {
		return ds.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(final Class<?> iface) throws SQLException {
		return ds.isWrapperFor(iface);
	}

	@Override
	public DataSource getPrimaryUnderlying() {
		return ds;
	}

	@Override
	public Collection<DataSource> getAllUnderlying() {
		final Collection<DataSource> ret = new ArrayList<DataSource>(1);
		ret.add(ds);
		return ret;
	}

}
