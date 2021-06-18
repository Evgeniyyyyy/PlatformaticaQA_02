package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestUtils;

public class MainPage extends BaseModel {

    @FindBy(xpath = "//p[text()= ' Fields ']")
    private WebElement fieldsMenuItem;

    @FindBy(xpath = "//p[text()= ' Readonly ']")
    private WebElement readonlyMenuItem;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public FieldsPage clickFieldsMenu() {
        TestUtils.jsClick(getDriver(), fieldsMenuItem);

        return new FieldsPage(getDriver());
    }

    public ReadonlyPage clickReadonlyMenu(){
        TestUtils.jsClick(getDriver(), readonlyMenuItem);

        return new ReadonlyPage(getDriver());
    }
}
