import base.BaseTest;
import model.FieldsPage;
import model.MainPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static utils.ProjectUtils.*;
import static utils.TestUtils.jsClick;

public class EntityFieldsTest extends BaseTest {


    private static final String TITLE_VALUE = RandomStringUtils.randomAlphabetic(200);
    private static final String COMMENTS_VALUE = RandomStringUtils.randomAlphabetic(150);
    private static final String INT_VALUE = String.valueOf(RandomUtils.nextInt());
    private static final String DECIMAL_VALUE = String.format("%.2f", new Random().nextFloat());
    private static final String DATE_VALUE = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    private static final String DATE_TIME_VALUE = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    private static final String TESTER_NAME = "apptester1@tester.test";
    private static final String EDIT_TITLE_VALUE = RandomStringUtils.randomAlphabetic(100);
    private static final String EDIT_COMMENTS_VALUE = RandomStringUtils.randomAlphabetic(200);
    private static final String EDIT_INT_VALUE = String.valueOf(RandomUtils.nextInt());
    private static final String EDIT_DECIMAL_VALUE = String.format("%.2f", new Random().nextFloat());
    private static final String EDIT_DATE_VALUE = "01/01/0001";
    private static final String EDIT_DATE_TIME_VALUE = "31/12/9999 23:59:59";

    private static final List<String> NEW_EXPECTED_RESULT = List.of(
            TITLE_VALUE, COMMENTS_VALUE, INT_VALUE,
            DECIMAL_VALUE, DATE_VALUE, DATE_TIME_VALUE, "", TESTER_NAME, "");

    private static final List<String> EDIT_RESULT = List.of(
            EDIT_TITLE_VALUE, EDIT_COMMENTS_VALUE, EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE, EDIT_DATE_VALUE, EDIT_DATE_TIME_VALUE, "", TESTER_NAME, "");

    private static final List<String> VIEW_RESULT = List.of(
            "", EDIT_TITLE_VALUE, EDIT_COMMENTS_VALUE, EDIT_INT_VALUE,
            EDIT_DECIMAL_VALUE, EDIT_DATE_VALUE, EDIT_DATE_TIME_VALUE, TESTER_NAME);

    private static final By ACTUAL_RESULT = By.xpath("//tbody/tr/td/a");

    @Test
    public void testCreateRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillTitle(TITLE_VALUE)
                .fillDate(DATE_VALUE)
                .fillComments(COMMENTS_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .clickSave();

        Assert.assertEquals(fieldsPage.getRowCount(), 1);
        Assert.assertEquals(fieldsPage.getRow(0), NEW_EXPECTED_RESULT);
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testEditRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickEdit()
                .fillDateTime(EDIT_DATE_TIME_VALUE)
                .fillTitle(EDIT_TITLE_VALUE)
                .fillDate(EDIT_DATE_VALUE)
                .fillComments(EDIT_COMMENTS_VALUE)
                .fillInt(EDIT_INT_VALUE)
                .fillDecimal(EDIT_DECIMAL_VALUE)
                .clickSave();

        Assert.assertEquals(fieldsPage.getRow(0), EDIT_RESULT);
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testReorderRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .clickNewButton()
                .fillDateTime(DATE_TIME_VALUE)
                .fillTitle(TITLE_VALUE)
                .fillDate(DATE_VALUE)
                .fillComments(COMMENTS_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .clickSave();

        fieldsPage.clickOrder();
        Assert.assertEquals(fieldsPage.getRow(0), EDIT_RESULT);

        fieldsPage.getReorder();
        Assert.assertEquals(fieldsPage.getRow(0), NEW_EXPECTED_RESULT);

        fieldsPage.clickToggle();
        fieldsPage.getNewReorder();
        Assert.assertEquals(fieldsPage.getRows(0), VIEW_RESULT);

    }

    @Ignore
    @Test(dependsOnMethods = "testReorderRecord")
    public void testSearchCreatedRecord() {

        FieldsPage fieldsPage = new MainPage(getDriver())
                .clickFieldsMenu()
                .searchInput(TITLE_VALUE);
//        getEntity(getDriver(), "Fields");
//        findElement(By.xpath("//input[@placeholder = 'Search']")).sendKeys(EXPECTED_RESULT.get(0));

        getWait().until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//span[@class='pagination-info']"), "Showing 1 to 1 of 1 rows"));

        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), NEW_EXPECTED_RESULT);
    }

    @Ignore
    @Test
    public void deletePermanentlyRecordFromRecycleBin() {
        jsClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Fields ']")));
        clickCreateRecord(getDriver());
        clickSave(getDriver());
        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']")));
        jsClick(getDriver(), getDriver().findElement(By.xpath("//a[contains(@href,'delete')]")));
        getDriver().findElement(By.partialLinkText("delete_outline")).click();
        getDriver().findElement(By.partialLinkText("delete permanently")).click();

        Assert.assertEquals(getDriver().findElement(
                By.xpath("//*[@class='card-body']")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");
    }
}
