import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EntityTagTest extends BaseTest{

    private static final Random random = new Random();
    private static final String TEXT = UUID.randomUUID().toString();
    private static final Integer INT = random.nextInt();
    private static final Double DECIMAL = random.nextDouble();

    private static final By TAG_BUTTON = By.xpath("//p[text()=' Tag ']");

    private static final By NEW_FOLDER = By.xpath("//i[text()='create_new_folder']");
    private static final By STRING_INPUT = By.id("string");
    private static final By TEXT_INPUT = By.id("text");
    private static final By INT_INPUT = By.id("int");
    private static final By DECIMAL_INPUT = By.id("decimal");
    private static final By DATE_INPUT = By.id("date");
    private static final By DATETIME_INPUT = By.id("datetime");

    private static final By PENCIL_BOX = By.xpath("//i[@class='fa fa-pencil']");
    private static final By SQUARE_BOX = By.xpath("//i[@class='fa fa-check-square-o']");
    private static final By CHECK_ROW = By.xpath(
            "//table[@id='pa-all-entities-table']/tbody/tr[@data-index='0']");

    private static final By DRAFT_BUTTON = By.id("pa-entity-form-draft-btn");
    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");

    private void createRecord() {

        ProjectUtils.start(getDriver());

        TestUtils.scrollClick(getDriver(), getDriver().findElement(TAG_BUTTON));

        getDriver().findElement(NEW_FOLDER).click();
        getDriver().findElement(STRING_INPUT).sendKeys(TEXT);
        getDriver().findElement(TEXT_INPUT).sendKeys(TEXT);
        getDriver().findElement(INT_INPUT).sendKeys(INT.toString());
        getDriver().findElement(DECIMAL_INPUT).sendKeys(DECIMAL.toString());
        TestUtils.scrollClick(getDriver(),DATE_INPUT);
        TestUtils.scrollClick(getDriver(),DATETIME_INPUT);

        getDriver().findElement(By.xpath("//div[text()='apptester1@tester.test']")).click();
        WebElement apptester = getDriver().findElement(By.xpath("//span[text()='tester26@tester.test']"));
        TestUtils.jsClick(getDriver(), apptester);
    }

    @Test
    public void testCreateRecord() {

        createRecord();

        TestUtils.scrollClick(getDriver(), getDriver().findElement(SAVE_BUTTON));

        List<WebElement> records = getDriver().findElements(CHECK_ROW);

        Assert.assertEquals(records.size(), 1);
        Assert.assertTrue(getDriver().findElement(SQUARE_BOX).isDisplayed());
    }

    @Test
    public void testCreateDraftRecord() {

        createRecord();

        TestUtils.scrollClick(getDriver(), getDriver().findElement(DRAFT_BUTTON));

        List<WebElement> records = getDriver().findElements(CHECK_ROW);

        Assert.assertEquals(records.size(), 1);
        Assert.assertTrue(getDriver().findElement(PENCIL_BOX).isDisplayed());
    }
}
