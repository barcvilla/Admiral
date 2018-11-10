package org.admiral.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public final class Ini implements Serializable
{
	private static Logger log = LogManager.getRootLogger();
	
	/** Property file name				*/
	public static final String	ADEMPIERE_PROPERTY_FILE = "Admiral.properties";
	
	/** Connection Details	*/
	public static final String	P_CONNECTION =		"Connection";
	
	/**	Container for Properties    */
	private static Properties 		s_prop = new Properties();
	private static String s_propertyFileName = null;
	
	/** IsClient*/
	private static boolean 			s_cliente = true;
	
	private static String getFileName(boolean tryUserHome)
	{
		String base = null;
		if(tryUserHome && s_cliente)
		{
			base = System.getProperty("user.home");
		}
		
		if(base != null && !base.endsWith(File.separator))
		{
			base += File.separator;
		}
		
		if(base == null)
		{
			base = "";
		}
		
		return base + ADEMPIERE_PROPERTY_FILE;
	}
	
	//Cargamos los parametros de inicia desde el disco
	public static void loadProperties()
	{
		loadProperties(getFileName(s_cliente));
	}
	
	public static boolean loadProperties(String filename)
	{
		boolean loadOk = false;
		s_prop = new Properties();
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(filename);
			s_prop.load(fis);
			loadOk = true;
			//log.debug(s_prop.toString());
			fis.close();
		}
		catch(IOException ex)
		{
			//log.log(Level.SEVERE ,filename + " - " + ex.toString());
			log.log(null, null,Level.SEVERE, filename + " - " + ex.toString());
			loadOk = false;
		}
		
		return loadOk;
	}
	
	/**
	 * Obtenemos una propiedad
	 * @param key : propiedad que vamos a obtener del archivo Ini
	 * @return
	 */
	public static String getProperty(String key)
	{
		if(key == null)
		{
			return "";
		}
		
		String value = s_prop.getProperty(key, "");
		if(value == null)
		{
			return "";
		}
		return value;
	}
}
