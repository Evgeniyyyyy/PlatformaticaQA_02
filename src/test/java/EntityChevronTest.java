import base.BaseTest;
import model.*;
import model.ChevronPage.ChevronPageDto;
import org.testng.Assert;
import org.testng.annotations.Test;


public class EntityChevronTest extends BaseTest {

    final private static String PENDING = "Pending";
    final private static String FULFILLMENT = "Fulfillment";
    final private static String SENT = "Sent";

    final private static String NON_DRAFT = "fa fa-check-square-o";
    final private static String DRAFT = "fa fa-pencil";

    private static final ChevronPageDto EXPECTED_DATA_PENDING_RECORD = ChevronPage.generateRandomData(PENDING);
    private static final ChevronPageDto EXPECTED_DATA_FULFILLMENT_RECORD = ChevronPage.generateRandomData(FULFILLMENT);
    private static final ChevronPageDto EXPECTED_DATA_SENT_RECORD = ChevronPage.generateRandomData(SENT);
    private static final ChevronPageDto EXPECTED_DATA_PENDING_DRAFT_RECORD = ChevronPage.generateRandomData(PENDING);
    private static final ChevronPageDto EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD = ChevronPage.generateRandomData(FULFILLMENT);
    private static final ChevronPageDto EXPECTED_DATA_SENT_DRAFT_RECORD = ChevronPage.generateRandomData(SENT);


    @Test
    public void testCancelCreateDraftRecord() {

        ChevronPage chevronPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickNewButton()
                .setFormFields(EXPECTED_DATA_PENDING_DRAFT_RECORD)
                .clickCancel();

        Assert.assertTrue(chevronPage.isTableEmpty());
    }

    @Test
    public void testCancelCreateRecord() {

        ChevronPage chevronPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickNewButton()
                .setFormFields(EXPECTED_DATA_SENT_RECORD)
                .clickCancel()
                .clickAllButton();

        Assert.assertTrue(chevronPage.isTableEmpty());
    }

    @Test
    public void testCreatePendingRecord() {

        ChevronPage chevronPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickNewButton()
                .setFormFields(EXPECTED_DATA_PENDING_RECORD)
                .clickSave();

        Assert.assertEquals(chevronPage.getRowCount(), 1);
        Assert.assertEquals(chevronPage.getRow(0), EXPECTED_DATA_PENDING_RECORD);
        Assert.assertEquals(chevronPage.getIcon(), NON_DRAFT);
    }

    @Test(dependsOnMethods = {"testCreatePendingRecord"})
    public void testViewPendingRecord() {

        ChevronViewPage chevronViewPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickView();

        Assert.assertEquals(chevronViewPage.getData(), EXPECTED_DATA_PENDING_RECORD);
    }

    @Test(dependsOnMethods = {"testViewPendingRecord"})
    public void testEditPendingRecord() {

        ChevronPage chevronPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickEdit()
                .checkFormIsNotEmpty()
                .emptyForm()
                .setFormFields(EXPECTED_DATA_FULFILLMENT_RECORD)
                .clickSave();

        Assert.assertEquals(chevronPage.getRowCount(), 1);
        Assert.assertEquals(chevronPage.getRow(0), EXPECTED_DATA_FULFILLMENT_RECORD);
        Assert.assertEquals(chevronPage.getIcon(), NON_DRAFT);
    }

    @Test(dependsOnMethods = {"testEditPendingRecord"})
    public void testDeleteFulfillmentRecord() {

        ChevronPage chevronPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickDelete();

        Assert.assertTrue(chevronPage.isTableEmpty());
        Assert.assertTrue(chevronPage.isNotEmptyRecycleBin());
    }


    @Test(dependsOnMethods = {"testDeleteFulfillmentRecord"})
    public void testDeletePermanentlyFulfillmentRecord() {

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickRecycleBin()
                .clickDeletedRecordPermanently();

        Assert.assertTrue(recycleBinPage.getTextCardBody().contains(
                "Good job with housekeeping! Recycle bin is currently empty!"));
    }
}