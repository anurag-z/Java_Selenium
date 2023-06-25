package com.ibm.Eapp.utility;

import java.util.HashMap;
import java.util.ResourceBundle;

public class Constants {
	
	final public static ResourceBundle rsBundle = ResourceBundle.getBundle("com.ibm.Eapp.config.app");
	
	
	public static final String driverPath =rsBundle.getString("driverPath");
	public static final String reportPath =rsBundle.getString("reportPath"); 
	public static final String snapshotsPath = rsBundle.getString("snapshotsPath");
	public static String puttyPath = rsBundle.getString("puttyPath");
    public static String bathMacroPath = rsBundle.getString("bathMacroPath");
    //public static final String configPath =rsBundle.getString("configPath");
    public static final String testController =rsBundle.getString("testController");
    public static final String suiteListenerClass =rsBundle.getString("suiteListenerClass");
    
    public static final String proxyType =rsBundle.getString("proxyType");
    public static final String proxyFile =rsBundle.getString(proxyType);
    public static final String proxyZipFilePath =rsBundle.getString("proxyZipFilePath");
    public static final String proxyCancelZipFilePath =rsBundle.getString("proxyCancelZipFilePath");
    
    
    public static final String strTestProcess=rsBundle.getString("TestProcess");
    public static final String[] TestProcessArr =strTestProcess.split("~");
    
    public static final HashMap<String,HashMap<String,String>> TestProcessMap=new HashMap<String,HashMap<String,String>>();
    static
    {
    	for(String TestProcess:TestProcessArr)
    	{
    		System.out.println("TestProcess " + TestProcess);
    		HashMap<String,String> TestProcessSubMap=new HashMap<String,String>();
    		TestProcessSubMap.put("suiteName", TestProcess);
    		TestProcessSubMap.put("configXmlPath", rsBundle.getString(TestProcess + ".configXmlPath"));
    		TestProcessSubMap.put("browser", rsBundle.getString(TestProcess + ".browser"));
    		TestProcessSubMap.put("createTestNGPath", rsBundle.getString(TestProcess + ".createTestNGPath"));
    		TestProcessSubMap.put("datasheetPath", rsBundle.getString(TestProcess + ".datasheetPath"));
    		TestProcessSubMap.put("datasheetResultPath", rsBundle.getString(TestProcess + ".datasheetResultPath"));
    		TestProcessMap.put(TestProcess, TestProcessSubMap);
    	}
    }
    
    
    
	public static String DB_SQL_URL=rsBundle.getString("DB_SQL_URL");    
	public static String DB_SQL_DRV=rsBundle.getString("DB_SQL_DRV");  
	public static String DB_SQL_USR=rsBundle.getString("DB_SQL_USR");   
	public static String DB_SQL_PWD=rsBundle.getString("DB_SQL_PWD"); 	
	
	public static String Eapp_DB_SQL_URL=rsBundle.getString("Eapp_DB_SQL_URL");    
	public static String Eapp_DB_SQL_DRV=rsBundle.getString("Eapp_DB_SQL_DRV");  
	public static String Eapp_DB_SQL_USR=rsBundle.getString("Eapp_DB_SQL_USR");   
	public static String Eapp_DB_SQL_PWD=rsBundle.getString("Eapp_DB_SQL_PWD"); 

//
//	public static String EApp_EE_DB_SQL_URL=rsBundle.getString("EApp_EE_DB_SQL_URL");    
//	public static String EApp_EE_DB_SQL_USR=rsBundle.getString("EApp_EE_DB_SQL_USR");  
//	public static String EApp_EE_DB_SQL_PWD=rsBundle.getString("EApp_EE_DB_SQL_PWD");   
//	public static String EApp_EE_DB_SQL_DRV=rsBundle.getString("EApp_EE_DB_SQL_DRV"); 

	
	

}


