package Pages;

import Locators.EditCountry;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EditCountryPage extends Page {

    public EditCountryPage(WebDriver driver, String url) {
        super(driver, url);
    }

    public List<WebElement> getExternalLinks(){
        return driver.findElements(EditCountry.EXTERNAL_LINKS);
    }
}
