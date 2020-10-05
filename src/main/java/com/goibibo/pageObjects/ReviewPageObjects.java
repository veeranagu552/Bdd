package com.goibibo.pageObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.goibibo.utils.ActionsClass;
import com.goibibo.utils.DriverManager;

public class ReviewPageObjects {
	Logger logger = LogManager.getLogger(BookingPageObjects.class);
	ActionsClass actionsClass = new ActionsClass();
	Actions actions = new Actions(DriverManager.getDriver());
	WebDriver driver;

	public ReviewPageObjects(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// checks for Ticket Details text and returns true if found, else false
	public boolean checkReviewPage() {
		String xpath = "//span[text()='Ticket Details']";
		int ticketDetailsElement = driver.findElements(By.xpath(xpath)).size();
		if (ticketDetailsElement > 0)
			return true;
		else
			return false;
	}

	@FindBy(xpath = "//span[@class='fl mobdn ico18 padL10']")
	List<WebElement> multiplejourneyBanner;

	@FindBy(xpath = "//span[@class='fl mobdn ico18 padL10']")
	WebElement journeyBanner;

	// one way trip validation, gets source, destination, dept date and validates
	public void validateSourceDestinationDepartureDate(String source, String destination, String departureDate) {
		// we will get text from a banner which will have all the details(source, dest,
		// date)
		String title = actionsClass.getTextFromElement(journeyBanner);
		String sourceFromPage = title.split("-")[0].trim(); // extracting source from the banner text
		String destinationFromPage = title.split("-")[1].trim().split(" ")[0].trim(); // extracting destination from the
																						// banner text
		System.out.println(sourceFromPage);
		System.out.println(destinationFromPage);
		String dateFromPage = title.split(",")[1].trim(); // extracting date from the banner text
		System.out.println(dateFromPage);
		Assert.assertEquals(sourceFromPage, source);
		Assert.assertEquals(destinationFromPage, destination);
		SimpleDateFormat dateFromPageFormat = new SimpleDateFormat("dd MMM yyyy"); // date format from page
		SimpleDateFormat dateFromFeature = new SimpleDateFormat("dd-MMMM-yyyy"); // date format from feature
		Date dateFromPageObj = null;
		Date dateFromFeatureObj = null;
		try {
			dateFromPageObj = dateFromPageFormat.parse(dateFromPage); // creating date objects for assertion
			dateFromFeatureObj = dateFromFeature.parse(departureDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("Date fea : " + dateFromFeatureObj + " Date page : " + dateFromPageObj);
		Assert.assertEquals(dateFromPageObj, dateFromFeatureObj);
	}

	// round trip validation. validates source, destination, dept date, return date
	public void validateSourceDestinationDepartureDateReturnDate(String source, String destination,
			String departureDate, String returnDate) {
		int size = multiplejourneyBanner.size();
		String title = actionsClass.getTextFromElement(multiplejourneyBanner.get(0)); // extracting text from banner,
																						// which will have all the
																						// details
		String sourceFromPage = title.split("-")[0].trim(); // extracting source text from banner
		String destinationFromPage = title.split("-")[1].trim().split(" ")[0].trim(); // extracting destination text
																						// from banner
		System.out.println(sourceFromPage);
		System.out.println(destinationFromPage);
		Assert.assertEquals(sourceFromPage, source);
		Assert.assertEquals(destinationFromPage, destination);
		String departureDateFromPage = title.split(",")[1].trim(); // extracting date text from banner
		System.out.println(departureDateFromPage);
		SimpleDateFormat departureDateFromPageFormat = new SimpleDateFormat("dd MMM yyyy"); // creating date objects for
																							// assertion
		SimpleDateFormat departureDateFromFeature = new SimpleDateFormat("dd-MMMM-yyyy");
		Date departureDateFromPageObj = null;
		Date departureDateFromFeatureObj = null;
		try {
			departureDateFromPageObj = departureDateFromPageFormat.parse(departureDateFromPage);
			departureDateFromFeatureObj = departureDateFromFeature.parse(departureDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("departure date from feature : " + departureDateFromPageObj + " departure date page page: "
				+ departureDateFromFeatureObj);
		Assert.assertEquals(departureDateFromPageObj, departureDateFromFeatureObj);
		// second banner will have the return date
		String returnDateFromPage = actionsClass.getTextFromElement(multiplejourneyBanner.get(1)).split(",")[1].trim();
		System.out.println(returnDateFromPage);
		Date returnDateFromPageObj = null;
		Date returnDateFromFeatureObj = null;
		try {
			returnDateFromPageObj = departureDateFromPageFormat.parse(returnDateFromPage);
			returnDateFromFeatureObj = departureDateFromFeature.parse(returnDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("return date from feature : " + returnDateFromPageObj + " return date page page: "
				+ returnDateFromFeatureObj);
		Assert.assertEquals(returnDateFromPageObj, returnDateFromFeatureObj); // asserting dates
	}

	// multicity validation. validates, source cities, destination cities and dates
	public void multicityValidation(String noFromFeature, String[] sourcesArrayFromFeature,
			String[] destinationsArrayFromFeature, String[] datesArrayFromFeature) {
		List<WebElement> titleBanners = multiplejourneyBanner; // gives list of banner elements. we will iterate through
																// every banner and fetches source, destination and
																// date. Then we will assert with feature texts
		int noFromPage = titleBanners.size();
		Assert.assertEquals(noFromPage, Integer.parseInt(noFromFeature)); // checking no is equal or not
		String[] sourcesArrayFromPage = new String[noFromPage]; // creating string array to store sources, destinations,
																// and dates
		String[] destinationsArrayFromPage = new String[noFromPage];
		String[] datesArrayFromPage = new String[noFromPage];
		int i = 0;
		for (WebElement element : titleBanners) { // traversing through all the banners and extracting details
			String title = actionsClass.getTextFromElement(element);
			String source = title.split("-")[0].trim(); // extracting source
			System.out.println("got source : " + source);
			String destination = title.split("-")[1].trim().split(" ")[0].trim(); // extracting destination
			System.out.println("got destination : " + destination);
			String date = title.split(",")[1].trim(); // extracting date
			System.out.println("got date : " + date);
			sourcesArrayFromPage[i] = source;
			destinationsArrayFromPage[i] = destination;
			datesArrayFromPage[i] = date;
			i++;
		}
		SimpleDateFormat datePageFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat dateFeatureFormat = new SimpleDateFormat("dd-MMMM-yyyy");
		for (i = 0; i < noFromPage; i++) { // traversing through the arrays and asserting
			System.out.println("Asserting : " + sourcesArrayFromFeature[i] + " " + sourcesArrayFromPage[i]);
			Assert.assertEquals(sourcesArrayFromFeature[i], sourcesArrayFromPage[i]);
			System.out.println("Asserting : " + destinationsArrayFromFeature[i] + " " + destinationsArrayFromPage[i]);
			Assert.assertEquals(destinationsArrayFromFeature[i], destinationsArrayFromPage[i]);
			Date pageDate = null;
			Date featureDate = null;
			try {
				pageDate = datePageFormat.parse(datesArrayFromPage[i]);
				featureDate = dateFeatureFormat.parse(datesArrayFromFeature[i]);
			} catch (ParseException e) {
			}
			System.out.println("Asserting : " + pageDate + " " + featureDate);
			Assert.assertEquals(pageDate, featureDate);
		}
	}
}
