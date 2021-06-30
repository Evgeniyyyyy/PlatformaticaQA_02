import base.BaseTest;
import model.ExportDestinationPage;
import model.ExportDestinationViewPage;
import model.MainPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class EntityExportDestinationTest extends BaseTest {

    private static final String STRING_VALUE = "Some string";
    private static final String TEXT_VALUE = "Export destination text.";
    private static final String INT_VALUE = "457";
    private static final String DECIMAL_VALUE = "27.35";
    private static final String DATE_VALUE = "01/06/2021";
    private static final String DATETIME_VALUE = "01/06/2021 13:07:06";
    private static final String USERNAME_VALUE = "apptester1@tester.test";
    private static final String FILE_VALUE = "";
    private static final String CHECK_SQUARE_ICON = "fa fa-check-square-o";
    private static final String PENCIL_ICON = "fa fa-pencil";
    private static final String STRING_EDIT_VALUE = "New string";
    private static final String TEXT_EDIT_VALUE = "New text.";
    private static final String INT_EDIT_VALUE = "12";
    private static final String DECIMAL_EDIT_VALUE = "0.20";
    private static final String DATE_EDIT_VALUE = "18/07/2021";
    private static final String DATETIME_EDIT_VALUE = "18/07/2021 17:07:07";
    private static final String SEARCH_INPUT = "Som";
    private static final String TEXT_INFORMATION = "Showing 1 to 1 of 1 rows";

    private final static List<String> EXPECTED_VALUES = Arrays.asList(
            STRING_VALUE, TEXT_VALUE, INT_VALUE, DECIMAL_VALUE,
            DATE_VALUE, DATETIME_VALUE, FILE_VALUE, USERNAME_VALUE);

    private final static List<String> EXPECTED_EDIT_VALUES = Arrays.asList(
            STRING_EDIT_VALUE, TEXT_EDIT_VALUE, INT_EDIT_VALUE, DECIMAL_EDIT_VALUE,
            DATE_EDIT_VALUE, DATETIME_EDIT_VALUE, FILE_VALUE, USERNAME_VALUE);

    @Test
    public void testCreateRecord() {

        ExportDestinationPage exportDestinationPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickNewButton()
                .fillString(STRING_VALUE)
                .fillText(TEXT_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .fillDate(DATE_VALUE)
                .fillDateTime(DATETIME_VALUE)
                .clickSave();

        Assert.assertEquals(exportDestinationPage.getClassIcon(), CHECK_SQUARE_ICON);
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

    @Test
    public void testCreateDraftRecord() {
        ExportDestinationPage exportDestinationPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickNewButton()
                .fillString(STRING_VALUE)
                .fillText(TEXT_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .fillDate(DATE_VALUE)
                .fillDateTime(DATETIME_VALUE)
                .clickSaveDraft();
        Assert.assertEquals(exportDestinationPage.getClassIcon(), PENCIL_ICON);
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

        Assert.assertEquals(exportDestinationPage.getClassIcon(), PENCIL_ICON);
        Assert.assertEquals(exportDestinationPage.getRow(0), EXPECTED_EDIT_VALUES);
    }

    @Test
    public void testSearchRecord() {
        ExportDestinationPage exportDestinationPage = new MainPage(getDriver())
                .clickExportDestinationMenu()
                .clickNewButton()
                .fillString(STRING_VALUE)
                .fillText(TEXT_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .fillDate(DATE_VALUE)
                .fillDateTime(DATETIME_VALUE)
                .clickSaveDraft();

        exportDestinationPage.clickNewButton()
                .fillString(STRING_EDIT_VALUE)
                .fillText(TEXT_EDIT_VALUE)
                .fillInt(INT_EDIT_VALUE)
                .fillDecimal(DECIMAL_EDIT_VALUE)
                .fillDate(DATE_EDIT_VALUE)
                .fillDateTime(DATETIME_EDIT_VALUE)
                .clickSave();

        exportDestinationPage.clickExportDestinationMenu()
                .searchInput(SEARCH_INPUT)
                .findTextInfo(TEXT_INFORMATION);

        Assert.assertEquals(exportDestinationPage.getRowCount(), 1);
        Assert.assertEquals(exportDestinationPage.getRow(0), EXPECTED_VALUES);
    }
}
