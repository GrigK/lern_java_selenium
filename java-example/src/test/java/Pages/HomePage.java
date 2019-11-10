package Pages;

import Locators.Home;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class HomePage extends Page {

    public HomePage(WebDriver driver) {
        super(driver, "http://litecart.local/ru/");
    }

    private List<WebElement> getProductsFromBox(By box){
        assert isElementPresent(box) : box.toString() + " products not found on Home page";
//        WebElement element = driver.findElement(box);
        return driver.findElement(box).findElements(Home.PRODUCT_IN_LIST);
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
        return element.findElement(Home.PRODUCT_LINK).getAttribute("href");
    }

    public CartPage clickToCart(){
        assert isElementPresent(Home.LINK_CART) : "Not found link cart on home page";
        String text = driver.findElement(By.cssSelector("title")).getText();
        clickElement(Home.LINK_CART);
        wait.until(ExpectedConditions.textToBe(By.cssSelector("title"), text));

        return new CartPage(driver);
    }
}
