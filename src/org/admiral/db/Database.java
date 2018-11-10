package org.admiral.db;

public class Database {
	/** PostgreSQL ID   */
	public static String        DB_POSTGRESQL = "PostgreSQL";
	
	/** Supported Databases     */
	public static String[]      DB_NAMES = new String[] {DB_POSTGRESQL};
	
	//Database Classes
	protected static Class<?>[] DB_CLASSES = new Class[] {DB_PostgreSQL.class};
	
	//Obtenemos Database por el Id de la database
	public static AdmiralDatabase getDatabase(String type) throws Exception
	{
		AdmiralDatabase db = null;
		for(int i = 0; i < Database.DB_NAMES.length; i++)
		{
			if(Database.DB_NAMES[i].equals(type))
			{
				db = (AdmiralDatabase)Database.DB_CLASSES[i].newInstance();
				break;
			}
		}
		
		return db;
	}

}
