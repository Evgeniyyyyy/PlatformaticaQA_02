package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceholderDifPage extends MainPage {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(xpath = "//tbody/tr/td[1]/i")
    private WebElement iconPencil;

    @FindBy(xpath = "//tbody/tr/td[@class = 'pa-list-table-th']")
    private static List<WebElement> resultColumns;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(xpath = "//tbody/tr")
    private WebElement row;

    @FindBy(xpath = "//div[1]/div[1]/ul[1]/li[1]/a[1]")
    private WebElement buttonList;

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsMenu;

    @FindBy(xpath = "//a[contains(text(),'edit')]")
    private WebElement buttonEdit;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement buttonView;

    @FindBy(xpath = "//a[contains (text(), 'delete')]")
    private WebElement buttonDelete;

    @FindBy(xpath = "//i[contains(text(),'delete_outline')]")
    private WebElement iconRecycleBin;

    @FindBy(xpath = "//span[@class='notification']/b[1]")
    private WebElement recycleBinCountNotification;

    @FindBy(xpath = "//i[contains(text(),'delete_outline')]")
    private WebElement notificationEmpty;


    public PlaceholderDifPage(WebDriver driver) {
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

    public String getClassIcon() {

        return iconPencil.getAttribute("class");
    }

    public PlaceholderDifEditPage clickNewButton() {
        newButton.click();

        return new PlaceholderDifEditPage(getDriver());
    }

    public PlaceholderDifPage clickButtonList() {
        TestUtils.jsClick(getDriver(), buttonList);

        return new PlaceholderDifPage(getDriver());
    }

    public PlaceholderDifPage clickActionsMenu() {
        TestUtils.jsClick(getDriver(), actionsMenu);

        return new PlaceholderDifPage(getDriver());
    }

    public PlaceholderDifEditPage clickButtonEdit() {
        TestUtils.jsClick(getDriver(), buttonEdit);

        return new PlaceholderDifEditPage(getDriver());
    }

    public PlaceholderDifViewPage clickButtonView() {
        TestUtils.jsClick(getDriver(), buttonView);

        return new PlaceholderDifViewPage(getDriver());
    }

    public PlaceholderDifPage clickButtonDelete() {
        TestUtils.jsClick(getDriver(), buttonDelete);

        return new PlaceholderDifPage(getDriver());
    }

    public RecycleBinPage clickIconRecycleBin() {
        iconRecycleBin.click();

        return new RecycleBinPage(getDriver());
    }

    public List<WebElement> getPlaceholderTableColumns() {
        return resultColumns;
    }

    public String getRecycleBinCountNotification() {
        return recycleBinCountNotification.getText();
    }

    public PlaceholderDifPage deletePlaceholderRecord() {
        return this
                .clickPlaceholderDifMenu()
                .clickButtonList()
                .clickActionsMenu()
                .clickButtonDelete();
    }

}
