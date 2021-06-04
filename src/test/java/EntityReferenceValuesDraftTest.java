import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityReferenceValuesDraftTest extends BaseTest {
    private static final String Label_Input_Value = "label value";
    private static final String Filter1_Input_Value = "Filter1 value";
    private static final String Filter2_Input_Value = "Filter2 value";

    private static final By Reference_Values_Menu = By.xpath("//p[contains(text(),'Reference values')]");
    private static final By Create_New_Folder_Button = By.xpath("//i[contains(text(),'create_new_folder')]");
    private static final By Label_Input = By.xpath("//input[@id='label']");
    private static final By Filter1_Input = By.xpath("//input[@id='filter_1']");
    private static final By Filter2_Input = By.xpath("//input[@id='filter_2']");
    private static final By Safe_Draft_Button = By.xpath("//*[@id='pa-entity-form-draft-btn']");
    private static final By Pencil_Icon = By.xpath("//i[@class='fa fa-pencil']");

    @Test
    public void testCreateNewDraftRecord() {
        start(getDriver());
        scrollClick(getDriver(), getDriver().findElement(Reference_Values_Menu));

        getDriver().findElement(Create_New_Folder_Button).click();
        getDriver().findElement(Label_Input).sendKeys(Label_Input_Value);
        getDriver().findElement(Filter1_Input).sendKeys(Filter1_Input_Value);
        getDriver().findElement(Filter2_Input).sendKeys(Filter2_Input_Value);
        getDriver().findElement(Safe_Draft_Button).click();
        getDriver().findElement(Pencil_Icon).isDisplayed();

        WebElement Pencil_Icon = findElement(By.xpath("//i[@class='fa fa-pencil']"));
        Assert.assertEquals(Pencil_Icon.getAttribute("class"), "fa fa-pencil");
        Assert.assertNotNull(getDriver().findElement(By.xpath("//table[@id='pa-all-entities-table']")));
    }
}
