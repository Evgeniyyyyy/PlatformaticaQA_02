import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static utils.ProjectUtils.start;
import static utils.TestUtils.movingIsFinished;
import static utils.TestUtils.scrollClick;

public class EntityEventsChain1Test extends BaseTest {

    private void clickEventsChain1Menu(){
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[contains(.,'Events Chain 1')]")));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']"))));
    }
    private void clickCreateNewFolderButton() {
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.id("f1")));
    }

    private void inputF1Value(String f1Value){
        getDriver().findElement(By.id("f1")).sendKeys(f1Value);
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(getDriver().findElement(By.id("f10")), "value"));
    }

    private void clickSaveButton(){
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

    private List<WebElement> getCells(){
        return getDriver().findElements(By.xpath("//table[@id = 'pa-all-entities-table']/tbody/tr/td/a"));
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
    public void testCreateNewRecord() {
        final String f1InputValue = "1";
        final List<String> expectedValues = Arrays.asList("1", "2", "4", "8", "16", "32", "64", "128", "256", "512");

        start(getDriver());
        clickEventsChain1Menu();
        clickCreateNewFolderButton();
        inputF1Value(f1InputValue);
        clickSaveButton();

        Assert.assertEquals(getCells().size(), expectedValues.size());
        Assert.assertEquals(getRowValues(), expectedValues);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test
    public void testEditRecord() {
        List<String> expectedValues = List.of("2", "4", "8", "16", "32", "64", "128", "256", "512", "1024");

        start(getDriver());

        TestUtils.scrollClick(getDriver(), By.xpath("//p[contains(text(), 'Events Chain 1')]"));

        createNewRecord();

        String text = getDriver().findElement(By.xpath("//span[@class = 'pagination-info']")).getText();

        Assert.assertEquals(text, "Showing 1 to 1 of 1 rows");

        List<WebElement> cells = getDriver().findElements(By.xpath("//td[@class = 'pa-list-table-th']"));
        List<String> oldValues = new ArrayList<>();
        for(WebElement cell: cells){
            oldValues.add(cell.getText());
        }

        getDriver().findElement(By.xpath("//i[contains(., 'menu')]")).click();

        getWait().until(movingIsFinished(By.xpath("//a[contains(., 'edit')]"))).click();

        getDriver().findElement(By.id("f10")).clear();
        getDriver().findElement(By.id("f1")).clear();
        getDriver().findElement(By.id("f1")).sendKeys("2");
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(getDriver().findElement(By.id("f10")), "value"));

        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();

        List<WebElement> cells1 = getDriver().findElements(By.xpath("//td[@class = 'pa-list-table-th']"));
        List<String> actualValues = new ArrayList<>();
        for(WebElement cell: cells1){
            actualValues.add(cell.getText());
        }

        Assert.assertEquals(actualValues, expectedValues);
        Assert.assertNotEquals(actualValues, oldValues);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }
}
