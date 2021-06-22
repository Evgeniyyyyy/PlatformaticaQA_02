import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static utils.TestUtils.*;

public class EntityEventsChain1Test extends BaseTest {

    private static final By CREATE_NEW_FOLDER = By.xpath("//i[text() = 'create_new_folder']");
    private static final By EVENTS_CHAIN_1 = By.xpath("//p[contains(.,'Events Chain 1')]");
    private static final By F1 = By.id("f1");
    private static final By F10 = By.id("f10");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By SAVE_DRAFT_BUTTON = By.id("pa-entity-form-draft-btn");
    private static final By DROP_DOWN_MENU = By.xpath("//i[contains(., 'menu')]");
    private static final By EDIT_MENU = By.xpath("//a[contains(., 'edit')]");
    private static final By DELETE_MENU = By.xpath("//a[contains (text(), 'delete')]");
    private static final By CANCEL = By.xpath("//button[text()='Cancel']");
    private static final By CARD_BODY = By.xpath("//div[@class = 'card-body ']");
    private static final By TABLE = By.id("pa-all-entities-table");
    private static final By CELLS = By.xpath("//table[@id = 'pa-all-entities-table']/tbody/tr/td/a");
    private static final By ICON = By.xpath("//tbody/tr/td[1]/i");
    private static final By RECYCLE_BIN = By.xpath("//a[@href='index.php?action=recycle_bin']");

    private static final String NEW_F1_VALUE = "2";
    private static final String EXPECTED_TEXT_RECYCLE_BIN_AFTER_DELETE = "delete_outline\n" + "1";

    private void clickEventsChain1Menu() {
        scrollClick(getDriver(), findElement(EVENTS_CHAIN_1));
        getWait().until(ExpectedConditions.elementToBeClickable(findElement(CREATE_NEW_FOLDER)));
    }

