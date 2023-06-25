package com.ibm.automation.ui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ibm.utility.Common_Functions;
import com.ibm.utility.reports.interfaces.ITestReporter;







public class WrapperMethods {

	private String datasheetPath;
	private String sheetName;
	private String snapshotsPath;
	private String reportPath;
	private String timeout;
	
	
	private ITestReporter testReporter;
	
	public WrapperMethods()
	{
	}
	
	public WrapperMethods(String datasheetPath,String sheetName,String snapshotsPath,String reportPath,String timeout)
	{
		this.datasheetPath=datasheetPath;
		this.sheetName=sheetName;
		this.snapshotsPath=snapshotsPath;
		this.reportPath=reportPath;
		this.timeout=timeout;
	
	}
	
	public WrapperMethods(String datasheetPath,String sheetName,String snapshotsPath,String reportPath,String timeout,ITestReporter testReporter)
	{
		this.datasheetPath=datasheetPath;
		this.sheetName=sheetName;
		this.snapshotsPath=snapshotsPath;
		this.reportPath=reportPath;
		this.timeout=timeout;
		this.testReporter=testReporter;
	
	}


	
	
	
	
	


	public boolean isElementPresentByXpath(String xpath,WebDriver driver,String Element_Name) throws InterruptedException
	{
		try
		{
			driver.findElement(By.xpath(xpath));
			testReporter.Log_Pass(Element_Name+" Exist",Element_Name+" Exist");
		}
		catch(Throwable t)
		{
			//Log.error("Element not Found -->"+t.getMessage());
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean isElementPresentByLinkText(String linkText,WebDriver driver) throws InterruptedException
	{
		try
		{
			driver.findElement(By.linkText(linkText));
			testReporter.Log_Pass(linkText+" Exist",linkText+" Exist");
		}
		catch(Throwable t)
		{


			testReporter.Log_Fail(linkText+" does not Exist",linkText+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean isElementPresentByID(String ID,WebDriver driver,String Element_Name) throws InterruptedException
	{
		try
		{
			driver.findElement(By.id(ID));
			testReporter.Log_Pass(Element_Name+" Exist",Element_Name+" Exist");
		}
		catch(Throwable t)
		{


			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean isElementPresentByName(String Name,WebDriver driver,String Element_Name) throws InterruptedException
	{
		try
		{
			driver.findElement(By.name(Name));
			testReporter.Log_Pass(Element_Name+" Exist",Element_Name+" Exist");
		}
		catch(Throwable t)
		{


			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean isElementPresentByClassName(String className,WebDriver driver,String Element_Name) throws InterruptedException
	{
		try
		{
			driver.findElement(By.className(className));
			testReporter.Log_Pass(Element_Name+" Exist",Element_Name+" Exist");
		}
		catch(Throwable t)
		{


			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			return false;
		}
		return true;
	}


	public void clickButtonID(WebDriver screenName,String ID,String Element_Name) throws Exception
	{
		try
		{


			WebElement webButton = screenName.findElement(By.id(ID));
			webButton.click();
			testReporter.Log_Pass(Element_Name+" Clicked",Element_Name+" Clicked");
		}
		catch(Throwable t)
		{
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}
	public void clickButton(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			WebElement webButton = screenName.findElement(By.xpath(ObjectxPath));
			webButton.click();
			testReporter.Log_Pass(Element_Name+" Clicked",Element_Name+" Clicked");
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}
	public void clickLink(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			WebElement webButton = screenName.findElement(By.xpath(ObjectxPath));
			webButton.click();
			testReporter.Log_Pass(Element_Name+" Clicked",Element_Name+" Clicked");
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}


	public void clickLinkByLinkText(WebDriver screenName,String linkText,String Element_Name) throws Exception
	{
		try
		{
			WebElement webButton = screenName.findElement(By.linkText(linkText));
			webButton.click();
			testReporter.Log_Pass(Element_Name+" Clicked",Element_Name+" Clicked");
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}

	public boolean clickFirst(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		boolean flag=true;
		try
		{
			WebElement webButton = screenName.findElement(By.xpath(ObjectxPath));
			webButton.click();
			testReporter.Log_Pass(Element_Name+" Clicked",Element_Name+" Clicked");
		}
		catch(Throwable t)

		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");

		}
		return flag;
	}
	public void inputText(WebDriver screenName,String ObjectxPath,String sValue,String Element_Name) throws Exception
	{
		try
		{
			WebElement inputText = screenName.findElement(By.xpath(ObjectxPath));
			inputText.clear();
			inputText.sendKeys(sValue);
			testReporter.Log_Pass(Element_Name+" Entered",sValue + " entered in "+ Element_Name);
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			//throw new Exception("Element Not Present");
			throw new CustomException("Element Not Present" ,t,ErrorCodes.ELEMENT_NOT_VISIBLE);
		}


	}


	public void removeAttribute(WebDriver driver) {
		List<WebElement> inputs = driver.findElements(By.tagName("input"));
		for (WebElement input : inputs) {
			((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly','readonly')", input);
		}
	}

	public void typeNonEditable(WebDriver driver,String ObjectxPath,String sValue,String Element_Name) throws Exception {
		removeAttribute(driver);
		inputText(driver,ObjectxPath,sValue,Element_Name);
	}


	public void enterText(WebDriver screenName, String ObjectxPath, String sValue,String Element_Name) throws Exception {
		Actions actions = new Actions(screenName);
		try
		{
			WebElement inputText = screenName.findElement(By.xpath(ObjectxPath));
			actions.moveToElement(inputText);
			actions.click();
			actions.sendKeys(sValue);
			actions.build().perform();
			testReporter.Log_Pass(Element_Name+" Entered",sValue + " entered in "+ Element_Name);
		}  catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}


	}
	public void inputTextByID(WebDriver screenName,String ID,String sValue,String Element_Name) throws Exception
	{
		try
		{	WebElement inputText = screenName.findElement(By.id(ID));


		inputText.sendKeys(sValue);
		testReporter.Log_Pass(Element_Name+" Entered",sValue + " entered in "+ Element_Name);
		}  catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}


	}

	public void selectCheckBox(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{	WebElement checkBox = screenName.findElement(By.xpath(ObjectxPath));
		checkBox.click();
		testReporter.Log_Pass(Element_Name+" checkbox clicked",Element_Name+" checkbox clicked");
		}  catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}
	public void selectRadio(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			WebElement radioButton = screenName.findElement(By.xpath(ObjectxPath));
			radioButton.click();
			testReporter.Log_Pass(Element_Name+" radiobutton clicked",Element_Name+" radiobutton clicked");
		}  catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}

	public String getInputTextValue(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			WebElement inputText = screenName.findElement(By.xpath(ObjectxPath));
			testReporter.Log_Pass(Element_Name+" exist",Element_Name+" has "+ inputText.getText());
			return inputText.getText();
		}
		catch(Throwable t)

		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");

		}
	}


	public String getInputValue(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			WebElement inputText = screenName.findElement(By.xpath(ObjectxPath));
			testReporter.Log_Pass(Element_Name+" exist",Element_Name+" has "+ inputText.getText());
			return inputText.getAttribute("value");
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}


	public String getAttributeValue(WebDriver screenName,String ObjectxPath,String attributeName,String Element_Name) throws Exception
	{
		try
		{
			WebElement inputText = screenName.findElement(By.xpath(ObjectxPath));
			testReporter.Log_Pass(Element_Name+" exist",Element_Name+" has "+ inputText.getText());
			return inputText.getAttribute("attributeName");
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}

	public void clearInputTextValue(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			WebElement inputText = screenName.findElement(By.xpath(ObjectxPath));
			inputText.clear();
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}
	public void clearInputTextValueByName(WebDriver screenName,String name,String Element_Name) throws Exception
	{
		try
		{
			WebElement inputText = screenName.findElement(By.name(name));
			inputText.clear();
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}
	public void clearAndInputTextValue(WebDriver screenName,String ObjectxPath,String value,String Element_Name) throws Exception
	{


		try
		{
			WebElement inputText = screenName.findElement(By.xpath(ObjectxPath));
			Thread.sleep(1000);
			inputText.clear();
			Thread.sleep(1000);
			inputText.sendKeys(value);
			testReporter.Log_Pass(Element_Name+" cleared & Entered with "+value,Element_Name+" cleared & Entered with "+value);
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}
	public void selectDropBoxValue(WebDriver screenName,String ObjectxPath,String value,String Element_Name) throws Exception
	{
		try
		{
			WebElement selectDropBox = screenName.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			select.selectByValue(value);
			testReporter.Log_Pass(Element_Name+" selected with "+value,Element_Name+" selected with "+value);
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}

	}


	public void selectDropBoxByVisibleText(WebDriver screenName,String ObjectxPath,String value,String Element_Name) throws Exception
	{
		try
		{
			WebElement selectDropBox = screenName.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			select.selectByVisibleText(value);
			testReporter.Log_Pass(Element_Name+" selected with "+value,Element_Name+" selected with "+value);
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}


	public void selectDropBoxValueByID(WebDriver screenName,String ID,String value,String Element_Name) throws Exception
	{
		try
		{
			WebElement selectDropBox = screenName.findElement(By.id(ID));
			Select select = new Select(selectDropBox);
			select.selectByValue(value);
			testReporter.Log_Pass(Element_Name+" selected with "+value,Element_Name+" selected with "+value);
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}

	}
	public void selectDropBoxValueByName(WebDriver screenName,String Name,String value,String Element_Name) throws Exception
	{
		try
		{
			WebElement selectDropBox = screenName.findElement(By.name(Name));
			Select select = new Select(selectDropBox);
			select.selectByValue(value);
			testReporter.Log_Pass(Element_Name+" selected with "+value,Element_Name+" selected with "+value);
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}

	}
	public void selectDropBoxValue(WebDriver screenName,String ObjectxPath,int index,String Element_Name) throws Exception
	{
		try
		{
			WebElement selectDropBox = screenName.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			select.selectByIndex(index);
			testReporter.Log_Pass(Element_Name+" selected with "+index,Element_Name+" selected with "+index);
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}

	}
	public void selectDropBoxDefaultValue(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			WebElement selectDropBox = screenName.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			select.selectByIndex(0);
			testReporter.Log_Pass(Element_Name+" selected with default value ",Element_Name+" selected with default value ");
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}

	}
	public String getDropBoxDefaultValue(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			WebElement selectDropBox = screenName.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			testReporter.Log_Pass(Element_Name+" selected value is "+select.getFirstSelectedOption().getText(),Element_Name+" selected value is "+select.getFirstSelectedOption().getText());
			return select.getFirstSelectedOption().getText();


		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}
	public String getDropBoxSelectedValue(WebDriver screenName,String ObjectxPath,int index,String Element_Name) throws Exception
	{
		try
		{
			WebElement selectDropBox = screenName.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			testReporter.Log_Pass(Element_Name+" selected value is "+select.getFirstSelectedOption().getText(),Element_Name+" selected value is "+select.getFirstSelectedOption().getText());
			return select.getOptions().get(index).getText();
		}


		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}

	public int getDropBoxSize(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			WebElement selectDropBox = screenName.findElement(By.xpath(ObjectxPath));
			Select select = new Select(selectDropBox);
			testReporter.Log_Pass(Element_Name+" dropbox size is "+select.getOptions().size(),Element_Name+" dropbox size is "+select.getOptions().size());
			return select.getOptions().size();
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}



	public String[] getDropBoxValue(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			WebElement selectDropBox = screenName.findElement(By.xpath(ObjectxPath));
			Select select =new Select(selectDropBox);
			List<WebElement> optionValue = select.getOptions();
			String[] value = new String[optionValue.size()];
			for(int i =0;i<optionValue.size();i++)
				value[i] = optionValue.get(i).getText();
			return value;
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}

	}

	public int getTotalTableCell(WebDriver driver,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			testReporter.Log_Pass(Element_Name+" table size is "+driver.findElements(By.xpath(ObjectxPath)).size(),Element_Name+" table size is "+driver.findElements(By.xpath(ObjectxPath)).size());
			return driver.findElements(By.xpath(ObjectxPath)).size();
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}

	public int getElementsSize(WebDriver driver,String ObjectxPath,String Element_Name) throws Exception
	{
		try
		{
			testReporter.Log_Pass(Element_Name+" element size is "+driver.findElements(By.xpath(ObjectxPath)).size(),Element_Name+" element size is "+driver.findElements(By.xpath(ObjectxPath)).size());
			return driver.findElements(By.xpath(ObjectxPath)).size();
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}


	public int getElementCount(WebDriver driver,String className,String Element_Name) throws Exception
	{
		int count=0;

		try
		{
			count = driver.findElements(By.className(className)).size();
			testReporter.Log_Pass(Element_Name+" element count "+count,Element_Name+" element size is "+count);
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
		return count;
	}

	public boolean isElementDisplayed(WebDriver driver,String xpath,String Element_Name,int t)
	{
		boolean flag=false;
		try
		{
			driver.manage().timeouts().implicitlyWait(t, TimeUnit.SECONDS);
			if(driver.findElement(By.xpath(xpath)).isDisplayed())
			{	testReporter.Log_Pass(Element_Name +" is displayed ", Element_Name +" is displayed ");
			flag = true;
			}
		}
		catch(Throwable e)
		{
			testReporter.Log_Pass(Element_Name +" is not displayed ", Element_Name +" is not displayed ");
			flag = false;
		}
		return flag;

	}
	public boolean isElementDisplayed(WebDriver driver,String xpath,String Element_Name)
	{
		boolean flag=false;
		try
		{
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			if(driver.findElement(By.xpath(xpath)).isDisplayed())
			{	testReporter.Log_Pass(Element_Name +" is displayed ", Element_Name +" is displayed ");
			flag = true;
			}
		}
		catch(Throwable e)
		{
			testReporter.Log_Pass(Element_Name +" is not displayed ", Element_Name +" is not displayed ");
			flag = false;
		}
		return flag;

	}

	public boolean isElementDisplay(WebDriver driver,String xpath)
	{
		boolean flag=false;
		try
		{
			if(driver.findElement(By.xpath(xpath)).isDisplayed())
			{	
				flag = true;
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			flag = false;
		}
		return flag;

	}


	//  Rohit - ETS -- 11 June 2015
	public void mouseHoverAction_3(WebDriver driver,String mainElementXP, String subElementXP,String subSubElementXP,String Element_Name) throws Exception{


		try
		{
			Actions action = new Actions(driver);
			WebElement mainElement = driver.findElement(By.xpath(mainElementXP));
			action.moveToElement(mainElement).perform();
			WebElement subElement = driver.findElement(By.xpath(subElementXP));
			action.moveToElement(subElement).perform();
			WebElement subSubElement = driver.findElement(By.xpath(subSubElementXP));
			action.moveToElement(subSubElement);
			action.click();
			action.perform();


			testReporter.Log_Pass("Click action is performed on the selected Product Type"+Element_Name,"Click action is performed on the selected Product Type"+Element_Name);

		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}
	public void mouseHoverAction_2(WebDriver driver,String mainElementXP, String subElementXP,String Element_Name) throws Exception{


		try
		{
			Actions action = new Actions(driver);
			WebElement mainElement = driver.findElement(By.xpath(mainElementXP));
			action.moveToElement(mainElement).perform();
			WebElement subElement = driver.findElement(By.xpath(subElementXP));
			action.moveToElement(subElement);
			action.click();
			action.perform();


			testReporter.Log_Pass("Click action is performed on the selected Product Type"+Element_Name,"Click action is performed on the selected Product Type"+Element_Name);

		}
		catch(Throwable t)

		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}
	public void mouseHoverAction(WebDriver driver,String mainElementXP,String Element_Name) throws Exception{


		try
		{
			Actions action = new Actions(driver);
			WebElement mainElement = driver.findElement(By.xpath(mainElementXP));
			action.moveToElement(mainElement).clickAndHold().build().perform();
			action.release().perform();
			//action.perform();


			testReporter.Log_Pass("Click action is performed on the selected Product Type"+Element_Name,"Click action is performed on the selected Product Type"+Element_Name);

		}
		catch(Throwable t)

		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}


	public void mouseHoverAction(WebDriver driver,String mainElementXP, String subElementXP,String subSubElementXP, String subBesideElementXP,String Element_Name) throws Exception{
		try

		{
			Actions action = new Actions(driver);
			WebElement mainElement = driver.findElement(By.xpath(mainElementXP));
			action.moveToElement(mainElement).perform();
			WebElement subElement = driver.findElement(By.xpath(subElementXP));
			action.moveToElement(subElement).perform();
			WebElement subSubElement = driver.findElement(By.xpath(subSubElementXP));
			action.moveToElement(subSubElement).perform();
			WebElement subBesideElement = driver.findElement(By.xpath(subBesideElementXP));
			action.moveToElement(subBesideElement).perform();
			action.click();
			action.perform();


			testReporter.Log_Pass("Click action is performed on the selected Product Type"+Element_Name,"Click action is performed on the selected Product Type"+Element_Name);

		}
		catch(Throwable t)

		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}


	public void selectclass(WebDriver driver,WebElement parent,String elementToSelect,String Element_Name) throws Exception{


		try
		{
			Select dropdown = new Select(parent);
			dropdown.selectByVisibleText(elementToSelect);
			testReporter.Log_Pass("selected "+elementToSelect,"selected "+elementToSelect+"in "+parent);

		}
		catch(Throwable t)

		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", driver,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");


		}


	}




	public void getWindowHandle(WebDriver driver , String title) {
		Set<String> handles = driver.getWindowHandles();

		String[] browser =	handles.toArray(new String[0]);
		System.out.println("Number of browsers opened are"
				+ browser.length);
		for (int i=0; i<handles.size();i++) {

			driver.switchTo().window(browser[i]);
			if(driver.getTitle().equals(title)){
				driver.getWindowHandle();

			}

		}

	} 


	public void selectWindowIfElementPresent(WebDriver driver, String element) throws InterruptedException {
		Thread.sleep(2000);
		Set<String> windows = driver.getWindowHandles();
		Object[] win = windows.toArray(); System.out.println(win.length);
		driver.switchTo().window(win[0].toString());
		if (isElementDisplay(driver, element)) {
			testReporter.Log_Pass("Selected Pop Up : " + driver.switchTo().window(win[0].toString()).getTitle(),"Selected Pop Up : " + driver.switchTo().window(win[0].toString()).getTitle());
		} else {
			driver.switchTo().window(win[1].toString());
			System.out.println("Selected Pop Up : " + driver.switchTo().window(win[1].toString()).getTitle());
		}
	}


	public void waitForElementNotPresent(WebDriver driver, String element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element)));
		} catch (TimeoutException te) {
		}
	}


	public void waitForElementVisible(WebDriver driver, String element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
		} catch (NoSuchWindowException e) {
		} catch (InvalidElementStateException e) {
		} catch (TimeoutException te) {
		} catch (NoSuchElementException e) {
		} catch (WebDriverException we) {
		}
	}


	public void waitForElementClickable(WebDriver driver, String element,String text) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 80);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
		} catch (NoSuchWindowException e) {
		} catch (InvalidElementStateException e) {
		} catch (TimeoutException te) {
		} catch (NoSuchElementException e) {
		} catch (WebDriverException we) {
		}
	}


	public void waitForTextPresent(WebDriver driver, String text) throws InterruptedException {
		try {
			Thread.sleep(2000);
			long timer = 0;
			while (isTextPresent(driver, text) == false && timer < Long.valueOf("30000")) {
				Thread.sleep(500);
				timer += 5000;
			}
		} catch (NoSuchWindowException e) {
		}
	}


	public void waitForPageToLoad(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(timeout),TimeUnit.SECONDS);
	}



	public boolean isTextPresent(WebDriver driver, String text) {
		boolean flag = false;
		try {
			flag = driver.findElement(By.xpath("//*[contains(.,'" + text + "')]")).isDisplayed();
		} catch (NoSuchElementException e) {
			return flag;
		} catch (NoSuchWindowException e) {
			return flag;
		}
		return flag;
	}


	public void getTableData(WebDriver driver , String xpath)
	{
		// Grab the table 
		WebElement table = driver.findElement(By.xpath(xpath)); 

		// Now get all the TR elements from the table 
		List<WebElement> allRows = table.findElements(By.tagName("tr")); 

		// And iterate over them, getting the cells 
		for (WebElement row : allRows) { 
			List<WebElement> cells = row.findElements(By.tagName("td")); 

			// Print the contents of each cell
			for (WebElement cell : cells) { 
				System.out.println(cell.getText());

			}
		}
	}




	//Function for fetching the value from the object when value attribute is not present.
	public String getObjectValue(WebDriver driver,String xpath) {

		WebElement webElement=driver.findElement(By.xpath(xpath));
		JavascriptExecutor e = (JavascriptExecutor) driver;
		return (String) e.executeScript(String.format("return $('#%s').val();", webElement.getAttribute("id")));
	}



	public String getObjectValueClass(WebDriver driver, String xpathValue) {
		WebElement webElement=driver.findElement(By.xpath(xpathValue));
		JavascriptExecutor e = (JavascriptExecutor) driver;
		return (String) e.executeScript(String.format("return $('#%s').val();", webElement.getAttribute("class")));
	}


	public void doubleClick(WebDriver driver,String string) throws InterruptedException
	{
		try{
		
		Actions action = new Actions(driver);
		WebElement webElement=driver.findElement(By.xpath(string));
		action.moveToElement(webElement);
		Thread.sleep(2000);
		action.doubleClick();
		//action.doubleClick(myElemment);
		action.build().perform();
		}catch (Exception e) {
			
		}
		}
		
	
	 
	public int getElementCountXPath(WebDriver driver, String ObjectPath,String Element_Name) throws InterruptedException {
		int count = 0;
		if (isElementPresentByXpath(ObjectPath, driver,Element_Name)) {
			count = driver.findElements(By.xpath(ObjectPath)).size();
		}
		return count;
	}


	public void acceptAlert(WebDriver driver) throws InterruptedException {
		try {
			Alert alert = waitforAlertPresent(driver);
			if (!alert.equals(null))
				alert.accept();
		} catch (NoAlertPresentException ex) {
		}
	}


	public Alert waitforAlertPresent(WebDriver driver) throws InterruptedException {
		int i = 0;
		Alert alert = null;
		while (i++ < 30) {
			try {
				alert = driver.switchTo().alert();
				return alert;
			} catch (NoAlertPresentException e) {
				Thread.sleep(1000);
				continue;
			}
		}
		return alert;
	}
	public void waitForPopUp(WebDriver driver, String b) throws InterruptedException {
		Thread.sleep(3000);
		try {
			selectPopUp(driver, b);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (NoSuchWindowException e1) {
		} catch (Exception e) {
		}
	}


	public void selectPopUp(WebDriver driver, String arg) {
		boolean flag = false;
		try {
			for (int i = 0; i < 2 && flag == false; i++) {
				Set<String> pops = driver.getWindowHandles();









				Iterator<String> it = pops.iterator();
				if (pops.size() > 1) {
					System.out.println("No of Windows " + pops.size());
					for (int j = 0; j < pops.size() && flag == false; j++) {
						String popupHandle = it.next().toString();
						if (driver.switchTo().window(popupHandle).getTitle().contains(arg)) {
							driver.switchTo().window(popupHandle);
							flag = true;
						}
					}flag = true;


					pops.clear();
				}
			}
		} catch (NoSuchWindowException e) {
			System.out.println("Not able to Navigate to Window " + arg);
		} catch (Exception e) {
		}
	}


	public void checkUsingJavaScript(WebDriver driver, String obj,String ObjectName ) throws InterruptedException {
		try
		{
			WebElement element = null;
			if(obj.startsWith("id")){
				element = driver.findElement(By.id(obj.split("id:")[1]));	
			}else if(obj.startsWith("name")){
				element = driver.findElement(By.name(obj.split("name:")[1]));

			}else{
				element = driver.findElement(By.xpath(obj));

			}
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);
			testReporter.Log_Pass(ObjectName+" clicked", ObjectName+" Ciicked");
		}
		catch(Throwable t)
		{


			testReporter.Log_Fail(ObjectName+" Not Present", ObjectName+" Not Present", driver,snapshotsPath);
			t.printStackTrace();
			new Exception(ObjectName+" not present");
		}
	}


	public void waitForFrameAndSwitch(WebDriver driver , String frameName) {
		WebDriverWait wait = new WebDriverWait(driver,60);
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));


	}


