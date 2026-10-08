package tests;

import java.net.URI;

import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class FirstAppTest {

    @Test
    public void launchApp() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();

        // Device configuration
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("emulator-5554");

        // APK path
        options.setApp("C:\\Users\\sumit\\Downloads\\nesLocal.apk");

        // Start Appium session
        AndroidDriver driver = new AndroidDriver(
                new URI("http://127.0.0.1:4723").toURL(),
                options
        );

        System.out.println("App launched successfully!");

        Thread.sleep(5000);

        driver.quit();
    }
}