package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	// whenever we create page, every page have its own driver
	// Now we need to initialize the driver, How to initialize the driver --> By
	// using constructor
	private WebDriver driver;

	// Everypage its having own ElementUtil
	private ElementUtil eleUtil;

	// 1.By locator
	private By productHeader = By.tagName("h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By cartSuccessMessage = By.cssSelector("div.alert.alert-success");

	private Map<String, String> productInfoMap = new HashMap<String, String>();

	// 2.Page constructor
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeaderValue() {
		String productHeaderValue = eleUtil.doElementGetText(productHeader);
		System.out.println("product header : " + productHeaderValue);
		return productHeaderValue;
	}

	public int getProductImageCount() {
		int imagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("product images count : " + imagesCount);
		return imagesCount;

	}
	
	public void enterQuantity(int qty) {
		System.out.println("product Quantity : " + qty);
		eleUtil.doSendKeys(quantity, String.valueOf(qty));
	}
	
	public String addProductToCart() {
		eleUtil.doClick(addToCartBtn);
		String successMessage = eleUtil.waitForElementVisible(cartSuccessMessage, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		
		//testing
		//testin
		StringBuilder sb = new StringBuilder(successMessage);
		String mesg = sb.substring(0, successMessage.length()-1).replace("\n", "").toString();
		
		System.out.println("Cart success Message :" + mesg);
		return mesg;
	}
	
	public Map<String, String> getProductInfo() {
//		Brand: Apple
//		Product Code: Product 18
//		Reward Points: 800
//		Availability: In Stock

		//productInfoMap = new HashMap<String, String>(); //doesnot maintain order
		productInfoMap = new LinkedHashMap<String, String>();//will maintain the order the way we are adding the values
		//productInfoMap = new TreeMap<String, String>();//print in alphabetical order/sorted order - sort the keys

		// header:
		productInfoMap.put("productname", getProductHeaderValue());
		getProductMetaData();
		getProductPriceData();
		System.out.println("productInfoMap");
		return productInfoMap;
	}

	// fetching meta data:
	private void getProductMetaData() {
		// meta data:
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for (WebElement e : metaList) {
			String meta = e.getText();
			String metaInfo[] = meta.split(":");
			String key = metaInfo[0].trim();
			String value = metaInfo[1].trim();
			productInfoMap.put(key, value);
		}
	}

	//fetching price data:
	private void getProductPriceData() {
//		//price:
//		$2,000.00
//		Ex Tax: $2,000.00
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String price = priceList.get(0).getText();
		String exTax = priceList.get(1).getText();
		String exTaxVal = exTax.split(":")[1].trim();

		productInfoMap.put("productprice", price);
		productInfoMap.put("exTax", exTaxVal);
	}
	


}
