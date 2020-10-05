package com.goibibo.pageObjects;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.goibibo.utils.ActionsClass;
import com.goibibo.utils.DriverManager;

public class BookingPageObjects {
	Logger logger = LogManager.getLogger(BookingPageObjects.class);
	ActionsClass actionsClass = new ActionsClass();
	Actions actions = new Actions(DriverManager.getDriver());
	WebDriver driver;

	public BookingPageObjects(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//input[@value='BOOK']")
	WebElement bookButton;

	public void clickBookButton() {
		actionsClass.clickElement(bookButton);
	}

	// checks for book button, if found atleast one, returns true, else returns
	// false
	public boolean checkBookingPage() {
		String xpath = "//input[@value='BOOK']";
		int noOfBookButtons = driver.findElements(By.xpath(xpath)).size();
		if (noOfBookButtons > 0)
			return true;
		else
			return false;
	}

	@FindBy(xpath = "//span[@data-cy='finalPrice']")
	List<WebElement> pricesList;

	public String getLowestPrice() {
		int lowest = 999999999;
		String lowestPriceString = null; // the will hold the lowest price in string format with commas
		for (WebElement element : pricesList) {
			// gets price from elements and check with the min
			// if it is less than the min, changes the min to checked price
			// converting to int for easy comparisons
			String currentPriceText = actionsClass.getTextFromElement(element);
			// removing commas, to not get parsing exception
			int currentPrice = Integer.parseInt(currentPriceText.replaceAll(",", ""));
			if (currentPrice < lowest) {
				lowest = currentPrice;
				lowestPriceString = currentPriceText; // storing the current string in lowest string
			}

		}
		return lowestPriceString;
	}

	public void bookFlightWithPrice(String price) {
		// xpath for the immediate book button with the given price
		String xpath = "//span[@data-cy='finalPrice' and text()='" + price + "']/following::input[@data-cy='bookBtn']";
		actionsClass.clickElementByXpath(xpath);
	}
}
