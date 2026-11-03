
package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
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
                "new UiSelector().descriptionContains(" +
                "\"Self-reported Health and Self-reported Health Conditions\")"
            );

    // Health Rating dropdown
    private final By healthRatingDropdown =
            AppiumBy.androidUIAutomator(
                "new UiSelector().descriptionContains(\"Health Rating\")"
            );

    // Exact Health Condition dropdown locator
    private final By healthConditionDropdown =
    	    AppiumBy.xpath(
    	        "//android.view.View[@content-desc='Health Condition\nSelect']"
    	    );

    // Actual options from your XML
    private final By healthOptions =
            AppiumBy.xpath(
                "//android.view.View[@content-desc and @clickable='true']"
            );

    private final By applyButton =
            AppiumBy.accessibilityId("Apply");

    private final By saveButton =
            AppiumBy.accessibilityId(
                "Save Self-reported Health and Conditions"
            );

    public SelfReportedHealth(AndroidDriver driver) {

        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(30)
        );
    }

    // Open Self-reported Health section
    public void clickSelfReportedHealth() {

        WebElement section = wait.until(
                ExpectedConditions.elementToBeClickable(
                        selfReportedHealthSection
                )
        );

        section.click();

        System.out.println(
                "Self-reported Health section clicked."
        );
    }

    // Select random Health Rating
    public void selectRandomHealthRating() {

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        healthRatingDropdown
                )
        );

        dropdown.click();

        String[] ratings = {
                "Excellent",
                "Very good",
                "Good"
        };

        String selectedRating = ratings[
                ThreadLocalRandom.current().nextInt(
                        ratings.length
                )
        ];

        By optionLocator =
                AppiumBy.accessibilityId(selectedRating);

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(
                        optionLocator
                )
        );

        option.click();

        System.out.println(
                "Health Rating selected: " + selectedRating
        );
    }

    // Select random Health Condition
   

public void selectRandomHealthCondition() {

    By dropdownLocator = AppiumBy.xpath(
        "//android.view.View[@content-desc='Health Condition\nSelect']"
    );

    // Open dropdown
    WebElement dropdown = wait.until(
        ExpectedConditions.elementToBeClickable(dropdownLocator)
    );

    dropdown.click();

    System.out.println("Health Condition dropdown clicked.");

    Set<String> excludedConditions = new HashSet<>(
        Arrays.asList("None", "Cancer", "Diabetes")
    );

    // Only actual dropdown list items
    By optionsLocator = AppiumBy.xpath(
        "//android.view.View[@content-desc and @clickable='true']"
    );

    // Wait for actual options to appear
    wait.until(d -> {
        List<WebElement> elements = d.findElements(optionsLocator);

        for (WebElement element : elements) {
            try {
                String value = element.getAttribute("content-desc");

                if (value != null
                        && !value.trim().isEmpty()
                        && !value.equals("Scrim")
                        && !value.contains("Health Condition")
                        && !excludedConditions.contains(value.trim())) {
                    return true;
                }
            } catch (StaleElementReferenceException ignored) {
            }
        }

        return false;
    });

    // Get visible valid conditions
    List<String> validConditions = new ArrayList<>();

    for (WebElement element : driver.findElements(optionsLocator)) {

        try {
            String value = element.getAttribute("content-desc");

            if (value == null) {
                continue;
            }

            value = value.trim();

            if (!value.isEmpty()
                    && !excludedConditions.contains(value)
                    && !value.equals("Scrim")
                    && !value.contains("Health Condition")
                    && element.isDisplayed()
                    && element.isEnabled()) {

                if (!validConditions.contains(value)) {
                    validConditions.add(value);
                }
            }

        } catch (StaleElementReferenceException ignored) {
        }
    }

    System.out.println("Valid Health Conditions: " + validConditions);

    if (validConditions.isEmpty()) {
        throw new IllegalStateException(
            "No valid Health Condition options found."
        );
    }

    // Random actual condition
    String selectedCondition = validConditions.get(
        ThreadLocalRandom.current().nextInt(validConditions.size())
    );

    System.out.println("Trying to select: " + selectedCondition);

    // Fresh locator for selected condition
    By selectedLocator = AppiumBy.xpath(
        "//android.view.View[@content-desc="
        + xpathLiteral(selectedCondition)
        + " and @clickable='true']"
    );

    WebElement selectedOption = wait.until(
        ExpectedConditions.elementToBeClickable(selectedLocator)
    );

    selectedOption.click();

    System.out.println(
        "Health Condition selected: " + selectedCondition
    );

    // Apply button
    By applyButton = AppiumBy.accessibilityId("Apply");

    WebElement apply = wait.until(
        ExpectedConditions.elementToBeClickable(applyButton)
    );

    apply.click();

    System.out.println(
        "Health Condition Apply clicked successfully."
    );
}

// Handles quotes safely in XPath values
private String xpathLiteral(String value) {

    if (!value.contains("'")) {
        return "'" + value + "'";
    }

    if (!value.contains("\"")) {
        return "\"" + value + "\"";
    }

    String[] parts = value.split("'", -1);

    StringBuilder result = new StringBuilder("concat(");

    for (int i = 0; i < parts.length; i++) {

        if (i > 0) {
            result.append(", \"'\", ");
        }

        result.append("'").append(parts[i]).append("'");
    }

    result.append(")");

    return result.toString();
}



    // Save Self-reported Health
    public void clickSaveSelfReportedHealth() {

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(
                        saveButton
                )
        );

        button.click();

        System.out.println(
                "Save Self-reported Health and Conditions clicked successfully."
        );
    }
}
