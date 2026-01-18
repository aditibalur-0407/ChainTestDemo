package pages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


public class BaseClass {

	protected WebDriver driver;
	
	@BeforeSuite
	public void setupReportPath() {
	    String timestamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
	    //System.setProperty("chaintest.generator.simple.output-file", "target/chaintest/TestChainTestDemo_" + timestamp + ".html");
	}

	@BeforeTest
	public void setUp() {
		String browser = PropertyReader.getProperty("browserType").toString();
		ChainPluginService.getInstance().addSystemInfo("Browser : ", browser);
		ChainPluginService.getInstance().addSystemInfo("Owner Name : ", "Aditi Balur");
		ChainPluginService.getInstance().addSystemInfo("Application Name : ","SharePoint");
		ChainPluginService.getInstance().addSystemInfo("Tenant Name : ","LoopBMS");

		switch (browser.toLowerCase().trim()) {
		case "chrome":
			driver = new ChromeDriver();
			ChainTestListener.log("Opened the Browser : "+browser);
			break;
		case "firefox":
			driver = new FirefoxDriver();
			ChainTestListener.log("Opened the Browser : "+browser);
			break;
		case "edge":
			driver = new EdgeDriver();
			ChainTestListener.log("Opened the Browser : "+browser);
			break;
		case "safari":
			driver = new SafariDriver();
			ChainTestListener.log("Opened the Browser : "+browser);
			break;
		default:
			System.out.println("Please pass the correct browser name :" + PropertyReader.getProperty("browserType"));
			throw new IllegalArgumentException("WRONG BROWSER : " + PropertyReader.getProperty("browserType"));
		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		driver.get(PropertyReader.getProperty("applicationUrl"));
		ChainTestListener.log("Navigated to the URL  : "+PropertyReader.getProperty("applicationUrl"));
	}

	@AfterMethod
	public void attachScreenshot(ITestResult result)
	{ 
		if(result.isSuccess()) 
		{
			ChainTestListener.embed(takeScreenshot(), "image/png"); 
		}
		else
		//if(!result.isSuccess())
		{
			ChainTestListener.embed(takeScreenshot(), "image/png"); 
		} 
	}


	public byte[] takeScreenshot()
	{ 
		return ((TakesScreenshot)(driver)).getScreenshotAs(OutputType.BYTES); 
	}

	public void sendEmail() throws IOException, AddressException, MessagingException 
	{
		String emailBody = "";
		try {
		     emailBody = Files.readString(Paths.get("target/chaintest/AditiEmail.html"));
		    System.out.println("Email body loaded:\n" + emailBody);
		} catch (IOException e) {
		    e.printStackTrace();
		}

		  Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com"); // e.g., smtp.gmail.com
	        props.put("mail.smtp.port", "587");

	        // Authenticate
	        Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication("reflectautomation@gmail.com", "zvostbkfhcwsmfuv");
	            }
	        });

	        // Create email message
	        MimeMessage message = new MimeMessage(session);
	        message.setFrom(new InternetAddress("reflectautomation@gmail.com"));
	        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse("reflectautomation@gmail.com"));
	        message.setSubject("Automation Test Summary");
	        message.setContent(emailBody, "text/html; charset=utf-8");

	        // Send email
	        Transport.send(message);

	        System.out.println("Email sent with ChainTest report.");
	}


	@AfterSuite
	public void tearDown() throws AddressException, IOException, MessagingException {
		driver.quit();
		//sendEmail();
	}
}
