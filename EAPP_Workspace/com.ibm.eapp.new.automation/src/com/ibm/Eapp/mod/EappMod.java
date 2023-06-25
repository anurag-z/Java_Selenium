package com.ibm.Eapp.mod;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;

import java.awt.datatransfer.StringSelection;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.ibm.Eapp.auto.Controller;
import com.ibm.Eapp.db.AutomationDbConnect;
import com.ibm.Eapp.db.EappDbConnect;
import com.ibm.Eapp.utility.Constants;
import com.ibm.automation.IF.TestModIF;
import com.ibm.automation.ui.AutomationWrapper;
import com.ibm.automation.ui.CustomException;
import com.ibm.automation.ui.DataSheetDto;

import com.ibm.utility.reports.Extent_Reporting;

public class EappMod extends Controller implements TestModIF {

	// public WebDriver driver;
	public boolean flag = false;
	private static Logger logger=Logger.getLogger(EappMod.class);
	private HashMap<String, String> dataMap;
	private HashMap dataSheetMap;
	private AutomationWrapper autoWrapper;
	private EappDbConnect dbObj;
	
	HashMap<String, HashMap> result1;
	ArrayList<String> lstOfDocs=new ArrayList<String>();
	String Scenario2;

	//private D2cDbConnect dbObj;

//	@Parameters({ "paramData", "tcID", "sheetName", "timeout" })
	@Test
	public void Execute(HashMap paramData, String tcID, String sheetName, String timeout,String datasheetPath,String datasheetResultPath) throws Exception,Throwable {
		logger.info("EappMod initiated....");
		
		this.dataSheetMap=paramData;
		dbObj = new EappDbConnect();
		
		//To identiify Secnarios
		Scenario2=sheetName;
		String Scenario3=paramData.get("ChannelName").toString();
		
		//Controller con =new Controller();
		
		//enable for CSE Module
		/*result1=con.datasheetLoading(tcID,sheetName,paramData,datasheetPath,datasheetResultPath);
		
		System.out.println(result1);*/
		
		dataMap = new HashMap<String, String>();
	

//		
	testReporter.startTest(TC_ID + "_" + Scenario2 + "_" + Scenario3);
		autoWrapper = new AutomationWrapper(datasheetPath,datasheetResultPath ,sheetName,
				Constants.snapshotsPath, Constants.reportPath, timeout, Constants.DB_SQL_URL, Constants.DB_SQL_DRV,
				Constants.DB_SQL_USR, Constants.DB_SQL_PWD,testReporter);
		
		autoWrapper.Execute(paramData, driver, TC_ID, dataMap,paramData, this,"EAPP",Scenario2,Scenario3);
		
//		
//		Extent_Reporting.destroy();
//		
		Thread.sleep(9000);

	}
	
	
	
	
	
	
	//Custom Method configured in Locator Sheet
	public void doSplitDate(String... params) 
	{
		try
		{
			
			String date=dataSheetMap.get(params[0]).toString();
//			
			
			//TODO : date split logic to be implemented
			
			String DobArr[]=date.split("-");
			String Year=DobArr[2].toString().trim();
			String Month=DobArr[1].toString().trim();
			String day=DobArr[0].toString().trim();
			
			dataMap.put("Day", day);
			dataMap.put("Month", Month);
			dataMap.put("Year", Year);
			
					}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
	
	public void Navigate(String... params) 
	{
		try
		{
			
			String URL=dataSheetMap.get(params[0]).toString();
//			
			
			//TODO : date split logic to be implemented
			
		driver.get(URL);
			
					}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
	

		
		
		
	public void doCheckPOI(String... params) throws Throwable 
		{
			try
			{
				System.out.println(params[0]);
				String POI=dataSheetMap.get(params[0]).toString();
				
				dataMap.put("POI1", "NA");
				dataMap.put("POI2", "NA");
				dataMap.put("POI3","NA" );
				dataMap.put("POI4","NA" );
				System.out.println(">>PP>>" + dataMap);
				
				int i=1;
				for(String paramValue:POI.split(","))
				{
					i++;
					dataMap.put("POI" + i, paramValue);
				}
		
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			System.out.println(">>PP>>" + dataMap);
		}	
	
	
	
	
	
	public void doDocsUpload() throws CustomException,AWTException,InterruptedException
	
	{
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
		String docPath=dataSheetMap.get("Path").toString();
		try{
		
			StringSelection ss = new StringSelection(docPath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, ss);
		/*	String myText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
			System.out.println(myText);
			 Actions action = new Actions(driver);
			 Thread.sleep(1000);
			    action.sendKeys(Keys.ENTER).perform();
			    Thread.sleep(1500);
			    action.sendKeys(Keys.CONTROL+ "v").perform();
			    Thread.sleep(1500);
			    action.sendKeys(Keys.ENTER).perform();
			    */
			    
			//imitate mouse events like ENTER, CTRL+C, CTRL+V
			Robot robot = new Robot();
			robot.delay(250);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(50);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException("Docs_upload browse element not visible", e, ErrorCodes.TIMEOUT);
		}
		
		
	}
	
public void doDocsUpload(String... params) throws CustomException, InterruptedException
	
	{
	driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

		String docPath=dataSheetMap.get(params[0]).toString();
		try{
			StringSelection ss = new StringSelection(docPath);
			
			/* Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
				Transferable trans = sysClip.getContents(ss);
				 sysClip.setContents(trans, this);*/
				
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

			//imitate mouse events like ENTER, CTRL+C, CTRL+V
			Robot robot = new Robot();
			robot.delay(250);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_V);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.delay(50);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}catch(Exception e){
			e.printStackTrace();
			throw new CustomException("Docs_upload browse element not visible", e, ErrorCodes.TIMEOUT);
		}
		
		
	}
	
public void doMedicalDocsUpload() throws Exception
	
	{
	try{
		
	
	//String Doc_NonMedical=dataSheetMap.get("Doc_NonMedicalProof").toString();
		
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
	Thread.sleep(3000);
	
	int i =1;
	Actions act = new Actions(driver);
		
	List<WebElement> lst = driver.findElements(By.xpath("//a[@title='Browse & Upload']/label"));

	for (WebElement e : lst) {
		
		System.out.println(e);
		
		
		if(i>=3){
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("window.scrollBy(0,150)", "");
			
		}
		
		String s=driver.findElement(By.xpath("(//a[@title='Browse & Upload']/label//preceding::label[@class='docName'])["+i+"]")).getText();
		System.out.println(driver.findElement(By.xpath("(//a[@title='Browse & Upload']/label//preceding::label[@class='docName'])["+i+"]")).getText());
		
		lstOfDocs.add(s);
		System.out.println(lstOfDocs);
		act.moveToElement(e).click().build().perform();
		Thread.sleep(2000);
		//method.Upload_documet_Robot(Excel_Handling.Get_Data(TC_ID, "Path"));
		doDocsUpload();
		Thread.sleep(2000);
					
		i++;
		
		
		
	}
	Thread.sleep(1000);
	WebElement e1 = driver.findElement(By.xpath("//a[@title='Save & Continue' or @title='Confirm']"));

	e1.click();
	
	WebDriverWait wait = new WebDriverWait(driver, 80);
	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]")));
	Thread.sleep(1000);
	WebElement e2 = driver.findElement(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]"));

	e2.click();

				
	
	
	}catch(Exception e)
	{
		throw new CustomException("Docs_upload browse element not visible", e, ErrorCodes.TIMEOUT);
	}
	
	
	}
public void doDocSubmit(String... params) throws Exception
{
	try{
		
		
		//String Doc_NonMedical=dataSheetMap.get("Doc_NonMedicalProof").toString();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		String docType=dataSheetMap.get(params[0]).toString();
		
		if(!docType.equalsIgnoreCase("NA"))
		{
		//driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

		WebElement selectDropBox = driver.findElement(By.xpath("//select[@id='documentType']"));
		Select select = new Select(selectDropBox);
		select.selectByVisibleText(docType);
		}
		
	
		Actions act = new Actions(driver);
			
		WebElement e = driver.findElement(By.xpath("//a[@title='Browse & Upload']/label"));

		
		act.moveToElement(e).click().build().perform();
			
				
			
			Thread.sleep(2000);
			//method.Upload_documet_Robot(Excel_Handling.Get_Data(TC_ID, "Path"));
			doDocsUpload();
			Thread.sleep(2000);
			
			WebElement e1 = driver.findElement(By.xpath("//a[@title='Save & Continue' or @title='Confirm']"));
			//act.moveToElement(e1).click().build().perform();
			e1.click();
			
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]")));
			WebElement e2 = driver.findElement(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]"));

			//act.moveToElement(e2).click().build().perform();
			e2.click();			
			
			
			
		
		
		}catch(Exception e)
		{
			throw new CustomException("Docs_upload browse element not visible", e, ErrorCodes.TIMEOUT);
		}
	
}
public void doDocSubmit() throws Exception
{
	try{
		
		
		//String Doc_NonMedical=dataSheetMap.get("Doc_NonMedicalProof").toString();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		//String Doc_path=dataSheetMap.get(param).toString();
		Actions act = new Actions(driver);
			
		WebElement e = driver.findElement(By.xpath("//a[@title='Browse & Upload']/label"));

			act.moveToElement(e).click().build().perform();
			
				
			
			Thread.sleep(2000);
			//method.Upload_documet_Robot(Excel_Handling.Get_Data(TC_ID, "Path"));

			doDocsUpload();
			Thread.sleep(2000);
			
			WebElement e1 = driver.findElement(By.xpath("//a[@title='Save & Continue' or @title='Confirm']"));
			
			//act.moveToElement(e1).click().build().perform();
			e1.click();
		
			
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]")));
			WebElement e2 = driver.findElement(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]"));

			//act.moveToElement(e2).click().build().perform();
			e2.click();
			
						
			
			
			
		
		
		}catch(Exception e)
		{
			throw new CustomException("Docs_upload browse element not visible", e, ErrorCodes.TIMEOUT);
		}
	
}
	
	//Custom Method configured in Locator Sheet
