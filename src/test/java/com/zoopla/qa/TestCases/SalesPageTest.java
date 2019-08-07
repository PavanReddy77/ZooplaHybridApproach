package com.zoopla.qa.TestCases;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zoopla.qa.BaseClass.TestBase;
import com.zoopla.qa.Pages.HomePage;
import com.zoopla.qa.Pages.SalesPage;

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
	public void verifySalesPageTitle(Method method)
	{
		extentTest = extent.startTest(method.getName());
		String title = salesPage.verifySalesPageTitle();
		Assert.assertEquals(title, "Property for Sale in London - Buy Properties in London - Zoopla11");
		Log.info("Sales Page Title Verified");
	}
	
	@Test(priority=2)
	public void verityZooplaLogo(Method method)
	{
		extentTest = extent.startTest(method.getName());
		boolean flag = salesPage.verifyLogo();
		Assert.assertTrue(flag);
		Log.info("Sales Page --- Zoopla Logo Verified");
	}
	
	@Test(priority=3)
	public void printListValues(Method method)
	{
		extentTest = extent.startTest(method.getName());
		salesPage.priceListPropertiesReverse();
		Log.info("All Price Lists are printed in Descending Order");
	}
	
	@Test(priority=4)
	public void clickOnPositionValue(Method method)
	{
		extentTest = extent.startTest(method.getName());
		salesPage.getPropertyByPosition(Integer.parseInt(property.getProperty("PropertyPosition")));
		Log.info("Clicked on Fifth Position from  Price List");
	}
}
