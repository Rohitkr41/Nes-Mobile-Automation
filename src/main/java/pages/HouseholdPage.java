package pages;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class HouseholdPage {

	private AndroidDriver driver;
	private WebDriverWait wait;
	private Random random = new Random();

	public HouseholdPage(AndroidDriver driver) {

		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	// ============================================
	// Household
	// ============================================

	private By household = AppiumBy.accessibilityId("Household");

	private By addHousehold = AppiumBy.accessibilityId("Add Household");

	// ============================================
	// Region
	// ============================================

	private By regionDropdown = AppiumBy.accessibilityId("Select region...");

	// ============================================
	// Cluster
	// ============================================

	private By clusterDropdown = AppiumBy.accessibilityId("Select cluster...");

	// ============================================
	// Household Name / First Text Field
	// ============================================

	private By householdTextField = AppiumBy
			.androidUIAutomator("new UiSelector()" + ".className(\"android.widget.EditText\")" + ".instance(0)");

	// ============================================
	// Door No
	// ============================================

	private By doorNoField = AppiumBy
			.androidUIAutomator("new UiSelector()" + ".className(\"android.widget.EditText\")" + ".instance(1)");

	// ============================================
	// Area
	// ============================================

	private By areaField = AppiumBy
			.androidUIAutomator("new UiSelector()" + ".className(\"android.widget.EditText\")" + ".instance(2)");

	// ============================================
	// Total Member
	// ============================================

	private By totalMemberField = AppiumBy
			.androidUIAutomator("new UiSelector()" + ".className(\"android.widget.EditText\")" + ".instance(3)");

	// ============================================
	// Screening Status Dropdown
	// ============================================

	private By screeningStatusDropdown = AppiumBy.xpath("//*[@content-desc='Screening status\nSelect']");

	// ============================================
	// Continue
	// ============================================

	private By continueButton = AppiumBy.accessibilityId("Continue");

	// ============================================
	// SAVE
	// ============================================

	private By saveButton = AppiumBy.accessibilityId("SAVE");

	// ============================================
	// Success Message
	// ============================================

	private By successMessage = AppiumBy
			.xpath("//android.view.View[contains(@content-desc,'Household added successfully')]");
	// ============================================
	// OK Popup
	// ============================================

	private By okButton = AppiumBy.xpath("//android.view.View[@content-desc='OK']");

	// ============================================
	// Click Household
	// ============================================

	public void clickHousehold() {

		wait.until(ExpectedConditions.elementToBeClickable(household)).click();

		System.out.println("Household clicked successfully!");
	}

	// ============================================
	// Click Add Household
	// ============================================

	public void clickAddHousehold() {

		wait.until(ExpectedConditions.elementToBeClickable(addHousehold)).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(regionDropdown));

		System.out.println("Add Household form opened!");
	}

	// ============================================
	// Select Random Region
	// ============================================

	public void selectRandomRegion() {

		// Open Region dropdown
		wait.until(ExpectedConditions.elementToBeClickable(regionDropdown)).click();

		System.out.println("Region dropdown opened!");

		// Region options
		By regionOptions = AppiumBy.xpath("//android.view.View[@content-desc]");

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(regionOptions));

		List<WebElement> regions = driver.findElements(regionOptions);

		// Remove unwanted elements
		regions.removeIf(element -> {

			String value = element.getAttribute("content-desc");

			return value == null || value.trim().isEmpty() || value.equals("Select region...")
					|| value.equals("Select cluster...") || value.equals("SAVE") || value.equals("Household")
					|| value.equals("Add Household") || value.equals("Continue");
		});

		if (regions.isEmpty()) {

			throw new RuntimeException("No Region options found!");
		}

		// Select random Region
		WebElement selectedRegion = regions.get(random.nextInt(regions.size()));

		String regionName = selectedRegion.getAttribute("content-desc");

		System.out.println("Trying to select Region: " + regionName);

		selectedRegion.click();

		System.out.println("Region selected successfully: " + regionName);
	}

	// ============================================
	// Select Random Cluster
	// ============================================

	public void selectRandomCluster() {

		// Open Cluster dropdown
		wait.until(ExpectedConditions.elementToBeClickable(clusterDropdown)).click();

		System.out.println("Cluster dropdown opened!");

		// Cluster options
		By clusterOptions = AppiumBy.xpath("//android.view.View[@content-desc]");

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(clusterOptions));

		List<WebElement> clusters = driver.findElements(clusterOptions);

		// Remove unwanted elements
		clusters.removeIf(element -> {

			String value = element.getAttribute("content-desc");

			return value == null || value.trim().isEmpty() || value.equals("Select region...")
					|| value.equals("Select cluster...") || value.equals("SAVE") || value.equals("Household")
					|| value.equals("Add Household") || value.equals("Continue");
		});

		if (clusters.isEmpty()) {

			throw new RuntimeException("No Cluster options found for selected Region!");
		}

		// Select random Cluster
		WebElement selectedCluster = clusters.get(random.nextInt(clusters.size()));

		String clusterName = selectedCluster.getAttribute("content-desc");

		System.out.println("Trying to select Cluster: " + clusterName);

		selectedCluster.click();

		System.out.println("Random Cluster selected: " + clusterName);
	}

	// ============================================
	// Enter Household Text
	// ============================================

	public void enterHouseholdText(String text) {

		if (text == null || text.trim().isEmpty() || text.length() > 8) {

			throw new IllegalArgumentException("Text must contain 1 to 8 characters.");
		}

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(householdTextField));

		field.click();
		field.clear();
		field.sendKeys(text);

		System.out.println("Household text entered: " + text);
	}

	// ============================================
	// Enter Door No
	// ============================================

	public void enterDoorNo(String doorNo) {

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(doorNoField));

		field.click();
		field.clear();
		field.sendKeys(doorNo);

		System.out.println("Door No entered: " + doorNo);
	}

	// ============================================
	// Enter Area
	// ============================================

	public void enterArea(String area) {

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(areaField));

		field.click();
		field.clear();
		field.sendKeys(area);

		System.out.println("Area entered: " + area);
	}

	// ============================================
	// Enter Total Member
	// ============================================

	public void enterTotalMember(String totalMember) {

		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(totalMemberField));

		field.click();
		field.clear();
		field.sendKeys(totalMember);

		System.out.println("Total Member entered: " + totalMember);
	}

	// ============================================
	// Open Screening Status Dropdown
	// ============================================

	public void clickScreeningStatus() {

		WebElement screeningStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(screeningStatusDropdown));

		screeningStatus.click();

		System.out.println("Screening Status clicked successfully!");
	}

	// ============================================
	// Click Continue
	// ============================================

	public void clickContinue() {

		WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));

		continueBtn.click();

		System.out.println("Continue clicked successfully!");
	}

	// ============================================
	// Click SAVE
	// ============================================

	public void clickSave() {

		wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();

		System.out.println("SAVE clicked successfully!");
	}

	// ============================================
	// Verify Household Success
	// ============================================

	public void verifyHouseholdSuccess() {

		WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));

		String actualMessage = message.getAttribute("content-desc");

		System.out.println("Actual Success Message: " + actualMessage);

		Assert.assertTrue(actualMessage != null && actualMessage.contains("Household added successfully"),
				"Success message is not displayed!");

		System.out.println("Success message verified successfully!");
	}
	// ============================================
	// Click OK
	// ============================================

	public void clickOk() {

		WebElement ok = wait.until(ExpectedConditions.elementToBeClickable(okButton));

		ok.click();

		System.out.println("OK clicked successfully!");
	}

}