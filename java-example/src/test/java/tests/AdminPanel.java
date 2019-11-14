package tests;

import Pages.AdminPanelLoginPage;
import org.junit.Test;

import java.util.*;

public class AdminPanel extends TestBaseNew {
    @Test
    public void LoginAndLogout() {
        /**
         * простое тестирование на вход
         * и выход из панели упарвления
         */
        app.loginAdminPanel();
        app.logoutAdminPanel();
    }

    @Test
    public void enterAdminPanelCheckHeaders() {
        /**
         * 1) входит в панель администратора http://localhost/litecart/admin
         * 2) прокликивает последовательно все пункты меню слева, включая вложенные пункты
         * 3) для каждой страницы проверяет наличие заголовка
         */
        app.loginAdminPanel();
        app.checkTitleOnLeftMenuPages();
        app.logoutAdminPanel();
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
        app.loginAdminPanel();
        app.checkSortingCountries();
        app.logoutAdminPanel();
    }

    @Test
    public void clickToCountryAndCheckNewWindow() {
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
        app.loginAdminPanel();
        app.checkExternalLinks();
        app.logoutAdminPanel();
    }

    @Test
    public void addNewProductViaAdminPanel(){
        /**
         * открыть меню Catalog, в правом верхнем углу нажать кнопку "Add New Product",
         * заполнить поля с информацией о товаре и сохранить.
         * Достаточно заполнить только информацию на вкладках
         * General, Information и Prices.
         * Скидки (Campains) на вкладке Prices можно не добавлять.
         * После сохранения товара нужно убедиться, что он появился в каталоге (в админке).
         * Клиентскую часть магазина можно не проверять.
         */
        app.loginAdminPanel();
        app.openCatalogAndClickAddNewProduct();
        app.editNewProduct();
        app.logoutAdminPanel();

    }
}
