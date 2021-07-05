package model;

import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;

public class ChildRecordsLoopViewPage extends BaseViewPage<ChildRecordsLoopPage> {

    public ChildRecordsLoopViewPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ChildRecordsLoopPage createMasterPage() {
        return new ChildRecordsLoopPage(getDriver());
    }
}
