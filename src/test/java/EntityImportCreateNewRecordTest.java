import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static utils.ProjectUtils.clickSave;
import static utils.TestUtils.*;

public class EntityImportCreateNewRecordTest extends BaseTest {

    private static final String STRING_INPUT = "Hello";
    private static final String TEXT_INPUT = "everyone";
    private static final String INT_INPUT = "555";
    private static final String DECIMAL_INPUT = "55.55";
    private static final String DATA_INPUT = "03/06/2021";
    private static final String DATA_TIME_INPUT = "12/06/2021 09:09:09";
    private static final String EMPTY = "";
    private static final String USER_DEFAULT = "apptester1@tester.test";

    private void clickImportMenu() {
        scrollClick(getDriver(), getDriver().findElement(By.xpath("//p[.=' Import ']")));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                By.xpath("//i[text() = 'create_new_folder']"))));
    }

    private void clickCreateNewFolderButton() {
        getDriver().findElement(By.xpath("//i[text() = 'create_new_folder']")).click();
    }

    private void inputValue() {
        getDriver().findElement(By.id("string")).sendKeys(STRING_INPUT);
        getDriver().findElement(By.id("text")).sendKeys(TEXT_INPUT);
        getDriver().findElement(By.id("int")).sendKeys(INT_INPUT);
        getDriver().findElement(By.id("decimal")).sendKeys(DECIMAL_INPUT);

        getDriver().findElement(By.id("date")).click();
        getDriver().findElement(By.id("date")).clear();
        getDriver().findElement(By.id("date")).sendKeys(DATA_INPUT);

        getDriver().findElement(By.id("datetime")).click();
        getDriver().findElement(By.id("datetime")).clear();
        getDriver().findElement(By.id("datetime")).sendKeys(DATA_TIME_INPUT);
    }

    private String getAttributeClass() {
        return getDriver().findElement(By.xpath("//tbody/tr/td[1]/i")).getAttribute("class");
    }

    private String getShowingOne() {
        return getDriver().findElement(By.xpath("//span[@class = 'pagination-info']")).getText();
    }

    private void clickDropDownMenu() {
        getDriver().findElement(By.xpath("//i[contains(., 'menu')]")).click();
    }

    private void clickDeleteMenu() {
        clickDropDownMenu();
        getWait().until(movingIsFinished(By.xpath("//a[contains (text(), 'delete')]"))).click();
        getWait().until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class = 'card-body ']")));
    }

    @Test
    public void testCreateNewRecord() {
        final List<String> expectedValues = Arrays.asList(
                STRING_INPUT, TEXT_INPUT, INT_INPUT, DECIMAL_INPUT, DATA_INPUT, DATA_TIME_INPUT, EMPTY, USER_DEFAULT);

        clickImportMenu();
        clickCreateNewFolderButton();
        inputValue();
        jsClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));

        List<WebElement> cells = getDriver().findElements(By.xpath("//td[@class= 'pa-list-table-th']"));
        List<String> actualValues = cells.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertEquals(actualValues, expectedValues);
        Assert.assertEquals(cells.size(), expectedValues.size());
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }

    @Test(dependsOnMethods = "testCreateNewRecord")
    public void testDeleteRecord() {
        String expectedTextCardBodyBeforeDelete = "String\n" + "Text\n" + "Int\n" + "Decimal\n" + "Date\n" + "Datetime\n" +
                "File\n" + "User\n" + "Actions\n" + "Hello\n" + "everyone\n" + "555\n" + "55.55\n" +
                "03/06/2021\n" +
                "12/06/2021 09:09:09\n" +
                "apptester1@tester.test\n" +
                "menu\n" +
                "Showing 1 to 1 of 1 rows";

        String expectedTextRecycleBinBeforeDelete = "delete_outline";
        String expectedTextRecycleBinAfterDelete = "delete_outline\n" + "1";

        clickImportMenu();

        String textCardBodyBeforeDelete = getDriver().findElement(By.xpath("//div[@class= 'card-body ']")).getText();
        String textRecycleBinBeforeDelete = getDriver().findElement(
                By.xpath("//a[@href= 'index.php?action=recycle_bin']")).getText();

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

    @Test
    public void testSearchCreatedRecordImport() {
        clickImportMenu();
        completeNewFields("Milk");
        completeNewFields("Cow");
        completeNewFields("Milk2");
        putInSearch("mIlK");

        getWait().until(ExpectedConditions.textToBePresentInElementLocated(
                By.xpath("//span[@class='pagination-info']"),
                "Showing 1 to 2 of 2 rows"));
        Assert.assertEquals(findElement(
                By.xpath("//span[@class='pagination-info']")).getText(),
                "Showing 1 to 2 of 2 rows");

        List<String> results = findElements(By.xpath("//tbody/tr/td[2]/a"))
                .stream()
                .map(x -> x.getText())
                .collect(Collectors.toList());
        Assert.assertEquals(results, List.of("Milk", "Milk2"));
    }

    public void completeNewFields(String str1) {
        clickCreateNewFolderButton();
        getDriver().findElement(By.id("string")).sendKeys(str1);
        getWait().until(ExpectedConditions.attributeToBe(
                By.id("string"), "value", str1));
        getDriver().findElement(By.id("text")).sendKeys(str1);
        jsClick(getDriver(), getDriver().findElement(
                By.id("pa-entity-form-save-btn")));
    }

    public void putInSearch(String str1) {
        getDriver().findElement(By.xpath("//input[@placeholder='Search']")).sendKeys(str1);
    }
}