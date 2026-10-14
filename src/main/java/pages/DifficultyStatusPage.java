package pages;

import java.time.Duration;
import java.util.ArrayList;
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
			.xpath("//android.view.MenuItem[@content-desc='No difficulty' " + "or @content-desc='Yes, some difficulty' "
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

		wait.until(ExpectedConditions.elementToBeClickable(selectDropdown)).click();

		System.out.println("Difficulty dropdown opened!");

		List<WebElement> options = wait.until(d -> {
			List<WebElement> result = new ArrayList<>();
			for (WebElement option : d.findElements(difficultyOptions)) {
				if (option.isDisplayed()) {
					result.add(option);
				}
			}
			return result.isEmpty() ? null : result;
		});

		WebElement selected = options.get(ThreadLocalRandom.current().nextInt(options.size()));

		String selectedValue = selected.getAttribute("content-desc");

		selected.click();

		System.out.println("Difficulty selected: " + selectedValue);
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