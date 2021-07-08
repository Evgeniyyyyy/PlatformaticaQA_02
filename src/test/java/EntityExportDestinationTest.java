import base.BaseTest;
import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class EntityExportDestinationTest extends BaseTest {

    private static final String STRING_INPUT_VALUE = "Some string";
    private static final String TEXT_INPUT_VALUE = "New text.";
    private static final String INT_INPUT_VALUE = "457";
    private static final String DECIMAL_INPUT_VALUE = "27.35";
    private static final String DATE_INPUT_VALUE = "01/06/2021";
    private static final String DATETIME_INPUT_VALUE = "01/06/2021 13:07:06";
    private static final String USERNAME_INPUT_VALUE = "apptester1@tester.test";
    private static final String FILE_INPUT_VALUE = "";
    private static final String SAVE_RECORD_ICON = "fa fa-check-square-o";
    private static final String SAVE_DRAFT_RECORD_ICON = "fa fa-pencil";
    private static final String STRING_EDIT_VALUE = "New string";
    private static final String TEXT_EDIT_VALUE = "Input text.";
    private static final String INT_EDIT_VALUE = "12";
    private static final String DECIMAL_EDIT_VALUE = "0.20";
    private static final String DATE_EDIT_VALUE = "18/07/2021";
    private static final String DATETIME_EDIT_VALUE = "18/07/2021 17:07:07";
    private static final String SEARCH_INPUT_VALUE = "Som";
    private static final String PAGINATION_INFO_TEXT = "Showing 1 to 1 of 1 rows";
    private static final String EMPTY_RECYCLE_BIN_MESSAGE = "Good job with housekeeping! Recycle bin is currently empty!";
    private static final String ENTITY_NAME = "Export destination";

    private final static List<String> EXPECTED_VALUES = Arrays.asList(
            STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
            DATE_INPUT_VALUE, DATETIME_INPUT_VALUE, FILE_INPUT_VALUE, USERNAME_INPUT_VALUE);

    private final static List<String> EXPECTED_EDIT_VALUES = Arrays.asList(
            STRING_EDIT_VALUE, TEXT_EDIT_VALUE, INT_EDIT_VALUE, DECIMAL_EDIT_VALUE,
            DATE_EDIT_VALUE, DATETIME_EDIT_VALUE, FILE_INPUT_VALUE, USERNAME_INPUT_VALUE);

    @Test
    public void testCreateRecord() {

        ExportDestinationPage exportDestinationPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickCreateRecordButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .fillDate(DATE_INPUT_VALUE)
                .fillDateTime(DATETIME_INPUT_VALUE)
                .clickSave();

        Assert.assertEquals(exportDestinationPage.getClassIcon(), SAVE_RECORD_ICON);
        Assert.assertEquals(exportDestinationPage.getRowCount(), 1);
        Assert.assertEquals(exportDestinationPage.getRow(0), EXPECTED_VALUES);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {

        ExportDestinationViewPage exportDestinationViewPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickActions()
                .clickActionsView();

        Assert.assertEquals(exportDestinationViewPage.getRecordInViewMode(), EXPECTED_VALUES);
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testCloseViewWindow() {

        ExportDestinationPage exportDestinationPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickActions()
                .clickActionsView()
                .closeViewWindow();

        Assert.assertTrue(exportDestinationPage.getRowCount() > 0);
    }

    @Test(dependsOnMethods = "testCloseViewWindow")
    public void testDeleteRecord() {

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickActions()
                .clickActionsDelete()
                .clickRecycleBin();

        Assert.assertEquals(recycleBinPage.getRowCount(), 1);
        Assert.assertEquals(RecycleBinPage.getEntityName(), ENTITY_NAME);
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testVerifyDeletedRecord() {

        ExportDestinationViewPage exportDestinationViewPage = new MainPage(getDriver())
                .clickRecycleBin()
                .clickDeletedRecordRow();

        Assert.assertEquals(exportDestinationViewPage.getRecordInViewMode(), EXPECTED_VALUES);
    }

    @Test
    public void testCancelRecord() {

        ExportDestinationPage exportDestinationPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickCreateRecordButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .fillDate(DATE_INPUT_VALUE)
                .fillDateTime(DATETIME_INPUT_VALUE)
                .clickCancel();

        Assert.assertEquals(exportDestinationPage.getRowCount(), 0);
    }

    @Test
    public void testCreateDraftRecord() {

        ExportDestinationPage exportDestinationPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickCreateRecordButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .fillDate(DATE_INPUT_VALUE)
                .fillDateTime(DATETIME_INPUT_VALUE)
                .clickSaveDraft();
        Assert.assertEquals(exportDestinationPage.getClassIcon(), SAVE_DRAFT_RECORD_ICON);
        Assert.assertEquals(exportDestinationPage.getRowCount(), 1);
        Assert.assertEquals(exportDestinationPage.getRow(0), EXPECTED_VALUES);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testViewDraftRecord() {

        ExportDestinationViewPage exportDestinationViewPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickActions()
                .clickActionsView();

        Assert.assertEquals(exportDestinationViewPage.getRecordInViewMode(), EXPECTED_VALUES);
    }

    @Test(dependsOnMethods = "testViewDraftRecord")
    public void testEditDraftRecord() {

        ExportDestinationPage exportDestinationPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickActions()
                .clickActionsEdit()
                .fillString(STRING_EDIT_VALUE)
                .fillText(TEXT_EDIT_VALUE)
                .fillInt(INT_EDIT_VALUE)
                .fillDecimal(DECIMAL_EDIT_VALUE)
                .fillDate(DATE_EDIT_VALUE)
                .fillDateTime(DATETIME_EDIT_VALUE)
                .clickSaveDraft();

        Assert.assertEquals(exportDestinationPage.getClassIcon(), SAVE_DRAFT_RECORD_ICON);
        Assert.assertEquals(exportDestinationPage.getRow(0), EXPECTED_EDIT_VALUES);
    }

    @Test(dependsOnMethods = "testEditDraftRecord")
    public void testDeleteDraftRecord() {

        ExportDestinationPage exportDestinationPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickActions()
                .clickActionsDelete();

        Assert.assertTrue(exportDestinationPage.isTableEmpty());
        Assert.assertEquals(exportDestinationPage.getTextNotificationRecycleBin(), 1);
    }

    @Test(dependsOnMethods = "testDeleteDraftRecord")
    public void testDeleteDraftRecordFromRecycleBin() {

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickRecycleBin()
                .clickDeletedRecord(ExportDestinationViewPage::new, viewPage -> {

                    Assert.assertEquals(viewPage.getRecordInViewMode(), EXPECTED_EDIT_VALUES);
                }).clickDeletedRecordPermanently();

        Assert.assertEquals(recycleBinPage.getTextCardBody(), EMPTY_RECYCLE_BIN_MESSAGE);
    }

    @Test
    public void testSearchRecord() {

        ExportDestinationPage exportDestinationPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickCreateRecordButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .fillDate(DATE_INPUT_VALUE)
                .fillDateTime(DATETIME_INPUT_VALUE)
                .clickSaveDraft();

        exportDestinationPage.clickCreateRecordButton()
                .fillString(STRING_EDIT_VALUE)
                .fillText(TEXT_EDIT_VALUE)
                .fillInt(INT_EDIT_VALUE)
                .fillDecimal(DECIMAL_EDIT_VALUE)
                .fillDate(DATE_EDIT_VALUE)
                .fillDateTime(DATETIME_EDIT_VALUE)
                .clickSave();

        exportDestinationPage.clickExportDestinationMenu()
                .searchInput(SEARCH_INPUT_VALUE)
                .findTextInfo(PAGINATION_INFO_TEXT);

        Assert.assertEquals(exportDestinationPage.getRowCount(), 1);
        Assert.assertEquals(exportDestinationPage.getRow(0), EXPECTED_VALUES);
    }

    @Test(dependsOnMethods = "testSearchRecord")
    public void testSortRecords() {

        ExportDestinationPage exportDestinationPage = new MainPage(getDriver())
                .clickExportDestinationMenu();

        Assert.assertEquals(exportDestinationPage.getRowCount(), 2);
        Assert.assertEquals(exportDestinationPage.getRow(0), EXPECTED_VALUES);

        exportDestinationPage.clickSortText();

        Assert.assertEquals(exportDestinationPage.getRow(0), EXPECTED_EDIT_VALUES);
    }

    @Test(dependsOnMethods = "testSortRecords")
    public void testReorderRecords() {

        ExportDestinationOrderPage exportDestinationOrderPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickOrderButton();

        Assert.assertEquals(exportDestinationOrderPage.getRow(0), EXPECTED_VALUES);

                exportDestinationOrderPage.movingRecord();

        Assert.assertEquals(exportDestinationOrderPage.getRow(0), EXPECTED_EDIT_VALUES);
    }

    @Test(dependsOnMethods = "testReorderRecords")
    public void testReorderAfterToggle() {

        final List<String> EXPECTED_VIEW_VALUES = Arrays.asList(
                "", STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE,
                DECIMAL_INPUT_VALUE, DATE_INPUT_VALUE, DATETIME_INPUT_VALUE, USERNAME_INPUT_VALUE);

        final List<String> EXPECTED_EDIT_VIEW_VALUES = Arrays.asList(
                "", STRING_EDIT_VALUE, TEXT_EDIT_VALUE, INT_EDIT_VALUE,
                DECIMAL_EDIT_VALUE, DATE_EDIT_VALUE, DATETIME_EDIT_VALUE, USERNAME_INPUT_VALUE);

        ExportDestinationOrderPage exportDestinationOrderPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickOrderButton()
                .clickToggleOrder()
                .movingBlockRecord();

        Assert.assertEquals(exportDestinationOrderPage.getOrderedRows(0), EXPECTED_VIEW_VALUES);
        Assert.assertEquals(exportDestinationOrderPage.getOrderedRows(1), EXPECTED_EDIT_VIEW_VALUES);
    }
}
