package model;

import model.base.BaseListMasterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class TagPage extends BaseListMasterPage<TagEditPage, TagViewPage> {

    @FindBy(xpath = "//div[@class='dropdown pull-left']")
    private WebElement actionMenu;

    @FindBy(xpath = "//a[text()='delete']")
    private WebElement delete;

    public TagPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected TagViewPage createViewPage() {
        return new TagViewPage(getDriver());
    }

    @Override
    protected TagEditPage createEditPage() {
        return new TagEditPage(getDriver());
    }

    public TagPage clickDelete() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), delete);

        return new TagPage(getDriver());
    }
}
