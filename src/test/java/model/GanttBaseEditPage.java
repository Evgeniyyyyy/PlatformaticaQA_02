package model;

import model.base.BaseEditPage;
import model.base.BaseMasterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

public abstract class GanttBaseEditPage<MasterPage extends BaseMasterPage, ThisPage extends GanttBaseEditPage> extends BaseEditPage<MasterPage> {

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

    @FindBy(id = "datetime")
    private WebElement ganttDateTime;

    @FindBy(id = "pa-entity-form-save-btn")
    private WebElement saveButton;

    @FindBy(id = "pa-entity-form-draft-btn")
    private WebElement saveDraftButton;

    @FindBy(xpath = "//button[@data-id='user']")
    private WebElement userName;

    public GanttBaseEditPage(WebDriver driver) {
        super(driver);
    }

    public ThisPage fillString(String value) {
        ganttString.sendKeys(value);

        return (ThisPage)this;
    }

    public void clearString() {
        ganttString.clear();
    }

    public ThisPage fillText(String value) {
        ganttText.sendKeys(value);

        return (ThisPage)this;
    }

    public void clearText() {
        ganttText.clear();
    }

    public ThisPage fillInt(String value) {
        ganttInt.sendKeys(value);

        return (ThisPage)this;
    }

    public void clearInt() {
        ganttInt.clear();
    }

    public ThisPage fillDecimal(String value) {
        ganttDecimal.sendKeys(value);

        return (ThisPage)this;
    }

    public void clearDecimal() {
        ganttDecimal.clear();
    }

    public ThisPage fillDate(String value) {
        ganttDate.click();
        ganttDate.clear();
        ganttDate.sendKeys(value);

        return (ThisPage)this;
    }

    public ThisPage fillDateTime(String value) {
        ganttDateTime.click();
        ganttDateTime.clear();
        ganttDateTime.sendKeys(value);

        return (ThisPage)this;
    }

    public ThisPage clearFields(){
        clearString();
        clearText();
        clearInt();
        clearDecimal();

        return (ThisPage)this;
    }

    public ThisPage findUser(String value) {
        TestUtils.scrollClick(getDriver(), userName);
        TestUtils.jsClick(getDriver(), getDriver().findElement(By.xpath(
                "//span[text()='" + value + "']")));

        return (ThisPage)this;
    }
}
