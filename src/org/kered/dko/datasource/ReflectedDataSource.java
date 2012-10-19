package org.kered.dko.datasource;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * This class will, given a class name and a method, call that method to get an
 * actual {@code javax.sql.DataSource}. &nbsp; It is then cached, and all other calls
 * are passed through.
 *
 * @author Derek Anderson
 */
public class ReflectedDataSource implements DataSource {

	DataSource ds = null;
	private final String cls;
	private final String method;
	private String schema = null;

	public ReflectedDataSource(final String cls, final String method) {
		this.cls = cls;
		this.method = method;
	}

	public ReflectedDataSource(final String cls, final String method, final String schema) {
		this.cls = cls;
		this.method = method;
		this.schema  = schema;
	}

	private void checkDS() {
		if (ds != null) return;
		try {
			Method m = null;
			if (schema == null) {
				m = Class.forName(cls).getMethod(method);
				ds = (DataSource) m.invoke(null);
			} else {
				m = Class.forName(cls).getMethod(method, String.class);
				ds = (DataSource) m.invoke(null, schema);
			}
			//System.err.println("ds: "+ ds);
		} catch (final SecurityException e) {
			throw new RuntimeException(e);
		} catch (final NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (final IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (final IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (final InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		checkDS();
		return ds.getLogWriter();
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		checkDS();
		return ds.getLoginTimeout();
	}

	@Override
	public void setLogWriter(final PrintWriter arg0) throws SQLException {
		checkDS();
		ds.setLogWriter(arg0);
	}

	@Override
	public void setLoginTimeout(final int arg0) throws SQLException {
		checkDS();
		ds.setLoginTimeout(arg0);
	}

	@Override
	public boolean isWrapperFor(final Class<?> arg0) throws SQLException {
		checkDS();
		if (ds.getClass().equals(arg0)) return true;
		return ds.isWrapperFor(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(final Class<T> arg0) throws SQLException {
		checkDS();
		if (ds.getClass().equals(arg0)) return (T) ds;
		return ds.unwrap(arg0);
	}

	@Override
	public Connection getConnection() throws SQLException {
		checkDS();
		return ds.getConnection();
	}

	@Override
	public Connection getConnection(final String arg0, final String arg1)
			throws SQLException {
		checkDS();
		return ds.getConnection(arg0, arg1);
	}

	public DataSource getUnderlyingDataSource() {
		checkDS();
		return ds;
	}

}
