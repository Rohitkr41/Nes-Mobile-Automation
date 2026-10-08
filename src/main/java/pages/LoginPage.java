package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class LoginPage {

	private AndroidDriver driver;
	private WebDriverWait wait;

	public LoginPage(AndroidDriver driver) {

		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	// --------------------------------
	// Username
	// --------------------------------

	private By username = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");

	// --------------------------------
	// Password
	// --------------------------------

	private By password = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");

	// --------------------------------
	// Login Button
	// --------------------------------

	private By loginButton = AppiumBy.accessibilityId("Login");

	// --------------------------------
	// Dashboard
	// --------------------------------

	private By dashboard = AppiumBy.accessibilityId("National Eye Survey");

	// --------------------------------
	// Enter Username
	// --------------------------------

	public void enterUsername(String usernameText) {

		WebElement usernameElement = wait.until(ExpectedConditions.elementToBeClickable(username));

		usernameElement.click();
		usernameElement.sendKeys(usernameText);
	}

	// --------------------------------
	// Enter Password
	// --------------------------------

	public void enterPassword(String passwordText) {

		WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(password));

		passwordElement.click();
		passwordElement.sendKeys(passwordText);
	}

	// --------------------------------
	// Click Login
	// --------------------------------

	public void clickLogin() {

		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

		System.out.println("Login button clicked!");
	}

	// --------------------------------
	// Complete Login
	// --------------------------------

	public void login(String usernameText, String passwordText) {

		enterUsername(usernameText);
		enterPassword(passwordText);
		clickLogin();
	}

	// --------------------------------
	// Verify Dashboard
	// --------------------------------

	public void verifyDashboard() {

		WebElement dashboardElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dashboard));

		Assert.assertTrue(dashboardElement.isDisplayed(), "Dashboard was not displayed");

		System.out.println("Dashboard displayed successfully!");
	}
}