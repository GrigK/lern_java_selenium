package app;

import Pages.CartPage;
import Pages.HomePage;
import Pages.ProductCardPage;
import com.google.common.io.Files;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Application {
    private EventFiringWebDriver driver;

    private HomePage homePage;
    private ProductCardPage productCardPage;
    private CartPage cartPage;

    public Application() {
        ChromeOptions ops = new ChromeOptions();
        ops.setCapability("unexpetedAlertBehaviour", "dismiss");
        driver = new EventFiringWebDriver(new ChromeDriver(ops));
        driver.register(new Application.EventListener());

        // Объекты страниц
        homePage = new HomePage(driver);
        productCardPage = new ProductCardPage(driver);
        cartPage = new CartPage(driver);
    }

    public void quit() {
        driver.quit();
    }

    /* *** */
    public List<LogEntry> getLogBrowser(){
        return driver.manage().logs().get("browser").getAll();
    }

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
        /**
         * Возвращает handle нового окна, если оно открыто
         * пример искользования:
         * String originalWindow = driver.getWindowHandle();
         * Set<String> existingWindows = driver.getWindowHandles()
         * el.click();
         * String newWindow = wait.until(anyWindowOtherThan(existingWindows));
         * assert newWindow != null : "New window not open";
         * driver.switchTo().window(newWindow);
         */
        return new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    /* ************* */
    public List<WebElement> getPopularProducts(){
        homePage.open();
        return homePage.getPopularProducts();
    }

    public void addProductToCart(WebElement product){
        String urlProduct = homePage.getProductLink(product);
        product.click();

        ProductCardPage productCardPage = new ProductCardPage(driver, urlProduct);
        productCardPage.selectAttribute(1);
        productCardPage.setQuantity(1);
        productCardPage.addToCart();
    }

    public void cleanCart(){
        homePage.clickToCart().emptyCart();
    }

    public static class EventListener extends AbstractWebDriverEventListener {
        /**
         * Слушатель для протоколирования тестов
         * @param by
         * @param element
         * @param driver
         */

        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by);
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by + " found");
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println(throwable);

            // снятие скриншота
            File tmp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screen = new File("screen_" + System.currentTimeMillis() + ".png");
            try {
                Files.copy(tmp, screen);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(screen);
        }

    }

}
