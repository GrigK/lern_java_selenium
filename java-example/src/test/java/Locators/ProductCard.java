package Locators;

import org.openqa.selenium.By;

public class ProductCard {
    public static final By PRODUCT_TITLE = By.cssSelector("h1.title");
    public static final By PRODUCT_FIRST_PRICE = By.cssSelector("div.information div.price-wrapper s"); //.regular-price
    public static final By PRODUCT_SECOND_PRICE = By.cssSelector("div.information div.price-wrapper strong"); //.campaign-price
}
