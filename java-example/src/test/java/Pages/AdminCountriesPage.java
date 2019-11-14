package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AdminCountriesPage extends Page {
    public AdminCountriesPage(WebDriver driver) {
        super(driver, "http://litecart.local/admin/?app=countries&doc=countries");
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "form[name='countries_form'] tbody")
    public WebElement tableAllCountries;

    @FindBy(css = "form[name='countries_form'] tbody tr.row")
    public List<WebElement> tableAllRows;

    public static final By COUNTRY_NAME = By.cssSelector("td:nth-child(5)");
    public static final By QUANTITY_SUBZONES = By.cssSelector("td:nth-child(6)");
    public static final By SUBZONE_LINK = By.cssSelector("td:nth-child(5) a");


    @FindBy(css = "table#table-zones")
    public WebElement tableSubzones;

    @FindBy(css = "tr")
    public List<WebElement> tableSubzoneRows;

    public static final By COUNTRY_NAME_SUBZONE = By.cssSelector("td:nth-child(3)");


    public EditCountryPage editCountry(WebElement row) {
        String link = row.findElement(SUBZONE_LINK).getAttribute("href");
        row.findElement(SUBZONE_LINK).click();
        assert isElementDisappeared(row) : "Not open edit country: " + link;
        return new EditCountryPage(driver, link);
    }

    public List<WebElement> getAllRows() {
        return tableAllRows;
    }

    public String getCountryName(WebElement row) {
        return row.findElement(COUNTRY_NAME).getText();
    }

    public int getQuantitySubzone(WebElement row) {
        return Integer.parseInt(row.findElement(QUANTITY_SUBZONES).getText());
    }

    public String getSubzoneLink(WebElement row) {
        return row.findElement(SUBZONE_LINK).getAttribute("href");
    }

    public List<WebElement> getSubzoneRows() {
        List<WebElement> rows = tableSubzones.findElements(By.cssSelector("tr"));
        return rows.subList(1, rows.size() - 1);
    }

    public String getSubzoneName(WebElement row) {
        return row.findElement(COUNTRY_NAME_SUBZONE).getText();
    }
}
