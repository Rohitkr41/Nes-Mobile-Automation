//package pages;
//
//import java.time.Duration;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import io.appium.java_client.AppiumBy;
//import io.appium.java_client.android.AndroidDriver;
//
//public class AddMember {
//
//    private AndroidDriver driver;
//    private WebDriverWait wait;
//
//    // ============================================
//    // Constructor
//    // ============================================
//
//    public AddMember(AndroidDriver driver) {
//
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//    }
//
//    // ============================================
//    // Continue Status Household
//    // ============================================
//
//    private By continueStatusHousehold = AppiumBy.xpath(
//            "//android.view.View[contains(@content-desc,'Status: Continue')]"
//    );
//
//    // ============================================
//    // Add Member
//    // ============================================
//
//    private By addMemberButton = AppiumBy.accessibilityId("Add Member");
//
//    // ============================================
//    // Click Continue Status Household
//    // ============================================
//
//    public void clickContinueStatusHousehold() {
//
//        WebElement household = wait.until(
//                ExpectedConditions.elementToBeClickable(continueStatusHousehold)
//        );
//
//        household.click();
//
//        System.out.println("Continue status household clicked successfully!");
//    }
//
//    // ============================================
//    // Click Add Member
//    // ============================================
//
//    public void clickAddMember() {
//
//        WebElement addMember = wait.until(
//                ExpectedConditions.elementToBeClickable(addMemberButton)
//        );
//
//        addMember.click();
//
//        System.out.println("Add Member clicked successfully!");
//    }
//}

package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AddMember {

	private AndroidDriver driver;
	private WebDriverWait wait;
	private Random random = new Random();

	// ============================================
	// Constructor
	// ============================================

	public AddMember(AndroidDriver driver) {

		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// ============================================
	// Continue Status Household
	// ============================================

	private By continueStatusHousehold = AppiumBy
			.xpath("//android.view.View[contains(@content-desc,'Status: Continue')]");

	// ============================================
	// Add Member
	// ============================================

	private By addMemberButton = AppiumBy.accessibilityId("Add Member");

	// ============================================
	// National ID
	// EditText Instance 0
	// ============================================

	private By nationalIdField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(8)");

	// ============================================
	// First Name
	// EditText Instance 1
	// ============================================

	private By firstNameField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");

	// ============================================
	// Middle Name
	// EditText Instance 2
	// ============================================

	private By middleNameField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(2)");

	// ============================================
	// Last Name
	// EditText Instance 3
	// ============================================

	private By lastNameField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(3)");

	// ============================================
	// Date Of Birth
	// EditText Instance 4
	// ============================================

	private By dobField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(4)");

	// ============================================
	// Primary Phone
	// EditText Instance 5
	// ============================================

	private By primaryPhoneField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(5)");

	// ============================================
	// Male Checkbox
	// ============================================

	private By maleCheckbox = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.CheckBox\").instance(0)");

//============================================
//Relation To Household Head
//============================================

	private By relationToHouseholdHead = AppiumBy
			.xpath("//android.view.View[contains(@content-desc,'Relation to household head')]");

//============================================
//Male Allowed Relations
//============================================

	private String[] maleRelations = { "Son", "Brother", "Niece", "Nephew" };

	// ============================================
	// Click Continue Status Household
	// ============================================

	public void clickContinueStatusHousehold() {

		WebElement household = wait.until(ExpectedConditions.elementToBeClickable(continueStatusHousehold));

		household.click();

		System.out.println("Continue status household clicked successfully!");
	}

	// ============================================
	// Click Add Member
	// ============================================

	public void clickAddMember() {

		WebElement addMember = wait.until(ExpectedConditions.elementToBeClickable(addMemberButton));

		addMember.click();

		System.out.println("Add Member clicked successfully!");
	}

	// ============================================
	// Enter National ID
	// Starts With 1
	// Minimum 1 - Maximum 10 Digits
	// ============================================

	public void enterNationalId() {

		// Generate random length between 1 and 10
		int length = random.nextInt(10) + 1;

		StringBuilder nationalId = new StringBuilder();

		// First digit must be 1
		nationalId.append("1");

		// Remaining digits
		for (int i = 1; i < length; i++) {

			nationalId.append(random.nextInt(10));
		}

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(nationalIdField));

		field.click();
		field.clear();
		field.sendKeys(nationalId.toString());

		System.out.println("National ID entered: " + nationalId);
	}

	// ============================================
	// Enter First Name
	// ============================================

	public void enterFirstName(String firstName) {

		if (firstName == null || firstName.trim().isEmpty()) {

			throw new IllegalArgumentException("First Name cannot be empty!");
		}

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(firstNameField));

		field.click();
		field.clear();
		field.sendKeys(firstName);

		System.out.println("First Name entered: " + firstName);
	}

	// ============================================
	// Enter Last Name
	// ============================================

	public void enterLastName(String lastName) {

		if (lastName == null || lastName.trim().isEmpty()) {

			throw new IllegalArgumentException("Last Name cannot be empty!");
		}

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(lastNameField));

		field.click();
		field.clear();
		field.sendKeys(lastName);

		System.out.println("Last Name entered: " + lastName);
	}

	// ============================================
	// Generate DOB
	// Age Between 5 - 15 Years
	// ============================================

	public void enterRandomDob() {

		// Random age between 5 and 15
		int age = random.nextInt(11) + 5;

		LocalDate today = LocalDate.now();

		// Random date between 5 and 15 years ago
		LocalDate dob = today.minusYears(age);

		String formattedDob = dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(dobField));

		field.click();
		field.clear();
		field.sendKeys(formattedDob);

		System.out.println("DOB entered: " + formattedDob + " | Age: " + age);
	}

	// ============================================
	// Enter Primary Mobile
	// Starts With 05
	// Exactly 10 Digits
	// ============================================

	public void enterPrimaryMobile() {

		StringBuilder mobileNumber = new StringBuilder();

		// Must start with 05
		mobileNumber.append("05");

		// Remaining 8 digits
		for (int i = 0; i < 8; i++) {

			mobileNumber.append(random.nextInt(10));
		}

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(primaryPhoneField));

		field.click();
		field.clear();
		field.sendKeys(mobileNumber.toString());

		System.out.println("Primary Mobile entered: " + mobileNumber);
	}

	// ============================================
	// Select Male
	// ============================================

	public void selectMale() {

		WebElement male = wait.until(ExpectedConditions.elementToBeClickable(maleCheckbox));

		male.click();

		System.out.println("Male selected successfully!");
	}

//============================================
//Select Relation For Male
//============================================

	public void selectMaleRelation() {

		// Open Relation dropdown
		WebElement relationDropdown = wait.until(ExpectedConditions.elementToBeClickable(relationToHouseholdHead));

		relationDropdown.click();

		System.out.println("Relation to household head dropdown opened!");

		// Randomly select one allowed relation
		String selectedRelation = maleRelations[random.nextInt(maleRelations.length)];

		System.out.println("Trying to select male relation: " + selectedRelation);

		// Find selected relation
		By relationOption = AppiumBy.xpath("//*[@content-desc='" + selectedRelation + "']");

		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(relationOption));

		option.click();

		System.out.println("Male relation selected successfully: " + selectedRelation);
	}

}
