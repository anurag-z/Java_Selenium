package com.ibm.automation.ui;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ibm.utility.reports.interfaces.ITestReporter;
/**
* @Class ActionUtils
* @created: Oct 26, 2017 07:22PM
* @author INOS006287(Ankush Khotpal)
* @version 1.1, 25 Nov 2017
*         
*/


public class ActionUtils 
{
	private static WebDriver driver = null;
	private static WebElement element = null;
	
	private static ITestReporter testReporter;
	
	private String datasheetPath;
	private String sheetName;
	private String snapshotsPath;
	private String reportPath;
	private String timeout;
	
	public ActionUtils(){
		
	}
	/*public ActionUtils(String datasheetPath,String sheetName,String snapshotsPath,String reportPath,String timeout)
	{
		this.datasheetPath=datasheetPath;
		this.sheetName=sheetName;
		this.snapshotsPath=snapshotsPath;
		this.reportPath=reportPath;
		this.timeout=timeout;
	}
	*/
	public ActionUtils(String datasheetPath,String sheetName,String snapshotsPath,String reportPath,String timeout,ITestReporter testReporter)
	{
		this.datasheetPath=datasheetPath;
		this.sheetName=sheetName;
		this.snapshotsPath=snapshotsPath;
		this.reportPath=reportPath;
		this.timeout=timeout;
		this.testReporter=testReporter;
	
	}
	public ITestReporter getTestReporter() {
		return testReporter;
	}

	public void setTestReporter(ITestReporter testReporter) {
		this.testReporter = testReporter;
	}

