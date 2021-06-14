import base.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.*;


public class EntityChevronTest extends BaseTest {

    final private static String ENTITY_NAME = "Chevron";
    final private static String PENDING = "Pending";
    final private static String FULFILLMENT = "Fulfillment";
    final private static String SENT = "Sent";


    private void chooseStringDropDownItem(String dropDownItem) {
        WebElement stringButton = findElement(By.xpath("//button[@data-id='string']"));
        Assert.assertEquals(stringButton.getAttribute("title"), "Pending");

        stringButton.click();

        jsClick(getDriver(),
                findElement(By.xpath("//ul[@class='dropdown-menu inner show']//span[contains(.,'"+dropDownItem+"')]")));
        getWait().until(
                ExpectedConditions.invisibilityOf(findElement(By.xpath("//div[@class='dropdown-menu']"))));

        Assert.assertEquals(stringButton.getAttribute("title"), dropDownItem);
    }

    private static List<String> makeRandomData(String dropDownItem){
        List<String> randomData = new ArrayList<>();

        randomData.add(dropDownItem);

        String formText = RandomStringUtils.randomAlphabetic(10);
        randomData.add(formText);
        randomData.add("" + RandomUtils.nextInt(0, 1000000));

        String formDecimal = RandomUtils.nextInt(0, 1000000) + "." + RandomStringUtils.randomNumeric(2);
        randomData.add(formDecimal);

        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        randomData.add(date);

        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        randomData.add(dateTime);

        randomData.add("");

        String user = "apptester1@tester.test";
        randomData.add(user);

        return randomData;
    }

    private void checkFormIsNotEmpty(){
        WebElement stringButton = findElement(By.xpath("//button[@data-id='string']"));
        WebElement text = findElement(By.xpath("//textarea[@id='text']"));
        WebElement intData = findElement(By.id("int"));
        WebElement decimalData = findElement(By.id("decimal"));
        WebElement date = findElement(By.xpath("//input[@id='date']"));
        WebElement datetime = findElement(By.xpath("//input[@id='datetime']"));
        WebElement user = findElement(By.xpath("//button[@data-id='user']"));
        Assert.assertFalse(stringButton.getAttribute("title").isEmpty());
        Assert.assertFalse(text.getText().isEmpty());
        Assert.assertFalse(intData.getAttribute("value").isEmpty());
        Assert.assertFalse(decimalData.getAttribute("value").isEmpty());
        Assert.assertFalse(date.getAttribute("value").isEmpty());
        Assert.assertFalse(datetime.getAttribute("value").isEmpty());
        Assert.assertFalse(user.getAttribute("title").isEmpty());
    }

    private void emptyForm(){
        WebElement date = findElement(By.xpath("//input[@id='date']"));
        date.click();
        date.clear();

        WebElement datetime = findElement(By.xpath("//input[@id='datetime']"));
        datetime.click();
        datetime.clear();

        WebElement text = findElement(By.xpath("//textarea[@id='text']"));
        text.click();
        text.clear();

        WebElement intData = findElement(By.xpath("//input[@id='int']"));
        intData.click();
        intData.clear();

        WebElement decimalData = findElement(By.xpath("//input[@id='decimal']"));
        decimalData.click();
        decimalData.clear();
    }

    private void fillFormFields(List<String> data){
        chooseStringDropDownItem(data.get(0));

        WebElement date = findElement(By.xpath("//input[@id='date']"));
        date.click();
        date.clear();
        date.sendKeys(data.get(4));

        WebElement datetime = findElement(By.xpath("//input[@id='datetime']"));
        datetime.click();
        datetime.clear();
        datetime.sendKeys(data.get(5));

        WebElement text = findElement(By.xpath("//textarea[@id='text']"));
        text.click();
        text.sendKeys(data.get(1));

        WebElement intData = findElement(By.xpath("//input[@id='int']"));
        intData.click();
        intData.sendKeys(data.get(2));

        WebElement decimalData = findElement(By.xpath("//input[@id='decimal']"));
        decimalData.click();
        decimalData.sendKeys(data.get(3));
    }

    private void checkCreatedRecord(String recordStatus, Boolean isDraft, List<String> data){
        if(recordStatus.equals(SENT)){
            findElement(By.xpath("//a[text()='All']")).click();
        }
        checkRecordInTable(isDraft, data);
    }

    private int chooseRecordNumberInTable(List<String> data) {
        List<WebElement> records = findElements(By.xpath("//tbody/tr"));

        for(int j = 1; j <= records.size(); j ++){
            List<WebElement> cells = findElements(By.xpath("//tbody/tr["+ j +"]/td[@class = 'pa-list-table-th']"));

            if(cells.get(1).getText().equals(data.get(1))){
                return j - 1;
            }
        }
        return 0;
    }

