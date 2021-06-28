import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static constants.EntityAssignConstants.*;
import static utils.ProjectUtils.*;
import static utils.TestUtils.movingIsFinished;
import static utils.TestUtils.scrollClick;

public class EntityVisibilityEventsTest extends BaseTest {

    final static String NEW_RECORD = "New modified record";
    final static String ENTITY_NAME = "Visibility events";

    private void clickVisibilityEventsMenu() {
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[text() = ' Visibility events ']")));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']"))));
    }

    private void clickCreateNewFolderButton() {
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//span[@class ='toggle']"))));
    }

    private void clickTriggerFieldButton() {
        getDriver().findElement(By.xpath("//span[@class ='toggle']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.id("test_field")));
    }

    private void inputTextValue(String text) {

        WebElement inputText = findElement(By.id("test_field"));

        if (inputText.getText() != null) {
            inputText.click();
            inputText.clear();
        }
        inputText.sendKeys(text);
    }

    private void clickSaveButton() {
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[text() = 'create_new_folder']")));
    }

    private void clickDropDownMenu() {
        getDriver().findElement(By.xpath("//i[contains(., 'menu')]")).click();
    }

    private void clickViewMenu() {
        clickDropDownMenu();
        getWait().until(movingIsFinished(By.xpath("//a[text()='view']"))).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='pa-view-field']")));
    }

    private String infoText() {
        return getDriver().findElement(By.xpath("//span[@class='pagination-info']")).getText();
    }

    private String getAttributeClass() {
        return getDriver().findElement(By.xpath("//div[@class='col-md-12']/div/div/i")).getAttribute("class");
    }

    private String viewText() {
        return getDriver().findElement(By.xpath("//span[@class='pa-view-field']")).getText();
    }

    private String getIconClass() {
        return findElement(ASSIGN_GET_ICON).getAttribute("class");
    }

    private String getValue() {
        return getDriver().findElement(By.xpath("//td/a")).getText();
    }

    @Test
    public void testCreateNewRecord() {

        final String testField = "My first test 2021";

        clickVisibilityEventsMenu();
        clickCreateNewFolderButton();
        clickTriggerFieldButton();
        inputTextValue(testField);
        clickSaveButton();

        Assert.assertEquals(infoText(), "Showing 1 to 1 of 1 rows");
        Assert.assertEquals(getIconClass(), "fa fa-check-square-o");
        Assert.assertEquals(getValue(), "My first test 2021");
    }

    @Test(dependsOnMethods = "testCreateNewRecord")
    public void testEditCreatedRecord() {

        getEntity(getDriver(), ENTITY_NAME);
        clickActionsEdit(getWait(), getDriver());
        inputTextValue(NEW_RECORD);
        clickSave(getDriver());

        Assert.assertEquals(infoText(), TEXT_MESSAGE_ONE);
        Assert.assertEquals(getIconClass(), CLASS_ITEM_SAVE);
        Assert.assertEquals(getValue(), NEW_RECORD);
    }

    @Ignore
    @Test(dependsOnMethods = "testEditCreatedRecord")
    public void testViewRecord() {

        getEntity(getDriver(), ENTITY_NAME);
        clickViewMenu();

        Assert.assertEquals(viewText(), "New modified record");
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }
}
