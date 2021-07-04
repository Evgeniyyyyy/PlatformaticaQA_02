package model;

import model.base.BaseListMasterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ArithmeticFunctionPage extends BaseListMasterPage<ArithmeticFunctionEditPage, ArithmeticFunctionViewPage> {

    @FindBy(xpath = "//button/i[text()='menu']")
    private WebElement actionsButton;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(xpath = "//tbody/tr/td[1]/i")
    private WebElement icon;

    @FindBy(css = "tr:nth-child(1) .material-icons")
    private  WebElement actionMenu;

    @FindBy(xpath = "//tr[@data-index='0']//a[contains(text(),'delete')]")
    private  WebElement actionMenuDelete;

    @FindBy(xpath = "//i[text()='delete_outline']")
    private WebElement recycleBinIcon;

    @FindBy(xpath = "//a[contains (text(), 'delete permanently')]")
    private WebElement deletePermanently;

    @FindBy(xpath = "//span[@class='notification']/b")
    private WebElement noticeRecycleBin;

    @FindBy(xpath = "//a[@aria-label='to page 2']")
    private WebElement pagination2;

    @FindBy(xpath = "//a[@aria-label='to page 1']")
    private WebElement pagination1;

    @FindBy(xpath = "//button[@class='btn btn-secondary dropdown-toggle']")
    private WebElement size;

    @FindBy(xpath = "//a[normalize-space()='25']")
    private WebElement size25;

    public ArithmeticFunctionPage(WebDriver driver) {
        super(driver); }

    public ArithmeticFunctionEditPage clickActions() {
        actionsButton.click();

        return new ArithmeticFunctionEditPage(getDriver());
    }

    public ArithmeticFunctionPage clickDeleteButton() {
        actionMenu.click();
        getWait().until(TestUtils.movingIsFinished(actionMenuDelete)).click();

        return new ArithmeticFunctionPage(getDriver());
    }

    public ArithmeticFunctionPage clickRecycleBinIcon(){
        recycleBinIcon.click();

        return new ArithmeticFunctionPage(getDriver());
    }

    public ArithmeticFunctionPage clickDeletePermanently(){
        deletePermanently.click();

        return new ArithmeticFunctionPage(getDriver());
    }

    public String getTextEmptyBin(){
        return table.getText();
    }

    @Override
    protected ArithmeticFunctionViewPage createViewPage() {
        return new ArithmeticFunctionViewPage(getDriver());
    }

    @Override
    protected ArithmeticFunctionEditPage createEditPage() {
        return new ArithmeticFunctionEditPage(getDriver());
    }

    public boolean iconCheck(String cssClass) {
        return icon.getAttribute("class").contains(cssClass);
    }

    public List<String> wrapValues(List<Integer> expectedValues) {
        return expectedValues.stream().map(String::valueOf).collect(Collectors.toList());
    }

    public String getNoticeRecycleBin(){
        return noticeRecycleBin.getText();
    }

    public ArithmeticFunctionPage clickPaginationButton(int value) {
        if (value==1){
            TestUtils.jsClick(getDriver(), pagination1);
        } else if (value==2) {
            TestUtils.jsClick(getDriver(), pagination2);
        }
        return new ArithmeticFunctionPage(getDriver());
    }

    public List<WebElement> getCells() {
        List<WebElement> tableRecords = getDriver().findElements(By.xpath("//div[@ class = 'card-body ']//table/tbody/tr"));
        return tableRecords;
    }

    public ArithmeticFunctionPage clickSizeButton() {
        size.click();
        return new ArithmeticFunctionPage(getDriver());
    }

    public ArithmeticFunctionPage clickSize25Button() {
        getWait().until(TestUtils.movingIsFinished(size25)).click();
        return new ArithmeticFunctionPage(getDriver());
    }
}
