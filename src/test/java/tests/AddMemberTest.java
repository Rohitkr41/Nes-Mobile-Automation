//package tests;
//
//import org.testng.annotations.Test;
//
//import base.BaseTest;
//
//import pages.AddMember;
//import pages.LoginPage;
//import pages.HouseholdPage;
//
//public class AddMemberTest extends BaseTest {
//
//    @Test
//    public void addMemberTest() {
//
//        // --------------------------------
//        // Login
//        // --------------------------------
//
//        LoginPage loginPage = new LoginPage(driver);
//
//        loginPage.login("Rohit", "Rohit@123");
//
//        // --------------------------------
//        // Location Permission
//        // --------------------------------
//
//        allowLocationPermission();
//
//        // --------------------------------
//        // Verify Dashboard
//        // --------------------------------
//
//        loginPage.verifyDashboard();
//
//        // --------------------------------
//        // Household
//        // --------------------------------
//
//        HouseholdPage householdPage = new HouseholdPage(driver);
//
//        householdPage.clickHousehold();
//
//        System.out.println("Household page opened successfully!");
//
//        // --------------------------------
//        // Add Member
//        // --------------------------------
//
//        AddMember addMember = new AddMember(driver);
//
//        // --------------------------------
//        // Click Household
//        // Whose Status is Continue
//        // --------------------------------
//
//        addMember.clickContinueStatusHousehold();
//
//        System.out.println("Continue status household clicked successfully!");
//        
//     // --------------------------------
//     // Click Add Member
//     // --------------------------------
//
//     addMember.clickAddMember();
//
//     System.out.println("Add Member page opened successfully!");
//
//
//    }
//}


package tests;

import org.testng.annotations.Test;

import base.BaseTest;

import pages.AddMember;
import pages.LoginPage;
import pages.HouseholdPage;

public class AddMemberTest extends BaseTest {

    @Test
    public void addMemberTest() {

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
        // Add Member
        // --------------------------------

        AddMember addMember = new AddMember(driver);

        // --------------------------------
        // Click Household
        // Whose Status is Continue
        // --------------------------------

        addMember.clickContinueStatusHousehold();

        System.out.println("Continue status household clicked successfully!");

        // --------------------------------
        // Click Add Member
        // --------------------------------

        addMember.clickAddMember();

        System.out.println("Add Member page opened successfully!");

        // --------------------------------
        // National ID
        // Starts With 1
        // 1 - 10 Digits
        // --------------------------------

        addMember.enterNationalId();

        // --------------------------------
        // First Name
        // --------------------------------

        addMember.enterFirstName("mohit");

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

        System.out.println("Member basic details entered successfully!");
        
     // --------------------------------
     // Select Gender - Male
     // --------------------------------

     addMember.selectMale();

     System.out.println("Male gender selected successfully!");

     // --------------------------------
     // Relation To Household Head
     // --------------------------------

     addMember.selectMaleRelation();
     
  // --------------------------------
  // Relation To Household Head
  // Male Allowed Relations
  // --------------------------------

  addMember.selectMaleRelation();


    }
}
