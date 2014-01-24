package org.kered.dko.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class CheapConnectionPoolingDataSource implements DataSource {
	
	private final DataSource src;
	private Stack<Connection> pool = new Stack<Connection>();

	public CheapConnectionPoolingDataSource(DataSource src) {
		this.src = src;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return src.getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		src.setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		src.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return src.getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return src.getParentLogger();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return src.unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return src.isWrapperFor(iface);
	}

	@Override
	public Connection getConnection() throws SQLException {
		while (true) {
			try {
				return pool.pop();
			} catch (EmptyStackException e) {
				Connection conn = new UnClosableConnection(src.getConnection(), new UnClosableConnection.CloseListener() {
					@Override
					public void wasClosed(final UnClosableConnection c) {
						pool.push(c);
					}
				});
				pool.push(conn);
			}
		}
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
