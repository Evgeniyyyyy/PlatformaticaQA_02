import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
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

        getWait().until(ExpectedConditions.visibilityOf(findElement(By.xpath("//li/a[text()='delete']"))));
        findElement(By.xpath("//li/a[text()='delete']")).click();

    }

    @Test
    public void deleteRecordFromRecycleBin() throws InterruptedException {
        final String message = "Good job with housekeeping! Recycle bin is currently empty!";

        createDraftRecord();
        deleteDraftRecord();

        findElement(By.xpath("//a/i[text()='delete_outline']")).click();

        getWait().until(ExpectedConditions.visibilityOf(getDriver().findElement(By.xpath("//a[text()='delete permanently']"))));
        findElement(By.xpath("//a[text()='delete permanently']")).click();

        WebElement expectedResult = getDriver().findElement(By.className("card-body"));
        Assert.assertEquals(expectedResult.getText(), message);

    }
}
