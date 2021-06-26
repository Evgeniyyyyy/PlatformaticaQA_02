package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class ReferenceValuesEditPage extends BasePage{

    @FindBy(id="label")
    WebElement fieldLabel;

    @FindBy(id="filter_1")
    WebElement fieldFilter_1;

    @FindBy(id="filter_2")
    WebElement fieldFilter_2;

    @FindBy(id="pa-entity-form-save-btn")
    private WebElement saveButton;

    public ReferenceValuesEditPage(WebDriver driver) {
        super(driver);
    }

    public ReferenceValuesPage clickSaveButton()
    {
        TestUtils.scrollClick(getDriver(), saveButton);

        return new ReferenceValuesPage(getDriver());
    }

    public ReferenceValuesEditPage fillLabel(String Label)
    {

        fieldLabel.sendKeys(Label);

        return this;
    }

    public ReferenceValuesEditPage fillFilter_1(String Filter_1)
    {
        fieldFilter_1.sendKeys(Filter_1);

        return this;
    }

    public ReferenceValuesEditPage fillFilter_2(String Filter_2)
    {
        fieldFilter_2.sendKeys(Filter_2);

        return this;
    }
    public ReferenceValuesEditPage clearFields()
    {
        fieldLabel.clear();
        fieldFilter_1.clear();
        fieldFilter_2.clear();
        return this;
    }
}