import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.scrollClick;

public class EntityExportDestinationCancelRecordTest extends BaseTest {

    @Test
    public void testCancelRecord() {

        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains(text(),'Export destination')]")));
        clickCreateRecord(getDriver());

        findElement(By.id("string")).sendKeys("Mac");
        findElement(By.id("text")).sendKeys("Book");
        findElement(By.id("int")).sendKeys("1");
        findElement(By.id("decimal")).sendKeys("1.10");
        findElement(By.id("date")).click();
        findElement(By.id("date")).clear();
        findElement(By.id("date")).sendKeys("09/06/2021");
        findElement(By.id("datetime")).click();
        findElement(By.id("datetime")).clear();
        findElement(By.id("datetime")).sendKeys("09/06/2021 20:05:23");

        clickCancel(getDriver());

        List<WebElement> records = getDriver().findElements(By.xpath("//td[@class ='pa-list-table-th']"));

        Assert.assertEquals(records.size(), 0);
        Assert.assertNull(findElement(By.xpath("//i[contains(text(),'delete_outline')]"))
                .getAttribute("span"));
    }
}
