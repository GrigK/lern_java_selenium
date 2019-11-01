import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class MyFirstTest {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;

    // locators
    static final By VIRTUAL_KEYBOARD = By.cssSelector("div[aria-label=\"Экранная клавиатура\"]");
    static final By INPUT_FIELD = By.name("q");
    static final By BTN_OK = By.name("btnK");
    static final By KEY_SPACE = By.id("K32");

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 10);
            return;
        }
        // для броузера Chrome
        ChromeOptions ops = new ChromeOptions();
        ops.setCapability("unexpetedAlertBehaviour", "dicmiss");
        // добавим параметры командной строки для броузера
        ops.addArguments("start-fullscreen");

        driver = new ChromeDriver(ops);
//        driver= new FirefoxDriver();
        tlDriver.set(driver);

        // вывести инфо о настройках броузера
        System.out.println(((HasCapabilities) driver).getCapabilities());

        // если не нашел то ждать 10 сек
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    driver.quit();
                    driver = null;
                })
        );
    }

    @Test
    public void myFirstTest() {
        driver.get("http://www.google.com/");
        // кликнем по виртуальной клавиатуре
        driver.findElement(VIRTUAL_KEYBOARD).click();
        // на клавиатуре кликнем на пробел
        driver.findElement(KEY_SPACE).click();
        // закроем виртуальную клавиатуру
        driver.findElement(VIRTUAL_KEYBOARD).click();
        driver.findElement(INPUT_FIELD).sendKeys("webdriver"); // StaleElementReferenceException
        driver.findElement(BTN_OK).click();
        wait.until(ExpectedConditions.titleIs("webdriver - Поиск в Google"));
    }

    @After
    public void stop() {
//        driver.quit();
//        driver = null;
    }
}
