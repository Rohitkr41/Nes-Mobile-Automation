package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.HouseholdPage;
import pages.LoginPage;

public class HouseholdTest extends BaseTest {

	@Test
	public void householdTest() {

		// --------------------------------
		// Login
		// --------------------------------

		loginAndOpenDashboard();
		HouseholdPage householdPage = new HouseholdPage(driver);

		householdPage.clickHousehold();

		System.out.println("Household page opened successfully!");

		// --------------------------------
		// Add Household
		// --------------------------------

		householdPage.clickAddHousehold();

		// --------------------------------
		// Random Region
		// --------------------------------

		householdPage.selectRandomRegion();

		// --------------------------------
		// Random Cluster
		// According to selected Region
		// --------------------------------

		householdPage.selectRandomCluster();

		// --------------------------------
		// Household Text
		// Maximum 8 Characters
		// --------------------------------

		householdPage.enterHouseholdText("rimt457");

		// --------------------------------
		// Door No
		// --------------------------------

		householdPage.enterDoorNo("c4");

		// --------------------------------
		// Area
		// --------------------------------

		householdPage.enterArea("gurgaon");

		// --------------------------------
		// Total Member
		// --------------------------------

		householdPage.enterTotalMember("6");

		// First Screening Status par click
		householdPage.clickScreeningStatus();

		// Then Continue
		householdPage.clickContinue();

		// --------------------------------
		// SAVE
		// --------------------------------

		householdPage.clickSave();

		// --------------------------------
		// Verify Success Message
		// --------------------------------

		householdPage.verifyHouseholdSuccess();

		// --------------------------------
		// Click OK
		// --------------------------------

		householdPage.clickOk();
	}
}
