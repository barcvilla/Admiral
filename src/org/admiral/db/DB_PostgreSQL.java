package org.admiral.db;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DB_PostgreSQL implements AdmiralDatabase
{
	/** Driver                  */
	private org.postgresql.Driver   s_driver = null;
    
    /** Driver class            */
    public static final String DRIVER = "org.postgresql.Driver";

	/** Default Port            */
	public static final int         DEFAULT_PORT = 5432;
	
	/** Data Source				*/
	private ComboPooledDataSource m_ds = null;
	
	/** Connection String       */
	private String          m_connection;
	/** Cached Database Name	*/
	private String			m_dbName = null;
        
    private String				m_userName = null;
    
    /** Connection String       	*/
	private String          		m_connectionURL;
	
	public DB_PostgreSQL()
	{}
	
	/**ConnectionString para la base de datos*/
	public String getConnectionURL(CConnection connection)
	{
		StringBuffer sb = new StringBuffer("jdbc:postgresql:");
		sb.append("//").append(connection.getDbHost()).append(":").append(connection.getDbPort())
		.append("/").append(connection.getDbName()).append("?encoding=UNICODE");
		m_connection = sb.toString();
		return m_connection;
	}
	
	public DataSource getDataSource(CConnection connection)
	{
		return m_ds;
	}

}
