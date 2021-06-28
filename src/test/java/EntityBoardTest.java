import base.BaseTest;

import model.*;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

import static utils.ProjectUtils.*;

public class EntityBoardTest extends BaseTest {

    private static final String STRING_INPUT_PENDING = "Pending";
    private static final String STRING_INPUT_ONTRACK = "On track";
    private static final String DRAFT_RECORD_ICON_CLASS_NAME = "fa fa-pencil";
    private static final String NON_DRAFT_RECORD_ICON_CLASS_NAME = "fa fa-check-square-o";
    private static final String PAGINATION_INFO_STR_1_OF_1 = "Showing 1 to 1 of 1 rows";
    private static final String PAGINATION_INFO_STR_2_OF_2 = "Showing 1 to 2 of 2 rows";
    private static final String MESSAGE_EMPTY_RECYCLE_BIN = "Good job with housekeeping! Recycle bin is currently empty!";

    private static final String TEXT_VALUE_PENDING = getTextRandom(8);
    private static final String INT_VALUE_PENDING = getIntRandom();
    private static final String DECIMAL_VALUE_PENDING = getRandomDecimalValue();
    private static final String TEXT_VALUE_ONTRACK = getTextRandom(8);
    private static final String INT_VALUE_ONTRACK = getIntRandom();
    private static final String DECIMAL_VALUE_ONTRACK = getRandomDecimalValue();
    private static final String USER_NAME = getUser();
    private static final String EDIT_TEXT_VALUE = "QWERTY";
    private static final String EDIT_INT_VALUE = "12345";
    private static final String EDIT_DECIMAL_VALUE = "33.55";

    private static final List<String> EXPECTED_CREATED_PENDING_RECORD = List.of(
            STRING_INPUT_PENDING,
            TEXT_VALUE_PENDING,
            INT_VALUE_PENDING,
            DECIMAL_VALUE_PENDING,
            "", "", "", USER_NAME);

    private static final List<String> EXPECTED_EDITED_PENDING_RECORD = List.of(
            STRING_INPUT_ONTRACK,
            TEXT_VALUE_PENDING,
            INT_VALUE_PENDING,
            DECIMAL_VALUE_PENDING,
            "", "", "", USER_NAME);

    private static final List<String> EXPECTED_EDITED_RECORD = List.of(
            STRING_INPUT_ONTRACK,
            EDIT_TEXT_VALUE,
            EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE,
            "", "", "", USER_NAME);

    private static final List<String> EXPECTED_CREATED_ONTRACK_RECORD = List.of(
            STRING_INPUT_ONTRACK,
            TEXT_VALUE_ONTRACK,
            INT_VALUE_ONTRACK,
            DECIMAL_VALUE_ONTRACK,
            "", "", "", USER_NAME);

    private static final List<String> EXPECTED_EDITED_ONTRACK_RECORD = List.of(
            STRING_INPUT_PENDING,
            TEXT_VALUE_ONTRACK,
            INT_VALUE_ONTRACK,
            DECIMAL_VALUE_ONTRACK,
            "", "", "", USER_NAME);

    private static final List<String> EXPECTED_ORDERED_PENDING_RECORD = List.of(
            "", STRING_INPUT_PENDING,
            TEXT_VALUE_PENDING,
            INT_VALUE_PENDING,
            DECIMAL_VALUE_PENDING,
            "", "", USER_NAME);

    private static final List<String> EXPECTED_ORDERED_ONTRACK_RECORD = List.of(
            "", STRING_INPUT_ONTRACK,
            TEXT_VALUE_ONTRACK,
            INT_VALUE_ONTRACK,
            DECIMAL_VALUE_ONTRACK,
            "", "", USER_NAME);

    private static final List<List<String>> ALL_RECORDS_TABLE = new ArrayList<>(List.of(EXPECTED_CREATED_PENDING_RECORD,
            EXPECTED_CREATED_ONTRACK_RECORD));

    private static String getRandomDecimalValue() {
        return RandomUtils.nextInt(0, 10000) + "." + RandomStringUtils.randomNumeric(2);
    }

    private String getStringInputRecord(List<String> list) {
        String stringInputRecord = "";

        for (String s : list) {
            if (!s.equals("")) {
                stringInputRecord += s + " ";
            }
        }
        return stringInputRecord.trim();
    }

    static class CompareByText implements Comparator<List<String>> {

        @Override
        public int compare(List r1, List r2) {
            String text1 = r1.get(1).toString();
            String text2 = r2.get(1).toString();
            return text1.compareTo(text2);
        }
    }

