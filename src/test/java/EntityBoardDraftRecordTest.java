import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.Arrays;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.*;

public class EntityBoardDraftRecordTest extends BaseTest {

    private static final By INTEGER_FIELD  = By.id("int");
    private static final By TEXT_FIELD  = By.id("text");
    private static final By DECIMAL_FIELD  = By.id("decimal");
    private static final By ACTUAL_SEARCH_RECORD = By.xpath("//tbody/tr/td/a");

    private static final String STRING_INPUT_VALUE = "Pending";
    private static final String INT_INPUT_VALUE = "12345";
    private static final String TEXT_INPUT_VALUE = "qwerty";
    private static final String DECIMAL_INPUT_VALUE = "12.45";
    private static final String NEW_DECIMAL_INPUT_VALUE = "0.22";
    private static final String USER_NAME = "tester10@tester.test";
    private static final String EMPTY_FIELD = "";

    private void clickListButton(WebDriver driver) {
        driver.findElement(By.xpath(
                "//a[@href='index.php?action=action_list&list_type=table&entity_id=31']")).click();
    }

    private  void clickActionsView(WebDriver driver) {
        driver.findElement(By.xpath("//button/i[text()='menu']")).click();
        getWait().until(TestUtils.movingIsFinished(By.linkText("view"))).click();
    }

    private  void clickActionsEdit(WebDriver driver) {
        driver.findElement(By.xpath("//button/i[text()='menu']")).click();
        getWait().until(movingIsFinished(By.linkText("edit"))).click();
    }

    private  void clickActionsDelete(WebDriver driver) {
        driver.findElement(By.xpath("//button/i[text()='menu']")).click();
        getWait().until(movingIsFinished(By.linkText("delete"))).click();
    }

    private void fillForm() {
        clickCreateRecord(getDriver());

        sendKeysOneByOne(findElement(INTEGER_FIELD), INT_INPUT_VALUE);
        sendKeysOneByOne(findElement(TEXT_FIELD), TEXT_INPUT_VALUE);
        sendKeysOneByOne(findElement(DECIMAL_FIELD), DECIMAL_INPUT_VALUE);

        findElement(By.xpath("//button[@data-id='string']")).click();
        jsClick(getDriver(), findElement(By.xpath("//select/option[text()='Pending']")));
        getWait().until(
                ExpectedConditions.invisibilityOf(findElement(By.xpath("//div[@class='dropdown-menu ']"))));

        scrollClick(getDriver(), findElement(By.xpath("//button[@data-id='user']")));
        jsClick(getDriver(), findElement(By.xpath("//span[text()='tester10@tester.test']")));

        clickSaveDraft(getDriver());
    }

    private void sendKeysOneByOne(WebElement element, String input) {
        char[] editKeys = input.toCharArray();
        for (char c : editKeys) {
            element.sendKeys(String.valueOf(c));
        }
    }

    private void editDraftRecord(){
        clickActionsEdit(getDriver());

        findElement(DECIMAL_FIELD).click();
        findElement(DECIMAL_FIELD).clear();
        sendKeysOneByOne(findElement(DECIMAL_FIELD), NEW_DECIMAL_INPUT_VALUE);

        clickSaveDraft(getDriver());
    }

    @Test
    public void testCreateDraftRecord() {

        final List<String> expectedCreatedRecord = Arrays
                .asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
                        EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_NAME);

        getEntity(getDriver(),"Board");

        fillForm();

        clickListButton(getDriver());

        WebElement icon = findElement(By.xpath("//tbody/tr/td/i"));
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");

        List<WebElement> actualRecord = findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(getActualValues(actualRecord), expectedCreatedRecord);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testViewDraftRecord() {

        final List<String> expectedViewedRecord = Arrays
                .asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
                        EMPTY_FIELD, EMPTY_FIELD);

        getEntity(getDriver(),"Board");
        clickListButton(getDriver());

        WebElement iconCheckBox = findElement(By.xpath("//tbody/tr/td/i"));
        Assert.assertEquals(iconCheckBox.getAttribute("class"), "fa fa-pencil");

        clickActionsView(getDriver());

        List<WebElement> actualRecord = findElements(By.xpath("//span[@class='pa-view-field']"));
        WebElement actualUser = findElement(By.xpath("//div[@class='form-group']/p"));

