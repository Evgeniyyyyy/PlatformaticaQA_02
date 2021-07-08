import base.BaseTest;

import model.*;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static utils.ProjectUtils.*;

public class EntityBoardTest extends BaseTest {

    private static final String DRAFT_RECORD_ICON_CLASS_NAME = "fa fa-pencil";
    private static final String NON_DRAFT_RECORD_ICON_CLASS_NAME = "fa fa-check-square-o";
    private static final String PAGINATION_INFO_STR_1_OF_1 = "Showing 1 to 1 of 1 rows";
    private static final String PAGINATION_INFO_STR_2_OF_2 = "Showing 1 to 2 of 2 rows";
    private static final String MESSAGE_EMPTY_RECYCLE_BIN = "Good job with housekeeping! Recycle bin is currently empty!";

    private static final String TEXT_VALUE_PENDING = getTextRandom(8);
    private static final String INT_VALUE_PENDING = getIntRandom();
    private static final String DECIMAL_VALUE_PENDING = getDoubleRandom();
    private static final String TEXT_VALUE_ONTRACK = getTextRandom(8);
    private static final String INT_VALUE_ONTRACK = getIntRandom();
    private static final String DECIMAL_VALUE_ONTRACK = getDoubleRandom();
    private static final String DATA_VALUE = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    private static final String DATA_TIME_VALUE = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    private static final String USER_NAME = getUser();
    private static final String EDIT_TEXT_VALUE = "QWERTY";
    private static final String EDIT_INT_VALUE = "12345";
    private static final String EDIT_DECIMAL_VALUE = "33.55";
    private static final String EDIT_DATA_VALUE = "01/12/2000";
    private static final String EDIT_DATA_TIME_VALUE = "31/01/2100 00:59:01";
    private static final String EDIT_USER_NAME = "tester299@tester.test";

    private static List<String> EXPECTED_CREATED_PENDING_RECORD;
    private static List<String> EXPECTED_EDITED_RECORD;
    private static List<String> EXPECTED_CREATED_ONTRACK_RECORD;
    private static List<List<String>> ALL_RECORDS_TABLE;

