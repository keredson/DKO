package org.nosco;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.nosco.util.Misc;

/**
 * This class wraps other {@code javax.sql.DataSource} instances.  It usually passes
 * through calls to the primary, but if {@code getMirroredConnection()}
 * is called a random mirror is used instead.
 *
 * @author Derek Anderson
 */
public class MirroredDataSource implements DataSource {

	private DataSource primary;
	private DataSource[] mirrors;

	/**
     * This class usually passes
     * through calls to the primary, but if {@code getMirroredConnection()}
     * is called a random mirror is used instead.
	 * @param primary
	 * @param mirrors
	 */
	public MirroredDataSource(DataSource primary, DataSource... mirrors) {
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
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		primary.setLogWriter(arg0);
	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		primary.setLoginTimeout(arg0);
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		if (primary.getClass().equals(arg0)) return true;
		return primary.isWrapperFor(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
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
		// cast will never overflow because will always be <= mirrors.length
		int i = (int) Math.round(Math.random() * mirrors.length);
		return mirrors[i].getConnection();
	}

	@Override
	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		return primary.getConnection(arg0, arg1);
	}

	@Override
	public String toString() {
		return "[MirroredDataSource primary="+ primary +" mirrors="
				+ Misc.join(",", mirrors) +"]";
	}

}
