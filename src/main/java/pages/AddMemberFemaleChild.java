
package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AddMemberFemaleChild extends AddMember {

    private final WebDriverWait femaleChildWait;

    // Female checkbox: instance(1)
    private final By femaleCheckbox = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.widget.CheckBox\").instance(1)");

    // Relation to household head dropdown
    private final By relationToHouseholdHead = AppiumBy.xpath(
            "//android.view.View[contains(@content-desc,'Relation to household head')]");

    // Only allowed relation
    private final By daughterOption = AppiumBy.xpath(
            "//*[@content-desc='Daughter' or @text='Daughter']");

    public AddMemberFemaleChild(AndroidDriver driver) {
        super(driver);
        this.femaleChildWait = new WebDriverWait(
                driver, Duration.ofSeconds(20));
    }

    // ============================================
    // Select Female
    // ============================================

    public void selectFemaleChild() {

        WebElement female = femaleChildWait.until(
                ExpectedConditions.elementToBeClickable(femaleCheckbox));

        female.click();

        System.out.println("Female selected for child successfully!");
    }

    // ============================================
    // Select Relation: Daughter ONLY
    // ============================================

    public void selectDaughterRelation() {

        WebElement relationDropdown = femaleChildWait.until(
                ExpectedConditions.elementToBeClickable(
                        relationToHouseholdHead));

        relationDropdown.click();

        System.out.println("Relation dropdown opened!");

        WebElement daughter = femaleChildWait.until(
                ExpectedConditions.elementToBeClickable(daughterOption));

        daughter.click();

        System.out.println("Relation selected: Daughter");
    }
}
