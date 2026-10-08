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

		LoginPage loginPage = new LoginPage(driver);

		loginPage.login("Rohit", "Rohit@123");

		// --------------------------------
		// Location Permission
		// --------------------------------

		allowLocationPermission();

		// --------------------------------
		// Verify Dashboard
		// --------------------------------

		loginPage.verifyDashboard();

		// --------------------------------
		// Household
		// --------------------------------

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

		householdPage.enterHouseholdText("ROHIT123");

		// --------------------------------
		// Door No
		// --------------------------------

		householdPage.enterDoorNo("12");

		// --------------------------------
		// Area
		// --------------------------------

		householdPage.enterArea("Delhi");

		// --------------------------------
		// Total Member
		// --------------------------------

		householdPage.enterTotalMember("4");

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