	public void waitForFrameAndSwitch(String frameXpath,WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver,60);
		driver.switchTo().defaultContent();
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(frameXpath)));







	}








	public void waitForElementVisible(WebDriver driver, String element,String Element_Name) throws Throwable {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));

		} 
		catch (Throwable we) {
			we.printStackTrace();
			testReporter.Log_Fail(Element_Name+"Not visible", Element_Name+"Not visible", driver,snapshotsPath);
			throw new CustomException("No Element Found to perform click" ,we,ErrorCodes.ELEMENT_NOT_VISIBLE);
			
		}
	}
	public void Javascriptexecutor_forClick(WebDriver driver , String frameName,String XpathObject,String ObjectName) throws Throwable {
		waitForPageToLoad(driver);
		waitForFrameAndSwitch(driver, frameName);
		waitForPageToLoad(driver);
		waitForElementVisible(driver, XpathObject,ObjectName);
		try
		{
			WebElement e=driver.findElement(By.xpath(XpathObject));


			JavascriptExecutor js = (JavascriptExecutor)driver;




			js.executeScript("arguments[0].click();", e);
			testReporter.Log_Pass(ObjectName+" clicked", ObjectName+" Ciicked");
			js=null;
			e=null;
		}
		catch(Throwable t)






		{


			testReporter.Log_Fail(ObjectName+" Not Present", ObjectName+" Not Present", driver,snapshotsPath);
			t.printStackTrace();
			new Exception(ObjectName+" not present");
		}


	}

	public String[] findElementsInArray(WebDriver driver, String ObjectxPath,String ObjectName) throws InterruptedException, Exception {
		String[] array = null;
		int i = 0;
		if (isElementPresentByXpath(ObjectxPath, driver, ObjectName)) {
			List<WebElement> list = driver.findElements(By.xpath(ObjectxPath));
			array = new String[list.size()];
			Iterator<WebElement> it = list.iterator();
			while (it.hasNext()) {
				array[i++] = it.next().getText();
			}
		} else {
			testReporter.Log_Fail("findElementsInArray", ObjectxPath + " is not present", driver,snapshotsPath);
			throw new Exception("findElementsInArray() --- >Element Not Present");
		}



		return array;
	}



	public void removeAttribute(WebDriver driver,String ObjectXpath) {
		WebElement input = driver.findElement(By.xpath(ObjectXpath));


		((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('disabled','disabled')", input);











	}


	public void clickUsingActions(WebDriver screenName,String ObjXpath,String Element_Name) throws Exception
	{
		try
		{
			Actions actions = new Actions(screenName);
			WebElement webButton = screenName.findElement(By.xpath(ObjXpath));
			actions.click(webButton);
			testReporter.Log_Pass(Element_Name+" Clicked",Element_Name+" Clicked");
		}
		catch(Throwable t)
		{
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}


	public List<WebElement> findElementsInList(WebDriver driver, String ObjectxPath,String ObjectName) throws InterruptedException, Exception {















		List<WebElement> list=null;




		if (isElementPresentByXpath(ObjectxPath, driver, ObjectName)){ 
			list = driver.findElements(By.xpath(ObjectxPath));	


		} else {
			testReporter.Log_Fail("findElementsInArray", ObjectxPath + " is not present", driver,snapshotsPath);
			throw new Exception("findElementsInArray() --- >Element Not Present");
		}
		return list;
	}


	public boolean selectWindow(String windowname,WebDriver driver) {


		try {


			Thread.sleep(1000);


		} catch (InterruptedException e1) {


			e1.printStackTrace();


		}


		boolean selWindow = false;


		try {


			if (windowname != null) {


				if (windowname.contains("null")) {


					switchToWindow(1,driver);


					selWindow = true;


				} else {


					selectPopUp(windowname,driver);

					selWindow = true;


				}


			} else {


				switchToWindow(1,driver);


				selWindow = true;


			}


		} catch (Exception e) {


			{


				e.getMessage();


				e.printStackTrace();


				switchToWindow(1,driver);


				selWindow = false;


			}


		}


		return selWindow;


	}


	public void switchToWindow(int WindowNumber,WebDriver driver) {

		WindowNumber = WindowNumber - 1;
		try{
			Set<String> windows = driver.getWindowHandles();
			System.out.println(windows.size());
			Object[] win = windows.toArray();
			System.out.println(win.length);
			driver.switchTo().window(win[WindowNumber].toString());
		}
		catch (Exception e) {
			// TODO: handle exception
			System.err.println("\nException\n");
		}
	}

	public void selectPopUp(String arg,WebDriver driver) {

		boolean flag = false;

		try {

			for (int i = 0; i < 2 && flag == false; i++) {

				Set<String> pops = driver.getWindowHandles();


				{


					Iterator<String> it = pops.iterator();


					if (pops.size() > 1) {


						System.out.println("No of Windows " + pops.size());


						for (int j = 0; j < pops.size() && flag == false; j++) {


							String popupHandle = it.next().toString();


							if (driver.switchTo().window(popupHandle).getTitle().contains(arg)) {


								driver.switchTo().window(popupHandle);


								flag = true;


							}


						}


						pops.clear();


					}


				}


			}

		} catch (NoSuchWindowException e) {

			System.out.println("Not able to Navigate to Window " + arg);

		} catch (Exception e) {


		}

	}



	public void waitUntilExist(WebDriver driver,String ObjectxPath) throws Exception









	{
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		int i=1;
		do{

			i=i+1;
			Thread.sleep(1000);

			try
			{
				WebElement webButton = driver.findElement(By.xpath(ObjectxPath));
				System.out.println("X"+i);
				webButton.getText();
			}
			catch(Throwable t)

			{ 
				break;


			}
		}while(i<100);
		waitForPageToLoad(driver);

	}





	public boolean  CheckifExist(WebDriver driver, String element,int number) {
		try {
			driver.manage().timeouts().implicitlyWait(number, TimeUnit.MILLISECONDS);
			WebDriverWait wait = new WebDriverWait(driver, number);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
			//driver.findElement(By.xpath(element));
			waitForPageToLoad(driver);
			return true;






		} catch (Exception e) {
			System.out.println("not exist");
			waitForPageToLoad(driver);
			return false;












		}
	}

	public void Clickbtn(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception
	{
		Thread.sleep(500);
		try
		{
			WebElement webButton = screenName.findElement(By.xpath(ObjectxPath));
			webButton.click();
			testReporter.Log_Pass(Element_Name+" Clicked",Element_Name+" Clicked");
		}
		catch(Throwable t)

		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}

	public void Clickbtn2(WebDriver screenName,String ObjectxPath,String Element_Name) throws Exception



	{
		Thread.sleep(500);
		try
		{
			WebElement webButton = screenName.findElement(By.xpath(ObjectxPath));
			webButton.click();
			testReporter.Log_Pass(Element_Name+" Clicked",Element_Name+" Clicked");
		}
		catch(Throwable t)
		{ 
			//testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			//t.printStackTrace();
			throw new Exception("Element Not Present");
		}
	}





	public void WaitFunction(WebDriver driver,String Message) throws Exception   
	{
		waitUntilExist(driver, "//*[contains(text(),'Loading...')]");
		waitUntilExist(driver, "//*[contains(text(),'Loading...')]");
		if(CheckifExist(driver, "//*[contains(text(),'Attention')]", 3)) 
		{
			testReporter.Log_Fail("Attention Error Occured", "Attention Error Occured"+Message, driver,snapshotsPath);
			new Exception("Attention Error Occured");
		}

	}


	public void waituntilDisplayed(WebDriver driver,String xpath) throws InterruptedException





	{


		int i=1;

		boolean x=false;
		do
		{
			i=i+1;
			Thread.sleep(2000);
			try
			{
				x=driver.findElement(By.xpath(xpath)).isDisplayed();

			}
			catch(Throwable t)
			{

			}
			if(i==60)
			{
				break;
			}

		}while(x==false);

	}

	public boolean  CheckifTextExistAndReport(WebDriver driver, String element,String Element_Name) throws InterruptedException {
		try {
			//driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
			String Strelement="//*[contains(text(),'"+element+"')]";
			driver.findElement(By.xpath(Strelement));
			testReporter.Log_Pass(Element_Name + " Exist", Element_Name + " is Exist");
			System.out.println("Element Exist");
			//	return true;
			//driver.switchTo().defaultContent();
			//System.out.println(""+driver.getPageSource().toString());
			//System.out.println("");
			//	if (driver.getPageSource().contains(element))
			//	{
			waitForPageToLoad(driver);
			return true;
			//	}
			//	else
			//	{
			//	return false;
			//	}



		} catch (Throwable t) {


			waitForPageToLoad(driver);
			System.out.println("not exist");
			t.printStackTrace();
			testReporter.Log_Fail(Element_Name + " does not Exist", Element_Name + "does not Exist",driver,snapshotsPath);

			return false;
		}




	}
	public boolean  CheckifExistwithWait (WebDriver driver, String element) {

		try {
			//driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
			//	WebDriverWait wait = new WebDriverWait(driver, 1);
			//wait.until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
			driver.findElement(By.xpath(element));
			// waitForPageToLoad(driver);
			return true;

		} catch (Throwable e) {
			//e.printStackTrace();

			return false;
		}
	}





	public void MouseClick(WebDriver driver,String Xpath) throws AWTException
	{


		Robot bot = new Robot();
		WebElement e= driver.findElement(By.xpath(Xpath));
		int x=e.getLocation().getX();
		int y=e.getLocation().getY();
		System.out.println(x+" "+y);
		bot.mouseMove(x, y);    
		bot.mousePress(InputEvent.BUTTON1_MASK);
		bot.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	public int getTableRowCount(WebDriver driver,String xapth)
	{
		try
		{
			WebElement htmltable=driver.findElement(By.xpath(xapth));


			List<WebElement> rows=htmltable.findElements(By.tagName("tr"));
			//List<WebElement> columns=rows.get(1).findElements(By.tagName("td"));

			//System.out.println("Number of columns:"+rows.size());
			System.out.println(rows.size());
			return rows.size();
		}
		catch(Throwable t)
		{





			t.printStackTrace();
			return 0;
		}





















































	}





	public boolean  CheckifTextExistwithoutReport(WebDriver driver, String element,String Element_Name) throws InterruptedException {
		try {
			//driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
			String Strelement="//*[contains(text(),'"+element+"')]";
			driver.findElement(By.xpath(Strelement));
			//	testReporter.Log_Pass(Element_Name + " Exist", Element_Name + " is Exist");
			System.out.println("Element Exist");
			//	return true;
			//driver.switchTo().defaultContent();
			//System.out.println(""+driver.getPageSource().toString());
			//System.out.println("");
			//	if (driver.getPageSource().contains(element))
			//	{
			waitForPageToLoad(driver);
			return true;
			//	}
			//	else
			//	{
			//	return false;
			//	}





















		} catch (Throwable t) {
			waitForPageToLoad(driver);
			System.out.println("not exist");
			//t.printStackTrace();
			//testReporter.Log_Fail(Element_Name + " does not Exist", Element_Name + "does not Exist",driver);








			return false;


		}

















	}
	public void Javascriptexecutor_forClick(WebDriver driver ,String XpathObject,String ObjectName) throws Throwable {
		//waitForPageToLoad(driver);

		//waitForFrameAndSwitch(driver, frameName);
		waitForPageToLoad(driver);

		//waitForElementVisible(driver, XpathObject,ObjectName);
		try
		{
			Thread.sleep(500);
			verifyPageLoad(driver);
			WebElement e=driver.findElement(By.xpath(XpathObject));


			JavascriptExecutor js = (JavascriptExecutor)driver;
			
			js.executeScript("arguments[0].click();", e);
			waitForPageToLoad(driver);
			testReporter.Log_Pass(ObjectName+" clicked", ObjectName+" Clicked");
			
			js=null;
			e=null;
		}
		catch(Throwable t)
		{

			testReporter.Log_Fail(ObjectName+" Not Present", ObjectName+" Not Present", driver,snapshotsPath);
			t.printStackTrace();
			//new Exception(ObjectName+" not present");
			throw new CustomException("No Element Found to perform click" ,t,ErrorCodes.ELEMENT_NOT_VISIBLE);
			
		}


	}


	public void javascriptExecutor_Setvalue(WebDriver driver,String Xpath,String data,String ElementName) throws InterruptedException, CustomException
	{

try{

	waitForPageToLoad(driver);
		WebElement VVIN = driver.findElement(By.xpath(Xpath));
		JavascriptExecutor myExecutor = ((JavascriptExecutor) driver);

		myExecutor.executeScript("arguments[0].value='"+data+"';",VVIN);
		testReporter.Log_Pass(ElementName+" is set with "+data, ElementName+" is set with "+data);
}
catch(Throwable t)
{ 
	testReporter.Log_Fail(ElementName+" does not Exist",ElementName+" does not Exist", driver,snapshotsPath);
	t.printStackTrace();
	//throw new Exception("Element Not Present");
	throw new CustomException("Element Not Present" ,t,ErrorCodes.ELEMENT_NOT_VISIBLE);
}
	}


	public void inputTextwithClick(WebDriver screenName,String ObjectxPath,String sValue,String Element_Name) throws Exception
	{
		try
		{
			WebElement inputText = screenName.findElement(By.xpath(ObjectxPath));
			//elementHighlight(screenName,inputText);
			inputText.click();
			inputText.clear();
			System.out.println(sValue);
			inputText.sendKeys(sValue);
			inputText.sendKeys(Keys.ENTER);
			testReporter.Log_Pass(Element_Name+" Entered",sValue + " entered in "+ Element_Name);
		}
		catch(Throwable t)
		{ 
			testReporter.Log_Fail(Element_Name+" does not Exist",Element_Name+" does not Exist", screenName,snapshotsPath);
			t.printStackTrace();
			//throw new Exception("Element Not Present");
			throw new CustomException("Element Not Present" ,t,ErrorCodes.ELEMENT_NOT_VISIBLE);
		}





	}





	/////////////////////////////////////////////////////////////////////////


































































	public boolean IsEnable(WebElement element){

		return element.isEnabled();
	}

	public boolean IsElement_Enable(WebDriver screenName,String xpath)throws Throwable{

		WebElement path=screenName.findElement(By.xpath(xpath));
		boolean flag = false;

		if(IsEnable(path)){
			flag = true;

		}else{

			flag = false;
		}
















		return flag;
	}

	public void Upload_documet_Robot(String Path) throws AWTException{

		try{
			StringSelection ss = new StringSelection(Path);
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
		}
	}

	public void ScrollToExpectedElement(WebDriver screenName,String xpath){

		WebElement elem = screenName.findElement(By.xpath(xpath));
		JavascriptExecutor exec = (JavascriptExecutor)screenName;
		exec.executeScript("arguments[0].scrollIntoView();", elem);
	}

	public boolean CheckBox_Chekif_Unchecked(WebDriver screenName,String Xpath,String Element_Name){

		boolean CheckStatus=screenName.findElement(By.xpath(Xpath)).isSelected();
		testReporter.Log_Pass(Element_Name+" Clicked",Element_Name+" Clicked");
		return CheckStatus;

	}


	public void validateData(WebDriver driver, String Xpathvalue, String expected)
	{
		try {


			WebElement element = driver.findElement(By.xpath(Xpathvalue));
			String compareString = element.getText();
			if(compareString.equalsIgnoreCase(expected))
			{
				testReporter.Log_Pass(driver.getTitle(), compareString);
			}
			else
			{
				testReporter.Log_Fail(driver.getTitle(), compareString, driver,snapshotsPath);
			}










		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
	}


	public void verifyPageLoad(WebDriver driver) throws InterruptedException{


		JavascriptExecutor js = (JavascriptExecutor)driver;

		for(int i=1;i<=100;i++){

			try{

				if(js.executeScript("return document.readyState").toString().equals("complete")){


					System.out.println("Page is loaded!");
					break;


				}else{

					Thread.sleep(1000);
				}




			}catch(Exception e){}

		}


	}

	
//	public String Update_Database(String Write_query,String Clm_Name){
//
//		String data = null;
//
//		try {
//
//			ResultSet rs1 = DB_Connect.ExecuteQuery(Write_query,"MSSQL");
//			while (rs1.next()) {
//
//				data=rs1.getString(Clm_Name);
//
//				//System.out.println(rs1.getString("Contract#"));
//
//
//			}
//
//			DB_Connect.closeConnection(rs1);
//
//		} catch (NullPointerException e) {
//
//
//
//
//			e.printStackTrace();     
//
//		} catch(Exception e){
//
//			e.printStackTrace();
//
//
//		}
//		return data;
//
//
//	}


	public void SwitchFrame(String framename, WebDriver driver)
	{
	 try{
		 
		 switch(framename)
		 {
		   case "MSF" :
		   driver.switchTo().defaultContent();	
		   driver.switchTo().frame("menuserverFrame");
		   System.out.println("Switched to MSF");
		   break;
		   
		   case "PSF" :
		   driver.switchTo().defaultContent();			
		   driver.switchTo().frame("pageserverFrame");
		   System.out.println("Switched to PSF");
		   break;
		   
		   case "TF" :
		   driver.switchTo().defaultContent();
		   driver.switchTo().frame("pageserverFrame");   
		   List<WebElement> lstf = driver.findElements(By.tagName("frame"));
		   for(WebElement elem : lstf){
				
				if(elem.getAttribute("id").equals("TitleFrame")){
					driver.switchTo().frame(elem);
					break;
					}
				}
		   
		   System.out.println("Switched to TF");
		   break;
			
		   case "CF" :
		   driver.switchTo().defaultContent();
		   driver.switchTo().frame("pageserverFrame");  
		   List<WebElement> lscf = driver.findElements(By.tagName("frame"));
		   
		   for(WebElement elem : lscf){
				
			if(elem.getAttribute("id").equals("ContentFrame")){
				driver.switchTo().frame(elem);
				break;
				}
			}
		   
//		   driver.switchTo().frame(lscf.get(2));
		   System.out.println("Switched to CF");
		   break;
			
		   case "WF" :
		   driver.switchTo().defaultContent();
		   driver.switchTo().frame("pageserverFrame");   
		   List<WebElement> lswf = driver.findElements(By.tagName("frame"));
		   for(WebElement elem : lswf){
				
				if(elem.getAttribute("id").equals("WaitFrame")){
					driver.switchTo().frame(elem);
					break;
					}
				}
		   System.out.println("Switched to WF");
		   break;
				
		   case "BF" :
		   driver.switchTo().defaultContent();
		   driver.switchTo().frame("pageserverFrame");   
		   List<WebElement> lsbf = driver.findElements(By.tagName("frame"));
		   for(WebElement elem : lsbf){
				
				if(elem.getAttribute("id").equals("ButtonFrame")){
					driver.switchTo().frame(elem);
					break;
					}
				}
		   System.out.println("Switched to BF");
		   break;
		   
		   case "MF" :
			   driver.switchTo().defaultContent();
			   driver.switchTo().frame("pageserverFrame");   
			   List<WebElement> lsmf = driver.findElements(By.tagName("frame"));
			   for(WebElement elem : lsmf){
					
					if(elem.getAttribute("id").equals("ButtonFrame")){
						driver.switchTo().frame(elem);
						break;
						}
					}
			   System.out.println("Switched to BF");
			   break;
		   
		   default : 
			   System.out.println("Frame not listed above");
		 }
	 	}
		 catch (Exception e)
		 {
			
			 System.out.println("FRAME EXCEPTION");
			 e.printStackTrace();
		 }
	 
	}
	
	
	public void windowScroll( WebDriver driver,int x,int y) throws Exception
	{
		JavascriptExecutor jse1 = (JavascriptExecutor) driver;
	
	       
		jse1.executeScript("window.scrollBy(" + x + "," + y + ")", "");

	}
	public void windowScroll1( WebDriver driver,String Xpath) throws Exception
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		WebElement element=driver.findElement(By.xpath(Xpath));
		jse.executeScript("window.scrollTo(0," + element.getLocation().y + ")");
		

	}
	

	

}