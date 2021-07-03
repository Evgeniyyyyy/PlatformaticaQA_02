package model;

import com.beust.jcommander.Strings;
import model.base.BaseListMasterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class AssignPage extends BaseListMasterPage<AssignEditPage, AssignViewPage> {

    public AssignPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//tbody/tr")
    private static List<WebElement> rows;

    @FindBy(xpath = "//tbody/tr")
    private WebElement row;

    @FindBy(xpath = "//tbody/tr/td/i")
    private static WebElement icon;

    @FindBy(xpath = "//input[@placeholder = 'Search']")
    private WebElement input;

    @FindBy(id = "customId_0")
    private WebElement card;

    @FindBy(xpath = "//a[contains(@href, 'id=37')]/i[text()='list']")
    private WebElement listButton;

    @FindBy(xpath = "//a[contains(@href, 'id=37')]/span/i[text()='format_line_spacing']")
    private WebElement orderButton;

    @FindBy(xpath = "//i[text()='delete_outline']")
    private WebElement recycleBinIcon;

    @FindBy(xpath = "//i[contains(@class,'fa fa-toggle')]")
    private WebElement toggle;

    @FindBy(className = "pagination-info")
    private static WebElement paginationInfo;

    @FindBy(xpath = "//div[@class='dropdown pull-left']")
    private WebElement actionMenu;

    @FindBy(xpath = "//a[text()='delete']")
    private WebElement delete;

    @Override
    protected AssignViewPage createViewPage() {
        return new AssignViewPage(getDriver());
    }

    @Override
    protected AssignEditPage createEditPage() {
        return new AssignEditPage(getDriver());
    }

    public List<String> getRows(int number) {
        return rows.get(number).findElements(By.className("card-view-value"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public AssignPage searchInput(String value) {
        input.clear();
        input.sendKeys(value);

        return this;
    }

    Actions actions = new Actions(getDriver());

    public AssignPage getReorder() {
        actions.moveToElement(row)
                .clickAndHold(row)
                .dragAndDropBy(row, 0, 20)
                .build()
                .perform();

        return new AssignPage(getDriver());
    }

    public AssignPage getNewReorder() {
        actions.moveToElement(card)
                .clickAndHold(card)
                .dragAndDropBy(card, 0, 140)
                .build()
                .perform();

        return this;
    }

    public AssignPage clickToggle() {
        toggle.click();

        return this;
    }

    public AssignPage clickOrderButton() {
        orderButton.click();

        return new AssignPage(getDriver());
    }

    public AssignPage clickListButton() {
        listButton.click();

        return new AssignPage(getDriver());
    }

    public AssignPage getTextPaginationInfo(String value) {
        getWait().until(ExpectedConditions.textToBePresentInElement(paginationInfo, value));
        return this;
    }

    public AssignPage clickDelete() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), delete);

        return new AssignPage(getDriver());
    }
}
