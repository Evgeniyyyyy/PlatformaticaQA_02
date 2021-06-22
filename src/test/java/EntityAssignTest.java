import base.BaseTest;
import constants.EntityAssignConstants;
import model.AssignPage;
import model.MainPage;
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

    private static final List<String> EXPECTED_RESULT = List.of(STRING, TEXT, INT, DECIMAL, "", "");

    private static final String ICON = "fa fa-check-square-o";
    private static final By ACTUAL_RESULT = By.xpath("//tbody/tr/td/a");


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

        Assert.assertEquals(assignPage.getClassIcon(), ICON);
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

    @Test(dependsOnMethods = "testSaveDraftRecord")
    public void testReorderRecords() {

        moveToAssignEntity();

        toggleAction(EntityAssignConstants.ASSIGN_TOGGLE_ORDER_ACTION);
        dragAndDropAction(getDriver());

        Assert.assertEquals(getIcon(EntityAssignConstants.ASSIGN_GET_ICON)
                .getAttribute("class"), EntityAssignConstants.CLASS_ITEM_SAVE_DRAFT);

        toggleAction(EntityAssignConstants.ASSIGN_TOGGLE_LIST_ACTION);

        Assert.assertEquals(getIcon(EntityAssignConstants.ASSIGN_GET_ICON)
                .getAttribute("class"), EntityAssignConstants.CLASS_ITEM_SAVE_DRAFT);
    }

    @Test
    public void testSearchRecord() {
        Map<By, String> locators1 = new HashMap<>();
        locators1.put(stringField, "Notes");
        locators1.put(decimalField, "0.10");
        locators1.put(intField, "3");

        Map<By, String> locators2 = new HashMap<>();
        locators2.put(textField, "Alphabet");
        locators2.put(decimalField, "0.25");
        locators2.put(intField, "9");

        moveToAssignEntity();

        addNewCard(getDriver(), locators1, EntityAssignConstants.ASSIGN_BUTTON_SAVE);
        addNewCard(getDriver(), locators2, EntityAssignConstants.ASSIGN_BUTTON_SAVE_DRAFT);

        List<WebElement> records = findElements(EntityAssignConstants.ASSIGN_GET_LIST_ROW);
        Assert.assertEquals(records.size(), 2);

        findSearchField("alp");
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(EntityAssignConstants.ASSIGN_GET_TEXT_MESSAGE,
                EntityAssignConstants.TEXT_MESSAGE_ONE));
        List<WebElement> records1 = findElements(EntityAssignConstants.ASSIGN_GET_LIST_ROW);
        Assert.assertEquals(records1.size(), 1);
        Assert.assertEquals(findElement(By.xpath("//tbody/tr/td[3]/a")).getText(),"Alphabet");

        clearSearchField();
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(EntityAssignConstants.ASSIGN_GET_TEXT_MESSAGE,
                EntityAssignConstants.TEXT_MESSAGE_TWO));
        findSearchField("note");
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(EntityAssignConstants.ASSIGN_GET_TEXT_MESSAGE,
                EntityAssignConstants.TEXT_MESSAGE_ONE));
        List<WebElement> records2 = findElements(EntityAssignConstants.ASSIGN_GET_LIST_ROW);
        Assert.assertEquals(records2.size(), 1);
        Assert.assertEquals(findElement(By.xpath("//tbody/tr/td[2]/a")).getText(),"Notes");
    }
}
