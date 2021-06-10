import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import static utils.ProjectUtils.*;
import static utils.TestUtils.jsClick;

public class EntityFieldsTest extends BaseTest {

    private void createRecord() {

        clickCreateRecord(getDriver());

        findElement(By.id("title")).sendKeys("Hello world");
        findElement(By.id("comments")).sendKeys("Be healthy");
        findElement(By.id("int")).sendKeys("123");
        findElement(By.id("decimal")).sendKeys("456.98");

        jsClick(getDriver(), getDriver().findElement(By.xpath("//button[@data-id='user']")));
        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//span[text()='tester26@tester.test']")));
    }

    private void editRecord() {

        getDriver().findElement(By.id("title")).clear();
        getDriver().findElement(By.id("title")).sendKeys("Hello for everyone");

        getDriver().findElement(By.id("comments")).clear();
        getDriver().findElement(By.id("comments")).sendKeys("Peace to all");

        getDriver().findElement(By.id("int")).clear();
        getDriver().findElement(By.id("int")).sendKeys("345");

        getDriver().findElement(By.id("decimal")).clear();
        getDriver().findElement(By.id("decimal")).sendKeys("345.67");

        jsClick(getDriver(), getDriver().findElement(By.xpath("//button[@data-id='user']")));
        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//span[text()='tester26@tester.test']")));
    }

    private void clickFieldsButton() {
        jsClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Fields ']")));
    }

    private final List<String> world = List.of(
            "Hello world", "Be healthy", "123", "456.98", "", "");

    private final List<String> everyone = List.of(
            "Hello for everyone", "Peace to all", "345", "345.67", "", "");

    @Test
    public void testCreateNewRecord(){

        clickFieldsButton();
        createRecord();
        clickSave(getDriver());

        List<WebElement> record = getDriver().findElements(By.xpath("//tbody/tr/td/a"));
        for (int i = 0; i < record.size(); i++) {
            Assert.assertEquals(record.get(i).getText(), world.get(i));
        }
    }

    @Test(dependsOnMethods = "testCreateNewRecord")
    public void testReorderRecord() {

        clickFieldsButton();
        clickCreateRecord(getDriver());
        editRecord();
        clickSaveDraft(getDriver());

        findElement(By.xpath("//i[text()='format_line_spacing']")).click();

        List<WebElement> record = getDriver().findElements(By.xpath("//tbody/tr/td/a"));
        Assert.assertEquals(record.get(0).getText(), world.get(0));

        Actions actions = new Actions(getDriver());
        WebElement row = findElement(By.xpath("//tbody/tr"));
        actions.moveToElement(row).clickAndHold(row).dragAndDropBy(row, 0, 20);
        Action swapRow = actions.build();
        swapRow.perform();

        List<WebElement> record1 = getDriver().findElements(By.xpath("//tbody/tr/td/a"));
        Assert.assertEquals(record1.get(0).getText(), everyone.get(0));

        getDriver().findElement(By.xpath("//i[@class='fa fa-toggle-off']")).click();

        List<WebElement> viewToggle = getDriver().findElements(By.xpath("//div/span/a"));
        Assert.assertEquals(viewToggle.get(0).getText(), everyone.get(0));

        WebElement card = getDriver().findElement(By.id("customId_0"));
        actions.moveToElement(card).clickAndHold(card).dragAndDropBy(card, 0, 100);
        Action swapCard = actions.build();
        swapCard.perform();

        List<WebElement> viewCard = getDriver().findElements(By.xpath("//div/span/a"));
        Assert.assertEquals(viewCard.get(0).getText(), world.get(0));
    }
}
