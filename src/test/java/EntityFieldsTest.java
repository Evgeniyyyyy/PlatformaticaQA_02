import base.BaseTest;
import model.FieldsEditPage;
import model.FieldsPage;
import model.MainPage;
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

    private static final String TITLE_VALUE = "Hello world";
    private static final String COMMENTS_VALUE = "Be healthy";
    private static final String INT_VALUE = "123";
    private static final String DECIMAL_VALUE = "456.98";

    private static final List<String> EDIT_RESULT = List.of(
            "Hello for everyone", "Peace to all", "345", "345.67", "", "");

    private static final List<String> EXPECTED_RESULT = List.of(
            TITLE_VALUE, COMMENTS_VALUE, INT_VALUE, DECIMAL_VALUE, "", "");

    private static final List<String> NEW_EXPECTED_RESULT = List.of(
            TITLE_VALUE, COMMENTS_VALUE, INT_VALUE, DECIMAL_VALUE, "", "", "", "apptester1@tester.test", "");

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

        findElement(FILL_STRING).sendKeys(EXPECTED_RESULT.get(0));
        findElement(FILL_TEXT).sendKeys(EXPECTED_RESULT.get(1));
        findElement(FILL_INT).sendKeys(EXPECTED_RESULT.get(2));
        findElement(FILL_DECIMAL).sendKeys(EXPECTED_RESULT.get(3));
        clickSave(getDriver());
    }

    private void editForm() {

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
    public void testCreateRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickNewButton()
                .fillTitle(TITLE_VALUE)
                .fillComments(COMMENTS_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .clickSave();

        Assert.assertEquals(fieldsPage.getRowCount(), 1);
        Assert.assertEquals(fieldsPage.getRow(0), NEW_EXPECTED_RESULT);

        //WebElement icon = getDriver().findElement(ICON);
        //Assert.assertEquals(icon.getAttribute("class"), "fa fa-check-square-o");
        //Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testEditRecord() {

        getEntity(getDriver(), "Fields");
        clickActionsEdit(getWait(),getDriver());
        editForm();

        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EDIT_RESULT);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testReorderRecord() {

        getEntity(getDriver(), "Fields");
        clickCreateRecord(getDriver());
        fillForm();

        findElement(By.xpath("//i[text()='format_line_spacing']")).click();
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)).get(0), EDIT_RESULT.get(0));

        Actions actions = new Actions(getDriver());
        WebElement row = findElement(By.xpath("//tbody/tr"));
        actions.moveToElement(row).clickAndHold(row).dragAndDropBy(row, 0, 20);
        Action swapRow = actions.build();
        swapRow.perform();

        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)).get(0), EXPECTED_RESULT.get(0));

        getDriver().findElement(By.xpath("//i[@class='fa fa-toggle-off']")).click();
        Assert.assertEquals(getActualValues(findElements(RECORD)).get(0), EXPECTED_RESULT.get(0));

        WebElement card = getDriver().findElement(By.id("customId_0"));
        actions.moveToElement(card).clickAndHold(card).dragAndDropBy(card, 0, 100);
        Action swapCard = actions.build();
        swapCard.perform();

        Assert.assertEquals(getActualValues(findElements(RECORD)).get(0), EDIT_RESULT.get(0));
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
