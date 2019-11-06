import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait; // для явных ожиданий
    private long waitSec = 10;

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            this.wait = new WebDriverWait(driver, waitSec);
            return;
        }

        ChromeOptions ops = new ChromeOptions();
        ops.setCapability("unexpetedAlertBehaviour", "dicmiss");
        // добавим параметры командной строки для броузера
        // ops.addArguments("start-fullscreen");

        driver = new ChromeDriver(ops);
        // driver= new FirefoxDriver();
        // driver = new RemoteWebDriver(DesiredCapabilities.chrome());

        tlDriver.set(driver);

        // вывести инфо о настройках броузера
        // System.out.println(((HasCapabilities) driver).getCapabilities());

        // если не нашел то ждать 10 сек. Неявное ожидание
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        this.wait = new WebDriverWait(driver, waitSec);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    driver.quit();
                    driver = null;
                })
        );
    }

//    @After
//    public void stop() {
//        driver.quit();
//        driver = null;
//    }

    public void setWait(long seconds) {
        waitSec = seconds;
        this.wait = new WebDriverWait(driver, waitSec);
    }

    public boolean isElementPresent(By locator){
        try {
            wait.until((WebDriver d) -> d.findElement(locator));
            return true;
        } catch (InvalidSelectorException e){
            throw e;
        } catch (TimeoutException e){
            return false;
        }
    }

    public boolean isElementPresent(WebElement el, By locator){
        try {
            wait.until((WebDriver d) -> el.findElement(locator));
            return true;
        } catch (InvalidSelectorException e){
            throw e;
        } catch (TimeoutException e){
            return false;
        }
    }

    public boolean isElementPresentNoWait(By locator){
        try {
            driver.findElement(locator);
            return true;
        } catch (InvalidSelectorException e){
            throw e;
        } catch (NoSuchElementException e){
            return false;
        }
    }

    public boolean areElementsPresent(By locator){
        return driver.findElements(locator).size() > 0;
    }

    public boolean expectTitlePage(String title){
        return wait.until(ExpectedConditions.titleIs(title));
    }
}
