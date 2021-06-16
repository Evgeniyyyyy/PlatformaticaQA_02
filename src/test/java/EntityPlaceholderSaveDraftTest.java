import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import static utils.TestUtils.*;

public class EntityPlaceholderSaveDraftTest extends BaseTest {

    private static final String STRING_INPUT_VALUE = UUID.randomUUID().toString();
    private static final String TEXT_INPUT_VALUE = UUID.randomUUID().toString();
    private static final String INT_INPUT_VALUE = "777";
    private static final String DECIMAL_INPUT_VALUE = "20.20";
    private static final String DATE_VALUE = "30/05/2021";
    private static final String DATETIME_VALUE = "01/06/2021 10:10:10";
    private static final String PENCIL_ICON_CLASS = "fa fa-pencil";
    private static final String EMPTY_RECYCLE_BIN_MESSAGE = "Good job with housekeeping! Recycle bin is currently empty!";

    private static final By PLACEHOLDER_MENU = By.xpath("//p[contains(text(),'Placeholder')]");
    private static final By ADD_NEW_RECORD = By.xpath("//i[contains(text(),'create_new_folder')]");
    private static final By STRING_FIELD = By.id("string");
    private static final By TEXT_FIELD = By.id("text");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By DATE_FIELD = By.id("date");
    private static final By DATETIME_FIELD = By.id("datetime");
    private static final By USER_VALUE_LIST = By.xpath("//div[contains(text(),'apptester1@tester.test')]");
    private static final By SAVE_DRAFT_BUTTON = By.id("pa-entity-form-draft-btn");
    private static final By ICON_PENCIL = By.xpath("//tbody/tr/td[1]/i");
    private static final By RESULT_COLUMNS = By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']");
    private static final By ACTIONS_MENU = By.xpath("//i[contains(text(),'menu')]");
    private static final By LIST_BUTTON = By.xpath("//div[1]/div[1]/ul[1]/li[1]/a[1]");
    private static final By DELETE_BUTTON = By.xpath("//a[contains (text(), 'delete')]");
    private static final By RECYCLE_BIN_ICON = By.xpath("//i[contains(text(),'delete_outline')]");
    private static final By RECYCLE_NOTIFICATION = By.xpath("//span[@class='notification']/b[1]");
    private static final By RECYCLE_NOTIFICATION_EMPTY = By.xpath("//i[contains(text(),'delete_outline')]");
    private static final By CLEAN_RECYCLE_BIN_BUTTON = By.xpath("//a[contains(text(),'delete permanently')]");
    private static final By CART_BODY_RECYCLE_BIN = By.xpath("//div[@class='card-body']");
    private static final By CONFIRMATION_DELETED_RECORD = By.xpath("//span[@class='pagination-info']");
    private static final By TABLE_RECYCLE_RECORD = By.xpath("//tbody/tr");

    @Test
    public void testCreateNewPlaceholderDraftRecord() {
        clickPlaceholderMenu();
        clickAddNewPlaceholder();
        fillNewPlaceholderDraftRecord();

        WebElement icon = getIcon();
        Assert.assertEquals(icon.getAttribute("class"), PENCIL_ICON_CLASS);

        List<WebElement> columnList = getPlaceholderTableColumns();
        List<String> expectedResultLine = createExpectedResults();
        Assert.assertEquals(columnList.size(), expectedResultLine.size());

        Assert.assertEquals(columnList.stream().map(WebElement::getText).collect(Collectors.toList()), expectedResultLine);
    }

    @Test(dependsOnMethods = "testCreateNewPlaceholderDraftRecord")
    public void testDeletePlaceholderDraftRecord() {
        clickPlaceholderMenu();
        deletePlaceholderDraftRecord();

        Assert.assertEquals(findElement(RECYCLE_NOTIFICATION).getText(), "1");

        findElement(RECYCLE_BIN_ICON).click();
        Assert.assertEquals(findElement(CONFIRMATION_DELETED_RECORD)
                .getText(), "Showing 1 to 1 of 1 rows");

        List<WebElement> rowRecycleBin = findElements(TABLE_RECYCLE_RECORD);
        Assert.assertEquals(rowRecycleBin.size(), 1);
    }

    @Test(dependsOnMethods = "testDeletePlaceholderDraftRecord")
    public void testDeleteRecycleBinDraftRecord() {
        clickPlaceholderMenu();
        cleanRecycleBin();

        Assert.assertEquals(findElement(CART_BODY_RECYCLE_BIN).getText(), EMPTY_RECYCLE_BIN_MESSAGE);

        clickPlaceholderMenu();
        Assert.assertEquals(findElement(RECYCLE_NOTIFICATION_EMPTY).getText(), "delete_outline");
    }

    private void cleanRecycleBin() {
        getDriver().findElement(RECYCLE_BIN_ICON).click();
        jsClick(getDriver(), getDriver().findElement(CLEAN_RECYCLE_BIN_BUTTON));
    }

    private List<String> createExpectedResults() {
        final List<String> expectedResultLine = List
                .of(STRING_INPUT_VALUE, TEXT_INPUT_VALUE, INT_INPUT_VALUE, DECIMAL_INPUT_VALUE, DATE_VALUE, DATETIME_VALUE, "", "", "apptester1@tester.test");
        return expectedResultLine;
    }

    private List<WebElement> getPlaceholderTableColumns() {
        List<WebElement> columnList = getDriver().findElements(RESULT_COLUMNS);
        return columnList;
    }

    private WebElement getIcon() {
        WebElement icon = getDriver().findElement(ICON_PENCIL);
        return icon;
    }

    private void fillNewPlaceholderDraftRecord() {
        getDriver().findElement(STRING_FIELD).sendKeys(STRING_INPUT_VALUE);
        getDriver().findElement(TEXT_FIELD).sendKeys(TEXT_INPUT_VALUE);
        getDriver().findElement(INT_FIELD).sendKeys(INT_INPUT_VALUE);
        getDriver().findElement(DECIMAL_FIELD).sendKeys(DECIMAL_INPUT_VALUE);
        getDriver().findElement(DATE_FIELD).click();
        getDriver().findElement(DATE_FIELD).clear();
        getDriver().findElement(DATE_FIELD).sendKeys(DATE_VALUE);
        getDriver().findElement(DATETIME_FIELD).click();
        getDriver().findElement(DATETIME_FIELD).clear();
        getDriver().findElement(DATETIME_FIELD).sendKeys(DATETIME_VALUE);
        getDriver().findElement(USER_VALUE_LIST).isDisplayed();
        scrollClick(getDriver(), getDriver().findElement(SAVE_DRAFT_BUTTON));
    }

    private void clickPlaceholderMenu() {
        scrollClick(getDriver(), getDriver().findElement(PLACEHOLDER_MENU));
    }

    private void clickAddNewPlaceholder() {
        getDriver().findElement(ADD_NEW_RECORD).click();
    }

    private void deletePlaceholderDraftRecord() {
        getWait().until(ExpectedConditions.visibilityOfElementLocated(LIST_BUTTON));
        findElement(LIST_BUTTON).click();
        jsClick(getDriver(), getDriver().findElement(ACTIONS_MENU));
        getWait().until(ExpectedConditions.visibilityOfElementLocated(DELETE_BUTTON));
        jsClick(getDriver(), getDriver().findElement(DELETE_BUTTON));
    }
}
