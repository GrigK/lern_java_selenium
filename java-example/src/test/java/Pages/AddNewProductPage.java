package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

public class AddNewProductPage extends Page {
    public AddNewProductPage(WebDriver driver) {
        super(driver, "http://litecart.local/admin/?category_id=0&app=catalog&doc=edit_product");
        PageFactory.initElements(driver, this);
    }

    /* General Tab */
    @FindBy(css = "td#content  div.tabs li:nth-child(1) a")
    public WebElement tabGeneral;

    @FindBy(name = "status")
    public WebElement toggleStatusInputs;

    @FindBy(name = "name\\[en\\]")
    public WebElement inputNameEn;

    @FindBy(name = "name\\[ru\\]")
    public WebElement inputNameRu;

    @FindBy(name = "categories\\[\\]")
    public List<WebElement> checkCategories;

    @FindBy(name = "product_groups\\[\\]")
    public List<WebElement> checkGroups;

    @FindBy(name = "quantity")
    public WebElement inputQuantity;

    @FindBy(css = "input[name=new_images\\[\\]]")
    public WebElement btnUploadImage;

    @FindBy(name = "date_valid_from")
    public WebElement inputDateFrom;

    @FindBy(name = "date_valid_to")
    public WebElement inputDateTo;

    /* Information tab*/
    @FindBy(css = "td#content  div.tabs li:nth-child(2) a")
    public WebElement tabInformation;

    @FindBy(name = "manufacturer_id")
    public WebElement selectManufatcurer;

    @FindBy(name = "short_description\\[en\\]")
    public WebElement inputShortDescrEn;

    @FindBy(name = "short_description\\[ru\\]")
    public WebElement inputShortDescrRu;

    @FindBy(name = "head_title\\[en\\]")
    public WebElement inputHeadTitleEn;

    @FindBy(name = "head_title\\[ru\\]")
    public WebElement inputHeadTitleRu;

    /* Prices tab */
    @FindBy(css = "td#content  div.tabs li:nth-child(4) a")
    public WebElement tabPrices;

    @FindBy(name = "purchase_price")
    public WebElement inputPurchasePrice;

    @FindBy(name = "purchase_price_currency_code")
    public WebElement selectPurchaseCurrency;

    @FindBy(name = "prices\\[USD\\]")
    public WebElement inputPriceUSD;

    @FindBy(name = "prices\\[EUR\\]")
    public WebElement inputPriceEUR;

    @FindBy(name = "prices\\[RUR\\]")
    public WebElement inputPriceRUR;


    @FindBy(css = "button[name=save]")
    public WebElement btnSave;
    @FindBy(css = "button[name=cancel]")
    public WebElement btnCancel;
    @FindBy(css = "button[name=delete]")
    public WebElement btnDelete;

    private static final By NOTICE_ERROR = By.cssSelector("div#notices div.notice.errors");


    /* Methods */
    public AddNewProductPage openGeneralTab(){
        tabGeneral.click();
        return this;
    }

    public AddNewProductPage openInformationTab(){
        tabInformation.click();
        return this;
    }

    public AddNewProductPage openPricesTab(){
        tabPrices.click();
        return this;
    }

    // first tab
    public AddNewProductPage setEnabled(){
        toggleStatusInputs.click();

        return this;
    }

    public AddNewProductPage setNameEn(String name){
        inputNameEn.sendKeys(name);
        return this;
    }

    public AddNewProductPage setNameRu(String name){
        inputNameRu.sendKeys(name);

        return this;
    }

    public AddNewProductPage setCategory(int cat){
        checkCategories.get(cat).click();
        return this;
    }

    public AddNewProductPage setGroup(int grp){
        checkGroups.get(grp).click();
        return this;
    }

    public AddNewProductPage setQuantity(double quantity){
        inputQuantity.clear();
        inputQuantity.sendKeys(String.format("%.2f", quantity).replace('.', ','));
        return this;
    }

    public AddNewProductPage uploadImage(String filename){
        File file = new File(filename);
        String path = file.getAbsolutePath();
        btnUploadImage.sendKeys(path);
        return this;
    }

    public AddNewProductPage setDateFrom(String from){
        /**
         * format dateString = MM/DD/YYYY
         */
        inputDateFrom.sendKeys(from);
        return this;
    }

    public AddNewProductPage setDateTo(String to){
        /**
         * format dateString = MM/DD/YYYY
         */
        inputDateTo.sendKeys(to);
        return this;
    }

    // second tab
    public AddNewProductPage setManufacturer(){
        Select select = new Select(selectManufatcurer);
        select.selectByValue("1"); // ACME Corp.
        return this;
    }

    public AddNewProductPage setShortDescriptionEn(String dscr){
        inputShortDescrEn.sendKeys(dscr);
        return this;
    }

    public AddNewProductPage setShortDescriptionRu(String dscr){
        inputShortDescrRu.sendKeys(dscr);
        return this;
    }

    public AddNewProductPage setHeadTitleEn(String hdr){
        inputHeadTitleEn.sendKeys(hdr);
        return this;
    }

    public AddNewProductPage setHeadTitleRu(String hdr){
        inputHeadTitleRu.sendKeys(hdr);
        return this;
    }

    //third tab
    public AddNewProductPage setPurchasePrice(double price){
        inputPurchasePrice.clear();
        inputPurchasePrice.sendKeys(String.format("%.2f", price).replace('.', ','));
        return this;
    }

    public AddNewProductPage selectPurchaseCurrencyCode(){
        Select select = new Select(selectPurchaseCurrency);
        select.selectByValue("RUR"); // Рубли РФ
        return this;
    }

    public AddNewProductPage setPriceUSD(double price){
        inputPriceUSD.sendKeys(String.format("%.2f", price));
        return this;
    }

    public AddNewProductPage setPriceEUR(double price){
        inputPriceEUR.sendKeys(String.format("%.2f", price));
        return this;
    }

    public AddNewProductPage setPriceRUR(double price){
        inputPriceRUR.sendKeys(String.format("%.2f", price));
        return this;
    }

    // buttons
    public void submitSave(){ btnSave.click();}

    public void submitCancel(){ btnCancel.click();}

    public void submitDelete(){ btnDelete.click();}

    // check error save
    public boolean isErrorSave(){
        setWait(2);
        if(areElementsPresentWait(NOTICE_ERROR)){
            return driver.findElement(NOTICE_ERROR).isDisplayed();
        }
        return false;
    }
}
