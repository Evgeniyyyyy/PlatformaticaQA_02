import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static utils.ProjectUtils.*;

import java.util.*;

public class EntityTagTest extends BaseTest{

    private static final List<String> EXPECTED_RESULT = List.of("Hello world", "Be healthy", "123", "456.98", "", "");
    private static final List<String> EDIT_RESULT = List.of("Entity Tag", "Edit a record", "8900", "284555.98", "", "");

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

        findElement(FILL_STRING).sendKeys(EXPECTED_RESULT.get(0));
        findElement(FILL_TEXT).sendKeys(EXPECTED_RESULT.get(1));
        findElement(FILL_INT).sendKeys(EXPECTED_RESULT.get(2));
        findElement(FILL_DECIMAL).sendKeys(EXPECTED_RESULT.get(3));
    }

    private void editRecord() {

        findElement(FILL_STRING).clear();
        findElement(FILL_STRING).sendKeys(EDIT_RESULT.get(0));

        findElement(FILL_TEXT).clear();
        findElement(FILL_TEXT).sendKeys(EDIT_RESULT.get(1));

        findElement(FILL_INT).clear();
        findElement(FILL_INT).sendKeys(EDIT_RESULT.get(2));

        findElement(FILL_DECIMAL).clear();
        findElement(FILL_DECIMAL).sendKeys(EDIT_RESULT.get(3));
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

    @Ignore
    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord(){
        getEntity(getDriver(), "Tag");
        clickActionsView(getWait(),getDriver(),0);
        List<WebElement> viewField =getDriver().findElements(By.xpath("//span[@class='pa-view-field']"));

        Assert.assertEquals(getActualValues(viewField), EXPECTED_RESULT);
    }
}
