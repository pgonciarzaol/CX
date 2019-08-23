package Variables;

public class Xpaths {
    private static String textToXpath(String text) {
        return "//*[@text='" + text + "']";
    }
    private static String packageToXpath(String text) {
        return "//*[@package='" + text + "']";
    }

    public static String cxAppPackage = packageToXpath("com.cxinventor.file.explorer:id/drawer_layout"),
    sdCardButton = "(//android.widget.ImageView[@content-desc=\"icon\"])[2]";
}
