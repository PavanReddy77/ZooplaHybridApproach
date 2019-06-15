package com.crm.qa.TestCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.BaseClass.TestBase;
import com.crm.qa.Pages.HomePage;
import com.crm.qa.Pages.SalesPage;

public class SalesPageTest extends TestBase
{
	HomePage homePage;
	SalesPage salesPage;
	
	public SalesPageTest()
	{
		super();
	}
	
	@BeforeMethod
	public void setUp()
	{
		initialization();
		Log.info("Browser Launched Successfully");
		
		homePage = new HomePage();
		salesPage = new SalesPage();
		
		salesPage = homePage.searchSaleLocation(property.getProperty("CityName"));
	}
	
	@Test(priority=1)
	public void verifySalesPageTitle()
	{
		String title = salesPage.verifySalesPageTitle();
		Assert.assertEquals(title, "Property for Sale in London - Buy Properties in London - Zoopla");
		Log.info("Sales Page Title Verified");
	}
	
	@Test(priority=2)
	public void verityZooplaLogo()
	{
		boolean flag = salesPage.verifyLogo();
		Assert.assertTrue(flag);
		Log.info("Sales Page --- Zoopla Logo Verified");
	}
	
	@Test(priority=3)
	public void printListValues()
	{
		salesPage.priceListPropertiesReverse();
		Log.info("All Price Lists are printed in Descending Order");
	}
	
	@Test(priority=4)
	public void clickOnPositionValue()
	{
		salesPage.getPropertyByPosition(Integer.parseInt(property.getProperty("PropertyPosition")));
		Log.info("Clicked on Fifth Position from  Price List");
	}
	
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
		Log.info("Browser Terminated");
	}
}
