package model;

import model.base.BaseListMasterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

public class ExportDestinationPage extends BaseListMasterPage<ExportDestinationEditPage, ExportDestinationViewPage> {

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

    @FindBy(xpath = "//div[text()='Text']")
    private WebElement sortText;

    public ExportDestinationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ExportDestinationViewPage createViewPage() {
        return new ExportDestinationViewPage(getDriver());
    }

    @Override
    protected ExportDestinationEditPage createEditPage() {
        return new ExportDestinationEditPage(getDriver());
    }

    public ExportDestinationPage clickActions() {
        actionsButton.click();

        return new ExportDestinationPage(getDriver());
    }

    public ExportDestinationViewPage clickActionsView() {
        getWait().until(TestUtils.movingIsFinished(actionsViewButton));
        actionsViewButton.click();

        return new ExportDestinationViewPage(getDriver());
    }

    public ExportDestinationEditPage clickActionsEdit() {
        actionsButton.click();
        TestUtils.jsClick(getDriver(), actionsEditButton);

        return new ExportDestinationEditPage(getDriver());
    }

    public ExportDestinationPage clickActionsDelete() {
        getWait().until(TestUtils.movingIsFinished(actionsDeleteButton));
        actionsDeleteButton.click();

        return new ExportDestinationPage(getDriver());
    }

    public ExportDestinationPage clickListButton() {
        listButton.click();

        return new ExportDestinationPage(getDriver());
    }

    public ExportDestinationOrderPage clickOrderButton() {
        orderButton.click();

        return new ExportDestinationOrderPage(getDriver());
    }

    public ExportDestinationPage searchInput(String value) {
        input.sendKeys(value);

        return this;
    }

    public ExportDestinationPage findTextInfo(String value) {
        getWait().until(ExpectedConditions.textToBePresentInElement(text, value));

        return this;
    }

    public void clickSortText() {
        sortText.click();
    }
}
