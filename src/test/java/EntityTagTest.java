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

    private static final List<String> EXPECTED_RESULT = List.of(STRING, TEXT, INT, DECIMAL, "", "");

    private static final By ICON = By.xpath("//tbody/tr/td/i");
    private static final By ACTUAL_RESULT = By.xpath("//tbody/tr/td/a");
    private static final By DELETED_RECORD = By.cssSelector("span.pa-view-field");

    private void fillForm() {

        getEntity(getDriver(),"Tag");
        clickCreateRecord(getDriver());

        findElement(By.id("string")).sendKeys(STRING);
        findElement(By.id("text")).sendKeys(TEXT);
        findElement(By.id("int")).sendKeys(INT);
        findElement(By.id("decimal")).sendKeys(DECIMAL);
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

    @Test(dependsOnMethods = "testCreateRecord")
    public void testDeleteRecord() {

        getEntity(getDriver(), "Tag");
        clickActionsDelete(getDriver());
        clickRecycleBin(getDriver());
        findElement(ACTUAL_RESULT).click();

        Assert.assertEquals(getActualValues(findElements(DELETED_RECORD)), EXPECTED_RESULT);
    }
}
