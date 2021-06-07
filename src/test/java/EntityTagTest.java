import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.*;

public class EntityTagTest extends BaseTest{

    private void createRecord() {

        ProjectUtils.start(getDriver());

        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Tag ']")));

        ProjectUtils.clickCreateRecord(getDriver());
        findElement(By.id("string")).sendKeys("Hello world");
        findElement(By.id("text")).sendKeys("Be healthy");
        findElement(By.id("int")).sendKeys("123");
        findElement(By.id("decimal")).sendKeys("456.98");

        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath("//button[@data-id='user']")));
        TestUtils.jsClick(getDriver(), getDriver().findElement(
                        By.xpath("//span[text()='tester26@tester.test']")));
    }

    private final List<String> world = List.of(
            "Hello world", "Be healthy", "123", "456.98", "", "");

    @Test
    public void testCreateRecord() {

        createRecord();
        ProjectUtils.clickSave(getDriver());

        List<WebElement> records = getDriver().findElements(By.xpath("//tbody/tr/td/a"));
        WebElement box = getDriver().findElement(By.xpath("//tbody/tr/td/i"));

        Assert.assertEquals(box.getAttribute("class"), "fa fa-check-square-o");
        for (int i = 0; i < records.size(); i++) {
            Assert.assertEquals(records.get(i).getText(), world.get(i));
        }
    }

    @Test
    public void testCreateDraftRecord() {

        createRecord();

        ProjectUtils.clickSaveDraft(getDriver());

        List<WebElement> records = getDriver().findElements(By.xpath("//tbody/tr/td/a"));
        WebElement box = getDriver().findElement(By.xpath("//tbody/tr/td/i"));

        Assert.assertEquals(box.getAttribute("class"), "fa fa-pencil");
        for (int i = 0; i < records.size(); i++) {
            Assert.assertEquals(records.get(i).getText(), world.get(i));
        }
    }

    @Test
    public void testCancelRecord() {

        createRecord();
        ProjectUtils.clickCancel(getDriver());

        Assert.assertNull(findElement(By.className("card-body")).getAttribute("value"));
    }
}
