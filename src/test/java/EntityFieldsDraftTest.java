import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static utils.ProjectUtils.*;
import static utils.TestUtils.jsClick;

public class EntityFieldsDraftTest extends BaseTest {

    private static final By TITLE_FIELD = By.id("title");
    private static final By COMMENTS_FIELD = By.id("comments");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By DATE_FIELD = By.id("date");
    private static final By DATETIME_FIELD = By.id("datetime");
    private static final By USER_FIELD = By.xpath("//div[@class='filter-option-inner']");
    private static final By TYPE_ICON = By.xpath("//tbody/tr[1]/td[1]/i[1]");
    private static final By TABLE_ROWS_ELEMENT = By.xpath("//div[@class='fixed-table-body']//table[@id='pa-all-entities-table']/tbody/tr");
    private static final By ACTUAL_TABLE_RESULT = By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']");

    private static final String TITLE_INPUT_VALUE = "Title 1";
    private static final String COMMENTS_INPUT_VALUE = "Title 1 comment 1.";
    private static final String INT_INPUT_VALUE = "100";
    private static final String DECIMAL_INPUT_VALUE = "10.11";
    private static final String EMPTY_FIELD = "";
    private static final String USER_NAME = "apptester1@tester.test";

    private static final Date date = new Date();
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    private static final String TITLE_INPUT_VALUE2 = "Edited Title Test";
    private static final String COMMENTS_INPUT_VALUE2 = "Edited Comments Text";
    private static final String INT_INPUT_VALUE2 = "22";
    private static final String DECIMAL_INPUT_VALUE2 = "2.22";
    private static final List<Object> EXPECTED_RESULT = Arrays
            .asList(TITLE_INPUT_VALUE, COMMENTS_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE,
                    formatter.format(date), EMPTY_FIELD, EMPTY_FIELD, USER_NAME, EMPTY_FIELD);

    private static final List<Object> EXPECTED_RESULT2 = Arrays
            .asList(TITLE_INPUT_VALUE2, COMMENTS_INPUT_VALUE2, INT_INPUT_VALUE2, DECIMAL_INPUT_VALUE2,
                    formatter.format(date), EMPTY_FIELD, EMPTY_FIELD, USER_NAME, EMPTY_FIELD);

    private void clearFields() {
        findElement(TITLE_FIELD).clear();
        findElement(COMMENTS_FIELD).clear();
        findElement(INT_FIELD).clear();
        findElement(DECIMAL_FIELD).clear();
        findElement(DATE_FIELD).clear();
        findElement(DATETIME_FIELD).clear();
    }

    @Test
    public void testCreateDraftRecord() {

        WebElement fieldsTab = getDriver().findElement(By.xpath("//p[contains(text(),' Fields ')]"));
        TestUtils.scrollClick(getDriver(), fieldsTab);
        getDriver().findElement(By.className("card-icon")).click();
        getDriver().findElement(TITLE_FIELD).sendKeys(TITLE_INPUT_VALUE);
        getDriver().findElement(COMMENTS_FIELD).sendKeys(COMMENTS_INPUT_VALUE);
        getDriver().findElement(INT_FIELD).sendKeys(INT_INPUT_VALUE);
        getDriver().findElement(DECIMAL_FIELD).sendKeys(DECIMAL_INPUT_VALUE);
        findElement(DATE_FIELD).click();
        jsClick(getDriver(), findElement(USER_FIELD));
        clickSaveDraft(getDriver());

        WebElement icon = findElement(TYPE_ICON);
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_TABLE_RESULT)), EXPECTED_RESULT);
    }

    @Test(dependsOnMethods= "testCreateDraftRecord")
    public void testViewDraftRecord(){

        getEntity(getDriver(), "Fields");

        List<WebElement> rows = getDriver().findElements(TABLE_ROWS_ELEMENT);
        Assert.assertEquals(rows.size(), 1);

        clickActionsView(getWait(), getDriver());

        List<Object> expectedRecord = Arrays.asList(COMMENTS_INPUT_VALUE, INT_INPUT_VALUE,
                DECIMAL_INPUT_VALUE, formatter.format(date), EMPTY_FIELD);

        Assert.assertEquals(getActualValues(findElements(By.xpath("//span [@class = 'pa-view-field']"))), expectedRecord);
        Assert.assertEquals(findElement(By.xpath("//div [@class = 'form-group']/p")).getText(), USER_NAME);
    }

    @Test(dependsOnMethods= "testViewDraftRecord")
    public void testEditDraftRecord(){

        getEntity(getDriver(), "Fields");

        clickActionsEdit(getWait(), getDriver());
        clearFields();

        findElement(TITLE_FIELD).sendKeys(TITLE_INPUT_VALUE2);
        findElement(COMMENTS_FIELD).sendKeys(COMMENTS_INPUT_VALUE2);
        findElement(INT_FIELD).sendKeys(INT_INPUT_VALUE2);
        findElement(DECIMAL_FIELD).sendKeys(DECIMAL_INPUT_VALUE2);

        clickSaveDraft(getDriver());

        WebElement icon1 = findElement(TYPE_ICON);
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(getActualValues(findElements(ACTUAL_TABLE_RESULT)), EXPECTED_RESULT2);
    }

    @Test(dependsOnMethods= "testEditDraftRecord")
    public void testDeleteDraftRecord(){

        getEntity(getDriver(), "Fields");

        clickActionsDelete(getWait(), getDriver());
        clickRecycleBin(getDriver());
        findElement(By.xpath("//tbody/tr/td/a")).click();

        List<Object> expectedRecord2 = Arrays.asList(COMMENTS_INPUT_VALUE2, INT_INPUT_VALUE2,
                DECIMAL_INPUT_VALUE2, formatter.format(date), EMPTY_FIELD);

        Assert.assertEquals(getActualValues(findElements(By.cssSelector("span.pa-view-field"))), expectedRecord2);
    }

    @Test(dependsOnMethods= "testDeleteDraftRecord")
    public void testPermanentlyDeleteDraftRecord(){

        getDriver().findElement(By.partialLinkText("delete_outline")).click();
        getDriver().findElement(By.partialLinkText("delete permanently")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class='card-body']")).getText(),
                "Good job with housekeeping! Recycle bin is currently empty!");
    }

}

