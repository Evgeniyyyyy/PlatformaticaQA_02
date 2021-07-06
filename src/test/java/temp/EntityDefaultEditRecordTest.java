package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class EntityDefaultEditRecordTest extends BaseTest {
    private void createRecord() {
        findElement(By.xpath("//i[contains(text(), 'create_new_folder')] ")).click();
        findElement(By.id("pa-entity-form-save-btn")).click();
    }

    @Test
    public void testEditRecord() {

        final List<String> oldValuesText = new ArrayList<>();
        final List<String> newValuesText = new ArrayList<>();

        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains(text(), 'Default')] ")));

        createRecord();

        List<WebElement> cells = findElements(By.xpath("//td[@class='pa-list-table-th']"));
        Assert.assertEquals(cells.size(), 9);

        List<WebElement> values = findElements(By.xpath("//td[@class='pa-list-table-th']/a"));
        for (int i = 0; i < values.size(); i++) {
            oldValuesText.add(values.get(i).getText());
        }
        String userText = cells.get(cells.size() - 1).getText();
        oldValuesText.add(userText);

        findElement(By.xpath("//button/i[text()='menu']")).click();
        getWait().until(TestUtils.movingIsFinished(By.xpath("//a[text()='edit']"))).click();

        WebElement stringField = findElement(By.id("string"));
        stringField.click();
        stringField.clear();
        stringField.sendKeys("Happy New Year");

        TestUtils.jsClick(getDriver(), findElement(By.id("pa-entity-form-save-btn")));

        List<WebElement> newValues = findElements(By.xpath("//td[@class='pa-list-table-th']/a"));
        for (int i = 0; i < newValues.size(); i++) {
            newValuesText.add(newValues.get(i).getText());
        }

        List<WebElement> newCells = findElements(By.xpath("//td[@class='pa-list-table-th']"));
        newValuesText.add(newCells.get(newCells.size() - 1).getText());

        Assert.assertNotEquals(newValuesText, oldValuesText);

        Assert.assertEquals(newValuesText.get(0), "Happy New Year");
    }
}

