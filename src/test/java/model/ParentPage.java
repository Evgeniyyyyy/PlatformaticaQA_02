package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ParentPage extends BasePage {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private static WebElement newButton;

    @FindBy(className = "card-body")
    private static WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private static List<WebElement> rows;

    @FindBy(xpath = "//tbody/tr")
    private WebElement row;

    @FindBy(xpath = "//tbody/tr/td/i")
    private static WebElement icon;

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsButton;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement actionsViewButton;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement actionsEditButton;

    @FindBy(xpath = "//a[text()='delete']")
    private WebElement actionsDeleteButton;

    @FindBy(xpath = "//a/span[@class='notification']")
    private static WebElement notificationRecycleBinIcon;

    @FindBy(xpath = "//a[contains(@href, 'id=57')]/i[text()='list']")
    private WebElement listButton;

    @FindBy(xpath = "//a[contains(@href, 'id=57')]/span/i[text()='format_line_spacing']")
    private WebElement orderButton;

    @FindBy(xpath = "//i[text()='delete_outline']")
    private WebElement recycleBinIcon;

    @FindBy(xpath = "//i[text()='format_line_spacing']")
    private WebElement order;

    @FindBy(xpath = "//i[contains(@class,'fa fa-toggle')]")
    private WebElement toggle;

    @FindBy(id = "customId_0")
    private WebElement card;

    @FindBy(xpath = "//input[@placeholder = 'Search']")
    private WebElement input;

    @FindBy(className = "pagination-info")
    private static WebElement paginationInfo;

    public ParentPage(WebDriver driver) {
        super(driver);
    }

    public ParentEditPage clickNewButton() {
        newButton.click();

        return new ParentEditPage(getDriver());
    }

    public RecycleBinPage clickRecycleBin(){
        recycleBinIcon.click();

        return new RecycleBinPage(getDriver());
    }

    public ParentPage clickActions() {
        actionsButton.click();

        return new ParentPage(getDriver());
    }

    public ParentViewPage clickActionsView() {
        getWait().until(TestUtils.movingIsFinished(actionsViewButton));
        actionsViewButton.click();

        return new ParentViewPage(getDriver());
    }

    public ParentEditPage clickActionsEdit() {
        getWait().until(TestUtils.movingIsFinished(actionsEditButton));
        actionsEditButton.click();

        return new ParentEditPage(getDriver());
    }

    public ParentPage clickActionsDelete() {
        getWait().until(TestUtils.movingIsFinished(actionsDeleteButton));
        actionsDeleteButton.click();

        return new ParentPage(getDriver());
    }

    public ParentPage clickListButton() {
        listButton.click();

        return new ParentPage(getDriver());
    }

    public ParentPage clickOrder() {
        order.click();

        return new ParentPage(getDriver());
    }

    Actions actions = new Actions(getDriver());

    public ParentPage getReorder() {
        actions.moveToElement(row)
               .clickAndHold(row)
               .dragAndDropBy(row, 0, 20)
               .build()
               .perform();

        return new ParentPage(getDriver());
    }

    public ParentPage clickToggle() {
        toggle.click();

        return this;
    }

    public ParentPage getNewReorder() {
        actions.moveToElement(card)
               .clickAndHold(card)
               .dragAndDropBy(card, 0, 140)
               .build()
               .perform();

        return this;
    }

    public List<String> getRows(int number) {
        return rows.get(number).findElements(By.className("card-view-value"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public ParentPage searchInput(String value) {
        input.clear();
        input.sendKeys(value);

        return this;
    }

    public ParentPage getTextPaginationInfo(String value) {
        getWait().until(ExpectedConditions.textToBePresentInElement(paginationInfo, value));
        return this;
    }
}
