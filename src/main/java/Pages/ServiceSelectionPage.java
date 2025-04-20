package Pages;

import Utilities.DBconnection;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceSelectionPage extends BasePage {
    public ServiceSelectionPage(Page page) {
        super(page);
    }


    private final String burgermenubtn = ".ui-icon.ui-icon-bars.ui-icon-shadow";
    private final String newvehicleinspectionbtn = "li:nth-child(5) div:nth-child(1) div:nth-child(1) a:nth-child(1)";
    private final String continuebtn = ".pt-10.btn.btn-large.proceed-btn.btn-containue";


    public void selectService() throws SQLException, IOException {


        clickButton(burgermenubtn);
        clickButton(newvehicleinspectionbtn);

//        Uncheck any active service on the screen

        Locator activeservices = page.locator("//a[@type='button' and contains(@class, 'active')]");
        activeservices.waitFor();
        int countactiveservices = activeservices.count();

        if (countactiveservices > 0) {
            for (int i = 0; i < countactiveservices; i++) {
                activeservices.nth(i).click();
            }
        }

//        Selecting the service that has its code mentioned in the configuration file
        String service_id = getProperty("serviceid");
        DBconnection dBcon = new DBconnection();
        ResultSet queryresult = dBcon.reteiveDBInformation("SELECT cis.NAME_LANG1   FROM CI_INS_SERVICES cis  WHERE id =" + service_id);
        while (queryresult.next()) {
            String servicename = queryresult.getString(1);
            System.out.println("The service that is under test in " + servicename);
            page.locator("text=" + servicename).click();
        }



        clickButton(continuebtn);

    }
}
