import java.util.*;

import Locators.AddNewProduct;
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

        assert !addProduct.isErrorSave() : "Error saving new product";

        loginPage.logout();
    }
}