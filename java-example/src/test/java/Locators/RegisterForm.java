package Locators;

import org.openqa.selenium.By;

public class RegisterForm {
    public static final By FIRST_NAME = By.cssSelector("input[name=firstname]");
    public static final By LAST_NAME = By.cssSelector("input[name=lastname]");
    public static final By ADDR_1 = By.cssSelector("input[name=address1]");
    public static final By ADDR_2 = By.cssSelector("input[name=address2]");
    public static final By POST_CODE = By.cssSelector("input[name=postcode]");
    public static final By CITY = By.cssSelector("input[name=city]");
    public static final By COUNTRY_LIST = By.cssSelector("span.select2");
    public static final By SELECTOR_COUNTRY = By.cssSelector("[id ^= select2-country_code]");
    public static final By SELECT_COUNTRY_US = By.cssSelector("li.select2-results__option[id $= US]");
    public static final By SELECT_COUNTRY_UK = By.cssSelector("li.select2-results__option[id $= GB]");
    public static final By SELECT_COUNTRY_RU = By.cssSelector("li.select2-results__option[id $= RU]");
    public static final By SELECT_ZONE_CODE = By.name("zone_code");
//    public static final By SELECT_ZONE_CODE_WAIT = By.cssSelector("select[name=zone_code] option[value=KS]");


    public static final By EMAIL = By.cssSelector("input[name=email]");
    public static final By PHONE = By.cssSelector("input[name=phone]");
    public static final By SUBSCRIBE_CHECK = By.cssSelector("input[name=newsletter]");
    public static final By PASSWORD = By.cssSelector("input[name=password]");
    public static final By CONFIRMED_PASSWORD = By.cssSelector("input[name=confirmed_password]");
    public static final By BTN_CREATE_ACCOUNT = By.cssSelector("button[name=create_account]");
}
