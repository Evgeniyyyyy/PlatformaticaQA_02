package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.jsClick;

public class EntityArithmeticFunctionEditAndDeleteRecordTest extends BaseTest {

    private static final By F1_FIELD = By.id("f1");
    private static final By F2_FIELD = By.id("f2");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By ARITHMETIC_FUNCTION_MENU_ITEM = By.xpath("//p[contains(text(),'Arithmetic Function')]");
    private static final By ARITHMETIC_FUNCTION_TABLE = By.xpath("//tbody/tr/td[@class='pa-list-table-th']");
    private static final By RECYCLE_BIN_CONTENT_TABLE = By.xpath("//tbody//span");
    private static final By CARD_BODY = By.xpath("//div[@class = 'card-body']");

    final List<Object> expectedRecordsRow = List.of(8, 9, 17, -1, 72, 1);
    final List<Object> expectedRecordsRowAfterEdit = List.of(9, 5, 14, 4, 45, 2);


    @Test
    public void testCreateNewRecord() {
        TestUtils.scrollClick(getDriver(), ARITHMETIC_FUNCTION_MENU_ITEM);
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//i[contains(text(),'create_new_folder')]"))).click();

        getWait().until(ExpectedConditions.presenceOfElementLocated(F1_FIELD)).sendKeys("8");
        findElement(F2_FIELD).sendKeys("9");
        getWait().until(ExpectedConditions.attributeToBe(By.id("div"), "value", "0.89"));
        getWait().until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON)).click();

        final List<WebElement> actualList = findElements(ARITHMETIC_FUNCTION_TABLE);
        Assert.assertEquals(actualList.size(), expectedRecordsRow.size());
        for (int i = 0; i < expectedRecordsRow.size(); i++) {
            Assert.assertEquals(actualList.get(i).getText(), expectedRecordsRow.get(i).toString());
        }
    }

    @Test(dependsOnMethods = "testCreateNewRecord")
    public void testEditExistingRecord() {
        TestUtils.scrollClick(getDriver(), ARITHMETIC_FUNCTION_MENU_ITEM);
        final List<WebElement> actualList = findElements(ARITHMETIC_FUNCTION_TABLE);
        Assert.assertEquals(actualList.size(), expectedRecordsRow.size());
        for (int i = 0; i < expectedRecordsRow.size(); i++) {
            Assert.assertEquals(actualList.get(i).getText(), expectedRecordsRow.get(i).toString());
        }

        clickActionsEdit(getWait(), getDriver());

        findElement(F1_FIELD).clear();
        findElement(F1_FIELD).sendKeys("9");
        findElement(F2_FIELD).clear();
        findElement(F2_FIELD).sendKeys("5");
        getWait().until(ExpectedConditions.attributeToBe(By.id("div"), "value", "1.8"));
        getDriver().findElement(SAVE_BUTTON).click();

        final List<WebElement> actualListAfterEdit = findElements(ARITHMETIC_FUNCTION_TABLE);
        Assert.assertEquals(actualListAfterEdit.size(), expectedRecordsRowAfterEdit.size());
        for (int i = 0; i < expectedRecordsRowAfterEdit.size(); i++) {
            Assert.assertEquals(actualListAfterEdit.get(i).getText(), expectedRecordsRowAfterEdit.get(i).toString());
        }
    }

    @Test(dependsOnMethods = "testEditExistingRecord")
    public void testDeleteExistingRecord_SendToRecycleBin() {
        TestUtils.scrollClick(getDriver(), ARITHMETIC_FUNCTION_MENU_ITEM);
        getWait().until(ExpectedConditions.presenceOfElementLocated(ARITHMETIC_FUNCTION_TABLE));
        final List<WebElement> actualListAfterEdit = findElements(ARITHMETIC_FUNCTION_TABLE);
        Assert.assertEquals(actualListAfterEdit.size(), expectedRecordsRowAfterEdit.size());
        for (int i = 0; i < expectedRecordsRowAfterEdit.size(); i++) {
            Assert.assertEquals(actualListAfterEdit.get(i).getText(), expectedRecordsRowAfterEdit.get(i).toString());
        }

        clickActionsDelete(getWait(), getDriver());
        clickRecycleBin(getDriver());
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[normalize-space()='Arithmetic Function']")));
        getWait().until(ExpectedConditions.elementToBeClickable(RECYCLE_BIN_CONTENT_TABLE)).click();

        final List<WebElement> viewField = getDriver().findElements(By.xpath("//div/span"));
        Assert.assertEquals(viewField.size(), expectedRecordsRowAfterEdit.size());
        for (int i = 0; i < expectedRecordsRowAfterEdit.size(); i++) {
            Assert.assertEquals(viewField.get(i).getText(), expectedRecordsRowAfterEdit.get(i).toString());
        }
    }

    @Test(dependsOnMethods = "testDeleteExistingRecord_SendToRecycleBin")
    public void testDeleteExistingRecord_RestoreFromRecycleBin() {
        clickRecycleBin(getDriver());
        findElement(RECYCLE_BIN_CONTENT_TABLE).click();

        final List<WebElement> viewField = getDriver().findElements(By.xpath("//div/span"));
        Assert.assertEquals(viewField.size(), expectedRecordsRowAfterEdit.size());
        for (int i = 0; i < expectedRecordsRowAfterEdit.size(); i++) {
            Assert.assertEquals(viewField.get(i).getText(), expectedRecordsRowAfterEdit.get(i).toString());
        }

        findElement(By.xpath("//button[@class='btn btn-sm pa-btn-close-form']")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='restore as draft']"))).click();

        WebElement contentRecycleBinCard = findElement(CARD_BODY);
        Assert.assertEquals(contentRecycleBinCard.getText(), "Good job with housekeeping! Recycle bin is currently empty!");

        TestUtils.scrollClick(getDriver(), ARITHMETIC_FUNCTION_MENU_ITEM);

        final List<WebElement> actualListAfterEdit = findElements(ARITHMETIC_FUNCTION_TABLE);
        Assert.assertEquals(actualListAfterEdit.size(), expectedRecordsRowAfterEdit.size());
        for (int i = 0; i < expectedRecordsRowAfterEdit.size(); i++) {
            Assert.assertEquals(actualListAfterEdit.get(i).getText(), expectedRecordsRowAfterEdit.get(i).toString());
        }
    }

    @Test(dependsOnMethods = "testDeleteExistingRecord_RestoreFromRecycleBin")
    public void testDeleteExistingRecord_DeletePermanently() {
        TestUtils.scrollClick(getDriver(), ARITHMETIC_FUNCTION_MENU_ITEM);
        final List<WebElement> actualListAfterEdit = findElements(ARITHMETIC_FUNCTION_TABLE);
        Assert.assertEquals(actualListAfterEdit.size(), expectedRecordsRowAfterEdit.size());
        for (int i = 0; i < expectedRecordsRowAfterEdit.size(); i++) {
            Assert.assertEquals(actualListAfterEdit.get(i).getText(), expectedRecordsRowAfterEdit.get(i).toString());
        }

        clickActionsDelete(getWait(), getDriver());
        clickRecycleBin(getDriver());
        findElement(By.xpath("//a[normalize-space()='delete permanently']")).click();

        WebElement contentRecycleBinCard = findElement(CARD_BODY);
        Assert.assertEquals(contentRecycleBinCard.getText(), "Good job with housekeeping! Recycle bin is currently empty!");

        TestUtils.scrollClick(getDriver(), ARITHMETIC_FUNCTION_MENU_ITEM);
        Assert.assertEquals(findElements(ARITHMETIC_FUNCTION_TABLE).size(), 0);

    }
}

