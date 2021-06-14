import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

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
    private static final By CHECK_ICON = By.xpath("//tbody/tr[1]/td[1]/i[1]");
    private static final By COLUMN_FIELD = By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']");
    private static final By RECYCLE_INFO = By.xpath("//span[@class='pagination-info']");
    private static final By ROWS_ELEMENT = By.xpath("//div[@class='fixed-table-body']//table[@id='pa-all-entities-table']/tbody/tr");

    private static final String STRING_INPUT_VALUE = "Test";
    private static final String TEXT_INPUT_VALUE = "Text";
    private static final String INT_INPUT_VALUE = "100";
    private static final String DECIMAL_INPUT_VALUE = "0.10";
    private static final String EMPTY_FIELD = "";
    private static final String USER_NAME = "tester100@tester.test";

    private static final String STRING_INPUT_VALUE2 = "Test2";
    private static final String TEXT_INPUT_VALUE2 = "Text2";
    private static final String INT_INPUT_VALUE2 = "200";
    private static final String DECIMAL_INPUT_VALUE2 = "0.20";

    private static final Date date = new Date();
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private static final List<Object> expectedValues = Arrays
            .asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
                    formatter.format(date), EMPTY_FIELD, EMPTY_FIELD, USER_NAME);

    private static final List<Object> expectedValues2 = Arrays
            .asList(STRING_INPUT_VALUE2, TEXT_INPUT_VALUE2, INT_INPUT_VALUE2, DECIMAL_INPUT_VALUE2,
                    formatter.format(date), EMPTY_FIELD, EMPTY_FIELD, USER_NAME);

    private void clickListButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(By.xpath("//a[@href=\"index.php?action=action_list&list_type=table&entity_id=35\"]"))))
                .click();
    }

    private void fillForm() {
        clickCreateRecord(getDriver());
        findElement(STRING_FIELD).sendKeys(STRING_INPUT_VALUE);
        findElement(TEXT_FIELD).sendKeys(TEXT_INPUT_VALUE);
        findElement(INT_FIELD).sendKeys(INT_INPUT_VALUE);
        findElement(DECIMAL_FIELD).sendKeys(DECIMAL_INPUT_VALUE);
        findElement(By.id("date")).click();
        findElement(By.xpath("//div[contains(text(),'apptester1@tester.test')]")).click();

        jsClick(getDriver(), findElement(By.xpath("//span[text()='tester100@tester.test']")));
    }

    @Test
    public void testCreateRecord() {

        getEntity(getDriver(), "Gantt");
        fillForm();
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

        getEntity(getDriver(), "Gantt");
        clickListButton();

        List<WebElement> rows = getDriver().findElements(ROWS_ELEMENT);
        Assert.assertEquals(rows.size(), 1);

        clickActionsView(getWait(), getDriver());

        List<Object> expectedRecords = Arrays.asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE,
                DECIMAL_INPUT_VALUE, formatter.format(date), EMPTY_FIELD);

        List<WebElement> actualRecords = findElements(By.xpath("//span [@class = 'pa-view-field']"));
        for (int i = 0; i < expectedRecords.size(); i++) {
            Assert.assertEquals(actualRecords.get(i).getText(), expectedRecords.get(i).toString());
        }
        Assert.assertEquals(findElement(By.xpath("//div [@class = 'form-group']/p")).getText(), USER_NAME);
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testEditRecord() {

        getEntity(getDriver(), "Gantt");
        clickListButton();

        List<WebElement> rows = getDriver().findElements(ROWS_ELEMENT);
        Assert.assertEquals(rows.size(), 1);

        clickActionsEdit(getWait(), getDriver());

        findElement(STRING_FIELD).clear();
        findElement(TEXT_FIELD).clear();
        findElement(INT_FIELD).clear();
        findElement(DECIMAL_FIELD).clear();

        findElement(STRING_FIELD).sendKeys(STRING_INPUT_VALUE2);
        findElement(TEXT_FIELD).sendKeys(TEXT_INPUT_VALUE2);
        findElement(INT_FIELD).sendKeys(INT_INPUT_VALUE2);
        findElement(DECIMAL_FIELD).sendKeys(DECIMAL_INPUT_VALUE2);

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
        getEntity(getDriver(), "Gantt");

        clickListButton();

        List<WebElement> rows = getDriver().findElements(ROWS_ELEMENT);
        Assert.assertEquals(rows.size(), 1);

        clickActionsDelete(getWait(), getDriver());

        String textCardBodyAfterDelete = getDriver().findElement(By.xpath("//div[@class = 'card-body ']")).getText();
        Assert.assertTrue(textCardBodyAfterDelete.isEmpty());

        clickRecycleBin(getDriver());

        WebElement recycleBinPage = findElement(RECYCLE_INFO);
        String currentString = recycleBinPage.getText();
        boolean checkBin = !currentString.equals("");
        Assert.assertTrue(checkBin, "Showing 1 to 1 of 1 rows");
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testRestoreRecord(){

        getEntity(getDriver(), "Gantt");
        clickRecycleBin(getDriver());
        findElement(By.linkText("restore as draft")).click();

        Assert.assertEquals(findElement(By.className("card-body")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");

        getEntity(getDriver(), "Gantt");
        clickListButton();

        WebElement icon2 = findElement(CHECK_ICON);
        Assert.assertEquals(icon2.getAttribute("class"), "fa fa-pencil");

        List<WebElement> columnList = findElements(COLUMN_FIELD);
        Assert.assertEquals(columnList.size(), expectedValues2.size());
        for (int i = 0; i < expectedValues2.size(); i++) {
            Assert.assertEquals(columnList.get(i).getText(), expectedValues2.get(i).toString());
        }
    }

    @Test
    public void testCreateDraftRecord() {

        getEntity(getDriver(), "Gantt");
        fillForm();
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