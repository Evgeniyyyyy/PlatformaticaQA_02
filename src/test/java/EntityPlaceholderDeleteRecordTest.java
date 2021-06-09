import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.List;

import static utils.TestUtils.scrollClick;

public class EntityPlaceholderDeleteRecordTest extends BaseTest {

    @Test
    public void testDeleteRecord() {

        ProjectUtils.start(getDriver());

        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains(text(),'Placeholder')]")));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(By.xpath("//i[contains (text(), 'create_new_folder')]"))))
                .click();
        getDriver().findElement(By.id("string")).sendKeys("Record#1");
        getDriver().findElement(By.id("text")).sendKeys("Some text here...");
        getDriver().findElement(By.id("int")).sendKeys("1000");
        getDriver().findElement(By.id("decimal")).sendKeys("20.00");
        scrollClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));

        getDriver().findElement(By.xpath("//i[contains(., 'menu')]")).click();
        getWait().until(TestUtils.movingIsFinished(By.xpath("//a[contains (text(), 'delete')]"))).click();

        ProjectUtils.clickRecycleBin(getDriver());
        getDriver().findElement(By.xpath("//tbody//span")).click();

        List<String> expected = List.of("Record#1", "Some text here...", "1000", "20.00", "", "");
        List<WebElement> actual = getDriver().findElements(By.xpath("//div/span"));
        for (int i = 0; i < actual.size(); i++) {
            Assert.assertEquals(actual.get(i).getText(), expected.get(i));
        }
    }
}
