package Locators;

import org.openqa.selenium.By;

public class AdminCatalog {
    public static final By BTN_NEW_PRODUCT = By.cssSelector("td#content > div a:nth-child(2)");
    public static final By NEXT_PAGE_ADD_NEW_PRODUCT = By.id("tab-general");
    public static final By CLOSED_FOLDER = By.cssSelector("form[name=catalog_form] table tr.row td:nth-child(3) i.fa.fa-folder");
    public static final By PRODUCT_LINK = By.cssSelector("form[name=catalog_form] table tr.row td:nth-child(3) img + a");
}
