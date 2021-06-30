package model;

import org.openqa.selenium.WebDriver;

public class BoardListEditPage extends BoardBaseEditPage<BoardListPage, BoardListEditPage> {

    public BoardListEditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected BoardListPage createMasterPage() {
        return new BoardListPage(getDriver());
    }
}
