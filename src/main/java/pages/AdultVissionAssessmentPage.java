package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AdultVissionAssessmentPage {
	private final AndroidDriver driver;
	private final WebDriverWait wait;

	private final By yesButton = AppiumBy.accessibilityId("Yes");

	private final By wearingyesButton = AppiumBy
			.androidUIAutomator("new UiSelector().description(\"Yes\").instance(1)");

	// content-desc real device par "-\nSelect" aata hai, isliye normalize-space
	private static final String SELECT_FIELDS = "//*[@content-desc and normalize-space(@content-desc)='- Select']";

	// Duration dropdown ke options
	private static final By DURATION_OPTIONS = AppiumBy
			.xpath("//android.widget.Button[@content-desc='Less than 1 year' " + "or @content-desc='1-2 years' "
					+ "or @content-desc='3-5 years' " + "or @content-desc='More than 5 years' "
					+ "or @content-desc=\"Don't know\"]");

	// Acuity options, jaise "0.40 (20/50)", "1.60 (CF)", "3.30 (NLP)"
	private static final By ACUITY_OPTIONS = AppiumBy
			.xpath("//android.view.View[@content-desc and contains(@content-desc, '(')]");

	public AdultVissionAssessmentPage(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	public void openMemberVisionAssessment(String patientName) {
		By visionAssessmentButton = AppiumBy.xpath("//*[contains(@content-desc, '" + patientName + "')]"
				+ "/ancestor::*[.//*[@content-desc='Vision Assessment']][1]"
				+ "//*[@content-desc='Vision Assessment']");

		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(visionAssessmentButton));

		button.click();
		System.out.println("Vision Assessment opened for: " + patientName);
	}

	public void clickYes() {
		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(yesButton));

		button.click();
		System.out.println("Yes clicked on Vision Assessment page.");
	}

	public void clickWearingGlassYes() {
		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(wearingyesButton));

		button.click();

		System.out.println("Wearing Glass - Yes clicked.");
	}

	public void clickNearCorrectionDropdown() {

		// Near Correction ke label ke just baad wala dropdown
		By nearCorrectionDropdown = AppiumBy.xpath("//*[@content-desc='Near correction — time since obtained']"
				+ "/following::*[@content-desc and contains(@content-desc, 'Select')][1]");

		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(nearCorrectionDropdown));

		dropdown.click();
		System.out.println("Near Correction dropdown clicked.");

		// Isi dropdown ke open hone par available options
		By optionsLocator = AppiumBy.xpath("//android.widget.Button[@content-desc='Less than 1 year' or "
				+ "@content-desc='1-2 years' or " + "@content-desc='3-5 years' or "
				+ "@content-desc='More than 5 years' or " + "@content-desc=\"Don't know\"]");

		List<WebElement> options = wait.until(driver -> {
			List<WebElement> availableOptions = new ArrayList<>();

			for (WebElement option : driver.findElements(optionsLocator)) {
				if (option.isDisplayed() && option.isEnabled()) {
					availableOptions.add(option);
				}
			}

			return availableOptions.isEmpty() ? null : availableOptions;
		});

		// Random option select
		WebElement selectedOption = options.get(ThreadLocalRandom.current().nextInt(options.size()));

		String selectedValue = selectedOption.getAttribute("content-desc");
		selectedOption.click();

		System.out.println("Near Correction duration selected: " + selectedValue);
	}

	public void selectRandomDuration() {
		By dropdown = AppiumBy.xpath("(" + SELECT_FIELDS + ")[1]");

		wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
		System.out.println("Duration dropdown clicked.");

		List<WebElement> options = wait.until(d -> {
			List<WebElement> result = new ArrayList<>();
			for (WebElement option : d.findElements(DURATION_OPTIONS)) {
				if (option.isDisplayed() && option.isEnabled()) {
					result.add(option);
				}
			}
			return result.isEmpty() ? null : result;
		});

		WebElement selected = options.get(ThreadLocalRandom.current().nextInt(options.size()));

		String selectedValue = selected.getAttribute("content-desc");
		selected.click();
		pause(500);

		System.out.println("Duration selected: " + selectedValue);
	}

	// Presenting OD = second-last "- Select" (OS abhi bhi "- Select" hai)
	public void selectRandomPresentingOd() {
		selectRandomAcuityValue("(" + SELECT_FIELDS + ")[last()-1]", "Presenting OD");
	}

	// Presenting OS = last "- Select" (OD pehle select ho chuka hona chahiye)
	public void selectRandomPresentingOs() {
		selectRandomAcuityValue("(" + SELECT_FIELDS + ")[last()]", "Presenting OS");
		scrollToBottom();
	}

	public void clickSaveAndExit() {
		By saveAndExitButton = AppiumBy.accessibilityId("Save & Exit");

		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(saveAndExitButton));

		button.click();
		System.out.println("Save & Exit clicked.");
	}

	public void clickOkOnPopup() {
		By okButton = AppiumBy.accessibilityId("OK");

		WebElement button = wait.until(ExpectedConditions.elementToBeClickable(okButton));

		button.click();
		System.out.println("Popup par OK clicked.");
	}

	// ---------------------------------------------------------------------
	// Helpers
	// ---------------------------------------------------------------------

	private void selectRandomAcuityValue(String fieldXpath, String label) {
		By field = AppiumBy.xpath(fieldXpath);

		wait.until(ExpectedConditions.elementToBeClickable(field)).click();
		System.out.println(label + " dropdown clicked.");

		List<WebElement> validOptions = wait.until(d -> {
			List<WebElement> result = new ArrayList<>();
			for (WebElement el : d.findElements(ACUITY_OPTIONS)) {
				if (isValueAbove030(el.getAttribute("content-desc"))) {
					result.add(el);
				}
			}
			return result.isEmpty() ? null : result;
		});

		WebElement selected = validOptions.get(ThreadLocalRandom.current().nextInt(validOptions.size()));

		String selectedValue = selected.getAttribute("content-desc");
		selected.click();
		pause(500);

		System.out.println(label + " value selected: " + selectedValue);
	}

	// "0.40 (20/50)" -> 0.40 > 0.30
	private boolean isValueAbove030(String desc) {
		if (desc == null) {
			return false;
		}
		try {
			String first = desc.trim().split("\\s+")[0];
			return Double.parseDouble(first) > 0.30;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void pause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void scrollToBottom() {
		try {
			boolean canScrollMore;

			do {
				canScrollMore = (Boolean) driver.executeScript("mobile: scrollGesture", java.util.Map.of("left", 50,
						"top", 200, "width", 900, "height", 1500, "direction", "down", "percent", 0.75));

			} while (canScrollMore);

			System.out.println("Page scrolled to bottom successfully.");

		} catch (Exception e) {
			System.out.println("Scroll to bottom failed: " + e.getMessage());
			throw e;
		}
	}

	// Near Presenting dropdown se random value select karna
	public void selectNVARandomNearPresenting() {

		// Near Vision section mein second "- Select" dropdown:
		// 1st = Near uncorrected
		// 2nd = Near presenting
		By nearPresentingDropdown = AppiumBy
				.xpath("(//*[@content-desc and normalize-space(@content-desc)='- Select'])[last()]");

		// Dropdown click karo
		wait.until(ExpectedConditions.elementToBeClickable(nearPresentingDropdown)).click();

		System.out.println("Near presenting dropdown clicked.");

		// N6 se N48 tak options
		By nearOptions = AppiumBy.xpath("//*[@content-desc and " + "normalize-space(@content-desc)='N6' or "
				+ "normalize-space(@content-desc)='N8' or " + "normalize-space(@content-desc)='N10' or "
				+ "normalize-space(@content-desc)='N12' or " + "normalize-space(@content-desc)='N14' or "
				+ "normalize-space(@content-desc)='N18' or " + "normalize-space(@content-desc)='N24' or "
				+ "normalize-space(@content-desc)='N36' or " + "normalize-space(@content-desc)='N48']");

		List<WebElement> options = wait.until(d -> {
			List<WebElement> result = new ArrayList<>();

			for (WebElement el : d.findElements(nearOptions)) {
				if (el.isDisplayed() && el.isEnabled()) {
					result.add(el);
				}
			}

			return result.isEmpty() ? null : result;
		});

		WebElement selected = options.get(ThreadLocalRandom.current().nextInt(options.size()));

		String selectedValue = selected.getAttribute("content-desc");
		selected.click();
		pause(500);

		System.out.println("Near presenting selected: " + selectedValue);
	}

	public void debugSelectDropdowns() {

		List<WebElement> dropdowns = driver
				.findElements(AppiumBy.xpath("//*[@content-desc and contains(@content-desc, 'Select')]"));

		System.out.println("Total dropdowns found: " + dropdowns.size());

		for (int i = 0; i < dropdowns.size(); i++) {
			String desc = dropdowns.get(i).getAttribute("content-desc");

			System.out.println("Dropdown index " + i + " = [" + desc + "]");
		}
	}
}
