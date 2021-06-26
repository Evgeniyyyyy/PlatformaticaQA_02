import base.BaseTest;
import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static utils.ProjectUtils.*;

public class EntityTagTest extends BaseTest{

    private static final String STRING_VALUE = getTextRandom(3);
    private static final String TEXT_VALUE = getTextRandom(22);
    private static final String INT_VALUE = getIntRandom();
    private static final String DECIMAL_VALUE = getDoubleRandom();
    private static final String DATE_VALUE = getDate(0);
    private static final String DATE_TIME_VALUE = getDateTime(0);
    private static final String TESTER_NAME = getUser();
    private static final String EDIT_TESTER_NAME = getUser();
    private static final String EDIT_STRING_VALUE = getTextRandom(15);
    private static final String EDIT_TEXT_VALUE = getTextRandom(12);
    private static final String EDIT_INT_VALUE = getIntRandom();
    private static final String EDIT_DECIMAL_VALUE = getDoubleRandom();
    private static final String EDIT_DATE_VALUE = getDate(getRandom(2555000));
    private static final String EDIT_DATE_TIME_VALUE = getDateTime(getRandom(2555000));
    private static final String ICON = "fa fa-check-square-o";
    private static final String ICON_DRAFT = "fa fa-pencil";

    private static final List<String> EXPECTED_RESULT = List.of(
            STRING_VALUE,
            TEXT_VALUE,
            INT_VALUE,
            DECIMAL_VALUE,
            DATE_VALUE,
            DATE_TIME_VALUE, "",
            TESTER_NAME);

    private static final List<String> EDIT_RESULT = List.of(
            EDIT_STRING_VALUE,
            EDIT_TEXT_VALUE,
            EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE,
            EDIT_DATE_VALUE,
            EDIT_DATE_TIME_VALUE, "",
            EDIT_TESTER_NAME);

    @Test
    public void testCancelRecord() {

        TagPage tagPage = new MainPage(getDriver())
                .clickTagMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillString(STRING_VALUE)
                .fillDate(DATE_VALUE)
                .fillText(TEXT_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .findUser(TESTER_NAME)
                .clickCancel();

        Assert.assertTrue(tagPage.isTableEmpty());
    }

    @Test
    public void testCreateRecord() {

        TagPage tagPage = new MainPage(getDriver())
                .clickTagMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillString(STRING_VALUE)
                .fillDate(DATE_VALUE)
                .fillText(TEXT_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .findUser(TESTER_NAME)
                .clickSave();

        Assert.assertEquals(tagPage.getRowCount(), 1);
        Assert.assertEquals(tagPage.getRow(0), EXPECTED_RESULT);
        Assert.assertEquals(tagPage.getClassIcon(), ICON);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord(){

        TagViewPage tagViewPage = new MainPage(getDriver())
                .clickTagMenu()
                .clickView();

        Assert.assertEquals(tagViewPage.getRecordInViewMode(), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testDeleteRecord() {

        TagPage tagPage = new MainPage(getDriver())
                .clickTagMenu()
                .clickDelete();

        Assert.assertTrue(tagPage.isTableEmpty());

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickRecycleBin();

        Assert.assertEquals(recycleBinPage.getTextNotificationRowCount(), "1");
        Assert.assertEquals(recycleBinPage.getRowCount(), 1);
    }

    @Test
    public void testCreateDraftRecord() {

        TagPage tagPage = new MainPage(getDriver())
                .clickTagMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillString(STRING_VALUE)
                .fillDate(DATE_VALUE)
                .fillText(TEXT_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .findUser(TESTER_NAME)
                .clickDraft();

        Assert.assertEquals(tagPage.getRowCount(), 1);
        Assert.assertEquals(tagPage.getRow(0), EXPECTED_RESULT);
        Assert.assertEquals(tagPage.getClassIcon(), ICON_DRAFT);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testEditRecord() {

        TagPage tagPage = new MainPage(getDriver())
                .clickTagMenu()
                .clickEdit()
                .fillDateTime(EDIT_DATE_TIME_VALUE)
                .fillString(EDIT_STRING_VALUE)
                .fillDate(EDIT_DATE_VALUE)
                .fillText(EDIT_TEXT_VALUE)
                .fillInt(EDIT_INT_VALUE)
                .fillDecimal(EDIT_DECIMAL_VALUE)
                .findUser(EDIT_TESTER_NAME)
                .clickSave();

        Assert.assertEquals(tagPage.getRowCount(), 1);
        Assert.assertEquals(tagPage.getRow(0), EDIT_RESULT);
        Assert.assertEquals(tagPage.getClassIcon(), ICON);
    }
}
