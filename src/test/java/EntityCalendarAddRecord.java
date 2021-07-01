import base.BaseTest;
import org.apache.commons.lang3.exception.ExceptionContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityCalendarAddRecord extends BaseTest {

    @Test
    public void testCalendarCreateNewRecord() throws InterruptedException {

        ProjectUtils.start(getDriver());
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains (text(), 'Calendar')]")));
Thread.sleep(1000);
        getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        getDriver().findElement(By.xpath("//input[@id='string']")).sendKeys("Hello");
        getDriver().findElement(By.xpath("//textarea[@id='text']")).sendKeys("World");
        getDriver().findElement(By.xpath("//input[@id='int']")).sendKeys("22");
        getDriver().findElement(By.xpath("//input[@id='decimal']")).sendKeys("22.2222222");
        getDriver().findElement(By.xpath("//input[@id='date']")).click();
        getDriver().findElement(By.xpath("//div[@class='datepicker']//td[@data-day = '05/05/2021']")).click();
        getDriver().findElement(By.xpath("//input[@id='datetime']")).click();
        getDriver().findElement(By.xpath("//ul[@class='list-unstyled']//td[@data-day = '05/05/2021']")).click();
        getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[contains(text(),'Hello')]")).getText(), "Hello");

    }
}