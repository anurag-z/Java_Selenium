package com.ibm.Eapp.mod;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.store.Path;
//import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ibm.Eapp.auto.Controller;
import com.ibm.Eapp.db.AutomationDbConnect;
import com.ibm.Eapp.db.EappDbConnect;
import com.ibm.Eapp.utility.Constants;
import com.ibm.automation.IF.TestModIF;
import com.ibm.automation.ui.AutomationWrapper;
import com.ibm.automation.ui.CustomException;
import com.ibm.automation.ui.DataSheetDto;
import com.ibm.utility.Excel_Handling;



public class EappCSEMod_old extends Controller implements TestModIF{

	// public WebDriver driver;
	public boolean flag = false;
	private static Logger logger=Logger.getLogger(EappCSEMod_old.class);
	private HashMap dataSheetMap;
	private AutomationWrapper autoWrapper;
	private EappDbConnect dbObj;
	private HashMap dataMap;
	String Scenario2;
	private ArrayList<String>CSEDocs=new ArrayList<String>();
	private ArrayList<String>lstOfDocs=new ArrayList<String>() ;
	//private D2cDbConnect dbObj;

	//@Parameters({ "paramData", "tcID", "sheetName", "timeout" })
	//@Parameters({  "tcID", "timeout" ,"datasheetPath","datasheetResultPath"})
	@Test
	
	public void Execute(HashMap paramData, String tcID, String sheetName, String timeout,String datasheetPath,String datasheetResultPath) throws Exception,Throwable {
	
		
		logger.info("EappCSEMod initiated....");
		
		//this.dataSheetMap=paramData;
		dbObj = new EappDbConnect();
		this.dataMap=dataMap;
		/*System.out.println(paramData);
		System.out.println(lstDocs);*/
		//lstOfDocs=lstDocs;
		//To identiify Secnarios
		Scenario2=sheetName;
		String Scenario3=paramData.get("ChannelName").toString();
		//paramData.putAll(testData);
		this.dataSheetMap=paramData;
		System.out.println(paramData);
		
		testReporter.startTest(TC_ID + "_" + Scenario2 + "_" + Scenario3);
		autoWrapper = new AutomationWrapper(datasheetPath,datasheetResultPath ,sheetName,
				Constants.snapshotsPath, Constants.reportPath, timeout, Constants.DB_SQL_URL, Constants.DB_SQL_DRV,
				Constants.DB_SQL_USR, Constants.DB_SQL_PWD,testReporter);
		
		autoWrapper.Execute(paramData, driver, TC_ID, dataMap,paramData, this,"EAPP",Scenario2,Scenario3);
		
		//testReporter.startTest(TC_ID + "_" + Scenario2 + "_" + Scenario3);
		/*autoWrapper = new AutomationWrapper(datasheetPath,datasheetResultPath ,sheetName,
				Constants.snapshotsPath, Constants.reportPath, timeout, Constants.DB_SQL_URL, Constants.DB_SQL_DRV,
				Constants.DB_SQL_USR, Constants.DB_SQL_PWD,testReporter);*/
		
		
//		
//		Extent_Reporting.destroy();
//		
		//Thread.sleep(9000);
	/*Excel_Handling.ExcelReader(datasheetPath, "CSEMastersheet", datasheetResultPath, "CSEMastersheet");
		
		HashMap<String, HashMap> result=Excel_Handling.getExcelDataAll("CSEMastersheet", "flag", "Yes", "SC_ID");
		System.out.println(result);
		String s=Excel_Handling.Get_Data(TC_ID, "SC_ID");
		System.out.println(result.get(s));*/
		
		
		//autoWrapper.Execute(paramData, driver, TC_ID, dataMap,paramData, this,"EAPP",Scenario2,Scenario3);
	//	List<DataSheetDto> list =doCSEModule("CSE",sheetName);
		//autoWrapper.Execute(paramData, driver, TC_ID, dataMap,paramData, this,0,"EAPP",list,"");
		
	
	}
	public void Execute(HashMap paramData, String tcID, String sheetName, String timeout,String datasheetPath,String datasheetResultPath,ArrayList lstDocs) throws Exception,Throwable {
	
		lstOfDocs=lstDocs;
	}
	/*public List<DataSheetDto> doCSEModule(String params,String Sheetname) throws Throwable,Exception
	{
		String strOptions=params;
		AutomationDbConnect aDb=new AutomationDbConnect();
	
			
			//Execute Query
			//changed
			//List<DataSheetDto> list=aDb.getCSELocators(strOptions,Sheetname);
			//return list;
			//autoWrapper.Execute(dataSheetMap, driver, TC_ID, dataMap,dataSheetMap, this,0,"EAPP",list,"");
		
		
	}*/
	
