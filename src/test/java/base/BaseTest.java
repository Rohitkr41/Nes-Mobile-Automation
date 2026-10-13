package base;

import java.net.URI;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import pages.LoginPage;

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
        options.setDeviceName("emulator-5554");
        options.setUdid("emulator-5554");

        // APK path
        options.setApp("C:\\Users\\sumit\\Downloads\\nesLocal.apk");

        // --------------------------------
        // Start Appium Session
        // --------------------------------

        driver = new AndroidDriver(
                new URI("http://127.0.0.1:4723").toURL(),
                options
        );

        System.out.println("App launched successfully!");

        // --------------------------------
        // Explicit Wait
        // --------------------------------

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    // --------------------------------
    // Location Permission
    // --------------------------------

    protected void allowLocationPermission() {

        try {

            WebElement allowLocation = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            AppiumBy.androidUIAutomator(
                                    "new UiSelector().text(\"While using the app\")"
                            )
                    )
            );

            allowLocation.click();

            System.out.println("Location permission allowed!");

        } catch (Exception e) {

            System.out.println(
                    "Location permission popup was not displayed."
            );
        }
    }
    
    protected void loginAndOpenDashboard() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Rohit", "Rohit@123");
        allowLocationPermission();
        loginPage.verifyDashboard();
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
