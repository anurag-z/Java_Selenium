package com.ibm.Eapp.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ISuite;
import org.testng.ISuiteListener;

import com.ibm.Eapp.utility.Constants;
import com.ibm.utility.reports.Reporter;


public class SuiteListener implements ISuiteListener {


	
	@Override
	public void onStart(ISuite arg0) 
	{
		System.out.println("SuiteListener.onStart");
		// Start the report here
        String filename=(new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")).format(new Date());
    	filename="Execution_Summary_"+filename+".html";
		
		Reporter.createReporter(Constants.reportPath + filename, true);
	}
	
	@Override
	public void onFinish(ISuite arg0) 
	{
		System.out.println("SuiteListener.onFinish");
	//	Reporter.closeReport();
	}



}
