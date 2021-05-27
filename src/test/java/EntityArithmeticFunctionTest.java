import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;

public class EntityArithmeticFunctionTest extends BaseTest {

    @Test
    public void testRecordSaveDraft(){

        ProjectUtils.start(getDriver());

        WebElement arithmeticFunction = getDriver().findElement(By.xpath("//p[contains(text(),'Arithmetic Function')]"));
        arithmeticFunction.click();
        WebElement newRecord = getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]"));
        newRecord.click();

        WebElement formF1 = getDriver().findElement(By.xpath("//input[@id='f1']"));
        formF1.sendKeys("10");
        WebElement formF2 = getDriver().findElement(By.xpath("//input[@id='f2']"));
        formF2.sendKeys("2");

        WebElement saveDraft = getDriver().findElement(By.xpath("//button[@id='pa-entity-form-draft-btn']"));
        saveDraft.click();

        WebElement createdRecord = getDriver().findElement(By.xpath("//tbody/tr[1]"));
        Assert.assertTrue(createdRecord.isDisplayed());
    }
}