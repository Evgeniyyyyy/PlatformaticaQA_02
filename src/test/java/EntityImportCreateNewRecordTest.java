import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static utils.TestUtils.scrollClick;

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

    private void clickCreateNewFolderButten() {
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

    private void clickSaveButton() {
        getDriver().findElement(By.id("pa-entity-form-save-btn")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(
                By.xpath("//i[text() = 'create_new_folder']"))));
    }

    @Test
    public void testCreateNewRecord() {
        final List<String> expectedValues = Arrays.asList(
                STRING_INPUT, TEXT_INPUT, INT_INPUT, DECIMAL_INPUT, DATA_INPUT, DATA_TIME_INPUT, EMPTY, USER_DEFAULT);

        clickImportMenu();
        clickCreateNewFolderButten();
        inputValue();
        clickSaveButton();

        List<WebElement> cells = getDriver().findElements(By.xpath("//td[@class= 'pa-list-table-th']"));
        List<String> actualValues = cells.stream().map(WebElement::getText).collect(Collectors.toList());

        Assert.assertEquals(actualValues, expectedValues);
        Assert.assertEquals(cells.size(), expectedValues.size());
        Assert.assertEquals(getAttributeClass(), "fa fa-check-square-o");
    }
}
