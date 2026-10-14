package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import io.appium.java_client.AppiumBy;
import pages.AccessToCare;
import pages.AddMember;
import pages.DifficultyStatusPage;
import pages.HouseholdPage;
import pages.Questionnaire;
import pages.SelfReportedEyeProblems;
import pages.SelfReportedHealth;
import pages.Sociodemographic;

public class AdultQuestionarireTest extends BaseTest {

    @Test
    public void openSelectedPatientQuestionnaireTest() {

        // 1. Login and open dashboard
        loginAndOpenDashboard();

        // 2. Open household
        HouseholdPage householdPage = new HouseholdPage(driver);
        householdPage.clickHousehold();

        // 3. Continue household status
        AddMember addMember = new AddMember(driver);
        addMember.clickContinueStatusHousehold();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(30));

        // 4. Wait for household members
        wait.until(d -> !d.findElements(
                AppiumBy.xpath(
                    "//*[contains(@content-desc, 'Member No:')]"
                )).isEmpty());

        // 5. Get patient list
        Questionnaire questionnaire = new Questionnaire(driver);

        List<String> patients = questionnaire.getPatientNames();

        System.out.println("Available patients: " + patients);

        Assert.assertFalse(
                patients.isEmpty(),
                "Patient list load nahi hui. Source: "
                        + driver.getPageSource()
        );

        // 6. Open selected patient questionnaire
        openPatientQuestionnaire();

        // 7. Sociodemographic section
        Sociodemographic sociodemographic =
                new Sociodemographic(driver);

        sociodemographic.clickSociodemographic();
        sociodemographic.enterRandomYearOfEducation(2);
        sociodemographic.selectReadAndWrite();
        sociodemographic.RandomNoneYearOfEducation(1);
        sociodemographic.selectRandomNearWorkDuration();
        sociodemographic.selectRandomTimeOutdoorDuration();
        sociodemographic.clickSaveSociodemographic();

        // 8. Self-reported Health section
        SelfReportedHealth health =
                new SelfReportedHealth(driver);
       
        health.selectRandomHealthRating();
        health.selectRandomHealthCondition();
        health.clickSaveSelfReportedHealth();

        // 9. Self-reported Eye Problems section
        SelfReportedEyeProblems eyeProblems =
                new SelfReportedEyeProblems(driver);

        // Agar section automatically open nahi hota,
       
        eyeProblems.selectRandomEyeProblem();
        eyeProblems.clickSaveSelfReportedEyeProblems();
        
        //difficulty status
        DifficultyStatusPage difficultyStatus = new DifficultyStatusPage(driver);
        difficultyStatus.clickDifficultyStatus();
        difficultyStatus.selectRandomDifficulty();
        difficultyStatus.scrollAndClickSaveDifficulty();
        
        

        // 10. Access to Care section
        AccessToCare care = new AccessToCare(driver);
        care.clickNo();
        care.selectRandomNeverEyeExam();
        care.clickCompleteQuestionnaire();
        
    }
}