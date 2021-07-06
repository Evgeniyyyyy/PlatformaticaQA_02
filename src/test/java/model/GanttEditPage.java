package model;

import org.openqa.selenium.WebDriver;

public class GanttEditPage extends GanttBaseEditPage<GanttPage, GanttEditPage> {

    public GanttEditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected GanttPage createMasterPage() {
        return new GanttPage(getDriver());
    }
}