    @Test
    public void testCreateDraftRecord() {

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickNewButton()
                .fillFields(EXPECTED_CREATED_PENDING_RECORD)
                .findUser(USER_NAME)
                .clickSaveDraft()
                .clickListButton();

        Assert.assertEquals(boardListPage.getRowCount(), 1);
        Assert.assertEquals(boardListPage.getClassIcon(), DRAFT_RECORD_ICON_CLASS_NAME);
        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testView() {

        BoardViewPage boardViewPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .clickActions()
                .clickActionsView();

        Assert.assertEquals(boardViewPage.getRecordInViewMode(), EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testView")
    public void testEditDraftRecord() {

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .clickActions()
                .clickActionsEdit()
                .clearFields()
                .fillFields(EntityBoardTest.EXPECTED_EDITED_RECORD)
                .clickSaveDraft()
                .clickListButton();

        Assert.assertEquals(boardListPage.getRowCount(), 1);
        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_EDITED_RECORD);
    }

    @Test(dependsOnMethods = "testEditDraftRecord")
    public void testDelete() {

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

    @Test(dependsOnMethods = "testDelete")
    public void testRestoreDraftRecord() {

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

    @Test(dependsOnMethods = "testRestoreDraftRecord")
    public void testDeleteRecordFromRecycleBin() {

        testDelete();

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickRecycleBin()
                .clickDeletedRecord(BoardViewPage::new, viewPage -> {

                    Assert.assertEquals(viewPage.getRecordInViewMode(), EXPECTED_EDITED_RECORD);

                }).clickDeletedRecordPermanently();

        Assert.assertEquals(recycleBinPage.getTextCardBody(), MESSAGE_EMPTY_RECYCLE_BIN);
    }

    @Test
    public void testSortRecords() {

        ALL_RECORDS_TABLE.sort(new CompareByText());

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickNewButton()
                .fillFields(EXPECTED_CREATED_PENDING_RECORD)
                .findUser(USER_NAME)
                .clickSaveDraft()
                .clickNewButton()
                .fillFields(EXPECTED_CREATED_ONTRACK_RECORD)
                .findUser(USER_NAME)
                .clickSave()
                .clickListButton();

        Assert.assertEquals(boardListPage.getRowCount(), ALL_RECORDS_TABLE.size());

        boardListPage.clickTextColumn();
        Assert.assertEquals(boardListPage.getRow(0), ALL_RECORDS_TABLE.get(0));
    }

    @Test(dependsOnMethods = "testSortRecords")
    public void testSearch() {

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .searchInputValue(STRING_INPUT_PENDING)
                .getTextPaginationInfo(PAGINATION_INFO_STR_1_OF_1);

        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_CREATED_PENDING_RECORD);

        boardListPage.searchInputValue("")
                .getTextPaginationInfo(PAGINATION_INFO_STR_2_OF_2);
        Assert.assertEquals(boardListPage.getRowCount(), ALL_RECORDS_TABLE.size());
    }

    @Test(dependsOnMethods = "testSortRecords")
    public void testReorderRecords() {

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickOrderButton();

        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_CREATED_PENDING_RECORD);
        Assert.assertEquals(boardListPage.getRow(1), EXPECTED_CREATED_ONTRACK_RECORD);

        boardListPage.getReorder(20);

        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_CREATED_ONTRACK_RECORD);
        Assert.assertEquals(boardListPage.getRow(1), EXPECTED_CREATED_PENDING_RECORD);

        boardListPage.clickToggle()
                .getReorder(140);

        Assert.assertEquals(boardListPage.getOrderToggleRow(0), EXPECTED_ORDERED_PENDING_RECORD);
        Assert.assertEquals(boardListPage.getOrderToggleRow(1), EXPECTED_ORDERED_ONTRACK_RECORD);
    }

    @Test
    public void testCreateRecord() {

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickNewButton()
                .fillFields(EXPECTED_CREATED_PENDING_RECORD)
                .findUser(USER_NAME)
                .clickSave()
                .clickListButton();

        Assert.assertEquals(boardListPage.getRowCount(), 1);
        Assert.assertEquals(boardListPage.getClassIcon(), NON_DRAFT_RECORD_ICON_CLASS_NAME);
        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {

        testView();
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testEditRecord() {

        BoardListPage boardListPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickListButton()
                .clickActions()
                .clickActionsEdit()
                .clearFields()
                .fillFields(EntityBoardTest.EXPECTED_EDITED_RECORD)
                .clickSave()
                .clickListButton();

        Assert.assertEquals(boardListPage.getRowCount(), 1);
        Assert.assertEquals(boardListPage.getRow(0), EXPECTED_EDITED_RECORD);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {

        testDelete();
    }

    @Test
    public void testCancelRecord() {

        BoardPage boardPage = new MainPage(getDriver())
                .clickBoardMenu();

        String textCardBodyBeforeCancel = boardPage.getTextCardBody();

        boardPage
                .clickNewButton()
                .fillFields(EXPECTED_CREATED_PENDING_RECORD)
                .findUser(USER_NAME)
                .clickCancel();

        Assert.assertEquals(boardPage.getTextCardBody(), textCardBodyBeforeCancel);
    }

    @Test
    public void testDragAndDropRecords() {

        BoardPage boardPage = new MainPage(getDriver())
                .clickBoardMenu()
                .clickNewButton()
                .fillFields(EXPECTED_CREATED_PENDING_RECORD)
                .findUser(USER_NAME)
                .clickSaveDraft()
                .clickNewButton()
                .fillFields(EXPECTED_CREATED_ONTRACK_RECORD)
                .findUser(USER_NAME)
                .clickSave();

        final String pendingRecord = getStringInputRecord(EXPECTED_CREATED_PENDING_RECORD);
        final String onTrackRecord = getStringInputRecord(EXPECTED_CREATED_ONTRACK_RECORD);

        Assert.assertEquals(boardPage.getTextPendingRecord(), pendingRecord);
        Assert.assertEquals(boardPage.getTextOnTrackRecord(), onTrackRecord);

        boardPage.movePendingToOnTrack();
        boardPage.moveOnTrackToPending();
        boardPage.clickListButton();

        Assert.assertEquals(boardPage.getRow(0), EXPECTED_EDITED_PENDING_RECORD);
        Assert.assertEquals(boardPage.getRow(1), EXPECTED_EDITED_ONTRACK_RECORD);
    }
}
