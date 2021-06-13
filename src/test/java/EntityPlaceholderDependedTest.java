import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.TestUtils.movingIsFinished;
import static utils.TestUtils.scrollClick;

public class EntityPlaceholderDependedTest extends BaseTest {

    private void clickEventsPlaceholderMenu() {
        TestUtils.scrollClick(getDriver(), getDriver().
                findElement(By.xpath("//p[contains (text(), 'Placeholder')]")));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']"))));
    }

    private void clickCreateRecordButton() {
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
    }

    private void clickSaveButton() {
        scrollClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']"))));
    }

    private void clickActionMenu() {
        getDriver().findElement(By.xpath("//i[contains(., 'menu')]")).click();
    }

    private void clickEditMenu() {
        clickActionMenu();
        getWait().until(movingIsFinished(By.xpath("//a[contains(., 'edit')]"))).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.id("string")));
    }

    private List<WebElement> getCells() {
        return getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));
    }

    private List<String> getRowValues() {
        List<String> actualValues = new ArrayList<>();

        for(WebElement cell : getCells()) {
            actualValues.add(cell.getText());
        }

        return actualValues;
    }

    private String getAttributeClass() {
        return getDriver().findElement(By.xpath("//tbody/tr/td[1]/i")).getAttribute("class");
    }

    @Test
    public void testCreateRecord() {

        final List<String> expected = List.of
                ("String field input", "Text field input", "1000", "20.55", "", "", "", "", "apptester1@tester.test");

        clickEventsPlaceholderMenu();
        clickCreateRecordButton();
        findElement(By.id("string")).sendKeys("String field input");
        findElement(By.id("text")).sendKeys("Text field input");
        getDriver().findElement(By.id("int")).sendKeys("1000");
        getDriver().findElement(By.id("decimal")).sendKeys("20.55");
        clickSaveButton();

        Assert.assertEquals(getRowValues(), expected);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testCreateRecord")
    public void testEditRecord() {

        final List<String> expected = List.of
                ("Record#1", "Some text here...", "1111", "155.11", "", "", "", "", "apptester1@tester.test");

        clickEventsPlaceholderMenu();
        clickEditMenu();
        WebElement stringField = getWait().until(ExpectedConditions.elementToBeClickable(By.id("string")));
        stringField.click();
        stringField.clear();
        getDriver().findElement(By.id("string")).sendKeys("Record#1");
        WebElement textField = getDriver().findElement(By.id("text"));
        textField.clear();
        textField.sendKeys("Some text here...");
        WebElement intField = getDriver().findElement(By.id("int"));
        intField.clear();
        intField.sendKeys("1111");
        WebElement decimalField = getDriver().findElement(By.id("decimal"));
        decimalField.clear();
        decimalField.sendKeys("155.11");
        clickSaveButton();

        List<WebElement> actual = getDriver().findElements(By.xpath("//td[@class='pa-list-table-th']"));
        for (int i = 0; i < actual.size(); i++) {
            Assert.assertEquals(actual.get(i).getText(), expected.get(i));
        }
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {

        List<String> expected = List.of
                ("Record#1", "Some text here...", "1111", "155.11", "", "", "", "", "apptester1@tester.test");

        clickEventsPlaceholderMenu();
        clickActionMenu();
        getWait().until(TestUtils.movingIsFinished(By.xpath("//a[contains (text(), 'delete')]"))).click();

        ProjectUtils.clickRecycleBin(getDriver());
        getDriver().findElement(By.xpath("//tbody//span")).click();

        List<WebElement> actual = getDriver().findElements(By.xpath("//div/span"));
        for (int i = 0; i < actual.size(); i++) {
            Assert.assertEquals(actual.get(i).getText(), expected.get(i));
        }
    }

    @Test(dependsOnMethods = "testDeleteRecord")
    public void testDeleteRecordPermanently () {

        clickEventsPlaceholderMenu();
        getDriver().findElement(By.xpath("//i[contains (text(), 'delete_outline')]")).click();
        getDriver().findElement(By.xpath("//a[contains (text(), 'delete permanently')]")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//div[@class='card-body']"))
                .getText(), "Good job with housekeeping! Recycle bin is currently empty!");
    }
}
