import base.BaseTest;
import constants.EntityParentConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static utils.ProjectUtils.*;

public class EntityParentTest extends BaseTest {

    private static final String STRING_INPUT_VALUE = "Pending";
    private static final String TEXT_INPUT_VALUE = "qwerty";
    private static final String INT_INPUT_VALUE = "12345";
    private static final String DECIMAL_INPUT_VALUE = "0.10";
    private static final String EMPTY_FIELD = "";
    private static final String USER_NAME = "apptester10@tester.test";
    private static final String INFO_STR_1_OF_1 = "Showing 1 to 1 of 1 rows";
    private static final String INFO_STR_2_OF_2 = "Showing 1 to 2 of 2 rows";

    private static final List<String> EDIT_RESULT = List.of(
            "Hello for everyone", "Peace to all", "345", "345.67", "", "", "", "tester268@tester.test");

    private static final List<String> EXPECTED_RESULT = List.of(
            "Hello world", "Be healthy", "123", "456.98", "", "", "", "tester26@tester.test");

    private static final By ICON = By.xpath("//tbody/tr/td/i");
    private static final By FILL_STRING = By.id("string");
    private static final By FILL_TEXT = By.id("text");
    private static final By FILL_INT = By.id("int");
    private static final By FILL_DECIMAL = By.id("decimal");
    private static final By INFO_STRING = By.xpath("//span[@class='pagination-info']");
    private static final By INPUT = By.xpath("//input[@type='text']");
    private static final By ACTUAL_RESULT = By.xpath("//td[@class='pa-list-table-th']");

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    final List<Object> expectedValues = Arrays
            .asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
                    formatter.format(date), EMPTY_FIELD, EMPTY_FIELD, USER_NAME);

    private static List<WebElement> RECORD(WebDriver driver) {
        return List.of(driver.findElement(By.xpath("//div/span/a")));
    }

    private void fillForms() {

        getEntity(getDriver(),"Parent");
        clickCreateRecord(getDriver());

        findElement(FILL_STRING).sendKeys(EXPECTED_RESULT.get(0));
        findElement(FILL_TEXT).sendKeys(EXPECTED_RESULT.get(1));
        findElement(FILL_INT).sendKeys(EXPECTED_RESULT.get(2));
        findElement(FILL_DECIMAL).sendKeys(EXPECTED_RESULT.get(3));

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//div[@class='filter-option-inner-inner']")));
        TestUtils.jsClick(getDriver(), findElement(By.xpath("//span[text()='tester26@tester.test']")));

        clickSave(getDriver());
    }

    private void editForms() {

        findElement(FILL_STRING).clear();
        findElement(FILL_STRING).sendKeys(EDIT_RESULT.get(0));

        findElement(FILL_TEXT).clear();
        findElement(FILL_TEXT).sendKeys(EDIT_RESULT.get(1));

        findElement(FILL_INT).clear();
        findElement(FILL_INT).sendKeys(EDIT_RESULT.get(2));

        findElement(FILL_DECIMAL).clear();
        findElement(FILL_DECIMAL).sendKeys(EDIT_RESULT.get(3));

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//div[@class='filter-option-inner-inner']")));
        TestUtils.jsClick(getDriver(), findElement(By.xpath("//span[text()='tester268@tester.test']")));

        clickSave(getDriver());
    }

    private void clickListButton() {
        findElement(By.xpath(
                "//a[@href='index.php?action=action_list&list_type=table&entity_id=57']")).click();
    }

    private void fillForm() {

        findElement(EntityParentConstants.STRING_FIELD).sendKeys(STRING_INPUT_VALUE);
        findElement(EntityParentConstants.TEXT_FIELD).sendKeys(TEXT_INPUT_VALUE);
        findElement(EntityParentConstants.INT_FIELD).sendKeys(INT_INPUT_VALUE);
        findElement(EntityParentConstants.DECIMAL_FIELD).sendKeys(DECIMAL_INPUT_VALUE);
        findElement(EntityParentConstants.DATE_FIELD).click();
        findElement(EntityParentConstants.TESTER_NAME_FIELD).click();

        TestUtils.jsClick(getDriver(), findElement(EntityParentConstants.TESTER_NAME));
    }

    private WebElement getIcon(By locator) {
        return findElement(locator);
    }

    private void clickActionButton() {
        WebElement button_action = findElement(EntityParentConstants.PARENT_ACTION_BUTTON);
        button_action.click();
    }

    private void deleteAction() {
        WebElement view_action = findElement(EntityParentConstants.PARENT_ACTION_DELETE);
        view_action.click();
    }

    @Test
    public void testCreateRecord() {

        fillForms();

        WebElement icon = getDriver().findElement(ICON);
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-check-square-o");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {

        getEntity(getDriver(), "Parent");
        clickListButton();

        clickActionsView(getWait(), getDriver());

        List<WebElement> row = findElements(By.xpath("//span[@class='pa-view-field']"));
        for (int i = 0; i < row.size(); i++) {
            Assert.assertEquals(row.get(i).getText(), EXPECTED_RESULT.get(i));
        }
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testEditRecord() {

        getEntity(getDriver(), "Parent");

        clickActionsEdit(getWait(), getDriver());
        editForms();

        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EDIT_RESULT);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testSearchRecord() {

        fillForms();

        List<WebElement> fields = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(fields.size(), 2);

        findElement(INPUT).sendKeys(EXPECTED_RESULT.get(0));
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(
                INFO_STRING, INFO_STR_1_OF_1));

        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);

        findElement(INPUT).clear();
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(
                INFO_STRING, INFO_STR_2_OF_2));

        findElement(INPUT).sendKeys(EDIT_RESULT.get(0));
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(
                INFO_STRING, INFO_STR_1_OF_1));

        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EDIT_RESULT);
    }

    @Test(dependsOnMethods = "testSearchRecord")
    public void testReorderRecord() {

        getEntity(getDriver(), "Parent");

        findElement(By.xpath("//input[@type='text']")).clear();
        findElement(By.xpath("//i[text()='format_line_spacing']")).click();

        Assert.assertEquals(findElements(ACTUAL_RESULT).get(0).getText(), EDIT_RESULT.get(0));

        Actions actions = new Actions(getDriver());
        WebElement row = findElement(By.xpath("//tbody/tr"));
        actions.moveToElement(row).clickAndHold(row).dragAndDropBy(row, 0, 20);
        Action swapRow = actions.build();
        swapRow.perform();

        Assert.assertEquals(findElements(ACTUAL_RESULT).get(0).getText(), EXPECTED_RESULT.get(0));

        getDriver().findElement(By.xpath("//i[@class='fa fa-toggle-off']")).click();

        Assert.assertEquals(RECORD(getDriver()).get(0).getText(), EXPECTED_RESULT.get(0));

        WebElement card = getDriver().findElement(By.id("customId_0"));
        actions.moveToElement(card).clickAndHold(card).dragAndDropBy(card, 0, 100);
        Action swapCard = actions.build();
        swapCard.perform();

        Assert.assertEquals(RECORD(getDriver()).get(0).getText(), EDIT_RESULT.get(0));
    }

    @Test
    public void testCancelRecord() {

        getEntity(getDriver(), "Parent");

        clickCreateRecord(getDriver());
        fillForm();
        clickCancel(getDriver());

        Assert.assertEquals(findElement(EntityParentConstants.PARENT_GET_CONTANER).getText(), "");
    }

    @Test(dependsOnMethods = "testCancelRecord")
    public void testCreateNewDraftRecord() {

        getEntity(getDriver(), "Parent");

        clickCreateRecord(getDriver());
        fillForm();
        clickSaveDraft(getDriver());

        getWait().
                until(ExpectedConditions.presenceOfElementLocated(
                        EntityParentConstants.GET_PARENT_TITLE));
        List<WebElement> records = findElements(EntityParentConstants.PARENT_GET_LIST_ROW);

        Assert.assertEquals(records.size(), 1);
        Assert.assertEquals(getIcon(EntityParentConstants.PARENT_GET_ICON)
                .getAttribute("class"), EntityParentConstants.CLASS_ITEM_SAVE_DRAFT);
    }

    @Test(dependsOnMethods = "testCreateNewDraftRecord")
    public void testDeleteRecord() {

        getEntity(getDriver(), "Parent");

        clickCreateRecord(getDriver());
        fillForm();
        clickSave(getDriver());

        getWait().until(ExpectedConditions.textToBePresentInElementLocated(EntityParentConstants.PARENT_GET_TEXT_MESSAGE,
                EntityParentConstants.TEXT_MESSAGE_TWO));
        List<WebElement> records1 = findElements(EntityParentConstants.PARENT_GET_LIST_ROW);
        Assert.assertEquals(records1.size(), 2);

        clickActionButton();
        deleteAction();
        getWait().
                until(ExpectedConditions.presenceOfElementLocated(
                        EntityParentConstants.GET_PARENT_TITLE));
        List<WebElement> records2 = findElements(EntityParentConstants.PARENT_GET_LIST_ROW);
        Assert.assertEquals(records2.size(), 1);
        Assert.assertEquals(findElement(EntityParentConstants.PARENT_RECYCLING_BIN_ICON_NOTICE).getText(), "1");
    }
}
