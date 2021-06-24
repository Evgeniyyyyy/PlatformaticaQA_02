import base.BaseTest;
import model.MainPage;
import model.RecycleBinPage;
import model.ReferenceValuesPage;
import org.apache.commons.lang3.time.DateUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EntityReferenceValues2Test extends BaseTest {

    private ReferenceValuesPage referenceValuesPage;
    private static List<List<String>> ListOfAllCreatedRecords;

    private ReferenceValuesPage createFillRecord(List<String> list)
    {
        return new MainPage(getDriver())
                .clickReferenceValueMenu()
                .clickCreateButton()
                .fillLabel(list.get(0))
                .fillFilter_1(list.get(1))
                .fillFilter_2(list.get(2))
                .clickSaveButton();
    }
    private ReferenceValuesPage editExistingRecord(List<String> list, String NameRecordWantToChange)
    {
        return new MainPage(getDriver())
                .clickReferenceValueMenu()
                .editRecord(NameRecordWantToChange)
                .clearFields()
                .fillLabel(list.get(0))
                .fillFilter_1(list.get(1))
                .fillFilter_2(list.get(2))
                .clickSaveButton();
    }

    @Test
    public void testDeleteRecordAndValidate() {
        ListOfAllCreatedRecords = List.of(
                List.of("first record","45",new SimpleDateFormat("dd/MM/yyyy").format(new Date())),
                List.of("second record","",new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(new Date(), 1))),
                List.of("third record","day",new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(new Date(), 2))));

        for(List<String> list: ListOfAllCreatedRecords)
            referenceValuesPage=createFillRecord(list);

        Assert.assertTrue(referenceValuesPage.isTableAvailable());
        Assert.assertEquals(referenceValuesPage.getTableRowsCount(),ListOfAllCreatedRecords.size());
        List<String> ExpectedResultList=new ArrayList<>();
        ListOfAllCreatedRecords.stream().reduce(ExpectedResultList,(sum, el)->{sum.addAll(el); return sum;});
        Assert.assertEquals(referenceValuesPage.getRowsValue(),ExpectedResultList);
        Assert.assertEquals(referenceValuesPage.getRowsValue(By.xpath("//td/i[@class='fa fa-check-square-o']")).size(),
                ListOfAllCreatedRecords.size());//verify left icon

        int OriginalNumberOfRecords=ListOfAllCreatedRecords.size();
        referenceValuesPage=referenceValuesPage.deleteRecord(ListOfAllCreatedRecords.get(1).get(0));
        OriginalNumberOfRecords--;
        Assert.assertEquals(referenceValuesPage.getRowsValueWithFilter(ListOfAllCreatedRecords.get(1).get(0)),0);
        Assert.assertEquals(referenceValuesPage.getTableRowsCount(), OriginalNumberOfRecords);
        Assert.assertEquals(referenceValuesPage.getNumberOfNotification(),ListOfAllCreatedRecords.size()
                - OriginalNumberOfRecords);

        referenceValuesPage=referenceValuesPage.deleteRecord(ListOfAllCreatedRecords.get(0).get(0));
        OriginalNumberOfRecords--;
        Assert.assertEquals(referenceValuesPage.getRowsValueWithFilter(ListOfAllCreatedRecords.get(0).get(0)),0);
        Assert.assertEquals(referenceValuesPage.getTableRowsCount(), OriginalNumberOfRecords);
        Assert.assertEquals(referenceValuesPage.getNumberOfNotification(),ListOfAllCreatedRecords.size()
                - OriginalNumberOfRecords);

        referenceValuesPage=referenceValuesPage.deleteRecord(ListOfAllCreatedRecords.get(2).get(0));
        OriginalNumberOfRecords--;
        Assert.assertEquals(referenceValuesPage.getRowsValueWithFilter(ListOfAllCreatedRecords.get(2).get(0)),0);
        Assert.assertEquals(referenceValuesPage.getTableRowsCount(), OriginalNumberOfRecords);
        Assert.assertEquals(referenceValuesPage.getNumberOfNotification(),ListOfAllCreatedRecords.size()
                - OriginalNumberOfRecords);
    }

    @Test(dependsOnMethods = "testDeleteRecordAndValidate")
    public void testVerifyDeletedRecordsInBin() {
        RecycleBinPage recycleBinPage=new MainPage(getDriver()).clickRecycleBin();
        Assert.assertTrue(referenceValuesPage.isTableAvailable());
        Assert.assertEquals(recycleBinPage.getRowCount(), ListOfAllCreatedRecords.size());
        Assert.assertEquals(recycleBinPage.getNumberOfNotification(),ListOfAllCreatedRecords.size());

        List<String> ExpectedResultList = ListOfAllCreatedRecords.stream().map(el -> el.get(0)).collect(Collectors.toList());
        Assert.assertTrue(ExpectedResultList.containsAll(recycleBinPage.getRowsValueByLabelOnly()));
    }

    @Test(dependsOnMethods = "testVerifyDeletedRecordsInBin")
    public void testPermanentDeleteRecordAndValidate() {
        RecycleBinPage recycleBinPage=new MainPage(getDriver()).clickRecycleBin();
        Assert.assertTrue(referenceValuesPage.isTableAvailable());

        int OriginalValueOfRecordsInBin = recycleBinPage.getRowCount();
        recycleBinPage = recycleBinPage.deleteRecord(ListOfAllCreatedRecords.get(1).get(0));
        OriginalValueOfRecordsInBin--;
        Assert.assertEquals(recycleBinPage.getRowsValuesWithFilter(ListOfAllCreatedRecords.get(1).get(0)),0);
        Assert.assertEquals(recycleBinPage.getRowCount(), OriginalValueOfRecordsInBin);
        Assert.assertEquals(recycleBinPage.getNumberOfNotification(),recycleBinPage.getRowCount());

        recycleBinPage = recycleBinPage.deleteRecord(ListOfAllCreatedRecords.get(0).get(0));
        OriginalValueOfRecordsInBin--;
        Assert.assertEquals(recycleBinPage.getRowsValuesWithFilter(ListOfAllCreatedRecords.get(0).get(0)),0);
        Assert.assertEquals(recycleBinPage.getRowCount(), OriginalValueOfRecordsInBin);
        Assert.assertEquals(recycleBinPage.getNumberOfNotification(),recycleBinPage.getRowCount());

        recycleBinPage = recycleBinPage.deleteRecord(ListOfAllCreatedRecords.get(2).get(0));
        OriginalValueOfRecordsInBin--;
        Assert.assertEquals(recycleBinPage.getRowsValuesWithFilter(ListOfAllCreatedRecords.get(2).get(0)),0);
        Assert.assertEquals(recycleBinPage.getRowCount(), OriginalValueOfRecordsInBin);
        Assert.assertEquals(recycleBinPage.getNumberOfNotification(),recycleBinPage.getRowCount());
    }

    @Test(dependsOnMethods = "testPermanentDeleteRecordAndValidate")
    public void testVerifyRecordsInReferencePageTable() {
        referenceValuesPage=new MainPage(getDriver())
                .clickReferenceValueMenu();
        Assert.assertTrue(!referenceValuesPage.isTableAvailable());
    }

    @Test
    public void testEditExistingRecord() {
        String LABEL_VALUE_1="first edition";
        String FIELD1_VALUE_1="35";
        String FIELD2_VALUE_1=new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String LABEL_VALUE_2="second edition";
        String FIELD1_VALUE_2="";
        String FIELD2_VALUE_2 = new SimpleDateFormat("dd/MM/yyyy").format(DateUtils.addDays(new Date(), 1));

        List<String> firstEdition=List.of(LABEL_VALUE_1,FIELD1_VALUE_1,FIELD2_VALUE_1);
        List<String> secondEdition=List.of(LABEL_VALUE_2,FIELD1_VALUE_2,FIELD2_VALUE_2);

        ReferenceValuesPage referenceValuesPage=createFillRecord(firstEdition);
        Assert.assertTrue(referenceValuesPage.isTableAvailable());
        Assert.assertEquals(referenceValuesPage.getTableRowsCount(),1);
        Assert.assertEquals(firstEdition,referenceValuesPage.getRowsValue(1));

        referenceValuesPage=editExistingRecord(secondEdition,LABEL_VALUE_1);
        Assert.assertTrue(referenceValuesPage.isTableAvailable());
        Assert.assertEquals(referenceValuesPage.getTableRowsCount(),1);
        Assert.assertEquals(secondEdition,referenceValuesPage.getRowsValue(1));
    }
}