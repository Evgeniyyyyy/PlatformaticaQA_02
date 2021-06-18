package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

import static utils.TestUtils.jsClick;

public class BoardEditPage extends BaseModel {

    @FindBy(xpath = "//button[@data-id='string']")
    private WebElement fieldString;

    @FindBy(xpath = "//div[text()='Pending']")
    private WebElement valueStringPending;

    @FindBy(xpath = "//div[text()='On track']")
    private WebElement valueStringOnTrack;

    @FindBy(xpath = "//div[text()='Done']")
    private WebElement valueStringDone;

    @FindBy(id = "text")
    private WebElement fieldText;

    @FindBy(id = "int")
    private WebElement fieldInt;

    @FindBy(id = "decimal")
    private WebElement fieldDecimal;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    public BoardEditPage(WebDriver driver) {
        super(driver);
    }

    private void sendKeysOneByOne(WebElement element, String input) {
        char[] editKeys = input.toCharArray();
        StringBuilder newString = new StringBuilder();
        for (char c : editKeys) {
            newString.append(c);
            element.sendKeys(String.valueOf(c));
            getWait().until(ExpectedConditions.attributeToBe(element, "value", String.valueOf(newString)));
        }
    }

    public BoardEditPage fillString(String value) {
        fieldString.click();
        if(value == "Pending") {
            jsClick(getDriver(), valueStringPending);
        }else if(value == "On track"){
            jsClick(getDriver(), valueStringOnTrack);
        }else {
            jsClick(getDriver(), valueStringDone);
        }
        return this;
    }

    public BoardEditPage fillText(String value) {

        getWait().until(ExpectedConditions.elementToBeClickable(fieldText));
        fieldText.click();
        sendKeysOneByOne(fieldText,value);

        return this;
    }

    public BoardEditPage fillInt(String value) {
        sendKeysOneByOne(fieldInt,value);

        return this;
    }

    public BoardEditPage fillDecimal(String value) {

        sendKeysOneByOne(fieldDecimal,value);

        return this;
    }

    public BoardPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new BoardPage(getDriver());
    }

    public BoardPage clickSaveDraft(){
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new BoardPage(getDriver());
    }
}
