package pages;

import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.chaintest.plugins.ChainTestListener;



public class LoginPage {

	private WebDriver driver;
	WebDriverWait wait;

	@FindBy(xpath = "//input[@type='email']")
	public WebElement username;

	@FindBy(xpath = "//input[@value='Next']")
	public WebElement nextButton;

	@FindBy(xpath = "//input[@type='password']")
	public WebElement password;

	@FindBy(xpath = "//input[@value='Sign in']")
	public WebElement signInButton;

	@FindBy(xpath = "//input[@value='Yes']")
	public WebElement staySignedInYesButton;

	@FindBy(xpath = "//img[@alt='Bid Management System']")
	public WebElement bmsLogo;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void enterUsername(String uname) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(username));
		username.click();
		username.sendKeys(uname);
		Thread.sleep(2000);
		ChainTestListener.log("Entered UserName : " +uname);
		wait.until(ExpectedConditions.elementToBeClickable(nextButton));
		nextButton.click();
		ChainTestListener.embed(((TakesScreenshot)(driver)).getScreenshotAs(OutputType.BYTES), "image/png");
	}

	public void enterPassword(String passwd) throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(password));
		password.click();
		password.sendKeys(passwd);
		Thread.sleep(2000);
		ChainTestListener.log("Entered Password : ************");
	}

	public void clickOnSignIn() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(signInButton));
		signInButton.click();
		staySignedInYesButton.click();             
		Thread.sleep(5000);
		ChainTestListener.log("Clicked on Signed In button. ");
	}
	
	public void loginIntoApp(String uname, String passwd) throws InterruptedException {
		enterUsername(uname);
		enterPassword(passwd);
		clickOnSignIn();
		Thread.sleep(10000);
		//ChainTestListener.step("Landed on BMS Home Page.");
		//ChainTestListener.pass("Logged in successfully and Landed on BMS Home Page.");
	}
	
}
