import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityReferenceValuesDraftTest extends BaseTest {

    private static final String LABEL_INPUT_VALUE = "label value";
    private static final String FILTER_1_INPUT_VALUE = "Filter1 value";
    private static final String FILTER_2_INPUT_VALUE = "Filter2 value";

    private static final By REFERENCE_VALUES_MENU = By.xpath("//p[contains(text(),'Reference values')]");
    private static final By CREATE_NEW_FOLDER_BUTTON = By.xpath("//i[contains(text(),'create_new_folder')]");
    private static final By LABEL_INPUT = By.xpath("//input[@id='label']");
    private static final By FILTER_1_INPUT = By.xpath("//input[@id='filter_1']");
    private static final By FILTER_2_INPUT = By.xpath("//input[@id='filter_2']");
    private static final By SAFE_DRAFT_BUTTON = By.xpath("//*[@id='pa-entity-form-draft-btn']");

    @Test
    public void testCreateNewDraftRecord() {

        scrollClick(getDriver(), getDriver().findElement(REFERENCE_VALUES_MENU));

        getDriver().findElement(CREATE_NEW_FOLDER_BUTTON).click();
        getDriver().findElement(LABEL_INPUT).sendKeys(LABEL_INPUT_VALUE);
        getDriver().findElement(FILTER_1_INPUT).sendKeys(FILTER_1_INPUT_VALUE);
        getDriver().findElement(FILTER_2_INPUT).sendKeys(FILTER_2_INPUT_VALUE);
        getDriver().findElement(SAFE_DRAFT_BUTTON).click();


        WebElement Pencil_Icon = findElement(By.xpath("//i[@class='fa fa-pencil']"));
        Assert.assertEquals(Pencil_Icon.getAttribute("class"), "fa fa-pencil");
        Assert.assertNull(findElement(By.className("card-body")).getAttribute("value"));
    }
}
