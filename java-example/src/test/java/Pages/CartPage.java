package Pages;

import Locators.Cart;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends Page {
    public CartPage(WebDriver driver) {
        super(driver, "http://litecart.local/ru/checkout");
    }

    public void emptyCart(){
        if(areElementsPresent(Cart.TABLE_ORDER)){
            setWait(5);
            assert areElementsPresent(Cart.BTN_REMOVE) : " Not found BTN_REMOVE";
            int quantityProds = driver.findElements(Cart.BTN_REMOVE).size();

            for(int i = 0; i < quantityProds; i++){
                WebElement btn = driver.findElements(Cart.BTN_REMOVE).get(0);
                // клик д.б. по видимой кнопке
                wait.until(ExpectedConditions.visibilityOf(btn));
                btn.click();

                // ждем когда изменится количество кнопок BTN_REMOVE
                if(areElementsPresent(Cart.TABLE_ORDER)) {
                    int finalI = i;
                    wait.until(new ExpectedCondition<Boolean>() {
                        public Boolean apply(WebDriver driver){
                            int cnt = driver.findElements(Cart.BTN_REMOVE).size();
                            return (quantityProds - finalI) - cnt == 1;
                        }
                    });

                }
            }
        }
    }

}
