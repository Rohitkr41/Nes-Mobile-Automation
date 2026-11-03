package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
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
			.xpath("//*[contains(@content-desc,'Access to Care') " + "and contains(@content-desc,'Pending')]");

	private final By completeQuestionnaireButton = AppiumBy.accessibilityId("Complete Questionnaire");

	public AccessToCare(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	public void clickAccessToCare() {
		WebElement section = wait.until(ExpectedConditions.elementToBeClickable(accessToCareSection));

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

		// Locate actual visible options anywhere in UI hierarchy
		By optionsLocator = AppiumBy.xpath("//*[@content-desc]");

		// Wait until at least one valid option appears
		wait.until(d -> {

			for (WebElement element : d.findElements(optionsLocator)) {

				try {
					String value = element.getAttribute("content-desc");

					if (value != null && validReasonNames.contains(value.trim()) && element.isDisplayed()
							&& element.isEnabled()) {

						return true;
					}

				} catch (StaleElementReferenceException ignored) {
				}
			}

			return false;
		});

		// Collect valid option names
		List<String> availableReasons = new ArrayList<>();

		for (WebElement element : driver.findElements(optionsLocator)) {

			try {
				String value = element.getAttribute("content-desc");

				if (value != null) {

					value = value.trim();

					if (validReasonNames.contains(value) && element.isDisplayed() && element.isEnabled()
							&& !availableReasons.contains(value)) {

						availableReasons.add(value);
					}
				}

			} catch (StaleElementReferenceException ignored) {
			}
		}

		System.out.println("Available reasons: " + availableReasons);

		if (availableReasons.isEmpty()) {
			throw new IllegalStateException("No valid Never Eye Exam options found.");
		}

		// Random selection
		String selectedReason = availableReasons.get(ThreadLocalRandom.current().nextInt(availableReasons.size()));

		System.out.println("Trying to select: " + selectedReason);

		// Fresh element lookup
		By selectedLocator = AppiumBy.xpath("//*[@content-desc=" + xpathLiteral(selectedReason) + "]");

		WebElement selectedOption = wait.until(ExpectedConditions.elementToBeClickable(selectedLocator));

		selectedOption.click();

		System.out.println("Never Eye Exam reason selected: " + selectedReason);

		// Apply button
		By applyButton = AppiumBy.accessibilityId("Apply");

		WebElement apply = wait.until(ExpectedConditions.elementToBeClickable(applyButton));

		apply.click();

		System.out.println("Never Eye Exam Apply clicked successfully.");
	}

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

	public void clickCompleteQuestionnaire() {

		// Pehle check: button already visible hai ya nahi
		List<WebElement> existing = driver.findElements(completeQuestionnaireButton);
		boolean found = !existing.isEmpty() && existing.get(0).isDisplayed();

		// Nahi mila to scroll karo
		if (!found) {
			Dimension screen = driver.manage().window().getSize();

			Map<String, Object> scrollArgs = new HashMap<>();
			scrollArgs.put("left", 30);
			scrollArgs.put("top", 200);
			scrollArgs.put("width", screen.getWidth() - 60);
			scrollArgs.put("height", screen.getHeight() - 400);
			scrollArgs.put("direction", "down");
			scrollArgs.put("percent", 0.6);

			for (int i = 0; i < 10; i++) {

				List<WebElement> buttons = driver.findElements(completeQuestionnaireButton);

				if (!buttons.isEmpty() && buttons.get(0).isDisplayed()) {
					found = true;
					break;
				}

				Object canScrollMore = ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", scrollArgs);

				// false matlab page aur scroll nahi ho sakta
				if (!Boolean.TRUE.equals(canScrollMore)) {
					break;
				}
			}

			// Aakhri scroll ke baad ek baar aur check
			if (!found && driver.findElements(completeQuestionnaireButton).isEmpty()) {
				throw new NoSuchElementException("Complete Questionnaire button scroll ke baad bhi nahi mila.");
			}
		}

		wait.until(ExpectedConditions.elementToBeClickable(completeQuestionnaireButton)).click();

		System.out.println("Complete Questionnaire clicked successfully!");
	}

}