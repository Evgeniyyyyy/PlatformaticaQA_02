import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;
import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityReferenceValuesTest extends BaseTest {
    private static final String LABEL_INPUT_VALUE = "Test1";
    private static final String FIELD1_INPUT_VALUE = "Test2";
    private static final String FIELD2_INPUT_VALUE = "Test3";

    private static final By REFERENCE_VALUES_ICON = By.xpath("//p[contains(text(),'Reference values')]");
    private static final By CREATE_NEW_FOLDER_ICON = By.xpath("//i[contains(text(),'create_new_folder')]");
    private static final By LABEL_INPUT = By.xpath("//input[@id='label']");
    private static final By FILTER_1_INPUT = By.xpath("//input[@id='filter_1']");
    private static final By FILTER_2_INPUT = By.xpath("//input[@id='filter_2']");
    private static final By SAVE_BUTTON = By.xpath("//button[@id='pa-entity-form-save-btn']");
    private static final By CHECK_SQUARE = By.xpath("//i[@class='fa fa-check-square-o']");
    private static final By COLUMN_LIST = By.xpath("//tbody/tr/td[@class = 'pa-list-table-th']");
    private static final By CREATED_LINE = By.xpath("//table[@id='pa-all-entities-table']");

    @Test
    public void testCreateNewRecord(){
        start(getDriver());
        final List<String> expectedValues =Arrays.asList(LABEL_INPUT_VALUE, FIELD1_INPUT_VALUE, FIELD2_INPUT_VALUE);
        scrollClick(getDriver(), findElement(REFERENCE_VALUES_ICON));

        findElement(CREATE_NEW_FOLDER_ICON).click();
        findElement(LABEL_INPUT).sendKeys(LABEL_INPUT_VALUE);
        findElement(FILTER_1_INPUT).sendKeys(FIELD1_INPUT_VALUE);
        findElement(FILTER_2_INPUT).sendKeys(FIELD2_INPUT_VALUE);
        findElement(SAVE_BUTTON).click();

        final List<WebElement> actualValues = findElements(COLUMN_LIST);

        Assert.assertNotNull(getDriver().findElement(CREATED_LINE));
        Assert.assertTrue(getDriver().findElement(CHECK_SQUARE).isDisplayed());

        Assert.assertEquals(actualValues.size(), expectedValues.size());
        for (int i = 0; i < expectedValues.size() ; i++) {
            Assert.assertEquals(actualValues.get(i).getText(), expectedValues.get(i));
        }
    }
}