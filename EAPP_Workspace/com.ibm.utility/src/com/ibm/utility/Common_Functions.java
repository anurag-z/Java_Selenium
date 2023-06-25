package com.ibm.utility;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;

public class Common_Functions {
		
	private static Logger logger=Logger.getLogger(Common_Functions.class);
	static String snappath=null;	
	public String GetXMLTagValue(String xmlpath, String tagname){
		
		String val=null;

		
		try {
			
			// Method to get the xml tag value from any given xml
			File f = new File(xmlpath);
			
			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			
			Document doc = docBuilder.parse(f);
			
			val = doc.getElementsByTagName(tagname).item(0).getTextContent();
			
				
			}
		 catch (Exception e) {
			logger.error("Exception in GetXMLTagValue():: ", e);
			e.printStackTrace();
		}
		
		return val;
		
	}
	
	public By locatorParser(String locator) {

		By loc = By.id(locator);

		if (locator.contains("id"))
		    loc = By.id(locator.substring(locator.indexOf("\"") + 1,
		        locator.length() - 2));

		else if (locator.contains("name"))
		    loc = By.name(locator.substring(locator.indexOf("\"") + 1,
		        locator.length() - 2));
		else if (locator.contains("cssSelector"))
		    loc = By.cssSelector(locator.substring(locator.indexOf("\"") + 1,
		        locator.length() - 2));

		if (locator.contains("xpath"))
		    loc = By.xpath(locator.substring(locator.indexOf("\"") + 1,
		        locator.length() - 2));

		return loc;

		}
	
	public static String captureScreenshot(WebDriver driver,String snapshotsPath){
		
		try{
			
		
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			System.out.println("TakesScreenshot Path:: "+scrFile.getAbsolutePath());
	        //The below method will save the screen shot in d drive with name "screenshot.png"
			String name = scrFile.getName();
			File desFile=new File(snapshotsPath+name);
			System.out.println("snapshotsPath Path:: "+desFile.getAbsolutePath());
            FileUtils.copyFile(scrFile, desFile);
//            String newPath = System.getProperty("user.dir") +"/";
//            snappath = newPath+snapshotsPath+name;
            snappath=desFile.getCanonicalPath();
            
		}catch(Exception e){
			
			System.out.println("Issue with snapshot capture");
			
		}
		return snappath;
	}
}
