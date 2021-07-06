package model;

import org.openqa.selenium.WebDriver;

public class GanttListEditPage extends GanttBaseEditPage<GanttListPage, GanttListEditPage>{

    public GanttListEditPage(WebDriver driver) { super(driver); }

    @Override
    protected GanttListPage createMasterPage() {
        return new GanttListPage(getDriver());
    }
}
