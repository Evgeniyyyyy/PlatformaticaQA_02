package model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public class MainPage extends BaseModel {

    @FindBy(xpath = "//p[text()= ' Fields ']")
    private WebElement fieldsMenuItem;

    @FindBy(xpath = "//p[text()= ' Readonly ']")
    private WebElement readonlyMenuItem;

    @FindBy(xpath = "//p[text()= ' Gantt ']")
    private WebElement ganttMenuItem;

    @FindBy(xpath = "//p[text()= ' Board ']")
    private WebElement boardMenuItem;

    @FindBy(xpath = "//p[text()= ' Arithmetic Function ']")
    private WebElement arithmeticFunctionMenuItem;

    public MainPage(WebDriver driver) {
        super(driver);
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
}
