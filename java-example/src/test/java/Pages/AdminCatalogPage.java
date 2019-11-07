package Pages;

import org.openqa.selenium.WebDriver;
import Locators.AdminCatalog;

public class AdminCatalogPage extends Page {
    public AdminCatalogPage(WebDriver driver) {
        super(driver, "http://litecart.local/admin/?app=catalog&doc=catalog");
    }

    public void submitAddNewProduct() {
        clickElement(AdminCatalog.BTN_NEW_PRODUCT);
        assert isElementPresent(AdminCatalog.NEXT_PAGE_ADD_NEW_PRODUCT) : "Not found page 'Add new product'";
    }

}
