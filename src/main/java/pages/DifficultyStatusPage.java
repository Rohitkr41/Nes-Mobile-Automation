package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.NoSuchElementException;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class DifficultyStatusPage {

	private final AndroidDriver driver;
	private final WebDriverWait wait;

	private final By difficultyStatusPending = AppiumBy
			.androidUIAutomator("new UiSelector().description(\"Difficulty Status Pending, Collapsed\")");

	private final By difficultyStatusAny = AppiumBy
			.androidUIAutomator("new UiSelector().descriptionStartsWith(\"Difficulty Status\")");

	// Pehla "Select" dropdown (jab tak select nahi hota, uska content-desc "Select"
	// rehta hai)
	private final By selectDropdown = AppiumBy
			.androidUIAutomator("new UiSelector().description(\"Select\").instance(0)");

	private final By saveDifficultyButton = AppiumBy.accessibilityId("Save Difficulty");

	// Dropdown ke options (android.view.MenuItem)
	private final By difficultyOptions = AppiumBy
			.xpath("//*[@content-desc='No difficulty' " + "or @content-desc='Yes, some difficulty' "
					+ "or @content-desc='Yes, a lot of difficulty' " + "or @content-desc='Cannot do at all']");

	public DifficultyStatusPage(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// ============================================
	// Click Difficulty Status
	// ============================================

	public void clickDifficultyStatus() {

		By locator = difficultyStatusPending;

		List<WebElement> exact = driver.findElements(difficultyStatusPending);
		if (exact.isEmpty()) {
			locator = difficultyStatusAny;
		}

		WebElement section = wait.until(ExpectedConditions.elementToBeClickable(locator));

		section.click();

		System.out.println("Difficulty Status clicked successfully!");
	}

	// ============================================
	// Pehle "Select" dropdown kholo aur
	// koi ek option random select karo
	// ============================================

	public void selectRandomDifficulty() {

		// Open dropdown
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(selectDropdown));

		dropdown.click();

		System.out.println("Difficulty dropdown opened!");

		// Actual available options
		List<String> validValues = Arrays.asList("No difficulty", "Yes, some difficulty", "Yes, a lot of difficulty",
				"Cannot do at all");

		// Wait for actual options
		wait.until(d -> {

			for (String value : validValues) {

				List<WebElement> elements = d
						.findElements(AppiumBy.xpath("//*[@content-desc=" + xpathLiteral(value) + "]"));

				for (WebElement element : elements) {
					try {
						if (element.isDisplayed() && element.isEnabled()) {
							return true;
						}
					} catch (StaleElementReferenceException ignored) {
					}
				}
			}

			return false;
		});

		// Select random option
		List<String> availableOptions = new ArrayList<>();

		for (String value : validValues) {

			List<WebElement> elements = driver
					.findElements(AppiumBy.xpath("//*[@content-desc=" + xpathLiteral(value) + "]"));

			for (WebElement element : elements) {
				try {
					if (element.isDisplayed() && element.isEnabled()) {
						availableOptions.add(value);
						break;
					}
				} catch (StaleElementReferenceException ignored) {
				}
			}
		}

		if (availableOptions.isEmpty()) {
			throw new IllegalStateException("No Difficulty options found in dropdown.");
		}

		System.out.println("Available Difficulty options: " + availableOptions);

		String selectedValue = availableOptions.get(ThreadLocalRandom.current().nextInt(availableOptions.size()));

		// Fresh element lookup before click
		By selectedLocator = AppiumBy.xpath("//*[@content-desc=" + xpathLiteral(selectedValue) + "]");

		WebElement selectedOption = wait.until(ExpectedConditions.elementToBeClickable(selectedLocator));

		selectedOption.click();

		System.out.println("Difficulty selected: " + selectedValue);
	}

// XPath helper
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

	public void scrollAndClickSaveDifficulty() {

		Dimension screen = driver.manage().window().getSize();

		Map<String, Object> scrollArgs = new HashMap<>();
		scrollArgs.put("left", 30);
		scrollArgs.put("top", 200);
		scrollArgs.put("width", screen.getWidth() - 60);
		scrollArgs.put("height", screen.getHeight() - 400);
		scrollArgs.put("direction", "down");
		scrollArgs.put("percent", 0.6);

		boolean found = false;

		for (int i = 0; i < 10; i++) {

			List<WebElement> buttons = driver.findElements(saveDifficultyButton);

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
		if (!found && driver.findElements(saveDifficultyButton).isEmpty()) {
			throw new NoSuchElementException("Save Difficulty button scroll ke baad bhi nahi mila.");
		}

		wait.until(ExpectedConditions.elementToBeClickable(saveDifficultyButton)).click();

		System.out.println("Save Difficulty clicked successfully!");
	}
}