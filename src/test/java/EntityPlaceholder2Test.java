import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.TestUtils.movingIsFinished;
import static utils.TestUtils.scrollClick;

public class EntityPlaceholder2Test extends BaseTest {

    private static final By ENTITY_PLACEHOLDER = By.xpath("//p[contains (text(), 'Placeholder')]");
    private static final By CREATE_NEW_RECORD = By.xpath("//i[text() = 'create_new_folder']");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By ACTIONS_BUTTON = By.xpath("//i[text() = 'menu']");
    private static final By STRING_FIELD = By.id("string");
    private static final By TEXT_FIELD = By.id("text");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By DATE_FIELD = By.id("date");
    private static final By DATETIME_FIELD = By.id("datetime");
    private static final By COLUMN_FIELD = By.xpath("//td[@class='pa-list-table-th']");
    private static final By EDIT_OPTION = By.xpath("//a[contains (text(), 'edit')]");
    private static final By DELETE_OPTION = By.xpath("//a[contains (text(), 'delete')]");
    private static final By RECYCLE_BIN = By.xpath("//i[contains (text(), 'delete_outline')]");
    private static final By DELETE_PERMANENTLY = By.xpath("//a[contains (text(), 'delete permanently')]");
    private static final By CHECK_ICON = By.xpath("//tbody/tr[1]/td[1]/i[1]");
    private static final By RECYCLE_INFO = By.xpath("//div[@class='card-body']");

    private void clickEventsPlaceholderMenu() {
        TestUtils.scrollClick(getDriver(), getDriver().findElement(ENTITY_PLACEHOLDER));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(CREATE_NEW_RECORD)));
    }

    private void clickCreateRecordButton() {
        getDriver().findElement(CREATE_NEW_RECORD).click();
    }

    private void clickSaveButton() {
        scrollClick(getDriver(), getDriver().findElement(SAVE_BUTTON));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(CREATE_NEW_RECORD)));
    }

    private void clickActionMenu() {
        getDriver().findElement(ACTIONS_BUTTON).click();
    }

    private void clickEditMenu() {
        clickActionMenu();
        getWait().until(movingIsFinished(EDIT_OPTION)).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(STRING_FIELD));
    }

    private List<WebElement> getCells() {
        return getDriver().findElements(COLUMN_FIELD);
    }

    private List<String> getRowValues() {
        List<String> actualValues = new ArrayList<>();

        for(WebElement cell : getCells()) {
            actualValues.add(cell.getText());
        }

        return actualValues;
    }

    private final String getAttributeClass() {
        return getDriver().findElement(CHECK_ICON).getAttribute("class");
    }

    @Test
    public void testCreateRecord() {

        final List<String> expected = List.of
                ("String field input", "Text field input", "1000", "20.55", "", "", "", "", "apptester1@tester.test");

        clickEventsPlaceholderMenu();
        clickCreateRecordButton();
        findElement(STRING_FIELD).sendKeys("String field input");
        findElement(TEXT_FIELD).sendKeys("Text field input");
        getDriver().findElement(INT_FIELD).sendKeys("1000");
        getDriver().findElement(DECIMAL_FIELD).sendKeys("20.55");
        getDriver().findElement(DATE_FIELD).clear();
        getDriver().findElement(DATETIME_FIELD).clear();
        clickSaveButton();

        Assert.assertEquals(getRowValues(), expected);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testEditRecord() {

        final List<String> expected = List.of
                ("Record#1", "Some text here...", "1111", "155.11", "", "", "", "", "apptester1@tester.test");

        clickEventsPlaceholderMenu();
        clickEditMenu();
        WebElement stringField = getWait().until(ExpectedConditions.elementToBeClickable(STRING_FIELD));
        stringField.click();
        stringField.clear();
        getDriver().findElement(STRING_FIELD).sendKeys("Record#1");
        WebElement textField = getDriver().findElement(TEXT_FIELD);
        textField.clear();
        textField.sendKeys("Some text here...");
        WebElement intField = getDriver().findElement(INT_FIELD);
        intField.clear();
        intField.sendKeys("1111");
        WebElement decimalField = getDriver().findElement(DECIMAL_FIELD);
        decimalField.clear();
        decimalField.sendKeys("155.11");
        clickSaveButton();

        List<WebElement> actual = getDriver().findElements(COLUMN_FIELD);
        for (int i = 0; i < actual.size(); i++) {
            Assert.assertEquals(actual.get(i).getText(), expected.get(i));
        }
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {

        List<String> expected = List.of
                ("Record#1", "Some text here...", "1111", "155.11", "", "", "", "", "apptester1@tester.test");

        clickEventsPlaceholderMenu();
        clickActionMenu();
        getWait().until(TestUtils.movingIsFinished(DELETE_OPTION)).click();

        ProjectUtils.clickRecycleBin(getDriver());
        getDriver().findElement(By.xpath("//tbody//span")).click();

        List<WebElement> actual = getDriver().findElements(By.xpath("//div/span"));
        for (int i = 0; i < actual.size(); i++) {
            Assert.assertEquals(actual.get(i).getText(), expected.get(i));
        }
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testDeleteRecordPermanently () {

        clickEventsPlaceholderMenu();
        getDriver().findElement(RECYCLE_BIN).click();
        getDriver().findElement(DELETE_PERMANENTLY).click();

        Assert.assertEquals(getDriver().findElement(RECYCLE_INFO)
                .getText(), "Good job with housekeeping! Recycle bin is currently empty!");
    }
}
