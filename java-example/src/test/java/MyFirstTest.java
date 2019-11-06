import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import com.devskiller.jfairy.producer.person.PersonProperties;

import java.util.*;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Locators.First;
import Locators.AdminLogin;
import Locators.HomePage;
import Locators.AdminCountries;
import Locators.ProductCard;
import Locators.RegisterForm;

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
        EnterToAdminSide();

        List<WebElement> elems = driver.findElements(AdminLogin.LEFT_MENU_URLS);
        List<String> urls = new ArrayList();
        elems.forEach((WebElement el) -> { urls.add(el.getAttribute("href")); });

        for (String page : urls) {
            driver.get(page);
            assert isElementPresent(AdminLogin.TITLE) : "Title not found";
            assert !driver .getTitle().equals("") : "Title is empty " + page;
        }
    }

    private void EnterToAdminSide(){
        setWait(5);
        String url = "http://litecart.local/admin/";
        driver.get(url);
        assert isElementPresent(AdminLogin.USERNAME) : "Input username field not found";
        assert isElementPresent(AdminLogin.PASSWORD) : "Input password field not found";
        assert isElementPresent(AdminLogin.BTN_LOGIN) : "Button login not found";
        driver.findElement(AdminLogin.USERNAME).sendKeys("admin");
        driver.findElement(AdminLogin.PASSWORD).sendKeys("ujuf0311");
        driver.findElement(AdminLogin.BTN_LOGIN).click();
        assert isElementPresent(AdminLogin.LOGO): "No logo found after login in admin page";
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
        EnterToAdminSide();
        driver.get(url);

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

    @Test
    public void shouldCorrectProductPageOpenFromHome(){
        /**
         * 1) Открыть главную страницу
         * 2) Кликнуть по первому товару в категории Campaigns
         * 3) Проверить, что открывается страница правильного товара
         * Более точно, проверить, что
         *   а) совпадает текст названия товара
         *   б) совпадает цена (обе цены)
         * Кроме того, проверить стили цены на главной странице и на странице товара --
         * первая цена серая, зачёркнутая, маленькая,
         * вторая цена красная жирная, крупная.
         */
        String url = "http://litecart.local/ru/";
        driver.get(url);
        assert isElementPresent(HomePage.BOX_CAMPAIGNS) : "Campaign products not found";
        WebElement campainghs = driver.findElement(HomePage.BOX_CAMPAIGNS);

        Map<String, List<String>> products = new HashMap<>();
        List<WebElement> productElements = campainghs.findElements(HomePage.PRODUCT_IN_LIST);
        productElements.forEach((WebElement prd) -> {
            String link = prd.findElement(HomePage.PRODUCT_LINK).getAttribute("href");
            List<String> prodAttrs = new ArrayList();

            String nameProduct = prd.findElement(HomePage.NAME_PRODUCT).getText();
            assert isElementPresent(HomePage.FIRST_PRICE_PRODUCT) : "Not found first price of product " + nameProduct;

            String firstPrice = prd.findElement(HomePage.FIRST_PRICE_PRODUCT).getText();
            assert isElementPresent(prd, HomePage.FIRST_PRICE_PRODUCT):
                    "Not found first price for product: " + nameProduct;
            assert prd.findElement(HomePage.FIRST_PRICE_PRODUCT).getAttribute("class").equals("regular-price") :
                    "Incorrect css class for first price for product " + nameProduct;
            String secondPrice = prd.findElement(HomePage.FIRST_PRICE_PRODUCT).getText();
            assert prd.findElement(HomePage.SECOND_PRICE_PRODUCT).getAttribute("class").equals("campaign-price") :
                    "Incorrect css class for second price for product " + nameProduct;
            prodAttrs.add(nameProduct);
            prodAttrs.add(firstPrice);
            prodAttrs.add(secondPrice);

            products.put(link, prodAttrs);
        });

        products.forEach((String link, List<String> attrs) -> {
            driver.get(link);
            assert isElementPresent(ProductCard.PRODUCT_TITLE): "Title product card not found";
            String productTitle = driver.findElement(ProductCard.PRODUCT_TITLE).getText();
            assert attrs.get(0).equals(productTitle):
                    "Should be product name: " + attrs.get(0) + " not: " + productTitle;
            assert isElementPresent(ProductCard.PRODUCT_FIRST_PRICE) :
                    "Not found first price for: " + productTitle;
            assert driver.findElement(ProductCard.PRODUCT_FIRST_PRICE)
                    .getAttribute("class").equals("regular-price") :
                    "Incorrect css class for first price for product " + productTitle;
            assert driver.findElement(ProductCard.PRODUCT_SECOND_PRICE)
                    .getAttribute("class").equals("campaign-price") :
                    "Incorrect css class for second price for product " + productTitle;
        });
    }

    @Test
    public void regNewUserInMarket(){
        /**
         * 1) регистрация новой учётной записи с достаточно уникальным адресом электронной
         * почты (чтобы не конфликтовало с ранее созданными пользователями),
         * 2) выход (logout), потому что после успешной регистрации автоматически происходит вход,
         * 3) повторный вход в только что созданную учётную запись,
         * 4) и ещё раз выход.
         */
        String urlRegister = "http://litecart.local/ru/create_account";

        Fairy fairy = Fairy.create();
        Person person = fairy.person();

        driver.get(urlRegister);
        assert isElementPresent(RegisterForm.FIRST_NAME): "Field 'first name' not found on register page";
        assert isElementPresent(RegisterForm.LAST_NAME): "Field 'last name' not found on register page";
        assert isElementPresent(RegisterForm.ADDR_1): "Field 'Address 1' not found on register page";
        assert isElementPresent(RegisterForm.ADDR_2): "Field 'Address 2' not found on register page";
        assert isElementPresent(RegisterForm.POST_CODE): "Field 'post' not found on register page";
        assert isElementPresent(RegisterForm.CITY): "Field 'city' not found on register page";
        assert isElementPresent(RegisterForm.COUNTRY_LIST): "Field 'Country' not found on register page";
        assert isElementPresent(RegisterForm.EMAIL): "Field 'email' not found on register page";
        assert isElementPresent(RegisterForm.PHONE): "Field 'phone' not found on register page";
        assert isElementPresent(RegisterForm.SUBSCRIBE_CHECK): "Field 'Subscribe' not found on register page";
        assert isElementPresent(RegisterForm.PASSWORD): "Field 'password' not found on register page";
        assert isElementPresent(RegisterForm.CONFIRMED_PASSWORD): "Field 'confirm password' not found on register page";
        assert isElementPresent(RegisterForm.BTN_CREATE_ACCOUNT): "Button 'Create account' not found on register page";

        driver.findElement(RegisterForm.FIRST_NAME).sendKeys(person.getFirstName());
        driver.findElement(RegisterForm.LAST_NAME).sendKeys(person.getLastName());
        driver.findElement(RegisterForm.ADDR_1).sendKeys(person.getAddress().getAddressLine1());
        driver.findElement(RegisterForm.ADDR_2).sendKeys(person.getAddress().getAddressLine2());
        driver.findElement(RegisterForm.POST_CODE).sendKeys("123456");
        driver.findElement(RegisterForm.CITY).sendKeys("TestCity");
        driver.findElement(RegisterForm.EMAIL).sendKeys(person.getEmail());
        driver.findElement(RegisterForm.PHONE).sendKeys(person.getTelephoneNumber());
        driver.findElement(RegisterForm.SUBSCRIBE_CHECK).click();
        driver.findElement(RegisterForm.PASSWORD).sendKeys(person.getPassword());
        driver.findElement(RegisterForm.CONFIRMED_PASSWORD).sendKeys(person.getPassword());
        driver.findElement(RegisterForm.BTN_CREATE_ACCOUNT).click();

        setWait(5);
        assert isElementPresent(HomePage.USER_ACCOUNT_BOX) : "Register new customer FAIL!";
        driver.get(HomePage.USER_LOGOUT_URL);
        assert !isElementPresent(HomePage.USER_ACCOUNT_BOX) : "Logout FAIL!";

        assert isElementPresent(HomePage.LOGIN_EMAIL): "Field 'email' not found on home page";
        assert isElementPresent(HomePage.LOGIN_PASSWORD): "Field 'password' not found on home page";
        assert isElementPresent(HomePage.LOGIN_BTN_ENTER): "Button 'login' not found on home page";
        driver.findElement(HomePage.LOGIN_EMAIL).sendKeys(person.getEmail());
        driver.findElement(HomePage.LOGIN_PASSWORD).sendKeys(person.getPassword());
        driver.findElement(HomePage.LOGIN_BTN_ENTER).click();

        assert isElementPresent(HomePage.USER_ACCOUNT_BOX) : "Login customer FAIL!";
        driver.get(HomePage.USER_LOGOUT_URL);
        assert !isElementPresent(HomePage.USER_ACCOUNT_BOX) : "Logout customer FAIL!";
    }

}
