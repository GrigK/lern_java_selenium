import com.google.common.io.Files;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;


public class TestBase {
    private static ThreadLocal<EventFiringWebDriver> tlDriver = new ThreadLocal<>();
    public EventFiringWebDriver driver;
    public WebDriverWait wait;
    private long waitSec = 10;

    public static class EventListener extends AbstractWebDriverEventListener{
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

        driver = new EventFiringWebDriver(new ChromeDriver(ops));
        driver.register(new EventListener());
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

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows){
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
        return  new ExpectedCondition<String>() {
            @Override
            public String apply( WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
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
            wait.until((WebDriver d) -> ExpectedConditions.visibilityOf(d.findElement(locator)));
//            wait.until((WebDriver d) -> d.findElement(locator));
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

    public List<LogEntry> getLogBrowser(){
        return driver.manage().logs().get("browser").getAll();
    }

}
