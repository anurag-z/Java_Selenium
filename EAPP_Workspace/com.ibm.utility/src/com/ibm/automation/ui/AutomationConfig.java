package com.ibm.automation.ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.ibm.utility.ConnectionManager;

public class AutomationConfig 
{
	
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	private String sql="SELECT * FROM DB_OWNER.T_APP_SCREEN_LOCATOR_EVENTS_MAPPING WHERE APP_CODE=? AND SCREEN_CODE=? AND IS_ACTIVE='Y' ORDER BY EVENT_SEQ";


//	private String eapp_sql="SELECT * FROM [db_owner].[T_EAPP_SCREEN_LOCATOR_EVENTS_MAPPING] WHERE SCENARIO_1='Common' OR (SCENARIO_2 LIKE ? AND SCENARIO_3 LIKE ?) OR (SCENARIO_2 LIKE ? AND SCENARIO_3 = 'NA') OR (SCENARIO_2 = 'NA' AND SCENARIO_3 LIKE ?) ORDER BY EVENT_SEQ";
	//private String eapp_sql="SELECT * FROM [db_owner].[T_EAPP_SCREEN_LOCATOR_EVENTS_MAPPING] WHERE IS_ACTIVE='Y' AND (SCENARIO_1='Common' OR (SCENARIO_2 LIKE '%CODE_01%' AND SCENARIO_3 LIKE '%CODE_02%') OR (SCENARIO_2 LIKE '%CODE_01%' AND SCENARIO_3 = 'NA') OR (SCENARIO_2 = 'NA' AND SCENARIO_3 LIKE '%CODE_02%')) ORDER BY EVENT_SEQ";
	private String eapp_sql="SELECT * FROM T_MAPP_WEB_APP_LOCATOR_EVENTS_MAPPING WHERE IS_ACTIVE='Y' AND (SCENARIO_1='Common' OR (SCENARIO_2 LIKE '%CODE_01%' AND SCENARIO_3 LIKE '%CODE_02%') OR (SCENARIO_2 LIKE '%CODE_01%' AND SCENARIO_3 = 'NA') OR (SCENARIO_2 = 'NA' AND SCENARIO_3 LIKE '%CODE_02%')) ORDER BY EVENT_SEQ";
	//private String eapp_sql="SELECT * FROM T_MAPP_WEB_APP_PSM WHERE IS_ACTIVE='Y' AND (SCENARIO_1='Common' OR (SCENARIO_2 LIKE '%CODE_01%' AND SCENARIO_3 LIKE '%CODE_02%') OR (SCENARIO_2 LIKE '%CODE_01%' AND SCENARIO_3 = 'NA') OR (SCENARIO_2 = 'NA' AND SCENARIO_3 LIKE '%CODE_02%')) ORDER BY EVENT_SEQ";



	public AutomationConfig(Connection con) throws Exception
	{
		this.con = con;
	}
	
	
	public List<DataSheetDto>  GetTestCases(String App_code,String Screen_code) throws Exception
	{
		 BeanListHandler<DataSheetDto> beanListHandler = new BeanListHandler<>(DataSheetDto.class);
		 QueryRunner runner =new QueryRunner();
		 List<DataSheetDto> list= runner.query(con, sql, beanListHandler,App_code,Screen_code);
		
		return list;
	}
	
	
	public List<DataSheetDto> GetTestCases(String testCaseType,String... params) throws Exception
	{
		if(testCaseType.equals("EAPP"))
		{
	    	BeanListHandler<DataSheetDto> beanListHandler = new BeanListHandler<>(DataSheetDto.class);
			QueryRunner runner =new QueryRunner();
			eapp_sql=eapp_sql.replaceAll("CODE_01", params[0]);
			System.out.println(eapp_sql);
			eapp_sql=eapp_sql.replaceAll("CODE_02", params[1]);
			System.out.println(eapp_sql);
			List<DataSheetDto> list= runner.query(con, eapp_sql, beanListHandler);
			return list;
		}
		
		return null;
	}	
	
	
	
	
	public void destroy() throws Exception
	{
		ConnectionManager.closeBD();
	}

	
	
//	public static void main(String[] args) throws Exception
//	{
//		
//		AutomationConfig obj=new AutomationConfig();
//		
//		for(DataSheetDto datasheet:obj.GetTestCases())
//		{
//			System.out.println(datasheet.getDT_CREATE());
//		}
//		
//	}
	
	
}
