package com.zoopla.qa.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.zoopla.qa.BaseClass.TestBase;

public class TestUtility extends TestBase
{
	//These 2 variable we used in TestBase Class for Page Load and Implicit Wait.
	public static long Page_Load_TimeOut = 40;
	public static long Implicit_Wait = 30;
	
	//Switching to Frame Utility.
	public void switchToFrame()
	{
		driver.switchTo().frame("mainpanel");
	}
	
	//Excel Sheet Path - Excel Utility.
	public static String TESTDATA_SHEET_PATH = "D:\\Pavan_JavaPractice\\Automation\\MavenHybridFramework"
			+ "\\src\\main\\java\\com\\crm\\qa\\TestData\\FreeCRMTestData.xlsx";		
	
	static Workbook book;
	static Sheet sheet;
	public static Object[][] getTestData(String sheetName)
	{
		FileInputStream file = null;
		try 
		{
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		try 
		{
			book = WorkbookFactory.create(file);
		} 
		catch (InvalidFormatException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) 
		{
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) 
			{
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		return data;
	}
	
	//Screenshot Utility.
	public static void takeScreenshotAtEndOfTest() throws IOException
	{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/Screenshots/" + System.currentTimeMillis() + ".png"));
	}
	
	//Explicit Wait for Click on any Element.
	public static void clickOn(WebDriver driver, WebElement element, int timeout)
	{
		new WebDriverWait(driver, timeout).
		until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	
	//Explicit Wait for Sending Data to any Element.
	public static void sendKeys(WebDriver driver, WebElement element, int timeout, String value)
	{
		new WebDriverWait(driver, timeout).
		until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(value);
	}
	
	//To Highlight the Element.
	public static void highLightElement(WebDriver driver,WebElement element)
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('style','background: palegreen; border: 8px solid red:')", element);
		try
		{
			Thread.sleep(500);
		}
		catch (InterruptedException e) 
		{
			System.out.println(e.getMessage());
		}
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white')", element);
	}
	
	//To Handle Frame.
	public void switchToFrame(int frame) 
	{
		try
		{
			driver.switchTo().frame(frame);
			System.out.println("Navigated to frame with id " + frame);
		} 
		catch (NoSuchFrameException e) 
		{
			System.out.println("Unable to locate frame with id " + frame + e.getStackTrace());
		} catch (Exception e) 
		{
			System.out.println("Unable to navigate to frame with id " + frame + e.getStackTrace());
		}
	}
	
	//To Scroll to Particular Element. 
	public static void scrollSpecificElement(WebDriver driver,WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}
	
	//Handle Alert in web base pop-up.
	public static void handleWebBaseAlert()
	{
		String alertMsg=driver.switchTo().alert().getText();
		System.out.println("Alert msg is:"+alertMsg);
		Alert alrt=driver.switchTo().alert();
		alrt.accept();
					
		driver.switchTo().alert().accept();
					
		Assert.assertEquals(alertMsg, "Field cannot be empty");
	}
	
	//Element Display or Not.
	public static void displayElement()
	{
		boolean elementDisplayed=driver.findElement(By.xpath("")).isDisplayed();
		if(elementDisplayed)
		{
			System.out.println("Element is displayed");
		}
		else
		{
			System.out.println("Element is not displayed");
		}
	}
	
	//Element is Enable or Not.
	public static void enableElement()
	{
		boolean enable=driver.findElement(By.xpath("")).isEnabled();
		if(enable)
		{
			System.out.println("Element is enabled in page");
		}
		else
		{
			System.out.println("Element is not enabled in page");
		}
	}
	
	//Extent Report - 1.
	public static String getSystemDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("_ddMMyyyy_HHmmss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	//Extent Report - 2.
	public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException
	{
		String dateName = new SimpleDateFormat("_ddMMyyyy_HHmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
	
	//Set Date For Log4J.
	public static void setDateForLog4j() 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("_ddMMyyy_HHmmss");
		System.setProperty("current_date", dateFormat.format(new Date()));
		PropertyConfigurator.configure("./src/main/resources/log4j.properties");
	}
}

	