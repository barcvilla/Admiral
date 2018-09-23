package org.admiral.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DB {
	private static Logger log = LogManager.getRootLogger();
	
	private static CConnection	s_cc = null;
	
	//set connection
	public static void setDBTarget(CConnection cc)
	{
		if(cc == null)
		{
			throw new IllegalArgumentException("Connection is Null");
		}
		
		if(cc != null && s_cc.equals(cc))
		{
			return;
		}
	}
}