	public void cseDocName(String... params) throws Throwable,Exception
	{
		List<WebElement> lst = driver.findElements(By.xpath("//div[@id='"+params[0]+"div']/ul//a"));
		int i = 1;
		String s;
		for(WebElement e: lst)
		{
			s=driver.findElement(By.xpath("(//div[@id='"+params[0]+"div']/ul//a)["+i+"]")).getText();
			
			if(!s.isEmpty())
			{
			CSEDocs.add(s);
			
			
			}
			i++;
			System.out.println(CSEDocs);
		}

	


}
	public void cseDocVerification() throws Throwable,Exception
	{


		for (String expected: lstOfDocs) {
			if (CSEDocs.contains(expected)) {
				testReporter.Log_Pass(expected , "Document is present in CSE");
				System.out.println("Doc name is verified");
			} else {
				testReporter.Log_Fail(expected,  "Document is not present in CSE");
				System.out.println("Search function verification failed" + expected);
			}
		}
		
	}
	
	
	public void cseDocDownloadVerification(String... params) throws Throwable,Exception
	{
		

		LinkedHashMap<String,String> TC=(LinkedHashMap<String, String>) dataSheetMap;
		String userID=TC.get("UserID");
		String CSE_folder_path="C:\\Users\\"+userID+"\\Downloads\\CSE";
		String Downloadedpath="C:\\Users\\"+userID+"\\Downloads";
		String sub_dir_Path=CSE_folder_path+"\\"+dataMap.get("APP_NO");
		File theDir = new File(CSE_folder_path);
		File subDir =new File(sub_dir_Path);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		 File copied;
		 boolean flag;
		 int i = 1;
		 int k = 0;
		 String s;
		 int counter=1;

		try{
			
			if( lstOfDocs.contains("INSURED MEDICAL REQUIREMENTS"))
			{
				k=lstOfDocs.size()-1;
			}
			
			if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			try{
				
				theDir.mkdir();
				subDir.mkdir();

			} 
			catch(SecurityException se){

			}        
		}
		List<WebElement> lst = driver.findElements(By.xpath("//div[@id='"+params[0]+"div']/ul//a"));
		
		for(WebElement e: lst)
		{
			s=driver.findElement(By.xpath("(//div[@id='"+params[0]+"div']/ul//a)["+i+"]")).getText();
			copied = new File(sub_dir_Path+"\\"+s.replaceAll(" ", "")+".pdf");
			if(!(s.isEmpty()||s.equalsIgnoreCase("Required Medical Documents Completed")))
			{
				do{
					driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
					
					js.executeScript("arguments[0].click();", e);
					
					Thread.sleep(1500);
			
					
			    counter++;
				}
				while(!(copied.exists())&&counter<4);
				File lastModifiedFile=getLatestFilefromDir(Downloadedpath);
			
			
				 FileUtils.copyFile(lastModifiedFile, copied);
				
				
					flag =copied.exists();
					if(flag==true)
					{
						testReporter.Log_Pass(s, "Successfully downloaded");
					}
					else if(flag==false&&counter>=4)
					{
						testReporter.Log_Fail(s, "Successfully downloaded");
					}
				
		  
               // System.out.println("Copy done!!!!!!!!!!!!!!");
			
			}
			i++;
			
		}
		int noOfCSEDocsDownloaded=theDir.listFiles().length;
		
		
		
		if(!((noOfCSEDocsDownloaded-2)==k))
		{
			System.out.println("Some of the documents from CSE is not downloadable");
		}
		else{
			System.out.println("Successfully all the CSE documents are downloadable");
		}
		
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally{ 
			
			theDir.delete();
		
		 }

		
		 }
	
	private File getLatestFilefromDir(String dirPath)
	{
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }

	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile;
	}
		
		}
