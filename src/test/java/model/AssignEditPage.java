package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestUtils;

public class AssignEditPage extends BasePage {

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

    @FindBy(id = "date")
    private WebElement fieldDate;

    @FindBy(id = "datetime")
    private WebElement fieldDateTime;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

    @FindBy(className = "filter-option-inner-inner")
    private WebElement fieldUserName;


    public AssignEditPage fillTitle(String value) {
        fieldString.click();
        fieldString.clear();
        fieldString.sendKeys(value);

        return this;
    }

    public AssignEditPage fillComments(String value) {
        fieldText.click();
        fieldText.clear();
        fieldText.sendKeys(value);

        return this;
    }

    public AssignEditPage fillInt(String value) {
        fieldInt.click();
        fieldInt.clear();
        fieldInt.sendKeys(value);

        return this;
    }

    public AssignEditPage fillDecimal(String value) {
        fieldDecimal.click();
        fieldDecimal.clear();
        fieldDecimal.sendKeys(value);

        return this;
    }

    public AssignEditPage fillDate(String value) {
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public AssignEditPage fillDateTime(String value) {
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(value);

        return this;
    }

    public AssignEditPage fillFields(String string, String text, String int_, String decimal) {
        fillTitle(string);
        fillComments(text);
        fillInt(int_);
        fillDecimal(decimal);

        return this;
    }

    public AssignPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new AssignPage(getDriver());
    }

    public AssignPage clickSaveDraft() {
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new AssignPage(getDriver());
    }

    public AssignPage clickCancel() {
        TestUtils.jsClick(getDriver(), cancelButton);

        return new AssignPage(getDriver());
    }

    public AssignEditPage findUser(String value) {
        TestUtils.jsClick(getDriver(), fieldUserName);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath(
                "//span[text()='" + value + "']")));

        return this;
    }
}
