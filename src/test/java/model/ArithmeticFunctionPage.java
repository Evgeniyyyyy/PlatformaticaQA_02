package model;

import model.base.BaseListMasterPage;
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
}
