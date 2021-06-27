package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.ProjectUtils;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BoardListPage extends BasePage {

    @FindBy(xpath = "//td[@class='pa-list-table-th']")
    private List<WebElement> recordsTable;

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsButton;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement actionsViewButton;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement actionsEditButton;

    @FindBy(xpath = "//a[text()='delete']")
    private WebElement actionsDeleteButton;

    @FindBy(xpath = "//input[@placeholder = 'Search']")
    private WebElement inputSearch;

    @FindBy(xpath = "//th[@data-field='text']/div")
    private WebElement textColumn;

    @FindBy(className = "pagination-info")
    private WebElement paginationInfo;

    @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
    private WebElement toggle;

    public BoardListPage(WebDriver driver) {
        super(driver);
    }

    public BoardListPage clickActions() {
        actionsButton.click();

        return new BoardListPage(getDriver());
    }

    public BoardViewPage clickActionsView() {
        getWait().until(TestUtils.movingIsFinished(actionsViewButton));
        actionsViewButton.click();

        return new BoardViewPage(getDriver());
    }

    public BoardEditPage clickActionsEdit() {
        getWait().until(TestUtils.movingIsFinished(actionsEditButton));
        actionsEditButton.click();

        return new BoardEditPage(getDriver());
    }

    public BoardListPage clickActionsDelete() {
        getWait().until(TestUtils.movingIsFinished(actionsDeleteButton));
        actionsDeleteButton.click();

        return new BoardListPage(getDriver());
    }

    public BoardListPage searchInputValue(String value) {
        inputSearch.clear();
        inputSearch.sendKeys(value);

        return this;
    }

    public void clickTextColumn() {
        textColumn.click();

        new BoardListPage(getDriver());
    }

    public List<String> getListRecordsTable(){
        return ProjectUtils.getActualValues(recordsTable);
    }

    public BoardListPage getTextPaginationInfo(String value) {
        getWait().until(ExpectedConditions.textToBePresentInElement(paginationInfo, value));
        return this;
    }

    public BoardListPage clickToggle(){
        toggle.click();

        return this;
    }
}
