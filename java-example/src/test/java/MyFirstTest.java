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
    private WebDriver driver;
    private WebDriverWait wait;

    // locators
    static final By virtualKeyboard = By.cssSelector("div[aria-label=\"Экранная клавиатура\"]");
    static final By inputField = By.name("q");
    static final By btnOk = By.name("btnK");
    static final By keySpace = By.id("K32");

    @Before
    public void start(){
        // для броузера Chrome
        ChromeOptions ops = new ChromeOptions();
        ops.setCapability("unexpetedAlertBehaviour", "dicmiss");
        // добавим параметры командной строки для броузера
        ops.addArguments("start-fullscreen");

        driver= new ChromeDriver(ops);
//        driver= new FirefoxDriver();

        // вывести инфо о настройках броузера
        System.out.println(((HasCapabilities) driver).getCapabilities());

        // если не нашел то ждать 10 сек
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait= new WebDriverWait(driver, 10);
    }

    @Test
    public void myFirstTest(){
        driver.get("http://www.google.com/");
        // кликнем по виртуальной клавиатуре
        driver.findElement(virtualKeyboard).click();
        // на клавиатуре кликнем на пробел
        driver.findElement(keySpace).click();
        // закроем виртуальную клавиатуру
        driver.findElement(virtualKeyboard).click();
        driver.findElement(inputField).sendKeys("webdriver"); // StaleElementReferenceException
        driver.findElement(btnOk).click();
        wait.until(ExpectedConditions.titleIs("webdriver - Поиск в Google"));
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
