package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchPage {

	// whenever we create page, every page have its own driver
	// Now we need to initialize the driver, How to initialize the driver --> By using constructor
	private WebDriver driver;

	// Everypage its having own ElementUtil
	private ElementUtil eleUtil;
	
	//1.private By locators:
	private By searchProductResults = By.cssSelector("div#content div.product-layout");
	
	
	// 2.Page Constructor
	public SearchPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public int getSearchProductCount() {
		int productCount =  eleUtil.waitForElementsVisible(searchProductResults, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("product count : " + productCount);
		return productCount;
	}
	
	
	public ProductInfoPage selectProduct(String productName) {
		By productLocator = By.linkText(productName);
		eleUtil.waitForElementVisible(productLocator, AppConstants.DEFAULT_MEDIUM_TIME_OUT).click();
		return new ProductInfoPage(driver);
	}
	
	
	
	
	
	

}
