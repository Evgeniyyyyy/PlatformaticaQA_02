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

    public void newData (WebDriver driver, String x, String y) {
        WebElement f1 = getDriver().findElement(By.xpath("//input[@data-field_name='f1']"));
        WebElement f2 = getDriver().findElement(By.xpath("//input[@data-field_name='f2']"));
        f1.clear();
        f1.sendKeys(x);
        f2.clear();
        f2.sendKeys(y);
        getDriver().findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")).click();
    }


    public void createRecord(WebDriver driver) {
        ProjectUtils.start(getDriver());
        WebElement arithmeticButton = getDriver().findElement(By.xpath("//p[text()=' Arithmetic Function ']/parent::a/parent::li"));
        TestUtils.scrollClick(getDriver(), arithmeticButton);
        getDriver().findElement(By.xpath("//i[text()='create_new_folder']")).click();
        newData(getDriver(), "10", "20");
    }

    @Test
    public void testCreateArithmeticFunctionRecord() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        createRecord(getDriver());
        getDriver().findElement(By.xpath("//div[@class='dropdown pull-left']")).click();
        WebElement editBtn = getDriver().findElement(By.xpath("//a[text()='edit']"));
        wait.until(ExpectedConditions.elementToBeClickable(editBtn));
        editBtn.click();
        String newF1 = "40";
        newData(getDriver(), newF1, "30");
        Assert.assertEquals(getDriver().findElement(By.xpath("//tr[@data-index='0']/td[2]")).getText(), newF1);
    }

}
