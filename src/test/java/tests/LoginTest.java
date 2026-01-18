package tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;

import pages.BaseClass;
import pages.LoginPage;
import pages.PropertyReader;

@Listeners(ChainTestListener.class)
public class LoginTest extends BaseClass{

	@Test(priority = 1)
	public void VerifyLoginTestWithValiCredentials() throws InterruptedException {
		LoginPage login = new LoginPage(driver);
		login.loginIntoApp(PropertyReader.getProperty("userName"), PropertyReader.getProperty("password"));
	}
	
	@Test(priority = 2)
	public void VerifyLoginTestWithInvalidCredentials() throws InterruptedException {
		LoginPage login = new LoginPage(driver);
		login.loginIntoApp(PropertyReader.getProperty("userName"), "1234");
	}

}
