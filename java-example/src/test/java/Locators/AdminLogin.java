package Locators;

import org.openqa.selenium.By;

public class AdminLogin {
    public static final By USERNAME = By.cssSelector("div#box-login-wrapper input[name='username']");
    public static final By PASSWORD = By.cssSelector("div#box-login-wrapper input[name='password']");
    public static final By BTN_LOGIN = By.cssSelector("div#box-login-wrapper button[name='login']");
    public static final By LEFT_MENU = By.cssSelector("ul#box-apps-menu");
    public static final By LEFT_MENU_URLS = By.cssSelector("ul#box-apps-menu a");
    public static final By LOGO = By.cssSelector(".logotype");
    public static final By TITLE = By.cssSelector("#body h1");
}
