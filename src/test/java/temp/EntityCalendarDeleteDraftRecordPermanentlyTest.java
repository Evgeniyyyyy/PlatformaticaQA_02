package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityCalendarDeleteDraftRecordPermanentlyTest extends BaseTest {

    String newEventTitle = "Calendar Event";
    String newEventDescription = "Event description.";
    String intNumbers = "28";
    String decimalNumbers = "20.21";

    private void createDraftCalendarRecord() {
        WebElement inputFieldString = findElement(By.id("string"));
        inputFieldString.clear();
        inputFieldString.sendKeys(newEventTitle);

        WebElement inputFieldText = findElement(By.id("text"));
        inputFieldText.clear();
        inputFieldText.sendKeys(newEventDescription);

        findElement(By.id("int")).sendKeys(intNumbers);
        findElement(By.id("decimal")).sendKeys(decimalNumbers);
        findElement(By.id("date")).click();
        findElement(By.id("datetime")).click();

        ProjectUtils.clickSave(getDriver());
    }

    @Test
    public void EntityCalendarDeleteDraftPermanentlyTest() {

        TestUtils.scrollClick(getDriver(), By.xpath("//p[contains (text(), 'Calendar')]"));

        findElement(By.xpath("//div[@class='card-icon']")).click();

        createDraftCalendarRecord();

        findElement(By.xpath("//*[contains(@href,'table&entity_id=32')]")).click();
        findElement(By.xpath("//*[contains(@class, 'btn btn-round')]")).click();
        findElement(By.xpath("//*[@class='dropdown-menu dropdown-menu-right show']/li[3]/a")).click();

        findElement(By.xpath("//a/i[contains (text(), 'delete_outline')]")).click();

        getWait().until(ExpectedConditions.elementToBeClickable(
               findElement(By.xpath("//a[contains (text(), 'delete permanently')]")))).click();

        Assert.assertEquals(findElement(By.xpath("//div[@class = 'card-body']")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");
    }
}
