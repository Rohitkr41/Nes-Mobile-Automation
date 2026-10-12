package pages;

import java.time.Duration;
import java.util.List;

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

    // Actual content-desc mein newline hai
    By dropdownLocator = AppiumBy.xpath(
        "//*[@content-desc='Health Condition\nSelect']"
    );

    WebElement dropdown = wait.until(
        ExpectedConditions.elementToBeClickable(dropdownLocator)
    );

    dropdown.click();

    System.out.println("Health Condition dropdown clicked.");

    java.util.Set<String> excludedConditions =
        new java.util.HashSet<>(
            java.util.Arrays.asList("None", "Diabetes", "Cancer")
        );

    // Popup ke elements inspect karein
    List<WebElement> elements = driver.findElements(
        AppiumBy.xpath("//*[@content-desc]")
    );

    System.out.println("===== DROPDOWN OPTIONS =====");

    for (WebElement element : elements) {
        String desc = element.getAttribute("content-desc");

        if (desc != null) {
            System.out.println("OPTION DEBUG: [" + desc + "]");
        }
    }

    // Questionnaire headers aur dropdown ko exclude karein
    By optionsLocator = AppiumBy.xpath(
        "//*[@content-desc and " +
        "not(contains(@content-desc,'Pending')) and " +
        "not(contains(@content-desc,'Collapsed')) and " +
        "not(@content-desc='Health Condition\nSelect')]"
    );

    wait.until(d -> {
        List<WebElement> options = d.findElements(optionsLocator);

        return options.stream().anyMatch(option -> {
            String value = option.getAttribute("content-desc");

            return value != null
                && !value.trim().isEmpty()
                && !value.contains("Self-reported")
                && !value.equals("Self reported health condition?")
                && !excludedConditions.contains(value.trim())
                && option.isDisplayed()
                && option.isEnabled();
        });
    });

    List<WebElement> options = driver.findElements(optionsLocator);
    List<WebElement> validOptions = new java.util.ArrayList<>();

    for (WebElement option : options) {

        String condition = option.getAttribute("content-desc");

        if (condition == null) {
            continue;
        }

        condition = condition.trim();

        if (condition.isEmpty()
                || condition.contains("Pending")
                || condition.contains("Collapsed")
                || condition.contains("Self-reported")
                || condition.equals("Self reported health condition?")
                || condition.equals("Health Condition\nSelect")
                || condition.equals("Save Self-reported Health and Conditions")
                || excludedConditions.contains(condition)
                || !option.isDisplayed()
                || !option.isEnabled()) {
            continue;
        }

        validOptions.add(option);
    }

    if (validOptions.isEmpty()) {
        throw new IllegalStateException(
            "No valid health condition options found. "
            + "Check dropdown debug output."
        );
    }

    WebElement selectedOption = validOptions.get(
        java.util.concurrent.ThreadLocalRandom.current()
            .nextInt(validOptions.size())
    );

    String selectedCondition =
        selectedOption.getAttribute("content-desc");

    selectedOption.click();

    System.out.println(
        "Health Condition selected: " + selectedCondition
    );

    // Apply button sirf tab click hoga jab woh available ho
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