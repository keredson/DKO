package org.kered.dko;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Constants.JOIN_TYPE;
import org.kered.dko.datasource.JDBCDriverDataSource;
import org.kered.dko.datasource.SingleThreadedDataSource;

class QuerySnapshot<T extends Table> implements Iterable<T> {

	private static final String PREFIX = "dko_snapshot_";
	private static final String EXT = ".sqlite.db";

	private final File f;
	private final boolean delete;
	private SingleThreadedDataSource ds;
	private final Query<T> q;
	private final String tmpTableName = genTmpTableName();
	private String sql;
	private List<Field<?>> fields;

	public QuerySnapshot(Query<T> q) {
		this.q = q;
		try {
			f = File.createTempFile(PREFIX, EXT);
			delete = true;
			f.deleteOnExit();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		init();
	}

	private String genTmpTableName() {
		StringBuffer sb = new StringBuffer();
		sb.append(PREFIX);
		for (int i=0; i<8; ++i) {
			sb.append((char) ('A' + Math.random()*('Z'-'A')));
		}
		return sb.toString();
	}

	public QuerySnapshot(Query<T> q, File f) {
		this.q = q;
		if (f.isDirectory()) {
			try {
				this.f = File.createTempFile(PREFIX, EXT, f);
				delete = true;
				f.deleteOnExit();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			this.f = f;
			delete = false;
		}
		init();
	}

	private void init() {
		// make sure sqlite driver is loaded...
		org.kered.dko.persistence.Util.getDS();
		String url = "jdbc:sqlite:" + f.getPath();
		ds = new SingleThreadedDataSource(new JDBCDriverDataSource(Constants.DB_TYPE.SQLITE3, url), 10000, false);

		Map<Field, String> fieldNameOverrides = LocalJoin.load(q, tmpTableName, ds);
		
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		fields = q.getSelectFields();
		for (Field<?> field : fields) {
			String fieldName = fieldNameOverrides.get(field);
			if (fieldName==null) fieldName = field.NAME;
			sb.append(tmpTableName).append(".").append(fieldName).append(", ");
		}
		sb.delete(sb.length()-2, sb.length()).append(" "); // delete the last comma
		sb.append("from ").append(tmpTableName);
		sql = sb.toString();
	}

	@Override
	public Iterator<T> iterator() {
		if (q instanceof DBQuery) {
			return new SelectFromOAI<T>((DBQuery<T>) q, buildSrcIterator());
		} else {
			throw new RuntimeException("not implemented (yet) for this query implementation: "+ q.getClass().getName());
		}
	}

	private PeekableClosableIterator<Object[]> buildSrcIterator() {
		return new PeekableClosableIterator<Object[]>() {
			Connection conn = null;
			private ResultSet rs = null;
			private Statement stmt = null;
			{
				try {
					conn = ds.getConnection();
					stmt = conn.createStatement();
					Util.log(sql, null);
					rs = stmt.executeQuery(sql);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			private Object[] next = null;
			@Override
			public Object[] peek() {
				if (next!=null) return next;
				try {
					if (!rs.next()) return null;
					int leftSize = fields.size();
					next = new Object[leftSize];
					for (int i=0; i<leftSize; ++i) {
						next[i] = Util.getTypedValueFromRS(rs, i+1, fields.get(i));
					}
					return next;
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
			@Override
			public boolean hasNext() {
				if (next==null) peek();
				return next != null;
			}
			@Override
			public Object[] next() {
				Object[] ret = next;
				next = null;
				return ret;
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			@Override
			public void close() {
				try {
					if (rs!=null && !rs.isClosed()) {
						rs.close();
						rs = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (stmt!=null && !stmt.isClosed()) {
						stmt.close();
						stmt = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					if (conn!=null && !conn.isClosed()) {
						conn.close();
						conn = null;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			@Override
			protected void finalize() throws Throwable {
				close();
				super.finalize();
			}
		};
	}

	@Override
	protected void finalize() throws Throwable {
		if (delete && f.exists()) f.delete();
		super.finalize();
	}

}
