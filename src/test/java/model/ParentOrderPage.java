package model;

import model.base.BaseOrderPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ParentOrderPage extends BaseOrderPage<ParentOrderPage> {

    @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
    private WebElement toggle;

    public ParentOrderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ParentEditPage createEditPage() {
        return new ParentEditPage(getDriver());
    }

    @Override
    protected ParentViewPage createViewPage() {
        return new ParentViewPage(getDriver());
    }

    @Override
    protected ParentOrderPage createMasterPage() {
        return new ParentOrderPage(getDriver());
    }

    public ParentOrderPage clickToggleOrder() {
        toggle.click();

        return new ParentOrderPage(getDriver());
    }
}
