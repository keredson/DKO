package org.kered.dko.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

import javax.sql.DataSource;


/**
 * This class wraps other {@code javax.sql.DataSource} instances.  It usually passes
 * through calls to the primary, but if {@code getMirroredConnection()}
 * is called a random mirror is used instead.
 *
 * @author Derek Anderson
 */
public class MirroredDataSource implements DataSource {

	private final DataSource primary;
	private DataSource[] mirrors;
	private static final Logger log = Logger.getLogger("org.kered.dko.datasource.MirroredDataSource");

	/**
     * This class usually passes
     * through calls to the primary, but if {@code getMirroredConnection()}
     * is called a random mirror is used instead.
	 * @param primary
	 * @param mirrors
	 */
	public MirroredDataSource(final DataSource primary, final DataSource... mirrors) {
		this.primary = primary;
		if (mirrors == null) this.mirrors = new DataSource[0];
		else this.mirrors = mirrors;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return primary.getLogWriter();
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return primary.getLoginTimeout();
	}

	@Override
	public void setLogWriter(final PrintWriter arg0) throws SQLException {
		primary.setLogWriter(arg0);
	}

	@Override
	public void setLoginTimeout(final int arg0) throws SQLException {
		primary.setLoginTimeout(arg0);
	}

	@Override
	public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
		if (primary.getClass().equals(arg0)) return true;
		return primary.isWrapperFor(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(final Class<T> arg0) throws SQLException {
		if (primary.getClass().equals(arg0)) return (T) primary;
		return primary.unwrap(arg0);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return primary.getConnection();
	}

	/**
	 * Returns a connection from a randomly selected mirror. &nbsp;
	 * Feel free to override this method to implement other
	 * load balancing stratigies in your own code.
	 * @return
	 * @throws SQLException
	 */
	public Connection getMirroredConnection() throws SQLException {
		// for now we randomly select the mirror
		// TODO: implement other strategies
		if (mirrors.length == 0) return getConnection();
		final Random random = new Random();
		final int i = random.nextInt(mirrors.length);
		for (int j=0; j<mirrors.length; ++j) {
			final DataSource mirror = mirrors[(i+j)%mirrors.length];
			try {
				return mirror.getConnection();
			} catch (final SQLException e) {
				// db down - try another
				log.warning("could not connect to "+ mirror +": "+ e.toString());
			}
		}
		try {
			return primary.getConnection();
		} catch (final SQLException e) {
			log.warning("could not connect to "+ primary +": "+ e.toString());
		}
		throw new SQLException("could not connect to any mirror: "
				+ Arrays.asList(mirrors) +" or the primary: "+ primary);
	}

	@Override
	public Connection getConnection(final String arg0, final String arg1)
			throws SQLException {
		return primary.getConnection(arg0, arg1);
	}

	@Override
	public String toString() {
		return "[MirroredDataSource primary="+ primary +" mirrors="
				+ Util.join(",", mirrors) +"]";
	}

	public DataSource getPrimaryDataSource() {
		return primary;
	}

	//@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}

}
