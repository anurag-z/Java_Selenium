package com.ibm.Eapp.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

import com.ibm.Eapp.utility.Constants;
import com.ibm.automation.ui.DataSheetDto;
import com.ibm.utility.ConnectionManager;

public class AutomationDbConnect 
{
	private static Logger logger=Logger.getLogger(AutomationDbConnect.class);
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	private String sql="SELECT * FROM DB_OWNER.T_EAPP_MEDICAL_FORMS WHERE SCENARIO_1 LIKE '%CODE_01%'";
	//private String cse_sql="SELECT * FROM [db_owner].[T_EAPP_T_EAPP_CSE_MODULE] WHERE SCENARIO_1 LIKE '%CODE_01%' AND SCENARIO_2 LIKE '%CODE_02%'";
	
	private String cse_sql="SELECT * FROM [db_owner].[T_EAPP_T_EAPP_CSE_MODULE] WHERE IS_ACTIVE='Y' AND (SCENARIO_1='%CODE_01%' OR (SCENARIO_2 LIKE '%CODE_02%' AND SCENARIO_3 LIKE '%CODE_03%') OR (SCENARIO_2 LIKE '%CODE_02%' AND SCENARIO_3 = 'NA') OR (SCENARIO_2 = 'NA' AND SCENARIO_3 LIKE '%CODE_03%'))ORDER BY EVENT_SEQ";
	private String eapp_sql="SELECT * FROM [db_owner].[T_EAPP_ERRORFlOW_LOCATOR_EVENTS_MAPPING] WHERE IS_ACTIVE='Y' AND (SCENARIO_1='Common' OR (SCENARIO_2 LIKE '%CODE_01%' AND SCENARIO_3 LIKE '%CODE_02%') OR (SCENARIO_2 LIKE '%CODE_01%' AND SCENARIO_3 = 'NA') OR (SCENARIO_2 = 'NA' AND SCENARIO_3 LIKE '%CODE_02%')) and SCREEN_CODE LIKE '%CODE_03%'ORDER BY EVENT_SEQ";
	
	
	private ConnectionManager conMgr;
	
	
	public AutomationDbConnect(Connection con) throws Exception
	{
		this.con = con;
	}
	
	
	
	public AutomationDbConnect() throws Exception
	{
		conMgr=new ConnectionManager(Constants.DB_SQL_URL, Constants.DB_SQL_DRV, Constants.DB_SQL_USR, Constants.DB_SQL_PWD);
		
		
		this.con = conMgr.getConnection();
	}
	
	
	public  List<DataSheetDto>  getMedicalLocators(String Scenario1) throws Exception
	{
		 logger.info("D2cDbConnect.getMedicalLocators:: start");		
		 BeanListHandler<DataSheetDto> beanListHandler = new BeanListHandler<>(DataSheetDto.class);
		 QueryRunner runner =new QueryRunner();
		 sql=sql.replaceAll("CODE_01", Scenario1);
		 List<DataSheetDto> list= runner.query(con, sql, beanListHandler);
		 logger.info("DataSheetDto List:: " + list);
		 logger.info("D2cDbConnect.getMedicalLocators:: end");
		 return list;
	}
	
	public  List<DataSheetDto>  getCSELocators(String Scenario1,String Scenario2, String Scenario3) throws Exception
	{
		 logger.info("D2cDbConnect.getCSELocators:: start");		
		 BeanListHandler<DataSheetDto> beanListHandler = new BeanListHandler<>(DataSheetDto.class);
		 QueryRunner runner =new QueryRunner();
		 cse_sql=cse_sql.replaceAll("CODE_01", Scenario1);
		 cse_sql=cse_sql.replaceAll("CODE_02", Scenario2);
		 cse_sql=cse_sql.replaceAll("CODE_03", Scenario3);
		 List<DataSheetDto> list= runner.query(con, cse_sql, beanListHandler);
		 logger.info("DataSheetDto List:: " + list);
		 logger.info("D2cDbConnect.getMedicalLocators:: end");
		 return list;
	}
	
	public List<DataSheetDto> GetTestCases(String... params) throws Exception
	{
		
	    	BeanListHandler<DataSheetDto> beanListHandler = new BeanListHandler<>(DataSheetDto.class);
			QueryRunner runner =new QueryRunner();
			eapp_sql=eapp_sql.replaceAll("CODE_01", params[0]);
			System.out.println(eapp_sql);
			eapp_sql=eapp_sql.replaceAll("CODE_02", params[1]);
			eapp_sql=eapp_sql.replaceAll("CODE_03", params[2]);
			System.out.println(eapp_sql);
			List<DataSheetDto> list= runner.query(con, eapp_sql, beanListHandler);
			return list;
		
		
	
	}	
	
	
	public void destroy() throws Exception
	{
		ConnectionManager.closeBD();
	}


	
	
}
