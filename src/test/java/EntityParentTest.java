import base.BaseTest;
import constants.EntityParentConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
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

    @Test
    public void testViewRecord() {

        ProjectUtils.start(getDriver());

        TestUtils.scrollClick(getDriver(),
                findElement(EntityParentConstants.LINK_PARENT_ENTITY));

        createRecord(EntityParentConstants.PARENT_BUTTON_SAVE_DRAFT);

        List<WebElement> records = findElements(EntityParentConstants.PARENT_GET_LIST_ROW);

        Assert.assertEquals(records.size(), 1);
        Assert.assertEquals(getIcon(EntityParentConstants.PARENT_GET_ICON)
                .getAttribute("class"), EntityParentConstants.CLASS_ITEM_SAVE_DRAFT);
    }
}