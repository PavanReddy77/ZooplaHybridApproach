package com.crm.qa.Pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.BaseClass.TestBase;

public class SalesPage extends TestBase
{
	@FindBy(xpath = "//img[contains(@class,'icon--logo')]")
	WebElement salesPageLogo;
		
	@FindBy(xpath = "//li[contains(@id,'listing_')]//a[contains(@class,'listing-results-price text-price')]")
	List<WebElement> priceListProperties;
	
	public SalesPage()
	{
		PageFactory.initElements(driver, this);
	}
	
	public String verifySalesPageTitle()
	{
		return driver.getTitle();
	}
	
	public boolean verifyLogo()
	{
		return salesPageLogo.isDisplayed();
	}
	
	//To Get all Values and Print them in Descending Order
	List<Integer> priceList = new ArrayList<Integer>();
	
	public void priceListPropertiesReverse()
	{
		for(WebElement propertyPrice : priceListProperties)
		{
			if(propertyPrice.getText().replaceAll("[^0-9]", "").isEmpty())
			{
				System.out.println(propertyPrice.getText()+" Which is not actual Value,  So this Property was not added in Price List");
			}
			else
			{
				priceList.add(Integer.parseInt(propertyPrice.getText().replaceAll("[^0-9]", "")));
			}
		}
		System.out.println("List of Properties Price in Descending Order ::: ");
		Collections.sort(priceList, Collections.reverseOrder());
		System.out.println(priceList);
	}
	
	public AgentsPage getPropertyByPosition(int position)
	{
		priceListProperties.get(position).click();
		
		return new AgentsPage();
	}
}
