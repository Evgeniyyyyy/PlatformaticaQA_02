import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.UUID;

import static utils.ProjectUtils.*;
import static utils.TestUtils.jsClick;

public class EntityFieldsTest extends BaseTest {

    private final List<String> WORLD = List.of(
            "Hello world", "Be healthy", "123", "456.98", "", "");

    private final List<String> EVERYONE = List.of(
            "Hello for everyone", "Peace to all", "345", "345.67", "", "");

    @Test
    public void testCreateNewRecord(){
        final String tile = UUID.randomUUID().toString();
        final String comments = UUID.randomUUID().toString();
        final int number = 10;
        final double decimal = 10.10;

        By menuFields = By.xpath("//p[contains(text(),'Fields')]");
        By title = By.xpath("//input[@id='title']");
        By coment = By.xpath("//textarea[@id='comments']");
        By num = By.xpath("//input[@id='int']");
        By dec = By.xpath("//input[@id='decimal']");
        By saveButton = By.xpath("//button[@id='pa-entity-form-save-btn']");

        start(getDriver());

        findElement(menuFields).click();
        WebElement newRecord = findElement(By.xpath("//i[text() = 'create_new_folder']"));
        newRecord.click();
        findElement(title).sendKeys(tile);
        findElement(coment).sendKeys(comments);
        findElement(num).sendKeys(String.valueOf(number));
        findElement(dec).sendKeys(String.valueOf(decimal));
        jsClick(getDriver(), findElement(saveButton));

        WebElement recordTitle = findElement(By.xpath("//tbody//tr//td[2]"));
        Assert.assertEquals(recordTitle.getText(), tile);
    }

    @Test
    public void testReorderRecord() {

        start(getDriver());
        jsClick(getDriver(), getDriver().findElement(By.xpath("//p[text()=' Fields ']")));
        clickCreateRecord(getDriver());

        findElement(By.id("title")).sendKeys("Hello world");
        findElement(By.id("comments")).sendKeys("Be healthy");
        findElement(By.id("int")).sendKeys("123");
        findElement(By.id("decimal")).sendKeys("456.98");

        jsClick(getDriver(), getDriver().findElement(By.xpath("//button[@data-id='user']")));
        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//span[text()='tester26@tester.test']")));
        clickSave(getDriver());

        clickCreateRecord(getDriver());
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
        clickSaveDraft(getDriver());

        findElement(By.xpath("//i[text()='format_line_spacing']")).click();

        List<WebElement> record = getDriver().findElements(By.xpath("//tbody/tr/td/a"));
        Assert.assertEquals(record.get(0).getText(), WORLD.get(0));

        Actions actions = new Actions(getDriver());
        WebElement row = findElement(By.xpath("//tbody/tr"));
        actions.moveToElement(row).clickAndHold(row).dragAndDropBy(row, 0, 20);
        Action swapRow = actions.build();
        swapRow.perform();

        List<WebElement> record1 = getDriver().findElements(By.xpath("//tbody/tr/td/a"));
        Assert.assertEquals(record1.get(0).getText(), EVERYONE.get(0));

        getDriver().findElement(By.xpath("//i[@class='fa fa-toggle-off']")).click();

        List<WebElement> viewToggle = getDriver().findElements(By.xpath("//div/span/a"));
        Assert.assertEquals(viewToggle.get(0).getText(), EVERYONE.get(0));

        WebElement card = getDriver().findElement(By.id("customId_0"));
        actions.moveToElement(card).clickAndHold(card).dragAndDropBy(card, 0, 100);
        Action swapCard = actions.build();
        swapCard.perform();

        List<WebElement> viewCard = getDriver().findElements(By.xpath("//div/span/a"));
        Assert.assertEquals(viewCard.get(0).getText(), WORLD.get(0));
    }
}
