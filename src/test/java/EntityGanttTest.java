import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
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
    By draftButton = By.id("pa-entity-form-draft-btn");
    By listButton = By.xpath("//div[@class='content']//li[2]//a[1]");
    By checkSquare = By.xpath("//i[@class='fa fa-check-square-o']");
    By checkPencil = By.xpath("//i[@class='fa fa-pencil']");

    private void mainActions(){
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
    }

    private void listButtonClick(){
        getDriver().findElement(listButton).click();
    }

    @Ignore
    @Test
    public void testCreateNewRecordAndSave() {

        mainActions();
        getDriver().findElement(saveButton).click();
        listButtonClick();
        Assert.assertTrue(getDriver().findElement(checkSquare).isDisplayed());
    }

    @Ignore
    @Test
    public void testCreateNewRecordAndSaveAsDraft(){

        mainActions();
        getDriver().findElement(draftButton).click();
        listButtonClick();
        Assert.assertTrue(getDriver().findElement(checkPencil).isDisplayed());
    }
}
