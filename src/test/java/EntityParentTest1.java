import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.List;

public class EntityParentTest1 extends BaseTest {


    private void createRecord() {

        ProjectUtils.clickCreateRecord(getDriver());
        findElement(By.id("string")).sendKeys("Hello world");
        findElement(By.id("text")).sendKeys("Be healthy");
        findElement(By.id("int")).sendKeys("123");
        findElement(By.id("decimal")).sendKeys("456.98");

        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath("//button[@data-id='user']")));

        TestUtils.jsClick(getDriver(), getDriver().findElement(
                By.xpath("//span[text()='tester26@tester.test']")));
    }

    private void createNewRecord() {

        getDriver().findElement(By.id("string")).clear();
        getDriver().findElement(By.id("string")).sendKeys("Hello for everyone");

        getDriver().findElement(By.id("text")).clear();
        getDriver().findElement(By.id("text")).sendKeys("Peace to all");

        getDriver().findElement(By.id("int")).clear();
        getDriver().findElement(By.id("int")).sendKeys("345");

        getDriver().findElement(By.id("decimal")).clear();
        getDriver().findElement(By.id("decimal")).sendKeys("345.67");

        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath("//button[@data-id='user']")));

        TestUtils.jsClick(getDriver(), getDriver().findElement(
                By.xpath("//span[text()='tester26@tester.test']")));
    }

    private void clickParentButton() {
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Parent ']")));
    }

    private void clickListButton() {
        findElement(By.xpath(
                "//a[@href='index.php?action=action_list&list_type=table&entity_id=57']")).click();
    }

    private void clickMenuActionButton() {
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath("//i[text()='menu']")));
    }

    private final List<String> expect = List.of(
            "Hello world", "Be healthy", "123", "456.98", "", "");

    @Test
    public void testCreateRecord() {

        ProjectUtils.start(getDriver());
        clickParentButton();
        createRecord();

        ProjectUtils.clickSave(getDriver());

        WebElement icon = findElement(By.xpath("//tbody/tr/td/i"));

        List<WebElement> records = getDriver().findElements(By.xpath("//tbody/tr"));
        List<WebElement> result = getDriver().findElements(By.xpath(
                "//a[@href='index.php?action=action_view&entity_id=57&row_id=301']"));

        Assert.assertEquals(records.size(), 1);
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-check-square-o");
        for (int i = 0; i < result.size(); i++) {
            Assert.assertEquals(result.get(i).getText(), expect.get(i));
        }
    }

    @Test
    public void testViewRecord() {

        ProjectUtils.start(getDriver());

        clickParentButton();
        createRecord();
        ProjectUtils.clickSave(getDriver());

        clickListButton();

        clickMenuActionButton();

        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath("//a[text()='view']")));

        List<WebElement> row = findElements(By.xpath("//span[@class='pa-view-field']"));

        Assert.assertEquals(row.size(), 6);
        for (int i = 0; i < row.size(); i++) {
            Assert.assertEquals(row.get(i).getText(), expect.get(i));
        }
    }

    @Test
    public void testEditRecord() {

        List<String> expect1 = List.of(
                "Hello for everyone", "Peace to all", "345", "345.67", "", "");

        ProjectUtils.start(getDriver());

        clickParentButton();
        createRecord();
        ProjectUtils.clickSave(getDriver());

        WebElement record = findElement(By.tagName("tbody"));
        record.getText();

        clickListButton();
        clickMenuActionButton();

        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath("//a[text()='edit']")));

        createNewRecord();
        ProjectUtils.clickSave(getDriver());

        List<WebElement> result = getDriver().findElements(By.xpath(
                "//a[@href='index.php?action=action_view&entity_id=57&row_id=301']"));
        for (int i = 0; i < result.size(); i++) {
            Assert.assertEquals(result.get(i).getText(), expect1.get(i));
        }

        WebElement newRecord = findElement(By.tagName("tbody"));
        newRecord.getText();
        Assert.assertNotEquals(record, newRecord);
    }

    @Test
    public void testSearchRecord() {

        ProjectUtils.start(getDriver());

        clickParentButton();
        createRecord();

        ProjectUtils.clickSave(getDriver());
        ProjectUtils.clickCreateRecord(getDriver());

        createNewRecord();

        ProjectUtils.clickSave(getDriver());

        WebElement record = findElement(By.xpath("//tr[@data-index='0']"));
        record.getText();

        WebElement record1 = findElement(By.xpath("//tr[@data-index='1']"));
        record1.getText();

        List <WebElement> fields = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(fields.size(), 2);
        Assert.assertNotEquals(record, record1);

        findElement(By.xpath("//input[@type='text']")).sendKeys("world");

        List<WebElement> result = findElements(By.xpath(
                "//a[@href='index.php?action=action_view&entity_id=57&row_id=301']"));

        for (int i = 0; i < result.size(); i++) {
            Assert.assertEquals(result.get(i).getText(), expect.get(i));
        }

        List <WebElement> field = findElements(By.xpath("//tbody/tr"));
        Assert.assertEquals(field.size(), 1);
    }
}
