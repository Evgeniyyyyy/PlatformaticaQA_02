import base.BaseTest;
import constants.EntityParentConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.ProjectUtils.*;
import utils.TestUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EntityParentTest extends BaseTest {
    final String stringInputValue = "Pending";
    final String textInputValue = "qwerty";
    final String intInputValue = "12345";
    final String decimalInputValue = "0.10";
    final String emptyField = "";
    final String userName = "apptester10@tester.test";

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    final List<Object> expectedValues = Arrays
            .asList(stringInputValue, textInputValue, intInputValue, decimalInputValue,
                    formatter.format(date), emptyField, emptyField, userName);

    private void fillForm() {

        findElement(EntityParentConstants.STRING_FIELD).sendKeys(stringInputValue);
        findElement(EntityParentConstants.TEXT_FIELD).sendKeys(textInputValue);
        findElement(EntityParentConstants.INT_FIELD).sendKeys(intInputValue);
        findElement(EntityParentConstants.DECIMAL_FIELD).sendKeys(decimalInputValue);
        findElement(EntityParentConstants.DATE_FIELD).click();
        findElement(EntityParentConstants.TESTER_NAME_FIELD).click();

        TestUtils.jsClick(getDriver(), findElement(EntityParentConstants.TESTER_NAME));
    }

    private void createRecord(By button) {
        findElement(EntityParentConstants.PARENT_ADD_CARD).click();
        fillForm();
        clickSaveButton(button);
    }

    private void clickSaveButton(By locator) {
        findElement(locator).click();
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
    public void testCreateNewDraftRecord() {

        start(getDriver());

        TestUtils.scrollClick(getDriver(),
                findElement(EntityParentConstants.LINK_PARENT_ENTITY));

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

    @Test
    public void testCancelRecord() {
        start(getDriver());
        TestUtils.scrollClick(getDriver(),
                findElement(EntityParentConstants.LINK_PARENT_ENTITY));

        clickCreateRecord(getDriver());
        fillForm();
        clickCancel(getDriver());

        Assert.assertNull(findElement(EntityParentConstants.PARENT_GET_CONTANER).getAttribute("value"));
    }

    @Test
    public void testDeleteRecord() {
        start(getDriver());

        TestUtils.scrollClick(getDriver(),
                findElement(EntityParentConstants.LINK_PARENT_ENTITY));

        clickCreateRecord(getDriver());
        fillForm();
        clickSave(getDriver());
        getWait().until(ExpectedConditions.visibilityOf(findElement(EntityParentConstants.PARENT_ADD_CARD)));
        clickCreateRecord(getDriver());
        fillForm();
        clickSaveDraft(getDriver());

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
