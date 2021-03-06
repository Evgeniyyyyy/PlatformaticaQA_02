package model;

import model.base.BaseEditPage;
import model.base.BaseMasterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.TestUtils;

import static utils.TestUtils.jsClick;

public abstract class BoardBaseEditPage<MasterPage extends BaseMasterPage, ThisPage extends BoardBaseEditPage> extends BaseEditPage<MasterPage> {

    public enum FieldString {
        Pending("Pending"),
        OnTrack("On track"),
        Done("Done");

        private String value;

        FieldString(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    @FindBy(xpath = "//button[@data-id='string']")
    private WebElement fieldString;

    @FindBy(xpath = "//span[text()='Pending']")
    private WebElement valueStringPending;

    @FindBy(xpath = "//span[text()='On track']")
    private WebElement valueStringOnTrack;

    @FindBy(xpath = "//span[text()='Done']")
    private WebElement valueStringDone;

    @FindBy(id = "text")
    private WebElement fieldText;

    @FindBy(id = "int")
    private WebElement fieldInt;

    @FindBy(id = "decimal")
    private WebElement fieldDecimal;

    @FindBy(id = "date")
    private WebElement fieldDate;

    @FindBy(id = "datetime")
    private WebElement fieldDateTime;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[@data-id='user']")
    private WebElement userName;

    public BoardBaseEditPage(WebDriver driver) {
        super(driver);
    }

    public ThisPage fillString(FieldString value) {

        fieldString.click();

        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//ul[@class='dropdown-menu inner show']//span[contains(.,'"+value.getValue()+"')]")));
        getWait().until(ExpectedConditions.invisibilityOf(getDriver().findElement(
                By.xpath("//div[@class='dropdown-menu']"))));

        Assert.assertEquals(fieldString.getAttribute("title"), value.getValue());

        return (ThisPage)this;
    }

    public ThisPage fillText(String value) {

        getWait().until(ExpectedConditions.elementToBeClickable(fieldText));

        fieldText.sendKeys(value);

        return (ThisPage)this;
    }

    public void clearText() {
        fieldText.clear();

    }

    public ThisPage fillInt(String value) {
        fieldInt.sendKeys(value);

        return (ThisPage)this;
    }

    public void clearInt() {
        fieldInt.clear();

    }

    public ThisPage fillDecimal(String value) {
        fieldDecimal.sendKeys(value);

        return (ThisPage)this;
    }
    public void clearDecimal() {
        fieldDecimal.clear();

    }

    public ThisPage fillDate(String value) {
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return (ThisPage)this;
    }

    public ThisPage fillDateTime(String value) {
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(value);

        return (ThisPage)this;
    }

    public ThisPage fillAllFields(FieldString fillString, String fillText, String fillInt,
                                  String fillDecimal, String fillDate, String fillDateTime) {

        fillDateTime(fillDateTime);
        fillDate(fillDate);
        fillText(fillText);
        fillInt(fillInt);
        fillDecimal(fillDecimal);
        fillString(fillString);

        return (ThisPage)this;
    }

    public ThisPage clearFields(){
        clearText();
        clearInt();
        clearDecimal();

        return (ThisPage)this;
    }

    public ThisPage findUser(String value) {
        TestUtils.scrollClick(getDriver(), userName);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath(
                "//span[text()='" + value + "']")));

        return (ThisPage)this;
    }
}
