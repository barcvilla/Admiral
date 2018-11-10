package org.admiral.db;

import java.sql.Connection;
import java.util.logging.Level;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DB_PostgreSQL implements AdmiralDatabase {

    private static Logger log = LogManager.getRootLogger();
    private org.postgresql.Driver s_driver = null;
    public static final String DRIVER = "org.postgresql.Driver";
    public static final int DEFAULT_PORT = 5432;
    private ComboPooledDataSource m_ds = null;
    private String m_connection;
    private String m_dbName = null;
    private String m_userName = null;
    private String m_connectionURL;
    private static int m_maxbusyconnections = 0;
    

    public DB_PostgreSQL() {
    }

    /**
     * ConnectionString para la base de datos
     */
    public String getConnectionURL(CConnection connection) {
        StringBuffer sb = new StringBuffer("jdbc:postgresql:");
        sb.append("//").append(connection.getDbHost()).append(":").append(connection.getDbPort())
                .append("/").append(connection.getDbName()).append("?encoding=UNICODE");
        m_connection = sb.toString();
        return m_connection;
    }

    //Creamos un DataSource
    public DataSource getDataSource(CConnection connection) {
        if (m_ds != null) {
            return m_ds;
        }

        try {
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
        } catch (Exception ex) {
            m_ds = null;
            log.log(null, null, Level.SEVERE, "not", ex);
        }

        return m_ds;
    }

    @Override
    public Connection getCachedConnection(CConnection connection, boolean autoCommit, int transactionIsolation)
            throws Exception {
        if(m_ds == null)
        {
            getDataSource(connection);
        }
        
        Connection conn = m_ds.getConnection();
        if(conn != null)
        {
            conn.setAutoCommit(autoCommit);
            conn.setTransactionIsolation(transactionIsolation);
            try
            {
                int numConnections = m_ds.getNumBusyConnections();
                if(numConnections >= m_maxbusyconnections && m_maxbusyconnections > 0)
                {
                    Runtime.getRuntime().runFinalization();
                }
            }
            catch(Exception ex){}
        }
        return conn;
    }

}
