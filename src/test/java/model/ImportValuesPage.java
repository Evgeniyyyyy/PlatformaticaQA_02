package model;

import model.base.BaseListMasterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

public class ImportValuesPage extends BaseListMasterPage<ImportValuesEditPage, ImportValuesViewPage> {

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsButton;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement actionsViewButton;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement actionsEditButton;

    @FindBy(xpath = "//a[text()='delete']")
    private WebElement actionsDeleteButton;

    @FindBy(xpath = "//ul[@class='pa-nav-pills-small nav nav-pills nav-pills-primary']/li/a/i[text()='list']")
    private WebElement listButton;

    @FindBy(xpath = "//i[text()='format_line_spacing']")
    private WebElement orderButton;

    @FindBy(xpath = "//input[@placeholder = 'Search']")
    private WebElement input;

    @FindBy(xpath = "//span[@class='pagination-info']")
    private WebElement text;

    @FindBy(xpath = "//th[@data-field='text']/div")
    private WebElement sortText;

    public ImportValuesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ImportValuesViewPage createViewPage() {
        return new ImportValuesViewPage(getDriver());
    }

    @Override
    protected ImportValuesEditPage createEditPage() {
        return new ImportValuesEditPage(getDriver());
    }

    public ImportValuesPage clickActions() {
        actionsButton.click();

        return new ImportValuesPage(getDriver());
    }

    public ImportValuesViewPage clickActionsView() {
        getWait().until(TestUtils.movingIsFinished(actionsViewButton));
        actionsViewButton.click();

        return new ImportValuesViewPage(getDriver());
    }

    public ImportValuesEditPage clickActionsEdit() {
        actionsButton.click();
        TestUtils.jsClick(getDriver(), actionsEditButton);

        return new ImportValuesEditPage(getDriver());
    }

    public ImportValuesPage clickActionsDelete() {
        getWait().until(TestUtils.movingIsFinished(actionsDeleteButton));
        actionsDeleteButton.click();

        return new ImportValuesPage(getDriver());
    }

    public ImportValuesPage clickListButton() {
    listButton.click();

    return new ImportValuesPage(getDriver());
}

    public ImportValuesOrderPage clickOrderButton() {
        orderButton.click();

        return new ImportValuesOrderPage(getDriver());
    }

    public ImportValuesPage searchInput(String value) {
        input.sendKeys(value);

        return this;
    }

    public ImportValuesPage getPaginationInfo(String value) {
        getWait().until(ExpectedConditions.textToBePresentInElement(text, value));

        return this;
    }

    public void clickSortText() {
        sortText.click();
    }
}
