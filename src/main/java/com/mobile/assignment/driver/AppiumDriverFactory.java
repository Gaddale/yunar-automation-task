package com.mobile.assignment.driver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class AppiumDriverFactory {

    public static AndroidDriver<AndroidElement> appiumDriver;

    private AppiumDriverFactory() {
    }

    public static AndroidDriver<AndroidElement> getAppiumDriver() throws Exception {

        File appDir = new File("src");
        File app = new File(appDir, "app-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "NagarajEmulator123");
//        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 14);
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        appiumDriver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        appiumDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return appiumDriver;
    }

    public static void killDriverInstance() {
        appiumDriver.quit();
    }
}
