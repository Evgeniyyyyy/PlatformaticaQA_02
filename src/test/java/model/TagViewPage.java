package model;

import model.base.BaseViewPage;
import org.openqa.selenium.WebDriver;

public class TagViewPage extends BaseViewPage<TagPage> {

    public TagViewPage(WebDriver driver) {super(driver);}

    @Override
    protected TagPage createMasterPage() {
        return new TagPage(getDriver());
    }
}
