import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static utils.ProjectUtils.login;
import static utils.ProjectUtils.start;
import static utils.TestUtils.jsClick;
import static utils.TestUtils.scrollClick;

public class EntityDefaultTest extends BaseTest {

    private static final By DEFAULT_TAB = By.xpath("//p[contains (text(), 'Default')]");
    private static final By CREATE_NEW_RECORD = By.xpath("//div/i[.='create_new_folder']");
    private static final By STRING_FIELD = By.id("string");
    private static final By TEXT_FIELD = By.id("text");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By DATE_FIELD = By.id("date");
    private static final By DATETIME_FIELD = By.id("datetime");
    private static final By TESTER_NAME_FIELD = By.xpath("//div[contains(text(),'apptester1@tester.test')]");
    private static final By TESTER_NAME = By.xpath("//span[text()='tester100@tester.test']");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By CHECK_ICON = By.xpath("//tbody/tr[1]/td[1]/i[1]");
    private static final By COLUMN_FIELD = By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']");
    private static final By ACTIONS_BUTTON = By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']");
    private static final By VIEW_OPTION = By.xpath("//a[@href] [contains(text(), 'view')]");
    private static final By LIST_OF_RECORDS = By.xpath("//span [@class = 'pa-view-field']");
    private static final By USER_FIELD = By.xpath("//div [@class = 'form-group']/p");
    private static final By EXIT_BUTTON = By.xpath("//i[contains(text(),'clear')]");

    final String stringInputValue = "String";
    final String textInputValue = "Text";
    final String intInputValue = "2021";
    final String decimalInputValue = "0.10";
    final String emptyField = "";
    final String userName = "tester100@tester.test";

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    final List<Object> expectedValues = Arrays
            .asList(stringInputValue, textInputValue, intInputValue, decimalInputValue,
                    formatter.format(date), emptyField, emptyField, emptyField, userName);

    private void createRecord() {
        start(getDriver());

        scrollClick(getDriver(), findElement(DEFAULT_TAB));

        findElement(CREATE_NEW_RECORD).click();
        findElement(STRING_FIELD).clear();
        findElement(TEXT_FIELD).clear();
        findElement(INT_FIELD).clear();
        findElement(DECIMAL_FIELD).clear();
        findElement(DATE_FIELD).clear();
        findElement(DATETIME_FIELD).clear();
        findElement(STRING_FIELD).sendKeys(stringInputValue);
        findElement(TEXT_FIELD).sendKeys(textInputValue);
        findElement(INT_FIELD).sendKeys(intInputValue);
        findElement(DECIMAL_FIELD).sendKeys(decimalInputValue);
        findElement(DATE_FIELD).click();
        findElement(TESTER_NAME_FIELD).click();

        jsClick(getDriver(), findElement(TESTER_NAME));
    }

    private void getAssertion(){
        List<WebElement> columnList = findElements(COLUMN_FIELD);

        Assert.assertEquals(columnList.size(), expectedValues.size());
        for (int i = 0; i < expectedValues.size(); i++) {
            Assert.assertEquals(columnList.get(i).getText(), expectedValues.get(i).toString());
        }
    }

    @Test
    public void testCreateRecord() {

        createRecord();
        jsClick(getDriver(), findElement(SAVE_BUTTON));

        WebElement icon1 = findElement(CHECK_ICON);
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-check-square-o");
        getAssertion();
    }

    @Test()
    public void testViewRecord() {

        createRecord();
        jsClick(getDriver(), findElement(SAVE_BUTTON));

        List<Object> expectedRecords = Arrays.asList(stringInputValue, textInputValue, intInputValue, decimalInputValue,
                formatter.format(date), emptyField);

        findElement(ACTIONS_BUTTON).click();

        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(VIEW_OPTION))).click();

        List<WebElement> actualRecords = findElements(LIST_OF_RECORDS);

        for (int i = 0; i < expectedRecords.size() ; i++) {
            Assert.assertEquals(actualRecords.get(i).getText(), expectedRecords.get(i).toString());
        }
        Assert.assertEquals(findElement(USER_FIELD).getText(), userName);

        findElement(EXIT_BUTTON).click();
    }
}