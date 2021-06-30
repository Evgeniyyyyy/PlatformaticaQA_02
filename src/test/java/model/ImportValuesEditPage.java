package model;

import model.base.BaseEditPage;
import model.base.BaseModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class ImportValuesEditPage extends BaseEditPage<ImportValuesPage> {

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

    @FindBy(xpath = "//button[@data-id='user']")
    private WebElement fieldUser;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(xpath = "//button[text()='Cancel']")
    private WebElement cancelButton;

    public ImportValuesEditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ImportValuesPage createMasterPage() {
        return new ImportValuesPage(getDriver());
    }

    public ImportValuesEditPage fillString(String value) {
        fieldString.clear();
        fieldString.sendKeys(value);

        return this;
    }

    public ImportValuesEditPage fillText(String value) {
        fieldText.clear();
        fieldText.sendKeys(value);

        return this;
    }

    public ImportValuesEditPage fillInt(String value) {
        fieldInt.clear();
        fieldInt.sendKeys(value);

        return this;
    }

    public ImportValuesEditPage fillDecimal(String value) {
        fieldDecimal.clear();
        fieldDecimal.sendKeys(value);

        return this;
    }

    public ImportValuesEditPage fillDate(String value) {
        fieldDate.click();
        fieldDate.clear();
        fieldDate.sendKeys(value);

        return this;
    }

    public ImportValuesEditPage fillDateTime(String value) {
        fieldDateTime.click();
        fieldDateTime.clear();
        fieldDateTime.sendKeys(value);

        return this;
    }

    public  ImportValuesPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return new ImportValuesPage(getDriver());
    }

    public ImportValuesPage clickSaveDraft() {
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new ImportValuesPage(getDriver());
    }

    public ImportValuesPage clickCancel() {
        TestUtils.jsClick(getDriver(), cancelButton);

        return new ImportValuesPage(getDriver());
    }
}
