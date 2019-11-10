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

    public ProductCardPage(WebDriver driver) {
        super(driver, "http://litecart.local/admin/?category_id=0&app=catalog&doc=edit_product");
    }

    public void selectAttribute(int sel){
        setWait(3);
        if(areElementsPresent(ProductCard.SELECT_SIZE)) {
            Select selector = new Select(driver.findElement(ProductCard.SELECT_SIZE));
            selector.selectByIndex(sel);
        }
    }

    public void setQuantity(int quantity){
        assert areElementsPresent(ProductCard.INPUT_QUANTITY) :
                "Field 'Input quantity' not found " + driver.getCurrentUrl();
        driver.findElement(ProductCard.INPUT_QUANTITY).clear();
        driver.findElement(ProductCard.INPUT_QUANTITY).sendKeys(String.format("%d", quantity));
    }

    public void addToCart(){
        setWait(5);
        assert areElementsPresent(ProductCard.BTN_ADD_TO_CART) :
                "Button add to cart not found on page " + driver.getCurrentUrl();
        assert areElementsPresent(ProductCard.QUANTITY_IN_CART) : "QUANTITY_IN_CART not found";
        WebElement cartQ = driver.findElement(ProductCard.QUANTITY_IN_CART);
        String text = cartQ.getText();
        driver.findElement(ProductCard.BTN_ADD_TO_CART).click();

        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(cartQ, text)));
    }
}
