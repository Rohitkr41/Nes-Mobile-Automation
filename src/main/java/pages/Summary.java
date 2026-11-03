package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class Summary {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private final By summaryButton =
            AppiumBy.accessibilityId("Summary");

    public Summary(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void clickSummary() {

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(summaryButton)
        );

        button.click();

        System.out.println("Summary button clicked successfully!");
    }
}