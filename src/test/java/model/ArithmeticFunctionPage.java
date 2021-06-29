package model;

import com.beust.jcommander.Strings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ArithmeticFunctionPage extends MainPage {

    @FindBy(xpath = "//i[text()='create_new_folder']")
    private WebElement newButton;

    @FindBy(className = "card-body")
    private WebElement table;

    @FindBy(className = "col-md-12")
    private WebElement viewTable;

    @FindBy(xpath = "//tbody/tr")
    private List<WebElement> rows;

    @FindBy(xpath = "//tbody/tr/td[1]/i")
    private WebElement icon;

    @FindBy(xpath = "//a[@aria-label='to page 2']")
    private WebElement pagination2;

    @FindBy(xpath = "//a[@aria-label='to page 1']")
    private WebElement pagination1;

    @FindBy(xpath = "//button[@class='btn btn-secondary dropdown-toggle']")
    private WebElement size;

    @FindBy(xpath = "//a[normalize-space()='25']")
    private WebElement size25;

    @FindBy(css = "tr:nth-child(1) .material-icons")
    private  WebElement actionMenu;

    @FindBy(xpath = "//tr[@data-index='0']//a[contains(text(),'view')]")
    private  WebElement actionMenuView;

    @FindBy(xpath = "//tr[@data-index='0']//a[contains(text(),'delete')]")
    private  WebElement actionMenuDelete;

    @FindBy(xpath = "//i[text()='delete_outline']")
    private WebElement recycleBinIcon;

    @FindBy(xpath = "//a[contains (text(), 'delete permanently')]")
    private WebElement deletePermanently;

    @FindBy(xpath = "//span[@class='notification']/b")
    private WebElement noticeRecycleBin;

    public ArithmeticFunctionPage(WebDriver driver) { super(driver); }

    public ArithmeticFunctionEditPage clickNewButton() {
        newButton.click();

        return new ArithmeticFunctionEditPage(getDriver());
    }
    public ArithmeticFunctionPage clickViewButton() {
        actionMenu.click();
        getWait().until(TestUtils.movingIsFinished(actionMenuView)).click();

        return new ArithmeticFunctionPage(getDriver());
    }

    public ArithmeticFunctionEditPage clickEditButton(int row) {
        getDriver().findElement(By.xpath("//tr[@data-index='"+ row +"']//button/i[text()='menu']")).click();
        getWait().until(TestUtils.movingIsFinished(getDriver().
                findElement(By.xpath("//tr[@data-index='"+ row +"']//a[text()='edit']")))).click();

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

    public boolean isTableEmpty() {
        return Strings.isStringEmpty(table.getText());
    }

    public List<String> getRow(int number) {
        return rows.get(number).findElements(By.className("pa-list-table-th"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
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
        size25.click();
        return new ArithmeticFunctionPage(getDriver());
    }

    public List<String> viewData(){
        return viewTable.findElements(By.xpath("//span[@class='pa-view-field']"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public String getTextEmptyBin(){
        return table.getText();
    }

    public int getRowCount() {
        if (isTableEmpty()) {
            return 0;
        } else {
            return rows.size();
        }
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