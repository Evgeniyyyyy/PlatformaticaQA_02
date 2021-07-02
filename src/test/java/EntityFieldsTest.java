import base.BaseTest;
import model.FieldsOrderPage;
import model.FieldsPage;
import model.MainPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.jsClick;

public class EntityFieldsTest extends BaseTest {

    private static final String TITLE_VALUE = getTextRandom(15);
    private static final String COMMENTS_VALUE = getTextRandom(22);
    private static final String INT_VALUE = getIntRandom();
    private static final String DECIMAL_VALUE = getDoubleRandom();
    private static final String DATE_VALUE = getDate(0);
    private static final String DATE_TIME_VALUE = getDateTime(0);
    private static final String USER_NAME = getUser();
    private static final String EDIT_USER_NAME = getUser();
    private static final String EDIT_TITLE_VALUE = "Hello world";
    private static final String SORT_COMMENTS_VALUE = "Abstract sort";
    private static final String EDIT_COMMENTS_VALUE = "Exact word";
    private static final String EDIT_INT_VALUE = "-2147483648";
    private static final String EDIT_DECIMAL_VALUE = "-323232.32";
    private static final String EDIT_DATE_VALUE = getDate(getRandom(2555000));
    private static final String EDIT_DATE_TIME_VALUE = getDateTime(getRandom(2555000));
    private static final String INFO_TEXT = "Showing 1 to 1 of 1 rows";
    private static final String SAVE_ICON = "fa fa-check-square-o";
    private static final String DRAFT_ICON = "fa fa-pencil";

    private static final List<String> EXPECTED_RESULT = List.of(
            TITLE_VALUE,
            COMMENTS_VALUE,
            INT_VALUE,
            DECIMAL_VALUE,
            DATE_VALUE,
            DATE_TIME_VALUE, "",
            USER_NAME, "");

    private static final List<String> EDIT_RESULT = List.of(
            EDIT_TITLE_VALUE,
            EDIT_COMMENTS_VALUE,
            EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE,
            EDIT_DATE_VALUE,
            EDIT_DATE_TIME_VALUE, "",
            EDIT_USER_NAME, "");

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
                .findUser(USER_NAME)
                .clickSave();

        Assert.assertEquals(fieldsPage.getClassIcon(), SAVE_ICON);
        Assert.assertEquals(fieldsPage.getRowCount(), 1);
        Assert.assertEquals(fieldsPage.getRow(0), EXPECTED_RESULT);
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
                .findUser(EDIT_USER_NAME)
                .clickSave();

        Assert.assertEquals(fieldsPage.getRow(0), EDIT_RESULT);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testReorderRecord() {

        FieldsOrderPage fieldsOrderPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillTitle(TITLE_VALUE)
                .fillDate(DATE_VALUE)
                .fillComments(COMMENTS_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .findUser(USER_NAME)
                .clickSave()
                .clickOrderButton()
                .movingRecord();

        Assert.assertEquals(fieldsOrderPage.getRow(0), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testReorderRecord")
    public void testReorderAfterToggle() {

        final List<String> ORDER_VIEW_RESULT = List.of(
                "", EDIT_TITLE_VALUE,
                EDIT_COMMENTS_VALUE,
                EDIT_INT_VALUE,
                EDIT_DECIMAL_VALUE,
                EDIT_DATE_VALUE,
                EDIT_DATE_TIME_VALUE,
                EDIT_USER_NAME);

        FieldsOrderPage fieldsOrderPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickOrderButton()
                .clickToggleOrder()
                .movingBlockRecord();

        Assert.assertEquals(fieldsOrderPage.getOrderedRows(0), ORDER_VIEW_RESULT);
    }

    @Test(dependsOnMethods = "testReorderAfterToggle")
    public void testSearchCreatedRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .searchInput(TITLE_VALUE)
                .findInfoText(INFO_TEXT);

        Assert.assertEquals(fieldsPage.getClassIcon(), SAVE_ICON);
        Assert.assertEquals(fieldsPage.getRow(0), EXPECTED_RESULT);
    }

    @Test
    public void testSortRecords() {

        final List<String> SORTED_RESULT = List.of(
                TITLE_VALUE,
                SORT_COMMENTS_VALUE,
                INT_VALUE,
                DECIMAL_VALUE,
                DATE_VALUE,
                DATE_TIME_VALUE, "",
                USER_NAME, "");

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickNewButton()
                .fillDateTime(EDIT_DATE_TIME_VALUE)
                .fillTitle(EDIT_TITLE_VALUE)
                .fillDate(EDIT_DATE_VALUE)
                .fillComments(EDIT_COMMENTS_VALUE)
                .fillInt(EDIT_INT_VALUE)
                .fillDecimal(EDIT_DECIMAL_VALUE)
                .findUser(EDIT_USER_NAME)
                .clickSave()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillTitle(TITLE_VALUE)
                .fillDate(DATE_VALUE)
                .fillComments(SORT_COMMENTS_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .findUser(USER_NAME)
                .clickSaveDraft()
                .clickSort();

        Assert.assertEquals(fieldsPage.getClassIcon(), DRAFT_ICON);
        Assert.assertEquals(fieldsPage.getRow(0), SORTED_RESULT);
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
                .findUser(USER_NAME)
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
