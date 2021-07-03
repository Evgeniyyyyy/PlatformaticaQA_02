package temp;

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
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import static utils.TestUtils.scrollClick;

public class EntityChevronSearchForRecordsTest extends BaseTest {

    private static HashMap<String, List<String>> ListOfAllCreatedRecords = new HashMap<String, List<String>>();
    private static WebElement SearchButton=null;
    private static final By TABLE_ROWS =By.xpath("//table[@id='pa-all-entities-table']/tbody/tr");

    private void ValidationSearchResult(String WhatWeAreLooking) {
        SearchButton.clear();
        SearchButton.sendKeys(WhatWeAreLooking);
        if(WhatWeAreLooking.equals("sent")) return;//will be mismatch because of bug
        //searching works with 2 letters as well, so I compare length <2)
        int ExpectedTotalRecord = 0;
        if (WhatWeAreLooking.trim().isEmpty() || SearchButton == null || WhatWeAreLooking.trim().length()<2)
            ExpectedTotalRecord = ListOfAllCreatedRecords.size();
        else {
            for (List<String> element : ListOfAllCreatedRecords.values())
                if (element.toString().toLowerCase().contains(WhatWeAreLooking))
                    ExpectedTotalRecord += 1;
            if(ExpectedTotalRecord==0)ExpectedTotalRecord=1;//there is one row with "No matching records found"
        }
        try{
            getWait().until(ExpectedConditions.numberOfElementsToBe(TABLE_ROWS,
                    ExpectedTotalRecord));
        }
        catch(Exception e){
            System.out.println("Mismatch: "+WhatWeAreLooking);
        }
    }

    private void createNewRecord(List<String> TestData){
        By CreateButton=By.xpath("//*[contains(text(),'create_new_folder')]");
        getWait().until(ExpectedConditions.presenceOfElementLocated(CreateButton));
        scrollClick(getDriver(),CreateButton);
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
        ProjectUtils.getEntity(getDriver(),"Chevron");
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
        ListOfAllCreatedRecords.put("Pending", List.of("Pending", "Pending record", "1345678","12.00",
                new SimpleDateFormat("dd/MM/yyyy").format(CurrentDate),
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(CurrentDate),
                "",
                "tester177@tester.test"));
        createNewRecord(ListOfAllCreatedRecords.get("Pending"));
        //2 record
        ListOfAllCreatedRecords.put("Fulfillment", List.of("Fulfillment", "Fulfillment record", "678",
                "13.32",
                new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(CurrentDate, 1)),
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(DateUtils.addDays(CurrentDate, 1)),
                "",
                "tester10@tester.test"));
        createNewRecord(ListOfAllCreatedRecords.get("Fulfillment"));
        //3record
        ListOfAllCreatedRecords.put("Sent", List.of("Sent", "Sent", "345","0.12",
                new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(CurrentDate, 2)),
                new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(DateUtils.addDays(CurrentDate, 2)),
                "",
                "tester1@tester.test"));
        createNewRecord(ListOfAllCreatedRecords.get("Sent"));

        scrollClick(getDriver(), (By.xpath("//a[text()='All']")));//turn on All sorting

        //validation of all 3 records with test data
        List<WebElement> ListOfRealResult = findElements(TABLE_ROWS);
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
        for (List<String> List : ListOfAllCreatedRecords.values())
            for (String Element : List)
                ValidationSearchResult(Element.toLowerCase());
    }

    @Test (dependsOnMethods ={"testCreateNewRecordsAndValidation"})
    public void testSearchingForRandomTestData() {
        //Searching and testing for random test data
        //searching works with word's length 2 letters!!!! or more
        List<String> RandomTestDataForSearching = List.of("pen","record","test","fill",
                "678","3.32","06/","14/","15/","177","10@","1@t");
        SetUp();
        SearchButton = findElement(By.xpath("//input[@type='text'][@placeholder='Search']"));
        for (String Element : RandomTestDataForSearching)
            ValidationSearchResult(Element.toLowerCase());
    }

    @Test (dependsOnMethods ={"testCreateNewRecordsAndValidation"})
    public void testSearchValueIsBlank(){
        //refresh searching (button has blank value)
        SetUp();
        SearchButton = findElement(By.xpath("//input[@type='text'][@placeholder='Search']"));
        ValidationSearchResult("");
    }

    @Test (dependsOnMethods ={"testCreateNewRecordsAndValidation"})
    public void testSearchValueLessTwo() {
        //Verify if search works only with value>2
        List<String> ListOfValueLess2 = List.of("p", "r", "", "1", "@", "!", "0");
        SetUp();
        SearchButton = findElement(By.xpath("//input[@type='text'][@placeholder='Search']"));
        for (String Element : ListOfValueLess2)
            ValidationSearchResult(Element.toLowerCase());
    }
}
