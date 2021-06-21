import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.jsClick;

public class EntityDefaultOLDTest extends BaseTest {

    private static final By STRING_FIELD = By.id("string");
    private static final By TEXT_FIELD = By.id("text");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By DATE_FIELD = By.id("date");
    private static final By DATETIME_FIELD = By.id("datetime");
    private static final By CHECK_ICON = By.xpath("//tbody/tr[1]/td[1]/i[1]");
    private static final By ROWS_ELEMENT = By.xpath("//div[@class='fixed-table-body']//table[@id='pa-all-entities-table']/tbody/tr");
    private static final By ACTUAL_RESULT = By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']");

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

    private static final List<Object> EXPECTED_RESULT = Arrays
            .asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
                    formatter.format(date), EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_NAME);

    private static final List<Object> EXPECTED_RESULT2 = Arrays
            .asList(STRING_INPUT_VALUE2, TEXT_INPUT_VALUE2, INT_INPUT_VALUE2, DECIMAL_INPUT_VALUE2,
                    formatter.format(date), EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_NAME);

    private void clearFields() {
        findElement(STRING_FIELD).clear();
        findElement(TEXT_FIELD).clear();
        findElement(INT_FIELD).clear();
        findElement(DECIMAL_FIELD).clear();
        findElement(DATE_FIELD).clear();
        findElement(DATETIME_FIELD).clear();
    }

    private void fillForm() {
        findElement(STRING_FIELD).sendKeys(STRING_INPUT_VALUE);
        findElement(TEXT_FIELD).sendKeys(TEXT_INPUT_VALUE);
        findElement(INT_FIELD).sendKeys(INT_INPUT_VALUE);
        findElement(DECIMAL_FIELD).sendKeys(DECIMAL_INPUT_VALUE);
        findElement(By.id("date")).click();
        findElement(By.xpath("//div[contains(text(),'apptester1@tester.test')]")).click();

        jsClick(getDriver(), findElement(By.xpath("//span[text()='tester100@tester.test']")));
    }

    private List<Object> fillFieldsRecordAndReturnExpectedList(
            String stringInputValueSort, String textInputValueSort,
            String intInputValueSort, String decimalInputValueSort,
            String userNameSort) {

        findElement(STRING_FIELD).sendKeys(stringInputValueSort);
        findElement(TEXT_FIELD).sendKeys(textInputValueSort);
        findElement(INT_FIELD).sendKeys(intInputValueSort);
        findElement(DECIMAL_FIELD).sendKeys(decimalInputValueSort);
        findElement(DATE_FIELD).click();
        findElement(By.xpath("//div[@class = 'filter-option-inner']")).click();
        By userNameForSort = By.xpath("//span[text()='"+ userNameSort + "']");

        jsClick(getDriver(), findElement(userNameForSort));

        return EXPECTED_RESULT;
    }

    private void clickListButton(){
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(By.xpath("//a[@href=\"index.php?action=action_list&list_type=table&entity_id=7\"]"))))
                .click();
    }

    private void clickOrderButton(){
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(By.xpath("//a[@href=\"index.php?action=action_list&list_type=table&entity_id=7&draggable=1\"]"))))
                .click();
    }

    @Test
    public void testCreateRecord() {

        getEntity(getDriver(), "Default");
        clickCreateRecord(getDriver());
        clearFields();
        fillForm();
        clickSave(getDriver());

        WebElement icon1 = findElement(CHECK_ICON);
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-check-square-o");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testCancelDefaultRecord () {

        getEntity(getDriver(), "Default");
        clickCreateRecord(getDriver());

        Assert.assertFalse(findElement(STRING_FIELD).getAttribute("value").isEmpty());
        Assert.assertFalse(findElement(TEXT_FIELD).getText().isEmpty());
        Assert.assertFalse(findElement(INT_FIELD).getAttribute("value").isEmpty());
        Assert.assertFalse(findElement(DECIMAL_FIELD).getAttribute("value").isEmpty());
        Assert.assertFalse(findElement(DATE_FIELD).getAttribute("value").isEmpty());
        Assert.assertFalse(findElement(DATETIME_FIELD).getAttribute("value").isEmpty());

        clickCancel(getDriver());

        Assert.assertNull(findElement(By.className("card-body")).getAttribute("value"));
    }

    @Test(dependsOnMethods = "testCancelDefaultRecord")
    public void testViewRecord() {

        getEntity(getDriver(), "Default");

        List<WebElement> rows = getDriver().findElements(ROWS_ELEMENT);
        Assert.assertEquals(rows.size(), 1);

        clickActionsView(getWait(), getDriver());

        List<Object> expectedRecord = Arrays.asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE,
                DECIMAL_INPUT_VALUE, formatter.format(date), EMPTY_FIELD);

        Assert.assertEquals(getActualValues(findElements(By.xpath("//span [@class = 'pa-view-field']"))), expectedRecord);
        Assert.assertEquals(findElement(By.xpath("//div [@class = 'form-group']/p")).getText(), USER_NAME);
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testSwitchBetweenListAndOrder() {

        getEntity(getDriver(), "Default");

        clickOrderButton();
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);

        clickListButton();
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testSwitchBetweenListAndOrder")
    public void testEditRecord() {

        getEntity(getDriver(), "Default");
        clickActionsEdit(getWait(), getDriver());
        clearFields();

        findElement(STRING_FIELD).sendKeys(STRING_INPUT_VALUE2);
        findElement(TEXT_FIELD).sendKeys(TEXT_INPUT_VALUE2);
        findElement(INT_FIELD).sendKeys(INT_INPUT_VALUE2);
        findElement(DECIMAL_FIELD).sendKeys(DECIMAL_INPUT_VALUE2);

        clickSave(getDriver());

        WebElement icon1 = findElement(CHECK_ICON);
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-check-square-o");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT2);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {

        getEntity(getDriver(), "Default");
        clickActionsDelete(getWait(), getDriver());
        clickRecycleBin(getDriver());
        findElement(By.xpath("//tbody/tr/td/a")).click();

        List<Object> expectedRecord2 = Arrays.asList(STRING_INPUT_VALUE2, TEXT_INPUT_VALUE2, INT_INPUT_VALUE2,
                DECIMAL_INPUT_VALUE2, formatter.format(date), EMPTY_FIELD);

        Assert.assertEquals(getActualValues(findElements(By.cssSelector("span.pa-view-field"))), expectedRecord2);
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testRestoreRecord() {

        getEntity(getDriver(), "Default");
        clickRecycleBin(getDriver());
        findElement(By.linkText("restore as draft")).click();

        Assert.assertEquals(findElement(By.className("card-body")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");

        getEntity(getDriver(), "Default");

        WebElement icon2 = findElement(CHECK_ICON);
        Assert.assertEquals(icon2.getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT2);
    }

    @Test
    public void testCreateDraftRecord() {

        getEntity(getDriver(), "Default");
        clickCreateRecord(getDriver());
        clearFields();
        fillForm();
        clickSaveDraft(getDriver());

        WebElement icon1 = findElement(CHECK_ICON);
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
    }

    @Test
    public void testCreateDefaultRecord() {

        getEntity(getDriver(), "Default");
        clickCreateRecord(getDriver());
        clickSave(getDriver());

        List<WebElement> rows = getDriver().findElements(ROWS_ELEMENT);
        Assert.assertEquals(rows.size(), 1);

        WebElement icon1 = findElement(CHECK_ICON);
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-check-square-o");
    }

    @Test
    public void testCreateDefaultDraftRecord() {

        getEntity(getDriver(), "Default");
        clickCreateRecord(getDriver());
        clickSaveDraft(getDriver());

        List<WebElement> rows = getDriver().findElements(ROWS_ELEMENT);
        Assert.assertEquals(rows.size(), 1);

        WebElement icon1 = findElement(CHECK_ICON);
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-pencil");
    }

    @Test
    public void testSortRecords() {

        getEntity(getDriver(), "Default");
        clickCreateRecord(getDriver());
        clearFields();

        List<Object> expectedValues1 = fillFieldsRecordAndReturnExpectedList(
                "String",
                "Text",
                "2021",
                "0.10",
                "tester100@tester.test");
        clickSave(getDriver());

        clickCreateRecord(getDriver());
        clearFields();

        List<Object> expectedValues2 = fillFieldsRecordAndReturnExpectedList(
                "Pending",
                "Success",
                "2018",
                "0.25",
                "tester88@tester.test");
        clickSaveDraft(getDriver());

        clickCreateRecord(getDriver());
        clearFields();

        List<Object> expectedValues3 = fillFieldsRecordAndReturnExpectedList(
                "Yamal",
                "News",
                "2035",
                "0.12",
                "tester107@tester.test");
        clickSave(getDriver());

        WebElement icon1 = findElement(By.xpath("//tbody/tr[1]/td[1]/i[1]"));
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-check-square-o");
        WebElement icon2 = findElement(By.xpath("//tbody/tr[2]/td[1]/i[1]"));
        Assert.assertEquals(icon2.getAttribute("class"), "fa fa-pencil");
        WebElement icon3 = findElement(By.xpath("//tbody/tr[3]/td[1]/i[1]"));
        Assert.assertEquals(icon3.getAttribute("class"), "fa fa-check-square-o");

        findElement(By.xpath("//thead/tr/th/div[text()=\"String\"]")).click();
        icon1 = findElement(By.xpath("//tbody/tr[1]/td[1]/i[1]"));
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-pencil");

        findElement(By.xpath("//thead/tr/th/div[text()=\"Text\"]")).click();
        icon2 = findElement(By.xpath("//tbody/tr[2]/td[1]/i[1]"));
        Assert.assertEquals(icon2.getAttribute("class"), "fa fa-pencil");

        findElement(By.xpath("//thead/tr/th/div[text()=\"Int\"]")).click();
        icon1 = findElement(By.xpath("//tbody/tr[1]/td[1]/i[1]"));
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-pencil");

        findElement(By.xpath("//thead/tr/th/div[text()=\"Decimal\"]")).click();
        icon3 = findElement(By.xpath("//tbody/tr[3]/td[1]/i[1]"));
        Assert.assertEquals(icon3.getAttribute("class"), "fa fa-pencil");
    }
}
