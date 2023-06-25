package com.ibm.Eapp.mod;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import org.testng.annotations.Test;

import com.ibm.Eapp.auto.Controller;
import com.ibm.Eapp.db.AutomationDbConnect;
import com.ibm.Eapp.db.EApp_EE_DbConnect;
import com.ibm.Eapp.db.EappDbConnect;
import com.ibm.Eapp.utility.Constants;
import com.ibm.automation.IF.TestModIF;

import com.ibm.automation.ui.AutomationWrapper;
import com.ibm.automation.ui.CustomException;
import com.ibm.automation.ui.DataSheetDto;
import com.ibm.utility.Excel_Handling;
import com.ibm.utility.reports.Reporter;



public class EappCSEMod extends Controller implements TestModIF{
	
		// public WebDriver driver;
		public boolean flag = false;
		private static Logger logger=Logger.getLogger(EappCSEMod.class);
		private HashMap<String, String> dataMap;
		private HashMap dataSheetMap,datasheetData,dataSheetMap1;
		private AutomationWrapper autoWrapper;
		private EappDbConnect dbObj;
		private EApp_EE_DbConnect dbObj_EE;
		private ArrayList<String>CSEDocs=new ArrayList<String>();
		ArrayList<String> lstOfDocs=new ArrayList<String>();
		HashMap<String, HashMap> result1;
		HashMap<String, HashMap> Data;
		String Scenario2,Scenario3;
		String Temp;
		

		String CSE_folder_path, Downloadedpath,sub_dir_Path;
		String userID;
		JavascriptExecutor js = (JavascriptExecutor)driver;
		 File copied,theDir,subDir;
		 boolean CSE_flag;
		 int i = 1;
		 int k=0, noOfCSEDocsDownloaded;
		 String s;
		 int counter=1;
		
		
		//private D2cDbConnect dbObj;

//	@Parameters({ "paramData", "tcID", "sheetName", "timeout" })
	
		 @Test
		public void Execute(HashMap paramData, String tcID, String sheetName, String timeout,String datasheetPath,String datasheetResultPath) throws Exception,Throwable {
			logger.info("EappMod initiated....");
			
			this.dataSheetMap=paramData;
			
			this.dataSheetMap1=paramData;
			dbObj = new EappDbConnect();
			
			//dbObj_EE = new EApp_EE_DbConnect();
			//To identiify Secnarios
			
			Temp=TC_ID;
			Scenario2=sheetName;
			Scenario3=paramData.get("ChannelName").toString();
			
			Controller con =new Controller();
			
			try
			
			{
				result1=con.datasheetLoading(tcID,sheetName,paramData,datasheetPath,datasheetResultPath);
			
		if(!(result1==null))
{
		
			dataSheetMap.putAll(result1);
			System.out.println(dataSheetMap);
			System.out.println(result1);
		}
			}catch(Exception e)
			{
				System.out.println(e);
			}
			dataMap = new HashMap<String, String>();
			

			testReporter.startTest(TC_ID + "_" + Scenario2 + "_" + Scenario3);
			autoWrapper = new AutomationWrapper(datasheetPath,datasheetResultPath ,sheetName,
					Constants.snapshotsPath, Constants.reportPath, timeout, Constants.DB_SQL_URL, Constants.DB_SQL_DRV,
					Constants.DB_SQL_USR, Constants.DB_SQL_PWD,testReporter);
			
			autoWrapper.Execute(paramData, driver, TC_ID, dataMap,paramData, this,"EAPP",Scenario2,Scenario3);
			
//			
//			Extent_Reporting.destroy();
//			
			Thread.sleep(9000);

		}
		
