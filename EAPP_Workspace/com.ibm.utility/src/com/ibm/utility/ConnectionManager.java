package com.ibm.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import org.apache.commons.dbutils.DbUtils;
import org.testng.log4testng.Logger;

public class ConnectionManager 
{
    private static String DB_SQL_URL;    
    private static String DB_SQL_DRV;  
    private static String DB_SQL_USR;   
    private static String DB_SQL_PWD; 
    private static Connection con;
    private static Logger logger=Logger.getLogger(ConnectionManager.class);
    
    private ConnectionManager()
    {
    	
    }
    

    
    public ConnectionManager(String DB_SQL_URL,String DB_SQL_DRV,String DB_SQL_USR, String DB_SQL_PWD)
    {
        this.DB_SQL_URL = DB_SQL_URL;    
        this.DB_SQL_DRV = DB_SQL_DRV;  
        this.DB_SQL_USR = DB_SQL_USR;   
        this.DB_SQL_PWD = DB_SQL_PWD; 
    }


    public Connection getConnection() 
    {
        try
        {
        	System.out.println("Driver:" + DB_SQL_DRV);
        	logger.info("Driver:" + DB_SQL_DRV);
        	
            Class.forName(DB_SQL_DRV);
            try 
            {
                con = DriverManager.getConnection(DB_SQL_URL, DB_SQL_USR, DB_SQL_PWD);
                System.out.println("Connected to Database."); 
                logger.info("Connected to Database.");
            } 
            catch (SQLException ex) 
            {
            	logger.error("SQLException in getConnection():: ", ex);
            	ex.printStackTrace();
                // log an exception. fro example:
                System.out.println("Failed to create the database connection."); 
            }
        } 
        catch (ClassNotFoundException ex) 
        {
            // log an exception. for example:
        	logger.error("ClassNotFoundException in getConnection():: ", ex);
            System.out.println("Driver not found."); 
        }
        return con;
    }
    
    
    
    public static void closeBD() 
    {
    	DbUtils.closeQuietly(con);
    }
}