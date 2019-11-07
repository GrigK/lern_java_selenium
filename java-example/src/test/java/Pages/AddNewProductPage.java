package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Locators.AddNewProduct;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
        sendStringElement(AddNewProduct.INPUT_QUANTITY, String.format("%.2f", quantity));
        return this;
    }

    public AddNewProductPage uploadImage(String path){
        sendStringElement(AddNewProduct.BTN_UPLOAD_IMAGE, path);
        return this;
    }

    public AddNewProductPage setDateFrom(String from){
        /**
         * format dateString = DDMMYYYY
         */
        sendStringElement(AddNewProduct.INPUT_DATE_FROM, from);
        return this;
    }

    public AddNewProductPage setDateTo(String to){
        /**
         * format dateString = DDMMYYYY
         */
        sendStringElement(AddNewProduct.INPUT_DATE_TO, to);
        return this;
    }

    public AddNewProductPage setManufacturer(){
        assert isElementPresent(AddNewProduct.SELECT_MANUFACTURER) : "Manufacturer list not found";
        WebElement manufacturerSelect = driver.findElement(AddNewProduct.SELECT_MANUFACTURER);
        Select select = new Select(manufacturerSelect);
        select.selectByValue("1"); // ACME Corp.
        return this;
    }

}