        Assert.assertEquals(getActualValues(actualRecord), expectedViewedRecord);
        Assert.assertEquals(actualUser.getText(), USER_NAME);
    }

    @Test(dependsOnMethods = "testViewDraftRecord")
    public void testEditDraftRecord() {

        final List<String> expectedEditedRecord = Arrays
                .asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, NEW_DECIMAL_INPUT_VALUE,
                        EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_NAME);

        getEntity(getDriver(),"Board");
        clickListButton(getDriver());

        List<WebElement> actualRecord = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(actualRecord.size(), 1);

        editDraftRecord();

        List<WebElement> actualEditedRecord = findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(getActualValues(actualEditedRecord), expectedEditedRecord);
    }

    @Test(dependsOnMethods = "testEditDraftRecord")
    public void testDeleteDraftRecord() {

        getEntity(getDriver(),"Board");

        clickListButton(getDriver());

        clickActionsDelete(getDriver());

        String textCardBodyAfterDelete = findElement(By.xpath("//div[@class = 'card-body ']")).getText();
        Assert.assertTrue(textCardBodyAfterDelete.isEmpty());

        clickRecycleBin(getDriver());

        WebElement checkNotification = findElement(By.xpath("//a/span[@class='notification']"));
        Assert.assertEquals(checkNotification.getText(), "1");

        List<WebElement> actualRecord = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(actualRecord.size(), 1);
    }

    @Test(dependsOnMethods = "testDeleteDraftRecord")
    public void testDeleteRecordFromRecycleBin() {

        final List<String> expectedDelitedRecord = Arrays
                .asList(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, NEW_DECIMAL_INPUT_VALUE,
                        EMPTY_FIELD, EMPTY_FIELD);

        clickRecycleBin(getDriver());

        WebElement header = findElement(By.xpath("//a[@class='navbar-brand']"));
        Assert.assertEquals(header.getText(), "Recycle Bin");

        getWait().until(ExpectedConditions.elementToBeClickable(By
                .xpath("//td[@class='pa-recycle-col']/a"))).click();

        List<WebElement> actualRecord = findElements(By.xpath("//span[@class='pa-view-field']"));
        WebElement actualUser = findElement(By.xpath("//div[@class='form-group']/p"));
        Assert.assertEquals(getActualValues(actualRecord), expectedDelitedRecord);
        Assert.assertEquals(actualUser.getText(), USER_NAME);

        findElement(By.xpath("//button[contains(.,'clear')]")).click();

        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("delete permanently"))).click();

        Assert.assertEquals(findElement(By.className("card-body")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");
    }

    @Test
    public void testSearchDraftRecord(){

        final By searchField = By.xpath("//input[contains(@class, 'search-input')]");
        final List<String> expectedFoundRecord1 = Arrays
                .asList(STRING_INPUT_VALUE, "books", "52", "5.36",
                        EMPTY_FIELD, EMPTY_FIELD);
        final List<String> expectedFoundRecord2 = Arrays
                .asList(STRING_INPUT_VALUE, "magazines", "199", "1.78",
                        EMPTY_FIELD, EMPTY_FIELD);
        final String searchTextValue1 = "books";
        final String searchTextValue2 = "magazines";

        getEntity(getDriver(),"Board");

        clickCreateRecord(getDriver());

        sendKeysOneByOne(findElement(INTEGER_FIELD), "52");
        sendKeysOneByOne(findElement(TEXT_FIELD), "books");
        sendKeysOneByOne(findElement(DECIMAL_FIELD), "5.36");
        clickSaveDraft(getDriver());

        clickCreateRecord(getDriver());

        sendKeysOneByOne(findElement(INTEGER_FIELD), "199");
        sendKeysOneByOne(findElement(TEXT_FIELD), "magazines");
        sendKeysOneByOne(findElement(DECIMAL_FIELD), "1.78");
        clickSaveDraft(getDriver());

        clickListButton(getDriver());

        Assert.assertEquals(findElements(By.xpath("//tbody/tr")).size(), 2);

        findElement(searchField).sendKeys(searchTextValue1);

        getWait().until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//span[@class='pagination-info']"), "Showing 1 to 1 of 1 rows"));

        Assert.assertEquals(getActualValues(findElements(ACTUAL_SEARCH_RECORD)), expectedFoundRecord1);

        findElement(searchField).clear();

        getWait().until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//span[@class='pagination-info']"), "Showing 1 to 2 of 2 rows"));

        findElement(searchField).sendKeys(searchTextValue2);
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//span[@class='pagination-info']"), "Showing 1 to 1 of 1 rows"));

        Assert.assertEquals(getActualValues(findElements(ACTUAL_SEARCH_RECORD)), expectedFoundRecord2);
    }
}
