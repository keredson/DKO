package org.kered.dko;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.sql.DataSource;

import org.kered.dko.datasource.JDBCDriverDataSource;
import org.kered.dko.datasource.MatryoshkaDataSource;
import org.kered.dko.datasource.UnClosableConnection;

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

	public static enum CALENDAR {
		NANOSECOND,
		MICROSECOND,
		MILLISECOND,
		SECOND,
		MINUTE,
		HOUR,
		DAY,
		WEEKDAY,
		WEEK,
		MONTH,
		QUARTER,
		YEAR,;
	}

	/**
	 * Used for tweaking generated SQL by database type.
	 * You can override the detected type by passing one of these constants
	 * into Query.use(DB_TYPE type).
	 *
	 * @author Derek Anderson
	 */
	public enum DB_TYPE {
		MYSQL,
		SQLSERVER,
		POSTGRES,
		ORACLE,
		SQLITE3,
		HSQL,
		DERBY,
		SQL92;

		private static Map<DataSource, DB_TYPE> cache = Collections
				.synchronizedMap(new WeakHashMap<DataSource, DB_TYPE>());

		static DB_TYPE detect(final DataSource ds) {

			// see if we've already typed this one
			final DB_TYPE cached = cache.get(ds);
			if (cached != null) return cached;

			// unwrap known datasource layers
			DataSource underlying = ds;
			while (true) {
				if (underlying instanceof MatryoshkaDataSource) {
					underlying = ((MatryoshkaDataSource)underlying).getPrimaryUnderlying();
					continue;
				}
				if (underlying == null) return null;
				break;
			}

			// known cases
			if (underlying instanceof JDBCDriverDataSource) {
			    final JDBCDriverDataSource jds = (JDBCDriverDataSource) underlying;
			    if (jds.getDBType()!=null) return jds.getDBType();
			    String url = jds.getURL();
				if (url.startsWith("jdbc:sqlserver")) return SQLSERVER;
				if (url.startsWith("jdbc:hsql")) return HSQL;
				if (url.startsWith("jdbc:derby")) return DERBY;
			}

			// is the class recognizable?
			final String className = underlying.getClass().getName();
			if (className.contains("SQLServer")) {
				cache.put(ds, SQLSERVER);
				return SQLSERVER;
			}
			if (className.contains("org.hsqldb")) {
				cache.put(ds, HSQL);
				return HSQL;
			}
			if (className.contains("com.mysql")) {
				cache.put(ds, MYSQL);
				return MYSQL;
			}
			if (className.contains("org.sqlite")) {
				cache.put(ds, SQLITE3);
				return SQLITE3;
			}
			if (className.contains("org.postgresql")) {
				cache.put(ds, POSTGRES);
				return POSTGRES;
			}
			if (className.startsWith("oracle.jdbc")) {
				cache.put(ds, ORACLE);
				return ORACLE;
			}

			// attempt to detect from a connection
			Connection conn = null;
			try {
				conn = ds.getConnection();
				final DB_TYPE type = detect(conn);
				if (type != null) {
					cache.put(ds, type);
					return type;
				}
			} catch (final SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}

			// unknown
			System.err.println("unknown db type for DataSource: "+ ds);
			return null;
		}

		static DB_TYPE detect(final Connection conn) throws SQLException {

			if (conn instanceof UnClosableConnection) {
				return detect(((UnClosableConnection)conn).getUnderlyingConnection());
			}

			// try from the class name
			final String className = conn.getClass().getName();
			if (className.contains("SQLServer")) return SQLSERVER;
			if (className.contains("SQLDroidConnection")) return SQLITE3;
			if (className.contains("SQLiteJDBC")) return SQLITE3;
			if (className.startsWith("oracle")) return ORACLE;
			if (className.startsWith("org.apache.derby")) return DERBY;

			// try from the jdbc metadata
			final DatabaseMetaData metaData = conn.getMetaData();
			String driver = null;
			String url = null;
			if (metaData != null) {
				driver = metaData.getDriverName();
				if (driver.contains("sqlserver")) return SQLSERVER;
				if (driver.contains("hsqldb")) return HSQL;
				url = metaData.getURL();
				if (url.startsWith("jdbc:sqlserver")) return SQLSERVER;
				if (url.startsWith("jdbc:hsql")) return HSQL;
				if (url.startsWith("jdbc:derby")) return DERBY;
			}

			System.err.println("unknown db type for Connection: "+ conn
					+" (driver:"+ driver +", url:"+ url +")");
			return null;
		}

		String getDatabaseTableSeparator() {
			return this == SQLSERVER ? ".dbo." : ".";
		}

	}

	@SuppressWarnings("serial")
	final public static Set<String> KEYWORDS_JAVA = Collections.unmodifiableSet(new HashSet<String>() {{
		final String[] kws = {"abstract", "continue", "for", "new", "switch", "assert",
				"default", "goto", "package", "synchronized", "boolean", "do",
				"if", "private", "this", "break", "double", "implements", "protected",
				"throw", "byte", "else", "import", "public", "throws", "case", "enum",
				"instanceof", "return", "transient", "catch", "extends", "int",
				"short", "try", "char", "final", "interface", "static", "void",
				"class", "finally", "long", "strictfp", "volatile", "const", "float",
				"native", "super", "while"};
		for (final String kw : kws) add(kw.toLowerCase());
	}});

	@SuppressWarnings("serial")
	final public static Set<String> KEYWORDS_SQL92 = Collections.unmodifiableSet(new HashSet<String>() {{
		final String[] kws = {"ABSOLUTE", "ACTION", "ADD", "ALL", "ALLOCATE", "ALTER", "AND", "ANY", "ARE", "AS", "ASC", "ASSERTION", "AT", "AUTHORIZATION", "AVG", "BEGIN", "BETWEEN", "BIT", "BIT_LENGTH", "BOTH", "BY", "CALL", "CASCADE", "CASCADED", "CASE", "CAST", "CATALOG", "CHAR", "CHAR_LENGTH", "CHARACTER", "CHARACTER_LENGTH", "CHECK", "CLOSE", "COALESCE", "COLLATE", "COLLATION", "COLUMN", "COMMIT", "CONDITION", "CONNECT", "CONNECTION", "CONSTRAINT", "CONSTRAINTS", "CONTAINS", "CONTINUE", "CONVERT", "CORRESPONDING", "COUNT", "CREATE", "CROSS", "CURRENT", "CURRENT_DATE", "CURRENT_PATH", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DATE", "DAY", "DEALLOCATE", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DEFERRABLE", "DEFERRED", "DELETE", "DESC", "DESCRIBE", "DESCRIPTOR", "DETERMINISTIC", "DIAGNOSTICS", "DISCONNECT", "DISTINCT", "DO", "DOMAIN", "DOUBLE", "DROP", "ELSE", "ELSEIF", "END", "ESCAPE", "EXCEPT", "EXCEPTION", "EXEC", "EXECUTE", "EXISTS", "EXIT", "EXTERNAL", "EXTRACT", "FALSE", "FETCH", "FIRST", "FLOAT", "FOR", "FOREIGN", "FOUND", "FROM", "FULL", "FUNCTION", "GET", "GLOBAL", "GO", "GOTO", "GRANT", "GROUP", "HANDLER", "HAVING", "HOUR", "IDENTITY", "IF", "IMMEDIATE", "IN", "INDICATOR", "INITIALLY", "INNER", "INOUT", "INPUT", "INSENSITIVE", "INSERT", "INT", "INTEGER", "INTERSECT", "INTERVAL", "INTO", "IS", "ISOLATION", "JOIN", "KEY", "LANGUAGE", "LAST", "LEADING", "LEAVE", "LEFT", "LEVEL", "LIKE", "LOCAL", "LOOP", "LOWER", "MATCH", "MAX", "MIN", "MINUTE", "MODULE", "MONTH", "NAMES", "NATIONAL", "NATURAL", "NCHAR", "NEXT", "NO", "NOT", "NULL", "NULLIF", "NUMERIC", "OCTET_LENGTH", "OF", "ON", "ONLY", "OPEN", "OPTION", "OR", "ORDER", "OUT", "OUTER", "OUTPUT", "OVERLAPS", "PAD", "PARAMETER", "PARTIAL", "PATH", "POSITION", "PRECISION", "PREPARE", "PRESERVE", "PRIMARY", "PRIOR", "PRIVILEGES", "PROCEDURE", "PUBLIC", "READ", "REAL", "REFERENCES", "RELATIVE", "REPEAT", "RESIGNAL", "RESTRICT", "RETURN", "RETURNS", "REVOKE", "RIGHT", "ROLLBACK", "ROUTINE", "ROWS", "SCHEMA", "SCROLL", "SECOND", "SECTION", "SELECT", "SESSION", "SESSION_USER", "SET", "SIGNAL", "SIZE", "SMALLINT", "SOME", "SPACE", "SPECIFIC", "SQL", "SQLCODE", "SQLERROR", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SUBSTRING", "SUM", "SYSTEM_USER", "TABLE", "TEMPORARY", "THEN", "TIME", "TIMESTAMP", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TO", "TRAILING", "TRANSACTION", "TRANSLATE", "TRANSLATION", "TRIM", "TRUE", "UNDO", "UNION", "UNIQUE", "UNKNOWN", "UNTIL", "UPDATE", "UPPER", "USAGE", "USER", "USING", "VALUE", "VALUES", "VARCHAR", "VARYING", "VIEW", "WHEN", "WHENEVER", "WHERE", "WHILE", "WITH", "WORK", "WRITE", "YEAR", "ZONE"};
		for (final String kw : kws) add(kw.toLowerCase());
	}});

	@SuppressWarnings("serial")
	final public static Set<String> KEYWORDS_SQL99 = Collections.unmodifiableSet(new HashSet<String>() {{
		final String[] kws = {"ABSOLUTE", "ACTION", "ADD", "AFTER", "ALL", "ALLOCATE", "ALTER", "AND", "ANY", "ARE", "ARRAY", "AS", "ASC", "ASENSITIVE", "ASSERTION", "ASYMMETRIC", "AT", "ATOMIC", "AUTHORIZATION", "BEFORE", "BEGIN", "BETWEEN", "BINARY", "BIT", "BLOB", "BOOLEAN", "BOTH", "BREADTH", "BY", "CALL", "CASCADE", "CASCADED", "CASE", "CAST", "CATALOG", "CHAR", "CHARACTER", "CHECK", "CLOB", "CLOSE", "COLLATE", "COLLATION", "COLUMN", "COMMIT", "CONDITION", "CONNECT", "CONNECTION", "CONSTRAINT", "CONSTRAINTS", "CONSTRUCTOR", "CONTINUE", "CORRESPONDING", "CREATE", "CROSS", "CUBE", "CURRENT", "CURRENT_DATE", "CURRENT_DEFAULT_TRANSFORM_GROUP", "CURRENT_PATH", "CURRENT_ROLE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_TRANSFORM_GROUP_FOR_TYPE", "CURRENT_USER", "CURSOR", "CYCLE", "DATA", "DATE", "DAY", "DEALLOCATE", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DEFERRABLE", "DEFERRED", "DELETE", "DEPTH", "DEREF", "DESC", "DESCRIBE", "DESCRIPTOR", "DETERMINISTIC", "DIAGNOSTICS", "DISCONNECT", "DISTINCT", "DO", "DOMAIN", "DOUBLE", "DROP", "DYNAMIC", "EACH", "ELSE", "ELSEIF", "END", "EQUALS", "ESCAPE", "EXCEPT", "EXCEPTION", "EXEC", "EXECUTE", "EXISTS", "EXIT", "EXTERNAL", "FALSE", "FETCH", "FILTER", "FIRST", "FLOAT", "FOR", "FOREIGN", "FOUND", "FREE", "FROM", "FULL", "FUNCTION", "GENERAL", "GET", "GLOBAL", "GO", "GOTO", "GRANT", "GROUP", "GROUPING", "HANDLER", "HAVING", "HOLD", "HOUR", "IDENTITY", "IF", "IMMEDIATE", "IN", "INDICATOR", "INITIALLY", "INNER", "INOUT", "INPUT", "INSENSITIVE", "INSERT", "INT", "INTEGER", "INTERSECT", "INTERVAL", "INTO", "IS", "ISOLATION", "ITERATE", "JOIN", "KEY", "LANGUAGE", "LARGE", "LAST", "LATERAL", "LEADING", "LEAVE", "LEFT", "LEVEL", "LIKE", "LOCAL", "LOCALTIME", "LOCALTIMESTAMP", "LOCATOR", "LOOP", "MAP", "MATCH", "METHOD", "MINUTE", "MODIFIES", "MODULE", "MONTH", "NAMES", "NATIONAL", "NATURAL", "NCHAR", "NCLOB", "NEW", "NEXT", "NO", "NONE", "NOT", "NULL", "NUMERIC", "OBJECT", "OF", "OLD", "ON", "ONLY", "OPEN", "OPTION", "OR", "ORDER", "ORDINALITY", "OUT", "OUTER", "OUTPUT", "OVER", "OVERLAPS", "PAD", "PARAMETER", "PARTIAL", "PARTITION", "PATH", "PRECISION", "PREPARE", "PRESERVE", "PRIMARY", "PRIOR", "PRIVILEGES", "PROCEDURE", "PUBLIC", "RANGE", "READ", "READS", "REAL", "RECURSIVE", "REF", "REFERENCES", "REFERENCING", "RELATIVE", "RELEASE", "REPEAT", "RESIGNAL", "RESTRICT", "RESULT", "RETURN", "RETURNS", "REVOKE", "RIGHT", "ROLE", "ROLLBACK", "ROLLUP", "ROUTINE", "ROW", "ROWS", "SAVEPOINT", "SCHEMA", "SCOPE", "SCROLL", "SEARCH", "SECOND", "SECTION", "SELECT", "SENSITIVE", "SESSION", "SESSION_USER", "SET", "SETS", "SIGNAL", "SIMILAR", "SIZE", "SMALLINT", "SOME", "SPACE", "SPECIFIC", "SPECIFICTYPE", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "START", "STATE", "STATIC", "SYMMETRIC", "SYSTEM", "SYSTEM_USER", "TABLE", "TEMPORARY", "THEN", "TIME", "TIMESTAMP", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TO", "TRAILING", "TRANSACTION", "TRANSLATION", "TREAT", "TRIGGER", "TRUE", "UNDER", "UNDO", "UNION", "UNIQUE", "UNKNOWN", "UNNEST", "UNTIL", "UPDATE", "USAGE", "USER", "USING", "VALUE", "VALUES", "VARCHAR", "VARYING", "VIEW", "WHEN", "WHENEVER", "WHERE", "WHILE", "WINDOW", "WITH", "WITHIN", "WITHOUT", "WORK", "WRITE", "YEAR", "ZONE"};
		for (final String kw : kws) add(kw.toLowerCase());
	}});

	@SuppressWarnings("serial")
	final public static Set<String> KEYWORDS_SQL2003 = Collections.unmodifiableSet(new HashSet<String>() {{
		final String[] kws = {"ADD", "ALL", "ALLOCATE", "ALTER", "AND", "ANY", "ARE", "ARRAY", "AS", "ASENSITIVE", "ASYMMETRIC", "AT", "ATOMIC", "AUTHORIZATION", "BEGIN", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOOLEAN", "BOTH", "BY", "CALL", "CALLED", "CASCADED", "CASE", "CAST", "CHAR", "CHARACTER", "CHECK", "CLOB", "CLOSE", "COLLATE", "COLUMN", "COMMIT", "CONDITION", "CONNECT", "CONSTRAINT", "CONTINUE", "CORRESPONDING", "CREATE", "CROSS", "CUBE", "CURRENT", "CURRENT_DATE", "CURRENT_DEFAULT_TRANSFORM_GROUP", "CURRENT_PATH", "CURRENT_ROLE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_TRANSFORM_GROUP_FOR_TYPE", "CURRENT_USER", "CURSOR", "CYCLE", "DATE", "DAY", "DEALLOCATE", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELETE", "DEREF", "DESCRIBE", "DETERMINISTIC", "DISCONNECT", "DISTINCT", "DO", "DOUBLE", "DROP", "DYNAMIC", "EACH", "ELEMENT", "ELSE", "ELSEIF", "END", "ESCAPE", "EXCEPT", "EXEC", "EXECUTE", "EXISTS", "EXIT", "EXTERNAL", "FALSE", "FETCH", "FILTER", "FLOAT", "FOR", "FOREIGN", "FREE", "FROM", "FULL", "FUNCTION", "GET", "GLOBAL", "GRANT", "GROUP", "GROUPING", "HANDLER", "HAVING", "HOLD", "HOUR", "IDENTITY", "IF", "IMMEDIATE", "IN", "INDICATOR", "INNER", "INOUT", "INPUT", "INSENSITIVE", "INSERT", "INT", "INTEGER", "INTERSECT", "INTERVAL", "INTO", "IS", "ITERATE", "JOIN", "LANGUAGE", "LARGE", "LATERAL", "LEADING", "LEAVE", "LEFT", "LIKE", "LOCAL", "LOCALTIME", "LOCALTIMESTAMP", "LOOP", "MATCH", "MEMBER", "MERGE", "METHOD", "MINUTE", "MODIFIES", "MODULE", "MONTH", "MULTISET", "NATIONAL", "NATURAL", "NCHAR", "NCLOB", "NEW", "NO", "NONE", "NOT", "NULL", "NUMERIC", "OF", "OLD", "ON", "ONLY", "OPEN", "OR", "ORDER", "OUT", "OUTER", "OUTPUT", "OVER", "OVERLAPS", "PARAMETER", "PARTITION", "PRECISION", "PREPARE", "PRIMARY", "PROCEDURE", "RANGE", "READS", "REAL", "RECURSIVE", "REF", "REFERENCES", "REFERENCING", "RELEASE", "REPEAT", "RESIGNAL", "RESULT", "RETURN", "RETURNS", "REVOKE", "RIGHT", "ROLLBACK", "ROLLUP", "ROW", "ROWS", "SAVEPOINT", "SCOPE", "SCROLL", "SEARCH", "SECOND", "SELECT", "SENSITIVE", "SESSION_USER", "SET", "SIGNAL", "SIMILAR", "SMALLINT", "SOME", "SPECIFIC", "SPECIFICTYPE", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "START", "STATIC", "SUBMULTISET", "SYMMETRIC", "SYSTEM", "SYSTEM_USER", "TABLE", "TABLESAMPLE", "THEN", "TIME", "TIMESTAMP", "TIMEZONE_HOUR", "TIMEZONE_MINUTE", "TO", "TRAILING", "TRANSLATION", "TREAT", "TRIGGER", "TRUE", "UNDO", "UNION", "UNIQUE", "UNKNOWN", "UNNEST", "UNTIL", "UPDATE", "USER", "USING", "VALUE", "VALUES", "VARCHAR", "VARYING", "WHEN", "WHENEVER", "WHERE", "WHILE", "WINDOW", "WITH", "WITHIN", "WITHOUT", "YEAR"};
		for (final String kw : kws) add(kw.toLowerCase());
	}});

	@SuppressWarnings("serial")
	final public static Set<String> KEYWORDS_SQLSERVER = Collections.unmodifiableSet(new HashSet<String>() {{
		final String[] kws = {"ADD", "EXCEPT", "PERCENT", "ALL", "EXEC", "PLAN", "ALTER", "EXECUTE", "PRECISION", "AND", "EXISTS", "PRIMARY", "ANY", "EXIT", "PRINT", "AS", "FETCH", "PROC", "ASC", "FILE", "PROCEDURE", "AUTHORIZATION", "FILLFACTOR", "PUBLIC", "BACKUP", "FOR", "RAISERROR", "BEGIN", "FOREIGN", "READ", "BETWEEN", "FREETEXT", "READTEXT", "BREAK", "FREETEXTTABLE", "RECONFIGURE", "BROWSE", "FROM", "REFERENCES", "BULK", "FULL", "REPLICATION", "BY", "FUNCTION", "RESTORE", "CASCADE", "GOTO", "RESTRICT", "CASE", "GRANT", "RETURN", "CHECK", "GROUP", "REVOKE", "CHECKPOINT", "HAVING", "RIGHT", "CLOSE", "HOLDLOCK", "ROLLBACK", "CLUSTERED", "IDENTITY", "ROWCOUNT", "COALESCE", "IDENTITY_INSERT", "ROWGUIDCOL", "COLLATE", "IDENTITYCOL", "RULE", "COLUMN", "IF", "SAVE", "COMMIT", "IN", "SCHEMA", "COMPUTE", "INDEX", "SELECT", "CONSTRAINT", "INNER", "SESSION_USER", "CONTAINS", "INSERT", "SET", "CONTAINSTABLE", "INTERSECT", "SETUSER", "CONTINUE", "INTO", "SHUTDOWN", "CONVERT", "IS", "SOME", "CREATE", "JOIN", "STATISTICS", "CROSS", "KEY", "SYSTEM_USER", "CURRENT", "KILL", "TABLE", "CURRENT_DATE", "LEFT", "TEXTSIZE", "CURRENT_TIME", "LIKE", "THEN", "CURRENT_TIMESTAMP", "LINENO", "TO", "CURRENT_USER", "LOAD", "TOP", "CURSOR", "NATIONAL", "TRAN", "DATABASE", "NOCHECK", "TRANSACTION", "DBCC", "NONCLUSTERED", "TRIGGER", "DEALLOCATE", "NOT", "TRUNCATE", "DECLARE", "NULL", "TSEQUAL", "DEFAULT", "NULLIF", "UNION", "DELETE", "OF", "UNIQUE", "DENY", "OFF", "UPDATE", "DESC", "OFFSETS", "UPDATETEXT", "DISK", "ON", "USE", "DISTINCT", "OPEN", "USER", "DISTRIBUTED", "OPENDATASOURCE", "VALUES", "DOUBLE", "OPENQUERY", "VARYING", "DROP", "OPENROWSET", "VIEW", "DUMMY", "OPENXML", "WAITFOR", "DUMP", "OPTION", "WHEN", "ELSE", "OR", "WHERE", "END", "ORDER", "WHILE", "ERRLVL", "OUTER", "WITH", "ESCAPE", "OVER", "WRITETEXT"};
		for (final String kw : kws) add(kw.toLowerCase());
	}});

	@SuppressWarnings("serial")
	final public static Set<String> KEYWORDS_MYSQL = Collections.unmodifiableSet(new HashSet<String>() {{
		final String[] kws = {"ADD", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ASENSITIVE", "BEFORE", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOTH", "BY", "CALL", "CASCADE", "CASE", "CHANGE", "CHAR", "CHARACTER", "CHECK", "COLLATE", "COLUMN", "CONDITION", "CONSTRAINT", "CONTINUE", "CONVERT", "CREATE", "CROSS", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DATABASE", "DATABASES", "DAY_HOUR", "DAY_MICROSECOND", "DAY_MINUTE", "DAY_SECOND", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELAYED", "DELETE", "DESC", "DESCRIBE", "DETERMINISTIC", "DISTINCT", "DISTINCTROW", "DIV", "DOUBLE", "DROP", "DUAL", "EACH", "ELSE", "ELSEIF", "ENCLOSED", "ESCAPED", "EXISTS", "EXIT", "EXPLAIN", "FALSE", "FETCH", "FLOAT", "FLOAT4", "FLOAT8", "FOR", "FORCE", "FOREIGN", "FROM", "FULLTEXT", "GRANT", "GROUP", "HAVING", "HIGH_PRIORITY", "HOUR_MICROSECOND", "HOUR_MINUTE", "HOUR_SECOND", "IF", "IGNORE", "IN", "INDEX", "INFILE", "INNER", "INOUT", "INSENSITIVE", "INSERT", "INT", "INT1", "INT2", "INT3", "INT4", "INT8", "INTEGER", "INTERVAL", "INTO", "IS", "ITERATE", "JOIN", "KEY", "KEYS", "KILL", "LEADING", "LEAVE", "LEFT", "LIKE", "LIMIT", "LINES", "LOAD", "LOCALTIME", "LOCALTIMESTAMP", "LOCK", "LONG", "LONGBLOB", "LONGTEXT", "LOOP", "LOW_PRIORITY", "MATCH", "MEDIUMBLOB", "MEDIUMINT", "MEDIUMTEXT", "MIDDLEINT", "MINUTE_MICROSECOND", "MINUTE_SECOND", "MOD", "MODIFIES", "NATURAL", "NOT", "NO_WRITE_TO_BINLOG", "NULL", "NUMERIC", "ON", "OPTIMIZE", "OPTION", "OPTIONALLY", "OR", "ORDER", "OUT", "OUTER", "OUTFILE", "PRECISION", "PRIMARY", "PROCEDURE", "PURGE", "READ", "READS", "REAL", "REFERENCES", "REGEXP", "RELEASE", "RENAME", "REPEAT", "REPLACE", "REQUIRE", "RESTRICT", "RETURN", "REVOKE", "RIGHT", "RLIKE", "SCHEMA", "SCHEMAS", "SECOND_MICROSECOND", "SELECT", "SENSITIVE", "SEPARATOR", "SET", "SHOW", "SMALLINT", "SONAME", "SPATIAL", "SPECIFIC", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SQL_BIG_RESULT", "SQL_CALC_FOUND_ROWS", "SQL_SMALL_RESULT", "SSL", "STARTING", "STRAIGHT_JOIN", "TABLE", "TERMINATED", "THEN", "TINYBLOB", "TINYINT", "TINYTEXT", "TO", "TRAILING", "TRIGGER", "TRUE", "UNDO", "UNION", "UNIQUE", "UNLOCK", "UNSIGNED", "UPDATE", "USAGE", "USE", "USING", "UTC_DATE", "UTC_TIME", "UTC_TIMESTAMP", "VALUES", "VARBINARY", "VARCHAR", "VARCHARACTER", "VARYING", "WHEN", "WHERE", "WHILE", "WITH", "WRITE", "XOR", "YEAR_MONTH", "ZEROFILL"};
		for (final String kw : kws) add(kw.toLowerCase());
	}});

	@SuppressWarnings("serial")
	final public static Set<String> INVALID_FIELD_NAMES = Collections.unmodifiableSet(new HashSet<String>() {{
		final String[] kws = {"ALL", "FIELDS", "PK", "PKS", "FKS"};
		for (final String kw : kws) add(kw.toUpperCase());
	}});


	/**
	 * Writes the generated SQL to stderr.  Now all SQL is logged by default
	 * to: {@code java.util.logging.Logger.getLogger("org.nosco.sql");}
	 */
	@Deprecated
	public static final String PROP_LOG_SQL = "org.nosco.log_sql";

	/**
	 * Writes the generated SQL to stderr.  Now all SQL is logged by default
	 * to: {@code java.util.logging.Logger.getLogger("org.nosco.sql");}
	 */
	@Deprecated
	public static final String PROP_LOG = "org.nosco.log";

	/**
	 * Writes the generated SQL to stderr.  Now all SQL is logged by default
	 * to: {@code java.util.logging.Logger.getLogger("org.nosco.sql");}
	 */
	@Deprecated
	public static final String PROP_LOG_SQL_DKO = "org.kered.dko.log_sql";

	/**
	 * A Java property that controls the select statement optimizations.
	 * These optimizations filter out fields from generated queries based on which
	 * columns have been accessed over the life of all the objects created from the query.
	 * Enabled by default.
	 */
	public static final String PROPERTY_OPTIMIZE_SELECT_FIELDS = "org.kered.dko.optimize_select_fields";
	@Deprecated
	static final String PROPERTY_OPTIMIZE_SELECT_FIELDS_OLD = "org.nosco.optimize_select_fields";

	public static final String PROPERTY_DELETE_LOCAL_TMP_DATABASES = "org.kered.dko.delete_local_tmp_databases";

	/**
	 * A Java property that controls the automatic warning of excessive lazy loading
	 * (which can seriously degrade both application and database performance).
	 * Enabled by default.
	 */
	public static final String PROPERTY_WARN_EXCESSIVE_LAZY_LOADING = "org.kered.dko.warn_excessive_lazy_loading";
	@Deprecated
	static final String PROPERTY_WARN_EXCESSIVE_LAZY_LOADING_OLD = "org.nosco.warn_excessive_lazy_loading";

	/**
	 * A Java property that controls where Nosco keeps cached performance metrics.
	 * By default: ~/.dko_optimizations
	 */
	@Deprecated
	public static final String PROPERTY_CACHE_DIR = "org.kered.dko.cache_dir";
	@Deprecated
	static final String PROPERTY_CACHE_DIR_OLD = "org.nosco.cache_dir";

	/**
	 * A Java property (a filename) that controls where DKO persists performance metrics.
	 * By default: ~/.dko_persistence_db
	 */
	public static final String PROPERTY_PERSISTENCE_DB = "org.kered.dko.persistence_db";

	public static final String PROPERTY_USE_PERSISTENCE_DB = "org.kered.dko.use_persistence_db";

	static enum JOIN_TYPE {

		LEFT("left join"),
		RIGHT("right join"),
		INNER("inner join"),
		OUTER("outer join"),
		CROSS("cross join");

		private final String s;
		JOIN_TYPE(final String s) {
		    this.s = s;
		}
		@Override
		public String toString() {
		    return s;
		}
	}

}

