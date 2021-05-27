import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class ArithmeticFunctionEditTest extends BaseTest {


    public void createRecord(WebDriver driver) {
        ProjectUtils.start(getDriver());
        WebElement arithmeticButton = getDriver().findElement(By.xpath("//p[text()=' Arithmetic Function ']/parent::a/parent::li"));
        TestUtils.scrollClick(getDriver(), arithmeticButton);
        getDriver().findElement(By.xpath("//i[text()='create_new_folder']")).click();
        getDriver().findElement(By.xpath("//input[@data-field_name='f1']")).sendKeys("10");
        getDriver().findElement(By.xpath("//input[@data-field_name='f2']")).sendKeys("20");
        getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")).click();
    }

    @Test
    public void testCreateArithmeticFunctionRecord() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        createRecord(getDriver());
        getDriver().findElement(By.xpath("//div[@class='dropdown pull-left']")).click();
        WebElement editBtn = getDriver().findElement(By.xpath("//a[text()='edit']"));
        wait.until(ExpectedConditions.elementToBeClickable(editBtn));
        editBtn.click();
        Assert.assertTrue(getDriver().findElement(By.xpath("//button[@id='pa-entity-form-draft-btn']")).isDisplayed());
    }

}
