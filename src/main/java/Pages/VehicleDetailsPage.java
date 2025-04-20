package Pages;

import Utilities.DBconnection;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Pattern;

public class VehicleDetailsPage extends BasePage {
    public VehicleDetailsPage(Page page) {
        super(page);
    }

    public String transaction_no;
    public String chassis_no;
    public String sequence_no;

    public String vehicleclassname;
    public String vehiclemakename;
    public String vehiclemodelname;
    public String platedetails;


    private final String vehicleclassarrowselector = "div[id='s2id_formId:vehicleClass:vehicleClassField'] b";
    private final String vehicleclasstextselector = "div[id='select2-drop'] input[type='text']";

    private final String vehiclemaketextselector = "input[id='formId:vehicleMake:vehicleMakeField']";

    private final String vehiclemodeltextselector = "input[id='formId:vehicleModel:vehicleModelField']";

    private final String vehicleyeariconselector = "div[id='makeYearDiv'] span[class='add-on']";
    private final String vehicleyeartextselector = "input[id='formId:makeYear:makeYearField']";
    private final String vehicleyearpickerselector = "//span[normalize-space()='2023']";

    private final String vehiclesequencetextselector = "input[id='formId:vhlNumber:vhlNumberField']";

    private final String color1textselector = "div[id='s2id_formId:j_idt1001:0:colorElement0:colorElement0Color0Field'] b";
    private final String colortextlistselector = "div[id='select2-drop'] input[type='text']";

    private final String platedcheckboxselector = "div[id='uniform-formId:vehiclePlated'] span";
    private final String platenumbertxtselector = "input[id='formId:plateDetails:plate-number-id']";
    private final String platecode1arrowselector = "div[id='s2id_formId:plateDetails:plate-new-design-1:plate-new-design-1Field'] b";
    private final String platecodelst1selector = "div[id='select2-drop'] input[type='text']";
    private final String platecode2arrowselector = "div[id='s2id_formId:plateDetails:plate-new-design-2:plate-new-design-2Field'] b";
    private final String platecode2lstselector = "div[id='select2-drop'] input[type='text']";
    private final String platecode3arrowselector = "div[id='s2id_formId:plateDetails:plate-new-design-3:plate-new-design-3Field'] b";
    private final String platecode3lstselector = "div[id='select2-drop'] input[type='text']";
    private final String continuebtn = "a[id='formId:btn-continue']";


