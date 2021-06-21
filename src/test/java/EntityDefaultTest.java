import base.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import model.*;


public class EntityDefaultTest extends BaseTest {

    private static final String STRING_INPUT_VALUE = RandomStringUtils.randomAlphabetic(20);
    private static final String TEXT_INPUT_VALUE = RandomStringUtils.randomAlphabetic(15);
    private static final String INT_INPUT_VALUE = String.valueOf(RandomUtils.nextInt());
    private static final String DECIMAL_INPUT_VALUE = String.format("%.2f", new Random().nextFloat());
    private static final String DATE_VALUE = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    private static final String DATE_TIME_VALUE = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    private static final String USER_DEFAULT_NAME = "apptester1@tester.test";
    private static final String USER_NAME = "apptester10@tester.test";
    private static final String EMPTY_FIELD = "";
    private static final String EDIT_STRING_VALUE = RandomStringUtils.randomAlphabetic(20);
    private static final String EDIT_TEXT_VALUE = RandomStringUtils.randomAlphabetic(15);
    private static final String EDIT_INT_VALUE = String.valueOf(RandomUtils.nextInt());
    private static final String EDIT_DECIMAL_VALUE = String.format("%.2f", new Random().nextFloat());
    private static final String EDIT_DATE_VALUE = "31/12/1000";
    private static final String EDIT_DATE_TIME_VALUE = "31/12/3000 23:59:59";
    private static final String INFO_STR_1_OF_1 = "Showing 1 to 1 of 1 rows";
    private static final String INFO_STR_2_OF_2 = "Showing 1 to 2 of 2 rows";
    private static final String CLASS_ICON_SAVE = "fa fa-check-square-o";
    private static final String CLASS_ICON_SAVE_DRAFT = "fa fa-pencil";
    private static List<String> ACTUAL_LIST = new ArrayList<>();

    private static final List<String> NEW_EXPECTED_RESULT = List.of(
            STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
            DATE_VALUE, DATE_TIME_VALUE, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static List<WebElement> RECORD(WebDriver driver) {
        return List.of(driver.findElement(By.xpath("//div/span/a")));
    }

    private static final List<String> EDIT_RESULT = List.of(
            EDIT_STRING_VALUE, EDIT_TEXT_VALUE, EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE, EDIT_DATE_VALUE, EDIT_DATE_TIME_VALUE, EMPTY_FIELD, EMPTY_FIELD, USER_DEFAULT_NAME);

    private static final List<String> VIEW_RESULT = List.of(
            STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
            DATE_VALUE, DATE_TIME_VALUE, USER_DEFAULT_NAME);

    @Test
    public void testCreateRecord() {

        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillString(STRING_INPUT_VALUE)
                .fillDate(DATE_VALUE)
                .fillText(TEXT_INPUT_VALUE)
                .fillInt(INT_INPUT_VALUE)
                .fillDecimal(DECIMAL_INPUT_VALUE)
                .clickSave();

        Assert.assertEquals(DefaultPage.getRowCount(), 1);
        Assert.assertEquals(DefaultPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(DefaultPage.getClassIcon(), CLASS_ICON_SAVE);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testCancelDefaultRecord () {
        DefaultPage defaultPage = new MainPage(getDriver())
                .clickDefaultMenu()
                .clickNewButton()
                .clickCancel();

        Assert.assertEquals(DefaultPage.getRowCount(), 1);
        Assert.assertEquals(DefaultPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(DefaultPage.getClassIcon(), CLASS_ICON_SAVE);

    }

    @Test(dependsOnMethods = "testCancelDefaultRecord")
    public void testViewRecord() {
        DefaultViewPage defaultViewPage = new MainPage(getDriver())
            .clickDefaultMenu()
            .clickActions()
            .clickActionsView();

        ACTUAL_LIST = defaultViewPage.getRecordInViewMode();

        Assert.assertEquals(ACTUAL_LIST.size(), VIEW_RESULT.size());
        for (int i = 0; i < VIEW_RESULT.size(); i++) {
            Assert.assertEquals(ACTUAL_LIST.get(i), VIEW_RESULT.get(i).toString());
        }
    }
}
