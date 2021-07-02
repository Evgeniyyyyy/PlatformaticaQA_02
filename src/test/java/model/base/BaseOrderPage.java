package model.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public abstract class BaseOrderPage<MasterPage extends BaseMasterPage> extends BaseListMasterPage {

    @FindBy(xpath = "//tbody/tr")
    private WebElement row;

    public BaseOrderPage(WebDriver driver) {
        super(driver);
    }

    protected abstract MasterPage createMasterPage();

    protected Actions actions = new Actions(getDriver());

    protected MasterPage swapRecords(Integer vertical) {
        actions.moveToElement(row)
                .clickAndHold(row)
                .dragAndDropBy(row, 0, vertical)
                .build()
                .perform();

        return createMasterPage();
    }

    public MasterPage movingRecord() {
        swapRecords(20);

        return createMasterPage();
    }

    public MasterPage movingBlockRecord() {
        swapRecords(140);

        return createMasterPage();
    }
}
