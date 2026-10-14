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
import pages.AdultVissionAssessmentPage;

public class AdultVisionAssessmentTest extends BaseTest {
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

        AdultVissionAssessmentPage visionAssessmentPage =
                new AdultVissionAssessmentPage(driver);

        visionAssessmentPage.clickYes();
        visionAssessmentPage.selectRandomDuration();
        
        visionAssessmentPage.clickWearingGlassYes();

        visionAssessmentPage.clickNearCorrectionDropdown();
        
        visionAssessmentPage.selectRandomPresentingOd();
        visionAssessmentPage.selectRandomPresentingOs();
        
        visionAssessmentPage.debugSelectDropdowns();
        
     // Near Presenting: random NVA value select
        visionAssessmentPage.selectNVARandomNearPresenting();

        // Save after completing the fields
        visionAssessmentPage.clickSaveAndExit();
    }
}
