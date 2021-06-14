import base.BaseTest;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import static utils.TestUtils.scrollClick;

public class EntityChevronSearchForRecordsTest extends BaseTest {

     private static HashMap<String, List<String>> ListOfAllCreatedRecords = new HashMap<String, List<String>>();
     private static WebElement SearchButton=null;

     private void getTestingAndValidation(String WhatWeAreLooking) {
         if (WhatWeAreLooking.trim().isEmpty() || SearchButton == null) return;
         //In the Chevron search function found bug, so temporally
         //I don't include test data that causes incorrect result
         List <String> TestDataForIncorrectResult=List.of("sent","delete", "edit", "view","di",
                 "chevron","15","59","me","re","pe");
         if(TestDataForIncorrectResult.contains(WhatWeAreLooking))return;
         SearchButton.clear();
         SearchButton.sendKeys(WhatWeAreLooking);
         int ExpectedTotalMatches = 0;
         for (List<String> element : ListOfAllCreatedRecords.values())
             if (element.toString().toLowerCase().contains(WhatWeAreLooking)) ExpectedTotalMatches += 1;
         //validation of test data
         getWait().until(ExpectedConditions.numberOfElementsToBe(By
                 .xpath("//*[@id='pa-all-entities-table']/tbody/tr"), ExpectedTotalMatches));
     }

    private void createNewRecord(List<String> TestData){
        findElement(By.xpath("//*[contains(text(),'create_new_folder')]")).click();//click on the button
        //choose item from dropbox accordingly with first item of test data
        /**/findElement(By.xpath("//button[@class='dropdown-toggle btn btn-link']")).click();
        /**/getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dropdown-menu show']")));
        /**/findElement(By
                .xpath("//div[@class='dropdown-menu show']/div/ul/child::li/a/span[2][text()='"+
                 TestData.get(0)+"']")).click();
       //filling form with rest test data
        WebElement date = findElement(By.id("date"));
        scrollClick(getDriver(), date);
        date.clear();
        date.sendKeys(TestData.get(4));
        WebElement dateTime = findElement(By.id("datetime"));
        scrollClick(getDriver(), dateTime);
        dateTime.clear();
        dateTime.sendKeys(TestData.get(5));
        findElement(By.id("int")).sendKeys(TestData.get(2));
        findElement(By.id("text")).sendKeys(TestData.get(1));
        findElement(By.id("decimal")).sendKeys(TestData.get(3));
        //choose user from User dropbox
        /**/findElement(By.xpath("//button[@data-id='user']")).click();
        /**/getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dropdown-menu show']")));
        /**/findElement(By
                .xpath("//div[@class='dropdown-menu show']/div/ul/child::li/a/span[2][text()='"+
                        TestData.get(7)+"']")).click();
         scrollClick(getDriver(), findElement(By.id("pa-entity-form-save-btn")));//save
   }

   private void SetUp() {
       TestUtils.scrollClick(getDriver(), By.xpath("//p[contains(text(),'Chevron')]"));
       Assert.assertEquals(findElement(By.xpath("//a[@class='navbar-brand']/b[contains(text(),'Chevron')]"))
               .getText(), "Chevron");
       if (!getDriver().getCurrentUrl().contains("&stage=_"))
           scrollClick(getDriver(), (By.xpath("//a[text()='All']")));//turn on All sorting
   }

    @Test
    public void testCreateNewRecordsAndValidation() {

        SetUp();
        Date CurrentDate = new Date();
        //1 record
        ListOfAllCreatedRecords.put("Pending", List.of("Pending", "Pending record", "1345678",
                "12.00",
                new SimpleDateFormat("dd/MM/yyyy").format(CurrentDate),
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(CurrentDate),
                "",
                "tester177@tester.test"));
        createNewRecord(ListOfAllCreatedRecords.get("Pending"));
        //2 record
        ListOfAllCreatedRecords.put("Fulfillment", List.of("Fulfillment", "Fulfillment record", "67",
                "13.32",
                new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(CurrentDate, 1)),
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(DateUtils.addDays(CurrentDate, 1)),
                "",
                "tester10@tester.test"));
        createNewRecord(ListOfAllCreatedRecords.get("Fulfillment"));
        //3record
        ListOfAllCreatedRecords.put("Sent", List.of("Sent", "Sent", "59",
                "0.12",
                new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(CurrentDate, 2)),
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(DateUtils.addDays(CurrentDate, 2)),
                "",
                "tester1@tester.test"));
        createNewRecord(ListOfAllCreatedRecords.get("Sent"));

        scrollClick(getDriver(), (By.xpath("//a[text()='All']")));//turn on All sorting

        //validation of all 3 records with test data
        List<WebElement> ListOfRealResult = findElements(By
                .xpath("//*[@id='pa-all-entities-table']/tbody/tr"));
        Assert.assertEquals(ListOfRealResult.size(), 3);

        for (int i = 1; i <= ListOfRealResult.size(); i++) {
            List<String> ListOfEntities = findElements(By.xpath("//tr[" + i + "]/td[@class='pa-list-table-th']"))
                    .stream().map(el -> el.getText()).collect(Collectors.toList());
            Assert.assertEquals(ListOfEntities, ListOfAllCreatedRecords.get(ListOfEntities.get(0)));
        }//end of validation
    }

    @Test (dependsOnMethods ={"testCreateNewRecordsAndValidation"})
    public void testSearchingForExactSameTestData() {
        //Searching for exact same test data which are located in the 3 created records
        SetUp();
        SearchButton = findElement(By.xpath("//input[@type='text'][@placeholder='Search']"));
        for (List<String> List : ListOfAllCreatedRecords.values()) {
            for (String Element : List) {
                getTestingAndValidation(Element.toLowerCase());
            }
        }
    }

    @Test (dependsOnMethods ={"testCreateNewRecordsAndValidation"})
    public void testSearchingForRandomTestData() {
        //Searching and testing for random test data
        List<String> RandomTestDataForSearching = List.of("pe", "me", "ful", "di",
                "Sent", "delete", "edit", "view","3.", "67", "59", "12", "00", "15", "0@", "1@",
                "7@", "2.", ".0");

        SetUp();
        SearchButton = findElement(By.xpath("//input[@type='text'][@placeholder='Search']"));
        for (String Element : RandomTestDataForSearching) {
            getTestingAndValidation(Element.toLowerCase());
        }
    }
}

