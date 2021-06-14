import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import javax.swing.text.Utilities;
import java.util.ArrayList;
import java.util.List;

import static utils.TestUtils.scrollClick;

public class EntityReadonlyTest extends BaseTest {

    private final List<String> EXPECTED_VALUES = List.of("", "", "0", "0.00", "", "");

    private void clickEventsReadonlyMenu() {
        TestUtils.scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains (text(), 'Readonly')]")));
    }

    private void clickCreateRecordButton() {
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
    }

    private void clickSaveButton() {
        scrollClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']"))));
    }

    private String getAttributeClass() {
        return getDriver().findElement(By.xpath("//tbody/tr/td[1]/i")).getAttribute("class");
    }

    private List<WebElement> getCells() {
        return getDriver().findElements(By.xpath("//table[@id = 'pa-all-entities-table']/tbody/tr/td/a"));
    }

    private List<String> getRowValues() {
        List<String> actualValues = new ArrayList<>();

        for (WebElement cell : getCells()) {
            actualValues.add(cell.getText());
        }

        return actualValues;
    }

    @Test
    public void testCreateRecord() {

        clickEventsReadonlyMenu();
        clickCreateRecordButton();
        findElement(By.id("string")).sendKeys("String");
        findElement(By.id("text")).sendKeys("Text");
        findElement(By.id("int")).sendKeys("1000");
        findElement(By.id("decimal")).sendKeys("20.55");
        clickSaveButton();

        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
        Assert.assertEquals(getRowValues(), EXPECTED_VALUES);
    }

    @Test
    public void testCreateDraftRecord() {

        clickEventsReadonlyMenu();
        clickCreateRecordButton();
        findElement(By.id("string")).sendKeys("String");
        findElement(By.id("text")).sendKeys("Text");
        findElement(By.id("int")).sendKeys("1000");
        findElement(By.id("decimal")).sendKeys("20.55");
        scrollClick(getDriver(), getDriver().findElement(
                By.id("pa-entity-form-draft-btn")));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath(
                        "//i[text() = 'create_new_folder']"))));


        Assert.assertEquals(getAttributeClass(), "fa fa-pencil");
        Assert.assertEquals(getRowValues(), EXPECTED_VALUES);
    }

}
