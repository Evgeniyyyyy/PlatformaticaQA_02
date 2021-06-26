package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class TagPage extends BasePage{

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(xpath = "//div[@class='dropdown pull-left']")
    private WebElement actionMenu;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement edit;

    @FindBy(xpath = "//a[text()='delete']")
    private WebElement delete;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement view;

    public TagPage(WebDriver driver) {
        super(driver);
    }

    public TagEditPage clickNewButton() {
        newButton.click();

        return new TagEditPage(getDriver());
    }

    public TagEditPage clickEdit() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), edit);

        return new TagEditPage(getDriver());
    }

    public TagPage clickDelete() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), delete);

        return new TagPage(getDriver());
    }

    public TagViewPage clickView() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), view);

        return new TagViewPage(getDriver());
    }
}
