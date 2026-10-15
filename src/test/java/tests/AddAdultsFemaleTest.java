package tests;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.AddAdultsFemale;
import pages.HouseholdPage;

public class AddAdultsFemaleTest extends BaseTest {

    @Test
    public void registerFemaleMember() {
        
        loginAndOpenDashboard();

		// --------------------------------
		// Household
		// --------------------------------

		HouseholdPage householdPage = new HouseholdPage(driver);

		householdPage.clickHousehold();

		System.out.println("Household page opened successfully!");

	
		AddAdultsFemale female = new AddAdultsFemale(driver);


		female.clickContinueStatusHousehold();

		female.clickAddMember();

		System.out.println("Add Member page opened successfully!");
		
		 
        // Existing methods reused from femaleAdult
        female.enterNationalId();
        female.enterFirstName("puja");
        female.enterLastName("kumari");
        female.enterRandomDobAdult();
        female.enterPrimaryMobile();

        // Female-specific methods
        female.selectFemale();
        female.selectFemaleRelation();
        female.selectMemberScreeningPresent();

        // Existing employment method
        female.selectAdultEmployment();
        female.clickOpenConsent();

		female.clickConsentCheckbox();

		
		female.clickParticipantPersonSignature();

		female.drawParticipantPersonSignature();

		female.clickSignConsent();

		female.clickSaveConsent();

		female.clickSave();

		female.clickSuccessPopupOk();
    }
}
