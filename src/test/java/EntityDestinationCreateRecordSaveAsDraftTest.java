import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestUtils;

public class EntityDestinationCreateRecordSaveAsDraftTest extends BaseTest {

    private static final By STRING_FIELD = By.id("string");
    private static final By TEXT_FIELD = By.id("text");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");
    private static final By DATE_FIELD = By.id("date");
    private static final By DATETIME_FIELD = By.id("datetime");
    private static final By USER_FIELD = By.className("filter-option-inner-inner");

    private static final String STRING_INPUT_VALUE = "Test";
    private static final String TEXT_INPUT_VALUE = "Text";
    private static final String INT_INPUT_VALUE = "100";
    private static final String DECIMAL_INPUT_VALUE = "0.10";
    private static final String CURRENT_USER = "tester33@tester.test";

    public static final By BUTTON_SAVE_DRAFT = By.id("pa-entity-form-draft-btn");
    public static final By ACTION_BUTTON = By.className("btn-primary");
    public static final By ACTION_VIEW = By.xpath("//div[@class=\"dropdown pull-left show\"]/ul/li[1]/a");

    private void fillFieldAndWait(By by, String value) {
        findElement(by).sendKeys(value);
        getWait().until(ExpectedConditions.textToBePresentInElementValue(by, value));

    }

    @Test //test by PRoman-86
    public void testCreateDraftRecord() {
        TestUtils.jsClick(getDriver(), findElement(By.xpath("//p[contains(text(),'Export destination')]")));
        TestUtils.jsClick(getDriver(), findElement(By.cssSelector(".card-icon")));

        fillFieldAndWait(STRING_FIELD, STRING_INPUT_VALUE);
        fillFieldAndWait(TEXT_FIELD, TEXT_INPUT_VALUE);
        fillFieldAndWait(INT_FIELD, INT_INPUT_VALUE);
        fillFieldAndWait(DECIMAL_FIELD, DECIMAL_INPUT_VALUE);
        findElement(DATE_FIELD).click();
        findElement(DATETIME_FIELD).click();
        TestUtils.jsClick(getDriver(), findElement(USER_FIELD));
        TestUtils.jsClick(getDriver(), getWait().until
                (ExpectedConditions.elementToBeClickable(By.cssSelector("li:nth-child(237) a:nth-child(1) span:nth-child(2)"))));
        TestUtils.jsClick(getDriver(), findElement(BUTTON_SAVE_DRAFT));
        TestUtils.jsClick(getDriver(), findElement(ACTION_BUTTON));
        getWait().until(TestUtils.movingIsFinished(findElement(ACTION_VIEW)));
        TestUtils.jsClick(getDriver(), findElement(ACTION_VIEW));

        Assert.assertEquals(findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]")).getText(), STRING_INPUT_VALUE);
        Assert.assertEquals(findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[4]/div[1]")).getText(), TEXT_INPUT_VALUE);
        Assert.assertEquals(findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[6]/div[1]")).getText(), INT_INPUT_VALUE);
        Assert.assertEquals(findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[8]/div[1]")).getText(), DECIMAL_INPUT_VALUE);
        Assert.assertNotEquals(findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[10]/div[1]")), "");
        Assert.assertNotEquals(findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[12]/div[1]")), "");
        Assert.assertEquals(findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[13]/p[1]")).getText(), CURRENT_USER);

    }

}
