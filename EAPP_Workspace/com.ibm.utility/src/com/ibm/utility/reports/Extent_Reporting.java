package com.ibm.utility.reports;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.ibm.utility.Common_Functions;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Extent_Reporting 
{
	
	public static String snapshotsPath;
	
	private static ExtentTest test;
	
	private static String filename;
    
    private static ExtentReports extent; 
	
	public Extent_Reporting(String snapshotsPath,String reportPath)
	{
		this.snapshotsPath=snapshotsPath;
    	DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();    
        String d=dateFormat.format(date);
    	filename="Execution_Summary_"+d+".html";
    	System.out.println("ExtentReports Path:: "+(reportPath+filename));
    	extent = new ExtentReports(reportPath+filename, true);
//    	extent = new ExtentReports("D:/" + filename, true);
	}
	
	
	public static ExtentReports getExtent() {
		return extent;
	}


	public static void startTest(String testCaseName)
	{
		test = extent.startTest(testCaseName, "Execution started for : "+testCaseName);
	}
	 

	public static void Log_Pass(String stepName, String passMessage)
	{
		test.log(LogStatus.PASS, stepName, passMessage);
	}

	public static  void Log_Fail(String stepName, String failMessage, WebDriver driver) throws InterruptedException
	{
		test.log(LogStatus.FAIL, stepName, failMessage);
		Thread.sleep(5000);
		String img = test.addScreenCapture(Common_Functions.captureScreenshot(driver,snapshotsPath));
		test.log(LogStatus.INFO, stepName, "Error Snap: " + img);
	}

	public static  void Log_Pass_with_Screenshot(String stepName, String passMessage, WebDriver driver) throws InterruptedException
	{
		test.log(LogStatus.PASS, stepName, passMessage);
		Thread.sleep(5000);
		String img = test.addScreenCapture(Common_Functions.captureScreenshot(driver,snapshotsPath));
		System.out.println("test.log path:: "+img);
		System.out.println("stepName:: "+stepName);
		test.log(LogStatus.INFO, stepName, "Success Snap: " + img);			
	}

	public static  void Log_Fail_with_Screenshot(String stepName, String passMessage, WebDriver driver) throws InterruptedException
	{
		test.log(LogStatus.FAIL, stepName, passMessage);
		Thread.sleep(5000);
		String img = test.addScreenCapture(Common_Functions.captureScreenshot(driver,snapshotsPath));
		test.log(LogStatus.INFO, stepName, "Success Snap: " + img);
	}
	

	public static void destroy()
	{
		extent.flush();
	}
	
	
	public static void destroyAll()
	{
		extent.flush();		
		extent.endTest(test);		
	}	
}
