import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ProjectUtils;
import utils.TestUtils;
import constants.EntityBoardConstants;

import java.util.List;

public class EntityBoardTest extends BaseTest {

    private void moveToElementAction(WebDriver driver) {
        Actions builder = new Actions(driver);
        WebElement searchField = findElement(EntityBoardConstants.LINK_BOARD_ICON);
        TestUtils.scroll(driver, searchField);
        builder.moveToElement(searchField);
        builder.click(findElement(EntityBoardConstants.LINK_BOARD_ENTITY));
        Action mouseoverAndClick = builder.build();
        mouseoverAndClick.perform();
    }

    private void addEmptyCard(WebDriver driver) {
        WebElement searchFieldAddCard = findElement(EntityBoardConstants.BOARD_ADD_CARD);
        searchFieldAddCard.click();
        WebElement buttonSave = findElement(EntityBoardConstants.BOARD_BUTTON_SAVE);
        TestUtils.scroll(driver, buttonSave);
        buttonSave.click();
    }

    private void toggleActionToList () {
        WebElement toggleActionToList = findElement(EntityBoardConstants.BOARD_TOGGLE_LIST_ACTION);
        toggleActionToList.click();
    }

    private void deleteAction() {
        WebElement buttonAction = findElement(EntityBoardConstants.BOARD_ACTION_BUTTON);
        buttonAction.click();
        WebElement deleteAction = getWait().until(ExpectedConditions.elementToBeClickable(
                EntityBoardConstants.BOARD_ACTION_DELETE));
        deleteAction.click();
    }

    private void goToRecyclingBin() {
        WebElement recyclingBin = findElement(EntityBoardConstants.BOARD_RECYCLING_BIN_ICON);
        recyclingBin.click();
    }

    private void deleteFromRecyclingBin() {
        WebElement deletePermanently = findElement(EntityBoardConstants.RECYCLING_BIN_DELETE_PERMANENTLY);
        deletePermanently.click();
    }

    private int checkCountOfRecords() {
        return findElements(By.cssSelector("tbody tr")).size();
    }

    private int countInRecyclingBin() {
        WebElement element = findElement(By.cssSelector("span.notification"));
        return Integer.parseInt(element.getText());
    }

    @Test
    public void testDeleteRecord() throws InterruptedException {

        ProjectUtils.start(getDriver());

        moveToElementAction(getDriver());

        addEmptyCard(getDriver());
        addEmptyCard(getDriver());
        toggleActionToList();

        int countOfRecordsBeforeDelete = checkCountOfRecords();

        deleteAction();

        int countOfRecordsAfterDelete = checkCountOfRecords();
        int count = countOfRecordsBeforeDelete - countOfRecordsAfterDelete;
        Assert.assertEquals(count, 1);

        int countInRecyclingBinBefore = countInRecyclingBin();
        deleteAction();
        int countInRecyclingBinAfter = countInRecyclingBin();
        int countInRB = countInRecyclingBinBefore - countInRecyclingBinAfter;
        Assert.assertEquals(countInRB, -1);
    }

    @Test
    public void testDeleteRecordFromRecyclingBin() throws InterruptedException {

        ProjectUtils.start(getDriver());

        moveToElementAction(getDriver());

        addEmptyCard(getDriver());
        addEmptyCard(getDriver());
        toggleActionToList();

        deleteAction();
        deleteAction();

        goToRecyclingBin();

        int countInRecyclingBinBefore = countInRecyclingBin();
        int countOfRecordsInRBBeforeDelete = checkCountOfRecords();

        deleteFromRecyclingBin();

        int countInRecyclingBinAfter = countInRecyclingBin();
        int countInRB = countInRecyclingBinBefore - countInRecyclingBinAfter;
        Assert.assertEquals(countInRB, 1);

        int countOfRecordsInRBAfterDelete = checkCountOfRecords();
        int count = countOfRecordsInRBBeforeDelete - countOfRecordsInRBAfterDelete;
        Assert.assertEquals(count, 1);
    }
}

