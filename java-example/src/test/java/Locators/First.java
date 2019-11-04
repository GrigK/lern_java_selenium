package Locators;

import org.openqa.selenium.By;

public class First {
    public static final By VIRTUAL_KEYBOARD = By.cssSelector("div[aria-label=\"Экранная клавиатура\"]");
    public static final By INPUT_FIELD = By.name("q");
    public static final By BTN_OK = By.name("btnK");
    public static final By KEY_SPACE = By.id("K32");
}