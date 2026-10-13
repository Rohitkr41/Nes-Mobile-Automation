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

public class SelfReportedEyeProblems {

	private final AndroidDriver driver;
	private final WebDriverWait wait;

	private final By selfReportedEyeProblemsSection = AppiumBy
			.androidUIAutomator("new UiSelector().descriptionContains("
					+ "\"Self-reported eye problems and any family history of eye diseases\")");

	// Flexible locators for the Eye Problems dropdown.
	private final By eyeProblemsDropdown = AppiumBy
			.androidUIAutomator("new UiSelector().description(\"Eye Problems\\nSelect\")");

	public SelfReportedEyeProblems(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	// Open the Self-reported Eye Problems section.
	public void clickSelfReportedEyeProblems() {

		WebElement section = wait.until(ExpectedConditions.elementToBeClickable(selfReportedEyeProblemsSection));

		section.click();

		System.out.println("Self-reported Eye Problems section opened.");
	}

	public void selectRandomEyeProblem() {
		By dropdownLocator = AppiumBy.xpath("//*[@content-desc='Eye Problems\nSelect']");

		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
		dropdown.click();

		System.out.println("Eye Problems dropdown clicked.");

		Set<String> excludedOptions = new HashSet<>(Arrays.asList("None"));

		// Dropdown mein diye gaye valid options
		Set<String> eyeProblemNames = new HashSet<>(
				Arrays.asList("Corneal opacity or scar", "Keratoconus", "Corneal dystrophy", "Infectious keratitis",
						"Dry eye disease", "Pterygium", "Trachoma or trichiasis", "Chemical or thermal injury"));

		By optionsLocator = AppiumBy.xpath("//*[@content-desc]");

		wait.until(d -> d.findElements(optionsLocator).stream().anyMatch(option -> {
			String value = option.getAttribute("content-desc");

			return value != null && eyeProblemNames.contains(value.trim()) && !excludedOptions.contains(value.trim())
					&& option.isDisplayed() && option.isEnabled();
		}));

		List<WebElement> validOptions = new ArrayList<>();

		for (WebElement option : driver.findElements(optionsLocator)) {
			String value = option.getAttribute("content-desc");

			if (value == null) {
				continue;
			}

			value = value.trim();

			if (eyeProblemNames.contains(value) && !excludedOptions.contains(value) && option.isDisplayed()
					&& option.isEnabled()) {
				validOptions.add(option);
			}
		}

		if (validOptions.isEmpty()) {
			throw new IllegalStateException("No valid eye problem options found in the dropdown.");
		}

		WebElement selectedOption = validOptions.get(ThreadLocalRandom.current().nextInt(validOptions.size()));

		String selectedProblem = selectedOption.getAttribute("content-desc");
		selectedOption.click();

		System.out.println("Eye Problem selected: " + selectedProblem);

		By applyButton = AppiumBy.accessibilityId("Apply");

		WebElement apply = wait.until(ExpectedConditions.elementToBeClickable(applyButton));
		apply.click();

		System.out.println("Apply button clicked successfully.");
	}
	// Save the completed section.
	public void clickSaveSelfReportedEyeProblems() {

		By saveButton = AppiumBy.accessibilityId("Save Self-reported eye problems and family history");

		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(saveButton));

		button.click();

		System.out.println("Save Self-reported eye problems and family history " + "clicked successfully.");
	}
}
