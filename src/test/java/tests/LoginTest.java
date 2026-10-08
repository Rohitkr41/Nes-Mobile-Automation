
package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {

        // --------------------------------
        // Login Page
        // --------------------------------

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(
                "Rohit",
                "Rohit@123"
        );

        // --------------------------------
        // Location Permission
        // --------------------------------

        allowLocationPermission();

        // --------------------------------
        // Verify Dashboard
        // --------------------------------

        loginPage.verifyDashboard();

        // --------------------------------
        // Final Result
        // --------------------------------

        System.out.println(
                "LOGIN + LOCATION + DASHBOARD successfully done"
        );
    }
}
