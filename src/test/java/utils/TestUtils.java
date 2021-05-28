package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Random;
import java.util.UUID;

public class TestUtils {

    final static Random random = new Random();
    final static String Text = UUID.randomUUID().toString();
    final static String Int = "" + random.nextInt();
    final static String Decimal = "" + random.nextDouble();

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

    public static void createNewFolder(WebDriver driver) {
        driver.findElement(By.xpath("//i[text()='create_new_folder']")).click();
    }

    public static void stringField(WebDriver driver) {
        driver.findElement(By.id("string")).sendKeys(Text);
    }

    public static void textField(WebDriver driver) {
        driver.findElement(By.id("text")).sendKeys(Text);
    }

    public static void intField(WebDriver driver) {
        driver.findElement(By.id("int")).sendKeys(Int);
    }

    public static void decimalField(WebDriver driver) {
        driver.findElement(By.id("decimal")).sendKeys(Decimal);
    }

    public static void dateField(WebDriver driver) {
        driver.findElement(By.id("date")).click();
    }

    public static void datetimeField(WebDriver driver) {
        driver.findElement(By.id("datetime")).click();
    }

    public static void draftButton(WebDriver driver) {
        WebElement draft = driver.findElement(By.id("pa-entity-form-draft-btn"));
        TestUtils.jsClick(driver, draft);
    }

    public static void tagButtonSideMenu(WebDriver driver) {
        WebElement tag = driver.findElement(By.xpath("//p[text()=' Tag ']"));
        TestUtils.scrollClick(driver, tag);
    }
}
