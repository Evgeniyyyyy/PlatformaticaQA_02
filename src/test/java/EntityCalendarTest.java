import base.BaseTest;
import model.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class EntityCalendarTest extends BaseTest {

    private static final String STRING_VALUE = "StringExampleCreateRecord";
    private static final String TEXT_VALUE = "TextExample";
    private static final String INT_VALUE = "1111";
    private static final String DECIMAL_VALUE = "456.98";
    private static final List<String> RESULT_RECORD_LIST = List.of(
            "StringExampleCreateRecord", "TextExample", "1111", "456.98");

    @Test
    public void testDeletedRecordFromBin(){

        RecycleBinPage recycleBin = new MainPage(getDriver())
                .clickCalendarMenu()
                .clickNewButton()
                .fillString(STRING_VALUE)
                .fillText(TEXT_VALUE)
                .fillDate()
                .fillDateTime()
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .clickSave()
                .listView()
                .clickActionDelete()
                .clickRecycleBin()
                .clickDeletedRecordPermanently();

        Assert.assertEquals(
                findElement(By.xpath("//*[@class='card-body']")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");

    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testViewRecord() {

        CalendarPage calendarPage = new MainPage(getDriver())
                .clickCalendarMenu()
                .viewRecord();

        Assert.assertEquals(findElement(By.xpath("//*[contains(text(), 'StringExa')]")).getText(),
                "StringExampleCreateRecord");
    }

    @Test
    public void testCreateRecord(){

        CalendarListPage calendarPage = new MainPage(getDriver())
                .clickCalendarMenu()
                .clickNewButton()
                .fillString(STRING_VALUE)
                .fillText(TEXT_VALUE)
                .fillDate()
                .fillDateTime()
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .clickSave()
                .listView();

        List<String> result = calendarPage.getRow(0);
        Assert.assertEquals(calendarPage.getRowCount(), 1);

        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(result.get(i), RESULT_RECORD_LIST.get(i));
        }
    }
}
