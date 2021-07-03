package model;

import model.base.BaseMasterPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BoardPage extends BaseMasterPage<BoardEditPage> {

    @FindBy(xpath = "//a[@href='index.php?action=action_list&list_type=table&entity_id=31']")
    private WebElement listButton;

    @FindBy(xpath = "//i[text()='format_line_spacing']")
    private WebElement orderButton;

    @FindBy(xpath = "//div[@class='kanban-item']")
    private List<WebElement> colomn;

    @FindBy(className = "kanban-drag")
    private List<WebElement> boxes;

    public BoardPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected BoardEditPage createEditPage() {
        return new BoardEditPage(getDriver());
    }

    public BoardListPage clickListButton() {
        listButton.click();

        return new BoardListPage(getDriver());
    }

    public BoardOrderPage clickOrderButton() {
        orderButton.click();

        return new BoardOrderPage(getDriver());
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

    public BoardPage movePendingToOnTrack(){
        moveRecord(colomn.get(0), 200,0);

        return this;
    }

    public BoardPage moveOnTrackToPending(){
        moveRecord(colomn.get(1), -200,0);

        return this;
    }

    public boolean isEmptyPage() {
        return boxes.stream().filter(e -> !"".equals(e.getText())).findFirst().isEmpty();
    }
}
