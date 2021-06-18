package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BoardPage extends MainPage {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(xpath = "//a[@href='index.php?action=action_list&list_type=table&entity_id=31']")
    private WebElement listButton;

    public BoardPage(WebDriver driver) {
        super(driver);
    }

    public BoardEditPage clickNewButton() {
        newButton.click();

        return new BoardEditPage(getDriver());
    }

    public BoardListPage clickListButton() {
        listButton.click();

        return new BoardListPage(getDriver());
    }
}
