package org.kered.dko.datasource;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * This class will, given a class name and a method, call that method to get an
 * actual {@code javax.sql.DataSource}. &nbsp; It is then cached, and all other calls
 * are passed through.
 *
 * @author Derek Anderson
 */
public class ReflectedDataSource implements MatryoshkaDataSource {

	private static final Logger log = Logger.getLogger("org.kered.dko.ReflectedDataSource");

	DataSource ds = null;
	private final String cls;
	private final String method;
	private String schema = null;
	StackTraceElement[] st = Thread.currentThread().getStackTrace();

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
		} catch (final Exception e) {
			log.severe("could not access "+ cls +"."+ method +": "+ e.getMessage());
			throw new RuntimeException(e);
		}
		if (ds==null) throw new RuntimeException("Method "+ cls +"."+ method +" returned a null DataSource.  "
				+"ReflectedDataSource created at:\n\t\t"+ Util.join("\n\t\t", st));
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
		if (ds==null) return false;
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

	//@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public DataSource getPrimaryUnderlying() {
		checkDS();
		return ds;
	}

	@Override
	public Collection<DataSource> getAllUnderlying() {
		checkDS();
		final Collection<DataSource> ret = new ArrayList<DataSource>(1);
		ret.add(ds);
		return ret;
	}

}
