package base;

import java.net.URI;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import pages.LoginPage;
import pages.Questionnaire;
import pages.ReferralPage;

public class BaseTest {

	protected AndroidDriver driver;
	protected WebDriverWait wait;

	@BeforeMethod
	public void setup() throws Exception {

		// --------------------------------
		// Appium Capabilities
		// --------------------------------

		UiAutomator2Options options = new UiAutomator2Options();

		options.setPlatformName("Android");
		options.setAutomationName("UiAutomator2");

		// Exact emulator
//        options.setDeviceName("emulator-5554");
//        options.setUdid("emulator-5554");

		options.setDeviceName("HA1FZFNG");
		options.setUdid("HA1FZFNG");

		// APK path
		options.setApp("C:\\Users\\sumit\\Downloads\\nesLocal.apk");

		// --------------------------------
		// Start Appium Session
		// --------------------------------

		driver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);

		System.out.println("App launched successfully!");

		// --------------------------------
		// Explicit Wait
		// --------------------------------

		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	// --------------------------------
	// Location Permission
	// --------------------------------

	
	protected void allowLocationPermission() {

		// Real device: resource-id
		By realDeviceButton = AppiumBy.id(
				"com.android.permissioncontroller:id/permission_allow_foreground_only_button");

		// Emulator (Android Studio): button ka text
		By emulatorButton = AppiumBy.androidUIAutomator(
				"new UiSelector().text(\"While using the app\")");

		// Dono me se jo pehle dikhe wo lo (max 10 sec wait)
		By allowButton = findFirstDisplayed(10, realDeviceButton, emulatorButton);

		if (allowButton != null) {

			driver.findElement(allowButton).click();

			System.out.println("Location permission allowed!");

		} else {

			System.out.println("Location permission popup was not displayed.");
		}
	}

	
	private By findFirstDisplayed(int seconds, By... locators) {

		long endTime = System.currentTimeMillis() + (seconds * 1000L);

		while (System.currentTimeMillis() < endTime) {

			for (By locator : locators) {
				if (!driver.findElements(locator).isEmpty()) {
					return locator;
				}
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				return null;
			}
		}

		return null;
	}

	protected void loginAndOpenDashboard() {
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login("Rohit", "Rohit@123");
		allowLocationPermission();
		loginPage.verifyDashboard();
	}

//    protected static final String PATIENT_NAME = "Govind Kumar";
	protected static final String PATIENT_NAME = "jitin Kumar";

	protected void openPatientQuestionnaire() {
		Questionnaire questionnaire = new Questionnaire(driver);

		questionnaire.openMemberSection(PATIENT_NAME, "Questionnaire");
	}

	protected void openPatientVisionAssessment() {
		Questionnaire questionnaire = new Questionnaire(driver);

		questionnaire.openMemberSection(PATIENT_NAME, "Vision Assessment");
	}

	protected void openPatientReferral(String patientName) {
		ReferralPage referralPage = new ReferralPage(driver);
		referralPage.openMemberReferral(patientName);
	}

	// --------------------------------
	// Close Driver
	// --------------------------------

	@AfterMethod
	public void tearDown() {

		if (driver != null) {
//            driver.quit();
		}
	}
}
