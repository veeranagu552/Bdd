package com.goibibo.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {
	private static ConfigFileReader configFileReader = new ConfigFileReader();
	private static WebDriver driver;

	public static WebDriver getDriver() {
		if (driver == null)
			driver = createDriver();
		return driver;
	}

	// creates driver if not created, and returns
	private static WebDriver createDriver() {
		String browser = configFileReader.getBrowserName();
		WebDriver driver = null;
		if (browser.equalsIgnoreCase("CHROME")) {
			String driverPath = configFileReader.getChromeDriverPath();
			String driverProperty = configFileReader.getChromeDriverProperty();
			System.setProperty(driverProperty, driverPath);
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("FIREFOX")) {
			String driverPath = configFileReader.getFirefoxDriverPath();
			String driverProperty = configFileReader.getFirefoxDriverProperty();
			System.setProperty(driverProperty, driverPath);
			driver = new FirefoxDriver();
		}
		return driver;
	}
}
