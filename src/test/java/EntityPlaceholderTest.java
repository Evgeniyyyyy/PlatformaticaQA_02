import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;
import java.util.List;

public class EntityPlaceholderTest extends BaseTest {
    By checkBox = By.xpath("//tbody/tr/td[1]/i");
    By checkMail = By.xpath("//tbody/tr/td[contains(text(),'tester99@tester.test')]");
    By checkRow = By.xpath("//table[@id = 'pa-all-entities-table']/tbody/tr");
    By createNewFolder = By.xpath("//div/i[text()='create_new_folder']");
    By userField = By.xpath("//div[@class = \"filter-option-inner-inner\"]");
    By findMail = By.xpath("//span[contains (text(),  \"tester99@tester.test\")]");
    By saveButton = By.id("pa-entity-form-save-btn");
    By actionsButton = By.xpath("//button[@class = \"btn btn-round btn-sm btn-primary dropdown-toggle\"]");
    By deleteButton = By.xpath("//li/a[contains (text(), \"delete\")]");
    By binTableRows = By.xpath("//tbody/tr");

    final String stringValue = "Test 01";
    final String textValue = "first test";
    final String intValue = "1";
    final String decimalValue = "5.5";
    final String mail = "tester99@tester.test";
    final String emptyBinPlaceholder = "Good job with housekeeping! Recycle bin is currently empty!";

    private void clickPlaceholderMenu() {
        TestUtils.scrollClick(getDriver(), findElement(By.xpath("//p[contains (text(), 'Placeholder')]")));
    }

    private void createNewRecord(){
        ProjectUtils.start(getDriver());

        clickPlaceholderMenu();

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
    }

    private void deleteRecord(){
        getDriver().findElement(actionsButton).click();
        TestUtils.jsClick(getDriver(), getDriver().findElement(deleteButton));
    }

    private void clickNotification() {
        getDriver().findElement(By.xpath("//span[@class = \"notification\"]")).click();
    }

    private WebElement icon() {
        return findElement(checkBox);
    }

    private WebElement result() {
        return findElement(checkMail);
    }

    private List<WebElement> record() {
        return findElements(checkRow);
    }

    @Test
    public void testCreateNewRecord() {
        createNewRecord();

        Assert.assertEquals(record().size(), 1);
        Assert.assertEquals(icon().getAttribute("class"), "fa fa-check-square-o");
        Assert.assertEquals(result().getText(), mail);
    }

    @Test
    public void testRestoreDeletedRecord() {
        createNewRecord();
        deleteRecord();
        clickNotification();

        List<WebElement> rows = findElements(binTableRows);
        Assert.assertEquals(rows.size(), 1);
        getDriver().findElement(By.linkText("restore as draft")).click();
        Assert.assertEquals(findElement(By.className("card-body")).getText(), emptyBinPlaceholder);

        clickPlaceholderMenu();

        Assert.assertEquals(record().size(), 1);
        Assert.assertEquals(icon().getAttribute("class"), "fa fa-pencil");
        Assert.assertEquals(result().getText(), mail);
    }

    @Test
    public void testDeleteExistingRecordPermanently() {
        createNewRecord();
        deleteRecord();
        clickNotification();

        List<WebElement> rows = findElements(binTableRows);
        Assert.assertEquals(rows.size(), 1);
        getDriver().findElement(By.linkText("delete permanently")).click();
        Assert.assertEquals(findElement(By.className("card-body")).getText(), emptyBinPlaceholder);

        clickPlaceholderMenu();

        Assert.assertEquals(record().size(), 0);
    }
}
