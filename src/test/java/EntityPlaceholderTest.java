import base.BaseTest;
import model.MainPage;
import model.PlaceholderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static utils.ProjectUtils.getActualValues;
import static utils.TestUtils.jsClick;
import static utils.TestUtils.scrollClick;

public class EntityPlaceholderTest extends BaseTest {

    private static final By ICON = By.xpath("//tbody/tr/td/i");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By ACTUAL_RESULT = By.xpath("//tbody/tr/td/a");
    private static final By CHECK_MAIL = By.xpath("//tbody/tr/td[contains(text(),'tester99@tester.test')]");
    private static final By CHECK_ROW = By.xpath("//table[@id = 'pa-all-entities-table']");
    private static final String ICON_SAVE = "fa fa-check-square-o";

    private static final By STRING = By.id("string");
    private static final By TEXT = By.id("text");
    private static final By INT = By.id("int");
    private static final By DECIMAL = By.id("decimal");
    private static final By DATE = By.id("date");
    private static final By DATE_TIME = By.id("datetime");
    private static final String USER_NAME = getUser();
    private static String getUser() {
        return "tester" + new Random().nextInt(299) + "@tester.test";
    }
    private static final String STRING_VALUE = "Test 01";
    private static final String TEXT_VALUE = "first test";
    private static final String INT_VALUE = "1";
    private static final String DECIMAL_VALUE = "1.11";
    private static final String DATE_VALUE = "01/01/2021";
    private static final String DATETIME_VALUE = "01/01/2021 10:10:10";
    private static final String EMPTY_BIN = "Good job with housekeeping! Recycle bin is currently empty!";
    private static final List<String> EXPECTED_RESULT = List.of(
            STRING_VALUE, TEXT_VALUE, INT_VALUE, DECIMAL_VALUE, DATE_VALUE, DATETIME_VALUE);

    private static final List<String> NEW_EXPECTED_RESULT = List.of(
            STRING_VALUE, TEXT_VALUE, INT_VALUE, DECIMAL_VALUE, DATE_VALUE, DATETIME_VALUE, "", "", USER_NAME);
    private static final List<String> EDITED_RESULT =
            Arrays.asList("Test 02", "Second test", "2", "2.22", "02/02/2020", "02/02/2020 02:02:02");

    private void clickPlaceholderMenu() {
        scrollClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Placeholder')]")));
    }

    private void clickActionButton(String menuName){
        jsClick(getDriver(), findElement(By.xpath("//i[contains(text(), 'menu')]")));
        getWait().until(TestUtils.movingIsFinished(getDriver()
                .findElement(By.xpath(String.format("//li/a[contains (text(), '%s')]", menuName))))).click();
    }

    private void clickNotification() {
        getDriver().findElement(By.xpath("//span[@class = 'notification']")).click();
    }

    @Test
    public void testCreateNewRecord() {

        PlaceholderPage placeholderPage = new MainPage(getDriver())
                .clickPlaceholderMenu()
                .clickNewButton()
                .fillString(STRING_VALUE)
                .fillText(TEXT_VALUE)
                .fillInt(INT_VALUE)
                .fillDecimal(DECIMAL_VALUE)
                .fillDate(DATE_VALUE)
                .fillDateTime(DATETIME_VALUE)
                .fillUserField(USER_NAME)
                .clickSave();

        Assert.assertEquals(placeholderPage.getRowCount(), 1);
        Assert.assertEquals(placeholderPage.getRow(0), NEW_EXPECTED_RESULT);
        Assert.assertEquals(placeholderPage.getClassIcon(), ICON_SAVE);
        }

    @Test(dependsOnMethods = "testCreateNewRecord")
    public void testViewRecord() {
        clickPlaceholderMenu();
        clickActionButton("view");

        List<WebElement> resultList = findElements(By.xpath("//span[@class='pa-view-field']"));
        Assert.assertEquals(resultList.size(), EXPECTED_RESULT.size());
        for (int i = 0; i < resultList.size(); i++) {
            Assert.assertEquals(resultList.get(i).getText(), EXPECTED_RESULT.get(i));
            }

        Assert.assertEquals(findElement(By.xpath("//div[@class = 'form-group']/p")).getText(), USER_NAME);
        }

    @Test(dependsOnMethods = "testViewRecord")
    public void testEditRecord() {


        clickPlaceholderMenu();
        clickActionButton("edit");

        findElement(STRING).clear();
        findElement(STRING).sendKeys(EDITED_RESULT.get(0));
        findElement(TEXT).clear();
        findElement(TEXT).sendKeys(EDITED_RESULT.get(1));
        findElement(INT).clear();
        findElement(INT).sendKeys(EDITED_RESULT.get(2));
        findElement(DECIMAL).clear();
        findElement(DECIMAL).sendKeys(EDITED_RESULT.get(3));
        findElement(DATE).click();
        findElement(DATE).clear();
        findElement(DATE).sendKeys(EDITED_RESULT.get(4));
        findElement(DATE_TIME).click();
        findElement(DATE_TIME).clear();
        findElement(DATE_TIME).sendKeys(EDITED_RESULT.get(5));
        TestUtils.jsClick(getDriver(), getDriver().findElement(SAVE_BUTTON));

        List<WebElement> editedList = findElements(ACTUAL_RESULT);
        Assert.assertEquals(editedList.size(), EXPECTED_RESULT.size());
        for (int i = 0; i < editedList.size(); i++) {
            Assert.assertNotEquals(editedList.get(i).getText(), EXPECTED_RESULT.get(i));
            }
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {
        clickPlaceholderMenu();
        clickActionButton("delete");

        Assert.assertNull(findElement(By.className("card-body")).getAttribute("value"));

        clickNotification();

        Assert.assertEquals(findElement(By.xpath("//span[@class='pagination-info']"))
                .getText(), "Showing 1 to 1 of 1 rows");
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testRestoreDeletedRecord() {
        clickPlaceholderMenu();
        clickNotification();

        List<WebElement> binRows = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(binRows.size(), 1);

        getDriver().findElement(By.linkText("restore as draft")).click();
        Assert.assertEquals(findElement(By.className("card-body")).getText(), EMPTY_BIN);

        clickPlaceholderMenu();

        Assert.assertEquals(findElements(CHECK_ROW).size(), 1);
        Assert.assertEquals(findElement(ICON).getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_RESULT)), EDITED_RESULT);
//      Assert.assertEquals(findElement(CHECK_MAIL).getText(), USER_NAME);
    }

    @Test(dependsOnMethods = "testRestoreDeletedRecord")
    public void testDeleteExistingRecordPermanently() {
        clickPlaceholderMenu();
        clickActionButton("delete");
        clickNotification();

        List<WebElement> binRows = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(binRows.size(), 1);
        getDriver().findElement(By.linkText("delete permanently")).click();
        Assert.assertEquals(findElement(By.className("card-body")).getText(), EMPTY_BIN);

        clickPlaceholderMenu();

        Assert.assertNull(findElement(By.className("card-body")).getAttribute("value"));
    }
}