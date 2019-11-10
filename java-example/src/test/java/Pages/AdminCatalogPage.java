package Pages;

import org.openqa.selenium.WebDriver;
import Locators.AdminCatalog;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AdminCatalogPage extends Page {
    public AdminCatalogPage(WebDriver driver) {
        super(driver, "http://litecart.local/admin/?app=catalog&doc=catalog");
    }

    public void submitAddNewProduct() {
        clickElement(AdminCatalog.BTN_NEW_PRODUCT);
        assert isElementPresent(AdminCatalog.NEXT_PAGE_ADD_NEW_PRODUCT) : "Not found page 'Add new product'";
    }

    public List<String> getProductsInRubberDucks(){
        assert isElementPresent(AdminCatalog.PRODUCT_LINK);
        List<String> links = new ArrayList<>();

        driver.findElements(AdminCatalog.PRODUCT_LINK)
                .forEach((WebElement el) -> {
                    links.add(el.getAttribute("href"));
                });
        return links;
    }

}
