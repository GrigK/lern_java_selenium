import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class MyFirstTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start(){
        driver= new ChromeDriver(); //FirefoxDriver();
        // если не нашел то ждать 10 сек
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait= new WebDriverWait(driver, 10);
    }

    @Test
    public void myFirstTest(){
        driver.get("http://www.google.com/");
        // кликнем по виртуальной клавиатуре
        driver.findElement(By.cssSelector("div[aria-label=\"Экранная клавиатура\"]")).click();
        // на клавиатуре кликнем на пробел
        driver.findElement(By.id("K32")).click();
        // закроем виртуальную клавиатуру
        driver.findElement(By.cssSelector("div[aria-label=\"Экранная клавиатура\"]")).click();
        driver.findElement(By.name("q")).sendKeys("webdriver"); // StaleElementReferenceException
        driver.findElement(By.name("btnK")).click();
        wait.until(ExpectedConditions.titleIs("webdriver - Поиск в Google"));
    }

    @After
    public void stop(){
        driver.quit();
        driver = null;
    }
}