    public void fillVehicleDetails() throws IOException, SQLException {

//      Getting transaction number

        String pageurl = page.url();
        transaction_no = page.evaluate("() => new URL(window.location.href).searchParams.get('entityId')").toString();
        System.out.println("Transaction id = " + transaction_no);


//      Getting chassis number

        chassis_no = page.locator("div[id='formId:chassisNumber:chassisNumberFieldDisabled'] label").innerText();
        System.out.println("Chassis no is " + chassis_no);

//


        DBconnection dbcon = new DBconnection();
        ResultSet res = dbcon.reteiveDBInformation(
                "SELECT c.DESCRIPTION_LANG1 AS vehicle_class, k.NAME_LANG1 AS vehicle_make, m.NAME_LANG1 AS vehicle_model " +
                        "FROM CI_VHL_VEHICLE_MODELS m , CI_VHL_VEHICLE_MAKES k, CI_VHL_VEHICLE_CLASSES c " +
                        "WHERE c.ID = m.VCL_ID " +
                        "AND k.ID = m.VMK_ID " +
                        "AND c.ID = 3 " +
                        "AND k.STATUS = 1 " +
                        "AND m.STATUS = 1 " +
                        "AND k.NAME_LANG1 NOT LIKE '%.' " +
                        "ORDER BY DBMS_RANDOM.VALUE " +
                        "FETCH FIRST 1 ROW ONLY ");


        while (res.next()) {
            vehicleclassname = res.getString("vehicle_class");  //get vehicle class name
            vehiclemakename = res.getString("vehicle_make");      //get vehicle make name
            vehiclemodelname = res.getString("vehicle_model");     // get vehicle model name
        }
        System.out.println("vehicle class is " + vehicleclassname);
        System.out.println("Vehicle maker is " + vehiclemakename);
        System.out.println("Vehicle model is " + vehiclemodelname);
        res.close();

//          Choose vehicle class

        clickButton(vehicleclassarrowselector);
        fillText(vehicleclasstextselector, vehicleclassname);
        page.keyboard().press("Tab");


//          choose vehicle make
        Locator makerlocator = page.locator(vehiclemaketextselector);
        makerlocator.focus();
        page.waitForTimeout(1000);
        makerlocator.pressSequentially(vehiclemakename);
        page.locator("(//ul[@class='typeahead dropdown-menu'])[1]").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.locator("li[data-value='"+vehiclemakename+"']").click();
        makerlocator.press("Enter");
        page.waitForTimeout(1000);
//        page.keyboard().press("Enter");
//        page.keyboard().press("Tab");

//            choose vehicle model

        Locator modellocator = page.locator(vehiclemodeltextselector);
//        page.waitForTimeout(800);
        modellocator.focus();
        page.waitForTimeout(1000);
        modellocator.pressSequentially(vehiclemodelname);
        page.locator("(//ul[@class='typeahead dropdown-menu'])[2]").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        page.locator("li[data-value='"+vehiclemodelname+"']").click();
        modellocator.press("Enter");
        page.waitForTimeout(1000);
//        page.keyboard().press("Enter");
//        page.keyboard().press("Tab");

//          Select make year
        clickButton(vehicleyeariconselector);
        clickButton(vehicleyearpickerselector);
        page.locator(vehicleyeartextselector).press("Tab");


        Locator platecheckboxlocator = page.locator(platedcheckboxselector);

        if (platecheckboxlocator.isChecked()) {
            System.out.println("The check box is already marked as plated");
        } else {

            platecheckboxlocator.check();
        }

        Locator sequencelocator = page.locator(vehiclesequencetextselector);
        Random seqrandom = new Random();
        sequence_no = String.valueOf(10000 + seqrandom.nextInt(999999));
        page.waitForTimeout(800);
        sequencelocator.focus();
        sequencelocator.fill(sequence_no);
//        sequencelocator.press("Tab");

//      Filling the color

        clickButton(color1textselector);
        fillText(colortextlistselector, "Red");
        page.locator(colortextlistselector).press("Enter");


//       Filling plate information

        Locator platenumberlocator = page.locator(platenumbertxtselector);
        Random platerandom = new Random();
        String plateno = String.valueOf(1000 + platerandom.nextInt(9999));
        platenumberlocator.fill(plateno);

//      Selecting plate codes
        String[] code = new String[3];
        DBconnection platcodecon = new DBconnection();
        for (int i = 0; i <= 2; i++) {
            ResultSet platecoderesult = platcodecon.reteiveDBInformation("SELECT value AS CODE FROM CI_APT_PLATE_CHARS capc  ORDER BY DBMS_RANDOM.VALUE FETCH FIRST 1 ROW ONLY");
            while (platecoderesult.next()) {
                code[i] = platecoderesult.getString("CODE");

            }
            platecoderesult.close();
        }

        clickButton(platecode1arrowselector);
        fillText(platecodelst1selector, code[0]);
        page.locator(platecodelst1selector).press("Tab");

//        clickButton(platecode2arrowselector);
        fillText(platecode2lstselector, code[1]);
        page.locator(platecode2lstselector).press("Tab");

//        clickButton(platecode3arrowselector);
        fillText(platecode3lstselector, code[2]);
        page.locator(platecode2lstselector).press("Enter");

        platedetails = plateno + code[0] + code[1] + code[2];
        System.out.println("Vehicle Plate is " + platedetails);

        clickButton(continuebtn);
        page.waitForTimeout(5000);
        page.waitForURL(Pattern.compile(".*pay_collect_fees.*"));
//        page.waitForURL(Pattern.compile(".*dashboard.*"));

        ;

    }
}








