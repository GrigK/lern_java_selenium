package Pages;

import org.openqa.selenium.WebDriver;

import Locators.AdminLogin;

public class AdminPanelLoginPage extends Page {
    public AdminPanelLoginPage(WebDriver driver) {
        super(driver, "http://litecart.local/admin/");
    }

    @Override
    public AdminPanelLoginPage open() {
        driver.get(url);
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
        assert  isElementPresent(AdminLogin.LOGOUT_ICON) : "Logout fail. Not found logout icon on admin side";
        driver.findElement(AdminLogin.LOGOUT_ICON).click(); // выход
        return this;
    }

    public boolean isOnThisPage() {
        return driver.findElements(AdminLogin.BOX_LOGIN).size() > 0;
    }

    public AdminPanelLoginPage enterUsername(String username) {
        assert isElementPresent(AdminLogin.USERNAME) : "Input username field on login page not found";
        driver.findElement(AdminLogin.USERNAME).sendKeys(username);
        return this;
    }

    public AdminPanelLoginPage enterPassword(String password) {
        assert isElementPresent(AdminLogin.PASSWORD) : "Input password field on login page not found";
        driver.findElement(AdminLogin.PASSWORD).sendKeys(password);
        return this;
    }

    public void submitLogin() {
        assert isElementPresent(AdminLogin.BTN_LOGIN) : "Button login on login page not found";
        driver.findElement(AdminLogin.BTN_LOGIN).click();
        assert isElementPresent(AdminLogin.LEFT_MENU) : "Login to admin panel fail";
    }
}
