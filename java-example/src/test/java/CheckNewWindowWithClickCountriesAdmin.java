import Locators.AdminCountries;
import Pages.AdminCatalogPage;
import Pages.AdminCountriesPage;
import Pages.AdminPanelLoginPage;
import Pages.EditCountryPage;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;

public class CheckNewWindowWithClickCountriesAdmin extends TestBase {
    @Test
    public void clickToCountryAndCheckNewWindow(){
        /**
         * 1) зайти в админку
         * 2) открыть пункт меню Countries (или страницу http://localhost/litecart/admin/?app=countries&doc=countries)
         * 3) открыть на редактирование какую-нибудь страну или начать создание новой
         * 4) возле некоторых полей есть ссылки с иконкой в виде квадратика со стрелкой
         *    -- они ведут на внешние страницы и открываются в новом окне, именно это и нужно проверить.
         * Конечно, можно просто убедиться в том, что у ссылки есть атрибут target="_blank".
         * Но в этом упражнении требуется именно кликнуть по ссылке, чтобы она открылась в новом окне,
         * потом переключиться в новое окно, закрыть его, вернуться обратно, и повторить эти действия
         * для всех таких ссылок.
         * Не забудьте, что новое окно открывается не мгновенно, поэтому требуется ожидание открытия окна.
         */
        AdminPanelLoginPage loginPage = new AdminPanelLoginPage(driver);
        loginPage.open();
        loginPage.login();

        AdminCountriesPage adminCountriesPage = new AdminCountriesPage(driver);
        adminCountriesPage.open();
        List<WebElement> countries =  adminCountriesPage.getCountrieNames();
        assert countries.size() > 0 : " Countries not found";

        System.out.println("Edit country: " + countries.get(2).getText());
        EditCountryPage editCountryPage = adminCountriesPage.editCountry(countries.get(2));

        System.out.println("Original window -> " + driver.getTitle());
        List<WebElement> extLinks = editCountryPage.getExternalLinks();

        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();

        for(WebElement el : extLinks){
            el.click();

            String newWindow = wait.until(anyWindowOtherThan(existingWindows));
            assert newWindow != null : "New window not open";
            driver.switchTo().window(newWindow);
            System.out.println(driver.getTitle());

            driver.close();
            driver.switchTo().window(originalWindow);
        }

        loginPage.logout();
    }
}
