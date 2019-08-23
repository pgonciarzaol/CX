package utils;



import Variables.Vars;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import steps.MyStepdefs;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TestUtils {

    private AndroidDriver<AndroidElement> driver;

    public TestUtils(AndroidDriver<AndroidElement> driver) {
        this.driver = driver;
    }

    private static MyStepdefs steps;

    public String randomString(int targetStringLength) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        System.out.println(generatedString);
        return generatedString;
    }

    public boolean checkIfElementExistsById(String elementId) {
        boolean isExists = true;
        try {
            driver.findElementById(elementId);
        } catch (NoSuchElementException e) {
            isExists = false;
        }
        return isExists;
    }

    public boolean checkIfElementExistsByClassName(String elementClassName) {
        boolean isExists = true;
        try {
            driver.findElementByClassName(elementClassName);
        } catch (NoSuchElementException e) {
            isExists = false;
        }
        return isExists;
    }

    public boolean checkIfElementExistsByXpath(String elementXpath) {
        boolean isExists = true;
        try {
            driver.findElementByXPath(elementXpath);
            System.out.println("\nElement was found: " + elementXpath);
        } catch (NoSuchElementException e) {
            System.out.println("\nCouldn't find element with xpath: " + elementXpath);
            isExists = false;
        }
        return isExists;
    }

    public void addDelay(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    public void captureScreenshots() throws IOException {
        String folder_name = "screenshotForPlayStore";
        File f = driver.getScreenshotAs(OutputType.FILE);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        String file_name = df.format(new Date()) + ".png";
        FileUtils.copyFile(f, new File(folder_name + "/" + file_name));
    }

    private void tapCoordinates(int x, int y) {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.tap(PointOption.point(x, y)).perform();
    }

    private void scrollFromDownToUpSmall() {

        int scrHeight = driver.manage().window().getSize().getHeight(); // To get the mobile screen height
        int scrWidth = driver.manage().window().getSize().getWidth();// To get the mobile screen width
        TouchAction touchAction = new TouchAction(driver);

        touchAction.press(PointOption.point(scrWidth / 2, (scrHeight / 5) * 4))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(scrWidth / 2, scrHeight / 5 * 3))
                .release()
                .perform();

    }

    private void scrollFromUpToDownSmall() {

        int scrHeight = driver.manage().window().getSize().getHeight(); // To get the mobile screen height
        int scrWidth = driver.manage().window().getSize().getWidth();// To get the mobile screen width
        TouchAction touchAction = new TouchAction(driver);

        touchAction.press(PointOption.point(scrWidth / 2, scrHeight / 5 * 3))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(scrWidth / 2, (scrHeight / 5) * 4))
                .release()
                .perform();

    }

    public void scrollUp() {
        int scrHeight = driver.manage().window().getSize().getHeight(); // To get the mobile screen height
        int scrWidth = driver.manage().window().getSize().getWidth();// To get the mobile screen width
        TouchAction touchAction = new TouchAction(driver);

        touchAction.press(PointOption.point(scrWidth / 2, (scrHeight / 5) * 4))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(scrWidth / 2, scrHeight / 5))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .release()
                .perform();
    }

    public void clearTextFullyID(String ID) {
        int stringLength = driver.findElementById(ID).getText().length();

        for (int i = 0; i < stringLength; i++) {
            driver.pressKeyCode(22); // "KEYCODE_DPAD_RIGHT"
        }

        for (int i = 0; i < stringLength; i++) {
            driver.pressKeyCode(67); // "KEYCODE_DEL"
        }
    }

    public void replaceTextID(String ID, String newText) {

        int stringLength = driver.findElementById(ID).getText().length();

        for (int i = 0; i < stringLength; i++) {
            driver.pressKeyCode(22); // "KEYCODE_DPAD_RIGHT"
        }
        addDelay(200);

        driver.findElementById(ID).sendKeys(newText);
    }

    private void setCountTimeOld() {
        Vars.startTime = System.currentTimeMillis();
    }

    public long setCountTime() {
        return System.currentTimeMillis();
    }

    public boolean timeCounter(long startTime, long secondsToCount) {
//        is true until times runs out
        return System.currentTimeMillis() - startTime < secondsToCount * 1000;
    }

    private boolean timeCounterOld(long seconds) {
        //        is true until times runs out
        System.out.println("Time counter set for " + Long.toString(seconds) + " seconds. " + Long.toString((System.currentTimeMillis() - Vars.startTime) / 1000) + " seconds past");
        return System.currentTimeMillis() - Vars.startTime < seconds * 1000;
    }

    public boolean timeCounterMillis(long millis) {
        boolean is = System.currentTimeMillis() - Vars.startTime < millis;
        return is;
    }

    public void scrollFromDownToUpUntilXpath(String Xpath, long timeLimit) {
        Vars.is = checkIfElementExistsByXpath(Xpath);
        Vars.tempInt = 0;
        setCountTimeOld();
        while (!Vars.is && timeCounterOld(timeLimit)) {
            Vars.tempInt = Vars.tempInt + 1;
            System.out.println("\n Scrolled times: " + Vars.tempInt);
            scrollFromDownToUpSmall();
            Vars.is = checkIfElementExistsByXpath(Xpath);
        }
    }

    public void scrollFromDownToUpUntilId(String Id, long seconds) {
        Vars.is = checkIfElementExistsById(Id);
        Vars.tempInt = 0;
        setCountTimeOld();
        while (!Vars.is && timeCounterOld(seconds)) {
            Vars.tempInt = Vars.tempInt + 1;
            System.out.println("\n Scrolled times: " + Vars.tempInt);
            scrollFromDownToUpSmall();
            Vars.is = checkIfElementExistsById(Id);
        }
    }

    public void scrollFromUpToDownUntilText(String text, long seconds) {
        Vars.is = checkIfElementExistsByText(text);
        Vars.tempInt = 0;
        setCountTimeOld();
        while (!Vars.is && timeCounterOld(seconds)) {
            Vars.tempInt = Vars.tempInt + 1;
            System.out.println("\n Scrolled times: " + Vars.tempInt);
            scrollFromUpToDownSmall();
            Vars.is = checkIfElementExistsByText(text);
        }
    }

    public void tapCenterOfText(String text) {
        TouchAction touchAction = new TouchAction(driver);

        Point point = driver.findElementByXPath("//*[@text='" + text + "']").getCenter();


        touchAction.tap(PointOption.point(point.getX(), point.getY()))
                .perform();


    }

    public int random(int maxValue) {
        int randomInt = ThreadLocalRandom.current().nextInt(maxValue);
        return randomInt;
    }

    public void longPressXpath(String xpath, int millis) {

        WebElement expandList = driver.findElementByXPath(xpath);
        TouchAction action = new TouchAction(driver);
        action.longPress(LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(expandList)).withDuration(Duration.ofMillis(millis))).perform();
    }

    public void longPressText(String text, int millis) {

        WebElement expandList = driver.findElementByXPath("//*[@text='" + text + "']");
        TouchAction action = new TouchAction(driver);
        action.longPress(LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(expandList)).withDuration(Duration.ofMillis(millis))).perform();
    }

    public void longPressID(String ID, int millis) {

        WebElement expandList = driver.findElementById(ID);
        TouchAction action = new TouchAction(driver);
        action.longPress(LongPressOptions.longPressOptions()
                .withElement(ElementOption.element(expandList)).withDuration(Duration.ofMillis(millis))).perform();
    }

    public void scrollUpUntilXpathOr5Moves(String Xpath) {
        boolean is = checkIfElementExistsByXpath(Xpath);
        int a = 1;
        while (!is && a < 6) {
            scrollFromDownToUpSmall();
            is = checkIfElementExistsByXpath(Xpath);
            a = a + 1;
        }
    }

    public String changeFirstCharacterOfAwordToUpperCase(String word) {
        String newWord = word.substring(0, 1).toUpperCase() + word.substring(1);
        System.out.println("\nThe new word starting from upper case is: " + newWord);
        return newWord;
    }

    public void tapCenterOfXpath(String xpath) {
        TouchAction touchAction = new TouchAction(driver);

        Point point = driver.findElementByXPath(xpath).getCenter();


        touchAction.tap(PointOption.point(point.getX(), point.getY()))
                .perform();
    }

    public void tapCenterOfID(String ID) {
        TouchAction touchAction = new TouchAction(driver);

        Point point = driver.findElementById(ID).getCenter();


        touchAction.tap(PointOption.point(point.getX(), point.getY()))
                .perform();
    }

    public void waitForItID(String ID, long seconds) {
        Vars.is = false;
        Vars.tempInt = 0;
        setCountTimeOld();
        while (!Vars.is && timeCounterOld(seconds)) {
            System.out.println("\n time counter is: " + timeCounterOld(seconds));
            try {
                addDelay(500);
                System.out.println("\n Tried to find id: \n" + ID + "\n times: " + Vars.tempInt);
                Vars.tempInt = Vars.tempInt + 1;
                System.out.println("\n Tried to find id: \n" + ID + "\n times: " + Vars.tempInt);
                driver.findElementById(ID);
                System.out.println("\n I've found element with id: " + ID);
                Vars.is = true;
            } catch (Exception e) {
                System.out.println("\n Couldn't find it..");
            }
        }
    }

    public void waitForItIDWithRefreshrate(String ID, long seconds, int refreshMilis) {
        Vars.is = false;
        Vars.tempInt = 0;
        setCountTimeOld();
        while (!Vars.is && timeCounterOld(seconds)) {
            System.out.println("\n time counter is: " + timeCounterOld(seconds));
            try {
                addDelay(refreshMilis);
                System.out.println("\n Tried to find id: \n" + ID + "\n times: " + Vars.tempInt);
                Vars.tempInt = Vars.tempInt + 1;
                System.out.println("\n Tried to find id: \n" + ID + "\n times: " + Vars.tempInt);
                driver.findElementById(ID);
                System.out.println("\n I've found element with id: " + ID);
                Vars.is = true;
            } catch (Exception e) {
                System.out.println("\n Couldn't find it..");
            }
        }
    }

    public void waitForItXpath(String xpath, long seconds) {
        Vars.is = false;
        Vars.tempInt = 0;
        setCountTimeOld();
        while (!Vars.is && timeCounterOld(seconds)) {
            System.out.println("\n time counter is: " + timeCounterOld(seconds));
            try {
                addDelay(500);
                System.out.println("\n Tried to find xpath: \n" + xpath + " times: " + Vars.tempInt);
                Vars.tempInt = Vars.tempInt + 1;
                driver.findElementByXPath(xpath);
                System.out.println("\n I've found element with xpath: " + xpath);
                Vars.is = true;
            } catch (NoSuchElementException e) {
                System.out.println("\n Couldn't find it..");
            }
        }
    }

    public void waitForItText(String text, long seconds) {
        boolean is = false;
        int temp = 0;
        setCountTimeOld();
        while (!is && timeCounterOld(seconds)) {
            System.out.println("\n time counter is: " + timeCounterOld(seconds));
            try {
                addDelay(500);
                System.out.println("\n Tried to find text: " + text + " times: " + temp);
                temp += 1;
                driver.findElementByXPath("//*[@text='" + text + "']");
                System.out.println("\n I've found element with text: " + text);
                is = true;
            } catch (Exception e) {

                System.out.println("\n Couldn't find it..");
            }
        }
    }

    public void findText(String text) {
        driver.findElementByXPath("//*[@text='" + text + "']");
    }

    public boolean checkIfElementExistsByText(String text) {
        boolean isExists = true;
        try {
            driver.findElementByXPath("//*[@text='" + text + "']");
            System.out.println("\nText was found: " + text);
        } catch (Exception e) {
            isExists = false;
        }
        return isExists;
    }

    public String textToXpath(String text) {
        return "//*[@text='" + text + "']";
    }

    public String capitalizeFirstLetter(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public String upInHierarchy(String xpath, int floors) {
        while (floors > 0) {
            xpath = xpath + "/..";
            floors = floors - 1;
        }
        return xpath;
    }

    public void clickUntilDisappearsId(String id, long seconds) {
        Vars.is = true;
        int j = 0;
        setCountTimeOld();
        while (Vars.is && timeCounterOld(seconds)) {
            System.out.println();
            driver.findElementById(id).click();
            j = j + 1;
            System.out.println("\nclicked " + id + " times: " + j);
            Vars.is = checkIfElementExistsById(id);
        }
    }

    public void getFocusBack() {
        addDelay(300);
        driver.pressKey(new KeyEvent(AndroidKey.APP_SWITCH));
        addDelay(300);
        tapCoordinates(500, 900);
    }

    private void tryTapElementWithText(String text) {
        try {
            tapCenterOfText(text);
            System.out.println("\nTapped element with text" + text);
        } catch (Exception e1) {
            System.out.println("Couldn't tap element with text: " + text);
        }
    }

    private void waitForTextToDissapear(String text, long timeoutInSeconds) {
        while (checkIfElementExistsByText(text) && timeCounterOld(timeoutInSeconds)) {
            addDelay(200);
            System.out.println("\nCan see text: " + text);
        }
        System.out.println("\nText " + text + " is shown no more.");
    }

    public long centralConnectionTime(String centralName) throws Exception {
        try {
            Long timeW = new TimeLimitedCodeBlock().runWithTimeout(() -> {
                boolean crash = false;
                if (Vars.versa) {
                    Vars.name = "versa ";
                }
                waitForItText(centralName, 15);
                long clickVersaTime = System.currentTimeMillis();
                if (!checkIfElementExistsByText(centralName)) {
                    return (long) 0;
                }
                tryTapElementWithText(centralName);
                setConnectionDate();
                setCountTimeOld();
                Vars.lastPleaseWaitOccurance = System.currentTimeMillis();
                waitForItText("Connecting", 5);
                setLastWaitOccurance();
                waitForItText("Please wait…", 2);
                waitForTextToDissapear("Please wait…", 60);
                waitForItText("Downloading data...", 1);
                waitForTextToDissapear("Downloading data...", 60);
                System.out.println("waited");
                return Vars.lastPleaseWaitOccurance - clickVersaTime;
            }, 240, TimeUnit.SECONDS);
            return timeW;
        } catch (Exception e) {
            System.out.println("Catched Exception in centralConnectionTime method");
            throw e;
        }
    }

    public String logFilePath(){
        if(Vars.testingComputer.equals("laptop")){
            return "/home/testy/ftp/";
        }
        else if (Vars.testingComputer.equals("workstation")){
            return "";
        }
        return "";
    }

    public void downloadLogsFromPhone() throws FileNotFoundException{
    try {
        String logsFileName = "Satel" + Vars.connectionDateAddedToFileName + ".txt";
        System.out.println("\nlogs file name is:" + logsFileName);
        byte[] file = driver.pullFile("/storage/emulated/0/Android/data/pl.satel.versacontrol/files/Satel_logs/" + logsFileName);
        String plik = new String(file);
        PrintWriter out = new PrintWriter(logFilePath() + logsFileName);
        out.println(plik);
        out.close();
//        System.out.println(plik);
    }catch (Exception e){
        System.out.println("Pull file error");
    }

    }
    public void setConnectionDate(){
        String temp = driver.getDeviceTime();
        Vars.timeAfterTappingCentralButton = temp.substring(0,10) + " " + temp.substring(11,19);
        Vars.connectionDateAddedToFileName = temp.substring(0,10);
        System.out.println(Vars.timeAfterTappingCentralButton);
            }
    private void setLastWaitOccurance() {
        while (checkIfElementExistsByText("Connecting") && timeCounterOld(60)) {
            Vars.lastPleaseWaitOccurance = System.currentTimeMillis();
        }
    }



    public String wasConnectionSuccessfulPrinter() {
        String temp = "temp";
        if (Vars.wasConnectionSuccessful == 1) {
            temp = "Connection succesful";
        }
        if (Vars.wasConnectionSuccessful == 2) {
            temp = "Connection failed";
        }
        if (Vars.wasConnectionSuccessful == 0) {
            temp = "Connection test not completed";
        }
        if (Vars.wasConnectionSuccessful == 3) {
            temp = "No connection popup";
        }
        return temp;
    }

    public void writeTextToFile(String text, String fileName) {
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(text);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }
    public void sendCommandToShell(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            System.out.println("the output stream is "+process.getOutputStream());
            BufferedReader reader=new BufferedReader( new InputStreamReader(process.getInputStream()));
            String s;
            while ((s = reader.readLine()) != null){
                System.out.println("The inout stream is " + s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}