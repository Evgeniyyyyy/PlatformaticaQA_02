package model;

import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;

public class FieldsViewPage extends BaseViewPage<FieldsPage> {

    public FieldsViewPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected FieldsPage createMasterPage() {
        return new FieldsPage(getDriver());
    }
}
