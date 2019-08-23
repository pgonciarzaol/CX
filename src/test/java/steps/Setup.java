package steps;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import cucumber.deps.com.thoughtworks.xstream.mapper.Mapper;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

class Setup {

    static  AndroidDriver<AndroidElement> capabilities() throws MalformedURLException
    {
        String appiumHost = "";
        if(System.getProperty("appium.host") == null)
            appiumHost="127.0.0.1";
        else
            appiumHost=System.getProperty("appium.host");


        AndroidDriver<AndroidElement>  driver;


        File appDir = new File("src/apk");
        File app = new File(appDir, "cx.apk");

        DesiredCapabilities capabilities = new DesiredCapabilities();
// EMULATOR
//        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus_6_API_27");
// REAL DEVICE
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
// UNIVERSAL
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
//        capabilities.setCapability(MobileCapabilityType.NO_RESET,"true");
        capabilities.setCapability("newCommandTimeout",300);
        capabilities.setCapability("automationName","UiAutomator2");
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("appPackage", "com.cxinventor.file.explorer");
        capabilities.setCapability("appActivity","com.alphainventor.filemanager.activity.MainActivity");
        driver = new AndroidDriver(new URL("http://" + appiumHost + ":4723/wd/hub"), capabilities);
        return driver;
    }

}