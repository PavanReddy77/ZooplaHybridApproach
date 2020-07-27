package com.zoopla.qa.Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.zoopla.qa.BaseClass.TestBase;

public class HomePage extends TestBase
{
	@FindBy(xpath = "//input[@name='q']")
	WebElement inputBox;
	
	@FindBy(xpath = "//button[@class='button button--primary']")
	WebElement clickSearchButton;
	
	@FindBy(xpath = "//img[contains(@class,'icon--logo')]")
	WebElement zooplaLogo;
	
	public HomePage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public String verifyHomePageTitle()
	{
		return driver.getTitle();
	}
	
	public boolean verifyLogo()
	{
		return zooplaLogo.isDisplayed();
	}
	
	public SalesPage searchSaleLocation(String input)
	{
		inputBox.sendKeys(input);
		clickSearchButton.click();
		
		return new SalesPage();
	}
}
