import base.BaseTest;
import constants.EntityAssignConstants;
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
}
