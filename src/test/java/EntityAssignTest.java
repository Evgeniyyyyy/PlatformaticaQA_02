import base.BaseTest;
import constants.EntityAssignConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.ProjectUtils.*;

import utils.ProjectUtils;
import utils.TestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityAssignTest extends BaseTest {

    final By stringField = EntityAssignConstants.ASSIGN_STRING_FIELD;
    final By textField = EntityAssignConstants.ASSIGN_TEXT_FIELD;
    final By decimalField = EntityAssignConstants.ASSIGN_DECIMAL_FIELD;
    final By intField = EntityAssignConstants.ASSIGN_INT_FIELD;

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private void moveToElementAction(WebDriver driver) {
        Actions builder = new Actions(driver);
        WebElement searchField = findElement(EntityAssignConstants.LINK_ASSIGN_ICON);
        TestUtils.scroll(driver, searchField);
        builder.moveToElement(searchField);
        builder.click(findElement(EntityAssignConstants.LINK_ASSIGN_ENTITY));
        Action mouseoverAndClick = builder.build();
        mouseoverAndClick.perform();
    }

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
        WebElement searchFieldAddCard = findElement(EntityAssignConstants.ASSIGN_ADD_CARD);
        searchFieldAddCard.click();
        WebElement buttonSave = findElement(locator);
        TestUtils.scroll(driver, buttonSave);
        buttonSave.click();
    }

    private void addNewCard(WebDriver driver, Map<By, String> locators, By button) {
        WebElement searchFieldAddCard = findElement(EntityAssignConstants.ASSIGN_ADD_CARD);
        searchFieldAddCard.click();

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

    @Test
    public void testReorderRecords() {

        start(getDriver());

        moveToElementAction(getDriver());

        addEmptyCard(getDriver(), EntityAssignConstants.ASSIGN_BUTTON_SAVE);
        addEmptyCard(getDriver(), EntityAssignConstants.ASSIGN_BUTTON_SAVE_DRAFT);

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

        start(getDriver());

        moveToElementAction(getDriver());

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

    private void createNewRecord(String stringRecord, String textRecord, String integerRecord, String decimalRecord) {

        final By stringField = By.id("string");
        final By textField = By.id("text");
        final By intField = By.id("int");
        final By decimalField = By.id("decimal");

        getDriver().findElement(EntityAssignConstants.ASSIGN_ADD_CARD).click();
        getDriver().findElement(stringField).sendKeys(stringRecord);
        getDriver().findElement(textField).sendKeys(textRecord);
        getDriver().findElement(intField).sendKeys(integerRecord);
        getDriver().findElement(decimalField).sendKeys(decimalRecord);

        WebElement saveButton = findElement(EntityAssignConstants.ASSIGN_BUTTON_SAVE);
        TestUtils.scrollClick(getDriver(),saveButton);
    }

    private void switchToViewMode() {

        final By listButton = By.xpath("(//*[@class='nav-item'])[3]");
        final By dropDownMenuButton = By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']");
        final By dropDownView = By.xpath("//a[contains(text(), 'view')]");

        getDriver().findElement(listButton).click();
        getDriver().findElement(dropDownMenuButton).click();
        getWait().until(ExpectedConditions.elementToBeClickable(dropDownView)).click();
    }

    private boolean isAssignButtonPresent() {
        try {
            getDriver().findElement(EntityAssignConstants.ASSIGN_ADD_CARD);
            return true;
        }catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    public void testViewMode() {
        final String stringRecord = "Just a string";
        final String textRecord = "Just a text";
        final String integerRecord = "61";
        final String decimalRecord = "0.61";
        final List<String> expectedRowsContent = List.of(stringRecord, textRecord, integerRecord, decimalRecord);

        ProjectUtils.start(getDriver());
        moveToElementAction(getDriver());
        createNewRecord(stringRecord, textRecord, integerRecord, decimalRecord);
        switchToViewMode();

        Assert.assertFalse(isAssignButtonPresent());

        List<WebElement> recordRowsContent = findElements(By.xpath("//span[@class='pa-view-field' and text()]"));

        Assert.assertEquals(recordRowsContent.size(), expectedRowsContent.size());
        for (int i = 0; i < expectedRowsContent.size(); i++) {
            Assert.assertEquals(recordRowsContent.get(i).getText(), expectedRowsContent.get(i));
        }
    }
}
