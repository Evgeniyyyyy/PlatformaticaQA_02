package model;

import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ExportDestinationViewPage extends BaseViewPage<ExportDestinationPage> {

    public ExportDestinationViewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span [@class = 'pa-view-field']")
    private List<WebElement> viewModeRecord;

    @FindBy(xpath = "//div[@class = 'form-group']/p")
    private WebElement user;

    @Override
    protected ExportDestinationPage createMasterPage() {
        return new ExportDestinationPage(getDriver());
    }
}
