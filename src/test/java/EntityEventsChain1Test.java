import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.TestUtils.*;

public class EntityEventsChain1Test extends BaseTest {

    private void clickEventsChain1Menu() {
        scrollClick(getDriver(), getDriver().findElement(
                By.xpath("//p[contains(.,'Events Chain 1')]")));
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']"))));
    }

    private void clickCreateNewFolderButton() {
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.id("f1")));
    }

    private void clickSaveButton() {
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']"))));
    }

    private void clickDropDownMenu() {
        getDriver().findElement(By.xpath("//i[contains(., 'menu')]")).click();
    }

    private void clickEditMenu() {
        clickDropDownMenu();
        getWait().until(movingIsFinished(By.xpath("//a[contains(., 'edit')]"))).click();
        getWait().until(ExpectedConditions.presenceOfElementLocated(By.id("f1")));
    }

    private void clickDeleteMenu() {
        clickDropDownMenu();
        getWait().until(movingIsFinished(By.xpath("//a[contains (text(), 'delete')]"))).click();
        getWait().until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'card-body ']")));
    }

    private void inputF1Value(String value) {
        getDriver().findElement(By.id("f1")).sendKeys(value);
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(getDriver().findElement(By.id("f10")), "value"));
    }

    private void editRecord(String value){
        final WebElement F1 = getDriver().findElement(By.id("f1"));
        final WebElement F10 = getDriver().findElement(By.id("f10"));

        F10.clear();
        F1.click();
        F1.clear();
        F1.sendKeys(value);
        getWait().until(ExpectedConditions.attributeToBeNotEmpty(F10, "value"));
    }

    private List<WebElement> getCells() {
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

    private String getPaginationInfo() {
        return getDriver().findElement(By.xpath("//span[@class = 'pagination-info']")).getText();
    }

    @Test
    public void testCreateNewRecord() {

        final String f1Value = "1";
        final List<String> expectedValues = Arrays.asList("1", "2", "4", "8", "16", "32", "64", "128", "256", "512");

        clickEventsChain1Menu();
        clickCreateNewFolderButton();
        inputF1Value(f1Value);
        clickSaveButton();

        Assert.assertEquals(getCells().size(), expectedValues.size());
        Assert.assertEquals(getRowValues(), expectedValues);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testCreateNewRecord")
    public void testEditRecord() {

        final String newF1Value = "2";
        final List<String> expectedValues = List.of("2", "4", "8", "16", "32", "64", "128", "256", "512", "1024");

        clickEventsChain1Menu();

        Assert.assertEquals(getPaginationInfo(), "Showing 1 to 1 of 1 rows");

        final List<String> oldValues = getRowValues();

        clickEditMenu();
        editRecord(newF1Value);
        clickSaveButton();

        Assert.assertEquals(getRowValues(), expectedValues);
        Assert.assertNotEquals(getRowValues(), oldValues);
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testEditRecord")
    public void testDeleteRecord() {

        String expectedTextCardBodyBeforeDelete = "F1\n" + "F2\n" + "F3\n" + "F4\n" + "F5\n" + "F6\n" + "F7\n" + "F8\n"
                + "F9\n" + "F10\n" + "Actions\n" + "2\n" + "4\n" + "8\n" + "16\n" + "32\n" + "64\n" + "128\n" +
                "256\n" + "512\n" + "1024\n" + "menu\n" + "Showing 1 to 1 of 1 rows";
        String expectedTextRecycleBinBeforeDelete = "delete_outline";
        String expectedTextRecycleBinAfterDelete = "delete_outline\n" + "1";

        clickEventsChain1Menu();

        Assert.assertEquals(getPaginationInfo(), "Showing 1 to 1 of 1 rows");

        String textCardBodyBeforeDelete = getDriver().findElement(By.xpath("//div[@class = 'card-body ']")).getText();
        String textRecycleBinBeforeDelete = getDriver().findElement(
                By.xpath("//a[@href='index.php?action=recycle_bin']")).getText();

        Assert.assertEquals(textCardBodyBeforeDelete, expectedTextCardBodyBeforeDelete);
        Assert.assertEquals(textRecycleBinBeforeDelete, expectedTextRecycleBinBeforeDelete);

        clickDeleteMenu();

        String textCardBodyAfterDelete = getDriver().findElement(By.xpath("//div[@class = 'card-body ']")).getText();
        String textRecycleBinAfterDelete = getDriver().findElement(
                By.xpath("//a[@href='index.php?action=recycle_bin']")).getText();

        Assert.assertTrue(textCardBodyAfterDelete.isBlank());
        Assert.assertTrue(textCardBodyAfterDelete.isEmpty());
        Assert.assertNotEquals(textRecycleBinBeforeDelete, textRecycleBinAfterDelete);
        Assert.assertEquals(textRecycleBinAfterDelete, expectedTextRecycleBinAfterDelete);
    }
}
