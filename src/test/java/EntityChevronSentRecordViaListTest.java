import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import static utils.ProjectUtils.start;
import static utils.TestUtils.scrollClick;

public class EntityChevronSentRecordViaListTest extends BaseTest {

    private static final By createNewRecord = By.xpath("//i[contains(text(),'create_new_folder')]");
    private static final By textField = By.xpath("//textarea[@id='text']");
    private static final By saveButton = By.xpath("//button[@id='pa-entity-form-save-btn']");
    private static final By sentButton = By.xpath("//button[contains(text(), 'Sent')]");
    private static final By verifySentButton = By.xpath("//a[contains(text(),'Sent')]");
    private static final By recordedData = By.xpath("//a[contains(text(), 'firstExample')]");
    private static final By CHEVRON_MENU = By.xpath("//p[contains(text(),'Chevron')]");

    @Test
    public void testCreateRecordVerifyThatExist() {

        final String ENTERED_TEXT_FIELD = "firstExample";

        start(getDriver());
        scrollClick(getDriver(), getDriver().findElement(CHEVRON_MENU));
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(createNewRecord))).click();
        getDriver().findElement(textField).sendKeys(ENTERED_TEXT_FIELD);
        getDriver().findElement(saveButton).click();
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(sentButton))).click();
        getWait().until(ExpectedConditions.elementToBeClickable(getDriver().findElement(verifySentButton))).click();

        Assert.assertEquals(getDriver().findElement(recordedData).getText(), ENTERED_TEXT_FIELD);
    }
}

