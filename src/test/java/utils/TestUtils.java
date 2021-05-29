package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.Random;
import java.util.UUID;

public class TestUtils {

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
}
