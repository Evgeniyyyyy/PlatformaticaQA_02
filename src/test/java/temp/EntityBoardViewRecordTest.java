package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.TestUtils;
import java.util.ArrayList;
import java.util.List;
import static utils.ProjectUtils.*;
import static utils.TestUtils.jsClick;

@Ignore
public class EntityBoardViewRecordTest extends BaseTest {

    private static final By BOARD_TAB = By.xpath("//p[contains (text(), 'Board')]");
    private static final By ACTIONS_BUTTON = By.xpath("//button/i[text()='menu']");
    private static final By LIST_BUTTON = By.xpath(
            "//a[@href='index.php?action=action_list&list_type=table&entity_id=31']");

    private static final List<String> EXPECTED_CREATED_RECORD = List.of(
            "Pending", "q", "1", "0.12", "", "");
    private static final String EXPECTED_USER = "tester10@tester.test";

    private void createRecord() {

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

        start(getDriver());

        jsClick(getDriver(), findElement(BOARD_TAB));

        createRecord();

        findElement(LIST_BUTTON).click();

        WebElement iconCheckBox = findElement(By.xpath("//tbody/tr/td/i"));
        Assert.assertEquals(iconCheckBox.getAttribute("class"), "fa fa-check-square-o");

        findElement(ACTIONS_BUTTON).click();

        getWait().until(TestUtils.movingIsFinished(By.linkText("view"))).click();

        List<WebElement> actualRecord = findElements(By.xpath("//span[@class='pa-view-field']"));
        WebElement actualUser = findElement(By.xpath("//div[@class='form-group']/p"));

        Assert.assertEquals(getActualValues(actualRecord), EXPECTED_CREATED_RECORD);
        Assert.assertEquals(actualUser.getText(), EXPECTED_USER);

        findElement(By.xpath("//i[text()='clear']")).click();
    }
}
