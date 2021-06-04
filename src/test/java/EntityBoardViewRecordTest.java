import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;
import java.util.ArrayList;
import java.util.List;
import static utils.ProjectUtils.*;


public class EntityBoardViewRecordTest extends BaseTest {

    private void createRecord() {

        start(getDriver());

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Board')]")));

        clickCreateRecord(getDriver());

        findElement(By.xpath("//button[@data-id='string']")).click();

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//select/option[text()='Pending']")));
        getWait().until(
                ExpectedConditions.invisibilityOf(findElement(By.xpath("//div[@class='dropdown-menu']"))));

        WebElement text = findElement(By.id("text"));
        text.click();
        text.sendKeys("q");

        WebElement integer = findElement(By.id("int"));
        integer.click();
        integer.sendKeys("1");

        WebElement decimal = findElement(By.id("decimal"));
        decimal.click();
        decimal.sendKeys("0.12");

        TestUtils.scrollClick(getDriver(), findElement(By.xpath("//button[@data-id='user']")));

        TestUtils.jsClick(getDriver(), findElement(By.xpath("//span[text()='tester10@tester.test']")));

        clickSave(getDriver());
    }

    private List<String> getActualValues(List<WebElement> actualElements) {
        List<String> listValues = new ArrayList<>();
        for (WebElement element : actualElements) {
            listValues.add(element.getText());
        }

        return listValues;
    }

    @Test
    public void testViewRecord() {

        createRecord();

        final List<String> expectedRecord = List.of(
                "Pending", "q", "1", "0.12", "", "");
        final String expectedUser = "tester10@tester.test";

        findElement(By.xpath("//a[@href='index.php?action=action_list&list_type=table&entity_id=31']")).click();

        WebElement iconCheckBox = findElement(By.xpath("//tbody/tr/td/i"));
        Assert.assertEquals(iconCheckBox.getAttribute("class"), "fa fa-check-square-o");

        findElement(By.xpath("//div[@class='dropdown pull-left']")).click();

        getWait().until(TestUtils.movingIsFinished(By.linkText("view"))).click();

        List<WebElement> actualRecord = findElements(By.xpath("//span[@class='pa-view-field']"));
        WebElement actualUser = findElement(By.xpath("//div[@class='form-group']/p"));

        Assert.assertEquals(actualRecord.size(), expectedRecord.size());
        Assert.assertEquals(getActualValues(actualRecord), expectedRecord);
        Assert.assertEquals(actualUser.getText(), expectedUser);

        findElement(By.xpath("//i[text()='clear']")).click();
    }
}
