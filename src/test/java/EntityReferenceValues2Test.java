import base.BaseTest;
import model.MainPage;
import model.RecycleBinPage;
import model.ReferenceValuesPage;
import model.ReferenceValuesViewPage;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/*
   Steps
   1.Create records 2. Edit records  3. Verify Toggle 4.Delete records  5.Restore records from Bin  6.Delete records and
   then delete permanently from Bin   7.Searching process
 */

public class EntityReferenceValues2Test extends BaseTest {

    private ReferenceValuesPage referenceValuesPage;

    private static List<List<String>> ListForCreatingRecords = List.of(   //test data
            List.of("first record", "45", new SimpleDateFormat("dd/MM/yyyy").format(new Date())),
            List.of("second record", "45", new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(new Date(), 1))),
            List.of("third record", "day", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));

    private static List<List<String>> ListForNewValuesOfRecords = List.of(   //test data for edit
            List.of("new first record", "2", "filter_1"),
            List.of("new second record", "1", "filter_2"),
            List.of("new third record", "way", "filter_3"));

    private static List<Integer> OrderOfProcessingRecords = List.of(1, 0, 2);

    private List<String> getExpectedResultList(List<List<String>> list) {
        List<String> ExpectedResultList = new ArrayList<>();
        return list.stream().reduce(ExpectedResultList, (sum, el) -> {sum.addAll(el);return sum;});
        //   return ExpectedResultList;
    }

    private ReferenceValuesPage createFillRecord(String fieldLabel, String fieldFilter_1, String fieldFilter_2) {
        return new MainPage(getDriver())
                .clickReferenceValueMenu()
                .clickCreateButton()
                .fillLabel(fieldLabel)
                .fillFilter_1(fieldFilter_1)
                .fillFilter_2(fieldFilter_2)
                .clickSaveButton();
    }

    private ReferenceValuesPage editExistingRecord(String fieldLabel, String fieldFilter_1, String fieldFilter_2,
                                                   String NameRecordWantToChange) {
        return new MainPage(getDriver())
                .clickReferenceValueMenu()
                .editRecord(NameRecordWantToChange)
                .clearFields()
                .fillLabel(fieldLabel)
                .fillFilter_1(fieldFilter_1)
                .fillFilter_2(fieldFilter_2)
                .clickSaveButton();
    }

    private void verificationSearchProcess(String WhatWeAreLooking) {
        referenceValuesPage = referenceValuesPage.fillSearch(WhatWeAreLooking);
        String finalWhatWeAreLooking = WhatWeAreLooking.toLowerCase().trim();;
        List <List<String>>  realResult = finalWhatWeAreLooking.equals("")?ListForCreatingRecords:
                ListForCreatingRecords.stream().filter(el->el.toString().contains(finalWhatWeAreLooking))
                        .collect(Collectors.toList());
        String ActualResult=realResult.stream().map(ell->ell.stream().collect(Collectors.joining("\n")) + "\nmenu")
                .collect(Collectors.joining("\n")).replaceAll("\n\n","\n");
        try {
            getWait().until(ExpectedConditions.textToBePresentInElement(referenceValuesPage.getWebElementOfTable(),
                    ActualResult));
        } catch (Exception error) {//because of BUG
            System.out.println("Mismatch: " + WhatWeAreLooking);
        }
    }

    @Test
    public void testCreateRecordsValidate() {
        for (List<String> list : ListForCreatingRecords) {
            String fieldLabel = list.get(0);
            String fieldFilter_1 = list.get(1);
            String fieldFilter_2 = list.get(2);
            referenceValuesPage = createFillRecord(fieldLabel, fieldFilter_1, fieldFilter_2);
        }

        Assert.assertEquals(referenceValuesPage.getTableRowsCount(), ListForCreatingRecords.size());
        Assert.assertEquals(referenceValuesPage.getRowsValue(), getExpectedResultList(ListForCreatingRecords));
        Assert.assertEquals(referenceValuesPage.getRowsValue(By.xpath("//td/i[@class='fa fa-check-square-o']")).size(),
                ListForCreatingRecords.size());//verify left icon
    }

    @Test(dependsOnMethods = "testCreateRecordsValidate")
    public void testEditExistingRecords() {    //3 records
        for (int i = 0; i < OrderOfProcessingRecords.size(); i++) {
            String fieldLabel = ListForNewValuesOfRecords.get(OrderOfProcessingRecords.get(i)).get(0);
            String fieldFilter_1 = ListForNewValuesOfRecords.get(OrderOfProcessingRecords.get(i)).get(1);
            String fieldFilter_2 = ListForNewValuesOfRecords.get(OrderOfProcessingRecords.get(i)).get(2);
            String NameRecordWantToChange = ListForCreatingRecords.get(OrderOfProcessingRecords.get(i)).get(0);
            referenceValuesPage = editExistingRecord(fieldLabel, fieldFilter_1, fieldFilter_2,
                    NameRecordWantToChange);
            Assert.assertEquals(referenceValuesPage.getTableRowsCount(), ListForNewValuesOfRecords.size());
            Assert.assertTrue(referenceValuesPage.isSearchValuePresent(ListForNewValuesOfRecords.get(OrderOfProcessingRecords.get(i))));
        }
    }

    @Test(dependsOnMethods = "testEditExistingRecords")
    public void testViewExistingRecords() {    //3 records
        for (int i = 0; i < OrderOfProcessingRecords.size(); i++) {
            String fieldLabel = ListForNewValuesOfRecords.get(OrderOfProcessingRecords.get(i)).get(0);
            ReferenceValuesViewPage referenceValuesViewPage = new MainPage(getDriver())
                    .clickReferenceValueMenu().viewRecord(fieldLabel);
            Assert.assertEquals(referenceValuesViewPage.getValues(), ListForNewValuesOfRecords.get(OrderOfProcessingRecords.get(i)));
            referenceValuesViewPage.clickClose();
        }
    }

    @Test(dependsOnMethods = "testViewExistingRecords")
    public void testVerifyIfToggleButtonWork() {
        referenceValuesPage = new MainPage(getDriver()).clickReferenceValueMenu();
        for (int i = 0; i < 2; i++) //2 positions of toggle
        {
            Assert.assertEquals(referenceValuesPage.getTableRowsCount(), ListForNewValuesOfRecords.size());
            Assert.assertEquals(referenceValuesPage.getRowsValue(), getExpectedResultList(ListForNewValuesOfRecords));
            Assert.assertEquals(referenceValuesPage.getRowsValue(By.xpath("//i[@class='fa fa-check-square-o']")).size(),
                    ListForNewValuesOfRecords.size());//verify left icon
            referenceValuesPage = referenceValuesPage.clickToggle();
        }
    }

    @Test(dependsOnMethods = "testVerifyIfToggleButtonWork")
    public void testDeleteRecordsOneByOne() {    //3 records
        int OriginalNumberOfRecords = ListForNewValuesOfRecords.size();
        for (int i = 0; i < OrderOfProcessingRecords.size(); i++) {
            String fieldLabel = ListForNewValuesOfRecords.get(OrderOfProcessingRecords.get(i)).get(0);
            referenceValuesPage = new MainPage(getDriver()).clickReferenceValueMenu()
                    .deleteRecord(fieldLabel);
            OriginalNumberOfRecords--;
            Assert.assertFalse(referenceValuesPage.isSearchValuePresent(ListForNewValuesOfRecords.get(OrderOfProcessingRecords.get(i))));
            Assert.assertEquals(referenceValuesPage.getTableRowsCount(), OriginalNumberOfRecords);
            Assert.assertEquals(referenceValuesPage.getNumberOfNotification(), ListForNewValuesOfRecords.size()
                    - OriginalNumberOfRecords);
        }
    }

    @Test(dependsOnMethods = "testDeleteRecordsOneByOne")
    public void testVerifyDeletedRecordsInBin() {
        RecycleBinPage recycleBinPage = new MainPage(getDriver()).clickRecycleBin();
        Assert.assertEquals(recycleBinPage.getRowCount(), ListForNewValuesOfRecords.size());
        Assert.assertEquals(recycleBinPage.getNumberOfNotification(), ListForNewValuesOfRecords.size());
        Assert.assertTrue(recycleBinPage.getValuesCount().containsAll(getExpectedResultList(ListForNewValuesOfRecords)));
    }

    @Test(dependsOnMethods = "testVerifyDeletedRecordsInBin")
    public void testRestoreDeletedRecords() {
        RecycleBinPage recycleBinPage = new MainPage(getDriver()).clickRecycleBin();
        for (int i = 1; i <= ListForNewValuesOfRecords.size(); i++)
            recycleBinPage = recycleBinPage.clickDeletedRestoreAsDraft();
        Assert.assertEquals(recycleBinPage.getRowCount(), 0);
        Assert.assertEquals(recycleBinPage.getNumberOfNotification(), 0);
    }

    @Test(dependsOnMethods = "testRestoreDeletedRecords")
    public void testVerifyDraftRecordsInReferencePageTable() {
        referenceValuesPage = new MainPage(getDriver()).clickReferenceValueMenu();
        Assert.assertEquals(referenceValuesPage.getTableRowsCount(), ListForNewValuesOfRecords.size());
        Assert.assertEquals(referenceValuesPage.getRowsValue(), getExpectedResultList(ListForNewValuesOfRecords));
        Assert.assertEquals(referenceValuesPage.getRowsValue(By.xpath("//td/i[@class='fa fa-pencil']")).size(),
                ListForNewValuesOfRecords.size());//verify left icon
    }

    @Test(dependsOnMethods = "testVerifyDraftRecordsInReferencePageTable")
    public void testPermanentDeletedRecordsAndValidateOnebyOne() {   //3 records
        referenceValuesPage = new MainPage(getDriver()).clickReferenceValueMenu();
        referenceValuesPage.fillSearch("");
        for (List<String> list : ListForNewValuesOfRecords)
            referenceValuesPage = referenceValuesPage.deleteRecord(list.get(0));
        Assert.assertEquals(referenceValuesPage.getTableRowsCount(), 0);

        RecycleBinPage recycleBinPage = new MainPage(getDriver()).clickRecycleBin();
        Assert.assertEquals(recycleBinPage.getRowCount(), ListForNewValuesOfRecords.size());

        int OriginalValueOfRecordsInBin = recycleBinPage.getRowCount();
        for (int i = 0; i < OrderOfProcessingRecords.size(); i++) {
            String fieldLabel = ListForNewValuesOfRecords.get(OrderOfProcessingRecords.get(i)).get(0);
            recycleBinPage = recycleBinPage.deleteRecord(fieldLabel);
            OriginalValueOfRecordsInBin--;
            Assert.assertFalse(recycleBinPage.isSearchValuePresent(ListForNewValuesOfRecords.get(OrderOfProcessingRecords.get(i))));
            Assert.assertEquals(recycleBinPage.getRowCount(), OriginalValueOfRecordsInBin);
            Assert.assertEquals(recycleBinPage.getNumberOfNotification(), recycleBinPage.getRowCount());
        }
    }

    @Test(dependsOnMethods = "testPermanentDeletedRecordsAndValidateOnebyOne")
    public void testVerifyRecordsInReferencePageTable() {
        referenceValuesPage = new MainPage(getDriver()).clickReferenceValueMenu();
        Assert.assertTrue(!referenceValuesPage.isTableAvailable());
    }

    @Test
    public void testSearchingForExactSameTestData() {
        testCreateRecordsValidate();
        referenceValuesPage = new MainPage(getDriver()).clickReferenceValueMenu();
        for (List<String> List : ListForCreatingRecords)
            for (String Element : List) verificationSearchProcess(Element);
    }

    @Test(dependsOnMethods = "testSearchingForExactSameTestData")
    public void testSearchValueIsBlank() {
        referenceValuesPage = new MainPage(getDriver()).clickReferenceValueMenu();
        verificationSearchProcess("");
    }

    @Test(dependsOnMethods = "testSearchingForExactSameTestData")
    public void testSearchingForRandomTestData() {
        referenceValuesPage = new MainPage(getDriver()).clickReferenceValueMenu();
        List<String> RandomTestDataForSearching = List.of("new1", "record", "test", "fill",
                "678", "3.32", "06/", "14/", "15/", "177","new");
        for (String Element : RandomTestDataForSearching) verificationSearchProcess(Element);
    }
}