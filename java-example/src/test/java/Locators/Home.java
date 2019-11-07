package Locators;

import org.openqa.selenium.By;


public class Home {
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

    public static final By LOGIN_EMAIL = By.cssSelector("form[name=login_form] input[name=email]");
    public static final By LOGIN_PASSWORD = By.cssSelector("form[name=login_form] input[type=password]");
    public static final By LOGIN_BTN_ENTER = By.cssSelector("button[name=login]");

    public static final By USER_ACCOUNT_BOX = By.cssSelector("#box-account");
    public static final String USER_LOGOUT_URL = "http://litecart.local/ru/logout";

}