    private void checkRecordInTable(Boolean isDraft, List<String> data){
        List<WebElement> records = findElements(By.xpath("//tbody/tr"));

        for(int j = 1; j <= records.size(); j ++){
            List<WebElement> cells = findElements(By.xpath("//tbody/tr["+ j +"]/td[@class = 'pa-list-table-th']"));

            if(cells.get(1).getText().equals(data.get(1))){
                if(isDraft){
                    WebElement icon = findElement(By.xpath("//i[contains(@class,'fa fa-pencil')]"));
                    Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");
                }
                else {
                    WebElement icon = findElement(By.xpath("//i[contains(@class,'check-square')]"));
                    Assert.assertEquals(icon.getAttribute("class"), "fa fa-check-square-o");
                }
                for (int i = 0; i < data.size(); i++) {
                    Assert.assertEquals(cells.get(i).getText(), data.get(i));
                }
            }
        }
    }

    private void checkRecordInViewMode(List<String> data) {
        List<WebElement> actualRecords = findElements(By.xpath("//span [@class = 'pa-view-field']"));

        for (int i = 0; i < actualRecords.size() ; i++) {
            Assert.assertEquals(actualRecords.get(i).getText(), data.get(i));
        }
        WebElement user = findElement(By.xpath("//div[@class = 'form-group']/p"));
        Assert.assertEquals(user.getText(), data.get(7));
    }

