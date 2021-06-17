import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.ProjectUtils.clickActionsView;
import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityViewCreatedRecordTest extends BaseTest {

    @Test
    public void testViewCreatedRecord() throws InterruptedException {

        start(getDriver());

        getDriver().findElement(By.xpath("//*[@id='pa-menu-item-45']/a/p")).click();

        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("title")).sendKeys("Test text 1");
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//*[@id='pa-entity-form-save-btn']")));

        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("title")).sendKeys("Unique text test 2");
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//*[@id='pa-entity-form-save-btn']")));

        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("title")).sendKeys("Some random text 3");
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//*[@id='pa-entity-form-save-btn']")));

        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("title")).sendKeys("Unique text we are looking for 4");
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//*[@id='pa-entity-form-save-btn']")));

        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("title")).sendKeys("Some more text 5");
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//*[@id='pa-entity-form-save-btn']")));

        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getDriver().findElement(By.id("title")).sendKeys("MORE TEXT MORE 6");
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//*[@id='pa-entity-form-save-btn']")));


        getDriver().findElement(By.xpath(
                "//*[contains(text(), 'Unique text we are looking for')]/../../td[11]/div/button")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath(
                        "//*[contains(text(), 'Unique text we are looking for')]/../../td[11]/div/ul/li[1]/a"))));
        getDriver().findElement(By.xpath(
                "//*[contains(text(), 'Unique text we are looking for')]/../../td[11]/div/ul/li[1]/a")).click();

        boolean res = getDriver().findElements(By.xpath(
                "//*[contains(text(), 'Unique text we are looking for')]")).size() > 0
                && getDriver().findElements(By.xpath("//*[contains(text(), 'Showing')]")).size() == 0;

        Assert.assertTrue(res);

    }
}