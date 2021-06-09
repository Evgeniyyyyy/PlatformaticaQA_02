package utils;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

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
}
