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

public class EntityParentTest1 extends BaseTest {

    private static final Random random = new Random();
    private static final String TEXT = UUID.randomUUID().toString();
    private static final Integer INT = random.nextInt();
    private static final Double DECIMAL = random.nextDouble();

    private static final By PARENT_BUTTON = By.xpath("//p[text()=' Parent ']");

    private static final By NEW_FOLDER = By.xpath("//i[text()='create_new_folder']");
    private static final By STRING_INPUT = By.id("string");
    private static final By TEXT_INPUT = By.id("text");
    private static final By INT_INPUT = By.id("int");
    private static final By DECIMAL_INPUT = By.id("decimal");
    private static final By DATE_INPUT = By.id("date");
    private static final By DATETIME_INPUT = By.id("datetime");

    private static final By LIST_BUTTON = By.xpath(
            "//a[@href='index.php?action=action_list&list_type=table&entity_id=57']");
    private static final By MENU_LIST_BUTTON = By.xpath("//i[text()='menu']");
    private static final By CHECK_ROW = By.xpath(
            "//table[@id='pa-all-entities-table']/tbody/tr[@data-index='0']");

    private static final By SAVE_BUTTON = By.id("pa-entity-form-save-btn");

    private void createRecord() {

        ProjectUtils.start(getDriver());

        TestUtils.scrollClick(getDriver(), getDriver().findElement(PARENT_BUTTON));

        getDriver().findElement(NEW_FOLDER).click();
        getDriver().findElement(STRING_INPUT).sendKeys(TEXT);
        getDriver().findElement(TEXT_INPUT).sendKeys(TEXT);
        getDriver().findElement(INT_INPUT).sendKeys(INT.toString());
        getDriver().findElement(DECIMAL_INPUT).sendKeys(DECIMAL.toString());
        TestUtils.scrollClick(getDriver(),DATE_INPUT);
        TestUtils.scrollClick(getDriver(),DATETIME_INPUT);

        getDriver().findElement(By.xpath("//button[@data-id='user']")).click();

        TestUtils.jsClick(getDriver(), getDriver().findElement(
                By.xpath("//span[text()='tester26@tester.test']")));
    }

    @Test
    public void testCreateRecord() {

        createRecord();

        TestUtils.scrollClick(getDriver(), getDriver().findElement(SAVE_BUTTON));

        WebElement row = findElement(By.tagName("tbody"));
        WebElement icon = findElement(By.xpath("//tbody/tr/td/i"));

        List<WebElement> records = getDriver().findElements(CHECK_ROW);

        Assert.assertEquals(records.size(), 1);
        Assert.assertEquals(row.getTagName(), "tbody");
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-check-square-o");
    }

    @Test
    public void testViewRecord() {

        createRecord();

        TestUtils.scrollClick(getDriver(), getDriver().findElement(SAVE_BUTTON));

        findElement(LIST_BUTTON).click();

        TestUtils.jsClick(getDriver(), getDriver().findElement(MENU_LIST_BUTTON));

        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath("//a[text()='view']")));

        List<WebElement> row = findElements(By.xpath("//span[@class='pa-view-field']"));

        Assert.assertEquals(row.size(), 6);
    }
}
