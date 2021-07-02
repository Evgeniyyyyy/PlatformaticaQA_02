import base.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import model.*;

public class EntityDefaultTest extends BaseTest {

    private static final String STRING_INPUT_VALUE = RandomStringUtils.randomAlphabetic(20);
    private static final String TEXT_INPUT_VALUE = RandomStringUtils.randomAlphabetic(15);
    private static final String INT_INPUT_VALUE = String.valueOf(RandomUtils.nextInt());
    private static final String DECIMAL_INPUT_VALUE = String.format("%.2f", new Random().nextFloat());
    private static final String DATE_VALUE = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    private static final String DATE_TIME_VALUE = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    private static final String STRING_DEFAULT_VALUE = "DEFAULT STRING VALUE";
    private static final String TEXT_DEFAULT_VALUE = "DEFAULT TEXT VALUE";
    private static final String INT_DEFAULT_VALUE = "55";
    private static final String DECIMAL_DEFAULT_VALUE = "110.32";
    private static final String DATE_DEFAULT_VALUE = "01/01/1970";
    private static final String DATE_TIME_DEFAULT_VALUE = "01/01/1970 00:00:00";
    private static final String USER_DEFAULT_NAME = "apptester1@tester.test";
    private static final String TESTER_NAME = "tester" + new Random().nextInt(299) + "@tester.test";
    private static final String EMPTY_FIELD = "";
    private static final String EDIT_STRING_VALUE = RandomStringUtils.randomAlphabetic(20);
    private static final String EDIT_TEXT_VALUE = RandomStringUtils.randomAlphabetic(15);
    private static final String EDIT_INT_VALUE = String.valueOf(RandomUtils.nextInt());
    private static final String EDIT_DECIMAL_VALUE = String.format("%.2f", new Random().nextFloat());
    private static final String EDIT_DATE_VALUE = "31/12/1000";
    private static final String EDIT_DATE_TIME_VALUE = "31/12/3000 23:59:59";
    private static final String INFO_STR_1_OF_1 = "Showing 1 to 1 of 1 rows";
    private static final String INFO_STR_2_OF_2 = "Showing 1 to 2 of 2 rows";
    private static final String CLASS_ICON_SAVE = "fa fa-check-square-o";
    private static final String CLASS_ICON_SAVE_DRAFT = "fa fa-pencil";
    private static List<String> ACTUAL_LIST = new ArrayList<>();
    private static final String ENTITY_NAME = "Default";
    private static final String CARD_BODY_TEXT_EMPTY =
            "Good job with housekeeping! Recycle bin is currently empty!";

