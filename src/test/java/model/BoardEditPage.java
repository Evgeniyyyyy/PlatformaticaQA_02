package model;

import model.base.BaseEditPage;
import model.base.BaseMasterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.TestUtils;

import java.util.List;

import static utils.TestUtils.jsClick;

public class BoardEditPage extends BoardBaseEditPage<BoardPage, BoardEditPage> {

    public BoardEditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected BoardPage createMasterPage() {
        return new BoardPage(getDriver());
    }
}
