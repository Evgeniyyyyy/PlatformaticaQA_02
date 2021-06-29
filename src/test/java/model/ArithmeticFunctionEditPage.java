package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

import static utils.ProjectUtils.clickCreateRecord;
import static utils.ProjectUtils.clickSave;

public class ArithmeticFunctionEditPage extends BaseModel{

    @FindBy(id = "f1")
    private WebElement F1_FIELD;

    @FindBy(id = "f2")
    private WebElement F2_FIELD;

    @FindBy(id = "div")
    private WebElement DIV;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(className = "btn-dark")
    private WebElement cancelButton;

    public ArithmeticFunctionEditPage(WebDriver driver) {
        super(driver);
    }

    public ArithmeticFunctionPage clickSave() {

        getWait().until(ExpectedConditions.attributeToBeNotEmpty(DIV, "value"));
        TestUtils.jsClick(getDriver(), saveButton);

        return new ArithmeticFunctionPage(getDriver());
    }

    public ArithmeticFunctionPage clickSaveDraft() {

        getWait().until(ExpectedConditions.attributeToBeNotEmpty(DIV, "value"));
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new ArithmeticFunctionPage(getDriver());
    }

    public ArithmeticFunctionPage clickCancel() {
        TestUtils.jsClick(getDriver(), cancelButton);

        return new ArithmeticFunctionPage(getDriver());
    }

    public ArithmeticFunctionEditPage fillForm(Integer value1, Integer value2) {
        DIV.clear();
        sendKeysOneByOne(F1_FIELD, value1.toString());
        sendKeysOneByOne(F2_FIELD, value2.toString());

        getWait().until(ExpectedConditions.attributeToBe(DIV, "value", String.valueOf(value1/value2)));

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
