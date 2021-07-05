package model;

import model.base.BaseOrderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ImportValuesOrderPage extends BaseOrderPage<ImportValuesOrderPage> {

    @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
    private WebElement toggle;

    public ImportValuesOrderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ImportValuesEditPage createEditPage() {
        return new ImportValuesEditPage(getDriver());
    }

    @Override
    protected ImportValuesViewPage createViewPage() {
        return new ImportValuesViewPage(getDriver());
    }

    @Override
    protected ImportValuesOrderPage createMasterPage() {
        return new ImportValuesOrderPage(getDriver());
    }

    public ImportValuesOrderPage clickToggleOrder() {
        toggle.click();

        return new ImportValuesOrderPage(getDriver());
    }
}
