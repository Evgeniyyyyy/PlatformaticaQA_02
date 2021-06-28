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

    final private static String EMPTY_RECYCLE_BIN_MESSAGE = "Good job with housekeeping! Recycle bin is currently empty!";

    private static final ChevronPageDto EXPECTED_DATA_PENDING_RECORD = ChevronPage.generateRandomData(PENDING);
    private static final ChevronPageDto EXPECTED_DATA_FULFILLMENT_RECORD = ChevronPage.generateRandomData(FULFILLMENT);
    private static final ChevronPageDto EXPECTED_DATA_FULFILLMENT_SENT_RECORD = ChevronPage.makeSentRecord(EXPECTED_DATA_FULFILLMENT_RECORD);
    private static final ChevronPageDto EXPECTED_DATA_SENT_RECORD = ChevronPage.generateRandomData(SENT);

    private static final ChevronPageDto EXPECTED_DATA_PENDING_DRAFT_RECORD = ChevronPage.generateRandomData(PENDING);
    private static final ChevronPageDto EXPECTED_DATA_PENDING_SENT_DRAFT_RECORD = ChevronPage.makeSentRecord(EXPECTED_DATA_PENDING_DRAFT_RECORD);
    private static final ChevronPageDto EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD = ChevronPage.generateRandomData(FULFILLMENT);
    private static final ChevronPageDto EXPECTED_DATA_SENT_DRAFT_RECORD = ChevronPage.generateRandomData(SENT);


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

        Assert.assertTrue(recycleBinPage.getTextCardBody().contains(EMPTY_RECYCLE_BIN_MESSAGE));
    }

    @Test
    public void testCreateFulfillmentDraftRecord() {

        ChevronPage chevronPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickNewButton()
                .setFormFields(EXPECTED_DATA_FULFILLMENT_RECORD)
                .clickSaveDraft();

        Assert.assertEquals(chevronPage.getRowCount(), 1);
        Assert.assertEquals(chevronPage.getRow(0), EXPECTED_DATA_FULFILLMENT_RECORD);
        Assert.assertEquals(chevronPage.getIcon(), DRAFT);
    }

    @Test(dependsOnMethods = {"testCreateFulfillmentDraftRecord"})
    public void testViewFulfillmentDraftRecord() {

        ChevronViewPage chevronViewPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickView();

        Assert.assertEquals(chevronViewPage.getData(), EXPECTED_DATA_FULFILLMENT_RECORD);
    }

    @Test(dependsOnMethods = {"testViewFulfillmentDraftRecord"})
    public void testSentFulfillmentDraftRecord() {

        ChevronPage chevronPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickSent()
                .clickAllButton();

        Assert.assertEquals(chevronPage.getRowCount(), 1);
        Assert.assertEquals(chevronPage.getRow(0), EXPECTED_DATA_FULFILLMENT_SENT_RECORD);
        Assert.assertEquals(chevronPage.getIcon(), DRAFT);
    }

    @Test(dependsOnMethods = {"testSentFulfillmentDraftRecord"})
    public void testEditSentRecord() {

        ChevronPage chevronPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickAllButton()
                .clickEdit()
                .checkFormIsNotEmpty()
                .emptyForm()
                .setFormFields(EXPECTED_DATA_SENT_RECORD)
                .clickSave()
                .clickAllButton();

        Assert.assertEquals(chevronPage.getRowCount(), 1);
        Assert.assertEquals(chevronPage.getRow(0), EXPECTED_DATA_SENT_RECORD);
        Assert.assertEquals(chevronPage.getIcon(), NON_DRAFT);
    }

    @Test(dependsOnMethods = {"testEditSentRecord"})
    public void testDeleteSentRecord() {

        ChevronPage chevronPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickAllButton()
                .clickDelete();

        Assert.assertTrue(chevronPage.isTableEmpty());
        Assert.assertTrue(chevronPage.isNotEmptyRecycleBin());
    }

    @Test(dependsOnMethods = {"testDeleteSentRecord"})
    public void testRestoreSentRecord() {

        RecycleBinPage recycleBinPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickRecycleBin()
                .clickDeletedRestoreAsDraft();

        Assert.assertTrue(recycleBinPage.getTextCardBody().contains(EMPTY_RECYCLE_BIN_MESSAGE));

        ChevronPage chevronPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickAllButton();

        Assert.assertEquals(chevronPage.getRowCount(), 1);
        Assert.assertEquals(chevronPage.getRow(0), EXPECTED_DATA_SENT_RECORD);
        Assert.assertEquals(chevronPage.getIcon(), DRAFT);
    }

    @Test
    public void testCreateSentDraftRecord() {

        ChevronPage chevronPage = new MainPage(getDriver())
                .clickChevronMenu()
                .clickNewButton()
                .setFormFields(EXPECTED_DATA_SENT_RECORD)
                .clickSaveDraft()
                .clickAllButton();

        Assert.assertEquals(chevronPage.getRowCount(), 1);
        Assert.assertEquals(chevronPage.getRow(0), EXPECTED_DATA_SENT_RECORD);
        Assert.assertEquals(chevronPage.getIcon(), DRAFT);
    }
}