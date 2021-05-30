import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityChevronSentRecordViaListTest extends BaseTest {

    By createNewRecord = By.xpath("//i[contains(text(),'create_new_folder')]");
    By textField = By.xpath("//textarea[@id='text']");
    By saveButton = By.xpath("//button[@id='pa-entity-form-save-btn']");
    By sentButton = By.xpath("//button[contains(text(), 'Sent')]");
    By verifySentButton = By.xpath("//a[contains(text(),'Sent')]");
    By recordedData = By.xpath("//a[contains(text(), 'firstExample')]");

    private static final By CHEVRON_MENU = By.xpath("//p[contains(text(),'Chevron')]");

    @Test
    public void testCreateNewRecord() {
        WebDriverWait wait = getWait();

        final String enteredTextField = "firstExample";

        start(getDriver());
        scrollClick(getDriver(), getDriver().findElement(CHEVRON_MENU));
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(createNewRecord))).click();
        getDriver().findElement(textField).sendKeys(enteredTextField);
        getDriver().findElement(saveButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(sentButton))).click();
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(verifySentButton))).click();
        getDriver().findElement(recordedData).isDisplayed();

        Assert.assertTrue(getDriver().findElement(recordedData).isDisplayed());
    }
}
