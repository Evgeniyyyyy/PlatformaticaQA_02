import base.BaseTest;
import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.ProjectUtils.*;
import java.util.*;

public class EntityAssignTest extends BaseTest {

    private static final String STRING = getTextRandom(25);
    private static final String TEXT = getTextRandom(25);
    private static final String INT = getIntRandom();
    private static final String DECIMAL = getDoubleRandom();
    private static final String DATE = "02/06/2021";
    private static final String DATETIME = "02/06/2021 22:00:28";
    private static final String EMPTY_FIELD = "";
    private static final String USER_DEFAULT_NAME = "apptester1@tester.test";

    private static final List<String> EXPECTED_RESULT = List.of(STRING, TEXT, INT, DECIMAL, DATE, DATETIME, "", USER_DEFAULT_NAME);

    private static final String CLASS_ICON_SAVE = "fa fa-check-square-o";
    private static final String CLASS_ICON_SAVE_DRAFT = "fa fa-pencil";
    private static final String INFO_STR_1_OF_1 = "Showing 1 to 1 of 1 rows";
    private static final String INFO_STR_2_OF_2 = "Showing 1 to 2 of 2 rows";
    private static final String INFO_STR_3_OF_3 = "Showing 1 to 3 of 3 rows";

    private static final String STRING_VALUE_1 = "String";
    private static final String TEXT_VALUE_1 = "Text";
    private static final String INT_VALUE_1 = "2021";
    private static final String DECIMAL_VALUE_1 = "0.10";

    private static final String STRING_VALUE_2 = "Pending";
    private static final String TEXT_VALUE_2 = "Success";
    private static final String INT_VALUE_2 = "2018";
    private static final String DECIMAL_VALUE_2 = "0.25";

    private static final String STRING_VALUE_3 = "Yamal";
    private static final String TEXT_VALUE_3 = "News";
    private static final String INT_VALUE_3 = "2035";
    private static final String DECIMAL_VALUE_3 = "0.12";

    private static final List<String> EDIT_RESULT = List.of(
            STRING_VALUE_3, TEXT_VALUE_3, INT_VALUE_3, DECIMAL_VALUE_3, DATE, DATETIME, "", USER_DEFAULT_NAME);

