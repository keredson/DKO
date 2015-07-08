package org.kered.dko.ant;

import org.apache.tools.ant.Task;

public class SchemaExtractor extends Task {
	
	SchemaExtractorBase base = new SchemaExtractorBase(this);

	public void setDBType(final String s) {
		base.setDBType(s);
	}

	public void setEnums(final String s) {
		base.setEnums(s);
	}

	public void setEnumsOut(final String s) {
		base.setEnumsOut(s);
	}

	/**
	 * A comma separated list of regex patterns.  If "schema_name.table_name" contains the
	 * java pattern, the table will be excluded.  Otherwise, it will be included.
	 * If this is not set, all tables will be included.
	 * @param s
	 */
	public void setExcludeTables(final String s) {
		base.setExcludeTables(s);
	}

	/**
	 * A comma separated list of regex patterns.  If "schema_name.table_name" contains the
	 * java pattern, the table will be included.  Otherwise, it will be excluded.
	 * If this is not set, all tables will be included.
	 * @param s
	 */
	public void setOnlyTables(final String s) {
		base.setOnlyTables(s);
	}
	
	public void setOut(final String s) {
		base.setOut(s);
	}

	public void setPassword(final String s) {
		base.setPassword(s);
	}

	public void setPasswordFile(final String s) {
		base.setPasswordFile(s);
	}

	public void setSchemas(final String s) {
		base.setSchemas(s);
	}

	public void setURL(final String s) throws Exception {
		base.setURL(s);
	}
	public void setUsername(final String s) {
		base.setUsername(s);
	}

	@Override
	public void execute() {
		base.execute();
	}

	public void setConnectionAdapter(final String s) throws Exception {
		base.setConnectionAdapter(s);
	}

	public void setConnectionName(final String s) {
		base.setConnectionName(s);
	}
}
