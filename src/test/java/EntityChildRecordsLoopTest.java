import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ProjectUtils;

public class EntityChildRecordsLoopTest extends BaseTest {

    @Test
    public void testChildRecordsLoopCreateCard() {
        getDriver().get("https://ref2.eteam.work/");

        ProjectUtils.login(getDriver());

        WebElement childRecordsLoop = getDriver().findElement(By.xpath("//p[contains(text(),'Child records loop')]"));
        childRecordsLoop .click();
        WebElement newChildRecLoopFolder = getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        newChildRecLoopFolder.click();

        WebElement startBalance = getDriver().findElement(By.xpath("//input[@id='start_balance']"));
        startBalance.sendKeys("100");

        WebElement newChildRecLoopRecord = getDriver().findElement(By.xpath("//tbody/tr[@id='add-row-68']/td[1]/button[1]"));
        newChildRecLoopRecord.click();

        WebElement amount = getDriver().findElement(By.xpath("//textarea[@id='t-68-r-1-amount']"));
        amount.clear();
        amount.sendKeys("200");

        WebElement item = getDriver().findElement(By.xpath("//textarea[@id='t-68-r-1-item']"));
        item.sendKeys("book");

        WebElement saveButton = getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']"));
        saveButton.click();

        WebElement cardsTableEntry = getDriver().findElement(By.xpath("//tbody/tr[1]"));
        Assert.assertTrue(cardsTableEntry.isDisplayed());
    }
}
