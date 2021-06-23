package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.TestUtils;

public class MainPage extends BaseModel {

    @FindBy(xpath = "//p[text()= ' Assign ']")
    private WebElement assignMenuItem;

    @FindBy(xpath = "//p[text()= ' Fields ']")
    private WebElement fieldsMenuItem;

    @FindBy(xpath = "//p[text()= ' Readonly ']")
    private WebElement readonlyMenuItem;

    @FindBy(xpath = "//p[text()= ' Calendar ']")
    private WebElement calendarMenuItem;

    @FindBy(xpath = "//p[text()= ' Gantt ']")
    private WebElement ganttMenuItem;

    @FindBy(xpath = "//p[text()= ' Board ']")
    private WebElement boardMenuItem;

    @FindBy(xpath = "//p[text()= ' Arithmetic Function ']")
    private WebElement arithmeticFunctionMenuItem;

    @FindBy(xpath = "//p[text()= ' Arithmetic Inline ']")
    private WebElement arithmeticInlineMenuItem;

    @FindBy(xpath = "//p[text()= ' Parent ']")
    private WebElement parentMenuItem;

    @FindBy(xpath = "//p[text()= ' Default ']")
    private WebElement defaultMenuItem;

    @FindBy(xpath = "//i[text()='delete_outline']")
    private WebElement recycleBinIcon;

    @FindBy(xpath = "//p[text()= ' Chevron ']")
    private WebElement chevronMenuItem;

    @FindBy(xpath = "//p[text()= ' Placeholder ']")
    private WebElement placeholderMenuItem;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public AssignPage clickAssignMenu(){
        TestUtils.jsClick(getDriver(), assignMenuItem);

        return new AssignPage(getDriver());
    }

    public CalendarPage clickCalendarMenu(){
        TestUtils.jsClick(getDriver(), calendarMenuItem);

        return new CalendarPage(getDriver());
    }

    public FieldsPage clickFieldsMenu() {
        TestUtils.jsClick(getDriver(), fieldsMenuItem);

        return new FieldsPage(getDriver());
    }

    public ReadonlyPage clickReadonlyMenu(){
        TestUtils.jsClick(getDriver(), readonlyMenuItem);

        return new ReadonlyPage(getDriver());
    }

    public GanttPage clickGanttMenu(){
        TestUtils.jsClick(getDriver(), ganttMenuItem);

        return new GanttPage(getDriver());
    }

    public BoardPage clickBoardMenu() {
        TestUtils.jsClick(getDriver(), boardMenuItem);

        return new BoardPage(getDriver());
    }

    public ArithmeticFunctionPage clickArithmeticFunctionMenu() {
        TestUtils.jsClick(getDriver(), arithmeticFunctionMenuItem);

        return new ArithmeticFunctionPage(getDriver());
    }

    public ArithmeticInlinePage clickArithmeticInlineMenu() {
        TestUtils.jsClick(getDriver(), arithmeticInlineMenuItem);

        return new ArithmeticInlinePage(getDriver());
    }

    public ParentPage clickParentMenu() {
        TestUtils.jsClick(getDriver(), parentMenuItem);

        return new ParentPage(getDriver());
    }

    public DefaultPage clickDefaultMenu() {
        TestUtils.jsClick(getDriver(), defaultMenuItem);

        return new DefaultPage(getDriver());
    }

    public RecycleBinPage clickRecycleBin(){
        recycleBinIcon.click();

        return new RecycleBinPage(getDriver());
    }

    public ChevronPage clickChevronMenu() {
        TestUtils.jsClick(getDriver(), chevronMenuItem);

        return new ChevronPage(getDriver());
    }

    public PlaceholderPage clickPlaceholderMenu() {
        TestUtils.jsClick(getDriver(), placeholderMenuItem);

        return new PlaceholderPage(getDriver());
    }
}
