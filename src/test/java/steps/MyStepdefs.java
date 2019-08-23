package steps;

import Variables.Xpaths;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import utils.TestUtils;

import java.io.IOException;
import java.net.MalformedURLException;

public class MyStepdefs {

    private static AndroidDriver<AndroidElement> driver;
    private static TestUtils utils;

    @Before
    public void start(Scenario scenario) throws IOException {
        printScenarioName(scenario);
        try {
            utils.checkIfElementExistsByXpath(Xpaths.cxAppPackage);
        } catch (NullPointerException e) {
            iRunCXApp();
        }
    }

    private void printScenarioName(Scenario scenario){
        String scenarioName="Starting scenario - " + scenario.getName();
        int nameLength = scenarioName.length();
        System.out.println("\n");
        String decorator = "";
        while (nameLength>0){
            decorator = decorator + "-";
            nameLength-=1;
        }
        System.out.println(decorator);
        System.out.println(scenarioName);

        nameLength = scenarioName.length();
        decorator="";
        while (nameLength>0){
            decorator = decorator + "-";
            nameLength-=1;
        }
        System.out.println(decorator);
    }
    @Given("I click sd card button")
    public void iClickSdCardButton() {
        driver.findElementByXPath(Xpaths.sdCardButton).click();
    }

    public void iRunCXApp() throws MalformedURLException {
        System.out.println("\nI run Satel app");
        driver = Setup.capabilities();
        utils = new TestUtils(driver);
        utils.addDelay(1000);
    }
}