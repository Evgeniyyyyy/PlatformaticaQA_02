package model;

import com.beust.jcommander.Strings;
import model.base.BaseListMasterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ImportValuesPage extends BaseListMasterPage<ImportValuesEditPage, ImportValuesViewPage> {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(xpath = "//tbody/tr/td[1]/i")
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
    private static WebElement recycleBinNotification;

    @FindBy(xpath = "//i[text()='delete_outline']")
    private WebElement recycleBinIcon;

    @FindBy(xpath = "//input[@placeholder = 'Search']")
    private WebElement input;

    @FindBy(xpath = "//span[@class='pagination-info']")
    private WebElement text;

    public ImportValuesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ImportValuesViewPage createViewPage() {
        return new ImportValuesViewPage(getDriver());
    }

    @Override
    protected ImportValuesEditPage createEditPage() {
        return new ImportValuesEditPage(getDriver());
    }

    public ImportValuesPage clickActions() {
        actionsButton.click();

        return new ImportValuesPage(getDriver());
    }

    public ImportValuesViewPage clickActionsView() {
        getWait().until(TestUtils.movingIsFinished(actionsViewButton));
        actionsViewButton.click();

        return new ImportValuesViewPage(getDriver());
    }

    public ImportValuesEditPage clickActionsEdit() {
        actionsButton.click();
        TestUtils.jsClick(getDriver(), actionsEditButton);

        return new ImportValuesEditPage(getDriver());
    }

    public ImportValuesPage clickActionsDelete() {
        getWait().until(TestUtils.movingIsFinished(actionsDeleteButton));
        actionsDeleteButton.click();

        return new ImportValuesPage(getDriver());
    }

    public int getTextNotificationRecycleBin() {

        return Integer.parseInt(recycleBinNotification.getText());
    }


    public ImportValuesPage searchInput(String value) {
        input.sendKeys(value);

        return this;
    }

    public ImportValuesPage findTextInfo(String value) {
        getWait().until(ExpectedConditions.textToBePresentInElement(text, value));

        return this;
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
        return icon.getAttribute("class");
    }
}

