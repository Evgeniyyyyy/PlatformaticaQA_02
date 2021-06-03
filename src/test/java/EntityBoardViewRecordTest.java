import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;
import java.util.List;


public class EntityBoardViewRecordTest extends BaseTest {

    private void createRecord() {
        ProjectUtils.start(getDriver());

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Board')]")));

        findElement(By.xpath("//div/i[text()='create_new_folder']")).click();

        findElement(By.xpath("//button[@data-id='string']")).click();

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//select/option[text()='Pending']")));

        TestUtils.scrollClick(getDriver(), findElement(By.xpath("//button[@data-id='user']")));

        TestUtils.jsClick(getDriver(), getDriver().findElement(
                By.xpath("//span[text()='tester10@tester.test']")));

        findElement(By.id("text")).sendKeys("qwerty");

        findElement(By.id("int")).sendKeys("1");

        findElement(By.id("decimal")).sendKeys("0.12");

        findElement(By.id("pa-entity-form-save-btn")).click();
    }

    @Test
    public void testViewRecord() {
        createRecord();

        final List<String> excpectedRecordColumn = List.of(
                "Pending", "qwerty", "1", "0.12", "", "");
        final List<String> excpectedRecordVRow = List.of(
                "Pending", "qwerty", "1", "0.12", "", "", "", "tester10@tester.test");

        findElement(By.xpath("//a[@href='index.php?action=action_list&list_type=table&entity_id=31']")).click();

        WebElement icon = findElement(By.xpath("//tbody/tr/td/i"));
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-check-square-o");

        List<WebElement> rowValues = findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        for (int i = 0; i < rowValues.size(); i++) {
            Assert.assertEquals(rowValues.get(i).getText(), excpectedRecordVRow.get(i));
        }

        findElement(By.xpath("//div[@class='dropdown pull-left']")).click();

        getWait().until(ExpectedConditions.elementToBeClickable(By.linkText("view"))).click();

        List<WebElement> columnValues = findElements(By.xpath("//span[@class='pa-view-field']"));
        for (int i = 0; i < columnValues.size(); i++) {
            Assert.assertEquals(columnValues.get(i).getText(), excpectedRecordColumn.get(i));
        }

        WebElement user = findElement(By.xpath("//div[@class='form-group']/p"));
        Assert.assertEquals(user.getText(), "tester10@tester.test");

        findElement(By.xpath("//i[text()='clear']")).click();
    }
}
