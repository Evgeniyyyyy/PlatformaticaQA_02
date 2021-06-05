import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.UUID;

public class EntityFieldsEditRecordTest extends BaseTest {

    private static final By TITLE_FIELD = By.id("title");
    private static final By COMMENTS_FIELD = By.id("comments");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");

    private String titleInputData = "Welcome to Java";
    private String commentsInputData = "Learning Java is so much fun";
    private String intInputData = "10";
    private String decimalInputData = "10.10";

    private String titleEditedInputData = "Welcome to Ruby";
    private String commentsEditedInputData = "Learning Ruby is so much easier than Java";
    private String intEditedInputData = "11";
    private String decimalEditedInputData = "11.10";

    private void createRecord() {
        ProjectUtils.start(getDriver());
        WebElement fieldsTab = getDriver().findElement(By.xpath("//p[contains(text(),' Fields ')]"));
        TestUtils.scrollClick(getDriver(), fieldsTab);
        getDriver().findElement(By.className("card-icon")).click();
        getDriver().findElement(TITLE_FIELD).sendKeys(titleInputData);
        getDriver().findElement(COMMENTS_FIELD).sendKeys(commentsInputData);
        getDriver().findElement(INT_FIELD).sendKeys(intInputData);
        getDriver().findElement(DECIMAL_FIELD).sendKeys(decimalInputData);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));
    }

    private void clickEditButton() {
        WebElement dropdown = getDriver().findElement(By.cssSelector("button.btn-round.dropdown-toggle"));
        TestUtils.scrollClick(getDriver(), dropdown);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath("//a[text()='edit']")));
    }

    @Test
    public void editRecordTest() {

        createRecord();
        clickEditButton();

        WebElement stringField = getDriver().findElement(TITLE_FIELD);
        stringField.clear();
        stringField.sendKeys(titleEditedInputData);

        WebElement textField = getDriver().findElement(COMMENTS_FIELD);

        textField.clear();
        textField.sendKeys(commentsEditedInputData);

        WebElement intField = getDriver().findElement(INT_FIELD);
        intField.clear();
        intField.sendKeys(intEditedInputData);

        WebElement decimalField = getDriver().findElement(DECIMAL_FIELD);
        decimalField.clear();
        decimalField.sendKeys(decimalEditedInputData);

        TestUtils.jsClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));

        String stringValue = getDriver().findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
        Assert.assertEquals(stringValue, titleEditedInputData);
        String textValue = getDriver().findElement(By.xpath("//tbody/tr[1]/td[3]")).getText();
        Assert.assertEquals(textValue, commentsEditedInputData);
        String intValue = getDriver().findElement(By.xpath("//tbody/tr[1]/td[4]")).getText();
        Assert.assertEquals(intValue, intEditedInputData);
        String decimalValue = getDriver().findElement(By.xpath("//tbody/tr[1]/td[5]")).getText();
        Assert.assertEquals(decimalValue, decimalEditedInputData);

    }
}