package org.admiral.db;

import java.sql.Connection;
import java.util.logging.Level;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DB_PostgreSQL implements AdmiralDatabase
{
	private static Logger log = LogManager.getRootLogger();
	
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
	
	private static int              m_maxbusyconnections = 0;
	
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
	
	//Creamos un DataSource
	public DataSource getDataSource(CConnection connection)
	{
		if(m_ds != null)
		{
			return m_ds;
		}
		
		try
		{
			System.setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
			ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDataSourceName("AdempiereDS");
            cpds.setDriverClass(DRIVER);
            //cargamos el jdbc driver
            cpds.setJdbcUrl(getConnectionURL(connection));
            cpds.setUser(connection.getDbUid());
            cpds.setPassword(connection.getDbPwd());
            cpds.setIdleConnectionTestPeriod(1200);
            cpds.setAcquireRetryAttempts(2);
            
            cpds.setInitialPoolSize(10);
            cpds.setMinPoolSize(5);
            cpds.setMaxPoolSize(150);
            cpds.setMaxIdleTimeExcessConnections(1200);
            cpds.setMaxIdleTime(1200);
            m_maxbusyconnections = 120;
            
            m_ds = cpds;
		}
		catch(Exception ex)
		{
			m_ds = null;
			log.log(null, null, Level.SEVERE, "not", ex);
		}
		
		return m_ds;
	}

	@Override
	public Connection getCachedConnection(CConnection connection, boolean autoCommit, int transactionIsolation)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
