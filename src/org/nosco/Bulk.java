package org.nosco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.sql.DataSource;

import org.nosco.Constants.DB_TYPE;
import org.nosco.Diff.RowChange;
import org.nosco.util.Misc;

/**
 * Provides optimized methods for CRUD operations on collections. &nbsp;
 * Note that if you find yourself doing the following:
 * <pre>   {@code List<MyObject> toDelete = MyObject.ALL.where(conditions...).asList();
 *   new Bulk(ds).deleteAll(toDelete);}</pre>
 * It's much more efficient to do this:
 * <pre>   {@code   List<MyObject> toDelete = MyObject.ALL.where(conditions...).deleteAll();}</pre>
 * The latter deletes them from the database without transferring everything over the network.
 * <p>
 * Note that all these functions are streaming enabled. &nbsp; That means the following:
 * <pre>   {@code DataSource from new DataSource(from_db...);
 *   DataSource to new DataSource(to_db...);
 *   new Bulk(to).insertAll(MyObject.ALL.use(from).where(conditions...));}</pre>
 * Will move all data from the "from" to the "to" database without having to load the entire
 * result list into memory at one time.
 * <p>
 * Note: If a transaction is desired, use {@code ThreadContext.startTransaction(ds)} before
 * and {@code ThreadContext.commitTransaction(ds)} after any of these calls.
 * @author Derek Anderson
 */
public class Bulk {

	private DataSource ds;
	private static final int BATCH_SIZE = 64;
	private final static String EOQ = "EOQ";

