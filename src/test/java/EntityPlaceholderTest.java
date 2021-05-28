import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityPlaceholderTest extends BaseTest {

    @Test
    public void testCreateNewRecord() {

        ProjectUtils.start(getDriver());

        WebElement menuPlaceholder = getDriver().findElement(By.xpath("//p[contains (text(), 'Placeholder')]"));
        TestUtils.scrollClick(getDriver(), menuPlaceholder);

        getDriver().findElement(By.xpath("//div/i[text()='create_new_folder']")).click();
        getDriver().findElement(By.id("string")).sendKeys("Test 01");
        getDriver().findElement(By.id("text")).sendKeys("first test");
        getDriver().findElement(By.id("int")).sendKeys("1");
        getDriver().findElement(By.id("decimal")).sendKeys("5.5");
        getDriver().findElement(By.id("date")).click();
        getDriver().findElement(By.id("datetime")).click();
        getDriver().findElement(By.xpath("//div[@class = \"filter-option-inner-inner\"]")).click();
        TestUtils.scrollClick(getDriver(), getDriver()
                .findElement(By.xpath("//span[contains (text(),  \"tester99@tester.test\")]")));
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));

        String placeholder = "//table[@id = \"pa-all-entities-table\"]/tbody/tr/td/a[contains (text (), \"Test 01\")]";
        WebElement result = getDriver().findElement(By.xpath(placeholder));

        Assert.assertTrue(result.isDisplayed());
    }
}