    private static final List<String> NEW_EXPECTED_RESULT = List.of(
            STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
            DATE_VALUE, DATE_TIME_VALUE, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> DEFAULT_EXPECTED_RESULT = List.of(
            STRING_DEFAULT_VALUE, TEXT_DEFAULT_VALUE, INT_DEFAULT_VALUE, DECIMAL_DEFAULT_VALUE,
            DATE_DEFAULT_VALUE, DATE_TIME_DEFAULT_VALUE, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> EDIT_RESULT = List.of(
            EDIT_STRING_VALUE, EDIT_TEXT_VALUE, EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE, EDIT_DATE_VALUE, EDIT_DATE_TIME_VALUE, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> VIEW_RESULT = List.of(
            STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
            DATE_VALUE, DATE_TIME_VALUE, USER_DEFAULT_NAME);

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

    private static final List<String> EXPECTED_RESULT_1 = List.of(
            STRING_VALUE_1, TEXT_VALUE_1, INT_VALUE_1, DECIMAL_VALUE_1,
            DATE_DEFAULT_VALUE, DATE_TIME_DEFAULT_VALUE, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> EXPECTED_RESULT_2 = List.of(
            STRING_VALUE_2, TEXT_VALUE_2, INT_VALUE_2, DECIMAL_VALUE_2,
            DATE_DEFAULT_VALUE, DATE_TIME_DEFAULT_VALUE, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> EXPECTED_RESULT_3 = List.of(
            STRING_VALUE_3, TEXT_VALUE_3, INT_VALUE_3, DECIMAL_VALUE_3,
            DATE_DEFAULT_VALUE, DATE_TIME_DEFAULT_VALUE, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    @Test
    public void testCreateRecord() {

        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillString(STRING_INPUT_VALUE)
                .fillDate(DATE_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .clickSave();

        Assert.assertEquals(defaultPage.getRowCount(), 1);
        Assert.assertEquals(defaultPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(defaultPage.getClassIcon(), CLASS_ICON_SAVE);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testCancelDefaultRecord () {
        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickNewButton()
                .clickCancel();

        Assert.assertEquals(defaultPage.getRowCount(), 1);
        Assert.assertEquals(defaultPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(defaultPage.getClassIcon(), CLASS_ICON_SAVE);

    }

    @Test(dependsOnMethods = "testCancelDefaultRecord")
    public void testViewRecord() {
        DefaultViewPage defaultViewPage = new MainPage(getDriver())
            .clickDefaultMenu()
            .clickViewButton(0);

        ACTUAL_LIST = defaultViewPage.getRecordInViewMode();

        Assert.assertEquals(ACTUAL_LIST.size(), VIEW_RESULT.size());
        for (int i = 0; i < VIEW_RESULT.size(); i++) {
            Assert.assertEquals(ACTUAL_LIST.get(i), VIEW_RESULT.get(i).toString());
        }
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testSwitchBetweenListAndOrder() {
        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickOrderButton();
        Assert.assertEquals(defaultPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(defaultPage.getClassIcon(), CLASS_ICON_SAVE);

        defaultPage.clickListButton();
        Assert.assertEquals(defaultPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(defaultPage.getClassIcon(), CLASS_ICON_SAVE);
    }

    @Test(dependsOnMethods = "testSwitchBetweenListAndOrder")
    public void testEditRecord() {
        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickEditButton()
                .fillDateTime(EDIT_DATE_TIME_VALUE)
                .fillString(EDIT_STRING_VALUE)
                .fillDate(EDIT_DATE_VALUE)
                .fillText(EDIT_TEXT_VALUE)
                .fillInt(EDIT_INT_VALUE)
                .fillDecimal(EDIT_DECIMAL_VALUE)
                .clickSaveDraft();

        Assert.assertEquals(defaultPage.getRowCount(), 1);
        Assert.assertEquals(defaultPage.getRow(0), EDIT_RESULT);
        Assert.assertEquals(defaultPage.getClassIcon(), CLASS_ICON_SAVE_DRAFT);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {
        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickActions()
                .clickActionsDelete();
        Assert.assertTrue(defaultPage.isTableEmpty());
        Assert.assertEquals(defaultPage.getTextNotificationRecycleBin(), 1);
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testRestoreRecord() {

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickRecycleBin();
        Assert.assertEquals(RecycleBinPage.getTextPaginationInfo(), INFO_STR_1_OF_1);

        recycleBinPage.clickDeletedRestoreAsDraft();
        Assert.assertEquals(recycleBinPage.getTextCardBody(), CARD_BODY_TEXT_EMPTY);

        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu();

        Assert.assertEquals(defaultPage.getRowCount(), 1);
        Assert.assertEquals(defaultPage.getRow(0), EDIT_RESULT);
        Assert.assertEquals(defaultPage.getClassIcon(), CLASS_ICON_SAVE_DRAFT);
    }

    @Test
    public void testCreateDraftRecord() {
        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillString(STRING_INPUT_VALUE)
                .fillDate(DATE_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .clickSaveDraft();

        Assert.assertEquals(defaultPage.getRowCount(), 1);
        Assert.assertEquals(defaultPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(defaultPage.getClassIcon(), CLASS_ICON_SAVE_DRAFT);
    }

    @Test
    public void testCreateDefaultRecord() {

        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickNewButton()
                .clickSave();

        Assert.assertEquals(defaultPage.getRowCount(), 1);
        Assert.assertEquals(defaultPage.getRow(0), DEFAULT_EXPECTED_RESULT);
        Assert.assertEquals(defaultPage.getClassIcon(), CLASS_ICON_SAVE);
    }

    @Test
    public void testCreateDefaultDraftRecord() {

        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickNewButton()
                .clickSaveDraft();

        Assert.assertEquals(defaultPage.getRowCount(), 1);
        Assert.assertEquals(defaultPage.getRow(0), DEFAULT_EXPECTED_RESULT);
        Assert.assertEquals(defaultPage.getClassIcon(), CLASS_ICON_SAVE_DRAFT);
    }

    @Test
    public void testSortRecords() {

        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickNewButton()
                .fillFields(STRING_VALUE_1,TEXT_VALUE_1,INT_VALUE_1,DECIMAL_VALUE_1)
                .clickSave();
        Assert.assertEquals(defaultPage.getRowCount(), 1);
        Assert.assertEquals(defaultPage.getRow(0), EXPECTED_RESULT_1);

        defaultPage.clickDefaultMenu()
                .clickNewButton()
                .fillFields(STRING_VALUE_2,TEXT_VALUE_2,INT_VALUE_2,DECIMAL_VALUE_2)
                .clickSaveDraft();
        Assert.assertEquals(defaultPage.getRowCount(), 2);
        Assert.assertEquals(defaultPage.getRow(1), EXPECTED_RESULT_2);

        defaultPage.clickDefaultMenu()
                .clickNewButton()
                .fillFields(STRING_VALUE_3,TEXT_VALUE_3,INT_VALUE_3,DECIMAL_VALUE_3)
                .clickSave();
        Assert.assertEquals(defaultPage.getRowCount(), 3);
        Assert.assertEquals(defaultPage.getRow(2), EXPECTED_RESULT_3);

        defaultPage.clickStringColumn();
        Assert.assertEquals(defaultPage.getRow(0), EXPECTED_RESULT_2);
        Assert.assertEquals(defaultPage.getRow(1), EXPECTED_RESULT_1);
        Assert.assertEquals(defaultPage.getRow(2), EXPECTED_RESULT_3);

        defaultPage.clickTextColumn();
        Assert.assertEquals(defaultPage.getRow(0), EXPECTED_RESULT_3);
        Assert.assertEquals(defaultPage.getRow(1), EXPECTED_RESULT_2);
        Assert.assertEquals(defaultPage.getRow(2), EXPECTED_RESULT_1);

        defaultPage.clickIntColumn();
        Assert.assertEquals(defaultPage.getRow(0), EXPECTED_RESULT_2);
        Assert.assertEquals(defaultPage.getRow(1), EXPECTED_RESULT_1);
        Assert.assertEquals(defaultPage.getRow(2), EXPECTED_RESULT_3);

        defaultPage.clickDecimalColumn();
        Assert.assertEquals(defaultPage.getRow(0), EXPECTED_RESULT_1);
        Assert.assertEquals(defaultPage.getRow(1), EXPECTED_RESULT_3);
        Assert.assertEquals(defaultPage.getRow(2), EXPECTED_RESULT_2);
    }
}
