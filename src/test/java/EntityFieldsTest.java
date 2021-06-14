import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.jsClick;

public class EntityFieldsTest extends BaseTest {

    private static final String STRING = "Hello world";
    private static final String TEXT = "Be healthy";
    private static final String INT = "123";
    private static final String DECIMAL = "456.98";
    private static final String EDIT_STRING = "Hello for everyone";
    private static final String EDIT_TEXT = "Peace to all";
    private static final String EDIT_INT = "345";
    private static final String EDIT_DECIMAL = "345.67";

    private static final List<String> EDIT_RESULT = List.of(EDIT_STRING, EDIT_TEXT, EDIT_INT, EDIT_DECIMAL, "", "");
    private static final List<String> EXPECTED_RESULT = List.of(STRING, TEXT, INT, DECIMAL, "", "");

    private static final By ICON = By.xpath("//tbody/tr/td/i");
    private static final By FILL_STRING = By.id("title");
    private static final By FILL_TEXT = By.id("comments");
    private static final By FILL_INT = By.id("int");
    private static final By FILL_DECIMAL = By.id("decimal");
    private static final By ACTUAL_RESULT = By.xpath("//tbody/tr/td/a");
    private static final By RECORD = By.xpath("//div/span/a");

    private void fillForm() {

        getEntity(getDriver(), "Fields");
        clickCreateRecord(getDriver());

        findElement(FILL_STRING).sendKeys(STRING);
        findElement(FILL_TEXT).sendKeys(TEXT);
        findElement(FILL_INT).sendKeys(INT);
        findElement(FILL_DECIMAL).sendKeys(DECIMAL);
        clickSave(getDriver());
    }

    private void editForm() {

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
    public void testCreateRecord() {

        fillForm();

        WebElement icon = getDriver().findElement(ICON);
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-check-square-o");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testReorderRecord() {

        getEntity(getDriver(), "Fields");
        clickCreateRecord(getDriver());
        editForm();

        findElement(By.xpath("//i[text()='format_line_spacing']")).click();
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)).get(0), EXPECTED_RESULT.get(0));

        Actions actions = new Actions(getDriver());
        WebElement row = findElement(By.xpath("//tbody/tr"));
        actions.moveToElement(row).clickAndHold(row).dragAndDropBy(row, 0, 20);
        Action swapRow = actions.build();
        swapRow.perform();

        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)).get(0), EDIT_RESULT.get(0));

        getDriver().findElement(By.xpath("//i[@class='fa fa-toggle-off']")).click();
        Assert.assertEquals(getActualValues(findElements(RECORD)).get(0), EDIT_RESULT.get(0));

        WebElement card = getDriver().findElement(By.id("customId_0"));
        actions.moveToElement(card).clickAndHold(card).dragAndDropBy(card, 0, 100);
        Action swapCard = actions.build();
        swapCard.perform();

        Assert.assertEquals(getActualValues(findElements(RECORD)).get(0), EXPECTED_RESULT.get(0));

    }

    @Test(dependsOnMethods = "testReorderRecord")
    public void testSearchCreatedRecord() {

        getEntity(getDriver(), "Fields");
        findElement(By.xpath("//input[@placeholder = 'Search']")).sendKeys(EXPECTED_RESULT.get(0));

        getWait().until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//span[@class='pagination-info']"), "Showing 1 to 1 of 1 rows"));

        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
    }

    @Test
    public void deletePermanentlyRecordFromRecycleBin() {
        jsClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Fields ']")));
        clickCreateRecord(getDriver());
        clickSave(getDriver());
        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']")));
        jsClick(getDriver(), getDriver().findElement(By.xpath("//a[contains(@href,'delete')]")));
        getDriver().findElement(By.partialLinkText("delete_outline")).click();
        getDriver().findElement(By.partialLinkText("delete permanently")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@class='card-body']")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");
    }
}
