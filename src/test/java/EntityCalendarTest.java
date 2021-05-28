import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.List;

import static utils.TestUtils.scroll;

public class EntityCalendarTest extends BaseTest {

    private void createCalendarRecord(String str, String text, String in, String decimal){
        TestUtils.scrollClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Calendar')]")));
        findElement(By.xpath("//*[@class='card-icon']")).click();

        findElement(By.id("string")).sendKeys(str);
        findElement(By.id("text")).sendKeys(text);
        findElement(By.id("date")).click();
        findElement(By.id("datetime")).click();
        findElement(By.id("int")).sendKeys(in);
        findElement(By.id("decimal")).sendKeys(decimal);

        TestUtils.scrollClick(getDriver(), findElement(By.id("pa-entity-form-save-btn")));
    }

    @Test
    public void testDeletedRecordFromBin(){
        ProjectUtils.start(getDriver());

        createCalendarRecord("StringExample", "TextExample", "12345", "0.1");

        findElement(By.xpath("//*[contains(@href,'table&entity_id=32')]")).click();
        findElement(By.xpath("//*[contains(@class, 'btn btn-round')]")).click();
        findElement(By.xpath("//*[@class='dropdown-menu dropdown-menu-right show']/li[3]/a")).click();
        findElement(By.xpath("//*[contains (text(), 'delete_outline')]")).click();
        findElement(By.xpath("//*[contains (text(), 'delete permanently')]")).click();
         
        Assert.assertEquals(
                findElement(By.xpath("//*[@class='card-body']")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");
    }
    @Test
    public void testCalendarViewCreatedRecord() {
        ProjectUtils.start(getDriver());
        createCalendarRecord("StringExampleCreateRecord", "TextExample", "54321", "0.1");
        TestUtils.scrollClick(getDriver(), findElement(By.xpath("//*[@class='fc-title']")));
        scroll(getDriver(), findElement(By.xpath("//*[@class='pa-view-field']")));

        Assert.assertEquals(findElement(By.xpath("//*[@class='pa-view-field']")).getText(),
                "StringExampleCreateRecord");
    }
    @Test
    public void testCreateRecord(){
        final List<String> createRecordList = List.of("StringExampleCreateRecord", "TextExample", "1111", "0.20");
        
        ProjectUtils.start(getDriver());

        createCalendarRecord("StringExampleCreateRecord", "TextExample", "1111", "0.20");

        findElement(By.xpath("//*[contains(@href,'table&entity_id=32')]")).click();
        
        Assert.assertEquals(findElement(By.xpath("//tbody/tr/td[@class='pa-list-table-th']")).getText(),
                "StringExampleCreateRecord");
        
        List<WebElement> result = getDriver().findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(result.get(i).getText(), createRecordList.get(i));
        }
    }
}
