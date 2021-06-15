import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.scrollClick;

public class EntityPlaceholderRestoreDeletedRecordTest extends BaseTest {
    @Test
    public void testDeleteRecord() {

        List<String> expectedResult = List.of("Task", "English", "25", "25.13", "31");

        scrollClick(getDriver(), By.xpath("//p[contains (text(), 'Placeholder')]"));
        clickCreateRecord(getDriver());

        findElement(By.id("string")).sendKeys("Task");
        findElement(By.id("text")).sendKeys("English");
        findElement(By.id("int")).sendKeys("25");
        findElement(By.id("decimal")).sendKeys("25.13");
        findElement(
                By.xpath("//div [@ id = '_field_container-user']/div/select/option [text() = 'tester29@tester.test']"))
                .click();

        clickSave(getDriver());
        clickActionsDelete(getWait(), getDriver());
        clickRecycleBin(getDriver());

        List<WebElement> rows = findElements(By.xpath("//table/tbody/tr"));

        Assert.assertEquals(rows.size(), 1);

        List<WebElement> testData = findElements(
                By.xpath("// table/tbody/tr/td [@class = 'pa-recycle-col']/a/span/b"));
        List<String> actualResult = new ArrayList<>();
        for (int i = 0; i <= testData.size() - 1; i++) {
            actualResult.add(testData.get(i).getText());
        }

        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testRestoreDeletedRecord() {

        List<String> expectedResult = List.of("Task", "English", "25", "25.13", "31");
        List<String> expectedResult1 = List.of("Task", "English", "25", "25.13", "", "", "", "", "tester29@tester.test");

        clickRecycleBin(getDriver());

        List<WebElement> rows = findElements(By.xpath("//table/tbody/tr"));

        Assert.assertEquals(rows.size(), 1);

        List<WebElement> testData = findElements(
                By.xpath("// table/tbody/tr/td [@class = 'pa-recycle-col']/a/span/b"));
        List<String> actualResult = new ArrayList<>();
        for (int i = 0; i <= testData.size() - 1; i++) {
            actualResult.add(testData.get(i).getText());
        }

        Assert.assertEquals(actualResult, expectedResult);

        findElement(
                By.xpath("//tbody/tr/td [@ class = 'pa-recycle-control']/a[contains (text(), 'restore as draft')]"))
                .click();
        scrollClick(getDriver(), By.xpath("//p[contains (text(), 'Placeholder')]"));

        List<WebElement> testData1 = findElements(
                By.xpath("//table/tbody/tr/td [@class = 'pa-list-table-th']"));
        List<String> actualResult1 = new ArrayList<>();
        for (int i = 0; i <= testData1.size() - 1; i++) {
            actualResult1.add(testData1.get(i).getText());
        }

        Assert.assertEquals(actualResult1, expectedResult1);

        WebElement icon = findElement(By.xpath("//td [1]/i"));

        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");
    }
}
