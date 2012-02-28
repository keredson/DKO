package org.nosco;

/**
 * Defines constants for use in this API.
 *
 * @author Derek Anderson
 */
public class Constants {

	public static enum DIRECTION {
		ASCENDING,
		DESCENDING
	}

	public enum DB_TYPE {MYSQL, SQLSERVER}

	public static final String PROP_LOG_SQL = "org.nosco.log_sql";

}

