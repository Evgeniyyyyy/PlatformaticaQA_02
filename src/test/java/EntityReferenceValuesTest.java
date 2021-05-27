import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityReferenceValuesTest extends BaseTest {
    private static final String LABEL_INPUT_VALUE = "Test1";
    private static final String FIELD1_INPUT_VALUE = "Test2";
    private static final String FIELD2_INPUT_VALUE = "Test3";

    By referenceValuesIcon = By.xpath("//p[contains(text(),'Reference values')]");
    By createNewFolderIcon = By.xpath("//i[contains(text(),'create_new_folder')]");
    By labelInput = By.xpath("//input[@id='label']");
    By filter1Input = By.xpath("//input[@id='filter_1']");
    By filter2Input = By.xpath("//input[@id='filter_2']");
    By saveButton = By.xpath("//button[@id='pa-entity-form-save-btn']");
    By checkSquare = By.xpath("//i[@class='fa fa-check-square-o']");

    @Test
    public void testCreateNewRecord(){
        start(getDriver());
        scrollClick(getDriver(), getDriver().findElement(referenceValuesIcon));

        getDriver().findElement(createNewFolderIcon).click();
        getDriver().findElement(labelInput).sendKeys(LABEL_INPUT_VALUE);
        getDriver().findElement(filter1Input).sendKeys(FIELD1_INPUT_VALUE);
        getDriver().findElement(filter2Input).sendKeys(FIELD2_INPUT_VALUE);
        getDriver().findElement(saveButton).click();

        Assert.assertNotNull(getDriver().findElement(By.xpath("//table[@id='pa-all-entities-table']")));
        Assert.assertTrue(getDriver().findElement(checkSquare).isDisplayed());
    }
}
