package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.Random;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchVehiclePage extends BasePage {
    public SearchVehiclePage(Page page) {
        super(page);
    }

    private final String searchpagetitle = "div[class='box-header grd-white color-silver-dark corner-top'] span";
    private final String searchbychassisbtn = "#insSlctVhlForm\\:vin-tab";
    private final String searchbtn = "[id^='insSlctVhlForm:j_idt110']";


    public void searchByChassis() {

        Locator searchtitle = page.locator(searchpagetitle);
        assertThat(searchtitle).isVisible();

        clickButton(searchbychassisbtn);

        String randomcharacterslist = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        Random random = new Random();

        for (int i = 0; i <= 16; i++) {
            String randomChar = String.valueOf(randomcharacterslist.charAt(random.nextInt(randomcharacterslist.length())));
            String chassisLocator = "#chassis-no-" + i;
            page.locator(chassisLocator).waitFor();
            page.locator(chassisLocator).press(randomChar);

        }
        clickButton(searchbtn);

    }


}
