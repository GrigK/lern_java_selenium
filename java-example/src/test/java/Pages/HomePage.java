package Pages;

import Locators.Home;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePage extends Page {

    public HomePage(WebDriver driver) {
        super(driver, "http://litecart.local/ru/");
    }

    private List<WebElement> getProductsFromBox(By box){
        assert isElementPresent(box) : box.toString() + " products not found on Home page";
        WebElement element = driver.findElement(box);
        return element.findElements(Home.PRODUCT_IN_LIST);
    }

    public List<WebElement> getPopularProducts(){
        return getProductsFromBox(Home.BOX_MOST_POPULAR);
    }
    public List<WebElement> getCampaignsProducts(){
        return getProductsFromBox(Home.BOX_CAMPAIGNS);
    }
    public List<WebElement> getLatestProducts(){
        return getProductsFromBox(Home.BOX_LATEST_PRODUCTS);
    }

    public String getProductLink(WebElement element){
        assert isElementPresent(element, Home.PRODUCT_LINK) : "Not found productLink in this element";
        return element.findElement(Home.PRODUCT_LINK).getAttribute("href");
    }

    public void clickToCart(){
        assert isElementPresent(Home.LINK_CART) : "Not found link cart on home page";
        clickElement(Home.LINK_CART);
    }
}
