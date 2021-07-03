package utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.*;

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

    public static List<String> getActualValues(List<WebElement> actualElements) {
        List<String> listValues = new ArrayList<>();
        for (WebElement element : actualElements) {
            listValues.add(element.getText());
        }
        return listValues;
    }

    public static void getEntity(WebDriver driver, String name) {
        jsClick(driver, driver.findElement(By.xpath("//p[text()= ' " + name + " ']")));
    }

    public static void clickActionsView(WebDriverWait wait, WebDriver driver, int row) {
        driver.findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[text()='menu']")).click();
        wait.until(TestUtils.movingIsFinished(driver.
            findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='view']")))).click();
    }

    public static void clickActionsView(WebDriverWait wait, WebDriver driver) {
        clickActionsView(wait, driver, 0);
    }

    public static void clickActionsEdit(WebDriverWait wait, WebDriver driver, int row) {
        driver.findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[text()='menu']")).click();
        wait.until(TestUtils.movingIsFinished(driver.
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='edit']")))).click();
    }

    public static void clickActionsEdit(WebDriverWait wait, WebDriver driver) {
        clickActionsEdit(wait, driver, 0);
    }

    public static void clickActionsDelete(WebDriverWait wait, WebDriver driver, int row) {
        driver.findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[text()='menu']")).click();
        wait.until(TestUtils.movingIsFinished(driver.
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='delete']")))).click();
    }

    public static void clickActionsDelete(WebDriverWait wait, WebDriver driver) {
        clickActionsDelete(wait, driver, 0);
    }

    public static String getTextRandom(Integer value) {
        return RandomStringUtils.randomAlphabetic(value);
    }

    public static String getIntRandom() {
        return  String.valueOf(RandomUtils.nextInt());
    }

    public static String getDoubleRandom() {
        return String.format(Locale.ROOT, "%.2f", new Random().nextFloat());
    }

    public static String getDate(Integer value) {
        return new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(new Date(), value));
    }

    public static String getDateTime(Integer value) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(DateUtils.addDays(new Date(), value));
    }

    public static String getUser() {
        return "tester" + getRandom(299) + "@tester.test";
    }

    public static Integer getRandom(Integer value) {
        return new Random().nextInt(value);
    }

    public static void sendKeysOneByOne(WebElement element, String input) {

        char[] editKeys = input.toCharArray();

        for (char c : editKeys) {
            element.sendKeys(String.valueOf(c));

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
