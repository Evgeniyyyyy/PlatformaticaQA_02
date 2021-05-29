import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static utils.ProjectUtils.start;
import static utils.TestUtils.jsClick;
import static utils.TestUtils.scrollClick;

public class EntityGanttTest extends BaseTest {

    private static final By GANTT_TAB = By.xpath("//p[contains (text(), 'Gantt')]");
    private static final By CREATE_NEW_RECORD = By.xpath("//div/i[.='create_new_folder']");
    private static final By STRING_FIELD = By.id("string");
    private static final By TEXT_FIELD = By.id("text");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By DATE_FIELD = By.id("date");
    private static final By TESTER_NAME_FIELD = By.xpath("//div[contains(text(),'apptester1@tester.test')]");
    private static final By TESTER_NAME = By.xpath("//span[text()='tester100@tester.test']");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");
    private static final By DRAFT_BUTTON = By.id("pa-entity-form-draft-btn");
    private static final By LIST_BUTTON = By.xpath("//div[@class='content']//ul//li[2]/a[1]");
    private static final By CHECK_ICON = By.xpath("//tbody/tr[1]/td[1]/i[1]");
    private static final By COLUMN_FIELD = By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']");

    final String stringInputValue = "Test";
    final String textInputValue = "Text";
    final String intInputValue = "100";
    final String decimalInputValue = "0.10";
    final String emptyField = "";
    final String userName = "tester100@tester.test";

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    final List<Object> expectedValues = Arrays
            .asList(stringInputValue, textInputValue, intInputValue, decimalInputValue,
                    formatter.format(date), emptyField, emptyField, userName);

    private void createRecord(){
        start(getDriver());

        scrollClick(getDriver(), getDriver().findElement(GANTT_TAB));

        getDriver().findElement(CREATE_NEW_RECORD).click();
        getDriver().findElement(STRING_FIELD).sendKeys(stringInputValue);
        getDriver().findElement(TEXT_FIELD).sendKeys(textInputValue);
        getDriver().findElement(INT_FIELD).sendKeys(intInputValue);
        getDriver().findElement(DECIMAL_FIELD).sendKeys(decimalInputValue);
        getDriver().findElement(DATE_FIELD).click();
        getDriver().findElement(TESTER_NAME_FIELD).click();

        jsClick(getDriver(), getDriver().findElement(TESTER_NAME));
    }

    private void clickListButton(){
        getWait().until(ExpectedConditions
                .attributeContains(getDriver().findElement(LIST_BUTTON), "class", "nav-link "));
        getDriver().findElement(LIST_BUTTON).click();
    }

    private void getAssertion(){
        List<WebElement> columnList = findElements(COLUMN_FIELD);

        Assert.assertEquals(columnList.size(), expectedValues.size());
        for (int i = 0; i < expectedValues.size(); i++) {
            Assert.assertEquals(columnList.get(i).getText(), expectedValues.get(i).toString());
        }
    }

    @Test
    public void testCreateRecord() {

        createRecord();
        getDriver().findElement(SAVE_BUTTON).click();
        clickListButton();

        WebElement icon1 = findElement(CHECK_ICON);
        Assert.assertEquals(icon1.getAttribute("class"), "fa fa-check-square-o");
        getAssertion();
    }

    @Test
    public void testCreateDraftRecord(){

        createRecord();
        getDriver().findElement(DRAFT_BUTTON).click();
        clickListButton();

        WebElement icon2 = findElement(CHECK_ICON);
        Assert.assertEquals(icon2.getAttribute("class"), "fa fa-pencil");
        getAssertion();
    }
}
