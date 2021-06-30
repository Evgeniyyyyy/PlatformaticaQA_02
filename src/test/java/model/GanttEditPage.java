package model;

import model.base.BaseEditPage;
import model.base.BaseModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.text.SimpleDateFormat;

public class GanttEditPage extends BaseEditPage<GanttListPage> {

    @FindBy(id = "string")
    private WebElement ganttString;

    @FindBy(id = "text")
    private WebElement ganttText;

    @FindBy(id = "int")
    private WebElement ganttInt;

    @FindBy(id = "decimal")
    private WebElement ganttDecimal;

    @FindBy(id = "date")
    private WebElement ganttDate;

    @FindBy(xpath = "//div[contains(text(),'apptester1@tester.test')]")
    private WebElement ganttUser;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    public GanttEditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected GanttListPage createMasterPage() {
        return new GanttListPage(getDriver());
    }

    public GanttEditPage fillString(String value) {
        ganttString.sendKeys(value);

        return this;
    }

    public void clearString() {
        ganttString.clear();
    }

    public GanttEditPage fillText(String value) {
        ganttText.sendKeys(value);

        return this;
    }

    public void clearText() {
        ganttText.clear();
    }

    public GanttEditPage fillInt(String value) {
        ganttInt.sendKeys(value);

        return this;
    }

    public void clearInt() {
        ganttInt.clear();
    }

    public GanttEditPage fillDecimal(String value) {
        ganttDecimal.sendKeys(value);

        return this;
    }

    public void clearDecimal() {
        ganttDecimal.clear();
    }

    public GanttEditPage fillDate(SimpleDateFormat value) {
        ganttDate.click();

        return this;
    }

    public GanttEditPage clearFields(){
        clearString();
        clearText();
        clearInt();
        clearDecimal();

        return this;
    }
}
