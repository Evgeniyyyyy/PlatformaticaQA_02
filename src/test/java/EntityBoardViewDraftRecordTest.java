import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.TestUtils.jsClick;
import static utils.TestUtils.scrollClick;

public class EntityBoardViewDraftRecordTest extends BaseTest {

    private void createNewDraftRecord() {
        findElement(By.xpath("//div[@class='card-icon']/i[text()='create_new_folder']")).click();

        WebElement date = findElement(By.id("date"));
        date.click();
        date.clear();
        date.sendKeys("30/05/2021");
        getWait().until(ExpectedConditions.attributeToBe(date, "value", "30/05/2021"));

        WebElement dateTime = findElement(By.id("datetime"));
        dateTime.click();
        dateTime.clear();
        dateTime.sendKeys("30/05/2021 20:00:00");
        getWait().until(ExpectedConditions.attributeToBe(dateTime, "value", "30/05/2021 20:00:00"));

        scrollClick(getDriver(), findElement(By.xpath("//button[@data-id='user']")));
        jsClick(getDriver(), findElement(By.xpath("//span[text()='tester43@tester.test']")));

        WebElement text = findElement(By.id("text"));
        text.click();
        text.sendKeys("Text");
        getWait().until(ExpectedConditions.attributeToBe(text, "value", "Text"));

        WebElement integer = findElement(By.id("int"));
        integer.sendKeys("23");
        getWait().until(ExpectedConditions.attributeToBe(integer, "value", "23"));

        WebElement decimal = findElement(By.id("decimal"));
        decimal.sendKeys("1.50");
        getWait().until(ExpectedConditions.attributeToBe(decimal, "value", "1.50"));

        findElement(By.id("pa-entity-form-draft-btn")).click();
    }

    private List<String> getActualValues(List<WebElement> actualElements) {
        List<String> list = new ArrayList<>();
        for (WebElement element : actualElements) {
            list.add(element.getText());
        }

        return list;
    }

    @Test
    public void testViewDraftRecord() {
        final List<String> expectedValues = Arrays.asList(
                "Pending", "Text", "23", "1.50", "30/05/2021", "30/05/2021 20:00:00");
        final String expectedUser = "tester43@tester.test";

        scrollClick(getDriver(), findElement(
                By.xpath("//div[@id='menu-list-parent']/ul/li/a/p[text()=' Board ']")));

        createNewDraftRecord();

        findElement(By.xpath("//a[contains(., 'List')]")).click();

        findElement(
                By.xpath("//button[@class='btn btn-round btn-sm btn-primary dropdown-toggle']")).click();
        findElement(By.linkText("view")).click();

        List<WebElement> actualElements = findElements(By.xpath("//span[@class='pa-view-field']"));
        WebElement actualUser = findElement(By.xpath("//div[@class='form-group']/p"));

        Assert.assertEquals(actualElements.size(), expectedValues.size());
        Assert.assertEquals(getActualValues(actualElements), expectedValues);
        Assert.assertEquals(actualUser.getText(), expectedUser);
    }
}
