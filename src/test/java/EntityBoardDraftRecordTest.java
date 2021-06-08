import base.DriverPerClassBaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import static utils.ProjectUtils.*;
import static utils.TestUtils.*;

public class EntityBoardDraftRecordTest extends DriverPerClassBaseTest {

    private static final By BOARD_TAB = By.xpath("//p[contains (text(), 'Board')]");
    private static final By ACTIONS_BUTTON = By.xpath("//button/i[text()='menu']");
    private static final By LIST_BUTTON = By.xpath(
            "//a[@href='index.php?action=action_list&list_type=table&entity_id=31']");

    private static final List<String> EXPECTED_CREATED_RECORD = List.of(
            "Pending", "q", "1", "0.12", "", "", "", "tester10@tester.test");

    private static final List<String> EXPECTED_EDITED_RECORD = List.of(
            "Pending", "q", "1", "0.22", "", "", "", "tester10@tester.test");

    private void createDraftRecord() {

        clickCreateRecord(getDriver());

        findElement(By.id("text")).sendKeys("q");
        findElement(By.id("int")).sendKeys("1");
        findElement(By.id("decimal")).sendKeys("0.12");
        findElement(By.xpath("//button[@data-id='string']")).click();

        jsClick(getDriver(), findElement(By.xpath("//select/option[text()='Pending']")));
        getWait().until(
                ExpectedConditions.invisibilityOf(findElement(By.xpath("//div[@class='dropdown-menu ']"))));

        scrollClick(getDriver(), findElement(By.xpath("//button[@data-id='user']")));
        jsClick(getDriver(), findElement(By.xpath("//span[text()='tester10@tester.test']")));

        clickSaveDraft(getDriver());
    }

    private List<String> getActualValues(List<WebElement> actualElements) {
        List<String> listValues = new ArrayList<>();
        for (WebElement element : actualElements) {
            listValues.add(element.getText());
        }
        return listValues;
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
        Assert.assertEquals(getActualValues(actualRecord), EXPECTED_CREATED_RECORD);
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testEditDraftRecord() {

        List<WebElement> actualRecord = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(actualRecord.size(), 1);

        WebElement rowsCountsText = findElement(By.xpath("//div/span[@class='pagination-info']"));
        Assert.assertEquals(rowsCountsText.getText(), "Showing 1 to 1 of 1 rows");

        findElement(ACTIONS_BUTTON).click();

        getWait().until(movingIsFinished(By.linkText("edit"))).click();

        findElement(By.id("decimal")).clear();
        findElement(By.id("decimal")).sendKeys("0.22");

        clickSaveDraft(getDriver());

        List<WebElement> actualEditedRecord = findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(getActualValues(actualEditedRecord), EXPECTED_EDITED_RECORD);
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
