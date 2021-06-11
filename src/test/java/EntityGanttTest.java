import base.BaseTest;
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

public class EntityGanttTest extends BaseTest {

    private static final By STRING_FIELD = By.id("string");
    private static final By TEXT_FIELD = By.id("text");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By DATE_FIELD = By.id("date");
    private static final By TESTER_NAME_FIELD = By.xpath("//div[contains(text(),'apptester1@tester.test')]");
    private static final By TESTER_NAME = By.xpath("//span[text()='tester100@tester.test']");
    private static final By LIST_BUTTON = By.xpath("//a[@href=\"index.php?action=action_list&list_type=table&entity_id=35\"]");
    private static final By CHECK_ICON = By.xpath("//tbody/tr[1]/td[1]/i[1]");
    private static final By COLUMN_FIELD = By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']");
    private static final By ACTIONS_BUTTON = By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']");
    private static final By VIEW_BUTTON = By.xpath("//a[normalize-space()='view']");
    private static final By EDIT_BUTTON = By.xpath("//a[@href] [contains(text(), 'edit')]");
    private static final By DELETE_BUTTON = By.xpath("//a[@href] [contains(text(), 'delete')]");
    private static final By RECYCLE_INFO = By.xpath("//span[@class='pagination-info']");
    private static final By LIST_OF_RECORDS = By.xpath("//span [@class = 'pa-view-field']");
    private static final By EXIT_BUTTON = By.xpath("//i[contains(text(),'clear')]");
    private static final By USER_FIELD = By.xpath("//div [@class = 'form-group']/p");
    private static final By ROWS_ELEMENT = By.xpath("//div[@class='fixed-table-body']//table[@id='pa-all-entities-table']/tbody/tr");
    private static final By CARD_BODY = By.xpath("//div[@class = 'card-body ']");

    private static final String STRING_INPUT_VALUE = "Test";
    private static final String TEXT_INPUT_VALUE = "Text";
    private static final String INT_INPUT_VALUE = "100";
    private static final String DECIMAL_INPUT_VALUE = "0.10";
    private static final String EMPTY_FIELD = "";
    private static final String USER_NAME = "tester100@tester.test";

    private final Date date = new Date();
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private final List<Object> expectedValues = Arrays
            .asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
                    formatter.format(date), EMPTY_FIELD, EMPTY_FIELD, USER_NAME);

    private void clickListButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(LIST_BUTTON)))
                .click();
    }

    private void chooseSideBarItem() {
        scrollClick(getDriver(), By.xpath("//a[@class='nav-link'][contains(.,'Gantt')]"));
    }

    private void startCreateSteps() {
        chooseSideBarItem();

        clickCreateRecord(getDriver());
        findElement(STRING_FIELD).sendKeys(STRING_INPUT_VALUE);
        findElement(TEXT_FIELD).sendKeys(TEXT_INPUT_VALUE);
        findElement(INT_FIELD).sendKeys(INT_INPUT_VALUE);
        findElement(DECIMAL_FIELD).sendKeys(DECIMAL_INPUT_VALUE);
        findElement(DATE_FIELD).click();
        findElement(TESTER_NAME_FIELD).click();

        jsClick(getDriver(), findElement(TESTER_NAME));
    }

    @Test
    public void testCreateRecord() {

        startCreateSteps();

        clickSave(getDriver());
        clickListButton();

        WebElement icon1 = findElement(CHECK_ICON);
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-check-square-o");

        List<WebElement> columnList = findElements(COLUMN_FIELD);
        Assert.assertEquals(columnList.size(), expectedValues.size());
        for (int i = 0; i < expectedValues.size(); i++) {
            Assert.assertEquals(columnList.get(i).getText(), expectedValues.get(i).toString());
        }
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {

        chooseSideBarItem();

        clickListButton();
        findElement(ACTIONS_BUTTON).click();

        List<WebElement> rows = getDriver().findElements(ROWS_ELEMENT);
        Assert.assertEquals(rows.size(), 1);

        getWait().until(TestUtils.movingIsFinished(getDriver().findElement(VIEW_BUTTON))).click();

        List<Object> expectedRecords = Arrays.asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE,
                DECIMAL_INPUT_VALUE, formatter.format(date), EMPTY_FIELD);
        List<WebElement> actualRecords = findElements(LIST_OF_RECORDS);
        for (int i = 0; i < expectedRecords.size(); i++) {
            Assert.assertEquals(actualRecords.get(i).getText(), expectedRecords.get(i).toString());
        }
        Assert.assertEquals(findElement(USER_FIELD).getText(), USER_NAME);

        findElement(EXIT_BUTTON).click();
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testEditRecord() {

        String stringInputValue2 = "Test2";
        String textInputValue2 = "Text2";
        String intInputValue2 = "200";
        String decimalInputValue2 = "0.20";

        List<Object> expectedValues2 = Arrays
                .asList(stringInputValue2, textInputValue2, intInputValue2, decimalInputValue2,
                        formatter.format(date), EMPTY_FIELD, EMPTY_FIELD, USER_NAME);

        chooseSideBarItem();

        clickListButton();
        findElement(ACTIONS_BUTTON).click();

        getWait().until(TestUtils.movingIsFinished(getDriver().findElement(EDIT_BUTTON))).click();

        findElement(STRING_FIELD).clear();
        findElement(TEXT_FIELD).clear();
        findElement(INT_FIELD).clear();
        findElement(DECIMAL_FIELD).clear();

        findElement(STRING_FIELD).sendKeys(stringInputValue2);
        findElement(TEXT_FIELD).sendKeys(textInputValue2);
        findElement(INT_FIELD).sendKeys(intInputValue2);
        findElement(DECIMAL_FIELD).sendKeys(decimalInputValue2);

        clickSave(getDriver());

        clickListButton();

        WebElement icon1 = findElement(CHECK_ICON);
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-check-square-o");

        List<WebElement> columnList = findElements(COLUMN_FIELD);
        Assert.assertEquals(columnList.size(), expectedValues2.size());
        for (int i = 0; i < expectedValues2.size(); i++) {
            Assert.assertEquals(columnList.get(i).getText(), expectedValues2.get(i).toString());
        }
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {
        chooseSideBarItem();

        clickListButton();
        findElement(ACTIONS_BUTTON).click();

        List<WebElement> rows = getDriver().findElements(ROWS_ELEMENT);
        Assert.assertEquals(rows.size(), 1);

        getWait().until(TestUtils.movingIsFinished(getDriver().findElement(DELETE_BUTTON))).click();

        String textCardBodyAfterDelete = getDriver().findElement(CARD_BODY).getText();
        Assert.assertTrue(textCardBodyAfterDelete.isEmpty());

        clickRecycleBin(getDriver());

        WebElement recycleBinPage = findElement(RECYCLE_INFO);
        String currentString = recycleBinPage.getText();
        boolean checkBin = !currentString.equals("");
        Assert.assertTrue(checkBin, "Showing 1 to 1 of 1 rows");
    }

    @Test
    public void testCreateDraftRecord() {

        startCreateSteps();

        clickSaveDraft(getDriver());
        clickListButton();

        WebElement icon2 = findElement(CHECK_ICON);
        Assert.assertEquals(icon2.getAttribute("class"), "fa fa-pencil");

        List<WebElement> columnList = findElements(COLUMN_FIELD);

        Assert.assertEquals(columnList.size(), expectedValues.size());
        for (int i = 0; i < expectedValues.size(); i++) {
            Assert.assertEquals(columnList.get(i).getText(), expectedValues.get(i).toString());
        }
    }
}