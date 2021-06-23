package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class PlaceholderEditPage extends BaseModel {

    public PlaceholderEditPage(WebDriver driver) {
        super(driver);
    }

    @FindBy (id = "string")
    private WebElement fieldString;

    @FindBy (id = "text")
    private WebElement fieldText;

    @FindBy (id = "int")
    private WebElement fieldInt;

    @FindBy (id = "decimal")
    private WebElement fieldDecimal;

    @FindBy (id = "date")
    private WebElement fieldDate;

    @FindBy (id = "datetime")
    private WebElement fieldDateTime;

    @FindBy (id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(className = "filter-option-inner-inner")
    private WebElement fieldUserName;

    public PlaceholderEditPage fillString(String value){
        fieldString.sendKeys(value);

        return this;
    }

    public PlaceholderEditPage fillText(String value){
        fieldText.sendKeys(value);

        return this;
    }

    public PlaceholderEditPage fillInt(String value){
        fieldInt.sendKeys(value);

        return this;
    }

    public PlaceholderEditPage fillDecimal(String value){
        fieldDecimal.sendKeys(value);

        return this;
    }

    public PlaceholderEditPage fillDate(String value){
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public PlaceholderEditPage fillDateTime(String value){
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(value);

        return this;
    }

    public PlaceholderEditPage fillUserField(String value) {
        TestUtils.jsClick(getDriver(), fieldUserName);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath(
                "//span[text()='" + value + "']")));

        return this;
    }

    public PlaceholderPage clickSave(){
        TestUtils.jsClick(getDriver(), saveButton);

        return new PlaceholderPage(getDriver());
    }
}