/*	public void getOTP() 
	{
		try
		{
			logger.info("App_No for OTP " + dataMap.get("APP_NO"));
			dataMap.put("OTP", dbObj.getOTP(dataMap.get("APP_NO")));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	*/

public void getDocsName(String params[])
{
	String docTab = params[0];
	
	String s = null;
	String Final;
	List<WebElement> lst = driver.findElements(By.xpath("//div[@id='noReqDoc']//following-sibling::li//following-sibling::h3/a"));
	for (WebElement e : lst) 
	{
		 s=e.getText().replaceAll("\n"," ");
		
		 if(s.contains("Standard"))
			{
			// System.out.println(s.substring(0, 9));
				s=s.replace("Standard ", "");
				
				System.out.println(s);
			}
		 if(s.contains("PAN Card"))
		 {
			 s=s.replace(" Proof", "");
				
				System.out.println(s);
		 }
	if(!s.equalsIgnoreCase("Non Medical Requirements"))
		{
			Final =(docTab +" "+s).toUpperCase();
			lstOfDocs.add(Final.replaceAll(" +", " "));
			
		}
	
	System.out.println(lstOfDocs);
	}
}

public void doMedicalCheckboxes(String... params) throws Throwable 
{
	try
	{
		System.out.println(params[0]);
		String med=dataSheetMap.get(params[0]).toString();
		
		dataMap.put("check1", "NA");
		dataMap.put("check2", "NA");
		dataMap.put("check3","NA" );
		dataMap.put("check4","NA" );
		dataMap.put("check5","NA" );
		dataMap.put("check6","NA" );
		dataMap.put("check7","NA" );
		System.out.println(">>PP>>" + dataMap);
		
		int i=1;
		if(!med.equalsIgnoreCase("NA"))
		{
		for(String paramValue:med.split(","))
		{
			
			dataMap.put("check" + i, paramValue);
			i++;
		}
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	System.out.println(">>PP>>" + dataMap);
}	


	
public void doMedicalDataset(String... params) throws Exception
{
	driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	String DataBlock=dataSheetMap.get(params[0]).toString();
	String SetData=dataSheetMap.get(params[1]).toString();
	
	String[] splitDataSet = DataBlock.split(",");
	try {
		for (int j = 0; j < splitDataSet.length; j++) {

			String[] splits = splitDataSet[j].split(":");

			switch (splits[0].toString().trim()) {

			case "CurrentSymptoms":
				String dataSet = splits[1].toString();
				System.out.println(dataSet);

				String g = "//input[@id='" + SetData
						+ "']/following::div/div[2]/div/div/div/input[@id='exectDiag']";

				WebElement elem = driver.findElement(By.xpath(g));
				JavascriptExecutor exec = (JavascriptExecutor) driver;
				exec.executeScript("arguments[0].scrollIntoView();", elem);

				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("window.scrollBy(0,-200)", "");

				WebElement inputText = driver.findElement(By.xpath(g));
				inputText.clear();
				inputText.sendKeys(dataSet);
				break;
				
			case "DoctorsDetail":
				String dataSet1 = splits[1].toString();
				System.out.println(dataSet1);
				String g1 = "//input[@id='" + SetData
						+ "']/following::div/div[2]/div/div/div/input[@id='dtlTreatDoct']";
				WebElement inputText2 = driver.findElement(By.xpath(g1));
				inputText2.clear();
				inputText2.sendKeys(dataSet1);
				break;
			case "DiagnosisDate":
				String dataSet2 = splits[1].toString();
				System.out.println(dataSet2);
				String g2 = "//input[@id='" + SetData
						+ "']/following::div/div[2]/div/div/div/input[@class='dateDiag']";
				WebElement inputText3 = driver.findElement(By.xpath(g2));
				inputText3.clear();
				inputText3.sendKeys(dataSet2);
				break;
			case "ConsultationLastDate":
				String dataSet3 = splits[1].toString();
				System.out.println(dataSet3);
				String g3 = "//input[@id='" + SetData
						+ "']/following::div/div[2]/div/div/div/input[@class='dateLastDiag']";
				WebElement inputText4 = driver.findElement(By.xpath(g3));
				inputText4.clear();
				inputText4.sendKeys(dataSet3);
				break;
			case "DiagnosisDetail":
				String dataSet4 = splits[1].toString();
				System.out.println(dataSet4);

				String g4 = "//input[@id='" + SetData
						+ "']/following::div/div[2]/div/div/div/input[@id='detailDiag']";
				WebElement inputText5 = driver.findElement(By.xpath(g4));
				inputText5.clear();
				inputText5.sendKeys(dataSet4);
				break;

			}

		}
	} catch (ArrayIndexOutOfBoundsException e) {
		System.out.println(e);
	}
	
	
}
	
	
	//Custom Method configured in Locator Sheet
	public void doMedcialForm(String... params) throws Throwable,Exception
	{
		String[] strMedicalOptions=dataSheetMap.get(params[0]).toString().split(",");
		AutomationDbConnect aDb=new AutomationDbConnect();
		
		for(String strMedicalOptionVal:strMedicalOptions)
		{
			
			//Execute Query
			
			List<DataSheetDto> list=aDb.getMedicalLocators(strMedicalOptionVal);
			autoWrapper.Execute(dataSheetMap, driver, TC_ID, dataMap,dataSheetMap, this,0,"EAPP",list,"");
		}
		
	}
	
	public void getOTP() 
	{
		
		try
		{
			
			logger.info("App_No for OTP " + dataMap.get("APP_NO"));
			System.out.println("APP Number : "+dataMap.get("APP_NO"));
			dataMap.put("OTP", dbObj.getOTP(dataMap.get("APP_NO")));
			System.out.println("OTP : " + dataMap.get("OTP"));
			Thread.sleep(3000);
			driver.findElement(By.xpath("//*[@id='otp']")).sendKeys(dataMap.get("OTP"));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void getAPPNO() {
		try {
			System.out.println(dataMap.get("APP_NO"));
			String App_No = dataMap.get("APP_NO").replaceAll("Primary App Number : ", "");
			logger.info("App_No " + App_No);
			dataMap.put("APP_NO", App_No);
			System.out.println(App_No);
			testReporter.Log_Pass("Application No is " +App_No, App_No+" fetched");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alertAction(String...params)
	{
		boolean isAlert=false;
	try
		{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			if(driver.findElement(By.xpath("//h1[contains(.,'"+params[0]+"')]/following::p/button")).isDisplayed())
			{
				driver.findElement(By.xpath("//h1[contains(.,'"+params[0]+"')]/following::p/button")).click();
				isAlert = true;
			}
		
		
	}catch(Exception e)
	{
		isAlert=false;
	}
	}
	
	public void alertActionUsingErrorMsg(String...params)
	{
		boolean isAlert=false;
	try
		{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			

			String pan=driver.findElement(By.xpath("//h1[contains(.,'')]/following::p")).getText();
			pan=pan.replace("Close", "").trim();
			System.out.println(pan);
			Thread.sleep(1000);
			if(driver.findElement(By.xpath("//p[contains(.,'"+params[0]+"')]/following::button")).isDisplayed())
			{
				driver.findElement(By.xpath("//p[contains(.,'"+params[0]+"')]/following::button")).click();
				isAlert = true;
			}
			if(pan.contains("upload PAN card proof"))
			{
				dataSheetMap.put("Doc_PanCard", "Yes");
			}
			
		
		
	}catch(Exception e)
	{
		isAlert=false;
	}
	}







	

	
	/*public void callingCSEMod(String ...param) throws Throwable 
	{
		try
		{
			
		
//			
			
			//TODO : calling CSE mod
			cse=new EappCSEMod_old();
			
			cse.Execute(dataSheetMap, "",Scenario2,"","","",lstOfDocs);
			
					}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
*/





	
	
	

}
