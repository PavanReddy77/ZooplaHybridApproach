package com.zoopla.qa.BaseClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.zoopla.qa.Utilities.TestUtility;
import com.zoopla.qa.Utilities.WebEventListener;

public class TestBase
{
	public static WebDriver driver; 
	public static Properties property; 
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static Logger Log;

	public TestBase() 
	{
		Log = Logger.getLogger(this.getClass()); 
		try 
		{
			property = new Properties();
			FileInputStream ip = new FileInputStream("D://Automation_Workspace//ZooplaHybridApproach//src//main//java//com//zoopla//qa//Configuration//Configuration.properties");
			property.load(ip);
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@BeforeTest
	public void setExtent()
	{
		TestUtility.setDateForLog4j();
	}
	
	public static void initialization() 
	{
		//String broswerName = System.getProperty("Browser");
		
		String broswerName = property.getProperty("Browser");
		if(broswerName.equals("chrome"))
		{
			System.setProperty("webdriver.chrome.driver","./Drivers/chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		else if(broswerName.equals("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","./Drivers/geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		
		e_driver = new EventFiringWebDriver(driver);
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtility.Page_Load_TimeOut, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtility.Implicit_Wait, TimeUnit.SECONDS);
		
		driver.get(property.getProperty("Url"));
	}
	
	@AfterTest
	public void endReport()
	{
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException
	{
		driver.quit();
		Log.info("Browser Terminated");
	}
}
