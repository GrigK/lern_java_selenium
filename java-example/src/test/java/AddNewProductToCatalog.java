import java.util.*;

import org.junit.Test;

import Pages.AdminPanelLoginPage;
import Pages.AdminCatalogPage;
import Pages.AddNewProductPage;

public class AddNewProductToCatalog extends TestBase{
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
        AdminPanelLoginPage loginPage = new AdminPanelLoginPage(driver);
        loginPage.open();
        loginPage.login();

        AdminCatalogPage adminCatalogPage = new AdminCatalogPage(driver);
        adminCatalogPage.open();
        adminCatalogPage.submitAddNewProduct();
        //TODO : здесь само тело теста - добавить данные на страницу

        loginPage.logout();
    }
}