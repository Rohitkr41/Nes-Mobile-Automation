package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AccessToCare {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private final By accessToCareSection = AppiumBy
        .androidUIAutomator(
            "new UiSelector().description(\"Access to Care\\nPending, Expanded\")"
        );

    public AccessToCare(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void clickAccessToCare() {
        WebElement section = wait.until(
            ExpectedConditions.elementToBeClickable(accessToCareSection)
        );

        section.click();
        System.out.println("Access to Care section opened.");
    }
    
    public void clickNo() {
        By noButton = AppiumBy.accessibilityId("No");

        WebElement button = wait.until(
            ExpectedConditions.elementToBeClickable(noButton)
        );

        button.click();
        System.out.println("No clicked.");
    }

    
}