package com.testBase;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.utility.PropertiesUtils;

public class TestBase {
	public static WebDriver driver;
	public static Logger log = Logger.getLogger(TestBase.class);
	public static ExtentReports report;
	public static ExtentTest test;
	public static ExtentSparkReporter spark;

	public static WebDriver initialization() {
		System.out.println("Initializing browser");
		log.info("initializing browser");
		String browser = PropertiesUtils.getProperty("browser");
		log.info(browser + " browser is initialized");
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			driver = new ChromeDriver();
		}
		if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "chromedriver.exe");
			driver = new FirefoxDriver();
		}
		log.info("maximising brower window");
		driver.manage().window().maximize();
		log.info("applying common waits over a brower");
		driver.get(PropertiesUtils.getProperty("url"));
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
	
	public void reportInitilization() {
		spark = new ExtentSparkReporter(System.getProperty("user.dir")+"/target/Reports/ExtentReoprt.html");
		report = new ExtentReports();
		report.setSystemInfo("Host name","Umesh Mundlik");
		report.setSystemInfo("Platform","Windows");
		report.setSystemInfo("Environment", "Test Automation");
		report.attachReporter(spark);
	}
	public String takeScreenShot(String name) {
		TakesScreenshot ts= (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir")+"/screenshots/"+name+".jpg";
		try {
			FileUtils.copyFile(src, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
	public static void main(String[] args) {
		initialization();
	}
}
