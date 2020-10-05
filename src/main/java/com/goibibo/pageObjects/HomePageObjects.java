package com.goibibo.pageObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.goibibo.utils.ActionsClass;
import com.goibibo.utils.ConfigFileReader;

public class HomePageObjects {
	private Logger logger = LogManager.getLogger(HomePageObjects.class);
	private ActionsClass actionsClass = new ActionsClass();
	private WebDriver driver;

	public HomePageObjects(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void openHomePage() {
		actionsClass.getUrl(new ConfigFileReader().getUrl());
	}

	@FindBy(id = "oneway")
	WebElement oneWayButtonElement;

	public void clickOneWayButton() {
		logger.info("Clicking one way button");
		actionsClass.clickElement(oneWayButtonElement);
	}

	@FindBy(id = "roundTrip")
	WebElement roundTripButtonElement;

	public void clickRoundTripButton() {
		logger.info("Clicking round trip");
		actionsClass.clickElement(roundTripButtonElement);
	}

	@FindBy(id = "multiCity")
	WebElement multiCityButtonElement;

	public void clickMultiCityButton() {
		logger.info("Clicking multi city");
		actionsClass.clickElement(multiCityButtonElement);
	}

	@FindBy(id = "gosuggest_inputSrc")
	WebElement sourceElement;

	public void selectSource(String source) {
		logger.info("Clicking source text field");
		actionsClass.sendKeys(sourceElement, source);
		logger.info("Selecting source");
		driver.findElement(By.id("react-autosuggest-1-suggestion--0")).click();
		// actions.sendKeys(Keys.DOWN).sendKeys(Keys.ENTER).build().perform();
	}

	@FindBy(id = "gosuggest_inputDest")
	WebElement destinationElement;

	public void selectDestination(String destination) {
		logger.info("Clicking destination");
		actionsClass.sendKeys(destinationElement, destination);
		logger.info("Selecting destination");
		driver.findElement(By.id("react-autosuggest-1-suggestion--0")).click();
		// actions.sendKeys(Keys.DOWN).sendKeys(Keys.ENTER).build().perform();
	}

	@FindBy(id = "departureCalendar")
	WebElement departureDateElement;

	public void clickDepartureDate() {
		logger.info("Clicking departure date field");
		actionsClass.clickElement(departureDateElement);
	}

	@FindBy(id = "returnCalendar")
	WebElement returnDateElement;

	public void clickReturnDate() {
		logger.info("Clicking return date field");
		actionsClass.clickElement(returnDateElement);
	}

	// checks the date is past or not, if past, returns false, else returns true
	private boolean checkDate(String date) {
		boolean flag = false;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");

		Date dateObj = null;
		try {
			dateObj = format.parse(date); // getting date object from string for compare operation
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date toDay = new Date(); // gives today date
		if (toDay.compareTo(dateObj) > 0) { // today is greater, i.e given date is past
			logger.info("Past date can not be selected");
			throw new RuntimeException("Past dates can not be selected : " + date);
		} else {
			logger.info("Date is selectable");
			flag = true;
		}
		return flag;
	}

	@FindBy(className = "DayPicker-Caption")
	WebElement monthYearElement;

	// gets month and year from calendar and returns year
	private String getYearFromPage() {
		String monthYear = actionsClass.getTextFromElement(monthYearElement);
		String[] monthYearArr = monthYear.split(" ");
		String year = monthYearArr[1];
		System.out.println("Year :" + year);
		return year;
	}

	// gets month and year from calendar and returns month;
	private String getMonthFromPage() {
		String monthYear = actionsClass.getTextFromElement(monthYearElement);
		String[] monthYearArr = monthYear.split(" ");
		String month = monthYearArr[0];
		return month;
	}

	@FindBy(xpath = "//span[@class='DayPicker-NavButton DayPicker-NavButton--next']")
	WebElement nextButton;

	private void clickNextButton() {
		try {
			actionsClass.clickElement(nextButton);
		} catch (TimeoutException e) {
			System.out.println("Unable to click next button");
		}
	}

	private void navigateToYear(String year) {
		while (!getYearFromPage().equals(year)) { // Iterates until current year reaches given year
			clickNextButton();
		}
	}

	private void navigateToMonth(String month) {
		while (!getMonthFromPage().equalsIgnoreCase(month)) { // Iterates until current month reaches given month
			clickNextButton();
		}
	}

	private void clickDay(String day) {
		day = String.valueOf(Integer.parseInt(day)); // this will remove leading zeros if any
		String xpath = "//div[@class='calDate' and text()='" + day + "']";
		actionsClass.clickElementByXpath(xpath);
	}

	public void selectDepartureDate(String dateToClick) {
		logger.info("Selecting date : " + dateToClick);
		if (checkDate(dateToClick)) { // if the given date is not past
			String[] dateArr = dateToClick.split("-"); // splitting and storing
			String day = dateArr[0];
			String month = dateArr[1];
			String year = dateArr[2];
			navigateToYear(year); // clicks next button until reaches the given year
			navigateToMonth(month); // clicks next button until reaches the given month
			clickDay(day); // clicks the day after reaching month and year
		}
	}

	@FindBy(id = "pax_link_common")
	WebElement travellerDetailsElement;

	public void clickTravellerDetails() {
		actionsClass.clickElement(travellerDetailsElement);
	}

	@FindBy(id = "adultPaxBox")
	WebElement noOfAdultsElement;

	@FindBy(id = "childPaxBox")
	WebElement noOfChildrenElement;

	@FindBy(id = "infantPaxBox")
	WebElement noOfInfantsElement;

	@FindBy(id = "pax_close")
	WebElement closeTravellerDetailsElement;

	public void enterNoOfAdults(String noOfAdults) {
		actionsClass.sendKeys(noOfAdultsElement, noOfAdults);
	}

	public void enterNoOfChilder(String noOfChildren) {
		actionsClass.sendKeys(noOfChildrenElement, noOfChildren);
	}

	public void enterNoOfInfants(String noOfInfants) {
		actionsClass.sendKeys(noOfInfantsElement, noOfInfants);
	}

	@FindBy(id = "gi_class")
	WebElement travelClassElement;

	public void selectTravelClass(String value) {
		actionsClass.dropdownSelectByVlaue(value, travelClassElement);
	}

	public void closeTravellerDetailsWindow() {
		actionsClass.clickElement(closeTravellerDetailsElement);
	}

	@FindBy(id = "gi_search_btn")
	WebElement searchButton;

	public void clickSearchButton() {
		actionsClass.clickElement(searchButton);
	}

	// returns true if the returnDate is future date for departure date
	public boolean checkDepartureAndReturnDates(String deptDate, String returnDate) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
		Date deptDateObj = null;
		Date returnDateObj = null;
		try {
			deptDateObj = format.parse(deptDate); // getting date object from string for compare operation
			returnDateObj = format.parse(returnDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (returnDateObj.compareTo(deptDateObj) < 0) { // return date is past for dept date
			return false;
		} else {
			return true;
		}
	}

	public void selectReturnDate(String returnDate) {
		selectDepartureDate(returnDate);
	}

	@FindBy(id = "react-autosuggest-1-suggestion--0")
	WebElement firstSuggestionElemet;

	@FindBy(xpath = "//span[text()='Add upto 4 cities']")
	WebElement addCityButton;

	public void clickAddCityButton() {
		actionsClass.clickElement(addCityButton);
	}

	@FindBy(id = "gosuggest_inputDest")
	List<WebElement> destinationFieldElementsList;

	public List<WebElement> getDestinationBoxes() {
		return destinationFieldElementsList;
	}

	public void enterDestinationCities(String[] destCities) {
		System.out.println("Size :" + destinationFieldElementsList.size());	//gives destination boxes
		for (int i = 0; i < destinationFieldElementsList.size(); i++) {
			System.out.println("Entering : " + destCities[i]);
			actionsClass.clickElement(destinationFieldElementsList.get(i));	//clicking destination box
			actionsClass.sendKeys(destinationFieldElementsList.get(i), "");
			actionsClass.sendKeys(destinationFieldElementsList.get(i), destCities[i]);	//sending destination city
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Actions actions = new Actions(driver);	//selecting the first one from auto suggestions
			actions.sendKeys(Keys.DOWN);
			actions.sendKeys(Keys.ENTER);
			actions.sendKeys(Keys.ESCAPE);
			actions.click().build().perform();
			// driver.findElement(By.id("react-autosuggest-1-suggestion--0")).click();
		}
	}

	@FindBy(id = "gosuggest_inputSrc")
	List<WebElement> sourceFieldElementsList;

	public List<WebElement> getSourceBoxes() {
		return sourceFieldElementsList;
	}

	public void enterSourceCities(String[] sourceCities) {
		Actions actions = new Actions(driver);
		for (int i = 0; i < sourceFieldElementsList.size(); i++) {	//iterates through all the source boxes
			System.out.println("Entering : " + sourceCities[i]);
			actions.moveToElement(sourceFieldElementsList.get(i));	//navigating to element and clicking the source
			actions.doubleClick();
			actions.build().perform();
			System.out.println("Back Space");
			//actions.sendKeys(Keys.BACK_SPACE).build().perform();
			
			sourceFieldElementsList.get(i).sendKeys(Keys.BACK_SPACE);	//removing if any text is present
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			actionsClass.sendKeys(sourceFieldElementsList.get(i), sourceCities[i]);	//entering source
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			WebElement dropdown = driver.findElement(By.id("react-autosuggest-1-suggestion--0"));	//selecting from auto suggestion dropdown
			actionsClass.clickElement(dropdown);
			// actions.sendKeys(Keys.DOWN).sendKeys(Keys.ENTER).sendKeys(Keys.ESCAPE).build().perform();
		}
	}

	@FindBy(id = "departureCalendar")
	List<WebElement> departureDatesList;

	public void selectDates(String[] datesArray) {
		for (int i = 0; i < departureDatesList.size(); i++) {	//traverse through the calender boxes
			departureDatesList.get(i).click();
			selectDepartureDate(datesArray[i]);	//selects date
		}
	}
	
	@FindBy(xpath = "//span[text()='Reset']")
	WebElement resetButton;
	
	public void clickResetButton() {
		actionsClass.clickElement(resetButton);
	}
}