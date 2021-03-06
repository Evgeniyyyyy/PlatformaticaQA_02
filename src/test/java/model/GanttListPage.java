package model;

import com.beust.jcommander.Strings;
import model.base.BaseListMasterPage;
import model.base.BaseModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class GanttListPage extends BaseListMasterPage<GanttEditPage, GanttViewPage> {

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(xpath = "//tbody/tr[1]/td[1]/i[1]")
    private WebElement icon;

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsButton;

    @FindBy(xpath = "//a[text()='view']")
    private WebElement actionsViewButton;

    @FindBy(xpath = "//a[text()='edit']")
    private WebElement actionsEditButton;

    public GanttListPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected GanttViewPage createViewPage() {
        return new GanttViewPage(getDriver());
    }

    @Override
    protected GanttEditPage createEditPage() {
        return new GanttEditPage(getDriver());
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

    public GanttListPage clickActions() {
        actionsButton.click();

        return new GanttListPage(getDriver());
    }

    public GanttViewPage clickActionsView() {
        getWait().until(TestUtils.movingIsFinished(actionsViewButton));
        actionsViewButton.click();

        return new GanttViewPage(getDriver());
    }

    public GanttEditPage clickActionsEdit() {
        getWait().until(TestUtils.movingIsFinished(actionsEditButton));
        actionsEditButton.click();

        return new GanttEditPage(getDriver());
    }
}