    @Test
    public void testCreateDraftRecord() {

        EXPECTED_CREATED_PENDING_RECORD = List.of(
                BoardBaseEditPage.FieldString.Pending.getValue(),
                TEXT_VALUE_PENDING,
                INT_VALUE_PENDING,
                DECIMAL_VALUE_PENDING,
                DATA_VALUE,
                DATA_TIME_VALUE,
                "", USER_NAME);

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickCreateRecordButton()
                .fillAllFields(BoardBaseEditPage.FieldString.Pending,
                        TEXT_VALUE_PENDING,
                        INT_VALUE_PENDING,
                        DECIMAL_VALUE_PENDING,
                        DATA_VALUE,
                        DATA_TIME_VALUE)
                .findUser(USER_NAME)
                .clickSaveDraft()
                .clickListButton();

        Assert.assertEquals(boardListPage.getClassIcon(), DRAFT_RECORD_ICON_CLASS_NAME);
        Assert.assertEquals(boardListPage.getRowCount(), 1);
        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testViewRecord() {

        BoardViewPage boardViewPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .clickViewButton(0);

        Assert.assertEquals(boardViewPage.getRecordInViewMode(), EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testEditDraftRecord() {

        EXPECTED_EDITED_RECORD = List.of(
                BoardBaseEditPage.FieldString.OnTrack.getValue(),
                EDIT_TEXT_VALUE,
                EDIT_INT_VALUE,
                EDIT_DECIMAL_VALUE,
                EDIT_DATA_VALUE,
                EDIT_DATA_TIME_VALUE,
                "", EDIT_USER_NAME);

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .clickActions()
                .clickActionsEdit()
                .clearFields()
                .fillAllFields(BoardBaseEditPage.FieldString.OnTrack,
                        EDIT_TEXT_VALUE,
                        EDIT_INT_VALUE,
                        EDIT_DECIMAL_VALUE,
                        EDIT_DATA_VALUE,
                        EDIT_DATA_TIME_VALUE)
                .findUser(EDIT_USER_NAME)
                .clickSaveDraft();

        Assert.assertEquals(boardListPage.getRowCount(), 1);
        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_EDITED_RECORD);
    }

    @Test(dependsOnMethods = "testEditDraftRecord")
    public void testDeleteRecord() {

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .clickActions()
                .clickActionsDelete();

        Assert.assertTrue(boardListPage.isTableEmpty());

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickRecycleBin();

        Assert.assertEquals(recycleBinPage.getTextNotificationRowCount(), "1");
        Assert.assertEquals(recycleBinPage.getRowCount(), 1);
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testRestoreRecord() {

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickRecycleBin();

        String textRecycleBinNotificationBeforeRestore = recycleBinPage.getTextNotificationRecycleBin();

        recycleBinPage.clickDeletedRestoreAsDraft();

        Assert.assertEquals(recycleBinPage.getTextCardBody(), MESSAGE_EMPTY_RECYCLE_BIN);

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton();

        String textRecycleBinNotificationAfterRestore = recycleBinPage.getTextNotificationRecycleBin();

        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_EDITED_RECORD);
        Assert.assertEquals(boardListPage.getClassIcon(), DRAFT_RECORD_ICON_CLASS_NAME);
        Assert.assertNotEquals(textRecycleBinNotificationBeforeRestore, textRecycleBinNotificationAfterRestore);
    }

    @Test(dependsOnMethods = "testRestoreRecord")
    public void testDeleteRecordFromRecycleBin() {

        testDeleteRecord();

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickRecycleBin()
                .clickDeletedRecord(BoardViewPage::new, viewPage -> {

                    Assert.assertEquals(viewPage.getRecordInViewMode(), EXPECTED_EDITED_RECORD);

                }).clickDeletedRecordPermanently();

        Assert.assertEquals(recycleBinPage.getTextCardBody(), MESSAGE_EMPTY_RECYCLE_BIN);
    }

    @Test
    public void testSortRecords() {

        EXPECTED_CREATED_PENDING_RECORD = List.of(
                BoardBaseEditPage.FieldString.Pending.getValue(),
                TEXT_VALUE_PENDING,
                INT_VALUE_PENDING,
                DECIMAL_VALUE_PENDING,
                DATA_VALUE,
                DATA_TIME_VALUE,
                "", USER_NAME);

        EXPECTED_CREATED_ONTRACK_RECORD = List.of(
                BoardBaseEditPage.FieldString.OnTrack.getValue(),
                TEXT_VALUE_ONTRACK,
                INT_VALUE_ONTRACK,
                DECIMAL_VALUE_ONTRACK,
                DATA_VALUE,
                DATA_TIME_VALUE,
                "", USER_NAME);

        ALL_RECORDS_TABLE = Arrays.asList(EXPECTED_CREATED_PENDING_RECORD,
                EXPECTED_CREATED_ONTRACK_RECORD);

        ALL_RECORDS_TABLE.sort(Comparator.comparing(list -> list.get(1)));

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickCreateRecordButton()
                .fillAllFields(BoardBaseEditPage.FieldString.Pending,
                        TEXT_VALUE_PENDING,
                        INT_VALUE_PENDING,
                        DECIMAL_VALUE_PENDING,
                        DATA_VALUE,
                        DATA_TIME_VALUE)
                .findUser(USER_NAME)
                .clickSaveDraft()
                .clickCreateRecordButton()
                .fillAllFields(BoardBaseEditPage.FieldString.OnTrack,
                        TEXT_VALUE_ONTRACK,
                        INT_VALUE_ONTRACK,
                        DECIMAL_VALUE_ONTRACK,
                        DATA_VALUE,
                        DATA_TIME_VALUE)
                .findUser(USER_NAME)
                .clickSave()
                .clickListButton()
                .clickTextColumn();

        Assert.assertEquals(boardListPage.getRows(), ALL_RECORDS_TABLE);
    }

    @Test(dependsOnMethods = "testSortRecords")
    public void testSearchRecord() {

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .searchInputValue(BoardBaseEditPage.FieldString.Pending.getValue())
                .getTextPaginationInfo(PAGINATION_INFO_STR_1_OF_1);

        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testSearchRecord")
    public void testCleanSearchInputField() {

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .searchInputValue(BoardBaseEditPage.FieldString.Pending.getValue())
                .searchInputValue("")
                .getTextPaginationInfo(PAGINATION_INFO_STR_2_OF_2);

        Assert.assertEquals(boardListPage.getRowCount(), ALL_RECORDS_TABLE.size());
    }

    @Test(dependsOnMethods = "testSortRecords")
    public void testReorderRecords() {

        BoardOrderPage boardOrderPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickOrderButton()
                .movingRecord();

        Assert.assertEquals(boardOrderPage.getRow(0), EXPECTED_CREATED_ONTRACK_RECORD);
        Assert.assertEquals(boardOrderPage.getRow(1), EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testReorderRecords")
    public void testReorderAfterToggle() {

        final List<String> expectedOrderedPendingRecord = List.of(
                "", BoardBaseEditPage.FieldString.Pending.getValue(),
                TEXT_VALUE_PENDING,
                INT_VALUE_PENDING,
                DECIMAL_VALUE_PENDING,
                DATA_VALUE,
                DATA_TIME_VALUE,
                USER_NAME);

        final List<String> expectedOrderedOntrackRecord = List.of(
                "", BoardBaseEditPage.FieldString.OnTrack.getValue(),
                TEXT_VALUE_ONTRACK,
                INT_VALUE_ONTRACK,
                DECIMAL_VALUE_ONTRACK,
                DATA_VALUE,
                DATA_TIME_VALUE,
                USER_NAME);

        BoardOrderPage boardOrderPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickOrderButton()
                .clickToggleOrder()
                .movingBlockRecord();

        Assert.assertEquals(boardOrderPage.getOrderedRows(0), expectedOrderedPendingRecord);
        Assert.assertEquals(boardOrderPage.getOrderedRows(1), expectedOrderedOntrackRecord);
    }

    @Test
    public void testCreateNonDraftRecord() {

        EXPECTED_CREATED_PENDING_RECORD = List.of(
                BoardBaseEditPage.FieldString.Pending.getValue(),
                TEXT_VALUE_PENDING,
                INT_VALUE_PENDING,
                DECIMAL_VALUE_PENDING,
                DATA_VALUE,
                DATA_TIME_VALUE,
                "", USER_NAME);

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickCreateRecordButton()
                .fillAllFields(BoardBaseEditPage.FieldString.Pending,
                        TEXT_VALUE_PENDING,
                        INT_VALUE_PENDING,
                        DECIMAL_VALUE_PENDING,
                        DATA_VALUE,
                        DATA_TIME_VALUE)
                .findUser(USER_NAME)
                .clickSave()
                .clickListButton();

        Assert.assertEquals(boardListPage.getClassIcon(), NON_DRAFT_RECORD_ICON_CLASS_NAME);
        Assert.assertEquals(boardListPage.getRowCount(), 1);
        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testCreateNonDraftRecord")
    public void testViewNonDraftRecord() {

        testViewRecord();
    }

    @Test(dependsOnMethods = "testViewNonDraftRecord")
    public void testEditNonDraftRecord() {

        EXPECTED_EDITED_RECORD = List.of(
                BoardBaseEditPage.FieldString.OnTrack.getValue(),
                EDIT_TEXT_VALUE,
                EDIT_INT_VALUE,
                EDIT_DECIMAL_VALUE,
                EDIT_DATA_VALUE,
                EDIT_DATA_TIME_VALUE,
                "", EDIT_USER_NAME);

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .clickActions()
                .clickActionsEdit()
                .clearFields()
                .fillAllFields(BoardBaseEditPage.FieldString.OnTrack,
                        EDIT_TEXT_VALUE,
                        EDIT_INT_VALUE,
                        EDIT_DECIMAL_VALUE,
                        EDIT_DATA_VALUE,
                        EDIT_DATA_TIME_VALUE)
                .findUser(EDIT_USER_NAME)
                .clickSave();

        Assert.assertEquals(boardListPage.getRowCount(), 1);
        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_EDITED_RECORD);
    }

    @Test(dependsOnMethods = "testEditNonDraftRecord")
    public void testDeleteNonDraftRecord() {

        testDeleteRecord();
    }

    @Test
    public void testCancelRecord() {

        boolean isEmptyPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickCreateRecordButton()
                .fillAllFields(BoardBaseEditPage.FieldString.Pending,
                        TEXT_VALUE_PENDING,
                        INT_VALUE_PENDING,
                        DECIMAL_VALUE_PENDING,
                        DATA_VALUE,
                        DATA_TIME_VALUE)
                .findUser(USER_NAME)
                .clickCancel()
                .isEmptyPage();

        Assert.assertTrue(isEmptyPage);
    }

    @Test
    public void testCreateAndCheckRecords() {

        EXPECTED_CREATED_PENDING_RECORD = List.of(
                BoardBaseEditPage.FieldString.Pending.getValue(),
                TEXT_VALUE_PENDING,
                INT_VALUE_PENDING,
                DECIMAL_VALUE_PENDING,
                DATA_VALUE,
                DATA_TIME_VALUE,
                "", USER_NAME);

        BoardPage boardPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickCreateRecordButton()
                .fillAllFields(BoardBaseEditPage.FieldString.Pending,
                        TEXT_VALUE_PENDING,
                        INT_VALUE_PENDING,
                        DECIMAL_VALUE_PENDING,
                        DATA_VALUE,
                        DATA_TIME_VALUE)
                .findUser(USER_NAME)
                .clickSave();

        String createdPendingRecordToString = EXPECTED_CREATED_PENDING_RECORD.stream()
                .filter(i -> !"".equals(i)).collect(Collectors.joining(" "));

        Assert.assertEquals(boardPage.getTextPendingRecord(), createdPendingRecordToString);
    }

    @DataProvider(name = "testData")
    public static Object[][] getData() {

        return new Object[][] {
                {BoardBaseEditPage.FieldString.Pending, BoardBaseEditPage.FieldString.OnTrack},
                {BoardBaseEditPage.FieldString.OnTrack, BoardBaseEditPage.FieldString.Done},
                {BoardBaseEditPage.FieldString.Done, BoardBaseEditPage.FieldString.Pending}
        };
    }

    @Test(dataProvider = "testData")
    public void testDragAndDropRecords(BoardBaseEditPage.FieldString fromBox, BoardBaseEditPage.FieldString toBox) {

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickCreateRecordButton()
                .fillAllFields(fromBox,
                        TEXT_VALUE_PENDING,
                        INT_VALUE_PENDING,
                        DECIMAL_VALUE_PENDING,
                        DATA_VALUE,
                        DATA_TIME_VALUE)
                .findUser(USER_NAME)
                .clickSave()
                .moveElement(fromBox.getValue(), toBox.getValue())
                .clickListButton();

        Assert.assertEquals(boardListPage.getRow(0).get(0), toBox.getValue());
    }
}
