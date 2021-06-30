package model;

import model.base.BaseModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class DefaultEditPage extends BaseModel {

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

    @FindBy(css = "div.filter-option-inner-inner")
    private WebElement fieldUser;

    @FindBy(xpath = "//div[contains(text(),'apptester10@tester.test')]")
    private WebElement chooseUser;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(className = "btn-dark")
    private WebElement cancelButton;

    public DefaultEditPage(WebDriver driver) {
        super(driver);
    }

    public DefaultEditPage fillString(String value) {
        fieldString.clear();
        fieldString.sendKeys(value);

        return this;
    }

    public DefaultEditPage fillText(String value) {
        fieldText.clear();
        fieldText.sendKeys(value);

        return this;
    }

    public DefaultEditPage fillInt(String value) {
        fieldInt.clear();
        fieldInt.sendKeys(value);

        return this;
    }

    public DefaultEditPage fillDecimal(String value) {
        fieldDecimal.clear();
        fieldDecimal.sendKeys(value);

        return this;
    }

    public DefaultEditPage fillDate(String value) {

        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public DefaultEditPage fillDateTime(String value) {
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(value);

        return this;
    }

    public DefaultEditPage clearElement(WebElement element) {
        element.clear();

        return this;
    }

    public DefaultEditPage clearElement(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        element.clear();

        return this;
    }

    public DefaultEditPage fillUser(String value) {
        fieldUser.click();
        TestUtils.jsClick(getDriver(), chooseUser);

        return this;
    }

    public DefaultPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new DefaultPage(getDriver());
    }

    public DefaultPage clickSaveDraft(){
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new DefaultPage(getDriver());
    }

    public DefaultPage clickCancel(){
        TestUtils.jsClick(getDriver(), cancelButton);

        return new DefaultPage(getDriver());
    }

    public DefaultEditPage fillFields(String string, String text, String int_, String decimal) {
        fillString(string);
        fillText(text);
        fillInt(int_);
        fillDecimal(decimal);

        return this;
    }
}
