package com.ibm.Eapp.auto;

import java.util.HashMap;
import java.util.LinkedHashMap;


import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ibm.automation.IF.TestModIF;
import com.ibm.automation.ui.Driver_Setup;
import com.ibm.utility.Excel_Handling;
import com.ibm.utility.reports.Reporter;
import com.ibm.utility.reports.interfaces.ITestReporter;

public class Controller extends Driver_Setup
{
	private static Logger logger=Logger.getLogger(Controller.class);
	public static WebDriver driver;
	public static ITestReporter testReporter;
	
	public HashMap<String, HashMap> result;
	public HashMap<String, HashMap> testData;
	
	@BeforeClass
	public void setUp() 
	{
		driver = getDriver();
		testReporter=Reporter.getTestReporter();
	}	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Parameters({ "mod","tcID","sheetName","timeout","datasheetPath","datasheetResultPath"})
	@Test(threadPoolSize = 2)
	public void reDirectProcess(String mod,String tcID,String sheetName,String timeout,String datasheetPath,String datasheetResultPath) throws Exception,Throwable
	{
		logger.info("com.ibm.Eapp.auto.Controller...start");
		logger.info("com.ibm.Eapp.auto.Controller invoked " + mod);
		logger.info("com.ibm.Eapp.auto.Controller invoked " + tcID);
	
		loadData(sheetName,datasheetPath,datasheetResultPath);
		
	
		System.out.println(Excel_Handling.TestData);
		
		System.out.println(Excel_Handling.TestData.get(tcID));
		Class cls = Class.forName(mod);			
		TestModIF obj =(TestModIF) cls.newInstance();
		obj.Execute(Excel_Handling.TestData.get(tcID), tcID, sheetName, timeout,datasheetPath,datasheetResultPath);
		
		logger.info("com.ibm.Eapp.auto.Controller...end");
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, HashMap> datasheetLoading(String tcID,String sheetName,HashMap testData,String datasheetPath,String datasheetResultPath) throws Exception
	{
		Excel_Handling.ExcelReader(datasheetPath, "CSEMastersheet", datasheetResultPath, "CSEMastersheet");
		result=Excel_Handling.getExcelDataAll("CSEMastersheet", "flag", "Yes", "SC_ID");
		System.out.println(result);
		System.out.println(testData);
		LinkedHashMap<String,String> TC=(LinkedHashMap<String, String>) testData;
		String s=TC.get("SC_ID");
		System.out.println(s);	
		System.out.println(result.get(s));
		return result.get(s);
			
	}
	

	private void getDataSheetParamMap(int rowIndex,  HashMap<String, String> paramData)
	{
		
		String [] param = Excel_Handling.Get_Data(TC_ID, "Param"+rowIndex).split("\\r?\\n");
		
		for(int i=1;i<param.length;i++)
		{
			paramData.put(param[i].split("=")[0].toString(),param[i].split("=")[1].toString());
		}
	}
		
		
	private void getDataSheetDataMap(int rowIndex,  HashMap<String, String> paramData)
	{
			
			String [] param = Excel_Handling.Get_Data(TC_ID, "Param"+rowIndex).split("\\r?\\n");
			
			for(int i=1;i<param.length;i++)
			{
				paramData.put(param[i].split("=")[0].toString(),param[i].split("=")[1].toString());
			}		
	}
	
	
	
	private void loadData(String sheetName,String datasheetPath,String datasheetResultPath) throws Exception{
		
		
		Excel_Handling.ExcelReader(datasheetPath, sheetName, datasheetResultPath, sheetName);
		
		Excel_Handling.getExcelDataAll(sheetName, "flag", "Yes", "tc_id");
		
		
		
	}
	
	
	@AfterClass
	public void destroy() 
	{
		testReporter.endTest();
		Reporter.flushReport();
	}
				
}
