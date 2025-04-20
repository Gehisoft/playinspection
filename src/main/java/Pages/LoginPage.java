package Pages;

import Utilities.ConfigHandler;
import com.microsoft.playwright.Page;

import java.io.IOException;

public class LoginPage extends BasePage{



    public LoginPage(Page page) {
        super(page);
    }

    private final String usernametxt = "#j_username";
    private final String passwordtxt = "#j_password";



    public void loginToSystem() throws IOException {
        ConfigHandler configHandler = new ConfigHandler();
        String e = configHandler.getPropertyValue("env");
        fillText(usernametxt,configHandler.getPropertyValue(e+"username"));
        fillText(passwordtxt,configHandler.getPropertyValue(e+"password"));
        clickButton("input[value='Login']");

    }

}



