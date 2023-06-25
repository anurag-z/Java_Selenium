package com.ibm.automation.ui;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ibm.automation.IF.TestModIF;
import com.ibm.utility.Common_Functions;
import com.ibm.utility.ConnectionManager;
import com.ibm.utility.Excel_Handling;
import com.ibm.utility.ReadExcelByMapping;
import com.ibm.utility.reports.interfaces.ITestReporter;

public class AutomationWrapper
{
	
	private static Logger logger=Logger.getLogger(AutomationWrapper.class);
	private String datasheetPath;
	private String sheetName;
	private String snapshotsPath;
	private String reportPath;
	private String datasheetResultPath;
	
	private ConnectionManager conMgr;
	
	private WrapperMethods method;
	
	private ActionUtils actionUtils;
	
	private AutomationConfig autoconfig=null;
	
	private ITestReporter testReporter;
	private Common_Functions screenshot;
	
	
	private AutomationWrapper()
	{

	}
	
	public AutomationWrapper(String datasheetPath,String sheetName,String snapshotsPath,String reportPath,String timeout,String DB_SQL_URL,String DB_SQL_DRV,String DB_SQL_USR, String DB_SQL_PWD) throws Throwable, Exception
	{
		this(datasheetPath, "", sheetName, snapshotsPath, reportPath, timeout, DB_SQL_URL, DB_SQL_DRV, DB_SQL_USR,  DB_SQL_PWD,null);
	}	
	
	public AutomationWrapper(String datasheetPath,String sheetName,String snapshotsPath,String reportPath,String timeout,String DB_SQL_URL,String DB_SQL_DRV,String DB_SQL_USR, String DB_SQL_PWD, ITestReporter testReporter) throws Throwable, Exception
	{
		this(datasheetPath, "", sheetName, snapshotsPath, reportPath, timeout, DB_SQL_URL, DB_SQL_DRV, DB_SQL_USR,  DB_SQL_PWD,testReporter);
	}	
	
	public AutomationWrapper(String datasheetPath,String datasheetResultPath,String sheetName,String snapshotsPath,String reportPath,String timeout,String DB_SQL_URL,String DB_SQL_DRV,String DB_SQL_USR, String DB_SQL_PWD,ITestReporter testReporter) throws Throwable, Exception
	{
		this.datasheetPath=datasheetPath;
		this.sheetName=sheetName;
		this.snapshotsPath=snapshotsPath;
		this.reportPath=reportPath;
		this.datasheetResultPath=datasheetResultPath;
		this.testReporter=testReporter;
		
//		testReporter=new testReporter(snapshotsPath,reportPath);

		method= new WrapperMethods(datasheetPath,sheetName,snapshotsPath,reportPath,timeout,testReporter);
		actionUtils= new ActionUtils(datasheetPath,sheetName,snapshotsPath,reportPath,timeout,testReporter);
		conMgr=new ConnectionManager(DB_SQL_URL,DB_SQL_DRV,DB_SQL_USR,DB_SQL_PWD);
		autoconfig=new AutomationConfig(conMgr.getConnection());
		screenshot=new Common_Functions();
		
	}
	

	

	
	

	
	
