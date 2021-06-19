import base.BaseTest;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utils.TestUtils.*;

public class EntityReferenceValues2Test extends BaseTest {

    private static final By TABLE_ROWS = By.xpath("//table[@id='pa-all-entities-table']/tbody/tr");
    private static final By RECORDS_IN_BIN = By.xpath("//td[@class='pa-recycle-col']//span[contains(text(),'Label')]/b");

    private static List<List<String>> ListOfAllCreatedRecords;

    private void SetUpPage() {
        ProjectUtils.getEntity(getDriver(), "Reference values");
        getWait().until(ExpectedConditions.textToBe(By.xpath("//b[contains(text(),'Reference values')]"), "Reference values"));
    }

    private void SetUpBinPage(){
        scrollClick(getDriver(), findElement(By.xpath("//ul[@class='navbar-nav']/li[1]")));
        getWait().until(ExpectedConditions.textToBe(By.xpath("//b[contains(text(),'Recycle Bin')]"),
                "Recycle Bin"));
    }

    private void createNewRecords() {
        SetUpPage();
        Date Date = new Date();
        ListOfAllCreatedRecords = List.of(
                List.of("first record","45",new SimpleDateFormat("dd/MM/yyyy").format(Date)),
                List.of("second record","",new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(Date, 1))),
                List.of("third record","day",new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(Date, 2))));

        for (int i = 0; i < ListOfAllCreatedRecords.size(); i++) {
            getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(text(),'create_new_folder')]")))
                    .click();
            List<String> CurrentList = ListOfAllCreatedRecords.get(i);
            findElement(By.id("label")).sendKeys(CurrentList.get(0));
            findElement(By.id("filter_1")).sendKeys(CurrentList.get(1));
            findElement(By.id("filter_2")).sendKeys(CurrentList.get(2));
            scrollClick(getDriver(), findElement(By.id("pa-entity-form-save-btn")));//save
            getWait().until(ExpectedConditions.visibilityOfElementLocated(By
                    .xpath("//table[@id='pa-all-entities-table']")));
        }
    }

    private int DeleteVerifyGetCurrentListRecords(String NameOfRecord) {
        scrollClick(getDriver(), By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[contains(.,'" +
                NameOfRecord + "')]//button"));
        getWait().until(movingIsFinished(By.xpath("//tr[contains(.,'" +
                NameOfRecord + "')]//a[contains (text(), 'delete')]"))).click();

        if (!isTableAvailable()) return 0;

        int isDeletedRecordIsLocatedInPageTable = findElements(By.xpath("//td[@class='pa-list-table-th']")).stream()
                .map(el -> el.getText()).filter(el -> el.contains(NameOfRecord)).collect(Collectors.toList()).size();
        Assert.assertEquals(isDeletedRecordIsLocatedInPageTable, 0);

        return findElements(TABLE_ROWS).size();
    }

    private int PermanentlyDeleteVerifyGetCurrentListRecords(String NameOfRecord) {
        if (!isTableAvailable()) Assert.fail("There is no records in the Recycle Bin!");

        scrollClick(getDriver(),
                By.xpath("//tr[contains(.,'Label') and contains(.,'" +
                        NameOfRecord + "')]//a[text()='delete permanently']"));

        if (!isTableAvailable()) return 0;

        List<WebElement> ListRecordsInBin=findElements(RECORDS_IN_BIN);
        int isDeletedRecordIsLocatedInBin = ListRecordsInBin.stream().map(el -> el.getText())
                .filter(el -> el.contains(NameOfRecord)).collect(Collectors.toList()).size();
        Assert.assertEquals(isDeletedRecordIsLocatedInBin, 0);

        return ListRecordsInBin.size();
    }

    private boolean isTableAvailable() {
        WebElement checkForTable = findElement(By.xpath("//div[contains(@class,'card-body')]"));
        if (checkForTable.getText().trim().isBlank() || checkForTable.getText().trim().isEmpty()
                || checkForTable.getText().contains("Recycle bin is currently empty"))
            return false;
        else return true;
    }

    private int getNumberOfNotification() {
        if (getDriver().getPageSource().contains("<span class=\"notification\">"))
            return Integer.parseInt(findElement(
                    By.xpath("//span[@class='notification']/b")).getText());
        else return 0;
    }

    @Test
    public void testDeleteRecordAndValidateOnebyOne() {
        createNewRecords();//create 3 records
        int OriginalNumberOfRecords = findElements(TABLE_ROWS).size();
        Assert.assertEquals(OriginalNumberOfRecords, ListOfAllCreatedRecords.size());
        Assert.assertEquals(findElements(By.xpath("//td/i[@class='fa fa-check-square-o']")).size(),
                ListOfAllCreatedRecords.size());//verify left icon

        List<String> ActualResultList =
                findElements(By.xpath("//td[@class='pa-list-table-th']")).stream()
                        .map(el -> el.getText()).collect(Collectors.toList());
        List<String> ExpectedResultList=new ArrayList<>();
        for(List<String> list: ListOfAllCreatedRecords)
            ExpectedResultList.addAll(list);
        Assert.assertEquals(ActualResultList,ExpectedResultList);

        int CurrentNumberOfRecords = DeleteVerifyGetCurrentListRecords(ListOfAllCreatedRecords.get(1).get(0));
        OriginalNumberOfRecords--;
        Assert.assertEquals(CurrentNumberOfRecords, OriginalNumberOfRecords);
        Assert.assertEquals(getNumberOfNotification(),ListOfAllCreatedRecords.size()
                - OriginalNumberOfRecords);

        CurrentNumberOfRecords = DeleteVerifyGetCurrentListRecords(ListOfAllCreatedRecords.get(0).get(0));
        OriginalNumberOfRecords--;
        Assert.assertEquals(CurrentNumberOfRecords, OriginalNumberOfRecords);
        Assert.assertEquals(getNumberOfNotification(),ListOfAllCreatedRecords.size()
                - OriginalNumberOfRecords);

        CurrentNumberOfRecords = DeleteVerifyGetCurrentListRecords(ListOfAllCreatedRecords.get(2).get(0));
        OriginalNumberOfRecords--;
        Assert.assertEquals(CurrentNumberOfRecords, OriginalNumberOfRecords);
        Assert.assertEquals(getNumberOfNotification(),ListOfAllCreatedRecords.size()
                - OriginalNumberOfRecords);
    }

    @Test(dependsOnMethods = "testDeleteRecordAndValidateOnebyOne")
    public void testVerifyDeletedRecordsInBin() {
        SetUpBinPage();

        Assert.assertTrue(isTableAvailable());

        int ActualNumberOfRecordsInBin = findElements(By.xpath("//div[@class='fixed-table-body']//table/tbody/tr")).size();
        Assert.assertEquals(ActualNumberOfRecordsInBin, ListOfAllCreatedRecords.size());

        List<String> ActualResultList =
                findElements(By.xpath("//tr//span[contains(text(),'Label')]/b"))
                        .stream().map(el -> el.getText()).collect(Collectors.toList());
        List<String> ExpectedResultList = ListOfAllCreatedRecords.stream().map(el -> el.get(0)).collect(Collectors.toList());
        Assert.assertTrue(ExpectedResultList.containsAll(ActualResultList));
    }

    @Test(dependsOnMethods = "testVerifyDeletedRecordsInBin")
    public void testPermanentDeleteRecordAndValidateOnebyOn() {
        if(!findElement(By.xpath("//a[@class='navbar-brand']")).getText().contains("Recycle Bin"))
            SetUpBinPage();

        if (!isTableAvailable()) Assert.fail("There is no records in the Recycle Bin!");

        int OriginalValueOfRecordsInBin = findElements(RECORDS_IN_BIN).size();
        int CurrentNumberOfRecordsInBin = PermanentlyDeleteVerifyGetCurrentListRecords(
                ListOfAllCreatedRecords.get(1).get(0));
        OriginalValueOfRecordsInBin--;
        Assert.assertEquals(CurrentNumberOfRecordsInBin, OriginalValueOfRecordsInBin);
        Assert.assertEquals(getNumberOfNotification(),CurrentNumberOfRecordsInBin);

        CurrentNumberOfRecordsInBin = PermanentlyDeleteVerifyGetCurrentListRecords(
                ListOfAllCreatedRecords.get(0).get(0));
        OriginalValueOfRecordsInBin--;
        Assert.assertEquals(CurrentNumberOfRecordsInBin, OriginalValueOfRecordsInBin);
        Assert.assertEquals(getNumberOfNotification(),CurrentNumberOfRecordsInBin);

        CurrentNumberOfRecordsInBin = PermanentlyDeleteVerifyGetCurrentListRecords(
                ListOfAllCreatedRecords.get(2).get(0));
        OriginalValueOfRecordsInBin--;
        Assert.assertEquals(CurrentNumberOfRecordsInBin, OriginalValueOfRecordsInBin);
        Assert.assertEquals(getNumberOfNotification(),CurrentNumberOfRecordsInBin);
    }

    @Test(dependsOnMethods = "testPermanentDeleteRecordAndValidateOnebyOn")
    public void testVerifyRecordsInReferencePageTable() {
        SetUpPage();
        Assert.assertTrue(!isTableAvailable());
    }
}