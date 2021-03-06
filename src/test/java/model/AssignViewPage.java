package model;

import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class AssignViewPage extends BaseViewPage<AssignPage> {

    @FindBy(xpath = "//span [@class = 'pa-view-field']")
    private List<WebElement> viewModeRecord;

    @FindBy(xpath = "//div[@class = 'form-group']/p")
    private WebElement user;

    public AssignViewPage(WebDriver driver) {
            super(driver);
        }

    @Override
    protected AssignPage createMasterPage() {
            return new AssignPage(getDriver());
    }
}
