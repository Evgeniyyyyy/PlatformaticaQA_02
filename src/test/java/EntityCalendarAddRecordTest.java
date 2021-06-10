import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityCalendarAddRecordTest extends BaseTest {

    @Ignore
    @Test
    public void testCreateRecord() throws InterruptedException {
        ProjectUtils.start(getDriver());
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains (text(), 'Calendar')]")));
        findElement(By.xpath("//i[contains(text(),'create_new_folder')]")).click();
        findElement(By.xpath("//input[@id='string']")).sendKeys("Hello");
        findElement(By.xpath("//textarea[@id='text']")).sendKeys("World");
        findElement(By.xpath("//input[@id='int']")).sendKeys("22");
        findElement(By.xpath("//input[@id='decimal']")).sendKeys("22.22");
        findElement(By.xpath("//input[@id='date']")).click();

        WebElement fieldDate = getDriver().findElement(By.id("date"));
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys("22/05/2022");

        WebElement fieldDateTime = getDriver().findElement(By.id("datetime"));
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys("27/05/2022 11:35:35");

        findElement(By.xpath("//button[@id='pa-entity-form-save-btn']")).click();

        findElement(By.xpath("//a[contains(., 'List')]")).click();

        List<WebElement> tableRecords = getDriver().findElements(By.xpath("//div[@ class = 'card-body ']//table/tbody/tr"));
        List<String> expectedResult = Arrays.asList(
                "Hello", "World", "22", "22.22", "22/05/2022", "27/05/2022 11:35:35", "", "apptester1@tester.test");
        List<WebElement> elementsWeb = getDriver().findElements(By.xpath("//td[@class = 'pa-list-table-th']"));
        List<String> ActualValue = new ArrayList<>();
        for (WebElement pc : elementsWeb){
            ActualValue.add(pc.getText());
        }

        Assert.assertEquals(tableRecords.size(), 1);
        Assert.assertEquals(ActualValue, expectedResult);
    }
}