package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

public class ReferenceValuesEditPage1 extends BaseModel {

    @FindBy(id = "label")
    private WebElement fieldLabel;

    @FindBy(id = "filter_1")
    private WebElement fieldFilter_1;

    @FindBy(id = "filter_2")
    private WebElement feildFilter_2;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(className = "btn-dark")
    private WebElement cancelButton;

    public ReferenceValuesEditPage1(WebDriver driver) {
        super(driver);
    }

    public ReferenceValuesEditPage1 fillLabel(String value) {
        fieldLabel.clear();
        fieldLabel.sendKeys(value);

        return this;
    }

    public ReferenceValuesEditPage1 fillFilter_1(String value) {
        fieldFilter_1.clear();
        fieldFilter_1.sendKeys(value);

        return this;
    }

    public ReferenceValuesEditPage1 fillFilter_2(String value) {
        feildFilter_2.clear();
        feildFilter_2.sendKeys(value);

        return this;
    }

    public ReferenceValuesPage1 clickSave() {
        getWait().until(ExpectedConditions.elementToBeClickable(saveButton));
        TestUtils.jsClick(getDriver(), saveButton);

        return new ReferenceValuesPage1(getDriver());
    }

    public ReferenceValuesPage1 clickSaveDraft() {
        getWait().until(ExpectedConditions.elementToBeClickable(saveDraftButton));
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return new ReferenceValuesPage1(getDriver());
    }

    public ReferenceValuesPage1 clickCancel() {
        getWait().until(ExpectedConditions.elementToBeClickable(cancelButton));
        TestUtils.jsClick(getDriver(), cancelButton);

        return new ReferenceValuesPage1(getDriver());
    }
}
