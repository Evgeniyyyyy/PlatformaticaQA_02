import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;
import static utils.TestUtils.scrollClick;

public class EntityBoardEditRecordTest extends BaseTest {

    final String NEW_TEXT = "any text";
    final String NEW_INT = "2222";

    private static final By ENTITY_BOARD_MENU = By.xpath("//div[@id='menu-list-parent']//li[10]/a/i");
    private static final By CREATE_NEW_RECORD = By.xpath("//i[contains (text(), 'create_new_folder')]");
    private static final By TEXT_INPUT_AREA = By.xpath("//p//span/textarea");
    private static final By INT_INPUT_AREA = By.xpath("//span/input[@id='int']");
    private static final By SAVE_BUTTON = By.xpath("//button[@id='pa-entity-form-save-btn']");
    private static final By LIST_BUTTON = By.xpath("//li//a[@class='nav-link ']/i[@class='material-icons']");
    private static final By ACTIONS_BUTTON = By.xpath("//tbody/tr//td//button[@type='button']");
    private static final By EDIT_BUTTON = By.xpath("//tbody/tr//td//li[2]/a");

    private void createRecord() {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);

        ProjectUtils.start(getDriver());

        TestUtils.scrollClick(getDriver(), getDriver().findElement(ENTITY_BOARD_MENU));
        wait.until(ExpectedConditions.elementToBeClickable(getDriver()
                .findElement(CREATE_NEW_RECORD)))
                .click();
        getDriver().findElement(TEXT_INPUT_AREA).sendKeys("Some text here...");
        getDriver().findElement(INT_INPUT_AREA).sendKeys("1234");
        WebElement findSaveButton = getDriver().findElement(SAVE_BUTTON);
        TestUtils.scrollClick(getDriver(),findSaveButton);
    }

        @Test
        public void testEditCreatedRecord() {
            final String TEXT = "any text";

            createRecord();

            WebDriverWait wait = new WebDriverWait(getDriver(), 10);

            scrollClick(getDriver(), getDriver().findElement(ENTITY_BOARD_MENU));
            getDriver().findElement(LIST_BUTTON).click();
            getDriver().findElement(ACTIONS_BUTTON).click();

            wait.until(ExpectedConditions.elementToBeClickable(EDIT_BUTTON));
            getDriver().findElement(EDIT_BUTTON).click();
            WebElement textField = getDriver().findElement(TEXT_INPUT_AREA);
            textField.clear();
            textField.sendKeys(NEW_TEXT);

            WebElement intField = getDriver().findElement(INT_INPUT_AREA);
            intField.clear();
            intField.sendKeys(NEW_INT);
            WebElement SaveButton = getDriver().findElement(SAVE_BUTTON);
            TestUtils.scrollClick(getDriver(),SaveButton);

            String resultText = getDriver().findElement(By.xpath("//tbody//td[3]/a")).getText();
            Assert.assertEquals(resultText, NEW_TEXT);
            String resultInt = getDriver().findElement(By.xpath("//tbody//td[4]/a")).getText();
            Assert.assertEquals(resultInt, NEW_INT);
        }
}
