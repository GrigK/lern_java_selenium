package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

public class Page {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected String url;
    protected long waitSec = 5;

    public Page(WebDriver driver, String url) {
        this.driver = driver;
        setUrl(url);
        wait = new WebDriverWait(driver, waitSec);
    }

    public Page open() {
        driver.get(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setWait(long seconds) {
        waitSec = seconds;
        this.wait = new WebDriverWait(driver, waitSec);
    }

    public boolean isElementPresent(By locator){
        try {
            wait.until((WebDriver d) -> ExpectedConditions.visibilityOf(d.findElement(locator)));
            return true;
        } catch (InvalidSelectorException e){
            throw e;
        } catch (TimeoutException e){
            return false;
        }
    }

    public boolean isElementPresent(WebElement el, By locator){
        try {
            wait.until((WebDriver d) -> ExpectedConditions.visibilityOf(el.findElement(locator)));
            return true;
        } catch (InvalidSelectorException e){
            throw e;
        } catch (TimeoutException e){
            return false;
        }
    }

    public boolean isElementDisappeared(By locator){
        try {
            wait.until((WebDriver d) -> ExpectedConditions.invisibilityOf(d.findElement(locator)));
            return true;
        } catch (InvalidSelectorException e){
            throw e;
        } catch (TimeoutException e){
            return false;
        }
    }

    public boolean isElementDisappeared(WebElement el, By locator){
        try {
            wait.until((WebDriver d) -> ExpectedConditions.invisibilityOf(el.findElement(locator)));
            return true;
        } catch (InvalidSelectorException e){
            throw e;
        } catch (TimeoutException e){
            return false;
        }
    }

    public boolean isElementPresentNoWait(By locator){
        try {
            driver.findElement(locator);
            return true;
        } catch (InvalidSelectorException e){
            throw e;
        } catch (NoSuchElementException e){
            return false;
        }
    }

    public boolean areElementsPresent(By locator){
        return driver.findElements(locator).size() > 0;
    }

}
