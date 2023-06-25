package com.ibm.utility.reports.interfaces;

import java.util.Date;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.ITest;

/*
 * This interface is similar to IExtentTestClass interface.
 * However the usage of this interface is bound to the 
 * testing framework instead of reporting framework.
 * Implementing classes will encapsulate the threading logic
 * of a framework inside this interface's implementation.
 */

public interface ITestReporter {
	
	void log(LogStatus logStatus, String stepName, String details) ;

	void log(LogStatus logStatus, String details) ;

	void log(LogStatus logStatus, String stepName, Throwable t) ;

	void log(LogStatus logStatus, Throwable t) ;

	void setDescription(String description) ;
	
	String getDescription() ;
	
	void setStartedTime(Date startedTime) ;
	
	Date getStartedTime() ;
	
	void setEndedTime(Date endedTime) ;
	
	Date getEndedTime() ;
	
	String addScreenCapture(String imgPath) ;

	String addBase64ScreenShot(String base64) ;

	String addScreencast(String screencastPath) ;

	ExtentTest assignCategory(String... categories) ;

	ExtentTest assignAuthor(String... authors) ;

	ExtentTest appendChild(ExtentTest node) ;

	LogStatus getRunStatus() ;

	ITest getTest() ;
	
	void Log_Pass(String stepName, String passMessage);
	void Log_Fail(String stepName, String failMessage) throws InterruptedException;
	void Log_Fail(String stepName, String failMessage,WebDriver driver,String snapshotsPath) throws InterruptedException;
	
	public  void Log_Pass_with_Screenshot(String stepName, String passMessage, WebDriver driver,String snapshotsPath) throws InterruptedException;
	public  void Log_Fail_with_Screenshot(String stepName, String passMessage, WebDriver driver,String snapshotsPath) throws InterruptedException;
	
	
	void startTest(String testName);
	void startTest(String testName, String testDescription);
	void endTest();
}
