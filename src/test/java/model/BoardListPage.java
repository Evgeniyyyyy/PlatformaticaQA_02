package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoardListPage extends MainPage {

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;


    @FindBy(xpath = "//td[@class='pa-list-table-th']")
    private List<WebElement> recordsTable;

    @FindBy(xpath = "//tbody/tr/td/i")
    private WebElement icon;

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsButton;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement actionsViewButton;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement actionsEditButton;

    @FindBy(xpath = "//a[text()='delete']")
    private WebElement actionsDeleteButton;

    @FindBy(xpath = "//a/span[@class='notification']")
    private WebElement notificationRecycleBinIcon;

    @FindBy(xpath = "//input[@placeholder = 'Search']")
    private WebElement inputSearch;

    @FindBy(xpath = "//th[@data-field='text']/div")
    private static WebElement textColumn;

    @FindBy(className = "pagination-info")
    private WebElement paginationInfo;

    public BoardListPage(WebDriver driver) {
        super(driver);
    }

    public boolean isTableEmpty() {
        return Strings.isStringEmpty(table.getText());
    }

    public List<String> getRow(int number) {
        return rows.get(number).findElements(By.className("pa-list-table-th"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public int getRowCount() {
        if (isTableEmpty()) {
            return 0;
        } else {
            return rows.size();
        }
    }

    public String getIcon() {
        return icon.getAttribute("class");
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

    public String getTextNotificationRecycleBin(){

        return notificationRecycleBinIcon.getText();
    }

    public BoardListPage searchInputValue(String value) {
        inputSearch.clear();
        inputSearch.sendKeys(value);

        return this;
    }

    public BoardListPage clickTextColumn() {
        textColumn.click();

        return new BoardListPage(getDriver());
    }

    public  List<String> getActualValues(List<WebElement> actualElements) {
        List<String> listValues = new ArrayList<>();
        for (WebElement element : actualElements) {
            listValues.add(element.getText());
        }
        return listValues;
    }

    public List<String> getListRecordsTable(){
        return getActualValues(recordsTable);
    }

    public BoardListPage getTextPaginationInfo(String value) {
        getWait().until(ExpectedConditions.textToBePresentInElement(paginationInfo, value));
        return this;
    }
}
