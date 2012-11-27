package org.kered.dko.datasource;

import java.util.Collection;

import javax.sql.DataSource;


/**
 * A DataSource that wraps another DataSource.
 *
 * @author Derek Anderson
 */
public interface MatryoshkaDataSource extends DataSource {

	public DataSource getPrimaryUnderlying();
	public Collection<DataSource> getAllUnderlying();

}
