import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

public class EntityCalendarRecordSearchTest extends BaseTest {

    private void createBasicCalendarRecord(String str, String date) {
        TestUtils.scrollClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Calendar')]")));
        findElement(By.xpath("//*[@class='card-icon']")).click();

        findElement(By.id("string")).sendKeys(str);
        findElement(By.id("date")).click();
        findElement(By.id("date")).clear();
        findElement(By.id("date")).sendKeys(date);

        TestUtils.scrollClick(getDriver(), findElement(By.id("pa-entity-form-save-btn")));
    }

    @Ignore
    @Test
    public void testSearchCreatedRecord() {
        ProjectUtils.start(getDriver());

        createBasicCalendarRecord("Vote",  "01/01/2025");
        createBasicCalendarRecord("Surgery",  "02/02/2025");

        findElement(By.xpath("//a[@href='index.php?action=action_list&list_type=table&entity_id=32']")).click();

        Assert.assertEquals(findElement(By.xpath("//span[@class='pagination-info']")).getText(),
                "Showing 1 to 2 of 2 rows");

        WebElement searchField = findElement((By.xpath("//input[@placeholder='Search']")));
        searchField.sendKeys("vote");
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//span[@class='pagination-info']"),
                "Showing 1 to 1 of 1 rows"));
        Assert.assertEquals(findElement(By.xpath("//span[@class='pagination-info']")).getText(),
                "Showing 1 to 1 of 1 rows");
        Assert.assertEquals(findElement(By.xpath("//tbody/tr/td[2]/a")).getText(),"Vote");

        searchField.clear();
        searchField.sendKeys("surgery");
        getWait().until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//tbody/tr/td[2]/a"),
                "Surgery"));
        Assert.assertEquals(findElement(By.xpath("//span[@class='pagination-info']")).getText(),
                "Showing 1 to 1 of 1 rows");
        Assert.assertEquals(findElement(By.xpath("//tbody/tr/td[2]/a")).getText(),"Surgery");
    }
}
