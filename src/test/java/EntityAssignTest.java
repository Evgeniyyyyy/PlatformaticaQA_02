import base.BaseTest;
import constants.EntityAssignConstants;
import model.AssignPage;
import model.MainPage;
import model.ParentPage;
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
import java.util.*;

public class EntityAssignTest extends BaseTest {

    private static final By stringField = EntityAssignConstants.ASSIGN_STRING_FIELD;
    private static final By textField = EntityAssignConstants.ASSIGN_TEXT_FIELD;
    private static final By decimalField = EntityAssignConstants.ASSIGN_DECIMAL_FIELD;
    private static final By intField = EntityAssignConstants.ASSIGN_INT_FIELD;
    private static final By dateField = EntityAssignConstants.ASSIGN_DATE_FIELD;
    private static final By datetimeField = EntityAssignConstants.ASSIGN_DATETIME_FIELD;
    private static final String STRING = "Entity assign";
    private static final String TEXT = "Create Record";
    private static final String INT = "666";
    private static final String DECIMAL = "666.66";
    private static final String DATE = "02/06/2021";
    private static final String DATETIME = "02/06/2021 22:00:28";
    private static final String EMPTY_FIELD = "";
    private static final String USER_DEFAULT_NAME = "apptester1@tester.test";

    private static final List<String> EXPECTED_RESULT = List.of(STRING, TEXT, INT, DECIMAL, "", "");

    private static final String CLASS_ICON_SAVE = "fa fa-check-square-o";
    private static final String CLASS_ICON_SAVE_DRAFT = "fa fa-pencil";
    private static final String INFO_STR_1_OF_1 = "Showing 1 to 1 of 1 rows";
    private static final String INFO_STR_2_OF_2 = "Showing 1 to 2 of 2 rows";
    private static final String INFO_STR_3_OF_3 = "Showing 1 to 3 of 3 rows";
    private static final By ACTUAL_RESULT = By.xpath("//tbody/tr/td/a");

    private static final String STRING_VALUE_1 = "String";
    private static final String TEXT_VALUE_1 = "Text";
    private static final String INT_VALUE_1 = "2021";
    private static final String DECIMAL_VALUE_1 = "0.10";

    private static final String STRING_VALUE_2 = "Pending";
    private static final String TEXT_VALUE_2 = "Success";
    private static final String INT_VALUE_2 = "2018";
    private static final String DECIMAL_VALUE_2 = "0.25";

    private static final String STRING_VALUE_3 = "Yamal";
    private static final String TEXT_VALUE_3 = "News";
    private static final String INT_VALUE_3 = "2035";
    private static final String DECIMAL_VALUE_3 = "0.12";

