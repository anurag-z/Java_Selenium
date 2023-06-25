package com.ibm.automation.IF;

import java.util.HashMap;

public interface TestModIF 
{

	public HashMap<String, String> dataMap=null;

//	@Parameters({ "paramData", "tcID", "sheetName", "timeout","datasheetPath","datasheetResultPath" })
//	@Test
	public void Execute(HashMap paramData, String tcID, String sheetName, String timeout,String datasheetPath,String datasheetResultPath) throws Exception,Throwable;
	
}
