package org.admiral.db;

import javax.sql.DataSource;

public interface AdmiralDatabase {
	public String getConnectionURL (CConnection connection);
	public DataSource getDataSource(CConnection connection);
}
