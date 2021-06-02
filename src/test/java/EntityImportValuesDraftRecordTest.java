import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityImportValuesDraftRecordTest extends BaseTest {

    @Test
    public void testCreateDraftRecord(){

        List<String> expectedValues = Arrays.asList(
                "Some string", "Import values text.", "457", "27.35",
                "01/06/2021", "01/06/2021 13:07:06", "", "apptester1@tester.test");

        start(getDriver());

        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains(.,' Import values ')]")));

        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                By.xpath("//i[text() = 'create_new_folder']"))));

        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();

        getDriver().findElement(By.id("string")).sendKeys("Some string");

        getDriver().findElement(By.id("text")).sendKeys("Import values text.");

        getDriver().findElement(By.id("int")).sendKeys("457");

        getDriver().findElement(By.id("decimal")).sendKeys("27.35");

        WebElement dateField = getDriver().findElement(By.id("date"));
        dateField.click();
        dateField.clear();
        dateField.sendKeys("01/06/2021");

        WebElement dateTimeField = getDriver().findElement(By.id("datetime"));
        dateTimeField.click();
        dateTimeField.clear();
        dateTimeField.sendKeys("01/06/2021 13:07:06");

        getDriver().findElement(By.xpath("//button[@data-id='user']")).click();

        scrollClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-draft-btn")));

        List<WebElement> rows = getDriver().findElements(
                By.xpath("//div[@class='card-body ']//table[@id='pa-all-entities-table']/tbody/tr"));

        List<WebElement> tds = getDriver().findElements(
                By.xpath("//table[@id = 'pa-all-entities-table']/tbody/tr/td[@class = 'pa-list-table-th']"));

        WebElement pencilIcon = getDriver().findElement(By.xpath("//tbody/tr/td[1]/i"));

        List<String> actualValues = new ArrayList<>();

        for(int i = 0; i < tds.size(); i++) {
            actualValues.add(tds.get(i).getText());
        }

        Assert.assertEquals(rows.size(),1);
        Assert.assertTrue(pencilIcon.isDisplayed());
        Assert.assertEquals(actualValues, expectedValues);
    }
}