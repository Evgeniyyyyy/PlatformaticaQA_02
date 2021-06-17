import base.BaseTest;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import static utils.TestUtils.*;

public class EntityReferenceValuesDeleteRecordTest extends BaseTest {

    private static final By TABLE_ROWS = By.xpath("//table[@id='pa-all-entities-table']/tbody/tr");

    private static List<List<String>> ListOfAllCreatedRecords;

    private void createNewRecords() {
        ProjectUtils.getEntity(getDriver(), "Reference values");
        getWait().until(ExpectedConditions.textToBe(By.xpath("//b[contains(text(),'Reference values')]"),"Reference values"));
        Date Date = new Date();
        ListOfAllCreatedRecords = List.of(
                List.of("first record", "45",
                        new SimpleDateFormat("dd/MM/yyyy").format(Date)),
                List.of("second record", "",
                        new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(Date, 1))),
                List.of("third record", "day",
                        new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(Date, 2))));

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
            Assert.assertEquals(findElements(TABLE_ROWS).size(), i + 1);

            Assert.assertTrue(findElement(By.xpath("//table[@id='pa-all-entities-table']//i"))
                    .getAttribute("class").contains("fa fa-check-square-o"));//verify left icon

            List<String> WebListOfEntities =
                    findElements(By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[contains(.,'" +
                    CurrentList.get(0) + "')]/td[@class='pa-list-table-th']")).stream()
                    .map(el -> el.getText()).collect(Collectors.toList());
            Assert.assertEquals(CurrentList, WebListOfEntities);
        }
    }

    private int getWebListOfRecordsAfterDeletion(String NameOfRecord) {
        scrollClick(getDriver(),By.xpath("//table[@id='pa-all-entities-table']/tbody/tr[contains(.,'" +
                NameOfRecord + "')]//button"));
        getWait().until(movingIsFinished(By.xpath("//tr[contains(.,'" +
                NameOfRecord + "')]//a[contains (text(), 'delete')]"))).click();

        if(!isTableAvailable())return 0;

        List<WebElement> WebListOfRecords = findElements(TABLE_ROWS);
        int ActualRecords = findElements(By.xpath("//td[@class='pa-list-table-th']")).stream()
                .map(el -> el.getText()).filter(el -> el.contains(NameOfRecord)).collect(Collectors.toList()).size();
        Assert.assertEquals(ActualRecords, 0);

        return WebListOfRecords.size();
    }

    private boolean isTableAvailable() {
        WebElement checkForTable=findElement(By.xpath("//div[contains(@class,'card-body')]"));
        if(checkForTable.getText().trim().isBlank() || checkForTable.getText().trim().isEmpty()
        || checkForTable.getText().contains("Recycle bin is currently empty"))
            return false;
        else return true;
    }

    @Test
    public void testDeleteRecordAndValidateOnebyOne() {
        createNewRecords();

        int OriginalValueOfRecords = findElements(TABLE_ROWS).size();
        int CurrentValueOfRecords = getWebListOfRecordsAfterDeletion(ListOfAllCreatedRecords.get(1).get(0));
        OriginalValueOfRecords--;
        Assert.assertEquals(CurrentValueOfRecords, OriginalValueOfRecords);
        Assert.assertEquals(findElement(By.xpath("//ul[@class='navbar-nav']/li[1]//span[@class='notification']/b"))
                        .getText(),String.valueOf(ListOfAllCreatedRecords.size()-OriginalValueOfRecords));

        CurrentValueOfRecords = getWebListOfRecordsAfterDeletion(ListOfAllCreatedRecords.get(0).get(0));
        OriginalValueOfRecords--;
        Assert.assertEquals(CurrentValueOfRecords, OriginalValueOfRecords);
        Assert.assertEquals(findElement(By.xpath("//ul[@class='navbar-nav']/li[1]//span[@class='notification']/b"))
                .getText(),String.valueOf(ListOfAllCreatedRecords.size()-OriginalValueOfRecords));

        CurrentValueOfRecords = getWebListOfRecordsAfterDeletion(ListOfAllCreatedRecords.get(2).get(0));
        OriginalValueOfRecords--;
        Assert.assertEquals(CurrentValueOfRecords, OriginalValueOfRecords);
        Assert.assertEquals(findElement(By.xpath("//ul[@class='navbar-nav']/li[1]//span[@class='notification']/b"))
                .getText(),String.valueOf(ListOfAllCreatedRecords.size()-OriginalValueOfRecords));
    }

    @Test(dependsOnMethods = "testDeleteRecordAndValidateOnebyOne")
    public void verifyDeletedRecordsInBin() {
        scrollClick(getDriver(), findElement(By.xpath("//ul[@class='navbar-nav']/li[1]")));
        getWait().until(ExpectedConditions.textToBe(By.xpath("//b[contains(text(),'Recycle Bin')]"),
                "Recycle Bin"));

        Assert.assertTrue(isTableAvailable());

        int ActualResult = findElements(By.xpath("//div[@class='fixed-table-body']//table/tbody/tr")).size();
        Assert.assertEquals(ActualResult, ListOfAllCreatedRecords.size());

        List<String> ActualResultList =
          findElements(By.xpath("//div[@class='fixed-table-body']//table/tbody/tr//span[contains(text(),'Label')]/b"))
          .stream().map(el -> el.getText()).collect(Collectors.toList());
        List<String> ExpectedResultList = ListOfAllCreatedRecords.stream().map(el -> el.get(0)).collect(Collectors.toList());
        Assert.assertTrue(ExpectedResultList.containsAll(ActualResultList));
    }
}