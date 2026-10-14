package pages;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class ReferralPage {

	private final AndroidDriver driver;
	private final WebDriverWait wait;

	// Patient dhoondne + scroll + poora card visible hone ka logic Questionnaire me hai
	private final Questionnaire memberList;

	private static final String REFERRAL_BUTTON = "Referral";

	private final By hospitalRadioButton = AppiumBy.accessibilityId("Hospital");

	// content-desc "Center Select" ya "Center\nSelect" dono match honge
	private final By centerDropdown = AppiumBy
			.xpath("//*[starts-with(@content-desc, 'Center') and contains(@content-desc, 'Select')]");

	// Date & time ka field
	private final By dateTimeField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(19)");

	private final By datePickerTitle = AppiumBy
			.androidUIAutomator("new UiSelector().descriptionContains(\"Select date\")");

	// Calendar me aaj ki date ("Tuesday, October 13, 2026, Today")
	private final By todayDate = AppiumBy
			.androidUIAutomator("new UiSelector().descriptionContains(\"Today\")");

	private final By saveAndExitButton = AppiumBy.accessibilityId("Save & Exit");

	private final By okButton = AppiumBy.accessibilityId("OK");

	private final By switchToTextInput = AppiumBy.accessibilityId("Switch to text input mode");

	private final By hourInput = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");

	private final By minuteInput = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");

	// Dropdown ke options (content-desc exactly jaise app me hain)
	private static final String[] CENTERS = {
			"Saudi medical center",
			"All india institute of medical sciences - delhi",
			"Saudi national hospital",
			"Saudi charitable hospital",
			"Saudi memorial hospital",
			"King abdullah medical city"
	};

	public ReferralPage(AndroidDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		this.memberList = new Questionnaire(driver);
	}

	/**
	 * Patient ke naam se uske card ka "Referral" button click karta hai.
	 *
	 * 1) Patient ka poora card (Summary tak) screen par ho -> click
	 * 2) Nahi -> neeche scroll karke dhoondo
	 * 3) Neeche nahi mila -> upar scroll karke dhoondo
	 */
	public void openMemberReferral(String patientName) {
		memberList.openMemberSection(patientName, REFERRAL_BUTTON);
	}

	public void clickHospital() {

		wait.until(ExpectedConditions.visibilityOfElementLocated(hospitalRadioButton));

		WebElement hospital = wait.until(ExpectedConditions.elementToBeClickable(hospitalRadioButton));
		hospital.click();

		System.out.println("Hospital radio button clicked.");
	}

	// Center dropdown kholo aur list me se koi ek random center select karo
	public void selectRandomCenter() {

		openCenterDropdown();

		// Options me se jo screen par dikh rahe hain unki list banao
		List<WebElement> options = wait.until(d -> {
			List<WebElement> visible = new ArrayList<>();

			for (String center : CENTERS) {
				List<WebElement> found = d.findElements(AppiumBy.accessibilityId(center));
				if (!found.isEmpty() && found.get(0).isDisplayed()) {
					visible.add(found.get(0));
				}
			}
			return visible.isEmpty() ? null : visible;
		});

		WebElement selected = options.get(ThreadLocalRandom.current().nextInt(options.size()));
		String centerName = selected.getAttribute("content-desc");

		wait.until(ExpectedConditions.elementToBeClickable(selected)).click();

		System.out.println("Center selected: " + centerName);
	}

	// Naam se specific center select karne ke liye
	public void selectCenter(String centerName) {

		openCenterDropdown();

		By option = AppiumBy.accessibilityId(centerName);

		wait.until(ExpectedConditions.elementToBeClickable(option)).click();

		System.out.println("Center selected: " + centerName);
	}

	private void openCenterDropdown() {

		try {
			WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(centerDropdown));
			dropdown.click();

		} catch (org.openqa.selenium.TimeoutException e) {
			// Debug: screen par "Center" wale saare content-desc print karo
			System.out.println("Center dropdown nahi mila. Screen par 'Center' wale elements:");
			for (WebElement el : driver.findElements(AppiumBy.xpath("//*[contains(@content-desc, 'enter')]"))) {
				System.out.println(" -> [" + el.getAttribute("content-desc").replace("\n", "\\n") + "]");
			}
			throw e;
		}

		System.out.println("Center dropdown clicked.");
	}

	// =====================================================
	// Date & Time: pehle aaj ki date, phir current time (hour, phir minute)
	// =====================================================
	public void selectCurrentDateAndTime() {

		// Device ka current time (emulator/phone ka), nahi mila to system time
		LocalTime now = getDeviceTime();
		int hour = now.getHour();
		int minute = now.getMinute();

		// 1) Date/time field kholo
		wait.until(ExpectedConditions.elementToBeClickable(dateTimeField)).click();
		System.out.println("Date & time field clicked.");

		// 2) Date picker: aaj ki date select karo
		wait.until(ExpectedConditions.visibilityOfElementLocated(datePickerTitle));
		wait.until(ExpectedConditions.elementToBeClickable(todayDate)).click();
		System.out.println("Today's date selected.");

		wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
		System.out.println("Date OK clicked.");

		// 3) Time picker: text input mode me jao (dial par hour/minute click se zyada stable)
		wait.until(ExpectedConditions.elementToBeClickable(switchToTextInput)).click();
		System.out.println("Switched to text input mode.");

		// Device 12-hour format me ho to AM/PM buttons dikhte hain
		boolean is12Hour = !driver.findElements(AppiumBy.accessibilityId("AM")).isEmpty()
				|| !driver.findElements(AppiumBy.accessibilityId("PM")).isEmpty();

		int hourToEnter = hour;

		if (is12Hour) {
			hourToEnter = (hour % 12 == 0) ? 12 : hour % 12;
		}

		// Pehle hour
		enterValue(hourInput, String.format("%02d", hourToEnter));
		System.out.println("Hour entered: " + hourToEnter);

		// Phir minute
		enterValue(minuteInput, String.format("%02d", minute));
		System.out.println("Minute entered: " + minute);

		// AM/PM select karo (sirf 12-hour format me)
		if (is12Hour) {
			String period = hour >= 12 ? "PM" : "AM";
			wait.until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(period))).click();
			System.out.println("Period selected: " + period);
		}

		wait.until(ExpectedConditions.elementToBeClickable(okButton)).click();
		System.out.println("Time OK clicked. Time set: " + String.format("%02d:%02d", hour, minute));
	}

	private void enterValue(By locator, String value) {
		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(locator));
		field.click();
		field.clear();
		field.sendKeys(value);
	}

	private LocalTime getDeviceTime() {
		try {
			String time = driver.getDeviceTime("HH:mm"); // e.g. "14:35"
			String[] parts = time.trim().split(":");
			return LocalTime.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		} catch (Exception e) {
			System.out.println("Device time nahi mila, system time use ho raha hai.");
			return LocalTime.now();
		}
	}

	public void clickSaveAndExit() {

		wait.until(ExpectedConditions.visibilityOfElementLocated(saveAndExitButton));

		WebElement saveAndExit = wait.until(ExpectedConditions.elementToBeClickable(saveAndExitButton));
		saveAndExit.click();

		System.out.println("Save & Exit clicked.");
	}
}