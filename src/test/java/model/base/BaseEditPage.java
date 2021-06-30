package model.base;

import model.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public abstract class BaseEditPage<MasterPage extends BaseMasterPage> extends MainPage {

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(className = "btn-dark")
    private WebElement cancelButton;

    public BaseEditPage(WebDriver driver) {
        super(driver);
    }

    protected abstract MasterPage createMasterPage();

    public MasterPage clickSave() {
        TestUtils.jsClick(getDriver(), saveButton);

        return createMasterPage();
    }

    public MasterPage clickSaveDraft() {
        TestUtils.jsClick(getDriver(), saveDraftButton);

        return createMasterPage();
    }

    public MasterPage clickCancel() {
        TestUtils.jsClick(getDriver(), cancelButton);

        return createMasterPage();
    }
}
