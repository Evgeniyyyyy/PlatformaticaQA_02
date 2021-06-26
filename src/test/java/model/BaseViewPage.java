package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseViewPage<MasterPage> extends MainPage {

    @FindBy(xpath = "//i[@class = 'material-icons'][text()='clear']")
    private WebElement closeViewWindow;

    public BaseViewPage(WebDriver driver) {
        super(driver);
    }

    protected abstract MasterPage createMasterPage();

    public MasterPage closeViewWindow() {
        closeViewWindow.click();

        return createMasterPage();
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
}
