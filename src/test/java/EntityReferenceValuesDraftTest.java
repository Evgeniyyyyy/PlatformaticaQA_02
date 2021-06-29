import base.BaseTest;
import model.MainPage;
import model.ReferenceValuesPage1;
import model.ReferenceValuesViewPage1;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;


public class EntityReferenceValuesDraftTest extends BaseTest {

    private static final String LABEL_INPUT_VALUE = "label";
    private static final String FILTER_1_INPUT_VALUE = "Filter_1";
    private static final String FILTER_2_INPUT_VALUE = "Filter_2";
    private static final String ICON_SAVE = "fa fa-check-square-o";
    private static final String ICON_SAVE_DRAFT = "fa fa-pencil";
    private static final String EMPTY_FIELD = "";
    private static final String EDIT_LABEL_VALUE = "TEST_1";
    private static final String EDIT_FILTER_1_VALUE = "TEST_2";
    private static final String EDIT_FILTER_2_VALUE = "TEST_3";


    private static final List<String> EXPECTED_RESULT = List.of(
            LABEL_INPUT_VALUE, FILTER_1_INPUT_VALUE, FILTER_2_INPUT_VALUE);

    private static final List<String> EXPECTED_RESULT_1 = List.of(
            FILTER_1_INPUT_VALUE, FILTER_2_INPUT_VALUE, EMPTY_FIELD);

    private static final List<String> CARD_VIEW_RESULT = List.of(EMPTY_FIELD,
            EDIT_LABEL_VALUE, EDIT_FILTER_1_VALUE, EDIT_FILTER_2_VALUE);

    private static final List<String> EDIT_RESULT = List.of(
            EDIT_LABEL_VALUE, EDIT_FILTER_1_VALUE, EDIT_FILTER_2_VALUE);


    @Test
    public void testCreateRecord() {

        ReferenceValuesPage1 referenceValuesPage1 = new MainPage(getDriver())
                .clickReferenceValuesMenu()
                .clickNewButton()
                .fillLabel(LABEL_INPUT_VALUE)
                .fillFilter_1(FILTER_1_INPUT_VALUE)
                .fillFilter_2(FILTER_2_INPUT_VALUE)
                .clickSave();


        Assert.assertEquals(ReferenceValuesPage1.getRowCount(), 1);
        Assert.assertEquals(ReferenceValuesPage1.getRow(0), EXPECTED_RESULT);
        Assert.assertEquals(ReferenceValuesPage1.getIcon(), ICON_SAVE);

    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {

        ReferenceValuesViewPage1 referenceValuesViewPage1 = new MainPage(getDriver())
                .clickReferenceValuesMenu()
                .clickActions()
                .clickActionsView();

        Assert.assertEquals(referenceValuesViewPage1.getRecordInViewMode(), EXPECTED_RESULT_1);
    }

    @Test(dependsOnMethods = "testViewRecord")
    public void testEditRecord() {

        ReferenceValuesPage1 referenceValuesPage1 = new MainPage(getDriver())
                .clickReferenceValuesMenu()
                .clickActions()
                .clickActionsEdit()
                .fillLabel(EDIT_LABEL_VALUE)
                .fillFilter_1(EDIT_FILTER_1_VALUE)
                .fillFilter_2(EDIT_FILTER_2_VALUE)
                .clickSaveDraft();
        Assert.assertEquals(ReferenceValuesPage1.getRowCount(), 1);
        Assert.assertEquals(ReferenceValuesPage1.getRow(0), EDIT_RESULT);

    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testReorderRecord() {
        ReferenceValuesPage1 referenceValuesPage1 = new MainPage(getDriver())
                .clickReferenceValuesMenu()
                .clickNewButton()
                .fillLabel(EDIT_LABEL_VALUE)
                .fillFilter_1(EDIT_FILTER_1_VALUE)
                .fillFilter_2(EDIT_FILTER_2_VALUE)
                .clickSave()
                .clickOrder();

        Assert.assertEquals(ReferenceValuesPage1.getRow(0), EDIT_RESULT);
        referenceValuesPage1.getReorder();

        Assert.assertEquals(ReferenceValuesPage1.getRow(0), EDIT_RESULT);

        referenceValuesPage1.clickToggle()
                .getNewReorder();

        Assert.assertEquals(ReferenceValuesPage1.getRows(0), CARD_VIEW_RESULT);
    }

    @Test
    public void testCancelRecord() {

        ReferenceValuesPage1 referenceValuesPage1 = new MainPage(getDriver())
                .clickReferenceValuesMenu()
                .clickNewButton()
                .fillLabel(LABEL_INPUT_VALUE)
                .fillFilter_1(FILTER_1_INPUT_VALUE)
                .fillFilter_2(FILTER_2_INPUT_VALUE)
                .clickCancel();

        Assert.assertTrue(ReferenceValuesPage1.isTableEmpty());
    }

    @Test(dependsOnMethods = "testCancelRecord")
    public void testCreateNewDraftRecord() {

        ReferenceValuesPage1 referenceValuesPage1 = new MainPage(getDriver())
                .clickReferenceValuesMenu()
                .clickNewButton()
                .fillLabel(LABEL_INPUT_VALUE)
                .fillFilter_1(FILTER_1_INPUT_VALUE)
                .fillFilter_2(FILTER_2_INPUT_VALUE)
                .clickSaveDraft();

        Assert.assertEquals(ReferenceValuesPage1.getRowCount(), 1);
        Assert.assertEquals(ReferenceValuesPage1.getRow(0), EXPECTED_RESULT);
        Assert.assertEquals(ReferenceValuesPage1.getIcon(), ICON_SAVE_DRAFT);


    }

    @Test(dependsOnMethods = "testCreateNewDraftRecord")
    public void testDeleteRecord() {

        ReferenceValuesPage1 referenceValuesPage1 = new MainPage(getDriver())
                .clickReferenceValuesMenu()
                .clickActions()
                .clickActionsDelete();

        Assert.assertTrue(ReferenceValuesPage1.isTableEmpty());
        Assert.assertEquals(ReferenceValuesPage1.getTextnotificationRecycleBinIcon(), 1);
    }
}
