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

    @Test
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
    public void testViewCreatedRecord() {
        final List<String> exceptedValues = Arrays.asList("-1", "-1", "-2", "-3", "-5", "-8", "-13", "-21", "-34", "-55");

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
