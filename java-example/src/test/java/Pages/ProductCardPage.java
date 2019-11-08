package Pages;

import org.openqa.selenium.WebDriver;
import Locators.ProductCard;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ProductCardPage extends Page {
    public ProductCardPage(WebDriver driver, String url) {
        super(driver, url);
    }

    public void selectAttribute(int sel){
        setWait(1);
        if(isElementPresent(ProductCard.SELECT_SIZE)) {
            WebElement selectElement = driver.findElement(ProductCard.SELECT_SIZE);
            Select selector = new Select(selectElement);
            selector.selectByIndex(sel);
        }
    }

    public void setQuantity(int quantity){
        assert isElementPresent(ProductCard.INPUT_QUANTITY) :
                "Field 'Input quantity' not found " + driver.getCurrentUrl();
        WebElement el = driver.findElement(ProductCard.INPUT_QUANTITY);
        el.clear();
        el.sendKeys(String.format("%d", quantity));
    }

    public void addToCart(){
        setWait(5);
        assert isElementPresent(ProductCard.BTN_ADD_TO_CART) :
                "Button add to cart not found on page " + driver.getCurrentUrl();
        WebElement cartQ = driver.findElement(ProductCard.QUANTITY_IN_CART);
        String text = cartQ.getText();
        driver.findElement(ProductCard.BTN_ADD_TO_CART).click();

        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(cartQ, text)));
    }
}
