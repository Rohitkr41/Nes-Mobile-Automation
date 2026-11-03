package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import java.util.Map;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.remote.RemoteWebElement;

public class Sociodemographic {

	private final AndroidDriver driver;
	private final WebDriverWait wait;

//	private final By sociodemographicSection = AppiumBy
//			.androidUIAutomator("new UiSelector().descriptionContains(\"Sociodemographic Status\")");
	private final By sociodemographicSection = AppiumBy
			.androidUIAutomator("new UiSelector().descriptionContains(\"Sociodemographic Status\")");

	public Sociodemographic(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	}

	private final By yearOfEducationField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");

	private final By highestSchoolEducationDropdown = AppiumBy
			.androidUIAutomator("new UiSelector().descriptionContains(\"Highest School Education\")");
	private final By NoneyearOfEducationField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");

	private By nearWorkDurationDropdown = AppiumBy.accessibilityId("Near Work Duration\nSelect");

	private By timeOutdoorDurationDropdown = AppiumBy.accessibilityId("Time Outdoor Duration\nSelect");

	private final By saveSociodemographicButton = AppiumBy.accessibilityId("Save Sociodemographic");

	// methods start here

	private void waitForLoaderToDisappear() {

    By loader = AppiumBy.className("android.widget.ProgressBar");

    try {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(loader));
    } catch (TimeoutException e) {
        System.out.println("Loader is still visible or did not disappear.");
        throw e;
    }
}

	private boolean isSociodemographicOpen() {

		List<WebElement> section = driver.findElements(sociodemographicSection);

		if (!section.isEmpty()) {
			String desc = section.get(0).getAttribute("content-desc");
			if (desc != null && desc.contains("Expanded")) {
				return true;
			}
		}

		// Form ka pehla field (Year of Education) dikh raha ho to bhi khula hua hai
		return !driver.findElements(yearOfEducationField).isEmpty();
	}

	public boolean clickSociodemographic() {

		waitForLoaderToDisappear();

		// Wait until Sociodemographic section is visible
		WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(sociodemographicSection));

		for (int attempt = 1; attempt <= 3; attempt++) {

			// Get latest element to avoid stale element reference
			section = wait.until(ExpectedConditions.presenceOfElementLocated(sociodemographicSection));

			String desc = section.getAttribute("content-desc");

			System.out.println("Sociodemographic description: " + desc);

			if (desc != null && desc.contains("Expanded")) {
				System.out.println("Sociodemographic already expanded.");
				return true;
			}

			if (desc != null && desc.contains("Pending")) {

				System.out.println("Sociodemographic is Pending. Clicking...");

				try {
					wait.until(ExpectedConditions.elementToBeClickable(sociodemographicSection)).click();

				} catch (Exception e) {

					System.out.println("Normal click failed. Trying gesture click.");

					section = driver.findElement(sociodemographicSection);

					driver.executeScript("mobile: clickGesture",
							Map.of("elementId", ((RemoteWebElement) section).getId()));
				}

				// Wait for form fields to become visible
				try {

					wait.until(ExpectedConditions.visibilityOfElementLocated(yearOfEducationField));

					System.out.println("Sociodemographic section opened successfully.");

					return true;

				} catch (TimeoutException e) {

					System.out.println("Section did not open. Attempt: " + attempt);

					waitForLoaderToDisappear();
				}

			} else {

				// Check whether the form is already open
				if (!driver.findElements(yearOfEducationField).isEmpty()) {
					System.out.println("Sociodemographic form already open.");
					return true;
				}

				System.out.println("Unexpected Sociodemographic description: " + desc);
			}
		}

		throw new org.openqa.selenium.ElementNotInteractableException(
				"Sociodemographic section could not be opened after 3 attempts.");
	}

	private void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void enterRandomYearOfEducation(int patientAge) {
		if (patientAge < 0) {
			throw new IllegalArgumentException("Patient age cannot be negative.");
		}

		int years = java.util.concurrent.ThreadLocalRandom.current().nextInt(patientAge + 1);

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(yearOfEducationField));

		field.click();
		field.clear();
		field.sendKeys(String.valueOf(years));

		System.out.println("Year of education entered: " + years + " (patient age: " + patientAge + ")");
	}

	public void selectReadAndWrite() {
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(highestSchoolEducationDropdown));
		dropdown.click();

		By readAndWriteOption = AppiumBy.xpath("//*[@text='Read and write' or @content-desc='Read and write']");

		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(readAndWriteOption));
		option.click();

		System.out.println("Highest School Education selected: Read and write");
	}

	public void RandomNoneYearOfEducation(int patientAge) {
		if (patientAge < 0) {
			throw new IllegalArgumentException("Patient age cannot be negative.");
		}

		int years = java.util.concurrent.ThreadLocalRandom.current().nextInt(patientAge + 1);

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(NoneyearOfEducationField));

		field.click();
		field.clear();
		field.sendKeys(String.valueOf(years));

		System.out.println("None Formal Year of education entered: " + years + " (patient age: " + patientAge + ")");
	}

	public void clickSaveSociodemographic() {
		WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(saveSociodemographicButton));

		saveButton.click();
		System.out.println("Save Sociodemographic clicked.");
	}

	public void selectRandomNearWorkDuration() {

		// Locate and click Near Work Duration dropdown
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(nearWorkDurationDropdown));

		dropdown.click();

		System.out.println("Near Work Duration dropdown clicked.");

		// Do not use XPath matches() because XPath 1.0 does not support it
		By durationButtons = AppiumBy.xpath("//android.widget.Button[@content-desc]");

		// Wait until valid HH:mm options appear
		List<WebElement> timeOptions = wait.until(d -> {

			List<WebElement> buttons = d.findElements(durationButtons);

			List<WebElement> validOptions = new java.util.ArrayList<>();

			for (WebElement button : buttons) {

				String time = button.getAttribute("content-desc");

				if (time != null && time.trim().matches("\\d{2}:\\d{2}")) {

					validOptions.add(button);
				}
			}

			return validOptions.isEmpty() ? null : validOptions;
		});

		// Select a random duration
		WebElement selectedOption = timeOptions
				.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(timeOptions.size()));

		String selectedTime = selectedOption.getAttribute("content-desc");

		selectedOption.click();

		System.out.println("Near Work Duration selected: " + selectedTime);
	}

	public void selectRandomTimeOutdoorDuration() {

		// Dropdown locate and click
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(timeOutdoorDurationDropdown));

		dropdown.click();

		System.out.println("Time Outdoor Duration dropdown clicked.");

		// HH:MM options locate karo
		By durationButtons = AppiumBy.xpath("//android.widget.Button[contains(@content-desc, ':')]");

		List<WebElement> timeOptions = wait.until(driver -> {

			List<WebElement> buttons = driver.findElements(durationButtons);

			List<WebElement> validOptions = new java.util.ArrayList<>();

			for (WebElement button : buttons) {

				String time = button.getAttribute("content-desc");

				if (time != null && time.matches("\\d{2}:\\d{2}")) {
					validOptions.add(button);
				}
			}

			return validOptions.isEmpty() ? null : validOptions;
		});

		// Random duration select karo
		WebElement selectedOption = timeOptions
				.get(java.util.concurrent.ThreadLocalRandom.current().nextInt(timeOptions.size()));

		String selectedTime = selectedOption.getAttribute("content-desc");

		selectedOption.click();

		System.out.println("Time Outdoor Duration selected: " + selectedTime);
	}

}