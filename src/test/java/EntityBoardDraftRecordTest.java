import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.List;

public class EntityBoardDraftRecordTest extends BaseTest {

    private void createDraftRecord() {

        ProjectUtils.start(getDriver());

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Board')]")));

        findElement(By.xpath("//div[@class='card-icon']")).click();

        findElement(By.xpath("//button[@data-id='string']")).click();

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//select/option[text()='Pending']")));

        findElement(By.id("text")).sendKeys("qwerty");
        getWait().until(ExpectedConditions.attributeToBe(
                By.id("text"), "value", "qwerty"));

        findElement(By.id("int")).sendKeys("1");
        getWait().until(ExpectedConditions.attributeToBe(
                By.id("int"), "value", "1"));

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

        getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='dropdown pull-left show']//a[text()='delete']")))
                .click();

    }

    private void assertsRecordInRecycleBin(){

        WebElement checkHeader = findElement(By.xpath("//a[@class='navbar-brand']"));
        Assert.assertEquals(checkHeader.getText(), "Recycle Bin");

        getWait().until(ExpectedConditions.visibilityOf(findElement(By.xpath("//div[@class='card-body']"))));

        List<WebElement> getRow = findElements(By.xpath("//tbody/tr[@data-index='0']"));
        Assert.assertEquals(getRow.size(), 1);

        WebElement checkNotification = findElement(By.xpath("//a/span[@class='notification']"));
        Assert.assertEquals(checkNotification.getText(), "1");
    }

    @Test
    public void deleteRecordFromRecycleBin() {

        final String getMessage = "Good job with housekeeping! Recycle bin is currently empty!";

        createDraftRecord();
        deleteDraftRecord();

        findElement(By.xpath("//a[@href='index.php?action=recycle_bin']")).click();

        assertsRecordInRecycleBin();

        findElement(By.xpath("//tr[@data-index='0']/td/a[contains(text(), 'delete permanently')]")).click();

        WebElement getExpectedResult = findElement(By.className("card-body"));

        Assert.assertEquals(getExpectedResult.getText(), getMessage);

    }
}
