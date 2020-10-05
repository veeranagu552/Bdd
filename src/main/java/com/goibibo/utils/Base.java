package com.goibibo.utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class Base {
	protected WebDriver driver= DriverManager.getDriver();

	public void initialSetup() {
		
	}
}
