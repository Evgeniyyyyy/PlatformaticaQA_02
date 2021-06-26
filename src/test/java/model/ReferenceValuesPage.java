package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

import static utils.TestUtils.*;

public class ReferenceValuesPage  extends BasePage{

    @FindBy(xpath = "//*[contains(text(),'create_new_folder')]")
    private WebElement referenceValuesCreateRecord;

    @FindBy(xpath = "//table[@id='pa-all-entities-table']/tbody/tr")
    private List<WebElement> tableRows;

    @FindBy (xpath="//td[@class='pa-list-table-th']")
    private static List<WebElement> tableColumns;

    public ReferenceValuesPage(WebDriver driver) {
        super(driver);
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
        if(isTableAvailable())
            return getDriver().findElements(By.xpath("//tr["+RowNumber+"]//td[@class='pa-list-table-th']")).stream()
                    .map(el -> el.getText()).collect(Collectors.toList());
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
            return tableColumns.stream().map(el -> el.getText()).collect(Collectors.toList());
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
}
