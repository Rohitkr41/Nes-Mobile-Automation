package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AddMemberAdult extends AddMember {

	private final AndroidDriver adultDriver;
	private final WebDriverWait adultWait;
	private final Random adultRandom = new Random();

	// ============================================
	// Locators (AddMember wale same)
	// ============================================

	private final By nationalIdField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");

	private final By firstNameField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");

	private final By lastNameField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(3)");

	private final By adultDobField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(4)");

	private final By primaryPhoneField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(5)");

	private final By maleCheckbox = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.CheckBox\").instance(0)");

	private final By adultRelationToHouseholdHead = AppiumBy
			.xpath("//android.view.View[contains(@content-desc,'Relation to household head')]");

	private final By adultEmploymentDropdown = AppiumBy
			.androidUIAutomator("new UiSelector().descriptionContains(\"Employment\")");

	// Male adult relations: Husband, Son, Father, Head of household
	private static final String[] ADULT_MALE_RELATION_NAMES = {
			"Husband", "Son", "Father", "HEAD OF HOUSEHOLD"
	};

	// Child, Employed full-time, Employed part-time, Self-employed, Retired ko chhod kar baaki
	private static final String[] ADULT_EMPLOYMENT_OPTIONS = {
			"Student",
			"Homemaker or caregiver",
			"Unemployed and looking for work",
			"Unemployed and not looking for work",
			"Unable to work due to disability or illness"
	};

	public AddMemberAdult(AndroidDriver driver) {
		super(driver);
		this.adultDriver = driver;
		this.adultWait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// ============================================
	// Enter National ID
	// Starts With 1, Exactly 10 Digits
	// ============================================

	@Override
	public void enterNationalId() {

		WebElement field = adultWait.until(ExpectedConditions.elementToBeClickable(nationalIdField));

		StringBuilder nationalId = new StringBuilder();

		// First digit must be 1
		nationalId.append("1");

		// Remaining 9 digits
		for (int i = 1; i < 10; i++) {
			nationalId.append(adultRandom.nextInt(10));
		}

		field.click();
		field.clear();
		field.sendKeys(nationalId.toString());

		System.out.println("National ID entered: " + nationalId);
	}

	// ============================================
	// Enter First Name
	// ============================================

	@Override
	public void enterFirstName(String firstName) {

		if (firstName == null || firstName.trim().isEmpty()) {
			throw new IllegalArgumentException("First Name cannot be empty!");
		}

		WebElement field = adultWait.until(ExpectedConditions.elementToBeClickable(firstNameField));

		field.click();
		field.clear();
		field.sendKeys(firstName);

		System.out.println("First Name entered: " + firstName);
	}

	// ============================================
	// Enter Last Name
	// ============================================

	@Override
	public void enterLastName(String lastName) {

		if (lastName == null || lastName.trim().isEmpty()) {
			throw new IllegalArgumentException("Last Name cannot be empty!");
		}

		WebElement field = adultWait.until(ExpectedConditions.elementToBeClickable(lastNameField));

		field.click();
		field.clear();
		field.sendKeys(lastName);

		System.out.println("Last Name entered: " + lastName);
	}

	// ============================================
	// Generate DOB
	// Age Between 30 - 90 Years
	// ============================================

	public void enterRandomDobAdult() {

		// Random age between 30 and 90
		int age = adultRandom.nextInt(61) + 30;

		LocalDate dob = LocalDate.now().minusYears(age);

		String formattedDob = dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		WebElement field = adultWait.until(ExpectedConditions.elementToBeClickable(adultDobField));

		field.click();
		field.clear();
		field.sendKeys(formattedDob);

		System.out.println("DOB entered: " + formattedDob + " | Age: " + age);
	}

	// ============================================
	// Enter Primary Mobile
	// Starts With 05, Exactly 10 Digits
	// ============================================

	@Override
	public void enterPrimaryMobile() {

		StringBuilder mobileNumber = new StringBuilder();

		// Must start with 05
		mobileNumber.append("05");

		// Remaining 8 digits
		for (int i = 0; i < 8; i++) {
			mobileNumber.append(adultRandom.nextInt(10));
		}

		WebElement field = adultWait.until(ExpectedConditions.elementToBeClickable(primaryPhoneField));

		field.click();
		field.clear();
		field.sendKeys(mobileNumber.toString());

		System.out.println("Primary Mobile entered: " + mobileNumber);
	}

	// ============================================
	// Select Male
	// ============================================

	@Override
	public void selectMale() {

		WebElement male = adultWait.until(ExpectedConditions.elementToBeClickable(maleCheckbox));

		male.click();

		System.out.println("Male selected successfully!");
	}

	// ============================================
	// Select Relation For Adult Male
	// Husband / Son / Father / Head of household
	// ============================================

	public void selectAdultMaleRelation() {

		WebElement relationDropdown = adultWait
				.until(ExpectedConditions.elementToBeClickable(adultRelationToHouseholdHead));

		relationDropdown.click();

		System.out.println("Relation to household head dropdown opened!");

		String selectedRelation = ADULT_MALE_RELATION_NAMES[adultRandom.nextInt(ADULT_MALE_RELATION_NAMES.length)];

		System.out.println("Trying to select male relation: " + selectedRelation);

		By relationOption;

		if (selectedRelation.equals("HEAD OF HOUSEHOLD")) {
			// content-desc multi-line hai: "HEAD OF HOUSEHOLD\nHead of household (self)"
			relationOption = AppiumBy.xpath("//*[starts-with(@content-desc,'HEAD OF HOUSEHOLD')]");
		} else {
			relationOption = AppiumBy.xpath("//*[@content-desc='" + selectedRelation + "']");
		}

		adultWait.until(ExpectedConditions.elementToBeClickable(relationOption)).click();

		System.out.println("Male relation selected successfully: " + selectedRelation);
	}

	// ============================================
	// Select Employment (excluded options chhod kar)
	// ============================================

	public void selectAdultEmployment() {

		WebElement employment = adultWait.until(ExpectedConditions.elementToBeClickable(adultEmploymentDropdown));
		employment.click();

		System.out.println("Employment dropdown opened!");

		String selectedEmployment = ADULT_EMPLOYMENT_OPTIONS[adultRandom.nextInt(ADULT_EMPLOYMENT_OPTIONS.length)];

		System.out.println("Trying to select: " + selectedEmployment);

		By optionLocator = AppiumBy
				.xpath("//*[@text='" + selectedEmployment + "' or @content-desc='" + selectedEmployment + "']");

		adultWait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();

		System.out.println("Employment selected: " + selectedEmployment);

		adultScrollToBottom();
	}

	// AddMember.scrollToBottom() private hai, isliye same logic yahan
	private void adultScrollToBottom() {
		Dimension screen = adultDriver.manage().window().getSize();

		Map<String, Object> args = new HashMap<>();
		args.put("left", 30);
		args.put("top", 200);
		args.put("width", screen.getWidth() - 60);
		args.put("height", screen.getHeight() - 400);
		args.put("direction", "down");
		args.put("percent", 0.8);

		for (int i = 0; i < 10; i++) {
			Object result = ((JavascriptExecutor) adultDriver).executeScript("mobile: scrollGesture", args);

			if (!Boolean.TRUE.equals(result)) {
				break;
			}
		}
	}
}