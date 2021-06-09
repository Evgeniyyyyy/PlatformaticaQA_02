import base.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.*;

@Ignore
public class EntityChevronTest extends BaseTest {

    final String ENTITY_NAME = "Chevron";
    final String PENDING = "Pending";
    final String FULFILLMENT = "Fulfillment";
    final String SENT = "Sent";

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

    private List<String> makeRandomData(String dropDownItem){
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
        chooseRecordInTable(isDraft, data);
    }

    private void chooseRecordInTable(Boolean isDraft, List<String> data){
        List<WebElement> records = findElements(By.xpath("//tbody/tr"));

        for(int j = 1; j < records.size() + 1; j ++){
            List<WebElement> cells = findElements(By.xpath("//tbody/tr["+ j +"]/td[@class = 'pa-list-table-th']"));
            System.out.println(cells.get(1).getText());
            System.out.println(data.get(1));
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

    @Test(dependsOnMethods = {"testCancelCreateRecord", "testCancelCreateDraftRecord"})
    public void testCreatePendingRecord(){
        String status = PENDING;

        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(status);
        fillFormFields(expectedData);
        clickSave(getDriver());
        checkCreatedRecord(status, false, expectedData);
    }

    @Test
    public void testCreateFulfillmentRecord(){
        String status = FULFILLMENT;

        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(status);
        fillFormFields(expectedData);
        clickSave(getDriver());
        checkCreatedRecord(status, false, expectedData);
    }

    @Test
    public void testCreateSentRecord(){
        String status = SENT;

        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(status);
        fillFormFields(expectedData);
        clickSave(getDriver());
        checkCreatedRecord(status, false, expectedData);
    }

    @Test
    public void testCreatePendingDraftRecord(){
        String status = PENDING;

        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(status);
        fillFormFields(expectedData);
        clickSaveDraft(getDriver());
        checkCreatedRecord(status, true, expectedData);
    }

    @Test
    public void testCreateFulfillmentDraftRecord(){
        String status = FULFILLMENT;

        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(status);
        fillFormFields(expectedData);
        clickSaveDraft(getDriver());
        checkCreatedRecord(status, true, expectedData);
    }

    @Test(dependsOnMethods = {"testCancelCreateRecord", "testCancelCreateDraftRecord"})
    public void testCreateSentDraftRecord(){
        String status = SENT;

        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(status);
        fillFormFields(expectedData);
        clickSaveDraft(getDriver());
        checkCreatedRecord(status, true, expectedData);
    }

    @Test
    public void testCancelCreateRecord(){
        String status = PENDING;

        start(getDriver());
        chooseSideBarItem(ENTITY_NAME);
        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(status);
        fillFormFields(expectedData);
        clickCancel(getDriver());
        Assert.assertTrue(findElements(By.cssSelector("tbody tr")).isEmpty());
    }

    @Test(dependsOnMethods = {"testCancelCreateRecord"})
    public void testCancelCreateDraftRecord(){
        String status = FULFILLMENT;

        clickCreateRecord(getDriver());
        List<String> expectedData = makeRandomData(status);
        fillFormFields(expectedData);
        clickCancel(getDriver());
        Assert.assertTrue(findElements(By.cssSelector("tbody tr")).isEmpty());
    }
}
