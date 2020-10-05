@goibibo
Feature: Goibibo flight reservation
  Goibibo flight reservation feature with different scenarios

  @onewaytrip
  Scenario Outline: Goibibo one way trip reservation
    Given User is on the goibibo home page
    When User clicks on one way trip
    And User selects source as <source> and destination as <destination>
    And User selects departure date as <departureDate>
    And User enters no of adults as <noofadults> children as <noofchildren> infants as <noofinfants>
    And User selects travel class as <travelclass>
    And User clicks on search button
    Then user should be navigated to booking page
    When User clicks Book button for lowest price
    Then User should be navigated to review page
    And User validates <source> <destination> <departureDate>

    Examples: 
      | source       | destination | departureDate      | noofadults | noofchildren | noofinfants | travelclass |
      | "Vijayawada" | "Chennai"   | "01-November-2020" | "2"        | "1"          | "1"         | "B"         |

  @roundtrip
  Scenario Outline: Goibibo round trip reservation
    Given User is on the goibibo home page
    When User clicks on round trip
    And User selects source as <source> and destination as <destination>
    And User selects departure date as <departureDate> and return date as <returnDate>
    And User enters no of adults as <noofadults> children as <noofchildren> infants as <noofinfants>
    And User selects travel class as <travelclass>
    And User clicks on search button
    Then user should be navigated to booking page
    And all the flights with fares should display
    When User clicks Book button
    Then User should be navigated to review page
    And User validates <source> <destination> <departureDate> <returnDate>

    Examples: 
      | source      | destination | departureDate   | returnDate    | noofadults | noofchildren | noofinfants | travelclass |
      | "Hyderabad" | "Goa"       | "05-April-2021" | "08-May-2021" | "2"        | "1"          | "0"         | "E"         |

  @multycitytrip
  Scenario Outline: Goibibo multi city reservation
    Given User is on the goibibo home page
    When User clicks on multicity button
    And User selects <noOf> sources as <sources> and destinations as <destinations> and departureDates as <departureDates>
    And User enters no of adults as <noofadults> children as <noofchildren> infants as <noofinfants>
    And User selects travel class as <travelclass>
    And User clicks on search button
    Then user should be navigated to booking page
    And all the flights with fares should display
    When User clicks Book button
    Then User should be navigated to review page
    And User validates <noOf> <sources> <destinations> <departureDates> for multicity trip

    Examples: 
      | noOf | sources                                   | destinations                    | departureDates                                                    | noofadults | noofchildren | noofinfants | travelclass |
      | "3"  | "Hyderabad,Vijayawada,Bhubaneswar"        | "Cuddapah,Chennai,Kolkata"      | "16-October-2020,17-October-2020,18-October-2020"                 | "2"        | "1"          | "0"         | "E"         |
      | "4"  | "Hyderabad,Vijayawada,Bhubaneswar,Mumbai" | "Cuddapah,Chennai,Kolkata,Pune" | "16-October-2020,17-October-2020,18-October-2020,19-October-2020" | "2"        | "1"          | "0"         | "E"         |
