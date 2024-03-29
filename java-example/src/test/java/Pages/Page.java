package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.NoSuchElementException;

public class Page {
    protected WebDriver driver;
    public WebDriverWait wait;

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

    /* *** */
    public boolean areElementsPresent(By locator) {
        return driver.findElements(locator).size() > 0;
    }

    public boolean areElementsPresentWait(By locator) {
        try {
            wait.until((WebDriver d) -> d.findElements(locator).size() > 0);
            return true;
        } catch (InvalidSelectorException e) {
            throw e;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickElement(By locator) {
        assert isElementPresent(locator) : locator.toString() + " not found on page " + driver.getCurrentUrl();
        driver.findElement(locator).click();
    }

    public void clickElement(WebElement element) {
        assert isElementPresent(element) : "WebElement not found on page " + driver.getCurrentUrl();
        element.click();
    }

    public boolean expectTitlePage(String title){
        return wait.until(ExpectedConditions.titleIs(title));
    }

    public Alert getAlert(){
        try {
            return wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException e) {
            return null;
        }
    }

    public boolean isElementDisappeared(By locator) {
        try {
            wait.until((WebDriver d) -> ExpectedConditions.invisibilityOf(d.findElement(locator)));
            return true;
        } catch (InvalidSelectorException e) {
            throw e;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementDisappeared(WebElement element) {
        try {
            wait.until(ExpectedConditions.stalenessOf(element));
            return true;
        }catch (StaleElementReferenceException e){
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementDisappeared(WebElement el, By locator) {
        try {
            wait.until((WebDriver d) -> ExpectedConditions.invisibilityOf(el.findElement(locator)));
            return true;
        } catch (InvalidSelectorException e) {
            throw e;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementNotPresent(By locator) {
        return !isElementPresentNoWait(locator);
    }

    public boolean isElementPresent(By locator) {
        try {
            wait.until((WebDriver d) -> ExpectedConditions.visibilityOf(d.findElement(locator)));
            return true;
        } catch (InvalidSelectorException e) {
            throw e;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementPresent(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
//             wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(element)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementNotPresent(WebElement element) {
        try {
            wait.until(ExpectedConditions.stalenessOf(element));
            return true;
        } catch (StaleElementReferenceException e){
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementPresent(WebElement el, By locator) {
        try {
            wait.until((WebDriver d) -> ExpectedConditions.visibilityOf(el.findElement(locator)));
            return true;
        } catch (InvalidSelectorException e) {
            throw e;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isElementPresentNoWait(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (InvalidSelectorException e) {
            throw e;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresentNoWait(WebElement element) {
        try {
            ExpectedConditions.not(ExpectedConditions.stalenessOf(element));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void sendStringElement(By locator, String str) {
        assert areElementsPresent(locator) : locator.toString() + " not found on page " + driver.getCurrentUrl();
        driver.findElement(locator).sendKeys(str);
    }

    public void setDatePicker(By selectorCalendar, String date) {
        /**
         * установить дату в jQuery календарике
         * setDatepicker(By.id("#datepicker"), "02/20/2019")
         */
        try {
            wait.until((WebDriver d) -> d.findElement(selectorCalendar).isDisplayed());
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                    String.format("$('{0}').datepicker('setDate', '{1}')", selectorCalendar.toString(), date));
        } catch (TimeoutException e) {
            assert false :
                    selectorCalendar.toString() + " not found(timeout) on page " + driver.getCurrentUrl();
        }
    }

}
