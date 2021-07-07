package model;

        import model.base.BaseModel;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.FindBy;
        import utils.TestUtils;

public class CalendarEditPage extends BaseModel {

    @FindBy(id = "string")
    private WebElement fieldString;

    @FindBy(id = "text")
    private WebElement fieldText;

    @FindBy(id = "date")
    private WebElement fieldDate;

    @FindBy(id = "datetime")
    private WebElement fieldDateTime;

    @FindBy(id = "int")
    private WebElement fieldInt;

    @FindBy(id = "decimal")
    private WebElement fieldDecimal;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    public CalendarEditPage(WebDriver driver) {
        super(driver);
    }

    public CalendarEditPage fillString(String value) {
        fieldString.sendKeys(value);

        return this;
    }

    public CalendarEditPage fillText(String value) {
        fieldText.sendKeys(value);

        return this;
    }

    public CalendarEditPage fillDate() {
        fieldDate.click();

        return this;
    }

    public CalendarEditPage fillDate(String value) {
        fillEditText(fieldDate, value);

        return this;
    }

    public CalendarEditPage fillDateTime() {
        fieldDateTime.click();

        return this;
    }

    public CalendarEditPage fillDateTime(String value) {
        fillEditText(fieldDateTime, value);
        return this;
    }

    void fillEditText(WebElement webElement, String value) {
        webElement.click();
        webElement.clear();
        webElement.sendKeys(value);
    }



    public CalendarEditPage fillInt(String value) {
        fieldInt.sendKeys(value);

        return this;
    }

    public CalendarEditPage fillDecimal(String value) {
        fieldDecimal.sendKeys(value);

        return this;
    }

    public CalendarPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new CalendarPage(getDriver());
    }

    public ReadonlyPage clickSaveDraft(){
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new ReadonlyPage(getDriver());
    }
}
