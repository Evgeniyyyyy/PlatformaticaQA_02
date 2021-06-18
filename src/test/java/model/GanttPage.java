package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GanttPage extends MainPage {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(xpath = "//a[@href=\"index.php?action=action_list&list_type=table&entity_id=35\"]")
    private WebElement listButton;

    public GanttPage(WebDriver driver) {
        super(driver);
    }

    public GanttEditPage clickNewButton() {
        newButton.click();

        return new GanttEditPage(getDriver());
    }

    public GanttListPage clickListButton() {
        listButton.click();

        return new GanttListPage(getDriver());
    }
}
