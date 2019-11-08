package Pages;

import Locators.Cart;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends Page {
    public CartPage(WebDriver driver) {
        super(driver, "http://litecart.local/ru/checkout");
    }

    public void emptyCart(){
        setWait(5);
        if(isElementPresent(Cart.TABLE_ORDER)){
            int quantityProds = driver.findElements(Cart.BTN_REMOVE).size();

            for(int i = 0; i < quantityProds; i++){
                WebElement tableOrder = driver.findElement(Cart.TABLE_ORDER);
                String text = tableOrder.getText();
                driver.findElements(Cart.BTN_REMOVE).get(0).click();
                wait.until(ExpectedConditions.not(
                        ExpectedConditions.textToBePresentInElement(tableOrder, text)));
            }
        }
    }

}
