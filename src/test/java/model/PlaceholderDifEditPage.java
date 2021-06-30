package model;

import model.base.BaseModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class PlaceholderDifEditPage extends BaseModel {

    @FindBy(id = "string")
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

    @FindBy(className = "filter-option-inner-inner")
    private WebElement fieldUserName;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

    public PlaceholderDifEditPage(WebDriver driver) {

        super(driver);
    }

    public PlaceholderDifEditPage fillComments(String value) {
        fieldString.sendKeys(value);

        return this;
    }


    public PlaceholderDifEditPage fillString(String value) {
        fieldString.sendKeys(value);

        return this;
    }

    public PlaceholderDifEditPage fillText(String value) {
        fieldText.sendKeys(value);

        return this;
    }

    public PlaceholderDifEditPage fillInt(String value) {
        fieldInt.sendKeys(value);

        return this;
    }

    public PlaceholderDifEditPage fillDecimal(String value) {
        fieldDecimal.sendKeys(value);

        return this;
    }

    public PlaceholderDifEditPage fillDate(String value) {
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public PlaceholderDifEditPage fillDateTime(String value) {
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(value);

        return this;
    }

    public PlaceholderDifEditPage fillUserField(String value) {
        TestUtils.jsClick(getDriver(), fieldUserName);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath(
                "//span[text()='" + value + "']")));

        return this;
    }

    public PlaceholderDifPage clickSaveDraft() {
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new PlaceholderDifPage(getDriver());
    }

    public PlaceholderDifEditPage fillNewString(String value) {
        fieldString.click();
        fieldString.clear();
        fieldString.sendKeys(value);

        return this;
    }

    public PlaceholderDifEditPage fillNewText(String value) {
        fieldText.click();
        fieldText.clear();
        fieldText.sendKeys(value);

        return this;
    }

    public PlaceholderDifEditPage fillNewInt(String value) {
        fieldInt.click();
        fieldInt.clear();
        fieldInt.sendKeys(value);

        return this;
    }

    public  PlaceholderDifEditPage clickCancel() {
        TestUtils.jsClick(getDriver(), cancelButton);

        return new  PlaceholderDifEditPage(getDriver());
    }
}





