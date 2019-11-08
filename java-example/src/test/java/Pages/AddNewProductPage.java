package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import Locators.AddNewProduct;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import javax.swing.*;
import java.io.File;
import java.util.List;

public class AddNewProductPage extends Page {
    public AddNewProductPage(WebDriver driver) {
        super(driver, "http://litecart.local/admin/?category_id=0&app=catalog&doc=edit_product");
    }

    public AddNewProductPage openGeneralTab(){
        clickElement(AddNewProduct.TAB_GENERAL);
        return this;
    }

    public AddNewProductPage openInformationTab(){
        clickElement(AddNewProduct.TAB_INFORMATION);
        return this;
    }

    public AddNewProductPage openPricesTab(){
        clickElement(AddNewProduct.TAB_PRICES);
        return this;
    }

    // first tab
    public AddNewProductPage setEnabled(){
        clickElement(AddNewProduct.TOGGLE_STATUS_INPUTS);
        return this;
    }

    public AddNewProductPage setNameEn(String name){
        sendStringElement(AddNewProduct.INPUT_NAME_EN, name);
        return this;
    }

    public AddNewProductPage setNameRu(String name){
        sendStringElement(AddNewProduct.INPUT_NAME_RU, name);
        return this;
    }

    public AddNewProductPage setCategory(int cat){
        assert isElementPresent(AddNewProduct.CHECK_CATEGORIES) : "Not found categories";
        List<WebElement> checkCategories = driver.findElements(AddNewProduct.CHECK_CATEGORIES);
        checkCategories.get(cat).click();
        return this;
    }

    public AddNewProductPage setGroup(int grp){
        List<WebElement> checkGroups = driver.findElements(AddNewProduct.CHECK_GROUPS);
        checkGroups.get(grp).click();
        return this;
    }

    public AddNewProductPage setQuantity(double quantity){
        clickElement(AddNewProduct.INPUT_QUANTITY);
        driver.findElement(AddNewProduct.INPUT_QUANTITY).clear();
        sendStringElement(
                AddNewProduct.INPUT_QUANTITY, String.format("%.2f", quantity).replace('.', ','));
        return this;
    }

    public AddNewProductPage uploadImage(String filename){
        File file = new File(filename);
        String path = file.getAbsolutePath();
        sendStringElement(AddNewProduct.BTN_UPLOAD_IMAGE, path);
        return this;
    }

    public AddNewProductPage setDateFrom(String from){
        /**
         * format dateString = MM/DD/YYYY
         */
        sendStringElement(AddNewProduct.INPUT_DATE_FROM, from);
//        setDatepicker(AddNewProduct.INPUT_DATE_FROM, from);
        return this;
    }

    public AddNewProductPage setDateTo(String to){
        /**
         * format dateString = MM/DD/YYYY
         */
        sendStringElement(AddNewProduct.INPUT_DATE_TO, to);
//        sendStringElement(AddNewProduct.INPUT_DATE_TO, to);
        return this;
    }

    // second tab
    public AddNewProductPage setManufacturer(){
        assert isElementPresent(AddNewProduct.SELECT_MANUFACTURER) : "Manufacturer list not found";
        WebElement manufacturerSelect = driver.findElement(AddNewProduct.SELECT_MANUFACTURER);
        Select select = new Select(manufacturerSelect);
        select.selectByValue("1"); // ACME Corp.
        return this;
    }

    public AddNewProductPage setShortDescriptionEn(String dscr){
        sendStringElement(AddNewProduct.INPUT_SHORT_DESCRIPTION_EN, dscr);
        return this;
    }

    public AddNewProductPage setShortDescriptionRu(String dscr){
        sendStringElement(AddNewProduct.INPUT_SHORT_DESCRIPTION_RU, dscr);
        return this;
    }

    public AddNewProductPage setHeadTitleEn(String hdr){
        sendStringElement(AddNewProduct.INPUT_HEAD_TITLE_EN, hdr);
        return this;
    }

    public AddNewProductPage setHeadTitleRu(String hdr){
        sendStringElement(AddNewProduct.INPUT_HEAD_TITLE_RU, hdr);
        return this;
    }

    //third tab
    public AddNewProductPage setPurchasePrice(double price){
        driver.findElement(AddNewProduct.INPUT_PURCHASE_PRICE).clear();
        sendStringElement(
                AddNewProduct.INPUT_PURCHASE_PRICE, String.format("%.2f", price).replace('.', ','));
        return this;
    }

    public AddNewProductPage selectPurchaseCurrencyCode(){
        assert isElementPresent(AddNewProduct.SELECT_PURCHASE_CORRENCY) : "Selector Purchase Currency Code not found";
        WebElement manufacturerSelect = driver.findElement(AddNewProduct.SELECT_PURCHASE_CORRENCY);
        Select select = new Select(manufacturerSelect);
        select.selectByValue("RUR"); // Рубли РФ
        return this;
    }

    public AddNewProductPage setPriceUSD(double price){
        sendStringElement(AddNewProduct.INPUT_PRICE_USD, String.format("%.2f", price));
        return this;
    }

    public AddNewProductPage setPriceEUR(double price){
        sendStringElement(AddNewProduct.INPUT_PRICE_EUR, String.format("%.2f", price));
        return this;
    }

    public AddNewProductPage setPriceRUR(double price){
        sendStringElement(AddNewProduct.INPUT_PRICE_RUR, String.format("%.2f", price));
        return this;
    }

    // buttons
    public void submitSave(){
        clickElement(AddNewProduct.BTN_SAVE);
    }

    public void submitCancel(){
        clickElement(AddNewProduct.BTN_CANCEL);
    }

    public void submitDelete(){
        clickElement(AddNewProduct.BTN_DELETE);
    }

    // check error save
    public boolean isErrorSave(){
        setWait(2);
        if(isElementPresent(AddNewProduct.NOTICE_ERROR)){
            return driver.findElement(AddNewProduct.NOTICE_ERROR).isDisplayed();
        }
        return false;
    }
}
