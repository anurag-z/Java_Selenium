package com.ibm.utility.reports;

import java.util.Locale;

import com.ibm.utility.reports.interfaces.ITestReporter;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

public class Reporter {

	private static ExtentReports reporter = null;
	private static ITestReporter testReporter = null;
	
	public static void createReporter(String filePath)
	{
		if(reporter == null)
			reporter = new ExtentReports(filePath);
		
	}
	
	public static void createReporter(String filePath, boolean replaceExisting)
	{
		if(reporter == null)
			reporter = new ExtentReports(filePath, replaceExisting);
	}
	
	public static void createReporter(String filePath, boolean replaceExisting, NetworkMode networkMode)
	{
		if(reporter == null)
			reporter = new ExtentReports(filePath, replaceExisting, networkMode);
	}
	
	public static void createReporter(String filePath, boolean replaceExisting, NetworkMode networkMode, Locale locale)
	{
		if(reporter == null)
			reporter = new ExtentReports(filePath, replaceExisting, networkMode, locale);
	}
	
	public static void createReporter(String filePath, boolean replaceExisting, DisplayOrder displayOrder)
	{
		if(reporter == null)
			reporter = new ExtentReports(filePath, replaceExisting, displayOrder);
	}
	
	public static void createReporter(String filePath, boolean replaceExisting, DisplayOrder displayOrder,  Locale locale)
	{
		if(reporter == null)
			reporter = new ExtentReports(filePath, replaceExisting, displayOrder, locale);
	}
	
	public static void createReporter(String filePath, boolean replaceExisting, DisplayOrder displayOrder,  NetworkMode networkMode)
	{
		if(reporter == null)
			reporter = new ExtentReports(filePath, replaceExisting, displayOrder, networkMode);
	}
	
	public static void createReporter(String filePath, boolean replaceExisting, DisplayOrder displayOrder,  NetworkMode networkMode, Locale locale)
	{
		if(reporter == null)
			reporter = new ExtentReports(filePath, replaceExisting, displayOrder, networkMode, locale);
	}
	
	public static void flushReport()
	{
		if(reporter != null)
			reporter.flush();
	}
	
	public static void closeReport()
	{
		if(reporter != null)
			reporter.close();
	}
	
	// this code can be further simplified
	public static ITestReporter getTestReporter()
	{
		if(testReporter == null)
		{
			if(reporter != null)
			{
				testReporter = new TestReporter(reporter);
			}
		}
		return testReporter;
	}
	
	
	
	
	
	


//	public void Log_Pass_with_Screenshot(String stepName, String passMessage, WebDriver driver) throws InterruptedException
//	{
//		testReporter.log(LogStatus.PASS, stepName, passMessage);
//		Thread.sleep(5000);
////		String img = reporter.addScreenCapture(Common_Functions.captureScreenshot(driver,snapshotsPath));
////		System.out.println("test.log path:: "+img);
//		System.out.println("stepName:: "+stepName);
////		testReporter.log(LogStatus.INFO, stepName, "Success Snap: " + img);			
//	}
//
//	public void Log_Fail_with_Screenshot(String stepName, String passMessage, WebDriver driver) throws InterruptedException
//	{
//		testReporter.log(LogStatus.FAIL, stepName, passMessage);
//		Thread.sleep(5000);
////		String img = reporter.addScreenCapture(Common_Functions.captureScreenshot(driver,snapshotsPath));
////		testReporter.log(LogStatus.INFO, stepName, "Success Snap: " + img);
//	}
}