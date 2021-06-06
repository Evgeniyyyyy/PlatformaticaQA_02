package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Random;
import java.util.UUID;

public class TestUtils {

    private static class MovingExpectedCondition implements ExpectedCondition<WebElement> {

        private By locator;
        private WebElement element = null;
        private Point location = null;

        public MovingExpectedCondition(WebElement element) {
            this.element = element;
        }

        public MovingExpectedCondition(By locator) {
            this.locator = locator;
        }

        @Override
        public WebElement apply(WebDriver driver) {
            if (element == null) {
                try {
                    element = driver.findElement(locator);
                } catch (NoSuchElementException e) {
                    return null;
                }
            }
            if (element.isDisplayed()) {
                Point location = element.getLocation();
                if (location.equals(this.location)) {
                    return element;
                }
                this.location = location;
            }
            return null;
        }
    }

    public static void jsClick(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public static void scroll(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void scrollClick(WebDriver driver, WebElement element) {
        scroll(driver, element);
        element.click();
    }

    public static void scrollClick(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        scroll(driver, element);
        element.click();
    }

    /*
        example: getWait().until(TestUtils.movingIsFinished(<locator> | <element>)).click();
     */

    public static ExpectedCondition<WebElement> movingIsFinished(WebElement element) {
        return new MovingExpectedCondition(element);
    }

    public static ExpectedCondition<WebElement> movingIsFinished(By locator) {
        return new MovingExpectedCondition(locator);
    }
}
