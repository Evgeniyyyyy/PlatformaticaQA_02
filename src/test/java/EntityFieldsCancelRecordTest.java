import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.ProjectUtils.clickCancel;
import static utils.ProjectUtils.clickCreateRecord;
import static utils.TestUtils.scrollClick;

public class EntityFieldsCancelRecordTest extends BaseTest {

    @Test
    public void testCancelRecord() {

        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains(.,' Fields ')]")));
        clickCreateRecord(getDriver());
        findElement(By.id("title")).sendKeys("Title");
        findElement(By.id("comments")).sendKeys("Text");
        findElement(By.id("int")).sendKeys("123");
        findElement(By.id("decimal")).sendKeys("25.34");

        WebElement date = findElement(By.id("date"));
        date.click();
        date.clear();
        date.sendKeys("07/06/2021");

        WebElement dateTime = findElement(By.id("datetime"));
        dateTime.click();
        dateTime.clear();
        dateTime.sendKeys("07/06/2021 13:07:06");

        clickCancel(getDriver());

        Assert.assertNull(findElement(By.className("card-body")).getAttribute("value"));
    }
}
