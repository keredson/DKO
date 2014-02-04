package org.kered.dko.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.kered.dko.Constants;
import org.kered.dko.Constants.DB_TYPE;

/**
 * When (usually older) JDBC drivers don't offer a DataSource implementation
 * this class will give you one.
 *
 * @author Derek Anderson
 */
public class JDBCDriverDataSource implements DataSource {

	private final String url;
	private final String username;
	private final String password;
	private final DB_TYPE type;

	public JDBCDriverDataSource(final String url) {
		if (url.startsWith("jdbc:sqlite")) type = DB_TYPE.SQLITE3;
		else if (url.startsWith("jdbc:derby")) type = DB_TYPE.DERBY;
		else type = null;
		this.url = url;
		this.username = null;
		this.password = null;
	}

	public JDBCDriverDataSource(final String url, final String username,
			final String password) {
		this.type = null;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public JDBCDriverDataSource(final Constants.DB_TYPE type, final String url) {
		this.type = type;
		this.url = url;
		this.username = null;
		this.password = null;
	}

	public JDBCDriverDataSource(final Constants.DB_TYPE type, final String url,
			final String username, final String password) {
		this.type = type;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLogWriter(final PrintWriter arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLoginTimeout(final int arg0) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(final Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection getConnection() throws SQLException {
		if (username == null && password == null) {
			return DriverManager.getConnection(url);
		} else {
			return DriverManager.getConnection(url, username, password);
		}
	}

	@Override
	public Connection getConnection(final String username, final String password)
			throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final JDBCDriverDataSource other = (JDBCDriverDataSource) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	// @Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}

	public DB_TYPE getDBType() {
		return type;
	}
	
	public String getURL() {
		return url;
	}

}
