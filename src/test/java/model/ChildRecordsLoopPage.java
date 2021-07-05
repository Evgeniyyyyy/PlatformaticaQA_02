package model;

import model.base.BaseListMasterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ChildRecordsLoopPage extends BaseListMasterPage<ChildRecordsLoopEditPage, ChildRecordsLoopViewPage> {

    @FindBy(xpath = "//tbody/tr/td[@class='pa-list-table-th']")
    private List<WebElement> rows;

    @FindBy(className = "pagination-info")
    private WebElement paginationInfo;

    public ChildRecordsLoopPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ChildRecordsLoopEditPage createEditPage() {
        return new ChildRecordsLoopEditPage(getDriver());
    }

    @Override
    protected ChildRecordsLoopViewPage createViewPage() {
        return new ChildRecordsLoopViewPage(getDriver());
    }

    public ChildRecordsLoopPage getTextPaginationInfo(String value) {
        getWait().until(ExpectedConditions.textToBePresentInElement(paginationInfo, value));

        return new ChildRecordsLoopPage(getDriver());
    }
}