	private static By locatorValue(String locatorTpye, String value) 
	{
		By by;
		switch (locatorTpye.toLowerCase()) 
		{
		case "id":
			by = By.id(value);
			break;
		case "name":
			by = By.name(value);
			break;
		case "classname":
			by = By.className(value);
			break;
		case "tagname":
			by = By.tagName(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;
		case "css":
			by = By.cssSelector(value);
			break;
		case "linktext":
			by = By.linkText(value);
			break;
		case "partiallinktext":
			by = By.partialLinkText(value);
			break;
	
		default:
			by = null;
			break;
		}
		return by;
	}
	
/**
*
* Method name enter_Text
*
* The methods used to enter text in text box and return void
*
* @param locaterType pass as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
* @param value pass element locater
* @param text pass text to be entered
* @param driver pass Webdriver Object
* @return void
 * @throws InterruptedException 
 * @throws CustomException 
* @since version 1.10
*/
	
	public static void enter_Text(String locatorType,String value, String text, WebDriver driver,String Element_Name) throws InterruptedException, CustomException {
//		System.out.println("enter_driver " + driver);
//		System.out.println("enter_text " + element);
		try 
		{
			By locator;
			locator = locatorValue(locatorType, value);
			element = driver.findElement(locator);
			element.clear();
			element.sendKeys(text);
			System.out.println("enter_text " + element);
			//testReporter.Log_Pass(text+" Exist",text+" Exist");
			testReporter.Log_Pass(Element_Name+" Entered",text + " entered in "+ Element_Name);
			
		} 
		catch (NoSuchElementException e) 
		{
			testReporter.Log_Fail(text+" does not Exist",text+" does not Exist", driver,text);
			System.err.format("No Element Found to enter text" + e);
			throw new CustomException("Element Not Present" ,e,ErrorCodes.ELEMENT_NOT_VISIBLE);
		}
//		return element;
	}
	

	public static void setCheckedByElementScript(String elementId,String text, WebDriver driver) 
	{

		JavascriptExecutor js = (JavascriptExecutor) driver;
		try 
		{
			System.out.println("Checked for " + elementId + " : " + (text.equalsIgnoreCase("YES")?"true":"false"));
			Thread.sleep(1000);
			String script=" document.getElementById('" + elementId + "').checked = " + (text.equalsIgnoreCase("YES")?"true":"false") +"; ";
			
			js.executeScript(script);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.format("No Element Found to setValueByElementScript" + e);
		}
	}
	
	
	
	
	
	public static void setValueByElementScript(String elementId,String text, WebDriver driver) 
	{

		JavascriptExecutor js = (JavascriptExecutor) driver;
		try 
		{
			Thread.sleep(1000);
			String script=" document.getElementById('" + elementId + "').value = \"" + text +"\"; ";
			js.executeScript(script);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.err.format("No Element Found to setValueByElementScript" + e);
		}
	}
	
	
	
	
	
	/**
	*
	* Method name clear_Text
	*
	* The methods used to clear text in text box and return void
	*
	* @param locaterType pass as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
	* @param value pass element locater
	* @param driver pass Webdriver Object
	* @return void
	* @since version 1.00
	*/
		
		public static void clear_Text(String locatorType,String value, WebDriver driver) {
//			System.out.println("enter_driver " + driver);
//			System.out.println("enter_text " + element);
			try 
			{
				By locator;
				locator = locatorValue(locatorType, value);
				element = driver.findElement(locator);
				element.clear();
				System.out.println("clear_text " + element+"\n");
			} 
			catch (NoSuchElementException e) 
			{
				System.err.format("No Element Found to clear text" + e+"\n");
			}
//			return element;
		}

	/**
	*
	* Method name click_On_Link
	*
	* The methods used to perform clicking action on link and return void
	*
	* @param locatorType pass type of locator as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
	* @param value pass element locator
	* @param driver pass Webdriver Object
	* @return void
	* @since version 1.00
	*/
	
	public static void click_On_Link(String locatorType, String value, WebDriver driver) {
		try 
		{
			By locator;
			locator = locatorValue(locatorType, value);
			element = driver.findElement(locator);
			element.click();
		} 
		catch (NoSuchElementException e) 
		{
			System.err.format("No Element Found to enter text" + e+"\n");		
		}

	}
	
	/**
	*
	* method name click_On_Link
	*
	* The methods used to perform clicking action on link and return void
	*
	* @param locatorType pass type of locator as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
	* @param value pass element locater
	* @param driver pass Webdriver Object
	* @return void
	 * @throws CustomException 
	* @since version 1.00
	*/

	public static void click_On_Button(String locatorType, String value,String Element, WebDriver driver)  throws InterruptedException, CustomException{
		
		Thread.sleep(1000);
		try 
		{
		
				By locator;
				locator = locatorValue(locatorType, value);
				WebElement element = driver.findElement(locator);
				element.click();
				testReporter.Log_Pass("click_On_Button",Element);
				
			
			
		} 
		catch (Exception e) 
		{
			testReporter.Log_Fail("click_On_Button","Failed..Element not found or internal error: " + Element,driver,"");
			System.err.format("No Element Found to perform click" + e+"\n");
			//throw new CustomException("No Element Found to perform click" ,e,ErrorCodes.ELEMENT_NOT_VISIBLE);
		}
	}
	
	
	
	public static void button_dropdown(String xPathClickString, String xPathString, String valueToMatch,String Element, WebDriver driver) throws Exception
	{
		ActionUtils.click_On_Button("xpath", xPathClickString, Element,driver);
//		ActionUtils.click_On_Button("xpath", "//span[contains(.,'" + valueToMatch + "')]", driver);
		
		try 
		{
			for (WebElement ele : driver.findElements(By.xpath(xPathString)))
			{
		          System.out.println("button_dropdown Values " + ele.getAttribute("innerHTML"));
		          //if (ele.getAttribute("innerHTML").contains(valueToMatch)) 
		          if (valueToMatch.equals(ele.getText().trim()))
		          {
		        	  System.out.println(ele.getText());
		        	  System.out.println("CLicked on " + valueToMatch);
		              ele.click();
		             break;
		          }
		    }
			
		} 
		catch (NoSuchElementException e) 
		{
			System.err.format("No Element Found to perform button_dropdown" + e+"\n");
		}
	}
	
	
	/**
	*
	* method name VerifyWebElement
	*
	* The methods used for verification of given WebElement and return Boolean
	*
	* @param locatorType pass type of locator as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
	* @param value pass element locater
	* @param condition pass type of condition (Displayed, Enable, Selected)
	* @param driver pass Webdriver Object
	* @return Boolean
	* @since version 1.1
	*/

	public static Boolean VerifyWebElement(String locatorType, String value, String condition, WebDriver driver) 
	{
		Boolean flag = null;
		try 
		{
			By locator;
			
			locator = locatorValue(locatorType, value);
			WebElement element = driver.findElement(locator);
			 if (condition.equalsIgnoreCase("displayed")) //isElementClickable
			    {
			    	 flag = element.isDisplayed();
				}
			    else if (condition.equalsIgnoreCase("enable")) //isElementPresent
			    {
			    	flag = element.isEnabled();
				}
			    else if (condition.equalsIgnoreCase("selected")) //isElementVisible
			    {
			    	 flag = element.isSelected();
				}
		} 
		catch (NoSuchElementException e) 
		{
			System.err.format("No Element Found to perform click" + e+"\n");
		}
		return flag;
	}
	
	/**
	*
	* Method name waitForElement
	*
	* The methods used to wait for given element and return void
	*
	* @param locaterType pass as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
	* @param locatorValue pass element locator
	* @param condition pass as (Clickable, Present, Visible, VisibleElement, ToBeSelected, frameToSwitch, AllVisible, InVisible)
	* @param driver pass Webdriver Object
	* @return void
	* @since version 1.00
	*/
	
	public static void waitForElement(String locatorType,String locatorValue, String condition, int sec)
	 {
		try 
		{
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			WebElement element = driver.findElement(locator);
			
			WebDriverWait wait = new WebDriverWait(driver, sec);
		    
		    if (condition.equalsIgnoreCase("clickable")) //isElementClickable
		    {
		    	 wait.until(ExpectedConditions.elementToBeClickable(element));
			}
		    else if (condition.equalsIgnoreCase("present")) //isElementPresent
		    {
		    	 wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			}
		    else if (condition.equalsIgnoreCase("visible")) //isElementVisible
		    {
		    	 wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			}
		    else if (condition.equalsIgnoreCase("visibleElement")) //isVisible
		    {
		    	 wait.until(ExpectedConditions.visibilityOf(element));
			}
		    else if (condition.equalsIgnoreCase("tobeselected")) //elementToBeSelected
		    {
		    	 wait.until(ExpectedConditions.elementToBeSelected(locator));
			}
		    else if (condition.equalsIgnoreCase("frametoswitch")) //elementToBeSelected
		    {
		    	 wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
			}
		    else if (condition.equalsIgnoreCase("allvisible")) //isAllVisible
		    {
		    	List<WebElement> linkElements = driver.findElements(locator);
		    	wait.until(ExpectedConditions.visibilityOfAllElements(linkElements));
			}
		    else if (condition.equalsIgnoreCase("invisible")) //isElementInVisible
		    {
		    	 wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			}
		    
		}
		catch (Exception e) 
		{
			
			System.err.format("Class Utils | Method Name waitForElement | Exception occured while performing waitForElement : "+e+"\n");
		}
	 }
	


	/**
	*
	* Method name selectDropDownAction
	*
	* The methods used to select dropdown action and return void
	*
	* @param locaterType pass as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
	* @param locatorValue pass element locator
	* @param dropdownType pass as (value, text)
	* @param dropdownValue pass as a value or Visible Text
	* @param driver pass Webdriver Object
	* @return void
	* @since version 1.00
	*/

	public static void selectDropDownAction (String locatorType,String locatorValue ,String dropdownType , String dropdownValue, WebDriver driver) throws Exception
	{ 
		try
		{
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			WebElement element = driver.findElement(locator);
			
			Select dropdown = new Select(element);
			System.out.println("SelectDropDown" +"===>"+dropdown);
			
			switch (dropdownType.toLowerCase()) 
			{
//			case "index":
//				Integer Int = Integer.valueOf(dropdownValue);
//				dropdown.selectByIndex(Int);
//				dropdown.selectByIndex(Integer.parseInt(frameElement));
//				break;
			case "value":
				dropdown.selectByValue(dropdownValue);
				break;
			case "text":
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				dropdown.selectByVisibleText(dropdownValue);
				break;
			default:				
				break;
				
			}
			testReporter.Log_Pass(dropdownValue+" Exist",dropdownValue +" Exist");
			return;  
			
		}
		
		catch (NoSuchElementException e) 
		{
			System.err.format("No Element Found to perform dropdown" + e+"\n");
			throw new CustomException("Element Not Present" ,e,ErrorCodes.ELEMENT_NOT_VISIBLE);
		}
	}
	
	/**
	*
	* Method name deSelectDropDownAction
	*
	* The methods used to Deselect dropdown action and return void
	*
	* @param locaterType pass as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
	* @param locatorValue pass element locator
	* @param dropdownType pass as (value, text)
	* @param dropdownValue pass as a value or Visible Text
	* @param driver pass Webdriver Object
	* @return void
	* @since version 1.00
	*/
	
	public static void deSelectDropDownAction (String locatorType,String locatorValue ,String dropdownType , String dropdownValue, WebDriver driver) throws Exception
	{ 
		try
		{
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			WebElement element = driver.findElement(locator);
			Select dropdown = new Select (element);   
			switch (dropdownType) 
			{
			/*case "index":
				dropdown.deselectByIndex(Integer.parseInt(dropdownValue));
				break;*/
			case "value":
				dropdown.deselectByValue(dropdownValue);
				break;
			case "text":
				dropdown.deselectByVisibleText(dropdownValue);
				break;
			default:				
				break;
			}
			return;
	        
		}
		catch (NoSuchElementException e) 
		{
			System.err.format("No Element Found to perform Deselect dropdown" + e+"\n");
		}
	}
	
	/**
	*
	* Method name javaScriptExecutor
	*
	* The method javaScriptExecutor is used to perform clicking action and return void
	*
	* @param locaterType pass as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
	* @param locatorValue pass element locator
	* @param driver pass Webdriver Object
	* @return void
	* @since version 1.00
	*/
	
	public static void javaScriptExecutor(String locatorType,String locatorValue,WebDriver driver)
	{
		try 
		{
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			WebElement element = driver.findElement(locator);
			
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
			System.out.println("Javascript Executer performed");
		} 
		catch (NoSuchElementException e) 
		{
			System.err.format("No Element Found to perform JavaScript Executer" +"===>"+ e+"\n");
		}
		catch (Exception e) 
		{
			System.err.format("Javascript Executer issue: check value passed");
			System.err.format("Javascript Executer Element with locator is not attached to the page document" + e+"\n");	
		}
	
	 }
	
	/**
	*
	* Method name getElementText
	*
	* The method getElementText is used to get text stored on given element and return String
	*
	* @param locaterType pass as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
	* @param locatorValue pass element locator
	* @param driver pass Webdriver Object
	* @return String
	 * @throws CustomException 
	* @since version 1.00
	*/
	
	public static String getElementText(String locatorType,String locatorValue,WebDriver driver) throws CustomException
	{
		String text= null;
		try 
		{
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			WebElement element = driver.findElement(locator);
			
				 text = element.getText();
		         System.out.println("getText is performed from element");
		} 
		catch (NoSuchElementException e) 
		{
			System.err.format("No Element Found to perform getText" + e+"\n");
			throw new CustomException("Element Not Present" ,e,ErrorCodes.ELEMENT_NOT_VISIBLE);
		}
		return text;
		
	 }
	
	/**
	*
	* Method name mouseHoverAction.
	*
	* The method mouseHoverAction is used to perform mouseHover action on given element and return void
	*
	* @param locaterType pass as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
	* @param locatorValue pass element locator
	* @param driver pass Webdriver Object
	* @return void
	* @since version 1.00
	*/
	
	public static void mouseHoverAction(String locatorType,String locatorValue,WebDriver driver)
	{
		try 
		{
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			WebElement element = driver.findElement(locator);
			
				 Actions action = new Actions(driver);
		         action.moveToElement(element).perform();
		         action.click(element);
		         action.build();
		         action.perform();
		         System.out.println("Click action is performed on the selected Product Type");
		} 
		catch (NoSuchElementException e) 
		{
			System.err.format("No Element Found to perform mouseHoverAction" + e+"\n");
		}
	 }
	
	/**
	*
	* Method name frameSwitch.
	* 
	* NOTE BY:- This method is only specific to INGENIUM Application
	*  
	* The method frameSwitch is used to perform switching action in between given frames and return void
	* 
	* @param Frame  LF0 - Login Page Frame, LF1 - After Login Page, MSF - menuserverFrame, PSF - pageserverFrame, TF - TitleFrame, 
	* MF - MessageFrame, CF - ContentFrame, WF - WaitFrame, BF - ButtonFrame
	*
	* @param frameName pass as (LF0, LF1, MSF, PSF, TF, MF, CF, WF, BF) 
	* @param driver pass Webdriver Object
	* @return void
	* @since version 1.00
	*/
	
	public static void frameSwitch(String frameName,WebDriver driver) throws Exception 
	{
		try 
		{
			if (frameName.equalsIgnoreCase("MSF")) 
			{
				driver.switchTo().defaultContent();
				driver.switchTo().frame("menuserverFrame");
				System.out.println("Got Switched into"+"==>"+frameName);
			}else if (frameName.equalsIgnoreCase("PSF")) 
			{
				driver.switchTo().defaultContent();
				driver.switchTo().frame("pageserverFrame");
				System.out.println("Got Switched into"+"==>"+frameName);
			}else if (frameName.equalsIgnoreCase("TF")) 
			{
				driver.switchTo().defaultContent();
				driver.switchTo().frame("pageserverFrame");
				driver.switchTo().frame("TitleFrame");
				System.out.println("Got Switched into"+"==>"+frameName);
			}else if (frameName.equalsIgnoreCase("MF")) 
			{
				driver.switchTo().defaultContent();
				driver.switchTo().frame("pageserverFrame");
				driver.switchTo().frame("MessageFrame");
				System.out.println("Got Switched into"+"==>"+frameName);
			}
			else if (frameName.equalsIgnoreCase("CF")) 
			{
				driver.switchTo().defaultContent();
				driver.switchTo().frame("pageserverFrame");
				driver.switchTo().frame("ContentFrame");
				System.out.println("Got Switched into"+"==>"+frameName);
			}else if (frameName.equalsIgnoreCase("WF")) 
			{
				driver.switchTo().defaultContent();
				driver.switchTo().frame("pageserverFrame");
				driver.switchTo().frame("WaitFrame");
				System.out.println("Got Switched into"+"==>"+frameName);
			}else if (frameName.equalsIgnoreCase("BF")) 
			{
				driver.switchTo().defaultContent();
				driver.switchTo().frame("pageserverFrame");
				driver.switchTo().frame("ButtonFrame");
				System.out.println("Got Switched into"+"==>"+frameName);
			}else if (frameName.equalsIgnoreCase("LF0")) 
			{
				driver.switchTo().defaultContent();
				driver.switchTo().frame("ContentFrame");
				System.out.println("Got Switched into"+"==>"+frameName);
			}else if (frameName.equalsIgnoreCase("LF1")) 
			{
				driver.switchTo().defaultContent();
				driver.switchTo().frame("ButtonFrame");
				System.out.println("Got Switched into"+"==>"+frameName);
			}
		}
		catch (ElementNotVisibleException e) 
		{
			System.err.format("Exception Case Into frameSwitch method "+ e+"\n");
		}
	}
	
	/**
	*
	* Method name switchToFrame
	*
	* The method switchToFrame is used to perform switching action in between given frames and return void
	*
	* @param locatorType pass as (id, name, classname, tagname, xpath, css, linktext, partiallinktext) 
	* @param frameElement pass frame element locator
	* @param frameType pass frame type (parent, default, index, string)
	* @param driver pass Webdriver Object
	* @return void
	* @since version 1.00
	*/
	
	public static void switchToFrame(String locatorType, String frameElement,String frameType, WebDriver driver) throws Exception
	{
		try 
		{
			List<WebElement> ele = driver.findElements(By.tagName("frame"));
            System.out.println("Number of frames in a page :"+"==>"+ ele.size()+"\n");
            
            switch (frameType.toLowerCase()) 
			{
			case "parent":
				driver.switchTo().parentFrame();
				System.out.println("Parent");
				break;
			case "default":
				driver.switchTo().defaultContent();
				System.out.println("DefaultContent");
				break;
			case "index":
				driver.switchTo().frame(Integer.parseInt(frameElement));//enter all values form 0-size
				System.out.println("Index");
				break;
			case "string":
				driver.switchTo().frame(frameElement);
				System.out.println("String");
				break;
		/*	case "webelement":
				if ((locatorType.toLowerCase()).contains("xpath")) {
					element = driver.findElement(By.xpath(frameElement));
					driver.switchTo().frame(element);
				}else if ((locatorType.toLowerCase()).contains("name")){
					element = driver.findElement(By.xpath(frameElement));
					driver.switchTo().frame(element);	
				}else if ((locatorType.toLowerCase()).contains("id")){
					element = driver.findElement(By.id(frameElement));
					driver.switchTo().frame(element);	
				}
				break; */
			default:
				frameType = null;
				break;
			}
		} 
		catch (NoSuchFrameException e) 
		{
			System.err.format("Unable to locate frame with element " + frameElement + e+"\n");
		} 
		catch (StaleElementReferenceException e) 
		{		
			System.err.format("Element with " + frameElement + "is not attached to the page document" + e+"\n");
		} 
		catch (ElementNotVisibleException e) 
		{
			System.err.format("Unable to navigate to frame with element " + frameElement + e+"\n");
		}	
	}
		
	
	}
