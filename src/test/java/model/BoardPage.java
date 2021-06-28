package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BoardPage extends BasePage {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(xpath = "//a[@href='index.php?action=action_list&list_type=table&entity_id=31']")
    private WebElement listButton;

    @FindBy(xpath = "//i[text()='format_line_spacing']")
    private WebElement orderButton;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//div[@class='kanban-item']")
    private List<WebElement> colomn;

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

    public BoardListPage clickOrderButton() {
        orderButton.click();

        return new BoardListPage(getDriver());
    }

    public String getTextCardBody(){

        return table.getText();
    }

    public String getTextPendingRecord(){

        return colomn.get(0).getText();
    }

    public String getTextOnTrackRecord(){

        return colomn.get(1).getText();
    }

    protected Actions actions = new Actions(getDriver());

    public void moveRecord(WebElement element, Integer gorizont, Integer vertical) {
        actions.moveToElement(element)
                .clickAndHold(element)
                .dragAndDropBy(element, gorizont, vertical)
                .build()
                .perform();
    }

    public void movePendingToOnTrack(){
        moveRecord(colomn.get(0), 200,0);
    }

    public void moveOnTrackToPending(){
        moveRecord(colomn.get(1), -200,0);
    }
}