		/*public void cseBasicComparison(String... params) throws Throwable,Exception
		{

			
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			


		}*/
		
		
		public void cseDocName(String... params) throws Throwable,Exception
		{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			List<WebElement> lst = driver.findElements(By.xpath("//div[@id='"+params[0]+"div']/ul//a"));
			int i = 1;
			String s;
			for(WebElement e: lst)
			{
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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


			System.out.println("EAPP_UI_Docs :" +lstOfDocs);
			System.out.println("CSE_UI_Docs :"+CSEDocs);

			for ( String expected: lstOfDocs) {

				try{
					if (CSEDocs.contains(expected))
					{

						testReporter.Log_Pass(expected , "Document is present in CSE");
						System.out.println("Doc name is verified");
					} 
					else 
					{
						testReporter.Log_Pass(expected,  "Document is not present in CSE");

					}
				}
				catch(Exception e)
				{
					System.out.println("Search function verification failed" + expected);
					testReporter.Log_Fail(expected,  "Document is not present in CSE");
				}

			}

		}
		
		public void cseDocFormation() throws Throwable,Exception
		{

			userID= dataSheetMap.get("UserID").toString();
			CSE_folder_path="C:\\Users\\"+userID+"\\Downloads\\CSE";
			Downloadedpath="C:\\Users\\"+userID+"\\Downloads";
			sub_dir_Path=CSE_folder_path+"\\"+dataMap.get("APP_NO");
			theDir = new File(CSE_folder_path);
			subDir =new File(sub_dir_Path);
			
					//k=lstOfDocs.size();
			
				
				if (!theDir.exists()) {
				System.out.println("creating directory: " + theDir.getName());
				try{
					
					theDir.mkdir();
				

				} 
				
				catch(SecurityException se){

				}  
				
			}
				if (!subDir.exists()) {
					System.out.println("creating directory: " + subDir.getName());
					try{
						
						subDir.mkdir();
						

					}
					catch(SecurityException se){

					} 
				}
				
			
		}
			public void cseDocDownloadVerification(String... params) throws Throwable,Exception
			{
				
				try
				
				{
					
				List<WebElement> lst = driver.findElements(By.xpath("//div[@id='"+params[0]+"div']/ul//a"));
				if(lst.size()==0)
				{
					testReporter.Log_Fail("CSE", "No document Download is Successful");
				}
				else
				{
					for(WebElement e: lst)
					{
						
						
						WebElement li=driver.findElement(By.xpath("(//div[@id='"+params[0]+"div']/ul//a)["+i+"]"));
						s=li.getText();
						
						
						
						copied = new File(sub_dir_Path+"\\"+s.replaceAll(" ", "")+".pdf");
						
						if(!(s.isEmpty()||s.equalsIgnoreCase("Required Medical Documents Completed")))
						{

							WebDriverWait wait = new WebDriverWait(driver, 360);
							wait.until(ExpectedConditions.elementToBeClickable(li));

							do{
								driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

								CSEDocs.add(s);
								System.out.println(CSEDocs);
						
								js.executeScript("arguments[0].click();", e);

								Thread.sleep(1500);


								counter++;
							}
								while(!(copied.exists())&&counter<3);
							//while((copied.exists()));
							File lastModifiedFile=getLatestFilefromDir(Downloadedpath);


							FileUtils.copyFile(lastModifiedFile, copied);


							CSE_flag =copied.exists();
							if(CSE_flag==true)
						{
							testReporter.Log_Pass(s, "Successfully downloaded");
						}
							else

							{
								//testReporter.Log_Fail(s, "Download is UnSuccessful");
								testReporter.Log_Fail(s, "Download is UnSuccessful");
								System.out.println("Download is failed");
							}


							// System.out.println("Copy done!!!!!!!!!!!!!!");

						}
						i++;

					}
				}
				}catch(Exception e)
				{
					System.out.println(e);
					testReporter.Log_Fail("CSE documents", "Download is UnSuccessful");
				}
				finally{ 
					
					i=1;
					
				
				 }
				}
				
			public void cseNoOfDocDownload() throws Throwable,Exception
			{
				try{
				noOfCSEDocsDownloaded=subDir.listFiles().length;
				k=lstOfDocs.size();
				System.out.println("EAPP Downloads:" + k);
				System.out.println("CSE downloads:" +noOfCSEDocsDownloaded);

				if(!((noOfCSEDocsDownloaded-2)==(k)))
				{
					System.out.println("Some of the non mandatory documents are not uploaded or some of the Documents from CSE is not downloadable");
				}
				else{
					System.out.println("Successfully all the CSE documents are downloadable");
				}
				}catch(Exception e)
				{
					System.out.println(e);
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
				Thread.sleep(2000);
				driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
				
				String URL=dataSheetMap.get(params[0]).toString();
		
		
				driver.navigate().to(URL);
	

				driver.navigate().refresh();
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
		
		
		
		
		
		public void doDocsUpload() throws CustomException
		
		{

			String docPath=dataSheetMap.get("Path").toString();
			try{
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				StringSelection ss = new StringSelection(docPath);
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
		
	public void doDocsUpload(String... params) throws CustomException
		
		{

			String docPath=dataSheetMap.get(params[0]).toString();
			
			try{
				StringSelection ss = new StringSelection(docPath);
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
	//	WebElement doc_Status = driver.findElement(By.xpath("//div[@class='formIdDiv']/div[2]"));
		
		for (WebElement e : lst) {
			
			
			if(i>=3){
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("window.scrollBy(0,150)", "");
				
			}
			WebElement doc_Status = driver.findElement(By.xpath("(//div[@class='formIdDiv']/div[2])["+i+"]"));
			System.out.println(doc_Status.getText());
			
			if(doc_Status.getText().contains("DOCUMENT UPLOAD PENDING")|| doc_Status.getText().contains("REJECTED BY CSE")||doc_Status.getText().contains("EXCEPTION BY CSE"))
			{
				
				
				if(!(driver.findElement(By.xpath("//span[@id='syncStatusSpan']")).getText().contains("Sync Successful")))
				{
				System.out.println(e);
				
			
			
			
			
			String s=driver.findElement(By.xpath("(//a[@title='Browse & Upload']/label//preceding::label[@class='docName'])["+i+"]")).getText();
			System.out.println(driver.findElement(By.xpath("(//a[@title='Browse & Upload']/label//preceding::label[@class='docName'])["+i+"]")).getText());
			
			lstOfDocs.add(s); 
			System.out.println(lstOfDocs);
			act.moveToElement(e).click().build().perform();
			Thread.sleep(2000);
			//method.Upload_documet_Robot(Excel_Handling.Get_Data(TC_ID, "Path"));
			doDocsUpload();
			Thread.sleep(2000);
					
				}
			}
			
			i++;
			
			
		}
		
		
		WebElement e1 = driver.findElement(By.xpath("//a[@title='Save & Continue' or @title='Confirm']"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if(e1.isDisplayed())

		{
			WebDriverWait wait = new WebDriverWait(driver, 360);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Save & Continue' or @title='Confirm']")));
			act.moveToElement(e1).click().build().perform();
			//e1.click();

			//WebDriverWait wait = new WebDriverWait(driver, 360);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]")));
			Thread.sleep(1000);
			WebElement e2 = driver.findElement(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]"));

			//e2.click();
			act.moveToElement(e2).click().build().perform();

		}	else
		{
			WebElement back = driver.findElement(By.xpath("//img[@src='pages/preSalesManager/eApp/html/images/iconBack.png']"));
			
			Thread.sleep(1000);
			JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
			myExecutor.executeScript("arguments[0].click();", back);
		
		}	

		
		}catch(Exception e)
		{
			throw new CustomException("Docs_upload browse element not visible", e, ErrorCodes.TIMEOUT);
		}
		
		
		}
	public void doDocSubmit(String... params) throws Exception
	{
		try{
			
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, 360);
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='pgIdentyProofPd']/div[3]")));
			
			WebElement doc_Status = driver.findElement(By.xpath("//div[@class='pgIdentyProofPd']/div[3]"));
			//WebElement doc_Status = driver.findElement(By.xpath("//div[@class='formIdDiv']/div[3]/following::div"));
			Thread.sleep(1000);
			
		
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='formIdDiv']/div[3]/following::div")));
			
			//System.out.println("Standard:"+doc_Status.getText());
			
			if(doc_Status.getText().contains("DOCUMENT UPLOAD PENDING") || doc_Status.getText().contains("REJECTED BY CSE")||doc_Status.getText().contains("EXCEPTION BY CSE"))
		{
				
			if(!(driver.findElement(By.xpath("//span[@id='syncStatusSpan']")).getText().contains("Sync Successful")))
			{	
				System.out.println("doc_status :"+driver.findElement(By.xpath("//span[@id='syncStatusSpan']")).getText());
				
				//String Doc_NonMedical=dataSheetMap.get("Doc_NonMedicalProof").toString();
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			String docType=dataSheetMap.get(params[0]).toString();
			
			if(!docType.equalsIgnoreCase("NA"))
			{
			//driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

			WebElement selectDropBox = driver.findElement(By.xpath("//select[@id='documentType']"));
			Select select = new Select(selectDropBox);
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
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
				
			//	WebDriverWait wait = new WebDriverWait(driver, 360);
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]")));
				WebElement e2 = driver.findElement(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]"));

				//act.moveToElement(e2).click().build().perform();
				e2.click();			
				
		}
		}
			else
			{
				WebElement back = driver.findElement(By.xpath("//img[@src='pages/preSalesManager/eApp/html/images/iconBack.png']"));
				Thread.sleep(1000);
				back.click();
			}
			}catch(Exception e)
			{
				throw new CustomException("Docs_upload browse element not visible", e, ErrorCodes.TIMEOUT);
			}
		
	}
	public void doDocSubmit() throws Exception
	{
		try{
			driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
			
			WebElement doc_Status = driver.findElement(By.xpath("//div[@class='formIdDiv']/div[2]"));
			
			WebDriverWait wait = new WebDriverWait(driver, 360);
			wait.until(ExpectedConditions.visibilityOf(doc_Status));
			
			//System.out.println("PAN:"+doc_Status.getText());
			if(doc_Status.getText().contains("DOCUMENT UPLOAD PENDING")|| doc_Status.getText().contains("REJECTED BY CSE")||doc_Status.getText().contains("EXCEPTION BY CSE"))
			{
				if(!(driver.findElement(By.xpath("//span[@id='syncStatusSpan']")).getText().contains("Sync Successful")))
				{
					driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

					Actions act = new Actions(driver);

					WebElement e = driver.findElement(By.xpath("//a[@title='Browse & Upload']/label"));

					act.moveToElement(e).click().build().perform();



					Thread.sleep(2000);


					doDocsUpload();
					Thread.sleep(2000);

					WebElement e1 = driver.findElement(By.xpath("//a[@title='Save & Continue' or @title='Confirm']"));


					e1.click();


					//WebDriverWait wait = new WebDriverWait(driver, 360);
					wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]")));
					WebElement e2 = driver.findElement(By.xpath("//p[@id='WLdialogBody']/button[contains(.,'Close')]"));

					//act.moveToElement(e2).click().build().perform();
					e2.click();

				}
			}
			else
			{
				WebElement back = driver.findElement(By.xpath("//img[@src='pages/preSalesManager/eApp/html/images/iconBack.png']"));
				Thread.sleep(1000);
				back.click();
			}
		}catch(Exception e)
		{
			throw new CustomException("Docs_upload browse element not visible", e, ErrorCodes.TIMEOUT);
		}

	}
		
	
	public void getDocsName(String params[])
	{
		String docTab = params[0];
		
		String s = null;
		String Final;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
			 if(s.contains("Income Proof For AML"))
			 {
				 s=s.replace(" For AML", "");
					
					System.out.println(s);
			 }
		if(!s.contains("Medical Requirements") )
		{				
			if(!s.contains("Pending"))

			{
				Final =(docTab +" "+s).toUpperCase();
				lstOfDocs.add(Final.replaceAll(" +", " "));
				
			}
		}
		
		System.out.println(lstOfDocs);
		}
	}
	
	
	
	public void getMedicalDocsname(String params[])
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		k=lstOfDocs.size();
		List<WebElement> lst = driver.findElements(By.xpath("//label[@class='docName']"));
		for(WebElement e:lst)
		{
			
			lstOfDocs.add(e.getText());
		}
	}

	public void getCSEDocsname()
	{
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String Medical=driver.findElement(By.xpath("//h1[@id='WLdialogTitle']")).getText();
		if(Medical.contains("Medical Documents"))
		{
		WebElement lst = driver.findElement(By.xpath("//h1[@id='WLdialogTitle']/following::p/p"));
		
		System.out.println(lst.getText());
			String Medicals_docs=lst.getText();
			 String[] lines = Medicals_docs.split("\\r?\\n");
			for(String e: lines)
			
				{
				CSEDocs.add(e);
				}
			
		
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
		public void CSEModCalling() throws Throwable,Exception
		{
			/*String CSEModscenario1=dataSheetMap.get(params[0]).toString();
			String CSEModscenario2=dataSheetMap.get(params[1]).toString();*/
			AutomationDbConnect aDb=new AutomationDbConnect();
			
	
				
				List<DataSheetDto> list=aDb.getCSELocators("CSE",Scenario2,Scenario3);
				
				autoWrapper.Execute(dataSheetMap, driver, TC_ID, dataMap,dataSheetMap, this,0,"EAPP",list,"");
			
			
		}
		
		public void alertAction(String...params)
		{
			boolean isAlert=false;
		try
			{
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				
/*				pan=driver.findElement(By.xpath("//h1[contains(.,'')]/following::p")).getText();
				pan=pan.replace("Close", "").trim();
				System.out.println(pan);
				Thread.sleep(1000);*/
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
				if(driver.findElement(By.xpath("//p[contains(.,'"+params[0]+"')]/following::button")).isDisplayed())
				{
					driver.findElement(By.xpath("//p[contains(.,'"+params[0]+"')]/following::button")).click();
					isAlert = true;
				}
		/*	if(driver.findElement(By.xpath("//p[contains(.,'"+params[0]+"')]/following::button")).getText()=="")
			{
				dataSheetMap.put("Doc_PanCard", "Yes");
			}*/
			
		}catch(Exception e)
		{
			isAlert=false;
		}
		}

		public void Errorchecking() throws Exception

		{
			WebElement paymentmsg= driver.findElement(By.xpath("//h1[@id='WLdialogTitle']"));
		
				if ((paymentmsg.getText().contains("Warning")))
				{
					throw new Exception("Payment failed");
				}
			
				
				
			}

		

		public void getAPPNO() {
			try {
				System.out.println(dataMap.get("APP_NO"));
				String App_No = dataMap.get("APP_NO").replaceAll("Primary App Number : ", "");
				logger.info("App_No " + App_No);
				dataMap.put("APP_NO", App_No);
				System.out.println(App_No);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void getOTP() 
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
		
	


		//Checking RWS/CSE status start - 18-03-2019
		public void get_RWS_Status() 
		{
			try
			{
				//dataMap.put("APP_NO", "EA10093247");
				String IAR_status = null;
				String CSE_status = null;

				ResultSet rs = dbObj_EE.get_RWS_Status(dataMap.get("APP_NO"));
				//Checking whether resultset returns nothing (ie. if the payment mode is Online)
				if (rs.next()== false) {
					System.out.println("Application is either incomplete/of online payment mode");
					dataMap.put("RWS_Status","Application is either incomplete/of online payment mode");
					testReporter.Log_Pass("Application status in RWS after login", "RWS status is Not Applicable since application is of online payment mode");

				} else {
					while (rs.next()) {
						IAR_status = rs.getString("IS_IAR_COMPLETE");
						CSE_status = rs.getString("CSE_STATUS");
						System.out.println("Sttaus 1 ->" + IAR_status);
						System.out.println("Sttaus 2 ->" + CSE_status);
					}
					if ((IAR_status.equalsIgnoreCase("true")) && (CSE_status == null)) 
					{
						dataMap.put("RWS_Status", "CSE verification pending");
						System.out.println("CSE Status of Application no. " + dataMap.get("APP_NO") + " is -> "+ dataMap.get("RWS_Status"));
						testReporter.Log_Pass("Application status in RWS after login", "CSE verification pending");
					}
					if ((IAR_status.equalsIgnoreCase("false")) && (CSE_status == null)) {
						{
							dataMap.put("RWS_Status","IAR and CSE verification Pending");
							System.out.println("CSE Status of Application no. "+dataMap.get("APP_NO")+" is -> "+ dataMap.get("RWS_Status"));
							testReporter.Log_Pass("Application status in RWS after login", "IAR and CSE verification Pending");
						}
					}
				}

				logger.info("RWS Status for App Number --->"+ dataMap.get("APP_NO")+ " is --> "+dataMap.get("RWS_Status"));
			}

			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public void CSEWait(String... params) throws NumberFormatException, InterruptedException 
		{
			if(dataSheetMap.get("REF_ID").toString().equalsIgnoreCase("NA"))
			{
				String time=dataSheetMap.get(params[0]).toString();
				System.out.println(time);
				TimeUnit.MINUTES.sleep(Integer.parseInt(time));
			}
		}
		//Checking RWS/CSE status End - 18-03-2019

		//Checking Application status in CSE Start -  26-03-2019
		public void checkCSEStatus(String... params) throws AssertionError
		{
			try
			{
				//method.validateData(driver, datasheet.getLOCATOR_KEY(), dataMap.get("Exp_CSE_status"));
				String xp = params[0].toString();
				System.out.println("EXp Message ->" + dataSheetMap.get("Exp_CSE_status"));
				WebElement element = driver.findElement(By.xpath(xp));
				//Replacing word Close(Text of Close button of pop up) with nothing since its also unnecessarily getting fetched from getText()
				String compareString = element.getText().replace("Close", "").trim();
				System.out.println("Actual Message ->" +compareString);

				try {
					//Assert.assertTrue(compareString.equalsIgnoreCase((String) dataSheetMap.get("Exp_CSE_status"))==true, "Application status is Not matching");
					Assert.assertTrue(compareString.equalsIgnoreCase((String) dataSheetMap.get("Exp_CSE_status"))==true); 
					System.out.println("Expected and Actual statuses are matched");
					testReporter.Log_Pass("Application status in CSE after login", "Application status is matching");
				}
				catch(AssertionError  e1)
				{
					System.out.println("Expected and Actual statuses are Not matching");
					testReporter.Log_Fail("Application status in CSE after login", "Application status is Not matching");
					//e1.printStackTrace();
				}
				//without Assertion
				/*System.out.println("Data is matched");
					testReporter.Log_Pass(driver.getTitle(), compareString);

					System.out.println("Data is Not matching");
					//testReporter.Log_Fail(driver.getTitle(), compareString, driver,snapshotsPath);

				if(compareString.equalsIgnoreCase((String) dataSheetMap.get("Exp_CSE_status")))
				{
					System.out.println("Data is matched");
					testReporter.Log_Pass(driver.getTitle(), compareString);
				}
				else
				{
					System.out.println("Data is Not matching");
					//testReporter.Log_Fail(driver.getTitle(), compareString, driver,snapshotsPath);
				}*/
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		//Checking Application status in CSE End -  26-03-2019

		@SuppressWarnings("unchecked")
		public void initializingTCID() throws Throwable 
		{
			
			TC_ID=(String) dataSheetMap.get("REF_ID");
			Data=Excel_Handling.getExcelDataAll(Scenario2, "Reload", "Yes", "tc_id");
			datasheetData=Data.get(TC_ID);
			datasheetData.putAll(result1);
			dataSheetMap=datasheetData;
			
			System.out.println(datasheetData);
			
		}
		
		
		public void CSEErrorFlow(String... params) throws Throwable 
		{
			//method.validateData(driver, datasheet.getLOCATOR_KEY(), dataMap.get("Exp_CSE_status"));
			try{
				String noErrorflag="No";
			String xp = params[0].toString();
			//String URL = dataSheetMap1.get(params[1]).toString();
		//	new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xp)));
		 if(driver.findElement(By.xpath(xp))!=null)
				
			 {
			
				WebElement element = driver.findElement(By.xpath(xp));
				//Replacing word Close(Text of Close button of pop up) with nothing since its also unnecessarily getting fetched from getText()
				String compareString = element.getText().replace("Close", "").trim();
				System.out.println("Actual Message ->" +compareString);

				switch(compareString)
				{
				/*case "Document has already been moved.":
					dataMap.put("Error", "CSE");
					break;*/
				case "Payment is not completed. Kindly go to Eapp to complete payment.":
					dataMap.put("Error", "payment");
					break;
				case "All mandatory documents are not uploaded. Kindly go to Eapp or App tracker to complete document upload.":
					dataMap.put("Error", "DocumentUpload");
					break;
				case "IAR is not completed. Kindly go to Eapp to complete IAR.":
					dataMap.put("Error", "IAR");
					break;
				case "Application not found.  Please verify the details entered.":
					destroy();
					break;
					
				 default:     
					 driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						if(driver.findElement(By.xpath("//label[contains(.,'Plan Name:')]/following::span")).isDisplayed())
						{ 
				          noErrorflag = "yes";
							break;
						}
					
					
					
					
					
				}
				
				if(noErrorflag.equals("No"))
				
				{
					AutomationDbConnect aDb=new AutomationDbConnect();

					List<DataSheetDto> list=aDb.GetTestCases(Scenario2,Scenario3,dataMap.get("Error"));
					
						autoWrapper.Execute(datasheetData, driver, TC_ID, dataMap,datasheetData, this,0,"EAPP",list,"");
						TC_ID=Temp;
						
					
				}
				
					
					/*JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					driver.get(URL);
					driver.findElement(By.xpath("//input[@id='j_username']")).clear();
					driver.findElement(By.xpath("//input[@id='j_username']")).sendKeys((String)dataSheetMap.get("username"));
					driver.findElement(By.xpath("//input[@id='j_password']")).sendKeys((String)dataSheetMap.get("password"));
					WebElement Elogin=driver.findElement(By.xpath("//a[@class='loginBtn']"));
					myExecutor.executeScript("arguments[0].click();", Elogin);
				//	driver.findElement(By.xpath("//a[@class='loginBtn']")).click();
					WebElement eapp=driver.findElement(By.xpath("//img[@src='./images/IconsonLandingPage/EApp.png']"));
					myExecutor.executeScript("arguments[0].click();", eapp);
					driver.manage().timeouts().implicitlyWait(360, TimeUnit.SECONDS);
				//	eapp.click();
					String app_no=(String) dataMap.get("APP_NO");
					WebElement eapp1=driver.findElement(By.xpath("//input[@placeholder='Search in report']"));
					
	
					WebDriverWait wait = new WebDriverWait(driver, 360);
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search in report']")));
					driver.manage().timeouts().implicitlyWait(360, TimeUnit.SECONDS);
					
					//eapp1.sendKeys(app_no);
					myExecutor.executeScript("arguments[0].value='"+app_no+"';",eapp1);
					
					WebElement eapp2=driver.findElement(By.xpath("//*[@id='applicationListTableBody']/tr/td[1]/a"));
					myExecutor.executeScript("arguments[0].click();", eapp2);
					eappReloading("payment");*/
				}
			 }catch(Exception e)
			 {
				 System.out.println(e);
				  throw new RuntimeException();
			 }
				
	
		}
		
		public void cseRejectMsg(String... params) throws Throwable 
		{

			try{
				
				String cseDecision_xp = params[0].toString();

				if(driver.findElement(By.xpath(cseDecision_xp))!=null)

				{

					Select element = new Select(driver.findElement(By.xpath(cseDecision_xp)));

					String CSE_Decision = element.getFirstSelectedOption().getText();
					System.out.println("Actual Message ->" +CSE_Decision);
					dataMap.put("Reject_decision", CSE_Decision);
					
	
				}
			}
			catch(Exception e)
			{
				
			}
		}
		
		public void cseRejectFlow() throws Throwable 
		{
			try{
				
			if(dataMap.get("Reject_decision").equalsIgnoreCase("Requirements pending & send back to user"))

					{
						dataMap.put("Error", "Reject");
						AutomationDbConnect aDb=new AutomationDbConnect();

						List<DataSheetDto> list=aDb.GetTestCases(Scenario2,Scenario3,dataMap.get("Error"));
						System.out.println(dataSheetMap);
						
						dataSheetMap.put("Doc_PendingQuestionary", "NA");

						autoWrapper.Execute(dataSheetMap, driver, TC_ID, dataMap,dataSheetMap, this,0,"EAPP",list,"");
					

					}
				}
			catch(Exception e)
			{
				System.out.println(e);
				throw new RuntimeException();
			}


		}
		
		public void cseAccept(String...params) throws Throwable 
		{
			try{
				int inc=1;
			List<WebElement> Accept_list=driver.findElements(By.xpath("//div[@id='"+params[0]+"div']/ul//a"));
			 for(WebElement a:Accept_list)
			 {
				if(a.getText().contains("Required Medical Documents Completed"))
				{
					driver.findElement(By.xpath("//div[@id='"+params[0]+"div']/ul//a[contains(.,'Required Medical Documents Completed')]//following::li/input")).click();
				}
				 if(inc>3)
				 {
					driver.findElement(By.xpath("(//div[@id='"+params[0]+"div']/ul//li[2]/input[@value='0'])["+inc+"]")).click();
				 }
				
				 if(params[0].equalsIgnoreCase("proposer"))
				 {
					 driver.findElement(By.xpath("(//div[@id='"+params[0]+"div']/ul//li[2]/input[@value='0'])["+inc+"]")).click();
				 }
				 inc++;
			 }

					
				}
			catch(Exception e)
			{
				System.out.println(e);
				throw new RuntimeException();
			}


		}
		
		
		
		
		/*public void eappReloading(String... params) throws Throwable 
		{
			AutomationDbConnect aDb=new AutomationDbConnect();
			
			List<DataSheetDto> list=aDb.GetTestCases(Scenario2,Scenario3,params[0]);
			try{
			autoWrapper.Execute(datasheetData, driver, TC_ID, dataMap,datasheetData, this,0,"EAPP",list,"");
			TC_ID=Temp;
			}
			catch(Exception e)
			{
				
			}
		}*/
		

}
		
