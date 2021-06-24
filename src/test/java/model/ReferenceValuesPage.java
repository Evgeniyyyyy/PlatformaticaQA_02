package model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.TestUtils;

import java.util.List;
import java.util.stream.Collectors;

import static utils.TestUtils.*;

public class ReferenceValuesPage  extends MainPage{

    @FindBy(xpath = "//*[contains(text(),'create_new_folder')]")
    private WebElement referenceValuesCreateRecord;

    @FindBy(xpath = "//table[@id='pa-all-entities-table']/tbody/tr")
    private List<WebElement> tableRows;

    @FindBy (xpath="//td/i[@class='fa fa-check-square-o']")
    private static List<WebElement> tableRowsOnlyIcon;

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
        RowNumber--; //because List starts with 0
        if(isTableAvailable())
            return tableRows.get(RowNumber).findElements(By.xpath("//td[@class='pa-list-table-th']")).stream()
                    .map(el -> el.getText()).collect(Collectors.toList());
        else return null;
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

    public int getRowsValueWithFilter(String filterWord)
    {
        if(isTableAvailable())
            return tableColumns.stream().map(el -> el.getText()).filter(el -> el.contains(filterWord))
                    .collect(Collectors.toList()).size();
        else return 0;
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