    private void clickCreateNewFolderButton() {
        findElement(CREATE_NEW_FOLDER).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(F1));
    }

    private void clickSaveButton() {
        findElement(SAVE_BUTTON).click();
        getWait().until(ExpectedConditions.elementToBeClickable(findElement(CREATE_NEW_FOLDER)));
    }

    private void clickSaveDraftButton() {
        findElement(SAVE_DRAFT_BUTTON).click();
        getWait().until(ExpectedConditions.elementToBeClickable(findElement(CREATE_NEW_FOLDER)));
    }

    private void clickCancelButton() {
        findElement(CANCEL).click();
    }

    private void clickDropDownMenu() {
        findElement(DROP_DOWN_MENU).click();
    }

    private void clickEditMenu() {
        clickDropDownMenu();
        getWait().until(movingIsFinished(EDIT_MENU)).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(F1));
    }

    private void clickDeleteMenu() {
        clickDropDownMenu();
        getWait().until(movingIsFinished(DELETE_MENU)).click();
        getWait().until(
                ExpectedConditions.presenceOfElementLocated(CARD_BODY));
    }

    private void inputF1Value(String value) {
        findElement(F1).sendKeys(value);
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(getDriver().findElement(F10), "value"));
    }

    private void editRecord(String value){
        final WebElement f1 = getDriver().findElement(F1);
        final WebElement f10 = getDriver().findElement(F10);

        f10.clear();
        f1.click();
        f1.clear();
        f1.sendKeys(value);
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(f10, "value"));
    }

    private List<WebElement> getCells() {
        return findElements(CELLS);
    }

    private List<String> getRowValues() {
        List<String> actualValues = new ArrayList<>();

        for(WebElement cell : getCells()) {
            actualValues.add(cell.getText());
        }

        return actualValues;
    }

    private String getAttributeClass() {
        return findElement(ICON).getAttribute("class");
    }

    private String getCardBodyText() {

        return findElement(CARD_BODY).getText();
    }

    private String getRecycleBinText() {

        return findElement(RECYCLE_BIN).getText();
    }

    private boolean isNumeric(String value){
        try {
            Long.parseLong(value);

            return true;
        } catch(NumberFormatException e){

            return false;
        }
    }

    private <T> T returnNumericValue(String value) {

        if (value != null && isNumeric(value)) {
            long stringParsed = Long.parseLong(value);

            if (stringParsed >= 0) {
                if (stringParsed <= 4194303) {

                    return (T) Integer.valueOf(value);

                } else {

                    return (T) Long.valueOf(stringParsed);
                }
            } else {

                return null;
            }
        }

        return null;
    }

    private List<String> addValues(int value) {
        List<String> expectedValues = new ArrayList<>();

        expectedValues.add(0, String.valueOf(value));

        for (int i = 1; i < 10; i ++) {
            value *= 2;
            expectedValues.add(i, String.valueOf(value));
        }
        return expectedValues;
    }

    private List<String> addValues(long value) {
        List<String> expectedValues = new ArrayList<>();

        expectedValues.add(0, String.valueOf(value));

        for (int i = 1; i < 10; i ++) {
            value *= 2;
            expectedValues.add(i, String.valueOf(value));
        }
        return expectedValues;
    }

    private List<String> getExpectedValues(String value) {

        if (returnNumericValue(value) instanceof Integer) {
            int valueInt = Integer.parseInt(value);

            return addValues(valueInt);

        } else if(returnNumericValue(value) instanceof Long) {
            long valueLong = Long.parseLong(value);

            return addValues(valueLong);
        }

        return null;
    }

    @Test
    public void testCreateNewRecord() {
        final String f1Value = "1";

        clickEventsChain1Menu();
        clickCreateNewFolderButton();
        inputF1Value(f1Value);
        clickSaveButton();

        Assert.assertEquals(getRowValues(), getExpectedValues(f1Value));
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testCreateNewRecord")
    public void testEditRecord() {

        clickEventsChain1Menu();

        final List<String> oldValues = getRowValues();

        clickEditMenu();
        editRecord(NEW_F1_VALUE);
        clickSaveButton();

        Assert.assertEquals(getRowValues(), getExpectedValues(NEW_F1_VALUE));
        Assert.assertNotEquals(getRowValues(), oldValues);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {

        clickEventsChain1Menu();

        final String textCardBodyBeforeDelete = getCardBodyText();
        final String textRecycleBinBeforeDelete = getRecycleBinText();

        clickDeleteMenu();

        final String textCardBodyAfterDelete = getCardBodyText();
        final String textRecycleBinAfterDelete = getRecycleBinText();

        Assert.assertFalse(textCardBodyBeforeDelete.isBlank());
        Assert.assertTrue(textCardBodyAfterDelete.isBlank());
        Assert.assertNotEquals(textRecycleBinBeforeDelete, textRecycleBinAfterDelete);
        Assert.assertEquals(textRecycleBinAfterDelete, EXPECTED_TEXT_RECYCLE_BIN_AFTER_DELETE);
    }

    @Test
    public void createChain1DraftRecord(){
        clickEventsChain1Menu();
        clickCreateNewFolderButton();
        inputF1Value("1");
        clickSaveDraftButton();

        List<String> cells = getRowValues();
        Assert.assertEquals(cells.size(), 10);
        for (int i = 0; i < 9; i++) {
            int cellI = Integer.valueOf(cells.get(i));
            int cellINext = Integer.valueOf(cells.get(i + 1));
            Assert.assertEquals(
                    cellI * 2,
                    cellINext,
                    "Expected cell[" + i + "] * 2 == cell[" + (i + 1) + "]"
            );
        }
    }

    @Test
    public void setEventsChain1RecordCancel(){
        clickEventsChain1Menu();
        clickCreateNewFolderButton();
        inputF1Value("1");
        clickCancelButton();

        Assert.assertTrue(getDriver().findElements(TABLE)
                .isEmpty());
    }
}
