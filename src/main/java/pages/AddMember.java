
package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
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
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");

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

	private By dobField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(4)");

	private By primaryPhoneField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(5)");

	private By maleCheckbox = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.CheckBox\").instance(0)");

	private By relationToHouseholdHead = AppiumBy
			.xpath("//android.view.View[contains(@content-desc,'Relation to household head')]");

	private final By memberScreeningStatus = AppiumBy.accessibilityId("Member Screening Status\nSelect");


	private final By employmentDropdown = AppiumBy
			.androidUIAutomator("new UiSelector().descriptionContains(\"Employment\")");

	// ============================================
	// Consent work flow
	// ============================================

	private By openConsentButton = AppiumBy.accessibilityId("Open Consent");

	// ============================================
	// Consent Checkbox
	// ============================================

	private By consentCheckbox = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.CheckBox\")");

	private By consentPersonNameField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");
	// ============================================
	// Relation With Participant
	// ============================================

	private final By relationWithParticipant = AppiumBy
			.androidUIAutomator("new UiSelector().descriptionContains(\"Relation with participant\")");

	// ============================================
	// Participant Person Signature
	// ============================================

	private By participantPersonSignature = AppiumBy.accessibilityId("Participant Person Signature");

	// ============================================
	// Participant Person Signature Canvas
	// ============================================

	private By participantSignatureCanvas = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(9)");

	// ============================================
	// Sign Consent
	// ============================================

