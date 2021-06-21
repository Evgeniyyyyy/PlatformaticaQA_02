package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class BoardViewPage extends MainPage {

    public BoardViewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span [@class = 'pa-view-field']")
    private List<WebElement> viewModeRecord;

    @FindBy(xpath = "//div[@class = 'form-group']/p")
    private WebElement user;

    @FindBy(xpath = "//i[@class = 'material-icons'][text()='clear']")
    private WebElement closeViewWindow;

    public List<String> getActualRecordInViewMode(){
        List<String> listValues = new ArrayList<>();
        for (WebElement element : viewModeRecord) {
            listValues.add(element.getText());
        }
        return listValues;
    }

    public String getActualUserName(){
        return user.getText();
    }

    public RecycleBinPage closeViewWindow(){
        closeViewWindow.click();

        return new RecycleBinPage(getDriver());

    }
}
