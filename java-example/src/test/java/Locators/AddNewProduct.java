package Locators;

import org.openqa.selenium.By;

public class AddNewProduct {

    public static final By TAB_GENERAL = By.cssSelector("td#content  div.tabs li:nth-child(1) a");
    public static final By TOGGLE_STATUS_INPUTS = By.cssSelector("input[name=status]"); // elements.size() = 2
    public static final By INPUT_NAME_EN = By.cssSelector("input[name=name\\[en\\]]");
    public static final By INPUT_NAME_RU = By.cssSelector("input[name=name\\[ru\\]]");
    public static final By CHECK_CATEGORIES = By.cssSelector("input[name=categories\\[\\]]"); // elements.size() = 3
    public static final By CHECK_GROUPS = By.cssSelector("input[name=product_groups\\[\\]"); // elements.size() = 3 man/woman/unisex
    public static final By INPUT_QUANTITY = By.cssSelector("input[name=quantity]");
    public static final By BTN_UPLOAD_IMAGE = By.cssSelector("input[name=new_images\\[\\]]");
    public static final By INPUT_DATE_FROM = By.cssSelector("input[name=date_valid_from]");
    public static final By INPUT_DATE_TO = By.cssSelector("input[name=date_valid_to]");

    public static final By TAB_INFORMATION = By.cssSelector("td#content  div.tabs li:nth-child(2) a");
    public static final By SELECT_MANUFACTURER = By.cssSelector("select[name=manufacturer_id]");
    public static final By INPUT_SHORT_DESCRIPTION_EN = By.cssSelector("input[name=short_description\\[en\\]]");
    public static final By INPUT_SHORT_DESCRIPTION_RU = By.cssSelector("input[name=short_description\\[ru\\]]");
    public static final By INPUT_HEAD_TITLE_EN = By.cssSelector("input[name=head_title\\[en\\]]");
    public static final By INPUT_HEAD_TITLE_RU = By.cssSelector("input[name=head_title\\[ru\\]]");

    public static final By TAB_PRICES = By.cssSelector("td#content  div.tabs li:nth-child(4) a");
    public static final By INPUT_PURCHASE = By.cssSelector("input[name=purchase_price]");
    public static final By SELECT_PURCHASE_CORRENCY = By.cssSelector("select[name=purchase_price_currency_code]");
    public static final By INPUT_PRICE_USD = By.cssSelector("input[name=prices\\[USD\\]]");
    public static final By INPUT_PRICE_EUR = By.cssSelector("input[name=prices\\[EUR\\]]");
    public static final By INPUT_PRICE_RUR = By.cssSelector("input[name=prices\\[RUR\\]]");

    public static final By BTN_SAVE = By.cssSelector("button[name=save]");
    public static final By BTN_CANCEL = By.cssSelector("button[name=cancel]");
    public static final By BTN_DELETE = By.cssSelector("button[name=delete]");

    public static final By NOTICE_ERROR = By.cssSelector("div#notices div.notice.errors"); // проверить видимость

}
