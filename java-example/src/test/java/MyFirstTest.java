import Pages.AdminPanelLoginPage;
import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;

import java.util.*;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import Locators.First;
//import Locators.AdminLogin;
import Locators.Home;
//import Locators.AdminCountries;
import Locators.ProductCard;
import Locators.RegisterForm;

public class MyFirstTest extends TestBase {

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
        assert isElementPresent(Home.TITLE) : "Title not found";
        assert isElementPresent(Home.BOX_MOST_POPULAR) : "Popular products not found";
        checkSticker(driver.findElement(Home.BOX_MOST_POPULAR));
        assert isElementPresent(Home.BOX_CAMPAIGNS) : "Campaign products not found";
        checkSticker(driver.findElement(Home.BOX_CAMPAIGNS));
        assert isElementPresent(Home.BOX_LATEST_PRODUCTS) : "Latest products not found";
        checkSticker(driver.findElement(Home.BOX_LATEST_PRODUCTS));

    }

    private void checkSticker(WebElement box) {
        List<WebElement> prods = box.findElements(Home.PRODUCTS);
        for (WebElement prod : prods) {
            assert prod.findElements(Home.STICKER).size() == 1 : "Sticker not one";
        }
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
        assert isElementPresent(Home.BOX_CAMPAIGNS) : "Campaign products not found";
        WebElement campainghs = driver.findElement(Home.BOX_CAMPAIGNS);

        Map<String, List<String>> products = new HashMap<>();
        List<WebElement> productElements = campainghs.findElements(Home.PRODUCT_IN_LIST);
        productElements.forEach((WebElement prd) -> {
            String link = prd.findElement(Home.PRODUCT_LINK).getAttribute("href");
            List<String> prodAttrs = new ArrayList();

            String nameProduct = prd.findElement(Home.NAME_PRODUCT).getText();
            assert isElementPresent(Home.FIRST_PRICE_PRODUCT) : "Not found first price of product " + nameProduct;

            String firstPrice = prd.findElement(Home.FIRST_PRICE_PRODUCT).getText();
            assert isElementPresent(prd, Home.FIRST_PRICE_PRODUCT):
                    "Not found first price for product: " + nameProduct;
            assert prd.findElement(Home.FIRST_PRICE_PRODUCT).getAttribute("class").equals("regular-price") :
                    "Incorrect css class for first price for product " + nameProduct;
            String secondPrice = prd.findElement(Home.FIRST_PRICE_PRODUCT).getText();
            assert prd.findElement(Home.SECOND_PRICE_PRODUCT).getAttribute("class").equals("campaign-price") :
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
        assert isElementPresent(Home.USER_ACCOUNT_BOX) : "Register new customer FAIL!";
        driver.get(Home.USER_LOGOUT_URL);
        assert !isElementPresent(Home.USER_ACCOUNT_BOX) : "Logout FAIL!";

        assert isElementPresent(Home.LOGIN_EMAIL): "Field 'email' not found on home page";
        assert isElementPresent(Home.LOGIN_PASSWORD): "Field 'password' not found on home page";
        assert isElementPresent(Home.LOGIN_BTN_ENTER): "Button 'login' not found on home page";
        driver.findElement(Home.LOGIN_EMAIL).sendKeys(person.getEmail());
        driver.findElement(Home.LOGIN_PASSWORD).sendKeys(person.getPassword());
        driver.findElement(Home.LOGIN_BTN_ENTER).click();

        assert isElementPresent(Home.USER_ACCOUNT_BOX) : "Login customer FAIL!";
        driver.get(Home.USER_LOGOUT_URL);
        assert !isElementPresent(Home.USER_ACCOUNT_BOX) : "Logout customer FAIL!";
    }

    @Test
    public void addNewProductViaAdminPanel(){
        AdminPanelLoginPage loginPage = new AdminPanelLoginPage(driver);
        loginPage.open();
        loginPage.login();
        loginPage.logout();
    }
}
