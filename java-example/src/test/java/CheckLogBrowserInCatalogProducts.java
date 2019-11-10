import Pages.AdminCatalogPage;
import Pages.AdminPanelLoginPage;
import org.junit.Test;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;

public class CheckLogBrowserInCatalogProducts extends TestBase {
    @Test
    public void checkEmptyLog(){
        /**
         * 1) зайти в админку
         * 2) открыть каталог, категорию, которая содержит товары
         * (страница http://litecart.local/admin/?app=catalog&doc=catalog&category_id=1)
         * 3) последовательно открывать страницы товаров и проверять, не появляются ли
         * в логе браузера сообщения (любого уровня)
         */
        AdminPanelLoginPage loginPage = new AdminPanelLoginPage(driver);
        loginPage.open();
        loginPage.login();

        AdminCatalogPage adminCatalogPage = new AdminCatalogPage(driver);
        adminCatalogPage.setUrl("http://litecart.local/admin/?app=catalog&doc=catalog&category_id=1");
        adminCatalogPage.open();

        for (String url : adminCatalogPage.getProductsInRubberDucks()){
            System.out.println(url);
            driver.get(url);
            getLogBrowser().forEach((LogEntry entry) -> {System.out.println(entry);});
        }

        loginPage.logout();
    }
}
