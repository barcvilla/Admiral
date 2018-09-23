package org.admiral.cliente;

import org.admiral.db.CConnection;
import org.admiral.util.Ini;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Admiral {
	
	static Logger log = LogManager.getRootLogger();
	
	public static synchronized boolean startup(boolean isCliente)
	{
		// System properties
		Ini.loadProperties();
		CConnection.get();
		return true;
	}
	
	public static void main(String[] args)
	{
		startup(true);
		
	}

}
