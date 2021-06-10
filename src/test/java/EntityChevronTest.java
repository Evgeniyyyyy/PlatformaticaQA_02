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

@Ignore
public class EntityChevronTest extends BaseTest {

    final private static String ENTITY_NAME = "Chevron";
    final private static String PENDING = "Pending";
    final private static String FULFILLMENT = "Fulfillment";
    final private static String SENT = "Sent";

    private void chooseSideBarItem(String name){
        scrollClick(getDriver(), By.xpath("//a[@class='nav-link'][contains(.,'"+name+"')]"));
    }

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
            System.out.println(cells.size());
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
            Assert.assertEquals(actualRecords.get(i).getText(), data.get(i).toString());
        }
        WebElement user = findElement(By.xpath("//div[@class = 'form-group']/p"));
        Assert.assertEquals(user.getText(), data.get(7));
    }

    final private static List<String> EXPECTED_DATA_PENDING_RECORD = makeRandomData(PENDING);
    final private static List<String> EXPECTED_DATA_FULFILLMENT_RECORD = makeRandomData(FULFILLMENT);
    final private static List<String> EXPECTED_DATA_SENT_RECORD = makeRandomData(SENT);
    final private static List<String> EXPECTED_DATA_PENDING_DRAFT_RECORD = makeRandomData(PENDING);
    final private static List<String> EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD = makeRandomData(FULFILLMENT);
    final private static List<String> EXPECTED_DATA_SENT_DRAFT_RECORD = makeRandomData(SENT);

    @Test
    public void testCreatePendingRecord(){
        String status = PENDING;

        chooseSideBarItem(ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_PENDING_RECORD);
        clickSave(getDriver());
        checkCreatedRecord(status, false, EXPECTED_DATA_PENDING_RECORD);
    }

    @Test(dependsOnMethods = {"testCreatePendingRecord"})
    public void testViewPendingRecord() {
        chooseSideBarItem(ENTITY_NAME);

        int row = chooseRecordNumberInTable(EXPECTED_DATA_PENDING_RECORD);
        findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[@class='material-icons'][position()=1]")).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='view']")))).click();

        checkRecordInViewMode(EXPECTED_DATA_PENDING_RECORD);
    }
    
    @Test
    public void testCreateFulfillmentRecord(){
        String status = FULFILLMENT;

        chooseSideBarItem(ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_FULFILLMENT_RECORD);
        clickSave(getDriver());
        checkCreatedRecord(status, false, EXPECTED_DATA_FULFILLMENT_RECORD);
    }

    @Ignore
    @Test(dependsOnMethods = {"testCreateFulfillmentRecord"})
    public void testViewFulfillmentRecord() {
        chooseSideBarItem(ENTITY_NAME);

        int row = chooseRecordNumberInTable(EXPECTED_DATA_FULFILLMENT_RECORD);
        findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[@class='material-icons'][position()=1]")).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='view']")))).click();

        checkRecordInViewMode(EXPECTED_DATA_FULFILLMENT_RECORD);
    }

    @Ignore
    @Test
    public void testCreateSentRecord(){
        String status = SENT;

        chooseSideBarItem(ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_SENT_RECORD);
        clickSave(getDriver());
        checkCreatedRecord(status, false, EXPECTED_DATA_SENT_RECORD);
    }

    @Ignore
    @Test(dependsOnMethods = {"testCreateSentRecord"})
    public void testViewSentRecord() {
        chooseSideBarItem(ENTITY_NAME);
        findElement(By.xpath("//a[text()='All']")).click();

        int row = chooseRecordNumberInTable(EXPECTED_DATA_SENT_RECORD);
        findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[@class='material-icons'][position()=1]")).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='view']")))).click();

        checkRecordInViewMode(EXPECTED_DATA_SENT_RECORD);
    }
    @Ignore
    @Test
    public void testCreatePendingDraftRecord(){
        String status = PENDING;

        chooseSideBarItem(ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_PENDING_DRAFT_RECORD);
        clickSaveDraft(getDriver());
        checkCreatedRecord(status, true, EXPECTED_DATA_PENDING_DRAFT_RECORD);
    }
    @Ignore
    @Test(dependsOnMethods = {"testCreatePendingDraftRecord"})
    public void testViewPendingDraftRecord() {
        chooseSideBarItem(ENTITY_NAME);

        int row = chooseRecordNumberInTable(EXPECTED_DATA_PENDING_DRAFT_RECORD);
        findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[@class='material-icons'][position()=1]")).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='view']")))).click();

        checkRecordInViewMode(EXPECTED_DATA_PENDING_DRAFT_RECORD);
    }
    @Ignore
    @Test
    public void testCreateFulfillmentDraftRecord(){
        String status = FULFILLMENT;

        chooseSideBarItem(ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD);
        clickSaveDraft(getDriver());
        checkCreatedRecord(status, true, EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD);
    }
    @Ignore
    @Test(dependsOnMethods = {"testCreateFulfillmentDraftRecord"})
    public void testViewFulfillmentDraftRecord() {
        chooseSideBarItem(ENTITY_NAME);

        int row = chooseRecordNumberInTable(EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD);
        findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[@class='material-icons'][position()=1]")).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='view']")))).click();

        checkRecordInViewMode(EXPECTED_DATA_FULFILLMENT_DRAFT_RECORD);
    }
    @Ignore
    @Test
    public void testCreateSentDraftRecord(){
        String status = SENT;

        chooseSideBarItem(ENTITY_NAME);
        clickCreateRecord(getDriver());
        fillFormFields(EXPECTED_DATA_SENT_DRAFT_RECORD);
        clickSaveDraft(getDriver());
        checkCreatedRecord(status, true, EXPECTED_DATA_SENT_DRAFT_RECORD);
    }
    @Ignore
    @Test(dependsOnMethods = {"testCreateSentDraftRecord"})
    public void testViewSentDraftRecord() {
        chooseSideBarItem(ENTITY_NAME);
        findElement(By.xpath("//a[text()='All']")).click();

        int row = chooseRecordNumberInTable(EXPECTED_DATA_SENT_DRAFT_RECORD);
        findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[@class='material-icons'][position()=1]")).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='view']")))).click();

        checkRecordInViewMode(EXPECTED_DATA_SENT_DRAFT_RECORD);
    }

    @Test
    public void testCancelCreateRecord(){
        String status = PENDING;

        chooseSideBarItem(ENTITY_NAME);
        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(status);
        fillFormFields(expectedData);
        clickCancel(getDriver());
        List<WebElement> tableElements = findElements(By.xpath("//div[@class='card-body ']/*"));
        Assert.assertEquals(tableElements.size(), 1);
    }

    @Test
    public void testCancelCreateDraftRecord(){
        String status = FULFILLMENT;

        chooseSideBarItem(ENTITY_NAME);
        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(status);
        fillFormFields(expectedData);
        clickCancel(getDriver());
        List<WebElement> tableElements = findElements(By.xpath("//div[@class='card-body ']/*"));
        Assert.assertEquals(tableElements.size(), 1);
    }

}
