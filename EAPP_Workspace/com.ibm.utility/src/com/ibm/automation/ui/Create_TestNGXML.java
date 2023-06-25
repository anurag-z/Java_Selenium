package com.ibm.automation.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.ibm.utility.Common_Functions;
import com.ibm.utility.Excel_Handling;
import com.ibm.utility.reports.Extent_Reporting;

public class Create_TestNGXML 
{
	
	private static Logger logger=Logger.getLogger(Create_TestNGXML.class);

	
	
	
	public void createXMLfile(HashMap<String,String> mapParams) throws Exception
	{
		
		logger.info("Create_TestNGXML.createXMLfile....Start");
	
		
		
		//==========================================================
		//Step1 Get Control Sheet
		System.out.println(mapParams.get("datasheetPath"));
		System.out.println(mapParams.get("datasheetResultPath"));
		Map<String, HashMap> map_control=this.getExcelReadControlSheet(mapParams.get("datasheetPath"),mapParams.get("datasheetResultPath"));
		//==========================================================
		
		

		
		
		//==========================================================
		//Loop to read control sheet
        for(HashMap map: map_control.values())
        {
        	
        	String sheetName="";
        	String is_Execute="";
   			
        	
        	
	    	sheetName = map.get("TC_Name").toString();
	    	is_Execute = map.get("Execute").toString();

    		
    		
	    	//==========================================================
    		//If Execution of Suite is allowed
    		if("YES".equalsIgnoreCase(is_Execute))
    		{
    			
    			
    			
    			//==========================================================
    			//Step2
    			Map<String, HashMap> map_sheets = this.getExcelReadDataSheet(mapParams.get("datasheetPath"),mapParams.get("datasheetResultPath"),sheetName);
    			//==========================================================
    			
    			
    			XmlSuite suite = new XmlSuite();
    			
    			//==========================================================
        		for(String key: map_sheets.keySet())
        		{
        			logger.info(key);
        			logger.info(Excel_Handling.Get_Data(key, "flag"));
        	    	
        			String testCaseName=Excel_Handling.Get_Data(key, "testCaseName");
        			String URL=Excel_Handling.Get_Data(key, "URL");
        			
        			if("Yes".equalsIgnoreCase(Excel_Handling.Get_Data(key, "flag")))
        			{
        	         	this.getTestSuite(suite,mapParams.get("timeout"),URL,mapParams.get("suiteName"),key,mapParams.get("testController"),testCaseName,sheetName,mapParams.get("driverPath"),mapParams.get("proxyFile"),mapParams.get("suiteListener"),mapParams.get("suiteListenerClass"),mapParams.get("datasheetPath"),mapParams.get("datasheetResultPath"));
        			}
        		}    			
        		//==========================================================

        		
                
                
        		//Create and RunING XML
        		System.out.println("sheetName :" + sheetName);
    			List<String> suites=this.createTestSuiteXML(suite, mapParams.get("createTestNGPath"));
    	        this.runTestNG(suites);
    		}
    		//==========================================================

        }
      //==========================================================		
		
//        extent_reporting.destroy();
		logger.info("Create_TestNGXML.createXMLfile....End");
	}
	
	
	
	
	
	
	
	
	//Step1
	private Map<String, HashMap> getExcelReadControlSheet(String datasheetPath,String datasheetResultPath) throws Exception 
	{
		logger.info("Create_TestNGXML.getExcelReadControlSheet....Start");
    	//calling out the excel datasheet instance to get all the "Y" data for setting up the testngxml
    	Excel_Handling excel_Control = new Excel_Handling();
    	excel_Control.copy(datasheetPath, datasheetResultPath);
    	
    	excel_Control.ExcelReader(datasheetPath, "Control", datasheetResultPath, "Control");
		try 
		{
			excel_Control.getExcelDataAll("Control", "Execute", "Yes", "Sr_No");
		} 
		catch (Exception e) 
		{
			logger.error("Exception in getExcelReadControlSheet():: ", e);
			e.printStackTrace();
		}
		logger.info("Create_TestNGXML.getExcelReadControlSheet....End");
		return excel_Control.TestData;
	}
	
	
	
	
	private Map<String, HashMap> getExcelReadDataSheet(String datasheetPath,String datasheetResultPath,String sheetname) 
	{
		logger.info("Create_TestNGXML.getExcelReadDataSheet....Start");
		Excel_Handling excel_Sheets = new Excel_Handling();
		try 
		{
			//code added for separate sheets
	    	excel_Sheets.ExcelReader(datasheetPath, sheetname.trim(),datasheetResultPath , sheetname.trim());
			excel_Sheets.getExcelDataAll(sheetname, "flag", "Yes", "tc_id");
		} 
		catch (Exception e) 
		{
			logger.error("Exception in getExcelReadDataSheet():: ", e);
			e.printStackTrace();
		}
		logger.info("Create_TestNGXML.getExcelReadDataSheet....End");
		return excel_Sheets.TestData;
	}	
	
	
	
	
	private void getTestSuite(XmlSuite suite,String timeout,String appURL,String suitName,String key, String ControllerClass,String TestClassName,String sheetName,String driverPath,String proxyFile,String suiteListener,String suiteListenerClass,String datasheetPath,String datasheetResultPath) throws Exception
	{
    	//creation of the testng xml based on parameters/data
		logger.info("Create_TestNGXML.getTestSuite....Start");

        suite.setName (suitName);
        suite.setParallel("tests");
        suite.setThreadCount(2);
//           
//        System.out.println(suiteListener);
//        System.out.println(suiteListenerClass);
        
        if(null!=suiteListener && suiteListener.equalsIgnoreCase("Y"))
        {
        	List<String> listeners=new ArrayList<String>();
        	listeners.add(suiteListenerClass);
        	suite.setListeners(listeners);
        }
        
        
        if(Integer.parseInt(Excel_Handling.Get_Data(key, "browser_instance"))>1)
        {
        	
        	suite.setParallel("tests");
    		suite.setThreadCount(Integer.parseInt(Excel_Handling.Get_Data(key, "browser_instance")));
        	
        	for(int i=1;i<=Integer.parseInt(Excel_Handling.Get_Data(key, "browser_instance"));i++)
        	{
    		
        		XmlTest test = new XmlTest ();		        		
        		test.setName (key+"_Instance_"+i);
        		
    	        test.setPreserveOrder("false");
    	        test.addParameter("tcID", key);
    	        test.addParameter("browserType", Excel_Handling.Get_Data(key, "browser_type"));
    	        test.addParameter("appURL", appURL);
    	        test.addParameter("driverPath", driverPath);
    	        test.addParameter("proxyFile", proxyFile);
    	        
        		test.addParameter("temp", "temp"+i);
        		
        		Map<String, String> paramMap=new HashMap<String, String>();
        		paramMap.put("mod", TestClassName);
        		paramMap.put("tcID", key);
        		paramMap.put("sheetName", sheetName);
        		paramMap.put("timeout", timeout);
        		paramMap.put("datasheetPath", datasheetPath);
        		paramMap.put("datasheetResultPath", datasheetResultPath);        		
        		 
        		XmlClass testClass = new XmlClass();
        		testClass.setName (ControllerClass);
        		testClass.setParameters(paramMap);
        		test.setXmlClasses (Arrays.asList (new XmlClass[] { testClass}));
        		
        		suite.addTest(test);
        	}
    		
    	}
        else
        {
    		
    		XmlTest test = new XmlTest();
    		
    		test.setName(key);
	        test.setPreserveOrder("true");
	        test.addParameter("tcID", key);
	        test.addParameter("browserType", Excel_Handling.Get_Data(key, "browser_type"));
	        test.addParameter("appURL", appURL);
	        test.addParameter("driverPath", driverPath);
	        test.addParameter("proxyFile", proxyFile);
	        

	        
    		Map<String, String> paramMap=new HashMap<String, String>();
    		paramMap.put("mod", TestClassName);
    		paramMap.put("tcID", key);
    		paramMap.put("sheetName", sheetName);
    		paramMap.put("timeout", timeout);
    		paramMap.put("datasheetPath", datasheetPath);
    		paramMap.put("datasheetResultPath", datasheetResultPath);      		
    		   		
    		XmlClass testClass = new XmlClass();
    		testClass.setName (ControllerClass);
    		testClass.setParameters(paramMap);
	        test.setXmlClasses (Arrays.asList (new XmlClass[] { testClass}));
	        
	        suite.addTest(test);
	        
    	}
        
		logger.info("Create_TestNGXML.getTestSuite....End");

	}
	
	
	
	
	
	//====================================================
	private List<String>  createTestSuiteXML(XmlSuite suite,String createTestNGPath) throws Exception
	{
        List<String> suites = new ArrayList<String>();
     
        File f =FileUtils.getFile(createTestNGPath);
        if(f.exists()) f.delete();
        f.createNewFile();
        FileUtils.writeByteArrayToFile(f, suite.toXml().getBytes());
        suites.add(f.getPath());
        
        return suites;
	}
	//====================================================
	
	
        
	//====================================================	
	private void runTestNG(List<String> suites) throws Exception
	{
		TestNG testNG = new TestNG();
        testNG.setTestSuites(suites);
        testNG.run();
	}
	//====================================================	


}
