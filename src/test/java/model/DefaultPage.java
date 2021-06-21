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

public class DefaultPage extends MainPage{
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

    @FindBy(id = "customId_0")
    private WebElement card;

    @FindBy(xpath = "//input[@placeholder = 'Search']")
    private WebElement input;

    @FindBy(className = "pagination-info")
    private static WebElement paginationInfo;

    public DefaultPage(WebDriver driver) {
        super(driver);
    }

    public DefaultEditPage clickNewButton() {
        newButton.click();

        return new DefaultEditPage(getDriver());
    }

    public static boolean isTableEmpty() {
        return Strings.isStringEmpty(table.getText());
    }

    public static List<String> getRow(int number) {
        return rows.get(number).findElements(By.className("pa-list-table-th"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public static int getRowCount() {
        if (isTableEmpty()) {
            return 0;
        } else {
            return rows.size();
        }
    }

    public static String getClassIcon() {
        return icon.getAttribute("class");
    }

    public RecycleBinPage clickRecycleBin(){
        recycleBinIcon.click();

        return new RecycleBinPage(getDriver());
    }

    public DefaultPage clickActions() {
        actionsButton.click();

        return new DefaultPage(getDriver());
    }

    public DefaultViewPage clickActionsView() {
        getWait().until(TestUtils.movingIsFinished(actionsViewButton));
        actionsViewButton.click();

        return new DefaultViewPage(getDriver());
    }

    public DefaultEditPage clickActionsEdit() {
        getWait().until(TestUtils.movingIsFinished(actionsEditButton));
        actionsEditButton.click();

        return new DefaultEditPage(getDriver());
    }

    public DefaultPage clickActionsDelete() {
        getWait().until(TestUtils.movingIsFinished(actionsDeleteButton));
        actionsDeleteButton.click();

        return new DefaultPage(getDriver());
    }

    public static int getTextNotificationRecycleBin(){

        return Integer.parseInt(notificationRecycleBinIcon.getText());
    }

    public DefaultPage clickListButton() {
        listButton.click();

        return new DefaultPage(getDriver());
    }

    public DefaultPage clickOrderButton() {
        order.click();

        return new DefaultPage(getDriver());
    }

    Actions actions = new Actions(getDriver());

    public DefaultPage getReorder() {
        actions.moveToElement(row)
               .clickAndHold(row)
               .dragAndDropBy(row, 0, 20)
               .build()
               .perform();

        return new DefaultPage(getDriver());
    }

    public DefaultPage clickToggle() {
        toggle.click();

        return this;
    }

    public DefaultPage getNewReorder() {
        actions.moveToElement(card)
               .clickAndHold(card)
               .dragAndDropBy(card, 0, 140)
               .build()
               .perform();

        return this;
    }

    public static List<String> getRows(int number) {
        return rows.get(number).findElements(By.className("card-view-value"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public DefaultPage searchInput(String value) {
        input.clear();
        input.sendKeys(value);

        return this;
    }

    public DefaultPage getTextPaginationInfo(String value) {
        getWait().until(ExpectedConditions.textToBePresentInElement(paginationInfo, value));
        return this;
    }
}
