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
		    this.wait = new WebDriverWait(
		        driver, Duration.ofSeconds(30)
		    );
	}
	
	private final By yearOfEducationField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)");

	private final By highestSchoolEducationDropdown = AppiumBy
			.androidUIAutomator("new UiSelector().descriptionContains(\"Highest School Education\")");
	private final By NoneyearOfEducationField = AppiumBy
			.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(1)");

	private By nearWorkDurationDropdown = AppiumBy.accessibilityId(
		    "Near Work Duration\nSelect"
		);


	private By timeOutdoorDurationDropdown =
	        AppiumBy.accessibilityId("Time Outdoor Duration\nSelect");

	private final By saveSociodemographicButton = AppiumBy.accessibilityId("Save Sociodemographic");

	// methods start here

	private void waitForLoaderToDisappear() {

	By loader = AppiumBy.className("android.widget.ProgressBar");

	// Loader aata hai to pehle use dikhne do (max 2 sec), phir gayab hone ka wait
	try {
		new WebDriverWait(driver, Duration.ofSeconds(2))
				.until(ExpectedConditions.visibilityOfElementLocated(loader));
	} catch (TimeoutException ignored) {
		// loader abhi dikha hi nahi, aage badho
	}

	wait.until(ExpectedConditions.invisibilityOfElementLocated(loader));

	pause(500); // UI settle hone ka chhota wait
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

	// 1) Loading khatam hone ka wait
	waitForLoaderToDisappear();

	// 2) Section aur uska status load hone ka wait
	String desc = wait.until(d -> {
		List<WebElement> els = d.findElements(sociodemographicSection);
		if (els.isEmpty()) {
			return null;
		}
		String value = els.get(0).getAttribute("content-desc");
		return (value != null && value.contains(",")) ? value : null;
	});

	System.out.println("Sociodemographic status: " + desc.replace("\n", " ").trim());

	if (desc.contains("Expanded")) {
		System.out.println("Sociodemographic already expanded. Click skipped.");
		return true;
	}

	if (!desc.contains("Pending")) {
		System.out.println("Sociodemographic status Pending nahi hai. Click skipped.");
		return false;
	}

	// 3) Click + verify + retry (max 3 baar)
	for (int attempt = 1; attempt <= 3; attempt++) {

		WebElement section = wait.until(ExpectedConditions.elementToBeClickable(sociodemographicSection));

		if (attempt == 2) {
			// Dusri try: gesture se click (normal click na chale to)
			driver.executeScript("mobile: clickGesture",
					Map.of("elementId", ((RemoteWebElement) section).getId()));
		} else {
			section.click();
		}

		pause(1000);

		if (isSociodemographicOpen()) {
			System.out.println("Sociodemographic opened (attempt " + attempt + ").");
			return true;
		}

		System.out.println("Sociodemographic khula nahi (attempt " + attempt + "), dobara try...");
		waitForLoaderToDisappear();
	}

	throw new org.openqa.selenium.ElementNotInteractableException(
			"Sociodemographic section 3 try ke baad bhi open nahi hua.");
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

    // Exact accessibility description from your XML
    By dropdownLocator = AppiumBy.accessibilityId(
        "Near Work Duration\nSelect"
    );

    WebElement dropdown = wait.until(
        ExpectedConditions.elementToBeClickable(dropdownLocator)
    );

    dropdown.click();

    System.out.println("Near Work Duration dropdown clicked.");

    // Wait for HH:MM options
    By durationOptions = AppiumBy.xpath(
        "//android.widget.Button[" +
        "matches(@content-desc, '\\d{2}:\\d{2}')]" 
    );

    List<WebElement> options = wait.until(driver -> {
        List<WebElement> buttons =
            driver.findElements(durationOptions);

        return buttons.isEmpty() ? null : buttons;
    });

    WebElement selected = options.get(
        java.util.concurrent.ThreadLocalRandom.current()
            .nextInt(options.size())
    );

    String selectedTime = selected.getAttribute("content-desc");

    selected.click();

    System.out.println(
        "Near Work Duration selected: " + selectedTime
    );
}
	public void selectRandomTimeOutdoorDuration() {

    // Dropdown locate and click
    WebElement dropdown = wait.until(
        ExpectedConditions.elementToBeClickable(
            timeOutdoorDurationDropdown
        )
    );

    dropdown.click();

    System.out.println("Time Outdoor Duration dropdown clicked.");

    // HH:MM options locate karo
    By durationButtons = AppiumBy.xpath(
        "//android.widget.Button[contains(@content-desc, ':')]"
    );

    List<WebElement> timeOptions = wait.until(driver -> {

        List<WebElement> buttons =
            driver.findElements(durationButtons);

        List<WebElement> validOptions =
            new java.util.ArrayList<>();

        for (WebElement button : buttons) {

            String time = button.getAttribute("content-desc");

            if (time != null &&
                    time.matches("\\d{2}:\\d{2}")) {
                validOptions.add(button);
            }
        }

        return validOptions.isEmpty() ? null : validOptions;
    });

    // Random duration select karo
    WebElement selectedOption = timeOptions.get(
        java.util.concurrent.ThreadLocalRandom.current()
            .nextInt(timeOptions.size())
    );

    String selectedTime =
        selectedOption.getAttribute("content-desc");

    selectedOption.click();

    System.out.println(
        "Time Outdoor Duration selected: " + selectedTime
    );
}
	

}
