import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;

import java.util.List;

import static utils.TestUtils.*;

public class EntityExportDestinationDeleteRecordTest extends BaseTest {
    private static final String LINK_RECYCLE_BIN = "https://ref2.eteam.work/index.php?action=recycle_bin";

    private void clickMenuExportDestination() {
        scrollClick(getDriver(), By.xpath("//p[contains(text(), 'Export destination')]"));
    }

    private void createNewRecord() {
        findElement(By.xpath("//div[@class = 'card-icon']")).click();
        findElement(By.id("string")).sendKeys("sdfghjkl");
        ProjectUtils.clickSave(getDriver());
    }

    private void goToRecycleBin() {
        getDriver().get(LINK_RECYCLE_BIN);
    }

    private boolean isRecycleBinEmpty() {
        List<WebElement> recycleBinRecordings = findElements(By
                .xpath("//table[@class = 'table pa-list-table table-bordered table-hover']/tbody/tr"));
        return (recycleBinRecordings.size() < 1);
    }

    @Test
    public void testDeleteRecord()  {

        ProjectUtils.start(getDriver());
        clickMenuExportDestination();
        createNewRecord();

        String text = findElement(By.xpath("//span[@class = 'pagination-info']")).getText();
        Assert.assertEquals(text, "Showing 1 to 1 of 1 rows");

        goToRecycleBin();
        Assert.assertTrue(isRecycleBinEmpty(), "Recycle Bin is not Empty!");

        clickMenuExportDestination();
        getDriver().findElement(By.xpath("//tr[@data-index='0']/td/div/button")).click();
        getWait().until(movingIsFinished(By.xpath("//a[contains(text(), 'delete')]"))).click();

        Assert.assertEquals(findElements(
                By.xpath("//span[@class = 'notification']")).size(), 1);
        Assert.assertEquals(findElements(By.xpath("//tbody/tr")).size(), 0);
    }
}
