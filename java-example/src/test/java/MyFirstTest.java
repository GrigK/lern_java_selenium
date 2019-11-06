import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Locators.First;
import Locators.AdminLogin;
import Locators.HomePage;
import Locators.AdminCountries;

import java.util.*;


public class MyFirstTest extends TestBase {
    @Test
    public void myFirstTest() {
        setWait(2);
        driver.get("http://www.google.com/");
        // кликнем по виртуальной клавиатуре
        assert (isElementPresent(First.VIRTUAL_KEYBOARD));
        driver.findElement(First.VIRTUAL_KEYBOARD).click();
        // на клавиатуре кликнем на пробел
        assert (isElementPresent(First.KEY_SPACE)) : "Not found space key on virtual keyboard"; // надо подождать пока появится :)
        driver.findElement(First.KEY_SPACE).click();
        // закроем виртуальную клавиатуру
        driver.findElement(First.VIRTUAL_KEYBOARD).click();
        driver.findElement(First.INPUT_FIELD).sendKeys("webdriver"); // StaleElementReferenceException
        driver.findElement(First.BTN_OK).click();
        assert expectTitlePage("webdriver - Поиск в Google") : "Incorrect page title ";
    }

    @Test
    public void enterAdminPanelCheckHeaders() {
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
        for (WebElement uri : elems) {
            urls.add(uri.getAttribute("href"));
        }

        for (String page : urls) {
            System.out.println(page);
            driver.get(page);
            assert isElementPresent(AdminLogin.TITLE) : "Title not found";
            assert driver.findElement(AdminLogin.TITLE).getText() != "" : "Title is empty";
        }
    }


    @Test
    public void checkStickerOnProductsInHomePage() {
        /**
         * Сделайте сценарий, проверяющий наличие стикеров у всех товаров в учебном приложении
         * litecart на главной странице. Стикеры -- это полоски в левом верхнем углу изображения товара,
         * на которых написано New или Sale или что-нибудь другое. Сценарий должен проверять,
         * что у каждого товара имеется ровно один стикер.
         */
        String url = "http://litecart.local/";
        driver.get(url);
        assert isElementPresent(HomePage.TITLE) : "Title not found";
        assert isElementPresent(HomePage.BOX_MOST_POPULAR) : "Popular products not found";
        checkSticker(driver.findElement(HomePage.BOX_MOST_POPULAR));
        assert isElementPresent(HomePage.BOX_CAMPAIGNS) : "Campaign products not found";
        checkSticker(driver.findElement(HomePage.BOX_CAMPAIGNS));
        assert isElementPresent(HomePage.BOX_LATEST_PRODUCTS) : "Latest products not found";
        checkSticker(driver.findElement(HomePage.BOX_LATEST_PRODUCTS));

    }

    private void checkSticker(WebElement box) {
        List<WebElement> prods = box.findElements(HomePage.PRODUCTS);
        for (WebElement prod : prods) {
            assert prod.findElements(HomePage.STICKER).size() == 1 : "Sticker not one";
        }
    }

    @Test
    public void checkSortingCountryAndGeofence() {
        /**
         * 1) на странице http://localhost/litecart/admin/?app=countries&doc=countries
         * а) проверить, что страны расположены в алфавитном порядке
         * б) для тех стран, у которых количество зон отлично от нуля -- открыть страницу этой страны и там проверить, что зоны расположены в алфавитном порядке
         * 2) на странице http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones
         * зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке
         */
        String url = "http://litecart.local/admin/?app=countries&doc=countries";
        driver.get(url);
        assert isElementPresent(AdminLogin.USERNAME) : "Input username field not found";
        assert isElementPresent(AdminLogin.PASSWORD) : "Input password field not found";
        assert isElementPresent(AdminLogin.BTN_LOGIN) : "Button login not found";
        driver.findElement(AdminLogin.USERNAME).sendKeys("admin");
        driver.findElement(AdminLogin.PASSWORD).sendKeys("ujuf0311");
        driver.findElement(AdminLogin.BTN_LOGIN).click();

        assert isElementPresent(AdminCountries.TABLE_ALL_COUNTRIES);
        WebElement tableCounties = driver.findElement(AdminCountries.TABLE_ALL_COUNTRIES);
        List<WebElement> rowsCountries = tableCounties.findElements(AdminCountries.TABLE_ROWS);

        List<String> countriesList = new ArrayList();
        List<String> subCountrieUrlsList = new ArrayList();

        rowsCountries.forEach((WebElement x) -> {
            countriesList.add(x.findElement(AdminCountries.COUNTRY_NAME).getText());
            int zones = Integer.parseInt(x.findElement(AdminCountries.QUANTITY_SUBZONES).getText());
            if(zones > 0){
                subCountrieUrlsList.add(x.findElement(AdminCountries.SUBZONE_LINK).getAttribute("href"));}
        });
        assert checkSortingList(countriesList): "The error of sorting the list of countries";

        for(String zoneUrl: subCountrieUrlsList){
            driver.get(zoneUrl);
            assert isElementPresent(AdminCountries.TABLE_SUBZONES): "Not found table subzones";
            WebElement tableSubCounties = driver.findElement(AdminCountries.TABLE_SUBZONES);
            List<WebElement> countrieRows = tableSubCounties.findElements(AdminCountries.TABLE_ROWS_SUBZONES);
            countrieRows.remove(0); // remove header line
            countrieRows.remove(countrieRows.size()-1); // remove add line

            List<String> subCountrieNames = new ArrayList();
            countrieRows.forEach((WebElement y) -> {
                subCountrieNames.add(y.findElement(AdminCountries.COUNTRY_NAME_SUBZONE).getText());});
            assert checkSortingList(subCountrieNames): "The error of sorting the list of subzones";
        }
    }

    private boolean checkSortingList(List<String> arr){
        /**
         * Let's check that the list is sorted correctly
         */
        List<String> src = new ArrayList();
        src.addAll(arr);
        Collections.sort(src);
        return src.equals(arr);
    }

}
