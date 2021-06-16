import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityImportDeleteTest extends BaseTest {

  @Ignore
    @Test
    public void testImportDeletePermanently() throws InterruptedException {

        start(getDriver());
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//*[@id='menu-list-parent']/ul/li[7]/a/p")));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
              By.xpath("//i[text() = 'create_new_folder']"))));
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("string")).sendKeys("Some string");

        scrollClick(getDriver(), getDriver().findElement(By.xpath("//*[@id='pa-entity-form-save-btn']")));
        getDriver().findElement(By.xpath("//*[@id='pa-all-entities-table']/tbody/tr[1]/td[10]/div/button")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//*[@id='pa-all-entities-table']/tbody/tr/td[10]/div/ul/li[3]/a"))));
        getDriver().findElement(By.xpath("//*[@id='pa-all-entities-table']/tbody/tr/td[10]/div/ul/li[3]/a")).click();
        getDriver().findElement(By.xpath("//*[text()='delete_outline']")).click();
        getDriver().findElement(By.xpath("//*[text()='delete permanently']")).click();

        boolean res = true;
        if (getDriver().findElements(By.xpath("//*[text()='Some string']")).size() > 0) res = false;

        Assert.assertTrue(res);

        }

}
