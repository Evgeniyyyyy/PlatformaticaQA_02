import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.*;
import model.*;

import static utils.ProjectUtils.*;

@Ignore
public class EntityParentTest extends BaseTest {

    private static final String STRING_INPUT_VALUE = getTextRandom(20);
    private static final String TEXT_INPUT_VALUE = getTextRandom(15);
    private static final String INT_INPUT_VALUE = getIntRandom();
    private static final String DECIMAL_INPUT_VALUE = getDoubleRandom();
    private static final String DATE_VALUE = getDate(0);
    private static final String DATE_TIME_VALUE = getDateTime(0);
    private static final String USER_NAME = getUser();
    private static final String EDIT_USER_NAME = getUser();
    private static final String EMPTY_FIELD = "";
    private static final String EDIT_STRING_VALUE = getTextRandom(20);
    private static final String EDIT_TEXT_VALUE = getTextRandom(15);
    private static final String EDIT_INT_VALUE = getIntRandom();
    private static final String EDIT_DECIMAL_VALUE = getDoubleRandom();
    private static final String EDIT_DATE_VALUE = getDate(350000);
    private static final String EDIT_DATE_TIME_VALUE = getDateTime(53000);
    private static final String INFO_STR_1_OF_1 = "Showing 1 to 1 of 1 rows";
    private static final String CLASS_ICON_SAVE = "fa fa-check-square-o";
    private static final String CLASS_ICON_SAVE_DRAFT = "fa fa-pencil";

    private static final List<String> NEW_EXPECTED_RESULT = List.of(
            STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
            DATE_VALUE, DATE_TIME_VALUE, EMPTY_FIELD, USER_NAME);

    private static final List<String> EDIT_RESULT = List.of(
            EDIT_STRING_VALUE, EDIT_TEXT_VALUE, EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE, EDIT_DATE_VALUE, EDIT_DATE_TIME_VALUE, EMPTY_FIELD, EDIT_USER_NAME);

    @Test
    public void testCreateRecord() {

        ParentPage parentPage = new MainPage(getDriver())
                .clickParentMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillString(STRING_INPUT_VALUE)
                .fillDate(DATE_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .findUser(USER_NAME)
                .clickSave();

        Assert.assertEquals(parentPage.getClassIcon(), CLASS_ICON_SAVE);
        Assert.assertEquals(parentPage.getRowCount(), 1);
        Assert.assertEquals(parentPage.getRow(0), NEW_EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {

        ParentViewPage parentViewPage = new MainPage(getDriver())
                .clickParentMenu()
//                .clickActions()
                .clickNewViewButton(0);

        Assert.assertEquals(parentViewPage.getRecordInViewMode(), NEW_EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testEditRecord() {

        ParentPage parentPage = new MainPage(getDriver())
                .clickParentMenu()
//                .clickActions()
                .clickEditButton()
                .fillDateTime(EDIT_DATE_TIME_VALUE)
                .fillString(EDIT_STRING_VALUE)
                .fillDate(EDIT_DATE_VALUE)
                .fillText(EDIT_TEXT_VALUE)
                .fillInt(EDIT_INT_VALUE)
                .fillDecimal(EDIT_DECIMAL_VALUE)
                .findUser(EDIT_USER_NAME)
                .clickSaveDraft();

        Assert.assertEquals(parentPage.getRowCount(), 1);
        Assert.assertEquals(parentPage.getRow(0), EDIT_RESULT);
    }

    @Test(dependsOnMethods = "testReorderRecord")
    public void testSearchRecord() {

        ParentPage parentPage = new MainPage(getDriver())
                .clickParentMenu()
                .searchInput(STRING_INPUT_VALUE)
                .getTextPaginationInfo(INFO_STR_1_OF_1);

        Assert.assertEquals(parentPage.getRow(0), NEW_EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testReorderRecord() {

        ParentOrderPage parentOrderPage = new MainPage(getDriver())
                .clickParentMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillString(STRING_INPUT_VALUE)
                .fillDate(DATE_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .findUser(USER_NAME)
                .clickSave()
                .clickOrderButton()
                .movingRecord();

        Assert.assertEquals(parentOrderPage.getRow(0), NEW_EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testReorderRecord")
    public void testReorderAfterMovingToggle() {

        final List<String> VIEW_RESULT = List.of(
                EMPTY_FIELD, EDIT_STRING_VALUE, EDIT_TEXT_VALUE, EDIT_INT_VALUE,
                EDIT_DECIMAL_VALUE, EDIT_DATE_VALUE, EDIT_DATE_TIME_VALUE, EDIT_USER_NAME);

        ParentOrderPage parentOrderPage = new MainPage(getDriver())
                .clickParentMenu()
                .clickOrderButton()
                .clickToggleOrder()
                .movingBlockRecord();

        Assert.assertEquals(parentOrderPage.getOrderedRows(0), VIEW_RESULT);
    }

    @Test
    public void testCancelRecord() {

        ParentPage parentPage = new MainPage(getDriver())
                .clickParentMenu()
                .clickNewButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .findUser(USER_NAME)
                .clickCancel();

        Assert.assertTrue(parentPage.isTableEmpty());
    }

    @Test(dependsOnMethods = "testCancelRecord")
    public void testCreateNewDraftRecord() {

        ParentPage parentPage = new MainPage(getDriver())
                .clickParentMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillString(STRING_INPUT_VALUE)
                .fillDate(DATE_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .findUser(USER_NAME)
                .clickSaveDraft();

        Assert.assertEquals(parentPage.getRowCount(), 1);
        Assert.assertEquals(parentPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(parentPage.getClassIcon(), CLASS_ICON_SAVE_DRAFT);
    }

    @Test(dependsOnMethods = "testCreateNewDraftRecord")
    public void testDeleteRecord() {

        ParentPage parentPage = new MainPage(getDriver())
                .clickParentMenu()
//                .clickActions()
                .clickActionsDelete();

        Assert.assertTrue(parentPage.isTableEmpty());
        Assert.assertEquals(parentPage.getTextNotificationRecycleBin(), 1);
    }
}
