import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.TestUtils.*;
import static utils.ProjectUtils.*;

import java.util.*;

public class EntityTagTest extends BaseTest{

    private void createRecord() {

        findElement(By.id("string")).sendKeys("Hello world");
        findElement(By.id("text")).sendKeys("Be healthy");
        findElement(By.id("int")).sendKeys("123");
        findElement(By.id("decimal")).sendKeys("456.98");

        jsClick(getDriver(), getDriver().findElement(By.xpath("//button[@data-id='user']")));
        jsClick(getDriver(), getDriver().findElement(
                        By.xpath("//span[text()='tester26@tester.test']")));
    }

    private void editRecord() {

        getDriver().findElement(By.id("string")).clear();
        getDriver().findElement(By.id("string")).sendKeys("Hello for everyone");

        getDriver().findElement(By.id("text")).clear();
        getDriver().findElement(By.id("text")).sendKeys("Peace to all");

        getDriver().findElement(By.id("int")).clear();
        getDriver().findElement(By.id("int")).sendKeys("345");

        getDriver().findElement(By.id("decimal")).clear();
        getDriver().findElement(By.id("decimal")).sendKeys("345.67");

        jsClick(getDriver(), getDriver().findElement(By.xpath("//button[@data-id='user']")));
        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//span[text()='tester26@tester.test']")));
    }

    private final List<String> world = List.of(
            "Hello world", "Be healthy", "123", "456.98", "", "");

    private final List<String> everyone = List.of(
            "Hello for everyone", "Peace to all", "345", "345.67", "", "");

    private void clickTagButton() {
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Tag ']")));
    }

    @Test
    public void testCancelRecord() {

        clickTagButton();
        clickCreateRecord(getDriver());
        createRecord();
        clickCancel(getDriver());

        Assert.assertNull(findElement(By.className("card-body")).getAttribute("value"));
    }

    @Test(dependsOnMethods = "testCancelRecord")
    public void testCreateRecord() {

        clickTagButton();
        clickCreateRecord(getDriver());
        createRecord();
        clickSave(getDriver());

        List<WebElement> records = getDriver().findElements(By.xpath("//tbody/tr/td/a"));
        WebElement box = getDriver().findElement(By.xpath("//tbody/tr/td/i"));

        Assert.assertEquals(box.getAttribute("class"), "fa fa-check-square-o");
        for (int i = 0; i < records.size(); i++) {
            Assert.assertEquals(records.get(i).getText(), world.get(i));
        }
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testCreateDraftRecord() {

        clickTagButton();
        clickCreateRecord(getDriver());
        editRecord();
        clickSaveDraft(getDriver());

        List<WebElement> records = getDriver().findElements(By.xpath("//tbody/tr[2]/td/a"));
        WebElement box = getDriver().findElement(By.xpath("//tbody/tr[2]/td/i"));

        Assert.assertEquals(box.getAttribute("class"), "fa fa-pencil");
        for (int i = 0; i < records.size(); i++) {
            Assert.assertEquals(records.get(i).getText(), everyone.get(i));
        }
    }
}
