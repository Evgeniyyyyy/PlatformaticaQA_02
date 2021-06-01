import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;
import java.util.List;


public class EntityBoardEditDraftRecordTest extends BaseTest {

    private void createDraftRecord() {

        ProjectUtils.start(getDriver());

        TestUtils.scrollClick(getDriver(),
                findElement(By.xpath("//p[contains (text(), 'Board')]")));

        getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div/i[text()='create_new_folder']"))).click();

        findElement(By.xpath("//button[@data-id='string']")).click();

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//select/option[text()='Pending']")));

        WebElement dateField = findElement(By.id("date"));
        dateField.click();
        dateField.clear();
        dateField.sendKeys("23/04/2021");

        WebElement dateTimeField = findElement((By.id("datetime")));
        dateTimeField.click();
        dateTimeField.clear();
        dateTimeField.sendKeys("20/05/2021 20:29:47");

        TestUtils.scroll(getDriver(), findElement(By.name("entity_form_data[user]")));

        WebElement user = findElement(By.xpath("//select/option[text() ='tester10@tester.test']"));
        user.click();

        findElement(By.id("text")).sendKeys("qwerty");
        getWait().until(ExpectedConditions.attributeToBe(
                By.id("text"), "value", "qwerty"));

        findElement(By.id("int")).sendKeys("1");
        getWait().until(ExpectedConditions.attributeToBe(
                By.id("int"), "value", "1"));

        findElement(By.id("decimal")).sendKeys("0.12");

        findElement(By.id("pa-entity-form-draft-btn")).click();
    }

    @Test
    public void testEditDraftRecord() {

        createDraftRecord();

        final List<String> excpectedEditedRecord = List.of(
                "Pending", "qwerty", "1", "0.22", "23/04/2021", "20/05/2021 20:29:47", "", "tester10@tester.test");

        WebElement headerBoard = findElement(By.xpath("//div[@class='d-flex justify-content-between']/h3"));
        Assert.assertEquals(headerBoard.getText(), "Board");

        findElement(By.xpath("//a[@href='index.php?action=action_list&list_type=table&entity_id=31']")).click();

        WebElement icon = findElement(By.xpath("//tbody/tr/td/i"));
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");

        findElement(By.xpath("//div[@class='dropdown pull-left']")).click();

        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("edit"))).click();

        findElement(By.id("decimal")).clear();
        findElement(By.id("decimal")).sendKeys("0.22");

        TestUtils.scrollClick(getDriver(),
                findElement(By.id("pa-entity-form-draft-btn")));

        List<WebElement> rowValues = findElements(By.xpath("//td[@class='pa-list-table-th']/a"));
        for (int i = 0; i < rowValues.size(); i++) {
            Assert.assertEquals(rowValues.get(i).getText(), excpectedEditedRecord.get(i));
        }
    }
}
