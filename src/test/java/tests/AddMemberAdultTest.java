package tests;

import org.testng.annotations.Test;

import base.BaseTest;

import pages.AddMemberAdult;
import pages.HouseholdPage;

public class AddMemberAdultTest extends BaseTest {

	@Test
	public void addAdultMemberTest() {

		loginAndOpenDashboard();

		// --------------------------------
		// Household
		// --------------------------------

		HouseholdPage householdPage = new HouseholdPage(driver);

		householdPage.clickHousehold();

		System.out.println("Household page opened successfully!");

		
		AddMemberAdult addMember = new AddMemberAdult(driver);

		addMember.clickContinueStatusHousehold();

		addMember.clickAddMember();

		System.out.println("Add Member page opened successfully!");

		addMember.enterNationalId();

		addMember.enterFirstName("mukesh");

		addMember.enterLastName("Kumar");

		

		addMember.enterRandomDobAdult();

		addMember.enterPrimaryMobile();

		addMember.selectMale();

	
		addMember.selectAdultMaleRelation();

		addMember.selectMemberScreeningPresent();

		
		addMember.selectAdultEmployment();

		

		addMember.clickOpenConsent();

		addMember.clickConsentCheckbox();

		
		addMember.clickParticipantPersonSignature();

		addMember.drawParticipantPersonSignature();

		addMember.clickSignConsent();

		addMember.clickSaveConsent();

		addMember.clickSave();

		addMember.clickSuccessPopupOk();
	}
}