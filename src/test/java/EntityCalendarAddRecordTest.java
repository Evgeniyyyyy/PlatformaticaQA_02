import base.BaseTest;
import model.CalendarListPage;
import model.CalendarPage;
import model.MainPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class EntityCalendarAddRecordTest extends BaseTest {

    private static final String STRING_VALUE = "Hello";
    private static final String TEXT_VALUE = "World";
    private static final String INT_VALUE = "22";
    private static final String DECIMAL_VALUE = "22.22";
    private static final String DATA_VALUE = "22/05/2022";
    private static final String DATA_TIME_VALUE = "27/05/2022 11:35:35";
    private static final String DATA_FILE = "";
    private static final String DATA_USER = "apptester1@tester.test";
    private static final List<String> expectedList = Arrays.asList(STRING_VALUE, TEXT_VALUE, INT_VALUE, DECIMAL_VALUE, DATA_VALUE, DATA_TIME_VALUE, DATA_FILE, DATA_USER);

    @Test
    public void testCreateRecord() {
        CalendarListPage calendarListPage = new MainPage(getDriver())
                .clickCalendarMenu()
                .clickNewButton()
                .fillString(STRING_VALUE)
                .fillText(TEXT_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .fillDate(DATA_VALUE)
                .fillDateTime(DATA_TIME_VALUE)
                .clickSave()
                .listView();

        Assert.assertEquals(calendarListPage.tableRecords().size(),1);
        Assert.assertEquals(calendarListPage.actualResult(), expectedList);
    }
}