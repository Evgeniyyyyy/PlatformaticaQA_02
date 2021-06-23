package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
}
