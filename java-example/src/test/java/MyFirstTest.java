import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Locators.First;
import Locators.AdminLogin;
import Locators.HomePage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class MyFirstTest extends TestBase {
    @Test
    public void myFirstTest() {
        setWait(2);
        driver.get("http://www.google.com/");
        // кликнем по виртуальной клавиатуре
        assert(isElementPresent(First.VIRTUAL_KEYBOARD));
        driver.findElement(First.VIRTUAL_KEYBOARD).click();
        // на клавиатуре кликнем на пробел
        assert(isElementPresent(First.KEY_SPACE)) : "Not found space key on virtual keyboard"; // надо подождать пока появится :)
        driver.findElement(First.KEY_SPACE).click();
        // закроем виртуальную клавиатуру
        driver.findElement(First.VIRTUAL_KEYBOARD).click();
        driver.findElement(First.INPUT_FIELD).sendKeys("webdriver"); // StaleElementReferenceException
        driver.findElement(First.BTN_OK).click();
        assert expectTitlePage("webdriver - Поиск в Google") : "Incorrect page title ";
    }

    @Test
    public void enterAdminPanelCheckHeaders(){
        /**
         * 1) входит в панель администратора http://localhost/litecart/admin
         * 2) прокликивает последовательно все пункты меню слева, включая вложенные пункты
         * 3) для каждой страницы проверяет наличие заголовка
         */
        String url = "http://litecart.local/admin/login.php?redirect_url=%2Fadmin%2F";
        driver.get(url);
        assert isElementPresent(AdminLogin.USERNAME) : "Input username field not found";
        assert isElementPresent(AdminLogin.PASSWORD) : "Input password field not found";
        assert isElementPresent(AdminLogin.BTN_LOGIN) : "Button login not found";
        driver.findElement(AdminLogin.USERNAME).sendKeys("admin");
        driver.findElement(AdminLogin.PASSWORD).sendKeys("ujuf0311");
        driver.findElement(AdminLogin.BTN_LOGIN).click();
        assert expectTitlePage("My Lite Store") : "Could not login to admin panel";

        List<WebElement> elems = driver.findElements(AdminLogin.LEFT_MENU_URLS);
        List<String> urls = new ArrayList();
        for(WebElement uri: elems){
            urls.add(uri.getAttribute("href"));
        }

        for(String page : urls){
            System.out.println(page );
            driver.get(page);
            assert isElementPresent(AdminLogin.TITLE): "Title not found";
            assert driver.findElement(AdminLogin.TITLE).getText() != "" : "Title is empty";
        }
    }


    @Test
    public void checkStickerOnProductsInHomePage(){
        /**
         * Сделайте сценарий, проверяющий наличие стикеров у всех товаров в учебном приложении
         * litecart на главной странице. Стикеры -- это полоски в левом верхнем углу изображения товара,
         * на которых написано New или Sale или что-нибудь другое. Сценарий должен проверять,
         * что у каждого товара имеется ровно один стикер.
         */
        String url = "http://litecart.local/";
        driver.get(url);
        assert isElementPresent(HomePage.TITLE): "Title not found";
        assert isElementPresent(HomePage.BOX_MOST_POPULAR): "Popular products not found";
        checkSticker(driver.findElement(HomePage.BOX_MOST_POPULAR));
        assert isElementPresent(HomePage.BOX_CAMPAIGNS): "Campaign products not found";
        checkSticker(driver.findElement(HomePage.BOX_CAMPAIGNS));
        assert isElementPresent(HomePage.BOX_LATEST_PRODUCTS): "Latest products not found";
        checkSticker(driver.findElement(HomePage.BOX_LATEST_PRODUCTS));

    }
    private void checkSticker(WebElement box){
        List<WebElement> prods = driver.findElements(HomePage.PRODUCTS);
        for(WebElement prod : prods){
            assert prod.findElements(HomePage.STICKER).size() == 1 : "Sticker not one";
        }
    }


}
