package model;

import model.base.BaseListMasterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

public class FieldsPage extends BaseListMasterPage<FieldsEditPage, FieldsViewPage> {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//div[@class='dropdown pull-left']")
    private WebElement actionMenu;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement edit;

    @FindBy(xpath = "//i[text()='format_line_spacing']")
    private WebElement order;

    @FindBy(id = "customId_0")
    private WebElement card;

    @FindBy(xpath = "//input[@placeholder = 'Search']")
    private WebElement input;

    @FindBy(xpath = "//span[@class='pagination-info']")
    private WebElement text;

    @FindBy(xpath = "//tbody/tr/td/i")
    private static WebElement icon;

    @FindBy(xpath = "//*[contains(text(), 'besttextever')]/../../td[11]/div/button")
    private WebElement exactViewButton;

    @FindBy(xpath = "//div[text()='Comments']")
    private WebElement titleSort;

    public FieldsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FieldsEditPage createEditPage() {
        return new FieldsEditPage(getDriver());
    }

    @Override
    protected FieldsViewPage createViewPage() {
        return new FieldsViewPage(getDriver());
    }

    public FieldsEditPage clickEdit() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), edit);

        return new FieldsEditPage(getDriver());
    }

    public FieldsOrderPage clickOrder() {
        order.click();

        return new FieldsOrderPage(getDriver());
    }

    public FieldsPage searchInput(String value) {
        input.sendKeys(value);

        return this;
    }

    public FieldsPage findInfoText(String value) {
        getWait().until(ExpectedConditions.textToBePresentInElement(text, value));

        return this;
    }

    public FieldsPage clickExactViewRecord() {
        TestUtils.jsClick(getDriver(), exactViewButton);

        getWait().until(ExpectedConditions.elementToBeClickable(
                getDriver().findElement(By.xpath(
                        "//*[contains(text(), 'besttextever')]/../../td[11]/div/ul/li[1]/a"))));

        getDriver().findElement(By.xpath(
                "//*[contains(text(), 'besttextever')]/../../td[11]/div/ul/li[1]/a")).click();

        return new FieldsPage(getDriver());
    }

    public FieldsPage clickSort() {
        titleSort.click();

        return new FieldsPage(getDriver());
    }
}
