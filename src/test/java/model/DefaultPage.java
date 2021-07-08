package model;

import model.base.BaseListMasterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class DefaultPage extends BaseListMasterPage<DefaultEditPage, DefaultViewPage> {

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsButton;

    @FindBy(xpath = "//a[text()='delete']")
    private WebElement actionsDeleteButton;

    @FindBy(xpath = "//a[contains(@href, 'id=7')]/i[text()='list']")
    private WebElement listButton;

    @FindBy(xpath = "//a[contains(@href, 'id=7')]/span/i[text()='format_line_spacing']")
    private WebElement orderButton;

    @FindBy(xpath = "//i[text()='delete_outline']")
    private WebElement recycleBinIcon;

    @FindBy(xpath = "//i[text()='format_line_spacing']")
    private WebElement order;

    @FindBy(xpath = "//i[contains(@class,'fa fa-toggle')]")
    private WebElement toggle;

    @FindBy(xpath = "//input[@placeholder = 'Search']")
    private WebElement input;

    @FindBy(xpath = "//th[@data-field='string']/div")
    private WebElement stringColumn;

    @FindBy(xpath = "//th[@data-field='text']/div")
    private WebElement textColumn;

    @FindBy(xpath = "//th[@data-field='int']/div")
    private WebElement intColumn;

    @FindBy(xpath = "//th[@data-field='decimal']/div")
    private WebElement decimalColumn;

    public DefaultPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected DefaultEditPage createEditPage() {
        return new DefaultEditPage(getDriver());
    }

    public DefaultPage clickActions() {
        actionsButton.click();

        return new DefaultPage(getDriver());
    }

    public DefaultPage clickActionsDelete() {
        getWait().until(TestUtils.movingIsFinished(actionsDeleteButton));
        actionsDeleteButton.click();

        return new DefaultPage(getDriver());
    }

    @Override
    protected DefaultViewPage createViewPage() {
        return new DefaultViewPage(getDriver());
    }

    public DefaultPage clickListButton() {
        listButton.click();

        return new DefaultPage(getDriver());
    }

    public DefaultPage clickStringColumn() {
        stringColumn.click();

        return new DefaultPage(getDriver());
    }

    public DefaultPage clickTextColumn() {
        textColumn.click();

        return new DefaultPage(getDriver());
    }

    public DefaultPage clickIntColumn() {
        intColumn.click();

        return new DefaultPage(getDriver());
    }

    public DefaultPage clickDecimalColumn() {
        decimalColumn.click();

        return new DefaultPage(getDriver());
    }

    public DefaultPage clickOrderButton() {
        order.click();

        return new DefaultPage(getDriver());
    }

    public DefaultPage clickToggle() {
        toggle.click();

        return this;
    }

    public DefaultPage searchInput(String value) {
        input.clear();
        input.sendKeys(value);

        return this;
    }
}
