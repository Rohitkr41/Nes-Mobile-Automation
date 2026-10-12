package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class Sociodemographic {

	private final AndroidDriver driver;
	private final WebDriverWait wait;
	

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

	public void clickSociodemographic() {

    WebElement section = wait.until(
        ExpectedConditions.elementToBeClickable(
            sociodemographicSection
        )
    );

    System.out.println(
        "Sociodemographic section found. Attempting click..."
    );

    section.click();

    System.out.println(
        "Sociodemographic click command executed."
    );
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