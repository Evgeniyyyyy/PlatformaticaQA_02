import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;
import java.util.List;
import static utils.ProjectUtils.*;

public class EntityBoardDraftRecordTest extends BaseTest {

    private static final List<String> EXPECTED_CREATED_RECORD = List.of(
            "Pending", "qw", "1", "0.12", "", "", "", "tester10@tester.test");

    private void createDraftRecord() {
        ProjectUtils.start(getDriver());

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Board')]")));

        clickCreateRecord(getDriver());

        findElement(By.xpath("//button[@data-id='string']")).click();

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//select/option[text()='Pending']")));

        TestUtils.scrollClick(getDriver(), findElement(By.xpath("//button[@data-id='user']")));

        TestUtils.jsClick(getDriver(), getDriver().findElement(
                By.xpath("//span[text()='tester10@tester.test']")));

        findElement(By.id("text")).sendKeys("qw");

        findElement(By.id("int")).sendKeys("1");

        findElement(By.id("decimal")).sendKeys("0.12");

        clickSaveDraft(getDriver());
    }

    private void deleteDraftRecord() {
        findElement(By.xpath("//a[@href='index.php?action=action_list&list_type=table&entity_id=31']"))
                .click();

        findElement(By.xpath("//div[@class='dropdown pull-left']")).click();

        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("delete"))).click();
    }

    @Ignore
    @Test
    public void testEditDraftRecord() {

        createDraftRecord();

        final List<String> excpectedEditedRecord = List.of(
                "Pending", "qw", "1", "0.22", "", "", "", "tester10@tester.test");

        WebElement headerBoard = findElement(By.xpath("//div[@class='d-flex justify-content-between']/h3"));
        Assert.assertEquals(headerBoard.getText(), "Board");

        findElement(By.xpath("//a[@href='index.php?action=action_list&list_type=table&entity_id=31']")).click();

        WebElement icon = findElement(By.xpath("//tbody/tr/td/i"));
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");

        findElement(By.xpath("//div[@class='dropdown pull-left']")).click();

        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("edit"))).click();

        findElement(By.id("decimal")).clear();
        findElement(By.id("decimal")).sendKeys("0.22");

        clickSaveDraft(getDriver());

        List<WebElement> rowValues = findElements(By.xpath("//td[@class='pa-list-table-th']/a"));
        for (int i = 0; i < rowValues.size(); i++) {
            Assert.assertEquals(rowValues.get(i).getText(), excpectedEditedRecord.get(i));
        }
    }

    @Ignore
    @Test
    public void testDeleteDraftRecord() {

        createDraftRecord();

        deleteDraftRecord();

        clickRecycleBin(getDriver());

        WebElement checkNotification = findElement(By.xpath("//a/span[@class='notification']"));
        Assert.assertEquals(checkNotification.getText(), "1");

        getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tbody/tr")));

        List<WebElement> recordValues = findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        for (int i = 0; i < recordValues.size(); i++) {
            Assert.assertEquals(recordValues.get(i).getText(), EXPECTED_CREATED_RECORD.get(i));
        }
    }

    @Ignore
    @Test
    public void testDeleteRecordFromRecycleBin() {

        createDraftRecord();

        deleteDraftRecord();

        clickRecycleBin(getDriver());

        WebElement header = findElement(By.xpath("//a[@class='navbar-brand']"));
        Assert.assertEquals(header.getText(), "Recycle Bin");

        getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tbody/tr")));

        List<WebElement> rows = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(rows.size(), 1);

        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("delete permanently"))).click();

        Assert.assertEquals(findElement(By.className("card-body")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");
    }
}
