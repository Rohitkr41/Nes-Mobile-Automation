
package tests;

import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() {

    	loginAndOpenDashboard();
        // --------------------------------
        // Final Result
        // --------------------------------

        System.out.println(
                "LOGIN + LOCATION + DASHBOARD successfully done"
        );
    }
}
