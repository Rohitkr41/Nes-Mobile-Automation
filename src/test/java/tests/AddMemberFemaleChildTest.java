
package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.AddMemberFemaleChild;
import pages.HouseholdPage;

public class AddMemberFemaleChildTest extends BaseTest {

    @Test
    public void registerFemaleChild() {

		loginAndOpenDashboard();
		// --------------------------------
		// Household
		// --------------------------------

		HouseholdPage householdPage = new HouseholdPage(driver);

		householdPage.clickHousehold();

		System.out.println("Household page opened successfully!");


        AddMemberFemaleChild child =
                new AddMemberFemaleChild(driver);
        
        child.clickContinueStatusHousehold();
        child.clickAddMember();
        

        // Existing AddMember methods
        child.enterNationalId();
        child.enterFirstName("rekha");
        child.enterLastName("gupta");
        child.enterRandomDob();
        child.enterPrimaryMobile();

        // New female-child methods
        child.selectFemaleChild();
        child.selectDaughterRelation();

        // Existing Member Screening Status method
        child.selectMemberScreeningPresent();

        // Existing Employment method
        child.selectChildOrStudent();

        // Existing Consent workflow methods
        child.clickOpenConsent();
        child.clickConsentCheckbox();
        child.enterConsentPersonName("Priya Sharma");
        child.selectParticipantRelation();
        child.clickParticipantPersonSignature();
        child.drawParticipantPersonSignature();
        child.clickSignConsent();
        child.enterWitnessName("Test Witness");
        child.clickWitnessPersonSignature();
        child.drawWitnessPersonSignature();
        child.clickSignConsent();
        child.clickSaveConsent();

        // Save member and handle success popup
        child.clickSave();
        child.clickSuccessPopupOk();
    }
}
