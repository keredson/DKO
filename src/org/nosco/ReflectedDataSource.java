package org.nosco;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class ReflectedDataSource implements DataSource {

	DataSource ds = null;
	private String cls;
	private String method;
	private String schema = null;

	public ReflectedDataSource(String cls, String method) {
		this.cls = cls;
		this.method = method;
	}

	public ReflectedDataSource(String cls, String method, String schema) {
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
			System.err.println("ds: "+ ds);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		checkDS();
		ds.setLogWriter(arg0);
	}

	@Override
	public void setLoginTimeout(int arg0) throws SQLException {
		checkDS();
		ds.setLoginTimeout(arg0);
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		checkDS();
		if (ds.getClass().equals(arg0)) return true;
		return ds.isWrapperFor(arg0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
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
	public Connection getConnection(String arg0, String arg1)
			throws SQLException {
		checkDS();
		return ds.getConnection(arg0, arg1);
	}

}
