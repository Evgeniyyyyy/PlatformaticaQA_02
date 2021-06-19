package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.text.SimpleDateFormat;

public class ParentEditPage extends BaseModel {

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

    public ParentEditPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getFieldString() {
        return fieldString;
    }

    public WebElement getFieldText() {
        return fieldText;
    }

    public WebElement getFieldInt() {
        return fieldInt;
    }

    public WebElement getFieldDecimal() {
        return fieldDecimal;
    }

    public WebElement getFieldDate() {
        return fieldDate;
    }

    public WebElement getFieldUser() {
        return fieldUser;
    }

    public WebElement getChooseUser() {
        return chooseUser;
    }

    public ParentEditPage fillString(String value) {
        fieldString.sendKeys(value);

        return this;
    }

    public ParentEditPage fillText(String value) {
        fieldText.sendKeys(value);

        return this;
    }

    public ParentEditPage fillInt(String value) {
        fieldInt.sendKeys(value);

        return this;
    }

    public ParentEditPage fillDecimal(String value) {
        fieldDecimal.sendKeys(value);

        return this;
    }

    public ParentEditPage fillDate(SimpleDateFormat value) {
        fieldDate.click();

        return this;
    }

    public ParentEditPage clearElement(WebElement element) {
        element.clear();

        return this;
    }

    public ParentEditPage clearElement(WebDriver driver, By by) {
        WebElement element = driver.findElement(by);
        element.clear();

        return this;
    }

    public ParentEditPage fillUser(String value) {
        fieldUser.click();
        TestUtils.jsClick(getDriver(), chooseUser);
        return this;
    }

    public ParentPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new ParentPage(getDriver());
    }

    public ParentPage clickSaveDraft(){
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new ParentPage(getDriver());
    }

    public ParentPage clickCancel(){
        TestUtils.jsClick(getDriver(), cancelButton);

        return new ParentPage(getDriver());
    }
}
