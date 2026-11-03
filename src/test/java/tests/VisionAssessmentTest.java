package tests;

import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import base.BaseTest;
import io.appium.java_client.AppiumBy;
import pages.AddMember;
import pages.HouseholdPage;
import pages.Questionnaire;
import pages.VisionAssessmentPage;

public class VisionAssessmentTest extends BaseTest {

    @Test
    public void clickYesOnVisionAssessment() throws InterruptedException {
        loginAndOpenDashboard();

        HouseholdPage householdPage = new HouseholdPage(driver);
        householdPage.clickHousehold();

        AddMember addMember = new AddMember(driver);
        addMember.clickContinueStatusHousehold();

       
        

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(d -> !d.findElements(
            AppiumBy.xpath("//*[contains(@content-desc, 'Member No:')]")
        ).isEmpty());

        Questionnaire questionnaire = new Questionnaire(driver);
        questionnaire.getPatientNames();
        

        
        openPatientVisionAssessment();

        VisionAssessmentPage visionAssessmentPage =
            new VisionAssessmentPage(driver);

        visionAssessmentPage.clickYes();
       
        visionAssessmentPage.selectRandomDuration();
        visionAssessmentPage.selectRandomPresentingOd();
        visionAssessmentPage.selectRandomPresentingOs();
        visionAssessmentPage.clickSaveAndExit();
        visionAssessmentPage.clickOkOnPopup();
    }
}