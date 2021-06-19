package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestUtils;

public class AssignEditPage extends BaseModel {

    public AssignEditPage(WebDriver driver) {

        super(driver);
    }

    @FindBy(id = "string")
    private WebElement fieldString;

    @FindBy(id = "text")
    private WebElement fieldText;

    @FindBy(id = "int")
    private WebElement fieldInt;

    @FindBy(id = "decimal")
    private WebElement fieldDecimal;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;


    public AssignEditPage fillTitle(String value) {
        fieldString.sendKeys(value);

        return this;
    }

    public AssignEditPage fillComments(String value) {
        fieldText.sendKeys(value);

        return this;
    }

    public AssignEditPage fillInt(String value) {
        fieldInt.sendKeys(value);

        return this;
    }

    public AssignEditPage fillDecimal(String value) {
        fieldDecimal.sendKeys(value);

        return this;
    }

    public AssignPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new AssignPage(getDriver());
    }
}
