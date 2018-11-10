package org.admiral.db;

import java.sql.Connection;

import javax.sql.DataSource;

public interface AdmiralDatabase {

    public String getConnectionURL(CConnection connection);

    public DataSource getDataSource(CConnection connection);

    public Connection getCachedConnection(CConnection connection, boolean autoCommit, int transactionIsolation) throws Exception;

}
