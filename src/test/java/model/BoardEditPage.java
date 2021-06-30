package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.TestUtils;

import java.util.List;

import static utils.TestUtils.jsClick;

public class BoardEditPage extends BaseModel {

    @FindBy(xpath = "//button[@data-id='string']")
    private WebElement fieldString;

    @FindBy(xpath = "//span[text()='Pending']")
    private WebElement valueStringPending;

    @FindBy(xpath = "//span[text()='On track']")
    private WebElement valueStringOnTrack;

    @FindBy(xpath = "//span[text()='Done']")
    private WebElement valueStringDone;

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

    @FindBy(xpath = "//button[@data-id='user']")
    private WebElement userName;

    public BoardEditPage(WebDriver driver) {
        super(driver);
    }

    public BoardEditPage fillString(String value) {
        fieldString.click();

        jsClick(getDriver(), getDriver().findElement(
                By.xpath("//ul[@class='dropdown-menu inner show']//span[contains(.,'"+value+"')]")));
        getWait().until(ExpectedConditions.invisibilityOf(getDriver().findElement(
                By.xpath("//div[@class='dropdown-menu']"))));

        Assert.assertEquals(fieldString.getAttribute("title"), value);

        return this;
    }

    public BoardEditPage fillText(String value) {

        getWait().until(ExpectedConditions.elementToBeClickable(fieldText));

        fieldText.sendKeys(value);

        return this;
    }

    public void clearText() {
        fieldText.clear();

    }

    public BoardEditPage fillInt(String value) {
       fieldInt.sendKeys(value);

        return this;
    }

    public void clearInt() {
        fieldInt.clear();

    }

    public BoardEditPage fillDecimal(String value) {
        fieldDecimal.sendKeys(value);

        return this;
    }
    public void clearDecimal() {
        fieldDecimal.clear();

    }

    public BoardEditPage fillDate(String value) {
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public BoardEditPage fillDateTime(String value) {
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(value);

        return this;
    }

    public BoardEditPage fillFields(List<String> list) {

        fillDateTime(list.get(5));
        fillDate(list.get(4));
        fillText(list.get(1));
        fillInt(list.get(2));
        fillDecimal(list.get(3));
        fillString(list.get(0));

        return this;
    }

    public BoardEditPage clearFields(){
        clearText();
        clearInt();
        clearDecimal();

        return this;
    }

    public BoardEditPage findUser(String value) {
        TestUtils.scrollClick(getDriver(), userName);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath(
                "//span[text()='" + value + "']")));

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

    public BoardPage clickCancel(){
        TestUtils.jsClick(getDriver(), cancelButton);

        return new BoardPage(getDriver());
    }
}
