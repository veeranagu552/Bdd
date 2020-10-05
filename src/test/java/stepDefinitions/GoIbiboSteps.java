package stepDefinitions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.goibibo.pageObjects.BookingPageObjects;
import com.goibibo.pageObjects.HomePageObjects;
import com.goibibo.pageObjects.ReviewPageObjects;
import com.goibibo.utils.ActionsClass;
import com.goibibo.utils.Base;

import io.cucumber.java.en.*;

public class GoIbiboSteps extends Base {
	// \"([^\"]*)\"$
	HomePageObjects homePage = new HomePageObjects(driver);
	BookingPageObjects bookingPage = new BookingPageObjects(driver);
	ReviewPageObjects reviewPage = new ReviewPageObjects(driver);
	Logger logger = LogManager.getLogger(GoIbiboSteps.class);
	ActionsClass actionsClass = new ActionsClass();

	@Given("User is on the goibibo home page")
	public void user_is_on_the_goibibo_home_page() {
		// Write code here that turns the phrase above into concrete actions
		homePage.openHomePage();
	}

	@When("User clicks on one way trip")
	public void user_clicks_on_one_way_trip() {
		homePage.clickOneWayButton();
	}

	@When("^User selects source as \"([^\")]*)\" and destination as \"([^\"]*)\"$")
	public void user_selects_source_as_hyd_and_destination_as_blr(String source, String destination) {
		homePage.selectSource(source);
		homePage.selectDestination(destination);
	}

	@When("User selects departure date as \"([^\"]*)\"$")
	public void user_selects_departure_date_as_september(String departureDate) {
		homePage.clickDepartureDate();
		homePage.selectDepartureDate(departureDate);
	}

	@When("User enters no of adults as \"([^\"]*)\" children as \"([^\"]*)\" infants as \"([^\"]*)\"$")
	public void user_enters_no_of_adults_as_children_as_infants_as(String noOfAdults, String noOfChildren,
			String noOfInfants) {
		homePage.clickTravellerDetails();
		homePage.enterNoOfAdults(noOfAdults);
		homePage.enterNoOfChilder(noOfChildren);
		homePage.enterNoOfInfants(noOfInfants);
	}

	@When("User selects travel class as \"([^\"]*)\"$")
	public void user_selects_travel_class(String travelClass) {
		homePage.selectTravelClass(travelClass);
		homePage.closeTravellerDetailsWindow();
	}

	@When("User clicks on search button")
	public void user_clicks_on_search_button() {
		homePage.clickSearchButton();
	}

	@Then("user should be navigated to booking page")
	public void user_should_be_navigated_to_booking_page() {
		if (bookingPage.checkBookingPage())
			logger.info("Navigated to bookings page");
		else {
			logger.info("Navigation to bookings page failed");
			throw new RuntimeException("Navigation to bookings page failed");
		}
	}

	@Then("all the flights with fares should display")
	public void all_the_flights_with_fares_should_display() {
		// Write code here that turns the phrase above into concrete actions
	}

	@When("User clicks Book button for lowest price")
	public void user_clicks_book_button_for_lowest_price() {
		String lowestPrice = bookingPage.getLowestPrice();
		System.out.println(lowestPrice);
		bookingPage.bookFlightWithPrice(lowestPrice);
	}

	@Then("User should be navigated to review page")
	public void user_should_be_navigated_to_review_page() {
		if (reviewPage.checkReviewPage())
			logger.info("Navigated to review page");
		else {
			logger.info("Navigation to review page failed");
			throw new RuntimeException("Navigation to review page failed");
		}
	}

	@When("User clicks on round trip")
	public void user_clicks_on_round_trip() {
		homePage.clickRoundTripButton();
	}

	@When("User selects departure date as \"([^\"]*)\" and return date as \"([^\"]*)\"$")
	public void user_selects_departure_date_as_and_return_date_as(String departureDate, String returnDate) {
		if (homePage.checkDepartureAndReturnDates(departureDate, returnDate)) {
			homePage.clickDepartureDate();
			homePage.selectDepartureDate(departureDate);
			homePage.selectReturnDate(returnDate);
		} else {
			throw new RuntimeException("Return date can not be less than departure date");
		}
	}

	@When("User clicks Book button")
	public void user_clicks_book_button() {
		bookingPage.clickBookButton();
	}

	@When("User clicks on multicity button")
	public void user_clicks_on_multicity_button() {
		homePage.clickMultiCityButton();
		homePage.clickResetButton();
	}

	@When("User selects \"([^\"]*)\" sources as \"([^\"]*)\" and destinations as \"([^\"]*)\" and departureDates as \"([^\"]*)\"$")
	public void user_selects_no_of_cities_sources_as_and_destinations_as_and_departure_dates_as(String noOfCitiesText,
			String sourceCities, String destinationCities, String dates) {
		int noOfCities = Integer.parseInt(noOfCitiesText);
		String[] sourceCitesArray = sourceCities.split(",");
		String[] destCitiesArray = destinationCities.split(",");
		String[] datesArray = dates.split(",");
		//checking whether no of sources, destinations and dates are equal 
		if (noOfCities != sourceCitesArray.length || noOfCities != destCitiesArray.length
				|| noOfCities != datesArray.length)
			throw new RuntimeException("no of cities or dates are not proper");
		if (noOfCities < 2)
			throw new RuntimeException("Minimum 2 cities needed");
		if (noOfCities == 3)	//if to add 3 cities click add city button 1 time
			homePage.clickAddCityButton();
		if (noOfCities == 4) { //if to add 4 cities click add city button 2 times
			System.out.println("No : 4");
			homePage.clickAddCityButton();
			homePage.clickAddCityButton();
		}
		if (noOfCities > 4)	//can not add more than 4 cities
			throw new RuntimeException("Can not add " + noOfCities + " cities");
		homePage.enterDestinationCities(destCitiesArray);	//enters destination cities
		homePage.enterSourceCities(sourceCitesArray);	//enter source cities	
		homePage.selectDates(datesArray);	//enters dates
	}

	@And("User validates \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_validates_source_destination_departure_date(String source, String destination,
			String departureDate) {
		reviewPage.validateSourceDestinationDepartureDate(source, destination, departureDate);
	}

	@And("User validates \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_validates_source_destination_departure_date_return_date(String source, String destination,
			String departureDate, String returnDate) {
		reviewPage.validateSourceDestinationDepartureDateReturnDate(source, destination, departureDate, returnDate);
	}

	@And("User clicks add city button")
	public void user_clicks_add_city_button() {
		homePage.clickAddCityButton();
	}
	
	@And("User validates \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" for multicity trip$")
	public void user_validates_for_multicity_trip(String no, String sources, String destinations, String dates) {
		reviewPage.multicityValidation(no, sources.split(","), destinations.split(","), dates.split(","));
	}
}