	/**
	 * Specify the target DataSource.
	 * Note that you can get the default DataSource from any {@code MyObject.ALL.getDataSource()}.
	 * @param ds
	 */
	public Bulk(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * Inserts all objects from the source iterable into the target DataSource. &nbsp;
	 * On error aborts. &nbsp;
	 * @param iterable
	 * @return
	 * @throws SQLException
	 */
	public <T extends Table> int insertAll(Iterable<T> iterable) throws SQLException {
		return insertAll(iterable, null, -1);
	}
	/**
	 * Inserts all objects from the source iterable into the target DataSource. &nbsp;
	 * On error aborts. &nbsp; Callback called every {@code frequency} seconds with the
	 * number of rows already inserted. &nbsp;
	 * If thread is interrupted returns the number of rows inserted before the interruption.
	 * @param iterable
	 * @param callback
	 * @param frequency
	 * @return
	 * @throws SQLException
	 */
	public <T extends Table> int insertAll(Iterable<T> iterable, StatusCallback callback,
			double frequency) throws SQLException {
		int count = 0;
		int resCount = 0;
		boolean first = true;
		double lastCallback = System.currentTimeMillis() / 1000.0;
		Connection conn = null;
		Field<?>[] fields = null;
		PreparedStatement psInsert = null;

		try {
			for (T table : iterable) {
				count += 1;
				if (first) {
					first = false;
					DBQuery<T> q = (DBQuery<T>) new DBQuery<T>(table.getClass()).use(ds);
					conn = q.getConnRW();
					fields = table.FIELDS();
					psInsert = createInsertPS(conn, q, table, fields);
				}
				int i=1;
				for (Field<?> field : fields) {
					Object o = table.get(field);
					o = table.__NOSCO_PRIVATE_mapType(o);
					// hack for sql server which otherwise gives:
					// com.microsoft.sqlserver.jdbc.SQLServerException:
					// The conversion from UNKNOWN to UNKNOWN is unsupported.
					if (o instanceof Character) psInsert.setString(i++, o.toString());
					else psInsert.setObject(i++, o);
				}
				psInsert.addBatch();
				if (count % BATCH_SIZE == 0) {
					for (int k : psInsert.executeBatch()) {
						resCount += k;
					}
					if (Thread.interrupted()) {
						return resCount;
					}
				}
				if (callback!=null && ((System.currentTimeMillis()/1000.0)
						- lastCallback > frequency)) {
					callback.call(count);
					lastCallback = System.currentTimeMillis() / 1000.0;
				}
			}
			if (count % BATCH_SIZE != 0) {
				for (int k : psInsert.executeBatch()) resCount += k;
			}
			if (psInsert != null && !psInsert.isClosed()) psInsert.close();
			if (conn!=null && !ThreadContext.inTransaction(ds)) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return resCount;
		} catch (SQLException e) {
			if (!ThreadContext.inTransaction(ds) && !conn.getAutoCommit()) conn.rollback();
			throw e;
		} finally {
			safeClose(psInsert);
			safeClose(conn, ds);
		}
	}

	private static void safeClose(PreparedStatement ps) {
		// c3p0 sometimes throws a NPE on isClosed()
		try { if (ps != null && !ps.isClosed()) ps.close(); }
		catch (NullPointerException e) { /* ignore */ }
		catch (SQLException e) { /* ignore */ }
	}

	private static void safeClose(Connection conn, DataSource ds) {
		try {
			if (conn != null && !conn.isClosed() && !ThreadContext.inTransaction(ds)) {
				conn.close();
			}
		}
		catch (NullPointerException e) { /* ignore */ }
		catch (SQLException e) { /* ignore */ }
	}

	/**
	 * Updates all objects (based on their primary keys) from the source iterable into the
	 * target DataSource. &nbsp; On error aborts. &nbsp;
	 * If thread is interrupted returns the number of rows inserted before the interruption. &nbsp;
	 * <p>Note that classes without primary keys are not supported at this time.
	 * @param iterable
	 * @return
	 * @throws SQLException
	 */
	public <T extends Table> int updateAll(Iterable<T> iterable) throws SQLException {
		int count = 0;
		int resCount = 0;
		boolean first = true;
		Connection conn = null;
		Field<?>[] fields = null;
		PreparedStatement psUpdate = null;
		Field<?>[] pks = null;

		try {
			for (T table : iterable) {
				if (first) {
					first = false;
					DBQuery<T> q = (DBQuery<T>) new DBQuery<T>(table.getClass()).use(ds);
					conn = q.getConnRW();
					fields = table.FIELDS();
					pks = Misc.getPK(table) == null ? null : Misc.getPK(table).GET_FIELDS();
					psUpdate = createUpdatePS(conn, q, table, fields, pks);
				}
				int i=1;
				for (Field<?> field : fields) {
					Object o = table.get(field);
					o = table.__NOSCO_PRIVATE_mapType(o);
					// hack for sql server which otherwise gives:
					// com.microsoft.sqlserver.jdbc.SQLServerException:
					// The conversion from UNKNOWN to UNKNOWN is unsupported.
					if (o instanceof Character) psUpdate.setString(i++, o.toString());
					else psUpdate.setObject(i++, o);
				}
				for (Field<?> field : pks) {
					Object o = table.get(field);
					o = table.__NOSCO_PRIVATE_mapType(o);
					// hack for sql server which otherwise gives:
					// com.microsoft.sqlserver.jdbc.SQLServerException:
					// The conversion from UNKNOWN to UNKNOWN is unsupported.
					if (o instanceof Character) psUpdate.setString(i++, o.toString());
					else psUpdate.setObject(i++, o);
				}
				psUpdate.addBatch();
				if (count % BATCH_SIZE == 0) {
					for (int k : psUpdate.executeBatch()) {
						resCount += k;
					}
					if (Thread.interrupted()) {
						return resCount;
					}
				}
			}
			if (count % BATCH_SIZE != 0) {
				for (int k : psUpdate.executeBatch()) resCount += k;
			}
			if (psUpdate != null && !psUpdate.isClosed()) psUpdate.close();
			if (conn!=null && !ThreadContext.inTransaction(ds)) {
				if (!conn.getAutoCommit()) conn.commit();
				safeClose(conn, ds);
			}
			return resCount;
		} catch (SQLException e) {
			if (!ThreadContext.inTransaction(ds) && !conn.getAutoCommit()) conn.rollback();
			throw e;
		} finally {
			safeClose(psUpdate);
			safeClose(conn, ds);
		}
	}

	/**
	 * Inserts all objects from the source iterable into the
	 * target DataSource. &nbsp; On error attempts to update (based on their primary keys). &nbsp;
	 * On update error aborts.
	 * <p>Note that classes without primary keys are not supported at this time.
	 * @param iterable
	 * @return
	 * @throws SQLException
	 */
	public <T extends Table> int insertOrUpdateAll(Iterable<T> iterable) throws SQLException {
		int count = 0;
		boolean first = true;
		Connection conn = null;
		Field<?>[] fields = null;
		PreparedStatement psInsert = null;
		PreparedStatement psUpdate = null;
		Field<?>[] pks = null;

		try {
			for (T table : iterable) {
				if (first) {
					first = false;
					DBQuery<T> q = (DBQuery<T>) new DBQuery<T>(table.getClass()).use(ds);
					conn = q.getConnRW();
					fields = table.FIELDS();
					psInsert = createInsertPS(conn, q, table, fields);
					pks = Misc.getPK(table) == null ? null : Misc.getPK(table).GET_FIELDS();
					psUpdate = createUpdatePS(conn, q, table, fields, pks);
				}
				int i=1;
				for (Field<?> field : fields) {
					Object o = table.get(field);
					o = table.__NOSCO_PRIVATE_mapType(o);
					// hack for sql server which otherwise gives:
					// com.microsoft.sqlserver.jdbc.SQLServerException:
					// The conversion from UNKNOWN to UNKNOWN is unsupported.
					if (o instanceof Character) psInsert.setString(i++, o.toString());
					else psInsert.setObject(i++, o);
				}
				try {
					psInsert.execute();
					count += psInsert.getUpdateCount();
				} catch (SQLException e) {
					int j=1;
					for (Field<?> field : fields) {
						Object o = table.get(field);
						o = table.__NOSCO_PRIVATE_mapType(o);
						// hack for sql server which otherwise gives:
						// com.microsoft.sqlserver.jdbc.SQLServerException:
						// The conversion from UNKNOWN to UNKNOWN is unsupported.
						if (o instanceof Character) psUpdate.setString(j++, o.toString());
						else psUpdate.setObject(j++, o);
					}
					for (Field<?> field : pks) {
						Object o = table.get(field);
						o = table.__NOSCO_PRIVATE_mapType(o);
						// hack for sql server which otherwise gives:
						// com.microsoft.sqlserver.jdbc.SQLServerException:
						// The conversion from UNKNOWN to UNKNOWN is unsupported.
						if (o instanceof Character) psUpdate.setString(j++, o.toString());
						else psUpdate.setObject(j++, o);
					}
					psUpdate.execute();
					count += psUpdate.getUpdateCount();
				}

			}
			if (psInsert != null && !psInsert.isClosed()) psInsert.close();
			if (psUpdate != null && !psUpdate.isClosed()) psUpdate.close();
			if (conn!=null && !ThreadContext.inTransaction(ds)) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;
		} catch (SQLException e) {
			if (!ThreadContext.inTransaction(ds) && !conn.getAutoCommit()) conn.rollback();
			throw e;
		} finally {
			safeClose(psInsert);
			safeClose(psUpdate);
			safeClose(conn, ds);
		}
	}

	/**
	 * Updates all objects (based on their primary keys) from the source iterable into the
	 * target DataSource. &nbsp; On error aborts. &nbsp;
	 * <p>Note that classes without primary keys are not supported at this time.
	 * @param iterable
	 * @return
	 * @throws SQLException
	 */
	public <T extends Table> int deleteAll(Iterable<T> iterable) throws SQLException {
		int count = 0;
		int resCount = 0;
		boolean first = true;
		Connection conn = null;
		PreparedStatement psDelete = null;
		Field<?>[] pks = null;

		try {
			for (T table : iterable) {
				if (first) {
					first = false;
					DBQuery<T> q = (DBQuery<T>) new DBQuery<T>(table.getClass()).use(ds);
					conn = q.getConnRW();
					pks = Misc.getPK(table) == null ? null : Misc.getPK(table).GET_FIELDS();
					if (pks == null || pks.length == 0) {
						throw new RuntimeException("cannot bulk delete from tha PK-less table");
					}
					psDelete = createDeletePS(conn, q, table, pks);
				}
				int i=1;
				for (Field<?> field : pks) {
					Object o = table.get(field);
					o = table.__NOSCO_PRIVATE_mapType(o);
					// hack for sql server which otherwise gives:
					// com.microsoft.sqlserver.jdbc.SQLServerException:
					// The conversion from UNKNOWN to UNKNOWN is unsupported.
					if (o instanceof Character) psDelete.setString(i++, o.toString());
					else psDelete.setObject(i++, o);
				}
				psDelete.addBatch();
				if (count % BATCH_SIZE == 0) {
					for (int k : psDelete.executeBatch()) resCount += k;
				}
			}
			if (count % BATCH_SIZE != 0) {
				for (int k : psDelete.executeBatch()) {
					resCount += k;
				}
				if (Thread.interrupted()) {
					return resCount;
				}
			}
			if (psDelete != null && !psDelete.isClosed()) psDelete.close();
			if (conn!=null && !ThreadContext.inTransaction(ds)) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return resCount;
		} catch (SQLException e) {
			if (!ThreadContext.inTransaction(ds) && !conn.getAutoCommit()) conn.rollback();
			throw e;
		} finally {
			safeClose(psDelete);
			safeClose(conn, ds);
		}
	}

	private <T extends Table> PreparedStatement createInsertPS(Connection conn,
			DBQuery<T> q, Table table, Field[] fields) throws SQLException {
		PreparedStatement ps;
		String sep = q.getDBType()==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		StringBuffer sb = new StringBuffer();
		sb.append("insert into ");
		sb.append(ThreadContext.getDatabaseOverride(ds, table.SCHEMA_NAME())
				+sep+ table.TABLE_NAME());
		sb.append(" (");
		sb.append(Misc.join(",", fields));
		String[] bindStrings = new String[fields.length];
		for (int i=0; i < fields.length; ++i) bindStrings[i] = "?";
		sb.append(") values (");
		sb.append(Misc.join(", ", bindStrings));
		sb.append(")");
		String sql = sb.toString();
		Misc.log(sql, null);
		ps = conn.prepareStatement(sql);
		return ps;
	}

	private <T extends Table> PreparedStatement createUpdatePS(Connection conn,
			DBQuery<T> q, Table table, Field[] fields, Field[] pks) throws SQLException {
		if (pks == null || pks.length == 0) throw new RuntimeException("cannot mass update on a table without primary keys");
		PreparedStatement ps;
		String sep = q.getDBType()==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(ThreadContext.getDatabaseOverride(ds, table.SCHEMA_NAME())
				+sep+ table.TABLE_NAME());
		sb.append(" set ");
		sb.append(Misc.join("=?, ", fields));
		sb.append("=?  where ");
		sb.append(Misc.join("=? and ", pks));
		sb.append("=?;");
		String sql = sb.toString();
		Misc.log(sql, null);
		ps = conn.prepareStatement(sql);
		return ps;
	}

	private <T extends Table> PreparedStatement createDeletePS(Connection conn,
			DBQuery<T> q, Table table, Field[] pks) throws SQLException {
		PreparedStatement ps;
		String sep = q.getDBType()==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(ThreadContext.getDatabaseOverride(ds, table.SCHEMA_NAME())
				+sep+ table.TABLE_NAME());
		sb.append(" where ");
		sb.append(Misc.join("=? and ", pks));
		sb.append("=?;");
		String sql = sb.toString();
		Misc.log(sql, null);
		ps = conn.prepareStatement(sql);
		return ps;
	}

	/**
	 * A callback interface for bulk load operations. &nbsp; Calls with the current
	 * count of rows inserted, updated or deleted every {@code frequency} seconds
	 * the bulk operation takes.
	 * @author Derek Anderson
	 */
	public static interface StatusCallback {
		public void call(int count);
	}

	private static class QueueIterator<T extends Table> implements Iterator<T> {
		@SuppressWarnings("rawtypes")
		private BlockingQueue queue;
		QueueIterator(BlockingQueue queue) {
			this.queue = queue;
		}
		T next = null;
		boolean eoq = false;
		@Override
		public boolean hasNext() {
			if (next != null) return true;
			if (eoq) return false;
			Object o;
			try {
				o = queue.take();
				if (EOQ.equals(o)) {
					eoq = true;
					return false;
				}
				next = (T) o;
				return true;
			} catch (InterruptedException e) {
				return false;
			}
		}
		@Override
		public T next() {
			T o = next;
			next = null;
			return o;
		}
		@Override
		public void remove() {}
	};

	private static class Counter {
		int count = 0;
	}

	@SuppressWarnings("rawtypes")
	public <T extends Table> int commitDiff(final Iterable<RowChange<T>> diff) {
		final Bulk bulk = this;
		final BlockingQueue adds = new ArrayBlockingQueue(10*1024);
		final BlockingQueue updates = new ArrayBlockingQueue(10*1024);
		final BlockingQueue deletes = new ArrayBlockingQueue(10*1024);
		final Counter count = new Counter();

		Thread prodThread = new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				for (RowChange<T> rc : diff) {
					count.count++;
					try {
						if (rc.isAdd()) adds.put(rc.getObject());
						if (rc.isUpdate()) updates.put(rc.getObject());
						if (rc.isDelete()) deletes.put(rc.getObject());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				try {
					adds.put(EOQ);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					updates.put(EOQ);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					deletes.put(EOQ);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread addThread = new Thread() {
			@Override
			public void run() {
				try {
					bulk.insertAll(new Iterable<T>() {
						@Override
						public Iterator<T> iterator() {
							return new QueueIterator<T>(adds);
						}});
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		};
		Thread updateThread = new Thread() {
			@Override
			public void run() {
				try {
					bulk.updateAll(new Iterable<T>() {
						@Override
						public Iterator<T> iterator() {
							return new QueueIterator<T>(updates);
						}});
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		};
		Thread deleteThread = new Thread() {
			@Override
			public void run() {
				try {
					bulk.deleteAll(new Iterable<T>() {
						@Override
						public Iterator<T> iterator() {
							return new QueueIterator<T>(deletes);
						}});
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		};
		prodThread.start();
		addThread.start();
		updateThread.start();
		deleteThread.start();

		try {
			prodThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			addThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			updateThread.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			deleteThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return count.count;
	}

}
