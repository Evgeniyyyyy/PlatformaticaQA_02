import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;

import static utils.ProjectUtils.*;

import java.util.*;

public class EntityTagTest extends BaseTest{

    private static final String STRING = "Hello world";
    private static final String TEXT = "Be healthy";
    private static final String INT = "123";
    private static final String DECIMAL = "456.98";
    private static final String EDIT_STRING = "Entity Tag";
    private static final String EDIT_TEXT = "Edit a record";
    private static final String EDIT_INT = "8900";
    private static final String EDIT_DECIMAL = "284555.98";

    private static final List<String> EXPECTED_RESULT = List.of(STRING, TEXT, INT, DECIMAL, "", "");
    private static final List<String> EDIT_RESULT = List.of(EDIT_STRING, EDIT_TEXT, EDIT_INT, EDIT_DECIMAL, "", "");

    private static final By ICON = By.xpath("//tbody/tr/td/i");
    private static final By ACTUAL_RESULT = By.xpath("//tbody/tr/td/a");
    private static final By DELETED_RECORD = By.cssSelector("span.pa-view-field");
    private static final By FILL_STRING = By.id("string");
    private static final By FILL_TEXT = By.id("text");
    private static final By FILL_INT = By.id("int");
    private static final By FILL_DECIMAL = By.id("decimal");

    private void fillForm() {

        getEntity(getDriver(),"Tag");
        clickCreateRecord(getDriver());

        findElement(By.id("string")).sendKeys(STRING);
        findElement(By.id("text")).sendKeys(TEXT);
        findElement(By.id("int")).sendKeys(INT);
        findElement(By.id("decimal")).sendKeys(DECIMAL);
    }

    private void editRecord() {

        findElement(FILL_STRING).clear();
        findElement(FILL_STRING).sendKeys(EDIT_STRING);

        findElement(FILL_TEXT).clear();
        findElement(FILL_TEXT).sendKeys(EDIT_TEXT);

        findElement(FILL_INT).clear();
        findElement(FILL_INT).sendKeys(EDIT_INT);

        findElement(FILL_DECIMAL).clear();
        findElement(FILL_DECIMAL).sendKeys(EDIT_DECIMAL);
        clickSave(getDriver());
    }

    @Test
    public void testCancelRecord() {

        fillForm();
        clickCancel(getDriver());

        Assert.assertNull(findElement(By.className("card-body")).getAttribute("value"));
    }

    @Test
    public void testCreateRecord() {

        fillForm();
        clickSave(getDriver());

        WebElement icon = findElement(ICON);
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-check-square-o");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
    }

    @Test
    public void testCreateDraftRecord() {

        fillForm();
        clickSaveDraft(getDriver());

        WebElement icon = findElement(ICON);
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testEditRecord() {

        getEntity(getDriver(), "Tag");
        clickActionsEdit(getWait(), getDriver());
        editRecord();

        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EDIT_RESULT);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testDeleteRecord() {

        getEntity(getDriver(), "Tag");
        clickActionsDelete(getWait(), getDriver());
        clickRecycleBin(getDriver());
        findElement(ACTUAL_RESULT).click();

        Assert.assertEquals(getActualValues(findElements(DELETED_RECORD)), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord(){
        getEntity(getDriver(), "Tag");
        clickActionsView(getWait(),getDriver(),0);
        List<WebElement> viewField =getDriver().findElements(By.xpath("//span[@class='pa-view-field']"));

        Assert.assertEquals(getActualValues(viewField), EXPECTED_RESULT);
    }
}
