import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;
import java.util.List;

public class EntityPlaceholderTest extends BaseTest {

    private static final By PLACEHOLDER_MENU = By.xpath("//p[contains (text(), 'Placeholder')]");

    @Ignore
    @Test
    public void testCreateNewRecord() {

        By createNewFolder = By.xpath("//div/i[text()='create_new_folder']");
        By userField = By.xpath("//div[@class = \"filter-option-inner-inner\"]");
        By findMail = By.xpath("//span[contains (text(),  \"tester99@tester.test\")]");
        By saveButton = By.id("pa-entity-form-save-btn");
        By checkBox = By.xpath("//tbody/tr/td[1]/i");
        By checkMail = By.xpath("//tbody/tr/td[contains(text(),'tester99@tester.test')]");
        By checkRow = By.xpath("//table[@id = 'pa-all-entities-table']/tbody/tr");
        String stringValue = "Test 01";
        String textValue = "first test";
        String intValue = "1";
        String decimalValue = "5.5";
        String mail = "tester99@tester.test";

        ProjectUtils.start(getDriver());

        TestUtils.scrollClick(getDriver(), findElement(PLACEHOLDER_MENU));

        getDriver().findElement(createNewFolder).click();
        getDriver().findElement(By.id("string")).sendKeys(stringValue);
        getDriver().findElement(By.id("text")).sendKeys(textValue);
        getDriver().findElement(By.id("int")).sendKeys(intValue);
        getDriver().findElement(By.id("decimal")).sendKeys(decimalValue);
        getDriver().findElement(By.id("date")).click();
        getDriver().findElement(By.id("datetime")).click();
        getDriver().findElement(userField).click();
        TestUtils.scrollClick(getDriver(), getDriver().findElement(findMail));
        TestUtils.jsClick(getDriver(), getDriver().findElement(saveButton));

        WebElement icon = findElement(checkBox);
        WebElement result = getDriver().findElement(checkMail);
        List<WebElement> record = getDriver().findElements(checkRow);

        Assert.assertEquals(record.size(), 1);
        Assert.assertEquals(icon.getAttribute("class"), "fa fa-check-square-o");
        Assert.assertEquals(result.getText(), mail);
    }
}
