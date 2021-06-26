package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

public class FieldsPage extends BasePage {

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

    @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
    private WebElement toggle;

    @FindBy(id = "customId_0")
    private WebElement card;

    @FindBy(xpath = "//input[@placeholder = 'Search']")
    private WebElement input;

    @FindBy(xpath = "//span[@class='pagination-info']")
    private WebElement text;

    @FindBy(xpath = "//tbody/tr/td/i")
    private static WebElement icon;

    public FieldsPage(WebDriver driver) {
        super(driver);
    }

    public FieldsEditPage clickNewButton() {
        newButton.click();

        return new FieldsEditPage(getDriver());
    }

    public FieldsEditPage clickEdit() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), edit);

        return new FieldsEditPage(getDriver());
    }

    public FieldsPage clickOrder() {
        order.click();

        return new FieldsPage(getDriver());
    }

    public FieldsPage clickToggle() {
        toggle.click();

        return this;
    }

    public FieldsPage searchInput(String value) {
        input.sendKeys(value);

        return this;
    }

    public FieldsPage findInfoText(String value) {
        getWait().until(ExpectedConditions.textToBePresentInElement(text, value));

        return this;
    }
}
