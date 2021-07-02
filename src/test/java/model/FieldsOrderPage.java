package model;

import model.base.BaseOrderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FieldsOrderPage extends BaseOrderPage<FieldsOrderPage> {

    @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
    private WebElement toggle;

    public FieldsOrderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FieldsEditPage createEditPage() {
        return new FieldsEditPage(getDriver());
    }

    @Override
    protected FieldsViewPage createViewPage() {
        return new FieldsViewPage(getDriver());
    }

    @Override
    protected FieldsOrderPage createMasterPage() {
        return new FieldsOrderPage(getDriver());
    }

    public FieldsOrderPage clickToggleOrder() {
        toggle.click();

        return new FieldsOrderPage(getDriver());
    }
}
