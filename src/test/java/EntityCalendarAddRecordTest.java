import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityCalendarAddRecordTest extends BaseTest {
    @Ignore
    @Test
    public void testCreateRecord() throws InterruptedException {
        ProjectUtils.start(getDriver());
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains (text(), 'Calendar')]")));
        findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        findElement(By.xpath("//input[@id='string']")).sendKeys("Hello");
        findElement(By.xpath("//textarea[@id='text']")).sendKeys("World");
        findElement(By.xpath("//input[@id='int']")).sendKeys("22");
        findElement(By.xpath("//input[@id='decimal']")).sendKeys("22.2222222");
        findElement(By.xpath("//input[@id='date']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().
                findElement(By.xpath("//td[@data-day = '05/05/2021']")))).
                click();
        findElement(By.xpath("//input[@id='datetime']")).click();
        findElement(By.xpath("//td[@data-day = '05/05/2021']"));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().
                findElement(By.xpath("//span[@class = 'fa fa-clock-o']")))).click();
        findElement(By.xpath("//span[@title='Pick Hour']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().
                findElement(By.xpath("//td[@class='hour']//div[contains(text(),'10')]")))).click();
        findElement(By.xpath("//span[@class = 'timepicker-minute']")).click();
        findElement(By.xpath("//td[@class='minute']//div[contains(text(),'15')]")).click();
        findElement(By.xpath("//table[@class='table-condensed']//span[@class = 'timepicker-second']")).click();
        findElement(By.xpath("//td[@class='second']//div[contains(text(),'20')]")).click();
        findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")).click();

        findElement(By.xpath("//span[@class='fc-title']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[contains(text(),'Hello')]")).getText(), "Hello");
    }
}