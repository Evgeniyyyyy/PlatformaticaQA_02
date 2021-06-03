import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityPlaceholderDeleteTest extends BaseTest {

    @Test
    public void testPlaceholderDeleteRecordPermanently() {
        ProjectUtils.start(getDriver());
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains (text(), 'Placeholder')]")));
        getDriver().findElement(By.xpath("//*[@class='card-icon']")).click();
        getDriver().findElement(By.id("string")).sendKeys("String field input");
        getDriver().findElement(By.id("text")).sendKeys("Text field input");
        getDriver().findElement(By.id("int")).sendKeys("525");
        getDriver().findElement(By.id("decimal")).sendKeys("525.478");
        getDriver().findElement(By.id("date")).click();
        getDriver().findElement(By.id("datetime")).click();
        TestUtils.scroll(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        getDriver().findElement(By.xpath("//*[@class='btn btn-round btn-sm btn-primary dropdown-toggle'] [@data-toggle='dropdown']")).click();
        getDriver().findElement(By.linkText("delete")).click();
        getDriver().findElement(By.partialLinkText("delete_outline")).click();
        getDriver().findElement(By.partialLinkText("delete permanently")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class='card-body']")).getText(), "Good job with housekeeping! Recycle bin is currently empty!");
    }
}
