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
import pages.HouseholdPage;
import pages.Questionnaire;
import pages.SelfReportedEyeProblems;
import pages.SelfReportedHealth;
import pages.Sociodemographic;

public class QuestionnaireTest extends BaseTest {

	@Test
	public void openSelectedPatientQuestionnaireTest() {

		loginAndOpenDashboard();

		// Open household
		HouseholdPage householdPage = new HouseholdPage(driver);
		householdPage.clickHousehold();

		// Continue household status
		AddMember addMember = new AddMember(driver);
		addMember.clickContinueStatusHousehold();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		// Wait until household members load
		wait.until(d -> !d.findElements(AppiumBy.xpath("//*[contains(@content-desc, 'Member No:')]")).isEmpty());

		Questionnaire questionnaire = new Questionnaire(driver);

		List<String> patients = questionnaire.getPatientNames();

		System.out.println("Available patients: " + patients);

		Assert.assertFalse(patients.isEmpty(), "Patient list load nahi hui. Source: " + driver.getPageSource());


        openPatientQuestionnaire();


		// Fill Sociodemographic section
		Sociodemographic sociodemographic = new Sociodemographic(driver);

			sociodemographic.clickSociodemographic();
			sociodemographic.enterRandomYearOfEducation(2);
			sociodemographic.selectReadAndWrite();
			sociodemographic.RandomNoneYearOfEducation(1);
			sociodemographic.selectRandomNearWorkDuration();
			sociodemographic.selectRandomTimeOutdoorDuration();
			sociodemographic.clickSaveSociodemographic();
		

		// Self-reported Health section open karein
		SelfReportedHealth health = new SelfReportedHealth(driver);

//		health.clickSelfReportedHealth();
		health.selectRandomHealthRating();
		health.selectRandomHealthCondition();
		health.clickSaveSelfReportedHealth();

		// Self-reported eye section open karein
		SelfReportedEyeProblems eyeProblems = new SelfReportedEyeProblems(driver);

//		eyeProblems.clickSelfReportedEyeProblems();
		eyeProblems.selectRandomEyeProblem();
		eyeProblems.clickSaveSelfReportedEyeProblems();

		// Access to care
		AccessToCare care = new AccessToCare(driver);
//		care.clickAccessToCare();
		care.clickNo();
		care.selectRandomNeverEyeExam();
		care.clickCompleteQuestionnaire();

	}
}
