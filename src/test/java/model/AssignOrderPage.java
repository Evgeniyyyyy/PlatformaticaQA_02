package model;

import model.base.BaseEditPage;
import model.base.BaseOrderPage;
import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AssignOrderPage extends BaseOrderPage<AssignPage> {

    @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
    private WebElement toggle;

    public AssignOrderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected BaseViewPage createViewPage() {
        return new AssignViewPage(getDriver());
    }

    @Override
    protected BaseEditPage createEditPage() {
        return new AssignEditPage(getDriver());
    }

    @Override
    protected AssignPage createMasterPage() {
        return new AssignPage(getDriver());
    }

    public AssignOrderPage clickToggleOrder() {
        toggle.click();

        return new AssignOrderPage(getDriver());
    }
}
