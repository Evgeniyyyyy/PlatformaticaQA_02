package model;

import model.base.BaseModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import static utils.TestUtils.scrollClick;

public class MainPage extends BaseModel {

    @FindBy(xpath = "//p[text()= ' Assign ']")
    private WebElement assignMenuItem;

    @FindBy(xpath = "//p[text()= ' Fields ']")
    private WebElement fieldsMenuItem;

    @FindBy(xpath = "//p[text()= ' Import ']")
    private WebElement importMenuItem;

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

    @FindBy(xpath = "//p[text()= ' Reference values ']")
    private WebElement referenceValuesMenuItem;

    @FindBy(xpath = "//p[text()= ' Placeholder ']")
    private WebElement placeholderDifMenuItem;

    @FindBy(xpath = "//p[text()= ' Import values ']")
    private WebElement importValuesMenuItem;

    @FindBy(xpath = "//p[text()= ' Tag ']")
    private WebElement tagMenuItem;

    @FindBy(xpath = "//p[text()= ' Export destination ']")
    private WebElement exportDestinationMenuItem;

    @FindBy(xpath = "//p[text()= ' Child records loop ']")
    private WebElement childRecordsLoopMenuItem;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public AssignPage clickAssignMenu() {
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

    public ReadonlyPage clickReadonlyMenu() {
        TestUtils.jsClick(getDriver(), readonlyMenuItem);

        return new ReadonlyPage(getDriver());
    }

    public GanttPage clickGanttMenu() {
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

    public ImportPage clickImportMenu() {
        TestUtils.jsClick(getDriver(), importMenuItem);

        return new ImportPage(getDriver());
    }

    public ImportValuesPage clickImportValuesMenu() {
        TestUtils.jsClick(getDriver(), importValuesMenuItem);

        return new ImportValuesPage(getDriver());
    }

    public ExportDestinationPage clickExportDestinationMenu() {
        TestUtils.jsClick(getDriver(), exportDestinationMenuItem);

        return new ExportDestinationPage(getDriver());
    }

    public RecycleBinPage clickRecycleBin() {
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

    public TagPage clickTagMenu(){
        TestUtils.jsClick(getDriver(), tagMenuItem);

        return new TagPage(getDriver());
    }


    public PlaceholderDifPage clickPlaceholderDifMenu() {
        scrollClick(getDriver(), placeholderDifMenuItem);

        return new PlaceholderDifPage(getDriver());
    }


    public ReferenceValuesPage clickReferenceValueMenu() {
        TestUtils.jsClick(getDriver(), referenceValuesMenuItem);

        return new ReferenceValuesPage(getDriver());
    }

    public ChildRecordsLoopPage clickChildRecordsLoopMenu() {
        TestUtils.jsClick(getDriver(), childRecordsLoopMenuItem);

        return new ChildRecordsLoopPage(getDriver());
    }

    public boolean isTableAvailable() {
        String table=getDriver().findElement(By.xpath("//div[contains(@class,'card-body')]"))
                .getText();
        if (table.trim().isBlank() || table.trim().isEmpty()
                || table.contains("Recycle bin is currently empty"))
            return false;
        else return true;
    }

    public int getNumberOfNotification() {
        if (getDriver().getPageSource().contains("<span class=\"notification\">"))
            return  Integer.parseInt(getDriver().findElement(By.xpath("//span[@class='notification']/b")).getText());
        else return 0;
    }

    public ReferenceValuesPage1 clickReferenceValuesMenu() {
        TestUtils.jsClick(getDriver(), referenceValuesMenuItem);

        return new ReferenceValuesPage1(getDriver());
    }
}
