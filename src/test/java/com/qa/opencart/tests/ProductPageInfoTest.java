package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class ProductPageInfoTest extends BaseTest {
	
	@BeforeClass
	public void productInfoPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	
	@DataProvider
	public Object[][] getProductImagesTestData() {
		return new Object[][] {
			{"MacBook", "MacBook Pro", 4},
			{"iMac", "iMac" , 3},
			{"Apple", "Apple Cinema 30\"", 6},
			{"Samsung", "Samsung SyncMaster 941BW", 1},
		};
	}
	
	@Test(dataProvider = "getProductImagesTestData")
	public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
		
		searchPage = accPage.performSerach(searchKey);
		productInfoPage = searchPage.selectProduct(productName);
		int actImagesCount = productInfoPage.getProductImageCount();
		Assert.assertEquals(actImagesCount, imagesCount);
	}
	
	
	@Test
	public void productInfoTest() {
		searchPage = accPage.performSerach("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		Map<String, String> actProductInfoMap = productInfoPage.getProductInfo();
		
		softAssert.assertEquals(actProductInfoMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProductInfoMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProductInfoMap.get("Productname"), "MacBook Pro");
		softAssert.assertEquals(actProductInfoMap.get("Productprice"), "$2,000.00");
		
		softAssert.assertAll();
	}
	
	//assert(Hard assertion) vs verify(soft assertion) - from testNG
	
	@Test
	public void addToCartTest() {
		searchPage = accPage.performSerach("MacBook");
		productInfoPage = searchPage.selectProduct("MacBook Pro");
		productInfoPage.enterQuantity(2);
		String actCartMessage = productInfoPage.addProductToCart();
		//Success: You have added MacBook Pro to your shopping cart!
		softAssert.assertTrue(actCartMessage.indexOf("Success")>=0);
		softAssert.assertTrue(actCartMessage.indexOf("MacBook Pro")>=0);
		softAssert.assertEquals(actCartMessage, "Success: You have added MacBook Pro to your shopping cart!");
		softAssert.assertAll();
		
	}
	
	
	
	
	
	
	
	
	
	

}
