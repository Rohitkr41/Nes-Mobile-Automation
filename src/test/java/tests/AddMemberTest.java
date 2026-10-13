package tests;

import org.testng.annotations.Test;

import base.BaseTest;

import pages.AddMember;
import pages.LoginPage;
import pages.HouseholdPage;

public class AddMemberTest extends BaseTest {

	@Test
	public void addMemberTest() {

		loginAndOpenDashboard();
		// --------------------------------
		// Household
		// --------------------------------

		HouseholdPage householdPage = new HouseholdPage(driver);

		householdPage.clickHousehold();

		System.out.println("Household page opened successfully!");

		// --------------------------------
		// Add Member
		// --------------------------------

		AddMember addMember = new AddMember(driver);

		// --------------------------------
		// Click Household
		// Whose Status is Continue
		// --------------------------------

		addMember.clickContinueStatusHousehold();

		// --------------------------------
		// Click Add Member
		// --------------------------------

		addMember.clickAddMember();

		System.out.println("Add Member page opened successfully!");


		addMember.enterNationalId();

		// --------------------------------
		// First Name
		// --------------------------------

		addMember.enterFirstName("karan");

		// --------------------------------
		// Last Name
		// --------------------------------

		addMember.enterLastName("Kumar");

		// --------------------------------
		// Date Of Birth
		// Age Between 5 - 15 Years
		// --------------------------------

		addMember.enterRandomDob();

		// --------------------------------
		// Primary Mobile
		// Starts With 05
		// 10 Digits
		// --------------------------------

		addMember.enterPrimaryMobile();

		// --------------------------------
		// Select Gender - Male
		// --------------------------------

		addMember.selectMale();

		// --------------------------------
		// Relation To Household Head
		// --------------------------------

		addMember.selectMaleRelation();

		addMember.selectMemberScreeningPresent();

		addMember.selectChildOrStudent();
		
		//- consent data process

		addMember.clickOpenConsent();
		
		addMember.clickConsentCheckbox();
		
		addMember.enterConsentPersonName("Rohit Kumar");
		
		addMember.selectParticipantRelation();
		
		addMember.clickParticipantPersonSignature();
		
		addMember.drawParticipantPersonSignature();
		
		addMember.clickSignConsent();
//		addMember.clickSaveConsent();
		
		addMember.enterWitnessName("Rahul Kumar");
		
		addMember.clickWitnessPersonSignature();
		addMember.drawWitnessPersonSignature();
		addMember.clickSignConsent();
		addMember.clickSaveConsent();
		addMember.clickSave();
		addMember.clickSuccessPopupOk();

	}
}
