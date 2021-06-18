import base.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.*;

public class EntityBoardDraftRecordTest extends BaseTest {

    private static final By TEXT_FIELD = By.id("text");
    private static final By INTEGER_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By ACTUAL_SEARCH_RECORD = By.xpath("//tbody/tr/td/a");

    private static final String ENTITY_NAME = "Board";
    private static final String STRING_INPUT_PENDING = "Pending";
    private static final String STRING_INPUT_ONTRACK = "On track";
    private static final String STRING_INPUT_DONE = "Done";
    private static final String USER_NAME = "tester10@tester.test";

    private static final List<String> EXPECTED_CREATED_PENDING_RECORD = makeRandomData(STRING_INPUT_PENDING);
    private static final List<String> EXPECTED_CREATED_ONTRACK_RECORD = makeRandomData(STRING_INPUT_ONTRACK);
    private static final List<String> EXPECTED_CREATED_DONE_RECORD = makeRandomData(STRING_INPUT_DONE);
    private static final List<List> ALL_RECORDS_TABLE = new ArrayList<>(List.of(EXPECTED_CREATED_PENDING_RECORD,
            EXPECTED_CREATED_ONTRACK_RECORD, EXPECTED_CREATED_DONE_RECORD));

    static class CompareByText implements Comparator<List> {

        @Override
        public int compare(List r1, List r2) {
            String text1 = r1.get(1).toString();
            String text2 = r2.get(1).toString();
            return text1.compareTo(text2);
        }
    }

    private static List<String> makeRandomData(String dropDownItem) {
        List<String> randomData = new ArrayList<>();

        randomData.add(dropDownItem);
        randomData.add(RandomStringUtils.randomAlphabetic(8));
        randomData.add("" + RandomUtils.nextInt(0, 100000));
        randomData.add(RandomUtils.nextInt(0, 10000) + "." + RandomStringUtils.randomNumeric(2));

        randomData.add("");
        randomData.add("");
        randomData.add("");

        randomData.add(USER_NAME);

        return randomData;
    }

