package org.admiral.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.admiral.util.Ini;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CConnection implements Serializable
{
	private static Logger log = LogManager.getRootLogger();
	private static CConnection s_cc = null;
	/** Database Connection 	*/
	private boolean 	m_okDB = false;
	
	private String m_db_host = "";
	private String m_db_port = "";
	private String m_db_name = "";
	private String m_db_uid = "";
	private String m_bd_pwd = "";
	private String m_type = "";
	
	private DataSource m_ds = null;
	
	private AdmiralDatabase m_db = null;
	
	
	//obtenemos una conexion con el servidor
	public DataSource getDataSource()
	{
		return m_ds;
	}
	
	//Creamos a una Conexion DB
	public boolean setDataSource()
	{
		if(m_ds == null)
		{
			if(getDatabase() != null)
			{
				m_ds = getDatabase().getDataSource(this);
			}
		}
		
		return m_ds != null;
	}
	
	//get database
	public AdmiralDatabase getDatabase()
	{
		if(m_db == null)
		{
			try
			{
				for(int i = 0; i < Database.DB_NAMES.length; i++)
				{
					if(Database.DB_NAMES[i].equals(m_type))
					{
						m_db = (AdmiralDatabase)Database.DB_CLASSES[i].newInstance();
						break;
					}
				}
				if(m_db != null)
				{
					m_db.getDataSource(this);
				}
			}
			catch(NoClassDefFoundError ee)
			{
				System.err.println("Error en el ambiente - verificar el archivo Admiral.properties");
			}
			catch(Exception ex)
			{
				log.debug(ex.toString());
			}
		}
		
		return m_db;
	}//getDatabase
	
	/**
	 * Crear Conexion
	 * @param autoCommit true - autoCommit connection
	 * @param trans	 
	 * */
	public Connection getConnection(boolean autoCommit, int transactionIsolation)
	{
		Connection conn = null;
		m_okDB = false;
		getDatabase();
		if(m_db == null)
		{
			return null;
		}
		
		try
		{
			try
			{
				conn = m_db.getCachedConnection(this, autoCommit, transactionIsolation);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			//verificamos la conexion
			if(conn != null)
			{
				if(conn.getTransactionIsolation() != transactionIsolation)
				{
					conn.setTransactionIsolation(transactionIsolation);
				}
				if(conn.getAutoCommit() != autoCommit)
				{
					conn.setAutoCommit(autoCommit);
				}
				m_okDB = true;
			}
		}
		catch(SQLException ule)
		{
			ule.printStackTrace();
		}
		
		return conn;
	}
	
	private void setType(String type)
	{
		this.m_type = type;
	}
	
	private String getType()
	{
		return m_type;
	}
	
	public void setDbUid(String dbUid)
	{
		this.m_db_uid = dbUid;
	}
	
	public String getDbUid()
	{
		return m_db_uid;
	}
	
	public void setDbPwd(String dbPwd)
	{
		this.m_bd_pwd = dbPwd;
	}
	
	public String getDbPwd()
	{
		return m_bd_pwd;
	}
	
	public void setDbName(String dbName)
	{
		this.m_db_name = dbName;
	}
	
	public String getDbName()
	{
		return m_db_name;
	}
	
	public void setDbHost(String app_host)
	{
		this.m_db_host = app_host;
	}
	
	public String getDbHost()
	{
		return m_db_host;
	}
	
	public void setDbPort(String app_port)
	{
		this.m_db_port = app_port;
	}
	
	public String getDbPort()
	{
		return m_db_port;
	}
	
	public static CConnection get()
	{
		if(s_cc == null)
		{
			String attributes = Ini.getProperty(Ini.P_CONNECTION);
			s_cc = new CConnection();
			s_cc.setAttributes(attributes);
		}
		return s_cc;
	}
	
	private void setAttributes(String attributes)
	{
		setDbHost(attributes.substring(attributes.indexOf("DBhost=") + 7, attributes.indexOf(",DBport=")));
		setDbPort(attributes.substring(attributes.indexOf("DBport=") + 7, attributes.indexOf(",DBname=")));
		setDbName(attributes.substring(attributes.indexOf("DBname=") + 7, attributes.indexOf(",DBUID=")));
		setDbUid(attributes.substring(attributes.indexOf("DBUID=") + 6, attributes.indexOf(",DBPWD=")));
		setDbPwd(attributes.substring(attributes.indexOf("DBPWD=") + 6, attributes.indexOf(",type=")));
		setType(attributes.substring(attributes.indexOf("type=") + 5, attributes.lastIndexOf(",")));
		//log.debug(getDbPwd());
	}
}
