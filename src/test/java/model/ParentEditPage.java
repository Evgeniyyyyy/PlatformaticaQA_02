package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class ParentEditPage extends BasePage {

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

    public ParentEditPage(WebDriver driver) {
        super(driver);
    }

    public ParentEditPage fillString(String value) {
        fieldString.clear();
        fieldString.sendKeys(value);

        return this;
    }

    public ParentEditPage fillText(String value) {
        fieldText.clear();
        fieldText.sendKeys(value);

        return this;
    }

    public ParentEditPage fillInt(String value) {
        fieldInt.clear();
        fieldInt.sendKeys(value);

        return this;
    }

    public ParentEditPage fillDecimal(String value) {
        fieldDecimal.clear();
        fieldDecimal.sendKeys(value);

        return this;
    }

    public ParentEditPage fillDate(String value) {

        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public ParentEditPage fillDateTime(String value) {
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(value);

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