    private void clickListButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//a[@href='index.php?action=action_list&list_type=table&entity_id=31']")));
        findElement(By.xpath(
                "//a[@href='index.php?action=action_list&list_type=table&entity_id=31']")).click();
    }

    private void fillFormFields(List<String> data) {
        getWait().until(ExpectedConditions.elementToBeClickable(TEXT_FIELD));
        findElement(TEXT_FIELD).click();
        findElement(TEXT_FIELD).sendKeys(data.get(1));
        getWait().until(ExpectedConditions.attributeToBe(findElement(TEXT_FIELD),"value", data.get(1)));
        findElement(INTEGER_FIELD).sendKeys(data.get(2));
        getWait().until(ExpectedConditions.attributeToBe(findElement(INTEGER_FIELD),"value", data.get(2)));
        findElement(DECIMAL_FIELD).sendKeys(data.get(3));
    }

    private void fillDropDownFields(String dropDownItem) {
        WebElement stringButton = findElement(By.xpath("//button[@data-id='string']"));
        stringButton.click();

        jsClick(getDriver(),
                findElement(By.xpath("//ul[@class='dropdown-menu inner show']//span[contains(.,'" + dropDownItem + "')]")));
        getWait().until(
                ExpectedConditions.invisibilityOf(findElement(By.xpath("//div[@class='dropdown-menu']"))));

        Assert.assertEquals(stringButton.getAttribute("title"), dropDownItem);

        scrollClick(getDriver(), findElement(By.xpath("//button[@data-id='user']")));
        jsClick(getDriver(), findElement(By.xpath("//span[text()='tester10@tester.test']")));
    }

    private void checkRecordInViewMode(List<String> data) {
        List<WebElement> actualRecords = findElements(By.xpath("//span [@class = 'pa-view-field']"));

        for (int i = 0; i < actualRecords.size(); i++) {
            Assert.assertEquals(actualRecords.get(i).getText(), data.get(i));
        }
        WebElement user = findElement(By.xpath("//div[@class = 'form-group']/p"));
        Assert.assertEquals(user.getText(), data.get(7));
    }

    private String getCardBodyText() {

        return findElement(By.xpath("//div[@class = 'card-body ']")).getText();
    }

    @Test
    public void testCreateDraftRecord() {

        getEntity(getDriver(), ENTITY_NAME);

        clickCreateRecord(getDriver());
        fillDropDownFields(STRING_INPUT_PENDING);
        fillFormFields(EXPECTED_CREATED_PENDING_RECORD);
        clickSaveDraft(getDriver());

        clickListButton();

        WebElement icon = findElement(By.xpath("//tbody/tr/td/i"));
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");

        List<WebElement> actualRecord = findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(getActualValues(actualRecord), EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testViewDraftRecord() {

        getEntity(getDriver(), ENTITY_NAME);
        clickListButton();

        WebElement iconCheckBox = findElement(By.xpath("//tbody/tr/td/i"));
        Assert.assertEquals(iconCheckBox.getAttribute("class"), "fa fa-pencil");

        clickActionsView(getWait(), getDriver());

        checkRecordInViewMode(EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testViewDraftRecord")
    public void testEditDraftRecord() {

        getEntity(getDriver(), ENTITY_NAME);
        clickListButton();

        List<WebElement> actualRecord = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(actualRecord.size(), 1);

        clickActionsEdit(getWait(), getDriver());

        findElement(TEXT_FIELD).click();
        findElement(TEXT_FIELD).clear();
        findElement(INTEGER_FIELD).click();
        findElement(INTEGER_FIELD).clear();
        findElement(DECIMAL_FIELD).click();
        findElement(DECIMAL_FIELD).clear();

        fillFormFields(EXPECTED_CREATED_PENDING_RECORD);

        clickSaveDraft(getDriver());

        List<WebElement> actualEditedRecord = findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(getActualValues(actualEditedRecord), EXPECTED_CREATED_PENDING_RECORD);
    }

    @Test(dependsOnMethods = "testEditDraftRecord")
    public void testDeleteDraftRecord() {

        getEntity(getDriver(), ENTITY_NAME);

        clickListButton();

        clickActionsDelete(getWait(), getDriver());

        Assert.assertEquals(getCardBodyText(),"");

        clickRecycleBin(getDriver());

        WebElement checkNotification = findElement(By.xpath("//a/span[@class='notification']"));
        Assert.assertEquals(checkNotification.getText(), "1");

        List<WebElement> actualRecord = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(actualRecord.size(), 1);
    }

    @Test(dependsOnMethods = "testDeleteDraftRecord")
    public void testDeleteRecordFromRecycleBin() {

        clickRecycleBin(getDriver());

        WebElement header = findElement(By.xpath("//a[@class='navbar-brand']"));
        Assert.assertEquals(header.getText(), "Recycle Bin");

        getWait().until(ExpectedConditions.elementToBeClickable(By
                .xpath("//td[@class='pa-recycle-col']/a"))).click();

        checkRecordInViewMode(EXPECTED_CREATED_PENDING_RECORD);

        findElement(By.xpath("//button[contains(.,'clear')]")).click();

        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("delete permanently"))).click();

        Assert.assertEquals(findElement(By.className("card-body")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");
    }

    @Test
    public void testSortRecords() {

        List<String> expectedSortedRecords = new ArrayList<>();
        Collections.sort(ALL_RECORDS_TABLE, new CompareByText());

        for (List list : ALL_RECORDS_TABLE) {
            for (Object el : list) {
                expectedSortedRecords.add(String.valueOf(el));
            }
        }

        getEntity(getDriver(), ENTITY_NAME);

        clickCreateRecord(getDriver());
        fillDropDownFields(STRING_INPUT_PENDING);
        fillFormFields(EXPECTED_CREATED_PENDING_RECORD);
        clickSaveDraft(getDriver());

        clickCreateRecord(getDriver());
        fillDropDownFields(STRING_INPUT_ONTRACK);
        fillFormFields(EXPECTED_CREATED_ONTRACK_RECORD);
        clickSaveDraft(getDriver());

        clickCreateRecord(getDriver());
        fillDropDownFields(STRING_INPUT_DONE);
        fillFormFields(EXPECTED_CREATED_DONE_RECORD);
        clickSaveDraft(getDriver());

        clickListButton();

        Assert.assertEquals(findElements(By.xpath("//tbody/tr")).size(), ALL_RECORDS_TABLE.size());

        jsClick(getDriver(), findElement(By.xpath("//th/div[text()='Text']")));

        List<WebElement> actualRecordsTable = findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(getActualValues(actualRecordsTable), expectedSortedRecords);
    }

    @Test(dependsOnMethods = "testSortRecords")
    public void testSearchDraftRecord() {

        final By searchField = By.xpath("//input[contains(@class, 'search-input')]");

        getEntity(getDriver(), ENTITY_NAME);

        clickListButton();

        Assert.assertEquals(findElements(By.xpath("//tbody/tr")).size(), ALL_RECORDS_TABLE.size());

        findElement(searchField).sendKeys(STRING_INPUT_PENDING);

        getWait().until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//span[@class='pagination-info']"), "Showing 1 to 1 of 1 rows"));

        for (int i = 0; i < findElements(ACTUAL_SEARCH_RECORD).size(); i++) {
            Assert.assertEquals(findElements(ACTUAL_SEARCH_RECORD).get(i).getText(),
                    EXPECTED_CREATED_PENDING_RECORD.get(i));
        }
    }

    @Test
    public void testCancelRecord(){

        getEntity(getDriver(), ENTITY_NAME);
        final String textCardBodyBeforeCancel = getCardBodyText();

        clickCreateRecord(getDriver());
        fillDropDownFields(STRING_INPUT_PENDING);
        fillFormFields(EXPECTED_CREATED_PENDING_RECORD);

        Assert.assertFalse(findElement(TEXT_FIELD).getAttribute("value").isEmpty());
        Assert.assertFalse(findElement(INTEGER_FIELD).getAttribute("value").isEmpty());
        Assert.assertFalse(findElement(DECIMAL_FIELD).getAttribute("value").isEmpty());

        clickCancel(getDriver());

        final String textCardBodyAfterCancel = getCardBodyText();
        Assert.assertEquals(textCardBodyAfterCancel, textCardBodyBeforeCancel);
    }
}
