package model;

import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class DefaultViewPage extends BaseViewPage<DefaultPage> {

    public DefaultViewPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected DefaultPage createMasterPage() {
        return new DefaultPage(getDriver());
    }

    @FindBy(xpath = "//span[@class='pa-view-field']")
    private List<WebElement> viewModeRecord;

    @FindBy(xpath = "//div[@class='form-group']/p")
    private WebElement user;

    public List<String> getRecordInViewMode(){
        List<String> listValues = new ArrayList<>();
        for (WebElement element : viewModeRecord) {
            listValues.add(element.getText());
        }
        listValues.add(user.getText());

        return listValues;
    }
}
