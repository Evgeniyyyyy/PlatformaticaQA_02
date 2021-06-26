package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BoardViewPage extends BaseViewPage<BoardListPage> {

    public BoardViewPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span [@class = 'pa-view-field']")
    private List<WebElement> viewModeRecord;

    @Override
    protected BoardListPage createMasterPage(){
        return new BoardListPage(getDriver());
    }
}
