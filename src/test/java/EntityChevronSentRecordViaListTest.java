import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;
import java.util.List;

public class EntityChevronSentRecordViaListTest extends BaseTest {

    final String enteredData = "firstExample";

    private void createNewRecord() {
        ProjectUtils.start(getDriver());
        TestUtils.scrollClick(getDriver(), By.xpath("//p[contains(text(),'Chevron')]"));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//i[contains(text(),'create_new_folder')]")))).click();
        getDriver().findElement(By.id("text")).sendKeys(enteredData);
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
    }

    @Test
    public void testSentRecordViaList() {
        createNewRecord();
        final List<Object> expectedRecordsRow = List.of(enteredData);
        final List<WebElement> actualList = findElements(By.xpath("//a[contains(text(),'firstExample')]"));
        Assert.assertEquals(actualList.size(), expectedRecordsRow.size());

        getDriver().findElement(By.xpath("//button[contains(text(),'Sent')]")).click();
        getDriver().findElement(By.xpath("//a[contains(text(),'Sent')]")).click();

        final List<Object> expectedRecordsRowAfterSent = List.of(enteredData);
        Assert.assertEquals(actualList.size(), expectedRecordsRowAfterSent.size());
    }
}
