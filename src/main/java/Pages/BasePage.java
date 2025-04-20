package Pages;

import Utilities.ConfigHandler;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.io.IOException;

public class BasePage {

    protected   Page page;
    public BasePage(Page page){
    this.page = page;
    }


    public void clickButton(String selector){
      page.locator(selector).waitFor();
      page.locator(selector).click();
    }


    public void fillText(String selector, String texttofill){
        page.locator(selector).focus();
        page.locator(selector).fill(texttofill);
    }

    public String getProperty(String propertyname) throws IOException {
        ConfigHandler configHandler = new ConfigHandler();
        return configHandler.getPropertyValue(propertyname);

    }

    public String getTextValue(String selector){
        return page.locator(selector).textContent();

    }

    public void waitForElemenet(String selector){
        page.locator(selector).waitFor(new Locator.WaitForOptions().setTimeout(300));
    }

}