    private void clickActionsView(int row) {
        findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[@class='material-icons'][position()=1]")).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='view']")))).click();
    }

    private void clickActionsEdit(int row) {
        findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[@class='material-icons'][position()=1]")).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='edit']")))).click();
    }

    private void clickActionsDelete(int row) {
        findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[@class='material-icons'][position()=1]")).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='delete']")))).click();
    }

    final private static List<String> EXPECTED_DATA_PENDING_RECORD = makeRandomData(PENDING);
    final private static List<String> EXPECTED_DATA_FULFILLMENT_RECORD = makeRandomData(FULFILLMENT);
    final private static List<String> EXPECTED_DATA_SENT_RECORD = makeRandomData(SENT);
    final private static List<String> EXPECTED_DATA_PENDING_DRAFT_RECORD = makeRandomData(PENDING);
    final private static List<String> EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD = makeRandomData(FULFILLMENT);
    final private static List<String> EXPECTED_DATA_SENT_DRAFT_RECORD = makeRandomData(SENT);

    @Test
    public void testCreatePendingRecord(){
        getEntity(getDriver(), ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_PENDING_RECORD);
        clickSave(getDriver());
        checkCreatedRecord(PENDING, false, EXPECTED_DATA_PENDING_RECORD);
    }

    @Test(dependsOnMethods = {"testCreatePendingRecord"})
    public void testViewPendingRecord() {
        getEntity(getDriver(), ENTITY_NAME);

        int row = chooseRecordNumberInTable(EXPECTED_DATA_PENDING_RECORD);
        clickActionsView(row);

        checkRecordInViewMode(EXPECTED_DATA_PENDING_RECORD);
    }

    @Test(dependsOnMethods = {"testViewPendingRecord"})
    public void testEditPendingRecord() {
        getEntity(getDriver(), ENTITY_NAME);

        int row = chooseRecordNumberInTable(EXPECTED_DATA_PENDING_RECORD);
        clickActionsEdit(row);

        checkFormIsNotEmpty();
        emptyForm();
        fillFormFields(EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD);
        clickSave(getDriver());
        checkCreatedRecord(FULFILLMENT, false, EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD);
    }

    @Test(dependsOnMethods =  {"testEditPendingRecord"})
    public void testDeleteFulfillmentRecord(){
        getEntity(getDriver(), ENTITY_NAME);

        int row = chooseRecordNumberInTable(EXPECTED_DATA_PENDING_RECORD);
        clickActionsDelete(row);
        List<WebElement> tableElements = findElements(By.xpath("//div[@class='card-body ']/*"));
        Assert.assertEquals(tableElements.size(), 1);

        String expectedTextRecycleBinAfterDelete = "delete_outline\n" + "1";
        String textRecycleBinAfterDelete = getDriver().findElement(
                By.xpath("//a[@href='index.php?action=recycle_bin']")).getText();
        Assert.assertEquals(textRecycleBinAfterDelete, expectedTextRecycleBinAfterDelete);
    }

    @Test(dependsOnMethods =  {"testDeleteFulfillmentRecord"})
    public void testDeletePermanentlyFulfillmentRecord() {
        getEntity(getDriver(), ENTITY_NAME);

        String expectedTextRecycleBinBeforeDelete = "delete_outline\n" + "1";
        String textRecycleBinBeforeDelete = getDriver().findElement(
                By.xpath("//a[@href='index.php?action=recycle_bin']")).getText();
        Assert.assertEquals(textRecycleBinBeforeDelete, expectedTextRecycleBinBeforeDelete);

        findElement(By.xpath("//i[contains(text(),'delete_outline')]")).click();
        findElement(By.linkText("delete permanently")).click();

        WebElement recyclerBin = getDriver().findElement(By.xpath("//div[@class = 'card-body']"));
        Assert.assertTrue(recyclerBin.getText().contains
                ("Good job with housekeeping! Recycle bin is currently empty!"));
    }

    @Test
    public void testCreateFulfillmentRecord(){
        getEntity(getDriver(), ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_FULFILLMENT_RECORD);
        clickSave(getDriver());
        checkCreatedRecord(FULFILLMENT, false, EXPECTED_DATA_FULFILLMENT_RECORD);
    }

    @Test(dependsOnMethods = {"testCreateFulfillmentRecord"})
    public void testViewFulfillmentRecord() {
        getEntity(getDriver(), ENTITY_NAME);

        int row = chooseRecordNumberInTable(EXPECTED_DATA_FULFILLMENT_RECORD);
        clickActionsView(row);

        checkRecordInViewMode(EXPECTED_DATA_FULFILLMENT_RECORD);
    }
    @Ignore
    @Test
    public void testCreateSentRecord(){
        getEntity(getDriver(), ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_SENT_RECORD);
        clickSave(getDriver());
        checkCreatedRecord(SENT, false, EXPECTED_DATA_SENT_RECORD);
    }
    @Ignore
    @Test(dependsOnMethods = {"testCreateSentRecord"})
    public void testViewSentRecord() {
        getEntity(getDriver(), ENTITY_NAME);
        findElement(By.xpath("//a[text()='All']")).click();

        int row = chooseRecordNumberInTable(EXPECTED_DATA_SENT_RECORD);
        clickActionsView(row);

        checkRecordInViewMode(EXPECTED_DATA_SENT_RECORD);
    }
    @Ignore
    @Test
    public void testCreatePendingDraftRecord(){
        getEntity(getDriver(), ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_PENDING_DRAFT_RECORD);
        clickSaveDraft(getDriver());
        checkCreatedRecord(PENDING, true, EXPECTED_DATA_PENDING_DRAFT_RECORD);
    }
    @Ignore
    @Test(dependsOnMethods = {"testCreatePendingDraftRecord"})
    public void testViewPendingDraftRecord() {
        getEntity(getDriver(), ENTITY_NAME);

        int row = chooseRecordNumberInTable(EXPECTED_DATA_PENDING_DRAFT_RECORD);
        clickActionsView(row);

        checkRecordInViewMode(EXPECTED_DATA_PENDING_DRAFT_RECORD);
    }
    @Ignore
    @Test
    public void testCreateFulfillmentDraftRecord(){
        getEntity(getDriver(), ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD);
        clickSaveDraft(getDriver());
        checkCreatedRecord(FULFILLMENT, true, EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD);
    }
    @Ignore
    @Test(dependsOnMethods = {"testCreateFulfillmentDraftRecord"})
    public void testViewFulfillmentDraftRecord() {
        getEntity(getDriver(), ENTITY_NAME);

        int row = chooseRecordNumberInTable(EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD);
        clickActionsView(row);

        checkRecordInViewMode(EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD);
    }
    @Ignore
    @Test
    public void testCreateSentDraftRecord(){
        getEntity(getDriver(), ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_SENT_DRAFT_RECORD);
        clickSaveDraft(getDriver());
        checkCreatedRecord(SENT, true, EXPECTED_DATA_SENT_DRAFT_RECORD);
    }
    @Ignore
    @Test(dependsOnMethods = {"testCreateSentDraftRecord"})
    public void testViewSentDraftRecord() {
        getEntity(getDriver(), ENTITY_NAME);
        findElement(By.xpath("//a[text()='All']")).click();

        int row = chooseRecordNumberInTable(EXPECTED_DATA_SENT_DRAFT_RECORD);
        clickActionsView(row);

        checkRecordInViewMode(EXPECTED_DATA_SENT_DRAFT_RECORD);
    }

    @Test
    public void testCancelCreateRecord(){
        getEntity(getDriver(), ENTITY_NAME);
        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(PENDING);
        fillFormFields(expectedData);
        clickCancel(getDriver());
        List<WebElement> tableElements = findElements(By.xpath("//div[@class='card-body ']/*"));
        Assert.assertEquals(tableElements.size(), 1);
    }

    @Test
    public void testCancelCreateDraftRecord(){
        getEntity(getDriver(), ENTITY_NAME);
        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(FULFILLMENT);
        fillFormFields(expectedData);
        clickCancel(getDriver());
        List<WebElement> tableElements = findElements(By.xpath("//div[@class='card-body ']/*"));
        Assert.assertEquals(tableElements.size(), 1);
    }
}
