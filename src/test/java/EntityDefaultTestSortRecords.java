import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.ProjectUtils.*;
import utils.TestUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static utils.ProjectUtils.clickCreateRecord;
import static utils.ProjectUtils.start;
import static utils.TestUtils.jsClick;
import static utils.TestUtils.scrollClick;

public class EntityDefaultTestSortRecords extends BaseTest {

    private static final By DEFAULT_TAB = By.xpath("//p[contains (text(), 'Default')]");
    private static final By CREATE_NEW_RECORD = By.xpath("//div/i[.='create_new_folder']");
    private static final By STRING_FIELD = By.id("string");
    private static final By TEXT_FIELD = By.id("text");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By DATE_FIELD = By.id("date");
    private static final By DATETIME_FIELD = By.id("datetime");
    private static final By TESTER_NAME_FIELD = By.xpath("//div[@class = 'filter-option-inner']");
    private static final By TESTER_NAME = By.xpath("//span[text()='tester100@tester.test']");
    private static final By TESTER_NAME2 = By.xpath("//span[text()='tester88@tester.test']");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By CHECK_ICON = By.xpath("//tbody/tr[1]/td[1]/i[1]");
    private static final By COLUMN_FIELD = By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']");
    private static final By ACTIONS_BUTTON = By.xpath("//i[text()='menu']");
    private static final By VIEW_OPTION = By.xpath("//a[@href] [contains(text(), 'view')]");
    private static final By LIST_OF_RECORDS = By.xpath("//span [@class = 'pa-view-field']");
    private static final By USER_FIELD = By.xpath("//div [@class = 'form-group']/p");
    private static final By EXIT_BUTTON = By.xpath("//i[contains(text(),'clear')]");
    private static final By EDIT_OPTION = By.xpath("//a[@href] [contains(text(), 'edit')]");
    private static final By DELETE_BUTTON = By.xpath("//a[@href] [contains(text(), 'delete')]");
    private static final By RECYCLE_BIN = By.xpath("//i[contains(text(),'delete_outline')]");
    private static final By RECYCLE_INFO = By.xpath("//span[@class='pagination-info']");
    private static final By COLUMN_STRING = By.xpath("//thead/tr/th/div[text()=\"String\"]");
    private static final By COLUMN_TEXT = By.xpath("//thead/tr/th/div[text()=\"Text\"]");
    private static final By COLUMN_INT = By.xpath("//thead/tr/th/div[text()=\"Int\"]");
    private static final By COLUMN_DECIMAL = By.xpath("//thead/tr/th/div[text()=\"Decimal\"]");
    private static final By COLUMN_USER = By.xpath("//thead/tr/th/div[text()=\"User\"]");

    final String emptyField = "";

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private void clearFields() {
        findElement(STRING_FIELD).clear();
        findElement(TEXT_FIELD).clear();
        findElement(INT_FIELD).clear();
        findElement(DECIMAL_FIELD).clear();
        findElement(DATE_FIELD).clear();
        findElement(DATETIME_FIELD).clear();
    }

    public List<Object> createRecordForSort(
            String stringInputValueSort, String textInputValueSort,
            String intInputValueSort, String decimalInputValueSort,
            String userNameSort) {

        findElement(STRING_FIELD).sendKeys(stringInputValueSort);
        findElement(TEXT_FIELD).sendKeys(textInputValueSort);
        findElement(INT_FIELD).sendKeys(intInputValueSort);
        findElement(DECIMAL_FIELD).sendKeys(decimalInputValueSort);
        findElement(DATE_FIELD).click();
        findElement(TESTER_NAME_FIELD).click();
        By userNameForSort = By.xpath("//span[text()='"+ userNameSort + "']");

        jsClick(getDriver(), findElement(userNameForSort));
        final List<Object> expectedValues = Arrays
                .asList(stringInputValueSort, textInputValueSort, intInputValueSort, decimalInputValueSort,
                        formatter.format(date), emptyField, emptyField, emptyField, userNameSort);
        return expectedValues;
    }

    @Test
    public void testSortRecords() {
        start(getDriver());
        scrollClick(getDriver(), findElement(DEFAULT_TAB));
        clickCreateRecord(getDriver());
        clearFields();
        List<Object> expectedValues1 = createRecordForSort(
                "String",
                "Text",
                "2021",
                "0.10",
                "tester100@tester.test");
        clickSave(getDriver());

        getWait().until(ExpectedConditions.elementToBeClickable(findElement(CREATE_NEW_RECORD)));
        clickCreateRecord(getDriver());
        clearFields();
        List<Object> expectedValues2 = createRecordForSort(
                "Pending",
                "Success",
                "2018",
                "0.25",
                "tester88@tester.test");
        clickSaveDraft(getDriver());

        getWait().until(ExpectedConditions.elementToBeClickable(findElement(CREATE_NEW_RECORD)));
        clickCreateRecord(getDriver());
        clearFields();
        List<Object> expectedValues3 = createRecordForSort(
                "Yamal",
                "News",
                "2035",
                "0.12",
                "tester107@tester.test");
        clickSave(getDriver());
        getWait().until(ExpectedConditions.elementToBeClickable(findElement(CREATE_NEW_RECORD)));

        WebElement icon1 = findElement(By.xpath("//tbody/tr[1]/td[1]/i[1]"));
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-check-square-o");
        WebElement icon2 = findElement(By.xpath("//tbody/tr[2]/td[1]/i[1]"));
        Assert.assertEquals(icon2.getAttribute("class"), "fa fa-pencil");
        WebElement icon3 = findElement(By.xpath("//tbody/tr[3]/td[1]/i[1]"));
        Assert.assertEquals(icon3.getAttribute("class"), "fa fa-check-square-o");

        findElement(COLUMN_STRING).click();
        icon1 = findElement(By.xpath("//tbody/tr[1]/td[1]/i[1]"));
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-pencil");

        findElement(COLUMN_TEXT).click();
        icon2 = findElement(By.xpath("//tbody/tr[2]/td[1]/i[1]"));
        Assert.assertEquals(icon2.getAttribute("class"), "fa fa-pencil");

        findElement(COLUMN_INT).click();
        icon1 = findElement(By.xpath("//tbody/tr[1]/td[1]/i[1]"));
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-pencil");

        findElement(COLUMN_DECIMAL).click();
        icon3 = findElement(By.xpath("//tbody/tr[3]/td[1]/i[1]"));
        Assert.assertEquals(icon3.getAttribute("class"), "fa fa-pencil");

    }
}
