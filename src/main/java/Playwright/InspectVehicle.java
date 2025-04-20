package Playwright;


import Pages.*;
import Utilities.ConfigHandler;
import Utilities.PlaywrightFactory;
import com.microsoft.playwright.Page;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;

public class InspectVehicle   {

    private Page page;
   public String env;
    ConfigHandler configHandler;

    @BeforeClass
    public void settingEnvOpenBrowser() throws IOException {
        configHandler = new ConfigHandler();
        env = configHandler.getPropertyValue("env");
        page = PlaywrightFactory.initiatePage();
        page.navigate(configHandler.getPropertyValue(env+"url"));

    }

    @Test(testName = "Booth operator Add plated Vehicle", priority = 1)
    private void inspectPlatedVehicle() throws SQLException, IOException {
        LoginPage loginPage = new LoginPage(page);
        ServiceSelectionPage serviceSelectionPage = new ServiceSelectionPage(page);
        loginPage.loginToSystem();
        serviceSelectionPage.selectService();
        SearchVehiclePage searchVehiclePage = new SearchVehiclePage(page);
        searchVehiclePage.searchByChassis();
        VehicleDetailsPage vehicleDetailsPage = new VehicleDetailsPage(page);
        vehicleDetailsPage.fillVehicleDetails();
        PaymentPage paymentPage = new PaymentPage(page);
        paymentPage.payByAamalyCash();


    }


}
