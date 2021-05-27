import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityGanttTest extends BaseTest {

    private static final String STRING_INPUT_VALUE = "Test";
    private static final String TEXT_INPUT_VALUE = "Text";
    private static final String INT_INPUT_VALUE = "100";
    private static final String DECIMAL_INPUT_VALUE = "0.1";

    By ganttTab = By.xpath("//p[contains (text(), 'Gantt')]");
    By createNewRecord = By.xpath("//div/i[.='create_new_folder']");
    By stringField = By.id("string");
    By textField = By.id("text");
    By intField = By.id("int");
    By decimalField = By.id("decimal");
    By dateField = By.id("date");
    By datetimeField = By.id("datetime");
    By testerNameField = By.xpath("//div[contains(text(),'apptester1@tester.test')]");
    By saveButton = By.id("pa-entity-form-save-btn");

    private int checkRecord(WebDriver driver){
        List<WebElement> rowsCount = driver.findElements(By.className("e-treecolumn-container"));
        return rowsCount.size();
    }

    @Test
    public void testCreateNewRecordAndSave() {
        start(getDriver());
        scrollClick(getDriver(), getDriver().findElement(ganttTab));

        getDriver().findElement(createNewRecord).click();
        getDriver().findElement(stringField).sendKeys(STRING_INPUT_VALUE);
        getDriver().findElement(textField).sendKeys(TEXT_INPUT_VALUE);
        getDriver().findElement(intField).sendKeys(INT_INPUT_VALUE);
        getDriver().findElement(decimalField).sendKeys(DECIMAL_INPUT_VALUE);
        getDriver().findElement(dateField).click();
        getDriver().findElement(datetimeField).click();
        getDriver().findElement(testerNameField).isDisplayed();
        getDriver().findElement(saveButton).click();

        Assert.assertEquals(checkRecord(getDriver()), 1);
    }
}
