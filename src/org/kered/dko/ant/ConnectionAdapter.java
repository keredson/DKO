package org.kered.dko.ant;

import java.sql.Connection;

import org.apache.tools.ant.Task;

/**
 * Created: Apr 29, 2014
 *
 * Interface to be implemented by the user, allowing DKO to get a connection without having to specify
 * any of the normal connection parameters (username, password, etc.)
 *
 * @author Scott Lynch <scott.lynch@twosigma.com>
 */
public interface ConnectionAdapter
{
    /**
    * @param connectionName A string identifier that the implementing class can use to determine which connection to return
    * @param task The ant Task that is being executed by DKO. This can be used to query for additional environment parameters
    */
    public Connection getConnection(String connectionName, Task task);
}
