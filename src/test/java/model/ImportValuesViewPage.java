package model;

import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ImportValuesViewPage extends BaseViewPage<ImportValuesPage> {

    public ImportValuesViewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span [@class = 'pa-view-field']")
    private List<WebElement> viewModeRecord;

    @FindBy(xpath = "//div[@class = 'form-group']/p")
    private WebElement user;

    @Override
    protected ImportValuesPage createMasterPage() {
        return new ImportValuesPage(getDriver());
    }
}
