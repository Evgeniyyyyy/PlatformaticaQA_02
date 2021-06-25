import base.BaseTest;
import model.FieldsPage;
import model.MainPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.jsClick;

public class EntityFieldsTest extends BaseTest {

    private static final String TITLE_VALUE = getTextRandom(3);
    private static final String COMMENTS_VALUE = getTextRandom(22);
    private static final String INT_VALUE = getIntRandom();
    private static final String DECIMAL_VALUE = getDoubleRandom();
    private static final String DATE_VALUE = getDate(0);
    private static final String DATE_TIME_VALUE = getDateTime(0);
    private static final String TESTER_NAME = getUser();
    private static final String EDIT_TESTER_NAME = getUser();
    private static final String EDIT_TITLE_VALUE = getTextRandom(15);
    private static final String EDIT_COMMENTS_VALUE = getTextRandom(12);
    private static final String EDIT_INT_VALUE = getIntRandom();
    private static final String EDIT_DECIMAL_VALUE = getDoubleRandom();
    private static final String EDIT_DATE_VALUE = getDate(getRandom(2555000));
    private static final String EDIT_DATE_TIME_VALUE = getDateTime(getRandom(2555000));
    private static final String INFO_TEXT = "Showing 1 to 1 of 1 rows";
    private static final String ICON = "fa fa-check-square-o";

    private static final List<String> EXPECTED_RESULT = List.of(
            TITLE_VALUE,
            COMMENTS_VALUE,
            INT_VALUE,
            DECIMAL_VALUE,
            DATE_VALUE,
            DATE_TIME_VALUE, "",
            TESTER_NAME, "");

    private static final List<String> EDIT_RESULT = List.of(
            EDIT_TITLE_VALUE,
            EDIT_COMMENTS_VALUE,
            EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE,
            EDIT_DATE_VALUE,
            EDIT_DATE_TIME_VALUE, "",
            EDIT_TESTER_NAME, "");

    private static final List<String> ORDER_VIEW_RESULT = List.of(
            "", EDIT_TITLE_VALUE,
            EDIT_COMMENTS_VALUE,
            EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE,
            EDIT_DATE_VALUE,
            EDIT_DATE_TIME_VALUE,
            EDIT_TESTER_NAME);

    @Test
    public void testCreateRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillTitle(TITLE_VALUE)
                .fillDate(DATE_VALUE)
                .fillComments(COMMENTS_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .findUser(TESTER_NAME)
                .clickSave();

        Assert.assertEquals(fieldsPage.getRowCount(), 1);
        Assert.assertEquals(fieldsPage.getRow(0), EXPECTED_RESULT);
        Assert.assertEquals(fieldsPage.getClassIcon(), ICON);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testEditRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickEdit()
                .fillDateTime(EDIT_DATE_TIME_VALUE)
                .fillTitle(EDIT_TITLE_VALUE)
                .fillDate(EDIT_DATE_VALUE)
                .fillComments(EDIT_COMMENTS_VALUE)
                .fillInt(EDIT_INT_VALUE)
                .fillDecimal(EDIT_DECIMAL_VALUE)
                .findUser(EDIT_TESTER_NAME)
                .clickSave();

        Assert.assertEquals(fieldsPage.getRow(0), EDIT_RESULT);
        Assert.assertEquals(fieldsPage.getClassIcon(), ICON);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testReorderRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillTitle(TITLE_VALUE)
                .fillDate(DATE_VALUE)
                .fillComments(COMMENTS_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .findUser(TESTER_NAME)
                .clickSave()
                .clickOrder();

        Assert.assertEquals(fieldsPage.getRow(0), EDIT_RESULT);

        fieldsPage.getReorder(20);
        Assert.assertEquals(fieldsPage.getRow(0), EXPECTED_RESULT);

        fieldsPage.clickToggle()
                  .getReorder(140);
        Assert.assertEquals(fieldsPage.getOrderToggleRow(0), ORDER_VIEW_RESULT);
    }

    @Test(dependsOnMethods = "testReorderRecord")
    public void testSearchCreatedRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .searchInput(TITLE_VALUE)
                .findInfoText(INFO_TEXT);

        Assert.assertEquals(fieldsPage.getRow(0), EXPECTED_RESULT);
        Assert.assertEquals(fieldsPage.getClassIcon(), ICON);
    }

    @Test
    public void testCancelRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillTitle(TITLE_VALUE)
                .fillDate(DATE_VALUE)
                .fillComments(COMMENTS_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .findUser(TESTER_NAME)
                .clickCancel();

        Assert.assertTrue(fieldsPage.isTableEmpty());
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

    @Test
    public void deletePermanentlyDraftRecordFromRecycleBin() {
        jsClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Fields ']")));
        clickCreateRecord(getDriver());
        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//button [text()='Save draft']")));
        jsClick(getDriver(), getDriver().findElement(By.xpath("//a[contains(@href,'delete')]")));
        getDriver().findElement(By.partialLinkText("delete_outline")).click();
        getDriver().findElement(By.partialLinkText("delete permanently")).click();


        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@class='card-body']")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");
    }
}