    private static final List<String> EXPECTED_RESULT_1 = List.of(
            STRING_VALUE_1, TEXT_VALUE_1, INT_VALUE_1, DECIMAL_VALUE_1,
            EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> EXPECTED_RESULT_2 = List.of(
            STRING_VALUE_2, TEXT_VALUE_2, INT_VALUE_2, DECIMAL_VALUE_2,
            EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> EXPECTED_RESULT_3 = List.of(
            STRING_VALUE_3, TEXT_VALUE_3, INT_VALUE_3, DECIMAL_VALUE_3,
            EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> EXPECTED_ORDER_RESULT_1 = List.of(
            EMPTY_FIELD, STRING_VALUE_1, TEXT_VALUE_1, INT_VALUE_1, DECIMAL_VALUE_1,
            EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> EXPECTED_ORDER_RESULT_2 = List.of(
            EMPTY_FIELD, STRING_VALUE_2, TEXT_VALUE_2, INT_VALUE_2, DECIMAL_VALUE_2,
            EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private void dragAndDropAction(WebDriver driver) {
        Actions builder = new Actions(driver);
        WebElement searchField = findElement(EntityAssignConstants.ASSIGN_ROW_SAVE_RECORD);
        builder.moveToElement(searchField)
                .clickAndHold(searchField)
                .dragAndDropBy(searchField, 0, 20);
        Action swapRow = builder.build();
        swapRow.perform();
    }

    private void addEmptyCard(WebDriver driver, By locator) {
        clickCreateRecord(getDriver());
        WebElement buttonSave = findElement(locator);
        TestUtils.scroll(driver, buttonSave);
        buttonSave.click();
    }

    private void addNewCard(WebDriver driver, Map<By, String> locators, By button) {
        clickCreateRecord(getDriver());

        locators.forEach((key, value) -> {
            findElement(key).sendKeys(value);
        });

        WebElement buttonSave = findElement(button);
        TestUtils.scroll(driver, buttonSave);
        buttonSave.click();
    }

    private void toggleAction (By locator) {
        WebElement toggleActionToList = findElement(locator);
        toggleActionToList.click();
    }

    private WebElement getIcon(By locator) {
        return findElement(locator);
    }

    private void findSearchField(String value) {
        findElement(EntityAssignConstants.ASSIGN_GET_SEARCH_FIELD).sendKeys(value);
    }

    private void clearSearchField() {
        findElement(EntityAssignConstants.ASSIGN_GET_SEARCH_FIELD).clear();
    }

    private void createNewRecord(String stringRecord, String textRecord, String integerRecord, String decimalRecord) {

        clickCreateRecord(getDriver());
        findElement(stringField).sendKeys(stringRecord);
        findElement(textField).sendKeys(textRecord);
        findElement(intField).sendKeys(integerRecord);
        findElement(decimalField).sendKeys(decimalRecord);

        clickSave(getDriver());
    }

    private void switchToViewMode() {

        final By listButton = By.xpath("(//*[@class='nav-item'])[3]");
        final By dropDownMenuButton = By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']");
        final By dropDownView = By.xpath("//a[contains(text(), 'view')]");

        findElement(listButton).click();
        findElement(dropDownMenuButton).click();
        getWait().until(ExpectedConditions.elementToBeClickable(dropDownView)).click();
    }

    private void moveToAssignEntity() {
        TestUtils.jsClick(getDriver(), findElement(EntityAssignConstants.LINK_ASSIGN_ENTITY));
    }

    private void clickEditButton() {
        WebElement dropdown = getDriver().findElement(By.cssSelector("button.btn-round.dropdown-toggle"));
        TestUtils.scrollClick(getDriver(), dropdown);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath("//a[text()='edit']")));
    }

    private void fillForm() {

        getEntity(getDriver(),"Assign");
        clickCreateRecord(getDriver());

        findElement(By.id("string")).sendKeys(STRING);
        findElement(By.id("text")).sendKeys(TEXT);
        findElement(By.id("int")).sendKeys(INT);
        findElement(By.id("decimal")).sendKeys(DECIMAL);
    }

    @Test
    public void testCreateRecord() {

        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickNewButton()
                .fillTitle(STRING)
                .fillComments(TEXT)
                .fillInt(INT)
                .fillDecimal(DECIMAL)
                .clickSave();

        Assert.assertEquals(assignPage.getClassIcon(), CLASS_ICON_SAVE);
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
        Assert.assertEquals(assignPage.getRowCount(), 1);
    }

    @Test
    public void testCancelRecord() {

        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickNewButton()
                .fillTitle(STRING)
                .fillComments(TEXT)
                .fillInt(INT)
                .fillDate(DATE)
                .fillDateTime(DATETIME)
                .clickCancel();

        Assert.assertEquals(findElement(EntityAssignConstants.ASSIGN_GET_CONTANER).getText(), "");
    }

    @Test(dependsOnMethods = "testCancelRecord")
    public void testViewMode() {
        final String stringRecord = "Just a string";
        final String textRecord = "Just a text";
        final String integerRecord = "61";
        final String decimalRecord = "0.61";
        final List<String> expectedRowsContent = List.of(stringRecord, textRecord, integerRecord, decimalRecord);

        moveToAssignEntity();
        createNewRecord(stringRecord, textRecord, integerRecord, decimalRecord);
        switchToViewMode();

        List<WebElement> recordRowsContent = findElements(By.xpath("//span[@class='pa-view-field' and text()]"));

        Assert.assertEquals(recordRowsContent.size(), expectedRowsContent.size());
        for (int i = 0; i < expectedRowsContent.size(); i++) {
            Assert.assertEquals(recordRowsContent.get(i).getText(), expectedRowsContent.get(i));
        }
    }

    @Test(dependsOnMethods = "testViewMode")
    public void testEditRecord() {

        final String stringEditedInputData = "Edited record 10";
        final String textEditedInputData = "Edited text of record 10";
        final String intEditedInputData = "11";
        final String decimalEditedInputData = "11.10";

        moveToAssignEntity();

        clickEditButton();

        WebElement stringField = findElement(EntityAssignConstants.ASSIGN_STRING_FIELD);
        stringField.clear();
        stringField.sendKeys(stringEditedInputData);

        WebElement textField = findElement(EntityAssignConstants.ASSIGN_TEXT_FIELD);
        textField.clear();
        textField.sendKeys(textEditedInputData);

        WebElement intField = findElement(EntityAssignConstants.ASSIGN_INT_FIELD);
        intField.clear();
        intField.sendKeys(intEditedInputData);

        WebElement decimalField = findElement(EntityAssignConstants.ASSIGN_DECIMAL_FIELD);
        decimalField.clear();
        decimalField.sendKeys(decimalEditedInputData);

        TestUtils.jsClick(getDriver(), findElement(By.id("pa-entity-form-save-btn")));

        String stringValue = findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
        Assert.assertEquals(stringValue, stringEditedInputData);
        String textValue = findElement(By.xpath("//tbody/tr[1]/td[3]")).getText();
        Assert.assertEquals(textValue, textEditedInputData);
        String intValue = findElement(By.xpath("//tbody/tr[1]/td[4]")).getText();
        Assert.assertEquals(intValue, intEditedInputData);
        String decimalValue = findElement(By.xpath("//tbody/tr[1]/td[5]")).getText();
        Assert.assertEquals(decimalValue, decimalEditedInputData);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testSaveDraftRecord() {

        final String stringInput = "Hello";
        final String textInput = "everyone";
        final String intInput = "555";
        final String decimalInput = "55.55";
        final String dataInput = "03/06/2021";
        final String dataTimeInput = "12/06/2021 09:09:09";
        final String empty = "";
        final String userDefault = "apptester1@tester.test";

        final List<String> expectedValues = Arrays.asList(
                stringInput, textInput, intInput, decimalInput, dataInput, dataTimeInput, empty, userDefault);

        moveToAssignEntity();

        clickCreateRecord(getDriver());
        findElement(stringField).sendKeys(stringInput);
        findElement(textField).sendKeys(textInput);
        findElement(intField).sendKeys(intInput);
        findElement(decimalField).sendKeys(decimalInput);

        findElement(dateField).click();
        findElement(dateField).clear();
        findElement(dateField).sendKeys(dataInput);

        findElement(datetimeField).click();
        findElement(datetimeField).clear();
        findElement(datetimeField).sendKeys(dataTimeInput);

        clickSaveDraft(getDriver());

        List<WebElement> cells = findElements(By.xpath("//tbody/tr[@data-index='1']/td[@class= 'pa-list-table-th']"));
        List<String> actualValues = new ArrayList<>();

        for (WebElement cell : cells) {
            actualValues.add(cell.getText());
        }

        Assert.assertEquals(cells.size(), expectedValues.size());
        Assert.assertEquals(actualValues, expectedValues);
        Assert.assertEquals(getDriver().findElement(
                By.xpath("//tbody/tr[2]/td[1]/i")).getAttribute("class"), "fa fa-pencil");
    }

    @Test
    public void testReorderRecords() {

        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickNewButton()
                .fillFields(STRING_VALUE_1, TEXT_VALUE_1, INT_VALUE_1, DECIMAL_VALUE_1)
                .clickSave();
        Assert.assertEquals(AssignPage.getRow(0), EXPECTED_RESULT_1);

        assignPage.clickNewButton()
                .fillFields(STRING_VALUE_2, TEXT_VALUE_2, INT_VALUE_2, DECIMAL_VALUE_2)
                .clickSaveDraft();
        Assert.assertEquals(AssignPage.getRow(1), EXPECTED_RESULT_2);

        assignPage.clickOrderButton().getReorder();
        Assert.assertEquals(AssignPage.getRow(0), EXPECTED_RESULT_2);
        Assert.assertEquals(AssignPage.getRow(1), EXPECTED_RESULT_1);

        assignPage.clickToggle()
                .getNewReorder();
        Assert.assertEquals(AssignPage.getRows(0), EXPECTED_ORDER_RESULT_1);
        Assert.assertEquals(AssignPage.getRows(1), EXPECTED_ORDER_RESULT_2);
    }

    @Test(dependsOnMethods = "testReorderRecords")
    public void testSearchRecord() {
        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickNewButton()
                .fillFields(STRING_VALUE_3, TEXT_VALUE_3, INT_VALUE_3, DECIMAL_VALUE_3)
                .clickSave()
                .searchInput(STRING_VALUE_3)
                .getTextPaginationInfo(INFO_STR_1_OF_1);
        Assert.assertEquals(ParentPage.getRow(0), EXPECTED_RESULT_3);

        assignPage.searchInput("").getTextPaginationInfo(INFO_STR_3_OF_3);
        Assert.assertEquals(ParentPage.getRowCount(), 3);

        assignPage.searchInput(TEXT_VALUE_2).getTextPaginationInfo(INFO_STR_1_OF_1);
        Assert.assertEquals(ParentPage.getRow(0), EXPECTED_RESULT_2);

        assignPage.searchInput("").getTextPaginationInfo(INFO_STR_3_OF_3);
        assignPage.searchInput(INT_VALUE_1).getTextPaginationInfo(INFO_STR_1_OF_1);
        Assert.assertEquals(ParentPage.getRow(0), EXPECTED_RESULT_1);
    }
}
