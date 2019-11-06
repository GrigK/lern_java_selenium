package Locators;

import org.openqa.selenium.By;

public class AdminCountries {
    public static final By TABLE_ALL_COUNTRIES = By.cssSelector("form[name='countries_form'] tbody");
    public static final By TABLE_ROWS = By.cssSelector("tr.row");
    public static final By COUNTRY_NAME = By.cssSelector("tr.row td:nth-child(5)");
    public static final By QUANTITY_SUBZONES = By.cssSelector("tr.row td:nth-child(6)");
    public static final By SUBZONE_LINK = By.cssSelector("tr.row td:nth-child(5) a");


    public static final By TABLE_SUBZONES = By.cssSelector("table#table-zones");
    public static final By TABLE_ROWS_SUBZONES = By.cssSelector("tr");
    public static final By COUNTRY_NAME_SUBZONE = By.cssSelector("tr td:nth-child(3)");
}
