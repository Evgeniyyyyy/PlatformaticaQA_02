package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class TagEditPage extends BaseModel{

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
    private WebElement fieldDatetime;

    @FindBy(className = "filter-option-inner-inner")
    private WebElement fieldUserName;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(xpath = "//button[@class='btn btn-dark']")
    private WebElement cancelButton;

    public TagEditPage(WebDriver driver) {
        super(driver);
    }

    public TagEditPage fillString(String value) {
        fieldString.clear();
        fieldString.sendKeys(value);

        return this;
    }

    public TagEditPage fillText(String value) {
        fieldText.clear();
        fieldText.sendKeys(value);

        return this;
    }

    public TagEditPage fillInt(String value) {
        fieldInt.clear();
        fieldInt.sendKeys(value);

        return this;
    }

    public TagEditPage fillDecimal(String value) {
        fieldDecimal.clear();
        fieldDecimal.sendKeys(value);

        return this;
    }

    public TagEditPage fillDate(String value) {
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public TagEditPage fillDateTime(String value) {
        fieldDatetime.click();
        fieldDatetime.clear();
        fieldDatetime.sendKeys(value);

        return this;
    }

    public TagEditPage findUser(String value) {
        TestUtils.jsClick(getDriver(), fieldUserName);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath(
                "//span[text()='" + value + "']")));

        return this;
    }

    public TagPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new TagPage(getDriver());
    }

    public TagPage clickDraft() {
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new TagPage(getDriver());
    }

    public TagPage clickCancel() {
        TestUtils.jsClick(getDriver(), cancelButton);

        return new TagPage(getDriver());
    }
}
