package model;

import model.base.BaseOrderPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BoardOrderPage extends BaseOrderPage<BoardOrderPage> {

    @FindBy(xpath = "//i[@class='fa fa-toggle-off']")
    private WebElement toggle;

    public BoardOrderPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected BoardEditPage createEditPage() {
        return new BoardEditPage(getDriver());
    }

    @Override
    protected BoardViewPage createViewPage() {
        return new BoardViewPage(getDriver());
    }

    @Override
    protected BoardOrderPage createMasterPage() {
        return new BoardOrderPage(getDriver());
    }

    public BoardOrderPage clickToggleOrder() {
        toggle.click();

        return new BoardOrderPage(getDriver());
    }
}
