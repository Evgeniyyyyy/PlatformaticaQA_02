package model;

import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;

public class ExportDestinationViewPage extends BaseViewPage<ExportDestinationPage> {

    public ExportDestinationViewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span [@class = 'pa-view-field']")
    private List<WebElement> viewModeRecord;

    @FindBy(xpath = "//div[@class = 'form-group']/p")
    private WebElement user;

    public List<String> getRecordInViewMode(){
        List<String> listValues = new ArrayList<>();
        for (WebElement element : viewModeRecord) {
            listValues.add(element.getText());
        }
        listValues.add("");
        listValues.add(user.getText());

        return listValues;
    }

    @Override
    protected ExportDestinationPage createMasterPage() {
        return new ExportDestinationPage(getDriver());
    }
}
