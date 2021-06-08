import base.DriverPerClassBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import utils.TestUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.*;

public class EntityGanttTest extends DriverPerClassBaseTest {

    private static final By GANTT_TAB = By.xpath("//p[contains (text(), 'Gantt')]");
    private static final By CREATE_NEW_RECORD = By.xpath("//div/i[.='create_new_folder']");
    private static final By STRING_FIELD = By.id("string");
    private static final By TEXT_FIELD = By.id("text");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By DATE_FIELD = By.id("date");
    private static final By TESTER_NAME_FIELD = By.xpath("//div[contains(text(),'apptester1@tester.test')]");
    private static final By TESTER_NAME = By.xpath("//span[text()='tester100@tester.test']");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By DRAFT_BUTTON = By.id("pa-entity-form-draft-btn");
    private static final By LIST_BUTTON = By.xpath("//a[@href=\"index.php?action=action_list&list_type=table&entity_id=35\"]");
    private static final By CHECK_ICON = By.xpath("//tbody/tr[1]/td[1]/i[1]");
    private static final By COLUMN_FIELD = By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']");
    private static final By ACTIONS_BUTTON = By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']");
    private static final By VIEW_BUTTON = By.xpath("//a[normalize-space()='view']");
    private static final By EDIT_BUTTON = By.xpath("//a[@href] [contains(text(), 'edit')]");
    private static final By LIST_OF_RECORDS = By.xpath("//span [@class = 'pa-view-field']");
    private static final By EXIT_BUTTON = By.xpath("//i[contains(text(),'clear')]");
    private static final By USER_FIELD = By.xpath("//div [@class = 'form-group']/p");
    private static final By ROWS_ELEMENT = By.xpath(" //div[@class='fixed-table-body']//table[@id='pa-all-entities-table']/tbody/tr");

    private final String stringInputValue = "Test";
    private final String textInputValue = "Text";
    private final String intInputValue = "100";
    private final String decimalInputValue = "0.10";
    private final String emptyField = "";
    private final String userName = "tester100@tester.test";

    private final Date date = new Date();
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private final List<Object> expectedValues = Arrays
            .asList(stringInputValue, textInputValue, intInputValue, decimalInputValue,
                    formatter.format(date), emptyField, emptyField, userName);

    private void clickListButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(LIST_BUTTON)))
                .click();
    }

    @Test
    public void testCreateRecord() {

        start(getDriver());

        scrollClick(getDriver(), findElement(GANTT_TAB));

        findElement(CREATE_NEW_RECORD).click();
        findElement(STRING_FIELD).sendKeys(stringInputValue);
        findElement(TEXT_FIELD).sendKeys(textInputValue);
        findElement(INT_FIELD).sendKeys(intInputValue);
        findElement(DECIMAL_FIELD).sendKeys(decimalInputValue);
        findElement(DATE_FIELD).click();
        findElement(TESTER_NAME_FIELD).click();

        jsClick(getDriver(), findElement(TESTER_NAME));
        jsClick(getDriver(), findElement(SAVE_BUTTON));

        clickListButton();

        WebElement icon1 = findElement(CHECK_ICON);
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-check-square-o");

        final List<WebElement> columnList = findElements(COLUMN_FIELD);

        Assert.assertEquals(columnList.size(), expectedValues.size());
        for (int i = 0; i < expectedValues.size(); i++) {
            Assert.assertEquals(columnList.get(i).getText(), expectedValues.get(i).toString());
        }
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {

        final List<WebElement> rows = getDriver().findElements(ROWS_ELEMENT);
        Assert.assertEquals(rows.size(), 1);

        findElement(ACTIONS_BUTTON).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().findElement(VIEW_BUTTON))).click();

        final List<Object> expectedRecords = Arrays.asList(stringInputValue, textInputValue, intInputValue,
                decimalInputValue, formatter.format(date), emptyField);
        final List<WebElement> actualRecords = findElements(LIST_OF_RECORDS);

        for (int i = 0; i < expectedRecords.size(); i++) {
            Assert.assertEquals(actualRecords.get(i).getText(), expectedRecords.get(i).toString());
        }
        Assert.assertEquals(findElement(USER_FIELD).getText(), userName);

        findElement(EXIT_BUTTON).click();
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testCreateDraftRecord() {

        findElement(ACTIONS_BUTTON).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().findElement(EDIT_BUTTON))).click();

        jsClick(getDriver(), findElement(DRAFT_BUTTON));
        clickListButton();

        WebElement icon2 = findElement(CHECK_ICON);
        Assert.assertEquals(icon2.getAttribute("class"), "fa fa-pencil");

        final List<WebElement> columnList = findElements(COLUMN_FIELD);

        Assert.assertEquals(columnList.size(), expectedValues.size());
        for (int i = 0; i < expectedValues.size(); i++) {
            Assert.assertEquals(columnList.get(i).getText(), expectedValues.get(i).toString());
        }
    }
}