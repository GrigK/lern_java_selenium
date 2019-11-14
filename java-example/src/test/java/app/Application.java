package app;

import Pages.*;
import com.google.common.io.Files;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Application {
    private EventFiringWebDriver driver;

    private HomePage homePage;
    private ProductCardPage productCardPage;
    private CartPage cartPage;

    private AdminPanelLoginPage adminPanelLoginPage;
    private AdminCountriesPage adminCountriesPage;

    public Application() {
        ChromeOptions ops = new ChromeOptions();
        ops.setCapability("unexpectedAlertBehaviour", "dismiss");
        driver = new EventFiringWebDriver(new ChromeDriver(ops));
        driver.register(new Application.EventListener());

        homePage = new HomePage(driver);
        productCardPage = new ProductCardPage(driver);
        cartPage = new CartPage(driver);

        adminPanelLoginPage = new AdminPanelLoginPage(driver);
        adminCountriesPage = new AdminCountriesPage(driver);
    }

    public void quit() {
        driver.quit();
    }

    /* *** */
    public List<LogEntry> getLogBrowser() {
        return driver.manage().logs().get("browser").getAll();
    }

    public ExpectedCondition<String> anyWindowOtherThan(Set<String> oldWindows) {
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
        return new ExpectedCondition<String>() {
            @Override
            public String apply(WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(oldWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    /* ************* */
    public List<WebElement> getPopularProducts() {
        homePage.open();
        return homePage.getPopularProducts();
    }

    public void addProductToCart(WebElement product) {
        String urlProduct = homePage.getProductLink(product);
        product.click();

        ProductCardPage productCardPage = new ProductCardPage(driver, urlProduct);
        productCardPage.selectAttribute(1);
        productCardPage.setQuantity(1);
        productCardPage.addToCart();
    }

    public void cleanCart() {
        homePage.clickToCart().emptyCart();
    }

    public void loginAdminPanel() {
        /**
         * Вход в админ-панель
         */
        adminPanelLoginPage.open().login();
    }

    public void logoutAdminPanel() {
        /**
         * выход из админ панели
         */
        adminPanelLoginPage.logout();
    }

    public void checkTitleOnLeftMenuPages() {
        /**
         * проверка на не пустой title страницы
         */
        adminPanelLoginPage.getLeftMenuLinks().forEach(
                (String url) -> {
                    driver.get(url);
                    Assert.assertFalse("Title on page is empty: " + url, driver.getTitle().equals(""));
                });
    }

    public void checkSortingCountries() {
        adminCountriesPage.open();
        List<WebElement> rowsCountries = adminCountriesPage.getAllRows();

        List<String> countriesList = new ArrayList();
        List<String> subCountrieUrlsList = new ArrayList();

        rowsCountries.forEach((WebElement row) -> {
            countriesList.add(adminCountriesPage.getCountryName(row));
            if (adminCountriesPage.getQuantitySubzone(row) > 0) {
                subCountrieUrlsList.add(adminCountriesPage.getSubzoneLink(row));
            }
        });
        Assert.assertTrue("The error of sorting the list of countries", checkSortingList(countriesList));

        for (String zoneUrl : subCountrieUrlsList) {
            adminCountriesPage.setUrl(zoneUrl);
            adminCountriesPage.open();

            List<WebElement> countrieRows = adminCountriesPage.getSubzoneRows();
            List<String> subCountrieNames = new ArrayList();

            countrieRows.forEach((WebElement row) -> {
                subCountrieNames.add(adminCountriesPage.getSubzoneName(row));
            });
            assert checkSortingList(subCountrieNames) : "The error of sorting the list of subzones " + zoneUrl;
        }
    }

    private boolean checkSortingList(List<String> arr) {
        /**
         * Let's check that the list is sorted correctly
         */
        List<String> src = new ArrayList();
        src.addAll(arr);
        Collections.sort(src);
        return src.equals(arr);
    }

    public void checkExternalLinks() {
        adminCountriesPage.open();
        WebElement countryRow = adminCountriesPage.getAllRows().get(2);
        System.out.println("Edit country: " + adminCountriesPage.getCountryName(countryRow));

        EditCountryPage editCountryPage = adminCountriesPage.editCountry(countryRow);
        System.out.println("Original window -> " + driver.getTitle());
        List<WebElement> extLinks = editCountryPage.getExternalLinks();

        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();

        for (WebElement el : extLinks) {
            el.click();

            String newWindow = adminCountriesPage.wait.until(anyWindowOtherThan(existingWindows));
            Assert.assertTrue("New window not open", newWindow != null);
            driver.switchTo().window(newWindow);
            System.out.println("New window:" + driver.getTitle());

            driver.close();
            driver.switchTo().window(originalWindow);
        }
    }

    public void  openCatalogAndClickAddNewProduct(){
        AdminCatalogPage adminCatalogPage = new AdminCatalogPage(driver);
        adminCatalogPage.open();
        adminCatalogPage.submitAddNewProduct();
    }

    public void editNewProduct(){
        AddNewProductPage addProduct = new AddNewProductPage(driver);
        addProduct.openGeneralTab()
                .setEnabled().setNameEn("New product").setNameRu("Новый продукт")
                .setCategory(1).setGroup(0).setGroup(1)
                .setQuantity(25.0)
                .uploadImage("/home/grig/Изображения/Аватарки для проги/anonym.png")
                .setDateFrom("08112019").setDateTo("08112020")

                .openInformationTab()
                .setManufacturer()
                .setShortDescriptionEn("Short description product").setShortDescriptionRu("Короткое описание товара")
                .setHeadTitleEn("New product head").setHeadTitleRu("Новый продукт head")

                .openPricesTab()
                .setPurchasePrice(9.99).selectPurchaseCurrencyCode()
                .setPriceUSD(15.0).setPriceEUR(11.0).setPriceRUR(975.0)
                .submitSave();

        Assert.assertFalse("Error saving new product", addProduct.isErrorSave());
    }


    /**
     * Class EventListener for logging selenium events
     */
    public static class EventListener extends AbstractWebDriverEventListener {
        /**
         * Слушатель для протоколирования тестов
         *
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

}
