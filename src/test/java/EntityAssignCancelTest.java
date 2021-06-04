import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityAssignCancelTest extends BaseTest {

    @Test
    public void testCancelRecord() {

        start(getDriver());

        WebElement assignMenu = getDriver().findElement(By.xpath("//p[contains(text(),' Assign ')]"));
        scrollClick(getDriver(), assignMenu);

        getWait().until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(By.xpath("//i[text()='create_new_folder']"))))
                .click();

        getDriver().findElement(By.id("string")).sendKeys("String");
        getDriver().findElement(By.id("text")).sendKeys("Text");
        getDriver().findElement(By.id("int")).sendKeys("7");
        getDriver().findElement(By.id("date")).click();
        getDriver().findElement(By.id("date")).clear();
        getDriver().findElement(By.id("date")).sendKeys("02/06/2021");
        getDriver().findElement(By.id("datetime")).click();
        getDriver().findElement(By.id("datetime")).clear();
        getDriver().findElement(By.id("datetime")).sendKeys("02/06/2021 22:00:28");

        WebElement cancelButton = getDriver().findElement(By.xpath("//button[text()='Cancel']"));
        scrollClick(getDriver(), cancelButton);

        List<WebElement> records = getDriver().findElements(By.xpath("//td[@class = 'pa-list-table-th']"));

        Assert.assertEquals(records.size(), 0);
    }
}
