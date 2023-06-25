package com.ibm.Eapp.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.ibm.Eapp.utility.*;
import com.ibm.utility.ConnectionManager;

public class EappDbConnect 
{
	private static Logger logger=Logger.getLogger(EappDbConnect.class);
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	
	private String sql="select OTP from TBL_OTP_AUTHNTICATE_FLAGS where  APP_NO= ?";

	//"select OTP from TBL_OTP_AUTHNTICATE_FLAGS where  APP.application_no = ?"; select OTP from TBL_OTP_AUTHNTICATE_FLAGS where  APP_NO= 'EA90017847'
	//select OTP from TBL_OTP_AUTHNTICATE_FLAGS where  APP_NO=
	private ConnectionManager conMgr;
	
	
	public EappDbConnect(Connection con) throws Exception
	{
		this.con = con;
	}
	
	
	
	public EappDbConnect() throws Exception
	{
		conMgr=new ConnectionManager(Constants.Eapp_DB_SQL_URL, Constants.Eapp_DB_SQL_DRV, Constants.Eapp_DB_SQL_USR, Constants.Eapp_DB_SQL_PWD);
		
		
		this.con = conMgr.getConnection();
	}
	
	
	public String  getOTP(String AppNo) throws Exception
	{
		 logger.info("D2cDbConnect.getOTP:: start");	
		 System.out.println(AppNo);
		 BeanListHandler<AppDetailsOTP> beanListHandler = new BeanListHandler<>(AppDetailsOTP.class);
		 QueryRunner runner =new QueryRunner();
		 List<AppDetailsOTP> list= runner.query(con, sql, beanListHandler,AppNo);
		 System.out.println("OTP:" +list);
		 logger.info("AppDetailsOTP List:: " + list);
		 logger.info("D2cDbConnect.getOTP:: end");
		 return list.get(0).getOTP();
	}
	
	
	public void destroy() throws Exception
	{
		ConnectionManager.closeBD();
	}


	
	
}
