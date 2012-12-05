package org.kered.dko.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
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
	private Connection conn = null;
	private static final Logger log = Logger.getLogger("org.kered.dko.datasource.SingleThreadedDataSource");

	/**
	 * @param ds
	 * @param timeout (in millis)
	 */
	public SingleThreadedDataSource(final DataSource ds, final long timeout, boolean reuse) {
		this.ds = ds;
		this.timeout = timeout;
	}

	@Override
	public synchronized Connection getConnection() throws SQLException {
		long stopBy = System.currentTimeMillis() + timeout;
		try {
			while (System.currentTimeMillis() < stopBy) {
				if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
					if (!connOk(conn)) conn = ds.getConnection();
					return new UnClosableConnection(conn, new UnClosableConnection.CloseListener() {
						@Override
						public void wasClosed(final UnClosableConnection c) {
							lock.unlock();
						}
					});
				}
			}
			throw new RuntimeException("unable to aquire connection lock after "+ timeout +"ms");
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean connOk(Connection conn) {
		if (conn==null) return false;
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select 1");
			rs.next();
			rs.getInt(1);
			stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.close();
			} catch (SQLException e1) {
				e.printStackTrace();
			}
			conn = null;
			return false;
		}
	}

	@Override
	public Connection getConnection(final String username, final String password)
			throws SQLException {
		long stopBy = System.currentTimeMillis() + timeout;
		try {
			while (System.currentTimeMillis() < stopBy) {
				if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
					if (!connOk(conn)) conn = ds.getConnection(username, password);
					return new UnClosableConnection(conn, new UnClosableConnection.CloseListener() {
						@Override
						public void wasClosed(final UnClosableConnection c) {
							lock.unlock();
						}
					});
				}
			}
			throw new RuntimeException("unable to aquire connection lock after "+ timeout +"ms");
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
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

	//@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}

}
