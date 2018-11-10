package org.admiral.cliente;

import org.admiral.db.CConnection;
import org.admiral.db.DB;
import org.admiral.util.Ini;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Admiral {
	
	static Logger log = LogManager.getRootLogger();
	
	public static synchronized boolean startup(boolean isCliente)
	{
		// System properties
		Ini.loadProperties();
		DB.setDBTarget(CConnection.get());
		
		return startupEnvironment(isCliente);
	}
	
	public static boolean startupEnvironment(boolean isClient)
	{
		if(!DB.isConnected())
		{
			System.out.println("No DataBase");
		}
		else
		{
			System.out.println("Connected DB");
		}
		
		return true;
	}
	
	public static void main(String[] args)
	{
		startup(true);
		
		
	}

}
