package model;

import model.base.BaseMasterPage;

import org.openqa.selenium.By;
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

    protected Actions actions = new Actions(getDriver());

    public BoardPage moveElement(String from, String to){
        WebElement element = getDriver().findElement(By.xpath(String.format("//div[@data-id='%s']/main/div", from)));
        WebElement toPlace = getDriver().findElement(By.xpath(String.format("//div[@data-id='%s']/main", to)));

        actions
                .dragAndDrop(element, toPlace)
                .build()
                .perform();

        return this;
    }

    public boolean isEmptyPage() {
        return boxes.stream().filter(e -> !"".equals(e.getText())).findFirst().isEmpty();
    }
}
