import base.BaseTest;
import constants.EntityParentConstants;
import model.FieldsPage;
import model.MainPage;
import model.ParentPage;
import org.apache.commons.lang3.RandomStringUtils;
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
import java.util.*;

import static utils.ProjectUtils.*;

public class EntityParentTest extends BaseTest {

    private static final String STRING_INPUT_VALUE = "Pending";
    private static final String TEXT_INPUT_VALUE = "qwerty";
    private static final String INT_INPUT_VALUE = "12345";
    private static final String DECIMAL_INPUT_VALUE = "0.10";
    private static final String EMPTY_FIELD = "";
    private static final String USER_DEFAULT_NAME = "apptester1@tester.test";
    private static final String USER_NAME = "apptester10@tester.test";
    private static final String INFO_STR_1_OF_1 = "Showing 1 to 1 of 1 rows";
    private static final String INFO_STR_2_OF_2 = "Showing 1 to 2 of 2 rows";
    private static final String CLASS_ICON_SAVE = "fa fa-check-square-o";

    private static final By ICON = By.xpath("//tbody/tr/td/i");
    private static final By FIELD_STRING = By.id("string");
    private static final By FIELD_TEXT = By.id("text");
    private static final By FIELD_INT = By.id("int");
    private static final By FIELD_DECIMAL = By.id("decimal");
    private static final By FIELD_DATE = By.id("date");
    private static final By FIELD_DATE_TIME = By.id("datetime");
    private static final By INFO_STRING = By.xpath("//span[@class='pagination-info']");
    private static final By INPUT = By.xpath("//input[@type='text']");
    private static final By ACTUAL_RESULT = By.xpath("//td[@class='pa-list-table-th']");

    private static final List<By> ELEMENTS = List.of(
            FIELD_STRING, FIELD_TEXT, FIELD_INT, FIELD_DECIMAL, FIELD_DATE, FIELD_DATE_TIME);

    Random random = new Random();
    private static final Date DATE = new Date();
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

    final List<Object> expectedValues = Arrays
            .asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
                    FORMATTER.format(DATE), EMPTY_FIELD, EMPTY_FIELD, USER_NAME);

    private static final List<String> NEW_EXPECTED_RESULT = List.of(
            STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
            EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static List<WebElement> RECORD(WebDriver driver) {
        return List.of(driver.findElement(By.xpath("//div/span/a")));
    }

    List <String> EXPECTED_RESULT = List.of(
            RandomStringUtils.randomAlphabetic(3),
            RandomStringUtils.randomAlphabetic(30),
            String.valueOf(random.nextInt(10000)),
            String.format("%.2f", random.nextFloat()),
            new SimpleDateFormat ("dd/MM/yyyy").format(DATE),
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(DATE),
            "", "tester26@tester.test");

    List <String> EDIT_RESULT = List.of(
            RandomStringUtils.randomAlphabetic(20),
            RandomStringUtils.randomAlphabetic(15),
            String.valueOf(random.nextInt(10000)),
            String.format("%.2f", random.nextFloat()),
            "15/11/0078", "17/06/0902 12:31:44",
            "", "tester27@tester.test");

    private void fillForms() {

        getEntity(getDriver(),"Parent");
        clickCreateRecord(getDriver());

        for (int i = 0; i < ELEMENTS.size(); i++) {
            findElement(ELEMENTS.get(i)).click();
            findElement(ELEMENTS.get(i)).clear();
            findElement(ELEMENTS.get(i)).sendKeys(EXPECTED_RESULT.get(i));
        }

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//div[@class='filter-option-inner-inner']")));
        TestUtils.jsClick(getDriver(), findElement(By.xpath("//span[text()='tester26@tester.test']")));
        clickSave(getDriver());
    }

    private void editForms() {

        for (int i = 0; i < ELEMENTS.size(); i++) {
            findElement(ELEMENTS.get(i)).click();
            findElement(ELEMENTS.get(i)).clear();
            findElement(ELEMENTS.get(i)).sendKeys(EDIT_RESULT.get(i));
        }

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//div[@class='filter-option-inner-inner']")));
        TestUtils.jsClick(getDriver(), findElement(By.xpath("//span[text()='tester27@tester.test']")));

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

        ParentPage parentPage = new MainPage(getDriver())
                .clickParentMenu()
                .clickNewButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .clickSave();

        Assert.assertEquals(ParentPage.getRowCount(), 1);
        Assert.assertEquals(ParentPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(ParentPage.getClassIcon(), CLASS_ICON_SAVE);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {

        getEntity(getDriver(), "Parent");
        clickListButton();

        clickActionsView(getWait(), getDriver());

        List<WebElement> row = findElements(By.xpath("//span[@class='pa-view-field']"));
        for (int i = 0; i < row.size(); i++) {
            Assert.assertEquals(row.get(i).getText(), NEW_EXPECTED_RESULT.get(i));
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
