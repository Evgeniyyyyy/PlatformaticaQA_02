package model.base;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseListMasterPage<EditPage extends BaseEditPage, ViewPage extends BaseViewPage> extends BaseMasterPage<EditPage> {

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(xpath = "//tbody/tr/td/i")
    private WebElement icon;

    @FindBy(xpath = "//a/span[@class='notification']")
    private WebElement notificationRecycleBinIcon;

    @FindBy(xpath = "//i[text()='delete_outline']")
    private WebElement recycleBinIcon;

    @FindBy(xpath = "//div[@class='dropdown pull-left']")
    private WebElement actionMenu;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement view;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement edit;

    @FindBy(xpath = "//a[text()='delete']")
    private WebElement delete;

    public BaseListMasterPage(WebDriver driver) {
        super(driver);
    }

    protected Actions actions = new Actions(getDriver());

    public boolean isTableEmpty() {
        return Strings.isStringEmpty(table.getText());
    }

    public List<String> getRow(int number) {
        return rows.get(number).findElements(By.className("pa-list-table-th"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getOrderToggleRow(int number) {
        return rows.get(number).findElements(By.className("card-view-value"))
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

    public int getTextNotificationRecycleBin(){

        return Integer.parseInt(notificationRecycleBinIcon.getText());
    }

    public void getReorder(Integer value) {
        actions.moveToElement(rows.get(0))
                .clickAndHold(rows.get(0))
                .dragAndDropBy(rows.get(0), 0, value)
                .build()
                .perform();
    }

    protected abstract ViewPage createViewPage();
    protected abstract EditPage createEditPage();

    public ViewPage clickViewButton(int rowNumber) {

        actionMenu.click();
        TestUtils.jsClick(getDriver(), view);

        return createViewPage();
    }

    public EditPage clickEditButton() {
        actionMenu.click();
        TestUtils.jsClick(getDriver(), edit);

        return createEditPage();
    }
}
