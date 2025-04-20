package Utilities;

import com.microsoft.playwright.*;

import java.awt.*;
import java.io.IOException;

public class PlaywrightFactory {

    public static String env;
    private static Playwright playwright;
    private static Browser browser;

    public static Page initiatePage() throws IOException {

        BrowserType browserType = null;
        ConfigHandler configHandler = new ConfigHandler();
        env = configHandler.getPropertyValue("env");
        String browsername = configHandler.getPropertyValue("browser");

        if (playwright == null) {
            playwright = Playwright.create();
        }
        switch (browsername.toLowerCase()) {
            case "chrome":
                browserType = playwright.chromium();
                break;
            case "firefox":
                browserType = playwright.firefox();
                break;
            case "webkit":
                browserType = playwright.webkit();
                break;
        }

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        browser = browserType.launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(10));
        BrowserContext cont = browser.newContext(new Browser.NewContextOptions().setIgnoreHTTPSErrors(true).setViewportSize((int)screensize.getWidth(), (int)screensize.getHeight()));// Ignore SSL certificate warnings
        cont.clearCookies();
        cont.clearPermissions();
        Page page = cont.newPage();
        return page;

    }
    public static void closeBrowser(){
        if (browser!=null){
            browser.close();
            playwright.close();
        }
    }

}
