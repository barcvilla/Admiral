package org.admiral.cliente;

import org.admiral.util.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Admiral {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public static synchronized boolean startup(boolean isCliente)
	{
		return false;
	}
	
	public static void main(String[] args)
	{
		startup(true);
	}

}
