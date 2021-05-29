import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityBoardDraftRecordTest extends BaseTest {

    private void createDraftRecord() throws InterruptedException {

        ProjectUtils.start(getDriver());

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Board')]")));

        findElement(By.className("card-icon")).click();

        findElement(By.xpath("//button[@data-id='string']")).click();

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//a/span[text() = 'Pending']")));

        findElement(By.id("text")).sendKeys("qwerty");
        Thread.sleep(1000);

        findElement(By.name("entity_form_data[int]")).sendKeys("1");
        Thread.sleep(1000);

        findElement(By.id("decimal")).sendKeys("0.12");

        WebElement dateField = findElement(By.id("date"));
        dateField.click();
        dateField.clear();
        dateField.sendKeys("23/04/2021");

        WebElement dateTimeField = findElement((By.id("datetime")));
        dateTimeField.click();
        dateTimeField.clear();
        dateTimeField.sendKeys("20/05/2021 20:29:47");

        TestUtils.scroll(getDriver(), findElement(By.name("entity_form_data[user]")));

        WebElement getUser = findElement(By.xpath("//select/option[text() ='apptester10@tester.test']"));
        getUser.click();
        getWait().until(ExpectedConditions.elementToBeSelected(getUser));

        TestUtils.jsClick(getDriver(), findElement(By.id("pa-entity-form-draft-btn")));

    }

    private void deleteDraftRecord() {

        findElement(By.xpath("//a[@href='index.php?action=action_list&list_type=table&entity_id=31']"))
                .click();

        findElement(By.xpath("//div[@class='dropdown pull-left']")).click();

        WebElement getDeleteLink = findElement(By.xpath("//li/a[text()='delete']"));
        getWait().until(ExpectedConditions.visibilityOf(getDeleteLink));
        getDeleteLink.click();

    }

    @Ignore
    @Test
    public void deleteRecordFromRecycleBin() throws InterruptedException {

        final String getMessage = "Good job with housekeeping! Recycle bin is currently empty!";

        createDraftRecord();
        deleteDraftRecord();

        findElement(By.xpath("//a[@href='index.php?action=recycle_bin']")).click();

        WebElement getDeletePermanentlyLink = findElement(
                By.xpath("//tr[@data-index='0']/td/a[contains(text(), 'delete permanently')]"));
        getWait().until(ExpectedConditions.visibilityOf(getDeletePermanentlyLink));
        getDeletePermanentlyLink.click();

        WebElement getExpectedResult = findElement(By.className("card-body"));
        Assert.assertEquals(getExpectedResult.getText(), getMessage);

    }
}
