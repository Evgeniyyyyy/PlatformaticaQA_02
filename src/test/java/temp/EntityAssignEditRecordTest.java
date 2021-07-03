package temp;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import utils.ProjectUtils;
import utils.TestUtils;

@Ignore
public class EntityAssignEditRecordTest extends BaseTest {

    private static final By STRING_FIELD = By.id("string");
    private static final By TEXT_FIELD = By.id("text");
    private static final By INT_FIELD = By.id("int");
    private static final By DECIMAL_FIELD = By.id("decimal");

    private String stringInputData = "Record 10";
    private String textInputData = "Text of record 10";
    private String intInputData = "10";
    private String decimalInputData = "10.10";

    private String stringEditedInputData = "Edited record 10";
    private String textEditedInputData = "Edited text of record 10";
    private String intEditedInputData = "11";
    private String decimalEditedInputData = "11.10";

    private void createRecord() {
        ProjectUtils.start(getDriver());
        WebElement assignTab = getDriver().findElement(By.xpath("//p[contains(text(),' Assign ')]"));
        TestUtils.scrollClick(getDriver(), assignTab);
        getDriver().findElement(By.className("card-icon")).click();
        getDriver().findElement(STRING_FIELD).sendKeys(stringInputData);
        getDriver().findElement(TEXT_FIELD).sendKeys(textInputData);
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

        WebElement stringField = getDriver().findElement(STRING_FIELD);
        stringField.clear();
        stringField.sendKeys(stringEditedInputData);

        WebElement textField = getDriver().findElement(TEXT_FIELD);
        textField.clear();
        textField.sendKeys(textEditedInputData);

        WebElement intField = getDriver().findElement(INT_FIELD);
        intField.clear();
        intField.sendKeys(intEditedInputData);

        WebElement decimalField = getDriver().findElement(DECIMAL_FIELD);
        decimalField.clear();
        decimalField.sendKeys(decimalEditedInputData);

        TestUtils.jsClick(getDriver(), getDriver().findElement(By.id("pa-entity-form-save-btn")));

        String stringValue = getDriver().findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
        Assert.assertEquals(stringValue, stringEditedInputData);
        String textValue = getDriver().findElement(By.xpath("//tbody/tr[1]/td[3]")).getText();
        Assert.assertEquals(textValue, textEditedInputData);
        String intValue = getDriver().findElement(By.xpath("//tbody/tr[1]/td[4]")).getText();
        Assert.assertEquals(intValue, intEditedInputData);
        String decimalValue = getDriver().findElement(By.xpath("//tbody/tr[1]/td[5]")).getText();
        Assert.assertEquals(decimalValue, decimalEditedInputData);

    }
}


