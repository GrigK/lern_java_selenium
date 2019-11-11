package Pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

//import Locators.AdminLogin;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class AdminPanelLoginPage extends Page {
    public AdminPanelLoginPage(WebDriver driver) {
        super(driver, "http://litecart.local/admin/");
        PageFactory.initElements(driver, this);
        //PageFactory.initElements(new AjaxElementLocatorFactory(driver, Constants.WebDriverWaitDuration), this);
    }

    // elements this page
    @FindBy(name="username")
    public WebElement usernameInput;

    @FindBy(name="password")
    public WebElement passwordInput;

    @FindBy(name="login")
    public WebElement btnLogin;

    @FindBy(css="ul#box-apps-menu")
    public WebElement leftMenu;

    @FindBy(css="ul#box-apps-menu a")
    public List<WebElement> leftMenuUrls;

    @FindBy(css=".logotype")
    public WebElement logoImage;

    @FindBy(id="box-login")
    public List<WebElement> boxLogin;

    @FindBy(css="div.header a:nth-child(5)")
    public WebElement logoutIcon;


    // ***
    @Override
    public AdminPanelLoginPage open() {
        super.open();
        return this;
    }

    public AdminPanelLoginPage login(){
        return login("admin", "ujuf0311");
    }

    public AdminPanelLoginPage login(String name, String pass){
        assert isOnThisPage() : "Wrong login page";
        setWait(2);
        enterUsername(name);
        enterPassword(pass);
        submitLogin();
        return this;
    }

    public AdminPanelLoginPage logout(){
        logoutIcon.click();
        return this;
    }

    public boolean isOnThisPage() { return boxLogin.size() > 0;}

    public AdminPanelLoginPage enterUsername(String username) {
        usernameInput.sendKeys(username);
        return this;
    }

    public AdminPanelLoginPage enterPassword(String password) {
        passwordInput.sendKeys(password);
        return this;
    }

    public void submitLogin() {
        btnLogin.click();
        Assert.assertTrue("Login to admin panel failed", isElementPresent(leftMenu));
    }

    public List<String> getLeftMenuLinks(){
        List<String> urls = new ArrayList();
        leftMenuUrls.forEach((WebElement el) -> { urls.add(el.getAttribute("href")); });
        return urls;
    }

}
