package Locators;

import org.openqa.selenium.By;

public class Cart {
    public static final By BTN_REMOVE = By.cssSelector("button[name=remove_cart_item]");
    public static final By TABLE_ORDER = By.cssSelector("div#order_confirmation-wrapper");

}
