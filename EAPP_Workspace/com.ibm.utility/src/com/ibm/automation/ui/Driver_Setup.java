package com.ibm.automation.ui;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;




public class Driver_Setup{
	
	public WebDriver driver;
	private String driverPath;
    public static String TC_ID=null;
    public String Url=null;
    public String browser_Type=null;
    
    public String proxyFile=null;
    
    

	
	
	public static String getTC_ID() {
		return TC_ID;
	}


	public static void setTC_ID(String tC_ID) {
		TC_ID = tC_ID;
	}


	public String getUrl() {
		return Url;
	}


	public void setUrl(String url) {
		Url = url;
	}


	public String getBrowser_Type() {
		return browser_Type;
	}


	public void setBrowser_Type(String browser_Type) {
		this.browser_Type = browser_Type;
	}


	public String getproxyFile() {
		return proxyFile;
	}


	public void setproxyFile(String proxyFile) {
		this.proxyFile = proxyFile;
	}


	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}


	public Driver_Setup()
	{
		
	}
	

	public String getDriverPath() {
		return driverPath;
	}


	public void setDriverPath(String driverPath) {
		this.driverPath = driverPath;
	}
	
	public WebDriver getDriver() {
		
		return driver;
	}
	
	
	
	
	@Parameters({ "browserType", "appURL","tcID","driverPath","proxyFile" })
	@BeforeClass
	public void initializeTestBaseSetup(String browserType, String appURL,String tcID,String driverPath,String proxyFile) {
		try {
			setDriverPath(driverPath);
			setproxyFile(proxyFile);
			setDriver(browserType.toUpperCase(), appURL);
			TC_ID=tcID;
			Url=appURL;
			browser_Type=browserType.toUpperCase();

		} catch (Exception e) {
			System.out.println("initializeTestBaseSetup Error....." );
			e.printStackTrace();
		}
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	private void setDriver(String browserType, String appURL) throws Exception {
		
		switch(browserType) {
		case "IE":
			driver = initIEDriver(appURL);
			break;
		case "CHROME":
			driver = initChromeDriver(appURL);
			break;
		case "FIREFOX":
			driver = initFirefoxDriver(appURL);
			break;
		default:
			System.out.println("browser : " + browserType
					+ " is invalid, Launching Firefox as default for execution...");
			driver = initFirefoxDriver(appURL);
		}
	}
	
	
	
	
	
	
	
	
	

	@SuppressWarnings("deprecation")
	private WebDriver initIEDriver(String appURL) {
		System.out.println("Launching Internet Explorer with new profile..");
		System.setProperty("webdriver.ie.driver", driverPath+ "IEDriverServer.exe");
		DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
		//Log.info(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS);
		cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		cap.setCapability("nativeEvents", false);    
		cap.setCapability("unexpectedAlertBehaviour", "accept");
		cap.setCapability("ignoreProtectedModeSettings", true);
		cap.setCapability("disable-popup-blocking", true);
		cap.setCapability("enablePersistentHover", true);
		cap.setCapability("ignoreZoomSetting", true);
		cap.setJavascriptEnabled(true);	
		driver = new InternetExplorerDriver(cap);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.navigate().to(appURL);
		return driver;
	}
	
	
	
	
	private WebDriver initChromeDriver(String appURL) throws Exception 
	{
		return initChromeDriver(appURL,"INOS004419","aug@2018");
	}
	
	private WebDriver initChromeDriver(String appURL,String userId,String password) throws Exception 
	{
		System.out.println("Launching google chrome with new profile..");		
		System.setProperty("webdriver.chrome.driver", driverPath+ "chromedriver.exe");
		
//		=========================================
//        Proxy proxy = new org.openqa.selenium.Proxy();
//        proxy.setHttpProxy("10.1.0.236" + ":" + 80);
//        proxy.setSslProxy("10.1.0.236" + ":" + 80);
//        proxy.setFtpProxy("10.1.0.236" + ":" + 80);
//        proxy.setSocksUsername("inos004419");
//        proxy.setSocksPassword("aug@2018");
//
//        DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
//        desiredCapabilities.setCapability(CapabilityType.PROXY, proxy);
//		WebDriver driver = new ChromeDriver(desiredCapabilities);
//		=========================================
		
		
	
		
		System.out.println("proxyFile "  + proxyFile);
//		=========================================		
		if(proxyFile==null || "".equals(proxyFile)|| "NA".equalsIgnoreCase(proxyFile))
		{
			driver = new ChromeDriver();
		
		}
		else
		{
			ChromeOptions chrome_options = new ChromeOptions();
			chrome_options.addExtensions(new File(proxyFile));
			driver = new ChromeDriver(chrome_options);			
		}
//		=========================================       		
        

       		
		

		
//		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//String baseUrl="http://" + "ssgrp0143" + ":" + "mar-2016" + "@" + "mpowerateaseuat.birlasunlife.com:9083/BSLI/apps/services/www/eapp/desktopbrowser/default/index.html#";
		driver.navigate().to(appURL);
	/*	WebDriverWait wait = new WebDriverWait(driver, 30);      
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());     
		alert.authenticateUsing(new UserAndPassword("ssgrp0143", "mar-2016"));*/
		
		
		
		
		
		
		

		
		
		return driver;
	}

	private WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		System.setProperty("webdriver.gecko.driver", driverPath+ "geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to(appURL);
		return driver;
	}
	
	

	

	

}

