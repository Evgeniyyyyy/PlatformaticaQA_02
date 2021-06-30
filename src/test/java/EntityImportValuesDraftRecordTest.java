import base.BaseTest;
import model.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class EntityImportValuesDraftRecordTest extends BaseTest {

    private static final String STRING_INPUT = "Some string";
    private static final String TEXT_INPUT = "New text.";
    private static final String INT_INPUT = "457";
    private static final String DECIMAL_INPUT = "27.35";
    private static final String DATE_INPUT = "01/06/2021";
    private static final String DATETIME_INPUT = "01/06/2021 13:07:06";
    private static final String USERNAME_INPUT = "apptester1@tester.test";
    private static final String FILE_INPUT = "";
    private static final String STRING_INPUT2 = "New string";
    private static final String TEXT_INPUT2 = "Import values text.";
    private static final String INT_INPUT2 = "12";
    private static final String DECIMAL_INPUT2 = "0.20";
    private static final String DATE_INPUT2 = "18/07/2021";
    private static final String DATETIME_INPUT2 = "18/07/2021 17:07:07";
    private static final String PENCIL_ICON = "fa fa-pencil";
    private static final String EMPTY_RECYCLE_BIN_MESSAGE = "Good job with housekeeping! Recycle bin is currently empty!";
    private static final String SEARCH_INPUT = "Som";
    private static final String TEXT_INFORMATION = "Showing 1 to 1 of 1 rows";

    private final static List<String> EXPECTED_VALUES = Arrays.asList(STRING_INPUT, TEXT_INPUT,
            INT_INPUT, DECIMAL_INPUT, DATE_INPUT, DATETIME_INPUT, FILE_INPUT, USERNAME_INPUT);

    private final static List<String> EXPECTED_VALUES2 = Arrays.asList(STRING_INPUT2, TEXT_INPUT2,
            INT_INPUT2, DECIMAL_INPUT2, DATE_INPUT2, DATETIME_INPUT2, FILE_INPUT, USERNAME_INPUT);

    @Test
    public void testCreateDraftRecord() {

        ImportValuesPage importValuesPage = new MainPage(getDriver())
                .clickImportValuesMenu()
                .clickNewButton()
                .fillString(STRING_INPUT)
                .fillText(TEXT_INPUT)
                .fillInt(INT_INPUT)
                .fillDecimal(DECIMAL_INPUT)
                .fillDate(DATE_INPUT)
                .fillDateTime(DATETIME_INPUT)
                .clickSaveDraft();

        Assert.assertEquals(importValuesPage.getClassIcon(), PENCIL_ICON);
        Assert.assertEquals(importValuesPage.getRow(0), EXPECTED_VALUES);
    }

    @Test
    public void testCancelRecord() {

        ImportValuesPage importValuesPage = new MainPage(getDriver())
                .clickImportValuesMenu()
                .clickNewButton()
                .fillString(STRING_INPUT)
                .fillText(TEXT_INPUT)
                .fillInt(INT_INPUT)
                .fillDecimal(DECIMAL_INPUT)
                .fillDate(DATE_INPUT)
                .fillDateTime(DATETIME_INPUT)
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
                .fillString(STRING_INPUT2)
                .fillText(TEXT_INPUT2)
                .fillInt(INT_INPUT2)
                .fillDecimal(DECIMAL_INPUT2)
                .fillDate(DATE_INPUT2)
                .fillDateTime(DATETIME_INPUT2)
                .clickSaveDraft();

        Assert.assertEquals(importValuesPage.getClassIcon(), PENCIL_ICON);
        Assert.assertEquals(importValuesPage.getRow(0), EXPECTED_VALUES2);
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
                .clickDeletedRecord(BoardViewPage::new, viewPage -> {

                    Assert.assertEquals(viewPage.getRecordInViewMode(), EXPECTED_VALUES2);

                }).clickDeletedRecordPermanently();

        Assert.assertEquals(recycleBinPage.getTextCardBody(), EMPTY_RECYCLE_BIN_MESSAGE);
    }

    @Test
    public void testSearchRecord() {

        ImportValuesPage importValuesPage = new MainPage(getDriver())
                .clickImportValuesMenu()
                .clickNewButton()
                .fillString(STRING_INPUT)
                .fillText(TEXT_INPUT)
                .fillInt(INT_INPUT)
                .fillDecimal(DECIMAL_INPUT)
                .fillDate(DATE_INPUT)
                .fillDateTime(DATETIME_INPUT)
                .clickSaveDraft();

        importValuesPage.clickNewButton()
                .fillString(STRING_INPUT2)
                .fillText(TEXT_INPUT2)
                .fillInt(INT_INPUT2)
                .fillDecimal(DECIMAL_INPUT2)
                .fillDate(DATE_INPUT2)
                .fillDateTime(DATETIME_INPUT2)
                .clickSave();

        importValuesPage.clickImportValuesMenu()
                .searchInput(SEARCH_INPUT)
                .findTextInfo(TEXT_INFORMATION);

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

        Assert.assertEquals(importValuesPage.getRow(0), EXPECTED_VALUES2);
    }
}
