import base.BaseTest;
import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class EntityImportValuesTest extends BaseTest {

    private static final String STRING_INPUT_VALUE = "Some string";
    private static final String TEXT_INPUT_VALUE = "New text.";
    private static final String INT_INPUT_VALUE = "457";
    private static final String DECIMAL_INPUT_VALUE = "27.35";
    private static final String DATE_INPUT_VALUE = "01/06/2021";
    private static final String DATETIME_INPUT_VALUE = "01/06/2021 13:07:06";
    private static final String USERNAME_INPUT_VALUE = "apptester1@tester.test";
    private static final String FILE_INPUT_VALUE = "";
    private static final String STRING_EDIT_VALUE = "New string";
    private static final String TEXT_EDIT_VALUE = "Import values text.";
    private static final String INT_EDIT_VALUE = "12";
    private static final String DECIMAL_EDIT_VALUE = "0.20";
    private static final String DATE_EDIT_VALUE = "18/07/2021";
    private static final String DATETIME_EDIT_VALUE = "18/07/2021 17:07:07";
    private static final String DRAFT_RECORD_ICON = "fa fa-pencil";
    private static final String EMPTY_RECYCLE_BIN_MESSAGE = "Good job with housekeeping! Recycle bin is currently empty!";
    private static final String SEARCH_INPUT_VALUE = "Som";
    private static final String PAGINATION_INFO_TEXT = "Showing 1 to 1 of 1 rows";

    private final static List<String> EXPECTED_VALUES = Arrays.asList(
            STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
            DATE_INPUT_VALUE, DATETIME_INPUT_VALUE, FILE_INPUT_VALUE, USERNAME_INPUT_VALUE);

    private final static List<String> EXPECTED_EDIT_VALUES = Arrays.asList(
            STRING_EDIT_VALUE, TEXT_EDIT_VALUE, INT_EDIT_VALUE, DECIMAL_EDIT_VALUE,
            DATE_EDIT_VALUE, DATETIME_EDIT_VALUE, FILE_INPUT_VALUE, USERNAME_INPUT_VALUE);

    @Test
    public void testCreateDraftRecord() {

        ImportValuesPage importValuesPage = new MainPage(getDriver())
                .clickImportValuesMenu()
                .clickCreateRecordButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .fillDate(DATE_INPUT_VALUE)
                .fillDateTime(DATETIME_INPUT_VALUE)
                .clickSaveDraft();

        Assert.assertEquals(importValuesPage.getClassIcon(), DRAFT_RECORD_ICON);
        Assert.assertEquals(importValuesPage.getRowCount(), 1);
        Assert.assertEquals(importValuesPage.getRow(0), EXPECTED_VALUES);
    }

    @Test
    public void testCancelRecord() {

        ImportValuesPage importValuesPage = new MainPage(getDriver())
                .clickImportValuesMenu()
                .clickCreateRecordButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .fillDate(DATE_INPUT_VALUE)
                .fillDateTime(DATETIME_INPUT_VALUE)
                .clickCancel();

        Assert.assertTrue(importValuesPage.isTableEmpty());
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testViewDraftRecord() {

        ImportValuesViewPage importValuesViewPage = new MainPage(getDriver())
                .clickImportValuesMenu()
                .clickActions()
                .clickActionsView();

        Assert.assertEquals(importValuesViewPage.getRecordInViewMode(), EXPECTED_VALUES);
    }

    @Test(dependsOnMethods = "testViewDraftRecord")
    public void testEditDraftRecord() {

        ImportValuesPage importValuesPage = new MainPage(getDriver())
                .clickImportValuesMenu()
                .clickActions()
                .clickActionsEdit()
                .fillString(STRING_EDIT_VALUE)
                .fillText(TEXT_EDIT_VALUE)
                .fillInt(INT_EDIT_VALUE)
                .fillDecimal(DECIMAL_EDIT_VALUE)
                .fillDate(DATE_EDIT_VALUE)
                .fillDateTime(DATETIME_EDIT_VALUE)
                .clickSaveDraft();

        Assert.assertEquals(importValuesPage.getClassIcon(), DRAFT_RECORD_ICON);
        Assert.assertEquals(importValuesPage.getRow(0), EXPECTED_EDIT_VALUES);
    }

    @Test(dependsOnMethods = "testEditDraftRecord")
    public void testDeleteDraftRecord() {

        ImportValuesPage importValuesPage = new MainPage(getDriver())
                .clickImportValuesMenu()
                .clickActions()
                .clickActionsDelete();

        Assert.assertTrue(importValuesPage.isTableEmpty());
        Assert.assertEquals(importValuesPage.getTextNotificationRecycleBin(), 1);
    }

    @Test(dependsOnMethods = "testDeleteDraftRecord")
    public void testDeleteDraftRecordFromRecycleBin() {

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickRecycleBin()
                .clickDeletedRecord(ImportValuesViewPage::new, viewPage -> {

                    Assert.assertEquals(viewPage.getRecordInViewMode(), EXPECTED_EDIT_VALUES);

                }).clickDeletedRecordPermanently();

        Assert.assertEquals(recycleBinPage.getTextCardBody(), EMPTY_RECYCLE_BIN_MESSAGE);
    }

    @Test
    public void testSearchRecord() {

        ImportValuesPage importValuesPage = new MainPage(getDriver())
                .clickImportValuesMenu()
                .clickCreateRecordButton()
                .fillString(STRING_INPUT_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .fillDate(DATE_INPUT_VALUE)
                .fillDateTime(DATETIME_INPUT_VALUE)
                .clickSaveDraft();

        importValuesPage.clickCreateRecordButton()
                .fillString(STRING_EDIT_VALUE)
                .fillText(TEXT_EDIT_VALUE)
                .fillInt(INT_EDIT_VALUE)
                .fillDecimal(DECIMAL_EDIT_VALUE)
                .fillDate(DATE_EDIT_VALUE)
                .fillDateTime(DATETIME_EDIT_VALUE)
                .clickSave();

        importValuesPage.clickImportValuesMenu()
                .searchInput(SEARCH_INPUT_VALUE)
                .getPaginationInfo(PAGINATION_INFO_TEXT);

        Assert.assertEquals(importValuesPage.getRowCount(), 1);
        Assert.assertEquals(importValuesPage.getRow(0), EXPECTED_VALUES);
    }

    @Test(dependsOnMethods = "testSearchRecord")
    public void testSortRecords() {

        ImportValuesPage importValuesPage = new MainPage(getDriver())
                .clickImportValuesMenu();

        Assert.assertEquals(importValuesPage.getRowCount(), 2);
        Assert.assertEquals(importValuesPage.getRow(0), EXPECTED_VALUES);

        importValuesPage.clickSortText();

        Assert.assertEquals(importValuesPage.getRow(0), EXPECTED_EDIT_VALUES);
    }
}
