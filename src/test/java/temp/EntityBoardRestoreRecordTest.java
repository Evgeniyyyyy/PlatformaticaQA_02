package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.*;
import static utils.TestUtils.jsClick;

public class EntityBoardRestoreRecordTest extends BaseTest {

    private static final By INTEGER_FIELD = By.id("int");
    private static final By TEXT_FIELD = By.id("text");
    private static final By DECIMAL_FIELD = By.id("decimal");

    private static final String STRING_INPUT_VALUE = "Pending";
    private static final String INT_INPUT_VALUE = "12345";
    private static final String TEXT_INPUT_VALUE = "qwerty";
    private static final String DECIMAL_INPUT_VALUE = "12.45";
    private static final String USER_NAME = "tester10@tester.test";
    private static final String EMPTY_FIELD = "";
    private static final List<String> expectedValues = Arrays.asList(
            STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
            EMPTY_FIELD, EMPTY_FIELD, EMPTY_FIELD, USER_NAME
    );

    private void clickListButton(WebDriver driver) {
        driver.findElement(By.xpath(
                "//a[@href='index.php?action=action_list&list_type=table&entity_id=31']")).click();
    }

    private void clickActionsDelete(WebDriver driver) {
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

    @Test
    public void testCreateDraftRecord() {

        getEntity(getDriver(), "Board");
        fillForm();
        clickListButton(getDriver());

        WebElement icon = findElement(By.xpath("//tbody/tr/td/i"));

        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");

        List<WebElement> actualRecord = findElements(By.xpath("//td[@class='pa-list-table-th']"));

        Assert.assertEquals(getActualValues(actualRecord), expectedValues);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testDeleteDraftRecord() {

        getEntity(getDriver(), "Board");
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
    public void testRestoreDeleted() {

        clickRecycleBin(getDriver());
        String textRecycleBinBeforeRestore = getDriver().findElement(
                By.xpath("//a[@href='index.php?action=recycle_bin']")).getText();
        findElement(By.linkText("restore as draft")).click();
        WebElement message = findElement(
                By.xpath("//div[@class='card-body']"));

        Assert.assertEquals(message.getText(), "Good job with housekeeping! Recycle bin is currently empty!");

        getEntity(getDriver(), "Board");
        clickListButton(getDriver());
        List<WebElement> actualEditedRecord = findElements(By.xpath("//td[@class='pa-list-table-th']"));

        Assert.assertEquals(getActualValues(actualEditedRecord), expectedValues);
        Assert.assertEquals(findElement(By.xpath("//td[1]/i")).getAttribute("class"), "fa fa-pencil");

        String textRecycleBinAfterRestore = getDriver().findElement(
                By.xpath("//a[@href='index.php?action=recycle_bin']")).getText();

        Assert.assertNotEquals(textRecycleBinBeforeRestore, textRecycleBinAfterRestore);
    }
}
