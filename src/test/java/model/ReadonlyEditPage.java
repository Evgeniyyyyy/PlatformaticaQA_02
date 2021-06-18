package model;

        import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.FindBy;
        import org.openqa.selenium.support.PageFactory;
        import utils.TestUtils;

public class ReadonlyEditPage extends BaseModel {

    @FindBy(id = "string")
    private WebElement fieldString;

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

    public ReadonlyEditPage(WebDriver driver) {
        super(driver);
    }

    public ReadonlyEditPage fillString(String value) {
        fieldString.sendKeys(value);

        return this;
    }

    public ReadonlyEditPage fillText(String value) {
        fieldText.sendKeys(value);

        return this;
    }

    public ReadonlyEditPage fillInt(String value) {
        fieldInt.sendKeys(value);

        return this;
    }

    public ReadonlyEditPage fillDecimal(String value) {
        fieldDecimal.sendKeys(value);

        return this;
    }

    public ReadonlyPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new ReadonlyPage(getDriver());
    }

    public ReadonlyPage clickSaveDraft(){
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new ReadonlyPage(getDriver());
    }
}
