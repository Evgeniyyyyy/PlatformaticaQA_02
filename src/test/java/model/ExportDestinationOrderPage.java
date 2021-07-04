package model;

import model.base.BaseOrderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ExportDestinationOrderPage extends BaseOrderPage<ExportDestinationOrderPage> {

    @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
    private WebElement toggle;

    public ExportDestinationOrderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ExportDestinationEditPage createEditPage() {
        return new ExportDestinationEditPage(getDriver());
    }

    @Override
    protected ExportDestinationViewPage createViewPage() {
        return new ExportDestinationViewPage(getDriver());
    }

    @Override
    protected ExportDestinationOrderPage createMasterPage() {
        return new ExportDestinationOrderPage(getDriver());
    }

    public ExportDestinationOrderPage clickToggleOrder() {
        toggle.click();

        return new ExportDestinationOrderPage(getDriver());
    }
}