    private static final List<String> EXPECTED_RESULT_1 = List.of(
            STRING_VALUE_1, TEXT_VALUE_1, INT_VALUE_1, DECIMAL_VALUE_1,
            EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> EXPECTED_RESULT_2 = List.of(
            STRING_VALUE_2, TEXT_VALUE_2, INT_VALUE_2, DECIMAL_VALUE_2,
            EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> EXPECTED_RESULT_3 = List.of(
            STRING_VALUE_3, TEXT_VALUE_3, INT_VALUE_3, DECIMAL_VALUE_3,
            EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> EXPECTED_ORDER_RESULT_1 = List.of(
            EMPTY_FIELD, STRING_VALUE_1, TEXT_VALUE_1, INT_VALUE_1, DECIMAL_VALUE_1,
            EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> EXPECTED_ORDER_RESULT_2 = List.of(
            EMPTY_FIELD, STRING_VALUE_2, TEXT_VALUE_2, INT_VALUE_2, DECIMAL_VALUE_2,
            EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);


    @Test
    public void testCreateRecord() {

        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickNewButton()
                .fillTitle(STRING)
                .fillComments(TEXT)
                .fillInt(INT)
                .fillDecimal(DECIMAL)
                .fillDateTime(DATETIME)
                .fillDate(DATE)
                .findUser(USER_DEFAULT_NAME)
                .clickSave();

        Assert.assertEquals(assignPage.getRowCount(), 1);
        Assert.assertEquals(assignPage.getRow(0), EXPECTED_RESULT);
        Assert.assertEquals(assignPage.getClassIcon(), CLASS_ICON_SAVE);
    }

    @Test
    public void testCancelRecord() {

        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickNewButton()
                .fillTitle(STRING)
                .fillComments(TEXT)
                .fillInt(INT)
                .fillDate(DATE)
                .fillDateTime(DATETIME)
                .clickCancel();

        Assert.assertTrue(assignPage.isTableEmpty());
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewMode() {
        AssignViewPage assignViewPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickViewButton(0);

        Assert.assertEquals(assignViewPage.getRecordInViewMode(), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testViewMode")
    public void testEditRecord() {

        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickEditButton()
                .fillTitle(STRING_VALUE_3)
                .fillComments(TEXT_VALUE_3)
                .fillInt(INT_VALUE_3)
                .fillDecimal(DECIMAL_VALUE_3)
                .findUser(USER_DEFAULT_NAME)
                .clickSave();

        Assert.assertEquals(assignPage.getRow(0), EDIT_RESULT);
        Assert.assertEquals(assignPage.getClassIcon(), CLASS_ICON_SAVE);
    }

    @Test
    public void testSaveDraftRecord() {

        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickNewButton()
                .fillTitle(STRING)
                .fillComments(TEXT)
                .fillInt(INT)
                .fillDecimal(DECIMAL)
                .fillDateTime(DATETIME)
                .fillDate(DATE)
                .findUser(USER_DEFAULT_NAME)
                .clickSaveDraft();

        Assert.assertEquals(assignPage.getRowCount(), 1);
        Assert.assertEquals(assignPage.getRow(0), EXPECTED_RESULT);
        Assert.assertEquals(assignPage.getClassIcon(), CLASS_ICON_SAVE_DRAFT);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {

        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickDelete();

        Assert.assertTrue(assignPage.isTableEmpty());

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickRecycleBin();

        Assert.assertEquals(recycleBinPage.getTextNotificationRowCount(), "1");
        Assert.assertEquals(recycleBinPage.getRowCount(), 1);
    }

    @Test
    public void testReorderRecords() {

        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickNewButton()
                .fillFields(STRING_VALUE_1, TEXT_VALUE_1, INT_VALUE_1, DECIMAL_VALUE_1)
                .clickSave();
        Assert.assertEquals(assignPage.getRow(0), EXPECTED_RESULT_1);

        assignPage.clickNewButton()
                .fillFields(STRING_VALUE_2, TEXT_VALUE_2, INT_VALUE_2, DECIMAL_VALUE_2)
                .clickSaveDraft();
        Assert.assertEquals(assignPage.getRow(1), EXPECTED_RESULT_2);

        assignPage.clickOrderButton().getReorder();
        Assert.assertEquals(assignPage.getRow(0), EXPECTED_RESULT_2);
        Assert.assertEquals(assignPage.getRow(1), EXPECTED_RESULT_1);

        assignPage.clickToggle()
                .getNewReorder();
        Assert.assertEquals(assignPage.getRows(0), EXPECTED_ORDER_RESULT_1);
        Assert.assertEquals(assignPage.getRows(1), EXPECTED_ORDER_RESULT_2);
    }

    @Test(dependsOnMethods = "testReorderRecords")
    public void testSearchRecordThird() {
        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .clickNewButton()
                .fillFields(STRING_VALUE_3, TEXT_VALUE_3, INT_VALUE_3, DECIMAL_VALUE_3)
                .clickSave()
                .searchInput(STRING_VALUE_3)
                .getTextPaginationInfo(INFO_STR_1_OF_1);
        Assert.assertEquals(assignPage.getRow(0), EXPECTED_RESULT_3);

   }

    @Test(dependsOnMethods = "testSearchRecordThird")
    public void testSearchRecordEmpty() {
        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .searchInput("").getTextPaginationInfo(INFO_STR_3_OF_3);
        Assert.assertEquals(assignPage.getRowCount(), 3);

    }

    @Test(dependsOnMethods = "testSearchRecordEmpty")
    public void testSearchRecordSecond() {
        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .searchInput(TEXT_VALUE_2).getTextPaginationInfo(INFO_STR_1_OF_1);
        Assert.assertEquals(assignPage.getRow(0), EXPECTED_RESULT_2);

    }

    @Test(dependsOnMethods = "testSearchRecordSecond")
    public void testSearchRecordFirst() {
        AssignPage assignPage = new MainPage(getDriver())
                .clickAssignMenu()
                .searchInput("")
                .searchInput(INT_VALUE_1).getTextPaginationInfo(INFO_STR_1_OF_1);
        Assert.assertEquals(assignPage.getRow(0), EXPECTED_RESULT_1);

    }
}
