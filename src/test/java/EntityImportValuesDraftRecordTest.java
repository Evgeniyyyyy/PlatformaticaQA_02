import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.ProjectUtils.*;

public class EntityImportValuesDraftRecordTest extends BaseTest {

    private static final By STRING_FIELD = By.id("string");
    private static final By TEXT_FIELD = By.id("text");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By DATE_FIELD = By.id("date");
    private static final By DATETIME_FIELD = By.id("datetime");

    private static final String STRING_INPUT = "Some string";
    private static final String TEXT_INPUT = "Import values text.";
    private static final String INT_INPUT = "457";
    private static final String DECIMAL_INPUT = "27.35";
    private static final String DATE_INPUT = "01/06/2021";
    private static final String DATETIME_INPUT = "01/06/2021 13:07:06";
    private static final String USERNAME_INPUT = "apptester1@tester.test";
    private static final String FILE_INPUT = "";
    private static final String IMPORT_VALUES = "Import values";
    private static final String STRING_INPUT2 = "New string";
    private static final String TEXT_INPUT2 = "New text.";
    private static final String INT_INPUT2 = "12";
    private static final String DECIMAL_INPUT2 = "0.20";
    private static final String DATE_INPUT2 = "18/07/2021";
    private static final String DATETIME_INPUT2 = "18/07/2021 17:07:07";

    private final static List<String> EXPECTED_VALUES = Arrays.asList(STRING_INPUT, TEXT_INPUT,
            INT_INPUT, DECIMAL_INPUT, DATE_INPUT, DATETIME_INPUT, FILE_INPUT, USERNAME_INPUT);

    private final static List<String> EXPECTED_RECORDS = Arrays.asList(STRING_INPUT, TEXT_INPUT,
            INT_INPUT, DECIMAL_INPUT, DATE_INPUT, DATETIME_INPUT);

    private final static List<String> EXPECTED_VALUES2 = Arrays.asList(STRING_INPUT2, TEXT_INPUT2,
            INT_INPUT2, DECIMAL_INPUT2, DATE_INPUT2, DATETIME_INPUT2, FILE_INPUT, USERNAME_INPUT);

    private void fillForm() {
        findElement(STRING_FIELD).sendKeys(STRING_INPUT);
        findElement(TEXT_FIELD).sendKeys(TEXT_INPUT);
        findElement(INT_FIELD).sendKeys(INT_INPUT);
        findElement(DECIMAL_FIELD).sendKeys(DECIMAL_INPUT);

        WebElement date = findElement(DATE_FIELD);
        date.click();
        date.clear();
        date.sendKeys(DATE_INPUT);

        WebElement dateTime = findElement(DATETIME_FIELD);
        dateTime.click();
        dateTime.clear();
        dateTime.sendKeys(DATETIME_INPUT);
    }

    private void editForm() {
        WebElement string = findElement(STRING_FIELD);
        string.clear();

        WebElement text = findElement(TEXT_FIELD);
        text.clear();

        WebElement intField = findElement(INT_FIELD);
        intField.clear();

        WebElement decimal = findElement(DECIMAL_FIELD);
        decimal.clear();

        WebElement date = findElement(DATE_FIELD);
        date.click();
        date.clear();
        date.sendKeys(DATE_INPUT2);

        WebElement dateTime = findElement(DATETIME_FIELD);
        dateTime.click();
        dateTime.clear();
        dateTime.sendKeys(DATETIME_INPUT2);

        string.sendKeys(STRING_INPUT2);
        text.sendKeys(TEXT_INPUT2);
        intField.sendKeys(INT_INPUT2);
        decimal.sendKeys(DECIMAL_INPUT2);
    }

    @Test
    public void testCreateDraftRecord() {

        getEntity(getDriver(), IMPORT_VALUES);
        clickCreateRecord(getDriver());
        fillForm();
        clickSaveDraft(getDriver());

        List<WebElement> tds = findElements(By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']"));

        WebElement pencilIcon = getDriver().findElement(By.xpath("//tbody/tr/td[1]/i"));

        List<String> actualValues = new ArrayList<>();
        for (WebElement td : tds) {
            actualValues.add(td.getText());
        }

        Assert.assertEquals(pencilIcon.getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(actualValues, EXPECTED_VALUES);
    }

    @Test
    public void testCancelRecord() {

        getEntity(getDriver(), IMPORT_VALUES);
        clickCreateRecord(getDriver());
        fillForm();
        clickCancel(getDriver());

        Assert.assertNull(findElement(By.className("card-body")).getAttribute("value"));
    }

    @Test(dependsOnMethods = "testCreateDraftRecord")
    public void testViewDraftRecord() {

        getEntity(getDriver(), IMPORT_VALUES);

        WebElement iconCheckBox = findElement(By.xpath("//tbody/tr/td/i"));

        Assert.assertEquals(iconCheckBox.getAttribute("class"), "fa fa-pencil");

        clickActionsView(getWait(), getDriver());

        List<WebElement> viewField = findElements(By.xpath("//span [@class = 'pa-view-field']"));

        List<String> actualValues = new ArrayList<>();
        for (WebElement td : viewField) {
            actualValues.add(td.getText());
        }

        Assert.assertEquals(actualValues, EXPECTED_RECORDS);
    }

    @Test(dependsOnMethods = "testViewDraftRecord")
    public void testEditDraftRecord() {
        getEntity(getDriver(), IMPORT_VALUES);
        clickActionsEdit(getWait(), getDriver());
        editForm();
        clickSaveDraft(getDriver());

        List<WebElement> tds = findElements(By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']"));

        WebElement pencilIcon = getDriver().findElement(By.xpath("//tbody/tr/td[1]/i"));

        List<String> actualValues = new ArrayList<>();
        for (WebElement td : tds) {
            actualValues.add(td.getText());
        }

        Assert.assertEquals(pencilIcon.getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(actualValues, EXPECTED_VALUES2);
    }
}
