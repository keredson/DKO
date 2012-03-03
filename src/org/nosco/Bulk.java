package org.nosco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.nosco.Constants.DB_TYPE;
import org.nosco.util.Misc;

/**
 * Provides optimized methods for CRUD operations on collections. &nbsp;
 * Note that if you find yourself doing the following:
 * <pre>   {@code List<MyObject> toDelete = MyObject.ALL.where(conditions...).asList();
 *   new Bulk(ds).deleteAll();}</pre>
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
		int count = 0;
		boolean first = true;
		Connection conn = null;
		Field[] fields = null;
		PreparedStatement psInsert = null;

		try {
			for (T table : iterable) {
				if (first) {
					first = false;
					QueryImpl<T> q = (QueryImpl<T>) new QueryImpl<T>(table.getClass()).use(ds);
					conn = q.getConnRW();
					fields = table.FIELDS();
					psInsert = createInsertPS(conn, q, table, fields);
				}
				int i=1;
				for (Field field : fields) {
					Object o = table.get(field);
					// hack for sql server which otherwise gives:
					// com.microsoft.sqlserver.jdbc.SQLServerException:
					// The conversion from UNKNOWN to UNKNOWN is unsupported.
					if (o instanceof Character) psInsert.setString(i++, o.toString());
					else psInsert.setObject(i++, o);
				}
				psInsert.execute();
				count += psInsert.getUpdateCount();

			}
			psInsert.close();
			if (!ThreadContext.inTransaction(ds)) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;
		} catch (SQLException e) {
			if (!ThreadContext.inTransaction(ds) && !conn.getAutoCommit()) conn.rollback();
			throw e;
		} finally {
			if (psInsert != null && !psInsert.isClosed()) psInsert.close();
			if (!ThreadContext.inTransaction(ds)) conn.close();
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
	public <T extends Table> int updateAll(Iterable<T> iterable) throws SQLException {
		int count = 0;
		boolean first = true;
		Connection conn = null;
		Field[] fields = null;
		PreparedStatement psUpdate = null;
		Field[] pks = null;

		try {
			for (T table : iterable) {
				if (first) {
					first = false;
					QueryImpl<T> q = (QueryImpl<T>) new QueryImpl<T>(table.getClass()).use(ds);
					conn = q.getConnRW();
					fields = table.FIELDS();
					pks = table.PK() == null ? null : table.PK().GET_FIELDS();
					psUpdate = createUpdatePS(conn, q, table, fields, pks);
				}
				int i=1;
				for (Field field : fields) {
					Object o = table.get(field);
					// hack for sql server which otherwise gives:
					// com.microsoft.sqlserver.jdbc.SQLServerException:
					// The conversion from UNKNOWN to UNKNOWN is unsupported.
					if (o instanceof Character) psUpdate.setString(i++, o.toString());
					else psUpdate.setObject(i++, o);
				}
				for (Field field : pks) {
					Object o = table.get(field);
					// hack for sql server which otherwise gives:
					// com.microsoft.sqlserver.jdbc.SQLServerException:
					// The conversion from UNKNOWN to UNKNOWN is unsupported.
					if (o instanceof Character) psUpdate.setString(i++, o.toString());
					else psUpdate.setObject(i++, o);
				}
				psUpdate.execute();
				count += psUpdate.getUpdateCount();

			}
			psUpdate.close();
			if (!ThreadContext.inTransaction(ds)) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;
		} catch (SQLException e) {
			if (!ThreadContext.inTransaction(ds) && !conn.getAutoCommit()) conn.rollback();
			throw e;
		} finally {
			if (psUpdate != null && !psUpdate.isClosed()) psUpdate.close();
			if (!ThreadContext.inTransaction(ds)) conn.close();
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
		Field[] fields = null;
		PreparedStatement psInsert = null;
		PreparedStatement psUpdate = null;
		Field[] pks = null;

		try {
			for (T table : iterable) {
				if (first) {
					first = false;
					QueryImpl<T> q = (QueryImpl<T>) new QueryImpl<T>(table.getClass()).use(ds);
					conn = q.getConnRW();
					fields = table.FIELDS();
					psInsert = createInsertPS(conn, q, table, fields);
					pks = table.PK() == null ? null : table.PK().GET_FIELDS();
					psUpdate = createUpdatePS(conn, q, table, fields, pks);
				}
				int i=1;
				for (Field field : fields) {
					Object o = table.get(field);
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
					for (Field field : fields) {
						Object o = table.get(field);
						// hack for sql server which otherwise gives:
						// com.microsoft.sqlserver.jdbc.SQLServerException:
						// The conversion from UNKNOWN to UNKNOWN is unsupported.
						if (o instanceof Character) psUpdate.setString(j++, o.toString());
						else psUpdate.setObject(j++, o);
					}
					for (Field field : pks) {
						Object o = table.get(field);
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
			if (!ThreadContext.inTransaction(ds)) {
				if (!conn.getAutoCommit()) conn.commit();
				conn.close();
			}
			return count;
		} catch (SQLException e) {
			if (!ThreadContext.inTransaction(ds) && !conn.getAutoCommit()) conn.rollback();
			throw e;
		} finally {
			if (psInsert != null && !psInsert.isClosed()) psInsert.close();
			if (psUpdate != null && !psUpdate.isClosed()) psUpdate.close();
			if (!ThreadContext.inTransaction(ds)) conn.close();
		}
	}

	private <T extends Table> PreparedStatement createInsertPS(Connection conn,
			QueryImpl<T> q, Table table, Field[] fields) throws SQLException {
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
			QueryImpl<T> q, Table table, Field[] fields, Field[] pks) throws SQLException {
		if (pks == null || pks.length == 0) throw new RuntimeException("cannot mass update on a table without primary keys");
		PreparedStatement ps;
		String sep = q.getDBType()==DB_TYPE.SQLSERVER ? ".dbo." : ".";
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(ThreadContext.getDatabaseOverride(ds, table.SCHEMA_NAME())
				+sep+ table.TABLE_NAME());
		sb.append(" set ");
		sb.append(Misc.join("=? ,", fields));
		sb.append("=?  where ");
		sb.append(Misc.join("=? ,", pks));
		sb.append("=?;");
		String sql = sb.toString();
		Misc.log(sql, null);
		ps = conn.prepareStatement(sql);
		return ps;
	}

}
