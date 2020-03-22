package assignment;

import com.mobile.assignment.Configuration;
import com.mobile.assignment.driver.AppiumDriverFactory;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static org.apache.log4j.Logger.getLogger;

public class TestBase {

    private static Logger logger = getLogger(CalendarTest.class.getName());
    private static String adbPath = Configuration.SDK_PATH.getValue() + "platform-tools" + File.separator + "adb";
    private static String emulatorPath = Configuration.SDK_PATH.getValue() + "tools" + File.separator + "emulator";
    public static AppiumDriverLocalService service;
    public AndroidDriver<AndroidElement> appiumDriver;

    public static void launchEmulator(String nameOfAVD) {
        logger.info("Starting emulator for '" + nameOfAVD + "' ...");
        String[] aCommand = new String[] { emulatorPath, "-avd", nameOfAVD };
        try {
            Process process = new ProcessBuilder(aCommand).start();
            process.waitFor(10, TimeUnit.SECONDS);
            logger.info("Emulator launched successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeEmulator() {
        logger.info("Killing emulator...");
        String[] aCommand = new String[] { adbPath, "emu", "kill" };
        try {
            Process process = new ProcessBuilder(aCommand).start();
            process.waitFor(1, TimeUnit.SECONDS);
            logger.info("Emulator closed successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeMethod
    public void setUp() throws Exception {
        logger.info("----------------------------------Start Test-------------------------------------------------");
        startServer();
        launchEmulator(Configuration.EMULATOR_NAME.getValue());
        appiumDriver = AppiumDriverFactory.getAppiumDriver();
        appiumDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        if (appiumDriver != null) {
            AppiumDriverFactory.killDriverInstance();
        }
        closeEmulator();
        service.stop();
        logger.info("Stopping Appium Server....");
        logger.info("----------------------------------End Test-------------------------------------------------");
    }

    public void startServer() throws InterruptedException {
        service = AppiumDriverLocalService
                .buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File(Configuration.DRIVER_EXE_PATH.getValue()))
                .withAppiumJS(new File(Configuration.APPIUM_JS_Path.getValue()))
                .withIPAddress("127.0.0.1").usingPort(4723));
        service.start();
        logger.info("Starting Appium Server....");
        Thread.sleep(5000);
    }
}
