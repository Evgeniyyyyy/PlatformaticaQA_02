import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityTagTest extends BaseTest{

    @Test
    public void testCreateNewRecord() {

        ProjectUtils.start(getDriver());

        WebElement menuTag = getDriver().findElement(By.xpath("//p[contains(text(),'Tag')]"));
        TestUtils.scrollClick(getDriver(), menuTag);

        getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        getDriver().findElement(By.xpath("//input[@id='string']")).sendKeys("String");
        getDriver().findElement(By.xpath("//textarea[@id='text']")).sendKeys("text");
        getDriver().findElement(By.xpath("//input[@id='int']")).sendKeys("2");
        getDriver().findElement(By.xpath("//input[@id='decimal']")).sendKeys("2.20");
        getDriver().findElement(By.xpath("//input[@id='date']")).click();
        getDriver().findElement(By.xpath("//input[@id='datetime']")).click();
        getDriver().findElement(By.xpath("//div[contains(text(),'apptester1@tester.test')]")).isDisplayed();
        getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")).click();

        WebElement checkCreatedRecord = getDriver().findElement(By.xpath("//i[@class='fa fa-check-square-o']"));
        Assert.assertTrue(checkCreatedRecord.isDisplayed());
    }
}
