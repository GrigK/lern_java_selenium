import org.junit.Test;
import org.openqa.selenium.By;

public class SecondTest extends TestBase {
    public static final By VIRTUAL_KEYBOARD = By.cssSelector("div[aria-label=\"Экранная клавиатура\"]");
    public static final By INPUT_FIELD = By.name("q");
    public static final By BTN_OK = By.name("btnK");
    public static final By KEY_SPACE = By.id("K32");

    @Test
    public void myGoogleTest() {
        setWait(2);
        driver.get("http://www.google.com/");
        // кликнем по виртуальной клавиатуре
        assert (isElementPresent(VIRTUAL_KEYBOARD));
        driver.findElement(VIRTUAL_KEYBOARD).click();
        // на клавиатуре кликнем на пробел
        assert (isElementPresent(KEY_SPACE)) : "Not found space key on virtual keyboard"; // надо подождать пока появится :)
        driver.findElement(KEY_SPACE).click();
        // закроем виртуальную клавиатуру
        driver.findElement(VIRTUAL_KEYBOARD).click();
        driver.findElement(INPUT_FIELD).sendKeys("webdriver"); // StaleElementReferenceException
        driver.findElement(BTN_OK).click();
        assert expectTitlePage("webdriver - Поиск в Google") : "Incorrect page title ";
    }
}
