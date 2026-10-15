package pages;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AddAdultsFemale extends AddMemberAdult {

    private final AndroidDriver femaleDriver;
    private final WebDriverWait femaleWait;
    private final Random femaleRandom = new Random();

    // Female checkbox: instance(1)
    private final By femaleCheckbox = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.CheckBox\").instance(1)");

    // Relation dropdown
    private final By femaleRelationDropdown = AppiumBy.xpath(
            "//android.view.View[contains(@content-desc,'Relation to household head')]");

    // Only allowed female relations
    private static final String[] FEMALE_RELATIONS = {
            "Wife",
            "Daughter",
            "Mother"
    };

    public AddAdultsFemale(AndroidDriver driver) {
        super(driver);
        this.femaleDriver = driver;
        this.femaleWait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ============================================
    // Select Female
    // ============================================

    public void selectFemale() {

        WebElement female = femaleWait.until(
                ExpectedConditions.elementToBeClickable(femaleCheckbox));

        female.click();

        System.out.println("Female selected successfully!");
    }

  

    public void selectFemaleRelation() {

        WebElement relationDropdown = femaleWait.until(
                ExpectedConditions.elementToBeClickable(femaleRelationDropdown));

        relationDropdown.click();

        System.out.println("Female relation dropdown opened!");

        String selectedRelation = FEMALE_RELATIONS[
                femaleRandom.nextInt(FEMALE_RELATIONS.length)];

        System.out.println("Trying to select female relation: " + selectedRelation);

        By relationOption;

        if ("Mother".equals(selectedRelation)) {

            relationOption = AppiumBy.xpath(
                    "//*[translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                    + "'abcdefghijklmnopqrstuvwxyz')='mother' "
                    + "or translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                    + "'abcdefghijklmnopqrstuvwxyz')='mother']");

        } else {

            relationOption = AppiumBy.xpath(
                    "//*[@content-desc='" + selectedRelation + "' "
                    + "or @text='" + selectedRelation + "']");
        }

        femaleWait.until(
                ExpectedConditions.elementToBeClickable(relationOption)).click();

        System.out.println("Female relation selected successfully: " + selectedRelation);
    }
}
