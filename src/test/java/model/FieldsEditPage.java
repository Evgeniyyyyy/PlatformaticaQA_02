package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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

    public FieldsEditPage(WebDriver driver) {
        super(driver);
    }

    public FieldsEditPage fillTitle(String value) {
        fieldTitle.sendKeys(value);

        return this;
    }

    public FieldsEditPage fillComments(String value) {
        fieldComments.sendKeys(value);

        return this;
    }

    public FieldsEditPage fillInt(String value) {
        fieldInt.sendKeys(value);

        return this;
    }

    public FieldsEditPage fillDecimal(String value) {
        fieldDecimal.sendKeys(value);

        return this;
    }

    public FieldsPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new FieldsPage(getDriver());
    }
}
