package model;

import model.base.BaseListMasterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

import static utils.TestUtils.*;

public class ReferenceValuesPage  extends BaseListMasterPage<ReferenceValuesEditPage, ReferenceValuesViewPage> {

    @FindBy(xpath = "//*[contains(text(),'create_new_folder')]")
    private static WebElement referenceValuesCreateRecord;

    @FindBy(xpath = "//table[@id='pa-all-entities-table']/tbody/tr")
    private List<WebElement> tableRows;

    @FindBy (xpath="//td[@class='pa-list-table-th']")
    private List<WebElement> tableColumns;

    @FindBy (xpath="//button[@name='toggle']/i")
    private static WebElement realResult;

    @FindBy (xpath="//div[@class='card-view'][span[text()!='Actions']]//a")
    private List<WebElement> tableValuesToggleOn;

    @FindBy (xpath="//input[@type='text'][@placeholder='Search']")
    private static WebElement searchButton;

    @FindBy (xpath="//table[@id='pa-all-entities-table']//tbody")
    private WebElement valuesOfTable;

    public ReferenceValuesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ReferenceValuesViewPage createViewPage() {
        return new ReferenceValuesViewPage(getDriver());
    }

    @Override
    protected ReferenceValuesEditPage createEditPage() {
        return new ReferenceValuesEditPage(getDriver());
    }

    public ReferenceValuesEditPage clickCreateButton() {
        TestUtils.jsClick(getDriver(), referenceValuesCreateRecord);

        return new ReferenceValuesEditPage(getDriver());
    }

    public int getTableRowsCount()
    {
        if(isTableAvailable()) return tableRows.size();
        else return 0;
    }

    public List<String> getRowsValue(int RowNumber)
    {
        if(isTableAvailable()) {
            String xpathRowNumber = (!isToggleOn()) ? "//tr[" + RowNumber + "]//td[@class='pa-list-table-th']" :
                    "//tr[" + RowNumber + "]//div[@class='card-view'][span[text()!='Actions']]//a";

            return getDriver().findElements(By.xpath(xpathRowNumber)).stream().map(el -> el.getText())
                    .collect(Collectors.toList());
        }
        else return null;
    }

    public boolean isSearchValuePresent(List<String> searchingRecord) {
        if (isTableAvailable()) {
            for (int i = 1; i <= tableRows.size(); i++)
                if (getRowsValue(i).equals(searchingRecord))
                    return true;
            return false;
        }
        else return false;
    }

    public List<String> getRowsValue()
    {
        if(isTableAvailable())
            return (!isToggleOn() ? tableColumns : tableValuesToggleOn).stream().map(el -> el.getText()).collect(Collectors.toList());
        else return null;
    }

    public List<String> getRowsValue(By by)
    {
        if(isTableAvailable())
            return getDriver().findElements(by).stream().map(el -> el.getText()).collect(Collectors.toList());
        else return null;
    }

    public ReferenceValuesPage deleteRecord(String NameOfRecord) {
        scrollClick(getDriver(), By.xpath("//tbody/tr[contains(.,'"+NameOfRecord + "')]//button"));
        getWait().until(movingIsFinished(By.xpath("//tr[contains(.,'" +NameOfRecord + "')]//a[contains (text(), 'delete')]")))
                .click();
        return new ReferenceValuesPage(getDriver());
    }

    public ReferenceValuesEditPage editRecord(String NameOfRecord) {
        scrollClick(getDriver(), By.xpath("//tbody/tr[contains(.,'"+NameOfRecord + "')]//button"));
        getWait().until(movingIsFinished(By.xpath("//tr[contains(.,'" +NameOfRecord + "')]//a[contains(@href, 'action_edit')]")))
                .click();
        return new ReferenceValuesEditPage(getDriver());
    }

    public ReferenceValuesViewPage viewRecord(String NameOfRecord) {
        scrollClick(getDriver(), By.xpath("//tbody/tr[contains(.,'"+NameOfRecord + "')]//button"));
        getWait().until(movingIsFinished(By.xpath("//tr[contains(.,'" +NameOfRecord + "')]//a[contains(@href, 'action_view')]")))
                .click();
        return new ReferenceValuesViewPage(getDriver());
    }

    public boolean isToggleOn()
    {
        return realResult.getAttribute("class").contains("toggle-on");
    }

    public ReferenceValuesPage clickToggle()
    {
        scrollClick(getDriver(), realResult);

        return new ReferenceValuesPage(getDriver());
    }

    public ReferenceValuesPage fillSearch(String searchWord) {
        searchButton.clear();
        searchButton.sendKeys(searchWord);

        return new ReferenceValuesPage(getDriver());
    }

    public WebElement getWebElementOfTable() {

        return valuesOfTable;
    }
}