package model;

        import com.beust.jcommander.Strings;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.support.FindBy;
        import utils.TestUtils;

        import java.util.List;
        import java.util.stream.Collectors;

public class CalendarPage extends MainPage {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(xpath = "//a[contains(@href, 'action_list&list_type=table&entity_id=32')]")
    private WebElement listButton;

    @FindBy(xpath = "//a[contains(@href, 'index.php?action=action_list&list_type=table&entity_id=32&draggable=1')]")
    private WebElement orderButton;

    @FindBy(xpath = "//*[text()='month']")
    private WebElement monthViewButton;

    @FindBy(xpath = "//*[text()='week']")
    private WebElement weekViewButton;

    @FindBy(xpath = "//*[text()='day']")
    private WebElement dayViewButton;

    @FindBy(xpath = "//span[contains(text(), 'StringExample')]")
    private WebElement record;


    public CalendarPage viewRecord (){
        TestUtils.jsClick(getDriver(), record);

        return this;
    }

    public CalendarPage dayView (){
        TestUtils.jsClick(getDriver(), weekViewButton);

        return this;
    }

    public CalendarPage weekView (){
        TestUtils.jsClick(getDriver(), weekViewButton);

        return this;
    }

    public CalendarPage monthView (){
        TestUtils.jsClick(getDriver(), monthViewButton);

        return this;
    }

    public CalendarPage(WebDriver driver) {
        super(driver);
    }

    public CalendarEditPage clickNewButton() {
        TestUtils.jsClick(getDriver(), newButton);

        return new CalendarEditPage(getDriver());
    }

    public CalendarListPage listView(){
        TestUtils.jsClick(getDriver(), listButton);

        return new CalendarListPage(getDriver());
    }

    public CalendarOrderPage orderView(){
        TestUtils.jsClick(getDriver(), orderButton);

        return new CalendarOrderPage(getDriver());
    }

    public boolean isTableEmpty() {
        return Strings.isStringEmpty(table.getText());
    }

    public List<String> getRow(int number) {
        return rows.get(number).findElements(By.className("pa-list-table-th"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public int getRowCount() {
        if (isTableEmpty()) {
            return 0;
        } else {
            return rows.size();
        }
    }
}
