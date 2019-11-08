package Locators;

import org.openqa.selenium.By;

public class ProductCard {
    public static final By PRODUCT_TITLE = By.cssSelector("h1.title");
    public static final By PRODUCT_FIRST_PRICE = By.cssSelector("div.information div.price-wrapper s"); //.regular-price
    public static final By PRODUCT_SECOND_PRICE = By.cssSelector("div.information div.price-wrapper strong"); //.campaign-price
    public static final By SELECT_SIZE = By.cssSelector("td.options select");
    public static final By INPUT_QUANTITY = By.cssSelector("input[name=quantity]");
    public static final By BTN_ADD_TO_CART = By.cssSelector("button[name=add_cart_product]");
    public static final By CART = By.cssSelector("div#cart");
    public static final By QUANTITY_IN_CART = By.cssSelector("div#cart span.quantity");
}
