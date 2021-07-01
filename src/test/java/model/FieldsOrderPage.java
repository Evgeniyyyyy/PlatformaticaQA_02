package model;

import model.base.BaseOrderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FieldsOrderPage extends BaseOrderPage<FieldsPage> {

    @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
    private WebElement toggle;

    public FieldsOrderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FieldsPage createMasterPage() {
        return new FieldsPage(getDriver());
    }

        public FieldsOrderPage clickToggleOrder() {
        toggle.click();

        return new FieldsOrderPage(getDriver());
    }

}
