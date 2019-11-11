package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class EditCountryPage extends Page {

    public EditCountryPage(WebDriver driver, String url) {
        super(driver, url);
        PageFactory.initElements(driver, this);

    }

    @FindBy(css = "i.fa.fa-external-link")
    public List<WebElement> externalLinks;


    public List<WebElement> getExternalLinks() {
        return externalLinks;
    }
}
