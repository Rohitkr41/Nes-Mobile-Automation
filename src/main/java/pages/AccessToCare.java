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

public class AccessToCare {

	private final AndroidDriver driver;
	private final WebDriverWait wait;

	private final By accessToCareSection = AppiumBy.xpath(
		    "//*[contains(@content-desc,'Access to Care') " +
		    "and contains(@content-desc,'Pending')]"
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

		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(noButton));

		button.click();
		System.out.println("No clicked.");
	}

	public void selectRandomNeverEyeExam() {
		By dropdownLocator = AppiumBy.xpath("//*[@content-desc='Never Eye Exam\nSelect']");

		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
		dropdown.click();

		System.out.println("Never Eye Exam dropdown clicked.");

		Set<String> validReasonNames = new HashSet<>(Arrays.asList("Eyes were fine / no problem",
				"Other health problems took priority", "Thought eye problems are part of ageing/nothing can be done",
				"Cost (consultation or accompanying person)", "Clinic too far away", "No transport",
				"No one to take me", "Did not know where to go", "Could not get an appointment",
				"Waiting time too long"));

		By optionsLocator = AppiumBy.xpath("//android.widget.ScrollView/*[@content-desc]");

		wait.until(d -> d.findElements(optionsLocator).stream().anyMatch(option -> {
			String value = option.getAttribute("content-desc");

			return value != null && validReasonNames.contains(value.trim()) && option.isDisplayed()
					&& option.isEnabled();
		}));

		List<WebElement> validOptions = new ArrayList<>();

		for (WebElement option : driver.findElements(optionsLocator)) {
			String value = option.getAttribute("content-desc");

			if (value != null && validReasonNames.contains(value.trim()) && option.isDisplayed()
					&& option.isEnabled()) {
				validOptions.add(option);
			}
		}

		if (validOptions.isEmpty()) {
			throw new IllegalStateException("No valid Never Eye Exam options found in the dropdown.");
		}

		WebElement selectedOption = validOptions.get(ThreadLocalRandom.current().nextInt(validOptions.size()));

		String selectedReason = selectedOption.getAttribute("content-desc");
		selectedOption.click();

		System.out.println("Never Eye Exam reason selected: " + selectedReason);

		By applyButton = AppiumBy.accessibilityId("Apply");

		WebElement apply = wait.until(ExpectedConditions.elementToBeClickable(applyButton));
		apply.click();

		System.out.println("Apply button clicked successfully.");
	}

	public void clickCompleteQuestionnaire() {
		By completeButton = AppiumBy.accessibilityId("Complete Questionnaire");

		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(completeButton));

		button.click();
		System.out.println("Complete Questionnaire clicked.");
	}

}