	/**
	 * 
	 * @param m  			==> Excel Column data (Header data | Parameter data), this is further used on Screen.
	 * @param driver		==> webdriver
	 * @param TC_ID	   		==> Test Case ID
	 * @param dataMap  		==> Data Map to hold any return value from Screen
	 * @param App_code     	==> APP CODE to fetch data from database
	 * @param Screen_code  	==> SCREEN CODE to fetch data from database
	 * @param callingClass 	==> reference of calling class to invoke customer method
	 * @throws Throwable, Exception
	 */
	public void Execute(HashMap m,WebDriver driver,String TC_ID,HashMap<String,String> dataMap,String App_code,String Screen_code, Object callingClass) throws Throwable, Exception
	{
		Execute(m, driver, TC_ID,dataMap,null,App_code, Screen_code, callingClass);
		
	}
	
	
	
	
	/**
	 * 
	 * @param m  			==> Excel Column data (Header data | Parameter data), this is further used on Screen.
	 * @param driver		==> webdriver
	 * @param TC_ID	   		==> Test Case ID
	 * @param dataMap  		==> Data Map to hold any return value from Screen
	 * @param testData 		==> Excel Column data (Header data) only, only in case of Parameter data scenario.
	 * @param App_code     	==> APP CODE to fetch data from database
	 * @param Screen_code  	==> SCREEN CODE to fetch data from database
	 * @param callingClass 	==> reference of calling class to invoke customer method
	 * @throws Throwable, Exception
	 */
	public void Execute(HashMap m,WebDriver driver,String TC_ID,HashMap<String,String> dataMap,HashMap<String, String> testData,String App_code,String Screen_code, Object callingClass) throws Throwable, Exception
	{
		Execute( m, driver, TC_ID,dataMap, testData, App_code, Screen_code,  callingClass, 0);
		
	}
	
	
	/**
	 * 
	 * @param m  			==> Excel Column data (Header data | Parameter data), this is further used on Screen.
	 * @param driver		==> webdriver
	 * @param TC_ID	   		==> Test Case ID
	 * @param dataMap  		==> Data Map to hold any return value from Screen
	 * @param testData 		==> Excel Column data (Header data) only, only in case of Parameter data scenario.
	 * @param callingClass 	==> reference of calling class to invoke customer method
	 * @throws Throwable, Exception
	 */
	public void Execute(HashMap m,WebDriver driver,String TC_ID,HashMap<String,String> dataMap,HashMap<String, String> testData,Object callingClass,String testType, String... params) throws Throwable, Exception
	{
		Execute( m, driver, TC_ID,dataMap, testData, callingClass, 0,testType,params);
		
	}
	
	
	
