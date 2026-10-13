package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class SelfReportedHealth {

	private final AndroidDriver driver;
	private final WebDriverWait wait;

	private final By selfReportedHealthSection =
	        AppiumBy.androidUIAutomator(
	            "new UiSelector().descriptionContains(\"Self-reported Health and Self-reported Health Conditions\")"
	        );

	public SelfReportedHealth(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	public void clickSelfReportedHealth() {

    By sectionLocator = AppiumBy.androidUIAutomator(
        "new UiSelector().descriptionContains(\"Self-reported Health and Self-reported Health Conditions\")"
    );

    WebElement section = wait.until(
        ExpectedConditions.presenceOfElementLocated(sectionLocator)
    );

    wait.until(ExpectedConditions.elementToBeClickable(sectionLocator));

    section.click();

    System.out.println("Self-reported Health section clicked.");
}

	public void selectRandomHealthRating() {

    // Health Rating dropdown locate karein
    By healthRatingDropdown = AppiumBy.androidUIAutomator(
        "new UiSelector().descriptionContains(\"Health Rating\")"
    );

    WebElement dropdown = wait.until(
        ExpectedConditions.elementToBeClickable(healthRatingDropdown)
    );

    dropdown.click();

    System.out.println("Health Rating dropdown clicked.");

    // Sirf first 3 options mein se random selection
    String[] ratings = {"Excellent", "Very good", "Good"};

    String selectedRating = ratings[
        java.util.concurrent.ThreadLocalRandom.current()
            .nextInt(ratings.length)
    ];

    // Option ko accessibility ID se locate karein
    By optionLocator = AppiumBy.accessibilityId(selectedRating);

    WebElement option = wait.until(
        ExpectedConditions.elementToBeClickable(optionLocator)
    );

    option.click();

    System.out.println("Health Rating selected: " + selectedRating);
}
	
	public void selectRandomHealthCondition() {
    By dropdownLocator = AppiumBy.xpath(
        "//*[@content-desc='Health Condition\nSelect']"
    );

    WebElement dropdown = wait.until(
        ExpectedConditions.elementToBeClickable(dropdownLocator)
    );
    dropdown.click();

    System.out.println("Health Condition dropdown clicked.");

    Set<String> excludedConditions = new HashSet<>(
        Arrays.asList("None", "Cancer", "Diabetes")
    );

    By optionsLocator = AppiumBy.xpath(
        "//android.widget.ScrollView/*[@content-desc]"
    );

    wait.until(d -> d.findElements(optionsLocator).stream().anyMatch(option -> {
        String value = option.getAttribute("content-desc");

        return value != null
            && !value.trim().isEmpty()
            && !excludedConditions.contains(value.trim())
            && option.isDisplayed()
            && option.isEnabled();
    }));

    List<WebElement> validOptions = new ArrayList<>();

    for (WebElement option : driver.findElements(optionsLocator)) {
        String value = option.getAttribute("content-desc");

        if (value != null
                && !value.trim().isEmpty()
                && !excludedConditions.contains(value.trim())
                && option.isDisplayed()
                && option.isEnabled()) {
            validOptions.add(option);
        }
    }

    if (validOptions.isEmpty()) {
        throw new IllegalStateException(
            "No valid Health Condition options found in the dropdown."
        );
    }

    WebElement selectedOption = validOptions.get(
        ThreadLocalRandom.current().nextInt(validOptions.size())
    );

    String selectedCondition = selectedOption.getAttribute("content-desc");
    selectedOption.click();

    System.out.println("Health Condition selected: " + selectedCondition);

    By applyButton = AppiumBy.accessibilityId("Apply");

    WebElement apply = wait.until(
        ExpectedConditions.elementToBeClickable(applyButton)
    );
    apply.click();

    System.out.println("Apply button clicked successfully.");
}

	public void clickSaveSelfReportedHealth() {

		By saveButton = AppiumBy.accessibilityId("Save Self-reported Health and Conditions");

		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(saveButton));

		button.click();

		System.out.println("Save Self-reported Health and Conditions clicked successfully.");
	}

}
