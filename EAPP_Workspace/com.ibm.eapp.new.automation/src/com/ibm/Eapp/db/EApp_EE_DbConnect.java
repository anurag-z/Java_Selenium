package com.ibm.Eapp.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.ibm.Eapp.mod.EappCSEMod;
import com.ibm.Eapp.utility.*;
import com.ibm.utility.ConnectionManager;

public class EApp_EE_DbConnect 
{
	private static Logger logger=Logger.getLogger(EApp_EE_DbConnect.class);
	private Connection con1 = null;
	private Statement stmt1 = null;
	private ResultSet rs1 = null;
	private Connection con2 = null;
	private Statement stmt2 = null;
	private ResultSet rs2 = null;

	

	private String sql1="select IS_IAR_COMPLETE,CSE_STATUS from EApp_EE.dbo.VW_RWS_DATA where PAYMENT_MODE not in ('ONLINE') and APP_NUMBER = '";
	
	private ConnectionManager Eapp_EE_conMgr;


	public EApp_EE_DbConnect(Connection con1) throws Exception
	{
		this.con1 = con1;
	}

//	public EApp_EE_DbConnect() throws Exception
//	{
//		System.out.println("EApp EE Db connection is established...");
//		Eapp_EE_conMgr=new ConnectionManager(Constants.EApp_EE_DB_SQL_URL, Constants.EApp_EE_DB_SQL_DRV, Constants.EApp_EE_DB_SQL_USR, Constants.EApp_EE_DB_SQL_PWD);
//		this.con1 = Eapp_EE_conMgr.getConnection();
//	}

	public  ResultSet get_RWS_Status(String AppNo) throws Exception
	{
		System.out.println("Checking RWS status...");
		//System.out.println("Checking CSE status ...");
		Statement stmt1 = con1.createStatement();
		String sq = sql1 + AppNo + "'";
		//System.out.println("Query ->" + sq);
		ResultSet rs1 = stmt1.executeQuery(sq);

		return rs1;
	}

	public void destroy() throws Exception
	{
		ConnectionManager.closeBD();
	}




}
