package com.zoopla.qa.TestCases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.zoopla.qa.BaseClass.TestBase;
import com.zoopla.qa.Pages.HomePage;
import com.zoopla.qa.Pages.SalesPage;

public class HomePageTest extends TestBase
{	
	HomePage homePage;
	SalesPage salesPage;
	
	public HomePageTest()
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
	}
	
	@Test(priority=1)
	public void verifyHomePageTitle()
	{
		String title = homePage.verifyHomePageTitle();
		Assert.assertEquals(title, "Zoopla > Search Property to Buy, Rent, House Prices, Estate Agents11");
		Log.info("Home Page Title Verified");
	}
	
	@Test(priority=2)
	public void verityZooplaLogo()
	{
		boolean flag = homePage.verifyLogo();
		Assert.assertTrue(flag);
		Log.info("Home Page --- Zoopla Logo Verified");
	}
	
	@Test(priority=3, enabled=false)
	public void enterCityNameToSearchBox()
	{
		salesPage = homePage.searchSaleLocation(property.getProperty("CityName"));
		Log.info("Successfully Entered City Name and Clicked on Search Button");
	}
}
