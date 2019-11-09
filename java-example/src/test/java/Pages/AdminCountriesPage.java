package Pages;

import Locators.AdminCountries;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AdminCountriesPage extends Page {
    public AdminCountriesPage(WebDriver driver) {
        super(driver, "http://litecart.local/admin/?app=countries&doc=countries");
    }

    public List<WebElement> getCountrieNames(){
        assert isElementPresent(AdminCountries.TABLE_ALL_COUNTRIES) : "Table countries not found";
        WebElement tableCounties = driver.findElement(AdminCountries.TABLE_ALL_COUNTRIES);
        return tableCounties.findElements(AdminCountries.SUBZONE_LINK);
    }

    public EditCountryPage editCountry(WebElement elementNameCountry){
        String link = elementNameCountry.getAttribute("href");
        elementNameCountry.click();
        assert isElementDisappeared(elementNameCountry) : "Not open edit country: " + link;
        return new EditCountryPage(driver, link);
    }
}
