package model;

import model.base.BaseModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.TestUtils;


import static utils.TestUtils.jsClick;

public class ChevronEditPage extends BaseModel {
    @FindBy(xpath = "//button[@data-id='string']")
    private WebElement fieldString;

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

    @FindBy(xpath = "//button[@data-id='user']")
    private WebElement fieldUser;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

    public ChevronEditPage(WebDriver driver) {
        super(driver);
    }

    public ChevronEditPage fillString(String value) {
        fieldString.click();
        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//ul[@class='dropdown-menu inner show']//span[contains(.,'"+value+"')]")));
        getWait().until(ExpectedConditions.invisibilityOf(getDriver().findElement(
                By.xpath("//div[@class='dropdown-menu']"))));

        Assert.assertEquals(fieldString.getAttribute("title"), value);

        return this;
    }

    public ChevronEditPage fillText(String value) {
        fieldText.sendKeys(value);

        return this;
    }

    public ChevronEditPage fillInt(String value) {
        fieldInt.sendKeys(value);

        return this;
    }

    public ChevronEditPage fillDecimal(String value) {
        fieldDecimal.sendKeys(value);

        return this;
    }

    public ChevronEditPage fillDate(String value) {
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public ChevronEditPage fillDateTime(String value) {
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(value);

        return this;
    }

    public ChevronPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new ChevronPage(getDriver());
    }

    public ChevronPage clickSaveDraft() {
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new ChevronPage(getDriver());
    }

    public ChevronPage clickCancel() {
        TestUtils.jsClick(getDriver(), cancelButton);

        return new ChevronPage(getDriver());
    }

    public ChevronEditPage checkFormIsNotEmpty(){
        Assert.assertFalse(fieldString.getAttribute("title").isEmpty());
        Assert.assertFalse(fieldText.getText().isEmpty());
        Assert.assertFalse(fieldInt.getAttribute("value").isEmpty());
        Assert.assertFalse(fieldDecimal.getAttribute("value").isEmpty());
        Assert.assertFalse(fieldDate.getAttribute("value").isEmpty());
        Assert.assertFalse(fieldDateTime.getAttribute("value").isEmpty());
        Assert.assertFalse(fieldUser.getAttribute("title").isEmpty());

        return this;
    }

    public ChevronEditPage emptyForm(){
        fieldDate.click();
        fieldDate.clear();

        fieldDateTime.click();
        fieldDateTime.clear();

        fieldText.clear();
        fieldInt.clear();
        fieldDecimal.clear();

        return this;
    }

    public ChevronEditPage setFormFields(ChevronPage.ChevronPageDto data){
        fillString(data.stringDropDownValue);
        fillDateTime(data.dateTimeValue);
        fillDate(data.dateValue);
        fillText(data.formTextValue);
        fillInt(data.formIntValue);
        fillDecimal(data.formDecimalValue);

        return this;
    }
}
