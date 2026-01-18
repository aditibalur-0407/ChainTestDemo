package pages;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.chaintest.service.ChainPluginService;
import com.sun.jna.platform.FileUtils;

public class PropertyReader {
	 private static WebDriver driver;
	 
	   private static Properties properties;
	 
	   static {
	       properties = new Properties();
	       try {
	           FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
	           properties.load(fis);
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
	   }
	 
	   public static String getProperty(String key) {
	       return properties.getProperty(key);
	   }
	 
	   public static WebDriver getDriver() {
	       if (driver == null) {
	           driver = new ChromeDriver();
	           //ChainPluginService.getInstance().addSystemInfo("Build#", "1.0");
	           //ChainPluginService.getInstance().addSystemInfo("Owner Name#", "Aditi Balur");
	           //ChainPluginService.getInstance().addSystemInfo("Application Name : ", "SharePoint");
	           //ChainPluginService.getInstance().addSystemInfo("Tenant Name : ", "LoopBMS");
	       }
	       return driver;
	   }
	   
		/*
		 * private static String captureScreenshot(WebDriver driver, String testName) {
		 * try { File screenshot = ((TakesScreenshot)
		 * driver).getScreenshotAs(OutputType.FILE); String screenshotPath =
		 * "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
		 * FileUtils.CopyFile(screenshot, new File(screenshotPath)); return
		 * screenshotPath; } catch (IOException e) { e.printStackTrace(); return null; }
		 * }
		 */
	   
		/*
		 * private static void captureAndEmbedScreenshot(String stepDescription) { try {
		 * // Take a screenshot at current test step File screenshot =
		 * ((TakesScreenshot)
		 * propertyReader.getDriver()).getScreenshotAs(OutputType.FILE); String
		 * screenshotPath = "screenshots/" + stepDescription.replaceAll("\\s+", "_") +
		 * ".png"; // Use step description as filename FileUtils.copyFile(screenshot,
		 * new File(screenshotPath));
		 * 
		 * // Log the step chainTestListener.log(stepDescription);
		 * 
		 * // Embed the screenshot in the report
		 * 
		 * } catch (IOException e) { e.printStackTrace(); } }
		 */
	 
	   public static void quitDriver() {
	       if (driver != null) {
	           driver.quit();
	           driver = null;
	       }
	   }
}
