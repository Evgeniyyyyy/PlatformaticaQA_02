package model.base;

import model.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseMasterPage<EditPage extends BaseEditPage> extends MainPage {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    public BaseMasterPage(WebDriver driver) {
        super(driver);
    }

    protected abstract EditPage createEditPage();

    public EditPage clickNewButton() {
        newButton.click();

        return createEditPage();
    }
}
