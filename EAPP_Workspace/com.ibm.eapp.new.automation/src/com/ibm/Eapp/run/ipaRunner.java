package com.ibm.Eapp.run;


import java.io.File;

import org.apache.log4j.Logger;

import com.ibm.Eapp.xml.TestNGXML_Helper;

public class ipaRunner 
{
	private static Logger logger=Logger.getLogger(ipaRunner.class);
	public static void main(String[] args) throws Exception 
	{
		
		logger.info("ipaRunner.main() Start::");
		File currentDirFile = new File(".");
		String helper = currentDirFile.getAbsolutePath();
		String path=System.getProperty("user.dir");
//		Extent_Reporting extent_reporting=new Extent_Reporting(Constants.snapshotsPath, Constants.reportPath);
		TestNGXML_Helper testNGXMLObj=new TestNGXML_Helper();
		testNGXMLObj.createXMLfile();
		
		
		
	
//		Extent_Reporting.destroyAll();
		logger.info("ipaRunner.main() End::");
		
	}
}