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

public class EntityBoardDraftRecordTest extends BaseTest {

    private void createDraftRecord() {
        ProjectUtils.start(getDriver());

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Board')]")));

        findElement(By.xpath("//div/i[text()='create_new_folder']")).click();

        findElement(By.xpath("//button[@data-id='string']")).click();

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//select/option[text()='Pending']")));

        WebElement dateField = findElement(By.id("date"));
        dateField.click();
        dateField.clear();
        dateField.sendKeys("23/04/2021");

        WebElement dateTimeField = findElement((By.id("datetime")));
        dateTimeField.click();
        dateTimeField.clear();
        dateTimeField.sendKeys("20/05/2021 20:29:47");

        TestUtils.scroll(getDriver(), findElement(By.name("entity_form_data[user]")));

        WebElement user = findElement(By.xpath("//select/option[text() ='apptester10@tester.test']"));
        user.click();

        findElement(By.id("text")).sendKeys("qwerty");
        getWait().until(ExpectedConditions.attributeToBe(
                By.id("text"), "value", "qwerty"));

        findElement(By.id("int")).sendKeys("1");
        getWait().until(ExpectedConditions.attributeToBe(
                By.id("int"), "value", "1"));

        findElement(By.id("decimal")).sendKeys("0.12");

        findElement(By.id("pa-entity-form-draft-btn")).click();
    }

    private void deleteDraftRecord() {

        findElement(By.xpath("//a[@href='index.php?action=action_list&list_type=table&entity_id=31']"))
                .click();

        findElement(By.xpath("//div[@class='dropdown pull-left']")).click();

        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("delete"))).click();
    }
    @Ignore
    @Test
    public void deleteRecordFromRecycleBin() {

        createDraftRecord();
        deleteDraftRecord();

        findElement(By.xpath("//a[@href='index.php?action=recycle_bin']")).click();

        WebElement header = findElement(By.xpath("//a[@class='navbar-brand']"));
        Assert.assertEquals(header.getText(), "Recycle Bin");

        getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//tbody/tr")));

        List<WebElement> rows = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(rows.size(), 1);

        WebElement checkNotification = findElement(By.xpath("//a/span[@class='notification']"));
        Assert.assertEquals(checkNotification.getText(), "1");

        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("delete permanently"))).click();

        Assert.assertEquals(findElement(By.className("card-body")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");
    }
}
