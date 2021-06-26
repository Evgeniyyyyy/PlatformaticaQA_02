package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class PlaceholderDifViewPage extends MainPage {

    @FindBy(xpath = "//span[@class='pa-view-field']")
    private List<WebElement> viewResultList;

    @FindBy(xpath = "//div[@class='form-group']/p")
    private WebElement userName;

    public PlaceholderDifViewPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getViewResultList() {
        return viewResultList;
    }

    public WebElement getUserName() {
        return userName;
    }
}


