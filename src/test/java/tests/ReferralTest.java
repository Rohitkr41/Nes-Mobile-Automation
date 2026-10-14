package tests;

import java.time.Duration;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import io.appium.java_client.AppiumBy;
import pages.AddMember;
import pages.HouseholdPage;
import pages.Questionnaire;
import pages.ReferralPage;

public class ReferralTest extends BaseTest {

    @Test
    public void openPatientReferralTest() {
        loginAndOpenDashboard();

        HouseholdPage householdPage = new HouseholdPage(driver);
        householdPage.clickHousehold();

        AddMember addMember = new AddMember(driver);
        addMember.clickContinueStatusHousehold();

        // Household members load hone ka wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(d -> !d.findElements(
            AppiumBy.xpath("//*[contains(@content-desc, 'Member No:')]")
        ).isEmpty());

        Questionnaire questionnaire = new Questionnaire(driver);
        Assert.assertFalse(
            questionnaire.getPatientNames().isEmpty(),
            "Patient list load nahi hui."
        );

        // Patient ke naam se Referral button click
        // (screen par ho to seedha click, nahi to scroll karke)
        ReferralPage referralPage = new ReferralPage(driver);
        referralPage.openMemberReferral(PATIENT_NAME);

        // Referral page
        referralPage.clickHospital();
        referralPage.selectRandomCenter();
        referralPage.selectCurrentDateAndTime();
        referralPage.clickSaveAndExit();
    }
}