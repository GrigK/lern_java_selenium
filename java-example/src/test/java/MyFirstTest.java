import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Locators.First;


public class MyFirstTest extends TestBase {
    @Test
    public void myFirstTest() {
        driver.get("http://www.google.com/");
        // кликнем по виртуальной клавиатуре
        driver.findElement(First.VIRTUAL_KEYBOARD).click();
        // на клавиатуре кликнем на пробел
        driver.findElement(First.KEY_SPACE).click();
        // закроем виртуальную клавиатуру
        driver.findElement(First.VIRTUAL_KEYBOARD).click();
        driver.findElement(First.INPUT_FIELD).sendKeys("webdriver"); // StaleElementReferenceException
        driver.findElement(First.BTN_OK).click();
        wait.until(ExpectedConditions.titleIs("webdriver - Поиск в Google"));
    }

}
