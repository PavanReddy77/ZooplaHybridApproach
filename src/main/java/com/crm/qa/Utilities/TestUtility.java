package com.crm.qa.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
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

import com.crm.qa.BaseClass.TestBase;

public class TestUtility extends TestBase
{
	
	//Here we write all common methods which are available for all the Classes.
	
	//1. These 2 variable we used in TestBase Class for Page Load and Implicit Wait.
	public static long Page_Load_TimeOut = 20;
	public static long Implicit_Wait = 30;
	
	
	//2. Switching to Frame Utility.
	public void switchToFrame()
	{
		driver.switchTo().frame("mainpanel");
	}
	
	
	//3. Excel Sheet Path - Excel Utility.
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
		// System.out.println(sheet.getLastRowNum() + "--------" +
		// sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) 
		{
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) 
			{
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				//System.out.println(data[i][k]);
			}
		}
		return data;
	}
	
	//4. Screenshot Utility.
	public static void takeScreenshotAtEndOfTest() throws IOException
	{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/Screenshots/" + System.currentTimeMillis() + ".png"));
	}
	
	//5. Explicit Wait for Click on any Element
	public static void clickOn(WebDriver driver, WebElement element, int timeout)
	{
		new WebDriverWait(driver, timeout).
		until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}
	
	//6. Explicit Wait for Sending Data to any Element
	public static void sendKeys(WebDriver driver, WebElement element, int timeout, String value)
	{
		new WebDriverWait(driver, timeout).
		until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(value);
	}
	
	//7. To Highlight the Element
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
	
	//8. To Handle Frame
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
	
	//9. To Scroll to Particular Element 
	public static void scrollSpecificElement(WebDriver driver,WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}
	
	//10. Handle Alert in web base pop-up
	public static void handleWebBaseAlert()
	{
		String alertMsg=driver.switchTo().alert().getText();// To Capture Alert text
		System.out.println("Alert msg is:"+alertMsg);
		Alert alrt=driver.switchTo().alert();// In direct approach to handle alert
		alrt.accept();
					
		driver.switchTo().alert().accept();
					
		Assert.assertEquals(alertMsg, "Field cannot be empty");//Verify Alert Message	
	}
	
	//11. Element Display or Not
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
	
	//12. Element is Enable or Not
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
	
	//13. Sending Email 
	//You can refer the user guide https://commons.apache.org/proper/commons-email/userguide.html
	public static void sendEmail() throws EmailException
	{
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("saquibhelal78@gmail.com", "****"));
		email.setSSLOnConnect(true);
		email.setFrom("rehan@gmail.com");
		email.setSubject("Automation Test Report");
		email.setMsg("This is a test mail from Selenium :-)");
		email.addTo("rehanabrahim@gmail.com");
		email.send();
		System.out.println("Email Sent Successfully======>");
	}
}

	