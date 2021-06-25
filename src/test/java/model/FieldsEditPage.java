package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class FieldsEditPage extends BaseModel {

    @FindBy(id = "title")
    private WebElement fieldTitle;

    @FindBy(id = "comments")
    private WebElement fieldComments;

    @FindBy(id = "int")
    private WebElement fieldInt;

    @FindBy(id = "decimal")
    private WebElement fieldDecimal;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "date")
    private WebElement fieldDate;

    @FindBy(id = "datetime")
    private WebElement fieldDateTime;

    @FindBy(className = "filter-option-inner-inner")
    private WebElement fieldUserName;

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

    public FieldsEditPage(WebDriver driver) {
        super(driver);
    }

    public FieldsEditPage fillTitle(String value) {
        fieldTitle.clear();
        fieldTitle.sendKeys(value);

        return this;
    }

    public FieldsEditPage fillComments(String value) {
        fieldComments.clear();
        fieldComments.sendKeys(value);

        return this;
    }

    public FieldsEditPage fillInt(String value) {
        fieldInt.clear();
        fieldInt.sendKeys(value);

        return this;
    }

    public FieldsEditPage fillDecimal(String value) {
        fieldDecimal.clear();
        fieldDecimal.sendKeys(value);

        return this;
    }

    public FieldsPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new FieldsPage(getDriver());
    }

    public FieldsEditPage fillDate(String value) {
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public FieldsEditPage fillDateTime(String value) {
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(value);

        return this;
    }

    public FieldsEditPage findUser(String value) {
        TestUtils.jsClick(getDriver(), fieldUserName);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath(
                "//span[text()='" + value + "']")));

        return this;
    }

    public FieldsPage clickCancel() {
        TestUtils.jsClick(getDriver(), cancelButton);

        return new FieldsPage(getDriver());
    }
}
