import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;
import java.util.List;
import static utils.TestUtils.jsClick;

public class EntityArithmeticFunctionEditExistingRecordTest extends BaseTest {

    private static final By F1_FIELD = By.id("f1");
    private static final By F2_FIELD = By.id("f2");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");

    private void createNewRecord() {
        ProjectUtils.start(getDriver());
        TestUtils.scrollClick(getDriver(), By.xpath("//p[contains(text(),'Arithmetic Function')]"));
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(text(),'create_new_folder')]"))).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(F1_FIELD)).sendKeys("8");
        findElement(F2_FIELD).sendKeys("9");
        getWait().until(ExpectedConditions.attributeToBe(By.id("div"), "value", "0.89"));
        getWait().until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON)).click();
    }

    private void clickEdit() {
        jsClick(getDriver(), getDriver().findElement(By.cssSelector(".btn-round")));
        jsClick(getDriver(), getDriver().findElement(By.xpath("//a[contains(., 'edit')]")));
    }

    private void editFields() {
        findElement(F1_FIELD).clear();
        findElement(F1_FIELD).sendKeys("9");
        findElement(F2_FIELD).clear();
        findElement(F2_FIELD).sendKeys("5");
        getWait().until(ExpectedConditions.attributeToBe(By.id("div"), "value", "1.8"));
        getDriver().findElement(SAVE_BUTTON).click();
    }

    @Test
    public void testEditExistingRecord() {
        createNewRecord();
        final List<Object> expectedRecordsRow = List.of(8, 9, 17, -1, 72, 1);
        final List<WebElement> actualList = findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        Assert.assertEquals(actualList.size(), expectedRecordsRow.size());
        for (int i = 0; i < expectedRecordsRow.size(); i++) {
            Assert.assertEquals(actualList.get(i).getText(), expectedRecordsRow.get(i).toString());
        }

        clickEdit();

        editFields();
        final List<Object> expectedRecordsRowAfterEdit = List.of(9, 5, 14, 4, 45, 2);
        final List<WebElement> actualListAfterEdit = findElements(By.xpath("//tbody/tr/td[@class='pa-list-table-th']"));
        Assert.assertEquals(actualListAfterEdit.size(), expectedRecordsRowAfterEdit.size());
        for (int i = 0; i < expectedRecordsRowAfterEdit.size(); i++) {
            Assert.assertEquals(actualListAfterEdit.get(i).getText(), expectedRecordsRowAfterEdit.get(i).toString());
        }
    }
}
