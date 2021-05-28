import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityCalendarTest extends BaseTest {

    private void createCalendarRecord(String str, String text, Integer i, Double decimal){
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains (text(), 'Calendar')]")));
        getDriver().findElement(By.xpath("//*[@class='card-icon']")).click();
        getDriver().findElement(By.id("string")).sendKeys(str);
        getDriver().findElement(By.id("text")).sendKeys(text);
        getDriver().findElement(By.id("date")).click();
        getDriver().findElement(By.id("datetime")).click();
        getDriver().findElement(By.id("int")).sendKeys(i.toString());
        getDriver().findElement(By.id("decimal")).sendKeys(decimal.toString());
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
    }

    @Test
    public void testDeletedRecordFromBinDeletePermanently(){
        ProjectUtils.start(getDriver());
        createCalendarRecord("StringExample", "TextExample", 12345, 0.1);
        getDriver().findElement(By.xpath("//*[contains(@href,'table&entity_id=32')]")).click();
        getDriver().findElement(By.xpath("//*[contains(@class, 'btn btn-round')]")).click();
        getDriver().findElement(By.xpath("//*[@class='dropdown-menu dropdown-menu-right show']/li[3]/a")).click();
        getDriver().findElement(By.xpath("//*[contains (text(), 'delete_outline')]")).click();
        getDriver().findElement(By.xpath("//*[contains (text(), 'delete permanently')]")).click();
         
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class='card-body']")).getText(),"Good job with housekeeping! Recycle bin is currently empty!");
    }
}
