package model;

import model.base.BaseModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

public class ArithmeticInlineEditPage extends BaseModel {

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

    public ArithmeticInlineEditPage(WebDriver driver) {
        super(driver);
    }

    public ArithmeticInlineEditPage fillF1_Field(String value) {
        F1_FIELD.sendKeys(value);

        return this;
    }

    public ArithmeticInlineEditPage fillF2_Field(String value) {
        F2_FIELD.sendKeys(value);

        return this;
    }

    public ArithmeticInlineEditPage fillF1_FieldSlow(Integer value) {
        sendKeysOneByOne(F1_FIELD, value.toString());

        return this;
    }

    public ArithmeticInlineEditPage fillF2_FieldSlow(Integer value) {
        sendKeysOneByOne(F2_FIELD, value.toString());

        return this;
    }

    public ArithmeticInlinePage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new ArithmeticInlinePage(getDriver());
    }

    public ArithmeticInlinePage clickSaveDraft() {
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new ArithmeticInlinePage(getDriver());
    }

    public ArithmeticInlinePage clickCancel() {
        TestUtils.jsClick(getDriver(), cancelButton);

        return new ArithmeticInlinePage(getDriver());
    }

    public ArithmeticInlineEditPage fillForm(Integer value1, Integer value2) {
        sendKeysOneByOne(F1_FIELD, value1.toString());
        sendKeysOneByOne(F2_FIELD, value2.toString());
        getWait().until(ExpectedConditions.textToBePresentInElementValue(SUM, String.valueOf(value1 + value2)));
        getWait().until(ExpectedConditions.textToBePresentInElementValue(SUB, String.valueOf(value1 - value2)));
        getWait().until(ExpectedConditions.textToBePresentInElementValue(MUL, String.valueOf(value1 * value2)));
        getWait().until(ExpectedConditions.textToBePresentInElementValue(DIV, String.valueOf(value1 / value2)));

        return this;
    }

    private void sendKeysOneByOne(WebElement element, String input) {
        if (!element.getAttribute("value").isEmpty()) {
            element.clear();
        }

        char[] editKeys = input.toCharArray();
        StringBuilder newString = new StringBuilder();
        for (char c : editKeys) {
            newString.append(c);
            element.sendKeys(String.valueOf(c));
            getWait().until(ExpectedConditions.attributeToBe(element, "value", String.valueOf(newString)));
        }
    }
}
