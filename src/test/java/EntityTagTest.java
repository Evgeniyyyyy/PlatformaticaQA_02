import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.ProjectUtils.*;

import java.util.*;

public class EntityTagTest extends BaseTest{

    private static final String STRING = "Hello world";
    private static final String TEXT = "Be healthy";
    private static final String INT = "123";
    private static final String DECIMAL = "456.98";

    private static final List<String> EXPECTED_RESULT = List.of(STRING, TEXT, INT, DECIMAL, "", "");

    private static final By ICON = By.xpath("//tbody/tr/td/i");

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
        for (int i = 0; i < ACTUAL_RESULT(getDriver()).size(); i++) {
            Assert.assertEquals(ACTUAL_RESULT(getDriver()).get(i).getText(), EXPECTED_RESULT.get(i));
        }
    }

    @Test
    public void testCreateDraftRecord() {

        fillForm();
        clickSaveDraft(getDriver());

        WebElement icon = findElement(ICON);
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");
        for (int i = 0; i < ACTUAL_RESULT(getDriver()).size(); i++) {
            Assert.assertEquals(ACTUAL_RESULT(getDriver()).get(i).getText(), EXPECTED_RESULT.get(i));
        }
    }
}
