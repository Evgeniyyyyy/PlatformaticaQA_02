import base.BaseTest;
import constants.EntityAssignConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityAssignTest extends BaseTest {

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

    private void toggleAction (By locator) {
        WebElement toggleActionToList = findElement(locator);
        toggleActionToList.click();
    }

    private WebElement getIcon(By locator) {
        return findElement(locator);
    }

    @Test
    public void testReorderRecords() {

        ProjectUtils.start(getDriver());

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
}
