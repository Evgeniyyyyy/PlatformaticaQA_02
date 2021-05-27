import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityCalendarTest extends BaseTest {

    @Test
    public void testDeletedRecordFromBinDeletePermanently(){
        ProjectUtils.start(getDriver());
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains (text(), 'Calendar')]")));

        getDriver().findElement(By.xpath("//*[@class='card-icon']")).click();
        getDriver().findElement(By.id("string")).sendKeys("StringExample");
        getDriver().findElement(By.id("text")).sendKeys("TextExample");
        getDriver().findElement(By.id("date")).click();
        getDriver().findElement(By.id("datetime")).click();
        getDriver().findElement(By.id("int")).sendKeys("12345");
        getDriver().findElement(By.id("decimal")).sendKeys("0.1");

        WebElement buttonSave = getDriver().findElement(By.id("pa-entity-form-save-btn"));
        TestUtils.scrollClick(getDriver(), buttonSave);
       
        getDriver().findElement(By.xpath("//*[contains(@href,'table&entity_id=32')]")).click();
        getDriver().findElement(By.xpath("//*[contains(@class, 'btn btn-round')]")).click();
        getDriver().findElement(By.xpath("//*[@class='dropdown-menu dropdown-menu-right show']/li[3]/a")).click();
        getDriver().findElement(By.xpath("//*[contains (text(), 'delete_outline')]")).click();
        getDriver().findElement(By.xpath("//*[contains (text(), 'delete permanently')]")).click();
        
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class='card-body']")).getText(),"Good job with housekeeping! Recycle bin is currently empty!");
    }
}
