package com.ibm.Eapp.xml;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.ibm.Eapp.utility.Constants;
import com.ibm.automation.ui.Create_TestNGXML;
import com.ibm.utility.Common_Functions;

public class TestNGXML_Helper 
{
	
	private static Logger logger=Logger.getLogger(TestNGXML_Helper.class);
	
	@SuppressWarnings({ "deprecation", "static-access" })
	@Test 
	public void createXMLfile() throws Exception
	{
		logger.info("TestNGXML_Helper.createXMLfile....Start");
		
		Create_TestNGXML testIngXML=null;
		
		
		
		//Common configs
		HashMap<String,String> mapParams=new HashMap<String,String>();
		mapParams.put("testController", Constants.testController);
		mapParams.put("driverPath", Constants.driverPath);
		mapParams.put("proxyFile", Constants.proxyFile);
		mapParams.put("suiteListener", "Y");
		mapParams.put("suiteListenerClass", Constants.suiteListenerClass);
		
		for(HashMap<String,String> process:Constants.TestProcessMap.values())
		{
			System.out.println(process);
			testIngXML=new Create_TestNGXML();
			mapParams.put("suiteName", process.get("suiteName"));
			mapParams.put("datasheetPath", process.get("datasheetPath"));
			mapParams.put("datasheetResultPath", process.get("datasheetResultPath"));			
			mapParams.put("createTestNGPath", process.get("createTestNGPath"));			
			mapParams.put("configPath", process.get("configXmlPath"));
			String timeout=(new Common_Functions().GetXMLTagValue(process.get("configXmlPath"), "timeout"));
			mapParams.put("timeout", timeout);
			testIngXML.createXMLfile(mapParams);
		}

		
		logger.info("TestNGXML_Helper.createXMLfile....End");
	}
	
	


}
