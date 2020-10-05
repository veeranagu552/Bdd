package com.goibibo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ConfigFileReader {
	private Properties properties;
	private final String propertiesFilePath = "src/main/resources/Configuration.properties";
	Logger logger = LogManager.getLogger(ConfigFileReader.class);
	public ConfigFileReader() {
		logger.info("configFileReader object created");
		File file = new File(propertiesFilePath);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.fatal("properties file not found");
		}
		properties = new Properties();
		try {
			properties.load(fis);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getChromeDriverProperty() {
		String chromeDriverProperty = properties.getProperty("chrome_driver_property");
		if (chromeDriverProperty != null)
			return chromeDriverProperty;
		else
			throw new RuntimeException("Chrome driver property is not specified in properties file");
	}
	public String getChromeDriverPath() {
		String chromeDriverPath = properties.getProperty("chrome_driver_path");
		if (chromeDriverPath != null)
			return chromeDriverPath;
		else
			throw new RuntimeException("Chrome driver path is not specified in properties file");
	}
	public String getFirefoxDriverPath() {
		String firefixDriverPath = properties.getProperty("firefox_driver_path");
		if (firefixDriverPath != null)
			return firefixDriverPath;
		else
			throw new RuntimeException("Firefox driver path is not specified in properties file");
	}

	public String getFirefoxDriverProperty() {
		String firefoxDriverProperty = properties.getProperty("firefox_driver_property");
		if (firefoxDriverProperty != null)
			return firefoxDriverProperty;
		else
			throw new RuntimeException("Firefix driver property is not specified in properties file");
	}
	public String getBrowserName() {
		String browserName = properties.getProperty("browser");
		if (browserName != null)
			return browserName;
		else
			throw new RuntimeException("Browser name is not specified in properties file");
	}

	public int getImplicitWait() {
		String implicitWait = properties.getProperty("implicitWait");
		if (implicitWait != null)
			return Integer.parseInt(implicitWait);
		else
			throw new RuntimeException("Implicit wait is not specified in properties file");
	}
	
	public String getUrl() {
		String url = properties.getProperty("url");
		if (url != null)
			return url;
		else
			throw new RuntimeException("Url is not specified in properties file");
	}
}
