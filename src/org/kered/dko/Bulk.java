package org.kered.dko;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.kered.dko.Constants.DB_TYPE;
import org.kered.dko.Diff.FieldChange;
import org.kered.dko.Diff.RowChange;
import org.kered.dko.Tuple.Tuple2;

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
 * <pre>   {@code DataSource from = new DataSource(from_db...);
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

	private final DataSource ds;
	private final DB_TYPE dbType;
	private final int batchSize;
	private long rateLimit = -1;
	private static final int DEFAULT_BATCH_SIZE = 64;

	/**
	 * Specify the target DataSource.
	 * Note that you can get the default DataSource from any {@code MyObject.ALL.getDataSource()}.
	 * @param ds
	 */
	public Bulk(final DataSource ds) {
		this.ds = ds;
		dbType = DB_TYPE.detect(ds);
		batchSize = DEFAULT_BATCH_SIZE;
	}

	/**
	 * Specify the target DataSource.
	 * Note that you can get the default DataSource from any {@code MyObject.ALL.getDataSource()}.
	 * @param ds
	 * @param batchSize the JDBCX batch size to use
	 */
	public Bulk(final DataSource ds, final int batchSize) {
		this.ds = ds;
		dbType = DB_TYPE.detect(ds);
		this.batchSize = batchSize;
	}
	
	public Bulk setRateLimit(long limit) {
		this.rateLimit  = limit;
		return this;
	}

	/**
	 * Inserts all objects from the source iterable into the target DataSource. &nbsp;
	 * On error aborts. &nbsp;
	 * @param iterable
	 * @return
	 * @throws SQLException
	 */
	public <T extends Table> long insertAll(final Iterable<T> iterable) throws SQLException {
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
	public <T extends Table> long insertAll(final Iterable<T> iterable, final StatusCallback callback,
			final double frequency) throws SQLException {
		long start = System.currentTimeMillis();
		long c = 0;
		double lastCallback = System.currentTimeMillis() / 1000.0;
		final Map<String, Inserter<T>> inserters = new HashMap<String,Inserter<T>>();
		for (final T t : iterable) {
			++c;
			if (rateLimit>0 && c*1000/(System.currentTimeMillis()-start) > rateLimit) {
				try { Thread.currentThread().sleep(1000); }
				catch (InterruptedException e) { /* ignore */ }
			}
			final String key = t.__NOSCO_FETCHED_VALUES.toString();
			Inserter<T> inserter = inserters.get(key);
			if (inserter == null) {
				inserter = new Inserter<T>();
				inserters.put(key, inserter);
			}
			final boolean batchWentOut = inserter.push(t);
			if (callback!=null && batchWentOut && ((System.currentTimeMillis()/1000.0) - lastCallback > frequency)) {
				long count = 0;
				for (final Inserter<T> i : inserters.values()) {
					count += i.count;
				}
				callback.call(count);
				lastCallback = System.currentTimeMillis() / 1000.0;
			}
		}
		long count = 0;
		for (final Inserter<T> inserter : inserters.values()) {
			inserter.finish();
			count += inserter.count;
		}
		return count;
	}

	private static void safeClose(final PreparedStatement ps) {
		// c3p0 sometimes throws a NPE on isClosed()
		try { if (ps != null && !ps.isClosed()) ps.close(); }
		catch (final Throwable e) { /* ignore */ }
	}

	private static void safeClose(final Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
		catch (final Throwable e) { /* ignore */ }
	}

	/**
	 * Updates all objects (based on their primary keys) from the source iterable into the
	 * target DataSource. &nbsp; On error aborts. &nbsp;
	 * @param iterable
	 * @return
	 * @throws SQLException
	 */
	public <T extends Table> long updateAll(final Iterable<T> iterable) throws SQLException {
		return updateAll(iterable, null, -1);
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
	public <T extends Table> long updateAll(final Iterable<T> iterable, final StatusCallback callback,
			final double frequency) throws SQLException {
		long start = System.currentTimeMillis();
		long c = 0;
		double lastCallback = System.currentTimeMillis() / 1000.0;
		final Map<String, Updater<T>> updaters = new HashMap<String,Updater<T>>();
		for (final T t : iterable) {
			if (t.__NOSCO_UPDATED_VALUES==null) continue;
			++c;
			if (rateLimit>0 && c*1000/(System.currentTimeMillis()-start) > rateLimit) {
				try { Thread.currentThread().sleep(1000); }
				catch (InterruptedException e) { /* ignore */ }
			}
			final String key = t.__NOSCO_UPDATED_VALUES.toString();
			Updater<T> updater = updaters.get(key);
			if (updater == null) {
				//System.err.println("t.__NOSCO_UPDATED_VALUES: "+ t.__NOSCO_UPDATED_VALUES);
				updater = new Updater<T>();
				updaters.put(key, updater);
			}
			final boolean batchWentOut = updater.push(t);
			if (callback!=null && batchWentOut && ((System.currentTimeMillis()/1000.0) - lastCallback > frequency)) {
				long count = 0;
				for (final Updater<T> u : updaters.values()) {
					count += u.count;
				}
				callback.call(count);
				lastCallback = System.currentTimeMillis() / 1000.0;
			}
		}
		long count = 0;
		for (final Updater<T> updater : updaters.values()) {
			updater.finish();
			count += updater.count;
		}
		return count;
	}

	private class Doer<T extends Table> {

		@SuppressWarnings("unchecked")
		private final T[] buffer = (T[]) new Table[batchSize];
		private int pos = 0;
		protected boolean init = false;
		protected Field<?>[] fields;
		protected Connection conn;
		protected Method pre = null;
		protected Method post = null;
		protected PreparedStatement ps;
		protected Boolean shouldCloseConn = true;
		private boolean finished = false;
		long count = 0;
		RejectCallback<T> rc = null;
		Class<? extends Table> clazz;

		boolean push(final T t) throws SQLException {
			buffer[pos++] = t;
			if (pos == buffer.length) {
				pushBatch();
				return true;
			}
			return false;
		}

		@SuppressWarnings("unchecked")
		private void pushBatch() throws SQLException {
			if (!init) init(buffer[0]);
			if (pre != null) {
				try {
					final Object[] cba = (Object[]) Array.newInstance(clazz, pos);
					System.arraycopy(buffer, 0, cba, 0, pos);
					pre.invoke(null, cba, ds);
				} catch (final IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (final IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (final InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
			executeBatch(0, pos);
			if (post != null) {
				try {
					final Object[] cba = (Object[]) Array.newInstance(clazz, pos);
					System.arraycopy(buffer, 0, cba, 0, pos);
					post.invoke(null, cba, ds);
				} catch (final IllegalArgumentException e) {
					throw new RuntimeException(e);
				} catch (final IllegalAccessException e) {
					throw new RuntimeException(e);
				} catch (final InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
			pos = 0;
		}

		private void executeBatch(final int start, final int end) throws SQLException, BatchUpdateException {
			//System.err.println("executing batch of " + (end-start));
			for (int i=start; i<end; ++i) {
				final Table table = buffer[i];
				int k=1;
				for (int j=0; j<fields.length; ++j) {
					final Field<?> field = fields[j];
					Object o = table.get(field);
					o = table.__NOSCO_PRIVATE_mapType(o);
					// hack for sql server which otherwise gives:
					// com.microsoft.sqlserver.jdbc.SQLServerException:
					// The conversion from UNKNOWN to UNKNOWN is unsupported.
					if (o instanceof Character) ps.setString(k++, o.toString());
					else ps.setObject(k++, o);
					//System.err.print(o + ", ");
				}
				//System.err.println();
				ps.addBatch();
			}
			try {
				//System.err.println("executing batch of " + (end-start));
				final int[] batchResults = ps.executeBatch();
				//System.err.println("... done! "+ Util.join(",", batchResults));
				if (shouldCloseConn && !conn.getAutoCommit()) conn.commit();
				//System.err.println("... committed!");
				for (final int k : batchResults) {
					count += k;
				}
			} catch (final BatchUpdateException e) {
				if (shouldCloseConn && !conn.getAutoCommit()) conn.rollback();
				if (rc == null) throw e;
				final int[] batchResults = e.getUpdateCounts();
				//System.err.println(e);
				//System.err.println(e.getCause());
				//System.err.print("batchResults("+ batchResults.length +") ");
				final List<T> rejects = new ArrayList<T>();
				for (int i=0; i<batchResults.length; ++i) {
					//System.err.print(" " + batchResults[i] +":"+ buffer[start+i]);
					if (batchResults[i]<=0) rejects.add(buffer[start+i]);
					else count += batchResults[i];
				}
				//System.err.println();
				if (batchResults.length < end-start) {
					// some JDBC drivers (*cough* HSQL *cough*) stop immediately if any
					// row throws an exception.  (instead of trying all rows and reporting
					// which rows throw an exception)  this behavior sucks because it forces
					// you to resubmit each object individually to the db if they all fail,
					// completely eliminating the benefit of batch operations!
					rejects.add(buffer[start + batchResults.length]);
					if (start+1 < end) executeBatch(start+1, end);
				}
				rc.reject(rejects);
			}
		}

		protected void init(final Table table) throws SQLException {
			init = true;
			clazz = table.getClass();
			final Tuple2<Connection, Boolean> connInfo = DBQuery.getConnRW(ds);
			conn = connInfo.a;
			shouldCloseConn  = connInfo.b;
		}

		void finish() throws SQLException {
			if (pos > 0) pushBatch();
			safeClose(ps);
			if (shouldCloseConn) {
				if (conn != null && !conn.getAutoCommit()) conn.commit();
				safeClose(conn);
			}
			finished  = true;
			//System.err.println(this +" finished "+ count);
		}

		@Override
		protected void finalize() throws Throwable {
			if (!finished) finish();
		}

	}

	private class Inserter<T extends Table> extends Doer<T> {

		public Inserter() {}

		Inserter(final RejectCallback<T> rc) {
			this.rc  = rc;
		}

		protected void init(final Table table) throws SQLException {
			super.init(table);
			final List<Field<?>> allFields = Util.getFIELDS(table.getClass());
			fields = new Field[table.__NOSCO_FETCHED_VALUES.cardinality()];
			for (int i=0, j=0; i<allFields.size(); ++i) {
				if (table.__NOSCO_FETCHED_VALUES.get(i)) {
					fields[j++] = allFields.get(i);
				}
			}
			try {
				final Class<? extends Table> clazz = table.getClass();
				final java.lang.reflect.Field preField = clazz.getDeclaredField("__NOSCO_CALLBACK_INSERT_PRE");
				preField.setAccessible(true);
				pre = (Method) preField.get(table);
				final java.lang.reflect.Field postField = clazz.getDeclaredField("__NOSCO_CALLBACK_INSERT_POST");
				postField.setAccessible(true);
				post = (Method) postField.get(table);
			}
			catch (final ClassCastException e) { /* ignore */ }
			catch (final SecurityException e) { /* ignore */ }
			catch (final NoSuchFieldException e) { /* ignore */ }
			catch (final IllegalArgumentException e) { /* ignore */ }
			catch (final IllegalAccessException e) { /* ignore */ }

			// create the statement
			final String sep = dbType==DB_TYPE.SQLSERVER ? ".dbo." : ".";
			final StringBuffer sb = new StringBuffer();
			sb.append("insert into ");
			String schema = Context.getSchemaToUse(ds, Util.getSCHEMA_NAME(table.getClass()));
			sb.append((schema==null || "".equals(schema) ? "" : schema+sep) + Util.getTABLE_NAME(table.getClass()));
			sb.append(" (");
			sb.append(Util.joinFields(dbType, ", ", fields));
			sb.append(") values (");
			for (int i=0; i<fields.length; ++i) {
				sb.append("?,");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append(")");
			final String sql = sb.toString();
			Util.log(sql, null);
			ps = conn.prepareStatement(sql);
		}

	}

	private class Updater<T extends Table> extends Doer<T> {

		private BitSet values = null;

		Updater() {}

		public Updater(final BitSet values) {
			this.values  = values;
		}

		@Override
		protected void init(final Table table) throws SQLException {
			super.init(table);
			if (values==null) values = table.__NOSCO_UPDATED_VALUES;
			final List<Field<?>> pks = Util.getPK(table).GET_FIELDS();
			for (final Field<?> pk : pks) {
				values.clear(pk.INDEX);
			}
			final List<Field<?>> allFields = Util.getFIELDS(table.getClass());
			fields = new Field[values.cardinality() + pks.size()];
			for (int i=0, j=0; i<allFields.size(); ++i) {
				if (values.get(i)) {
					fields[j++] = allFields.get(i);
				}
			}
			for (int i=0; i<pks.size(); ++i) {
				fields[fields.length - pks.size() + i] = pks.get(i);
			}
			try {
				final Class<? extends Table> clazz = table.getClass();
				final java.lang.reflect.Field preField = clazz.getDeclaredField("__NOSCO_CALLBACK_UPDATE_PRE");
				preField.setAccessible(true);
				pre = (Method) preField.get(table);
				final java.lang.reflect.Field postField = clazz.getDeclaredField("__NOSCO_CALLBACK_UPDATE_POST");
				postField.setAccessible(true);
				post = (Method) postField.get(table);
			}
			catch (final ClassCastException e) { /* ignore */ }
			catch (final SecurityException e) { /* ignore */ }
			catch (final NoSuchFieldException e) { /* ignore */ }
			catch (final IllegalArgumentException e) { /* ignore */ }
			catch (final IllegalAccessException e) { /* ignore */ }

			// create the statement
			final String sep = dbType==DB_TYPE.SQLSERVER ? ".dbo." : ".";
			final StringBuffer sb = new StringBuffer();
			sb.append("update ");
			sb.append(Context.getSchemaToUse(ds, Util.getSCHEMA_NAME(table.getClass()))
					+sep+ Util.getTABLE_NAME(table.getClass()));
			sb.append(" set ");
			for (int i=0; i<fields.length-pks.size(); ++i) {
				sb.append(fields[i].getSQL(dbType));
				sb.append("=?, ");
			}
			sb.delete(sb.length()-2, sb.length());
			sb.append(" where ");
			sb.append(Util.joinFields(dbType, "=? and ", pks));
			sb.append("=?;");
			final String sql = sb.toString();
			Util.log(sql, null);
			ps = conn.prepareStatement(sql);
		}

	}

	private class Deleter<T extends Table> extends Doer<T> {

		Deleter() {}

		@Override
		protected void init(final Table table) throws SQLException {
			super.init(table);
			final List<Field<?>> get_FIELDS = Util.getPK(table).GET_FIELDS();
			fields = new Field[get_FIELDS.size()];
			for (int i=0; i<get_FIELDS.size(); ++i) fields[i] = get_FIELDS.get(i);
			try {
				pre = (Method) table.getClass().getDeclaredField("__NOSCO_CALLBACK_DELETE_PRE").get(table);
				post = (Method) table.getClass().getDeclaredField("__NOSCO_CALLBACK_DELETE_POST").get(table);
			}
			catch (final ClassCastException e) { /* ignore */ }
			catch (final SecurityException e) { /* ignore */ }
			catch (final NoSuchFieldException e) { /* ignore */ }
			catch (final IllegalArgumentException e) { /* ignore */ }
			catch (final IllegalAccessException e) { /* ignore */ }

			// create the statement
			final String sep = dbType==DB_TYPE.SQLSERVER ? ".dbo." : ".";
			final StringBuffer sb = new StringBuffer();
			sb.append("delete from ");
			sb.append(Context.getSchemaToUse(ds, Util.getSCHEMA_NAME(table.getClass()))
					+sep+ Util.getTABLE_NAME(table.getClass()));
			sb.append(" where ");
			sb.append(Util.joinFields(dbType, "=? and ", fields));
			sb.append("=?;");
			final String sql = sb.toString();
			Util.log(sql, null);
			ps = conn.prepareStatement(sql);
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
	public <T extends Table> long insertOrUpdateAll(final Iterable<T> iterable) throws SQLException {
		return insertOrUpdateAll(iterable, null, -1);
	}
	/**
	 * Inserts all objects from the source iterable into the
	 * target DataSource. &nbsp; On error attempts to update (based on their primary keys). &nbsp;
	 * On update error aborts.
	 * <p>Note that classes without primary keys are not supported at this time.
	 * @param iterable
	 * @param callback
	 * @param frequency
	 * @return
	 * @throws SQLException
	 */
	public <T extends Table> long insertOrUpdateAll(final Iterable<T> iterable, final StatusCallback callback,
			final double frequency) throws SQLException {
		long start = System.currentTimeMillis();
		long c = 0;
		double lastCallback = System.currentTimeMillis() / 1000.0;
		final Map<String, Inserter<T>> inserters = new HashMap<String,Inserter<T>>();
		final Map<String, Updater<T>> updaters = new HashMap<String,Updater<T>>();
		final List<T> rejects = new ArrayList<T>();
		for (final T t : iterable) {
			if (t.__NOSCO_UPDATED_VALUES==null) continue;
			++c;
			if (rateLimit>0 && c*1000/(System.currentTimeMillis()-start) > rateLimit) {
				try { Thread.currentThread().sleep(1000); }
				catch (InterruptedException e) { /* ignore */ }
			}
			// we use a string for the key because the bitset could change out from under us
			final String insertKey = t.__NOSCO_FETCHED_VALUES.toString();
			Inserter<T> inserter = inserters.get(insertKey);
			if (inserter == null) {
				inserter = new Inserter<T>(new RejectCallback<T>() {
					@Override
					void reject(final Collection<T> rs) {
						rejects.addAll(rs);
						//System.err.println("found rejects "+ rs.size());
					}
				});
				inserters.put(insertKey, inserter);
			}
			inserter.push(t);
			if (!rejects.isEmpty()) {
				for (final T r : rejects) {
					//System.err.println("reject: "+ r);
					final String key = r.__NOSCO_UPDATED_VALUES.toString();
					Updater<T> updater = updaters.get(key);
					if (updater == null) {
						//System.err.println("updaters.size(): "+ updaters.size());
						//System.err.println("r.__NOSCO_UPDATED_VALUES: "+ key);
						updater = new Updater<T>();
						updaters.put(key, updater);
					}
					updater.push(r);
				}
				rejects.clear();
			}
			if (callback!=null && ((System.currentTimeMillis()/1000.0) - lastCallback > frequency)) {
				long count = 0;
				for (final Inserter<T> i : inserters.values()) {
					count += i.count;
				}
				for (final Updater<T> u : updaters.values()) {
					count += u.count;
				}
				callback.call(count);
				lastCallback = System.currentTimeMillis() / 1000.0;
			}
		}
		long count = 0;
		for (final Inserter<T> inserter : inserters.values()) {
			inserter.finish();
			count += inserter.count;
		}
		for (final T r : rejects) {
			//System.err.println("reject: "+ r);
			final String updateKey = r.__NOSCO_UPDATED_VALUES.toString();
			Updater<T> updater = updaters.get(updateKey);
			if (updater == null) {
				//System.err.println("r2.__NOSCO_UPDATED_VALUES: "+ r.__NOSCO_UPDATED_VALUES);
				updater = new Updater<T>();
				updaters.put(updateKey, updater);
			}
			updater.push(r);
		}
		for (final Updater<T> updater : updaters.values()) {
			updater.finish();
			count += updater.count;
		}
		return count;
	}

	/**
	 * Deletes from the supplied DataSource all the elements in this Iterable.
	 * @param iterable
	 * @return the number of elements deleted
	 * @throws SQLException
	 */
	public <T extends Table> long deleteAll(final Iterable<T> iterable) throws SQLException {
		return deleteAll(iterable, null, -1);
	}

	/**
	 * Deletes from the supplied DataSource all the elements in this Iterable.
	 * @param iterable
	 * @param callback
	 * @param frequency
	 * @return the number of elements deleted
	 * @throws SQLException
	 */
	public <T extends Table> long deleteAll(final Iterable<T> iterable, final StatusCallback callback,
			final double frequency) throws SQLException {
		long start = System.currentTimeMillis();
		long c = 0;
		double lastCallback = System.currentTimeMillis() / 1000.0;
		final Deleter<T> deleter = new Deleter<T>();
		for (final T t : iterable) {
			++c;
			if (rateLimit>0 && c*1000/(System.currentTimeMillis()-start) > rateLimit) {
				try { Thread.currentThread().sleep(1000); }
				catch (InterruptedException e) { /* ignore */ }
			}
			deleter.push(t);
			if (callback!=null && ((System.currentTimeMillis()/1000.0) - lastCallback > frequency)) {
				callback.call(deleter.count);
				lastCallback = System.currentTimeMillis() / 1000.0;
			}
		}
		deleter.finish();
		return deleter.count;
	}

	/**
	 * A callback interface for bulk load operations. &nbsp; Calls with the current
	 * count of rows inserted, updated or deleted every {@code frequency} seconds
	 * the bulk operation takes.
	 * @author Derek Anderson
	 */
	public static interface StatusCallback {
		public void call(long count);
	}

	static abstract class RejectCallback<T extends Table> {
		abstract void reject(final Collection<T> rejects);
	}

	public <T extends Table> long commitDiff(final Iterable<RowChange<T>> diff) throws SQLException {
		final Map<String, Inserter<T>> inserters = new HashMap<String,Inserter<T>>();
		final Map<BitSet, Updater<T>> updaters = new HashMap<BitSet,Updater<T>>();
		final Deleter<T> deleter = new Deleter<T>();
		for (final RowChange<T> rc : diff) {
			final T t = rc.getObject();
			if (rc.isAdd()) {
				final String insertKey = t.__NOSCO_FETCHED_VALUES.toString();
				Inserter<T> inserter = inserters.get(insertKey);
				if (inserter == null) {
					inserter = new Inserter<T>();
					inserters.put(insertKey, inserter);
				}
				inserter.push(t);
			} else if (rc.isUpdate()) {
				final BitSet values = new BitSet();
				for (final FieldChange<T, ?> change : rc.getChanges()) {
					values.set(change.field.INDEX);
				}
				Updater<T> updater = updaters.get(values);
				if (updater == null) {
					System.err.println("values: "+ values);
					updater = new Updater<T>(values);
					updaters.put(values, updater);
				}
				updater.push(t);
			} else if (rc.isDelete()) {
				deleter.push(t);
			}
		}
		long count = 0;
		for (final Inserter<T> inserter : inserters.values()) {
			inserter.finish();
			count += inserter.count;
		}
		for (final Updater<T> updater : updaters.values()) {
			updater.finish();
			count += updater.count;
		}
		deleter.finish();
		count += deleter.count;
		return count;
	}

}