//	private By signConsentButton = AppiumBy.accessibilityId("Sign Consent");
	private By signConsentButton = AppiumBy.xpath(
	        "//android.widget.Button[@content-desc='Sign Consent']"
	);
	// ============================================
	// Witness Name
	// ============================================

	private By witnessNameField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");
	
	private By witnessPersonSignature =
	        AppiumBy.accessibilityId("Witness Person Signature");

	private By witnessSignatureCanvas =
	        AppiumBy.androidUIAutomator(
	                "new UiSelector().className(\"android.view.View\").instance(9)"
	        );
	
	private By saveConsentButton = AppiumBy.accessibilityId("Save Consent");
	private By saveButton = AppiumBy.accessibilityId("SAVE");
	
	private By successPopupOkButton = AppiumBy.accessibilityId("OK");
	

	private String[] maleRelations = { "Son" };

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

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(nationalIdField));

		// Generate exactly 10-digit National ID
		StringBuilder nationalId = new StringBuilder();

		// First digit must be 1
		nationalId.append("1");

		// Generate remaining 9 digits
		for (int i = 1; i < 10; i++) {
			nationalId.append(random.nextInt(10));
		}

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

	// ============================================
	// Select Member Screening Status - Present
	// ============================================

	public void selectMemberScreeningPresent() {
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(memberScreeningStatus));
		dropdown.click();

		By presentOption = AppiumBy.androidUIAutomator("new UiSelector().description(\"Present\")");

		wait.until(ExpectedConditions.elementToBeClickable(presentOption)).click();
	}

	public void selectChildOrStudent() {
		WebElement employment = wait.until(ExpectedConditions.elementToBeClickable(employmentDropdown));
		employment.click();

		System.out.println("Employment dropdown opened!");

		String[] options = { "Child", "Student" };
		String selectedEmployment = options[java.util.concurrent.ThreadLocalRandom.current().nextInt(options.length)];

		System.out.println("Trying to select: " + selectedEmployment);

		By optionLocator = AppiumBy
				.xpath("//*[@text='" + selectedEmployment + "' or @content-desc='" + selectedEmployment + "']");

		WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
		option.click();
		System.out.println("Employment selected: " + selectedEmployment);
		scrollToBottom();
	}

	// ============================================
	// Click Open Consent
	// ============================================

	public void clickOpenConsent() {

		WebElement consent = wait.until(ExpectedConditions.elementToBeClickable(openConsentButton));

		consent.click();

		System.out.println("Open Consent clicked successfully!");
	}

	// ============================================
	// Click Consent Checkbox
	// ============================================

	public void clickConsentCheckbox() {

		WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(consentCheckbox));

		checkbox.click();

		System.out.println("Consent checkbox clicked successfully!");
	}

	// ============================================
	// Enter Consent Person Name
	// ============================================

	public void enterConsentPersonName(String personName) {

		if (personName == null || personName.trim().isEmpty()) {
			throw new IllegalArgumentException("Consent Person Name cannot be empty!");
		}

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(consentPersonNameField));

		field.click();
		field.clear();
		field.sendKeys(personName);

		System.out.println("Consent Person Name entered: " + personName);
	}

	// ============================================
	// Allowed Relations
	// ============================================

	private String[] allowedParticipantRelations = { "Father", "Mother"};

	// ============================================
	// Select Allowed Relation
	// ============================================

	public void selectParticipantRelation() {
    Dimension screen = driver.manage().window().getSize();

    Map<String, Object> scrollArgs = new HashMap<>();
    scrollArgs.put("left", 30);
    scrollArgs.put("top", 200);
    scrollArgs.put("width", screen.getWidth() - 60);
    scrollArgs.put("height", screen.getHeight() - 400);
    scrollArgs.put("percent", 0.6);

   
    scrollArgs.put("direction", "up");
    for (int i = 0; i < 10; i++) {
        List<WebElement> fields = driver.findElements(relationWithParticipant);

        if (!fields.isEmpty() && fields.get(0).isDisplayed()) {
            break;
        }

        Object canScrollMore = ((JavascriptExecutor) driver)
                .executeScript("mobile: scrollGesture", scrollArgs);

        if (!Boolean.TRUE.equals(canScrollMore)) {
            break;
        }
    }

    WebElement relationDropdown = wait.until(
            ExpectedConditions.elementToBeClickable(relationWithParticipant)
    );
    relationDropdown.click();

    // Khuli relation list ko uske top par le jao
    for (int i = 0; i < 10; i++) {
        Object canScrollMore = ((JavascriptExecutor) driver)
                .executeScript("mobile: scrollGesture", scrollArgs);

        if (!Boolean.TRUE.equals(canScrollMore)) {
            break;
        }
    }


    scrollArgs.put("direction", "down");

    for (int i = 0; i < 10; i++) {
        for (String relation : allowedParticipantRelations) {
            By option = AppiumBy.xpath(
                    "//*[@text='" + relation
                            + "' or @content-desc='" + relation + "']"
            );

            List<WebElement> matches = driver.findElements(option);

            if (!matches.isEmpty() && matches.get(0).isDisplayed()) {
                wait.until(
                        ExpectedConditions.elementToBeClickable(option)
                ).click();

                System.out.println("Relation selected: " + relation);
                return;
            }
        }

        Object canScrollMore = ((JavascriptExecutor) driver)
                .executeScript("mobile: scrollGesture", scrollArgs);

        if (!Boolean.TRUE.equals(canScrollMore)) {
            break;
        }
    }

    throw new NoSuchElementException(
            "Relation list mein Father ya Mother nahi mila."
    );
}

	// ============================================
	// Click Participant Person Signature
	// ============================================

	public void clickParticipantPersonSignature() {

		WebElement signature = wait.until(ExpectedConditions.elementToBeClickable(participantPersonSignature));

		signature.click();

		System.out.println("Participant Person Signature clicked successfully!");
	}

	// ============================================
	// Draw Participant Person Signature
	// ============================================

	public void drawParticipantPersonSignature() {
    WebElement canvas = wait.until(
            ExpectedConditions.visibilityOfElementLocated(participantSignatureCanvas)
    );

    Rectangle rect = canvas.getRect();

    int left = rect.getX();
    int top = rect.getY();
    int width = rect.getWidth();
    int height = rect.getHeight();

    if (width <= 0 || height <= 0) {
        throw new IllegalStateException("Signature canvas has invalid dimensions.");
    }

    // Keep all points inside the canvas.
    int x1 = left + (int) (width * 0.15);
    int y1 = top + (int) (height * 0.55);
    int x2 = left + (int) (width * 0.35);
    int y2 = top + (int) (height * 0.30);
    int x3 = left + (int) (width * 0.50);
    int y3 = top + (int) (height * 0.65);
    int x4 = left + (int) (width * 0.70);
    int y4 = top + (int) (height * 0.35);
    int x5 = left + (int) (width * 0.85);
    int y5 = top + (int) (height * 0.55);

    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    Sequence signature = new Sequence(finger, 0);

    signature.addAction(finger.createPointerMove(
            Duration.ZERO,
            PointerInput.Origin.viewport(),
            x1, y1
    ));
    signature.addAction(finger.createPointerDown(0));
    signature.addAction(finger.createPointerMove(
            Duration.ofMillis(250),
            PointerInput.Origin.viewport(),
            x2, y2
    ));
    signature.addAction(finger.createPointerMove(
            Duration.ofMillis(250),
            PointerInput.Origin.viewport(),
            x3, y3
    ));
    signature.addAction(finger.createPointerMove(
            Duration.ofMillis(250),
            PointerInput.Origin.viewport(),
            x4, y4
    ));
    signature.addAction(finger.createPointerMove(
            Duration.ofMillis(250),
            PointerInput.Origin.viewport(),
            x5, y5
    ));
    signature.addAction(finger.createPointerUp(0));

    driver.perform(Collections.singletonList(signature));
}

	// ============================================
	// Click Sign Consent
	// ============================================

	public void clickSignConsent() {

    WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(20)
    );

    WebElement signConsent = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                    signConsentButton
            )
    );

    wait.until(
            ExpectedConditions.visibilityOf(signConsent)
    );

    System.out.println(
            "Sign Consent displayed: " + signConsent.isDisplayed()
    );

    System.out.println(
            "Sign Consent enabled: " + signConsent.isEnabled()
    );

    wait.until(
            ExpectedConditions.elementToBeClickable(
                    signConsentButton
            )
    ).click();

    System.out.println("Sign Consent button clicked!");
}

	// ============================================
	// Enter Witness Name
	// ============================================

	public void enterWitnessName(String witnessName) {

		if (witnessName == null || witnessName.trim().isEmpty()) {
			throw new IllegalArgumentException("Witness Name cannot be empty!");
		}

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(witnessNameField));

		field.click();
		field.clear();
		field.sendKeys(witnessName);

		System.out.println("Witness Name entered: " + witnessName);
	}

	private void scrollToBottom() {
		Dimension screen = driver.manage().window().getSize();

		Map<String, Object> args = new HashMap<>();
		args.put("left", 30);
		args.put("top", 200);
		args.put("width", screen.getWidth() - 60);
		args.put("height", screen.getHeight() - 400);
		args.put("direction", "down");
		args.put("percent", 0.8);

		for (int i = 0; i < 10; i++) {
			Object result = ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", args);

			// false means page aur scroll nahi ho sakta
			if (!Boolean.TRUE.equals(result)) {
				break;
			}
		}
	}
	
	public void clickWitnessPersonSignature() {
	    WebElement signature = wait.until(
	            ExpectedConditions.elementToBeClickable(witnessPersonSignature)
	    );

	    signature.click();
	    System.out.println("Witness Person Signature clicked.");
	}
	
	public void drawWitnessPersonSignature() {
	    WebElement canvas = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(witnessSignatureCanvas)
	    );

	    int width = canvas.getSize().getWidth();
	    int height = canvas.getSize().getHeight();

	    if (width <= 0 || height <= 0) {
	        throw new IllegalStateException(
	                "Witness signature canvas has invalid dimensions: "
	                        + width + "x" + height
	        );
	    }

	    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
	    Sequence signature = new Sequence(finger, 0);

	    int x1 = -(int) (width * 0.35);
	    int y1 =  (int) (height * 0.10);
	    int x2 = -(int) (width * 0.15);
	    int y2 = -(int) (height * 0.20);
	    int x3 =  (int) (width * 0.05);
	    int y3 =  (int) (height * 0.20);
	    int x4 =  (int) (width * 0.25);
	    int y4 = -(int) (height * 0.15);
	    int x5 =  (int) (width * 0.35);
	    int y5 =  (int) (height * 0.10);

	    signature.addAction(finger.createPointerMove(
	            Duration.ZERO,
	            PointerInput.Origin.fromElement(canvas),
	            x1, y1
	    ));
	    signature.addAction(finger.createPointerDown(0));
	    signature.addAction(finger.createPointerMove(
	            Duration.ofMillis(300),
	            PointerInput.Origin.fromElement(canvas),
	            x2, y2
	    ));
	    signature.addAction(finger.createPointerMove(
	            Duration.ofMillis(300),
	            PointerInput.Origin.fromElement(canvas),
	            x3, y3
	    ));
	    signature.addAction(finger.createPointerMove(
	            Duration.ofMillis(300),
	            PointerInput.Origin.fromElement(canvas),
	            x4, y4
	    ));
	    signature.addAction(finger.createPointerMove(
	            Duration.ofMillis(300),
	            PointerInput.Origin.fromElement(canvas),
	            x5, y5
	    ));
	    signature.addAction(finger.createPointerUp(0));

	    driver.perform(Collections.singletonList(signature));
	    System.out.println("Witness signature drawn.");
	}
	
	public void clickSaveConsent() {
	    WebElement saveConsent = wait.until(
	            ExpectedConditions.elementToBeClickable(saveConsentButton)
	    );

	    saveConsent.click();
	    System.out.println("Save Consent clicked.");
	}
	
	public void clickSave() {
	    WebElement save = wait.until(
	            ExpectedConditions.elementToBeClickable(saveButton)
	    );

	    save.click();
	    System.out.println("SAVE button clicked.");
	}
	
	public void clickSuccessPopupOk() {
	    WebElement okButton = wait.until(
	            ExpectedConditions.elementToBeClickable(successPopupOkButton)
	    );

	    okButton.click();
	    System.out.println("Success popup OK clicked succrssfully");
	}
}
