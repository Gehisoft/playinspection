package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;

import java.util.Locale;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PaymentPage extends BasePage{
    public PaymentPage(Page page) {
        super(page);
    }


    private final String payoptionsarrowselector = "span[class='select2-arrow'] b";
    private final String aamalypaymentselector = "li[class='select2-results-dept-0 select2-result select2-result-selectable'] div[class='select2-result-label']";

    public void payByAamalyCash(){

    clickButton(payoptionsarrowselector);
    page.locator("#select2-drop").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    clickButton(aamalypaymentselector);
    }

}