	/**
	 * 
	 * @param m  			==> Excel Column data (Header data | Parameter data), this is further used on Screen.
	 * @param driver		==> webdriver
	 * @param TC_ID	   		==> Test Case ID
	 * @param dataMap  		==> Data Map to hold any return value from Screen
	 * @param testData 		==> Excel Column data (Header data) only, only in case of Parameter data scenario.
	 * @param App_code     	==> APP CODE to fetch data from database
	 * @param Screen_code  	==> SCREEN CODE to fetch data from database
	 * @param callingClass 	==> reference of calling class to invoke customer method
	 * @param current_index	==> To read data from excel in case of multiple dataset (Ingenium Jointlife and Multiple Client scenario 	  
	 * @throws Throwable, Exception
	 */
	public void Execute(HashMap m,WebDriver driver,String TC_ID,HashMap<String,String> dataMap,HashMap<String, String> testData,String App_code,String Screen_code, Object callingClass,int current_index) throws Throwable,Exception
	{
		
		System.out.println(m);
		logger.info("AutomationWrapper....");
		
		//Get TestCaseName
//		testReporter.startTest(TC_ID);
		try 
		{

			 for(DataSheetDto datasheet:autoconfig.GetTestCases(App_code,Screen_code))
			 {
				 
				 System.out.println(">>" +datasheet.getDATASHEET_KEY());
				 
				 
				 
				 String datasheetValue = "";
                 System.out.println(dataMap);
                 if(datasheet.getDATASHEET_KEY().equalsIgnoreCase("SEC_INS_LAST_CONSULT_DT_Q1")){
						
						System.out.println("Debug Start Here");
						/*driver.findElement(By.xpath("//*[@id='menuTabBar']/div[2]/div[1]")).click();
						String s=driver.findElement(By.xpath("//div[@class='clip-content']/b")).getText();
						System.out.println(s);*/
					}

				 //If Blank then [get Value from DataMap for PARAM1] OR [get Value from Excel HashMap for DATASHEET_KEY]
				 //If Blank then [get Value from DataMap for PARAM2] OR datasheetvalue 
				 datasheetValue = "".equals(datasheet.getDATASHEET_KEY())?dataMap.get(datasheet.getLOCATOR_PARAM1()):m.get(datasheet.getDATASHEET_KEY()).toString();
				 datasheetValue = "".equals(datasheetValue)?dataMap.get(datasheet.getLOCATOR_PARAM2()):datasheetValue;

				 logger.info(datasheet.getDATASHEET_KEY() + " - datasheetValue: " + datasheetValue);
				 
//				 try
//				 {
					 if(!("NA".equalsIgnoreCase(datasheetValue)))
					 {
						 doEvents(datasheet,datasheetValue,driver,TC_ID,dataMap, testData,callingClass,current_index);
					 }
					 else
					 {
						 logger.info("Not eligible for Action " + datasheet.getDATASHEET_KEY());
					 }

//				 }
//				 catch(Throwable ex)
//				 {
//					 testReporter.Log_Fail("Failed Events", "Failed while processing Events for EventID:" + datasheet.getID());
//					 logger.error("In catch DataSheetDto lOOP " + ex);
//					 ex.printStackTrace();				 
//				 }
//				 

			 }

			 
			 
		} 
		catch (Exception e) 
		{
			System.out.println("In catch ::"+testReporter);
			e.printStackTrace();
			screenshot.captureScreenshot(driver, snapshotsPath);
			testReporter.Log_Fail("Failed Events", "Failed while processing Events :Unknown Exception check trace logs");
			//driver.findElement(By.xpath("//div[@id='WLdialog']/p")).
			
			//Update flag in Excel
			
		}
		finally
		{
			logger.info("testData : " + testData);
			logger.info("dataMap : " + dataMap);
			logger.info("m : " + m);
			autoconfig.destroy();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * @param m  			==> Excel Column data (Header data | Parameter data), this is further used on Screen.
	 * @param driver		==> webdriver
	 * @param TC_ID	   		==> Test Case ID
	 * @param dataMap  		==> Data Map to hold any return value from Screen
	 * @param Screen_code  	==> SCREEN CODE to fetch data from database
	 * @param callingClass 	==> reference of calling class to invoke customer method
	 * @param current_index	==> To read data from excel in case of multiple dataset (Ingenium Jointlife and Multiple Client scenario
	 * @param testCaseType	==> To pass test case type to invoke particular Locator sheet to load
	 * @param params		==> Array of String to pass Scenarios or AppCode or SCreenCode
	 * @throws Throwable, Exception
	 */
	public void Execute(HashMap m,WebDriver driver,String TC_ID,HashMap<String,String> dataMap,HashMap<String, String> testData,Object callingClass,int current_index,String testCaseType,String... params) throws Throwable, Exception
	{
		
		System.out.println(m);
		logger.info("AutomationWrapper....");
		
		autoconfig.GetTestCases(testCaseType,params);
		Execute(m, driver, TC_ID, dataMap,testData, callingClass, current_index, testCaseType,autoconfig.GetTestCases(testCaseType,params),params);

	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 
	 * @param m  			==> Excel Column data (Header data | Parameter data), this is further used on Screen.
	 * @param driver		==> webdriver
	 * @param TC_ID	   		==> Test Case ID
	 * @param dataMap  		==> Data Map to hold any return value from Screen
	 * @param Screen_code  	==> SCREEN CODE to fetch data from database
	 * @param callingClass 	==> reference of calling class to invoke customer method
	 * @param current_index	==> To read data from excel in case of multiple dataset (Ingenium Jointlife and Multiple Client scenario
	 * @param testCaseType	==> To pass test case type to invoke particular Locator sheet to load
	 * @param DataSheetDtoList	==> To pass DataSheetDtoList to invoke loop to parse Locators
	 * @param params		==> Array of String to pass Scenarios or AppCode or SCreenCode
	 * @throws Throwable, Exception
	 */
	public void Execute(HashMap m,WebDriver driver,String TC_ID,HashMap<String,String> dataMap,HashMap<String, String> testData,Object callingClass,int current_index,String testCaseType,List<DataSheetDto> DataSheetDtoList,String... params) throws Throwable, Exception
	{
		
		System.out.println(m);
		logger.info("AutomationWrapper....");
		
		//Get TestCaseName
//		testReporter.startTest(TC_ID);
		try 
		{

			 for(DataSheetDto datasheet:DataSheetDtoList)
			 {
				 
				 System.out.println(">>" +datasheet.getDATASHEET_KEY());
				 
				 String datasheetValue = "";
				 
				
				 //If Blank then [get Value from DataMap for PARAM1] OR [get Value from Excel HashMap for DATASHEET_KEY]
				 //If Blank then [get Value from DataMap for PARAM2] OR datasheetvalue 
				 
				 if(datasheet.getDATASHEET_KEY().equalsIgnoreCase("TXT_CARefA")){
						
  						System.out.println("Debug Start Here");
						/*driver.findElement(By.xpath("//*[@id='menuTabBar']/div[2]/div[1]")).click();
						String s=driver.findElement(By.xpath("//div[@class='clip-content']/b")).getText();
						System.out.println(s);*/
					}

				 System.out.println("Value is: ----->"+dataMap.get(datasheet.getLOCATOR_PARAM1()));
				 System.out.println("Value is: ----->"+m.get(datasheet.getDATASHEET_KEY()));

				 
				 
				 datasheetValue = "".equalsIgnoreCase(datasheet.getDATASHEET_KEY())?dataMap.get(datasheet.getLOCATOR_PARAM1()):m.get(datasheet.getDATASHEET_KEY()).toString();
				 
				 datasheetValue = "".equalsIgnoreCase(datasheetValue)?dataMap.get(datasheet.getLOCATOR_PARAM2()):datasheetValue;

				 logger.info(datasheet.getDATASHEET_KEY() + " - datasheetValue: " + datasheetValue);
				 
//				 try
//				 {
					 if(!("NA".equalsIgnoreCase(datasheetValue)))
					 {
						 doEvents(datasheet,datasheetValue,driver,TC_ID,dataMap, testData,callingClass,current_index);
						
					 }
					 else
					 {
						 logger.info("Not eligible for Action " + datasheet.getDATASHEET_KEY());
					 }

//				 }
//				 catch(Throwable ex)
//				 {
//					 testReporter.Log_Fail("Failed Events", "Failed while processing Events for EventID:" + datasheet.getID());
//					 logger.error("In catch DataSheetDto lOOP " + ex);
//					 ex.printStackTrace();				 
//				 }
					 //ReadExcelByMapping.writeExcel1(datasheetPath,sheetName,TC_ID,"Testcase_Status","");
				 
					// System.out.println(testReporter.getRunStatus());
			 }

			 
			 
		} 
		catch (Exception e) 
		{
			System.out.println("In catch ::"+testReporter);
			e.printStackTrace();
			//ReadExcelByMapping.writeExcel1(datasheetPath,sheetName,TC_ID,"Testcase_Status","FAIL");
			testReporter.Log_Fail("Failed Events", "Failed while processing Events :Unknown Exception check trace logs",driver,snapshotsPath);
			
		}
		finally
		{
			logger.info("testData : " + testData);
			logger.info("dataMap : " + dataMap);
			logger.info("m : " + m);
			autoconfig.destroy();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void doEvents(DataSheetDto datasheet,String datasheetValue,WebDriver driver,String TC_ID,HashMap<String,String> dataMap,HashMap<String, String> testData,Object callingClass,int current_index) throws Throwable,Exception
	{
		System.out.println("getLOCATOR_TYP " + datasheet.getLOCATOR_TYP());
		System.out.println("getLOCATOR_KEY " + datasheet.getLOCATOR_KEY());
		System.out.println("datasheetValue " + datasheetValue);
				//driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		 switch(datasheet.getEVENT_CODE())
		 {
		 case "CLICK_ON_BUTTON":    
			 ActionUtils.click_On_Button(datasheet.getLOCATOR_TYP(),datasheet.getLOCATOR_KEY() , datasheet.getREMARKS(),driver);
			 break;

		 case "BUTTON_DROPDOWN":    
			 ActionUtils.button_dropdown(datasheet.getLOCATOR_PARAM1(), datasheet.getLOCATOR_KEY() , datasheetValue,datasheet.getDATASHEET_KEY(),driver);
			 break;
			
		 case "SELECT_COMBOBOX":    
			 ActionUtils.button_dropdown(datasheet.getLOCATOR_PARAM1(), datasheet.getLOCATOR_KEY().replaceAll("~VALUE~", datasheetValue) , datasheetValue,datasheet.getDATASHEET_KEY(),driver);
			 break;
			 
		 case "VALUE_BY_SCRIPT":
			 ActionUtils.setValueByElementScript(datasheet.getLOCATOR_KEY() , datasheetValue,driver);
			 break;							 
			 
		 case "CHECKED_BY_SCRIPT":
			 method.Javascriptexecutor_forClick(driver, "//input[@id='" + datasheet.getLOCATOR_KEY() + "']", datasheet.getLOCATOR_KEY());
			 ActionUtils.setCheckedByElementScript(datasheet.getLOCATOR_KEY() , datasheetValue,driver);
			 break;							 
			 
		 case "ENTER_TEXT":    
			 
			 ActionUtils.enter_Text(datasheet.getLOCATOR_TYP(), datasheet.getLOCATOR_KEY(),datasheetValue, driver,datasheet.getDATASHEET_KEY());						 
			 break;
			 
		 case "ENTER_TEXT_data":    
			 
			if(dataMap.get(datasheet.getLOCATOR_PARAM1())==null)
			 ActionUtils.enter_Text(datasheet.getLOCATOR_TYP(), datasheet.getLOCATOR_KEY(),datasheetValue, driver,datasheet.getDATASHEET_KEY());
			else
				ActionUtils.enter_Text(datasheet.getLOCATOR_TYP(), datasheet.getLOCATOR_KEY(),dataMap.get(datasheet.getLOCATOR_PARAM1()), driver,datasheet.getDATASHEET_KEY());
			 break;
			 
		 case "ENTER_TEXT_VALUE":    
			 System.out.println(datasheet.getLOCATOR_KEY().replaceAll("~VALUE~",testData.get(datasheet.getLOCATOR_PARAM1())));
			 method.inputText(driver, datasheet.getLOCATOR_KEY().replaceAll("~VALUE~",testData.get(datasheet.getLOCATOR_PARAM1())), datasheetValue, datasheet.getDATASHEET_KEY());
			// ActionUtils.enter_Text(datasheet.getLOCATOR_TYP(),datasheet.getLOCATOR_KEY().replaceAll("~VALUE~",testData.get(datasheet.getLOCATOR_PARAM1())),datasheetValue, driver);						 
			 break;
			 

		 case "CLEAR_TEXT":
			 ActionUtils.clear_Text(datasheet.getLOCATOR_TYP(), datasheet.getLOCATOR_KEY(), driver); 
			 break;

		 case "SELECTDROPDOWNACTION":
			 ActionUtils.selectDropDownAction(datasheet.getLOCATOR_TYP(), datasheet.getLOCATOR_KEY(), datasheet.getLOCATOR_PARAM1(), datasheetValue, driver);
			 break;
			 
		 case "SELECTDROPDOWNACTION_VALUE":
			
			 ActionUtils.selectDropDownAction(datasheet.getLOCATOR_TYP(),datasheet.getLOCATOR_KEY().replaceAll("~VALUE~",testData.get(datasheet.getLOCATOR_PARAM2())), datasheet.getLOCATOR_PARAM1(), datasheetValue, driver);
			 break;
			 
		 case "SELECTDROPDOWNACTION_DATA":
				
			 ActionUtils.selectDropDownAction(datasheet.getLOCATOR_TYP(),datasheet.getLOCATOR_KEY().replaceAll("~VALUE~",(datasheet.getLOCATOR_PARAM2())), datasheet.getLOCATOR_PARAM1(), datasheetValue, driver);
			 break;	 

		 case "CLICK_ON_LINK":
			 ActionUtils.click_On_Link(datasheet.getLOCATOR_TYP(), datasheet.getLOCATOR_KEY(), driver); 
			 break;

		 case "GETELEMENTTEXT":
			 dataMap.put(datasheet.getLOCATOR_PARAM1(), ActionUtils.getElementText(datasheet.getLOCATOR_TYP(), datasheet.getLOCATOR_KEY(), driver));
			 break;

		 case "LOG_PASS_WITH_SCREENSHOT":
			 testReporter.Log_Pass_with_Screenshot(datasheet.getLOCATOR_PARAM1(), datasheet.getLOCATOR_PARAM2(), driver,snapshotsPath); 
			 break;

		 case "SWITCHFRAME":
			 method.SwitchFrame(datasheet.getLOCATOR_PARAM1(), driver);
			 break;	

		 case "WRITEEXCEL":
//			 ReadExcelByMapping.writeExcel(datasheetPath,sheetName,Integer.parseInt(TC_ID), datasheet.getLOCATOR_PARAM1(), dataMap.get(datasheet.getLOCATOR_PARAM1()));
			 String excelPath="";
			 if(datasheet.getLOCATOR_PARAM2()==null || datasheet.getLOCATOR_PARAM2().equals(""))
			 {
				 excelPath=datasheetPath;
			 }
			 else if(datasheet.getLOCATOR_PARAM2().equals("datasheetResultPath"))
			 {
				 excelPath=datasheetResultPath;				 
			 }
			 
			 System.out.println("ExcelColumn:: "+dataMap.get(datasheet.getLOCATOR_PARAM1()));
			 System.out.println(datasheet.getLOCATOR_PARAM1());
			 System.out.println(excelPath+"SheetName : "+sheetName);
			//HashMap<String, String> colInxMap=ReadExcelByMapping.getColumnDataIndex(Constant, sheetName, 0);
			//ReadExcelByMapping.writeExcel1(excelPath,sheetName,Integer.parseInt(TC_ID), datasheet.getLOCATOR_PARAM1(), getExcelColumn(testData,datasheet.getLOCATOR_PARAM1(), dataMap.get(datasheet.getLOCATOR_PARAM1())));
			 ReadExcelByMapping.writeExcel1(excelPath,sheetName,TC_ID, datasheet.getLOCATOR_PARAM1(), dataMap.get(datasheet.getLOCATOR_PARAM1()));
			 break;	
			 
		 case "CLEAREXCEL_COLUMN":
//			 ReadExcelByMapping.writeExcel(datasheetPath,sheetName,Integer.parseInt(TC_ID), datasheet.getLOCATOR_PARAM1(), dataMap.get(datasheet.getLOCATOR_PARAM1())); 
			 ReadExcelByMapping.writeExcel(datasheetResultPath,sheetName,Integer.parseInt(TC_ID), datasheet.getLOCATOR_PARAM1(), "");
			 break;								 

		 case "METHOD.Selectdropdown_ByVisibleText":
			 if(!dataMap.get(datasheet.getLOCATOR_PARAM1()).contains("NA")){
			 method.selectDropBoxByVisibleText(driver, datasheet.getLOCATOR_KEY(), dataMap.get(datasheet.getLOCATOR_PARAM1()), datasheet.getDATASHEET_KEY());
			 }
			 else
				 method.selectDropBoxByVisibleText(driver, datasheet.getLOCATOR_KEY(), datasheetValue, datasheet.getDATASHEET_KEY());
			 break;	
			 
		 case "METHOD.Selectdropdown_ByVisibleText_Param":
			 if(!dataMap.get(datasheet.getLOCATOR_PARAM1()).contains("NA")){
				 method.selectDropBoxByVisibleText(driver, datasheet.getLOCATOR_KEY().replaceAll("~VALUE~",dataMap.get(datasheet.getLOCATOR_PARAM1())), datasheetValue, datasheet.getDATASHEET_KEY());
			 } break;	
			 
		 case "WAIT":
			 Thread.sleep(Integer.parseInt(datasheet.getLOCATOR_PARAM1())); 
			 break;		

		 case "READEXCEL":
//			 dataMap.put(datasheet.getLOCATOR_PARAM1(), testData.get(datasheet.getLOCATOR_PARAM1()).split("~")[current_index]);
			 dataMap.put(datasheet.getLOCATOR_PARAM1(), readExcelColumn(testData.get(datasheet.getLOCATOR_PARAM1()).split("~"),current_index));
			 break;		

		 case "CUSTOM_METHOD":

			 	//datasheet.getLOCATOR_PARAM1() will be Custom Method Name of Calling class
			 	//datasheet.getLOCATOR_PARAM2() we are referring as data sheet column name instead of getDATASHEET_KEY, 
			 	//this column will be used to store Mulitple Datasheet column name.
			 
			// if(null!=datasheet.getDATASHEET_KEY() || !"".equals(datasheet.getDATASHEET_KEY()))
			
			 if(null!=datasheet.getLOCATOR_PARAM2() && !"".equals(datasheet.getLOCATOR_PARAM2()))
			 {
				 
				 Method customMethod = callingClass.getClass().getMethod(datasheet.getLOCATOR_PARAM1(),java.lang.String[].class);
				 customMethod.invoke(callingClass, new Object[] {datasheet.getLOCATOR_PARAM2().split("~")});
			 }
			 else
			 {
				 Method customMethod = callingClass.getClass().getMethod(datasheet.getLOCATOR_PARAM1());
				 customMethod.invoke(callingClass);
			 }

			 break;	
			 
		 case "METHOD.CLICKRADIOBUTTONBYXPATH":
//			 method.Javascriptexecutor_forClick(driver, "//input[@id='" + datasheetValue + "']", datasheetValue);
			 method.Javascriptexecutor_forClick(driver, datasheet.getLOCATOR_KEY().replaceAll("~VALUE~", datasheetValue), datasheet.getREMARKS());
			 break;	

		 case "METHOD.CLICKMULTIPLECHECKS":
//			 method.Javascriptexecutor_forClick(driver, "//input[@id='" + datasheetValue + "']", datasheetValue);
			 if(! dataMap.get(datasheet.getLOCATOR_PARAM1()).equalsIgnoreCase("NA"))
			 {
			 method.Javascriptexecutor_forClick(driver, datasheet.getLOCATOR_KEY().replaceAll("~VALUE~", dataMap.get(datasheet.getLOCATOR_PARAM1())), datasheet.getREMARKS());
			
			 }
			 break;	
			 
		 case "METHOD.CLICKLINK_BYTEXT":

			
			 method.Javascriptexecutor_forClick(driver, datasheet.getLOCATOR_KEY().replace("~VALUE~",datasheet.getLOCATOR_PARAM2()).replace("~VALUE1~", datasheetValue), datasheet.getREMARKS());
			
			 
			 break;	
			 
		 case "METHOD.CLICKBUTTONBYXPATH":

			 method.Javascriptexecutor_forClick(driver, datasheet.getLOCATOR_KEY(), datasheet.getREMARKS());

			 break;
			 
		 case "METHOD.WAIT_ELEMENT_VISIBLE":

			 method.waitForElementVisible(driver, datasheet.getLOCATOR_KEY(), datasheet.getDATASHEET_KEY()); 

			 break;								 

		 case "METHOD.SET_VALUE_BY_XPATH_SCRIPT":

			 method.javascriptExecutor_Setvalue(driver, datasheet.getLOCATOR_KEY(), datasheetValue, datasheet.getDATASHEET_KEY()); 
			// ActionUtils.enter_Text(datasheet.getLOCATOR_TYP(), datasheet.getLOCATOR_KEY(),datasheetValue, driver,datasheet.getDATASHEET_KEY());						 
			 

			 break;			
			 
			 
		 case "METHOD.PAGE_VERIFY":

			 method.verifyPageLoad(driver);

			 break;		
			 
		
		 case "METHOD.WAIT_ELEMENT_NOT_PRESENT":

			 method.waitForElementNotPresent(driver,  datasheet.getLOCATOR_KEY());

			 break;			 
			 
		 case "METHOD.INPUT_TEXT":

//			 method.inputText();

			 break;	
			 
		 case "METHOD.MAXIMIZE":

			 driver.manage().window().maximize();

			 break;	
			 
		 case "METHOD.WAIT_ELEMENT_CLICKABLE":

		 method.waitForElementClickable(driver,datasheet.getLOCATOR_KEY(),"");

			 break;	
			 
		 case "METHOD.ACTION_DOUBLECLICK":

			 
			 driver.switchTo().defaultContent();
			// method.doubleClick(driver, datasheet.getLOCATOR_KEY());

				 break;	
			 
		 case "METHOD.WAIT_PAGE_LOAD":

			 method.waitForPageToLoad(driver); 

			 break;								 
			 
		 case "METHOD.UPLOAD_DOC":

		// method.Upload_documet_Robot() 

			 break;		
			 
		 case "RREFESH_PAGE":

				driver.navigate().refresh();

					 break;	
			 
		 case "METHOD.IS_ELEMENT_DISPLAYED":

			 method.isElementDisplayed(driver, datasheet.getLOCATOR_KEY(), datasheetValue);

			 break;		
			 
		 case "METHOD.SCROLLING_BYXpath":

			// method.windowScroll(driver,Integer.parseInt(datasheet.getLOCATOR_PARAM1()),Integer.parseInt(datasheet.getLOCATOR_PARAM2()));
			 method.windowScroll1(driver,datasheet.getLOCATOR_KEY());
			 break;		
			
		 case "METHOD.SCROLLING":

				method.windowScroll(driver,Integer.parseInt(datasheet.getLOCATOR_PARAM1()),Integer.parseInt(datasheet.getLOCATOR_PARAM2()));
				
				 break;	 
			 
		 case "METHOD.SWITCH_WINDOW":

			method.switchToWindow(Integer.parseInt(datasheet.getLOCATOR_PARAM1()), driver);

			 break;	
		 case "EXCEL.UPDATE_CHILD":

			 
			 /*
			 Excel_Handling.Get_Data(datasheet.getREF_TC_ID(), "flag");
			 String mod=Excel_Handling.Get_Data(datasheet.getREF_TC_ID(), "testCaseName");
			 Class cls = Class.forName(mod);			
			 TestModIF obj =(TestModIF) cls.newInstance();
			 obj.Execute(Excel_Handling.TestData.get(datasheet.getREF_TC_ID()), datasheet.getREF_TC_ID(), sheetName, timeout,datasheetPath,datasheetResultPath);
			  */
			 break;	
		 default:     
			 System.out.println("Region not available"); 
		 }

	}
	
	
	
	
	
	
	
	
	
	//value = CurrentIndex - Arrsize
	private String readExcelColumn(String[] excelValueArr,int CurrentIndex)
	{
		return excelValueArr[CurrentIndex];
	}
	
	
	
	private String getExcelColumn(HashMap<String, String> testdata ,String key,String newValue)
	{
		String excelColValue= testdata.get(key)==null?"":testdata.get(key);
		if(excelColValue.length()==0) 
		{
			excelColValue=newValue;
		}
		else if(excelColValue.length()>0) 
		{
			excelColValue=excelColValue + "~" + newValue;
		}
		testdata.put(key, excelColValue);
		return excelColValue;
	}	
	
}

