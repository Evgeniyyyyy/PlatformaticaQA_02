import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.TestUtils.movingIsFinished;
import static utils.TestUtils.scrollClick;

public class EntityEventsChain2Test extends BaseTest {

    private void clickEventsChain2Menu() {
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains(.,'Events Chain 2')]")));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']"))));
    }

    private void clickCreateNewFolderButton() {
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.id("f1")));
    }

    private void inputF1Value(String f1Value) {
        getDriver().findElement(By.id("f1")).sendKeys(f1Value);
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(getDriver().findElement(By.id("f10")), "value"));
    }

    private void editRecord(String f1Value){
        final WebElement F1 = getDriver().findElement(By.id("f1"));
        final WebElement F10 = getDriver().findElement(By.id("f10"));

        F10.clear();
        F1.click();
        F1.clear();
        F1.sendKeys(f1Value);
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(F10, "value"));
    }

    private void clickSaveButton() {
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']"))));
    }

    private void createNewRecord() {
        getDriver().findElement(By.xpath("//i[contains(text(), 'create_new_folder')]")).click();
        getDriver().findElement(By.id("f1")).sendKeys("5");
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(getDriver().findElement(By.id("f10")), "value"));

        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
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

    private String getAttributeClass() {
        return getDriver().findElement(By.xpath("//tbody/tr/td[1]/i")).getAttribute("class");
    }

    @Test
    public void testCreateNewRecord01() {
        final String f1InputValue = "1";
        final List<String> exceptedValues = Arrays.asList("1", "1", "2", "3", "5", "8", "13", "21", "34", "55");

        clickEventsChain2Menu();
        clickCreateNewFolderButton();
        inputF1Value(f1InputValue);
        clickSaveButton();

        Assert.assertEquals(getCells().size(), exceptedValues.size());
        Assert.assertEquals(getRowValues(), exceptedValues);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test
    public void testCreateNewRecord02() {
        final String f1InputValue = "0";
        final List<String> exceptedValues = Arrays.asList("0", "0", "0", "0", "0", "0", "0", "0", "0", "0");

        clickEventsChain2Menu();
        clickCreateNewFolderButton();
        getDriver().findElement(By.id("f1")).sendKeys(f1InputValue);
        clickSaveButton();

        Assert.assertEquals(getCells().size(), exceptedValues.size());
        Assert.assertEquals(getRowValues(), exceptedValues);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testCreateNewRecord02")
    public void testDeleteCreatedRecord() {
        clickEventsChain2Menu();

        getDriver().findElement(By.xpath("//i[contains(., 'menu')]")).click();
        getWait().until(movingIsFinished(By.xpath("//a[contains(text(), 'delete')]"))).click();

        String expectedTextRecycleBinAfterDelete = "delete_outline\n" + "1";
        String textCardBodyAfterDelete = getDriver().findElement(By.xpath("//div[@class = 'card-body ']")).getText();
        String textRecycleBinAfterDelete = getDriver().findElement(
                By.xpath("//a[@href='index.php?action=recycle_bin']")).getText();

        Assert.assertTrue(textCardBodyAfterDelete.isBlank());
        Assert.assertTrue(textCardBodyAfterDelete.isEmpty());
        Assert.assertEquals(textRecycleBinAfterDelete, expectedTextRecycleBinAfterDelete);
    }

    @Test(dependsOnMethods = "testDeleteCreatedRecord")
    public void testCreateNewRecord03() {
        final String f1InputValue = "-1";
        final List<String> exceptedValues = Arrays.asList("-1", "-1", "-2", "-3", "-5", "-8", "-13", "-21", "-34", "-55");

        clickEventsChain2Menu();
        clickCreateNewFolderButton();
        inputF1Value(f1InputValue);
        clickSaveButton();

        Assert.assertEquals(getCells().size(), exceptedValues.size());
        Assert.assertEquals(getRowValues(), exceptedValues);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testCreateNewRecord03")
    public void testEditCreatedRecord() {
        final String f1InputValue = "3";
        final List<String> exceptedValues = Arrays.asList("3", "3", "6", "9", "15", "24", "39", "63", "102", "165");

        clickEventsChain2Menu();

        getDriver().findElement(By.xpath("//i[contains(., 'menu')]")).click();
        getWait().until(movingIsFinished(By.xpath("//a[contains(., 'edit')]"))).click();

        editRecord(f1InputValue);
        clickSaveButton();

        Assert.assertEquals(getCells().size(), exceptedValues.size());
        Assert.assertEquals(getRowValues(), exceptedValues);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testEditCreatedRecord")
    public void testViewCreatedRecord() {
        final List<String> exceptedValues = Arrays.asList("3", "3", "6", "9", "15", "24", "39", "63", "102", "165");

        clickEventsChain2Menu();

        getDriver().findElement(By.xpath("//i[contains(., 'menu')]")).click();
        getWait().until(movingIsFinished(By.xpath("//a[contains(., 'view')]"))).click();

        List<WebElement> cells = getDriver().findElements(By.xpath("//span[@class = 'pa-view-field']"));
        List<String> actualValues = new ArrayList<>();
        for(WebElement cell: cells){
            actualValues.add(cell.getText());
        }

        Assert.assertEquals(actualValues, exceptedValues);
    }
}
