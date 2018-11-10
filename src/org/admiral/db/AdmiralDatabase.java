package org.admiral.db;

import java.sql.Connection;

import javax.sql.DataSource;

public interface AdmiralDatabase {
	public String getConnectionURL (CConnection connection);
	public DataSource getDataSource(CConnection connection);
	/**
	 * 	Get Cached Connection on Server
	 *	@param connection info
	 *  @param autoCommit true if autocommit connection
	 *  @param transactionIsolation Connection transaction level
	 *	@return connection or null
	 *  @throws Exception
	 */
	public Connection getCachedConnection (CConnection connection, 
		boolean autoCommit, int transactionIsolation) throws Exception;

}
