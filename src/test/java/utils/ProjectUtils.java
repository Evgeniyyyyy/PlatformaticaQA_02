package utils;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import static utils.TestUtils.jsClick;

public class ProjectUtils {

    @Deprecated
    public static void login(WebDriver driver) {
    }

    @Deprecated
    public static void login(WebDriver driver, String login, String pas) {
    }

    @Deprecated
    public static void reset(WebDriver driver) {
    }

    @Deprecated
    public static void get(WebDriver driver) {
    }

    @Deprecated
    public static void start(WebDriver driver) {
    }

    public static void clickSave(WebDriver driver) {
        TestUtils.jsClick(driver, driver.findElement(By.id("pa-entity-form-save-btn")));
    }

    public static void clickSaveDraft(WebDriver driver) {
        TestUtils.jsClick(driver, driver.findElement(By.id("pa-entity-form-draft-btn")));
    }

    public static void clickCancel(WebDriver driver) {
        TestUtils.jsClick(driver, driver.findElement(By.xpath("//button[text()='Cancel']")));
    }

    public static void clickCreateRecord(WebDriver driver) {
        driver.findElement(By.xpath("//i[text()='create_new_folder']")).click();
    }

    public static void clickRecycleBin(WebDriver driver) {
        driver.findElement(By.xpath("//i[text()='delete_outline']")).click();
    }

    public static void cleanOut(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        driver.findElement(By.id("navbarDropdownProfile")).click();
        TestUtils.jsClick(driver,
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'!!! Reset all for my user !!!')]"))));
    }

    public static List<WebElement> ACTUAL_RESULT(WebDriver driver) {
        return List.of(driver.findElement(By.xpath("//tbody/tr/td/a")));
    }

    public static void getEntity(WebDriver driver, String name) {
        jsClick(driver, driver.findElement(By.xpath("//p[text()= ' " + name + " ']")));
    }

    public static void clickActionsView(WebDriver driver) {
        driver.findElement(By.xpath("//button/i[@class='material-icons']")).click();
        TestUtils.jsClick(driver, driver.findElement(By.xpath("//a[text()='view']")));
    }

    public static void clickActionsEdit(WebDriver driver) {
        driver.findElement(By.xpath("//button/i[@class='material-icons']")).click();
        TestUtils.jsClick(driver, driver.findElement(By.xpath("//a[text()='edit']")));
    }

    public static void clickActionsDelete(WebDriver driver) {
        driver.findElement(By.xpath("//button/i[@class='material-icons']")).click();
        TestUtils.jsClick(driver, driver.findElement(By.xpath("//a[text()='delete']")));
    }
}
