import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static utils.ProjectUtils.*;
import static utils.TestUtils.*;

@Ignore
public class EntityBoardDraftRecordTest extends BaseTest {

    private static final By BOARD_TAB = By.xpath("//p[contains (text(), 'Board')]");
    private static final By ACTIONS_BUTTON = By.xpath("//button/i[text()='menu']");
    private static final By LIST_BUTTON = By.xpath(
            "//a[@href='index.php?action=action_list&list_type=table&entity_id=31']");
    private static final By INTEGER_FIELD  = By.id("int");
    private static final By TEXT_FIELD  = By.id("text");
    private static final By DECIMAL_FIELD  = By.id("decimal");

    private final String stringInputValue = "Pending";
    private final String intInputValue = "12345";
    private final String textInputValue = "qwerty";
    private final String decimalInputValue = "12.45";
    private final String newDecimalInputValue = "0.22";
    private final String userName = "tester10@tester.test";
    private final String emptyField = "";

    final List<Object> expectedCreatedRecord = Arrays
            .asList(stringInputValue, textInputValue, intInputValue, decimalInputValue,
                    emptyField, emptyField, emptyField, userName);

    final List<Object> expectedEditedRecord = Arrays
            .asList(stringInputValue, textInputValue, intInputValue, newDecimalInputValue,
                    emptyField, emptyField, emptyField, userName);

    private void createDraftRecord() {

        clickCreateRecord(getDriver());

        sendKeysOneByOne(findElement(INTEGER_FIELD), intInputValue);
        sendKeysOneByOne(findElement(TEXT_FIELD), textInputValue);
        sendKeysOneByOne(findElement(DECIMAL_FIELD), decimalInputValue);

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

    private List<String> getActualValues(List<WebElement> actualElements) {
        List<String> listValues = new ArrayList<>();
        for (WebElement element : actualElements) {
            listValues.add(element.getText());
        }
        return listValues;
    }

    private void editDraftRecord(){
        findElement(ACTIONS_BUTTON).click();
        getWait().until(movingIsFinished(By.linkText("edit"))).click();

        findElement(DECIMAL_FIELD).click();
        findElement(DECIMAL_FIELD).clear();
        sendKeysOneByOne(findElement(DECIMAL_FIELD), newDecimalInputValue);

        clickSaveDraft(getDriver());
    }

    private void deleteDraftRecord() {

        findElement(LIST_BUTTON).click();

        findElement(ACTIONS_BUTTON).click();
        getWait().until(movingIsFinished(By.linkText("delete"))).click();
    }

    @Test
    public void testCreateDraftRecord() {

        start(getDriver());

        jsClick(getDriver(), findElement(BOARD_TAB));

        createDraftRecord();

        findElement(LIST_BUTTON).click();

        WebElement icon = findElement(By.xpath("//tbody/tr/td/i"));
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");

        List<WebElement> actualRecord = findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(getActualValues(actualRecord), expectedCreatedRecord);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testEditDraftRecord() {

        List<WebElement> actualRecord = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(actualRecord.size(), 1);

        WebElement rowsCountsText = findElement(By.xpath("//div/span[@class='pagination-info']"));
        Assert.assertEquals(rowsCountsText.getText(), "Showing 1 to 1 of 1 rows");

        editDraftRecord();

        List<WebElement> actualEditedRecord = findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(getActualValues(actualEditedRecord), expectedEditedRecord);
    }

    @Test(dependsOnMethods = "testEditDraftRecord")
    public void testDeleteDraftRecord() {

        deleteDraftRecord();

        clickRecycleBin(getDriver());

        WebElement checkNotification = findElement(By.xpath("//a/span[@class='notification']"));
        Assert.assertEquals(checkNotification.getText(), "1");

        List<WebElement> actualRecord = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(actualRecord.size(), 1);
    }

    @Test(dependsOnMethods = "testDeleteDraftRecord")
    public void testDeleteRecordFromRecycleBin() {

        WebElement header = findElement(By.xpath("//a[@class='navbar-brand']"));
        Assert.assertEquals(header.getText(), "Recycle Bin");

        List<WebElement> actualRecord = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(actualRecord.size(), 1);

        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("delete permanently"))).click();

        Assert.assertEquals(findElement(By.className("card-body")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");
    }
}
