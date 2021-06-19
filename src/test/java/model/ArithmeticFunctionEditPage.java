package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class ArithmeticFunctionEditPage extends BaseModel{

    @FindBy(id = "f1")
    private WebElement F1_FIELD;

    @FindBy(id = "f2")
    private WebElement F2_FIELD;

    @FindBy(id = "sum")
    private WebElement SUM;

    @FindBy(id = "sub")
    private WebElement SUB;

    @FindBy(id = "mul")
    private WebElement MUL;

    @FindBy(id = "div")
    private WebElement DIV;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(id = "//button[@class='btn btn-dark']")
    private WebElement cancelButton;


    public ArithmeticFunctionEditPage(WebDriver driver) {
        super(driver);
    }

    public ArithmeticFunctionEditPage fillF1_Field(String value) {
        F1_FIELD.sendKeys(value);

        return this;
    }

    public ArithmeticFunctionEditPage fillF2_Field(String value) {
        F2_FIELD.sendKeys(value);

        return this;
    }

    public ArithmeticFunctionPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new ArithmeticFunctionPage(getDriver());
    }

    public ArithmeticFunctionPage clickSaveDraft() {
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new ArithmeticFunctionPage(getDriver());
    }

    public ArithmeticFunctionPage clickCancel() {
        TestUtils.jsClick(getDriver(), cancelButton);

        return new ArithmeticFunctionPage(getDriver());
    }
}
