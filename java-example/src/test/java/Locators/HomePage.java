package Locators;

import org.openqa.selenium.By;


public class HomePage {
    public static final By TITLE = By.cssSelector("title");
    public static final By BOX_MOST_POPULAR = By.cssSelector("#box-most-popular");
    public static final By BOX_CAMPAIGNS = By.cssSelector("#box-campaigns");
    public static final By BOX_LATEST_PRODUCTS = By.cssSelector("#box-latest-products");
    public static final By PRODUCTS = By.cssSelector(".product");
    public static final By STICKER = By.cssSelector(".sticker");

    public static final By PRODUCT_IN_LIST = By.cssSelector("li.product");
    public static final By PRODUCT_LINK = By.cssSelector("li.product a");
    public static final By NAME_PRODUCT = By.cssSelector("div.name");
    public static final By FIRST_PRICE_PRODUCT = By.cssSelector("div.price-wrapper s"); //.regular-price
    public static final By SECOND_PRICE_PRODUCT = By.cssSelector("div.price-wrapper strong"); //.campaign-price
}
