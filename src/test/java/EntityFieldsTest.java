import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;
import java.util.UUID;

public class EntityFieldsTest extends BaseTest {

    @Test
    public void testCreateNewRecord(){
        final String tile = UUID.randomUUID().toString();
        final String comments = UUID.randomUUID().toString();
        final int number = 10;
        final double decimal = 10.10;

        By menuFields = By.xpath("//p[contains(text(),'Fields')]");
        By title = By.xpath("//input[@id='title']");
        By coment = By.xpath("//textarea[@id='comments']");
        By num = By.xpath("//input[@id='int']");
        By dec = By.xpath("//input[@id='decimal']");
        By saveButton = By.xpath("//button[@id='pa-entity-form-save-btn']");

        ProjectUtils.start(getDriver());

        findElement(menuFields).click();
        WebElement newRecord = findElement(By.xpath("//i[text() = 'create_new_folder']"));
        newRecord.click();
        findElement(title).sendKeys(tile);
        findElement(coment).sendKeys(comments);
        findElement(num).sendKeys(String.valueOf(number));
        findElement(dec).sendKeys(String.valueOf(decimal));
        TestUtils.jsClick(getDriver(), findElement(saveButton));

        WebElement recordTitle = findElement(By.xpath("//tbody//tr//td[2]"));
        Assert.assertEquals(recordTitle.getText(), tile);
    }
}
