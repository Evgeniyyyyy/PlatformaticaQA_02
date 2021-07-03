package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.List;

public class EntityBoardCreateRecordTest extends BaseTest {

    @Test
    public void testCreateRecord() {

        final List<String> expected = List.of("anyText", "2", "0.2", "", "", "");

        TestUtils.scrollClick(getDriver(), getDriver()
                .findElement(By.xpath("//div[@id='menu-list-parent']//li[10]//p")));
        getDriver().findElement(By.xpath("//div[@class='card-icon']/i")).click();
        getDriver().findElement(By.id("text")).sendKeys("anyText");
        getDriver().findElement(By.id("int")).sendKeys("2");
        getDriver().findElement(By.id("decimal")).sendKeys("0.2");
        WebElement saveButton = findElement(By.id("pa-entity-form-save-btn"));
        TestUtils.scrollClick(getDriver(), saveButton);

        getDriver().findElement(By.xpath("//li[2]//i[contains (text(),'list')]"));
        List<WebElement> actual = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']/a"));
        for (int i = 0; i < actual.size(); i++) {
            Assert.assertEquals(actual.get(i).getText(), expected);
        }
    }
}
