package model;

import model.base.BaseEditPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utils.ProjectUtils;

public class ChildRecordsLoopEditPage extends BaseEditPage<ChildRecordsLoopPage> {

    @FindBy(id = "start_balance")
    private WebElement fieldStartBalance;

    @FindBy(xpath = "//input[@id='end_balance']")
    private WebElement fieldEndBalance;


    @FindBy(xpath = "//td[@class = 'pa-add-row-btn-col']/button")
    private WebElement addRowBtn;

    @FindBy(xpath = "//tr[@id='row-68-1']//div/i[contains(text(), 'clear')]")
    private WebElement deleteRow;

    public ChildRecordsLoopEditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ChildRecordsLoopPage createMasterPage() {
        return new ChildRecordsLoopPage(getDriver());
    }

    public ChildRecordsLoopEditPage fillStartBalance(String value) {
        fieldStartBalance.clear();
        fieldStartBalance.sendKeys(value);

        return this;
    }

    public ChildRecordsLoopEditPage clickAddRowBtn() {

        addRowBtn.click();
        return this;
    }

    public ChildRecordsLoopEditPage fillAmount(int numberOfRow, String value) {
        WebElement fieldAmount = getDriver().findElement(By.id("t-68-r-" + numberOfRow + "-amount"));

        fieldAmount.clear();
        fieldAmount.sendKeys(value);

        return this;
    }

    public ChildRecordsLoopEditPage fillItem(int numberOfRow, String value) {
        WebElement fieldItem = getDriver().findElement(By.id("t-68-r-" + numberOfRow + "-item"));

        fieldItem.clear();
        ProjectUtils.sendKeysOneByOne(fieldItem, value);

        return this;
    }

    public ChildRecordsLoopEditPage deleteRow(int numberOfRow){
        WebElement deleteRecord = getDriver().findElement(
                By.xpath("//tr[@id='row-68-" + numberOfRow + "']//div/i[contains(text(), 'clear')]"));

        deleteRecord.click();
        return this;
    }
}
