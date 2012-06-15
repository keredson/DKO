package org.nosco;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

/**
 * This class has been refactored into the {@code Context} class. &nbsp;
 * Existing methods forward to their replacements where possible.
 * This class will be removed in an upcoming version.
 *
 * @author Derek Anderson
 * @deprecated please use Context.getThreadContext()
 */
public class ThreadContext {

	private ThreadContext() {}

	/**
	 * A convenience method to the new location for this functionality.
	 * Will be removed in an upcoming version.
	 * @param ds
	 * @return
	 * @deprecated please use Context.inTransaction(ds);
	 */
	public static boolean inTransaction(DataSource ds) {
		return Context.inTransaction(ds);
	}

	/**
	 * A convenience method to the new location for this functionality.
	 * Will be removed in an upcoming version.
	 * @param ds
	 * @return
	 * @throws SQLException
	 * @deprecated please use Context.getThreadContext().startTransaction(ds);
	 */
	public static boolean startTransaction(DataSource ds) throws SQLException {
		return Context.getThreadContext().startTransaction(ds);
	}

	/**
	 * A convenience method to the new location for this functionality.
	 * Will be removed in an upcoming version.
	 * @param ds
	 * @return
	 * @throws SQLException
	 * @deprecated please use Context.getThreadContext().commitTransaction(ds);
	 */
	public static boolean commitTransaction(DataSource ds) throws SQLException {
		return Context.getThreadContext().commitTransaction(ds);
	}

	/**
	 * A convenience method to the new location for this functionality.
	 * Will be removed in an upcoming version.
	 * @param ds
	 * @return
	 * @throws SQLException
	 * @deprecated please use Context.getThreadContext().rollbackTransactionThrowSQLException(ds);
	 */
	public static boolean rollbackTransaction(DataSource ds) throws SQLException {
		return Context.getThreadContext().rollbackTransactionThrowSQLException(ds);
	}

	/**
	 * A convenience method to the new location for this functionality.
	 * Will be removed in an upcoming version.
	 * @param ds
	 * @return
	 * @throws SQLException
	 * @deprecated please use Context.getThreadContext().rollbackTransaction(ds);
	 */
	public static boolean rollbackTransactionIgnoreException(DataSource ds) {
		return Context.getThreadContext().rollbackTransaction(ds);
	}

	/**
	 * A convenience method to the new location for this functionality.
	 * Will be removed in an upcoming version.
	 * @param ds
	 * @return null is not currently in a transaction
	 * @deprecated please use Context.getConnection(ds);
	 */
	public static Connection getConnection(DataSource ds) {
		return Context.getConnection(ds);
	}

	/**
	 * No longer functional.
	 * @deprecated please use the {@code ConnectionCountingDataSource} wrapper
	 */
	public static long getConnectionCount() {
		return 0;
	}

	/**
	 * No longer functional.
	 * @deprecated please use the {@code ConnectionCountingDataSource} wrapper
	 */
	static long incrementConnectionCount() {
		return 0;
	}

	/**
	 * No longer functional.
	 * @deprecated please use the {@code ConnectionCountingDataSource} wrapper
	 */
	public static void setConnectionCount(long count) {}

	/**
	 * A convenience method to the new location for this functionality.
	 * Will be removed in an upcoming version.
	 * @param ds
	 * @param oldName
	 * @param newName
	 * @deprecated please use {@code Context.getThreadContext().overrideSchema(ds, oldName, newName)}
	 */
	public static void setDatabaseOverride(DataSource ds, String oldName, String newName) {
		Context.getThreadContext().overrideSchema(ds, oldName, newName);
	}

	/**
	 * A convenience method to the new location for this functionality.
	 * Will be removed in an upcoming version.
	 * @param ds
	 * @param name
	 * @return
	 * @deprecated please use {@code Context.getSchemaToUse(ds, name)}
	 */
	public static String getDatabaseOverride(DataSource ds, String name) {
		return Context.getSchemaToUse(ds, name);
	}

}
