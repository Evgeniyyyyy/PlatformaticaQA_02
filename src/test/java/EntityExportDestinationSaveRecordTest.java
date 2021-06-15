import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.scrollClick;

public class EntityExportDestinationSaveRecordTest extends BaseTest {

    private final static List<String> EXPECTED_VALUES = Arrays.asList(
            "Some string", "Export destination text.", "457", "27.35",
            "01/06/2021", "01/06/2021 13:07:06", "", "apptester1@tester.test");

    @Test
    public void testSaveRecord() {

        scrollClick(getDriver(), findElement(By.xpath("//p[contains(text(), ' Export destination ')]")));
        clickCreateRecord(getDriver());

        findElement(By.id("string")).sendKeys("Some string");
        findElement(By.id("text")).sendKeys("Export destination text.");
        findElement(By.id("int")).sendKeys("457");
        findElement(By.id("decimal")).sendKeys("27.35");

        WebElement date = findElement(By.id("date"));
        date.click();
        date.clear();
        date.sendKeys("01/06/2021");

        WebElement dateTime = findElement(By.id("datetime"));
        dateTime.click();
        dateTime.clear();
        dateTime.sendKeys("01/06/2021 13:07:06");

        clickSave(getDriver());

        List<WebElement> tds = findElements(By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']"));

        WebElement checkSquareIcon = findElement(By.xpath("//i[@class='fa fa-check-square-o']"));

        List<String> actualValues = new ArrayList<>();
        for(int i = 0; i < tds.size(); i++) {
            actualValues.add(tds.get(i).getText());
        }

        Assert.assertEquals(checkSquareIcon.getAttribute("class"), "fa fa-check-square-o");
        Assert.assertEquals(actualValues, EXPECTED_VALUES);
    }
}
